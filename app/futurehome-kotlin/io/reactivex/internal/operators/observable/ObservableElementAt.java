package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;

public final class ObservableElementAt<T> extends AbstractObservableWithUpstream<T, T> {
   final T defaultValue;
   final boolean errorOnFewer;
   final long index;

   public ObservableElementAt(ObservableSource<T> var1, long var2, T var4, boolean var5) {
      super(var1);
      this.index = var2;
      this.defaultValue = (T)var4;
      this.errorOnFewer = var5;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableElementAt.ElementAtObserver<>(var1, this.index, this.defaultValue, this.errorOnFewer));
   }

   static final class ElementAtObserver<T> implements Observer<T>, Disposable {
      long count;
      final T defaultValue;
      boolean done;
      final Observer<? super T> downstream;
      final boolean errorOnFewer;
      final long index;
      Disposable upstream;

      ElementAtObserver(Observer<? super T> var1, long var2, T var4, boolean var5) {
         this.downstream = var1;
         this.index = var2;
         this.defaultValue = (T)var4;
         this.errorOnFewer = var5;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            Object var1 = this.defaultValue;
            if (var1 == null && this.errorOnFewer) {
               this.downstream.onError(new NoSuchElementException());
            } else {
               if (var1 != null) {
                  this.downstream.onNext((T)var1);
               }

               this.downstream.onComplete();
            }
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.downstream.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         if (!this.done) {
            long var2 = this.count;
            if (var2 == this.index) {
               this.done = true;
               this.upstream.dispose();
               this.downstream.onNext((T)var1);
               this.downstream.onComplete();
            } else {
               this.count = var2 + 1L;
            }
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
