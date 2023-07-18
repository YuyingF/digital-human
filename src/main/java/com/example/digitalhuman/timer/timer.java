package com.example.digitalhuman.timer;

import com.example.digitalhuman.Websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class timer {

    @Autowired
    private WebSocket WebTimer;

    @Scheduled(fixedDelay = 2000)
    private void Delay_2s()
    {
        //WebTimer.sendAll("data");
    }

}
