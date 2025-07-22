package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class CompletablePeek extends Completable {
   final Action onAfterTerminate;
   final Action onComplete;
   final Action onDispose;
   final Consumer<? super Throwable> onError;
   final Consumer<? super Disposable> onSubscribe;
   final Action onTerminate;
   final CompletableSource source;

   public CompletablePeek(
      CompletableSource var1, Consumer<? super Disposable> var2, Consumer<? super Throwable> var3, Action var4, Action var5, Action var6, Action var7
   ) {
      this.source = var1;
      this.onSubscribe = var2;
      this.onError = var3;
      this.onComplete = var4;
      this.onTerminate = var5;
      this.onAfterTerminate = var6;
      this.onDispose = var7;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new CompletablePeek.CompletableObserverImplementation(this, var1));
   }

   final class CompletableObserverImplementation implements CompletableObserver, Disposable {
      final CompletableObserver downstream;
      final CompletablePeek this$0;
      Disposable upstream;

      CompletableObserverImplementation(CompletablePeek var1, CompletableObserver var2) {
         this.this$0 = var1;
         this.downstream = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void dispose() {
         label17:
         try {
            this.this$0.onDispose.run();
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            RxJavaPlugins.onError(var3);
            break label17;
         }

         this.upstream.dispose();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void doAfter() {
         try {
            this.this$0.onAfterTerminate.run();
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            RxJavaPlugins.onError(var3);
            return;
         }
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onComplete() {
         if (this.upstream != DisposableHelper.DISPOSED) {
            try {
               this.this$0.onComplete.run();
               this.this$0.onTerminate.run();
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.downstream.onError(var3);
               return;
            }

            this.downstream.onComplete();
            this.doAfter();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         if (this.upstream == DisposableHelper.DISPOSED) {
            RxJavaPlugins.onError((Throwable)var1);
         } else {
            label22:
            try {
               this.this$0.onError.accept((Throwable)var1);
               this.this$0.onTerminate.run();
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               var1 = new CompositeException((Throwable)var1, var4);
               break label22;
            }

            this.downstream.onError((Throwable)var1);
            this.doAfter();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSubscribe(Disposable var1) {
         try {
            this.this$0.onSubscribe.accept(var1);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            var1.dispose();
            this.upstream = DisposableHelper.DISPOSED;
            EmptyDisposable.error(var4, this.downstream);
            return;
         }

         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }
}
