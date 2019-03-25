package eng.eSystem.geometry2D;

public class Point {
  public final double x;
  public final double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return String.format("(%s;%s)", x, y);
  }
}
