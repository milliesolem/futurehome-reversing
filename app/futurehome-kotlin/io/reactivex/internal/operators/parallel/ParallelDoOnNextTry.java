package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFailureHandling;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelDoOnNextTry<T> extends ParallelFlowable<T> {
   final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
   final Consumer<? super T> onNext;
   final ParallelFlowable<T> source;

   public ParallelDoOnNextTry(ParallelFlowable<T> var1, Consumer<? super T> var2, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> var3) {
      this.source = var1;
      this.onNext = var2;
      this.errorHandler = var3;
   }

   @Override
   public int parallelism() {
      return this.source.parallelism();
   }

   @Override
   public void subscribe(Subscriber<? super T>[] var1) {
      if (this.validate(var1)) {
         int var3 = var1.length;
         Subscriber[] var4 = new Subscriber[var3];

         for (int var2 = 0; var2 < var3; var2++) {
            Subscriber var5 = var1[var2];
            if (var5 instanceof ConditionalSubscriber) {
               var4[var2] = new ParallelDoOnNextTry.ParallelDoOnNextConditionalSubscriber<>(
                  (ConditionalSubscriber<? super T>)var5, this.onNext, this.errorHandler
               );
            } else {
               var4[var2] = new ParallelDoOnNextTry.ParallelDoOnNextSubscriber<>(var5, this.onNext, this.errorHandler);
            }
         }

         this.source.subscribe(var4);
      }
   }

   static final class ParallelDoOnNextConditionalSubscriber<T> implements ConditionalSubscriber<T>, Subscription {
      boolean done;
      final ConditionalSubscriber<? super T> downstream;
      final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
      final Consumer<? super T> onNext;
      Subscription upstream;

      ParallelDoOnNextConditionalSubscriber(
         ConditionalSubscriber<? super T> var1, Consumer<? super T> var2, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> var3
      ) {
         this.downstream = var1;
         this.onNext = var2;
         this.errorHandler = var3;
      }

      public void cancel() {
         this.upstream.cancel();
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
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

      public void onNext(T var1) {
         if (!this.tryOnNext((T)var1) && !this.done) {
            this.upstream.request(1L);
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
         this.upstream.request(var1);
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean tryOnNext(T var1) {
         if (this.done) {
            return false;
         } else {
            long var3 = 0L;

            while (true) {
               try {
                  this.onNext.accept((T)var1);
                  return this.downstream.tryOnNext((T)var1);
               } catch (Throwable var18) {
                  Throwable var5 = var18;
                  Exceptions.throwIfFatal(var18);

                  BiFunction var6;
                  try {
                     var6 = this.errorHandler;
                  } catch (Throwable var17) {
                     Exceptions.throwIfFatal(var17);
                     this.cancel();
                     this.onError(new CompositeException(var18, var17));
                     return false;
                  }

                  var3++;

                  try {
                     var6 = ObjectHelper.requireNonNull((ParallelFailureHandling)var6.apply(var3, var5), "The errorHandler returned a null item");
                  } catch (Throwable var16) {
                     Exceptions.throwIfFatal(var16);
                     this.cancel();
                     this.onError(new CompositeException(var18, var16));
                     return false;
                  }

                  int var2 = <unrepresentable>.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling[var6.ordinal()];
                  if (var2 != 1) {
                     if (var2 != 2) {
                        if (var2 != 3) {
                           this.cancel();
                           this.onError(var18);
                           return false;
                        }

                        this.cancel();
                        this.onComplete();
                     }

                     return false;
                  }
                  continue;
               }
            }
         }
      }
   }

   static final class ParallelDoOnNextSubscriber<T> implements ConditionalSubscriber<T>, Subscription {
      boolean done;
      final Subscriber<? super T> downstream;
      final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
      final Consumer<? super T> onNext;
      Subscription upstream;

      ParallelDoOnNextSubscriber(
         Subscriber<? super T> var1, Consumer<? super T> var2, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> var3
      ) {
         this.downstream = var1;
         this.onNext = var2;
         this.errorHandler = var3;
      }

      public void cancel() {
         this.upstream.cancel();
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
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

      public void onNext(T var1) {
         if (!this.tryOnNext((T)var1)) {
            this.upstream.request(1L);
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
         this.upstream.request(var1);
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean tryOnNext(T var1) {
         if (this.done) {
            return false;
         } else {
            long var3 = 0L;

            while (true) {
               try {
                  this.onNext.accept((T)var1);
                  break;
               } catch (Throwable var18) {
                  Throwable var5 = var18;
                  Exceptions.throwIfFatal(var18);

                  BiFunction var6;
                  try {
                     var6 = this.errorHandler;
                  } catch (Throwable var17) {
                     Exceptions.throwIfFatal(var17);
                     this.cancel();
                     this.onError(new CompositeException(var18, var17));
                     return false;
                  }

                  var3++;

                  try {
                     var6 = ObjectHelper.requireNonNull((ParallelFailureHandling)var6.apply(var3, var5), "The errorHandler returned a null item");
                  } catch (Throwable var16) {
                     Exceptions.throwIfFatal(var16);
                     this.cancel();
                     this.onError(new CompositeException(var18, var16));
                     return false;
                  }

                  int var2 = <unrepresentable>.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling[var6.ordinal()];
                  if (var2 != 1) {
                     if (var2 != 2) {
                        if (var2 != 3) {
                           this.cancel();
                           this.onError(var18);
                           return false;
                        }

                        this.cancel();
                        this.onComplete();
                     }

                     return false;
                  }
                  continue;
               }
            }

            this.downstream.onNext(var1);
            return true;
         }
      }
   }
}
