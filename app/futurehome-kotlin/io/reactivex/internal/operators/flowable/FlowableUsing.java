package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableUsing<T, D> extends Flowable<T> {
   final Consumer<? super D> disposer;
   final boolean eager;
   final Callable<? extends D> resourceSupplier;
   final Function<? super D, ? extends Publisher<? extends T>> sourceSupplier;

   public FlowableUsing(Callable<? extends D> var1, Function<? super D, ? extends Publisher<? extends T>> var2, Consumer<? super D> var3, boolean var4) {
      this.resourceSupplier = var1;
      this.sourceSupplier = var2;
      this.disposer = var3;
      this.eager = var4;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      Object var2;
      try {
         var2 = this.resourceSupplier.call();
      } catch (Throwable var15) {
         Exceptions.throwIfFatal(var15);
         EmptySubscription.error(var15, var1);
         return;
      }

      Publisher var3;
      try {
         var3 = ObjectHelper.requireNonNull(this.sourceSupplier.apply((D)var2), "The sourceSupplier returned a null Publisher");
      } catch (Throwable var14) {
         Exceptions.throwIfFatal(var14);

         try {
            this.disposer.accept((D)var2);
         } catch (Throwable var13) {
            Exceptions.throwIfFatal(var13);
            EmptySubscription.error(new CompositeException(var14, var13), var1);
            return;
         }

         EmptySubscription.error(var14, var1);
         return;
      }

      var3.subscribe(new FlowableUsing.UsingSubscriber<>(var1, var2, this.disposer, this.eager));
   }

   static final class UsingSubscriber<T, D> extends AtomicBoolean implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = 5904473792286235046L;
      final Consumer<? super D> disposer;
      final Subscriber<? super T> downstream;
      final boolean eager;
      final D resource;
      Subscription upstream;

      UsingSubscriber(Subscriber<? super T> var1, D var2, Consumer<? super D> var3, boolean var4) {
         this.downstream = var1;
         this.resource = (D)var2;
         this.disposer = var3;
         this.eager = var4;
      }

      public void cancel() {
         this.disposeAfter();
         this.upstream.cancel();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void disposeAfter() {
         if (this.compareAndSet(false, true)) {
            try {
               this.disposer.accept(this.resource);
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               RxJavaPlugins.onError(var3);
               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onComplete() {
         if (this.eager) {
            if (this.compareAndSet(false, true)) {
               try {
                  this.disposer.accept(this.resource);
               } catch (Throwable var3) {
                  Exceptions.throwIfFatal(var3);
                  this.downstream.onError(var3);
                  return;
               }
            }

            this.upstream.cancel();
            this.downstream.onComplete();
         } else {
            this.downstream.onComplete();
            this.upstream.cancel();
            this.disposeAfter();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onError(Throwable var1) {
         if (this.eager) {
            Throwable var2;
            label32: {
               if (this.compareAndSet(false, true)) {
                  try {
                     this.disposer.accept(this.resource);
                  } catch (Throwable var4) {
                     var2 = var4;
                     Exceptions.throwIfFatal(var4);
                     break label32;
                  }
               }

               var2 = null;
            }

            this.upstream.cancel();
            if (var2 != null) {
               this.downstream.onError(new CompositeException(var1, var2));
            } else {
               this.downstream.onError(var1);
            }
         } else {
            this.downstream.onError(var1);
            this.upstream.cancel();
            this.disposeAfter();
         }
      }

      public void onNext(T var1) {
         this.downstream.onNext(var1);
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
