package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.disposables.CancellableDisposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableCreate extends Completable {
   final CompletableOnSubscribe source;

   public CompletableCreate(CompletableOnSubscribe var1) {
      this.source = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(CompletableObserver var1) {
      CompletableCreate.Emitter var2 = new CompletableCreate.Emitter(var1);
      var1.onSubscribe(var2);

      try {
         this.source.subscribe(var2);
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         var2.onError(var4);
         return;
      }
   }

   static final class Emitter extends AtomicReference<Disposable> implements CompletableEmitter, Disposable {
      private static final long serialVersionUID = -2467358622224974244L;
      final CompletableObserver downstream;

      Emitter(CompletableObserver var1) {
         this.downstream = var1;
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
         if (this.get() != DisposableHelper.DISPOSED) {
            Disposable var1 = this.getAndSet(DisposableHelper.DISPOSED);
            if (var1 != DisposableHelper.DISPOSED) {
               try {
                  this.downstream.onComplete();
               } finally {
                  if (var1 != null) {
                     var1.dispose();
                  }
               }
            }
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (!this.tryOnError(var1)) {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void setCancellable(Cancellable var1) {
         this.setDisposable(new CancellableDisposable(var1));
      }

      @Override
      public void setDisposable(Disposable var1) {
         DisposableHelper.set(this, var1);
      }

      @Override
      public String toString() {
         return String.format("%s{%s}", this.getClass().getSimpleName(), super.toString());
      }

      @Override
      public boolean tryOnError(Throwable var1) {
         Object var2 = var1;
         if (var1 == null) {
            var2 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
         }

         if (this.get() != DisposableHelper.DISPOSED) {
            Disposable var5 = this.getAndSet(DisposableHelper.DISPOSED);
            if (var5 != DisposableHelper.DISPOSED) {
               try {
                  this.downstream.onError((Throwable)var2);
               } finally {
                  if (var5 != null) {
                     var5.dispose();
                  }
               }

               return true;
            }
         }

         return false;
      }
   }
}
