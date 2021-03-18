package eng.eSystem.collection;

import eng.eSystem.collection.subinterfaces.IEditableCollection;

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
