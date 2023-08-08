package com.icbc.digitalhuman.websocket;

import com.icbc.digitalhuman.dto.InfoAndText;
import com.icbc.digitalhuman.entity.Conversation;
import com.icbc.digitalhuman.entity.NecessaryInfo;
import com.icbc.digitalhuman.entity.UnnecessaryInfo;
import com.icbc.digitalhuman.entity.User;
import com.icbc.digitalhuman.service.ConversationService;
import com.icbc.digitalhuman.utils.ApplicationContextRegister;
import com.icbc.digitalhuman.utils.DateUtils;
import com.icbc.digitalhuman.utils.FormatUtils;
import com.icbc.digitalhuman.utils.RegexUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Future;

@ServerEndpoint("/test-one")
@Component
//@Service
public class WebSocket {
    private static Map<String, Session> clients = new ConcurrentHashMap<>();
    private static int user_state = 0;//1 填写阶段 2 修改阶段 3 评分阶段
    private static int modify_flag = 0;
    private static CopyOnWriteArraySet<Session> idle = new CopyOnWriteArraySet<>();
    private static final ConcurrentHashMap<Session, Future<Void>> busy = new ConcurrentHashMap<>();

    InfoAndText infoAndText = new InfoAndText();

    /**
     * 客户端连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        InetSocketAddress remoteAddress = WebSocketUtils.getRemoteAddress(session);
        System.out.println("有新的客户端连接了,ip为:" + remoteAddress + "ID为：" + session.getId());
        //将新用户存入在线的组
        clients.put(session.getId(), session);
        idle.add(session);
        //sendAll(JSON.toJSONString(new JsonResult<Map<String, Integer>>(nodeService.getOnlineOfflineCount(), "workState")));
        System.out.println(User.username + "正在登录");
        send(session.getId(), User.username + "你好，我是工小妍，如果需要提交审批，请以冒号分开，例如：");
        send(session.getId(), "预估耗时（分钟）:20");
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

        String user_request = RegexUtils.judge_user_desire(message);

        String User_ID = session.getId();
        System.out.println("服务端收到客户端" + User_ID + "发来的消息: " + message + "");

        String reply = "";
        String info = "";

        // 说明规则
        if (user_state == 0 && user_request == "1") {
            reply = "检测到您似乎准备进行批量作业，请输入相应的参数，以便我进行处理";
            user_state = 1;
        }
        // 填写信息
        if (user_state == 1 && user_request == "0") {
            infoAndText.setText(message);
            infoAndText = RegexUtils.extractInfo(infoAndText);
            NecessaryInfo necessaryInfo = infoAndText.getNecessaryInfo();
            UnnecessaryInfo unnecessaryInfo = infoAndText.getUnnecessaryInfo();

            DateUtils dateUtils = new DateUtils();
            dateUtils.setVersionAndProductionDate();
            String version = dateUtils.getVersion();
            String productionDate = dateUtils.getProductionDate();
            unnecessaryInfo.setVersion(version);
            unnecessaryInfo.setProductionDate(productionDate);

            info = FormatUtils.formatInfo(necessaryInfo, unnecessaryInfo);

            if (necessaryInfo.checkAllFilled().equals("全部必填项均有值") && modify_flag == 0) {
                reply = "已收到您的消息，" + necessaryInfo.checkAllFilled() + "，请仔细阅读后回复没问题或有问题";
                user_state = 2;
            } else if (modify_flag == 1) {
                reply = "以下是您修改后的内容，请回复没问题或有问题";
                user_state = 2;
            } else {
                reply = "已收到您的消息，" + necessaryInfo.checkAllFilled() + "请继续补充";
            }
        }
        // 修改信息
        if (user_state == 2 && user_request == "4") {
            reply = "好的，请您再次提交您想要修改的部分";
            modify_flag = 1;
            user_state = 1;
        }
        // 请求打分
        if (user_state == 2 && user_request == "3") {
            reply = "请您对本次服务进行评分（1-10分）并留下您的意见或建议";
            user_state = 3;
        }
        // 打分阶段
        if (user_state == 3 && user_request == "0") {
            int evaluation = RegexUtils.extractEvaluation(message); // 提取评分
            String feedback = RegexUtils.extractFeedback(message); // 提取留言

            if (evaluation >= 1 && evaluation <= 10) {
                // 用户输入合法的评分，记录评分和留言
                Conversation conversation = new Conversation();
                conversation.setEvaluation(evaluation);
                conversation.setFeedback(feedback);
                conversation.setUsername(User.username);
                ApplicationContext act = ApplicationContextRegister.getApplicationContext();
                ConversationService conversationService = act.getBean(ConversationService.class);
                conversationService.create(conversation);
                // 提示用户感谢并重置状态
                reply = "感谢您的评价和留言，祝您工作愉快！";
                user_state = 0;
            } else {
                // 用户输入的评分不合法，重新提示
                reply = "请您对本次服务进行评分（1-10分）并留下您的意见或建议：";
            }
        }

        send(User_ID, reply);
        if (info != "") {
            send(User_ID, info);
        }
    }

    /**
     * 推送消息到指定用户
     *
     * @param userId  用户ID
     * @param message 发送的消息
     */
    public static void send(String userId, String message) {
        Session session = clients.get(userId);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            System.out.println("错误！！\r\n链接" + userId + "发送失败");
        }

    }
}
