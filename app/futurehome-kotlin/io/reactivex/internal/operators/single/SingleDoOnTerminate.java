package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;

public final class SingleDoOnTerminate<T> extends Single<T> {
   final Action onTerminate;
   final SingleSource<T> source;

   public SingleDoOnTerminate(SingleSource<T> var1, Action var2) {
      this.source = var1;
      this.onTerminate = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new SingleDoOnTerminate.DoOnTerminate(this, var1));
   }

   final class DoOnTerminate implements SingleObserver<T> {
      final SingleObserver<? super T> downstream;
      final SingleDoOnTerminate this$0;

      DoOnTerminate(SingleObserver<? super T> var1, SingleObserver var2) {
         this.this$0 = var1;
         this.downstream = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         label17:
         try {
            this.this$0.onTerminate.run();
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            var1 = new CompositeException((Throwable)var1, var4);
            break label17;
         }

         this.downstream.onError((Throwable)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.downstream.onSubscribe(var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSuccess(T var1) {
         try {
            this.this$0.onTerminate.run();
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.downstream.onError(var3);
            return;
         }

         this.downstream.onSuccess((T)var1);
      }
   }
}
