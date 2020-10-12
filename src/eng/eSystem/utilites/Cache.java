package eng.eSystem.utilites;

import eng.eSystem.EMath;
import eng.eSystem.collections.EMap;
import eng.eSystem.collections.IMap;
import eng.eSystem.functionalInterfaces.Producer;
import eng.eSystem.functionalInterfaces.Selector;
import eng.eSystem.validation.EAssert;

public class Cache<Tkey, Ttype> {
  private final Selector<Tkey, Ttype> instanceProducer;

  private final IMap<Tkey, Ttype> cache = new EMap<>();

  public Cache(Selector<Tkey, Ttype> instanceProducer) {
    EAssert.Argument.isNotNull(instanceProducer, "instanceProducer");
    this.instanceProducer = instanceProducer;
  }

  public Ttype get(Tkey key) {
    if (!cache.containsKey(key))
      obtainInstanceForKey(key);
    return cache.get(key);
  }

  public void clear() {
    this.cache.clear();
  }

  protected void obtainInstanceForKey(Tkey key) {
    Ttype instance = instanceProducer.getValue(key);
    this.cache.set(key, instance);
  }
}
