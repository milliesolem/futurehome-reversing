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
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFlatMap<T, U> extends AbstractFlowableWithUpstream<T, U> {
   final int bufferSize;
   final boolean delayErrors;
   final Function<? super T, ? extends Publisher<? extends U>> mapper;
   final int maxConcurrency;

   public FlowableFlatMap(Flowable<T> var1, Function<? super T, ? extends Publisher<? extends U>> var2, boolean var3, int var4, int var5) {
      super(var1);
      this.mapper = var2;
      this.delayErrors = var3;
      this.maxConcurrency = var4;
      this.bufferSize = var5;
   }

   public static <T, U> FlowableSubscriber<T> subscribe(
      Subscriber<? super U> var0, Function<? super T, ? extends Publisher<? extends U>> var1, boolean var2, int var3, int var4
   ) {
      return new FlowableFlatMap.MergeSubscriber<>(var0, var1, var2, var3, var4);
   }

   @Override
   protected void subscribeActual(Subscriber<? super U> var1) {
      if (!FlowableScalarXMap.tryScalarXMapSubscribe(this.source, var1, this.mapper)) {
         this.source.subscribe(subscribe(var1, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
      }
   }

   static final class InnerSubscriber<T, U> extends AtomicReference<Subscription> implements FlowableSubscriber<U>, Disposable {
      private static final long serialVersionUID = -4606175640614850599L;
      final int bufferSize;
      volatile boolean done;
      int fusionMode;
      final long id;
      final int limit;
      final FlowableFlatMap.MergeSubscriber<T, U> parent;
      long produced;
      volatile SimpleQueue<U> queue;

      InnerSubscriber(FlowableFlatMap.MergeSubscriber<T, U> var1, long var2) {
         this.id = var2;
         this.parent = var1;
         int var4 = var1.bufferSize;
         this.bufferSize = var4;
         this.limit = var4 >> 2;
      }

      @Override
      public void dispose() {
         SubscriptionHelper.cancel(this);
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == SubscriptionHelper.CANCELLED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void onComplete() {
         this.done = true;
         this.parent.drain();
      }

      public void onError(Throwable var1) {
         this.lazySet(SubscriptionHelper.CANCELLED);
         this.parent.innerError(this, var1);
      }

      public void onNext(U var1) {
         if (this.fusionMode != 2) {
            this.parent.tryEmit((U)var1, this);
         } else {
            this.parent.drain();
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
               }
            }

            var1.request(this.bufferSize);
         }
      }

      void requestMore(long var1) {
         if (this.fusionMode != 1) {
            var1 = this.produced + var1;
            if (var1 >= this.limit) {
               this.produced = 0L;
               this.get().request(var1);
            } else {
               this.produced = var1;
            }
         }
      }
   }

   static final class MergeSubscriber<T, U> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
      static final FlowableFlatMap.InnerSubscriber<?, ?>[] CANCELLED = new FlowableFlatMap.InnerSubscriber[0];
      static final FlowableFlatMap.InnerSubscriber<?, ?>[] EMPTY = new FlowableFlatMap.InnerSubscriber[0];
      private static final long serialVersionUID = -2117620485640801370L;
      final int bufferSize;
      volatile boolean cancelled;
      final boolean delayErrors;
      volatile boolean done;
      final Subscriber<? super U> downstream;
      final AtomicThrowable errs = new AtomicThrowable();
      long lastId;
      int lastIndex;
      final Function<? super T, ? extends Publisher<? extends U>> mapper;
      final int maxConcurrency;
      volatile SimplePlainQueue<U> queue;
      final AtomicLong requested;
      int scalarEmitted;
      final int scalarLimit;
      final AtomicReference<FlowableFlatMap.InnerSubscriber<?, ?>[]> subscribers;
      long uniqueId;
      Subscription upstream;

      MergeSubscriber(Subscriber<? super U> var1, Function<? super T, ? extends Publisher<? extends U>> var2, boolean var3, int var4, int var5) {
         AtomicReference var6 = new AtomicReference();
         this.subscribers = var6;
         this.requested = new AtomicLong();
         this.downstream = var1;
         this.mapper = var2;
         this.delayErrors = var3;
         this.maxConcurrency = var4;
         this.bufferSize = var5;
         this.scalarLimit = Math.max(1, var4 >> 1);
         var6.lazySet(EMPTY);
      }

      boolean addInner(FlowableFlatMap.InnerSubscriber<T, U> var1) {
         FlowableFlatMap.InnerSubscriber[] var3;
         FlowableFlatMap.InnerSubscriber[] var4;
         do {
            var3 = this.subscribers.get();
            if (var3 == CANCELLED) {
               var1.dispose();
               return false;
            }

            int var2 = var3.length;
            var4 = new FlowableFlatMap.InnerSubscriber[var2 + 1];
            System.arraycopy(var3, 0, var4, 0, var2);
            var4[var2] = var1;
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var3, var4));

         return true;
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.cancel();
            this.disposeAll();
            if (this.getAndIncrement() == 0) {
               SimplePlainQueue var1 = this.queue;
               if (var1 != null) {
                  var1.clear();
               }
            }
         }
      }

      boolean checkTerminate() {
         if (this.cancelled) {
            this.clearScalarQueue();
            return true;
         } else if (!this.delayErrors && this.errs.get() != null) {
            this.clearScalarQueue();
            Throwable var1 = this.errs.terminate();
            if (var1 != ExceptionHelper.TERMINATED) {
               this.downstream.onError(var1);
            }

            return true;
         } else {
            return false;
         }
      }

      void clearScalarQueue() {
         SimplePlainQueue var1 = this.queue;
         if (var1 != null) {
            var1.clear();
         }
      }

      void disposeAll() {
         FlowableFlatMap.InnerSubscriber[] var4 = this.subscribers.get();
         FlowableFlatMap.InnerSubscriber[] var3 = CANCELLED;
         if (var4 != var3) {
            var4 = this.subscribers.getAndSet(var3);
            if (var4 != var3) {
               int var2 = var4.length;

               for (int var1 = 0; var1 < var2; var1++) {
                  var4[var1].dispose();
               }

               Throwable var5 = this.errs.terminate();
               if (var5 != null && var5 != ExceptionHelper.TERMINATED) {
                  RxJavaPlugins.onError(var5);
               }
            }
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            this.drainLoop();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drainLoop() {
         Subscriber var22 = this.downstream;
         int var3 = 1;

         while (!this.checkTerminate()) {
            SimplePlainQueue var21 = this.queue;
            long var11 = this.requested.get();
            boolean var7;
            if (var11 == Long.MAX_VALUE) {
               var7 = true;
            } else {
               var7 = false;
            }

            long var15 = 0L;
            long var17 = var11;
            long var13 = var15;
            if (var21 != null) {
               while (true) {
                  var13 = 0L;

                  Object var20;
                  for (var20 = null; var11 != 0L; var11--) {
                     var20 = var21.poll();
                     if (this.checkTerminate()) {
                        return;
                     }

                     if (var20 == null) {
                        break;
                     }

                     var22.onNext(var20);
                     var15++;
                     var13++;
                  }

                  if (var13 != 0L) {
                     if (var7) {
                        var11 = Long.MAX_VALUE;
                     } else {
                        var11 = this.requested.addAndGet(-var13);
                     }
                  }

                  var17 = var11;
                  var13 = var15;
                  if (var11 == 0L) {
                     break;
                  }

                  if (var20 == null) {
                     var17 = var11;
                     var13 = var15;
                     break;
                  }
               }
            }

            boolean var19 = this.done;
            var21 = this.queue;
            FlowableFlatMap.InnerSubscriber[] var43 = this.subscribers.get();
            int var4 = var43.length;
            if (var19 && (var21 == null || var21.isEmpty()) && var4 == 0) {
               Throwable var44 = this.errs.terminate();
               if (var44 != ExceptionHelper.TERMINATED) {
                  if (var44 == null) {
                     var22.onComplete();
                  } else {
                     var22.onError(var44);
                  }
               }

               return;
            }

            boolean var28;
            if (var4 == 0) {
               var11 = var13;
               var28 = 0;
            } else {
               label362: {
                  var11 = this.lastId;
                  int var2 = this.lastIndex;
                  if (var4 > var2) {
                     var28 = var2;
                     if (var43[var2].id == var11) {
                        break label362;
                     }
                  }

                  var28 = var2;
                  if (var4 <= var2) {
                     var28 = 0;
                  }

                  for (int var30 = 0; var30 < var4 && var43[var28].id != var11; var30++) {
                     int var5 = var28 + 1;
                     var28 = var5;
                     if (var5 == var4) {
                        var28 = 0;
                     }
                  }

                  this.lastIndex = var28;
                  this.lastId = var43[var28].id;
               }

               int var6 = var28;
               boolean var31 = false;
               byte var33 = 0;
               var11 = var13;
               var28 = var4;
               var13 = var17;
               var4 = var33;

               label336:
               while (true) {
                  if (var4 >= var28) {
                     var28 = var31;
                     break;
                  }

                  if (this.checkTerminate()) {
                     return;
                  }

                  FlowableFlatMap.InnerSubscriber var23 = var43[var6];
                  Object var46 = null;

                  while (!this.checkTerminate()) {
                     int var8;
                     label375: {
                        SimpleQueue var24 = var23.queue;
                        if (var24 == null) {
                           var15 = var13;
                        } else {
                           for (var15 = 0L; var13 != 0L; var15++) {
                              try {
                                 var46 = var24.poll();
                              } catch (Throwable var26) {
                                 Exceptions.throwIfFatal(var26);
                                 var23.dispose();
                                 this.errs.addThrowable(var26);
                                 if (!this.delayErrors) {
                                    this.upstream.cancel();
                                 }

                                 if (this.checkTerminate()) {
                                    return;
                                 }

                                 this.removeInner(var23);
                                 var8 = var4 + 1;
                                 var34 = true;
                                 var17 = var11;
                                 break label375;
                              }

                              if (var46 == null) {
                                 break;
                              }

                              var22.onNext(var46);
                              if (this.checkTerminate()) {
                                 return;
                              }

                              var13--;
                           }

                           if (var15 != 0L) {
                              if (!var7) {
                                 var13 = this.requested.addAndGet(-var15);
                              } else {
                                 var13 = Long.MAX_VALUE;
                              }

                              var23.requestMore(var15);
                           }

                           var15 = var13;
                           if (var13 != 0L) {
                              if (var46 != null) {
                                 continue;
                              }

                              var15 = var13;
                           }
                        }

                        var19 = var23.done;
                        var24 = var23.queue;
                        if (var19 && (var24 == null || var24.isEmpty())) {
                           this.removeInner(var23);
                           if (this.checkTerminate()) {
                              return;
                           }

                           var11++;
                           var31 = true;
                        }

                        if (var15 == 0L) {
                           var28 = var31;
                           var43 = var43;
                           break label336;
                        }

                        int var10 = var6 + 1;
                        var34 = var31;
                        var6 = var10;
                        var8 = var4;
                        var13 = var15;
                        var17 = var11;
                        if (var10 == var28) {
                           var6 = 0;
                           var17 = var11;
                           var13 = var15;
                           var8 = var4;
                           var34 = var31;
                        }
                     }

                     var4 = var8 + 1;
                     var31 = var34;
                     var11 = var17;
                     continue label336;
                  }

                  return;
               }

               this.lastIndex = var6;
               this.lastId = var43[var6].id;
            }

            if (var11 != 0L && !this.cancelled) {
               this.upstream.request(var11);
            }

            if (!var28) {
               var28 = this.addAndGet(-var3);
               var3 = var28;
               if (var28 == 0) {
                  return;
               }
            }
         }
      }

      SimpleQueue<U> getInnerQueue(FlowableFlatMap.InnerSubscriber<T, U> var1) {
         SimpleQueue var3 = var1.queue;
         Object var2 = var3;
         if (var3 == null) {
            var2 = new SpscArrayQueue(this.bufferSize);
            var1.queue = (SimpleQueue<U>)var2;
         }

         return (SimpleQueue<U>)var2;
      }

      SimpleQueue<U> getMainQueue() {
         SimplePlainQueue var2 = this.queue;
         Object var1 = var2;
         if (var2 == null) {
            if (this.maxConcurrency == Integer.MAX_VALUE) {
               var1 = new SpscLinkedArrayQueue(this.bufferSize);
            } else {
               var1 = new SpscArrayQueue(this.maxConcurrency);
            }

            this.queue = (SimplePlainQueue<U>)var1;
         }

         return (SimpleQueue<U>)var1;
      }

      void innerError(FlowableFlatMap.InnerSubscriber<T, U> var1, Throwable var2) {
         if (this.errs.addThrowable(var2)) {
            var1.done = true;
            if (!this.delayErrors) {
               this.upstream.cancel();
               FlowableFlatMap.InnerSubscriber[] var5 = this.subscribers.getAndSet(CANCELLED);
               int var4 = var5.length;

               for (int var3 = 0; var3 < var4; var3++) {
                  var5[var3].dispose();
               }
            }

            this.drain();
         } else {
            RxJavaPlugins.onError(var2);
         }
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
            if (this.errs.addThrowable(var1)) {
               this.done = true;
               if (!this.delayErrors) {
                  FlowableFlatMap.InnerSubscriber[] var4 = this.subscribers.getAndSet(CANCELLED);
                  int var3 = var4.length;

                  for (int var2 = 0; var2 < var3; var2++) {
                     var4[var2].dispose();
                  }
               }

               this.drain();
            } else {
               RxJavaPlugins.onError(var1);
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            Publisher var6;
            try {
               var6 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null Publisher");
            } catch (Throwable var12) {
               Exceptions.throwIfFatal(var12);
               this.upstream.cancel();
               this.onError(var12);
               return;
            }

            if (var6 instanceof Callable) {
               try {
                  var1 = ((Callable)var6).call();
               } catch (Throwable var11) {
                  Exceptions.throwIfFatal(var11);
                  this.errs.addThrowable(var11);
                  this.drain();
                  return;
               }

               if (var1 != null) {
                  this.tryEmitScalar((U)var1);
               } else if (this.maxConcurrency != Integer.MAX_VALUE && !this.cancelled) {
                  int var3 = this.scalarEmitted + 1;
                  this.scalarEmitted = var3;
                  int var2 = this.scalarLimit;
                  if (var3 == var2) {
                     this.scalarEmitted = 0;
                     this.upstream.request(var2);
                  }
               }
            } else {
               long var4 = (long)(this.uniqueId++);
               var1 = new FlowableFlatMap.InnerSubscriber<>(this, var4);
               if (this.addInner(var1)) {
                  var6.subscribe(var1);
               }
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            if (!this.cancelled) {
               int var2 = this.maxConcurrency;
               if (var2 == Integer.MAX_VALUE) {
                  var1.request(Long.MAX_VALUE);
               } else {
                  var1.request(var2);
               }
            }
         }
      }

      void removeInner(FlowableFlatMap.InnerSubscriber<T, U> var1) {
         FlowableFlatMap.InnerSubscriber[] var4;
         FlowableFlatMap.InnerSubscriber[] var5;
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
               var4 = new FlowableFlatMap.InnerSubscriber[var3 - 1];
               System.arraycopy(var5, 0, var4, 0, var2);
               System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
            }
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, var4));
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.drain();
         }
      }

      void tryEmit(U var1, FlowableFlatMap.InnerSubscriber<T, U> var2) {
         if (this.get() == 0 && this.compareAndSet(0, 1)) {
            long var3 = this.requested.get();
            SimpleQueue var8 = var2.queue;
            if (var3 != 0L && (var8 == null || var8.isEmpty())) {
               this.downstream.onNext(var1);
               if (var3 != Long.MAX_VALUE) {
                  this.requested.decrementAndGet();
               }

               var2.requestMore(1L);
            } else {
               SimpleQueue var7 = var8;
               if (var8 == null) {
                  var7 = this.getInnerQueue(var2);
               }

               if (!var7.offer(var1)) {
                  this.onError(new MissingBackpressureException("Inner queue full?!"));
                  return;
               }
            }

            if (this.decrementAndGet() == 0) {
               return;
            }
         } else {
            SimpleQueue var6 = var2.queue;
            Object var5 = var6;
            if (var6 == null) {
               var5 = new SpscArrayQueue(this.bufferSize);
               var2.queue = (SimpleQueue<U>)var5;
            }

            if (!((SimpleQueue<Object>)var5).offer(var1)) {
               this.onError(new MissingBackpressureException("Inner queue full?!"));
               return;
            }

            if (this.getAndIncrement() != 0) {
               return;
            }
         }

         this.drainLoop();
      }

      void tryEmitScalar(U var1) {
         if (this.get() == 0 && this.compareAndSet(0, 1)) {
            long var4 = this.requested.get();
            SimplePlainQueue var7 = this.queue;
            if (var4 != 0L && (var7 == null || var7.isEmpty())) {
               this.downstream.onNext(var1);
               if (var4 != Long.MAX_VALUE) {
                  this.requested.decrementAndGet();
               }

               if (this.maxConcurrency != Integer.MAX_VALUE && !this.cancelled) {
                  int var3 = this.scalarEmitted + 1;
                  this.scalarEmitted = var3;
                  int var2 = this.scalarLimit;
                  if (var3 == var2) {
                     this.scalarEmitted = 0;
                     this.upstream.request(var2);
                  }
               }
            } else {
               Object var6 = var7;
               if (var7 == null) {
                  var6 = this.getMainQueue();
               }

               if (!((SimpleQueue<Object>)var6).offer(var1)) {
                  this.onError(new IllegalStateException("Scalar queue full?!"));
                  return;
               }
            }

            if (this.decrementAndGet() == 0) {
               return;
            }
         } else {
            if (!this.getMainQueue().offer((U)var1)) {
               this.onError(new IllegalStateException("Scalar queue full?!"));
               return;
            }

            if (this.getAndIncrement() != 0) {
               return;
            }
         }

         this.drainLoop();
      }
   }
}
