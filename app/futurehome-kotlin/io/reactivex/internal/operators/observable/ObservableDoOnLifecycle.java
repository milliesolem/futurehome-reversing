package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.observers.DisposableLambdaObserver;

public final class ObservableDoOnLifecycle<T> extends AbstractObservableWithUpstream<T, T> {
   private final Action onDispose;
   private final Consumer<? super Disposable> onSubscribe;

   public ObservableDoOnLifecycle(Observable<T> var1, Consumer<? super Disposable> var2, Action var3) {
      super(var1);
      this.onSubscribe = var2;
      this.onDispose = var3;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new DisposableLambdaObserver<>(var1, this.onSubscribe, this.onDispose));
   }
}
