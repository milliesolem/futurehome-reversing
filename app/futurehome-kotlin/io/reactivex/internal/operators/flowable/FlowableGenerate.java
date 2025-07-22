package io.reactivex.internal.operators.flowable;

import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableGenerate<T, S> extends Flowable<T> {
   final Consumer<? super S> disposeState;
   final BiFunction<S, Emitter<T>, S> generator;
   final Callable<S> stateSupplier;

   public FlowableGenerate(Callable<S> var1, BiFunction<S, Emitter<T>, S> var2, Consumer<? super S> var3) {
      this.stateSupplier = var1;
      this.generator = var2;
      this.disposeState = var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      Object var2;
      try {
         var2 = this.stateSupplier.call();
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptySubscription.error(var4, var1);
         return;
      }

      var1.onSubscribe(new FlowableGenerate.GeneratorSubscription<>(var1, this.generator, this.disposeState, (S)var2));
   }

   static final class GeneratorSubscription<T, S> extends AtomicLong implements Emitter<T>, Subscription {
      private static final long serialVersionUID = 7565982551505011832L;
      volatile boolean cancelled;
      final Consumer<? super S> disposeState;
      final Subscriber<? super T> downstream;
      final BiFunction<S, ? super Emitter<T>, S> generator;
      boolean hasNext;
      S state;
      boolean terminate;

      GeneratorSubscription(Subscriber<? super T> var1, BiFunction<S, ? super Emitter<T>, S> var2, Consumer<? super S> var3, S var4) {
         this.downstream = var1;
         this.generator = var2;
         this.disposeState = var3;
         this.state = (S)var4;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      private void dispose(S var1) {
         try {
            this.disposeState.accept((S)var1);
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            RxJavaPlugins.onError(var3);
            return;
         }
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            if (BackpressureHelper.add(this, 1L) == 0L) {
               Object var1 = this.state;
               this.state = null;
               this.dispose((S)var1);
            }
         }
      }

      @Override
      public void onComplete() {
         if (!this.terminate) {
            this.terminate = true;
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.terminate) {
            RxJavaPlugins.onError(var1);
         } else {
            Object var2 = var1;
            if (var1 == null) {
               var2 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }

            this.terminate = true;
            this.downstream.onError((Throwable)var2);
         }
      }

      @Override
      public void onNext(T var1) {
         if (!this.terminate) {
            if (this.hasNext) {
               this.onError(new IllegalStateException("onNext already called in this generate turn"));
            } else if (var1 == null) {
               this.onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            } else {
               this.hasNext = true;
               this.downstream.onNext(var1);
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            if (BackpressureHelper.add(this, var1) == 0L) {
               Object var7 = this.state;
               BiFunction var9 = this.generator;

               label76:
               while (true) {
                  long var3 = 0L;

                  while (true) {
                     Object var8 = var7;
                     if (var3 != var1) {
                        if (this.cancelled) {
                           this.state = null;
                           this.dispose((S)var7);
                           return;
                        }

                        this.hasNext = false;

                        try {
                           var7 = var9.apply(var8, this);
                        } catch (Throwable var11) {
                           Exceptions.throwIfFatal(var11);
                           this.cancelled = true;
                           this.state = null;
                           this.onError(var11);
                           this.dispose((S)var8);
                           return;
                        }

                        if (this.terminate) {
                           this.cancelled = true;
                           this.state = null;
                           this.dispose((S)var7);
                           return;
                        }

                        var3++;
                     } else {
                        long var5 = this.get();
                        var7 = var7;
                        var1 = var5;
                        if (var3 == var5) {
                           this.state = (S)var8;
                           var3 = this.addAndGet(-var3);
                           var7 = var8;
                           var1 = var3;
                           if (var3 == 0L) {
                              break label76;
                           }
                           break;
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
