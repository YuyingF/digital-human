package com.icbc.digitalhuman.entity;

import lombok.Data;

import java.util.List;

@Data
public class BatchWorkDef {
    List<Integer> groupCode;
    String workId;
    String workName;
    String batchMode;
    String workInterval;
    String workNowTime;
    int workSeq = 1;
    int workType = 1;
    int workInitType = 0;
    String workInitProc;
    String workProcName;
    String workParaNum;
    String workParamMode;
    int dataFlag = 0;
    int commitNum = 500;
    int scaleFlag = 0;
    int validFlag = 1;
    String notes;
}
