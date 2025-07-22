package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableRepeatWhen<T> extends AbstractObservableWithUpstream<T, T> {
   final Function<? super Observable<Object>, ? extends ObservableSource<?>> handler;

   public ObservableRepeatWhen(ObservableSource<T> var1, Function<? super Observable<Object>, ? extends ObservableSource<?>> var2) {
      super(var1);
      this.handler = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      Subject var3 = PublishSubject.create().toSerialized();

      ObservableSource var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.handler.apply(var3), "The handler returned a null ObservableSource");
      } catch (Throwable var5) {
         Exceptions.throwIfFatal(var5);
         EmptyDisposable.error(var5, var1);
         return;
      }

      ObservableRepeatWhen.RepeatWhenObserver var6 = new ObservableRepeatWhen.RepeatWhenObserver<>(var1, var3, this.source);
      var1.onSubscribe(var6);
      var2.subscribe(var6.inner);
      var6.subscribeNext();
   }

   static final class RepeatWhenObserver<T> extends AtomicInteger implements Observer<T>, Disposable {
      private static final long serialVersionUID = 802743776666017014L;
      volatile boolean active;
      final Observer<? super T> downstream;
      final AtomicThrowable error;
      final ObservableRepeatWhen.RepeatWhenObserver<T>.InnerRepeatObserver inner;
      final Subject<Object> signaller;
      final ObservableSource<T> source;
      final AtomicReference<Disposable> upstream;
      final AtomicInteger wip;

      RepeatWhenObserver(Observer<? super T> var1, Subject<Object> var2, ObservableSource<T> var3) {
         this.downstream = var1;
         this.signaller = var2;
         this.source = var3;
         this.wip = new AtomicInteger();
         this.error = new AtomicThrowable();
         this.inner = new ObservableRepeatWhen.RepeatWhenObserver.InnerRepeatObserver(this);
         this.upstream = new AtomicReference<>();
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this.upstream);
         DisposableHelper.dispose(this.inner);
      }

      void innerComplete() {
         DisposableHelper.dispose(this.upstream);
         HalfSerializer.onComplete(this.downstream, this, this.error);
      }

      void innerError(Throwable var1) {
         DisposableHelper.dispose(this.upstream);
         HalfSerializer.onError(this.downstream, var1, this, this.error);
      }

      void innerNext() {
         this.subscribeNext();
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.upstream.get());
      }

      @Override
      public void onComplete() {
         DisposableHelper.replace(this.upstream, null);
         this.active = false;
         this.signaller.onNext(0);
      }

      @Override
      public void onError(Throwable var1) {
         DisposableHelper.dispose(this.inner);
         HalfSerializer.onError(this.downstream, var1, this, this.error);
      }

      @Override
      public void onNext(T var1) {
         HalfSerializer.onNext(this.downstream, (T)var1, this, this.error);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.upstream, var1);
      }

      void subscribeNext() {
         if (this.wip.getAndIncrement() == 0) {
            do {
               if (this.isDisposed()) {
                  return;
               }

               if (!this.active) {
                  this.active = true;
                  this.source.subscribe(this);
               }
            } while (this.wip.decrementAndGet() != 0);
         }
      }

      final class InnerRepeatObserver extends AtomicReference<Disposable> implements Observer<Object> {
         private static final long serialVersionUID = 3254781284376480842L;
         final ObservableRepeatWhen.RepeatWhenObserver this$0;

         InnerRepeatObserver(ObservableRepeatWhen.RepeatWhenObserver var1) {
            this.this$0 = var1;
         }

         @Override
         public void onComplete() {
            this.this$0.innerComplete();
         }

         @Override
         public void onError(Throwable var1) {
            this.this$0.innerError(var1);
         }

         @Override
         public void onNext(Object var1) {
            this.this$0.innerNext();
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }
      }
   }
}
