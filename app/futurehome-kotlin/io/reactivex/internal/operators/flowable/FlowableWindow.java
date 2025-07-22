package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindow<T> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
   final int bufferSize;
   final long size;
   final long skip;

   public FlowableWindow(Flowable<T> var1, long var2, long var4, int var6) {
      super(var1);
      this.size = var2;
      this.skip = var4;
      this.bufferSize = var6;
   }

   @Override
   public void subscribeActual(Subscriber<? super Flowable<T>> var1) {
      long var4 = this.skip;
      long var2 = this.size;
      if (var4 == var2) {
         this.source.subscribe(new FlowableWindow.WindowExactSubscriber<>(var1, this.size, this.bufferSize));
      } else if (var4 > var2) {
         this.source.subscribe(new FlowableWindow.WindowSkipSubscriber<>(var1, this.size, this.skip, this.bufferSize));
      } else {
         this.source.subscribe(new FlowableWindow.WindowOverlapSubscriber<>(var1, this.size, this.skip, this.bufferSize));
      }
   }

   static final class WindowExactSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
      private static final long serialVersionUID = -2365647875069161133L;
      final int bufferSize;
      final Subscriber<? super Flowable<T>> downstream;
      long index;
      final AtomicBoolean once;
      final long size;
      Subscription upstream;
      UnicastProcessor<T> window;

      WindowExactSubscriber(Subscriber<? super Flowable<T>> var1, long var2, int var4) {
         super(1);
         this.downstream = var1;
         this.size = var2;
         this.once = new AtomicBoolean();
         this.bufferSize = var4;
      }

      public void cancel() {
         if (this.once.compareAndSet(false, true)) {
            this.run();
         }
      }

      public void onComplete() {
         UnicastProcessor var1 = this.window;
         if (var1 != null) {
            this.window = null;
            var1.onComplete();
         }

         this.downstream.onComplete();
      }

      public void onError(Throwable var1) {
         UnicastProcessor var2 = this.window;
         if (var2 != null) {
            this.window = null;
            var2.onError(var1);
         }

         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         long var2 = this.index;
         UnicastProcessor var4 = this.window;
         if (var2 == 0L) {
            this.getAndIncrement();
            var4 = UnicastProcessor.create(this.bufferSize, this);
            this.window = var4;
            this.downstream.onNext(var4);
         }

         var2++;
         var4.onNext(var1);
         if (var2 == this.size) {
            this.index = 0L;
            this.window = null;
            var4.onComplete();
         } else {
            this.index = var2;
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            var1 = BackpressureHelper.multiplyCap(this.size, var1);
            this.upstream.request(var1);
         }
      }

      @Override
      public void run() {
         if (this.decrementAndGet() == 0) {
            this.upstream.cancel();
         }
      }
   }

   static final class WindowOverlapSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
      private static final long serialVersionUID = 2428527070996323976L;
      final int bufferSize;
      volatile boolean cancelled;
      volatile boolean done;
      final Subscriber<? super Flowable<T>> downstream;
      Throwable error;
      final AtomicBoolean firstRequest;
      long index;
      final AtomicBoolean once;
      long produced;
      final SpscLinkedArrayQueue<UnicastProcessor<T>> queue;
      final AtomicLong requested;
      final long size;
      final long skip;
      Subscription upstream;
      final ArrayDeque<UnicastProcessor<T>> windows;
      final AtomicInteger wip;

      WindowOverlapSubscriber(Subscriber<? super Flowable<T>> var1, long var2, long var4, int var6) {
         super(1);
         this.downstream = var1;
         this.size = var2;
         this.skip = var4;
         this.queue = new SpscLinkedArrayQueue<>(var6);
         this.windows = new ArrayDeque<>();
         this.once = new AtomicBoolean();
         this.firstRequest = new AtomicBoolean();
         this.requested = new AtomicLong();
         this.wip = new AtomicInteger();
         this.bufferSize = var6;
      }

      public void cancel() {
         this.cancelled = true;
         if (this.once.compareAndSet(false, true)) {
            this.run();
         }
      }

      boolean checkTerminated(boolean var1, boolean var2, Subscriber<?> var3, SpscLinkedArrayQueue<?> var4) {
         if (this.cancelled) {
            var4.clear();
            return true;
         } else {
            if (var1) {
               Throwable var5 = this.error;
               if (var5 != null) {
                  var4.clear();
                  var3.onError(var5);
                  return true;
               }

               if (var2) {
                  var3.onComplete();
                  return true;
               }
            }

            return false;
         }
      }

      void drain() {
         if (this.wip.getAndIncrement() == 0) {
            Subscriber var9 = this.downstream;
            SpscLinkedArrayQueue var10 = this.queue;
            int var1 = 1;

            int var12;
            do {
               long var5 = this.requested.get();
               long var3 = 0L;

               while (true) {
                  long var13;
                  var12 = (var13 = var3 - var5) == 0L ? 0 : (var13 < 0L ? -1 : 1);
                  if (!var12) {
                     break;
                  }

                  boolean var8 = this.done;
                  UnicastProcessor var11 = (UnicastProcessor)var10.poll();
                  boolean var7;
                  if (var11 == null) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  if (this.checkTerminated(var8, var7, var9, var10)) {
                     return;
                  }

                  if (var7) {
                     break;
                  }

                  var9.onNext(var11);
                  var3++;
               }

               if (!var12 && this.checkTerminated(this.done, var10.isEmpty(), var9, var10)) {
                  return;
               }

               if (var3 != 0L && var5 != Long.MAX_VALUE) {
                  this.requested.addAndGet(-var3);
               }

               var12 = this.wip.addAndGet(-var1);
               var1 = var12;
            } while (var12 != 0);
         }
      }

      public void onComplete() {
         if (!this.done) {
            Iterator var1 = this.windows.iterator();

            while (var1.hasNext()) {
               ((Processor)var1.next()).onComplete();
            }

            this.windows.clear();
            this.done = true;
            this.drain();
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            Iterator var2 = this.windows.iterator();

            while (var2.hasNext()) {
               ((Processor)var2.next()).onError(var1);
            }

            this.windows.clear();
            this.error = var1;
            this.done = true;
            this.drain();
         }
      }

      public void onNext(T var1) {
         if (!this.done) {
            long var2 = this.index;
            if (var2 == 0L && !this.cancelled) {
               this.getAndIncrement();
               UnicastProcessor var6 = UnicastProcessor.create(this.bufferSize, this);
               this.windows.offer(var6);
               this.queue.offer(var6);
               this.drain();
            }

            var2++;
            Iterator var9 = this.windows.iterator();

            while (var9.hasNext()) {
               ((Processor)var9.next()).onNext(var1);
            }

            long var4 = this.produced + 1L;
            if (var4 == this.size) {
               this.produced = var4 - this.skip;
               var1 = this.windows.poll();
               if (var1 != null) {
                  var1.onComplete();
               }
            } else {
               this.produced = var4;
            }

            if (var2 == this.skip) {
               this.index = 0L;
            } else {
               this.index = var2;
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            if (!this.firstRequest.get() && this.firstRequest.compareAndSet(false, true)) {
               var1 = BackpressureHelper.multiplyCap(this.skip, var1 - 1L);
               var1 = BackpressureHelper.addCap(this.size, var1);
               this.upstream.request(var1);
            } else {
               var1 = BackpressureHelper.multiplyCap(this.skip, var1);
               this.upstream.request(var1);
            }

            this.drain();
         }
      }

      @Override
      public void run() {
         if (this.decrementAndGet() == 0) {
            this.upstream.cancel();
         }
      }
   }

   static final class WindowSkipSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
      private static final long serialVersionUID = -8792836352386833856L;
      final int bufferSize;
      final Subscriber<? super Flowable<T>> downstream;
      final AtomicBoolean firstRequest;
      long index;
      final AtomicBoolean once;
      final long size;
      final long skip;
      Subscription upstream;
      UnicastProcessor<T> window;

      WindowSkipSubscriber(Subscriber<? super Flowable<T>> var1, long var2, long var4, int var6) {
         super(1);
         this.downstream = var1;
         this.size = var2;
         this.skip = var4;
         this.once = new AtomicBoolean();
         this.firstRequest = new AtomicBoolean();
         this.bufferSize = var6;
      }

      public void cancel() {
         if (this.once.compareAndSet(false, true)) {
            this.run();
         }
      }

      public void onComplete() {
         UnicastProcessor var1 = this.window;
         if (var1 != null) {
            this.window = null;
            var1.onComplete();
         }

         this.downstream.onComplete();
      }

      public void onError(Throwable var1) {
         UnicastProcessor var2 = this.window;
         if (var2 != null) {
            this.window = null;
            var2.onError(var1);
         }

         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         long var2 = this.index;
         UnicastProcessor var4 = this.window;
         if (var2 == 0L) {
            this.getAndIncrement();
            var4 = UnicastProcessor.create(this.bufferSize, this);
            this.window = var4;
            this.downstream.onNext(var4);
         }

         var2++;
         if (var4 != null) {
            var4.onNext(var1);
         }

         if (var2 == this.size) {
            this.window = null;
            var4.onComplete();
         }

         if (var2 == this.skip) {
            this.index = 0L;
         } else {
            this.index = var2;
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            if (!this.firstRequest.get() && this.firstRequest.compareAndSet(false, true)) {
               var1 = BackpressureHelper.addCap(
                  BackpressureHelper.multiplyCap(this.size, var1), BackpressureHelper.multiplyCap(this.skip - this.size, var1 - 1L)
               );
               this.upstream.request(var1);
            } else {
               var1 = BackpressureHelper.multiplyCap(this.skip, var1);
               this.upstream.request(var1);
            }
         }
      }

      @Override
      public void run() {
         if (this.decrementAndGet() == 0) {
            this.upstream.cancel();
         }
      }
   }
}
