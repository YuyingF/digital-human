package com.icbc.digitalhuman.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icbc.digitalhuman.entity.User;

public interface UserService extends IService<User> {

    void create(User user);

    User findByUsername(String username);
}
