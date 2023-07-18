package com.icbc.digitalhuman;

import com.icbc.digitalhuman.Entity.NecessaryInfo;
import com.icbc.digitalhuman.Entity.UnnecessaryInfo;
import com.icbc.digitalhuman.Utils.Regex;
import javafx.util.Pair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DigitalHumanApplication {
    public static void main(String[] args) {

        SpringApplication.run(DigitalHumanApplication.class, args);





//        String text = "接口输入参数:193492    \n是否支持重跑：否\n是否会发生中断：是\n 执行范围：哈哈哈哈哈\n 执行额度:D-每日    ";
//        Pair<NecessaryInfo, UnnecessaryInfo> result = Regex.extractInfo(text);
//
//        NecessaryInfo necessaryInfo = result.getKey();
//        UnnecessaryInfo unnecessaryInfo = result.getValue();
//
//        System.out.println("Necessary Info:");
//        System.out.println(necessaryInfo);
//
//        System.out.println("Unnecessary Info:");
//        System.out.println(unnecessaryInfo);
    }
}
