package com.icbc.digitalhuman.entity;

import lombok.Data;

@Data
public class UnnecessaryInfo {

    public String id;
    // 处理存过接口
    public String storedProcedureInterface;
    // 接口输入参数补充说明
    public String inputParameterDescription;
    // 接口输出参数
    public String outputParameters;
    // 中断解决方案
    public String interruptionSolution;
    // 上游应用
    public String upstreamApplication;
    // 文件接口名
    public String fileInterfaceName;
    // 文件结构是否变化
    public String isFileStructureChanged;
    // 作业名称
    public String jobName;
    // 执行频度补充说明
    public String executionFrequencyDescription;
    // 上游应用文本中文名
    public String upstreamApplicationChineseName;
    // 临时表字段是否需要处
    public String isTemporaryTableFieldsRequired;
    // 是否有存量文件
    public String hasLegacyFiles;
    // 上游文本相关说明
    public String upstreamTextDescription;
    // 上游联系人
    public String upstreamContact;
    // 上游文件传输方式
    public String upstreamFileTransferMethod;
    // 上游点对点传输接收信
    public String upstreamPointToPointTransmission;
    // 下游应用
    public String downstreamApplication;
    // 下游目标接口
    public String downstreamTargetInterface;
    // 下游联系人
    public String downstreamContact;
    // 下游点对点传输接收信
    public String downstreamPointToPointTransmission;
    // 下游文件传输方式
    public String downstreamFileTransferMethod;
    // 开发组补充说明
    public String developmentTeamDescription;
    // 申请人
    public String applicant;
    // 申请时间
    public String applicationTime;
    // 执行范围说明
    public String executionScopeDescription;
}
