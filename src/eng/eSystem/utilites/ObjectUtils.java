package eng.eSystem.utilites;

import java.util.Comparator;

public class ObjectUtils {

  /**
   * Orders classes by their inheritance.
   * <p>
   * It is based on Class.isAssignableFrom() method.
   */
  public static class TypeOrderComparer implements Comparator<Class<?>> {

    public enum Direction {
      ascending,
      descending;
    }

    private final Direction direction;

    public TypeOrderComparer(Direction direction) {
      this.direction = direction;
    }

    public TypeOrderComparer() {
      this(Direction.ascending);
    }

    @Override
    public int compare(Class<?> a, Class<?> b) {
      int ret;
      if (a.equals(b))
        ret = 0;
      else if (a.isAssignableFrom(b))
        ret = -1;
      else
        ret = 1;
      if (direction == Direction.descending)
        ret = -ret;
      return ret;
    }
  }

  public static boolean equals(Object a, Object b) {
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
