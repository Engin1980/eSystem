package eng.eSystem.functionalInterfaces;

public interface Function <T1, TResult> {
  TResult apply(T1 a);
}
