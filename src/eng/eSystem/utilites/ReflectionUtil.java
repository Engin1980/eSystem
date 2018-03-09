/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eng.eSystem.utilites;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.lang.String;

/**
 *
 * @author Marek Vajgl
 */
public class ReflectionUtil {

  /**
   * 
   * @param packageName
   * @return 
   */
  public static List<Class> tryGetAllTypes(String packageName) {
    ClassLoader cls = ReflectionUtil.class.getClassLoader();
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
  
  public static List<Class> filterByParent(List<Class> classes, Class requiredParent){
    List<Class> ret = new ArrayList<>();
    
    for (Class c : classes){
      if (requiredParent.isAssignableFrom(c)){
        ret.add(c);
      }
    }
    
    return ret;
  }

}
