package eng.eSystem.collection2;

import java.util.HashMap;
import java.util.Map;

public class EMap<K, V> extends EAbstractMap<K, V> {
  public static <K, V> EMap<K, V> of(Iterable<Map.Entry<K, V>> entries) {
    EMap<K, V> ret = new EMap<>();
    ret.setMany(entries);
    return ret;
  }

  //TODO implement from EMap ver 1
  protected EMap() {
    super(new HashMap<>());
  }
}
