package eng.eSystem.utilites.awt;

import eng.eSystem.utilites.ConversionUtils;

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
    Container container = ConversionUtils.tryConvert(top);
    if (container != null) {
      for (Component component : container.getComponents()) {
        adjustComponentTree(component, adjuster);
      }
    }
  }
}
