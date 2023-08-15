package com.icbc.digitalhuman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icbc.digitalhuman.entity.WorkFlowControl;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkFlowControlMapper extends BaseMapper<WorkFlowControl> {

    List<WorkFlowControl> find(String moduleName);
}
