package com.icbc.digitalhuman.websocket;

import com.icbc.digitalhuman.dto.InfoAndText;
import com.icbc.digitalhuman.entity.NecessaryInfo;
import com.icbc.digitalhuman.entity.UnnecessaryInfo;
import com.icbc.digitalhuman.entity.User;
import com.icbc.digitalhuman.entity.WorkFlowControl;
import com.icbc.digitalhuman.utils.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Future;

@ServerEndpoint(value = "/test-one", configurator = HttpSessionConfigurator.class)
@Component
//@Service
public class WebSocket {
    private static Map<String, Session> clients = new ConcurrentHashMap<>();
    private static int user_state = 0;  // 0 选择日期 1 填写开始 2 批量选择 3 完成评价
    private static int modify_flag = 0;
    private static CopyOnWriteArraySet<Session> idle = new CopyOnWriteArraySet<>();
    private static final ConcurrentHashMap<Session, Future<Void>> busy = new ConcurrentHashMap<>();
    private HttpSession httpSession;
    private InfoAndText infoAndText = new InfoAndText();
    private StringBuilder dialog = new StringBuilder();
    private String username;
    private String reply;

    /**
     * 客户端连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        InetSocketAddress remoteAddress = WebSocketUtils.getRemoteAddress(session);
        System.out.println("有新的客户端连接了,ip为:" + remoteAddress + "ID为：" + session.getId());
        //将新用户存入在线的组
        clients.put(session.getId(), session);
        idle.add(session);

        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        User user = (User) httpSession.getAttribute("user");

        if (user != null) {
            // 用户已登录
            username = user.getUsername();  // 保存用户名到成员变量
            System.out.println(username + "正在登录");
            reply = username + "您好，欢迎提交批量申请表，请选择所需的投产日期，如都不满意，请在输入框内发送您期望的日期。";
            sendMessage(session.getId(), reply);
            LogUtils.appendToDialog(dialog, username, reply);
            sendMessage(session.getId(), DateUtils.chooseProductionDate());
        } else {
            // 用户未登录
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 客户端关闭
     *
     * @param session session
     */
    @OnClose
    public void onClose(Session session) {
        InetSocketAddress remoteAddress = WebSocketUtils.getRemoteAddress(session);
        System.out.println("有用户断开了, ip为:" + remoteAddress);
        //将掉线的用户移除在线的组里
        clients.remove(session.getId());
        idle.remove(session);
        busy.remove(session);

        LogUtils.saveDialog(dialog.toString(), username);
    }

    /**
     * 发生错误
     *
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     *
     * @param message 消息对象
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        LogUtils.appendToDialog(dialog, username, message);
        int user_request = RegexUtils.messageJudgement(message);
        String User_ID = session.getId();
        System.out.println("服务端收到客户端" + User_ID + "发来的消息: " + message + "");

        // 说明规则
        if (user_state == 0 && user_request == 1) {
            reply = "请依据表格进行提交数据，星号为必填项，我们为您生成了一些默认值，如需修改，请直接覆盖即可。" +
                    "填写规则为“字段：内容”，不同字段之间请换行。";
            user_state = 1;

            NecessaryInfo necessaryInfo = new NecessaryInfo();
            UnnecessaryInfo unnecessaryInfo = new UnnecessaryInfo();

            DateUtils dateUtils = new DateUtils();
            necessaryInfo.setVersion(dateUtils.setVersion(message));
            necessaryInfo.setProductionDate(message);
            necessaryInfo.setEffectiveDate(dateUtils.setEffectiveDate(message));

            infoAndText.setNecessaryInfo(necessaryInfo);
            infoAndText.setUnnecessaryInfo(unnecessaryInfo);

            sendEntity(User_ID, infoAndText);
            sendMessage(User_ID, reply);
        }
        // 填写信息
        if (user_state == 1 && user_request == 0) {
            infoAndText.setText(message);
            infoAndText = RegexUtils.extractInfo(infoAndText);
            String checkResult = infoAndText.getNecessaryInfo().checkAllFilled();
            if (checkResult.equals("全部必填项均有值") && modify_flag == 0) {
                reply = "已收到您的消息，" + checkResult + "，请问是否需要修改？";
                user_state = 2;
                sendEntity(User_ID, infoAndText);
                sendMessage(User_ID, reply);
                sendMessage(User_ID, "#111");
            } else if (modify_flag == 1) {
                reply = "以上是您修改后的内容，请问是否需要修改？";
                user_state = 2;
                sendEntity(User_ID, infoAndText);
                sendMessage(User_ID, reply);
                sendMessage(User_ID, "#111");
            } else {
                reply = "已收到您的消息，" + checkResult + "请继续补充。";
                sendEntity(User_ID, infoAndText);
                sendMessage(User_ID, reply);
            }
        }
        // 修改信息
        if (user_state == 2 && user_request == 2) {
            reply = "好的，请输入您想要修改的部分。";
            modify_flag = 1;
            user_state = 1;
            sendMessage(User_ID, reply);
        }
        // 场次选择
        if (user_state == 2 && user_request == 3) {
            reply = "根据您提交的信息，我们有以下批量场次可供选择，如都不满意，请与批量开发工作人员沟通。";
            sendMessage(User_ID, reply);
            String moduleName = infoAndText.getNecessaryInfo().getApplication() + "_" + infoAndText.getNecessaryInfo().getUpstreamApplication();
            BatchUtils batchUtils = new BatchUtils();
            sendMessage(User_ID, batchUtils.find(moduleName));
            user_state = 3;
            modify_flag = 0;
        }
        // 感谢服务
        if (user_state == 3 && user_request == 0) {
            reply = "本次批量申请任务已完成,请您对我们的服务进行评分并留下宝贵的意见。";
            sendMessage(User_ID, reply);
            sendMessage(User_ID, "#123");
            LogUtils.appendToDialog(dialog, username, reply);
            user_state = 0;
            modify_flag = 0;
            infoAndText.getNecessaryInfo().setApplication(message);
            SqlUtils sqlUtils = new SqlUtils();
            sqlUtils.toSql(infoAndText, username);
        }
        LogUtils.appendToDialog(dialog, "Bot", reply);
    }

    /**
     * 推送消息
     *
     * @param userId  用户ID
     * @param message 发送的消息
     */
    public static void sendMessage(String userId, String message) {
        Session session = clients.get(userId);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            System.out.println("错误！！\r\n链接" + userId + "发送失败");
        }
    }

    /**
     * 推送实体
     */
    public static void sendEntity(String userId, InfoAndText infoAndText) {
        Session session = clients.get(userId);
        try {
            String infoAndTextJson = FormatUtils.convertInfoAndTextToJson(infoAndText);
            session.getBasicRemote().sendText(infoAndTextJson);
        } catch (IOException e) {
            System.out.println("错误！！\r\n链接" + userId + "发送失败");
        }
    }
}


