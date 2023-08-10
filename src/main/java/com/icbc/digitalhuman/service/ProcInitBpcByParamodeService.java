package com.icbc.digitalhuman.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icbc.digitalhuman.entity.ProcInitBpcByParamode;

public interface ProcInitBpcByParamodeService extends IService<ProcInitBpcByParamode> {

    String find(String interfaceInputParameters);
}
