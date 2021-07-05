package eng.eSystem.eXml;

import eng.eSystem.EStringBuilder;
import eng.eSystem.collections.*;
import eng.eSystem.utilites.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class XElement {

  public static XElement fromElement(Element el) {
    XElement ret = new XElement(el.getTagName());
    XElementLocationData locationData = (XElementLocationData) el.getUserData(XElementLocationData.LOCATION_DATA_KEY);
    ret.locationData = locationData;

    // attributes
    for (int i = 0; i < el.getAttributes().getLength(); i++) {
      Node n = el.getAttributes().item(i);
      String name = n.getNodeName();
      String value = n.getNodeValue();
      ret.setAttribute(name, value);
    }

    // children
    for (int i = 0; i < el.getChildNodes().getLength(); i++) {
      Node n = el.getChildNodes().item(i);
      if (n.getNodeType() != Node.ELEMENT_NODE) continue;
      XElement tmp = XElement.fromElement((Element) n);
      ret.addElement(tmp);
    }

    // direct content
    if (ret.getChildren().isEmpty()) {
      String value = el.getTextContent();
      ret.setContent(value);
    } else {
      ret.setContent("");
    }

    return ret;
  }

  public static XElement fromString(String xmlString) {
    XDocument doc;
    try {
      InputStream is = new ByteArrayInputStream(xmlString.getBytes());
      doc = XDocument.load(is);
    } catch (Exception e) {
      throw new IllegalArgumentException(sf("Unable to load XML from '%s'.", xmlString), e);
    }
    XElement ret = doc.getRoot();
    return ret;
  }

  private final IMap<String, String> attributes = new EMap<>();
  private final IList<XElement> children = new EList<>();
  private String content;
  private XElementLocationData locationData;
  private final String name;
  private XElement parent;

  public XElement(String name) {
    this.name = name;
  }

  public XElement(String name, String content) {
    this.name = name;
    this.content = content;
  }

  public void addElement(XElement childElement) {
    if (childElement.getParent() != null)
      throw new XmlRuntimeException("Element " + childElement + " has already a parent " + childElement.getParent() + ".");
    childElement.setParent(this);
    this.children.add(childElement);
  }

  public void detachFromParent() {
    if (this.parent != null)
      this.parent.children.tryRemove(this);
    this.parent = null;
  }

  public String getAttribute(String name) {
    String ret = getAttributes().get(name);
    return ret;
  }

  public IReadOnlyMap<String, String> getAttributes() {
    return attributes;
  }

  public XElement getChild(String name) {
    XElement ret;
    IReadOnlyList<XElement> tmp = this.getChildren(name);
    if (tmp.size() == 0) {
      throw new XmlRuntimeException("Element '" + name + "' not found.");
    } else if (tmp.size() > 1) {
      throw new XmlRuntimeException("Element '" + name + "' has multiple occurrences.");
    } else {
      ret = tmp.get(0);
    }
    return ret;
  }

  public IReadOnlyList<XElement> getChildren() {
    return children;
  }

  public IReadOnlyList<XElement> getChildren(String name) {
    return children.where(q -> q.getName().equals(name));
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public XElementLocationData getLocationData() {
    return locationData;
  }

  public String getName() {
    return name;
  }

  public XElement getParent() {
    return parent;
  }

  private void setParent(XElement parent) {
    this.parent = parent;
  }

  public boolean hasAttribute(String name) {
    return this.attributes.containsKey(name);
  }

  public void removeAttribute(String attributeName) {
    this.attributes.remove(attributeName);
  }

  public void removeElement(XElement childElement) {
    this.children.remove(childElement);
    childElement.setParent(null);
  }

  public void setAttribute(String attributeName, String attributeValue) {
    this.attributes.set(attributeName, attributeValue);
  }

  public Element toElement(Document doc) {
    Element ret;
    try {
      ret = doc.createElement(this.name);
    } catch (Exception ex) {
      throw new XmlRuntimeException("Failed to create an element '" + this.name + "'.", ex);
    }

    for (String key : this.attributes.getKeys()) {
      String val = this.attributes.get(key);
      try {
        ret.setAttribute(key, val);
      } catch (Exception ex) {
        throw new XmlRuntimeException(sf("Failed to set attribute '%s=\"%s\"' to element '%s'.", key, val, this.name));
      }
    }

    if (this.children.isEmpty())
      ret.setTextContent(this.getContent());
    else {
      for (XElement child : this.children) {
        Element tmp = child.toElement(doc);
        ret.appendChild(tmp);
      }
    }

    return ret;
  }

  public String toFullString() {
    String ret = toFullString(0);
    return ret;
  }

  @Override
  public String toString() {
    return toString(false);
  }

  public String toString(boolean withAttributes) {
    EStringBuilder ret = new EStringBuilder();
    ret.append("<").append(this.getName());
    if (withAttributes)
      for (String key : attributes.getKeys()) {
        ret.append(" ").append(key).append("=\"").append(this.attributes.get(key)).append("\"");
      }
    ret.append(">");
    return ret.toString();
  }

  public String toXPath() {
    EStringBuilder sb = new EStringBuilder();

    XElement el = this;
    while (el != null) {
      sb.insert(0, "/" + el.getName());
      el = el.getParent();
    }

    return sb.toString();
  }

  public String toXmlPath(boolean withAttributes) {
    EStringBuilder sb = new EStringBuilder();
    boolean isFirst = true;

    XElement el = this;
    while (el != null) {
      if (isFirst)
        isFirst = false;
      else
        sb.insert(0, "/");
      sb.insert(0, el.toString(withAttributes));
      el = el.getParent();
    }

    return sb.toString();
  }

  public Optional<String> tryGetAttribute(String name) {
    Optional<String> ret = getAttributes().tryGet(name);
    return ret;
  }

  public Optional<XElement> tryGetChild(String name) {
    Optional<XElement> ret;
    IReadOnlyList<XElement> tmp = this.getChildren(name);
    if (tmp.size() == 0) {
      ret = Optional.empty();
    } else if (tmp.size() > 1) {
      throw new XmlRuntimeException("Element '" + name + "' has multiple occurrences.");
    } else {
      ret = Optional.of(tmp.get(0));
    }
    return ret;
  }

  private String toFullString(int indentLevel) {
    EStringBuilder ret = new EStringBuilder();
    String indent = "";
    for (int i = 0; i < indentLevel; i++) {
      indent += "  ";
    }
    ret.append(indent);
    ret.append("<").append(this.getName());
    for (String key : attributes.getKeys()) {
      ret.append(" ").append(key).append("=\"").append(this.attributes.get(key)).append("\"");
    }
    if (!StringUtils.isNullOrEmpty(this.content)) {
      ret.append(">");
      ret.appendLine();
      ret.append(indent).append("  ").append(this.content);
      ret.appendLine();
      ret.append(indent).appendFormat("</%s>", this.getName());
    } else if (!this.getChildren().isEmpty()) {
      ret.append(">").appendLine();
      for (XElement child : children) {
        ret.append(child.toFullString(indentLevel + 1));
      }
      ret.append(indent).appendFormat("</%s>", this.getName());
    } else {
      ret.append("/>");
    }
    ret.appendLine();
    return ret.toString();
  }
}
