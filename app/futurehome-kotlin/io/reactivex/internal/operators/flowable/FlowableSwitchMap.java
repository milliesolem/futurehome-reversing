package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSwitchMap<T, R> extends AbstractFlowableWithUpstream<T, R> {
   final int bufferSize;
   final boolean delayErrors;
   final Function<? super T, ? extends Publisher<? extends R>> mapper;

   public FlowableSwitchMap(Flowable<T> var1, Function<? super T, ? extends Publisher<? extends R>> var2, int var3, boolean var4) {
      super(var1);
      this.mapper = var2;
      this.bufferSize = var3;
      this.delayErrors = var4;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      if (!FlowableScalarXMap.tryScalarXMapSubscribe(this.source, var1, this.mapper)) {
         this.source.subscribe(new FlowableSwitchMap.SwitchMapSubscriber<>(var1, this.mapper, this.bufferSize, this.delayErrors));
      }
   }

   static final class SwitchMapInnerSubscriber<T, R> extends AtomicReference<Subscription> implements FlowableSubscriber<R> {
      private static final long serialVersionUID = 3837284832786408377L;
      final int bufferSize;
      volatile boolean done;
      int fusionMode;
      final long index;
      final FlowableSwitchMap.SwitchMapSubscriber<T, R> parent;
      volatile SimpleQueue<R> queue;

      SwitchMapInnerSubscriber(FlowableSwitchMap.SwitchMapSubscriber<T, R> var1, long var2, int var4) {
         this.parent = var1;
         this.index = var2;
         this.bufferSize = var4;
      }

      public void cancel() {
         SubscriptionHelper.cancel(this);
      }

      public void onComplete() {
         FlowableSwitchMap.SwitchMapSubscriber var1 = this.parent;
         if (this.index == var1.unique) {
            this.done = true;
            var1.drain();
         }
      }

      public void onError(Throwable var1) {
         FlowableSwitchMap.SwitchMapSubscriber var2 = this.parent;
         if (this.index == var2.unique && var2.error.addThrowable(var1)) {
            if (!var2.delayErrors) {
               var2.upstream.cancel();
               var2.done = true;
            }

            this.done = true;
            var2.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(R var1) {
         FlowableSwitchMap.SwitchMapSubscriber var2 = this.parent;
         if (this.index == var2.unique) {
            if (this.fusionMode == 0 && !this.queue.offer((R)var1)) {
               this.onError(new MissingBackpressureException("Queue full?!"));
               return;
            }

            var2.drain();
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.setOnce(this, var1)) {
            if (var1 instanceof QueueSubscription) {
               QueueSubscription var3 = (QueueSubscription)var1;
               int var2 = var3.requestFusion(7);
               if (var2 == 1) {
                  this.fusionMode = var2;
                  this.queue = var3;
                  this.done = true;
                  this.parent.drain();
                  return;
               }

               if (var2 == 2) {
                  this.fusionMode = var2;
                  this.queue = var3;
                  var1.request(this.bufferSize);
                  return;
               }
            }

            this.queue = new SpscArrayQueue<>(this.bufferSize);
            var1.request(this.bufferSize);
         }
      }

      public void request(long var1) {
         if (this.fusionMode != 1) {
            this.get().request(var1);
         }
      }
   }

   static final class SwitchMapSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
      static final FlowableSwitchMap.SwitchMapInnerSubscriber<Object, Object> CANCELLED;
      private static final long serialVersionUID = -3491074160481096299L;
      final AtomicReference<FlowableSwitchMap.SwitchMapInnerSubscriber<T, R>> active = new AtomicReference<>();
      final int bufferSize;
      volatile boolean cancelled;
      final boolean delayErrors;
      volatile boolean done;
      final Subscriber<? super R> downstream;
      final AtomicThrowable error;
      final Function<? super T, ? extends Publisher<? extends R>> mapper;
      final AtomicLong requested = new AtomicLong();
      volatile long unique;
      Subscription upstream;

      static {
         FlowableSwitchMap.SwitchMapInnerSubscriber var0 = new FlowableSwitchMap.SwitchMapInnerSubscriber(null, -1L, 1);
         CANCELLED = var0;
         var0.cancel();
      }

      SwitchMapSubscriber(Subscriber<? super R> var1, Function<? super T, ? extends Publisher<? extends R>> var2, int var3, boolean var4) {
         this.downstream = var1;
         this.mapper = var2;
         this.bufferSize = var3;
         this.delayErrors = var4;
         this.error = new AtomicThrowable();
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.cancel();
            this.disposeInner();
         }
      }

      void disposeInner() {
         FlowableSwitchMap.SwitchMapInnerSubscriber var2 = this.active.get();
         FlowableSwitchMap.SwitchMapInnerSubscriber var1 = CANCELLED;
         if (var2 != var1) {
            var2 = this.active.getAndSet(var1);
            if (var2 != var1 && var2 != null) {
               var2.cancel();
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            Subscriber var12 = this.downstream;
            int var1 = 1;

            while (!this.cancelled) {
               if (this.done) {
                  if (this.delayErrors) {
                     if (this.active.get() == null) {
                        if (this.error.get() != null) {
                           var12.onError(this.error.terminate());
                        } else {
                           var12.onComplete();
                        }

                        return;
                     }
                  } else {
                     if (this.error.get() != null) {
                        this.disposeInner();
                        var12.onError(this.error.terminate());
                        return;
                     }

                     if (this.active.get() == null) {
                        var12.onComplete();
                        return;
                     }
                  }
               }

               FlowableSwitchMap.SwitchMapInnerSubscriber var11 = this.active.get();
               SimpleQueue var9;
               if (var11 != null) {
                  var9 = var11.queue;
               } else {
                  var9 = null;
               }

               if (var9 != null) {
                  if (var11.done) {
                     if (!this.delayErrors) {
                        if (this.error.get() != null) {
                           this.disposeInner();
                           var12.onError(this.error.terminate());
                           return;
                        }

                        if (var9.isEmpty()) {
                           ExternalSyntheticBackportWithForwarding0.m(this.active, var11, null);
                           continue;
                        }
                     } else if (var9.isEmpty()) {
                        ExternalSyntheticBackportWithForwarding0.m(this.active, var11, null);
                        continue;
                     }
                  }

                  long var6 = this.requested.get();
                  long var4 = 0L;

                  boolean var2;
                  label193: {
                     while (true) {
                        boolean var3 = false;
                        var2 = var3;
                        if (var4 == var6) {
                           break label193;
                        }

                        if (this.cancelled) {
                           return;
                        }

                        boolean var8 = var11.done;

                        Object var10;
                        label179:
                        try {
                           var10 = var9.poll();
                        } catch (Throwable var14) {
                           Exceptions.throwIfFatal(var14);
                           var11.cancel();
                           this.error.addThrowable(var14);
                           var10 = null;
                           var8 = true;
                           break label179;
                        }

                        if (var10 == null) {
                           var2 = true;
                        } else {
                           var2 = false;
                        }

                        if (var11 != this.active.get()) {
                           break;
                        }

                        if (var8) {
                           if (!this.delayErrors) {
                              if (this.error.get() != null) {
                                 var12.onError(this.error.terminate());
                                 return;
                              }

                              if (var2) {
                                 ExternalSyntheticBackportWithForwarding0.m(this.active, var11, null);
                                 break;
                              }
                           } else if (var2) {
                              ExternalSyntheticBackportWithForwarding0.m(this.active, var11, null);
                              break;
                           }
                        }

                        if (var2) {
                           var2 = var3;
                           break label193;
                        }

                        var12.onNext(var10);
                        var4++;
                     }

                     var2 = true;
                  }

                  if (var4 != 0L && !this.cancelled) {
                     if (var6 != Long.MAX_VALUE) {
                        this.requested.addAndGet(-var4);
                     }

                     var11.request(var4);
                  }

                  if (var2) {
                     continue;
                  }
               }

               int var16 = this.addAndGet(-var1);
               var1 = var16;
               if (var16 == 0) {
                  return;
               }
            }

            this.active.lazySet(null);
         }
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.drain();
         }
      }

      public void onError(Throwable var1) {
         if (!this.done && this.error.addThrowable(var1)) {
            if (!this.delayErrors) {
               this.disposeInner();
            }

            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            long var2 = this.unique + 1L;
            this.unique = var2;
            FlowableSwitchMap.SwitchMapInnerSubscriber var4 = this.active.get();
            if (var4 != null) {
               var4.cancel();
            }

            Publisher var5;
            try {
               var5 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The publisher returned is null");
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               this.upstream.cancel();
               this.onError(var7);
               return;
            }

            var1 = new FlowableSwitchMap.SwitchMapInnerSubscriber<>(this, var2, this.bufferSize);

            while (true) {
               var4 = this.active.get();
               if (var4 == CANCELLED) {
                  break;
               }

               if (ExternalSyntheticBackportWithForwarding0.m(this.active, var4, var1)) {
                  var5.subscribe(var1);
                  break;
               }
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
            if (this.unique == 0L) {
               this.upstream.request(Long.MAX_VALUE);
            } else {
               this.drain();
            }
         }
      }
   }
}
