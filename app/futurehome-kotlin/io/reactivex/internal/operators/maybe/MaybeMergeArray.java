package io.reactivex.internal.operators.maybe;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Subscriber;

public final class MaybeMergeArray<T> extends Flowable<T> {
   final MaybeSource<? extends T>[] sources;

   public MaybeMergeArray(MaybeSource<? extends T>[] var1) {
      this.sources = var1;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      MaybeSource[] var5 = this.sources;
      int var2 = var5.length;
      Object var4;
      if (var2 <= bufferSize()) {
         var4 = new MaybeMergeArray.MpscFillOnceSimpleQueue(var2);
      } else {
         var4 = new MaybeMergeArray.ClqSimpleQueue();
      }

      var4 = new MaybeMergeArray.MergeMaybeObserver(var1, var2, var4);
      var1.onSubscribe(var4);
      AtomicThrowable var6 = var4.error;

      for (MaybeSource var7 : var5) {
         if (var4.isCancelled() || var6.get() != null) {
            break;
         }

         var7.subscribe(var4);
      }
   }

   static final class ClqSimpleQueue<T> extends ConcurrentLinkedQueue<T> implements MaybeMergeArray.SimpleQueueWithConsumerIndex<T> {
      private static final long serialVersionUID = -4025173261791142821L;
      int consumerIndex;
      final AtomicInteger producerIndex = new AtomicInteger();

      @Override
      public int consumerIndex() {
         return this.consumerIndex;
      }

      @Override
      public void drop() {
         this.poll();
      }

      @Override
      public boolean offer(T var1) {
         this.producerIndex.getAndIncrement();
         return super.offer(var1);
      }

      @Override
      public boolean offer(T var1, T var2) {
         throw new UnsupportedOperationException();
      }

      @Override
      public T poll() {
         Object var1 = super.poll();
         if (var1 != null) {
            this.consumerIndex++;
         }

         return (T)var1;
      }

      @Override
      public int producerIndex() {
         return this.producerIndex.get();
      }
   }

   static final class MergeMaybeObserver<T> extends BasicIntQueueSubscription<T> implements MaybeObserver<T> {
      private static final long serialVersionUID = -660395290758764731L;
      volatile boolean cancelled;
      long consumed;
      final Subscriber<? super T> downstream;
      final AtomicThrowable error;
      boolean outputFused;
      final MaybeMergeArray.SimpleQueueWithConsumerIndex<Object> queue;
      final AtomicLong requested;
      final CompositeDisposable set;
      final int sourceCount;

      MergeMaybeObserver(Subscriber<? super T> var1, int var2, MaybeMergeArray.SimpleQueueWithConsumerIndex<Object> var3) {
         this.downstream = var1;
         this.sourceCount = var2;
         this.set = new CompositeDisposable();
         this.requested = new AtomicLong();
         this.error = new AtomicThrowable();
         this.queue = var3;
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.set.dispose();
            if (this.getAndIncrement() == 0) {
               this.queue.clear();
            }
         }
      }

      @Override
      public void clear() {
         this.queue.clear();
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            if (this.outputFused) {
               this.drainFused();
            } else {
               this.drainNormal();
            }
         }
      }

      void drainFused() {
         Subscriber var4 = this.downstream;
         MaybeMergeArray.SimpleQueueWithConsumerIndex var3 = this.queue;
         int var1 = 1;

         while (!this.cancelled) {
            Throwable var5 = this.error.get();
            if (var5 != null) {
               var3.clear();
               var4.onError(var5);
               return;
            }

            boolean var2;
            if (var3.producerIndex() == this.sourceCount) {
               var2 = 1;
            } else {
               var2 = 0;
            }

            if (!var3.isEmpty()) {
               var4.onNext(null);
            }

            if (var2) {
               var4.onComplete();
               return;
            }

            var2 = this.addAndGet(-var1);
            var1 = var2;
            if (var2 == 0) {
               return;
            }
         }

         var3.clear();
      }

      void drainNormal() {
         Subscriber var8 = this.downstream;
         MaybeMergeArray.SimpleQueueWithConsumerIndex var9 = this.queue;
         long var3 = this.consumed;
         int var1 = 1;

         int var10;
         do {
            long var5 = this.requested.get();

            while (true) {
               long var11;
               var10 = (var11 = var3 - var5) == 0L ? 0 : (var11 < 0L ? -1 : 1);
               if (!var10) {
                  break;
               }

               if (this.cancelled) {
                  var9.clear();
                  return;
               }

               if (this.error.get() != null) {
                  var9.clear();
                  var8.onError(this.error.terminate());
                  return;
               }

               if (var9.consumerIndex() == this.sourceCount) {
                  var8.onComplete();
                  return;
               }

               Object var7 = var9.poll();
               if (var7 == null) {
                  break;
               }

               if (var7 != NotificationLite.COMPLETE) {
                  var8.onNext(var7);
                  var3++;
               }
            }

            if (!var10) {
               if (this.error.get() != null) {
                  var9.clear();
                  var8.onError(this.error.terminate());
                  return;
               }

               while (var9.peek() == NotificationLite.COMPLETE) {
                  var9.drop();
               }

               if (var9.consumerIndex() == this.sourceCount) {
                  var8.onComplete();
                  return;
               }
            }

            this.consumed = var3;
            var10 = this.addAndGet(-var1);
            var1 = var10;
         } while (var10 != 0);
      }

      boolean isCancelled() {
         return this.cancelled;
      }

      @Override
      public boolean isEmpty() {
         return this.queue.isEmpty();
      }

      @Override
      public void onComplete() {
         this.queue.offer(NotificationLite.COMPLETE);
         this.drain();
      }

      @Override
      public void onError(Throwable var1) {
         if (this.error.addThrowable(var1)) {
            this.set.dispose();
            this.queue.offer(NotificationLite.COMPLETE);
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.set.add(var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.queue.offer(var1);
         this.drain();
      }

      @Override
      public T poll() throws Exception {
         Object var1;
         do {
            var1 = this.queue.poll();
         } while (var1 == NotificationLite.COMPLETE);

         return (T)var1;
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.drain();
         }
      }

      @Override
      public int requestFusion(int var1) {
         if ((var1 & 2) != 0) {
            this.outputFused = true;
            return 2;
         } else {
            return 0;
         }
      }
   }

   static final class MpscFillOnceSimpleQueue<T> extends AtomicReferenceArray<T> implements MaybeMergeArray.SimpleQueueWithConsumerIndex<T> {
      private static final long serialVersionUID = -7969063454040569579L;
      int consumerIndex;
      final AtomicInteger producerIndex = new AtomicInteger();

      MpscFillOnceSimpleQueue(int var1) {
         super(var1);
      }

      @Override
      public void clear() {
         while (this.poll() != null && !this.isEmpty()) {
         }
      }

      @Override
      public int consumerIndex() {
         return this.consumerIndex;
      }

      @Override
      public void drop() {
         int var1 = this.consumerIndex;
         this.lazySet(var1, null);
         this.consumerIndex = var1 + 1;
      }

      @Override
      public boolean isEmpty() {
         boolean var1;
         if (this.consumerIndex == this.producerIndex()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public boolean offer(T var1) {
         ObjectHelper.requireNonNull(var1, "value is null");
         int var2 = this.producerIndex.getAndIncrement();
         if (var2 < this.length()) {
            this.lazySet(var2, (T)var1);
            return true;
         } else {
            return false;
         }
      }

      @Override
      public boolean offer(T var1, T var2) {
         throw new UnsupportedOperationException();
      }

      @Override
      public T peek() {
         int var1 = this.consumerIndex;
         return var1 == this.length() ? null : this.get(var1);
      }

      @Override
      public T poll() {
         int var1 = this.consumerIndex;
         if (var1 == this.length()) {
            return null;
         } else {
            AtomicInteger var3 = this.producerIndex;

            do {
               Object var2 = this.get(var1);
               if (var2 != null) {
                  this.consumerIndex = var1 + 1;
                  this.lazySet(var1, null);
                  return (T)var2;
               }
            } while (var3.get() != var1);

            return null;
         }
      }

      @Override
      public int producerIndex() {
         return this.producerIndex.get();
      }
   }

   interface SimpleQueueWithConsumerIndex<T> extends SimpleQueue<T> {
      int consumerIndex();

      void drop();

      T peek();

      @Override
      T poll();

      int producerIndex();
   }
}
