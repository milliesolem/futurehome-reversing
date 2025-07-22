package io.reactivex.internal.operators.observable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableFlatMapCompletable<T> extends AbstractObservableWithUpstream<T, T> {
   final boolean delayErrors;
   final Function<? super T, ? extends CompletableSource> mapper;

   public ObservableFlatMapCompletable(ObservableSource<T> var1, Function<? super T, ? extends CompletableSource> var2, boolean var3) {
      super(var1);
      this.mapper = var2;
      this.delayErrors = var3;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableFlatMapCompletable.FlatMapCompletableMainObserver<>(var1, this.mapper, this.delayErrors));
   }

   static final class FlatMapCompletableMainObserver<T> extends BasicIntQueueDisposable<T> implements Observer<T> {
      private static final long serialVersionUID = 8443155186132538303L;
      final boolean delayErrors;
      volatile boolean disposed;
      final Observer<? super T> downstream;
      final AtomicThrowable errors;
      final Function<? super T, ? extends CompletableSource> mapper;
      final CompositeDisposable set;
      Disposable upstream;

      FlatMapCompletableMainObserver(Observer<? super T> var1, Function<? super T, ? extends CompletableSource> var2, boolean var3) {
         this.downstream = var1;
         this.mapper = var2;
         this.delayErrors = var3;
         this.errors = new AtomicThrowable();
         this.set = new CompositeDisposable();
         this.lazySet(1);
      }

      @Override
      public void clear() {
      }

      @Override
      public void dispose() {
         this.disposed = true;
         this.upstream.dispose();
         this.set.dispose();
      }

      void innerComplete(ObservableFlatMapCompletable.FlatMapCompletableMainObserver<T>.InnerObserver var1) {
         this.set.delete(var1);
         this.onComplete();
      }

      void innerError(ObservableFlatMapCompletable.FlatMapCompletableMainObserver<T>.InnerObserver var1, Throwable var2) {
         this.set.delete(var1);
         this.onError(var2);
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public boolean isEmpty() {
         return true;
      }

      @Override
      public void onComplete() {
         if (this.decrementAndGet() == 0) {
            Throwable var1 = this.errors.terminate();
            if (var1 != null) {
               this.downstream.onError(var1);
            } else {
               this.downstream.onComplete();
            }
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            if (this.delayErrors) {
               if (this.decrementAndGet() == 0) {
                  var1 = this.errors.terminate();
                  this.downstream.onError(var1);
               }
            } else {
               this.dispose();
               if (this.getAndSet(0) > 0) {
                  var1 = this.errors.terminate();
                  this.downstream.onError(var1);
               }
            }
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         CompletableSource var2;
         try {
            var2 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null CompletableSource");
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.upstream.dispose();
            this.onError(var4);
            return;
         }

         this.getAndIncrement();
         var1 = new ObservableFlatMapCompletable.FlatMapCompletableMainObserver.InnerObserver(this);
         if (!this.disposed && this.set.add(var1)) {
            var2.subscribe(var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public T poll() throws Exception {
         return null;
      }

      @Override
      public int requestFusion(int var1) {
         return var1 & 2;
      }

      final class InnerObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
         private static final long serialVersionUID = 8606673141535671828L;
         final ObservableFlatMapCompletable.FlatMapCompletableMainObserver this$0;

         InnerObserver(ObservableFlatMapCompletable.FlatMapCompletableMainObserver var1) {
            this.this$0 = var1;
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
            this.this$0.innerComplete(this);
         }

         @Override
         public void onError(Throwable var1) {
            this.this$0.innerError(this, var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }
      }
   }
}
