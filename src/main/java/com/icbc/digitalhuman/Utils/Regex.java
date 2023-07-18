package com.icbc.digitalhuman.Utils;

import com.icbc.digitalhuman.Entity.NecessaryInfo;
import com.icbc.digitalhuman.Entity.UnnecessaryInfo;
import javafx.util.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static Pair<NecessaryInfo, UnnecessaryInfo> extractInfo(String inputText) {
        // 定义属性和正则表达式的映射
        String[][] propertyRegex = {
                {"interfaceInputParameters", "接口输入参数[:：](.*?)(?:\\n|$)"},
                {"isRetrySupported", "是否支持重跑[:：](.*?)(?:\\n|$)"},
                {"isInterruptPossible", "是否会发生中断[:：](.*?)(?:\\n|$)"},
                {"estimatedTimeInMinutes", "预估耗时（分钟）[:：](.*?)(?:\\n|$)"},
                {"applicationType", "申请类型[:：](.*?)(?:\\n|$)"},
                {"effectiveDate", "生效日期[:：](.*?)(?:\\n|$)"},
                {"deliveryDate", "交付日期[:：](.*?)(?:\\n|$)"},
                {"productionDate", "投产日期[:：](.*?)(?:\\n|$)"},
                {"version", "版本[:：](.*?)(?:\\n|$)"},
                {"centralProjectNumber", "中心项目标号[:：](.*?)(?:\\n|$)"},
                {"projectName", "项目名称[:：](.*?)(?:\\n|$)"},
                {"requirementSubItem", "需求子条目[:：](.*?)(?:\\n|$)"},
                {"application", "应用[:：](.*?)(?:\\n|$)"},
                {"batchCategory", "批量种类[:：](.*?)(?:\\n|$)"},
                {"batchSession", "批量场次[:：](.*?)(?:\\n|$)"},
                {"jobDescription", "作业描述[:：](.*?)(?:\\n|$)"},
                {"prerequisiteJob", "前提作业[:：](.*?)(?:\\n|$)"},
                {"executionFrequency", "执行频度[:：](.*?)(?:\\n|$)"},
                {"executionScope", "执行范围[:：](.*?)(?:\\n|$)"},
                {"storedProcedureInterface", "处理存过接口[:：](.*?)(?:\\n|$)"},
                {"inputParameterDescription", "接口输入参数补充说明[:：](.*?)(?:\\n|$)"},
                {"outputParameters", "接口输出参数[:：](.*?)(?:\\n|$)"},
                {"interruptionSolution", "中断解决方案[:：](.*?)(?:\\n|$)"},
                {"upstreamApplication", "上游应用[:：](.*?)(?:\\n|$)"},
                {"fileInterfaceName", "文件接口名[:：](.*?)(?:\\n|$)"},
                {"isFileStructureChanged", "文件结构是否变化[:：](.*?)(?:\\n|$)"},
                {"jobName", "作业名称[:：](.*?)(?:\\n|$)"},
                {"executionFrequencyDescription", "执行频度补充说明[:：](.*?)(?:\\n|$)"},
                {"upstreamApplicationChineseName", "上游应用文本中文名[:：](.*?)(?:\\n|$)"},
                {"isTemporaryTableFieldsRequired", "临时表字段是否需要处[:：](.*?)(?:\\n|$)"},
                {"hasLegacyFiles", "是否有存量文件[:：](.*?)(?:\\n|$)"},
                {"upstreamTextDescription", "上游文本相关说明[:：](.*?)(?:\\n|$)"},
                {"upstreamContact", "上游联系人[:：](.*?)(?:\\n|$)"},
                {"upstreamFileTransferMethod", "上游文件传输方式[:：](.*?)(?:\\n|$)"},
                {"upstreamPointToPointTransmission", "上游点对点传输接收信[:：](.*?)(?:\\n|$)"},
                {"downstreamApplication", "下游应用[:：](.*?)(?:\\n|$)"},
                {"downstreamTargetInterface", "下游目标接口[:：](.*?)(?:\\n|$)"},
                {"downstreamContact", "下游联系人[:：](.*?)(?:\\n|$)"},
                {"downstreamPointToPointTransmission", "下游点对点传输接收信[:：](.*?)(?:\\n|$)"},
                {"downstreamFileTransferMethod", "下游文件传输方式[:：](.*?)(?:\\n|$)"},
                {"developmentTeamDescription", "开发组补充说明[:：](.*?)(?:\\n|$)"},
                {"applicant", "申请人[:：](.*?)(?:\\n|$)"},
                {"applicationTime", "申请时间[:：](.*?)(?:\\n|$)"},
                {"executionScopeDescription", "执行范围说明[:：](.*?)(?:\\n|$)"},
        };


        NecessaryInfo necessaryInfo = new NecessaryInfo();
        UnnecessaryInfo unnecessaryInfo = new UnnecessaryInfo();

        // 提取属性值
        for (String[] mapping : propertyRegex) {
            String propertyName = mapping[0];
            String regex = mapping[1];

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(inputText);

            if (matcher.find()) {
                String value = matcher.group(1).trim();
                switch (propertyName) {
                    case "interfaceInputParameters":
                        necessaryInfo.setInterfaceInputParameters(value);
                        break;
                    case "isRetrySupported":
                        necessaryInfo.setIsRetrySupported(value);
                        break;
                    case "isInterruptPossible":
                        necessaryInfo.setIsInterruptPossible(value);
                        break;
                    case "estimatedTimeInMinutes":
                        necessaryInfo.setEstimatedTimeInMinutes(Integer.parseInt(value));
                        break;
                    case "applicationType":
                        necessaryInfo.setApplicationType(value);
                        break;
                    case "effectiveDate":
                        necessaryInfo.setEffectiveDate(value);
                        break;
                    case "deliveryDate":
                        necessaryInfo.setDeliveryDate(value);
                        break;
                    case "productionDate":
                        necessaryInfo.setProductionDate(value);
                        break;
                    case "version":
                        necessaryInfo.setVersion(value);
                        break;
                    case "centralProjectNumber":
                        necessaryInfo.setCentralProjectNumber(value);
                        break;
                    case "projectName":
                        necessaryInfo.setProjectName(value);
                        break;
                    case "requirementSubItem":
                        necessaryInfo.setRequirementSubItem(value);
                        break;
                    case "application":
                        necessaryInfo.setApplication(value);
                        break;
                    case "batchCategory":
                        necessaryInfo.setBatchCategory(value);
                        break;
                    case "batchSession":
                        necessaryInfo.setBatchSession(value);
                        break;
                    case "jobDescription":
                        necessaryInfo.setJobDescription(value);
                        break;
                    case "prerequisiteJob":
                        necessaryInfo.setPrerequisiteJob(value);
                        break;
                    case "executionFrequency":
                        necessaryInfo.setExecutionFrequency(value);
                        break;
                    case "executionScope":
                        necessaryInfo.setExecutionScope(value);
                        break;
                    case "storedProcedureInterface":
                        unnecessaryInfo.setStoredProcedureInterface(value);
                        break;
                    case "inputParameterDescription":
                        unnecessaryInfo.setInputParameterDescription(value);
                        break;
                    case "outputParameters":
                        unnecessaryInfo.setOutputParameters(value);
                        break;
                    case "interruptionSolution":
                        unnecessaryInfo.setInterruptionSolution(value);
                        break;
                    case "upstreamApplication":
                        unnecessaryInfo.setUpstreamApplication(value);
                        break;
                    case "fileInterfaceName":
                        unnecessaryInfo.setFileInterfaceName(value);
                        break;
                    case "isFileStructureChanged":
                        unnecessaryInfo.setIsFileStructureChanged(value);
                        break;
                    case "jobName":
                        unnecessaryInfo.setJobName(value);
                        break;
                    case "executionFrequencyDescription":
                        unnecessaryInfo.setExecutionFrequencyDescription(value);
                        break;
                    case "upstreamApplicationChineseName":
                        unnecessaryInfo.setUpstreamApplicationChineseName(value);
                        break;
                    case "isTemporaryTableFieldsRequired":
                        unnecessaryInfo.setIsTemporaryTableFieldsRequired(value);
                        break;
                    case "hasLegacyFiles":
                        unnecessaryInfo.setHasLegacyFiles(value);
                        break;
                    case "upstreamTextDescription":
                        unnecessaryInfo.setUpstreamTextDescription(value);
                        break;
                    case "upstreamContact":
                        unnecessaryInfo.setUpstreamContact(value);
                        break;
                    case "upstreamFileTransferMethod":
                        unnecessaryInfo.setUpstreamFileTransferMethod(value);
                        break;
                    case "upstreamPointToPointTransmission":
                        unnecessaryInfo.setUpstreamPointToPointTransmission(value);
                        break;
                    case "downstreamApplication":
                        unnecessaryInfo.setDownstreamApplication(value);
                        break;
                    case "downstreamTargetInterface":
                        unnecessaryInfo.setDownstreamTargetInterface(value);
                        break;
                    case "downstreamContact":
                        unnecessaryInfo.setDownstreamContact(value);
                        break;
                    case "downstreamPointToPointTransmission":
                        unnecessaryInfo.setDownstreamPointToPointTransmission(value);
                        break;
                    case "downstreamFileTransferMethod":
                        unnecessaryInfo.setDownstreamFileTransferMethod(value);
                        break;
                    case "developmentTeamDescription":
                        unnecessaryInfo.setDevelopmentTeamDescription(value);
                        break;
                    case "applicant":
                        unnecessaryInfo.setApplicant(value);
                        break;
                    case "applicationTime":
                        unnecessaryInfo.setApplicationTime(value);
                        break;
                    case "executionScopeDescription":
                        unnecessaryInfo.setExecutionScopeDescription(value);
                        break;
                }
            }
        }
        return new Pair<>(necessaryInfo, unnecessaryInfo);
    }
}
