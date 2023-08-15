package com.icbc.digitalhuman.utils;

import com.icbc.digitalhuman.entity.WorkFlowControl;
import com.icbc.digitalhuman.mapper.WorkFlowControlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class BatchUtils {

    @Autowired
    private WorkFlowControlMapper workFlowControlMapper;

    public static BatchUtils batchUtils;

    @PostConstruct
    public void init() {
        batchUtils = this;
        batchUtils.workFlowControlMapper = this.workFlowControlMapper;
    }

    public String find(String moduleName) {
        List<WorkFlowControl> workflowControls = batchUtils.workFlowControlMapper.find(moduleName);
        StringBuilder messageBuilder = new StringBuilder();
        for (WorkFlowControl control : workflowControls) {
            messageBuilder.append("Module Name: ").append(control.getModuleName())
                    .append(", Begin Time: ").append(control.getBeginTime())
                    .append(", End Time: ").append(control.getEndTime())
                    .append("\n");
        }
        return messageBuilder.toString();
    }
}
