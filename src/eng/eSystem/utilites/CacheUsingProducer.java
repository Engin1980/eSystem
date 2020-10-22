package eng.eSystem.utilites;

import eng.eSystem.functionalInterfaces.Producer;
import eng.eSystem.validation.EAssert;

public class CacheUsingProducer<T> {
  private final Producer<T> instanceProducer;
  private boolean obtained;
  private T cachedValue = null;

  public CacheUsingProducer(Producer<T> instanceProducer) {
    EAssert.Argument.isNotNull(instanceProducer, "instanceProducer");
    this.instanceProducer = instanceProducer;
    this.obtained = false;
  }

  public void clear() {
    this.cachedValue = null; this.obtained = false;
  }

  public T get() {
    if (!obtained)
      obtainInstance();
    return cachedValue;
  }

  protected void obtainInstance() {
    this.cachedValue = instanceProducer.invoke();
    this.obtained = true;
  }
}
