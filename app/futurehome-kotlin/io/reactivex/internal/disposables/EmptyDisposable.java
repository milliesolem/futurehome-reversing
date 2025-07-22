package io.reactivex.internal.disposables;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.internal.fuseable.QueueDisposable;

public enum EmptyDisposable implements QueueDisposable<Object> {
   INSTANCE,
   NEVER;
   private static final EmptyDisposable[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      EmptyDisposable var0 = new EmptyDisposable();
      INSTANCE = var0;
      EmptyDisposable var1 = new EmptyDisposable();
      NEVER = var1;
      $VALUES = new EmptyDisposable[]{var0, var1};
   }

   public static void complete(CompletableObserver var0) {
      var0.onSubscribe(INSTANCE);
      var0.onComplete();
   }

   public static void complete(MaybeObserver<?> var0) {
      var0.onSubscribe(INSTANCE);
      var0.onComplete();
   }

   public static void complete(Observer<?> var0) {
      var0.onSubscribe(INSTANCE);
      var0.onComplete();
   }

   public static void error(Throwable var0, CompletableObserver var1) {
      var1.onSubscribe(INSTANCE);
      var1.onError(var0);
   }

   public static void error(Throwable var0, MaybeObserver<?> var1) {
      var1.onSubscribe(INSTANCE);
      var1.onError(var0);
   }

   public static void error(Throwable var0, Observer<?> var1) {
      var1.onSubscribe(INSTANCE);
      var1.onError(var0);
   }

   public static void error(Throwable var0, SingleObserver<?> var1) {
      var1.onSubscribe(INSTANCE);
      var1.onError(var0);
   }

   @Override
   public void clear() {
   }

   @Override
   public void dispose() {
   }

   @Override
   public boolean isDisposed() {
      boolean var1;
      if (this == INSTANCE) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean isEmpty() {
      return true;
   }

   @Override
   public boolean offer(Object var1) {
      throw new UnsupportedOperationException("Should not be called!");
   }

   @Override
   public boolean offer(Object var1, Object var2) {
      throw new UnsupportedOperationException("Should not be called!");
   }

   @Override
   public Object poll() throws Exception {
      return null;
   }

   @Override
   public int requestFusion(int var1) {
      return var1 & 2;
   }
}
