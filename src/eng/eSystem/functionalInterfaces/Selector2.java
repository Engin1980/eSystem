package eng.eSystem.functionalInterfaces;

public interface Selector2<T1, T2, TResult> {
  TResult invoke(T1 a, T2 b);
}
