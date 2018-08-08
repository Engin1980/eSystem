/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eng.eSystem.utilites;

import java.util.function.Predicate;

/**
 *
 * @author Marek Vajgl
 */
public class ArrayUtils {

  /**
   * Gets random element from the array. Array cannot be empty.
   * @param array Non-empty non-null array
   * @param <T> Type of array item
   * @return Random element from the array
   */
  public static <T> T getRandom(T[] array){
    
    if (array == null) {
      throw new IllegalArgumentException("Argument \"array\" cannot be null.");
    }
    if (array.length == 0) {
      throw new IllegalArgumentException("Argument \"array\" cannot be empty.");
    }

    int rnd = (int) (Math.random() * array.length);
    T ret = array[rnd];
    return ret;

  }

  public static <T> boolean contains(T[] array, T element){
    boolean ret = false;
    for (T t : array) {
      if (t.equals(element)){
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(char[] array, char element){
    boolean ret = false;
    for (char t : array) {
      if (t == element){
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(boolean[] array, boolean element){
    boolean ret = false;
    for (boolean t : array) {
      if (t == element){
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(byte[] array, byte element){
    boolean ret = false;
    for (byte t : array) {
      if (t == element){
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(short[] array, short element){
    boolean ret = false;
    for (short t : array) {
      if (t == element){
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(int[] array, int element){
    boolean ret = false;
    for (int t : array) {
      if (t == element){
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(long[] array, long element){
    boolean ret = false;
    for (long t : array) {
      if (t == element){
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(float[] array, float element){
    boolean ret = false;
    for (float t : array) {
      if (t == element){
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(double[] array, double element){
    boolean ret = false;
    for (double t : array) {
      if (t == element){
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static <T> boolean contains(T[] array, Predicate<T> predicate){
    boolean ret = false;
    for (T t : array) {
      if (predicate.test(t)){
        ret = true;
        break;
      }
    }
    return ret;
  }
}
