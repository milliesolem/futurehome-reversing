package com.polidea.rxandroidble2.internal.util;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

public class ObservableUtil {
   private static final ObservableTransformer<?, ?> IDENTITY_TRANSFORMER = new ObservableTransformer<Object, Object>() {
      public Observable<Object> apply(Observable<Object> var1) {
         return var1;
      }
   };

   private ObservableUtil() {
   }

   public static <T> ObservableTransformer<T, T> identityTransformer() {
      return (ObservableTransformer<T, T>)IDENTITY_TRANSFORMER;
   }

   public static <T> Observable<T> justOnNext(T var0) {
      return Observable.<T>never().startWith((T)var0);
   }
}
