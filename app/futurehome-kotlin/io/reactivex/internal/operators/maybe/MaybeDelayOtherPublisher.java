package io.reactivex.internal.operators.maybe;

import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class MaybeDelayOtherPublisher<T, U> extends AbstractMaybeWithUpstream<T, T> {
   final Publisher<U> other;

   public MaybeDelayOtherPublisher(MaybeSource<T> var1, Publisher<U> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new MaybeDelayOtherPublisher.DelayMaybeObserver<>(var1, this.other));
   }

   static final class DelayMaybeObserver<T, U> implements MaybeObserver<T>, Disposable {
      final MaybeDelayOtherPublisher.OtherSubscriber<T> other;
      final Publisher<U> otherSource;
      Disposable upstream;

      DelayMaybeObserver(MaybeObserver<? super T> var1, Publisher<U> var2) {
         this.other = new MaybeDelayOtherPublisher.OtherSubscriber<>(var1);
         this.otherSource = var2;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
         SubscriptionHelper.cancel(this.other);
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.other.get() == SubscriptionHelper.CANCELLED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void onComplete() {
         this.upstream = DisposableHelper.DISPOSED;
         this.subscribeNext();
      }

      @Override
      public void onError(Throwable var1) {
         this.upstream = DisposableHelper.DISPOSED;
         this.other.error = var1;
         this.subscribeNext();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.other.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         this.upstream = DisposableHelper.DISPOSED;
         this.other.value = (T)var1;
         this.subscribeNext();
      }

      void subscribeNext() {
         this.otherSource.subscribe(this.other);
      }
   }

   static final class OtherSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
      private static final long serialVersionUID = -1215060610805418006L;
      final MaybeObserver<? super T> downstream;
      Throwable error;
      T value;

      OtherSubscriber(MaybeObserver<? super T> var1) {
         this.downstream = var1;
      }

      public void onComplete() {
         Throwable var1 = this.error;
         if (var1 != null) {
            this.downstream.onError(var1);
         } else {
            var1 = this.value;
            if (var1 != null) {
               this.downstream.onSuccess((T)var1);
            } else {
               this.downstream.onComplete();
            }
         }
      }

      public void onError(Throwable var1) {
         Throwable var2 = this.error;
         if (var2 == null) {
            this.downstream.onError(var1);
         } else {
            this.downstream.onError(new CompositeException(var2, var1));
         }
      }

      public void onNext(Object var1) {
         var1 = this.get();
         if (var1 != SubscriptionHelper.CANCELLED) {
            this.lazySet(SubscriptionHelper.CANCELLED);
            var1.cancel();
            this.onComplete();
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.setOnce(this, var1, Long.MAX_VALUE);
      }
   }
}
