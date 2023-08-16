package com.icbc.digitalhuman.utils;

import com.icbc.digitalhuman.dto.InfoAndText;
import com.icbc.digitalhuman.entity.WorkFlowControl;
import com.icbc.digitalhuman.mapper.NecessaryInfoMapper;
import com.icbc.digitalhuman.mapper.UnnecessaryInfoMapper;
import com.icbc.digitalhuman.mapper.WorkFlowControlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class InfoUtils {

    @Autowired
    private WorkFlowControlMapper workFlowControlMapper;

    @Autowired
    private NecessaryInfoMapper necessaryInfoMapper;

    @Autowired
    private UnnecessaryInfoMapper unnecessaryInfoMapper;

    public static InfoUtils infoUtils;

    @PostConstruct
    public void init() {
        infoUtils = this;
        infoUtils.workFlowControlMapper = this.workFlowControlMapper;
        infoUtils.necessaryInfoMapper = this.necessaryInfoMapper;
        infoUtils.unnecessaryInfoMapper = this.unnecessaryInfoMapper;
    }

    public String findModuleName(String moduleName) {
        List<WorkFlowControl> workflowControls = infoUtils.workFlowControlMapper.find(moduleName);
        StringBuilder messageBuilder = new StringBuilder();
        for (WorkFlowControl control : workflowControls) {
            messageBuilder.append("!Module Name: ").append(control.getModuleName()).append(", Begin Time: ").append(control.getBeginTime()).append(", End Time: ").append(control.getEndTime()).append("\n");
        }
        return messageBuilder.toString();
    }

    public InfoAndText findInfoAndText(String jobId) {
        InfoAndText infoAndText = new InfoAndText();
        infoAndText.setNecessaryInfo(infoUtils.necessaryInfoMapper.find(jobId));
        infoAndText.setUnnecessaryInfo(infoUtils.unnecessaryInfoMapper.find(jobId));
        return infoAndText;
    }

    public void createInfoAndText(InfoAndText infoAndText) {
        infoUtils.necessaryInfoMapper.create(infoAndText.getNecessaryInfo());
        infoUtils.unnecessaryInfoMapper.create(infoAndText.getUnnecessaryInfo());
    }
}
