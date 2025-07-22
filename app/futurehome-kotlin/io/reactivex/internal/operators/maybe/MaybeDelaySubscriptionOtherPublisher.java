package io.reactivex.internal.operators.maybe;

import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class MaybeDelaySubscriptionOtherPublisher<T, U> extends AbstractMaybeWithUpstream<T, T> {
   final Publisher<U> other;

   public MaybeDelaySubscriptionOtherPublisher(MaybeSource<T> var1, Publisher<U> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.other.subscribe(new MaybeDelaySubscriptionOtherPublisher.OtherSubscriber<>(var1, this.source));
   }

   static final class DelayMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
      private static final long serialVersionUID = 706635022205076709L;
      final MaybeObserver<? super T> downstream;

      DelayMaybeObserver(MaybeObserver<? super T> var1) {
         this.downstream = var1;
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
         DisposableHelper.setOnce(this, var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.downstream.onSuccess((T)var1);
      }
   }

   static final class OtherSubscriber<T> implements FlowableSubscriber<Object>, Disposable {
      final MaybeDelaySubscriptionOtherPublisher.DelayMaybeObserver<T> main;
      MaybeSource<T> source;
      Subscription upstream;

      OtherSubscriber(MaybeObserver<? super T> var1, MaybeSource<T> var2) {
         this.main = new MaybeDelaySubscriptionOtherPublisher.DelayMaybeObserver<>(var1);
         this.source = var2;
      }

      @Override
      public void dispose() {
         this.upstream.cancel();
         this.upstream = SubscriptionHelper.CANCELLED;
         DisposableHelper.dispose(this.main);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.main.get());
      }

      public void onComplete() {
         if (this.upstream != SubscriptionHelper.CANCELLED) {
            this.upstream = SubscriptionHelper.CANCELLED;
            this.subscribeNext();
         }
      }

      public void onError(Throwable var1) {
         if (this.upstream != SubscriptionHelper.CANCELLED) {
            this.upstream = SubscriptionHelper.CANCELLED;
            this.main.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(Object var1) {
         if (this.upstream != SubscriptionHelper.CANCELLED) {
            this.upstream.cancel();
            this.upstream = SubscriptionHelper.CANCELLED;
            this.subscribeNext();
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.main.downstream.onSubscribe(this);
            var1.request(Long.MAX_VALUE);
         }
      }

      void subscribeNext() {
         MaybeSource var1 = this.source;
         this.source = null;
         var1.subscribe(this.main);
      }
   }
}
