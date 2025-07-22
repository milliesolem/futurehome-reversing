package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFromObservable<T> extends Flowable<T> {
   private final Observable<T> upstream;

   public FlowableFromObservable(Observable<T> var1) {
      this.upstream = var1;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.upstream.subscribe(new FlowableFromObservable.SubscriberObserver<>(var1));
   }

   static final class SubscriberObserver<T> implements Observer<T>, Subscription {
      final Subscriber<? super T> downstream;
      Disposable upstream;

      SubscriberObserver(Subscriber<? super T> var1) {
         this.downstream = var1;
      }

      public void cancel() {
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
      public void onNext(T var1) {
         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.upstream = var1;
         this.downstream.onSubscribe(this);
      }

      public void request(long var1) {
      }
   }
}
