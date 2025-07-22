package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelMap<T, R> extends ParallelFlowable<R> {
   final Function<? super T, ? extends R> mapper;
   final ParallelFlowable<T> source;

   public ParallelMap(ParallelFlowable<T> var1, Function<? super T, ? extends R> var2) {
      this.source = var1;
      this.mapper = var2;
   }

   @Override
   public int parallelism() {
      return this.source.parallelism();
   }

   @Override
   public void subscribe(Subscriber<? super R>[] var1) {
      if (this.validate(var1)) {
         int var3 = var1.length;
         Subscriber[] var5 = new Subscriber[var3];

         for (int var2 = 0; var2 < var3; var2++) {
            Subscriber var4 = var1[var2];
            if (var4 instanceof ConditionalSubscriber) {
               var5[var2] = new ParallelMap.ParallelMapConditionalSubscriber<>((ConditionalSubscriber<? super R>)var4, this.mapper);
            } else {
               var5[var2] = new ParallelMap.ParallelMapSubscriber<>(var4, this.mapper);
            }
         }

         this.source.subscribe(var5);
      }
   }

   static final class ParallelMapConditionalSubscriber<T, R> implements ConditionalSubscriber<T>, Subscription {
      boolean done;
      final ConditionalSubscriber<? super R> downstream;
      final Function<? super T, ? extends R> mapper;
      Subscription upstream;

      ParallelMapConditionalSubscriber(ConditionalSubscriber<? super R> var1, Function<? super T, ? extends R> var2) {
         this.downstream = var1;
         this.mapper = var2;
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

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            try {
               var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null value");
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.cancel();
               this.onError(var3);
               return;
            }

            this.downstream.onNext(var1);
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

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean tryOnNext(T var1) {
         if (this.done) {
            return false;
         } else {
            try {
               var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null value");
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.cancel();
               this.onError(var3);
               return false;
            }

            return this.downstream.tryOnNext((R)var1);
         }
      }
   }

   static final class ParallelMapSubscriber<T, R> implements FlowableSubscriber<T>, Subscription {
      boolean done;
      final Subscriber<? super R> downstream;
      final Function<? super T, ? extends R> mapper;
      Subscription upstream;

      ParallelMapSubscriber(Subscriber<? super R> var1, Function<? super T, ? extends R> var2) {
         this.downstream = var1;
         this.mapper = var2;
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

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            try {
               var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null value");
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.cancel();
               this.onError(var3);
               return;
            }

            this.downstream.onNext(var1);
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
   }
}
