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
}
