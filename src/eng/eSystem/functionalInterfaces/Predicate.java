package eng.eSystem.functionalInterfaces;

public interface Predicate<TInput> extends Selector<TInput, Boolean> {
  default Predicate<TInput> negate(){
    Predicate<TInput> ret = t -> !this.invoke(t);
    return ret;
  }
}
