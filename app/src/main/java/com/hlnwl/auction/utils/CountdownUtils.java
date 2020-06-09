package com.hlnwl.auction.utils;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.iwgang.countdownview.CountdownView;
import cn.iwgang.countdownview.DynamicConfig;

public class CountdownUtils {
    public static void getCounDown(CountdownView countdownView, String dateStr){
        String date1 = StringsUtils.getSystemTime1();
        String date2 = dateStr;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            long diff = d2.getTime() - d1.getTime();
            double result = diff * 1.0 / (1000 * 60 * 60);
            if (result <= 24) {
                countdownView.customTimeShow(false, true, true, true, false);
                countdownView.start(diff);
            } else {
                countdownView.customTimeShow(true, true, true, true, false);
                DynamicConfig.Builder builder = new DynamicConfig.Builder();
                builder.setSuffixDay("天");
                countdownView.dynamicShow(builder.build());
                countdownView.start(diff);
            }

        } catch (Exception e) {
        }
    }


}
