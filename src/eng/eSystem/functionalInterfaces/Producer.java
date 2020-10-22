package eng.eSystem.functionalInterfaces;

public interface Producer<TResult> {
  TResult invoke();
}
