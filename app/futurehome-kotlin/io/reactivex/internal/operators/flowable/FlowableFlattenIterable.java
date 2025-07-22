package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFlattenIterable<T, R> extends AbstractFlowableWithUpstream<T, R> {
   final Function<? super T, ? extends Iterable<? extends R>> mapper;
   final int prefetch;

   public FlowableFlattenIterable(Flowable<T> var1, Function<? super T, ? extends Iterable<? extends R>> var2, int var3) {
      super(var1);
      this.mapper = var2;
      this.prefetch = var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Subscriber<? super R> var1) {
      if (this.source instanceof Callable) {
         Object var2;
         try {
            var2 = ((Callable)this.source).call();
         } catch (Throwable var8) {
            Exceptions.throwIfFatal(var8);
            EmptySubscription.error(var8, var1);
            return;
         }

         if (var2 == null) {
            EmptySubscription.complete(var1);
         } else {
            try {
               var2 = this.mapper.apply((T)var2).iterator();
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               EmptySubscription.error(var7, var1);
               return;
            }

            FlowableFromIterable.subscribe(var1, (Iterator<? extends T>)var2);
         }
      } else {
         this.source.subscribe(new FlowableFlattenIterable.FlattenIterableSubscriber<>(var1, this.mapper, this.prefetch));
      }
   }

   static final class FlattenIterableSubscriber<T, R> extends BasicIntQueueSubscription<R> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -3096000382929934955L;
      volatile boolean cancelled;
      int consumed;
      Iterator<? extends R> current;
      volatile boolean done;
      final Subscriber<? super R> downstream;
      final AtomicReference<Throwable> error;
      int fusionMode;
      final int limit;
      final Function<? super T, ? extends Iterable<? extends R>> mapper;
      final int prefetch;
      SimpleQueue<T> queue;
      final AtomicLong requested;
      Subscription upstream;

      FlattenIterableSubscriber(Subscriber<? super R> var1, Function<? super T, ? extends Iterable<? extends R>> var2, int var3) {
         this.downstream = var1;
         this.mapper = var2;
         this.prefetch = var3;
         this.limit = var3 - (var3 >> 2);
         this.error = new AtomicReference<>();
         this.requested = new AtomicLong();
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.cancel();
            if (this.getAndIncrement() == 0) {
               this.queue.clear();
            }
         }
      }

      boolean checkTerminated(boolean var1, boolean var2, Subscriber<?> var3, SimpleQueue<?> var4) {
         if (this.cancelled) {
            this.current = null;
            var4.clear();
            return true;
         } else {
            if (var1) {
               if (this.error.get() != null) {
                  Throwable var5 = ExceptionHelper.terminate(this.error);
                  this.current = null;
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

      @Override
      public void clear() {
         this.current = null;
         this.queue.clear();
      }

      void consumedOne(boolean var1) {
         if (var1) {
            int var2 = this.consumed + 1;
            if (var2 == this.limit) {
               this.consumed = 0;
               this.upstream.request(var2);
            } else {
               this.consumed = var2;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            Subscriber var15 = this.downstream;
            SimpleQueue var16 = this.queue;
            boolean var3;
            if (this.fusionMode != 1) {
               var3 = true;
            } else {
               var3 = false;
            }

            Iterator var13 = this.current;
            int var1 = 1;

            label423:
            while (true) {
               Iterator var12;
               while (true) {
                  var12 = var13;
                  if (var13 != null) {
                     break;
                  }

                  boolean var5 = this.done;

                  Object var14;
                  try {
                     var14 = var16.poll();
                  } catch (Throwable var36) {
                     Exceptions.throwIfFatal(var36);
                     this.upstream.cancel();
                     ExceptionHelper.addThrowable(this.error, var36);
                     Throwable var41 = ExceptionHelper.terminate(this.error);
                     this.current = null;
                     var16.clear();
                     var15.onError(var41);
                     return;
                  }

                  boolean var4;
                  if (var14 == null) {
                     var4 = true;
                  } else {
                     var4 = false;
                  }

                  if (this.checkTerminated(var5, var4, var15, var16)) {
                     return;
                  }

                  var12 = var13;
                  if (var14 == null) {
                     break;
                  }

                  try {
                     var12 = this.mapper.apply((T)var14).iterator();
                     var4 = var12.hasNext();
                  } catch (Throwable var35) {
                     Exceptions.throwIfFatal(var35);
                     this.upstream.cancel();
                     ExceptionHelper.addThrowable(this.error, var35);
                     var15.onError(ExceptionHelper.terminate(this.error));
                     return;
                  }

                  if (var4) {
                     this.current = var12;
                     break;
                  }

                  this.consumedOne(var3);
                  var13 = null;
               }

               Iterator var43 = var12;
               if (var12 != null) {
                  long var10 = this.requested.get();
                  long var6 = 0L;

                  while (true) {
                     var13 = var12;
                     long var8 = var6;
                     if (var6 != var10) {
                        if (this.checkTerminated(this.done, false, var15, var16)) {
                           return;
                        }

                        try {
                           var13 = ObjectHelper.requireNonNull((Iterator)var12.next(), "The iterator returned a null value");
                        } catch (Throwable var33) {
                           Exceptions.throwIfFatal(var33);
                           this.current = null;
                           this.upstream.cancel();
                           ExceptionHelper.addThrowable(this.error, var33);
                           var15.onError(ExceptionHelper.terminate(this.error));
                           return;
                        }

                        var15.onNext(var13);
                        if (this.checkTerminated(this.done, false, var15, var16)) {
                           return;
                        }

                        var6++;

                        boolean var38;
                        try {
                           var38 = var12.hasNext();
                        } catch (Throwable var34) {
                           Exceptions.throwIfFatal(var34);
                           this.current = null;
                           this.upstream.cancel();
                           ExceptionHelper.addThrowable(this.error, var34);
                           var15.onError(ExceptionHelper.terminate(this.error));
                           return;
                        }

                        if (var38) {
                           continue;
                        }

                        this.consumedOne(var3);
                        this.current = null;
                        var13 = null;
                        var8 = var6;
                     }

                     if (var8 == var10) {
                        boolean var40 = this.done;
                        boolean var39;
                        if (var16.isEmpty() && var13 == null) {
                           var39 = true;
                        } else {
                           var39 = false;
                        }

                        if (this.checkTerminated(var40, var39, var15, var16)) {
                           return;
                        }
                     }

                     if (var8 != 0L && var10 != Long.MAX_VALUE) {
                        this.requested.addAndGet(-var8);
                     }

                     var43 = var13;
                     if (var13 == null) {
                        continue label423;
                     }
                     break;
                  }
               }

               int var2 = this.addAndGet(-var1);
               var13 = var43;
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            }
         }
      }

      @Override
      public boolean isEmpty() {
         boolean var1;
         if (this.current == null && this.queue.isEmpty()) {
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
         if (!this.done && ExceptionHelper.addThrowable(this.error, var1)) {
            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (!this.done) {
            if (this.fusionMode == 0 && !this.queue.offer((T)var1)) {
               this.onError(new MissingBackpressureException("Queue is full?!"));
            } else {
               this.drain();
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            if (var1 instanceof QueueSubscription) {
               QueueSubscription var3 = (QueueSubscription)var1;
               int var2 = var3.requestFusion(3);
               if (var2 == 1) {
                  this.fusionMode = var2;
                  this.queue = var3;
                  this.done = true;
                  this.downstream.onSubscribe(this);
                  return;
               }

               if (var2 == 2) {
                  this.fusionMode = var2;
                  this.queue = var3;
                  this.downstream.onSubscribe(this);
                  var1.request(this.prefetch);
                  return;
               }
            }

            this.queue = new SpscArrayQueue<>(this.prefetch);
            this.downstream.onSubscribe(this);
            var1.request(this.prefetch);
         }
      }

      @Override
      public R poll() throws Exception {
         Iterator var1 = this.current;

         Iterator var2;
         while (true) {
            var2 = var1;
            if (var1 != null) {
               break;
            }

            var1 = this.queue.poll();
            if (var1 == null) {
               return null;
            }

            var2 = this.mapper.apply((T)var1).iterator();
            if (var2.hasNext()) {
               this.current = var2;
               break;
            }

            var1 = null;
         }

         var1 = ObjectHelper.requireNonNull((Iterator)var2.next(), "The iterator returned a null value");
         if (!var2.hasNext()) {
            this.current = null;
         }

         return (R)var1;
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.drain();
         }
      }

      @Override
      public int requestFusion(int var1) {
         return (var1 & 1) != 0 && this.fusionMode == 1 ? 1 : 0;
      }
   }
}
