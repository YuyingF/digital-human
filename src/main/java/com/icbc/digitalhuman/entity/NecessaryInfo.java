package com.icbc.digitalhuman.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class NecessaryInfo {
    // 接口输入参数
    public String interfaceInputParameters;
    // 是否支持重跑
    public String isRetrySupported;
    // 是否会发生中断
    public String isInterruptPossible;
    // 预估耗时（分钟）
    public String estimatedTimeInMinutes;
    // 申请类型
    public String applicationType;
    // 生效日期
    public String effectiveDate;
    // 交付日期
    public String deliveryDate;
    // 投产日期
    public String productionDate;
    // 版本
    public String version;
    // 中心项目标号
    public String centralProjectNumber;
    // 项目名称
    public String projectName;
    // 需求子条目
    public String requirementSubItem;
    // 应用
    public String application;
    // 批量种类
    public String batchCategory;
    // 批量场次
    public String batchSession;
    // 作业描述
    public String jobDescription;
    // 前提作业
    public String prerequisiteJob;
    // 执行频度
    public String executionFrequency;
    // 执行范围
    public String executionScope;

    private static final Map<String, String> PROPERTY_NAME_MAP = new HashMap<>();

    static {
        PROPERTY_NAME_MAP.put("interfaceInputParameters", "接口输入参数");
        PROPERTY_NAME_MAP.put("isRetrySupported", "是否支持重跑");
        PROPERTY_NAME_MAP.put("isInterruptPossible", "是否会发生中断");
        PROPERTY_NAME_MAP.put("estimatedTimeInMinutes", "预估耗时（分钟）");
        PROPERTY_NAME_MAP.put("applicationType", "申请类型");
        PROPERTY_NAME_MAP.put("effectiveDate", "生效日期");
        PROPERTY_NAME_MAP.put("deliveryDate", "交付日期");
        PROPERTY_NAME_MAP.put("productionDate", "投产日期");
        PROPERTY_NAME_MAP.put("version", "版本");
        PROPERTY_NAME_MAP.put("centralProjectNumber", "中心项目标号");
        PROPERTY_NAME_MAP.put("projectName", "项目名称");
        PROPERTY_NAME_MAP.put("requirementSubItem", "需求子条目");
        PROPERTY_NAME_MAP.put("application", "应用");
        PROPERTY_NAME_MAP.put("batchCategory", "批量种类");
        PROPERTY_NAME_MAP.put("batchSession", "批量场次");
        PROPERTY_NAME_MAP.put("jobDescription", "作业描述");
        PROPERTY_NAME_MAP.put("prerequisiteJob", "前提作业");
        PROPERTY_NAME_MAP.put("executionFrequency", "执行频度");
        PROPERTY_NAME_MAP.put("executionScope", "执行范围");
    }

    public String checkAllFilled() {
        StringBuilder missingFields = new StringBuilder();

        for (Map.Entry<String, String> entry : PROPERTY_NAME_MAP.entrySet()) {
            String propertyName = entry.getKey();
            String chineseName = entry.getValue();

            try {
                // 获取属性值
                java.lang.reflect.Field field = getClass().getDeclaredField(propertyName);
                field.setAccessible(true);
                Object value = field.get(this);

                // 检查属性值是否为空
                if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                    missingFields.append(chineseName).append(", ");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (missingFields.length() > 0) {
            missingFields.deleteCharAt(missingFields.length() - 2); // 移除最后的逗号和空格
            return "缺少以下属性: " + missingFields.toString() + "。";
        } else {
            return "全部属性都有值";
        }
    }
}
