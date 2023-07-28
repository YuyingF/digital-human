package com.icbc.digitalhuman.entity;

import lombok.Data;
import org.apache.ibatis.jdbc.Null;


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

    public String getAllPropertiesAsString() {
        String result="";
        if(storedProcedureInterface!= null){
            result+="处理存过接口"+storedProcedureInterface+"  \r\n";
        }
        if(inputParameterDescription!= null){
            result+="接口输入参数补充说明"+inputParameterDescription+"  \r\n";
        }
        if(outputParameters!= null){
            result+="接口输出参数"+outputParameters+"  \r\n";
        }
        if(interruptionSolution!= null){
            result+="中断解决方案"+interruptionSolution+"  \r\n";
        }
        if(upstreamApplication!= null){
            result+="上游应用"+upstreamApplication+"  \r\n";
        }
        if(fileInterfaceName!= null){
            result+="文件接口名"+fileInterfaceName+"  \r\n";
        }
        if(isFileStructureChanged!= null){
            result+="文件结构是否变化"+isFileStructureChanged+"  \r\n";
        }
        if(jobName!= null){
            result+="作业名称"+jobName+"  \r\n";
        }
        if(executionFrequencyDescription!= null){
            result+="执行频度补充说明"+executionFrequencyDescription+"  \r\n";
        }
        if(upstreamApplicationChineseName!= null){
            result+="上游应用文本中文名"+upstreamApplicationChineseName+"  \r\n";
        }
        if(isTemporaryTableFieldsRequired!= null){
            result+="临时表字段是否需要处"+isTemporaryTableFieldsRequired+"  \r\n";
        }
        if(hasLegacyFiles!= null){
            result+="是否有存量文件"+hasLegacyFiles+"  \r\n";
        }
        if(upstreamTextDescription!= null){
            result+="上游文本相关说明"+upstreamTextDescription+"  \r\n";
        }
        if(upstreamContact!= null){
            result+="上游联系人"+upstreamContact+"  \r\n";
        }
        if(upstreamFileTransferMethod!= null){
            result+="上游文件传输方式"+upstreamFileTransferMethod+"  \r\n";
        }
        if(upstreamPointToPointTransmission!= null){
            result+="上游点对点传输接收信"+upstreamPointToPointTransmission+"  \r\n";
        }
        if(downstreamApplication!= null){
            result+="下游应用"+downstreamApplication+"  \r\n";
        }
        if(downstreamTargetInterface!= null){
            result+="下游目标接口"+downstreamTargetInterface+"  \r\n";
        }
        if(downstreamContact!= null){
            result+="下游联系人"+downstreamContact+"  \r\n";
        }
        if(downstreamPointToPointTransmission!= null){
            result+="下游点对点传输接收信"+downstreamPointToPointTransmission+"  \r\n";
        }
        if(downstreamFileTransferMethod!= null){
            result+="下游文件传输方式"+downstreamFileTransferMethod+"  \r\n";
        }
        if(developmentTeamDescription!= null){
            result+="开发组补充说明"+developmentTeamDescription+"  \r\n";
        }
        if(applicant!= null){
            result+="申请人"+applicant+"  \r\n";
        }
        if(applicationTime!= null){
            result+="申请时间"+applicationTime+"  \r\n";
        }
        if(executionScopeDescription!= null){
            result+="执行范围说明"+executionScopeDescription+"  \r\n";
        }

        return result;
    }
}
