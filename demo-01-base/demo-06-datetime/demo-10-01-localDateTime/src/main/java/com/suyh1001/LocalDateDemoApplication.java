package com.suyh1001;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author suyh
 * @since 2023-12-08
 */
public class LocalDateDemoApplication {
    public static void main(String[] args) {
        fun01();
        fun02();
        fun03();
    }

    private static void fun01() {
        {
            LocalDate now = LocalDate.now();
            System.out.println("当前日期: " + now);
        }

        {
            LocalDate localDate = LocalDate.of(2023, 12, 8);
            System.out.println("自定义日期: " + localDate);
        }

        {
            // 这里的 08 里面的0 不能少，少了就会报解析异常了。
            // 但是可以使用指定的日期格式解析
            LocalDate parseLocalDate = LocalDate.parse("2023-12-08");
            System.out.println("解析自定义日期：" + parseLocalDate);
        }
    }

    private static void fun02() {
        LocalDate todayDate = LocalDate.now();
        LocalDate tomorrowDate = todayDate.plusDays(1L);
        LocalDate yesterdayDate = todayDate.plusDays(-1L);
        System.out.println("昨天：" + yesterdayDate);
        System.out.println("今天：" + todayDate);
        System.out.println("明天：" + tomorrowDate);
    }

    private static void fun03() {
        // LocalDate  ==> long
        // 好像LocalDate 没有提供直接转换成时间戳的API。
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = localDate.atStartOfDay();
        ZoneId zoneId = ZoneId.systemDefault();
        if (true) {
            // suyh - 留个示例
            zoneId = ZoneId.of("Asia/Shanghai");
        }
        long timestamp = localDateTime.atZone(zoneId).toInstant().toEpochMilli();
        System.out.println("时间戳: " + timestamp);
    }


    // 以前写的TestDateTimeFormatter 里面的代码，移动到一起. #######################################################
    public void test01() {
        // LocalDate 与LocalDateTime 的格式化字符串
        final DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // LocalDate 只有日期，没有HH:mm:ss
        LocalDate localDateNow = LocalDate.now();
        String localDateNowFormat = dayFormat.format(localDateNow);

        final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        // LocalDateTime 是比较详细的时间类
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        String localDateTimeNowFormat = dateTimeFormat.format(localDateTimeNow);
        // log.info("localDateNowFormat: {}, localDateTimeNowFormat: {}", localDateNowFormat, localDateTimeNowFormat);
    }

    /**
     * 1.从日期获取ZonedDateTime并使用其方法toLocalDateTime（）获取LocalDateTime
     * 2.使用LocalDateTime的Instant（）工厂方法
     */
    public void test02() {
        // 转换: Date  ==>  LocalDateTime
        Date date = new Date();
        ZoneId zoneId = ZoneId.systemDefault();

        {
            // 方式一
            Instant instant = date.toInstant();
            LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
            System.out.println("Date = " + date);
            System.out.println("LocalDateTime = " + localDateTime);
        }
        {
            // 方式二
            LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zoneId);
            System.out.println("Date = " + date);
            System.out.println("LocalDateTime = " + localDateTime);
        }
    }

    /**
     * 转换: LocalDateTime  ==>  Date
     * 1.使用atZone（）方法将LocalDateTime转换为ZonedDateTime
     * 2.将ZonedDateTime转换为Instant，并从中获取Date
     */
    public void test03() {
        // 系统默认时区ID
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);

        Date date = Date.from(zdt.toInstant());

        System.out.println("LocalDateTime = " + localDateTime);
        System.out.println("Date = " + date);
    }
}
