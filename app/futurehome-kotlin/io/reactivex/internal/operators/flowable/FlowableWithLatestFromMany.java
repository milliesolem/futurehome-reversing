package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWithLatestFromMany<T, R> extends AbstractFlowableWithUpstream<T, R> {
   final Function<? super Object[], R> combiner;
   final Publisher<?>[] otherArray;
   final Iterable<? extends Publisher<?>> otherIterable;

   public FlowableWithLatestFromMany(Flowable<T> var1, Iterable<? extends Publisher<?>> var2, Function<? super Object[], R> var3) {
      super(var1);
      this.otherArray = null;
      this.otherIterable = var2;
      this.combiner = var3;
   }

   public FlowableWithLatestFromMany(Flowable<T> var1, Publisher<?>[] var2, Function<? super Object[], R> var3) {
      super(var1);
      this.otherArray = var2;
      this.otherIterable = null;
      this.combiner = var3;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      Publisher[] var5 = this.otherArray;
      int var3;
      if (var5 == null) {
         Publisher[] var4 = new Publisher[8];

         Iterator var6;
         try {
            var6 = this.otherIterable.iterator();
         } catch (Throwable var17) {
            Exceptions.throwIfFatal(var17);
            EmptySubscription.error(var17, var1);
            return;
         }

         int var2 = 0;

         while (true) {
            var5 = var4;
            var3 = var2;

            Publisher var7;
            try {
               if (!var6.hasNext()) {
                  break;
               }

               var7 = (Publisher)var6.next();
            } catch (Throwable var18) {
               Exceptions.throwIfFatal(var18);
               EmptySubscription.error(var18, var1);
               return;
            }

            var5 = var4;

            try {
               if (var2 == var4.length) {
                  var5 = Arrays.copyOf(var4, (var2 >> 1) + var2);
               }
            } catch (Throwable var19) {
               Exceptions.throwIfFatal(var19);
               EmptySubscription.error(var19, var1);
               return;
            }

            var5[var2] = var7;
            var2++;
            var4 = var5;
         }
      } else {
         var3 = var5.length;
      }

      if (var3 == 0) {
         new FlowableMap<>(this.source, new FlowableWithLatestFromMany.SingletonArrayFunc(this)).subscribeActual(var1);
      } else {
         FlowableWithLatestFromMany.WithLatestFromSubscriber var20 = new FlowableWithLatestFromMany.WithLatestFromSubscriber<>(var1, this.combiner, var3);
         var1.onSubscribe(var20);
         var20.subscribe(var5, var3);
         this.source.subscribe(var20);
      }
   }

   final class SingletonArrayFunc implements Function<T, R> {
      final FlowableWithLatestFromMany this$0;

      SingletonArrayFunc(FlowableWithLatestFromMany var1) {
         this.this$0 = var1;
      }

      @Override
      public R apply(T var1) throws Exception {
         return ObjectHelper.requireNonNull(this.this$0.combiner.apply(new Object[]{var1}), "The combiner returned a null value");
      }
   }

   static final class WithLatestFromSubscriber<T, R> extends AtomicInteger implements ConditionalSubscriber<T>, Subscription {
      private static final long serialVersionUID = 1577321883966341961L;
      final Function<? super Object[], R> combiner;
      volatile boolean done;
      final Subscriber<? super R> downstream;
      final AtomicThrowable error;
      final AtomicLong requested;
      final FlowableWithLatestFromMany.WithLatestInnerSubscriber[] subscribers;
      final AtomicReference<Subscription> upstream;
      final AtomicReferenceArray<Object> values;

      WithLatestFromSubscriber(Subscriber<? super R> var1, Function<? super Object[], R> var2, int var3) {
         this.downstream = var1;
         this.combiner = var2;
         FlowableWithLatestFromMany.WithLatestInnerSubscriber[] var5 = new FlowableWithLatestFromMany.WithLatestInnerSubscriber[var3];

         for (int var4 = 0; var4 < var3; var4++) {
            var5[var4] = new FlowableWithLatestFromMany.WithLatestInnerSubscriber(this, var4);
         }

         this.subscribers = var5;
         this.values = new AtomicReferenceArray<>(var3);
         this.upstream = new AtomicReference<>();
         this.requested = new AtomicLong();
         this.error = new AtomicThrowable();
      }

      public void cancel() {
         SubscriptionHelper.cancel(this.upstream);
         FlowableWithLatestFromMany.WithLatestInnerSubscriber[] var3 = this.subscribers;
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].dispose();
         }
      }

      void cancelAllBut(int var1) {
         FlowableWithLatestFromMany.WithLatestInnerSubscriber[] var3 = this.subscribers;

         for (int var2 = 0; var2 < var3.length; var2++) {
            if (var2 != var1) {
               var3[var2].dispose();
            }
         }
      }

      void innerComplete(int var1, boolean var2) {
         if (!var2) {
            this.done = true;
            SubscriptionHelper.cancel(this.upstream);
            this.cancelAllBut(var1);
            HalfSerializer.onComplete(this.downstream, this, this.error);
         }
      }

      void innerError(int var1, Throwable var2) {
         this.done = true;
         SubscriptionHelper.cancel(this.upstream);
         this.cancelAllBut(var1);
         HalfSerializer.onError(this.downstream, var2, this, this.error);
      }

      void innerNext(int var1, Object var2) {
         this.values.set(var1, var2);
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.cancelAllBut(-1);
            HalfSerializer.onComplete(this.downstream, this, this.error);
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.cancelAllBut(-1);
            HalfSerializer.onError(this.downstream, var1, this, this.error);
         }
      }

      public void onNext(T var1) {
         if (!this.tryOnNext((T)var1) && !this.done) {
            this.upstream.get().request(1L);
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, var1);
      }

      public void request(long var1) {
         SubscriptionHelper.deferredRequest(this.upstream, this.requested, var1);
      }

      void subscribe(Publisher<?>[] var1, int var2) {
         FlowableWithLatestFromMany.WithLatestInnerSubscriber[] var5 = this.subscribers;
         AtomicReference var4 = this.upstream;

         for (int var3 = 0; var3 < var2; var3++) {
            if (var4.get() == SubscriptionHelper.CANCELLED) {
               return;
            }

            var1[var3].subscribe(var5[var3]);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean tryOnNext(T var1) {
         if (this.done) {
            return false;
         } else {
            AtomicReferenceArray var5 = this.values;
            int var3 = var5.length();
            Object[] var4 = new Object[var3 + 1];
            var4[0] = var1;
            int var2 = 0;

            while (var2 < var3) {
               var1 = var5.get(var2);
               if (var1 == null) {
                  return false;
               }

               var4[++var2] = var1;
            }

            try {
               var1 = ObjectHelper.requireNonNull(this.combiner.apply(var4), "The combiner returned a null value");
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               this.cancel();
               this.onError(var7);
               return false;
            }

            HalfSerializer.onNext(this.downstream, var1, this, this.error);
            return true;
         }
      }
   }

   static final class WithLatestInnerSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
      private static final long serialVersionUID = 3256684027868224024L;
      boolean hasValue;
      final int index;
      final FlowableWithLatestFromMany.WithLatestFromSubscriber<?, ?> parent;

      WithLatestInnerSubscriber(FlowableWithLatestFromMany.WithLatestFromSubscriber<?, ?> var1, int var2) {
         this.parent = var1;
         this.index = var2;
      }

      void dispose() {
         SubscriptionHelper.cancel(this);
      }

      public void onComplete() {
         this.parent.innerComplete(this.index, this.hasValue);
      }

      public void onError(Throwable var1) {
         this.parent.innerError(this.index, var1);
      }

      public void onNext(Object var1) {
         if (!this.hasValue) {
            this.hasValue = true;
         }

         this.parent.innerNext(this.index, var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.setOnce(this, var1, Long.MAX_VALUE);
      }
   }
}
