package io.reactivex.internal.operators.flowable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableMergeWithCompletable<T> extends AbstractFlowableWithUpstream<T, T> {
   final CompletableSource other;

   public FlowableMergeWithCompletable(Flowable<T> var1, CompletableSource var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      FlowableMergeWithCompletable.MergeWithSubscriber var2 = new FlowableMergeWithCompletable.MergeWithSubscriber(var1);
      var1.onSubscribe(var2);
      this.source.subscribe(var2);
      this.other.subscribe(var2.otherObserver);
   }

   static final class MergeWithSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = -4592979584110982903L;
      final Subscriber<? super T> downstream;
      final AtomicThrowable error;
      volatile boolean mainDone;
      final AtomicReference<Subscription> mainSubscription;
      volatile boolean otherDone;
      final FlowableMergeWithCompletable.MergeWithSubscriber.OtherObserver otherObserver;
      final AtomicLong requested;

      MergeWithSubscriber(Subscriber<? super T> var1) {
         this.downstream = var1;
         this.mainSubscription = new AtomicReference<>();
         this.otherObserver = new FlowableMergeWithCompletable.MergeWithSubscriber.OtherObserver(this);
         this.error = new AtomicThrowable();
         this.requested = new AtomicLong();
      }

      public void cancel() {
         SubscriptionHelper.cancel(this.mainSubscription);
         DisposableHelper.dispose(this.otherObserver);
      }

      public void onComplete() {
         this.mainDone = true;
         if (this.otherDone) {
            HalfSerializer.onComplete(this.downstream, this, this.error);
         }
      }

      public void onError(Throwable var1) {
         DisposableHelper.dispose(this.otherObserver);
         HalfSerializer.onError(this.downstream, var1, this, this.error);
      }

      public void onNext(T var1) {
         HalfSerializer.onNext(this.downstream, var1, this, this.error);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.deferredSetOnce(this.mainSubscription, this.requested, var1);
      }

      void otherComplete() {
         this.otherDone = true;
         if (this.mainDone) {
            HalfSerializer.onComplete(this.downstream, this, this.error);
         }
      }

      void otherError(Throwable var1) {
         SubscriptionHelper.cancel(this.mainSubscription);
         HalfSerializer.onError(this.downstream, var1, this, this.error);
      }

      public void request(long var1) {
         SubscriptionHelper.deferredRequest(this.mainSubscription, this.requested, var1);
      }

      static final class OtherObserver extends AtomicReference<Disposable> implements CompletableObserver {
         private static final long serialVersionUID = -2935427570954647017L;
         final FlowableMergeWithCompletable.MergeWithSubscriber<?> parent;

         OtherObserver(FlowableMergeWithCompletable.MergeWithSubscriber<?> var1) {
            this.parent = var1;
         }

         @Override
         public void onComplete() {
            this.parent.otherComplete();
         }

         @Override
         public void onError(Throwable var1) {
            this.parent.otherError(var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }
      }
   }
}
