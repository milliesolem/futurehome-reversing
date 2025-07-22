package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;

public final class FlowableMapNotification<T, R> extends AbstractFlowableWithUpstream<T, R> {
   final Callable<? extends R> onCompleteSupplier;
   final Function<? super Throwable, ? extends R> onErrorMapper;
   final Function<? super T, ? extends R> onNextMapper;

   public FlowableMapNotification(
      Flowable<T> var1, Function<? super T, ? extends R> var2, Function<? super Throwable, ? extends R> var3, Callable<? extends R> var4
   ) {
      super(var1);
      this.onNextMapper = var2;
      this.onErrorMapper = var3;
      this.onCompleteSupplier = var4;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      this.source.subscribe(new FlowableMapNotification.MapNotificationSubscriber<>(var1, this.onNextMapper, this.onErrorMapper, this.onCompleteSupplier));
   }

   static final class MapNotificationSubscriber<T, R> extends SinglePostCompleteSubscriber<T, R> {
      private static final long serialVersionUID = 2757120512858778108L;
      final Callable<? extends R> onCompleteSupplier;
      final Function<? super Throwable, ? extends R> onErrorMapper;
      final Function<? super T, ? extends R> onNextMapper;

      MapNotificationSubscriber(
         Subscriber<? super R> var1, Function<? super T, ? extends R> var2, Function<? super Throwable, ? extends R> var3, Callable<? extends R> var4
      ) {
         super(var1);
         this.onNextMapper = var2;
         this.onErrorMapper = var3;
         this.onCompleteSupplier = var4;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onComplete() {
         Object var1;
         try {
            var1 = ObjectHelper.requireNonNull(this.onCompleteSupplier.call(), "The onComplete publisher returned is null");
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.downstream.onError(var3);
            return;
         }

         this.complete((R)var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onError(Throwable var1) {
         Object var2;
         try {
            var2 = ObjectHelper.requireNonNull(this.onErrorMapper.apply(var1), "The onError publisher returned is null");
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.downstream.onError(new CompositeException(var1, var4));
            return;
         }

         this.complete((R)var2);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         try {
            var1 = ObjectHelper.requireNonNull(this.onNextMapper.apply((T)var1), "The onNext publisher returned is null");
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.downstream.onError(var3);
            return;
         }

         this.produced++;
         this.downstream.onNext(var1);
      }
   }
}
