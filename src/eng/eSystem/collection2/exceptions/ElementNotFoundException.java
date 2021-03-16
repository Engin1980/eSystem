package eng.eSystem.collection2.exceptions;

public class ElementNotFoundException extends  RuntimeException {

  public ElementNotFoundException() {
    super("Element not found.");
  }

  public ElementNotFoundException(Object element){
    super ("Element " + element + " not found.");
  }
}
