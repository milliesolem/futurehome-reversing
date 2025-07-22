package io.reactivex.subjects;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableSubject extends Completable implements CompletableObserver {
   static final CompletableSubject.CompletableDisposable[] EMPTY = new CompletableSubject.CompletableDisposable[0];
   static final CompletableSubject.CompletableDisposable[] TERMINATED = new CompletableSubject.CompletableDisposable[0];
   Throwable error;
   final AtomicReference<CompletableSubject.CompletableDisposable[]> observers;
   final AtomicBoolean once = new AtomicBoolean();

   CompletableSubject() {
      this.observers = new AtomicReference<>(EMPTY);
   }

   @CheckReturnValue
   public static CompletableSubject create() {
      return new CompletableSubject();
   }

   boolean add(CompletableSubject.CompletableDisposable var1) {
      CompletableSubject.CompletableDisposable[] var3;
      CompletableSubject.CompletableDisposable[] var4;
      do {
         var4 = this.observers.get();
         if (var4 == TERMINATED) {
            return false;
         }

         int var2 = var4.length;
         var3 = new CompletableSubject.CompletableDisposable[var2 + 1];
         System.arraycopy(var4, 0, var3, 0, var2);
         var3[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var4, var3));

      return true;
   }

   public Throwable getThrowable() {
      return this.observers.get() == TERMINATED ? this.error : null;
   }

   public boolean hasComplete() {
      boolean var1;
      if (this.observers.get() == TERMINATED && this.error == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasObservers() {
      boolean var1;
      if (this.observers.get().length != 0) {
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

   int observerCount() {
      return this.observers.get().length;
   }

   @Override
   public void onComplete() {
      AtomicBoolean var3 = this.once;
      int var1 = 0;
      if (var3.compareAndSet(false, true)) {
         CompletableSubject.CompletableDisposable[] var4 = this.observers.getAndSet(TERMINATED);

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
         CompletableSubject.CompletableDisposable[] var5 = this.observers.getAndSet(TERMINATED);

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

   void remove(CompletableSubject.CompletableDisposable var1) {
      CompletableSubject.CompletableDisposable[] var4;
      CompletableSubject.CompletableDisposable[] var5;
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
            var4 = new CompletableSubject.CompletableDisposable[var3 - 1];
            System.arraycopy(var5, 0, var4, 0, var2);
            System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var5, var4));
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      CompletableSubject.CompletableDisposable var2 = new CompletableSubject.CompletableDisposable(var1, this);
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
            var1.onComplete();
         }
      }
   }

   static final class CompletableDisposable extends AtomicReference<CompletableSubject> implements Disposable {
      private static final long serialVersionUID = -7650903191002190468L;
      final CompletableObserver downstream;

      CompletableDisposable(CompletableObserver var1, CompletableSubject var2) {
         this.downstream = var1;
         this.lazySet(var2);
      }

      @Override
      public void dispose() {
         CompletableSubject var1 = this.getAndSet(null);
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
