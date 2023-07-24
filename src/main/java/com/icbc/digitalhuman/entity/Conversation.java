package com.icbc.digitalhuman.entity;

import lombok.Data;

@Data
public class Conversation {
    String id;
    String question;
    String answer;
    int feedback = 1; // 1:满意 0：不满 默认满意
}
