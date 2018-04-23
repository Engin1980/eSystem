package eng.eSystem.eXml;

import org.w3c.dom.Document;

public class XDocumentProperties {

  private boolean standalone;
  private String encoding;
  private String version;

  public static XDocumentProperties create(Document doc){
    XDocumentProperties ret = new XDocumentProperties();

    ret.standalone = doc.getXmlStandalone();
    ret.encoding = doc.getXmlEncoding();
    ret.version = doc.getXmlVersion();

    return ret;
  }

  public XDocumentProperties() {
  }

  public boolean isStandalone() {
    return standalone;
  }

  public void setStandalone(boolean standalone) {
    this.standalone = standalone;
  }

  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}
