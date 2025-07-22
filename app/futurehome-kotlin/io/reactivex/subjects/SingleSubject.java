package io.reactivex.subjects;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleSubject<T> extends Single<T> implements SingleObserver<T> {
   static final SingleSubject.SingleDisposable[] EMPTY = new SingleSubject.SingleDisposable[0];
   static final SingleSubject.SingleDisposable[] TERMINATED = new SingleSubject.SingleDisposable[0];
   Throwable error;
   final AtomicReference<SingleSubject.SingleDisposable<T>[]> observers;
   final AtomicBoolean once = new AtomicBoolean();
   T value;

   SingleSubject() {
      this.observers = new AtomicReference<>(EMPTY);
   }

   @CheckReturnValue
   public static <T> SingleSubject<T> create() {
      return new SingleSubject<>();
   }

   boolean add(SingleSubject.SingleDisposable<T> var1) {
      SingleSubject.SingleDisposable[] var3;
      SingleSubject.SingleDisposable[] var4;
      do {
         var4 = this.observers.get();
         if (var4 == TERMINATED) {
            return false;
         }

         int var2 = var4.length;
         var3 = new SingleSubject.SingleDisposable[var2 + 1];
         System.arraycopy(var4, 0, var3, 0, var2);
         var3[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var4, var3));

      return true;
   }

   public Throwable getThrowable() {
      return this.observers.get() == TERMINATED ? this.error : null;
   }

   public T getValue() {
      return this.observers.get() == TERMINATED ? this.value : null;
   }

   public boolean hasObservers() {
      boolean var1;
      if (((SingleSubject.SingleDisposable[])this.observers.get()).length != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasThrowable() {
      boolean var1;
      if (this.observers.get() == TERMINATED && this.error != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasValue() {
      boolean var1;
      if (this.observers.get() == TERMINATED && this.value != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   int observerCount() {
      return ((SingleSubject.SingleDisposable[])this.observers.get()).length;
   }

   @Override
   public void onError(Throwable var1) {
      ObjectHelper.requireNonNull(var1, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      AtomicBoolean var4 = this.once;
      int var2 = 0;
      if (var4.compareAndSet(false, true)) {
         this.error = var1;
         SingleSubject.SingleDisposable[] var5 = this.observers.getAndSet(TERMINATED);

         for (int var3 = var5.length; var2 < var3; var2++) {
            var5[var2].downstream.onError(var1);
         }
      } else {
         RxJavaPlugins.onError(var1);
      }
   }

   @Override
   public void onSubscribe(Disposable var1) {
      if (this.observers.get() == TERMINATED) {
         var1.dispose();
      }
   }

   @Override
   public void onSuccess(T var1) {
      ObjectHelper.requireNonNull(var1, "onSuccess called with null. Null values are generally not allowed in 2.x operators and sources.");
      AtomicBoolean var4 = this.once;
      int var2 = 0;
      if (var4.compareAndSet(false, true)) {
         this.value = (T)var1;
         SingleSubject.SingleDisposable[] var5 = this.observers.getAndSet(TERMINATED);

         for (int var3 = var5.length; var2 < var3; var2++) {
            var5[var2].downstream.onSuccess((T)var1);
         }
      }
   }

   void remove(SingleSubject.SingleDisposable<T> var1) {
      SingleSubject.SingleDisposable[] var4;
      SingleSubject.SingleDisposable[] var5;
      do {
         var5 = this.observers.get();
         int var3 = var5.length;
         if (var3 == 0) {
            return;
         }

         int var2 = 0;

         while (true) {
            if (var2 >= var3) {
               var2 = -1;
               break;
            }

            if (var5[var2] == var1) {
               break;
            }

            var2++;
         }

         if (var2 < 0) {
            return;
         }

         if (var3 == 1) {
            var4 = EMPTY;
         } else {
            var4 = new SingleSubject.SingleDisposable[var3 - 1];
            System.arraycopy(var5, 0, var4, 0, var2);
            System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var5, var4));
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      SingleSubject.SingleDisposable var2 = new SingleSubject.SingleDisposable<>(var1, this);
      var1.onSubscribe(var2);
      if (this.add(var2)) {
         if (var2.isDisposed()) {
            this.remove(var2);
         }
      } else {
         Throwable var3 = this.error;
         if (var3 != null) {
            var1.onError(var3);
         } else {
            var1.onSuccess(this.value);
         }
      }
   }

   static final class SingleDisposable<T> extends AtomicReference<SingleSubject<T>> implements Disposable {
      private static final long serialVersionUID = -7650903191002190468L;
      final SingleObserver<? super T> downstream;

      SingleDisposable(SingleObserver<? super T> var1, SingleSubject<T> var2) {
         this.downstream = var1;
         this.lazySet(var2);
      }

      @Override
      public void dispose() {
         SingleSubject var1 = this.getAndSet(null);
         if (var1 != null) {
            var1.remove(this);
         }
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }
}
