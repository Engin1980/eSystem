/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eng.eSystem.utilites;

import eng.eSystem.functionalInterfaces.Predicate;

/**
 * @author Marek Vajgl
 */
public class ArrayUtils {

  public static <T> int indexOf(T[] array, Predicate<T> predicate){
    for (int i = 0; i < array.length; i++) {
      if (predicate.invoke(array[i]))
        return i;
    }
    return -1;
  }

  public static <T> boolean contains(T[] array, T element) {
    boolean ret = false;
    for (T t : array) {
      if (t.equals(element)) {
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(char[] array, char element) {
    boolean ret = false;
    for (char t : array) {
      if (t == element) {
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(boolean[] array, boolean element) {
    boolean ret = false;
    for (boolean t : array) {
      if (t == element) {
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(byte[] array, byte element) {
    boolean ret = false;
    for (byte t : array) {
      if (t == element) {
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(short[] array, short element) {
    boolean ret = false;
    for (short t : array) {
      if (t == element) {
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(int[] array, int element) {
    boolean ret = false;
    for (int t : array) {
      if (t == element) {
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(long[] array, long element) {
    boolean ret = false;
    for (long t : array) {
      if (t == element) {
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(float[] array, float element) {
    boolean ret = false;
    for (float t : array) {
      if (t == element) {
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static boolean contains(double[] array, double element) {
    boolean ret = false;
    for (double t : array) {
      if (t == element) {
        ret = true;
        break;
      }
    }
    return ret;
  }

  public static <T> boolean contains(T[] array, Predicate<T> predicate) {
    boolean ret = false;
    for (T t : array) {
      if (predicate.invoke(t)) {
        ret = true;
        break;
      }
    }
    return ret;
  }

  /**
   * Gets random element from the array. Array cannot be empty.
   *
   * @param array Non-empty non-null array
   * @param <T>   Type of array item
   * @return Random element from the array
   */
  public static <T> T getRandom(T[] array) {

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

  public static double[] toPrimitive(Double[] data) {
    if (data == null) return null;
    double[] ret = new double[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static int[] toPrimitive(Integer[] data) {
    if (data == null) return null;
    int[] ret = new int[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static long[] toPrimitive(Long[] data) {
    if (data == null) return null;
    long[] ret = new long[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static byte[] toPrimitive(Byte[] data) {
    if (data == null) return null;
    byte[] ret = new byte[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static short[] toPrimitive(Short[] data) {
    if (data == null) return null;
    short[] ret = new short[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static float[] toPrimitive(Float[] data) {
    if (data == null) return null;
    float[] ret = new float[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static boolean[] toPrimitive(Boolean[] data) {
    if (data == null) return null;
    boolean[] ret = new boolean[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static char[] toPrimitive(Character[] data) {
    if (data == null) return null;
    char[] ret = new char[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static Double[] toWrapper(double[] data) {
    Double[] ret = new Double[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static Integer[] toWrapper(int[] data) {
    Integer[] ret = new Integer[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static Long[] toWrapper(long[] data) {
    Long[] ret = new Long[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static Byte[] toWrapper(byte[] data) {
    Byte[] ret = new Byte[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static Short[] toWrapper(short[] data) {
    Short[] ret = new Short[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static Float[] toWrapper(float[] data) {
    Float[] ret = new Float[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static Boolean[] toWrapper(boolean[] data) {
    Boolean[] ret = new Boolean[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }

  public static Character[] toWrapper(char[] data) {
    Character[] ret = new Character[data.length];
    for (int i = 0; i < data.length; i++) {
      ret[i] = data[i];
    }
    return ret;
  }
}
