package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableWithLatestFrom<T, U, R> extends AbstractObservableWithUpstream<T, R> {
   final BiFunction<? super T, ? super U, ? extends R> combiner;
   final ObservableSource<? extends U> other;

   public ObservableWithLatestFrom(ObservableSource<T> var1, BiFunction<? super T, ? super U, ? extends R> var2, ObservableSource<? extends U> var3) {
      super(var1);
      this.combiner = var2;
      this.other = var3;
   }

   @Override
   public void subscribeActual(Observer<? super R> var1) {
      SerializedObserver var2 = new SerializedObserver(var1);
      ObservableWithLatestFrom.WithLatestFromObserver var3 = new ObservableWithLatestFrom.WithLatestFromObserver<>(var2, this.combiner);
      var2.onSubscribe(var3);
      this.other.subscribe(new ObservableWithLatestFrom.WithLatestFromOtherObserver(this, var3));
      this.source.subscribe(var3);
   }

   static final class WithLatestFromObserver<T, U, R> extends AtomicReference<U> implements Observer<T>, Disposable {
      private static final long serialVersionUID = -312246233408980075L;
      final BiFunction<? super T, ? super U, ? extends R> combiner;
      final Observer<? super R> downstream;
      final AtomicReference<Disposable> other;
      final AtomicReference<Disposable> upstream = new AtomicReference<>();

      WithLatestFromObserver(Observer<? super R> var1, BiFunction<? super T, ? super U, ? extends R> var2) {
         this.other = new AtomicReference<>();
         this.downstream = var1;
         this.combiner = var2;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this.upstream);
         DisposableHelper.dispose(this.other);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.upstream.get());
      }

      @Override
      public void onComplete() {
         DisposableHelper.dispose(this.other);
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         DisposableHelper.dispose(this.other);
         this.downstream.onError(var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         Object var2 = this.get();
         if (var2 != null) {
            try {
               var1 = ObjectHelper.requireNonNull(this.combiner.apply((T)var1, (U)var2), "The combiner returned a null value");
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.dispose();
               this.downstream.onError(var4);
               return;
            }

            this.downstream.onNext((R)var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.upstream, var1);
      }

      public void otherError(Throwable var1) {
         DisposableHelper.dispose(this.upstream);
         this.downstream.onError(var1);
      }

      public boolean setOther(Disposable var1) {
         return DisposableHelper.setOnce(this.other, var1);
      }
   }

   final class WithLatestFromOtherObserver implements Observer<U> {
      private final ObservableWithLatestFrom.WithLatestFromObserver<T, U, R> parent;
      final ObservableWithLatestFrom this$0;

      WithLatestFromOtherObserver(ObservableWithLatestFrom.WithLatestFromObserver<T, U, R> var1, ObservableWithLatestFrom.WithLatestFromObserver var2) {
         this.this$0 = var1;
         this.parent = var2;
      }

      @Override
      public void onComplete() {
      }

      @Override
      public void onError(Throwable var1) {
         this.parent.otherError(var1);
      }

      @Override
      public void onNext(U var1) {
         this.parent.lazySet((U)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.parent.setOther(var1);
      }
   }
}
