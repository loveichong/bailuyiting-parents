package com.bailuyiting.commons.until;


import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	public static final String YEAR_TIME = "yyyyMMddHHmmss";
	public static final String DAY = "yyyyMMdd";
	public static final String MONTH = "yyyyMM";
	public static final String YEAR = "yyyy";
	public static final String CURR_TIME = "HHmmss";
	public static final int ADD_DATE_FIELD_YEAR = 1;
	public static final int ADD_DATE_FIELD_MONTH = 2;
	public static final int ADD_DATE_FIELD_DAY = 3;
	public static final int ADD_DATE_FIELD_WEEK = 4;

	public static final long HOUR_LONG = 60 * 60 * 1000l;
	public static final long MINUTE_LONG = 60 * 1000l;
	public static final long HALF_HOUR_LONG = HOUR_LONG / 2;
	public static final long SIX_HOUR_LONG = HOUR_LONG / 6;
	public static final long DAY_LONG = 24 * HOUR_LONG;

	public static String formatNow() {
		return formatDate(new Date());
	}

	public static String formatMMss() {
		return formatDate(new Date(), "mm:ss");
	}

	public static String formatHH() {
		return formatDate(new Date(), "HH");
	}

	public static String formatMM() {
		return formatDate(new Date(), "mm");
	}

	public static String formatSS() {
		return formatDate(new Date(), "ss");
	}

	public static String formatDD() {
		return formatDate(new Date(), "dd");
	}

	public static String formatDay() {
		return formatDate(new Date(), DAY);
	}

	public static String formatMonthMM() {
		return formatDate(new Date(), "MM");
	}

	public static String formatMonth() {
		return formatDate(new Date(), MONTH);
	}

	public static String formatYeah() {
		return formatDate(new Date(), YEAR);
	}

	public static String formatDate(Date date) {
		return formatDate(date, YEAR_TIME);
	}

	public static String formatDate(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date totDate(String date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date d = new Date();
		try {
			d = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date totDate(String date) {
		return totDate(date, YEAR_TIME);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Long totDateLong(String date) {
		return totDate(date, YEAR_TIME).getTime();
	}

	/**
	 * 
	 * @param time
	 *            mm:ss
	 * @return
	 */
	public static int dateToInt(String time) {
		String mm = time.split(":")[1];
		String hh = time.split(":")[0];
		int imm = Integer.parseInt(mm);
		int ihh = Integer.parseInt(hh) * 60;
		if (ihh == 0) {
			imm += 1;
		}
		int itotal = ihh + imm;
		return itotal;
	}

	/**
	 * 日期的加减
	 * 
	 * @param d
	 * @param amount
	 *            需要加减的数量,负数为减
	 * @param field
	 *            <li>ADD_DATE_FIELD_DAY 按天加减</li>
	 *            <li>ADD_DATE_FIELD_WEEK 按周加减</li>
	 *            <li>ADD_DATE_FIELD_MONTH 按月加减</li>
	 *            <li>ADD_DATE_FIELD_YEAR 按年加减</li>
	 * @return
	 */
	public static Date addDate(Date d, int amount, int field) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		switch (field) {
		case ADD_DATE_FIELD_DAY:
			c.add(Calendar.DAY_OF_MONTH, amount);
			break;
		case ADD_DATE_FIELD_MONTH:
			c.add(Calendar.MONTH, amount);
			break;
		case ADD_DATE_FIELD_YEAR:
			c.add(Calendar.YEAR, amount);
			break;
		case ADD_DATE_FIELD_WEEK:
			c.add(Calendar.WEEK_OF_MONTH, amount);
			break;
		default:
			c.add(Calendar.DAY_OF_MONTH, amount);
		}
		return c.getTime();
	}

	/**
	 * 日期字符串的加减
	 * 
	 * @param d
	 * @param format
	 *            默认为“yyyyMMdd”
	 * @param amount
	 *            需要加减的数量,负数为减
	 * @param field
	 *            <li>ADD_DATE_FIELD_DAY 按天加减</li>
	 *            <li>ADD_DATE_FIELD_WEEK 按周加减</li>
	 *            <li>ADD_DATE_FIELD_MONTH 按月加减</li>
	 *            <li>ADD_DATE_FIELD_YEAR 按年加减</li>
	 * @return
	 */
	public static String addDate(String dStr, String format, int amount, int field) {
		if (StringUtils.isEmpty(format)) {
			format = DAY;
		}
		Date d = totDate(dStr, format);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		switch (field) {
		case ADD_DATE_FIELD_DAY:
			c.add(Calendar.DAY_OF_MONTH, amount);
			break;
		case ADD_DATE_FIELD_MONTH:
			c.add(Calendar.MONTH, amount);
			break;
		case ADD_DATE_FIELD_YEAR:
			c.add(Calendar.YEAR, amount);
			break;
		case ADD_DATE_FIELD_WEEK:
			c.add(Calendar.WEEK_OF_MONTH, amount);
			break;
		default:
			c.add(Calendar.DAY_OF_MONTH, amount);
		}
		return formatDate(c.getTime(), format);
	}

	/**
	 * 判断是否为月末
	 * 
	 * @param d
	 * @return
	 */
	public static boolean isMonthEnd(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			return true;
		}
		return false;
	}

	public static List<String> getMonthInterval(String startMonth, String endMonth) {
		if (StringUtils.isEmpty(startMonth)) {
			return null;
		}
		if (StringUtils.isEmpty(endMonth)) {
			endMonth = formatMonth();
		}
		Date startMonthD = totDate(startMonth, MONTH);
		Date endMonthD = totDate(endMonth, MONTH);
		Date tempD = startMonthD;
		List<String> result = new ArrayList<String>();
		while (tempD == null || tempD.compareTo(endMonthD) <= 0) {
			result.add(formatDate(tempD, MONTH));
			tempD = addDate(tempD, 1, ADD_DATE_FIELD_MONTH);
		}
		return result;
	}

	public static List<String> getDayInterval(String startDay, String endDay) {
		if (StringUtils.isEmpty(startDay)) {
			return null;
		}
		if (StringUtils.isEmpty(endDay)) {
			endDay = formatDay();
		}
		Date startDayD = totDate(startDay, DAY);
		Date endDayD = totDate(endDay, DAY);
		Date tempD = startDayD;
		List<String> result = new ArrayList<String>();
		while (tempD == null || tempD.compareTo(endDayD) <= 0) {
			result.add(formatDate(tempD, DAY));
			tempD = addDate(tempD, 1, ADD_DATE_FIELD_DAY);
		}
		return result;
	}

	private static final String DAY_ = "yyyy-MM-dd";

	public static List<String> getMonthDay() {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		String cmonth = DateUtils.formatDate(date, DateUtils.MONTH);
		String month = DateUtils.formatDate(date, DateUtils.MONTH);
		Map<String, String> rdata = new HashMap<String, String>();
		while (cmonth.equals(month)) {
			rdata.put(DateUtils.formatDate(c.getTime(), DAY_), "");
			c.add(Calendar.DATE, -1);
			month = DateUtils.formatDate(c.getTime(), DateUtils.MONTH);
		}
		c = Calendar.getInstance();
		date = c.getTime();
		cmonth = DateUtils.formatDate(date, DateUtils.MONTH);
		month = DateUtils.formatDate(date, DateUtils.MONTH);
		while (cmonth.equals(month)) {
			rdata.put(DateUtils.formatDate(c.getTime(), DAY_), "");
			c.add(Calendar.DATE, 1);
			month = DateUtils.formatDate(c.getTime(), DateUtils.MONTH);
		}
		List<String> lists = new ArrayList<String>();
		lists.addAll(rdata.keySet());
		Collections.sort(lists);
		return lists;
	}

	public static List<String> getMonthDay(Date startDate, Date endDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		Date date = c.getTime();
		String cmonth = DateUtils.formatDate(date, DateUtils.MONTH);
		String month = DateUtils.formatDate(date, DateUtils.MONTH);
		Map<String, String> rdata = new HashMap<String, String>();
		while (cmonth.equals(month)) {
			rdata.put(DateUtils.formatDate(c.getTime(), DAY_), "");
			c.add(Calendar.DATE, -1);
			month = DateUtils.formatDate(c.getTime(), DateUtils.MONTH);
		}
		c.setTime(startDate);
		date = c.getTime();
		String cmonth1 = DateUtils.formatDate(date, DateUtils.MONTH);
		if (cmonth1.equals(cmonth)) {
			month = DateUtils.formatDate(date, DateUtils.MONTH);
			int i = 0;
			while (cmonth1.equals(month)) {
				if (i != 0) {
					rdata.remove(DateUtils.formatDate(c.getTime(), DAY_));
				}
				c.add(Calendar.DATE, -1);
				month = DateUtils.formatDate(c.getTime(), DateUtils.MONTH);
				i++;
			}
		} else {
			month = DateUtils.formatDate(date, DateUtils.MONTH);
			while (cmonth1.equals(month)) {
				rdata.put(DateUtils.formatDate(c.getTime(), DAY_), "");
				c.add(Calendar.DATE, 1);
				month = DateUtils.formatDate(c.getTime(), DateUtils.MONTH);
			}
		}
		List<String> lists = new ArrayList<String>();
		lists.addAll(rdata.keySet());
		Collections.sort(lists);
		return lists;
	}

	private static final String MONTH_ = "yyyy-MM";

	public static List<String> getYearMonth() {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		String cyear = DateUtils.formatDate(date, DateUtils.YEAR);
		String year = DateUtils.formatDate(date, DateUtils.YEAR);
		Map<String, String> rdata = new HashMap<String, String>();
		while (cyear.equals(year)) {
			rdata.put(DateUtils.formatDate(c.getTime(), MONTH_), "");
			c.add(Calendar.MONTH, -1);
			year = DateUtils.formatDate(c.getTime(), DateUtils.YEAR);
		}
		c = Calendar.getInstance();
		date = c.getTime();
		cyear = DateUtils.formatDate(date, DateUtils.YEAR);
		year = DateUtils.formatDate(date, DateUtils.YEAR);
		while (cyear.equals(year)) {
			rdata.put(DateUtils.formatDate(c.getTime(), MONTH_), "");
			c.add(Calendar.MONTH, 1);
			year = DateUtils.formatDate(c.getTime(), DateUtils.YEAR);
		}
		List<String> lists = new ArrayList<String>();
		lists.addAll(rdata.keySet());
		Collections.sort(lists);
		return lists;
	}

	public static List<String> getYearMonth(Date startDate, Date endDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		Date date = c.getTime();
		String cyear = DateUtils.formatDate(date, DateUtils.YEAR);
		String year = DateUtils.formatDate(date, DateUtils.YEAR);
		Map<String, String> rdata = new HashMap<String, String>();
		while (cyear.equals(year)) {
			rdata.put(DateUtils.formatDate(c.getTime(), MONTH_), "");
			c.add(Calendar.MONTH, -1);
			year = DateUtils.formatDate(c.getTime(), DateUtils.YEAR);
		}
		c.setTime(startDate);
		date = c.getTime();
		String cyear1 = DateUtils.formatDate(date, DateUtils.YEAR);
		if (cyear1.equals(cyear)) {
			year = DateUtils.formatDate(date, DateUtils.YEAR);
			int i = 0;
			while (cyear1.equals(year)) {
				if (i != 0) {
					rdata.remove(DateUtils.formatDate(c.getTime(), MONTH_));
				}
				c.add(Calendar.MONTH, -1);
				year = DateUtils.formatDate(c.getTime(), DateUtils.YEAR);
				i++;
			}
		} else {
			year = DateUtils.formatDate(date, DateUtils.YEAR);
			while (cyear1.equals(year)) {
				rdata.put(DateUtils.formatDate(c.getTime(), MONTH_), "");
				c.add(Calendar.MONTH, 1);
				year = DateUtils.formatDate(c.getTime(), DateUtils.YEAR);
			}
		}
		List<String> lists = new ArrayList<String>();
		lists.addAll(rdata.keySet());
		Collections.sort(lists);
		return lists;
	}

	public static int getBetweenDateDays(Date startD, Date endD) {
		int days = (int) ((endD.getTime() - startD.getTime()) / (60 * 1000 * 60 * 24));
		return days;
	}

	public static int getBetweenDateHours(Date startD, Date endD) {
		int hours = (int) ((endD.getTime() - startD.getTime()) / (60 * 1000 * 60));
		return hours;
	}

	public static int getBetweenDateMinutes(Date startD, Date endD) {
		int minutes = (int) ((endD.getTime() - startD.getTime()) / (60 * 1000));
		return minutes;
	}

	/**
	 * 得到本周周一
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 1);
		return formatDate(c.getTime(), DAY);
	}
	/**
	 * 得到本周周一
	 * 
	 * @return yyyy-MM-dd
	 */
	public static Date getMondayOfThisWeek(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 1);
		return c.getTime();
	}
	/**
	 * 取周数，每周开始从周一算
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getWeeks(Date startDate, Date endDate) {
		int a = 0;
		try {
			a=(int) ((getMondayOfThisWeek(endDate).getTime()-(getMondayOfThisWeek(startDate).getTime()-4*24*60*60*1000))/(7*24*60*60*1000));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a+1;
	}

	public static void main(String[] args) throws ParseException {
		//Date d = parseDate("20161106", "yyyyMMdd");
		//Date d1 = parseDate("20161107", "yyyyMMdd");

		//System.out.println(getWeeks(d, d1));
		int nextInt = new Random().nextInt(10000);
		System.out.println(nextInt);
	}

}
