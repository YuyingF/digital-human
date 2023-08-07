package com.icbc.digitalhuman.entity;

import lombok.Data;

@Data
public class Conversation {

    String id;
    int evaluation = 10;
    String feedback;
    String updateTime;
    String username;
}
