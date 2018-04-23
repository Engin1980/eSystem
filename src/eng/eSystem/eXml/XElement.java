package eng.eSystem.eXml;

import eng.eSystem.EStringBuilder;
import eng.eSystem.collections.*;
import eng.eSystem.exceptions.EXmlRuntimeException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XElement {
  private final String name;
  private final IList<XElement> children = new EList<>();
  private final IMap<String, String> attributes = new EMap<>();
  private XElement parent;
  private String content;

  public static XElement fromElement(Element el) {
    XElement ret = new XElement(el.getTagName());

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

  public XElement(String name) {
    this.name = name;
  }

  public XElement(String name, String content) {
    this.name = name;
    this.content = content;
  }

  public XElement getParent() {
    return parent;
  }

  private void setParent(XElement parent) {
    this.parent = parent;
  }

  public String getName() {
    return name;
  }

  public void addElement(XElement childElement) {
    if (childElement.getParent() != null)
      throw new EXmlRuntimeException("Element " + childElement + " has already a parent " + childElement.getParent() + ".");
    childElement.setParent(this);
    this.children.add(childElement);
  }

  public void setAttribute(String attributeName, String attributeValue) {
    this.attributes.set(attributeName, attributeValue);
  }

  public void removeElement(XElement childElement) {
    this.children.remove(childElement);
    childElement.setParent(null);
  }

  public void removeAttribute(String attributeName) {
    this.attributes.remove(attributeName);
  }

  public IReadOnlyList<XElement> getChildren() {
    return children;
  }

  public IReadOnlyMap<String, String> getAttributes() {
    return attributes;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return toString(false);
  }

  public Element toElement(Document doc) {
    Element ret = doc.createElement(this.name);

    for (String key : this.attributes.getKeys()) {
      String val = this.attributes.get(key);
      ret.setAttribute(key, val);
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

  public String toString(boolean withAttributes) {
    EStringBuilder ret = new EStringBuilder();
    ret.append("<").append(this.getName());
    if (withAttributes)
      for (String key : attributes.getKeys()) {
        ret.append(" ").append(key).append(this.attributes.get(key));
      }
    ret.append(">");
    return ret.toString();
  }
}
