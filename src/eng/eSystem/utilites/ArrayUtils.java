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
}
