package io.reactivex.internal.util;

import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public enum ArrayListSupplier implements Callable<List<Object>>, Function<Object, List<Object>> {
   INSTANCE;
   private static final ArrayListSupplier[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      ArrayListSupplier var0 = new ArrayListSupplier();
      INSTANCE = var0;
      $VALUES = new ArrayListSupplier[]{var0};
   }

   public static <T> Callable<List<T>> asCallable() {
      return INSTANCE;
   }

   public static <T, O> Function<O, List<T>> asFunction() {
      return INSTANCE;
   }

   public List<Object> apply(Object var1) throws Exception {
      return new ArrayList<>();
   }

   public List<Object> call() throws Exception {
      return new ArrayList<>();
   }
}
