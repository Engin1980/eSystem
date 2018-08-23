package eng.eSystem;

public class EMath {
  public static int max(int ... values){
    if (values.length == 0)
      throw new IllegalArgumentException("There must be at least one input value.");

    int ret = values[0];
    for (int i = 1; i < values.length; i++) {
      if (ret < values[i])
        ret = values[i];
    }
    return ret;
  }

  public static double max(double ... values){
    if (values.length == 0)
      throw new IllegalArgumentException("There must be at least one input value.");

    double ret = values[0];
    for (int i = 1; i < values.length; i++) {
      if (ret < values[i])
        ret = values[i];
    }
    return ret;
  }

  public static int min(int ... values){
    if (values.length == 0)
      throw new IllegalArgumentException("There must be at least one input value.");

    int ret = values[0];
    for (int i = 1; i < values.length; i++) {
      if (ret > values[i])
        ret = values[i];
    }
    return ret;
  }

  public static double min(double ... values){
    if (values.length == 0)
      throw new IllegalArgumentException("There must be at least one input value.");

    double ret = values[0];
    for (int i = 1; i < values.length; i++) {
      if (ret > values[i])
        ret = values[i];
    }
    return ret;
  }

  /**
   * Rounds the double value to zero. If the number is greater than 0, its floor is taken. If the number is lower than 0, its ceil is taken.
   * @param value
   * @return
   */
  public static int roundToZero(double value) {
    if (value > 0) {
      return (int) Math.floor(value);
    } else {
      return (int) Math.ceil(value);
    }
  }
}
