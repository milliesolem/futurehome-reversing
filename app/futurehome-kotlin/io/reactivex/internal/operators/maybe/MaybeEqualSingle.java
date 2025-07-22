package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeEqualSingle<T> extends Single<Boolean> {
   final BiPredicate<? super T, ? super T> isEqual;
   final MaybeSource<? extends T> source1;
   final MaybeSource<? extends T> source2;

   public MaybeEqualSingle(MaybeSource<? extends T> var1, MaybeSource<? extends T> var2, BiPredicate<? super T, ? super T> var3) {
      this.source1 = var1;
      this.source2 = var2;
      this.isEqual = var3;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super Boolean> var1) {
      MaybeEqualSingle.EqualCoordinator var2 = new MaybeEqualSingle.EqualCoordinator<>(var1, this.isEqual);
      var1.onSubscribe(var2);
      var2.subscribe(this.source1, this.source2);
   }

   static final class EqualCoordinator<T> extends AtomicInteger implements Disposable {
      final SingleObserver<? super Boolean> downstream;
      final BiPredicate<? super T, ? super T> isEqual;
      final MaybeEqualSingle.EqualObserver<T> observer1;
      final MaybeEqualSingle.EqualObserver<T> observer2;

      EqualCoordinator(SingleObserver<? super Boolean> var1, BiPredicate<? super T, ? super T> var2) {
         super(2);
         this.downstream = var1;
         this.isEqual = var2;
         this.observer1 = new MaybeEqualSingle.EqualObserver<>(this);
         this.observer2 = new MaybeEqualSingle.EqualObserver<>(this);
      }

      @Override
      public void dispose() {
         this.observer1.dispose();
         this.observer2.dispose();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void done() {
         if (this.decrementAndGet() == 0) {
            Object var4 = this.observer1.value;
            Object var2 = this.observer2.value;
            if (var4 != null && var2 != null) {
               boolean var7;
               try {
                  var7 = this.isEqual.test((T)var4, (T)var2);
               } catch (Throwable var6) {
                  Exceptions.throwIfFatal(var6);
                  this.downstream.onError(var6);
                  return;
               }

               this.downstream.onSuccess(var7);
            } else {
               SingleObserver var3 = this.downstream;
               boolean var1;
               if (var4 == null && var2 == null) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               var3.onSuccess(var1);
            }
         }
      }

      void error(MaybeEqualSingle.EqualObserver<T> var1, Throwable var2) {
         if (this.getAndSet(0) > 0) {
            MaybeEqualSingle.EqualObserver var3 = this.observer1;
            if (var1 == var3) {
               this.observer2.dispose();
            } else {
               var3.dispose();
            }

            this.downstream.onError(var2);
         } else {
            RxJavaPlugins.onError(var2);
         }
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.observer1.get());
      }

      void subscribe(MaybeSource<? extends T> var1, MaybeSource<? extends T> var2) {
         var1.subscribe(this.observer1);
         var2.subscribe(this.observer2);
      }
   }

   static final class EqualObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
      private static final long serialVersionUID = -3031974433025990931L;
      final MaybeEqualSingle.EqualCoordinator<T> parent;
      Object value;

      EqualObserver(MaybeEqualSingle.EqualCoordinator<T> var1) {
         this.parent = var1;
      }

      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public void onComplete() {
         this.parent.done();
      }

      @Override
      public void onError(Throwable var1) {
         this.parent.error(this, var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.value = var1;
         this.parent.done();
      }
   }
}
