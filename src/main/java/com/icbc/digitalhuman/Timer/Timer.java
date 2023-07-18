package com.icbc.digitalhuman.Timer;

import com.icbc.digitalhuman.Websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Timer {

    @Autowired
    private WebSocket WebTimer;

    @Scheduled(fixedDelay = 2000)
    private void Delay_2s()
    {
        //WebTimer.sendAll("data");
    }

}
