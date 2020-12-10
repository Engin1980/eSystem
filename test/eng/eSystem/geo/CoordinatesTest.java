package eng.eSystem.geo;

import org.junit.Test;
import static org.junit.Assert.*;

public class CoordinatesTest {

  @Test
  public void getIntersection() {
    Coordinate a = new Coordinate(49.70713, 18.12845);
    double radialA = 44;
    Coordinate b = new Coordinate(49.71637, 18.27706);
    double radialB = 314;

    Coordinate exp = new Coordinate(49.75972, 18.207);
    Coordinate act = Coordinates.getIntersection(a, radialA, b, radialB);

    assertEquals(exp.getLatitude().get(), act.getLatitude().get(), 0.001);
    assertEquals(exp.getLongitude().get(), act.getLongitude().get(), 0.001);
  }
}
