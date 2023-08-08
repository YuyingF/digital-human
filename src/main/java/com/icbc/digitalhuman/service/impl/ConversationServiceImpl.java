package com.icbc.digitalhuman.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icbc.digitalhuman.entity.Conversation;
import com.icbc.digitalhuman.entity.User;
import com.icbc.digitalhuman.mapper.ConversationMapper;
import com.icbc.digitalhuman.service.ConversationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation> implements ConversationService {
    @Resource
    ConversationMapper conversationMapper;

    @Override
    @Transactional
    public void create(Conversation conversation) {
        String id = String.valueOf(UUID.randomUUID());
        conversation.setId(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String updateTime = LocalDateTime.now().format(formatter);
        conversation.setUpdateTime(updateTime);
        conversation.setUsername(User.username);
        conversationMapper.create(conversation);
    }
}
