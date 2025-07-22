package io.reactivex.internal.subscriptions;

import io.reactivex.internal.fuseable.QueueSubscription;
import org.reactivestreams.Subscriber;

public enum EmptySubscription implements QueueSubscription<Object> {
   INSTANCE;
   private static final EmptySubscription[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      EmptySubscription var0 = new EmptySubscription();
      INSTANCE = var0;
      $VALUES = new EmptySubscription[]{var0};
   }

   public static void complete(Subscriber<?> var0) {
      var0.onSubscribe(INSTANCE);
      var0.onComplete();
   }

   public static void error(Throwable var0, Subscriber<?> var1) {
      var1.onSubscribe(INSTANCE);
      var1.onError(var0);
   }

   public void cancel() {
   }

   @Override
   public void clear() {
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
   public Object poll() {
      return null;
   }

   public void request(long var1) {
      SubscriptionHelper.validate(var1);
   }

   @Override
   public int requestFusion(int var1) {
      return var1 & 2;
   }

   @Override
   public String toString() {
      return "EmptySubscription";
   }
}
