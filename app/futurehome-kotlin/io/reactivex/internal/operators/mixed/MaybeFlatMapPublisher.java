package io.reactivex.internal.operators.mixed;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class MaybeFlatMapPublisher<T, R> extends Flowable<R> {
   final Function<? super T, ? extends Publisher<? extends R>> mapper;
   final MaybeSource<T> source;

   public MaybeFlatMapPublisher(MaybeSource<T> var1, Function<? super T, ? extends Publisher<? extends R>> var2) {
      this.source = var1;
      this.mapper = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      this.source.subscribe(new MaybeFlatMapPublisher.FlatMapPublisherSubscriber<>(var1, this.mapper));
   }

   static final class FlatMapPublisherSubscriber<T, R> extends AtomicReference<Subscription> implements FlowableSubscriber<R>, MaybeObserver<T>, Subscription {
      private static final long serialVersionUID = -8948264376121066672L;
      final Subscriber<? super R> downstream;
      final Function<? super T, ? extends Publisher<? extends R>> mapper;
      final AtomicLong requested;
      Disposable upstream;

      FlatMapPublisherSubscriber(Subscriber<? super R> var1, Function<? super T, ? extends Publisher<? extends R>> var2) {
         this.downstream = var1;
         this.mapper = var2;
         this.requested = new AtomicLong();
      }

      public void cancel() {
         this.upstream.dispose();
         SubscriptionHelper.cancel(this);
      }

      @Override
      public void onComplete() {
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      public void onNext(R var1) {
         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.deferredSetOnce(this, this.requested, var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSuccess(T var1) {
         try {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null Publisher");
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.downstream.onError(var3);
            return;
         }

         var1.subscribe(this);
      }

      public void request(long var1) {
         SubscriptionHelper.deferredRequest(this, this.requested, var1);
      }
   }
}
