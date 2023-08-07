package com.icbc.digitalhuman.entity;

import lombok.Data;

@Data
public class Conversation {

    String id;
    String username;
    String question;
    String answer;
    int feedback = 1; // 1:满意 0：不满 默认满意
    String updateTime;

    //0代表什么都没读到
    //1代表用户要审批
    //2代表打招呼
    //3代表用户确认ok
    public static String judge_user_desire(String input){
        int hello_flag=0;
        int job_request_flag=0;
        int confirm_flag=0;
        if(input.contains("你好")||input.contains("您好")){
            hello_flag=1;
        }
        if(input.contains("要审批")||input.contains("要提交")){
            job_request_flag=1;
        }
        if(input.equals("是")||input.equals("没错")||input.equals("没问题"))
        {
            confirm_flag=1;
        }
        if(input.equals("有问题"))
        {
            confirm_flag=2;
        }
        if(job_request_flag==1)
        {
            return "1";
        }
        else if(confirm_flag==1){
            return "3";
        }
        else if(confirm_flag==2){
            return "4";
        }
        else if(hello_flag==1){
            return "2";
        }
        return "0";
    }
}
