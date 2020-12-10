package eng.eSystem.geo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

  @Test
  public void getIntersection2() {
    Coordinate a = new Coordinate(49.7069100291, 18.1279470026);
    double ra = 44;
    Coordinate b = new Coordinate(49.7210063785, 18.2465650141);
    double rb = 314;

    Coordinate exp = new Coordinate(49.7519604117 , 18.1960965693);
    Coordinate act = Coordinates.getIntersection(a, ra, b, rb);

    assertEquals(exp.getLatitude().get(), act.getLatitude().get(), 0.001);
    assertEquals(exp.getLongitude().get(), act.getLongitude().get(), 0.001);
  }

  @Test
  public void getIntersection2Opposite() {
    Coordinate a = new Coordinate(49.7069100291, 18.1279470026);
    double ra = 224;
    Coordinate b = new Coordinate(49.6262472868, 18.1035710871);
    double rb = 314;

    Coordinate exp = new Coordinate(49.6577062458 , 18.0537892878);
    Coordinate act = Coordinates.getIntersection(a, ra, b, rb);

    assertEquals(exp.getLatitude().get(), act.getLatitude().get(), 0.001);
    assertEquals(exp.getLongitude().get(), act.getLongitude().get(), 0.001);
  }

  @Test
  public void getDistanceToRadialUpRight(){
    Coordinate a = new Coordinate(49.7069100291, 18.1279470026);
    double ra = 44;
    Coordinate b = new Coordinate(49.7210063785, 18.2465650141);

    double exp = 5;
    double act = Coordinates.getDistanceToRadialInKm(b, a, ra);

    assertEquals(exp, act, 0.05);
  }

  @Test
  public void getDistanceToRadialUpRightOpposite(){
    Coordinate a = new Coordinate(49.7069100291, 18.1279470026);
    double ra = 224;
    Coordinate b = new Coordinate(49.7210063785, 18.2465650141);

    double exp = 5;
    double act = Coordinates.getDistanceToRadialInKm(b, a, ra);

    assertEquals(exp, act, 0.05);
  }

  @Test
  public void getDistanceToRadialUpLeft(){
    Coordinate a = new Coordinate(49.7069100291, 18.1279470026);
    double ra = 44;
    Coordinate b = new Coordinate(49.7832269222, 18.1459714472);

    double exp = 5;
    double act = Coordinates.getDistanceToRadialInKm(b, a, ra);

    assertEquals(exp, act, 0.05);
  }

  @Test
  public void getDistanceToRadialUpLeftOpposite(){
    Coordinate a = new Coordinate(49.7069100291, 18.1279470026);
    double ra = 224;
    Coordinate b = new Coordinate(49.7832269222, 18.1459714472);

    double exp = 5;
    double act = Coordinates.getDistanceToRadialInKm(b, a, ra);

    assertEquals(exp, act, 0.05);
  }

  @Test
  public void getDistanceToRadialDownRight(){
    Coordinate a = new Coordinate(49.7069100291, 18.1279470026);
    double ra = 44;
    Coordinate b = new Coordinate(49.6262472868, 18.1035710871);

    double exp = 5;
    double act = Coordinates.getDistanceToRadialInKm(b, a, ra);

    assertEquals(exp, act, 0.05);
  }

  @Test
  public void getDistanceToRadialDownRightOpposite(){
    Coordinate a = new Coordinate(49.7069100291, 18.1279470026);
    double ra = 224;
    Coordinate b = new Coordinate(49.6262472868, 18.1035710871);

    double exp = 5;
    double act = Coordinates.getDistanceToRadialInKm(b, a, ra);

    assertEquals(exp, act, 0.05);
  }

  @Test
  public void getDistanceToRadialDownLeft(){
    Coordinate a = new Coordinate(49.7069100291, 18.1279470026);
    double ra = 44;
    Coordinate b = new Coordinate(49.6890337765, 18.0038358271);

    double exp = 5;
    double act = Coordinates.getDistanceToRadialInKm(b, a, ra);

    assertEquals(exp, act, 0.05);
  }

  @Test
  public void getDistanceToRadialDownLeftOpposite(){
    Coordinate a = new Coordinate(49.7069100291, 18.1279470026);
    double ra = 224;
    Coordinate b = new Coordinate(49.6890337765, 18.0038358271);

    double exp = 5;
    double act = Coordinates.getDistanceToRadialInKm(b, a, ra);

    assertEquals(exp, act, 0.05);
  }
}
