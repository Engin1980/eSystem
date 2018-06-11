package eng.eSystem.utilites.awt;

import eng.eSystem.utilites.ConversionUtils;

import javax.swing.*;
import java.awt.*;
import java.util.function.Predicate;

public class ComponentUtils {

  public interface IComponentAdjuster {
    void adjust(Component c);
  }

  public static void adjustComponentTree(Component top, IComponentAdjuster adjuster) {
    ComponentUtils.adjustComponentTree(top, null, adjuster);
  }

  public static void adjustComponentTree(Component top, Predicate<Component> selector, IComponentAdjuster adjuster) {

    if (selector == null || selector.test(top))
      adjuster.adjust(top);
    Container container = ConversionUtils.tryConvert(top, Container.class);
    if (container != null) {
      for (Component component : container.getComponents()) {
        adjustComponentTree(component, selector, adjuster);
      }
    }
  }

  public static void printComponentTree(Container component) {
    printGuiTreeJComponent(component, 0);
  }

  private static void printGuiTreeJComponent(Container component, int index) {
    for (int i = 0; i < index; i++) {
      System.out.print(" ");
    }
    String layoutName;
    if (component.getLayout() != null)
      layoutName = component.getLayout().getClass().getSimpleName();
    else
      layoutName = "N/A";
    System.out.println(
        String.format("%s -- %s : %d x %d :: %d x %d using %s - visible? %s",
            component.getClass().getName(),
            component.getName(),
            component.getX(),
            component.getY(),
            component.getWidth(),
            component.getHeight(),
            layoutName,
            Boolean.toString(component.isVisible())
        ));
    for (Component item : component.getComponents()) {
      if (item instanceof Container)
        printGuiTreeJComponent((Container) item, index + 1);
    }
  }
}
