package eng.eSystem.collections;

public class EDistinctItemList<T extends IDistinctItem<?>> extends EDistinctList<T>   {

  public EDistinctItemList(Behavior onDuplicitBehavior) {
    super(q->q.getValue(), onDuplicitBehavior);
  }

  public EDistinctItemList(){
    this(Behavior.exception);
  }
}
