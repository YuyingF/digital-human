package com.icbc.digitalhuman.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icbc.digitalhuman.dto.InfoAndText;
import com.icbc.digitalhuman.entity.NecessaryInfo;
import com.icbc.digitalhuman.entity.UnnecessaryInfo;
import com.icbc.digitalhuman.entity.User;
import com.icbc.digitalhuman.utils.DateUtils;
import com.icbc.digitalhuman.utils.RegexUtils;
import com.icbc.digitalhuman.utils.SqlUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Future;

@ServerEndpoint(value = "/test-one", configurator = HttpSessionConfigurator.class)
@Component
//@Service
public class WebSocket {
    private static Map<String, Session> clients = new ConcurrentHashMap<>();
    private static int user_state = 0;
    private static int modify_flag = 0;
    private static CopyOnWriteArraySet<Session> idle = new CopyOnWriteArraySet<>();
    private static final ConcurrentHashMap<Session, Future<Void>> busy = new ConcurrentHashMap<>();
    InfoAndText infoAndText = new InfoAndText();

    private HttpSession httpSession;
    private String username; // 保存用户名的成员变量

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
            sendMessage(session.getId(), username + "您好，欢迎提交批量申请表，请选择您希望的投产日期。");
            sendMessage(session.getId(),"#123");
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

        // 说明规则
        if (user_state == 0 && user_request == "1") {
            reply = "检测到您似乎准备进行批量作业，请输入相应的参数，以便我进行处理";
            user_state = 1;

            NecessaryInfo necessaryInfo = new NecessaryInfo();
            UnnecessaryInfo unnecessaryInfo = new UnnecessaryInfo();

            DateUtils dateUtils = new DateUtils();
            dateUtils.setDate();
            necessaryInfo.setVersion(dateUtils.getVersion());
            necessaryInfo.setProductionDate(dateUtils.getProductionDate());
            necessaryInfo.setEffectiveDate(dateUtils.getEffectiveDate());

            infoAndText.setNecessaryInfo(necessaryInfo);
            infoAndText.setUnnecessaryInfo(unnecessaryInfo);

            System.out.println("开始的时候" + infoAndText.getNecessaryInfo().getProductionDate());
            System.out.println("------------------------------");

            sendEntity(User_ID, infoAndText);
        }
        // 填写信息
        if (user_state == 1 && user_request == "0") {
            infoAndText.setText(message);
            infoAndText = RegexUtils.extractInfo(infoAndText);
            String checkResult = infoAndText.getNecessaryInfo().checkAllFilled();
            if (checkResult.equals("全部必填项均有值") && modify_flag == 0) {
                reply = "已收到您的消息，" + checkResult + "，请仔细阅读后回复没问题或有问题";
                user_state = 2;
            } else if (modify_flag == 1) {
                reply = "以下是您修改后的内容，请回复没问题或有问题";
                user_state = 2;
            } else {
                reply = "已收到您的消息，" + checkResult + "请继续补充";
            }
            sendEntity(User_ID, infoAndText);
        }
        // 修改信息
        if (user_state == 2 && user_request == "4") {
            reply = "好的，请您再次提交您想要修改的部分";
            modify_flag = 1;
            user_state = 1;
        }
        // 感谢服务
        if (user_state == 2 && user_request == "3") {
            reply = "本次批量申请任务已完成";
            user_state = 0;
            SqlUtils sqlUtils = new SqlUtils();
            sqlUtils.toSql(infoAndText, username);
        }

        sendMessage(User_ID, reply);
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
            String infoAndTextJson = convertInfoAndTextToJson(infoAndText);
            session.getBasicRemote().sendText(infoAndTextJson);
        } catch (IOException e) {
            System.out.println("错误！！\r\n链接" + userId + "发送失败");
        }
    }
    public static String convertInfoAndTextToJson(InfoAndText infoAndText) {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = "{" +
                "\"版本\":\"" + infoAndText.getNecessaryInfo().version + "\"," +
                "\"中心项目编号\":\"" + infoAndText.getNecessaryInfo().centralProjectNumber + "\"," +
                "\"项目名称\":\"" + infoAndText.getNecessaryInfo().projectName + "\"," +
                "\"需求子条目\":\"" + infoAndText.getNecessaryInfo().requirementSubItem + "\"," +
                "\"应用\":\"" + infoAndText.getNecessaryInfo().application + "\"," +
                "\"批量种类\":\"" + infoAndText.getNecessaryInfo().batchCategory + "\"," +
                "\"批量场次\":\"" + infoAndText.getNecessaryInfo().batchSession + "\"," +

                "\"作业id\":\"" + infoAndText.getUnnecessaryInfo().jobId + "\"," +
                "\"作业名称\":\"" + infoAndText.getUnnecessaryInfo().jobName + "\"," +

                "\"作业描述\":\"" + infoAndText.getNecessaryInfo().jobDescription + "\"," +
                "\"前提作业\":\"" + infoAndText.getNecessaryInfo().prerequisiteJob + "\"," +
                "\"执行频度\":\"" + infoAndText.getNecessaryInfo().executionFrequency + "\"," +
                "\"执行范围\":\"" + infoAndText.getNecessaryInfo().executionScope + "\"," +
                "\"处理存过接口（程序接口）\":\"" + infoAndText.getNecessaryInfo().storedProcedureInterface + "\"," +
                "\"接口输入参数\":\"" + infoAndText.getNecessaryInfo().interfaceInputParameters + "\"," +
                "\"接口输出参数\":\"" + infoAndText.getNecessaryInfo().interfaceOutputParameters + "\"," +
                "\"是否支持重跑\":\"" + infoAndText.getNecessaryInfo().isRetrySupported + "\"," +
                "\"是否会发生中断\":\"" + infoAndText.getNecessaryInfo().isInterruptPossible + "\"," +

                "\"中断解决方案\":\"" + infoAndText.getUnnecessaryInfo().interruptionSolution + "\"," +

                "\"预估耗时\":\"" + infoAndText.getNecessaryInfo().estimatedTime + "\"," +
                "\"申请类型\":\"" + infoAndText.getNecessaryInfo().applicationType + "\"," +
                "\"生效日期\":\"" + infoAndText.getNecessaryInfo().effectiveDate + "\"," +
                "\"交付日期\":\"" + infoAndText.getNecessaryInfo().deliveryDate + "\"," +
                "\"投产日期\":\"" + infoAndText.getNecessaryInfo().productionDate + "\"," +
                "\"上游应用\":\"" + infoAndText.getNecessaryInfo().upstreamApplication + "\"," +

                //不必要的
                "\"执行频度补充说明\":\"" + infoAndText.getUnnecessaryInfo().executionFrequencyDescription + "\"," +
                "\"执行范围说明\":\"" + infoAndText.getUnnecessaryInfo().executionScopeDescription + "\"," +
                "\"接口输入参数补充说明\":\"" + infoAndText.getUnnecessaryInfo().inputParameterDescription + "\"," +

                "\"文件接口名\":\"" + infoAndText.getUnnecessaryInfo().fileInterfaceName + "\"," +
                "\"文件结构是否变化\":\"" + infoAndText.getUnnecessaryInfo().isFileStructureChanged + "\"," +
                "\"上游应用文本中文名\":\"" + infoAndText.getUnnecessaryInfo().upstreamApplicationChineseName + "\"," +
                "\"临时表字段是否需要处理\":\"" + infoAndText.getUnnecessaryInfo().isTemporaryTableFieldsRequired + "\"," +
                "\"是否有存量文件\":\"" + infoAndText.getUnnecessaryInfo().hasLegacyFiles + "\"," +
                "\"上游文本相关说明\":\"" + infoAndText.getUnnecessaryInfo().upstreamTextDescription + "\"," +
                "\"上游联系人\":\"" + infoAndText.getUnnecessaryInfo().upstreamContact + "\"," +
                "\"上游文件传输方式\":\"" + infoAndText.getUnnecessaryInfo().upstreamFileTransferMethod + "\"," +
                "\"上游点对点传输接收信息\":\"" + infoAndText.getUnnecessaryInfo().upstreamPointToPointTransmission + "\"," +
                "\"下游应用\":\"" + infoAndText.getUnnecessaryInfo().downstreamApplication + "\"," +
                "\"下游目标接口\":\"" + infoAndText.getUnnecessaryInfo().downstreamTargetInterface + "\"," +
                "\"下游联系人\":\"" + infoAndText.getUnnecessaryInfo().downstreamContact + "\"," +
                "\"下游文件传输方式\":\"" + infoAndText.getUnnecessaryInfo().downstreamFileTransferMethod + "\"," +
                "\"下游点对点传输接收信息\":\"" + infoAndText.getUnnecessaryInfo().downstreamPointToPointTransmission + "\"," +
                "\"开发组补充说明\":\"" + infoAndText.getUnnecessaryInfo().developmentTeamDescription + "\"," +
                "\"版本库\":\"" + infoAndText.getUnnecessaryInfo().versionLibrary + "\"," +
                "\"程序清单\":\"" + infoAndText.getUnnecessaryInfo().programList + "\"," +
                "\"文件所属应用类型\":\"" + infoAndText.getUnnecessaryInfo().fileApplicationType + "\"," +
                "\"分布式作业名称\":\"" + infoAndText.getUnnecessaryInfo().distributedJobName + "\"," +
                "\"分布式作业执行时间\":\"" + infoAndText.getUnnecessaryInfo().distributedJobExecutionTime + "\"" +
                "}";
        return json;
    }
}


