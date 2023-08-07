package com.icbc.digitalhuman.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icbc.digitalhuman.entity.Conversation;

public interface ConversationService extends IService<Conversation> {

    void create(Conversation conversation);
}
