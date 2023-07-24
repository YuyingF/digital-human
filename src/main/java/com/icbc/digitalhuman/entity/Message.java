package com.icbc.digitalhuman.entity;

import lombok.Data;

@Data
public class Message {

    private Integer code = 1;
    private String msg = "成功";
    private Object object;
}
