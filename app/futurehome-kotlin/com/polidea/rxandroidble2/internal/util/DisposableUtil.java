package com.polidea.rxandroidble2.internal.util;

import io.reactivex.ObservableEmitter;
import io.reactivex.SingleEmitter;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class DisposableUtil {
   private DisposableUtil() {
   }

   public static <T> DisposableObserver<T> disposableObserverFromEmitter(ObservableEmitter<T> var0) {
      return new DisposableObserver<T>(var0) {
         final ObservableEmitter val$emitter;

         {
            this.val$emitter = var1;
         }

         @Override
         public void onComplete() {
            this.val$emitter.onComplete();
         }

         @Override
         public void onError(Throwable var1) {
            this.val$emitter.tryOnError(var1);
         }

         @Override
         public void onNext(T var1) {
            this.val$emitter.onNext(var1);
         }
      };
   }

   public static <T> DisposableSingleObserver<T> disposableSingleObserverFromEmitter(ObservableEmitter<T> var0) {
      return new DisposableSingleObserver<T>(var0) {
         final ObservableEmitter val$emitter;

         {
            this.val$emitter = var1;
         }

         @Override
         public void onError(Throwable var1) {
            this.val$emitter.tryOnError(var1);
         }

         @Override
         public void onSuccess(T var1) {
            this.val$emitter.onNext(var1);
            this.val$emitter.onComplete();
         }
      };
   }

   public static <T> DisposableSingleObserver<T> disposableSingleObserverFromEmitter(SingleEmitter<T> var0) {
      return new DisposableSingleObserver<T>(var0) {
         final SingleEmitter val$emitter;

         {
            this.val$emitter = var1;
         }

         @Override
         public void onError(Throwable var1) {
            this.val$emitter.tryOnError(var1);
         }

         @Override
         public void onSuccess(T var1) {
            this.val$emitter.onSuccess(var1);
         }
      };
   }
}
