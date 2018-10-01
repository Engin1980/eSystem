package eng.eSystem.geo;

import eng.eSystem.exceptions.EEnumValueUnsupportedException;
import eng.eSystem.utilites.UnitUtils;

public class Coordinates {
  public enum eHeadingToRadialBehavior {
    gentle,
    standard,
    aggresive
  }
  private final static double R = 6371;

  /**
   * Returns new Coordinate based on initial point, distance
   * and heading.
   *
   * @param coordinate   Original point
   * @param heading      Heading
   * @param distanceInNM Distance in nauctional miles
   * @return New point
   */
  public static Coordinate getCoordinate(Coordinate coordinate, double heading, double distanceInNM) {
    double lat = toRadians(coordinate.getLatitude().get());
    double lon = toRadians(coordinate.getLongitude().get());
    double bear = toRadians(heading);
    double d = UnitUtils.Distance.nmToKm(distanceInNM);
    double f = d / R;

    double rLat = Math.asin(
        Math.sin(lat) * Math.cos(f) + Math.cos(lat) * Math.sin(f) * Math.cos(bear));
    double rLon
        = lon + Math.atan2(Math.sin(bear) * Math.sin(f) * Math.cos(lat), Math.cos(f) - Math.sin(lat) * Math.sin(rLat));

    rLat = toDegrees(rLat);
    rLon = toDegrees(rLon);

    Coordinate ret = new Coordinate(rLat, rLon);
    return ret;
  }

  /**
   * Returns distance between two points
   *
   * @param a First point
   * @param b Second point
   * @return Distance in nauctional miles
   */
  public static double getDistanceInNM(Coordinate a, Coordinate b) {
    double s1 = a.getLatitude().get();
    s1 = toRadians(s1);
    double s2 = b.getLatitude().get();
    s2 = toRadians(s2);
    double ds = b.getLatitude().get() - a.getLatitude().get();
    ds = toRadians(ds);
    double dl = b.getLongitude().get() - a.getLongitude().get();
    dl = toRadians(dl);

    double aa = Math.sin(ds / 2) * Math.sin(ds / 2)
        + Math.cos(s1) * Math.cos(s2)
        * Math.sin(dl / 2) * Math.sin(dl / 2);
    double cc = 2 * Math.atan2(Math.sqrt(aa), Math.sqrt(1 - aa));
    double ret = R * cc;
    ret = UnitUtils.Distance.kmToNM(ret);

    return ret;
  }

  /**
   * Returns initial bearing
   *
   * @param from Initial point
   * @param to   Target point
   * @return Bearing in degrees
   */
  public static double getBearing(Coordinate from, Coordinate to) {
    double dLon = to.getLongitude().get() - from.getLongitude().get();
    dLon = toRadians(dLon);

    double lat2 = toRadians(to.getLatitude().get());
    double lat1 = toRadians(from.getLatitude().get());

    double x = Math.sin(dLon) * Math.cos(lat2);
    double y = Math.cos(lat1) * Math.sin(lat2)
        - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);
    double ret = Math.atan2(x, y);
    ret = toDegrees(ret);
    ret = (ret + 360) % 360;
    return ret;
  }

  //  private static final double RADIAL_APPROACH_MULTIPLIER = 7;
//  private static final double RADIAL_MAX_DIFF = 45; //30;
  public static double getHeadingToRadial(Coordinate current, Coordinate target, double radialToTarget, eHeadingToRadialBehavior behavior) {
    double RADIAL_APPROACH_MULTIPLIER;
    double RADIAL_MAX_DIFF;
    switch (behavior) {
      case gentle:
        RADIAL_APPROACH_MULTIPLIER = 4;
        RADIAL_MAX_DIFF = 15;
        break;
      case standard:
        RADIAL_APPROACH_MULTIPLIER = 4;
        RADIAL_MAX_DIFF = 30;
        break;
      case aggresive:
        RADIAL_APPROACH_MULTIPLIER = 7;
        RADIAL_MAX_DIFF = 45;
        break;
      default:
        throw new EEnumValueUnsupportedException(behavior);
    }

    double heading = Coordinates.getBearing(current, target);
    double diff = Headings.subtract(heading, radialToTarget);
    diff *= RADIAL_APPROACH_MULTIPLIER;
    if (Math.abs(diff) > RADIAL_MAX_DIFF) {
      diff = Math.signum(diff) * RADIAL_MAX_DIFF;
    }

    double ret = radialToTarget + diff;
    ret = Headings.to(ret);
    return ret;
  }

  private static double toRadians(double degrees) {
    return degrees * Math.PI / 180;
  }

  private static double toDegrees(double radians) {
    return radians * 180 / Math.PI;
  }

  private Coordinates() {
  }

}
