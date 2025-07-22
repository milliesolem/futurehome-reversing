package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Notification;
import io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;

public final class FlowableMaterialize<T> extends AbstractFlowableWithUpstream<T, Notification<T>> {
   public FlowableMaterialize(Flowable<T> var1) {
      super(var1);
   }

   @Override
   protected void subscribeActual(Subscriber<? super Notification<T>> var1) {
      this.source.subscribe(new FlowableMaterialize.MaterializeSubscriber<>(var1));
   }

   static final class MaterializeSubscriber<T> extends SinglePostCompleteSubscriber<T, Notification<T>> {
      private static final long serialVersionUID = -3740826063558713822L;

      MaterializeSubscriber(Subscriber<? super Notification<T>> var1) {
         super(var1);
      }

      public void onComplete() {
         this.complete(Notification.createOnComplete());
      }

      protected void onDrop(Notification<T> var1) {
         if (var1.isOnError()) {
            RxJavaPlugins.onError(var1.getError());
         }
      }

      public void onError(Throwable var1) {
         this.complete(Notification.createOnError(var1));
      }

      public void onNext(T var1) {
         this.produced++;
         this.downstream.onNext(Notification.createOnNext(var1));
      }
   }
}
