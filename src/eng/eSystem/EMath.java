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

  public static Integer min(Integer ... values){
    if (values.length == 0)
      throw new IllegalArgumentException("There must be at least one input value.");

    Integer ret = values[0];
    for (int i = 1; i < values.length; i++) {
      Integer val = values[i];
      if (val == null) continue;
      if (ret == null)
        ret = val;
      else if (val < ret)
        ret = val;
    }
    return ret;
  }

  public static Double min(Double ... values){
    if (values.length == 0)
      throw new IllegalArgumentException("There must be at least one input value.");

    Double ret = values[0];
    for (int i = 1; i < values.length; i++) {
      Double val = values[i];
      if (val == null) continue;
      if (ret == null)
        ret = val;
      else if (val < ret)
        ret = val;
    }
    return ret;
  }

  public static Long min(Long ... values){
    if (values.length == 0)
      throw new IllegalArgumentException("There must be at least one input value.");

    Long ret = values[0];
    for (int i = 1; i < values.length; i++) {
      Long val = values[i];
      if (val == null) continue;
      if (ret == null)
        ret = val;
      else if (val < ret)
        ret = val;
    }
    return ret;
  }

  public static Integer max(Integer ... values){
    if (values.length == 0)
      throw new IllegalArgumentException("There must be at least one input value.");

    Integer ret = values[0];
    for (int i = 1; i < values.length; i++) {
      Integer val = values[i];
      if (val == null) continue;
      if (ret == null)
        ret = val;
      else if (val > ret)
        ret = val;
    }
    return ret;
  }

  public static Double max(Double ... values){
    if (values.length == 0)
      throw new IllegalArgumentException("There must be at least one input value.");

    Double ret = values[0];
    for (int i = 1; i < values.length; i++) {
      Double val = values[i];
      if (val == null) continue;
      if (ret == null)
        ret = val;
      else if (val > ret)
        ret = val;
    }
    return ret;
  }

  public static Long max(Long ... values){
    if (values.length == 0)
      throw new IllegalArgumentException("There must be at least one input value.");

    Long ret = values[0];
    for (int i = 1; i < values.length; i++) {
      Long val = values[i];
      if (val == null) continue;
      if (ret == null)
        ret = val;
      else if (val > ret)
        ret = val;
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
