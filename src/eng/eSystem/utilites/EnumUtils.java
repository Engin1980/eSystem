package eng.eSystem.utilites;

public class EnumUtils {
  public static <T extends Enum> boolean is(T currentValue, T[] values){
    for (T value : values) {
      if (value == currentValue)
        return true;
    }
    return false;
  }
}
