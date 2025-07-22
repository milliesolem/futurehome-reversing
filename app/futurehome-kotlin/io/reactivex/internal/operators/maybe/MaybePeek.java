package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class MaybePeek<T> extends AbstractMaybeWithUpstream<T, T> {
   final Action onAfterTerminate;
   final Action onCompleteCall;
   final Action onDisposeCall;
   final Consumer<? super Throwable> onErrorCall;
   final Consumer<? super Disposable> onSubscribeCall;
   final Consumer<? super T> onSuccessCall;

   public MaybePeek(
      MaybeSource<T> var1, Consumer<? super Disposable> var2, Consumer<? super T> var3, Consumer<? super Throwable> var4, Action var5, Action var6, Action var7
   ) {
      super(var1);
      this.onSubscribeCall = var2;
      this.onSuccessCall = var3;
      this.onErrorCall = var4;
      this.onCompleteCall = var5;
      this.onAfterTerminate = var6;
      this.onDisposeCall = var7;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new MaybePeek.MaybePeekObserver<>(var1, this));
   }

   static final class MaybePeekObserver<T> implements MaybeObserver<T>, Disposable {
      final MaybeObserver<? super T> downstream;
      final MaybePeek<T> parent;
      Disposable upstream;

      MaybePeekObserver(MaybeObserver<? super T> var1, MaybePeek<T> var2) {
         this.downstream = var1;
         this.parent = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void dispose() {
         label17:
         try {
            this.parent.onDisposeCall.run();
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            RxJavaPlugins.onError(var3);
            break label17;
         }

         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void onAfterTerminate() {
         try {
            this.parent.onAfterTerminate.run();
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            RxJavaPlugins.onError(var3);
            return;
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onComplete() {
         if (this.upstream != DisposableHelper.DISPOSED) {
            try {
               this.parent.onCompleteCall.run();
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.onErrorInner(var3);
               return;
            }

            this.upstream = DisposableHelper.DISPOSED;
            this.downstream.onComplete();
            this.onAfterTerminate();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.upstream == DisposableHelper.DISPOSED) {
            RxJavaPlugins.onError(var1);
         } else {
            this.onErrorInner(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void onErrorInner(Throwable var1) {
         label17:
         try {
            this.parent.onErrorCall.accept((Throwable)var1);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            var1 = new CompositeException((Throwable)var1, var4);
            break label17;
         }

         this.upstream = DisposableHelper.DISPOSED;
         this.downstream.onError((Throwable)var1);
         this.onAfterTerminate();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            try {
               this.parent.onSubscribeCall.accept(var1);
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               var1.dispose();
               this.upstream = DisposableHelper.DISPOSED;
               EmptyDisposable.error(var4, this.downstream);
               return;
            }

            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSuccess(T var1) {
         if (this.upstream != DisposableHelper.DISPOSED) {
            try {
               this.parent.onSuccessCall.accept((T)var1);
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.onErrorInner(var3);
               return;
            }

            this.upstream = DisposableHelper.DISPOSED;
            this.downstream.onSuccess((T)var1);
            this.onAfterTerminate();
         }
      }
   }
}
