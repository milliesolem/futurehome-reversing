package org.junit.experimental.theories;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterSignature {
   private static final Map<Class<?>, Class<?>> CONVERTABLE_TYPES_MAP = buildConvertableTypesMap();
   private final Annotation[] annotations;
   private final Class<?> type;

   private ParameterSignature(Class<?> var1, Annotation[] var2) {
      this.type = var1;
      this.annotations = var2;
   }

   private static Map<Class<?>, Class<?>> buildConvertableTypesMap() {
      HashMap var0 = new HashMap();
      putSymmetrically(var0, boolean.class, Boolean.class);
      putSymmetrically(var0, byte.class, Byte.class);
      putSymmetrically(var0, short.class, Short.class);
      putSymmetrically(var0, char.class, Character.class);
      putSymmetrically(var0, int.class, Integer.class);
      putSymmetrically(var0, long.class, Long.class);
      putSymmetrically(var0, float.class, Float.class);
      putSymmetrically(var0, double.class, Double.class);
      return Collections.unmodifiableMap(var0);
   }

   private <T extends Annotation> T findDeepAnnotation(Annotation[] var1, Class<T> var2, int var3) {
      if (var3 == 0) {
         return null;
      } else {
         for (Annotation var6 : var1) {
            if (var2.isInstance(var6)) {
               return (T)var2.cast(var6);
            }

            var6 = this.findDeepAnnotation(var6.annotationType().getAnnotations(), var2, var3 - 1);
            if (var6 != null) {
               return (T)var2.cast(var6);
            }
         }

         return null;
      }
   }

   private boolean isAssignableViaTypeConversion(Class<?> var1, Class<?> var2) {
      Map var3 = CONVERTABLE_TYPES_MAP;
      return var3.containsKey(var2) ? var1.isAssignableFrom((Class<?>)var3.get(var2)) : false;
   }

   private static <T> void putSymmetrically(Map<T, T> var0, T var1, T var2) {
      var0.put(var1, var2);
      var0.put(var2, var1);
   }

   public static ArrayList<ParameterSignature> signatures(Method var0) {
      return signatures(var0.getParameterTypes(), var0.getParameterAnnotations());
   }

   private static ArrayList<ParameterSignature> signatures(Class<?>[] var0, Annotation[][] var1) {
      ArrayList var3 = new ArrayList();

      for (int var2 = 0; var2 < var0.length; var2++) {
         var3.add(new ParameterSignature(var0[var2], var1[var2]));
      }

      return var3;
   }

   public static List<ParameterSignature> signatures(Constructor<?> var0) {
      return signatures(var0.getParameterTypes(), var0.getParameterAnnotations());
   }

   public boolean canAcceptType(Class<?> var1) {
      boolean var2;
      if (!this.type.isAssignableFrom(var1) && !this.isAssignableViaTypeConversion(this.type, var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public boolean canAcceptValue(Object var1) {
      boolean var2;
      if (var1 == null) {
         if (!this.type.isPrimitive()) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else {
         var2 = this.canAcceptType(var1.getClass());
      }

      return var2;
   }

   public boolean canPotentiallyAcceptType(Class<?> var1) {
      boolean var2;
      if (!var1.isAssignableFrom(this.type) && !this.isAssignableViaTypeConversion(var1, this.type) && !this.canAcceptType(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public <T extends Annotation> T findDeepAnnotation(Class<T> var1) {
      return this.findDeepAnnotation(this.annotations, var1, 3);
   }

   public <T extends Annotation> T getAnnotation(Class<T> var1) {
      for (Annotation var2 : this.getAnnotations()) {
         if (var1.isInstance(var2)) {
            return (T)var1.cast(var2);
         }
      }

      return null;
   }

   public List<Annotation> getAnnotations() {
      return Arrays.asList(this.annotations);
   }

   public Class<?> getType() {
      return this.type;
   }

   public boolean hasAnnotation(Class<? extends Annotation> var1) {
      boolean var2;
      if (this.getAnnotation(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
