package com.icbc.digitalhuman.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DateUtils {

    public static String chooseProductionDate() {
        Date currentDate = new Date();
        // 设置日期的最小值，确保选择的日期大于当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        currentDate = getMinTimeOfDate(currentDate, sdf);

        String[] dates = {"2023/07/08", "2023/08/05", "2023/09/02", "2023/10/14", "2023/11/18", "2023/12/09"};

        List<Date> validDates = new ArrayList<>();

        for (String dateString : dates) {
            try {
                Date date = sdf.parse(dateString);
                // 设置日期的最小值，确保选择的日期大于当前日期
                date = getMinTimeOfDate(date, sdf);

                // 计算日期差值
                long difference = date.getTime() - currentDate.getTime();

                // 确保选择的日期大于两周
                if (difference > 14 * 24 * 60 * 60 * 1000) {
                    validDates.add(date);
                }

                // 收集到三个有效日期后停止循环
                if (validDates.size() >= 3) {
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // 格式化日期，并使用 "$" 连接
        List<String> formattedDates = validDates.stream()
                .map(date -> "$" + sdf.format(date))
                .collect(Collectors.toList());

        return String.join("", formattedDates);
    }

    public String setVersion(String productionDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse(productionDate);
            SimpleDateFormat versionFormat = new SimpleDateFormat("yyyyMM");
            return versionFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String setEffectiveDate(String productionDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate productionLocalDate = LocalDate.parse(productionDate, formatter);
            LocalDate effectiveLocalDate = productionLocalDate.plusDays(1);
            return effectiveLocalDate.format(formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Date getMinTimeOfDate(Date date, SimpleDateFormat sdf) {
        try {
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }
}
