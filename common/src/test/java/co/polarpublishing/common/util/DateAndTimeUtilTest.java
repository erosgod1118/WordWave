package co.polarpublishing.common.util;

import java.time.Period;
import java.util.Calendar;
import org.junit.Assert;
import org.junit.Test;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
public class DateAndTimeUtilTest {

  @Test
  public void testGetPeriod_WithNoYearMonthDayDiff() {
    Calendar calendar1 = Calendar.getInstance();
    Calendar calendar2 = Calendar.getInstance();

    Period periodDiff = DateAndTimeUtil.getPeriodDiff(calendar1.getTimeInMillis(), calendar2.getTimeInMillis());

    Assert.assertNotNull(periodDiff);
    Assert.assertEquals(0, periodDiff.getYears());
    Assert.assertEquals(0, periodDiff.getMonths());
    Assert.assertEquals(0, periodDiff.getDays());
  }

  @Test
  public void testGetPeriod_With1YearDiff() {
    Calendar calendar1 = Calendar.getInstance();
    calendar1.set(Calendar.YEAR, 2020);

    Calendar calendar2 = Calendar.getInstance();
    calendar2.set(Calendar.YEAR, 2021);

    Period periodDiff = DateAndTimeUtil.getPeriodDiff(calendar1.getTimeInMillis(),
        calendar2.getTimeInMillis());

    Assert.assertNotNull(periodDiff);
    Assert.assertEquals(1, periodDiff.getYears());
    Assert.assertEquals(0, periodDiff.getMonths());
    Assert.assertEquals(0, periodDiff.getDays());
  }

  @Test
  public void testGetPeriod_With1MonthDiff() {
    Calendar calendar1 = Calendar.getInstance();
    calendar1.set(Calendar.MONTH, 0);
    calendar1.set(Calendar.DATE, 10);

    Calendar calendar2 = Calendar.getInstance();
    calendar2.set(Calendar.MONTH, 1);
    calendar2.set(Calendar.DATE, 10);

    Period periodDiff = DateAndTimeUtil.getPeriodDiff(calendar1.getTimeInMillis(), calendar2.getTimeInMillis());

    Assert.assertNotNull(periodDiff);
    Assert.assertEquals(0, periodDiff.getYears());
    Assert.assertEquals(1, periodDiff.getMonths());
    Assert.assertEquals(0, periodDiff.getDays());
  }

  @Test
  public void testGetPeriod_With1DayDiff() {
    Calendar calendar1 = Calendar.getInstance();

    Calendar calendar2 = Calendar.getInstance();
    calendar2.add(Calendar.DATE, 1);

    Period periodDiff = DateAndTimeUtil.getPeriodDiff(calendar1.getTimeInMillis(), calendar2.getTimeInMillis());

    Assert.assertNotNull(periodDiff);
    Assert.assertEquals(0, periodDiff.getYears());
    Assert.assertEquals(0, periodDiff.getMonths());
    Assert.assertEquals(1, periodDiff.getDays());
  }

  @Test
  public void testGetPeriod_With11MonthDiff() {
    Calendar calendar1 = Calendar.getInstance();
    calendar1.set(Calendar.MONTH, 5);

    Calendar calendar2 = Calendar.getInstance();
    calendar2.set(Calendar.MONTH, 5);
    calendar2.add(Calendar.MONTH, 11);

    Period periodDiff = DateAndTimeUtil.getPeriodDiff(calendar1.getTimeInMillis(), calendar2.getTimeInMillis());

    Assert.assertNotNull(periodDiff);
    Assert.assertEquals(0, periodDiff.getYears());
    Assert.assertEquals(11, periodDiff.getMonths());
    Assert.assertEquals(0, periodDiff.getDays());
  }

  @Test
  public void testGetPeriod_With20DaysDiff() {
    Calendar calendar1 = Calendar.getInstance();
    calendar1.set(Calendar.DATE, 15);

    Calendar calendar2 = Calendar.getInstance();
    calendar2.set(Calendar.DATE, 15);
    calendar2.add(Calendar.DATE, 20);

    Period periodDiff = DateAndTimeUtil.getPeriodDiff(calendar1.getTimeInMillis(), calendar2.getTimeInMillis());

    Assert.assertNotNull(periodDiff);
    Assert.assertEquals(0, periodDiff.getYears());
    Assert.assertEquals(0, periodDiff.getMonths());
    Assert.assertEquals(20, periodDiff.getDays());
  }

  @Test
  public void testGetPeriod_With1Year2Month16DaysDiff() {
    Calendar calendar1 = Calendar.getInstance();
    calendar1.set(Calendar.YEAR, 2020);
    calendar1.set(Calendar.MONTH, 6);
    calendar1.set(Calendar.DATE, 15);

    Calendar calendar2 = Calendar.getInstance();
    calendar2.set(Calendar.YEAR, 2021);
    calendar2.set(Calendar.MONTH, 8);
    calendar2.set(Calendar.DATE, 15);
    calendar2.add(Calendar.DATE, 16);

    Period periodDiff = DateAndTimeUtil.getPeriodDiff(calendar1.getTimeInMillis(), calendar2.getTimeInMillis());

    Assert.assertNotNull(periodDiff);
    Assert.assertEquals(1, periodDiff.getYears());
    Assert.assertEquals(2, periodDiff.getMonths());
    Assert.assertEquals(16, periodDiff.getDays());
  }

}
