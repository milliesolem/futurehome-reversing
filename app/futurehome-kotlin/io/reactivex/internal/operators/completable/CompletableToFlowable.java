package io.reactivex.internal.operators.completable;

import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.internal.observers.SubscriberCompletableObserver;
import org.reactivestreams.Subscriber;

public final class CompletableToFlowable<T> extends Flowable<T> {
   final CompletableSource source;

   public CompletableToFlowable(CompletableSource var1) {
      this.source = var1;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      SubscriberCompletableObserver var2 = new SubscriberCompletableObserver(var1);
      this.source.subscribe(var2);
   }
}
