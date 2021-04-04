/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eng.eSystem.deprecated;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Marek Vajgl
 */
@Deprecated
public class DateTime {

  private final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
  
  private final int millisecond;
  private final int second;
  private final int minute;
  private final int hour;
  private final int day;
  private final int month;
  private final int year;

  public DateTime(int year, int month, int day, int hour, int minute, int second, int millisecond) {
    this.year = year;
    this.month = month;
    this.day = day;
    this.hour = hour;
    this.minute = minute;
    this.second = second;
    this.millisecond = millisecond;
  }

  public DateTime(int year, int month, int day, int hour, int minute, int second) {
    this(year, month, day, hour, minute, second, 0);
  }

  public DateTime(int year, int month, int day) {
    this(year, month, day, 0, 0, 0, 0);
  }

  public DateTime() {
    this.year = 0;
    this.month = 0;
    this.day = 0;
    this.hour = 0;
    this.minute = 0;
    this.second = 0;
    this.millisecond = 0;
  }

  public static DateTime getNow() {
    Calendar c = Calendar.getInstance();
    DateTime ret = new DateTime(
      c.get(Calendar.YEAR),
      c.get(Calendar.MONTH),
      c.get(Calendar.DAY_OF_MONTH),
      c.get(Calendar.HOUR_OF_DAY),
      c.get(Calendar.MINUTE),
      c.get(Calendar.SECOND),
      c.get(Calendar.MILLISECOND));
    return ret;
  }

  public DateTime clone(
    boolean cloneYear, boolean cloneMonth, boolean cloneDay,
    boolean cloneHour, boolean cloneMinute, boolean cloneSecond, boolean cloneMillisecond) {
    int year = cloneYear ? this.year : 0;
    int month = cloneMonth ? this.month : 0;
    int day = cloneDay ? this.day : 0;
    int hour = cloneHour ? this.hour : 0;
    int minute = cloneMinute ? this.minute : 0;
    int second = cloneSecond ? this.second : 0;
    int millisecond = cloneMillisecond ? this.millisecond : 0;

    DateTime ret = new DateTime(year, month, day, hour, minute, second, millisecond);
    return ret;
  }

  public DateTime getTime() {
    return this.clone(false, false, false, true, true, true, true);
  }

  public DateTime getDate() {
    return this.clone(true, true, true, false, false, false, false);
  }

  public java.util.Date toDate() {
    Date d = toCalendar().getTime();
    return d;
  }

  public java.util.Calendar toCalendar() {
    Calendar c = new GregorianCalendar(year, month, month, hour, minute, second);
    return c;
  }
  
  public String toString(String pattern){
    DateFormat sdf = new SimpleDateFormat(pattern);
    Date d = toDate();
    return sdf.format(d);
  }
  
  @Override
  public String toString(){
    return this.toString(DEFAULT_PATTERN);
  }
  
}
