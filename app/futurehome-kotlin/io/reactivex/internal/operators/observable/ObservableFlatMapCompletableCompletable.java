package io.reactivex.internal.operators.observable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableFlatMapCompletableCompletable<T> extends Completable implements FuseToObservable<T> {
   final boolean delayErrors;
   final Function<? super T, ? extends CompletableSource> mapper;
   final ObservableSource<T> source;

   public ObservableFlatMapCompletableCompletable(ObservableSource<T> var1, Function<? super T, ? extends CompletableSource> var2, boolean var3) {
      this.source = var1;
      this.mapper = var2;
      this.delayErrors = var3;
   }

   @Override
   public Observable<T> fuseToObservable() {
      return RxJavaPlugins.onAssembly(new ObservableFlatMapCompletable<>(this.source, this.mapper, this.delayErrors));
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new ObservableFlatMapCompletableCompletable.FlatMapCompletableMainObserver<>(var1, this.mapper, this.delayErrors));
   }

   static final class FlatMapCompletableMainObserver<T> extends AtomicInteger implements Disposable, Observer<T> {
      private static final long serialVersionUID = 8443155186132538303L;
      final boolean delayErrors;
      volatile boolean disposed;
      final CompletableObserver downstream;
      final AtomicThrowable errors;
      final Function<? super T, ? extends CompletableSource> mapper;
      final CompositeDisposable set;
      Disposable upstream;

      FlatMapCompletableMainObserver(CompletableObserver var1, Function<? super T, ? extends CompletableSource> var2, boolean var3) {
         this.downstream = var1;
         this.mapper = var2;
         this.delayErrors = var3;
         this.errors = new AtomicThrowable();
         this.set = new CompositeDisposable();
         this.lazySet(1);
      }

      @Override
      public void dispose() {
         this.disposed = true;
         this.upstream.dispose();
         this.set.dispose();
      }

      void innerComplete(ObservableFlatMapCompletableCompletable.FlatMapCompletableMainObserver<T>.InnerObserver var1) {
         this.set.delete(var1);
         this.onComplete();
      }

      void innerError(ObservableFlatMapCompletableCompletable.FlatMapCompletableMainObserver<T>.InnerObserver var1, Throwable var2) {
         this.set.delete(var1);
         this.onError(var2);
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
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
         try {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null CompletableSource");
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.upstream.dispose();
            this.onError(var4);
            return;
         }

         this.getAndIncrement();
         ObservableFlatMapCompletableCompletable.FlatMapCompletableMainObserver.InnerObserver var2 = new ObservableFlatMapCompletableCompletable.FlatMapCompletableMainObserver.InnerObserver(
            this
         );
         if (!this.disposed && this.set.add(var2)) {
            var1.subscribe(var2);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      final class InnerObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
         private static final long serialVersionUID = 8606673141535671828L;
         final ObservableFlatMapCompletableCompletable.FlatMapCompletableMainObserver this$0;

         InnerObserver(ObservableFlatMapCompletableCompletable.FlatMapCompletableMainObserver var1) {
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
