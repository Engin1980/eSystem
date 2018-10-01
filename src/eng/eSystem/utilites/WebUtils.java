package eng.eSystem.utilites;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebUtils {
  public static String downloadUrl(String targetUrl) {
    HttpURLConnection connection = null;
    StringBuilder response;

    try {
      //Create connection
      URL url = new URL(targetUrl);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setUseCaches(false);
      connection.setDoOutput(true);

      //Get Response
      InputStream is = connection.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
      response = new StringBuilder();

      String line;
      while ((line = rd.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      rd.close();
    } catch (Exception e) {
      throw new RuntimeException("Failed to download Url " + targetUrl + ".", e);
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }

    return response.toString();
  }
}
