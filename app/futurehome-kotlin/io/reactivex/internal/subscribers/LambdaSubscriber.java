package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.observers.LambdaConsumerIntrospection;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class LambdaSubscriber<T>
   extends AtomicReference<Subscription>
   implements FlowableSubscriber<T>,
   Subscription,
   Disposable,
   LambdaConsumerIntrospection {
   private static final long serialVersionUID = -7251123623727029452L;
   final Action onComplete;
   final Consumer<? super Throwable> onError;
   final Consumer<? super T> onNext;
   final Consumer<? super Subscription> onSubscribe;

   public LambdaSubscriber(Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3, Consumer<? super Subscription> var4) {
      this.onNext = var1;
      this.onError = var2;
      this.onComplete = var3;
      this.onSubscribe = var4;
   }

   public void cancel() {
      SubscriptionHelper.cancel(this);
   }

   @Override
   public void dispose() {
      this.cancel();
   }

   @Override
   public boolean hasCustomOnError() {
      boolean var1;
      if (this.onError != Functions.ON_ERROR_MISSING) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
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

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void onComplete() {
      if (this.get() != SubscriptionHelper.CANCELLED) {
         this.lazySet(SubscriptionHelper.CANCELLED);

         try {
            this.onComplete.run();
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            RxJavaPlugins.onError(var3);
            return;
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void onError(Throwable var1) {
      if (this.get() != SubscriptionHelper.CANCELLED) {
         this.lazySet(SubscriptionHelper.CANCELLED);

         try {
            this.onError.accept(var1);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            RxJavaPlugins.onError(new CompositeException(var1, var4));
            return;
         }
      } else {
         RxJavaPlugins.onError(var1);
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void onNext(T var1) {
      if (!this.isDisposed()) {
         try {
            this.onNext.accept((T)var1);
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.get().cancel();
            this.onError(var3);
            return;
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onSubscribe(Subscription var1) {
      if (SubscriptionHelper.setOnce(this, var1)) {
         try {
            this.onSubscribe.accept(this);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            var1.cancel();
            this.onError(var4);
            return;
         }
      }
   }

   public void request(long var1) {
      this.get().request(var1);
   }
}
