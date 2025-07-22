package io.reactivex.internal.util;

import io.reactivex.CompletableObserver;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public enum EmptyComponent
   implements FlowableSubscriber<Object>,
   Observer<Object>,
   MaybeObserver<Object>,
   SingleObserver<Object>,
   CompletableObserver,
   Subscription,
   Disposable {
   INSTANCE;
   private static final EmptyComponent[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      EmptyComponent var0 = new EmptyComponent();
      INSTANCE = var0;
      $VALUES = new EmptyComponent[]{var0};
   }

   public static <T> Observer<T> asObserver() {
      return INSTANCE;
   }

   public static <T> Subscriber<T> asSubscriber() {
      return INSTANCE;
   }

   public void cancel() {
   }

   @Override
   public void dispose() {
   }

   @Override
   public boolean isDisposed() {
      return true;
   }

   @Override
   public void onComplete() {
   }

   @Override
   public void onError(Throwable var1) {
      RxJavaPlugins.onError(var1);
   }

   @Override
   public void onNext(Object var1) {
   }

   @Override
   public void onSubscribe(Disposable var1) {
      var1.dispose();
   }

   @Override
   public void onSubscribe(Subscription var1) {
      var1.cancel();
   }

   @Override
   public void onSuccess(Object var1) {
   }

   public void request(long var1) {
   }
}
