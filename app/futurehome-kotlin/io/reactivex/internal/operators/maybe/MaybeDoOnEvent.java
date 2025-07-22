package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.disposables.DisposableHelper;

public final class MaybeDoOnEvent<T> extends AbstractMaybeWithUpstream<T, T> {
   final BiConsumer<? super T, ? super Throwable> onEvent;

   public MaybeDoOnEvent(MaybeSource<T> var1, BiConsumer<? super T, ? super Throwable> var2) {
      super(var1);
      this.onEvent = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new MaybeDoOnEvent.DoOnEventMaybeObserver<>(var1, this.onEvent));
   }

   static final class DoOnEventMaybeObserver<T> implements MaybeObserver<T>, Disposable {
      final MaybeObserver<? super T> downstream;
      final BiConsumer<? super T, ? super Throwable> onEvent;
      Disposable upstream;

      DoOnEventMaybeObserver(MaybeObserver<? super T> var1, BiConsumer<? super T, ? super Throwable> var2) {
         this.downstream = var1;
         this.onEvent = var2;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onComplete() {
         this.upstream = DisposableHelper.DISPOSED;

         try {
            this.onEvent.accept(null, null);
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.downstream.onError(var3);
            return;
         }

         this.downstream.onComplete();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         this.upstream = DisposableHelper.DISPOSED;

         label20:
         try {
            this.onEvent.accept(null, (Throwable)var1);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            var1 = new CompositeException((Throwable)var1, var4);
            break label20;
         }

         this.downstream.onError((Throwable)var1);
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
         this.upstream = DisposableHelper.DISPOSED;

         try {
            this.onEvent.accept((T)var1, null);
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.downstream.onError(var3);
            return;
         }

         this.downstream.onSuccess((T)var1);
      }
   }
}
