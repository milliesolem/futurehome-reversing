package io.reactivex.internal.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public enum HashMapSupplier implements Callable<Map<Object, Object>> {
   INSTANCE;
   private static final HashMapSupplier[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      HashMapSupplier var0 = new HashMapSupplier();
      INSTANCE = var0;
      $VALUES = new HashMapSupplier[]{var0};
   }

   public static <K, V> Callable<Map<K, V>> asCallable() {
      return INSTANCE;
   }

   public Map<Object, Object> call() throws Exception {
      return new HashMap<>();
   }
}
