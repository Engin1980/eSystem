package eng.eSystem.geo.geocoding;

import eng.eSystem.geo.Coordinate;

public interface IGeocoding {
  Coordinate geocode(String address);
}
