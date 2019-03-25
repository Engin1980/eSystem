package eng.eSystem.geometry2D;

import org.junit.Test;

import static org.junit.Assert.*;

public class LineTest {

  @Test
  public void getDistanceA() {
    Line line = new Line(
        new Point(1, 1),
        new Point(3, 3));
    Point point = new Point(2, 2);

    System.out.println(point);

    double exp = 0;
    double act = line.getDistance(point);
    assertEquals(exp, act, 0);
  }

  @Test
  public void getDistanceB() {
    Line line = new Line(
        new Point(1, 1),
        new Point(3, 1));
    Point point = new Point(7, 8);

    double exp = 7;
    double act = line.getDistance(point);
    assertEquals(exp, act, 0);
  }
}