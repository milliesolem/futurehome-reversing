package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableDoFinally<T> extends AbstractObservableWithUpstream<T, T> {
   final Action onFinally;

   public ObservableDoFinally(ObservableSource<T> var1, Action var2) {
      super(var1);
      this.onFinally = var2;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableDoFinally.DoFinallyObserver<>(var1, this.onFinally));
   }

   static final class DoFinallyObserver<T> extends BasicIntQueueDisposable<T> implements Observer<T> {
      private static final long serialVersionUID = 4109457741734051389L;
      final Observer<? super T> downstream;
      final Action onFinally;
      QueueDisposable<T> qd;
      boolean syncFused;
      Disposable upstream;

      DoFinallyObserver(Observer<? super T> var1, Action var2) {
         this.downstream = var1;
         this.onFinally = var2;
      }

      @Override
      public void clear() {
         this.qd.clear();
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         this.runFinally();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public boolean isEmpty() {
         return this.qd.isEmpty();
      }

      @Override
      public void onComplete() {
         this.downstream.onComplete();
         this.runFinally();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
         this.runFinally();
      }

      @Override
      public void onNext(T var1) {
         this.downstream.onNext((T)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            if (var1 instanceof QueueDisposable) {
               this.qd = (QueueDisposable<T>)var1;
            }

            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public T poll() throws Exception {
         Object var1 = this.qd.poll();
         if (var1 == null && this.syncFused) {
            this.runFinally();
         }

         return (T)var1;
      }

      @Override
      public int requestFusion(int var1) {
         QueueDisposable var3 = this.qd;
         boolean var2 = false;
         if (var3 != null && (var1 & 4) == 0) {
            var1 = var3.requestFusion(var1);
            if (var1 != 0) {
               if (var1 == 1) {
                  var2 = true;
               }

               this.syncFused = var2;
            }

            return var1;
         } else {
            return 0;
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void runFinally() {
         if (this.compareAndSet(0, 1)) {
            try {
               this.onFinally.run();
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               RxJavaPlugins.onError(var3);
               return;
            }
         }
      }
   }
}
