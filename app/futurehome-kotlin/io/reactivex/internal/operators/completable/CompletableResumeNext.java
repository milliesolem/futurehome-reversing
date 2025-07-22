package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableResumeNext extends Completable {
   final Function<? super Throwable, ? extends CompletableSource> errorMapper;
   final CompletableSource source;

   public CompletableResumeNext(CompletableSource var1, Function<? super Throwable, ? extends CompletableSource> var2) {
      this.source = var1;
      this.errorMapper = var2;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      CompletableResumeNext.ResumeNextObserver var2 = new CompletableResumeNext.ResumeNextObserver(var1, this.errorMapper);
      var1.onSubscribe(var2);
      this.source.subscribe(var2);
   }

   static final class ResumeNextObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
      private static final long serialVersionUID = 5018523762564524046L;
      final CompletableObserver downstream;
      final Function<? super Throwable, ? extends CompletableSource> errorMapper;
      boolean once;

      ResumeNextObserver(CompletableObserver var1, Function<? super Throwable, ? extends CompletableSource> var2) {
         this.downstream = var1;
         this.errorMapper = var2;
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
         if (this.once) {
            this.downstream.onError(var1);
         } else {
            this.once = true;

            CompletableSource var2;
            try {
               var2 = ObjectHelper.requireNonNull(this.errorMapper.apply(var1), "The errorMapper returned a null CompletableSource");
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.downstream.onError(new CompositeException(var1, var4));
               return;
            }

            var2.subscribe(this);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.replace(this, var1);
      }
   }
}
