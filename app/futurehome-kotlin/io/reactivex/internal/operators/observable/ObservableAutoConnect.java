package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableAutoConnect<T> extends Observable<T> {
   final AtomicInteger clients;
   final Consumer<? super Disposable> connection;
   final int numberOfObservers;
   final ConnectableObservable<? extends T> source;

   public ObservableAutoConnect(ConnectableObservable<? extends T> var1, int var2, Consumer<? super Disposable> var3) {
      this.source = var1;
      this.numberOfObservers = var2;
      this.connection = var3;
      this.clients = new AtomicInteger();
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(var1);
      if (this.clients.incrementAndGet() == this.numberOfObservers) {
         this.source.connect(this.connection);
      }
   }
}
