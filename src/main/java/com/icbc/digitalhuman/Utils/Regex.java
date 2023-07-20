package com.icbc.digitalhuman.Utils;

import com.icbc.digitalhuman.DTO.InfoAndText;
import com.icbc.digitalhuman.Entity.NecessaryInfo;
import com.icbc.digitalhuman.Entity.UnnecessaryInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    private static final Map<String, String> PROPERTY_REGEX_MAP = new HashMap<>();

    static {
        // 定义属性和正则表达式的映射
        PROPERTY_REGEX_MAP.put("interfaceInputParameters", "接口输入参数[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("isRetrySupported", "是否支持重跑[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("isInterruptPossible", "是否会发生中断[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("estimatedTimeInMinutes", "预估耗时（分钟）[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("applicationType", "申请类型[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("effectiveDate", "生效日期[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("deliveryDate", "交付日期[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("productionDate", "投产日期[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("version", "版本[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("centralProjectNumber", "中心项目标号[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("projectName", "项目名称[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("requirementSubItem", "需求子条目[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("application", "应用[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("batchCategory", "批量种类[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("batchSession", "批量场次[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("jobDescription", "作业描述[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("prerequisiteJob", "前提作业[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("executionFrequency", "执行频度[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("executionScope", "执行范围[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("storedProcedureInterface", "处理存过接口[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("inputParameterDescription", "接口输入参数补充说明[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("outputParameters", "接口输出参数[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("interruptionSolution", "中断解决方案[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("upstreamApplication", "上游应用[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("fileInterfaceName", "文件接口名[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("isFileStructureChanged", "文件结构是否变化[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("jobName", "作业名称[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("executionFrequencyDescription", "执行频度补充说明[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("upstreamApplicationChineseName", "上游应用文本中文名[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("isTemporaryTableFieldsRequired", "临时表字段是否需要处[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("hasLegacyFiles", "是否有存量文件[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("upstreamTextDescription", "上游文本相关说明[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("upstreamContact", "上游联系人[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("upstreamFileTransferMethod", "上游文件传输方式[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("upstreamPointToPointTransmission", "上游点对点传输接收信[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("downstreamApplication", "下游应用[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("downstreamTargetInterface", "下游目标接口[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("downstreamContact", "下游联系人[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("downstreamPointToPointTransmission", "下游点对点传输接收信[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("downstreamFileTransferMethod", "下游文件传输方式[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("developmentTeamDescription", "开发组补充说明[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("applicant", "申请人[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("applicationTime", "申请时间[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("executionScopeDescription", "执行范围说明[:：](.*?)(?:\\n|$)");
    }

    public static InfoAndText extractInfo(InfoAndText infoAndText) {
        NecessaryInfo necessaryInfo = infoAndText.getNecessaryInfo();
        UnnecessaryInfo unnecessaryInfo = infoAndText.getUnnecessaryInfo();
        String text = infoAndText.getText();

        if (necessaryInfo == null) {
            necessaryInfo = new NecessaryInfo();
            infoAndText.setNecessaryInfo(necessaryInfo);
        }

        if (unnecessaryInfo == null) {
            unnecessaryInfo = new UnnecessaryInfo();
            infoAndText.setUnnecessaryInfo(unnecessaryInfo);
        }

        // 提取属性值
        NecessaryInfo finalNecessaryInfo = necessaryInfo;
        UnnecessaryInfo finalUnnecessaryInfo = unnecessaryInfo;
        PROPERTY_REGEX_MAP.forEach((propertyName, regex) -> {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                String value = matcher.group(1).trim();
                switch (propertyName) {
                    case "interfaceInputParameters":
                        finalNecessaryInfo.setInterfaceInputParameters(value);
                        break;
                    case "isRetrySupported":
                        finalNecessaryInfo.setIsRetrySupported(value);
                        break;
                    case "isInterruptPossible":
                        finalNecessaryInfo.setIsInterruptPossible(value);
                        break;
                    case "estimatedTimeInMinutes":
                        finalNecessaryInfo.setEstimatedTimeInMinutes(value);
                        break;
                    case "applicationType":
                        finalNecessaryInfo.setApplicationType(value);
                        break;
                    case "effectiveDate":
                        finalNecessaryInfo.setEffectiveDate(value);
                        break;
                    case "deliveryDate":
                        finalNecessaryInfo.setDeliveryDate(value);
                        break;
                    case "productionDate":
                        finalNecessaryInfo.setProductionDate(value);
                        break;
                    case "version":
                        finalNecessaryInfo.setVersion(value);
                        break;
                    case "centralProjectNumber":
                        finalNecessaryInfo.setCentralProjectNumber(value);
                        break;
                    case "projectName":
                        finalNecessaryInfo.setProjectName(value);
                        break;
                    case "requirementSubItem":
                        finalNecessaryInfo.setRequirementSubItem(value);
                        break;
                    case "application":
                        finalNecessaryInfo.setApplication(value);
                        break;
                    case "batchCategory":
                        finalNecessaryInfo.setBatchCategory(value);
                        break;
                    case "batchSession":
                        finalNecessaryInfo.setBatchSession(value);
                        break;
                    case "jobDescription":
                        finalNecessaryInfo.setJobDescription(value);
                        break;
                    case "prerequisiteJob":
                        finalNecessaryInfo.setPrerequisiteJob(value);
                        break;
                    case "executionFrequency":
                        finalNecessaryInfo.setExecutionFrequency(value);
                        break;
                    case "executionScope":
                        finalNecessaryInfo.setExecutionScope(value);
                        break;
                    case "storedProcedureInterface":
                        finalUnnecessaryInfo.setStoredProcedureInterface(value);
                        break;
                    case "inputParameterDescription":
                        finalUnnecessaryInfo.setInputParameterDescription(value);
                        break;
                    case "outputParameters":
                        finalUnnecessaryInfo.setOutputParameters(value);
                        break;
                    case "interruptionSolution":
                        finalUnnecessaryInfo.setInterruptionSolution(value);
                        break;
                    case "upstreamApplication":
                        finalUnnecessaryInfo.setUpstreamApplication(value);
                        break;
                    case "fileInterfaceName":
                        finalUnnecessaryInfo.setFileInterfaceName(value);
                        break;
                    case "isFileStructureChanged":
                        finalUnnecessaryInfo.setIsFileStructureChanged(value);
                        break;
                    case "jobName":
                        finalUnnecessaryInfo.setJobName(value);
                        break;
                    case "executionFrequencyDescription":
                        finalUnnecessaryInfo.setExecutionFrequencyDescription(value);
                        break;
                    case "upstreamApplicationChineseName":
                        finalUnnecessaryInfo.setUpstreamApplicationChineseName(value);
                        break;
                    case "isTemporaryTableFieldsRequired":
                        finalUnnecessaryInfo.setIsTemporaryTableFieldsRequired(value);
                        break;
                    case "hasLegacyFiles":
                        finalUnnecessaryInfo.setHasLegacyFiles(value);
                        break;
                    case "upstreamTextDescription":
                        finalUnnecessaryInfo.setUpstreamTextDescription(value);
                        break;
                    case "upstreamContact":
                        finalUnnecessaryInfo.setUpstreamContact(value);
                        break;
                    case "upstreamFileTransferMethod":
                        finalUnnecessaryInfo.setUpstreamFileTransferMethod(value);
                        break;
                    case "upstreamPointToPointTransmission":
                        finalUnnecessaryInfo.setUpstreamPointToPointTransmission(value);
                        break;
                    case "downstreamApplication":
                        finalUnnecessaryInfo.setDownstreamApplication(value);
                        break;
                    case "downstreamTargetInterface":
                        finalUnnecessaryInfo.setDownstreamTargetInterface(value);
                        break;
                    case "downstreamContact":
                        finalUnnecessaryInfo.setDownstreamContact(value);
                        break;
                    case "downstreamPointToPointTransmission":
                        finalUnnecessaryInfo.setDownstreamPointToPointTransmission(value);
                        break;
                    case "downstreamFileTransferMethod":
                        finalUnnecessaryInfo.setDownstreamFileTransferMethod(value);
                        break;
                    case "developmentTeamDescription":
                        finalUnnecessaryInfo.setDevelopmentTeamDescription(value);
                        break;
                    case "applicant":
                        finalUnnecessaryInfo.setApplicant(value);
                        break;
                    case "applicationTime":
                        finalUnnecessaryInfo.setApplicationTime(value);
                        break;
                    case "executionScopeDescription":
                        finalUnnecessaryInfo.setExecutionScopeDescription(value);
                        break;
                }
            }
        });
        return infoAndText;
    }
}
