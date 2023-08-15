package com.icbc.digitalhuman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icbc.digitalhuman.entity.WorkCount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkCountMapper extends BaseMapper<WorkCount> {

    void create(WorkCount workCount);

    void update(String version);

    WorkCount find(String version);
}
