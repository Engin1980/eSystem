package eng.eSystem.functionalInterfaces;

public interface Function2<T1, T2, TResult> {
  TResult apply(T1 a, T2 b);
}
