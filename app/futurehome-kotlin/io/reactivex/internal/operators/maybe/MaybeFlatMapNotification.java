package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeFlatMapNotification<T, R> extends AbstractMaybeWithUpstream<T, R> {
   final Callable<? extends MaybeSource<? extends R>> onCompleteSupplier;
   final Function<? super Throwable, ? extends MaybeSource<? extends R>> onErrorMapper;
   final Function<? super T, ? extends MaybeSource<? extends R>> onSuccessMapper;

   public MaybeFlatMapNotification(
      MaybeSource<T> var1,
      Function<? super T, ? extends MaybeSource<? extends R>> var2,
      Function<? super Throwable, ? extends MaybeSource<? extends R>> var3,
      Callable<? extends MaybeSource<? extends R>> var4
   ) {
      super(var1);
      this.onSuccessMapper = var2;
      this.onErrorMapper = var3;
      this.onCompleteSupplier = var4;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super R> var1) {
      this.source.subscribe(new MaybeFlatMapNotification.FlatMapMaybeObserver<>(var1, this.onSuccessMapper, this.onErrorMapper, this.onCompleteSupplier));
   }

   static final class FlatMapMaybeObserver<T, R> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
      private static final long serialVersionUID = 4375739915521278546L;
      final MaybeObserver<? super R> downstream;
      final Callable<? extends MaybeSource<? extends R>> onCompleteSupplier;
      final Function<? super Throwable, ? extends MaybeSource<? extends R>> onErrorMapper;
      final Function<? super T, ? extends MaybeSource<? extends R>> onSuccessMapper;
      Disposable upstream;

      FlatMapMaybeObserver(
         MaybeObserver<? super R> var1,
         Function<? super T, ? extends MaybeSource<? extends R>> var2,
         Function<? super Throwable, ? extends MaybeSource<? extends R>> var3,
         Callable<? extends MaybeSource<? extends R>> var4
      ) {
         this.downstream = var1;
         this.onSuccessMapper = var2;
         this.onErrorMapper = var3;
         this.onCompleteSupplier = var4;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
         this.upstream.dispose();
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onComplete() {
         MaybeSource var1;
         try {
            var1 = ObjectHelper.requireNonNull(this.onCompleteSupplier.call(), "The onCompleteSupplier returned a null MaybeSource");
         } catch (Exception var2) {
            Exceptions.throwIfFatal(var2);
            this.downstream.onError(var2);
            return;
         }

         var1.subscribe(new MaybeFlatMapNotification.FlatMapMaybeObserver.InnerObserver(this));
      }

      @Override
      public void onError(Throwable var1) {
         MaybeSource var2;
         try {
            var2 = ObjectHelper.requireNonNull(this.onErrorMapper.apply(var1), "The onErrorMapper returned a null MaybeSource");
         } catch (Exception var3) {
            Exceptions.throwIfFatal(var3);
            this.downstream.onError(new CompositeException(var1, var3));
            return;
         }

         var2.subscribe(new MaybeFlatMapNotification.FlatMapMaybeObserver.InnerObserver(this));
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
         try {
            var1 = ObjectHelper.requireNonNull(this.onSuccessMapper.apply((T)var1), "The onSuccessMapper returned a null MaybeSource");
         } catch (Exception var2) {
            Exceptions.throwIfFatal(var2);
            this.downstream.onError(var2);
            return;
         }

         var1.subscribe(new MaybeFlatMapNotification.FlatMapMaybeObserver.InnerObserver(this));
      }

      final class InnerObserver implements MaybeObserver<R> {
         final MaybeFlatMapNotification.FlatMapMaybeObserver this$0;

         InnerObserver(MaybeFlatMapNotification.FlatMapMaybeObserver var1) {
            this.this$0 = var1;
         }

         @Override
         public void onComplete() {
            this.this$0.downstream.onComplete();
         }

         @Override
         public void onError(Throwable var1) {
            this.this$0.downstream.onError(var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this.this$0, var1);
         }

         @Override
         public void onSuccess(R var1) {
            this.this$0.downstream.onSuccess((R)var1);
         }
      }
   }
}
