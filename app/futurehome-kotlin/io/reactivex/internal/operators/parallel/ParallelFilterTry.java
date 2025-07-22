package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFailureHandling;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelFilterTry<T> extends ParallelFlowable<T> {
   final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
   final Predicate<? super T> predicate;
   final ParallelFlowable<T> source;

   public ParallelFilterTry(ParallelFlowable<T> var1, Predicate<? super T> var2, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> var3) {
      this.source = var1;
      this.predicate = var2;
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
               var4[var2] = new ParallelFilterTry.ParallelFilterConditionalSubscriber<>(
                  (ConditionalSubscriber<? super T>)var5, this.predicate, this.errorHandler
               );
            } else {
               var4[var2] = new ParallelFilterTry.ParallelFilterSubscriber<>(var5, this.predicate, this.errorHandler);
            }
         }

         this.source.subscribe(var4);
      }
   }

   abstract static class BaseFilterSubscriber<T> implements ConditionalSubscriber<T>, Subscription {
      boolean done;
      final BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> errorHandler;
      final Predicate<? super T> predicate;
      Subscription upstream;

      BaseFilterSubscriber(Predicate<? super T> var1, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> var2) {
         this.predicate = var1;
         this.errorHandler = var2;
      }

      public final void cancel() {
         this.upstream.cancel();
      }

      public final void onNext(T var1) {
         if (!this.tryOnNext((T)var1) && !this.done) {
            this.upstream.request(1L);
         }
      }

      public final void request(long var1) {
         this.upstream.request(var1);
      }
   }

   static final class ParallelFilterConditionalSubscriber<T> extends ParallelFilterTry.BaseFilterSubscriber<T> {
      final ConditionalSubscriber<? super T> downstream;

      ParallelFilterConditionalSubscriber(
         ConditionalSubscriber<? super T> var1, Predicate<? super T> var2, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> var3
      ) {
         super(var2, var3);
         this.downstream = var1;
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

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean tryOnNext(T var1) {
         boolean var5 = this.done;
         boolean var6 = false;
         if (!var5) {
            long var3 = 0L;

            while (true) {
               boolean var7;
               try {
                  var7 = this.predicate.test((T)var1);
               } catch (Throwable var21) {
                  Throwable var8 = var21;
                  Exceptions.throwIfFatal(var21);

                  BiFunction var9;
                  try {
                     var9 = this.errorHandler;
                  } catch (Throwable var20) {
                     Exceptions.throwIfFatal(var20);
                     this.cancel();
                     this.onError(new CompositeException(var21, var20));
                     break;
                  }

                  var3++;

                  try {
                     var9 = ObjectHelper.requireNonNull((ParallelFailureHandling)var9.apply(var3, var8), "The errorHandler returned a null item");
                  } catch (Throwable var19) {
                     Exceptions.throwIfFatal(var19);
                     this.cancel();
                     this.onError(new CompositeException(var21, var19));
                     break;
                  }

                  int var2 = <unrepresentable>.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling[var9.ordinal()];
                  if (var2 != 1) {
                     if (var2 != 2) {
                        if (var2 != 3) {
                           this.cancel();
                           this.onError(var21);
                           return false;
                        }

                        this.cancel();
                        this.onComplete();
                     }

                     return false;
                  }
                  continue;
               }

               var5 = var6;
               if (var7) {
                  var5 = var6;
                  if (this.downstream.tryOnNext((T)var1)) {
                     var5 = true;
                  }
               }

               return var5;
            }
         }

         return false;
      }
   }

   static final class ParallelFilterSubscriber<T> extends ParallelFilterTry.BaseFilterSubscriber<T> {
      final Subscriber<? super T> downstream;

      ParallelFilterSubscriber(Subscriber<? super T> var1, Predicate<? super T> var2, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> var3) {
         super(var2, var3);
         this.downstream = var1;
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

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean tryOnNext(T var1) {
         if (!this.done) {
            long var3 = 0L;

            while (true) {
               boolean var5;
               try {
                  var5 = this.predicate.test((T)var1);
               } catch (Throwable var19) {
                  Throwable var6 = var19;
                  Exceptions.throwIfFatal(var19);

                  BiFunction var7;
                  try {
                     var7 = this.errorHandler;
                  } catch (Throwable var18) {
                     Exceptions.throwIfFatal(var18);
                     this.cancel();
                     this.onError(new CompositeException(var19, var18));
                     break;
                  }

                  var3++;

                  try {
                     var7 = ObjectHelper.requireNonNull((ParallelFailureHandling)var7.apply(var3, var6), "The errorHandler returned a null item");
                  } catch (Throwable var17) {
                     Exceptions.throwIfFatal(var17);
                     this.cancel();
                     this.onError(new CompositeException(var19, var17));
                     break;
                  }

                  int var2 = <unrepresentable>.$SwitchMap$io$reactivex$parallel$ParallelFailureHandling[var7.ordinal()];
                  if (var2 != 1) {
                     if (var2 != 2) {
                        if (var2 != 3) {
                           this.cancel();
                           this.onError(var19);
                           return false;
                        }

                        this.cancel();
                        this.onComplete();
                     }

                     return false;
                  }
                  continue;
               }

               if (var5) {
                  this.downstream.onNext(var1);
                  return true;
               }

               return false;
            }
         }

         return false;
      }
   }
}
