package eng.eSystem.utilites;

/**
 * This class contains methods used to extend the behavior of Exceptions.
 *
 * @author Marek Vajgl
 */
public class ExceptionUtils {
  /**
   * Returns long exception string including type and all exception causes.
   *
   * @param t The exception to be printed
   * @return The full exception text message
   */
  public static String toFullString(Throwable t) {
    String ret = toFullString(t, "==>");
    return ret;
  }

  /**
   * Returns long exception string including type and all exception causes.
   *
   * @param t         The exception to be printed
   * @param separator The string used as separator between messages.
   * @return The full exception text message
   */
  public static String toFullString(Throwable t, String separator) {
    StringBuilder sb = new StringBuilder();

    Throwable tt = t;
    while (tt != null) {
      if (t != tt) {
        sb.append(separator);
      }
      sb.append(String.format("[%s]:: %s (%s)",
              tt.getClass().getSimpleName(),
              tt.getMessage(),
              tt.getStackTrace()[0].toString()));
      tt = tt.getCause();
    }

    return sb.toString();
  }

  /**
   * Returns multiline string including all exception and stack trace data.
   * @param t Exception
   * @return Multiline string
   */
  public static String toFullStringWithStackTrace(Throwable t) {
    StringBuilder sb = new StringBuilder();
    int indent = 0;
    while (t != null) {
      sb.append(StringUtils.repeat("\t", indent))
              .append(String.format("[%s]::%s%n", t.getClass().getName(), t.getMessage()));
      for (StackTraceElement ste : t.getStackTrace()) {
        sb.append(StringUtils.repeat("\t", indent))
                .append("  ")
                .append(ste.toString());
      }
      indent++;
      t = t.getCause();
    }
    return sb.toString();
  }
}
