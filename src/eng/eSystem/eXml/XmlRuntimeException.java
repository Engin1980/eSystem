package eng.eSystem.eXml;

import eng.eSystem.exceptions.ApplicationException;

public class XmlRuntimeException extends ApplicationException {
  public XmlRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public XmlRuntimeException(String message) {
    super(message);
  }
}
