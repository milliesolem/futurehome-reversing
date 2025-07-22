package io.reactivex.subjects;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class PublishSubject<T> extends Subject<T> {
   static final PublishSubject.PublishDisposable[] EMPTY = new PublishSubject.PublishDisposable[0];
   static final PublishSubject.PublishDisposable[] TERMINATED = new PublishSubject.PublishDisposable[0];
   Throwable error;
   final AtomicReference<PublishSubject.PublishDisposable<T>[]> subscribers = new AtomicReference<>(EMPTY);

   PublishSubject() {
   }

   @CheckReturnValue
   public static <T> PublishSubject<T> create() {
      return new PublishSubject<>();
   }

   boolean add(PublishSubject.PublishDisposable<T> var1) {
      PublishSubject.PublishDisposable[] var3;
      PublishSubject.PublishDisposable[] var4;
      do {
         var4 = this.subscribers.get();
         if (var4 == TERMINATED) {
            return false;
         }

         int var2 = var4.length;
         var3 = new PublishSubject.PublishDisposable[var2 + 1];
         System.arraycopy(var4, 0, var3, 0, var2);
         var3[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var4, var3));

      return true;
   }

   @Override
   public Throwable getThrowable() {
      return this.subscribers.get() == TERMINATED ? this.error : null;
   }

   @Override
   public boolean hasComplete() {
      boolean var1;
      if (this.subscribers.get() == TERMINATED && this.error == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasObservers() {
      boolean var1;
      if (((PublishSubject.PublishDisposable[])this.subscribers.get()).length != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasThrowable() {
      boolean var1;
      if (this.subscribers.get() == TERMINATED && this.error != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void onComplete() {
      Object var4 = this.subscribers.get();
      PublishSubject.PublishDisposable[] var3 = TERMINATED;
      if (var4 != var3) {
         var3 = this.subscribers.getAndSet(var3);
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].onComplete();
         }
      }
   }

   @Override
   public void onError(Throwable var1) {
      ObjectHelper.requireNonNull(var1, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      Object var4 = this.subscribers.get();
      PublishSubject.PublishDisposable[] var5 = TERMINATED;
      if (var4 == var5) {
         RxJavaPlugins.onError(var1);
      } else {
         this.error = var1;
         var4 = this.subscribers.getAndSet(var5);
         int var3 = ((Object[])var4).length;

         for (int var2 = 0; var2 < var3; var2++) {
            ((PublishSubject.PublishDisposable)((Object[])var4)[var2]).onError(var1);
         }
      }
   }

   @Override
   public void onNext(T var1) {
      ObjectHelper.requireNonNull(var1, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
      PublishSubject.PublishDisposable[] var4 = this.subscribers.get();
      int var3 = var4.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4[var2].onNext(var1);
      }
   }

   @Override
   public void onSubscribe(Disposable var1) {
      if (this.subscribers.get() == TERMINATED) {
         var1.dispose();
      }
   }

   void remove(PublishSubject.PublishDisposable<T> var1) {
      while (true) {
         PublishSubject.PublishDisposable[] var5 = this.subscribers.get();
         if (var5 != TERMINATED && var5 != EMPTY) {
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

            PublishSubject.PublishDisposable[] var4;
            if (var3 == 1) {
               var4 = EMPTY;
            } else {
               var4 = new PublishSubject.PublishDisposable[var3 - 1];
               System.arraycopy(var5, 0, var4, 0, var2);
               System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
            }

            if (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, var4)) {
               continue;
            }
         }

         return;
      }
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      PublishSubject.PublishDisposable var2 = new PublishSubject.PublishDisposable<>(var1, this);
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

   static final class PublishDisposable<T> extends AtomicBoolean implements Disposable {
      private static final long serialVersionUID = 3562861878281475070L;
      final Observer<? super T> downstream;
      final PublishSubject<T> parent;

      PublishDisposable(Observer<? super T> var1, PublishSubject<T> var2) {
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

      public void onComplete() {
         if (!this.get()) {
            this.downstream.onComplete();
         }
      }

      public void onError(Throwable var1) {
         if (this.get()) {
            RxJavaPlugins.onError(var1);
         } else {
            this.downstream.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (!this.get()) {
            this.downstream.onNext((T)var1);
         }
      }
   }
}
