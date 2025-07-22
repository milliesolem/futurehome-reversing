package io.reactivex;

import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.NotificationLite;

public final class Notification<T> {
   static final Notification<Object> COMPLETE = new Notification<>(null);
   final Object value;

   private Notification(Object var1) {
      this.value = var1;
   }

   public static <T> Notification<T> createOnComplete() {
      return (Notification<T>)COMPLETE;
   }

   public static <T> Notification<T> createOnError(Throwable var0) {
      ObjectHelper.requireNonNull(var0, "error is null");
      return new Notification<>(NotificationLite.error(var0));
   }

   public static <T> Notification<T> createOnNext(T var0) {
      ObjectHelper.requireNonNull(var0, "value is null");
      return new Notification<>(var0);
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 instanceof Notification) {
         var1 = var1;
         return ObjectHelper.equals(this.value, var1.value);
      } else {
         return false;
      }
   }

   public Throwable getError() {
      Object var1 = this.value;
      return NotificationLite.isError(var1) ? NotificationLite.getError(var1) : null;
   }

   public T getValue() {
      Object var1 = this.value;
      return (T)(var1 != null && !NotificationLite.isError(var1) ? this.value : null);
   }

   @Override
   public int hashCode() {
      Object var2 = this.value;
      int var1;
      if (var2 != null) {
         var1 = var2.hashCode();
      } else {
         var1 = 0;
      }

      return var1;
   }

   public boolean isOnComplete() {
      boolean var1;
      if (this.value == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isOnError() {
      return NotificationLite.isError(this.value);
   }

   public boolean isOnNext() {
      Object var2 = this.value;
      boolean var1;
      if (var2 != null && !NotificationLite.isError(var2)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public String toString() {
      StringBuilder var1 = (StringBuilder)this.value;
      if (var1 == null) {
         return "OnCompleteNotification";
      } else if (NotificationLite.isError(var1)) {
         StringBuilder var2 = new StringBuilder("OnErrorNotification[");
         var2.append(NotificationLite.getError(var1));
         var2.append("]");
         return var2.toString();
      } else {
         var1 = new StringBuilder("OnNextNotification[");
         var1.append(this.value);
         var1.append("]");
         return var1.toString();
      }
   }
}
