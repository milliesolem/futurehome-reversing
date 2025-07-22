package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.flowables.GroupedFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.EmptyComponent;
import io.reactivex.plugins.RxJavaPlugins;
import j..util.concurrent.ConcurrentHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableGroupBy<T, K, V> extends AbstractFlowableWithUpstream<T, GroupedFlowable<K, V>> {
   final int bufferSize;
   final boolean delayError;
   final Function<? super T, ? extends K> keySelector;
   final Function<? super Consumer<Object>, ? extends Map<K, Object>> mapFactory;
   final Function<? super T, ? extends V> valueSelector;

   public FlowableGroupBy(
      Flowable<T> var1,
      Function<? super T, ? extends K> var2,
      Function<? super T, ? extends V> var3,
      int var4,
      boolean var5,
      Function<? super Consumer<Object>, ? extends Map<K, Object>> var6
   ) {
      super(var1);
      this.keySelector = var2;
      this.valueSelector = var3;
      this.bufferSize = var4;
      this.delayError = var5;
      this.mapFactory = var6;
   }

   @Override
   protected void subscribeActual(Subscriber<? super GroupedFlowable<K, V>> var1) {
      Object var2;
      ConcurrentLinkedQueue var3;
      label21: {
         try {
            if (this.mapFactory != null) {
               var3 = new ConcurrentLinkedQueue();
               var2 = new FlowableGroupBy.EvictionAction(var3);
               var2 = this.mapFactory.apply((Consumer<Object>)var2);
               break label21;
            }

            var2 = new ConcurrentHashMap();
         } catch (Exception var4) {
            Exceptions.throwIfFatal(var4);
            var1.onSubscribe(EmptyComponent.INSTANCE);
            var1.onError(var4);
            return;
         }

         var3 = null;
      }

      FlowableGroupBy.GroupBySubscriber var5 = new FlowableGroupBy.GroupBySubscriber<>(
         var1, this.keySelector, this.valueSelector, this.bufferSize, this.delayError, (Map<Object, FlowableGroupBy.GroupedUnicast<K, V>>)var2, var3
      );
      this.source.subscribe(var5);
   }

   static final class EvictionAction<K, V> implements Consumer<FlowableGroupBy.GroupedUnicast<K, V>> {
      final Queue<FlowableGroupBy.GroupedUnicast<K, V>> evictedGroups;

      EvictionAction(Queue<FlowableGroupBy.GroupedUnicast<K, V>> var1) {
         this.evictedGroups = var1;
      }

      public void accept(FlowableGroupBy.GroupedUnicast<K, V> var1) {
         this.evictedGroups.offer(var1);
      }
   }

   public static final class GroupBySubscriber<T, K, V> extends BasicIntQueueSubscription<GroupedFlowable<K, V>> implements FlowableSubscriber<T> {
      static final Object NULL_KEY = new Object();
      private static final long serialVersionUID = -3688291656102519502L;
      final int bufferSize;
      final AtomicBoolean cancelled = new AtomicBoolean();
      final boolean delayError;
      boolean done;
      final Subscriber<? super GroupedFlowable<K, V>> downstream;
      Throwable error;
      final Queue<FlowableGroupBy.GroupedUnicast<K, V>> evictedGroups;
      volatile boolean finished;
      final AtomicInteger groupCount;
      final Map<Object, FlowableGroupBy.GroupedUnicast<K, V>> groups;
      final Function<? super T, ? extends K> keySelector;
      boolean outputFused;
      final SpscLinkedArrayQueue<GroupedFlowable<K, V>> queue;
      final AtomicLong requested = new AtomicLong();
      Subscription upstream;
      final Function<? super T, ? extends V> valueSelector;

      public GroupBySubscriber(
         Subscriber<? super GroupedFlowable<K, V>> var1,
         Function<? super T, ? extends K> var2,
         Function<? super T, ? extends V> var3,
         int var4,
         boolean var5,
         Map<Object, FlowableGroupBy.GroupedUnicast<K, V>> var6,
         Queue<FlowableGroupBy.GroupedUnicast<K, V>> var7
      ) {
         this.groupCount = new AtomicInteger(1);
         this.downstream = var1;
         this.keySelector = var2;
         this.valueSelector = var3;
         this.bufferSize = var4;
         this.delayError = var5;
         this.groups = var6;
         this.evictedGroups = var7;
         this.queue = new SpscLinkedArrayQueue<>(var4);
      }

      private void completeEvictions() {
         if (this.evictedGroups != null) {
            int var1 = 0;

            while (true) {
               FlowableGroupBy.GroupedUnicast var2 = this.evictedGroups.poll();
               if (var2 == null) {
                  if (var1 != 0) {
                     this.groupCount.addAndGet(-var1);
                  }
                  break;
               }

               var2.onComplete();
               var1++;
            }
         }
      }

      public void cancel() {
         if (this.cancelled.compareAndSet(false, true)) {
            this.completeEvictions();
            if (this.groupCount.decrementAndGet() == 0) {
               this.upstream.cancel();
            }
         }
      }

      public void cancel(K var1) {
         if (var1 == null) {
            var1 = NULL_KEY;
         }

         this.groups.remove(var1);
         if (this.groupCount.decrementAndGet() == 0) {
            this.upstream.cancel();
            if (!this.outputFused && this.getAndIncrement() == 0) {
               this.queue.clear();
            }
         }
      }

      boolean checkTerminated(boolean var1, boolean var2, Subscriber<?> var3, SpscLinkedArrayQueue<?> var4) {
         if (this.cancelled.get()) {
            var4.clear();
            return true;
         } else {
            if (this.delayError) {
               if (var1 && var2) {
                  Throwable var6 = this.error;
                  if (var6 != null) {
                     var3.onError(var6);
                  } else {
                     var3.onComplete();
                  }

                  return true;
               }
            } else if (var1) {
               Throwable var5 = this.error;
               if (var5 != null) {
                  var4.clear();
                  var3.onError(var5);
                  return true;
               }

               if (var2) {
                  var3.onComplete();
                  return true;
               }
            }

            return false;
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
         SpscLinkedArrayQueue var6 = this.queue;
         Subscriber var4 = this.downstream;
         int var1 = 1;

         while (!this.cancelled.get()) {
            boolean var3 = this.finished;
            if (var3 && !this.delayError) {
               Throwable var5 = this.error;
               if (var5 != null) {
                  var6.clear();
                  var4.onError(var5);
                  return;
               }
            }

            var4.onNext(null);
            if (var3) {
               Throwable var7 = this.error;
               if (var7 != null) {
                  var4.onError(var7);
               } else {
                  var4.onComplete();
               }

               return;
            }

            int var2 = this.addAndGet(-var1);
            var1 = var2;
            if (var2 == 0) {
               return;
            }
         }
      }

      void drainNormal() {
         SpscLinkedArrayQueue var9 = this.queue;
         Subscriber var10 = this.downstream;
         int var1 = 1;

         int var12;
         do {
            long var5 = this.requested.get();
            long var3 = 0L;

            while (true) {
               long var13;
               var12 = (var13 = var3 - var5) == 0L ? 0 : (var13 < 0L ? -1 : 1);
               if (!var12) {
                  break;
               }

               boolean var8 = this.finished;
               GroupedFlowable var11 = (GroupedFlowable)var9.poll();
               boolean var7;
               if (var11 == null) {
                  var7 = true;
               } else {
                  var7 = false;
               }

               if (this.checkTerminated(var8, var7, var10, var9)) {
                  return;
               }

               if (var7) {
                  break;
               }

               var10.onNext(var11);
               var3++;
            }

            if (!var12 && this.checkTerminated(this.finished, var9.isEmpty(), var10, var9)) {
               return;
            }

            if (var3 != 0L) {
               if (var5 != Long.MAX_VALUE) {
                  this.requested.addAndGet(-var3);
               }

               this.upstream.request(var3);
            }

            var12 = this.addAndGet(-var1);
            var1 = var12;
         } while (var12 != 0);
      }

      @Override
      public boolean isEmpty() {
         return this.queue.isEmpty();
      }

      public void onComplete() {
         if (!this.done) {
            Iterator var1 = this.groups.values().iterator();

            while (var1.hasNext()) {
               ((FlowableGroupBy.GroupedUnicast)var1.next()).onComplete();
            }

            this.groups.clear();
            Queue var2 = this.evictedGroups;
            if (var2 != null) {
               var2.clear();
            }

            this.done = true;
            this.finished = true;
            this.drain();
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            Iterator var2 = this.groups.values().iterator();

            while (var2.hasNext()) {
               ((FlowableGroupBy.GroupedUnicast)var2.next()).onError(var1);
            }

            this.groups.clear();
            Queue var3 = this.evictedGroups;
            if (var3 != null) {
               var3.clear();
            }

            this.error = var1;
            this.finished = true;
            this.drain();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            SpscLinkedArrayQueue var6 = this.queue;

            Object var4;
            try {
               var4 = this.keySelector.apply((T)var1);
            } catch (Throwable var12) {
               Exceptions.throwIfFatal(var12);
               this.upstream.cancel();
               this.onError(var12);
               return;
            }

            Object var3;
            if (var4 != null) {
               var3 = (FlowableGroupBy.GroupedUnicast)var4;
            } else {
               var3 = (FlowableGroupBy.GroupedUnicast)NULL_KEY;
            }

            FlowableGroupBy.GroupedUnicast var5 = this.groups.get(var3);
            boolean var2;
            if (var5 == null) {
               if (this.cancelled.get()) {
                  return;
               }

               var4 = FlowableGroupBy.GroupedUnicast.createWith(var4, this.bufferSize, this, this.delayError);
               this.groups.put(var3, (FlowableGroupBy.GroupedUnicast<K, V>)var4);
               this.groupCount.getAndIncrement();
               var2 = true;
               var3 = (FlowableGroupBy.GroupedUnicast)var4;
            } else {
               var2 = false;
               var3 = var5;
            }

            try {
               var1 = ObjectHelper.requireNonNull(this.valueSelector.apply((T)var1), "The valueSelector returned null");
            } catch (Throwable var11) {
               Exceptions.throwIfFatal(var11);
               this.upstream.cancel();
               this.onError(var11);
               return;
            }

            var3.onNext(var1);
            this.completeEvictions();
            if (var2) {
               var6.offer(var3);
               this.drain();
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(this.bufferSize);
         }
      }

      public GroupedFlowable<K, V> poll() {
         return this.queue.poll();
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

   static final class GroupedUnicast<K, T> extends GroupedFlowable<K, T> {
      final FlowableGroupBy.State<T, K> state;

      protected GroupedUnicast(K var1, FlowableGroupBy.State<T, K> var2) {
         super((K)var1);
         this.state = var2;
      }

      public static <T, K> FlowableGroupBy.GroupedUnicast<K, T> createWith(K var0, int var1, FlowableGroupBy.GroupBySubscriber<?, K, T> var2, boolean var3) {
         return new FlowableGroupBy.GroupedUnicast<>((K)var0, new FlowableGroupBy.State<>(var1, var2, (K)var0, var3));
      }

      public void onComplete() {
         this.state.onComplete();
      }

      public void onError(Throwable var1) {
         this.state.onError(var1);
      }

      public void onNext(T var1) {
         this.state.onNext((T)var1);
      }

      @Override
      protected void subscribeActual(Subscriber<? super T> var1) {
         this.state.subscribe(var1);
      }
   }

   static final class State<T, K> extends BasicIntQueueSubscription<T> implements Publisher<T> {
      private static final long serialVersionUID = -3852313036005250360L;
      final AtomicReference<Subscriber<? super T>> actual;
      final AtomicBoolean cancelled;
      final boolean delayError;
      volatile boolean done;
      Throwable error;
      final K key;
      final AtomicBoolean once;
      boolean outputFused;
      final FlowableGroupBy.GroupBySubscriber<?, K, T> parent;
      int produced;
      final SpscLinkedArrayQueue<T> queue;
      final AtomicLong requested = new AtomicLong();

      State(int var1, FlowableGroupBy.GroupBySubscriber<?, K, T> var2, K var3, boolean var4) {
         this.cancelled = new AtomicBoolean();
         this.actual = new AtomicReference<>();
         this.once = new AtomicBoolean();
         this.queue = new SpscLinkedArrayQueue<>(var1);
         this.parent = var2;
         this.key = (K)var3;
         this.delayError = var4;
      }

      public void cancel() {
         if (this.cancelled.compareAndSet(false, true)) {
            this.parent.cancel(this.key);
         }
      }

      boolean checkTerminated(boolean var1, boolean var2, Subscriber<? super T> var3, boolean var4) {
         if (this.cancelled.get()) {
            this.queue.clear();
            return true;
         } else {
            if (var1) {
               if (var4) {
                  if (var2) {
                     Throwable var5 = this.error;
                     if (var5 != null) {
                        var3.onError(var5);
                     } else {
                        var3.onComplete();
                     }

                     return true;
                  }
               } else {
                  Throwable var6 = this.error;
                  if (var6 != null) {
                     this.queue.clear();
                     var3.onError(var6);
                     return true;
                  }

                  if (var2) {
                     var3.onComplete();
                     return true;
                  }
               }
            }

            return false;
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
         SpscLinkedArrayQueue var5 = this.queue;
         Subscriber var4 = this.actual.get();
         int var1 = 1;

         while (true) {
            if (var4 != null) {
               if (this.cancelled.get()) {
                  var5.clear();
                  return;
               }

               boolean var3 = this.done;
               if (var3 && !this.delayError) {
                  Throwable var6 = this.error;
                  if (var6 != null) {
                     var5.clear();
                     var4.onError(var6);
                     return;
                  }
               }

               var4.onNext(null);
               if (var3) {
                  Throwable var7 = this.error;
                  if (var7 != null) {
                     var4.onError(var7);
                  } else {
                     var4.onComplete();
                  }

                  return;
               }
            }

            int var2 = this.addAndGet(-var1);
            if (var2 == 0) {
               return;
            }

            var1 = var2;
            if (var4 == null) {
               var4 = this.actual.get();
               var1 = var2;
            }
         }
      }

      void drainNormal() {
         SpscLinkedArrayQueue var11 = this.queue;
         boolean var9 = this.delayError;
         Subscriber var10 = this.actual.get();
         int var1 = 1;

         while (true) {
            if (var10 != null) {
               long var5 = this.requested.get();
               long var3 = 0L;

               int var2;
               while (true) {
                  long var14;
                  var2 = (var14 = var3 - var5) == 0L ? 0 : (var14 < 0L ? -1 : 1);
                  if (!var2) {
                     break;
                  }

                  boolean var8 = this.done;
                  Object var12 = var11.poll();
                  boolean var7;
                  if (var12 == null) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  if (this.checkTerminated(var8, var7, var10, var9)) {
                     return;
                  }

                  if (var7) {
                     break;
                  }

                  var10.onNext(var12);
                  var3++;
               }

               if (!var2 && this.checkTerminated(this.done, var11.isEmpty(), var10, var9)) {
                  return;
               }

               if (var3 != 0L) {
                  if (var5 != Long.MAX_VALUE) {
                     this.requested.addAndGet(-var3);
                  }

                  this.parent.upstream.request(var3);
               }
            }

            int var13 = this.addAndGet(-var1);
            if (var13 == 0) {
               return;
            }

            var1 = var13;
            if (var10 == null) {
               var10 = this.actual.get();
               var1 = var13;
            }
         }
      }

      @Override
      public boolean isEmpty() {
         if (this.queue.isEmpty()) {
            this.tryReplenish();
            return true;
         } else {
            return false;
         }
      }

      public void onComplete() {
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         this.drain();
      }

      public void onNext(T var1) {
         this.queue.offer((T)var1);
         this.drain();
      }

      @Override
      public T poll() {
         Object var1 = this.queue.poll();
         if (var1 != null) {
            this.produced++;
            return (T)var1;
         } else {
            this.tryReplenish();
            return null;
         }
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

      public void subscribe(Subscriber<? super T> var1) {
         if (this.once.compareAndSet(false, true)) {
            var1.onSubscribe(this);
            this.actual.lazySet(var1);
            this.drain();
         } else {
            EmptySubscription.error(new IllegalStateException("Only one Subscriber allowed!"), var1);
         }
      }

      void tryReplenish() {
         int var1 = this.produced;
         if (var1 != 0) {
            this.produced = 0;
            this.parent.upstream.request(var1);
         }
      }
   }
}
