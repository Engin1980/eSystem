package eng.eSystem.utilites;

import org.jetbrains.annotations.Nullable;

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
