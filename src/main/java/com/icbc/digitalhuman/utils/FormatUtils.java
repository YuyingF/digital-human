package com.icbc.digitalhuman.utils;

import com.icbc.digitalhuman.dto.InfoAndText;

public class FormatUtils {
    public static String convertInfoAndTextToJson(InfoAndText infoAndText) {
        String json = "{" +
                "\"版本\":\"" + infoAndText.getNecessaryInfo().version + "\"," +
                "\"中心项目编号\":\"" + infoAndText.getNecessaryInfo().centralProjectNumber + "\"," +
                "\"项目名称\":\"" + infoAndText.getNecessaryInfo().projectName + "\"," +
                "\"需求子条目\":\"" + infoAndText.getNecessaryInfo().requirementSubItem + "\"," +
                "\"应用名称\":\"" + infoAndText.getNecessaryInfo().application + "\"," +
                "\"批量种类\":\"" + infoAndText.getNecessaryInfo().batchCategory + "\"," +
                "\"批量场次\":\"" + infoAndText.getNecessaryInfo().batchSession + "\"," +

                "\"作业id\":\"" + infoAndText.getUnnecessaryInfo().jobId + "\"," +
                "\"作业名称\":\"" + infoAndText.getUnnecessaryInfo().jobName + "\"," +

                "\"作业描述\":\"" + infoAndText.getNecessaryInfo().jobDescription + "\"," +
                "\"前提作业\":\"" + infoAndText.getNecessaryInfo().prerequisiteJob + "\"," +
                "\"执行频度\":\"" + infoAndText.getNecessaryInfo().executionFrequency + "\"," +
                "\"执行范围\":\"" + infoAndText.getNecessaryInfo().executionScope + "\"," +
                "\"处理存过接口（程序接口）\":\"" + infoAndText.getNecessaryInfo().storedProcedureInterface + "\"," +
                "\"接口输入参数\":\"" + infoAndText.getNecessaryInfo().interfaceInputParameters + "\"," +
                "\"接口输出参数\":\"" + infoAndText.getNecessaryInfo().interfaceOutputParameters + "\"," +
                "\"是否支持重跑\":\"" + infoAndText.getNecessaryInfo().isRetrySupported + "\"," +
                "\"是否会发生中断\":\"" + infoAndText.getNecessaryInfo().isInterruptPossible + "\"," +

                "\"中断解决方案\":\"" + infoAndText.getUnnecessaryInfo().interruptionSolution + "\"," +

                "\"预估耗时\":\"" + infoAndText.getNecessaryInfo().estimatedTime + "\"," +
                "\"申请类型\":\"" + infoAndText.getNecessaryInfo().applicationType + "\"," +
                "\"生效日期\":\"" + infoAndText.getNecessaryInfo().effectiveDate + "\"," +
                "\"交付日期\":\"" + infoAndText.getNecessaryInfo().deliveryDate + "\"," +
                "\"投产日期\":\"" + infoAndText.getNecessaryInfo().productionDate + "\"," +
                "\"上游应用\":\"" + infoAndText.getNecessaryInfo().upstreamApplication + "\"," +

                //不必要的
                "\"执行频度补充说明\":\"" + infoAndText.getUnnecessaryInfo().executionFrequencyDescription + "\"," +
                "\"执行范围说明\":\"" + infoAndText.getUnnecessaryInfo().executionScopeDescription + "\"," +
                "\"接口输入参数补充说明\":\"" + infoAndText.getUnnecessaryInfo().inputParameterDescription + "\"," +

                "\"文件接口名\":\"" + infoAndText.getUnnecessaryInfo().fileInterfaceName + "\"," +
                "\"文件结构是否变化\":\"" + infoAndText.getUnnecessaryInfo().isFileStructureChanged + "\"," +
                "\"上游应用文本中文名\":\"" + infoAndText.getUnnecessaryInfo().upstreamApplicationChineseName + "\"," +
                "\"临时表字段是否需要处理\":\"" + infoAndText.getUnnecessaryInfo().isTemporaryTableFieldsRequired + "\"," +
                "\"是否有存量文件\":\"" + infoAndText.getUnnecessaryInfo().hasLegacyFiles + "\"," +
                "\"上游文本相关说明\":\"" + infoAndText.getUnnecessaryInfo().upstreamTextDescription + "\"," +
                "\"上游联系人\":\"" + infoAndText.getUnnecessaryInfo().upstreamContact + "\"," +
                "\"上游文件传输方式\":\"" + infoAndText.getUnnecessaryInfo().upstreamFileTransferMethod + "\"," +
                "\"上游点对点传输接收信息\":\"" + infoAndText.getUnnecessaryInfo().upstreamPointToPointTransmission + "\"," +
                "\"下游应用\":\"" + infoAndText.getUnnecessaryInfo().downstreamApplication + "\"," +
                "\"下游目标接口\":\"" + infoAndText.getUnnecessaryInfo().downstreamTargetInterface + "\"," +
                "\"下游联系人\":\"" + infoAndText.getUnnecessaryInfo().downstreamContact + "\"," +
                "\"下游文件传输方式\":\"" + infoAndText.getUnnecessaryInfo().downstreamFileTransferMethod + "\"," +
                "\"下游点对点传输接收信息\":\"" + infoAndText.getUnnecessaryInfo().downstreamPointToPointTransmission + "\"," +
                "\"开发组补充说明\":\"" + infoAndText.getUnnecessaryInfo().developmentTeamDescription + "\"," +
                "\"版本库\":\"" + infoAndText.getUnnecessaryInfo().versionLibrary + "\"," +
                "\"程序清单\":\"" + infoAndText.getUnnecessaryInfo().programList + "\"," +
                "\"文件所属应用类型\":\"" + infoAndText.getUnnecessaryInfo().fileApplicationType + "\"," +
                "\"分布式作业名称\":\"" + infoAndText.getUnnecessaryInfo().distributedJobName + "\"," +
                "\"分布式作业执行时间\":\"" + infoAndText.getUnnecessaryInfo().distributedJobExecutionTime + "\"" +
                "}";
        return json;
    }
}

//        stringBuilder.append("当前批量审批表信息汇报如下：").append(System.lineSeparator());
//        stringBuilder.append("版本：").append(necessaryInfo.getVersion()).append("；");
//        stringBuilder.append("中心项目编号：").append(necessaryInfo.getCentralProjectNumber()).append("；");
//        stringBuilder.append("项目名称：").append(necessaryInfo.getProjectName()).append("；");
//        stringBuilder.append("需求子条目：").append(necessaryInfo.getRequirementSubItem()).append("；");
//        stringBuilder.append("应用：").append(necessaryInfo.getApplication()).append("；");
//        stringBuilder.append("批量种类：").append(necessaryInfo.getBatchCategory()).append("；");
//        stringBuilder.append("批量场次：").append(necessaryInfo.getBatchSession()).append("；");
//        stringBuilder.append("作业id：").append(unnecessaryInfo.getJobId()).append("；");
//        stringBuilder.append("作业名称：").append(unnecessaryInfo.getJobName()).append("；");
//        stringBuilder.append("作业描述：").append(necessaryInfo.getJobDescription()).append("；");
//        stringBuilder.append("前提作业：").append(necessaryInfo.getPrerequisiteJob()).append("；");
//        stringBuilder.append("执行频度：").append(necessaryInfo.getExecutionFrequency()).append("；");
//        stringBuilder.append("执行频度补充说明：").append(unnecessaryInfo.getExecutionFrequencyDescription()).append("；");
//        stringBuilder.append("执行范围：").append(necessaryInfo.getExecutionScope()).append("；");
//        stringBuilder.append("执行范围说明：").append(unnecessaryInfo.getExecutionScopeDescription()).append("；");
//        stringBuilder.append("处理存过接口（程序接口）：").append(necessaryInfo.getStoredProcedureInterface()).append("；");
//        stringBuilder.append("接口输入参数：").append(necessaryInfo.getInterfaceInputParameters()).append("；");
//        stringBuilder.append("接口输入参数补充说明（请按参数顺序填写）：").append(unnecessaryInfo.getInputParameterDescription()).append("；");
//        stringBuilder.append("接口输出参数：").append(necessaryInfo.getInterfaceOutputParameters()).append("；");
//        stringBuilder.append("是否支持重跑：").append(necessaryInfo.getIsRetrySupported()).append("；");
//        stringBuilder.append("是否会发生中断：").append(necessaryInfo.getIsInterruptPossible()).append("；");
//        stringBuilder.append("中断解决方案：").append(unnecessaryInfo.getInterruptionSolution()).append("；");
//        stringBuilder.append("预估耗时：").append(necessaryInfo.getEstimatedTime()).append("；");
//        stringBuilder.append("申请类型：").append(necessaryInfo.getApplicationType()).append("；");
//        stringBuilder.append("生效日期：").append(necessaryInfo.getEffectiveDate()).append("；");
//        stringBuilder.append("交付日期：").append(necessaryInfo.getDeliveryDate()).append("；");
//        stringBuilder.append("投产日期：").append(necessaryInfo.getProductionDate()).append("；");
//        stringBuilder.append("上游应用：").append(unnecessaryInfo.getUpstreamApplicationChineseName()).append("；");
//        stringBuilder.append("文件接口名：").append(unnecessaryInfo.getFileInterfaceName()).append("；");
//        stringBuilder.append("文件结构是否变化：").append(unnecessaryInfo.getIsFileStructureChanged()).append("；");
//        stringBuilder.append("上游应用文本中文名：").append(unnecessaryInfo.getUpstreamApplicationChineseName()).append("；");
//        stringBuilder.append("临时表字段是否需要处理：").append(unnecessaryInfo.getIsTemporaryTableFieldsRequired()).append("；");
//        stringBuilder.append("是否有存量文件：").append(unnecessaryInfo.getHasLegacyFiles()).append("；");
//        stringBuilder.append("上游文本相关说明：").append(unnecessaryInfo.getUpstreamTextDescription()).append("；");
//        stringBuilder.append("上游联系人：").append(unnecessaryInfo.getUpstreamContact()).append("；");
//        stringBuilder.append("上游文件传输方式：").append(unnecessaryInfo.getUpstreamFileTransferMethod()).append("；");
//        stringBuilder.append("上游点对点传输接收信息：").append(unnecessaryInfo.getUpstreamPointToPointTransmission()).append("；");
//        stringBuilder.append("下游应用：").append(unnecessaryInfo.getDownstreamApplication()).append("；");
//        stringBuilder.append("下游目标接口：").append(unnecessaryInfo.getDownstreamTargetInterface()).append("；");
//        stringBuilder.append("下游联系人：").append(unnecessaryInfo.getDownstreamContact()).append("；");
//        stringBuilder.append("下游文件传输方式：").append(unnecessaryInfo.getDownstreamFileTransferMethod()).append("；");
//        stringBuilder.append("下游点对点传输接收信息：").append(unnecessaryInfo.getDownstreamPointToPointTransmission()).append("；");
//        stringBuilder.append("开发组补充说明：").append(unnecessaryInfo.getDevelopmentTeamDescription()).append("；");
//        stringBuilder.append("版本库：").append(unnecessaryInfo.getVersionLibrary()).append("；");
//        stringBuilder.append("程序清单：").append(unnecessaryInfo.getProgramList()).append("；");
//        stringBuilder.append("文件所属应用类型：").append(unnecessaryInfo.getFileApplicationType()).append("；");
//        stringBuilder.append("分布式作业名称：").append(unnecessaryInfo.getDistributedJobName()).append("；");
//        stringBuilder.append("分布式作业执行时间：").append(unnecessaryInfo.getDistributedJobExecutionTime()).append("；");

