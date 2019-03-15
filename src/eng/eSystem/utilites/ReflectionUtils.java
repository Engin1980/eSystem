/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eng.eSystem.utilites;

import eng.eSystem.collections.EList;
import eng.eSystem.collections.IList;
import eng.eSystem.collections.IReadOnlyList;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.lang.String;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

/**
 * @author Marek Vajgl
 */
public class ReflectionUtils {

  public static Type[] getParameterizedTypes(Object object) {
    Type superclassType = object.getClass().getGenericSuperclass();
    if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
      return null;
    }
    return ((ParameterizedType) superclassType).getActualTypeArguments();
  }

  /**
   * @param packageName
   * @return
   */
  public static List<Class> tryGetAllTypes(String packageName) {
    ClassLoader cls = ReflectionUtils.class.getClassLoader();
    packageName = packageName.replace('.', '/');
    Enumeration<URL> urls;

    try {
      urls = cls.getResources(packageName);
    } catch (IOException ex) {
      return null;
    }

    List<Class> ret = new ArrayList<>();
    while (urls.hasMoreElements()) {
      URL url = urls.nextElement();
      Class c;
      File f = new File(url.getFile());
      if (f.getName().endsWith(".class")) {
        try {
          c = cls.loadClass(packageName + "." + f.getName().substring(0, f.getName().length() - 6));
          ret.add(c);
        } catch (ClassNotFoundException ex) {
        }
      }
    }

    return ret;
  }

  public static List<Class> filterByParent(List<Class> classes, Class requiredParent) {
    List<Class> ret = new ArrayList<>();

    for (Class c : classes) {
      if (requiredParent.isAssignableFrom(c)) {
        ret.add(c);
      }
    }

    return ret;
  }

  public static int getInheritanceDistance(Class ancestorType, Class descendantType) {
    if (ancestorType.isAssignableFrom(descendantType) == false) {
      throw new IllegalArgumentException(
          sf("There is no inheritance relation between '%s' and '%s'.",
              ancestorType.getName(),
              descendantType.getName()));
    }

    int ret = tryTraceForParent(ancestorType, descendantType);
    return ret;
  }

  private static int tryTraceForParent(Class targetAncestor, Class descendant) {
    if (targetAncestor.equals(descendant))
      return 0;

    IReadOnlyList<Class> ancestors = getAllAncestors(descendant);
    for (Class ancestor : ancestors) {
      int tmp = tryTraceForParent(targetAncestor, ancestor);
      if (tmp >= 0)
        return tmp + 1;
    }
    return -1;
  }

  private static IList<Class> getAllAncestors(Class type) {
    IList<Class> ret = new EList<>();
    if (type.getSuperclass() != null)
      ret.add(type.getSuperclass());
    ret.add(type.getInterfaces());
    return ret;
  }

}
