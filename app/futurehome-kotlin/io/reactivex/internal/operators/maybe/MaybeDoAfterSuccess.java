package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class MaybeDoAfterSuccess<T> extends AbstractMaybeWithUpstream<T, T> {
   final Consumer<? super T> onAfterSuccess;

   public MaybeDoAfterSuccess(MaybeSource<T> var1, Consumer<? super T> var2) {
      super(var1);
      this.onAfterSuccess = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new MaybeDoAfterSuccess.DoAfterObserver<>(var1, this.onAfterSuccess));
   }

   static final class DoAfterObserver<T> implements MaybeObserver<T>, Disposable {
      final MaybeObserver<? super T> downstream;
      final Consumer<? super T> onAfterSuccess;
      Disposable upstream;

      DoAfterObserver(MaybeObserver<? super T> var1, Consumer<? super T> var2) {
         this.downstream = var1;
         this.onAfterSuccess = var2;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
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
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSuccess(T var1) {
         this.downstream.onSuccess((T)var1);

         try {
            this.onAfterSuccess.accept((T)var1);
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            RxJavaPlugins.onError(var3);
            return;
         }
      }
   }
}
