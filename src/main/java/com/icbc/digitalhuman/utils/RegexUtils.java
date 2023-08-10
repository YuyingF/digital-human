package com.icbc.digitalhuman.utils;

import com.icbc.digitalhuman.dto.InfoAndText;
import com.icbc.digitalhuman.entity.NecessaryInfo;
import com.icbc.digitalhuman.entity.UnnecessaryInfo;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    private static final Map<String, String> PROPERTY_REGEX_MAP = new HashMap<>();

    static {
        // NecessaryInfo
        PROPERTY_REGEX_MAP.put("version", "版本[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("centralProjectNumber", "中心项目编号[:：](.*?)(?:\\n|$)");
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
        PROPERTY_REGEX_MAP.put("interfaceInputParameters", "接口输入参数[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("interfaceOutputParameters", "接口输出参数[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("isRetrySupported", "是否支持重跑[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("isInterruptPossible", "是否会发生中断[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("estimatedTime", "预估耗时[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("applicationType", "申请类型[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("effectiveDate", "生效日期[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("deliveryDate", "交付日期[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("productionDate", "投产日期[:：](.*?)(?:\\n|$)");

        // UnnecessaryInfo
        PROPERTY_REGEX_MAP.put("jobId", "作业id[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("jobName", "作业名称[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("executionFrequencyDescription", "执行频度补充说明[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("executionScopeDescription", "执行范围说明[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("inputParameterDescription", "接口输入参数补充说明[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("interruptionSolution", "中断解决方案[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("upstreamApplication", "上游应用[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("fileInterfaceName", "文件接口名[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("isFileStructureChanged", "文件结构是否变化[:：](.*?)(?:\\n|$)");
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
        PROPERTY_REGEX_MAP.put("downstreamFileTransferMethod", "下游文件传输方式[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("downstreamPointToPointTransmission", "下游点对点传输接收信[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("developmentTeamDescription", "开发组补充说明[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("versionLibrary", "版本库[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("programList", "程序清单[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("fileApplicationType", "文件所属应用类型[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("distributedJobName", "分布式作业名称[:：](.*?)(?:\\n|$)");
        PROPERTY_REGEX_MAP.put("distributedJobExecutionTime", "分布式作业执行时间[:：](.*?)(?:\\n|$)");
    }

    public static InfoAndText extractInfo(InfoAndText infoAndText) {
        NecessaryInfo necessaryInfo = infoAndText.getNecessaryInfo();
        UnnecessaryInfo unnecessaryInfo = infoAndText.getUnnecessaryInfo();
        String text = infoAndText.getText();

        PROPERTY_REGEX_MAP.forEach((propertyName, regex) -> {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                String value = matcher.group(1).trim();
                switch (propertyName) {
                    // NecessaryInfo properties
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
                        necessaryInfo.setStoredProcedureInterface(value);
                        break;
                    case "interfaceInputParameters":
                        necessaryInfo.setInterfaceInputParameters(value);
                        break;
                    case "interfaceOutputParameters":
                        necessaryInfo.setInterfaceOutputParameters(value);
                        break;
                    case "isRetrySupported":
                        necessaryInfo.setIsRetrySupported(value);
                        break;
                    case "isInterruptPossible":
                        necessaryInfo.setIsInterruptPossible(value);
                        break;
                    case "estimatedTime":
                        necessaryInfo.setEstimatedTime(value);
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
                    case "upstreamApplication":
                        necessaryInfo.setUpstreamApplication(value);
                        break;

                    // UnnecessaryInfo properties
                    case "jobId":
                        unnecessaryInfo.setJobId(value);
                        break;
                    case "jobName":
                        unnecessaryInfo.setJobName(value);
                        break;
                    case "executionFrequencyDescription":
                        unnecessaryInfo.setExecutionFrequencyDescription(value);
                        break;
                    case "executionScopeDescription":
                        unnecessaryInfo.setExecutionScopeDescription(value);
                        break;
                    case "inputParameterDescription":
                        unnecessaryInfo.setInputParameterDescription(value);
                        break;
                    case "interruptionSolution":
                        unnecessaryInfo.setInterruptionSolution(value);
                        break;
                    case "fileInterfaceName":
                        unnecessaryInfo.setFileInterfaceName(value);
                        break;
                    case "isFileStructureChanged":
                        unnecessaryInfo.setIsFileStructureChanged(value);
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
                    case "downstreamFileTransferMethod":
                        unnecessaryInfo.setDownstreamFileTransferMethod(value);
                        break;
                    case "downstreamPointToPointTransmission":
                        unnecessaryInfo.setDownstreamPointToPointTransmission(value);
                        break;
                    case "developmentTeamDescription":
                        unnecessaryInfo.setDevelopmentTeamDescription(value);
                        break;
                    case "versionLibrary":
                        unnecessaryInfo.setVersionLibrary(value);
                        break;
                    case "programList":
                        unnecessaryInfo.setProgramList(value);
                        break;
                    case "fileApplicationType":
                        unnecessaryInfo.setFileApplicationType(value);
                        break;
                    case "distributedJobName":
                        unnecessaryInfo.setDistributedJobName(value);
                        break;
                    case "distributedJobExecutionTime":
                        unnecessaryInfo.setDistributedJobExecutionTime(value);
                        break;

                    // Ignore unknown properties
                    default:
                        break;
                }
            }
        });
        return infoAndText;
    }

    public static int messageJudgement(String message) {
        // 1 投产日期
        // 2 确认提交
        // 3 继续修改
        if (isDate(message)) {
            return 1;
        } else if (message.equals("否")) {
            return 2;
        } else if (message.equals("是")) {
            return 3;
        }
        return 0;
    }

    public static boolean isDate(String input) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            sdf.setLenient(false);
            sdf.parse(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
