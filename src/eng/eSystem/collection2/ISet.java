package eng.eSystem.collection2;

import eng.eSystem.collection2.subinterfaces.IEditableCollection;

public interface ISet<T> extends IReadOnlySet<T>, IEditableCollection<T> {
}
