package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindowTimed<T> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
   final int bufferSize;
   final long maxSize;
   final boolean restartTimerOnMaxSize;
   final Scheduler scheduler;
   final long timeskip;
   final long timespan;
   final TimeUnit unit;

   public FlowableWindowTimed(Flowable<T> var1, long var2, long var4, TimeUnit var6, Scheduler var7, long var8, int var10, boolean var11) {
      super(var1);
      this.timespan = var2;
      this.timeskip = var4;
      this.unit = var6;
      this.scheduler = var7;
      this.maxSize = var8;
      this.bufferSize = var10;
      this.restartTimerOnMaxSize = var11;
   }

   @Override
   protected void subscribeActual(Subscriber<? super Flowable<T>> var1) {
      SerializedSubscriber var2 = new SerializedSubscriber(var1);
      if (this.timespan == this.timeskip) {
         if (this.maxSize == Long.MAX_VALUE) {
            this.source.subscribe(new FlowableWindowTimed.WindowExactUnboundedSubscriber<>(var2, this.timespan, this.unit, this.scheduler, this.bufferSize));
         } else {
            this.source
               .subscribe(
                  new FlowableWindowTimed.WindowExactBoundedSubscriber<>(
                     var2, this.timespan, this.unit, this.scheduler, this.bufferSize, this.maxSize, this.restartTimerOnMaxSize
                  )
               );
         }
      } else {
         this.source
            .subscribe(
               new FlowableWindowTimed.WindowSkipSubscriber<>(var2, this.timespan, this.timeskip, this.unit, this.scheduler.createWorker(), this.bufferSize)
            );
      }
   }

   static final class WindowExactBoundedSubscriber<T> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Subscription {
      final int bufferSize;
      long count;
      final long maxSize;
      long producerIndex;
      final boolean restartTimerOnMaxSize;
      final Scheduler scheduler;
      volatile boolean terminated;
      final SequentialDisposable timer = new SequentialDisposable();
      final long timespan;
      final TimeUnit unit;
      Subscription upstream;
      UnicastProcessor<T> window;
      final Scheduler.Worker worker;

      WindowExactBoundedSubscriber(Subscriber<? super Flowable<T>> var1, long var2, TimeUnit var4, Scheduler var5, int var6, long var7, boolean var9) {
         super(var1, new MpscLinkedQueue<>());
         this.timespan = var2;
         this.unit = var4;
         this.scheduler = var5;
         this.bufferSize = var6;
         this.maxSize = var7;
         this.restartTimerOnMaxSize = var9;
         if (var9) {
            this.worker = var5.createWorker();
         } else {
            this.worker = null;
         }
      }

      public void cancel() {
         this.cancelled = true;
      }

      public void disposeTimer() {
         this.timer.dispose();
         Scheduler.Worker var1 = this.worker;
         if (var1 != null) {
            var1.dispose();
         }
      }

      void drainLoop() {
         SimplePlainQueue var10 = this.queue;
         Subscriber var9 = this.downstream;
         UnicastProcessor var8 = this.window;
         int var1 = 1;

         while (!this.terminated) {
            boolean var3 = this.done;
            Object var7 = var10.poll();
            boolean var2;
            if (var7 == null) {
               var2 = 1;
            } else {
               var2 = 0;
            }

            boolean var4 = var7 instanceof FlowableWindowTimed.WindowExactBoundedSubscriber.ConsumerIndexHolder;
            if (var3 && (var2 || var4)) {
               this.window = null;
               var10.clear();
               var7 = this.error;
               if (var7 != null) {
                  var8.onError((Throwable)var7);
               } else {
                  var8.onComplete();
               }

               this.disposeTimer();
               return;
            }

            if (var2) {
               var2 = this.leave(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else {
               label57:
               if (var4) {
                  FlowableWindowTimed.WindowExactBoundedSubscriber.ConsumerIndexHolder var11 = (FlowableWindowTimed.WindowExactBoundedSubscriber.ConsumerIndexHolder)var7;
                  if (this.restartTimerOnMaxSize) {
                     var7 = var8;
                     if (this.producerIndex != var11.index) {
                        break label57;
                     }
                  }

                  var8.onComplete();
                  this.count = 0L;
                  var8 = UnicastProcessor.create(this.bufferSize);
                  this.window = var8;
                  long var5 = this.requested();
                  if (var5 == 0L) {
                     this.window = null;
                     this.queue.clear();
                     this.upstream.cancel();
                     var9.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
                     this.disposeTimer();
                     return;
                  }

                  var9.onNext(var8);
                  var7 = var8;
                  if (var5 != Long.MAX_VALUE) {
                     this.produced(1L);
                     var7 = var8;
                  }
               } else {
                  var8.onNext(NotificationLite.getValue(var7));
                  long var13 = this.count + 1L;
                  if (var13 >= this.maxSize) {
                     this.producerIndex++;
                     this.count = 0L;
                     var8.onComplete();
                     var13 = this.requested();
                     if (var13 == 0L) {
                        this.window = null;
                        this.upstream.cancel();
                        this.downstream.onError(new MissingBackpressureException("Could not deliver window due to lack of requests"));
                        this.disposeTimer();
                        return;
                     }

                     var7 = UnicastProcessor.create(this.bufferSize);
                     this.window = (UnicastProcessor<T>)var7;
                     this.downstream.onNext(var7);
                     if (var13 != Long.MAX_VALUE) {
                        this.produced(1L);
                     }

                     if (this.restartTimerOnMaxSize) {
                        this.timer.get().dispose();
                        Scheduler.Worker var19 = this.worker;
                        FlowableWindowTimed.WindowExactBoundedSubscriber.ConsumerIndexHolder var21 = new FlowableWindowTimed.WindowExactBoundedSubscriber.ConsumerIndexHolder(
                           this.producerIndex, this
                        );
                        var13 = this.timespan;
                        Disposable var20 = var19.schedulePeriodically(var21, var13, var13, this.unit);
                        this.timer.replace(var20);
                     }
                  } else {
                     this.count = var13;
                     var7 = var8;
                  }
               }

               var8 = (UnicastProcessor)var7;
            }
         }

         this.upstream.cancel();
         var10.clear();
         this.disposeTimer();
      }

      public void onComplete() {
         this.done = true;
         if (this.enter()) {
            this.drainLoop();
         }

         this.downstream.onComplete();
      }

      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         if (this.enter()) {
            this.drainLoop();
         }

         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         if (!this.terminated) {
            if (this.fastEnter()) {
               UnicastProcessor var4 = this.window;
               var4.onNext(var1);
               long var2 = this.count + 1L;
               if (var2 >= this.maxSize) {
                  this.producerIndex++;
                  this.count = 0L;
                  var4.onComplete();
                  var2 = this.requested();
                  if (var2 == 0L) {
                     this.window = null;
                     this.upstream.cancel();
                     this.downstream.onError(new MissingBackpressureException("Could not deliver window due to lack of requests"));
                     this.disposeTimer();
                     return;
                  }

                  var1 = UnicastProcessor.create(this.bufferSize);
                  this.window = var1;
                  this.downstream.onNext(var1);
                  if (var2 != Long.MAX_VALUE) {
                     this.produced(1L);
                  }

                  if (this.restartTimerOnMaxSize) {
                     this.timer.get().dispose();
                     Scheduler.Worker var6 = this.worker;
                     FlowableWindowTimed.WindowExactBoundedSubscriber.ConsumerIndexHolder var10 = new FlowableWindowTimed.WindowExactBoundedSubscriber.ConsumerIndexHolder(
                        this.producerIndex, this
                     );
                     var2 = this.timespan;
                     Disposable var7 = var6.schedulePeriodically(var10, var2, var2, this.unit);
                     this.timer.replace(var7);
                  }
               } else {
                  this.count = var2;
               }

               if (this.leave(-1) == 0) {
                  return;
               }
            } else {
               this.queue.offer(NotificationLite.next(var1));
               if (!this.enter()) {
                  return;
               }
            }

            this.drainLoop();
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            Subscriber var4 = this.downstream;
            var4.onSubscribe(this);
            if (this.cancelled) {
               return;
            }

            UnicastProcessor var5 = UnicastProcessor.create(this.bufferSize);
            this.window = var5;
            long var2 = this.requested();
            if (var2 != 0L) {
               var4.onNext(var5);
               if (var2 != Long.MAX_VALUE) {
                  this.produced(1L);
               }

               FlowableWindowTimed.WindowExactBoundedSubscriber.ConsumerIndexHolder var8 = new FlowableWindowTimed.WindowExactBoundedSubscriber.ConsumerIndexHolder(
                  this.producerIndex, this
               );
               Disposable var9;
               if (this.restartTimerOnMaxSize) {
                  Scheduler.Worker var10 = this.worker;
                  var2 = this.timespan;
                  var9 = var10.schedulePeriodically(var8, var2, var2, this.unit);
               } else {
                  Scheduler var11 = this.scheduler;
                  var2 = this.timespan;
                  var9 = var11.schedulePeriodicallyDirect(var8, var2, var2, this.unit);
               }

               if (this.timer.replace(var9)) {
                  var1.request(Long.MAX_VALUE);
               }
            } else {
               this.cancelled = true;
               var1.cancel();
               var4.onError(new MissingBackpressureException("Could not deliver initial window due to lack of requests."));
            }
         }
      }

      public void request(long var1) {
         this.requested(var1);
      }

      static final class ConsumerIndexHolder implements Runnable {
         final long index;
         final FlowableWindowTimed.WindowExactBoundedSubscriber<?> parent;

         ConsumerIndexHolder(long var1, FlowableWindowTimed.WindowExactBoundedSubscriber<?> var3) {
            this.index = var1;
            this.parent = var3;
         }

         @Override
         public void run() {
            FlowableWindowTimed.WindowExactBoundedSubscriber var1 = this.parent;
            if (!var1.cancelled) {
               var1.queue.offer(this);
            } else {
               var1.terminated = true;
            }

            if (var1.enter()) {
               var1.drainLoop();
            }
         }
      }
   }

   static final class WindowExactUnboundedSubscriber<T>
      extends QueueDrainSubscriber<T, Object, Flowable<T>>
      implements FlowableSubscriber<T>,
      Subscription,
      Runnable {
      static final Object NEXT = new Object();
      final int bufferSize;
      final Scheduler scheduler;
      volatile boolean terminated;
      final SequentialDisposable timer = new SequentialDisposable();
      final long timespan;
      final TimeUnit unit;
      Subscription upstream;
      UnicastProcessor<T> window;

      WindowExactUnboundedSubscriber(Subscriber<? super Flowable<T>> var1, long var2, TimeUnit var4, Scheduler var5, int var6) {
         super(var1, new MpscLinkedQueue<>());
         this.timespan = var2;
         this.unit = var4;
         this.scheduler = var5;
         this.bufferSize = var6;
      }

      public void cancel() {
         this.cancelled = true;
      }

      void drainLoop() {
         SimplePlainQueue var9 = this.queue;
         Subscriber var10 = this.downstream;
         UnicastProcessor var7 = this.window;
         int var1 = 1;

         while (true) {
            boolean var6 = this.terminated;
            boolean var5 = this.done;
            Object var8 = var9.poll();
            if (var5 && (var8 == null || var8 == NEXT)) {
               this.window = null;
               var9.clear();
               var8 = this.error;
               if (var8 != null) {
                  var7.onError((Throwable)var8);
               } else {
                  var7.onComplete();
               }

               this.timer.dispose();
               return;
            }

            if (var8 == null) {
               int var2 = this.leave(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else if (var8 == NEXT) {
               var7.onComplete();
               if (!var6) {
                  var8 = UnicastProcessor.create(this.bufferSize);
                  this.window = (UnicastProcessor<T>)var8;
                  long var3 = this.requested();
                  if (var3 == 0L) {
                     this.window = null;
                     this.queue.clear();
                     this.upstream.cancel();
                     var10.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
                     this.timer.dispose();
                     return;
                  }

                  var10.onNext(var8);
                  var7 = (UnicastProcessor)var8;
                  if (var3 != Long.MAX_VALUE) {
                     this.produced(1L);
                     var7 = (UnicastProcessor)var8;
                  }
               } else {
                  this.upstream.cancel();
               }
            } else {
               var7.onNext(NotificationLite.getValue(var8));
            }
         }
      }

      public void onComplete() {
         this.done = true;
         if (this.enter()) {
            this.drainLoop();
         }

         this.downstream.onComplete();
      }

      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         if (this.enter()) {
            this.drainLoop();
         }

         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         if (!this.terminated) {
            if (this.fastEnter()) {
               this.window.onNext((T)var1);
               if (this.leave(-1) == 0) {
                  return;
               }
            } else {
               this.queue.offer(NotificationLite.next(var1));
               if (!this.enter()) {
                  return;
               }
            }

            this.drainLoop();
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.window = UnicastProcessor.create(this.bufferSize);
            Subscriber var4 = this.downstream;
            var4.onSubscribe(this);
            long var2 = this.requested();
            if (var2 != 0L) {
               var4.onNext(this.window);
               if (var2 != Long.MAX_VALUE) {
                  this.produced(1L);
               }

               if (!this.cancelled) {
                  SequentialDisposable var5 = this.timer;
                  Scheduler var7 = this.scheduler;
                  var2 = this.timespan;
                  if (var5.replace(var7.schedulePeriodicallyDirect(this, var2, var2, this.unit))) {
                     var1.request(Long.MAX_VALUE);
                  }
               }
            } else {
               this.cancelled = true;
               var1.cancel();
               var4.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
            }
         }
      }

      public void request(long var1) {
         this.requested(var1);
      }

      @Override
      public void run() {
         if (this.cancelled) {
            this.terminated = true;
         }

         this.queue.offer(NEXT);
         if (this.enter()) {
            this.drainLoop();
         }
      }
   }

   static final class WindowSkipSubscriber<T> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Subscription, Runnable {
      final int bufferSize;
      volatile boolean terminated;
      final long timeskip;
      final long timespan;
      final TimeUnit unit;
      Subscription upstream;
      final List<UnicastProcessor<T>> windows;
      final Scheduler.Worker worker;

      WindowSkipSubscriber(Subscriber<? super Flowable<T>> var1, long var2, long var4, TimeUnit var6, Scheduler.Worker var7, int var8) {
         super(var1, new MpscLinkedQueue<>());
         this.timespan = var2;
         this.timeskip = var4;
         this.unit = var6;
         this.worker = var7;
         this.bufferSize = var8;
         this.windows = new LinkedList<>();
      }

      public void cancel() {
         this.cancelled = true;
      }

      void complete(UnicastProcessor<T> var1) {
         this.queue.offer(new FlowableWindowTimed.WindowSkipSubscriber.SubjectWork(var1, false));
         if (this.enter()) {
            this.drainLoop();
         }
      }

      void drainLoop() {
         SimplePlainQueue var8 = this.queue;
         Subscriber var9 = this.downstream;
         List var7 = this.windows;
         int var1 = 1;

         while (!this.terminated) {
            boolean var5 = this.done;
            Object var11 = var8.poll();
            boolean var2;
            if (var11 == null) {
               var2 = 1;
            } else {
               var2 = 0;
            }

            boolean var6 = var11 instanceof FlowableWindowTimed.WindowSkipSubscriber.SubjectWork;
            if (var5 && (var2 || var6)) {
               var8.clear();
               Throwable var13 = this.error;
               if (var13 != null) {
                  Iterator var15 = var7.iterator();

                  while (var15.hasNext()) {
                     ((UnicastProcessor)var15.next()).onError(var13);
                  }
               } else {
                  Iterator var14 = var7.iterator();

                  while (var14.hasNext()) {
                     ((UnicastProcessor)var14.next()).onComplete();
                  }
               }

               var7.clear();
               this.worker.dispose();
               return;
            }

            if (var2) {
               var2 = this.leave(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else if (var6) {
               FlowableWindowTimed.WindowSkipSubscriber.SubjectWork var16 = (FlowableWindowTimed.WindowSkipSubscriber.SubjectWork)var11;
               if (var16.open) {
                  if (!this.cancelled) {
                     long var3 = this.requested();
                     if (var3 != 0L) {
                        UnicastProcessor var17 = UnicastProcessor.create(this.bufferSize);
                        var7.add(var17);
                        var9.onNext(var17);
                        if (var3 != Long.MAX_VALUE) {
                           this.produced(1L);
                        }

                        this.worker.schedule(new FlowableWindowTimed.WindowSkipSubscriber.Completion(this, var17), this.timespan, this.unit);
                     } else {
                        var9.onError(new MissingBackpressureException("Can't emit window due to lack of requests"));
                     }
                  }
               } else {
                  var7.remove(var16.w);
                  var16.w.onComplete();
                  if (var7.isEmpty() && this.cancelled) {
                     this.terminated = true;
                  }
               }
            } else {
               Iterator var10 = var7.iterator();

               while (var10.hasNext()) {
                  ((UnicastProcessor)var10.next()).onNext(var11);
               }
            }
         }

         this.upstream.cancel();
         var8.clear();
         var7.clear();
         this.worker.dispose();
      }

      public void onComplete() {
         this.done = true;
         if (this.enter()) {
            this.drainLoop();
         }

         this.downstream.onComplete();
      }

      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         if (this.enter()) {
            this.drainLoop();
         }

         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         if (this.fastEnter()) {
            Iterator var2 = this.windows.iterator();

            while (var2.hasNext()) {
               ((UnicastProcessor)var2.next()).onNext(var1);
            }

            if (this.leave(-1) == 0) {
               return;
            }
         } else {
            this.queue.offer(var1);
            if (!this.enter()) {
               return;
            }
         }

         this.drainLoop();
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            if (this.cancelled) {
               return;
            }

            long var2 = this.requested();
            if (var2 != 0L) {
               UnicastProcessor var4 = UnicastProcessor.create(this.bufferSize);
               this.windows.add(var4);
               this.downstream.onNext(var4);
               if (var2 != Long.MAX_VALUE) {
                  this.produced(1L);
               }

               this.worker.schedule(new FlowableWindowTimed.WindowSkipSubscriber.Completion(this, var4), this.timespan, this.unit);
               Scheduler.Worker var6 = this.worker;
               var2 = this.timeskip;
               var6.schedulePeriodically(this, var2, var2, this.unit);
               var1.request(Long.MAX_VALUE);
            } else {
               var1.cancel();
               this.downstream.onError(new MissingBackpressureException("Could not emit the first window due to lack of requests"));
            }
         }
      }

      public void request(long var1) {
         this.requested(var1);
      }

      @Override
      public void run() {
         FlowableWindowTimed.WindowSkipSubscriber.SubjectWork var1 = new FlowableWindowTimed.WindowSkipSubscriber.SubjectWork(
            UnicastProcessor.create(this.bufferSize), true
         );
         if (!this.cancelled) {
            this.queue.offer(var1);
         }

         if (this.enter()) {
            this.drainLoop();
         }
      }

      final class Completion implements Runnable {
         private final UnicastProcessor<T> processor;
         final FlowableWindowTimed.WindowSkipSubscriber this$0;

         Completion(UnicastProcessor<T> var1, UnicastProcessor var2) {
            this.this$0 = var1;
            this.processor = var2;
         }

         @Override
         public void run() {
            this.this$0.complete(this.processor);
         }
      }

      static final class SubjectWork<T> {
         final boolean open;
         final UnicastProcessor<T> w;

         SubjectWork(UnicastProcessor<T> var1, boolean var2) {
            this.w = var1;
            this.open = var2;
         }
      }
   }
}
