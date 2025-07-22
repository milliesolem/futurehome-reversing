package io.reactivex.internal.util;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.ProtocolViolationException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class EndConsumerHelper {
   private EndConsumerHelper() {
      throw new IllegalStateException("No instances!");
   }

   public static String composeMessage(String var0) {
      StringBuilder var1 = new StringBuilder("It is not allowed to subscribe with a(n) ");
      var1.append(var0);
      var1.append(" multiple times. Please create a fresh instance of ");
      var1.append(var0);
      var1.append(" and subscribe that to the target source instead.");
      return var1.toString();
   }

   public static void reportDoubleSubscription(Class<?> var0) {
      RxJavaPlugins.onError(new ProtocolViolationException(composeMessage(var0.getName())));
   }

   public static boolean setOnce(AtomicReference<Disposable> var0, Disposable var1, Class<?> var2) {
      ObjectHelper.requireNonNull(var1, "next is null");
      if (!ExternalSyntheticBackportWithForwarding0.m(var0, null, var1)) {
         var1.dispose();
         if (var0.get() != DisposableHelper.DISPOSED) {
            reportDoubleSubscription(var2);
         }

         return false;
      } else {
         return true;
      }
   }

   public static boolean setOnce(AtomicReference<Subscription> var0, Subscription var1, Class<?> var2) {
      ObjectHelper.requireNonNull(var1, "next is null");
      if (!ExternalSyntheticBackportWithForwarding0.m(var0, null, var1)) {
         var1.cancel();
         if (var0.get() != SubscriptionHelper.CANCELLED) {
            reportDoubleSubscription(var2);
         }

         return false;
      } else {
         return true;
      }
   }

   public static boolean validate(Disposable var0, Disposable var1, Class<?> var2) {
      ObjectHelper.requireNonNull(var1, "next is null");
      if (var0 != null) {
         var1.dispose();
         if (var0 != DisposableHelper.DISPOSED) {
            reportDoubleSubscription(var2);
         }

         return false;
      } else {
         return true;
      }
   }

   public static boolean validate(Subscription var0, Subscription var1, Class<?> var2) {
      ObjectHelper.requireNonNull(var1, "next is null");
      if (var0 != null) {
         var1.cancel();
         if (var0 != SubscriptionHelper.CANCELLED) {
            reportDoubleSubscription(var2);
         }

         return false;
      } else {
         return true;
      }
   }
}
