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
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

/**
 * @author Marek Vajgl
 */
public class ReflectionUtils {
  public static class Package {
    /**
     * Returns all classes declared in the package
     * @param packageName Package name
     * @return Readonly list of classes.
     */
    public static IReadOnlyList<Class<?>> tryGetAllTypes(String packageName) {
      ClassLoader cls = ReflectionUtils.class.getClassLoader();
      packageName = packageName.replace('.', '/');
      Enumeration<URL> urls;

      try {
        urls = cls.getResources(packageName);
      } catch (IOException ex) {
        return null;
      }

      IList<Class<?>> ret = new EList<>();
      while (urls.hasMoreElements()) {
        URL url = urls.nextElement();
        File f = new File(url.getFile());
        if (f.getName().endsWith(".class")) {
          try {
            Class<?> c = cls.loadClass(packageName + "." + f.getName().substring(0, f.getName().length() - 6));
            ret.add(c);
          } catch (ClassNotFoundException ex) {
            // intentionally blank
          }
        }
      }
      return ret;
    }
  }

  public static class ClassUtils {
    private static class PrimitiveToWrap{
      final Class<?> primitive;
      final Class<?> wrap;

      public PrimitiveToWrap(Class<?> primitive, Class<?> wrap) {
        this.primitive = primitive;
        this.wrap = wrap;
      }
    }

    private static final IList<PrimitiveToWrap> primitiveToWraps;
    static{
      primitiveToWraps = new EList<>();
      primitiveToWraps.add(new PrimitiveToWrap(int.class, Integer.class));
      primitiveToWraps.add(new PrimitiveToWrap(short.class, Short.class));
      primitiveToWraps.add(new PrimitiveToWrap(byte.class, Byte.class));
      primitiveToWraps.add(new PrimitiveToWrap(long.class, Long.class));
      primitiveToWraps.add(new PrimitiveToWrap(double.class, Double.class));
      primitiveToWraps.add(new PrimitiveToWrap(float.class, Float.class));
      primitiveToWraps.add(new PrimitiveToWrap(boolean.class, Boolean.class));
      primitiveToWraps.add(new PrimitiveToWrap(char.class, Character.class));
    }

    /**
     * Returns all the fields of a class, including inherited.
     *
     * @param type Required class to get fields over
     * @return A readonly IList of fields.
     */
    public static IReadOnlyList<Field> getFields(Class<?> type) {
      IList<Field> ret = new EList<>();

      Class<?> tmp = type;
      while (tmp != null) {
        Field[] fields = tmp.getDeclaredFields();
        ret.add(fields);
        tmp = tmp.getSuperclass();
      }
      return ret;
    }

    public Class<?> tryWrapPrimitive(Class<?> type){
      PrimitiveToWrap ptw = primitiveToWraps.tryGetFirst(q->q.primitive.equals(type));
      if (ptw != null)
        return ptw.wrap;
      else
        return null;
    }

    public Class<?> tryUnwapToPrimitive(Class<?> type){
      PrimitiveToWrap ptw = primitiveToWraps.tryGetFirst(q->q.wrap.equals(type));
      if (ptw != null)
        return ptw.primitive;
      else
        return null;
    }

  }

  public static List<Class<?>> filterByParent(List<Class<?>> classes, Class<?> requiredParent) {
    List<Class<?>> ret = new ArrayList<>();

    for (Class<?> c : classes) {
      if (requiredParent.isAssignableFrom(c)) {
        ret.add(c);
      }
    }

    return ret;
  }

  public static int getInheritanceDistance(Class<?> ancestorType, Class<?> descendantType) {
    if (ancestorType.isAssignableFrom(descendantType) == false) {
      throw new IllegalArgumentException(
          sf("There is no inheritance relation between '%s' and '%s'.",
              ancestorType.getName(),
              descendantType.getName()));
    }

    int ret = tryTraceForParent(ancestorType, descendantType);
    return ret;
  }

  public static Type[] getParameterizedTypes(Object object) {
    Type superclassType = object.getClass().getGenericSuperclass();
    if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
      return null;
    }
    return ((ParameterizedType) superclassType).getActualTypeArguments();
  }

  private static IList<Class<?>> getAllAncestors(Class<?> type) {
    IList<Class<?>> ret = new EList<>();
    if (type.getSuperclass() != null)
      ret.add(type.getSuperclass());
    ret.add(type.getInterfaces());
    return ret;
  }

  private static int tryTraceForParent(Class<?> targetAncestor, Class<?> descendant) {
    if (targetAncestor.equals(descendant))
      return 0;

    IReadOnlyList<Class<?>> ancestors = getAllAncestors(descendant);
    for (Class<?> ancestor : ancestors) {
      int tmp = tryTraceForParent(targetAncestor, ancestor);
      if (tmp >= 0)
        return tmp + 1;
    }
    return -1;
  }

}
