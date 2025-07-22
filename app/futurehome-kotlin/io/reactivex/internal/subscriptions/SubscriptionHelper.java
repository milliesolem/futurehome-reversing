package io.reactivex.internal.subscriptions;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.exceptions.ProtocolViolationException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public enum SubscriptionHelper implements Subscription {
   CANCELLED;
   private static final SubscriptionHelper[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      SubscriptionHelper var0 = new SubscriptionHelper();
      CANCELLED = var0;
      $VALUES = new SubscriptionHelper[]{var0};
   }

   public static boolean cancel(AtomicReference<Subscription> var0) {
      Subscription var2 = (Subscription)var0.get();
      SubscriptionHelper var1 = CANCELLED;
      if (var2 != var1) {
         Subscription var3 = var0.getAndSet(var1);
         if (var3 != var1) {
            if (var3 != null) {
               var3.cancel();
            }

            return true;
         }
      }

      return false;
   }

   public static void deferredRequest(AtomicReference<Subscription> var0, AtomicLong var1, long var2) {
      Subscription var4 = (Subscription)var0.get();
      if (var4 != null) {
         var4.request(var2);
      } else if (validate(var2)) {
         BackpressureHelper.add(var1, var2);
         Subscription var5 = (Subscription)var0.get();
         if (var5 != null) {
            var2 = var1.getAndSet(0L);
            if (var2 != 0L) {
               var5.request(var2);
            }
         }
      }
   }

   public static boolean deferredSetOnce(AtomicReference<Subscription> var0, AtomicLong var1, Subscription var2) {
      if (setOnce(var0, var2)) {
         long var3 = var1.getAndSet(0L);
         if (var3 != 0L) {
            var2.request(var3);
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean replace(AtomicReference<Subscription> var0, Subscription var1) {
      Subscription var2;
      do {
         var2 = (Subscription)var0.get();
         if (var2 == CANCELLED) {
            if (var1 != null) {
               var1.cancel();
            }

            return false;
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(var0, var2, var1));

      return true;
   }

   public static void reportMoreProduced(long var0) {
      StringBuilder var2 = new StringBuilder("More produced than requested: ");
      var2.append(var0);
      RxJavaPlugins.onError(new ProtocolViolationException(var2.toString()));
   }

   public static void reportSubscriptionSet() {
      RxJavaPlugins.onError(new ProtocolViolationException("Subscription already set!"));
   }

   public static boolean set(AtomicReference<Subscription> var0, Subscription var1) {
      Subscription var2;
      do {
         var2 = (Subscription)var0.get();
         if (var2 == CANCELLED) {
            if (var1 != null) {
               var1.cancel();
            }

            return false;
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(var0, var2, var1));

      if (var2 != null) {
         var2.cancel();
      }

      return true;
   }

   public static boolean setOnce(AtomicReference<Subscription> var0, Subscription var1) {
      ObjectHelper.requireNonNull(var1, "s is null");
      if (!ExternalSyntheticBackportWithForwarding0.m(var0, null, var1)) {
         var1.cancel();
         if (var0.get() != CANCELLED) {
            reportSubscriptionSet();
         }

         return false;
      } else {
         return true;
      }
   }

   public static boolean setOnce(AtomicReference<Subscription> var0, Subscription var1, long var2) {
      if (setOnce(var0, var1)) {
         var1.request(var2);
         return true;
      } else {
         return false;
      }
   }

   public static boolean validate(long var0) {
      if (var0 <= 0L) {
         StringBuilder var2 = new StringBuilder("n > 0 required but it was ");
         var2.append(var0);
         RxJavaPlugins.onError(new IllegalArgumentException(var2.toString()));
         return false;
      } else {
         return true;
      }
   }

   public static boolean validate(Subscription var0, Subscription var1) {
      if (var1 == null) {
         RxJavaPlugins.onError(new NullPointerException("next is null"));
         return false;
      } else if (var0 != null) {
         var1.cancel();
         reportSubscriptionSet();
         return false;
      } else {
         return true;
      }
   }

   public void cancel() {
   }

   public void request(long var1) {
   }
}
