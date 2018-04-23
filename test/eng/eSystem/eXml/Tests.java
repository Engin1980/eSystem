package eng.eSystem.eXml;

import eng.eSystem.exceptions.EXmlException;
import eng.eSystem.utilites.ExceptionUtil;
import org.junit.Test;

import static org.junit.Assert.fail;

public class Tests {

  @Test
  public void testLoad() {
    String src = "R:\\simTest.xml";
    XDocument doc = null;
    try {
      doc = XDocument.load(src);
    } catch (EXmlException e) {
      fail(ExceptionUtil.toFullString(e));
    }

    try {
      doc.save("R:\\simTest2.xml");
    } catch (EXmlException e) {
      fail(ExceptionUtil.toFullString(e));
    }

  }

}
