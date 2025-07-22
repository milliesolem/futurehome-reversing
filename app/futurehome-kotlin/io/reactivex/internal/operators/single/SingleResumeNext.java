package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.ResumeSingleObserver;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleResumeNext<T> extends Single<T> {
   final Function<? super Throwable, ? extends SingleSource<? extends T>> nextFunction;
   final SingleSource<? extends T> source;

   public SingleResumeNext(SingleSource<? extends T> var1, Function<? super Throwable, ? extends SingleSource<? extends T>> var2) {
      this.source = var1;
      this.nextFunction = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new SingleResumeNext.ResumeMainSingleObserver<>(var1, this.nextFunction));
   }

   static final class ResumeMainSingleObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
      private static final long serialVersionUID = -5314538511045349925L;
      final SingleObserver<? super T> downstream;
      final Function<? super Throwable, ? extends SingleSource<? extends T>> nextFunction;

      ResumeMainSingleObserver(SingleObserver<? super T> var1, Function<? super Throwable, ? extends SingleSource<? extends T>> var2) {
         this.downstream = var1;
         this.nextFunction = var2;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         SingleSource var2;
         try {
            var2 = ObjectHelper.requireNonNull(this.nextFunction.apply(var1), "The nextFunction returned a null SingleSource.");
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.downstream.onError(new CompositeException(var1, var4));
            return;
         }

         var2.subscribe(new ResumeSingleObserver<>(this, this.downstream));
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
   }
}
