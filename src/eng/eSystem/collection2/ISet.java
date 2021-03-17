package eng.eSystem.collection2;

import eng.eSystem.collection2.subinterfaces.IEditableCollection;

public interface ISet<T> extends IReadOnlySet<T>, IEditableCollection<T> {

  default ISet<T> with(T... elements) {
    this.addMany(elements);
    return this;
  }

  default ISet<T> with(Iterable<T> items) {
    this.addMany(items);
    return this;
  }

}
