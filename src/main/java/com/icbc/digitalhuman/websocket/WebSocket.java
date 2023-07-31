package com.icbc.digitalhuman.websocket;

import com.icbc.digitalhuman.dto.InfoAndText;
import com.icbc.digitalhuman.entity.Conversation;
import com.icbc.digitalhuman.entity.NecessaryInfo;
import com.icbc.digitalhuman.entity.UnnecessaryInfo;
import com.icbc.digitalhuman.entity.User;
import com.icbc.digitalhuman.mapper.ConversationMapper;
import com.icbc.digitalhuman.utils.CreatSQLCode;
import com.icbc.digitalhuman.utils.Regex;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Future;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.icbc.digitalhuman.entity.User.user_logging;
import static com.icbc.digitalhuman.utils.WriteReport.writeReport;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@ServerEndpoint("/test-one")
@Component
//@Service
public class WebSocket {
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    private static Map<String, Conversation> ID_to_conversation = new ConcurrentHashMap<>();
    private static int user_state=0;//1 进入审批阶段 2进入确认阶段
    private static int modify_flag=0;
    private static CopyOnWriteArraySet<Session> idle = new CopyOnWriteArraySet<>();
    //synchronize的对象需要是final
    private static final ConcurrentHashMap<Session, Future<Void>> busy = new ConcurrentHashMap<>();
    private InfoAndText infoAndText = new InfoAndText();

    @Resource
    ConversationMapper conversationMapper;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     * 在外部可以获取此连接的所有websocket对象，并能对其触发消息发送功能，我们的定时发送核心功能的实现在与此变量
     */
    //private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();
    public CopyOnWriteArraySet<Session> getIdle() {
        return idle;
    }


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
        Conversation conversation = new Conversation();
        ID_to_conversation.put(session.getId(),conversation);
        idle.add(session);
        //sendAll(JSON.toJSONString(new JsonResult<Map<String, Integer>>(nodeService.getOnlineOfflineCount(), "workState")));
        System.out.println(User.user_logging+"正在登录");

        ID_to_conversation.get(session.getId()).setId(User.user_logging);
        ID_to_conversation.get(session.getId()).setFeedback(1);
        ID_to_conversation.get(session.getId()).setAnswer(User.user_logging+"你好，我是工小妍，如果需要提交审批，请以冒号分开，例如：预估耗时（分钟）:20");
        sendMessageToOneUser(session.getId(), User.user_logging+"你好，我是工小妍，如果需要提交审批，请以冒号分开，例如：");
        sendMessageToOneUser(session.getId(), "预估耗时（分钟）:20");
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
        try {
            save_to_database(ID_to_conversation.get(session.getId()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String reply_add="empty";
        String user_request="0";
        NecessaryInfo necessaryInfo = infoAndText.getNecessaryInfo();
        UnnecessaryInfo unnecessaryInfo = infoAndText.getUnnecessaryInfo();
        String formattedDate = formatter.format(date);
        user_request=Conversation.judge_user_desire(message);
        String User_ID = session.getId();
        System.out.println("服务端收到客户端" + User_ID + "发来的消息: " + message + "");
        if(message.charAt(0)=='#') {

            if (message.equals("#good")){
                System.out.println(message);
                sendMessageToOneUser(User_ID, "已收到您的好评，感谢您的评价");
                ID_to_conversation.get(User_ID).setFeedback(1);
            }
            else if(message.equals("#bad")){
                System.out.println(message);
                sendMessageToOneUser(User_ID, "已收到您的差评，我们会努力改进我们的服务");
                ID_to_conversation.get(User_ID).setFeedback(0);
            }

        }
        else{
            String reply="";
            if(user_request.equals("2")){
                reply="您好，有什么可以帮助您的吗";
            }
            if(user_request.equals("1")&&user_state==0)
            {
                reply="检测到您似乎准备进行批量作业，请输入相应的参数，以便我进行处理";
                user_state=1;
            }
            if(user_state==1&&user_request.equals("0")){
                infoAndText.setText(message);
                infoAndText = Regex.extractInfo(infoAndText);
                necessaryInfo = infoAndText.getNecessaryInfo();
                unnecessaryInfo = infoAndText.getUnnecessaryInfo();
                if(necessaryInfo.checkAllFilled() == "全部属性都有值"&&modify_flag==0){
                    reply = "已收到您的消息，" + necessaryInfo.checkAllFilled()+"，以下是本次作业的全部汇总，请回复没问题/有问题";
                    reply_add=necessaryInfo.getAllPropertiesAsString()+unnecessaryInfo.getAllPropertiesAsString();
                    user_state=2;
                }
                else if(modify_flag==1){
                    reply="以下是您修改后的内容，请回复没问题/有问题";
                    reply_add=necessaryInfo.getAllPropertiesAsString()+unnecessaryInfo.getAllPropertiesAsString();
                    user_state=2;
                }
                else{
                    reply = "已收到您的消息，"+ necessaryInfo.checkAllFilled()+"请继续补充";
                }
            }

            if(user_state==2&&user_request=="3"){
                reply="好的，您提交的审批已全部记录，祝您工作愉快。";
                String code = CreatSQLCode.WriteSQLCode(necessaryInfo, unnecessaryInfo);
                modify_flag=0;
                user_state=0;
            }
            if(user_state==2&&user_request=="4"){
                reply="好的，请您再次提交您想要修改的部分";
                modify_flag=1;
                user_state=1;
            }

            //存入数据库
            try {
                save_to_database(ID_to_conversation.get(User_ID));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            sendMessageToOneUser(User_ID, reply);
            if(reply_add.equals("empty")==false){
                sendMessageToOneUser(User_ID, reply_add);
                reply=reply+reply_add;
                reply_add="empty";
            }
            reply= replaceNewLinesWithSpace(reply);
            message=replaceNewLinesWithSpace(message);
            ID_to_conversation.get(User_ID).setAnswer(reply);
            ID_to_conversation.get(User_ID).setQuestion(message);
            ID_to_conversation.get(User_ID).setUpdateTime(formattedDate);
            ID_to_conversation.get(User_ID).setFeedback(1);

        }
//        writeReport(formattedDate + "服务器收到客户端消息：\r\n", "conversionLog\\123.txt");
//        writeReport(message + "\r\n", "conversionLog\\123.txt");



//        writeReport(formattedDate + "服务器回复消息：\r\n", "conversionLog\\123.txt");
//        writeReport(reply + "\r\n", "conversionLog\\123.txt");
    }

    /**
     * 推送消息到指定用户
     *
     * @param userId  用户ID
     * @param message 发送的消息
     */
    public static void sendMessageToOneUser(String userId, String message) {
        Session session = clients.get(userId);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            System.out.println("错误！！\r\n链接" + userId + "发送失败");
        }

    }

    /**
     * 群发消息
     *
     * @param message 消息内容
     */
    public void sendAll(String message) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            Session session = sessionEntry.getValue();
            try {
                synchronized (session) {
//                send(session,message,500);
                    send(session, message);
                }
                //修改：误读IllegalStateException导致的异常退出
            } catch (IllegalStateException e) {
                System.out.println("发送消息" + message + "时出现IllegalStateException");
                e.printStackTrace();
            }
        }
    }

//    public void sendAll(String message){
////        System.out.println("添加了消息："+message+"进入队列");
//        messageBuffer.add(message);
//    }

    public static void send(Session session, String message) {
        synchronized (session) {
            //session.getAsyncRemote().sendText(message);
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save_to_database(Conversation conversation) throws IOException{
        String url="http://localhost:8080/index/feedback";//调用的接口地址
        String id = conversation.getId();
        String question = conversation.getQuestion();
        String answer = conversation.getAnswer();
        int feedback= conversation.getFeedback();
        String updateTime=conversation.getUpdateTime();
        System.out.println("____________________________________________________");
        System.out.println("向数据库接口发送的json为：");
        String param="{"+"\"username\""+":"+"\""+id+"\""+","+"\"question\""+":"+"\""+question+"\""+","+"\"answer\""+":"+"\""+answer+"\""+","+"\"feedback\""+":"+feedback+"}";
        System.out.println(param);
        System.out.println("____________________________________________________");
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(param, StandardCharsets.UTF_8);
        post.setEntity(entity);

        HttpResponse response = httpClient.execute(post);

    }
    public static String replaceNewLinesWithSpace(String input) {
        // 将"\r\n"替换为空格
        String step1 = input.replaceAll("\r\n", " ");
        // 将"\n"替换为空格
        String step2 = step1.replaceAll("\n", " ");
        return step2;
    }
    //2020.9.20注释了这段代码，为了解决时间延迟的问题
//    public static void send(Session session, String message, Integer timeout) throws InterruptedException {
//        if (timeout < 0) { // timeout后放弃本次发送
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            System.out.print(simpleDateFormat.format(new Date()) + ":");
//            System.out.println("发送数据:"+message+"失败，原因是超时");
//            return;
//        }
//        if (idle.remove(session)) { // 判断session是否空闲，抢占式
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            System.out.print(simpleDateFormat.format(new Date()) + ":");
//            System.out.println("发送数据"+message+"成功");
//            busy.put(session, session.getAsyncRemote().sendText( message));
//
//        } else {
//            // 若session当前不在idle集合，则去busy集合中查看session上次是否已经发送完毕，即采用惰性判断
//            synchronized (busy) {
//
//                if (busy.containsKey(session) && busy.get(session).isDone()) {
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    System.out.print(simpleDateFormat.format(new Date()) + ":");
//                    System.out.println("发现session的上一个任务已经完成");
////                    busy.remove(session);
////                    idle.add(session);
//
//                    System.out.println("发送数据"+message+"成功");
//                    busy.put(session, session.getAsyncRemote().sendText( message));
////                    if(busy.get(session).isDone()){
////                        System.out.println("aaa");
////                    }else{
////                        System.out.println("bbb");
////                    }
//                    return;
//                }
//            }
//            // 重试
//
//            Thread.sleep(5);
//            send(session, message, timeout - 100);
//        }
//    }

}
