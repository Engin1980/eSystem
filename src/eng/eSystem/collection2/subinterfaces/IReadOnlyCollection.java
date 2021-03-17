package eng.eSystem.collection2.subinterfaces;

import eng.eSystem.functionalInterfaces.Selector;

import java.util.function.Predicate;

public interface IReadOnlyCollection<Tin, Titem, Tout> {
  <K> Tout distinct(Selector<Titem, K> selector);

  Tout intersection(Tin otherList);

  Tout minus(Tin otherList);

  Tout union(Tin otherList);

  Tout where(Predicate<Titem> predicate);
}
