package com.icbc.digitalhuman.dto;

import com.icbc.digitalhuman.entity.NecessaryInfo;
import com.icbc.digitalhuman.entity.UnnecessaryInfo;
import lombok.Data;

@Data
public class InfoAndText {

    NecessaryInfo necessaryInfo;
    UnnecessaryInfo unnecessaryInfo;
    String text;
}
