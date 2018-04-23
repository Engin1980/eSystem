package eng.eSystem.utilites;

public class FunctionShortcuts {

  private FunctionShortcuts() {
  }

  public static String sf (String format, Object ... params){
    return String.format(format, params);
  }
}
