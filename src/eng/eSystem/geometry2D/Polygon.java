package eng.eSystem.geometry2D;

import eng.eSystem.Tuple;
import eng.eSystem.collections.EList;
import eng.eSystem.collections.IList;
import eng.eSystem.geo.Coordinate;
import eng.eSystem.utilites.NumberUtils;
import eng.eSystem.validation.EAssert;
import eng.eSystem.validation.EAssertException;

public class Polygon {
  private IList<Point> points = new EList<>();
  private final double minX;
  private final double maxX;
  private final double minY;
  private final double maxY;

  public Polygon(IList<Point> points) {
    this.points.addMany(points);
    minX = points.minDouble(q -> q.x).orElseThrow();
    maxX = points.maxDouble(q -> q.x).orElseThrow();
    minY = points.minDouble(q -> q.y).orElseThrow();
    maxY = points.maxDouble(q -> q.y).orElseThrow();
  }

  public Polygon(Point... points) {
    this(EList.of(points));
  }

  public int getLinesCount() {
    return points.size();
  }

  public LineSegment getLineSegment(int index) {
    EAssert.isTrue(index >= 0, "Index must be greater or equal to zero.");
    EAssert.isTrue(index < getLinesCount(), "Index must be lower than lines count.");
    if (index < getLinesCount() - 1) {
      return new LineSegment(points.get(index), points.get(index + 1));
    } else {
      return new LineSegment(points.getLast(), points.getFirst());
    }

  }

  public boolean isInside(Point c) {
    boolean ret;

    ret = NumberUtils.isBetweenOrEqual(minX, c.x, maxX);
    if (ret)
      ret = NumberUtils.isBetweenOrEqual(minY, c.y, maxY);
    if (ret) {
      int hit = 0;
      for (int i = 0; i < getLinesCount(); i++) {
        LineSegment line = getLineSegment(i);
        if (NumberUtils.isInRange(line.a.y, c.y, line.b.y) == false) {
          // line is out of range on Y-axis (above or below)
          continue;
        }
        if (Math.max(line.a.x, line.b.x) < c.x) {
          // line is on the left from the point
          continue;
        }
        if (Math.min(line.a.x, line.b.x) > c.x) {
          // line is on the right from the point
          hit++;
        } else {
          Line l = new Line(line.a, line.b);
          Line.eSide relativePosition = l.getRelativeLocation(c);
          if (line.a.y < line.b.y && relativePosition == Line.eSide.left)
            hit++;
          else if (line.a.y > line.b.y && relativePosition == Line.eSide.right)
            hit++;
//          double a = (line.b.y - line.a.y) / (line.b.x - line.a.x);
//          double b = line.a.y - a * line.a.x;
//          double p = a * c.x + b;
//          double diff = c.y - p;
//          if (a >= 0 && diff > 0)
//            hit++;
//          else if (a < 0 && diff < 0)
//            hit++;
        }
      }
      ret = (hit % 2 == 1);
    }
    return ret;
  }
}
