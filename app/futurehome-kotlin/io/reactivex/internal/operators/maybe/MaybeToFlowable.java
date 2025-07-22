package io.reactivex.internal.operators.maybe;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import org.reactivestreams.Subscriber;

public final class MaybeToFlowable<T> extends Flowable<T> implements HasUpstreamMaybeSource<T> {
   final MaybeSource<T> source;

   public MaybeToFlowable(MaybeSource<T> var1) {
      this.source = var1;
   }

   @Override
   public MaybeSource<T> source() {
      return this.source;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new MaybeToFlowable.MaybeToFlowableSubscriber<>(var1));
   }

   static final class MaybeToFlowableSubscriber<T> extends DeferredScalarSubscription<T> implements MaybeObserver<T> {
      private static final long serialVersionUID = 7603343402964826922L;
      Disposable upstream;

      MaybeToFlowableSubscriber(Subscriber<? super T> var1) {
         super(var1);
      }

      @Override
      public void cancel() {
         super.cancel();
         this.upstream.dispose();
      }

      @Override
      public void onComplete() {
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         this.complete((T)var1);
      }
   }
}
