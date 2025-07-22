package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableObserveOn<T> extends AbstractFlowableWithUpstream<T, T> {
   final boolean delayError;
   final int prefetch;
   final Scheduler scheduler;

   public FlowableObserveOn(Flowable<T> var1, Scheduler var2, boolean var3, int var4) {
      super(var1);
      this.scheduler = var2;
      this.delayError = var3;
      this.prefetch = var4;
   }

   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      Scheduler.Worker var2 = this.scheduler.createWorker();
      if (var1 instanceof ConditionalSubscriber) {
         this.source
            .subscribe(new FlowableObserveOn.ObserveOnConditionalSubscriber<>((ConditionalSubscriber<? super T>)var1, var2, this.delayError, this.prefetch));
      } else {
         this.source.subscribe(new FlowableObserveOn.ObserveOnSubscriber<>(var1, var2, this.delayError, this.prefetch));
      }
   }

   abstract static class BaseObserveOnSubscriber<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T>, Runnable {
      private static final long serialVersionUID = -8241002408341274697L;
      volatile boolean cancelled;
      final boolean delayError;
      volatile boolean done;
      Throwable error;
      final int limit;
      boolean outputFused;
      final int prefetch;
      long produced;
      SimpleQueue<T> queue;
      final AtomicLong requested;
      int sourceMode;
      Subscription upstream;
      final Scheduler.Worker worker;

      BaseObserveOnSubscriber(Scheduler.Worker var1, boolean var2, int var3) {
         this.worker = var1;
         this.delayError = var2;
         this.prefetch = var3;
         this.requested = new AtomicLong();
         this.limit = var3 - (var3 >> 2);
      }

      public final void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.cancel();
            this.worker.dispose();
            if (!this.outputFused && this.getAndIncrement() == 0) {
               this.queue.clear();
            }
         }
      }

      final boolean checkTerminated(boolean var1, boolean var2, Subscriber<?> var3) {
         if (this.cancelled) {
            this.clear();
            return true;
         } else {
            if (var1) {
               if (this.delayError) {
                  if (var2) {
                     this.cancelled = true;
                     Throwable var4 = this.error;
                     if (var4 != null) {
                        var3.onError(var4);
                     } else {
                        var3.onComplete();
                     }

                     this.worker.dispose();
                     return true;
                  }
               } else {
                  Throwable var5 = this.error;
                  if (var5 != null) {
                     this.cancelled = true;
                     this.clear();
                     var3.onError(var5);
                     this.worker.dispose();
                     return true;
                  }

                  if (var2) {
                     this.cancelled = true;
                     var3.onComplete();
                     this.worker.dispose();
                     return true;
                  }
               }
            }

            return false;
         }
      }

      @Override
      public final void clear() {
         this.queue.clear();
      }

      @Override
      public final boolean isEmpty() {
         return this.queue.isEmpty();
      }

      public final void onComplete() {
         if (!this.done) {
            this.done = true;
            this.trySchedule();
         }
      }

      public final void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.error = var1;
            this.done = true;
            this.trySchedule();
         }
      }

      public final void onNext(T var1) {
         if (!this.done) {
            if (this.sourceMode == 2) {
               this.trySchedule();
            } else {
               if (!this.queue.offer((T)var1)) {
                  this.upstream.cancel();
                  this.error = new MissingBackpressureException("Queue is full?!");
                  this.done = true;
               }

               this.trySchedule();
            }
         }
      }

      public final void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.trySchedule();
         }
      }

      @Override
      public final int requestFusion(int var1) {
         if ((var1 & 2) != 0) {
            this.outputFused = true;
            return 2;
         } else {
            return 0;
         }
      }

      @Override
      public final void run() {
         if (this.outputFused) {
            this.runBackfused();
         } else if (this.sourceMode == 1) {
            this.runSync();
         } else {
            this.runAsync();
         }
      }

      abstract void runAsync();

      abstract void runBackfused();

      abstract void runSync();

      final void trySchedule() {
         if (this.getAndIncrement() == 0) {
            this.worker.schedule(this);
         }
      }
   }

   static final class ObserveOnConditionalSubscriber<T> extends FlowableObserveOn.BaseObserveOnSubscriber<T> {
      private static final long serialVersionUID = 644624475404284533L;
      long consumed;
      final ConditionalSubscriber<? super T> downstream;

      ObserveOnConditionalSubscriber(ConditionalSubscriber<? super T> var1, Scheduler.Worker var2, boolean var3, int var4) {
         super(var2, var3, var4);
         this.downstream = var1;
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            if (var1 instanceof QueueSubscription) {
               QueueSubscription var3 = (QueueSubscription)var1;
               int var2 = var3.requestFusion(7);
               if (var2 == 1) {
                  this.sourceMode = 1;
                  this.queue = var3;
                  this.done = true;
                  this.downstream.onSubscribe(this);
                  return;
               }

               if (var2 == 2) {
                  this.sourceMode = 2;
                  this.queue = var3;
                  this.downstream.onSubscribe(this);
                  var1.request(this.prefetch);
                  return;
               }
            }

            this.queue = new SpscArrayQueue<>(this.prefetch);
            this.downstream.onSubscribe(this);
            var1.request(this.prefetch);
         }
      }

      @Override
      public T poll() throws Exception {
         Object var3 = this.queue.poll();
         if (var3 != null && this.sourceMode != 1) {
            long var1 = this.consumed + 1L;
            if (var1 == this.limit) {
               this.consumed = 0L;
               this.upstream.request(var1);
            } else {
               this.consumed = var1;
            }
         }

         return (T)var3;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      void runAsync() {
         ConditionalSubscriber var15 = this.downstream;
         SimpleQueue var16 = this.queue;
         long var5 = this.produced;
         long var3 = this.consumed;
         int var1 = 1;

         while (true) {
            long var11 = this.requested.get();

            int var2;
            while (true) {
               long var22;
               var2 = (var22 = var5 - var11) == 0L ? 0 : (var22 < 0L ? -1 : 1);
               if (!var2) {
                  break;
               }

               boolean var14 = this.done;

               Object var17;
               try {
                  var17 = var16.poll();
               } catch (Throwable var19) {
                  Exceptions.throwIfFatal(var19);
                  this.cancelled = true;
                  this.upstream.cancel();
                  var16.clear();
                  var15.onError(var19);
                  this.worker.dispose();
                  return;
               }

               boolean var13;
               if (var17 == null) {
                  var13 = true;
               } else {
                  var13 = false;
               }

               if (this.checkTerminated(var14, var13, var15)) {
                  return;
               }

               if (var13) {
                  break;
               }

               long var7 = var5;
               if (var15.tryOnNext(var17)) {
                  var7 = var5 + 1L;
               }

               long var9 = var3 + 1L;
               var5 = var7;
               var3 = var9;
               if (var9 == this.limit) {
                  this.upstream.request(var9);
                  var3 = 0L;
                  var5 = var7;
               }
            }

            if (!var2 && this.checkTerminated(this.done, var16.isEmpty(), var15)) {
               return;
            }

            var2 = this.get();
            if (var1 == var2) {
               this.produced = var5;
               this.consumed = var3;
               var2 = this.addAndGet(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else {
               var1 = var2;
            }
         }
      }

      @Override
      void runBackfused() {
         int var1 = 1;

         while (!this.cancelled) {
            boolean var3 = this.done;
            this.downstream.onNext(null);
            if (var3) {
               this.cancelled = true;
               Throwable var4 = this.error;
               if (var4 != null) {
                  this.downstream.onError(var4);
               } else {
                  this.downstream.onComplete();
               }

               this.worker.dispose();
               return;
            }

            int var2 = this.addAndGet(-var1);
            var1 = var2;
            if (var2 == 0) {
               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      void runSync() {
         ConditionalSubscriber var7 = this.downstream;
         SimpleQueue var8 = this.queue;
         long var3 = this.produced;
         int var1 = 1;

         while (true) {
            long var5 = this.requested.get();

            while (var3 != var5) {
               Object var9;
               try {
                  var9 = var8.poll();
               } catch (Throwable var11) {
                  Exceptions.throwIfFatal(var11);
                  this.cancelled = true;
                  this.upstream.cancel();
                  var7.onError(var11);
                  this.worker.dispose();
                  return;
               }

               if (this.cancelled) {
                  return;
               }

               if (var9 == null) {
                  this.cancelled = true;
                  var7.onComplete();
                  this.worker.dispose();
                  return;
               }

               if (var7.tryOnNext(var9)) {
                  var3++;
               }
            }

            if (this.cancelled) {
               return;
            }

            if (var8.isEmpty()) {
               this.cancelled = true;
               var7.onComplete();
               this.worker.dispose();
               return;
            }

            int var2 = this.get();
            if (var1 == var2) {
               this.produced = var3;
               var2 = this.addAndGet(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else {
               var1 = var2;
            }
         }
      }
   }

   static final class ObserveOnSubscriber<T> extends FlowableObserveOn.BaseObserveOnSubscriber<T> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -4547113800637756442L;
      final Subscriber<? super T> downstream;

      ObserveOnSubscriber(Subscriber<? super T> var1, Scheduler.Worker var2, boolean var3, int var4) {
         super(var2, var3, var4);
         this.downstream = var1;
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            if (var1 instanceof QueueSubscription) {
               QueueSubscription var3 = (QueueSubscription)var1;
               int var2 = var3.requestFusion(7);
               if (var2 == 1) {
                  this.sourceMode = 1;
                  this.queue = var3;
                  this.done = true;
                  this.downstream.onSubscribe(this);
                  return;
               }

               if (var2 == 2) {
                  this.sourceMode = 2;
                  this.queue = var3;
                  this.downstream.onSubscribe(this);
                  var1.request(this.prefetch);
                  return;
               }
            }

            this.queue = new SpscArrayQueue<>(this.prefetch);
            this.downstream.onSubscribe(this);
            var1.request(this.prefetch);
         }
      }

      @Override
      public T poll() throws Exception {
         Object var3 = this.queue.poll();
         if (var3 != null && this.sourceMode != 1) {
            long var1 = this.produced + 1L;
            if (var1 == this.limit) {
               this.produced = 0L;
               this.upstream.request(var1);
            } else {
               this.produced = var1;
            }
         }

         return (T)var3;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      void runAsync() {
         Subscriber var14 = this.downstream;
         SimpleQueue var13 = this.queue;
         long var3 = this.produced;
         int var1 = 1;

         while (true) {
            long var5 = this.requested.get();

            int var2;
            while (true) {
               long var20;
               var2 = (var20 = var3 - var5) == 0L ? 0 : (var20 < 0L ? -1 : 1);
               if (!var2) {
                  break;
               }

               boolean var12 = this.done;

               Object var15;
               try {
                  var15 = var13.poll();
               } catch (Throwable var17) {
                  Exceptions.throwIfFatal(var17);
                  this.cancelled = true;
                  this.upstream.cancel();
                  var13.clear();
                  var14.onError(var17);
                  this.worker.dispose();
                  return;
               }

               boolean var11;
               if (var15 == null) {
                  var11 = true;
               } else {
                  var11 = false;
               }

               if (this.checkTerminated(var12, var11, var14)) {
                  return;
               }

               if (var11) {
                  break;
               }

               var14.onNext(var15);
               long var9 = var3 + 1L;
               var3 = var9;
               if (var9 == this.limit) {
                  long var7 = var5;
                  if (var5 != Long.MAX_VALUE) {
                     var7 = this.requested.addAndGet(-var9);
                  }

                  this.upstream.request(var9);
                  var3 = 0L;
                  var5 = var7;
               }
            }

            if (!var2 && this.checkTerminated(this.done, var13.isEmpty(), var14)) {
               return;
            }

            var2 = this.get();
            if (var1 == var2) {
               this.produced = var3;
               var2 = this.addAndGet(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else {
               var1 = var2;
            }
         }
      }

      @Override
      void runBackfused() {
         int var1 = 1;

         while (!this.cancelled) {
            boolean var3 = this.done;
            this.downstream.onNext(null);
            if (var3) {
               this.cancelled = true;
               Throwable var4 = this.error;
               if (var4 != null) {
                  this.downstream.onError(var4);
               } else {
                  this.downstream.onComplete();
               }

               this.worker.dispose();
               return;
            }

            int var2 = this.addAndGet(-var1);
            var1 = var2;
            if (var2 == 0) {
               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      void runSync() {
         Subscriber var7 = this.downstream;
         SimpleQueue var8 = this.queue;
         long var3 = this.produced;
         int var1 = 1;

         while (true) {
            for (long var5 = this.requested.get(); var3 != var5; var3++) {
               Object var9;
               try {
                  var9 = var8.poll();
               } catch (Throwable var11) {
                  Exceptions.throwIfFatal(var11);
                  this.cancelled = true;
                  this.upstream.cancel();
                  var7.onError(var11);
                  this.worker.dispose();
                  return;
               }

               if (this.cancelled) {
                  return;
               }

               if (var9 == null) {
                  this.cancelled = true;
                  var7.onComplete();
                  this.worker.dispose();
                  return;
               }

               var7.onNext(var9);
            }

            if (this.cancelled) {
               return;
            }

            if (var8.isEmpty()) {
               this.cancelled = true;
               var7.onComplete();
               this.worker.dispose();
               return;
            }

            int var2 = this.get();
            if (var1 == var2) {
               this.produced = var3;
               var2 = this.addAndGet(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else {
               var1 = var2;
            }
         }
      }
   }
}
