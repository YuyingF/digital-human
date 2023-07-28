package com.icbc.digitalhuman.controller;

import com.icbc.digitalhuman.entity.Conversation;
import com.icbc.digitalhuman.service.ConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("index")
public class ConversationController {

    @Resource
    ConversationService conversationService;

    @PostMapping("/feedback")
    public ResponseEntity<String> feedback(@RequestBody Conversation conversation) {
        System.out.println("12345");
        try {
            conversationService.create(conversation);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail: " + e.getMessage());
        }
    }
}
