package eng.eSystem.collections.subinterfaces;

import eng.eSystem.functionalInterfaces.Selector;

import java.util.function.Predicate;

public interface IReadOnlyCollection<Tin, Titem, Tout> {
  /**
   * Select every item from the current collection only once
   *
   * @param selector Selector defining value using to evaluate equality
   * @param <K>      Type of value used to compare equality
   * @return Returns collection of the same type interface type as the current one, with every item only once.
   */
  <K> Tout distinct(Selector<Titem, K> selector);

  /**
   * Returns intersection of this collection with other one.
   *
   * @param otherCollection The other collection
   * @return Intersection of this and other collection.
   */
  Tout intersection(Tin otherCollection);

  /**
   * Returns copy of this collection without items in <code>otherCollection</code>
   *
   * @param otherCollection
   * @return New collection with items of this collection without items in parameter collection.
   */
  Tout minus(Tin otherCollection);

  /**
   * Returns union of this collection with other one.
   *
   * @param otherCollection The other collection
   * @return Union of this and other collection.
   */
  Tout union(Tin otherCollection);

  /**
   * Returns items of this collection matching predicate.
   *
   * @param predicate Predicate to match
   * @return New collection containing items matching predicate
   */
  Tout where(Predicate<Titem> predicate);

}
