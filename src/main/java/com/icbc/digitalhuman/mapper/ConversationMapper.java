package com.icbc.digitalhuman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icbc.digitalhuman.entity.Conversation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConversationMapper extends BaseMapper<Conversation> {

    void create(Conversation conversation);
}
