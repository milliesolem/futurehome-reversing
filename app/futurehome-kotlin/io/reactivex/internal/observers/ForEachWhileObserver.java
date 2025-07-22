package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class ForEachWhileObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
   private static final long serialVersionUID = -4403180040475402120L;
   boolean done;
   final Action onComplete;
   final Consumer<? super Throwable> onError;
   final Predicate<? super T> onNext;

   public ForEachWhileObserver(Predicate<? super T> var1, Consumer<? super Throwable> var2, Action var3) {
      this.onNext = var1;
      this.onError = var2;
      this.onComplete = var3;
   }

   @Override
   public void dispose() {
      DisposableHelper.dispose(this);
   }

   @Override
   public boolean isDisposed() {
      return DisposableHelper.isDisposed(this.get());
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onComplete() {
      if (!this.done) {
         this.done = true;

         try {
            this.onComplete.run();
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            RxJavaPlugins.onError(var3);
            return;
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onError(Throwable var1) {
      if (this.done) {
         RxJavaPlugins.onError(var1);
      } else {
         this.done = true;

         try {
            this.onError.accept(var1);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            RxJavaPlugins.onError(new CompositeException(var1, var4));
            return;
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onNext(T var1) {
      if (!this.done) {
         boolean var2;
         try {
            var2 = this.onNext.test((T)var1);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.dispose();
            this.onError(var4);
            return;
         }

         if (!var2) {
            this.dispose();
            this.onComplete();
         }
      }
   }

   @Override
   public void onSubscribe(Disposable var1) {
      DisposableHelper.setOnce(this, var1);
   }
}
