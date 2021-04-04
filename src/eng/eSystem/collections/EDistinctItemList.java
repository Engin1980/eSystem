package eng.eSystem.collections;

public class EDistinctItemList<T extends EDistinctItemList.IDistinctItem<?>> extends EDistinctList<T> {

  public interface IDistinctItem<T> {
    T getValue();
  }

  public EDistinctItemList(Behavior onDuplicitBehavior) {
    super(q -> q.getValue(), onDuplicitBehavior);
  }

  public EDistinctItemList() {
    this(Behavior.exception);
  }
}
