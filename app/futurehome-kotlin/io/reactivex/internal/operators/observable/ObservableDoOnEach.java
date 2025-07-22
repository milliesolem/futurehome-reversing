package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableDoOnEach<T> extends AbstractObservableWithUpstream<T, T> {
   final Action onAfterTerminate;
   final Action onComplete;
   final Consumer<? super Throwable> onError;
   final Consumer<? super T> onNext;

   public ObservableDoOnEach(ObservableSource<T> var1, Consumer<? super T> var2, Consumer<? super Throwable> var3, Action var4, Action var5) {
      super(var1);
      this.onNext = var2;
      this.onError = var3;
      this.onComplete = var4;
      this.onAfterTerminate = var5;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableDoOnEach.DoOnEachObserver<>(var1, this.onNext, this.onError, this.onComplete, this.onAfterTerminate));
   }

   static final class DoOnEachObserver<T> implements Observer<T>, Disposable {
      boolean done;
      final Observer<? super T> downstream;
      final Action onAfterTerminate;
      final Action onComplete;
      final Consumer<? super Throwable> onError;
      final Consumer<? super T> onNext;
      Disposable upstream;

      DoOnEachObserver(Observer<? super T> var1, Consumer<? super T> var2, Consumer<? super Throwable> var3, Action var4, Action var5) {
         this.downstream = var1;
         this.onNext = var2;
         this.onError = var3;
         this.onComplete = var4;
         this.onAfterTerminate = var5;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onComplete() {
         if (!this.done) {
            try {
               this.onComplete.run();
            } catch (Throwable var6) {
               Exceptions.throwIfFatal(var6);
               this.onError(var6);
               return;
            }

            this.done = true;
            this.downstream.onComplete();

            try {
               this.onAfterTerminate.run();
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               RxJavaPlugins.onError(var7);
               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError((Throwable)var1);
         } else {
            this.done = true;

            label51:
            try {
               this.onError.accept((Throwable)var1);
            } catch (Throwable var8) {
               Exceptions.throwIfFatal(var8);
               var1 = new CompositeException((Throwable)var1, var8);
               break label51;
            }

            this.downstream.onError((Throwable)var1);

            try {
               this.onAfterTerminate.run();
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               RxJavaPlugins.onError(var7);
               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         if (!this.done) {
            try {
               this.onNext.accept((T)var1);
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.upstream.dispose();
               this.onError(var3);
               return;
            }

            this.downstream.onNext((T)var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }
}
