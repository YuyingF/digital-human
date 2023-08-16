package com.icbc.digitalhuman.mapper;

import com.icbc.digitalhuman.entity.UnnecessaryInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UnnecessaryInfoMapper {

    void create(UnnecessaryInfo unnecessaryInfo);

    UnnecessaryInfo find(String jobId);
}
