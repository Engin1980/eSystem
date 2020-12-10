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

  public static Coordinate getIntersection(Coordinate pointA, double bearingA, Coordinate pointB, double bearingB) {
    double φ1 = toRadians(pointA.getLatitude().get());
    double λ1 = toRadians(pointA.getLongitude().get());
    double φ2 = toRadians(pointB.getLatitude().get());
    double λ2 = toRadians(pointB.getLongitude().get());
    double Δφ = Math.abs(φ1 - φ2);
    double Δλ = Math.abs(λ1 - λ2);
    double θ13 = toRadians(bearingA);
    double θ23 = toRadians(bearingB);

    double δ12 = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(Δφ / 2), 2) + Math.cos(φ1) * Math.cos(φ2) * Math.pow(Math.sin(Δλ / 2), 2)));
    double θa = Math.acos((Math.sin(φ2) - Math.sin(φ1) * Math.cos(δ12)) / (Math.sin(δ12) * Math.cos(φ1)));
    double θb = Math.acos((Math.sin(φ1) - Math.sin(φ2) * Math.cos(δ12)) / (Math.sin(δ12) * Math.cos(φ2)));

    double θ12, θ21;
    if (Math.sin(λ2 - λ1) > 0) {
      θ12 = θa;
      θ21 = 2 * Math.PI - θb;
    } else {
      θ12 = 2 * Math.PI - θa;
      θ21 = θb;
    }
    double α1 = θ13 - θ12;
    double α2 = θ21 - θ23;

    if (Math.sin(α1) == 0 && Math.sin(α2) == 0)
      throw new IllegalArgumentException("Infinite intersections"); // infinite intersections
    if (Math.sin(α1) * Math.sin(α2) < 0)
      throw new IllegalArgumentException("Ambigous intersection (antipodal?");        // ambiguous intersection (antipodal?)

    double α3 = Math.acos(-Math.cos(α1) * Math.cos(α2) + Math.sin(α1) * Math.sin(α2) * Math.cos(δ12));
    double δ13 = Math.atan2(Math.sin(δ12) * Math.sin(α1) * Math.sin(α2), Math.cos(α2) + Math.cos(α1) * Math.cos(α3));
    double φ3 = Math.asin(Math.sin(φ1) * Math.cos(δ13) + Math.cos(φ1) * Math.sin(δ13) * Math.cos(θ13));
    double Δλ13 = Math.atan2(Math.sin(θ13) * Math.sin(δ13) * Math.cos(φ1), Math.cos(δ13) - Math.sin(φ1) * Math.sin(φ3));
    double λ3 = λ1 + Δλ13;

    Coordinate ret = new Coordinate(toDegrees(φ3), toDegrees(λ3));
    return ret;
  }

//  crossTrackDistanceTo(pathStart, pathBrngEnd, radius=6371e3) {
//    if (!(pathStart instanceof LatLonNvectorSpherical)) throw new TypeError(`invalid pathStart ‘${pathStart}’`);
//    if (!(pathBrngEnd instanceof LatLonNvectorSpherical || !isNaN(pathBrngEnd))) throw new TypeError(`invalid pathBrngEnd ‘${pathBrngEnd}’`);
//
//    if (this.equals(pathStart)) return 0;
//
//        const p = this.toNvector();
//        const R = Number(radius);
//
//        const gc = pathBrngEnd instanceof LatLonNvectorSpherical   // (note JavaScript is not good at method overloading)
//            ? pathStart.toNvector().cross(pathBrngEnd.toNvector()) // great circle defined by two points
//            : pathStart.greatCircle(pathBrngEnd);                  // great circle defined by point + bearing
//
//        const α = gc.angleTo(p) - π/2; // angle between point & great-circle
//
//    return α * R;
//  }

  @Deprecated
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
