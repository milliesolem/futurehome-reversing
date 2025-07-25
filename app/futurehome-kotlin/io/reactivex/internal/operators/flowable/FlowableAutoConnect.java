package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Subscriber;

public final class FlowableAutoConnect<T> extends Flowable<T> {
   final AtomicInteger clients;
   final Consumer<? super Disposable> connection;
   final int numberOfSubscribers;
   final ConnectableFlowable<? extends T> source;

   public FlowableAutoConnect(ConnectableFlowable<? extends T> var1, int var2, Consumer<? super Disposable> var3) {
      this.source = var1;
      this.numberOfSubscribers = var2;
      this.connection = var3;
      this.clients = new AtomicInteger();
   }

   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(var1);
      if (this.clients.incrementAndGet() == this.numberOfSubscribers) {
         this.source.connect(this.connection);
      }
   }
}
