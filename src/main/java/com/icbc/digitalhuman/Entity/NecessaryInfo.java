package com.icbc.digitalhuman.Entity;

import lombok.Data;

@Data
public class NecessaryInfo {
    String id;
    // 接口输入参数
    String interfaceInputParameters;
    // 是否支持重跑
    String isRetrySupported;
    // 是否会发生中断
    String isInterruptPossible;
    // 预估耗时（分钟）
    int estimatedTimeInMinutes;
    // 申请类型
    String applicationType;
    // 生效日期
    String effectiveDate;
    // 交付日期
    String deliveryDate;
    // 投产日期
    String productionDate;
    // 版本
    String version;
    // 中心项目标号
    String centralProjectNumber;
    // 项目名称
    String projectName;
    // 需求子条目
    String requirementSubItem;
    // 应用
    String application;
    // 批量种类
    String batchCategory;
    // 批量场次
    String batchSession;
    // 作业描述
    String jobDescription;
    // 前提作业
    String prerequisiteJob;
    // 执行频度
    String executionFrequency;
    // 执行范围
    String executionScope;
}
