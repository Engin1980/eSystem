package eng.eSystem.collections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EListTest {

  @Test
  public void ctor() {
    IList<Integer> lst = new EList<>();
  }

  @Test
  public void remove_byPredicate() {
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

    act.remove(q -> q % 2 == 0);

    assertEquals(exp.size(), act.size());
    for (int i = 0; i < exp.size(); i++) {
      assertEquals(exp.get(i), act.get(i));
    }
  }

  @Test
  public void slice() {
    IList<String> lst = new EList<>();
    lst.add("1");
    lst.add("2");
    lst.add("3");
    lst.add("4");
    lst.add("5");
    lst.add("6");
    lst.add("7");

    lst.slice(0, 2, 3, 5, 6, 7);
    assertEquals(2, lst.size());
    assertEquals("2", lst.get(0));
    assertEquals("5", lst.get(1));
  }

  @Test
  public void sort() {
    IList<Integer> lst = new EList<>();
    lst.add(5);
    lst.add(2);
    lst.add(4);
    lst.add(3);
    lst.add(5);
    lst.add(1);
    lst.add(2);

    lst.sort(q -> q);

    assertEquals(7, lst.size());
    int prev = lst.get(0);
    for (int i = 1; i < lst.size(); i++) {
      int cur = lst.get(i);
      if (prev > cur)
        fail("Not sorted");
      else
        prev = cur;
    }
  }

  @Test
  public void where() {
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

    IList<Integer> act = lst.where(q -> q % 2 == 1);

    assertEquals(exp.size(), act.size());
    for (int i = 0; i < exp.size(); i++) {
      assertEquals(exp.get(i), act.get(i));
    }
  }

}