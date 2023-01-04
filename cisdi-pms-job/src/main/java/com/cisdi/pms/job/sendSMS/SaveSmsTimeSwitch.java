package com.cisdi.pms.job.sendSMS;

import com.cisdi.pms.job.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * 每年执行一次  第一年存储两年的数据  后面每一年存储一年的数据  默认短信发送开关为工作日发送（1）；非工作日不发送（0）
 *
 * @author hgh
 * @date 2022/12/21
 * @apiNote
 */

@Slf4j
@Component
public class SaveSmsTimeSwitch {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${cisdi-pms-job.sms-time-switch}")
    private boolean smsTimeSwitch;

    //@Autowired
    //void Constructor(JdbcTemplate jdbcTemplate) {
    //    this.jdbcTemplate = jdbcTemplate;
    //}

    /**
     * 默认日期格式
     */
    public static String DEFAULT_FORMAT = "yyyy-MM-dd";

    @Scheduled(cron = "${cisdi-pms-job.sms-time-corn}")
    //@Scheduled(fixedDelayString = "5000")
    public void saveSmsStitch() throws ParseException {

        if (!smsTimeSwitch){
            return;
        }

        //0 1 当前年  / 1 2 当前年后一年 / 0 2  当前年 与 当前年后一年
        int one = 1;
        int two = 2;

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        //获取当前年份
        String queryTime = getNowYear();
        //获取历史数据
        //查询数据库数据  有没有当前和当前年后一年的数据  防止系统重启，定时任务重复存储数据
        List<Map<String, Object>> maps = getData(queryTime);

        if (CollectionUtils.isEmpty(maps)) {
            //2.没有   存当前年 和 当前年后一年的数据
            one = 0;
            two = 2;
        } else {
            //数据只有一条  只有当前年数据
            if (maps.get(0).get("date").toString().contains(queryTime) && maps.size() == 1) {
                //有当前年数据  并且  没有当前年后一年的数据
                //只获取当前年后一年的数据
                one = 1;
                two = 2;
            } else {
                //有当前年数据  也  当前年后一年的数据
                return;
            }
        }

        //获取每年数据
        List<Map<String, String>> everyDay = getEveryDay(one, two);

        for (Map<String, String> s : everyDay) {
            Date parse = f.parse(s.get("date"));

            String httpArg = f.format(parse);
            String jsonResult = this.request(httpArg);
            // 0 上班 1周末 2节假日
            if ("0".equals(jsonResult)) {
                s.put("smsSwitch", "1");
            } else if ("1".equals(jsonResult) || "2".equals(jsonResult)) {
                s.put("smsSwitch", "0");
            }
        }

        everyDay.stream().forEach(e -> {
            //添加一条数据
            String id = Util.insertData(jdbcTemplate, "sms_time");
            String upSql = "update sms_time set DATE = ?,SMS_STATUS = ? where Id = ? ";
            //添加数据
            jdbcTemplate.update(upSql, e.get("date"), e.get("smsSwitch"), id);
        });
    }


    private List<Map<String, Object>> getData(String queryTime) {
        String querySql = "SELECT a.date , COUNT(a.date) num FROM (SELECT  SUBSTR(`DATE` FROM 1 for 4) date FROM sms_time) a WHERE a.date = ? OR date = ? GROUP BY date  ORDER BY SUBSTR(`DATE` FROM 1 for 4) asc";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(querySql, queryTime, String.valueOf((Integer.valueOf(queryTime) + 1)));
        return maps;
    }

    /**
     * 获取当前年年份
     * @return
     */
    private String getNowYear() {
        //获取当年时间
        SimpleDateFormat temp = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String queryTime = temp.format(date);
        return queryTime;
    }

    /**
     * 获取当前日期是否是工作日
     *
     * @param httpArg
     * @return
     */
    public static String request(String httpArg) {
        String httpUrl = "http://tool.bitefu.net/jiari/";
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?d=" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取每一天
     *
     * @param startYear
     * @param endYear
     * @return
     */
    private List<Map<String, String>> getEveryDay(int startYear, int endYear) {
        String[] splitS = formatDate(getCurrYearFirst(startYear)).split("-");
        String[] splitE = formatDate(getCurrYearFirst(endYear)).split("-");

        //逐日打印日期
        LocalDate startDate = LocalDate.of(Integer.valueOf(splitS[0]), Integer.valueOf(splitS[1]), Integer.valueOf(splitS[2]));
        LocalDate endDate = LocalDate.of(Integer.valueOf(splitE[0]), Integer.valueOf(splitE[1]), Integer.valueOf(splitE[2]));
        List<Map<String, String>> list = new ArrayList<>();
        while (startDate.isBefore(endDate)) {
            Map<String, String> map = new HashMap<>();
            map.put("date", startDate.toString());
            list.add(map);
            startDate = startDate.plusDays(1);
        }
        return list;
    }


    /**
     * 格式化日期
     *
     * @param date 日期对象
     * @return String 日期字符串
     */
    public static String formatDate(Date date) {
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        String sDate = f.format(date);
        return sDate;
    }

    /**
     * 获取当年的第一天
     *
     * @param year
     * @return
     */
    public static Date getCurrYearFirst(int year) {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear + year);
    }

    /**
     * 获取当年的最后一天
     *
     * @param year
     * @return
     */
    public static Date getCurrYearLast(int year) {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear + year);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

}
