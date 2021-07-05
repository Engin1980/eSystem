/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eng.eSystem.utilites;

/**
 *
 * @author Marek Vajgl
 */
public class NumberUtils {
  /**
   * Returns if value is between specified exclusive bounds.
   * @param min Minimum value
   * @param value Checked value
   * @param max Maximum value
   * @return True if value is between minimum and maximum, exclusively.
   */
  public static boolean isBetween(int min, int value, int max){
    return min < value && value < max;
  }
  /**
   * Returns if value is between specified inclusive bounds.
   * @param min Minimum value
   * @param value Checked value
   * @param max Maximum value
   * @return True if value is between minimum and maximum, inclusively.
   */
  public static boolean isBetweenOrEqual(int min, int value, int max){
    return min <= value && value <= max;
  }

  /**
   * Returns if value is between specified exclusive bounds.
   * @param min Minimum value
   * @param value Checked value
   * @param max Maximum value
   * @return True if value is between minimum and maximum, exclusively.
   */
  public static boolean isBetween(double min, double value, double max){
    return min < value && value < max;
  }
  /**
   * Returns if value is between specified inclusive bounds.
   * @param min Minimum value, must be lower or equal than maximum
   * @param value Checked value
   * @param max Maximum value, must be greater or equal than minimum
   * @return True if value is between minimum and maximum, inclusively.
   * @see #isInRange(double, double, double)
   */
  public static boolean isBetweenOrEqual(double min, double value, double max){
    return min <= value && value <= max;
  }

  /**
   * Returns if value is between specified exclusive bounds.
   * @param a First value
   * @param value Checked value
   * @param b Second value, must be greater or equal than minimum
   * @return True if value is between a and b, exclusively.
   * @see #isBetween(int, int, int)
   */
  public static boolean isInRange(double a, double value, double b){
    boolean ret;
    if (a <= b)
      ret = isBetween(a, value, b);
    else
      ret = isBetween(b, value, a);
    return ret;
  }

  public static boolean isInRangeInclusive(double a, double value, double b){
    boolean ret;
    if (a <= b)
      ret = isBetweenOrEqual(a, value, b);
    else
      ret = isBetweenOrEqual(b, value, a);
    return ret;
  }

  public static double round(double value, int radix){
    double mult = Math.pow(10, radix);
    double tmp = value / mult;
    tmp = Math.round(tmp);
    tmp = tmp * mult;
    return tmp;
  }

  public static double floor(double value, int radix){
    double mult = Math.pow(10, radix);
    double tmp = value / mult;
    tmp = Math.floor(tmp);
    tmp = tmp * mult;
    return tmp;
  }

  public static double ceil(double value, int radix){
    double mult = Math.pow(10, radix);
    double tmp = value / mult;
    tmp = Math.ceil(tmp);
    tmp = tmp * mult;
    return tmp;
  }

  /**
   * Returns the value, but bounded between maximum or minimum.
   * @param min Minimal returned value.
   * @param value Returned value if between min and max.
   * @param max Maximal returned value.
   * @return Returned value, if between min-max, or min/max.
   * Function returns middle value, if it fits into min..max interval. If value is
   * lower then minimum, minimum is returned. If value is higher than maximum,
   * maximum is returned. Function does not check and expects that min &lt;=max,
   * otherwise unexpected behavior occurs.
   */
  public static int boundBetween(int min, int value, int max){
    return value < min ? min : value > max ? max : value;
  }

  /**
   * Returns the value, but bounded between maximum or minimum.
   * @param min Minimal returned value.
   * @param value Returned value if between min and max.
   * @param max Maximal returned value.
   * @return Returned value, if between min-max, or min/max.
   * Function returns middle value, if it fits into min..max interval. If value is
   * lower then minimum, minimum is returned. If value is higher than maximum,
   * maximum is returned. Function does not check and expects that min @lt;=max,
   * otherwise unexpected behavior occurs.
   */
  public static double boundBetween(double min, double value, double max){
    return value < min ? min : value > max ? max : value;
  }
}
