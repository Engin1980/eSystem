package eng.eSystem.geometry2D;

public class Line {

  public enum eSide {
    onLine,
    left,
    right
  }

  private final double a;
  private final double b;
  private final double c;

  public Line(Point a, Point b, boolean forceDirectionLeftToRight) {
    this(a.x, a.y, b.x, b.y, forceDirectionLeftToRight);
  }

  public Line(double ax, double ay, double bx, double by, boolean forceDirectionLeftToRight) {
    if (forceDirectionLeftToRight && ax > bx){
      double tmp;
      tmp = ax;
      ax = bx;
      bx = tmp;
      tmp = ay;
      ay = by;
      by = tmp;
    }
    double ux = ax - bx;
    double uy = ay - by;
    this.a = -uy;
    this.b = ux;
    this.c = -this.a * ax - this.b * ay;
  }

  public Line(Point a, Point b) {
    this(a.x, a.y, b.x, b.y, false);
  }

  public Line(double ax, double ay, double bx, double by) {
    this(ax,ay,bx,by,false);
  }

  public double getDistance(Point p) {
    return getDistance(p.x, p.y);
  }

  public double getDistance(double px, double py) {
    // z https://matematika.cz/vzdalenost-bod-primka
    double ret = Math.abs(this.a * px + this.b * py + this.c) / Math.sqrt(this.a * this.a + this.b * this.b);
    return ret;
  }

  public eSide getRelativeLocation(Point p) {
    return getRelativeLocation(p.x, p.y);
  }

  public eSide getRelativeLocation(double px, double py) {
    eSide ret;
    double result = a * px + b * py + c;
    if (result == 0)
      ret = eSide.onLine;
    else if (result < 0)
      ret = eSide.left;
    else
      ret = eSide.right;
    return ret;
  }
}
