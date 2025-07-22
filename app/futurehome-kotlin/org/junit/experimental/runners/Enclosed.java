package org.junit.experimental.runners;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import org.junit.runners.Suite;
import org.junit.runners.model.RunnerBuilder;

public class Enclosed extends Suite {
   public Enclosed(Class<?> var1, RunnerBuilder var2) throws Throwable {
      super(var2, var1, filterAbstractClasses(var1.getClasses()));
   }

   private static Class<?>[] filterAbstractClasses(Class<?>[] var0) {
      ArrayList var3 = new ArrayList(var0.length);

      for (Class var4 : var0) {
         if (!Modifier.isAbstract(var4.getModifiers())) {
            var3.add(var4);
         }
      }

      return var3.toArray(new Class[var3.size()]);
   }
}
