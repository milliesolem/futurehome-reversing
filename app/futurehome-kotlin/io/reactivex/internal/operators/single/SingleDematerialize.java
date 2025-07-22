package io.reactivex.internal.operators.single;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Notification;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;

public final class SingleDematerialize<T, R> extends Maybe<R> {
   final Function<? super T, Notification<R>> selector;
   final Single<T> source;

   public SingleDematerialize(Single<T> var1, Function<? super T, Notification<R>> var2) {
      this.source = var1;
      this.selector = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super R> var1) {
      this.source.subscribe(new SingleDematerialize.DematerializeObserver<>(var1, this.selector));
   }

   static final class DematerializeObserver<T, R> implements SingleObserver<T>, Disposable {
      final MaybeObserver<? super R> downstream;
      final Function<? super T, Notification<R>> selector;
      Disposable upstream;

      DematerializeObserver(MaybeObserver<? super R> var1, Function<? super T, Notification<R>> var2) {
         this.downstream = var1;
         this.selector = var2;
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
         try {
            var1 = ObjectHelper.requireNonNull(this.selector.apply((T)var1), "The selector returned a null Notification");
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.downstream.onError(var3);
            return;
         }

         if (var1.isOnNext()) {
            this.downstream.onSuccess((R)var1.getValue());
         } else if (var1.isOnComplete()) {
            this.downstream.onComplete();
         } else {
            this.downstream.onError(var1.getError());
         }
      }
   }
}
