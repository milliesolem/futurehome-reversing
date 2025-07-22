package org.hamcrest.beans;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class PropertyUtil {
   public static final Object[] NO_ARGUMENTS = new Object[0];

   public static PropertyDescriptor getPropertyDescriptor(String var0, Object var1) throws IllegalArgumentException {
      for (PropertyDescriptor var4 : propertyDescriptorsFor(var1, null)) {
         if (var4.getName().equals(var0)) {
            return var4;
         }
      }

      return null;
   }

   public static PropertyDescriptor[] propertyDescriptorsFor(Object var0, Class<Object> var1) throws IllegalArgumentException {
      try {
         return Introspector.getBeanInfo(var0.getClass(), var1).getPropertyDescriptors();
      } catch (IntrospectionException var3) {
         StringBuilder var2 = new StringBuilder("Could not get property descriptors for ");
         var2.append(var0.getClass());
         throw new IllegalArgumentException(var2.toString(), var3);
      }
   }
}
