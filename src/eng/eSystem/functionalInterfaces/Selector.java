package eng.eSystem.functionalInterfaces;

public interface Selector<T1, TResult> {
  TResult invoke(T1 a);
}
