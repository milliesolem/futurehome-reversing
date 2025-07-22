package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowablePublishMulticast<T, R> extends AbstractFlowableWithUpstream<T, R> {
   final boolean delayError;
   final int prefetch;
   final Function<? super Flowable<T>, ? extends Publisher<? extends R>> selector;

   public FlowablePublishMulticast(Flowable<T> var1, Function<? super Flowable<T>, ? extends Publisher<? extends R>> var2, int var3, boolean var4) {
      super(var1);
      this.selector = var2;
      this.prefetch = var3;
      this.delayError = var4;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      FlowablePublishMulticast.MulticastProcessor var2 = new FlowablePublishMulticast.MulticastProcessor(this.prefetch, this.delayError);

      Publisher var3;
      try {
         var3 = ObjectHelper.requireNonNull(this.selector.apply(var2), "selector returned a null Publisher");
      } catch (Throwable var5) {
         Exceptions.throwIfFatal(var5);
         EmptySubscription.error(var5, var1);
         return;
      }

      var3.subscribe(new FlowablePublishMulticast.OutputCanceller(var1, var2));
      this.source.subscribe(var2);
   }

   static final class MulticastProcessor<T> extends Flowable<T> implements FlowableSubscriber<T>, Disposable {
      static final FlowablePublishMulticast.MulticastSubscription[] EMPTY = new FlowablePublishMulticast.MulticastSubscription[0];
      static final FlowablePublishMulticast.MulticastSubscription[] TERMINATED = new FlowablePublishMulticast.MulticastSubscription[0];
      int consumed;
      final boolean delayError;
      volatile boolean done;
      Throwable error;
      final int limit;
      final int prefetch;
      volatile SimpleQueue<T> queue;
      int sourceMode;
      final AtomicReference<FlowablePublishMulticast.MulticastSubscription<T>[]> subscribers;
      final AtomicReference<Subscription> upstream;
      final AtomicInteger wip;

      MulticastProcessor(int var1, boolean var2) {
         this.prefetch = var1;
         this.limit = var1 - (var1 >> 2);
         this.delayError = var2;
         this.wip = new AtomicInteger();
         this.upstream = new AtomicReference<>();
         this.subscribers = new AtomicReference<>(EMPTY);
      }

      boolean add(FlowablePublishMulticast.MulticastSubscription<T> var1) {
         FlowablePublishMulticast.MulticastSubscription[] var3;
         FlowablePublishMulticast.MulticastSubscription[] var4;
         do {
            var3 = this.subscribers.get();
            if (var3 == TERMINATED) {
               return false;
            }

            int var2 = var3.length;
            var4 = new FlowablePublishMulticast.MulticastSubscription[var2 + 1];
            System.arraycopy(var3, 0, var4, 0, var2);
            var4[var2] = var1;
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var3, var4));

         return true;
      }

      void completeAll() {
         for (FlowablePublishMulticast.MulticastSubscription var3 : this.subscribers.getAndSet(TERMINATED)) {
            if (var3.get() != Long.MIN_VALUE) {
               var3.downstream.onComplete();
            }
         }
      }

      @Override
      public void dispose() {
         SubscriptionHelper.cancel(this.upstream);
         if (this.wip.getAndIncrement() == 0) {
            SimpleQueue var1 = this.queue;
            if (var1 != null) {
               var1.clear();
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.wip.getAndIncrement() == 0) {
            SimpleQueue var17 = this.queue;
            int var1 = this.consumed;
            int var7 = this.limit;
            boolean var3;
            if (this.sourceMode != 1) {
               var3 = true;
            } else {
               var3 = false;
            }

            AtomicReference var16 = this.subscribers;
            FlowablePublishMulticast.MulticastSubscription[] var18 = (FlowablePublishMulticast.MulticastSubscription[])var16.get();
            int var4 = 1;

            while (true) {
               SimpleQueue var40;
               label253: {
                  int var5 = var18.length;
                  AtomicReference var36;
                  if (var17 != null && var5 != 0) {
                     int var8 = var18.length;
                     long var10 = Long.MAX_VALUE;
                     int var2 = 0;

                     while (var2 < var8) {
                        FlowablePublishMulticast.MulticastSubscription var19 = var18[var2];
                        long var14 = var19.get() - var19.emitted;
                        int var6;
                        long var12;
                        if (var14 != Long.MIN_VALUE) {
                           var6 = var5;
                           var12 = var10;
                           if (var10 > var14) {
                              var12 = var14;
                              var6 = var5;
                           }
                        } else {
                           var6 = var5 - 1;
                           var12 = var10;
                        }

                        var2++;
                        var5 = var6;
                        var10 = var12;
                     }

                     var2 = var1;
                     if (var5 == 0) {
                        var10 = 0L;
                        var2 = var1;
                     }

                     while (true) {
                        long var43;
                        var5 = (var43 = var10 - 0L) == 0L ? 0 : (var43 < 0L ? -1 : 1);
                        if (var5) {
                           if (this.isDisposed()) {
                              var17.clear();
                              return;
                           }

                           boolean var9 = this.done;
                           if (var9 && !this.delayError) {
                              Throwable var38 = this.error;
                              if (var38 != null) {
                                 this.errorAll(var38);
                                 return;
                              }
                           }

                           Object var20;
                           try {
                              var20 = var17.poll();
                           } catch (Throwable var22) {
                              Exceptions.throwIfFatal(var22);
                              SubscriptionHelper.cancel(this.upstream);
                              this.errorAll(var22);
                              return;
                           }

                           boolean var23;
                           if (var20 == null) {
                              var23 = 1;
                           } else {
                              var23 = 0;
                           }

                           if (var9 && var23) {
                              Throwable var34 = this.error;
                              if (var34 != null) {
                                 this.errorAll(var34);
                              } else {
                                 this.completeAll();
                              }

                              return;
                           }

                           if (!var23) {
                              int var29 = var18.length;
                              var23 = 0;

                              for (var28 = false; var23 < var29; var23++) {
                                 FlowablePublishMulticast.MulticastSubscription var41 = var18[var23];
                                 long var31 = var41.get();
                                 if (var31 != Long.MIN_VALUE) {
                                    if (var31 != Long.MAX_VALUE) {
                                       var41.emitted++;
                                    }

                                    var41.downstream.onNext(var20);
                                 } else {
                                    var28 = true;
                                 }
                              }

                              var10--;
                              var1 = var2;
                              if (var3) {
                                 var1 = ++var2;
                                 if (var2 == var7) {
                                    this.upstream.get().request(var7);
                                    var1 = 0;
                                 }
                              }

                              FlowablePublishMulticast.MulticastSubscription[] var42 = (FlowablePublishMulticast.MulticastSubscription[])var16.get();
                              if (var28 || var42 != var18) {
                                 var18 = var42;
                                 var40 = var17;
                                 var35 = var16;
                                 break label253;
                              }

                              var2 = var1;
                              continue;
                           }
                        }

                        var1 = var2;
                        var36 = var16;
                        if (!var5) {
                           if (this.isDisposed()) {
                              var17.clear();
                              return;
                           }

                           boolean var30 = this.done;
                           if (var30 && !this.delayError) {
                              Throwable var37 = this.error;
                              if (var37 != null) {
                                 this.errorAll(var37);
                                 return;
                              }
                           }

                           var1 = var2;
                           var36 = var16;
                           if (var30) {
                              var1 = var2;
                              var36 = var16;
                              if (var17.isEmpty()) {
                                 Throwable var33 = this.error;
                                 if (var33 != null) {
                                    this.errorAll(var33);
                                 } else {
                                    this.completeAll();
                                 }

                                 return;
                              }
                           }
                        }
                        break;
                     }
                  } else {
                     var36 = var16;
                  }

                  this.consumed = var1;
                  var4 = this.wip.addAndGet(-var4);
                  if (var4 == 0) {
                     return;
                  }

                  SimpleQueue var32 = var17;
                  if (var17 == null) {
                     var32 = this.queue;
                  }

                  FlowablePublishMulticast.MulticastSubscription[] var39 = (FlowablePublishMulticast.MulticastSubscription[])var36.get();
                  var35 = var36;
                  var18 = var39;
                  var40 = var32;
               }

               var16 = var35;
               var17 = var40;
            }
         }
      }

      void errorAll(Throwable var1) {
         for (FlowablePublishMulticast.MulticastSubscription var4 : this.subscribers.getAndSet(TERMINATED)) {
            if (var4.get() != Long.MIN_VALUE) {
               var4.downstream.onError(var1);
            }
         }
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.upstream.get() == SubscriptionHelper.CANCELLED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.drain();
         }
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
         if (!this.done) {
            if (this.sourceMode == 0 && !this.queue.offer((T)var1)) {
               this.upstream.get().cancel();
               this.onError(new MissingBackpressureException());
            } else {
               this.drain();
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.setOnce(this.upstream, var1)) {
            if (var1 instanceof QueueSubscription) {
               QueueSubscription var3 = (QueueSubscription)var1;
               int var2 = var3.requestFusion(3);
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
                  QueueDrainHelper.request(var1, this.prefetch);
                  return;
               }
            }

            this.queue = QueueDrainHelper.createQueue(this.prefetch);
            QueueDrainHelper.request(var1, this.prefetch);
         }
      }

      void remove(FlowablePublishMulticast.MulticastSubscription<T> var1) {
         FlowablePublishMulticast.MulticastSubscription[] var4;
         FlowablePublishMulticast.MulticastSubscription[] var5;
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
               var4 = new FlowablePublishMulticast.MulticastSubscription[var3 - 1];
               System.arraycopy(var5, 0, var4, 0, var2);
               System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
            }
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, var4));
      }

      @Override
      protected void subscribeActual(Subscriber<? super T> var1) {
         FlowablePublishMulticast.MulticastSubscription var2 = new FlowablePublishMulticast.MulticastSubscription<>(var1, this);
         var1.onSubscribe(var2);
         if (this.add(var2)) {
            if (var2.isCancelled()) {
               this.remove(var2);
               return;
            }

            this.drain();
         } else {
            Throwable var3 = this.error;
            if (var3 != null) {
               var1.onError(var3);
            } else {
               var1.onComplete();
            }
         }
      }
   }

   static final class MulticastSubscription<T> extends AtomicLong implements Subscription {
      private static final long serialVersionUID = 8664815189257569791L;
      final Subscriber<? super T> downstream;
      long emitted;
      final FlowablePublishMulticast.MulticastProcessor<T> parent;

      MulticastSubscription(Subscriber<? super T> var1, FlowablePublishMulticast.MulticastProcessor<T> var2) {
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
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.addCancel(this, var1);
            this.parent.drain();
         }
      }
   }

   static final class OutputCanceller<R> implements FlowableSubscriber<R>, Subscription {
      final Subscriber<? super R> downstream;
      final FlowablePublishMulticast.MulticastProcessor<?> processor;
      Subscription upstream;

      OutputCanceller(Subscriber<? super R> var1, FlowablePublishMulticast.MulticastProcessor<?> var2) {
         this.downstream = var1;
         this.processor = var2;
      }

      public void cancel() {
         this.upstream.cancel();
         this.processor.dispose();
      }

      public void onComplete() {
         this.downstream.onComplete();
         this.processor.dispose();
      }

      public void onError(Throwable var1) {
         this.downstream.onError(var1);
         this.processor.dispose();
      }

      public void onNext(R var1) {
         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      public void request(long var1) {
         this.upstream.request(var1);
      }
   }
}
