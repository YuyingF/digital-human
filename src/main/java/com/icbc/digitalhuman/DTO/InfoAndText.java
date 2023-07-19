package com.icbc.digitalhuman.DTO;

import com.icbc.digitalhuman.Entity.NecessaryInfo;
import com.icbc.digitalhuman.Entity.UnnecessaryInfo;
import lombok.Data;

@Data
public class InfoAndText {
    NecessaryInfo necessaryInfo;
    UnnecessaryInfo unnecessaryInfo;
    String text;
}
