package io.reactivex.internal.util;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.io.Serializable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public enum NotificationLite {
   COMPLETE;
   private static final NotificationLite[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      NotificationLite var0 = new NotificationLite();
      COMPLETE = var0;
      $VALUES = new NotificationLite[]{var0};
   }

   public static <T> boolean accept(Object var0, Observer<? super T> var1) {
      if (var0 == COMPLETE) {
         var1.onComplete();
         return true;
      } else if (var0 instanceof NotificationLite.ErrorNotification) {
         var1.onError(((NotificationLite.ErrorNotification)var0).e);
         return true;
      } else {
         var1.onNext(var0);
         return false;
      }
   }

   public static <T> boolean accept(Object var0, Subscriber<? super T> var1) {
      if (var0 == COMPLETE) {
         var1.onComplete();
         return true;
      } else if (var0 instanceof NotificationLite.ErrorNotification) {
         var1.onError(((NotificationLite.ErrorNotification)var0).e);
         return true;
      } else {
         var1.onNext(var0);
         return false;
      }
   }

   public static <T> boolean acceptFull(Object var0, Observer<? super T> var1) {
      if (var0 == COMPLETE) {
         var1.onComplete();
         return true;
      } else if (var0 instanceof NotificationLite.ErrorNotification) {
         var1.onError(((NotificationLite.ErrorNotification)var0).e);
         return true;
      } else if (var0 instanceof NotificationLite.DisposableNotification) {
         var1.onSubscribe(((NotificationLite.DisposableNotification)var0).upstream);
         return false;
      } else {
         var1.onNext(var0);
         return false;
      }
   }

   public static <T> boolean acceptFull(Object var0, Subscriber<? super T> var1) {
      if (var0 == COMPLETE) {
         var1.onComplete();
         return true;
      } else if (var0 instanceof NotificationLite.ErrorNotification) {
         var1.onError(((NotificationLite.ErrorNotification)var0).e);
         return true;
      } else if (var0 instanceof NotificationLite.SubscriptionNotification) {
         var1.onSubscribe(((NotificationLite.SubscriptionNotification)var0).upstream);
         return false;
      } else {
         var1.onNext(var0);
         return false;
      }
   }

   public static Object complete() {
      return COMPLETE;
   }

   public static Object disposable(Disposable var0) {
      return new NotificationLite.DisposableNotification(var0);
   }

   public static Object error(Throwable var0) {
      return new NotificationLite.ErrorNotification(var0);
   }

   public static Disposable getDisposable(Object var0) {
      return ((NotificationLite.DisposableNotification)var0).upstream;
   }

   public static Throwable getError(Object var0) {
      return ((NotificationLite.ErrorNotification)var0).e;
   }

   public static Subscription getSubscription(Object var0) {
      return ((NotificationLite.SubscriptionNotification)var0).upstream;
   }

   public static <T> T getValue(Object var0) {
      return (T)var0;
   }

   public static boolean isComplete(Object var0) {
      boolean var1;
      if (var0 == COMPLETE) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isDisposable(Object var0) {
      return var0 instanceof NotificationLite.DisposableNotification;
   }

   public static boolean isError(Object var0) {
      return var0 instanceof NotificationLite.ErrorNotification;
   }

   public static boolean isSubscription(Object var0) {
      return var0 instanceof NotificationLite.SubscriptionNotification;
   }

   public static <T> Object next(T var0) {
      return var0;
   }

   public static Object subscription(Subscription var0) {
      return new NotificationLite.SubscriptionNotification(var0);
   }

   @Override
   public String toString() {
      return "NotificationLite.Complete";
   }

   static final class DisposableNotification implements Serializable {
      private static final long serialVersionUID = -7482590109178395495L;
      final Disposable upstream;

      DisposableNotification(Disposable var1) {
         this.upstream = var1;
      }

      @Override
      public String toString() {
         StringBuilder var1 = new StringBuilder("NotificationLite.Disposable[");
         var1.append(this.upstream);
         var1.append("]");
         return var1.toString();
      }
   }

   static final class ErrorNotification implements Serializable {
      private static final long serialVersionUID = -8759979445933046293L;
      final Throwable e;

      ErrorNotification(Throwable var1) {
         this.e = var1;
      }

      @Override
      public boolean equals(Object var1) {
         if (var1 instanceof NotificationLite.ErrorNotification) {
            var1 = var1;
            return ObjectHelper.equals(this.e, var1.e);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return this.e.hashCode();
      }

      @Override
      public String toString() {
         StringBuilder var1 = new StringBuilder("NotificationLite.Error[");
         var1.append(this.e);
         var1.append("]");
         return var1.toString();
      }
   }

   static final class SubscriptionNotification implements Serializable {
      private static final long serialVersionUID = -1322257508628817540L;
      final Subscription upstream;

      SubscriptionNotification(Subscription var1) {
         this.upstream = var1;
      }

      @Override
      public String toString() {
         StringBuilder var1 = new StringBuilder("NotificationLite.Subscription[");
         var1.append(this.upstream);
         var1.append("]");
         return var1.toString();
      }
   }
}
