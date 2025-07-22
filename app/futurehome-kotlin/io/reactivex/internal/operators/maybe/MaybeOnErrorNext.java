package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeOnErrorNext<T> extends AbstractMaybeWithUpstream<T, T> {
   final boolean allowFatal;
   final Function<? super Throwable, ? extends MaybeSource<? extends T>> resumeFunction;

   public MaybeOnErrorNext(MaybeSource<T> var1, Function<? super Throwable, ? extends MaybeSource<? extends T>> var2, boolean var3) {
      super(var1);
      this.resumeFunction = var2;
      this.allowFatal = var3;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new MaybeOnErrorNext.OnErrorNextMaybeObserver<>(var1, this.resumeFunction, this.allowFatal));
   }

   static final class OnErrorNextMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
      private static final long serialVersionUID = 2026620218879969836L;
      final boolean allowFatal;
      final MaybeObserver<? super T> downstream;
      final Function<? super Throwable, ? extends MaybeSource<? extends T>> resumeFunction;

      OnErrorNextMaybeObserver(MaybeObserver<? super T> var1, Function<? super Throwable, ? extends MaybeSource<? extends T>> var2, boolean var3) {
         this.downstream = var1;
         this.resumeFunction = var2;
         this.allowFatal = var3;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onComplete() {
         this.downstream.onComplete();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         if (!this.allowFatal && !(var1 instanceof Exception)) {
            this.downstream.onError(var1);
         } else {
            MaybeSource var2;
            try {
               var2 = ObjectHelper.requireNonNull(this.resumeFunction.apply(var1), "The resumeFunction returned a null MaybeSource");
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.downstream.onError(new CompositeException(var1, var4));
               return;
            }

            DisposableHelper.replace(this, null);
            var2.subscribe(new MaybeOnErrorNext.OnErrorNextMaybeObserver.NextMaybeObserver<>(this.downstream, this));
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1)) {
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         this.downstream.onSuccess((T)var1);
      }

      static final class NextMaybeObserver<T> implements MaybeObserver<T> {
         final MaybeObserver<? super T> downstream;
         final AtomicReference<Disposable> upstream;

         NextMaybeObserver(MaybeObserver<? super T> var1, AtomicReference<Disposable> var2) {
            this.downstream = var1;
            this.upstream = var2;
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
            DisposableHelper.setOnce(this.upstream, var1);
         }

         @Override
         public void onSuccess(T var1) {
            this.downstream.onSuccess((T)var1);
         }
      }
   }
}
