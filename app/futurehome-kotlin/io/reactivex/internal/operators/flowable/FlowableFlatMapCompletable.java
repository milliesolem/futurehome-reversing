package io.reactivex.internal.operators.flowable;

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
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFlatMapCompletable<T> extends AbstractFlowableWithUpstream<T, T> {
   final boolean delayErrors;
   final Function<? super T, ? extends CompletableSource> mapper;
   final int maxConcurrency;

   public FlowableFlatMapCompletable(Flowable<T> var1, Function<? super T, ? extends CompletableSource> var2, boolean var3, int var4) {
      super(var1);
      this.mapper = var2;
      this.delayErrors = var3;
      this.maxConcurrency = var4;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableFlatMapCompletable.FlatMapCompletableMainSubscriber<>(var1, this.mapper, this.delayErrors, this.maxConcurrency));
   }

   static final class FlatMapCompletableMainSubscriber<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = 8443155186132538303L;
      volatile boolean cancelled;
      final boolean delayErrors;
      final Subscriber<? super T> downstream;
      final AtomicThrowable errors;
      final Function<? super T, ? extends CompletableSource> mapper;
      final int maxConcurrency;
      final CompositeDisposable set;
      Subscription upstream;

      FlatMapCompletableMainSubscriber(Subscriber<? super T> var1, Function<? super T, ? extends CompletableSource> var2, boolean var3, int var4) {
         this.downstream = var1;
         this.mapper = var2;
         this.delayErrors = var3;
         this.errors = new AtomicThrowable();
         this.set = new CompositeDisposable();
         this.maxConcurrency = var4;
         this.lazySet(1);
      }

      public void cancel() {
         this.cancelled = true;
         this.upstream.cancel();
         this.set.dispose();
      }

      @Override
      public void clear() {
      }

      void innerComplete(FlowableFlatMapCompletable.FlatMapCompletableMainSubscriber<T>.InnerConsumer var1) {
         this.set.delete(var1);
         this.onComplete();
      }

      void innerError(FlowableFlatMapCompletable.FlatMapCompletableMainSubscriber<T>.InnerConsumer var1, Throwable var2) {
         this.set.delete(var1);
         this.onError(var2);
      }

      @Override
      public boolean isEmpty() {
         return true;
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
               this.cancel();
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
         FlowableFlatMapCompletable.FlatMapCompletableMainSubscriber.InnerConsumer var2 = new FlowableFlatMapCompletable.FlatMapCompletableMainSubscriber.InnerConsumer(
            this
         );
         if (!this.cancelled && this.set.add(var2)) {
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

      @Override
      public T poll() throws Exception {
         return null;
      }

      public void request(long var1) {
      }

      @Override
      public int requestFusion(int var1) {
         return var1 & 2;
      }

      final class InnerConsumer extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
         private static final long serialVersionUID = 8606673141535671828L;
         final FlowableFlatMapCompletable.FlatMapCompletableMainSubscriber this$0;

         InnerConsumer(FlowableFlatMapCompletable.FlatMapCompletableMainSubscriber var1) {
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
