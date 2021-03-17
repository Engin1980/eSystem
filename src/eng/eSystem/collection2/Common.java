package eng.eSystem.collection2;

import eng.eSystem.collection2.exceptions.EmptyCollectionException;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

class Common {
  public static final Random rnd = new Random();

  public static void ensureNotEmpty(ICollection<?> collection) {
    if (collection.isEmpty())
      throw new EmptyCollectionException();
  }

  public static <T> void ensureIndexRange(IList<?> list, int index) {
    if (index < 0)
      throw new IllegalArgumentException(sf("Index value '%d' must be zero or greater.", index));
    if (index >= list.size())
      throw new IllegalArgumentException(sf("Index value '%d' must be lower than list size ('%d').", index, list.size()));
  }

  public static <T> T provideInstance(Class<T> innerType) {
    T ret;

    try {
      ret = innerType.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException("Unable to create a new instance of " + innerType.getName() + ".", e);
    }

    return ret;
  }
}
