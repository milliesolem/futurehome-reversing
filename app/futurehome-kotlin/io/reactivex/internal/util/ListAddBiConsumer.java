package io.reactivex.internal.util;

import io.reactivex.functions.BiFunction;
import java.util.List;

public enum ListAddBiConsumer implements BiFunction<List, Object, List> {
   INSTANCE;
   private static final ListAddBiConsumer[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      ListAddBiConsumer var0 = new ListAddBiConsumer();
      INSTANCE = var0;
      $VALUES = new ListAddBiConsumer[]{var0};
   }

   public static <T> BiFunction<List<T>, T, List<T>> instance() {
      return INSTANCE;
   }

   public List apply(List var1, Object var2) throws Exception {
      var1.add(var2);
      return var1;
   }
}
