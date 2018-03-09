/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eng.eSystem.utilites;

import java.lang.Exception;
import java.lang.String;

/**
 *
 * @author Marek Vajgl
 */
public class ParseUtil {

  public static int toInt(String value) {
    return Integer.parseInt(value);
  }

  public static Integer tryToInt(String value) {
    Integer ret;
    try {
      ret = toInt(value);
    } catch (Exception ex) {
      ret = null;
    }
    return ret;
  }

  public static boolean toBoolean(String value) {
    boolean ret;
    value = value.toLowerCase();
    ret = value.equals("true") || value.equals("t");
    if (!ret) {
      Integer i = tryToInt(value);
      if (i != null) {
        ret = i != 0;
      }
    }

    return ret;
  }

  public static Boolean tryToBoolean(String value){
    Boolean ret;
    try{
      ret = toBoolean(value);
    } catch (Exception ex){
      ret = null;
    }
    return ret;
  }
}
