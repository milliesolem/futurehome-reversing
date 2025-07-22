package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableConcatMap<T, R> extends AbstractFlowableWithUpstream<T, R> {
   final ErrorMode errorMode;
   final Function<? super T, ? extends Publisher<? extends R>> mapper;
   final int prefetch;

   public FlowableConcatMap(Flowable<T> var1, Function<? super T, ? extends Publisher<? extends R>> var2, int var3, ErrorMode var4) {
      super(var1);
      this.mapper = var2;
      this.prefetch = var3;
      this.errorMode = var4;
   }

   public static <T, R> Subscriber<T> subscribe(
      Subscriber<? super R> var0, Function<? super T, ? extends Publisher<? extends R>> var1, int var2, ErrorMode var3
   ) {
      int var4 = <unrepresentable>.$SwitchMap$io$reactivex$internal$util$ErrorMode[var3.ordinal()];
      if (var4 != 1) {
         return (Subscriber<T>)(var4 != 2
            ? new FlowableConcatMap.ConcatMapImmediate(var0, var1, var2)
            : new FlowableConcatMap.ConcatMapDelayed(var0, var1, var2, true));
      } else {
         return new FlowableConcatMap.ConcatMapDelayed(var0, var1, var2, false);
      }
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      if (!FlowableScalarXMap.tryScalarXMapSubscribe(this.source, var1, this.mapper)) {
         this.source.subscribe(subscribe(var1, this.mapper, this.prefetch, this.errorMode));
      }
   }

   abstract static class BaseConcatMapSubscriber<T, R>
      extends AtomicInteger
      implements FlowableSubscriber<T>,
      FlowableConcatMap.ConcatMapSupport<R>,
      Subscription {
      private static final long serialVersionUID = -3511336836796789179L;
      volatile boolean active;
      volatile boolean cancelled;
      int consumed;
      volatile boolean done;
      final AtomicThrowable errors;
      final FlowableConcatMap.ConcatMapInner<R> inner;
      final int limit;
      final Function<? super T, ? extends Publisher<? extends R>> mapper;
      final int prefetch;
      SimpleQueue<T> queue;
      int sourceMode;
      Subscription upstream;

      BaseConcatMapSubscriber(Function<? super T, ? extends Publisher<? extends R>> var1, int var2) {
         this.mapper = var1;
         this.prefetch = var2;
         this.limit = var2 - (var2 >> 2);
         this.inner = new FlowableConcatMap.ConcatMapInner<>(this);
         this.errors = new AtomicThrowable();
      }

      abstract void drain();

      @Override
      public final void innerComplete() {
         this.active = false;
         this.drain();
      }

      public final void onComplete() {
         this.done = true;
         this.drain();
      }

      public final void onNext(T var1) {
         if (this.sourceMode != 2 && !this.queue.offer((T)var1)) {
            this.upstream.cancel();
            this.onError(new IllegalStateException("Queue full?!"));
         } else {
            this.drain();
         }
      }

      @Override
      public final void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            if (var1 instanceof QueueSubscription) {
               QueueSubscription var3 = (QueueSubscription)var1;
               int var2 = var3.requestFusion(7);
               if (var2 == 1) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  this.done = true;
                  this.subscribeActual();
                  this.drain();
                  return;
               }

               if (var2 == 2) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  this.subscribeActual();
                  var1.request(this.prefetch);
                  return;
               }
            }

            this.queue = new SpscArrayQueue<>(this.prefetch);
            this.subscribeActual();
            var1.request(this.prefetch);
         }
      }

      abstract void subscribeActual();
   }

   static final class ConcatMapDelayed<T, R> extends FlowableConcatMap.BaseConcatMapSubscriber<T, R> {
      private static final long serialVersionUID = -2945777694260521066L;
      final Subscriber<? super R> downstream;
      final boolean veryEnd;

      ConcatMapDelayed(Subscriber<? super R> var1, Function<? super T, ? extends Publisher<? extends R>> var2, int var3, boolean var4) {
         super(var2, var3);
         this.downstream = var1;
         this.veryEnd = var4;
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.inner.cancel();
            this.upstream.cancel();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      void drain() {
         if (this.getAndIncrement() == 0) {
            while (true) {
               if (this.cancelled) {
                  return;
               }

               if (!this.active) {
                  boolean var2 = this.done;
                  if (var2 && !this.veryEnd && this.errors.get() != null) {
                     this.downstream.onError(this.errors.terminate());
                     return;
                  }

                  Object var3;
                  try {
                     var3 = this.queue.poll();
                  } catch (Throwable var14) {
                     Exceptions.throwIfFatal(var14);
                     this.upstream.cancel();
                     this.errors.addThrowable(var14);
                     this.downstream.onError(this.errors.terminate());
                     return;
                  }

                  boolean var1;
                  if (var3 == null) {
                     var1 = 1;
                  } else {
                     var1 = 0;
                  }

                  if (var2 && var1) {
                     var3 = this.errors.terminate();
                     if (var3 != null) {
                        this.downstream.onError((Throwable)var3);
                     } else {
                        this.downstream.onComplete();
                     }

                     return;
                  }

                  if (!var1) {
                     try {
                        var3 = ObjectHelper.requireNonNull(this.mapper.apply((T)var3), "The mapper returned a null Publisher");
                     } catch (Throwable var13) {
                        Exceptions.throwIfFatal(var13);
                        this.upstream.cancel();
                        this.errors.addThrowable(var13);
                        this.downstream.onError(this.errors.terminate());
                        return;
                     }

                     if (this.sourceMode != 1) {
                        var1 = this.consumed + 1;
                        if (var1 == this.limit) {
                           this.consumed = 0;
                           this.upstream.request(var1);
                        } else {
                           this.consumed = var1;
                        }
                     }

                     if (var3 instanceof Callable) {
                        Callable var18 = (Callable)var3;

                        try {
                           var3 = (Publisher)var18.call();
                        } catch (Throwable var15) {
                           label250: {
                              Exceptions.throwIfFatal(var15);
                              this.errors.addThrowable(var15);
                              if (!this.veryEnd) {
                                 this.upstream.cancel();
                                 this.downstream.onError(this.errors.terminate());
                                 return;
                              }

                              var3 = null;
                              break label250;
                           }
                        }

                        if (var3 == null) {
                           continue;
                        }

                        if (this.inner.isUnbounded()) {
                           this.downstream.onNext(var3);
                           continue;
                        }

                        this.active = true;
                        this.inner.setSubscription(new FlowableConcatMap.WeakScalarSubscription<>(var3, this.inner));
                     } else {
                        this.active = true;
                        var3.subscribe(this.inner);
                     }
                  }
               }

               if (this.decrementAndGet() == 0) {
                  break;
               }
            }
         }
      }

      @Override
      public void innerError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            if (!this.veryEnd) {
               this.upstream.cancel();
               this.done = true;
            }

            this.active = false;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void innerNext(R var1) {
         this.downstream.onNext(var1);
      }

      public void onError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void request(long var1) {
         this.inner.request(var1);
      }

      @Override
      void subscribeActual() {
         this.downstream.onSubscribe(this);
      }
   }

   static final class ConcatMapImmediate<T, R> extends FlowableConcatMap.BaseConcatMapSubscriber<T, R> {
      private static final long serialVersionUID = 7898995095634264146L;
      final Subscriber<? super R> downstream;
      final AtomicInteger wip;

      ConcatMapImmediate(Subscriber<? super R> var1, Function<? super T, ? extends Publisher<? extends R>> var2, int var3) {
         super(var2, var3);
         this.downstream = var1;
         this.wip = new AtomicInteger();
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.inner.cancel();
            this.upstream.cancel();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      void drain() {
         if (this.wip.getAndIncrement() == 0) {
            while (true) {
               if (this.cancelled) {
                  return;
               }

               if (!this.active) {
                  boolean var2 = this.done;

                  Object var3;
                  try {
                     var3 = this.queue.poll();
                  } catch (Throwable var15) {
                     Exceptions.throwIfFatal(var15);
                     this.upstream.cancel();
                     this.errors.addThrowable(var15);
                     this.downstream.onError(this.errors.terminate());
                     return;
                  }

                  boolean var1;
                  if (var3 == null) {
                     var1 = 1;
                  } else {
                     var1 = 0;
                  }

                  if (var2 && var1) {
                     this.downstream.onComplete();
                     return;
                  }

                  if (!var1) {
                     try {
                        var3 = ObjectHelper.requireNonNull(this.mapper.apply((T)var3), "The mapper returned a null Publisher");
                     } catch (Throwable var14) {
                        Exceptions.throwIfFatal(var14);
                        this.upstream.cancel();
                        this.errors.addThrowable(var14);
                        this.downstream.onError(this.errors.terminate());
                        return;
                     }

                     if (this.sourceMode != 1) {
                        var1 = this.consumed + 1;
                        if (var1 == this.limit) {
                           this.consumed = 0;
                           this.upstream.request(var1);
                        } else {
                           this.consumed = var1;
                        }
                     }

                     if (var3 instanceof Callable) {
                        Callable var18 = (Callable)var3;

                        try {
                           var3 = (Publisher)var18.call();
                        } catch (Throwable var13) {
                           Exceptions.throwIfFatal(var13);
                           this.upstream.cancel();
                           this.errors.addThrowable(var13);
                           this.downstream.onError(this.errors.terminate());
                           return;
                        }

                        if (var3 == null) {
                           continue;
                        }

                        if (this.inner.isUnbounded()) {
                           if (this.get() == 0 && this.compareAndSet(0, 1)) {
                              this.downstream.onNext(var3);
                              if (!this.compareAndSet(1, 0)) {
                                 this.downstream.onError(this.errors.terminate());
                                 return;
                              }
                           }
                           continue;
                        }

                        this.active = true;
                        this.inner.setSubscription(new FlowableConcatMap.WeakScalarSubscription<>(var3, this.inner));
                     } else {
                        this.active = true;
                        var3.subscribe(this.inner);
                     }
                  }
               }

               if (this.wip.decrementAndGet() == 0) {
                  break;
               }
            }
         }
      }

      @Override
      public void innerError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            this.upstream.cancel();
            if (this.getAndIncrement() == 0) {
               this.downstream.onError(this.errors.terminate());
            }
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void innerNext(R var1) {
         if (this.get() == 0 && this.compareAndSet(0, 1)) {
            this.downstream.onNext(var1);
            if (this.compareAndSet(1, 0)) {
               return;
            }

            this.downstream.onError(this.errors.terminate());
         }
      }

      public void onError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            this.inner.cancel();
            if (this.getAndIncrement() == 0) {
               this.downstream.onError(this.errors.terminate());
            }
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void request(long var1) {
         this.inner.request(var1);
      }

      @Override
      void subscribeActual() {
         this.downstream.onSubscribe(this);
      }
   }

   static final class ConcatMapInner<R> extends SubscriptionArbiter implements FlowableSubscriber<R> {
      private static final long serialVersionUID = 897683679971470653L;
      final FlowableConcatMap.ConcatMapSupport<R> parent;
      long produced;

      ConcatMapInner(FlowableConcatMap.ConcatMapSupport<R> var1) {
         super(false);
         this.parent = var1;
      }

      public void onComplete() {
         long var1 = this.produced;
         if (var1 != 0L) {
            this.produced = 0L;
            this.produced(var1);
         }

         this.parent.innerComplete();
      }

      public void onError(Throwable var1) {
         long var2 = this.produced;
         if (var2 != 0L) {
            this.produced = 0L;
            this.produced(var2);
         }

         this.parent.innerError(var1);
      }

      public void onNext(R var1) {
         this.produced++;
         this.parent.innerNext((R)var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         this.setSubscription(var1);
      }
   }

   interface ConcatMapSupport<T> {
      void innerComplete();

      void innerError(Throwable var1);

      void innerNext(T var1);
   }

   static final class WeakScalarSubscription<T> implements Subscription {
      final Subscriber<? super T> downstream;
      boolean once;
      final T value;

      WeakScalarSubscription(T var1, Subscriber<? super T> var2) {
         this.value = (T)var1;
         this.downstream = var2;
      }

      public void cancel() {
      }

      public void request(long var1) {
         if (var1 > 0L && !this.once) {
            this.once = true;
            Subscriber var3 = this.downstream;
            var3.onNext(this.value);
            var3.onComplete();
         }
      }
   }
}
