package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.LongConsumer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelPeek<T> extends ParallelFlowable<T> {
   final Consumer<? super T> onAfterNext;
   final Action onAfterTerminated;
   final Action onCancel;
   final Action onComplete;
   final Consumer<? super Throwable> onError;
   final Consumer<? super T> onNext;
   final LongConsumer onRequest;
   final Consumer<? super Subscription> onSubscribe;
   final ParallelFlowable<T> source;

   public ParallelPeek(
      ParallelFlowable<T> var1,
      Consumer<? super T> var2,
      Consumer<? super T> var3,
      Consumer<? super Throwable> var4,
      Action var5,
      Action var6,
      Consumer<? super Subscription> var7,
      LongConsumer var8,
      Action var9
   ) {
      this.source = var1;
      this.onNext = ObjectHelper.requireNonNull(var2, "onNext is null");
      this.onAfterNext = ObjectHelper.requireNonNull(var3, "onAfterNext is null");
      this.onError = ObjectHelper.requireNonNull(var4, "onError is null");
      this.onComplete = ObjectHelper.requireNonNull(var5, "onComplete is null");
      this.onAfterTerminated = ObjectHelper.requireNonNull(var6, "onAfterTerminated is null");
      this.onSubscribe = ObjectHelper.requireNonNull(var7, "onSubscribe is null");
      this.onRequest = ObjectHelper.requireNonNull(var8, "onRequest is null");
      this.onCancel = ObjectHelper.requireNonNull(var9, "onCancel is null");
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
            var4[var2] = new ParallelPeek.ParallelPeekSubscriber<>(var1[var2], this);
         }

         this.source.subscribe(var4);
      }
   }

   static final class ParallelPeekSubscriber<T> implements FlowableSubscriber<T>, Subscription {
      boolean done;
      final Subscriber<? super T> downstream;
      final ParallelPeek<T> parent;
      Subscription upstream;

      ParallelPeekSubscriber(Subscriber<? super T> var1, ParallelPeek<T> var2) {
         this.downstream = var1;
         this.parent = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void cancel() {
         label17:
         try {
            this.parent.onCancel.run();
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            RxJavaPlugins.onError(var3);
            break label17;
         }

         this.upstream.cancel();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onComplete() {
         if (!this.done) {
            this.done = true;

            try {
               this.parent.onComplete.run();
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               this.downstream.onError(var7);
               return;
            }

            this.downstream.onComplete();

            try {
               this.parent.onAfterTerminated.run();
            } catch (Throwable var6) {
               Exceptions.throwIfFatal(var6);
               RxJavaPlugins.onError(var6);
               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError((Throwable)var1);
         } else {
            this.done = true;

            label51:
            try {
               this.parent.onError.accept((Throwable)var1);
            } catch (Throwable var8) {
               Exceptions.throwIfFatal(var8);
               var1 = new CompositeException((Throwable)var1, var8);
               break label51;
            }

            this.downstream.onError((Throwable)var1);

            try {
               this.parent.onAfterTerminated.run();
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               RxJavaPlugins.onError(var7);
               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            try {
               this.parent.onNext.accept((T)var1);
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               this.onError(var7);
               return;
            }

            this.downstream.onNext(var1);

            try {
               this.parent.onAfterNext.accept((T)var1);
            } catch (Throwable var6) {
               Exceptions.throwIfFatal(var6);
               this.onError(var6);
               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;

            try {
               this.parent.onSubscribe.accept(var1);
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               var1.cancel();
               this.downstream.onSubscribe(EmptySubscription.INSTANCE);
               this.onError(var4);
               return;
            }

            this.downstream.onSubscribe(this);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void request(long var1) {
         label17:
         try {
            this.parent.onRequest.accept(var1);
         } catch (Throwable var5) {
            Exceptions.throwIfFatal(var5);
            RxJavaPlugins.onError(var5);
            break label17;
         }

         this.upstream.request(var1);
      }
   }
}
