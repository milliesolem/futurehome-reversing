package io.reactivex.internal.operators.maybe;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeDelayWithCompletable<T> extends Maybe<T> {
   final CompletableSource other;
   final MaybeSource<T> source;

   public MaybeDelayWithCompletable(MaybeSource<T> var1, CompletableSource var2) {
      this.source = var1;
      this.other = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.other.subscribe(new MaybeDelayWithCompletable.OtherObserver<>(var1, this.source));
   }

   static final class DelayWithMainObserver<T> implements MaybeObserver<T> {
      final MaybeObserver<? super T> downstream;
      final AtomicReference<Disposable> parent;

      DelayWithMainObserver(AtomicReference<Disposable> var1, MaybeObserver<? super T> var2) {
         this.parent = var1;
         this.downstream = var2;
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
         DisposableHelper.replace(this.parent, var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.downstream.onSuccess((T)var1);
      }
   }

   static final class OtherObserver<T> extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
      private static final long serialVersionUID = 703409937383992161L;
      final MaybeObserver<? super T> downstream;
      final MaybeSource<T> source;

      OtherObserver(MaybeObserver<? super T> var1, MaybeSource<T> var2) {
         this.downstream = var1;
         this.source = var2;
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
         this.source.subscribe(new MaybeDelayWithCompletable.DelayWithMainObserver<>(this, this.downstream));
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1)) {
            this.downstream.onSubscribe(this);
         }
      }
   }
}
