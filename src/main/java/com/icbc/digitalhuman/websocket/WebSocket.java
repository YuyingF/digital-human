package com.icbc.digitalhuman.websocket;

import com.icbc.digitalhuman.dto.InfoAndText;
import com.icbc.digitalhuman.entity.Conversation;
import com.icbc.digitalhuman.entity.NecessaryInfo;
import com.icbc.digitalhuman.entity.UnnecessaryInfo;
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

        sendMessageToOneUser(session.getId(), "客户端你好，我是工小妍，如果需要提交审批，请以冒号分开，例如：");
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
        String formattedDate = formatter.format(date);

        String User_ID = session.getId();

        writeReport(formattedDate + "服务器收到客户端消息：\r\n", "conversionLog\\123.txt");
        writeReport(message + "\r\n", "conversionLog\\123.txt");
        System.out.println("服务端收到客户端" + User_ID + "发来的消息: " + message + "");
        infoAndText.setText(message);

        infoAndText = Regex.extractInfo(infoAndText);
        NecessaryInfo necessaryInfo = infoAndText.getNecessaryInfo();
        UnnecessaryInfo unnecessaryInfo = infoAndText.getUnnecessaryInfo();


        String reply = "已收到您的消息，" + necessaryInfo.checkAllFilled();

        if (necessaryInfo.checkAllFilled() == "全部属性都有值") {
            String code = CreatSQLCode.WriteSQLCode(necessaryInfo, unnecessaryInfo);
        }
        Conversation conversation = new Conversation();
        conversation.setId(User_ID);
        conversation.setAnswer(reply);
        conversation.setQuestion(message);
        conversation.setUpdateTime(formattedDate);
        conversation.setFeedback(1);

//        ID_to_conversation.get(User_ID).setId(User_ID);
//        ID_to_conversation.get(User_ID).setAnswer(reply);
//        ID_to_conversation.get(User_ID).setQuestion(message);
//        ID_to_conversation.get(User_ID).setUpdateTime(formattedDate);
//        ID_to_conversation.get(User_ID).setFeedback(3);
        sendMessageToOneUser(User_ID, reply);

        //存入数据库
        try {
            save_to_database(conversation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        }
//        try {
//            conversationMapper.create(conversation);
//        } catch (Exception e) {
//            System.out.println("fuck you");
//        }

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
        String param="{"+"\"id\""+":"+"\""+id+"\""+","+"\"question\""+":"+"\""+question+"\""+","+"\"answer\""+":"+"\""+answer+"\""+","+"\"feedback\""+":"+feedback+","+"\"updateTime\""+":"+"\""+updateTime+"\""+"}";
        System.out.println(param);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(param, StandardCharsets.UTF_8);
        post.setEntity(entity);

        HttpResponse response = httpClient.execute(post);

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
