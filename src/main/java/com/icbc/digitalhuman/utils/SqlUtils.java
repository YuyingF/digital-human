package com.icbc.digitalhuman.utils;

import com.icbc.digitalhuman.dto.InfoAndText;
import com.icbc.digitalhuman.entity.BatchWorkDef;
import com.icbc.digitalhuman.entity.NecessaryInfo;
import com.icbc.digitalhuman.entity.WorkCount;
import com.icbc.digitalhuman.mapper.ProcInitBpcByParamodeMapper;
import com.icbc.digitalhuman.mapper.WorkCountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SqlUtils {

    @Autowired
    private ProcInitBpcByParamodeMapper procInitBpcByParamodeMapper;

    @Autowired
    private WorkCountMapper workCountMapper;

    //静态初始化
    public static SqlUtils sqlUtils;

    /**
     * 加上注解@PostConstruct，这样方法就会在Bean初始化之后被Spring容器执行
     */
    @PostConstruct
    public void init() {
        sqlUtils = this;
        sqlUtils.procInitBpcByParamodeMapper = this.procInitBpcByParamodeMapper;
        sqlUtils.workCountMapper = this.workCountMapper;
    }

    public BatchWorkDef infoToBatchWorkDef(InfoAndText infoAndText, String username) {

        BatchWorkDef batchWorkDef = new BatchWorkDef();
        NecessaryInfo necessaryInfo = infoAndText.getNecessaryInfo();

        String executionScope = necessaryInfo.getExecutionScope();
        List<Integer> groupCode = new ArrayList<>();
        if (executionScope.contains("全球")) {
            groupCode.addAll(Arrays.asList(1, 50, 51, 53, 55, 56, 58, 60, 62, 63, 66, 70, 71, 73, 77, 78, 79, 81, 83, 85, 86));
        } else {
            if (executionScope.contains("境内")) {
                groupCode.add(1);
            }
            if (executionScope.contains("境外")) {
                groupCode.addAll(Arrays.asList(50, 51, 53, 55, 56, 58, 60, 62, 63, 66, 70, 71, 73, 77, 78, 79, 81, 83, 85, 86));
            }
        }
        if (executionScope.contains("个人委托")) {
            groupCode.add(5);
        }
        batchWorkDef.setGroupCode(groupCode);

        String version = necessaryInfo.getVersion();
        int count;
        WorkCount existingWorkCount = sqlUtils.workCountMapper.find(version);
        if (existingWorkCount != null) {
            sqlUtils.workCountMapper.update(version);
            count = existingWorkCount.getCount() + 1;
        } else {
            WorkCount newWorkCount = new WorkCount();
            newWorkCount.setVersion(version);
            newWorkCount.setCount(1);
            sqlUtils.workCountMapper.create(newWorkCount);
            count = 1;
        }
        String countFormatted = (count < 10 ? "0" + count : String.valueOf(count));
        String workId = "y" + version.substring(0, 4) + "m" + version.substring(5, 6) + "w" + countFormatted;
        batchWorkDef.setWorkId(workId);

        batchWorkDef.setWorkName(necessaryInfo.getProjectName());

        batchWorkDef.setBatchMode(necessaryInfo.getApplication());

        String executionFrequency = necessaryInfo.getExecutionFrequency();
        String workInterval = batchWorkDef.getWorkInterval();
        if (executionFrequency.contains("日") || executionFrequency.contains("天")) {
            workInterval = "D";
        } else if (executionFrequency.contains("月")) {
            workInterval = "M";
        } else if (executionFrequency.contains("年")) {
            workInterval = "Y";
        } else if (executionFrequency.contains("不定")) {
            workInterval = "P";
        }
        batchWorkDef.setWorkInterval(workInterval);

        batchWorkDef.setWorkNowTime(necessaryInfo.getEffectiveDate());

        batchWorkDef.setWorkSeq(count);

        // workInitType;
        // workInitProc;

        batchWorkDef.setWorkProcName(necessaryInfo.getStoredProcedureInterface());

        String interfaceInputParameters = necessaryInfo.getInterfaceInputParameters();
        batchWorkDef.setWorkParaNum(interfaceInputParameters.split("\\+").length);

        String workParamMode = sqlUtils.procInitBpcByParamodeMapper.find(interfaceInputParameters);
        batchWorkDef.setWorkParamMode(workParamMode);

        String notes = version + "_" + username + "_DH_" + necessaryInfo.getRequirementSubItem();
        batchWorkDef.setNotes(notes);

        return batchWorkDef;
    }

    public void toSql(InfoAndText infoAndText, String username) {
        BatchWorkDef batchWorkDef = infoToBatchWorkDef(infoAndText, username);

        StringBuilder sqlBuilder = new StringBuilder();

        List<Integer> groupCodeList = batchWorkDef.getGroupCode();
        if (groupCodeList.size() > 1) {
            for (Integer groupCode : groupCodeList) {
                String insertStatement = insert(batchWorkDef, groupCode);
                sqlBuilder.append(insertStatement).append("\n");
            }
        } else {
            sqlBuilder.append(insert(batchWorkDef, null)).append("\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(batchWorkDef.getNotes()))) {
            writer.write(sqlBuilder.toString());
            System.out.println("SQL script generated successfully.");
        } catch (IOException e) {
            System.err.println("Error generating SQL script: " + e.getMessage());
        }
    }

    private String insert(BatchWorkDef batchWorkDef, Integer groupCode) {
        StringBuilder statementBuilder = new StringBuilder("INSERT INTO batch_work_def (GROUP_CODE, WORK_ID, " + "WORK_NAME, BATCH_MODE, WORK_INTERVAL, WORK_NOWTIME, WORK_SEQ, WORK_TYPE, WORK_INIT_TYPE, " + "WORK_INIT_PROC, WORK_PROCNAME, WORK_PARANUM, WORK_PARAMMODE, DATA_FLAG, COMMIT_NUM, SCALE_FLAG, " + "VALID_FLAG, NOTES) VALUES (");

        statementBuilder.append(groupCode).append(", ");
        statementBuilder.append("'").append(batchWorkDef.getWorkId()).append("', ");
        statementBuilder.append("'").append(batchWorkDef.getWorkName()).append("', ");
        statementBuilder.append("'").append(batchWorkDef.getBatchMode()).append("', ");
        statementBuilder.append("'").append(batchWorkDef.getWorkInterval()).append("', ");
        statementBuilder.append("'").append(batchWorkDef.getWorkNowTime()).append("', ");
        statementBuilder.append(batchWorkDef.getWorkSeq()).append(", ");
        statementBuilder.append(batchWorkDef.getWorkType()).append(", ");
        statementBuilder.append(batchWorkDef.getWorkInitType()).append(", ");
        statementBuilder.append("'").append(batchWorkDef.getWorkInitProc()).append("', ");
        statementBuilder.append("'").append(batchWorkDef.getWorkProcName()).append("', ");
        statementBuilder.append(batchWorkDef.getWorkParaNum()).append(", ");
        statementBuilder.append("'").append(batchWorkDef.getWorkParamMode()).append("', ");
        statementBuilder.append(batchWorkDef.getDataFlag()).append(", ");
        statementBuilder.append(batchWorkDef.getCommitNum()).append(", ");
        statementBuilder.append(batchWorkDef.getScaleFlag()).append(", ");
        statementBuilder.append(batchWorkDef.getValidFlag()).append(", ");
        statementBuilder.append("'").append(batchWorkDef.getNotes()).append("'");

        statementBuilder.append(");");

        return statementBuilder.toString();
    }
}
