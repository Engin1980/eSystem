package eng.eSystem.geometry2D;

import eng.eSystem.validation.EAssert;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class LineSegment {
  public final Point a;
  public final Point b;

  public LineSegment(Point a, Point b) {
    EAssert.Argument.isNotNull(a, "a");
    EAssert.Argument.isNotNull(b, "b");
    this.a = a;
    this.b = b;
  }

  public Point getA() {
    return a;
  }

  public Point getB() {
    return b;
  }

  @Override
  public String toString() {
    return sf("%s-%s", a.toString(), b.toString());
  }
}
