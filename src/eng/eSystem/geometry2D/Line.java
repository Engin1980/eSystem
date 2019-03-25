package eng.eSystem.geometry2D;

public class Line {
  private final double a;
  private final double b;
  private final double c;

  public Line(Point a, Point b) {
    double ux = a.x - b.x;
    double uy = a.y - b.y;
    this.a = -uy;
    this.b = ux;
    this.c = -this.a * a.x - this.b * a.y;
  }


  public double getDistance(Point p) {
    // z https://matematika.cz/vzdalenost-bod-primka
    double ret = Math.abs(this.a * p.x + this.b * p.y + this.c) / Math.sqrt(this.a * this.a + this.b * this.b);
    return ret;
  }
}
