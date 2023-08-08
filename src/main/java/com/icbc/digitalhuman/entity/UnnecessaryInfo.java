package com.icbc.digitalhuman.entity;

import lombok.Data;

@Data
public class UnnecessaryInfo {

    // 作业id
    public String jobId;
    // 作业名称
    public String jobName;
    // 执行频度补充说明
    public String executionFrequencyDescription;
    // 执行范围说明
    public String executionScopeDescription;
    // 接口输入参数补充说明（请按参数顺序填写）
    public String inputParameterDescription;
    // 中断解决方案
    public String interruptionSolution;
    // 上游应用
    public String upstreamApplication;
    // 文件接口名
    public String fileInterfaceName;
    // 文件结构是否变化
    public String isFileStructureChanged;
    // 上游应用文本中文名
    public String upstreamApplicationChineseName;
    // 临时表字段是否需要处理
    public String isTemporaryTableFieldsRequired;
    // 是否有存量文件
    public String hasLegacyFiles;
    // 上游文本相关说明
    public String upstreamTextDescription;
    // 上游联系人
    public String upstreamContact;
    // 上游文件传输方式
    public String upstreamFileTransferMethod;
    // 上游点对点传输接收信息
    public String upstreamPointToPointTransmission;
    // 下游应用
    public String downstreamApplication;
    // 下游目标接口
    public String downstreamTargetInterface;
    // 下游联系人
    public String downstreamContact;
    // 下游文件传输方式
    public String downstreamFileTransferMethod;
    // 下游点对点传输接收信息
    public String downstreamPointToPointTransmission;
    // 开发组补充说明
    public String developmentTeamDescription;
    // 版本库
    public String versionLibrary;
    // 程序清单
    public String programList;
    // 文件所属应用类型
    public String fileApplicationType;
    // 分布式作业名称
    public String distributedJobName;
    // 分布式作业执行时间
    public String distributedJobExecutionTime;
}
