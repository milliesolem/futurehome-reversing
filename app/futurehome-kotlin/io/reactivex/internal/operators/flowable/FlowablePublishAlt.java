package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowablePublishAlt<T> extends ConnectableFlowable<T> implements HasUpstreamPublisher<T>, ResettableConnectable {
   final int bufferSize;
   final AtomicReference<FlowablePublishAlt.PublishConnection<T>> current;
   final Publisher<T> source;

   public FlowablePublishAlt(Publisher<T> var1, int var2) {
      this.source = var1;
      this.bufferSize = var2;
      this.current = new AtomicReference<>();
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void connect(Consumer<? super Disposable> var1) {
      FlowablePublishAlt.PublishConnection var5;
      FlowablePublishAlt.PublishConnection var6;
      do {
         var6 = this.current.get();
         if (var6 != null) {
            var5 = var6;
            if (!var6.isDisposed()) {
               break;
            }
         }

         var5 = new FlowablePublishAlt.PublishConnection<>(this.current, this.bufferSize);
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.current, var6, var5));

      boolean var4 = var5.connect.get();
      boolean var3 = false;
      boolean var2 = var3;
      if (!var4) {
         var2 = var3;
         if (var5.connect.compareAndSet(false, true)) {
            var2 = true;
         }
      }

      try {
         var1.accept(var5);
      } catch (Throwable var8) {
         Exceptions.throwIfFatal(var8);
         throw ExceptionHelper.wrapOrThrow(var8);
      }

      if (var2) {
         this.source.subscribe(var5);
      }
   }

   public int publishBufferSize() {
      return this.bufferSize;
   }

   @Override
   public void resetIf(Disposable var1) {
      ExternalSyntheticBackportWithForwarding0.m(this.current, (FlowablePublishAlt.PublishConnection)var1, null);
   }

   @Override
   public Publisher<T> source() {
      return this.source;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      while (true) {
         FlowablePublishAlt.PublishConnection var3 = this.current.get();
         FlowablePublishAlt.PublishConnection var2 = var3;
         if (var3 == null) {
            var2 = new FlowablePublishAlt.PublishConnection<>(this.current, this.bufferSize);
            if (!ExternalSyntheticBackportWithForwarding0.m(this.current, var3, var2)) {
               continue;
            }
         }

         FlowablePublishAlt.InnerSubscription var5 = new FlowablePublishAlt.InnerSubscription(var1, var2);
         var1.onSubscribe(var5);
         if (var2.add(var5)) {
            if (var5.isCancelled()) {
               var2.remove(var5);
            } else {
               var2.drain();
            }

            return;
         }

         Throwable var4 = var2.error;
         if (var4 != null) {
            var1.onError(var4);
         } else {
            var1.onComplete();
         }

         return;
      }
   }

   static final class InnerSubscription<T> extends AtomicLong implements Subscription {
      private static final long serialVersionUID = 2845000326761540265L;
      final Subscriber<? super T> downstream;
      long emitted;
      final FlowablePublishAlt.PublishConnection<T> parent;

      InnerSubscription(Subscriber<? super T> var1, FlowablePublishAlt.PublishConnection<T> var2) {
         this.downstream = var1;
         this.parent = var2;
      }

      public void cancel() {
         if (this.getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
            this.parent.remove(this);
            this.parent.drain();
         }
      }

      public boolean isCancelled() {
         boolean var1;
         if (this.get() == Long.MIN_VALUE) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void request(long var1) {
         BackpressureHelper.addCancel(this, var1);
         this.parent.drain();
      }
   }

   static final class PublishConnection<T> extends AtomicInteger implements FlowableSubscriber<T>, Disposable {
      static final FlowablePublishAlt.InnerSubscription[] EMPTY = new FlowablePublishAlt.InnerSubscription[0];
      static final FlowablePublishAlt.InnerSubscription[] TERMINATED = new FlowablePublishAlt.InnerSubscription[0];
      private static final long serialVersionUID = -1672047311619175801L;
      final int bufferSize;
      final AtomicBoolean connect;
      int consumed;
      final AtomicReference<FlowablePublishAlt.PublishConnection<T>> current;
      volatile boolean done;
      Throwable error;
      volatile SimpleQueue<T> queue;
      int sourceMode;
      final AtomicReference<FlowablePublishAlt.InnerSubscription<T>[]> subscribers;
      final AtomicReference<Subscription> upstream;

      PublishConnection(AtomicReference<FlowablePublishAlt.PublishConnection<T>> var1, int var2) {
         this.current = var1;
         this.upstream = new AtomicReference<>();
         this.connect = new AtomicBoolean();
         this.bufferSize = var2;
         this.subscribers = new AtomicReference<>(EMPTY);
      }

      boolean add(FlowablePublishAlt.InnerSubscription<T> var1) {
         FlowablePublishAlt.InnerSubscription[] var3;
         FlowablePublishAlt.InnerSubscription[] var4;
         do {
            var3 = this.subscribers.get();
            if (var3 == TERMINATED) {
               return false;
            }

            int var2 = var3.length;
            var4 = new FlowablePublishAlt.InnerSubscription[var2 + 1];
            System.arraycopy(var3, 0, var4, 0, var2);
            var4[var2] = var1;
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var3, var4));

         return true;
      }

      boolean checkTerminated(boolean var1, boolean var2) {
         int var3 = 0;
         if (var1 && var2) {
            Throwable var5 = this.error;
            if (var5 != null) {
               this.signalError(var5);
            } else {
               FlowablePublishAlt.InnerSubscription[] var7 = this.subscribers.getAndSet(TERMINATED);

               for (int var4 = var7.length; var3 < var4; var3++) {
                  FlowablePublishAlt.InnerSubscription var6 = var7[var3];
                  if (!var6.isCancelled()) {
                     var6.downstream.onComplete();
                  }
               }
            }

            return true;
         } else {
            return false;
         }
      }

      @Override
      public void dispose() {
         this.subscribers.getAndSet(TERMINATED);
         ExternalSyntheticBackportWithForwarding0.m(this.current, this, null);
         SubscriptionHelper.cancel(this.upstream);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            SimpleQueue var16 = this.queue;
            int var1 = this.consumed;
            int var2 = this.bufferSize;
            int var6 = var2 - (var2 >> 2);
            boolean var3;
            if (this.sourceMode != 1) {
               var3 = true;
            } else {
               var3 = false;
            }

            int var4 = 1;

            while (true) {
               label155:
               while (true) {
                  var2 = var1;
                  if (var16 == null) {
                     break;
                  }

                  FlowablePublishAlt.InnerSubscription[] var17 = this.subscribers.get();
                  int var7 = var17.length;
                  long var8 = Long.MAX_VALUE;
                  var2 = 0;
                  boolean var5 = false;

                  while (var2 < var7) {
                     FlowablePublishAlt.InnerSubscription var18 = var17[var2];
                     long var12 = var18.get();
                     long var10 = var8;
                     if (var12 != Long.MIN_VALUE) {
                        var10 = Math.min(var12 - var18.emitted, var8);
                        var5 = true;
                     }

                     var2++;
                     var8 = var10;
                  }

                  var2 = var1;
                  if (!var5) {
                     var8 = 0L;
                     var2 = var1;
                  }

                  while (var8 != 0L) {
                     boolean var15 = this.done;

                     Object var19;
                     try {
                        var19 = var16.poll();
                     } catch (Throwable var21) {
                        Exceptions.throwIfFatal(var21);
                        this.upstream.get().cancel();
                        var16.clear();
                        this.done = true;
                        this.signalError(var21);
                        return;
                     }

                     boolean var14;
                     if (var19 == null) {
                        var14 = true;
                     } else {
                        var14 = false;
                     }

                     if (this.checkTerminated(var15, var14)) {
                        return;
                     }

                     if (var14) {
                        break;
                     }

                     for (FlowablePublishAlt.InnerSubscription var28 : var17) {
                        if (!var28.isCancelled()) {
                           var28.downstream.onNext(var19);
                           var28.emitted++;
                        }
                     }

                     var1 = var2;
                     if (var3) {
                        var1 = ++var2;
                        if (var2 == var6) {
                           this.upstream.get().request(var6);
                           var1 = 0;
                        }
                     }

                     var8--;
                     if (var17 != this.subscribers.get()) {
                        continue label155;
                     }

                     var2 = var1;
                  }

                  if (this.checkTerminated(this.done, var16.isEmpty())) {
                     return;
                  }
                  break;
               }

               this.consumed = var2;
               int var27 = this.addAndGet(-var4);
               if (var27 == 0) {
                  return;
               }

               var1 = var2;
               var4 = var27;
               if (var16 == null) {
                  var16 = this.queue;
                  var1 = var2;
                  var4 = var27;
               }
            }
         }
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.subscribers.get() == TERMINATED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void onComplete() {
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.error = var1;
            this.done = true;
            this.drain();
         }
      }

      public void onNext(T var1) {
         if (this.sourceMode == 0 && !this.queue.offer((T)var1)) {
            this.onError(new MissingBackpressureException("Prefetch queue is full?!"));
         } else {
            this.drain();
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.setOnce(this.upstream, var1)) {
            if (var1 instanceof QueueSubscription) {
               QueueSubscription var3 = (QueueSubscription)var1;
               int var2 = var3.requestFusion(7);
               if (var2 == 1) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  this.done = true;
                  this.drain();
                  return;
               }

               if (var2 == 2) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  var1.request(this.bufferSize);
                  return;
               }
            }

            this.queue = new SpscArrayQueue<>(this.bufferSize);
            var1.request(this.bufferSize);
         }
      }

      void remove(FlowablePublishAlt.InnerSubscription<T> var1) {
         while (true) {
            FlowablePublishAlt.InnerSubscription[] var5 = this.subscribers.get();
            int var3 = var5.length;
            if (var3 != 0) {
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

               FlowablePublishAlt.InnerSubscription[] var4;
               if (var3 == 1) {
                  var4 = EMPTY;
               } else {
                  var4 = new FlowablePublishAlt.InnerSubscription[var3 - 1];
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

      void signalError(Throwable var1) {
         for (FlowablePublishAlt.InnerSubscription var5 : this.subscribers.getAndSet(TERMINATED)) {
            if (!var5.isCancelled()) {
               var5.downstream.onError(var1);
            }
         }
      }
   }
}
