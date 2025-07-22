package io.reactivex.internal.operators.single;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class SingleFlatMapPublisher<T, R> extends Flowable<R> {
   final Function<? super T, ? extends Publisher<? extends R>> mapper;
   final SingleSource<T> source;

   public SingleFlatMapPublisher(SingleSource<T> var1, Function<? super T, ? extends Publisher<? extends R>> var2) {
      this.source = var1;
      this.mapper = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      this.source.subscribe(new SingleFlatMapPublisher.SingleFlatMapPublisherObserver<>(var1, this.mapper));
   }

   static final class SingleFlatMapPublisherObserver<S, T> extends AtomicLong implements SingleObserver<S>, FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = 7759721921468635667L;
      Disposable disposable;
      final Subscriber<? super T> downstream;
      final Function<? super S, ? extends Publisher<? extends T>> mapper;
      final AtomicReference<Subscription> parent;

      SingleFlatMapPublisherObserver(Subscriber<? super T> var1, Function<? super S, ? extends Publisher<? extends T>> var2) {
         this.downstream = var1;
         this.mapper = var2;
         this.parent = new AtomicReference<>();
      }

      public void cancel() {
         this.disposable.dispose();
         SubscriptionHelper.cancel(this.parent);
      }

      public void onComplete() {
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.disposable = var1;
         this.downstream.onSubscribe(this);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.deferredSetOnce(this.parent, this, var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSuccess(S var1) {
         try {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((S)var1), "the mapper returned a null Publisher");
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.downstream.onError(var3);
            return;
         }

         var1.subscribe(this);
      }

      public void request(long var1) {
         SubscriptionHelper.deferredRequest(this.parent, this, var1);
      }
   }
}
