package com.icbc.digitalhuman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icbc.digitalhuman.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    void create(User user);

    User findByUsername(String username);
}
