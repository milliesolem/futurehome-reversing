package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

public final class ObservableMapNotification<T, R> extends AbstractObservableWithUpstream<T, ObservableSource<? extends R>> {
   final Callable<? extends ObservableSource<? extends R>> onCompleteSupplier;
   final Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper;
   final Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper;

   public ObservableMapNotification(
      ObservableSource<T> var1,
      Function<? super T, ? extends ObservableSource<? extends R>> var2,
      Function<? super Throwable, ? extends ObservableSource<? extends R>> var3,
      Callable<? extends ObservableSource<? extends R>> var4
   ) {
      super(var1);
      this.onNextMapper = var2;
      this.onErrorMapper = var3;
      this.onCompleteSupplier = var4;
   }

   @Override
   public void subscribeActual(Observer<? super ObservableSource<? extends R>> var1) {
      this.source.subscribe(new ObservableMapNotification.MapNotificationObserver<>(var1, this.onNextMapper, this.onErrorMapper, this.onCompleteSupplier));
   }

   static final class MapNotificationObserver<T, R> implements Observer<T>, Disposable {
      final Observer<? super ObservableSource<? extends R>> downstream;
      final Callable<? extends ObservableSource<? extends R>> onCompleteSupplier;
      final Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper;
      final Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper;
      Disposable upstream;

      MapNotificationObserver(
         Observer<? super ObservableSource<? extends R>> var1,
         Function<? super T, ? extends ObservableSource<? extends R>> var2,
         Function<? super Throwable, ? extends ObservableSource<? extends R>> var3,
         Callable<? extends ObservableSource<? extends R>> var4
      ) {
         this.downstream = var1;
         this.onNextMapper = var2;
         this.onErrorMapper = var3;
         this.onCompleteSupplier = var4;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onComplete() {
         ObservableSource var1;
         try {
            var1 = ObjectHelper.requireNonNull(this.onCompleteSupplier.call(), "The onComplete ObservableSource returned is null");
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.downstream.onError(var3);
            return;
         }

         this.downstream.onNext(var1);
         this.downstream.onComplete();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         ObservableSource var2;
         try {
            var2 = ObjectHelper.requireNonNull(this.onErrorMapper.apply(var1), "The onError ObservableSource returned is null");
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.downstream.onError(new CompositeException(var1, var4));
            return;
         }

         this.downstream.onNext(var2);
         this.downstream.onComplete();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         try {
            var1 = ObjectHelper.requireNonNull(this.onNextMapper.apply((T)var1), "The onNext ObservableSource returned is null");
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.downstream.onError(var3);
            return;
         }

         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }
}
