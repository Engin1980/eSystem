package eng.eSystem.collections;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class Miscelaneous {

  @Test
  public void ToArrayTest(){
    IList<Integer> lst = new EList<>();
    for (int i = 0; i < 3; i++) {
      lst.add(i);
    }

    Integer[] exp = new Integer[]{0,1,2};
    Integer[] act = lst.toArray(Integer.class);

    assertArrayEquals(exp,act);
  }
}
