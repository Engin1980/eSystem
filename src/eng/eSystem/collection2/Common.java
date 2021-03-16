package eng.eSystem.collection2;

import eng.eSystem.collection2.exceptions.EmptyCollectionException;

import java.util.Random;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

class Common {
  public static final Random rnd = new Random();

  public static void ensureNotEmpty(ICollection collection){
    if (collection.isEmpty())
      throw new EmptyCollectionException();
  }

  public static <T> void ensureIndexRange(IList list, int index) {
    if (index < 0)
      throw new IllegalArgumentException(sf("Index value '%d' must be zero or greater.")), index;
    if (index >= list.size())
      throw new IllegalArgumentException(sf("Index value '%d' must be lower than list size ('%d').", index, list.size()))
  }
}
