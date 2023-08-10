package com.icbc.digitalhuman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icbc.digitalhuman.entity.ProcInitBpcByParamode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProcInitBpcByParamodeMapper extends BaseMapper<ProcInitBpcByParamode> {

    String find(String interfaceInputParameters);
}
