/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eng.eSystem.utilites;

import eng.eSystem.collections.EList;
import eng.eSystem.collections.IList;
import eng.eSystem.collections.IReadOnlyList;
import eng.eSystem.exceptions.ApplicationException;
import eng.eSystem.validation.EAssert;

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
  @Deprecated
  public static class Package {
    /**
     * Returns all classes declared in the package
     *
     * @param packageName Package name
     * @return Readonly list of classes.
     */
    @Deprecated
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

  public static class PackageUtils {
    /**
     * Returns all classes declared in the package
     *
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

    private static class PrimitiveToWrap {
      final Class<?> primitive;
      final Class<?> wrap;

      public PrimitiveToWrap(Class<?> primitive, Class<?> wrap) {
        this.primitive = primitive;
        this.wrap = wrap;
      }
    }

    private static final IList<PrimitiveToWrap> primitiveToWraps;

    static {
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

    public static boolean isPrimitiveOrWrappedPrimitive(Class<?> type) {
      return primitiveToWraps.isAny(q -> q.primitive.equals(type) || q.wrap.equals(type));
    }

    public static boolean isPrimitive(Class<?> type) {
      return primitiveToWraps.isAny(q -> q.primitive.equals(type));
    }

    public static boolean isWrappedPrimitive(Class<?> type) {
      return primitiveToWraps.isAny(q -> q.wrap.equals(type));
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
        ret.addMany(fields);
        tmp = tmp.getSuperclass();
      }
      return ret;
    }

    public static Class<?> tryWrapPrimitive(Class<?> type) {
      return primitiveToWraps.tryGetFirst(q -> q.primitive.equals(type)).map(q->q.wrap).orElse(null);
    }

    public static Class<?> tryUnwapToPrimitive(Class<?> type) {
      return primitiveToWraps.tryGetFirst(q -> q.wrap.equals(type)).map(q->q.primitive).orElse(null);
    }

  }

  public static class FieldUtils {
    public static void setFieldValue(Object target, String fieldName, Object value) {
      EAssert.Argument.isNotNull(target, "target");
      EAssert.Argument.isNonemptyString(fieldName, "fieldName");

      try {
        Field field = getField(target.getClass(), fieldName);
        field.setAccessible(true);
        field.set(target, value);
        field.setAccessible(false);
      } catch (Exception e) {
        throw new ApplicationException(sf("Unable to set field value. Class: '%s', field '%s'.", target.getClass(), fieldName), e);
      }
    }

    public static Object getFieldValue(Object source, String fieldName) {
      EAssert.Argument.isNotNull(source, "source");
      EAssert.Argument.isNonemptyString(fieldName, "fieldName");
      Object ret;
      try {
        Field field = getField(source.getClass(), fieldName);
        field.setAccessible(true);
        ret = field.get(source);
        field.setAccessible(false);
      } catch (Exception e) {
        throw new ApplicationException(sf("Unable to get field value. Class: '%s', field '%s'.", source.getClass(), fieldName), e);
      }
      return ret;
    }

    public static Field getField(Class clz, String fieldName) {
      Field[] fields = clz.getDeclaredFields();
      Field ret = ArrayUtils.tryGetFirst(fields, q -> q.getName().equals(fieldName));
      if (ret == null && clz.getSuperclass() != null)
        ret = getField(clz.getSuperclass(), fieldName);
      if (ret == null)
        throw new ApplicationException(sf("Failed to find field named '%s' in '%s'.", fieldName, clz.getName()));
      return ret;
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
    ret.addMany(type.getInterfaces());
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
