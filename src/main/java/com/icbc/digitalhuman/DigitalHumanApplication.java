package com.icbc.digitalhuman;

import com.icbc.digitalhuman.DTO.InfoAndText;
import com.icbc.digitalhuman.Entity.NecessaryInfo;
import com.icbc.digitalhuman.Entity.UnnecessaryInfo;
import com.icbc.digitalhuman.Utils.Regex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DigitalHumanApplication {
    public static void main(String[] args) {

        SpringApplication.run(DigitalHumanApplication.class, args);


        InfoAndText infoAndText = new InfoAndText();
        infoAndText.setText("接口输入参数:193492    \n是否支持重跑：否\n是否会发生中断：是\n 执行范围：哈哈哈哈哈\n 执行额度:D-每日    "
        );

        infoAndText = Regex.extractInfo(infoAndText);
        NecessaryInfo necessaryInfo = infoAndText.getNecessaryInfo();
        UnnecessaryInfo unnecessaryInfo = infoAndText.getUnnecessaryInfo();

        System.out.println("Necessary Info:");
        System.out.println(necessaryInfo);

        System.out.println("Unnecessary Info:");
        System.out.println(unnecessaryInfo);
        System.out.println(necessaryInfo.checkAllFilled());

        System.out.println("--------------");
        infoAndText.setText("预估耗时（分钟）:20    \n" +
                "申请类型:    String applicationType;\n" +
                "生效日期:    String effectiveDate;\n" +
                "交付日期:    String deliveryDate;\n" +
                "投产日期:    String productionDate;\n" +
                "版本:    String version;\n" +
                "中心项目标号:    String centralProjectNumber;\n" +
                "项目名称:    String projectName;\n" +
                "需求子条目:fjsofjowe\n" +
                "应用:fikjqo\n" +
                "批量种类:adkajwo\n" +
                "批量场次:sf5wfew\n" +
                "作业描述:665\n" +
                "前提作业：349u5\n" +
                "执行频度：456 \n" +
                "执行范围：123\n");

        infoAndText = Regex.extractInfo(infoAndText);
        necessaryInfo = infoAndText.getNecessaryInfo();
        unnecessaryInfo = infoAndText.getUnnecessaryInfo();

        System.out.println("Necessary Info:");
        System.out.println(necessaryInfo);

        System.out.println("Unnecessary Info:");
        System.out.println(unnecessaryInfo);

        System.out.println(necessaryInfo.checkAllFilled());

    }
}
