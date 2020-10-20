package eng.eSystem.utilites;

import eng.eSystem.collections.EMap;
import eng.eSystem.collections.IMap;
import eng.eSystem.functionalInterfaces.Selector;
import eng.eSystem.validation.EAssert;

public class CacheUsingSelector<Tkey, Ttype> {
  private final Selector<Tkey, Ttype> instanceProducer;

  private final IMap<Tkey, Ttype> cache = new EMap<>();

  public CacheUsingSelector(Selector<Tkey, Ttype> instanceProducer) {
    EAssert.Argument.isNotNull(instanceProducer, "instanceProducer");
    this.instanceProducer = instanceProducer;
  }

  public void clear() {
    this.cache.clear();
  }

  public Ttype get(Tkey key) {
    if (!cache.containsKey(key))
      obtainInstanceForKey(key);
    return cache.get(key);
  }

  protected void obtainInstanceForKey(Tkey key) {
    Ttype instance = instanceProducer.select(key);
    this.cache.set(key, instance);
  }
}
