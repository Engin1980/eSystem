package eng.eSystem.geo.geocoding;

import eng.eSystem.geo.Coordinate;
import org.junit.Test;

import static org.junit.Assert.*;

public class HereGeocodingTest {

  @Test
  public void geocode() {
    String address = "Marsa+Alam";
    IGeocoding geo = new HereGeocoding(null, null);

    Coordinate ret = geo.geocode(address);

  }
}