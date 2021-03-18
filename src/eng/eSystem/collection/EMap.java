package eng.eSystem.collection;

import java.util.HashMap;

public class EMap<K, V> extends EAbstractMap<K, V> {
  private final static Class DEFAULT_CLASS = HashMap.class;


  public EMap() {
    this(DEFAULT_CLASS);
  }

  public EMap(Class<? extends java.util.Map> innerType) {
    super((java.util.Map) Common.provideInstance(innerType));
  }
}
