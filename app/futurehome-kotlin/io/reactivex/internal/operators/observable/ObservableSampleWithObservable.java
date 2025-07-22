package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableSampleWithObservable<T> extends AbstractObservableWithUpstream<T, T> {
   final boolean emitLast;
   final ObservableSource<?> other;

   public ObservableSampleWithObservable(ObservableSource<T> var1, ObservableSource<?> var2, boolean var3) {
      super(var1);
      this.other = var2;
      this.emitLast = var3;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      SerializedObserver var2 = new SerializedObserver(var1);
      if (this.emitLast) {
         this.source.subscribe(new ObservableSampleWithObservable.SampleMainEmitLast<>(var2, this.other));
      } else {
         this.source.subscribe(new ObservableSampleWithObservable.SampleMainNoLast<>(var2, this.other));
      }
   }

   static final class SampleMainEmitLast<T> extends ObservableSampleWithObservable.SampleMainObserver<T> {
      private static final long serialVersionUID = -3029755663834015785L;
      volatile boolean done;
      final AtomicInteger wip = new AtomicInteger();

      SampleMainEmitLast(Observer<? super T> var1, ObservableSource<?> var2) {
         super(var1, var2);
      }

      @Override
      void completion() {
         this.done = true;
         if (this.wip.getAndIncrement() == 0) {
            this.emit();
            this.downstream.onComplete();
         }
      }

      @Override
      void run() {
         if (this.wip.getAndIncrement() == 0) {
            do {
               boolean var1 = this.done;
               this.emit();
               if (var1) {
                  this.downstream.onComplete();
                  return;
               }
            } while (this.wip.decrementAndGet() != 0);
         }
      }
   }

   static final class SampleMainNoLast<T> extends ObservableSampleWithObservable.SampleMainObserver<T> {
      private static final long serialVersionUID = -3029755663834015785L;

      SampleMainNoLast(Observer<? super T> var1, ObservableSource<?> var2) {
         super(var1, var2);
      }

      @Override
      void completion() {
         this.downstream.onComplete();
      }

      @Override
      void run() {
         this.emit();
      }
   }

   abstract static class SampleMainObserver<T> extends AtomicReference<T> implements Observer<T>, Disposable {
      private static final long serialVersionUID = -3517602651313910099L;
      final Observer<? super T> downstream;
      final AtomicReference<Disposable> other = new AtomicReference<>();
      final ObservableSource<?> sampler;
      Disposable upstream;

      SampleMainObserver(Observer<? super T> var1, ObservableSource<?> var2) {
         this.downstream = var1;
         this.sampler = var2;
      }

      public void complete() {
         this.upstream.dispose();
         this.completion();
      }

      abstract void completion();

      @Override
      public void dispose() {
         DisposableHelper.dispose(this.other);
         this.upstream.dispose();
      }

      void emit() {
         Object var1 = this.getAndSet(null);
         if (var1 != null) {
            this.downstream.onNext((T)var1);
         }
      }

      public void error(Throwable var1) {
         this.upstream.dispose();
         this.downstream.onError(var1);
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.other.get() == DisposableHelper.DISPOSED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void onComplete() {
         DisposableHelper.dispose(this.other);
         this.completion();
      }

      @Override
      public void onError(Throwable var1) {
         DisposableHelper.dispose(this.other);
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         this.lazySet((T)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            if (this.other.get() == null) {
               this.sampler.subscribe(new ObservableSampleWithObservable.SamplerObserver<>(this));
            }
         }
      }

      abstract void run();

      boolean setOther(Disposable var1) {
         return DisposableHelper.setOnce(this.other, var1);
      }
   }

   static final class SamplerObserver<T> implements Observer<Object> {
      final ObservableSampleWithObservable.SampleMainObserver<T> parent;

      SamplerObserver(ObservableSampleWithObservable.SampleMainObserver<T> var1) {
         this.parent = var1;
      }

      @Override
      public void onComplete() {
         this.parent.complete();
      }

      @Override
      public void onError(Throwable var1) {
         this.parent.error(var1);
      }

      @Override
      public void onNext(Object var1) {
         this.parent.run();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.parent.setOther(var1);
      }
   }
}
