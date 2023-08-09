package com.icbc.digitalhuman.utils;

import com.icbc.digitalhuman.dto.InfoAndText;
import com.icbc.digitalhuman.entity.BatchWorkDef;
import com.icbc.digitalhuman.entity.NecessaryInfo;
import com.icbc.digitalhuman.entity.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlUtils {
    public static BatchWorkDef infoToBatchWorkDef(InfoAndText infoAndText) {
        BatchWorkDef batchWorkDef = new BatchWorkDef();
        NecessaryInfo necessaryInfo = infoAndText.getNecessaryInfo();

        String executionScope = necessaryInfo.getExecutionScope();
        List<Integer> groupCode = new ArrayList<>();
        if (executionScope.contains("境内")) {
            groupCode.add(1);
        }
        if (executionScope.contains("境外")) {
            groupCode.addAll(Arrays.asList(50, 51, 53, 55, 56, 58, 60, 62, 63, 66, 70, 71, 73, 77, 78, 79, 81, 83, 85, 86));
        }
        if (executionScope.contains("个人委托")) {
            groupCode.add(5);
        }
        batchWorkDef.setGroupCode(groupCode);

        String productionDate = necessaryInfo.getProductionDate();
        System.out.println("sql" + productionDate);
        LocalDate productionLocalDate = LocalDate.parse(productionDate, DateTimeFormatter.ofPattern("yyyy/M/d"));
        int year = productionLocalDate.getYear();
        int month = productionLocalDate.getMonthValue();
        String formattedYear = String.valueOf(year);
        String formattedMonth = (month < 10 ? "0" + month : String.valueOf(month));

        batchWorkDef.setWorkId("y" + formattedYear + "m" + formattedMonth + "w01");

        batchWorkDef.setWorkName(necessaryInfo.getProjectName());

        // batchMode;

        String executionFrequency = necessaryInfo.getExecutionFrequency();
        String workInterval = batchWorkDef.getWorkInterval();
        if (executionFrequency.contains("日") || executionFrequency.contains("天")) {
            workInterval = "D";
        } else if (executionFrequency.contains("月")) {
            workInterval = "M";
        } else if (executionFrequency.contains("年")) {
            workInterval = "Y";
        }

        batchWorkDef.setWorkNowTime(necessaryInfo.getEffectiveDate());

        // workSeq
        // workInitType;
        // workInitProc;

        batchWorkDef.setWorkProcName(necessaryInfo.getStoredProcedureInterface());
        // workParaNum;
        // workParamMode;

        String notes = formattedYear + formattedMonth + "_" + User.username + "_DH_" + necessaryInfo.getRequirementSubItem();
        batchWorkDef.setNotes(notes);

        return batchWorkDef;
    }

    public static void toSql(InfoAndText infoAndText) {
        BatchWorkDef batchWorkDef = infoToBatchWorkDef(infoAndText);

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

    private static String insert(BatchWorkDef batchWorkDef, Integer groupCode) {
        StringBuilder statementBuilder = new StringBuilder("INSERT INTO batch_work_def (GROUP_CODE, WORK_ID, " +
                "WORK_NAME, BATCH_MODE, WORK_INTERVAL, WORK_NOWTIME, WORK_SEQ, WORK_TYPE, WORK_INIT_TYPE, " +
                "WORK_INIT_PROC, WORK_PROCNAME, WORK_PARANUM, WORK_PARAMMODE, DATA_FLAG, COMMIT_NUM, SCALE_FLAG, " +
                "VALID_FLAG, NOTES) VALUES (");

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
        statementBuilder.append("'").append(batchWorkDef.getWorkParaNum()).append("', ");
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
