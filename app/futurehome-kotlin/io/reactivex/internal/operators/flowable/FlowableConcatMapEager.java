package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscribers.InnerQueuedSubscriber;
import io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableConcatMapEager<T, R> extends AbstractFlowableWithUpstream<T, R> {
   final ErrorMode errorMode;
   final Function<? super T, ? extends Publisher<? extends R>> mapper;
   final int maxConcurrency;
   final int prefetch;

   public FlowableConcatMapEager(Flowable<T> var1, Function<? super T, ? extends Publisher<? extends R>> var2, int var3, int var4, ErrorMode var5) {
      super(var1);
      this.mapper = var2;
      this.maxConcurrency = var3;
      this.prefetch = var4;
      this.errorMode = var5;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      this.source
         .subscribe(new FlowableConcatMapEager.ConcatMapEagerDelayErrorSubscriber<>(var1, this.mapper, this.maxConcurrency, this.prefetch, this.errorMode));
   }

   static final class ConcatMapEagerDelayErrorSubscriber<T, R>
      extends AtomicInteger
      implements FlowableSubscriber<T>,
      Subscription,
      InnerQueuedSubscriberSupport<R> {
      private static final long serialVersionUID = -4255299542215038287L;
      volatile boolean cancelled;
      volatile InnerQueuedSubscriber<R> current;
      volatile boolean done;
      final Subscriber<? super R> downstream;
      final ErrorMode errorMode;
      final AtomicThrowable errors;
      final Function<? super T, ? extends Publisher<? extends R>> mapper;
      final int maxConcurrency;
      final int prefetch;
      final AtomicLong requested;
      final SpscLinkedArrayQueue<InnerQueuedSubscriber<R>> subscribers;
      Subscription upstream;

      ConcatMapEagerDelayErrorSubscriber(
         Subscriber<? super R> var1, Function<? super T, ? extends Publisher<? extends R>> var2, int var3, int var4, ErrorMode var5
      ) {
         this.downstream = var1;
         this.mapper = var2;
         this.maxConcurrency = var3;
         this.prefetch = var4;
         this.errorMode = var5;
         this.subscribers = new SpscLinkedArrayQueue<>(Math.min(var4, var3));
         this.errors = new AtomicThrowable();
         this.requested = new AtomicLong();
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.cancel();
            this.drainAndCancel();
         }
      }

      void cancelAll() {
         InnerQueuedSubscriber var1 = this.current;
         this.current = null;
         if (var1 != null) {
            var1.cancel();
         }

         while (true) {
            var1 = this.subscribers.poll();
            if (var1 == null) {
               return;
            }

            var1.cancel();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void drain() {
         if (this.getAndIncrement() == 0) {
            InnerQueuedSubscriber var11 = this.current;
            Subscriber var13 = this.downstream;
            ErrorMode var14 = this.errorMode;
            int var3 = 1;

            while (true) {
               long var9 = this.requested.get();
               if (var11 == null) {
                  if (var14 != ErrorMode.END && this.errors.get() != null) {
                     this.cancelAll();
                     var13.onError(this.errors.terminate());
                     return;
                  }

                  boolean var5 = this.done;
                  InnerQueuedSubscriber var12 = this.subscribers.poll();
                  if (var5 && var12 == null) {
                     Throwable var22 = this.errors.terminate();
                     if (var22 != null) {
                        var13.onError(var22);
                     } else {
                        var13.onComplete();
                     }

                     return;
                  }

                  var11 = var12;
                  if (var12 != null) {
                     this.current = var12;
                     var11 = var12;
                  }
               }

               boolean var1;
               long var7;
               label171: {
                  if (var11 != null) {
                     SimpleQueue var15 = var11.queue();
                     if (var15 != null) {
                        var7 = 0L;

                        boolean var2;
                        int var4;
                        InnerQueuedSubscriber var24;
                        while (true) {
                           long var25;
                           var4 = (var25 = var7 - var9) == 0L ? 0 : (var25 < 0L ? -1 : 1);
                           if (var4) {
                              if (this.cancelled) {
                                 this.cancelAll();
                                 return;
                              }

                              if (var14 == ErrorMode.IMMEDIATE && this.errors.get() != null) {
                                 this.current = null;
                                 var11.cancel();
                                 this.cancelAll();
                                 var13.onError(this.errors.terminate());
                                 return;
                              }

                              boolean var20 = var11.isDone();

                              try {
                                 var23 = var15.poll();
                              } catch (Throwable var17) {
                                 Exceptions.throwIfFatal(var17);
                                 this.current = null;
                                 var11.cancel();
                                 this.cancelAll();
                                 var13.onError(var17);
                                 return;
                              }

                              if (var23 == null) {
                                 var1 = (boolean)1;
                              } else {
                                 var1 = (boolean)0;
                              }

                              if (var20 && var1) {
                                 this.current = null;
                                 this.upstream.request(1L);
                                 var24 = null;
                                 var2 = true;
                                 break;
                              }

                              if (!var1) {
                                 var13.onNext(var23);
                                 var7++;
                                 var11.requestOne();
                                 continue;
                              }
                           }

                           var2 = false;
                           var24 = var11;
                           break;
                        }

                        var1 = var2;
                        var11 = var24;
                        if (!var4) {
                           if (this.cancelled) {
                              this.cancelAll();
                              return;
                           }

                           if (var14 == ErrorMode.IMMEDIATE && this.errors.get() != null) {
                              this.current = null;
                              var24.cancel();
                              this.cancelAll();
                              var13.onError(this.errors.terminate());
                              return;
                           }

                           boolean var21 = var24.isDone();
                           boolean var6 = var15.isEmpty();
                           var1 = var2;
                           var11 = var24;
                           if (var21) {
                              var1 = var2;
                              var11 = var24;
                              if (var6) {
                                 this.current = null;
                                 this.upstream.request(1L);
                                 var11 = null;
                                 var1 = 1;
                              }
                           }
                        }
                        break label171;
                     }
                  }

                  var1 = 0;
                  var7 = 0L;
               }

               if (var7 != 0L && var9 != Long.MAX_VALUE) {
                  this.requested.addAndGet(-var7);
               }

               if (!var1) {
                  var1 = this.addAndGet(-var3);
                  var3 = var1;
                  if (var1 == 0) {
                     return;
                  }
               }
            }
         }
      }

      void drainAndCancel() {
         if (this.getAndIncrement() == 0) {
            do {
               this.cancelAll();
            } while (this.decrementAndGet() != 0);
         }
      }

      @Override
      public void innerComplete(InnerQueuedSubscriber<R> var1) {
         var1.setDone();
         this.drain();
      }

      @Override
      public void innerError(InnerQueuedSubscriber<R> var1, Throwable var2) {
         if (this.errors.addThrowable(var2)) {
            var1.setDone();
            if (this.errorMode != ErrorMode.END) {
               this.upstream.cancel();
            }

            this.drain();
         } else {
            RxJavaPlugins.onError(var2);
         }
      }

      @Override
      public void innerNext(InnerQueuedSubscriber<R> var1, R var2) {
         if (var1.queue().offer(var2)) {
            this.drain();
         } else {
            var1.cancel();
            this.innerError(var1, new MissingBackpressureException());
         }
      }

      public void onComplete() {
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         try {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null Publisher");
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.upstream.cancel();
            this.onError(var4);
            return;
         }

         InnerQueuedSubscriber var2 = new InnerQueuedSubscriber<>(this, this.prefetch);
         if (!this.cancelled) {
            this.subscribers.offer(var2);
            var1.subscribe(var2);
            if (this.cancelled) {
               var2.cancel();
               this.drainAndCancel();
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            int var2 = this.maxConcurrency;
            long var3;
            if (var2 == Integer.MAX_VALUE) {
               var3 = Long.MAX_VALUE;
            } else {
               var3 = var2;
            }

            var1.request(var3);
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.drain();
         }
      }
   }
}
