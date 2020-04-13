package eng.eSystem.geometry2D;

import org.junit.Test;

import static org.junit.Assert.*;

public class PolygonTest {

  @Test
  public void isInsideSquareA() {
    Polygon polygon = new Polygon(
        new Point(0, 0),
        new Point(0, 10),
        new Point(20, 10),
        new Point(20, 0));
    Point point = new Point(5, 5);
    boolean expected = true;
    boolean actual = polygon.isInside(point);
    assertEquals(expected, actual);
  }

  @Test
  public void isInsideSquareB() {
    Polygon polygon = new Polygon(
        new Point(0, 0),
        new Point(0, 10),
        new Point(20, 10),
        new Point(20, 0));
    Point point = new Point(20, 20);
    boolean expected = false;
    boolean actual = polygon.isInside(point);
    assertEquals(expected, actual);
  }

  @Test
  public void isInsideMShapeA() {
    Polygon polygon = new Polygon(
        new Point(0, 0),
        new Point(0, 10),
        new Point(10, 0),
        new Point(20, 10),
        new Point(20, 0));
    Point point = new Point(1, 1);
    boolean expected = true;
    boolean actual = polygon.isInside(point);
    assertEquals(expected, actual);
  }

  @Test
  public void isInsideMShapeB() {
    Polygon polygon = new Polygon(
        new Point(0, 0),
        new Point(0, 10),
        new Point(10, 0),
        new Point(20, 10),
        new Point(20, 0));
    Point point = new Point(19, 1);
    boolean expected = true;
    boolean actual = polygon.isInside(point);
    assertEquals(expected, actual);
  }

  @Test
  public void isInsideMShapeC() {
    Polygon polygon = new Polygon(
        new Point(0, 0),
        new Point(0, 10),
        new Point(10, 0),
        new Point(20, 10),
        new Point(20, 0));
    Point point = new Point(10, 8);
    boolean expected = false;
    boolean actual = polygon.isInside(point);
    assertEquals(expected, actual);
  }

  @Test
  public void isInsideMShapeD() {
    Polygon polygon = new Polygon(
        new Point(0, 0),
        new Point(0, 10),
        new Point(10, 0),
        new Point(20, 10),
        new Point(20, 0));
    Point point = new Point(2, 5);
    boolean expected = true;
    boolean actual = polygon.isInside(point);
    assertEquals(expected, actual);
  }
}