package io.reactivex.subjects;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class UnicastSubject<T> extends Subject<T> {
   final boolean delayError;
   volatile boolean disposed;
   volatile boolean done;
   final AtomicReference<Observer<? super T>> downstream;
   boolean enableOperatorFusion;
   Throwable error;
   final AtomicReference<Runnable> onTerminate;
   final AtomicBoolean once;
   final SpscLinkedArrayQueue<T> queue;
   final BasicIntQueueDisposable<T> wip;

   UnicastSubject(int var1, Runnable var2) {
      this(var1, var2, true);
   }

   UnicastSubject(int var1, Runnable var2, boolean var3) {
      this.queue = new SpscLinkedArrayQueue<>(ObjectHelper.verifyPositive(var1, "capacityHint"));
      this.onTerminate = new AtomicReference<>(ObjectHelper.requireNonNull(var2, "onTerminate"));
      this.delayError = var3;
      this.downstream = new AtomicReference<>();
      this.once = new AtomicBoolean();
      this.wip = new UnicastSubject.UnicastQueueDisposable(this);
   }

   UnicastSubject(int var1, boolean var2) {
      this.queue = new SpscLinkedArrayQueue<>(ObjectHelper.verifyPositive(var1, "capacityHint"));
      this.onTerminate = new AtomicReference<>();
      this.delayError = var2;
      this.downstream = new AtomicReference<>();
      this.once = new AtomicBoolean();
      this.wip = new UnicastSubject.UnicastQueueDisposable(this);
   }

   @CheckReturnValue
   public static <T> UnicastSubject<T> create() {
      return new UnicastSubject<>(bufferSize(), true);
   }

   @CheckReturnValue
   public static <T> UnicastSubject<T> create(int var0) {
      return new UnicastSubject<>(var0, true);
   }

   @CheckReturnValue
   public static <T> UnicastSubject<T> create(int var0, Runnable var1) {
      return new UnicastSubject<>(var0, var1, true);
   }

   @CheckReturnValue
   public static <T> UnicastSubject<T> create(int var0, Runnable var1, boolean var2) {
      return new UnicastSubject<>(var0, var1, var2);
   }

   @CheckReturnValue
   public static <T> UnicastSubject<T> create(boolean var0) {
      return new UnicastSubject<>(bufferSize(), var0);
   }

   void doTerminate() {
      Runnable var1 = this.onTerminate.get();
      if (var1 != null && ExternalSyntheticBackportWithForwarding0.m(this.onTerminate, var1, null)) {
         var1.run();
      }
   }

   void drain() {
      if (this.wip.getAndIncrement() == 0) {
         Observer var2 = this.downstream.get();

         for (int var1 = 1; var2 == null; var2 = this.downstream.get()) {
            var1 = this.wip.addAndGet(-var1);
            if (var1 == 0) {
               return;
            }
         }

         if (this.enableOperatorFusion) {
            this.drainFused(var2);
         } else {
            this.drainNormal(var2);
         }
      }
   }

   void drainFused(Observer<? super T> var1) {
      SpscLinkedArrayQueue var6 = this.queue;
      boolean var4 = this.delayError;
      int var2 = 1;

      while (!this.disposed) {
         boolean var5 = this.done;
         if (!var4 && var5 && this.failedFast(var6, var1)) {
            return;
         }

         var1.onNext(null);
         if (var5) {
            this.errorOrComplete(var1);
            return;
         }

         int var3 = this.wip.addAndGet(-var2);
         var2 = var3;
         if (var3 == 0) {
            return;
         }
      }

      this.downstream.lazySet(null);
   }

   void drainNormal(Observer<? super T> var1) {
      SpscLinkedArrayQueue var9 = this.queue;
      boolean var6 = this.delayError;
      boolean var2 = true;
      int var4 = 1;

      while (!this.disposed) {
         boolean var7 = this.done;
         Object var8 = this.queue.poll();
         boolean var5;
         if (var8 == null) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         boolean var3 = var2;
         if (var7) {
            var3 = var2;
            if (!var6) {
               var3 = var2;
               if (var2) {
                  if (this.failedFast(var9, var1)) {
                     return;
                  }

                  var3 = false;
               }
            }

            if (var5) {
               this.errorOrComplete(var1);
               return;
            }
         }

         if (var5) {
            var5 = this.wip.addAndGet(-var4);
            var2 = var3;
            var4 = var5;
            if (var5 == 0) {
               return;
            }
         } else {
            var1.onNext(var8);
            var2 = var3;
         }
      }

      this.downstream.lazySet(null);
      var9.clear();
   }

   void errorOrComplete(Observer<? super T> var1) {
      this.downstream.lazySet(null);
      Throwable var2 = this.error;
      if (var2 != null) {
         var1.onError(var2);
      } else {
         var1.onComplete();
      }
   }

   boolean failedFast(SimpleQueue<T> var1, Observer<? super T> var2) {
      Throwable var3 = this.error;
      if (var3 != null) {
         this.downstream.lazySet(null);
         var1.clear();
         var2.onError(var3);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public Throwable getThrowable() {
      return this.done ? this.error : null;
   }

   @Override
   public boolean hasComplete() {
      boolean var1;
      if (this.done && this.error == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasObservers() {
      boolean var1;
      if (this.downstream.get() != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasThrowable() {
      boolean var1;
      if (this.done && this.error != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void onComplete() {
      if (!this.done && !this.disposed) {
         this.done = true;
         this.doTerminate();
         this.drain();
      }
   }

   @Override
   public void onError(Throwable var1) {
      ObjectHelper.requireNonNull(var1, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      if (!this.done && !this.disposed) {
         this.error = var1;
         this.done = true;
         this.doTerminate();
         this.drain();
      } else {
         RxJavaPlugins.onError(var1);
      }
   }

   @Override
   public void onNext(T var1) {
      ObjectHelper.requireNonNull(var1, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
      if (!this.done && !this.disposed) {
         this.queue.offer((T)var1);
         this.drain();
      }
   }

   @Override
   public void onSubscribe(Disposable var1) {
      if (this.done || this.disposed) {
         var1.dispose();
      }
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      if (!this.once.get() && this.once.compareAndSet(false, true)) {
         var1.onSubscribe(this.wip);
         this.downstream.lazySet(var1);
         if (this.disposed) {
            this.downstream.lazySet(null);
            return;
         }

         this.drain();
      } else {
         EmptyDisposable.error(new IllegalStateException("Only a single observer allowed."), var1);
      }
   }

   final class UnicastQueueDisposable extends BasicIntQueueDisposable<T> {
      private static final long serialVersionUID = 7926949470189395511L;
      final UnicastSubject this$0;

      UnicastQueueDisposable(UnicastSubject var1) {
         this.this$0 = var1;
      }

      @Override
      public void clear() {
         this.this$0.queue.clear();
      }

      @Override
      public void dispose() {
         if (!this.this$0.disposed) {
            this.this$0.disposed = true;
            this.this$0.doTerminate();
            this.this$0.downstream.lazySet(null);
            if (this.this$0.wip.getAndIncrement() == 0) {
               this.this$0.downstream.lazySet(null);
               if (!this.this$0.enableOperatorFusion) {
                  this.this$0.queue.clear();
               }
            }
         }
      }

      @Override
      public boolean isDisposed() {
         return this.this$0.disposed;
      }

      @Override
      public boolean isEmpty() {
         return this.this$0.queue.isEmpty();
      }

      @Override
      public T poll() throws Exception {
         return this.this$0.queue.poll();
      }

      @Override
      public int requestFusion(int var1) {
         if ((var1 & 2) != 0) {
            this.this$0.enableOperatorFusion = true;
            return 2;
         } else {
            return 0;
         }
      }
   }
}
