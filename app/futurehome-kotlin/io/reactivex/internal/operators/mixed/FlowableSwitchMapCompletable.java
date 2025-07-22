package io.reactivex.internal.operators.mixed;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class FlowableSwitchMapCompletable<T> extends Completable {
   final boolean delayErrors;
   final Function<? super T, ? extends CompletableSource> mapper;
   final Flowable<T> source;

   public FlowableSwitchMapCompletable(Flowable<T> var1, Function<? super T, ? extends CompletableSource> var2, boolean var3) {
      this.source = var1;
      this.mapper = var2;
      this.delayErrors = var3;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new FlowableSwitchMapCompletable.SwitchMapCompletableObserver<>(var1, this.mapper, this.delayErrors));
   }

   static final class SwitchMapCompletableObserver<T> implements FlowableSubscriber<T>, Disposable {
      static final FlowableSwitchMapCompletable.SwitchMapCompletableObserver.SwitchMapInnerObserver INNER_DISPOSED = new FlowableSwitchMapCompletable.SwitchMapCompletableObserver.SwitchMapInnerObserver(
         null
      );
      final boolean delayErrors;
      volatile boolean done;
      final CompletableObserver downstream;
      final AtomicThrowable errors;
      final AtomicReference<FlowableSwitchMapCompletable.SwitchMapCompletableObserver.SwitchMapInnerObserver> inner;
      final Function<? super T, ? extends CompletableSource> mapper;
      Subscription upstream;

      SwitchMapCompletableObserver(CompletableObserver var1, Function<? super T, ? extends CompletableSource> var2, boolean var3) {
         this.downstream = var1;
         this.mapper = var2;
         this.delayErrors = var3;
         this.errors = new AtomicThrowable();
         this.inner = new AtomicReference<>();
      }

      @Override
      public void dispose() {
         this.upstream.cancel();
         this.disposeInner();
      }

      void disposeInner() {
         AtomicReference var2 = this.inner;
         FlowableSwitchMapCompletable.SwitchMapCompletableObserver.SwitchMapInnerObserver var1 = INNER_DISPOSED;
         FlowableSwitchMapCompletable.SwitchMapCompletableObserver.SwitchMapInnerObserver var3 = var2.getAndSet(var1);
         if (var3 != null && var3 != var1) {
            var3.dispose();
         }
      }

      void innerComplete(FlowableSwitchMapCompletable.SwitchMapCompletableObserver.SwitchMapInnerObserver var1) {
         if (ExternalSyntheticBackportWithForwarding0.m(this.inner, var1, null) && this.done) {
            Throwable var2 = this.errors.terminate();
            if (var2 == null) {
               this.downstream.onComplete();
            } else {
               this.downstream.onError(var2);
            }
         }
      }

      void innerError(FlowableSwitchMapCompletable.SwitchMapCompletableObserver.SwitchMapInnerObserver var1, Throwable var2) {
         if (ExternalSyntheticBackportWithForwarding0.m(this.inner, var1, null) && this.errors.addThrowable(var2)) {
            if (this.delayErrors) {
               if (this.done) {
                  Throwable var3 = this.errors.terminate();
                  this.downstream.onError(var3);
               }
            } else {
               this.dispose();
               Throwable var4 = this.errors.terminate();
               if (var4 != ExceptionHelper.TERMINATED) {
                  this.downstream.onError(var4);
               }
            }
         } else {
            RxJavaPlugins.onError(var2);
         }
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.inner.get() == INNER_DISPOSED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void onComplete() {
         this.done = true;
         if (this.inner.get() == null) {
            Throwable var1 = this.errors.terminate();
            if (var1 == null) {
               this.downstream.onComplete();
            } else {
               this.downstream.onError(var1);
            }
         }
      }

      public void onError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            if (this.delayErrors) {
               this.onComplete();
            } else {
               this.disposeInner();
               var1 = this.errors.terminate();
               if (var1 != ExceptionHelper.TERMINATED) {
                  this.downstream.onError(var1);
               }
            }
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         CompletableSource var2;
         try {
            var2 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null CompletableSource");
         } catch (Throwable var5) {
            Exceptions.throwIfFatal(var5);
            this.upstream.cancel();
            this.onError(var5);
            return;
         }

         var1 = new FlowableSwitchMapCompletable.SwitchMapCompletableObserver.SwitchMapInnerObserver(this);

         while (true) {
            FlowableSwitchMapCompletable.SwitchMapCompletableObserver.SwitchMapInnerObserver var3 = this.inner.get();
            if (var3 == INNER_DISPOSED) {
               break;
            }

            if (ExternalSyntheticBackportWithForwarding0.m(this.inner, var3, var1)) {
               if (var3 != null) {
                  var3.dispose();
               }

               var2.subscribe(var1);
               break;
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(Long.MAX_VALUE);
         }
      }

      static final class SwitchMapInnerObserver extends AtomicReference<Disposable> implements CompletableObserver {
         private static final long serialVersionUID = -8003404460084760287L;
         final FlowableSwitchMapCompletable.SwitchMapCompletableObserver<?> parent;

         SwitchMapInnerObserver(FlowableSwitchMapCompletable.SwitchMapCompletableObserver<?> var1) {
            this.parent = var1;
         }

         void dispose() {
            DisposableHelper.dispose(this);
         }

         @Override
         public void onComplete() {
            this.parent.innerComplete(this);
         }

         @Override
         public void onError(Throwable var1) {
            this.parent.innerError(this, var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }
      }
   }
}
