package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;

public final class MaybeOnErrorComplete<T> extends AbstractMaybeWithUpstream<T, T> {
   final Predicate<? super Throwable> predicate;

   public MaybeOnErrorComplete(MaybeSource<T> var1, Predicate<? super Throwable> var2) {
      super(var1);
      this.predicate = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new MaybeOnErrorComplete.OnErrorCompleteMaybeObserver<>(var1, this.predicate));
   }

   static final class OnErrorCompleteMaybeObserver<T> implements MaybeObserver<T>, Disposable {
      final MaybeObserver<? super T> downstream;
      final Predicate<? super Throwable> predicate;
      Disposable upstream;

      OnErrorCompleteMaybeObserver(MaybeObserver<? super T> var1, Predicate<? super Throwable> var2) {
         this.downstream = var1;
         this.predicate = var2;
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

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         boolean var2;
         try {
            var2 = this.predicate.test(var1);
         } catch (Throwable var5) {
            Exceptions.throwIfFatal(var5);
            this.downstream.onError(new CompositeException(var1, var5));
            return;
         }

         if (var2) {
            this.downstream.onComplete();
         } else {
            this.downstream.onError(var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         this.downstream.onSuccess((T)var1);
      }
   }
}
