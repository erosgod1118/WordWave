package co.polarpublishing.common;

public class Utils {

  public static double trimDouble(double value, int charsAfterFp) {
    long factor = (long) Math.pow(10, charsAfterFp);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
  }

}
