package com.icbc.digitalhuman.mapper;

import com.icbc.digitalhuman.entity.NecessaryInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NecessaryInfoMapper {

    void create(NecessaryInfo necessaryInfo);

    NecessaryInfo find(String jobId);
}
