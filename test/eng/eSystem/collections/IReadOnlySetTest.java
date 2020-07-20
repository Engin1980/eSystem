package eng.eSystem.collections;

import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;

public class IReadOnlySetTest {

  @Test
  public void groupBy() {

    IList<LocalTime> lst = new EList<>();
    lst.add(LocalTime.of(10,10));
    lst.add(LocalTime.of(10,20));
    lst.add(LocalTime.of(10,30));
    lst.add(LocalTime.of(11,10));
    lst.add(LocalTime.of(11,20));
    lst.add(LocalTime.of(12, 20));

    IMap<Integer, IList<LocalTime>> map = lst.groupBy(q->q.getHour());

    assertTrue(map.containsKey(10));
    assertTrue(map.get(10).count() == 3);
    assertTrue(map.containsKey(11));
    assertTrue(map.get(11).count() == 2);
    assertTrue(map.containsKey(12));
    assertTrue(map.get(12).count() == 1);
  }
}