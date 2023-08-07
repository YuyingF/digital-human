package com.icbc.digitalhuman.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icbc.digitalhuman.entity.User;
import com.icbc.digitalhuman.mapper.UserMapper;
import com.icbc.digitalhuman.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    @Transactional
    public void create(User user) {
        String id = String.valueOf(UUID.randomUUID());
        user.setId(id);
        userMapper.create(user);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
