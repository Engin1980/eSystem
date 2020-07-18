/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eng.eSystem.utilites;

import org.jetbrains.annotations.NotNull;

/**
 * This class contains methods used to extend the behavior of Exceptions.
 *
 * @author Marek Vajgl
 */
public class ExceptionUtils {
  /**
   * Prints long exception string including type and all exception causes.
   *
   * @param t The exception to be printed
   * @return The full exception text message
   */
  public static String toFullString(@NotNull Throwable t) {
    String ret = toFullString(t, "==>");
    return ret;
  }

  /**
   * Prints long exception string including type and all exception causes.
   *
   * @param t         The exception to be printed
   * @param separator The string used as separator between messages.
   * @return The full exception text message
   */
  public static String toFullString(@NotNull Throwable t, @NotNull String separator) {
    StringBuilder sb = new StringBuilder();

    Throwable tt = t;
    while (tt != null) {
      if (t != tt) {
        sb.append(separator);
      }
      sb.append("[");
      sb.append(tt.getClass().getSimpleName());
      sb.append("]:: ");
      sb.append(tt.getMessage());

      tt = tt.getCause();
    }

    return sb.toString();
  }
}
