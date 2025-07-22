package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;

public final class CompletableDoOnEvent extends Completable {
   final Consumer<? super Throwable> onEvent;
   final CompletableSource source;

   public CompletableDoOnEvent(CompletableSource var1, Consumer<? super Throwable> var2) {
      this.source = var1;
      this.onEvent = var2;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new CompletableDoOnEvent.DoOnEvent(this, var1));
   }

   final class DoOnEvent implements CompletableObserver {
      private final CompletableObserver observer;
      final CompletableDoOnEvent this$0;

      DoOnEvent(CompletableDoOnEvent var1, CompletableObserver var2) {
         this.this$0 = var1;
         this.observer = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onComplete() {
         try {
            this.this$0.onEvent.accept(null);
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.observer.onError(var3);
            return;
         }

         this.observer.onComplete();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         label17:
         try {
            this.this$0.onEvent.accept((Throwable)var1);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            var1 = new CompositeException((Throwable)var1, var4);
            break label17;
         }

         this.observer.onError((Throwable)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.observer.onSubscribe(var1);
      }
   }
}
