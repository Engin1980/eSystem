package eng.eSystem.geo.geocoding;

import eng.eSystem.geo.Coordinate;
import eng.eSystem.utilites.WebUtils;
import eng.eSystem.validation.Validator;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HereGeocoding implements IGeocoding {
  private final String code;
  private final String id;

  public HereGeocoding(String id, String code) {
    Validator.isNotNull(id, "APP_ID cannot be null. Obtain some from developer.here.com");
    Validator.isNotNull(code, "APP_CODE cannot be null. Obtain some from developer.here.com");
    this.code = code;
    this.id = id;
  }

  public Coordinate geocode(String address) {
    String requestUrl = String.format("" +
            "https://geocoder.api.here.com/6.2/geocode.json?app_id=%s&app_code=%s&searchtext=%s",
        id,
        code,
        address);

    String response = WebUtils.downloadUrl(requestUrl);

    Coordinate ret = decodeCoordinate(response);

    return ret;


  }

  private static final double EMPTY = -1;

  private Coordinate decodeCoordinate(String response) {
    Pattern latPattern = Pattern.compile("\"Latitude\": ?(\\d+\\.+\\d+)");
    Pattern lngPattern = Pattern.compile("\"Longitude\": ?(\\d+\\.+\\d+)");
    double lat = EMPTY;
    double lng = EMPTY;
    Matcher m;

    m = latPattern.matcher(response);
    if (m.find()) {
      lat = decodeValue(m.group(1));
    }
    m = lngPattern.matcher(response);
    if (m.find())
      lng = decodeValue(m.group(1));

    if (lat == EMPTY || lng == EMPTY)
      throw new RuntimeException("Unable to find latitude/longitude in the response of length " + response.length());

    Coordinate ret = new Coordinate(lat, lng);
    return ret;
  }

  private double decodeValue(String value) {
    double ret;
    NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
    Number number;
    try {
      number = format.parse(value);
    } catch (ParseException e) {
      throw new RuntimeException("Failed to parse value of latitude/longitude from " + value + ".", e);
    }
    ret = number.doubleValue();

    return ret;

  }
}
