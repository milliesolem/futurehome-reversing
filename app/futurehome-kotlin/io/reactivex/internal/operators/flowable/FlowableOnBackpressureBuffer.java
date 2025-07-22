package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Action;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureBuffer<T> extends AbstractFlowableWithUpstream<T, T> {
   final int bufferSize;
   final boolean delayError;
   final Action onOverflow;
   final boolean unbounded;

   public FlowableOnBackpressureBuffer(Flowable<T> var1, int var2, boolean var3, boolean var4, Action var5) {
      super(var1);
      this.bufferSize = var2;
      this.unbounded = var3;
      this.delayError = var4;
      this.onOverflow = var5;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source
         .subscribe(new FlowableOnBackpressureBuffer.BackpressureBufferSubscriber<>(var1, this.bufferSize, this.unbounded, this.delayError, this.onOverflow));
   }

   static final class BackpressureBufferSubscriber<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -2514538129242366402L;
      volatile boolean cancelled;
      final boolean delayError;
      volatile boolean done;
      final Subscriber<? super T> downstream;
      Throwable error;
      final Action onOverflow;
      boolean outputFused;
      final SimplePlainQueue<T> queue;
      final AtomicLong requested = new AtomicLong();
      Subscription upstream;

      BackpressureBufferSubscriber(Subscriber<? super T> var1, int var2, boolean var3, boolean var4, Action var5) {
         this.downstream = var1;
         this.onOverflow = var5;
         this.delayError = var4;
         Object var6;
         if (var3) {
            var6 = new SpscLinkedArrayQueue(var2);
         } else {
            var6 = new SpscArrayQueue(var2);
         }

         this.queue = (SimplePlainQueue<T>)var6;
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.cancel();
            if (!this.outputFused && this.getAndIncrement() == 0) {
               this.queue.clear();
            }
         }
      }

      boolean checkTerminated(boolean var1, boolean var2, Subscriber<? super T> var3) {
         if (this.cancelled) {
            this.queue.clear();
            return true;
         } else {
            if (var1) {
               if (this.delayError) {
                  if (var2) {
                     Throwable var4 = this.error;
                     if (var4 != null) {
                        var3.onError(var4);
                     } else {
                        var3.onComplete();
                     }

                     return true;
                  }
               } else {
                  Throwable var5 = this.error;
                  if (var5 != null) {
                     this.queue.clear();
                     var3.onError(var5);
                     return true;
                  }

                  if (var2) {
                     var3.onComplete();
                     return true;
                  }
               }
            }

            return false;
         }
      }

      @Override
      public void clear() {
         this.queue.clear();
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            SimplePlainQueue var10 = this.queue;
            Subscriber var9 = this.downstream;
            int var1 = 1;

            int var12;
            do {
               if (this.checkTerminated(this.done, var10.isEmpty(), var9)) {
                  return;
               }

               long var7 = this.requested.get();
               long var5 = 0L;

               while (true) {
                  long var13;
                  var12 = (var13 = var5 - var7) == 0L ? 0 : (var13 < 0L ? -1 : 1);
                  if (!var12) {
                     break;
                  }

                  boolean var4 = this.done;
                  Object var11 = var10.poll();
                  boolean var3;
                  if (var11 == null) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  if (this.checkTerminated(var4, var3, var9)) {
                     return;
                  }

                  if (var3) {
                     break;
                  }

                  var9.onNext(var11);
                  var5++;
               }

               if (!var12 && this.checkTerminated(this.done, var10.isEmpty(), var9)) {
                  return;
               }

               if (var5 != 0L && var7 != Long.MAX_VALUE) {
                  this.requested.addAndGet(-var5);
               }

               var12 = this.addAndGet(-var1);
               var1 = var12;
            } while (var12 != 0);
         }
      }

      @Override
      public boolean isEmpty() {
         return this.queue.isEmpty();
      }

      public void onComplete() {
         this.done = true;
         if (this.outputFused) {
            this.downstream.onComplete();
         } else {
            this.drain();
         }
      }

      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         if (this.outputFused) {
            this.downstream.onError(var1);
         } else {
            this.drain();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.queue.offer((T)var1)) {
            this.upstream.cancel();
            var1 = new MissingBackpressureException("Buffer is full");

            label32:
            try {
               this.onOverflow.run();
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               var1.initCause(var4);
               break label32;
            }

            this.onError(var1);
         } else {
            if (this.outputFused) {
               this.downstream.onNext(null);
            } else {
               this.drain();
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(Long.MAX_VALUE);
         }
      }

      @Override
      public T poll() throws Exception {
         return this.queue.poll();
      }

      public void request(long var1) {
         if (!this.outputFused && SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.drain();
         }
      }

      @Override
      public int requestFusion(int var1) {
         if ((var1 & 2) != 0) {
            this.outputFused = true;
            return 2;
         } else {
            return 0;
         }
      }
   }
}
