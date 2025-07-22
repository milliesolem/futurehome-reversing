package com.jakewharton.rxrelay2;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class PublishRelay<T> extends Relay<T> {
   static final PublishRelay.PublishDisposable[] EMPTY = new PublishRelay.PublishDisposable[0];
   final AtomicReference<PublishRelay.PublishDisposable<T>[]> subscribers = new AtomicReference<>(EMPTY);

   PublishRelay() {
   }

   @CheckReturnValue
   public static <T> PublishRelay<T> create() {
      return new PublishRelay<>();
   }

   @Override
   public void accept(T var1) {
      if (var1 == null) {
         throw new NullPointerException("value == null");
      } else {
         PublishRelay.PublishDisposable[] var4 = this.subscribers.get();
         int var3 = var4.length;

         for (int var2 = 0; var2 < var3; var2++) {
            var4[var2].onNext(var1);
         }
      }
   }

   void add(PublishRelay.PublishDisposable<T> var1) {
      PublishRelay.PublishDisposable[] var3;
      PublishRelay.PublishDisposable[] var4;
      do {
         var4 = this.subscribers.get();
         int var2 = var4.length;
         var3 = new PublishRelay.PublishDisposable[var2 + 1];
         System.arraycopy(var4, 0, var3, 0, var2);
         var3[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var4, var3));
   }

   @Override
   public boolean hasObservers() {
      boolean var1;
      if (((PublishRelay.PublishDisposable[])this.subscribers.get()).length != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   void remove(PublishRelay.PublishDisposable<T> var1) {
      PublishRelay.PublishDisposable[] var4;
      PublishRelay.PublishDisposable[] var5;
      do {
         var5 = this.subscribers.get();
         if (var5 == EMPTY) {
            return;
         }

         int var3 = var5.length;
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
            var4 = new PublishRelay.PublishDisposable[var3 - 1];
            System.arraycopy(var5, 0, var4, 0, var2);
            System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, var4));
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      PublishRelay.PublishDisposable var2 = new PublishRelay.PublishDisposable<>(var1, this);
      var1.onSubscribe(var2);
      this.add(var2);
      if (var2.isDisposed()) {
         this.remove(var2);
      }
   }

   static final class PublishDisposable<T> extends AtomicBoolean implements Disposable {
      private static final long serialVersionUID = 3562861878281475070L;
      final Observer<? super T> downstream;
      final PublishRelay<T> parent;

      PublishDisposable(Observer<? super T> var1, PublishRelay<T> var2) {
         this.downstream = var1;
         this.parent = var2;
      }

      @Override
      public void dispose() {
         if (this.compareAndSet(false, true)) {
            this.parent.remove(this);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.get();
      }

      public void onNext(T var1) {
         if (!this.get()) {
            this.downstream.onNext((T)var1);
         }
      }
   }
}
