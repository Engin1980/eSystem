package eng.eSystem.collections;

import org.junit.Test;
import static org.junit.Assert.*;

public class EListTest {

  @Test
  public void ctor(){
    IList<Integer> lst = new EList<>();
  }

  @Test
  public void where(){
    IList<Integer> lst = new EList<>();
    for (int i = 0; i < 10; i++) {
      lst.add(i);
    }

    IList<Integer> exp = new EList<>();
    exp.add(1);
    exp.add(3);
    exp.add(5);
    exp.add(7);
    exp.add(9);

    IList<Integer> act = lst.where(q->q % 2 == 1);

    assertEquals(exp.size(), act.size());
    for (int i = 0; i < exp.size(); i++) {
      assertEquals(exp.get(i), act.get(i) );
    }
  }

  @Test
  public void remove_byPredicate(){
    IList<Integer> act = new EList<>();
    for (int i = 0; i < 10; i++) {
      act.add(i);
    }

    IList<Integer> exp = new EList<>();
    exp.add(1);
    exp.add(3);
    exp.add(5);
    exp.add(7);
    exp.add(9);

    act.remove(q->q % 2 == 0);

    assertEquals(exp.size(), act.size());
    for (int i = 0; i < exp.size(); i++) {
      assertEquals(exp.get(i), act.get(i) );
    }
  }



}