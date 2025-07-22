package io.reactivex.processors;

import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class UnicastProcessor<T> extends FlowableProcessor<T> {
   volatile boolean cancelled;
   final boolean delayError;
   volatile boolean done;
   final AtomicReference<Subscriber<? super T>> downstream;
   boolean enableOperatorFusion;
   Throwable error;
   final AtomicReference<Runnable> onTerminate;
   final AtomicBoolean once;
   final SpscLinkedArrayQueue<T> queue;
   final AtomicLong requested;
   final BasicIntQueueSubscription<T> wip;

   UnicastProcessor(int var1) {
      this(var1, null, true);
   }

   UnicastProcessor(int var1, Runnable var2) {
      this(var1, var2, true);
   }

   UnicastProcessor(int var1, Runnable var2, boolean var3) {
      this.queue = new SpscLinkedArrayQueue<>(ObjectHelper.verifyPositive(var1, "capacityHint"));
      this.onTerminate = new AtomicReference<>(var2);
      this.delayError = var3;
      this.downstream = new AtomicReference<>();
      this.once = new AtomicBoolean();
      this.wip = new UnicastProcessor.UnicastQueueSubscription(this);
      this.requested = new AtomicLong();
   }

   @CheckReturnValue
   public static <T> UnicastProcessor<T> create() {
      return new UnicastProcessor<>(bufferSize());
   }

   @CheckReturnValue
   public static <T> UnicastProcessor<T> create(int var0) {
      return new UnicastProcessor<>(var0);
   }

   @CheckReturnValue
   public static <T> UnicastProcessor<T> create(int var0, Runnable var1) {
      ObjectHelper.requireNonNull(var1, "onTerminate");
      return new UnicastProcessor<>(var0, var1);
   }

   @CheckReturnValue
   public static <T> UnicastProcessor<T> create(int var0, Runnable var1, boolean var2) {
      ObjectHelper.requireNonNull(var1, "onTerminate");
      return new UnicastProcessor<>(var0, var1, var2);
   }

   @CheckReturnValue
   public static <T> UnicastProcessor<T> create(boolean var0) {
      return new UnicastProcessor<>(bufferSize(), null, var0);
   }

   boolean checkTerminated(boolean var1, boolean var2, boolean var3, Subscriber<? super T> var4, SpscLinkedArrayQueue<T> var5) {
      if (this.cancelled) {
         var5.clear();
         this.downstream.lazySet(null);
         return true;
      } else {
         if (var2) {
            if (var1 && this.error != null) {
               var5.clear();
               this.downstream.lazySet(null);
               var4.onError(this.error);
               return true;
            }

            if (var3) {
               Throwable var6 = this.error;
               this.downstream.lazySet(null);
               if (var6 != null) {
                  var4.onError(var6);
               } else {
                  var4.onComplete();
               }

               return true;
            }
         }

         return false;
      }
   }

   void doTerminate() {
      Runnable var1 = this.onTerminate.getAndSet(null);
      if (var1 != null) {
         var1.run();
      }
   }

   void drain() {
      if (this.wip.getAndIncrement() == 0) {
         Subscriber var2 = this.downstream.get();

         for (int var1 = 1; var2 == null; var2 = this.downstream.get()) {
            var1 = this.wip.addAndGet(-var1);
            if (var1 == 0) {
               return;
            }
         }

         if (this.enableOperatorFusion) {
            this.drainFused(var2);
         } else {
            this.drainRegular(var2);
         }
      }
   }

   void drainFused(Subscriber<? super T> var1) {
      SpscLinkedArrayQueue var6 = this.queue;
      boolean var4 = this.delayError;
      int var2 = 1;

      while (!this.cancelled) {
         boolean var5 = this.done;
         if (!var4 && var5 && this.error != null) {
            var6.clear();
            this.downstream.lazySet(null);
            var1.onError(this.error);
            return;
         }

         var1.onNext(null);
         if (var5) {
            this.downstream.lazySet(null);
            Throwable var7 = this.error;
            if (var7 != null) {
               var1.onError(var7);
            } else {
               var1.onComplete();
            }

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

   void drainRegular(Subscriber<? super T> var1) {
      SpscLinkedArrayQueue var12 = this.queue;
      boolean var10 = this.delayError ^ true;
      int var2 = 1;

      do {
         long var6 = this.requested.get();
         long var4 = 0L;

         int var3;
         while (true) {
            long var13;
            var3 = (var13 = var6 - var4) == 0L ? 0 : (var13 < 0L ? -1 : 1);
            if (!var3) {
               break;
            }

            boolean var9 = this.done;
            Object var11 = var12.poll();
            boolean var8;
            if (var11 == null) {
               var8 = true;
            } else {
               var8 = false;
            }

            if (this.checkTerminated(var10, var9, var8, var1, var12)) {
               return;
            }

            if (var8) {
               break;
            }

            var1.onNext(var11);
            var4++;
         }

         if (!var3 && this.checkTerminated(var10, this.done, var12.isEmpty(), var1, var12)) {
            return;
         }

         if (var4 != 0L && var6 != Long.MAX_VALUE) {
            this.requested.addAndGet(-var4);
         }

         var2 = this.wip.addAndGet(-var2);
      } while (var2 != 0);
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
   public boolean hasSubscribers() {
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

   public void onComplete() {
      if (!this.done && !this.cancelled) {
         this.done = true;
         this.doTerminate();
         this.drain();
      }
   }

   public void onError(Throwable var1) {
      ObjectHelper.requireNonNull(var1, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      if (!this.done && !this.cancelled) {
         this.error = var1;
         this.done = true;
         this.doTerminate();
         this.drain();
      } else {
         RxJavaPlugins.onError(var1);
      }
   }

   public void onNext(T var1) {
      ObjectHelper.requireNonNull(var1, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
      if (!this.done && !this.cancelled) {
         this.queue.offer((T)var1);
         this.drain();
      }
   }

   @Override
   public void onSubscribe(Subscription var1) {
      if (!this.done && !this.cancelled) {
         var1.request(Long.MAX_VALUE);
      } else {
         var1.cancel();
      }
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      if (!this.once.get() && this.once.compareAndSet(false, true)) {
         var1.onSubscribe(this.wip);
         this.downstream.set(var1);
         if (this.cancelled) {
            this.downstream.lazySet(null);
         } else {
            this.drain();
         }
      } else {
         EmptySubscription.error(new IllegalStateException("This processor allows only a single Subscriber"), var1);
      }
   }

   final class UnicastQueueSubscription extends BasicIntQueueSubscription<T> {
      private static final long serialVersionUID = -4896760517184205454L;
      final UnicastProcessor this$0;

      UnicastQueueSubscription(UnicastProcessor var1) {
         this.this$0 = var1;
      }

      public void cancel() {
         if (!this.this$0.cancelled) {
            this.this$0.cancelled = true;
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
      public void clear() {
         this.this$0.queue.clear();
      }

      @Override
      public boolean isEmpty() {
         return this.this$0.queue.isEmpty();
      }

      @Override
      public T poll() {
         return this.this$0.queue.poll();
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.this$0.requested, var1);
            this.this$0.drain();
         }
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
