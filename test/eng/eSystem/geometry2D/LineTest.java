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

  @Test
  public void getRelativeLocationA(){
    Line line = new Line(
        new Point(0,0), new Point(10,10));
    Point p = new Point(5,5);
    Line.eSide expected = Line.eSide.onLine;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }

  @Test
  public void getRelativeLocationB(){
    Line line = new Line(
        new Point(0,0), new Point(10,10));
    Point p = new Point(7,5);
    Line.eSide expected = Line.eSide.right;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }
  @Test
  public void getRelativeLocationC(){
    Line line = new Line(
        new Point(0,0), new Point(10,10));
    Point p = new Point(2,5);
    Line.eSide expected = Line.eSide.left;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }

  @Test
  public void getRelativeLocationA2(){
    Line line = new Line(
        new Point(0,0), new Point(10,10));
    Point p = new Point(100,100);
    Line.eSide expected = Line.eSide.onLine;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }

  @Test
  public void getRelativeLocationB2(){
    Line line = new Line(
        new Point(0,0), new Point(10,10));
    Point p = new Point(200,25);
    Line.eSide expected = Line.eSide.right;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }
  @Test
  public void getRelativeLocationC2(){
    Line line = new Line(
        new Point(0,0), new Point(10,10));
    Point p = new Point(-400,-10);
    Line.eSide expected = Line.eSide.left;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }

  @Test
  public void getRelativeLocationAOpo(){
    Line line = new Line(
        new Point(10,10), new Point(0,0));
    Point p = new Point(5,5);
    Line.eSide expected = Line.eSide.onLine;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }

  @Test
  public void getRelativeLocationBOpo(){
    Line line = new Line(
        new Point(10,10), new Point(0,0));
    Point p = new Point(7,5);
    Line.eSide expected = Line.eSide.left;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }
  @Test
  public void getRelativeLocationCOpo(){
    Line line = new Line(
        new Point(10,10), new Point(0,0));
    Point p = new Point(2,5);
    Line.eSide expected = Line.eSide.right;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }

  @Test
  public void getRelativeLocationA2Opo(){
    Line line = new Line(
        new Point(10,10), new Point(0,0));
    Point p = new Point(100,100);
    Line.eSide expected = Line.eSide.onLine;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }

  @Test
  public void getRelativeLocationB2Opo(){
    Line line = new Line(
        new Point(10,10), new Point(0,0));
    Point p = new Point(200,25);
    Line.eSide expected = Line.eSide.left;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }
  @Test
  public void getRelativeLocationC2Opo(){
    Line line = new Line(
        new Point(10,10), new Point(0,0));
    Point p = new Point(-400,-10);
    Line.eSide expected = Line.eSide.right;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }

  @Test
  public void getRelativeLocationANorm(){
    Line line = new Line(
        new Point(10,10), new Point(0,0), true);
    Point p = new Point(5,5);
    Line.eSide expected = Line.eSide.onLine;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }

  @Test
  public void getRelativeLocationBNorm(){
    Line line = new Line(
        new Point(10,10), new Point(0,0), true);
    Point p = new Point(7,5);
    Line.eSide expected = Line.eSide.right;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }
  @Test
  public void getRelativeLocationCNorm(){
    Line line = new Line(
        new Point(10,10), new Point(0,0),true);
    Point p = new Point(2,5);
    Line.eSide expected = Line.eSide.left;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }

  @Test
  public void getRelativeLocationA2Norm(){
    Line line = new Line(
        new Point(10,10), new Point(0,0),true);
    Point p = new Point(100,100);
    Line.eSide expected = Line.eSide.onLine;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }

  @Test
  public void getRelativeLocationB2Norm(){
    Line line = new Line(
        new Point(10,10), new Point(0,0),true);
    Point p = new Point(200,25);
    Line.eSide expected = Line.eSide.right;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }
  @Test
  public void getRelativeLocationC2Norm(){
    Line line = new Line(
        new Point(10,10), new Point(0,0),true);
    Point p = new Point(-400,-10);
    Line.eSide expected = Line.eSide.left;
    Line.eSide actual = line.getRelativeLocation(p);
    assertEquals(expected, actual);
  }
}