package com.icbc.digitalhuman.Entity;

import lombok.Data;

@Data
public class UnnecessaryInfo {
    String id;
    // 处理存过接口
    String storedProcedureInterface;
    // 接口输入参数补充说明
    String inputParameterDescription;
    // 接口输出参数
    String outputParameters;
    // 中断解决方案
    String interruptionSolution;
    // 上游应用
    String upstreamApplication;
    // 文件接口名
    String fileInterfaceName;
    // 文件结构是否变化
    String isFileStructureChanged;
    // 作业名称
    String jobName;
    // 执行频度补充说明
    String executionFrequencyDescription;
    // 上游应用文本中文名
    String upstreamApplicationChineseName;
    // 临时表字段是否需要处
    String isTemporaryTableFieldsRequired;
    // 是否有存量文件
    String hasLegacyFiles;
    // 上游文本相关说明
    String upstreamTextDescription;
    // 上游联系人
    String upstreamContact;
    // 上游文件传输方式
    String upstreamFileTransferMethod;
    // 上游点对点传输接收信
    String upstreamPointToPointTransmission;
    // 下游应用
    String downstreamApplication;
    // 下游目标接口
    String downstreamTargetInterface;
    // 下游联系人
    String downstreamContact;
    // 下游点对点传输接收信
    String downstreamPointToPointTransmission;
    // 下游文件传输方式
    String downstreamFileTransferMethod;
    // 开发组补充说明
    String developmentTeamDescription;
    // 申请人
    String applicant;
    // 申请时间
    String applicationTime;
    // 执行范围说明
    String executionScopeDescription;
}
