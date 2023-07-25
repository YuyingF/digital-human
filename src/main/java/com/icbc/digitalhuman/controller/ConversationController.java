package com.icbc.digitalhuman.controller;

import com.icbc.digitalhuman.entity.Conversation;
import com.icbc.digitalhuman.mapper.ConversationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("index")
public class ConversationController {

    @Resource
    ConversationMapper conversationMapper;

    @PostMapping("/feedback")
    public ResponseEntity<String> feedback(@RequestBody Conversation conversation) {
        try {
            conversationMapper.create(conversation);
            return ResponseEntity.ok("反馈成功!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("反馈失败: " + e.getMessage());
        }
    }
}
