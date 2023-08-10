package com.icbc.digitalhuman.controller;

import com.icbc.digitalhuman.entity.Conversation;
import com.icbc.digitalhuman.service.ConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("index")
public class ConversationController {

    @Resource
    private ConversationService conversationService;

    @PostMapping("/feedback")
    public ResponseEntity<String> feedback(@RequestBody Conversation conversation) {
        System.out.println("用户"+conversation.getUsername()+"发来评分");
        try {
            conversationService.create(conversation);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail: " + e.getMessage());
        }
    }
}
