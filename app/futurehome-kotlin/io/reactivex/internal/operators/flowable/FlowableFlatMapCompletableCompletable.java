package io.reactivex.internal.operators.flowable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class FlowableFlatMapCompletableCompletable<T> extends Completable implements FuseToFlowable<T> {
   final boolean delayErrors;
   final Function<? super T, ? extends CompletableSource> mapper;
   final int maxConcurrency;
   final Flowable<T> source;

   public FlowableFlatMapCompletableCompletable(Flowable<T> var1, Function<? super T, ? extends CompletableSource> var2, boolean var3, int var4) {
      this.source = var1;
      this.mapper = var2;
      this.delayErrors = var3;
      this.maxConcurrency = var4;
   }

   @Override
   public Flowable<T> fuseToFlowable() {
      return RxJavaPlugins.onAssembly(new FlowableFlatMapCompletable<>(this.source, this.mapper, this.delayErrors, this.maxConcurrency));
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source
         .subscribe(new FlowableFlatMapCompletableCompletable.FlatMapCompletableMainSubscriber<>(var1, this.mapper, this.delayErrors, this.maxConcurrency));
   }

   static final class FlatMapCompletableMainSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Disposable {
      private static final long serialVersionUID = 8443155186132538303L;
      final boolean delayErrors;
      volatile boolean disposed;
      final CompletableObserver downstream;
      final AtomicThrowable errors;
      final Function<? super T, ? extends CompletableSource> mapper;
      final int maxConcurrency;
      final CompositeDisposable set;
      Subscription upstream;

      FlatMapCompletableMainSubscriber(CompletableObserver var1, Function<? super T, ? extends CompletableSource> var2, boolean var3, int var4) {
         this.downstream = var1;
         this.mapper = var2;
         this.delayErrors = var3;
         this.errors = new AtomicThrowable();
         this.set = new CompositeDisposable();
         this.maxConcurrency = var4;
         this.lazySet(1);
      }

      @Override
      public void dispose() {
         this.disposed = true;
         this.upstream.cancel();
         this.set.dispose();
      }

      void innerComplete(FlowableFlatMapCompletableCompletable.FlatMapCompletableMainSubscriber<T>.InnerObserver var1) {
         this.set.delete(var1);
         this.onComplete();
      }

      void innerError(FlowableFlatMapCompletableCompletable.FlatMapCompletableMainSubscriber<T>.InnerObserver var1, Throwable var2) {
         this.set.delete(var1);
         this.onError(var2);
      }

      @Override
      public boolean isDisposed() {
         return this.set.isDisposed();
      }

      public void onComplete() {
         if (this.decrementAndGet() == 0) {
            Throwable var1 = this.errors.terminate();
            if (var1 != null) {
               this.downstream.onError(var1);
            } else {
               this.downstream.onComplete();
            }
         } else if (this.maxConcurrency != Integer.MAX_VALUE) {
            this.upstream.request(1L);
         }
      }

      public void onError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            if (this.delayErrors) {
               if (this.decrementAndGet() == 0) {
                  var1 = this.errors.terminate();
                  this.downstream.onError(var1);
               } else if (this.maxConcurrency != Integer.MAX_VALUE) {
                  this.upstream.request(1L);
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
      public void onNext(T var1) {
         try {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null CompletableSource");
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.upstream.cancel();
            this.onError(var4);
            return;
         }

         this.getAndIncrement();
         FlowableFlatMapCompletableCompletable.FlatMapCompletableMainSubscriber.InnerObserver var2 = new FlowableFlatMapCompletableCompletable.FlatMapCompletableMainSubscriber.InnerObserver(
            this
         );
         if (!this.disposed && this.set.add(var2)) {
            var1.subscribe(var2);
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            int var2 = this.maxConcurrency;
            if (var2 == Integer.MAX_VALUE) {
               var1.request(Long.MAX_VALUE);
            } else {
               var1.request(var2);
            }
         }
      }

      final class InnerObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
         private static final long serialVersionUID = 8606673141535671828L;
         final FlowableFlatMapCompletableCompletable.FlatMapCompletableMainSubscriber this$0;

         InnerObserver(FlowableFlatMapCompletableCompletable.FlatMapCompletableMainSubscriber var1) {
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
