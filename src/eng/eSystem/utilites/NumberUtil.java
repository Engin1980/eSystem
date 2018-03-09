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
public class NumberUtil {
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
   * @param min Minimum value
   * @param value Checked value
   * @param max Maximum value
   * @return True if value is between minimum and maximum, inclusively.
   */
  public static boolean isBetweenOrEqual(double min, double value, double max){
    return min <= value && value <= max;
  }
}
