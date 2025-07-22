package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class CompletableMerge extends Completable {
   final boolean delayErrors;
   final int maxConcurrency;
   final Publisher<? extends CompletableSource> source;

   public CompletableMerge(Publisher<? extends CompletableSource> var1, int var2, boolean var3) {
      this.source = var1;
      this.maxConcurrency = var2;
      this.delayErrors = var3;
   }

   @Override
   public void subscribeActual(CompletableObserver var1) {
      CompletableMerge.CompletableMergeSubscriber var2 = new CompletableMerge.CompletableMergeSubscriber(var1, this.maxConcurrency, this.delayErrors);
      this.source.subscribe(var2);
   }

   static final class CompletableMergeSubscriber extends AtomicInteger implements FlowableSubscriber<CompletableSource>, Disposable {
      private static final long serialVersionUID = -2108443387387077490L;
      final boolean delayErrors;
      final CompletableObserver downstream;
      final AtomicThrowable error;
      final int maxConcurrency;
      final CompositeDisposable set;
      Subscription upstream;

      CompletableMergeSubscriber(CompletableObserver var1, int var2, boolean var3) {
         this.downstream = var1;
         this.maxConcurrency = var2;
         this.delayErrors = var3;
         this.set = new CompositeDisposable();
         this.error = new AtomicThrowable();
         this.lazySet(1);
      }

      @Override
      public void dispose() {
         this.upstream.cancel();
         this.set.dispose();
      }

      void innerComplete(CompletableMerge.CompletableMergeSubscriber.MergeInnerObserver var1) {
         this.set.delete(var1);
         if (this.decrementAndGet() == 0) {
            Throwable var2 = this.error.get();
            if (var2 != null) {
               this.downstream.onError(var2);
            } else {
               this.downstream.onComplete();
            }
         } else if (this.maxConcurrency != Integer.MAX_VALUE) {
            this.upstream.request(1L);
         }
      }

      void innerError(CompletableMerge.CompletableMergeSubscriber.MergeInnerObserver var1, Throwable var2) {
         this.set.delete(var1);
         if (!this.delayErrors) {
            this.upstream.cancel();
            this.set.dispose();
            if (this.error.addThrowable(var2)) {
               if (this.getAndSet(0) > 0) {
                  this.downstream.onError(this.error.terminate());
               }
            } else {
               RxJavaPlugins.onError(var2);
            }
         } else if (this.error.addThrowable(var2)) {
            if (this.decrementAndGet() == 0) {
               this.downstream.onError(this.error.terminate());
            } else if (this.maxConcurrency != Integer.MAX_VALUE) {
               this.upstream.request(1L);
            }
         } else {
            RxJavaPlugins.onError(var2);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.set.isDisposed();
      }

      public void onComplete() {
         if (this.decrementAndGet() == 0) {
            if (this.error.get() != null) {
               this.downstream.onError(this.error.terminate());
            } else {
               this.downstream.onComplete();
            }
         }
      }

      public void onError(Throwable var1) {
         if (!this.delayErrors) {
            this.set.dispose();
            if (this.error.addThrowable(var1)) {
               if (this.getAndSet(0) > 0) {
                  this.downstream.onError(this.error.terminate());
               }
            } else {
               RxJavaPlugins.onError(var1);
            }
         } else if (this.error.addThrowable(var1)) {
            if (this.decrementAndGet() == 0) {
               this.downstream.onError(this.error.terminate());
            }
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(CompletableSource var1) {
         this.getAndIncrement();
         CompletableMerge.CompletableMergeSubscriber.MergeInnerObserver var2 = new CompletableMerge.CompletableMergeSubscriber.MergeInnerObserver(this);
         this.set.add(var2);
         var1.subscribe(var2);
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

      final class MergeInnerObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
         private static final long serialVersionUID = 251330541679988317L;
         final CompletableMerge.CompletableMergeSubscriber this$0;

         MergeInnerObserver(CompletableMerge.CompletableMergeSubscriber var1) {
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
