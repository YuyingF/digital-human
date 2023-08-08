package com.icbc.digitalhuman.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class NecessaryInfo {

    // 中心项目编号
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
    // 处理存过接口（程序接口）
    public String storedProcedureInterface;
    // 接口输入参数
    public String interfaceInputParameters;
    // 接口输出参数
    public String interfaceOutputParameters;
    // 是否支持重跑
    public String isRetrySupported;
    // 是否会发生中断
    public String isInterruptPossible;
    // 预估耗时
    public String estimatedTime;
    // 申请类型
    public String applicationType;
    // 生效日期
    public String effectiveDate;
    // 交付日期
    public String deliveryDate;
    // 投产日期
    public String productionDate;

    private static final Map<String, String> PROPERTY_NAME_MAP = new HashMap<>();

    static {
        PROPERTY_NAME_MAP.put("centralProjectNumber", "中心项目编号");
        PROPERTY_NAME_MAP.put("projectName", "项目名称");
        PROPERTY_NAME_MAP.put("requirementSubItem", "需求子条目");
        PROPERTY_NAME_MAP.put("application", "应用");
        PROPERTY_NAME_MAP.put("batchCategory", "批量种类");
        PROPERTY_NAME_MAP.put("batchSession", "批量场次");
        PROPERTY_NAME_MAP.put("jobDescription", "作业描述");
        PROPERTY_NAME_MAP.put("prerequisiteJob", "前提作业");
        PROPERTY_NAME_MAP.put("executionFrequency", "执行频度");
        PROPERTY_NAME_MAP.put("executionScope", "执行范围");
        PROPERTY_NAME_MAP.put("storedProcedureInterface", "处理存过接口");
        PROPERTY_NAME_MAP.put("interfaceInputParameters", "接口输入参数");
        PROPERTY_NAME_MAP.put("interfaceOutputParameters", "接口输出参数");
        PROPERTY_NAME_MAP.put("isRetrySupported", "是否支持重跑");
        PROPERTY_NAME_MAP.put("isInterruptPossible", "是否会发生中断");
        PROPERTY_NAME_MAP.put("estimatedTime", "预估耗时");
        PROPERTY_NAME_MAP.put("applicationType", "申请类型");
        PROPERTY_NAME_MAP.put("effectiveDate", "生效日期");
        PROPERTY_NAME_MAP.put("deliveryDate", "交付日期");
        PROPERTY_NAME_MAP.put("productionDate", "投产日期");
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
                    missingFields.append(chineseName).append("，");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (missingFields.length() > 0) {
            System.out.println(missingFields.toString());
            return "缺少以下必填项: " + missingFields.toString();
        } else {
            return "全部必填项均有值";
        }
    }
}
