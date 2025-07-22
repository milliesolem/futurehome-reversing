package org.chromium.support_lib_boundary.util;

import android.os.Build;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;

public class BoundaryInterfaceReflectionUtil {
   static final boolean $assertionsDisabled = false;

   public static <T> T castToSuppLibClass(Class<T> var0, InvocationHandler var1) {
      return (T)(var1 == null ? null : var0.cast(Proxy.newProxyInstance(BoundaryInterfaceReflectionUtil.class.getClassLoader(), new Class[]{var0}, var1)));
   }

   public static boolean containsFeature(Collection<String> var0, String var1) {
      if (!var0.contains(var1)) {
         if (!isDebuggable()) {
            return false;
         }

         StringBuilder var3 = new StringBuilder();
         var3.append(var1);
         var3.append(":dev");
         if (!var0.contains(var3.toString())) {
            return false;
         }
      }

      return true;
   }

   public static boolean containsFeature(String[] var0, String var1) {
      return containsFeature(Arrays.asList(var0), var1);
   }

   public static InvocationHandler createInvocationHandlerFor(Object var0) {
      return var0 == null ? null : new BoundaryInterfaceReflectionUtil.InvocationHandlerWithDelegateGetter(var0);
   }

   public static InvocationHandler[] createInvocationHandlersForArray(Object[] var0) {
      if (var0 == null) {
         return null;
      } else {
         int var2 = var0.length;
         InvocationHandler[] var3 = new InvocationHandler[var2];

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1] = createInvocationHandlerFor(var0[var1]);
         }

         return var3;
      }
   }

   public static Method dupeMethod(Method var0, ClassLoader var1) throws ClassNotFoundException, NoSuchMethodException {
      Class var2 = Class.forName(var0.getDeclaringClass().getName(), true, var1);
      Class[] var3 = var0.getParameterTypes();
      return var2.getDeclaredMethod(var0.getName(), var3);
   }

   public static Object getDelegateFromInvocationHandler(InvocationHandler var0) {
      return var0 == null ? null : ((BoundaryInterfaceReflectionUtil.InvocationHandlerWithDelegateGetter)var0).getDelegate();
   }

   public static boolean instanceOfInOwnClassLoader(Object var0, String var1) {
      try {
         return Class.forName(var1, false, var0.getClass().getClassLoader()).isInstance(var0);
      } catch (ClassNotFoundException var3) {
         return false;
      }
   }

   private static boolean isDebuggable() {
      boolean var0;
      if (!"eng".equals(Build.TYPE) && !"userdebug".equals(Build.TYPE)) {
         var0 = false;
      } else {
         var0 = true;
      }

      return var0;
   }

   private static class InvocationHandlerWithDelegateGetter implements InvocationHandler {
      private final Object mDelegate;

      public InvocationHandlerWithDelegateGetter(Object var1) {
         this.mDelegate = var1;
      }

      public Object getDelegate() {
         return this.mDelegate;
      }

      @Override
      public Object invoke(Object var1, Method var2, Object[] var3) throws Throwable {
         var1 = this.mDelegate.getClass().getClassLoader();

         try {
            return BoundaryInterfaceReflectionUtil.dupeMethod(var2, var1).invoke(this.mDelegate, var3);
         } catch (InvocationTargetException var4) {
            throw var4.getTargetException();
         } catch (ReflectiveOperationException var5) {
            StringBuilder var7 = new StringBuilder("Reflection failed for method ");
            var7.append(var2);
            throw new RuntimeException(var7.toString(), var5);
         }
      }
   }
}
