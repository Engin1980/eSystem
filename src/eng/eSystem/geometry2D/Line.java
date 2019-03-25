package eng.eSystem.geometry2D;

public class Line {
  private final double a;
  private final double b;
  private final double c;

  public Line(Point a, Point b) {
    this(a.x, a.y, b.x, b.y);
  }

  public Line(double ax, double ay, double bx, double by){
    double ux = ax - bx;
    double uy = ay - by;
    this.a = -uy;
    this.b = ux;
    this.c = -this.a * ax - this.b * ay;
  }


  public double getDistance(Point p) {
    // z https://matematika.cz/vzdalenost-bod-primka
    double ret = Math.abs(this.a * p.x + this.b * p.y + this.c) / Math.sqrt(this.a * this.a + this.b * this.b);
    return ret;
  }
}
