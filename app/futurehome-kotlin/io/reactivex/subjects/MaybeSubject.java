package io.reactivex.subjects;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeSubject<T> extends Maybe<T> implements MaybeObserver<T> {
   static final MaybeSubject.MaybeDisposable[] EMPTY = new MaybeSubject.MaybeDisposable[0];
   static final MaybeSubject.MaybeDisposable[] TERMINATED = new MaybeSubject.MaybeDisposable[0];
   Throwable error;
   final AtomicReference<MaybeSubject.MaybeDisposable<T>[]> observers;
   final AtomicBoolean once = new AtomicBoolean();
   T value;

   MaybeSubject() {
      this.observers = new AtomicReference<>(EMPTY);
   }

   @CheckReturnValue
   public static <T> MaybeSubject<T> create() {
      return new MaybeSubject<>();
   }

   boolean add(MaybeSubject.MaybeDisposable<T> var1) {
      MaybeSubject.MaybeDisposable[] var3;
      MaybeSubject.MaybeDisposable[] var4;
      do {
         var3 = this.observers.get();
         if (var3 == TERMINATED) {
            return false;
         }

         int var2 = var3.length;
         var4 = new MaybeSubject.MaybeDisposable[var2 + 1];
         System.arraycopy(var3, 0, var4, 0, var2);
         var4[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var3, var4));

      return true;
   }

   public Throwable getThrowable() {
      return this.observers.get() == TERMINATED ? this.error : null;
   }

   public T getValue() {
      return this.observers.get() == TERMINATED ? this.value : null;
   }

   public boolean hasComplete() {
      boolean var1;
      if (this.observers.get() == TERMINATED && this.value == null && this.error == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasObservers() {
      boolean var1;
      if (((MaybeSubject.MaybeDisposable[])this.observers.get()).length != 0) {
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
      return ((MaybeSubject.MaybeDisposable[])this.observers.get()).length;
   }

   @Override
   public void onComplete() {
      AtomicBoolean var3 = this.once;
      int var1 = 0;
      if (var3.compareAndSet(false, true)) {
         MaybeSubject.MaybeDisposable[] var4 = this.observers.getAndSet(TERMINATED);

         for (int var2 = var4.length; var1 < var2; var1++) {
            var4[var1].downstream.onComplete();
         }
      }
   }

   @Override
   public void onError(Throwable var1) {
      ObjectHelper.requireNonNull(var1, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      AtomicBoolean var4 = this.once;
      int var2 = 0;
      if (var4.compareAndSet(false, true)) {
         this.error = var1;
         MaybeSubject.MaybeDisposable[] var5 = this.observers.getAndSet(TERMINATED);

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
         MaybeSubject.MaybeDisposable[] var5 = this.observers.getAndSet(TERMINATED);

         for (int var3 = var5.length; var2 < var3; var2++) {
            var5[var2].downstream.onSuccess((T)var1);
         }
      }
   }

   void remove(MaybeSubject.MaybeDisposable<T> var1) {
      MaybeSubject.MaybeDisposable[] var4;
      MaybeSubject.MaybeDisposable[] var5;
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
            var4 = new MaybeSubject.MaybeDisposable[var3 - 1];
            System.arraycopy(var5, 0, var4, 0, var2);
            System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var5, var4));
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      MaybeSubject.MaybeDisposable var2 = new MaybeSubject.MaybeDisposable<>(var1, this);
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
            var2 = this.value;
            if (var2 == null) {
               var1.onComplete();
            } else {
               var1.onSuccess(var2);
            }
         }
      }
   }

   static final class MaybeDisposable<T> extends AtomicReference<MaybeSubject<T>> implements Disposable {
      private static final long serialVersionUID = -7650903191002190468L;
      final MaybeObserver<? super T> downstream;

      MaybeDisposable(MaybeObserver<? super T> var1, MaybeSubject<T> var2) {
         this.downstream = var1;
         this.lazySet(var2);
      }

      @Override
      public void dispose() {
         MaybeSubject var1 = this.getAndSet(null);
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
