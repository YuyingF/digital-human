package com.icbc.digitalhuman.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private String version;
    private String productionDate;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public void setVersionAndProductionDate() {
        Date currentDate = new Date();

        // 设置日期的最小值，确保选择的日期大于当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        currentDate = getMinTimeOfDate(currentDate, sdf);

        String[] dates = {"2023/07/08", "2023/08/05", "2023/09/02", "2023/10/14", "2023/11/18", "2023/12/09"};

        // 设置初始的最小日期
        Date nextDate = null;
        long minDifference = Long.MAX_VALUE;

        // 遍历日期列表，找到满足条件的日期
        for (String dateString : dates) {
            try {
                Date date = sdf.parse(dateString);
                // 设置日期的最小值，确保选择的日期大于当前日期
                date = getMinTimeOfDate(date, sdf);

                // 计算日期差值
                long difference = date.getTime() - currentDate.getTime();

                // 确保选择的日期大于两周
                if (difference > 14 * 24 * 60 * 60 * 1000 && difference < minDifference) {
                    nextDate = date;
                    minDifference = difference;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (nextDate != null) {
            // 设置version属性
            SimpleDateFormat versionFormat = new SimpleDateFormat("yyyyMM");
            String versionString = versionFormat.format(nextDate);
            setVersion(versionString);

            // 设置productionDate属性
            SimpleDateFormat productionDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String productionDateString = productionDateFormat.format(nextDate);
            setProductionDate(productionDateString);
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
