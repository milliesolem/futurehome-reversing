package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBuffer<T, C extends Collection<? super T>> extends AbstractFlowableWithUpstream<T, C> {
   final Callable<C> bufferSupplier;
   final int size;
   final int skip;

   public FlowableBuffer(Flowable<T> var1, int var2, int var3, Callable<C> var4) {
      super(var1);
      this.size = var2;
      this.skip = var3;
      this.bufferSupplier = var4;
   }

   @Override
   public void subscribeActual(Subscriber<? super C> var1) {
      int var3 = this.size;
      int var2 = this.skip;
      if (var3 == var2) {
         this.source.subscribe(new FlowableBuffer.PublisherBufferExactSubscriber<>(var1, this.size, this.bufferSupplier));
      } else if (var2 > var3) {
         this.source.subscribe(new FlowableBuffer.PublisherBufferSkipSubscriber<>(var1, this.size, this.skip, this.bufferSupplier));
      } else {
         this.source.subscribe(new FlowableBuffer.PublisherBufferOverlappingSubscriber<>(var1, this.size, this.skip, this.bufferSupplier));
      }
   }

   static final class PublisherBufferExactSubscriber<T, C extends Collection<? super T>> implements FlowableSubscriber<T>, Subscription {
      C buffer;
      final Callable<C> bufferSupplier;
      boolean done;
      final Subscriber<? super C> downstream;
      int index;
      final int size;
      Subscription upstream;

      PublisherBufferExactSubscriber(Subscriber<? super C> var1, int var2, Callable<C> var3) {
         this.downstream = var1;
         this.size = var2;
         this.bufferSupplier = var3;
      }

      public void cancel() {
         this.upstream.cancel();
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            Collection var1 = this.buffer;
            if (var1 != null && !var1.isEmpty()) {
               this.downstream.onNext(var1);
            }

            this.downstream.onComplete();
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            Collection var4 = this.buffer;
            Collection var3 = var4;
            if (var4 == null) {
               try {
                  var3 = ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null buffer");
               } catch (Throwable var6) {
                  Exceptions.throwIfFatal(var6);
                  this.cancel();
                  this.onError(var6);
                  return;
               }

               this.buffer = (C)var3;
            }

            var3.add(var1);
            int var2 = this.index + 1;
            if (var2 == this.size) {
               this.index = 0;
               this.buffer = null;
               this.downstream.onNext(var3);
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
            this.upstream.request(BackpressureHelper.multiplyCap(var1, this.size));
         }
      }
   }

   static final class PublisherBufferOverlappingSubscriber<T, C extends Collection<? super T>>
      extends AtomicLong
      implements FlowableSubscriber<T>,
      Subscription,
      BooleanSupplier {
      private static final long serialVersionUID = -7370244972039324525L;
      final Callable<C> bufferSupplier;
      final ArrayDeque<C> buffers;
      volatile boolean cancelled;
      boolean done;
      final Subscriber<? super C> downstream;
      int index;
      final AtomicBoolean once;
      long produced;
      final int size;
      final int skip;
      Subscription upstream;

      PublisherBufferOverlappingSubscriber(Subscriber<? super C> var1, int var2, int var3, Callable<C> var4) {
         this.downstream = var1;
         this.size = var2;
         this.skip = var3;
         this.bufferSupplier = var4;
         this.once = new AtomicBoolean();
         this.buffers = new ArrayDeque<>();
      }

      public void cancel() {
         this.cancelled = true;
         this.upstream.cancel();
      }

      @Override
      public boolean getAsBoolean() {
         return this.cancelled;
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            long var1 = this.produced;
            if (var1 != 0L) {
               BackpressureHelper.produced(this, var1);
            }

            QueueDrainHelper.postComplete(this.downstream, this.buffers, this, this);
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.buffers.clear();
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            ArrayDeque var4 = this.buffers;
            int var2 = this.index;
            int var3 = var2 + 1;
            if (var2 == 0) {
               Collection var5;
               try {
                  var5 = ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null buffer");
               } catch (Throwable var7) {
                  Exceptions.throwIfFatal(var7);
                  this.cancel();
                  this.onError(var7);
                  return;
               }

               var4.offer(var5);
            }

            Collection var10 = (Collection)var4.peek();
            if (var10 != null && var10.size() + 1 == this.size) {
               var4.poll();
               var10.add(var1);
               this.produced++;
               this.downstream.onNext(var10);
            }

            Iterator var9 = var4.iterator();

            while (var9.hasNext()) {
               ((Collection)var9.next()).add(var1);
            }

            var2 = var3;
            if (var3 == this.skip) {
               var2 = 0;
            }

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
            if (QueueDrainHelper.postCompleteRequest(var1, this.downstream, this.buffers, this, this)) {
               return;
            }

            if (!this.once.get() && this.once.compareAndSet(false, true)) {
               var1 = BackpressureHelper.multiplyCap(this.skip, var1 - 1L);
               var1 = BackpressureHelper.addCap(this.size, var1);
               this.upstream.request(var1);
            } else {
               var1 = BackpressureHelper.multiplyCap(this.skip, var1);
               this.upstream.request(var1);
            }
         }
      }
   }

   static final class PublisherBufferSkipSubscriber<T, C extends Collection<? super T>> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = -5616169793639412593L;
      C buffer;
      final Callable<C> bufferSupplier;
      boolean done;
      final Subscriber<? super C> downstream;
      int index;
      final int size;
      final int skip;
      Subscription upstream;

      PublisherBufferSkipSubscriber(Subscriber<? super C> var1, int var2, int var3, Callable<C> var4) {
         this.downstream = var1;
         this.size = var2;
         this.skip = var3;
         this.bufferSupplier = var4;
      }

      public void cancel() {
         this.upstream.cancel();
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            Collection var1 = this.buffer;
            this.buffer = null;
            if (var1 != null) {
               this.downstream.onNext(var1);
            }

            this.downstream.onComplete();
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.buffer = null;
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            Collection var4 = this.buffer;
            int var2 = this.index;
            int var3 = var2 + 1;
            if (var2 == 0) {
               try {
                  var4 = ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null buffer");
               } catch (Throwable var6) {
                  Exceptions.throwIfFatal(var6);
                  this.cancel();
                  this.onError(var6);
                  return;
               }

               this.buffer = (C)var4;
            }

            if (var4 != null) {
               var4.add(var1);
               if (var4.size() == this.size) {
                  this.buffer = null;
                  this.downstream.onNext(var4);
               }
            }

            var2 = var3;
            if (var3 == this.skip) {
               var2 = 0;
            }

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
            if (this.get() == 0 && this.compareAndSet(0, 1)) {
               long var3 = BackpressureHelper.multiplyCap(var1, this.size);
               var1 = BackpressureHelper.multiplyCap(this.skip - this.size, var1 - 1L);
               this.upstream.request(BackpressureHelper.addCap(var3, var1));
            } else {
               this.upstream.request(BackpressureHelper.multiplyCap(this.skip, var1));
            }
         }
      }
   }
}
