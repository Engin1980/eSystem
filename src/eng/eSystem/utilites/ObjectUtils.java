package eng.eSystem.utilites;

import com.sun.istack.internal.Nullable;

public class ObjectUtils {

  public static boolean equals(@Nullable Object a, @Nullable Object b) {
    boolean ret;
    if (a == null)
      ret = b == null;
    else
      ret = a.equals(b);
    return ret;
  }

  private void ObjectUtils() {
  }
}
