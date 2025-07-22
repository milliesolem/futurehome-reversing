package io.reactivex.internal.operators.parallel;

import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelFilter<T> extends ParallelFlowable<T> {
   final Predicate<? super T> predicate;
   final ParallelFlowable<T> source;

   public ParallelFilter(ParallelFlowable<T> var1, Predicate<? super T> var2) {
      this.source = var1;
      this.predicate = var2;
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
               var4[var2] = new ParallelFilter.ParallelFilterConditionalSubscriber<>((ConditionalSubscriber<? super T>)var5, this.predicate);
            } else {
               var4[var2] = new ParallelFilter.ParallelFilterSubscriber<>(var5, this.predicate);
            }
         }

         this.source.subscribe(var4);
      }
   }

   abstract static class BaseFilterSubscriber<T> implements ConditionalSubscriber<T>, Subscription {
      boolean done;
      final Predicate<? super T> predicate;
      Subscription upstream;

      BaseFilterSubscriber(Predicate<? super T> var1) {
         this.predicate = var1;
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

   static final class ParallelFilterConditionalSubscriber<T> extends ParallelFilter.BaseFilterSubscriber<T> {
      final ConditionalSubscriber<? super T> downstream;

      ParallelFilterConditionalSubscriber(ConditionalSubscriber<? super T> var1, Predicate<? super T> var2) {
         super(var2);
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

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean tryOnNext(T var1) {
         if (!this.done) {
            boolean var2;
            try {
               var2 = this.predicate.test((T)var1);
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.cancel();
               this.onError(var4);
               return false;
            }

            if (var2) {
               return this.downstream.tryOnNext((T)var1);
            }
         }

         return false;
      }
   }

   static final class ParallelFilterSubscriber<T> extends ParallelFilter.BaseFilterSubscriber<T> {
      final Subscriber<? super T> downstream;

      ParallelFilterSubscriber(Subscriber<? super T> var1, Predicate<? super T> var2) {
         super(var2);
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

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean tryOnNext(T var1) {
         if (!this.done) {
            boolean var2;
            try {
               var2 = this.predicate.test((T)var1);
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.cancel();
               this.onError(var4);
               return false;
            }

            if (var2) {
               this.downstream.onNext(var1);
               return true;
            }
         }

         return false;
      }
   }
}
