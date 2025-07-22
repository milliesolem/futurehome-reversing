package io.sentry;

import java.lang.reflect.InvocationTargetException;

public final class OptionsContainer<T> {
   private final Class<T> clazz;

   private OptionsContainer(Class<T> var1) {
      this.clazz = var1;
   }

   public static <T> OptionsContainer<T> create(Class<T> var0) {
      return new OptionsContainer<>(var0);
   }

   public T createInstance() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
      return this.clazz.getDeclaredConstructor(null).newInstance(null);
   }
}
