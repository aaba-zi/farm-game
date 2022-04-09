package com.cn.farm.tools;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * The type Format tool.
 */
public class FormatTool {
    /**
     * Date between parse string.
     * @param start date and end date
     * @return setText string
     */
    public static String dateBetweenParse(Date start, Date end) {
        String result = "";
        long temp;
        temp =  DateUtil.between(start, end, DateUnit.DAY);
        result += temp + "day";
        start = DateUtil.offset(start, DateField.DAY_OF_YEAR, (int) temp);

        temp =  DateUtil.between(start, end, DateUnit.HOUR);
        result += temp + "hour";
        start = DateUtil.offset(start, DateField.HOUR, (int) temp);

        temp =  DateUtil.between(start, end, DateUnit.MINUTE);
        result += temp + "minute";
        start = DateUtil.offset(start, DateField.MINUTE, (int) temp);

        temp =  DateUtil.between(start, end, DateUnit.SECOND);
        result += temp + "second";
        start = DateUtil.offset(start, DateField.SECOND, (int) temp);

        return result;
    }
}
