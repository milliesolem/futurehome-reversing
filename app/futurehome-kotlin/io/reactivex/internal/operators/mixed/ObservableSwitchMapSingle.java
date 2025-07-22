package io.reactivex.internal.operators.mixed;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableSwitchMapSingle<T, R> extends Observable<R> {
   final boolean delayErrors;
   final Function<? super T, ? extends SingleSource<? extends R>> mapper;
   final Observable<T> source;

   public ObservableSwitchMapSingle(Observable<T> var1, Function<? super T, ? extends SingleSource<? extends R>> var2, boolean var3) {
      this.source = var1;
      this.mapper = var2;
      this.delayErrors = var3;
   }

   @Override
   protected void subscribeActual(Observer<? super R> var1) {
      if (!ScalarXMapZHelper.tryAsSingle(this.source, this.mapper, var1)) {
         this.source.subscribe(new ObservableSwitchMapSingle.SwitchMapSingleMainObserver<>(var1, this.mapper, this.delayErrors));
      }
   }

   static final class SwitchMapSingleMainObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
      static final ObservableSwitchMapSingle.SwitchMapSingleMainObserver.SwitchMapSingleObserver<Object> INNER_DISPOSED = new ObservableSwitchMapSingle.SwitchMapSingleMainObserver.SwitchMapSingleObserver<>(
         null
      );
      private static final long serialVersionUID = -5402190102429853762L;
      volatile boolean cancelled;
      final boolean delayErrors;
      volatile boolean done;
      final Observer<? super R> downstream;
      final AtomicThrowable errors;
      final AtomicReference<ObservableSwitchMapSingle.SwitchMapSingleMainObserver.SwitchMapSingleObserver<R>> inner;
      final Function<? super T, ? extends SingleSource<? extends R>> mapper;
      Disposable upstream;

      SwitchMapSingleMainObserver(Observer<? super R> var1, Function<? super T, ? extends SingleSource<? extends R>> var2, boolean var3) {
         this.downstream = var1;
         this.mapper = var2;
         this.delayErrors = var3;
         this.errors = new AtomicThrowable();
         this.inner = new AtomicReference<>();
      }

      @Override
      public void dispose() {
         this.cancelled = true;
         this.upstream.dispose();
         this.disposeInner();
      }

      void disposeInner() {
         AtomicReference var2 = this.inner;
         ObservableSwitchMapSingle.SwitchMapSingleMainObserver.SwitchMapSingleObserver var1 = INNER_DISPOSED;
         ObservableSwitchMapSingle.SwitchMapSingleMainObserver.SwitchMapSingleObserver var3 = var2.getAndSet(var1);
         if (var3 != null && var3 != var1) {
            var3.dispose();
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            Observer var4 = this.downstream;
            AtomicThrowable var6 = this.errors;
            AtomicReference var5 = this.inner;
            int var1 = 1;

            while (!this.cancelled) {
               if (var6.get() != null && !this.delayErrors) {
                  var4.onError(var6.terminate());
                  return;
               }

               boolean var3 = this.done;
               ObservableSwitchMapSingle.SwitchMapSingleMainObserver.SwitchMapSingleObserver var7 = (ObservableSwitchMapSingle.SwitchMapSingleMainObserver.SwitchMapSingleObserver)var5.get();
               boolean var2;
               if (var7 == null) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               if (var3 && var2) {
                  Throwable var9 = var6.terminate();
                  if (var9 != null) {
                     var4.onError(var9);
                  } else {
                     var4.onComplete();
                  }

                  return;
               }

               if (!var2 && var7.item != null) {
                  ExternalSyntheticBackportWithForwarding0.m(var5, var7, null);
                  var4.onNext(var7.item);
               } else {
                  var2 = this.addAndGet(-var1);
                  var1 = var2;
                  if (var2 == 0) {
                     return;
                  }
               }
            }
         }
      }

      void innerError(ObservableSwitchMapSingle.SwitchMapSingleMainObserver.SwitchMapSingleObserver<R> var1, Throwable var2) {
         if (ExternalSyntheticBackportWithForwarding0.m(this.inner, var1, null) && this.errors.addThrowable(var2)) {
            if (!this.delayErrors) {
               this.upstream.dispose();
               this.disposeInner();
            }

            this.drain();
         } else {
            RxJavaPlugins.onError(var2);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         this.done = true;
         this.drain();
      }

      @Override
      public void onError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            if (!this.delayErrors) {
               this.disposeInner();
            }

            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         ObservableSwitchMapSingle.SwitchMapSingleMainObserver.SwitchMapSingleObserver var2 = this.inner.get();
         if (var2 != null) {
            var2.dispose();
         }

         try {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null SingleSource");
         } catch (Throwable var5) {
            Exceptions.throwIfFatal(var5);
            this.upstream.dispose();
            this.inner.getAndSet((ObservableSwitchMapSingle.SwitchMapSingleMainObserver.SwitchMapSingleObserver<R>)INNER_DISPOSED);
            this.onError(var5);
            return;
         }

         ObservableSwitchMapSingle.SwitchMapSingleMainObserver.SwitchMapSingleObserver var3 = new ObservableSwitchMapSingle.SwitchMapSingleMainObserver.SwitchMapSingleObserver<>(
            this
         );

         while (true) {
            var2 = this.inner.get();
            if (var2 == INNER_DISPOSED) {
               break;
            }

            if (ExternalSyntheticBackportWithForwarding0.m(this.inner, var2, var3)) {
               var1.subscribe(var3);
               break;
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

      static final class SwitchMapSingleObserver<R> extends AtomicReference<Disposable> implements SingleObserver<R> {
         private static final long serialVersionUID = 8042919737683345351L;
         volatile R item;
         final ObservableSwitchMapSingle.SwitchMapSingleMainObserver<?, R> parent;

         SwitchMapSingleObserver(ObservableSwitchMapSingle.SwitchMapSingleMainObserver<?, R> var1) {
            this.parent = var1;
         }

         void dispose() {
            DisposableHelper.dispose(this);
         }

         @Override
         public void onError(Throwable var1) {
            this.parent.innerError(this, var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }

         @Override
         public void onSuccess(R var1) {
            this.item = (R)var1;
            this.parent.drain();
         }
      }
   }
}
