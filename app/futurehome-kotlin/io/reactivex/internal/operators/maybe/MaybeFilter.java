package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;

public final class MaybeFilter<T> extends AbstractMaybeWithUpstream<T, T> {
   final Predicate<? super T> predicate;

   public MaybeFilter(MaybeSource<T> var1, Predicate<? super T> var2) {
      super(var1);
      this.predicate = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new MaybeFilter.FilterMaybeObserver<>(var1, this.predicate));
   }

   static final class FilterMaybeObserver<T> implements MaybeObserver<T>, Disposable {
      final MaybeObserver<? super T> downstream;
      final Predicate<? super T> predicate;
      Disposable upstream;

      FilterMaybeObserver(MaybeObserver<? super T> var1, Predicate<? super T> var2) {
         this.downstream = var1;
         this.predicate = var2;
      }

      @Override
      public void dispose() {
         Disposable var1 = this.upstream;
         this.upstream = DisposableHelper.DISPOSED;
         var1.dispose();
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
         boolean var2;
         try {
            var2 = this.predicate.test((T)var1);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.downstream.onError(var4);
            return;
         }

         if (var2) {
            this.downstream.onSuccess((T)var1);
         } else {
            this.downstream.onComplete();
         }
      }
   }
}
