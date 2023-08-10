package com.icbc.digitalhuman.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icbc.digitalhuman.entity.ProcInitBpcByParamode;
import com.icbc.digitalhuman.mapper.ProcInitBpcByParamodeMapper;
import com.icbc.digitalhuman.service.ProcInitBpcByParamodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProcInitBpcByParamodeServiceImpl extends ServiceImpl<ProcInitBpcByParamodeMapper, ProcInitBpcByParamode> implements ProcInitBpcByParamodeService {

    @Resource
    ProcInitBpcByParamodeMapper procInitBpcByParamodeMapper;

    @Override
    public String find(String interfaceInputParameters) {
        return procInitBpcByParamodeMapper.find(interfaceInputParameters);
    }

}
