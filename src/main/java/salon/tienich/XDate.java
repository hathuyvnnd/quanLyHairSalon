/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.tienich;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author mrphu
 */
public class XDate {

    static SimpleDateFormat format = new SimpleDateFormat();

    /*Chuyển đổi String sang Date
    date : String cần chuyển
    pattern : định dạng thời gian
    return: Date kết quả*/
    public static Date toDate(String date, String pattern) {
        try {
            format.applyPattern(pattern);
            return format.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    /*Chuyển đổi từ Date sang String
    date là Date cần chuyển 
    pattern: định dạng thời gian
    return : String*/
    public static String toString(Date date, String pattern) {
        format.applyPattern(pattern);
        return format.format(date);
    }

    public static Date now() {
        return new Date();
    }

    public static Calendar now1() {
        return Calendar.getInstance();
    }

    public Calendar StringToCalendar(String input) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        calendar.setTime(simpleDateFormat.parse(input));
        return calendar;
    }

    /*Bổ sung số ngày vào thời gian
    date: thời gian hiện tại
    days: số ngày cần bổ sung
    return: Date kết quả*/
    public static Date addDays(Date date, long days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }
    static final SimpleDateFormat DATE_FORMATER = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * * Chuyển đổi String sang Date * @param date là String cần chuyển
     *
     *
     * @param date
     * @param pattern * là định dạng thời gian * @return Date kết quả PROJECT
     * DOCUMENT SAMPLE PROJECT - ỨNG DỤNG PHẦN MỀM PAGE 44
     *
     * @return date
     */
    public static Date toDate(String date, String... pattern) {
        try {
            if (pattern.length > 0) {

                DATE_FORMATER.applyPattern(pattern[0]);

            }
            if (date == null) {
                return XDate.now();
            }
            System.out.println(date);
            return DATE_FORMATER.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * * Chuyển đổi từ Date sang String * @param date là Date cần chuyển đổi
     *
     * * @param pattern là định dạng thời gian * @return String kết quả
     * @param date
     * @param pattern
     * @return String
     */
    public static String toString(Date date, String... pattern) {
        if (pattern.length > 0) {
            DATE_FORMATER.applyPattern(pattern[0]);
        }
        if (date == null) {
            date = XDate.now();
        }
        return DATE_FORMATER.format(date);
    }

    /**
     * * Lấy thời gian hiện tại * @return Date kết quả
     *
     * @return date
     */
    /**
     * * Bổ sung số ngày vào thời gian * @param date thời gian hiện có
     *
     *
     * @param date
     * @param days số ngày cần bổ sung váo date * @return Date kết quả
     * @return date
     */
    public static Date addDays(Date date, int days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }

    /**
     * * Bổ sung số ngày vào thời gian hiện hành * @param days số ngày cần bổ
     * sung vào thời gian hiện tại * @return Date kết quả
     *
     * @param days
     * @return date
     */
    public static Date add(int days) {
        Date now = XDate.now();
        now.setTime(now.getTime() + days * 24 * 60 * 60 * 1000);
        return now;
    }

}
