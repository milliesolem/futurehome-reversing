package io.reactivex.subjects;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.DeferredScalarDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public final class AsyncSubject<T> extends Subject<T> {
   static final AsyncSubject.AsyncDisposable[] EMPTY = new AsyncSubject.AsyncDisposable[0];
   static final AsyncSubject.AsyncDisposable[] TERMINATED = new AsyncSubject.AsyncDisposable[0];
   Throwable error;
   final AtomicReference<AsyncSubject.AsyncDisposable<T>[]> subscribers = new AtomicReference<>(EMPTY);
   T value;

   AsyncSubject() {
   }

   @CheckReturnValue
   public static <T> AsyncSubject<T> create() {
      return new AsyncSubject<>();
   }

   boolean add(AsyncSubject.AsyncDisposable<T> var1) {
      AsyncSubject.AsyncDisposable[] var3;
      AsyncSubject.AsyncDisposable[] var4;
      do {
         var4 = this.subscribers.get();
         if (var4 == TERMINATED) {
            return false;
         }

         int var2 = var4.length;
         var3 = new AsyncSubject.AsyncDisposable[var2 + 1];
         System.arraycopy(var4, 0, var3, 0, var2);
         var3[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var4, var3));

      return true;
   }

   @Override
   public Throwable getThrowable() {
      Throwable var1;
      if (this.subscribers.get() == TERMINATED) {
         var1 = this.error;
      } else {
         var1 = null;
      }

      return var1;
   }

   public T getValue() {
      Object var1;
      if (this.subscribers.get() == TERMINATED) {
         var1 = this.value;
      } else {
         var1 = null;
      }

      return (T)var1;
   }

   @Deprecated
   public Object[] getValues() {
      Object var2 = this.getValue();
      Object[] var1;
      if (var2 != null) {
         var1 = new Object[]{var2};
      } else {
         var1 = new Object[0];
      }

      return var1;
   }

   @Deprecated
   public T[] getValues(T[] var1) {
      Object var3 = this.getValue();
      if (var3 == null) {
         if (var1.length != 0) {
            var1[0] = null;
         }

         return (T[])var1;
      } else {
         Object[] var2 = var1;
         if (var1.length == 0) {
            var2 = Arrays.copyOf(var1, 1);
         }

         var2[0] = var3;
         if (var2.length != 1) {
            var2[1] = null;
         }

         return (T[])var2;
      }
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
      if (((AsyncSubject.AsyncDisposable[])this.subscribers.get()).length != 0) {
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

   public boolean hasValue() {
      boolean var1;
      if (this.subscribers.get() == TERMINATED && this.value != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void onComplete() {
      Object var5 = this.subscribers.get();
      AsyncSubject.AsyncDisposable[] var4 = TERMINATED;
      if (var5 != var4) {
         var5 = this.value;
         var4 = this.subscribers.getAndSet(var4);
         byte var2 = 0;
         int var1 = 0;
         if (var5 == null) {
            for (int var7 = var4.length; var1 < var7; var1++) {
               var4[var1].onComplete();
            }
         } else {
            int var3 = var4.length;

            for (int var6 = var2; var6 < var3; var6++) {
               var4[var6].complete(var5);
            }
         }
      }
   }

   @Override
   public void onError(Throwable var1) {
      ObjectHelper.requireNonNull(var1, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      Object var4 = this.subscribers.get();
      AsyncSubject.AsyncDisposable[] var5 = TERMINATED;
      if (var4 == var5) {
         RxJavaPlugins.onError(var1);
      } else {
         this.value = null;
         this.error = var1;
         var4 = this.subscribers.getAndSet(var5);
         int var3 = ((Object[])var4).length;

         for (int var2 = 0; var2 < var3; var2++) {
            ((AsyncSubject.AsyncDisposable)((Object[])var4)[var2]).onError(var1);
         }
      }
   }

   @Override
   public void onNext(T var1) {
      ObjectHelper.requireNonNull(var1, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
      if (this.subscribers.get() != TERMINATED) {
         this.value = (T)var1;
      }
   }

   @Override
   public void onSubscribe(Disposable var1) {
      if (this.subscribers.get() == TERMINATED) {
         var1.dispose();
      }
   }

   void remove(AsyncSubject.AsyncDisposable<T> var1) {
      AsyncSubject.AsyncDisposable[] var4;
      AsyncSubject.AsyncDisposable[] var5;
      do {
         var5 = this.subscribers.get();
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
            var4 = new AsyncSubject.AsyncDisposable[var3 - 1];
            System.arraycopy(var5, 0, var4, 0, var2);
            System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, var4));
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      AsyncSubject.AsyncDisposable var2 = new AsyncSubject.AsyncDisposable<>(var1, this);
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
            Object var4 = this.value;
            if (var4 != null) {
               var2.complete(var4);
            } else {
               var2.onComplete();
            }
         }
      }
   }

   static final class AsyncDisposable<T> extends DeferredScalarDisposable<T> {
      private static final long serialVersionUID = 5629876084736248016L;
      final AsyncSubject<T> parent;

      AsyncDisposable(Observer<? super T> var1, AsyncSubject<T> var2) {
         super(var1);
         this.parent = var2;
      }

      @Override
      public void dispose() {
         if (super.tryDispose()) {
            this.parent.remove(this);
         }
      }

      void onComplete() {
         if (!this.isDisposed()) {
            this.downstream.onComplete();
         }
      }

      void onError(Throwable var1) {
         if (this.isDisposed()) {
            RxJavaPlugins.onError(var1);
         } else {
            this.downstream.onError(var1);
         }
      }
   }
}
