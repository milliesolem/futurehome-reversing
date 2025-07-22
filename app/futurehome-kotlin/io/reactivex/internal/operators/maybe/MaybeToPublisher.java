package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeSource;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;

public enum MaybeToPublisher implements Function<MaybeSource<Object>, Publisher<Object>> {
   INSTANCE;
   private static final MaybeToPublisher[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      MaybeToPublisher var0 = new MaybeToPublisher();
      INSTANCE = var0;
      $VALUES = new MaybeToPublisher[]{var0};
   }

   public static <T> Function<MaybeSource<T>, Publisher<T>> instance() {
      return INSTANCE;
   }

   public Publisher<Object> apply(MaybeSource<Object> var1) throws Exception {
      return new MaybeToFlowable<>(var1);
   }
}
