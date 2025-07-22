package io.reactivex.internal.operators.parallel;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelSortedJoin<T> extends Flowable<T> {
   final Comparator<? super T> comparator;
   final ParallelFlowable<List<T>> source;

   public ParallelSortedJoin(ParallelFlowable<List<T>> var1, Comparator<? super T> var2) {
      this.source = var1;
      this.comparator = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      ParallelSortedJoin.SortedJoinSubscription var2 = new ParallelSortedJoin.SortedJoinSubscription<>(var1, this.source.parallelism(), this.comparator);
      var1.onSubscribe(var2);
      this.source.subscribe(var2.subscribers);
   }

   static final class SortedJoinInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<List<T>> {
      private static final long serialVersionUID = 6751017204873808094L;
      final int index;
      final ParallelSortedJoin.SortedJoinSubscription<T> parent;

      SortedJoinInnerSubscriber(ParallelSortedJoin.SortedJoinSubscription<T> var1, int var2) {
         this.parent = var1;
         this.index = var2;
      }

      void cancel() {
         SubscriptionHelper.cancel(this);
      }

      public void onComplete() {
      }

      public void onError(Throwable var1) {
         this.parent.innerError(var1);
      }

      public void onNext(List<T> var1) {
         this.parent.innerNext(var1, this.index);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.setOnce(this, var1, Long.MAX_VALUE);
      }
   }

   static final class SortedJoinSubscription<T> extends AtomicInteger implements Subscription {
      private static final long serialVersionUID = 3481980673745556697L;
      volatile boolean cancelled;
      final Comparator<? super T> comparator;
      final Subscriber<? super T> downstream;
      final AtomicReference<Throwable> error;
      final int[] indexes;
      final List<T>[] lists;
      final AtomicInteger remaining;
      final AtomicLong requested = new AtomicLong();
      final ParallelSortedJoin.SortedJoinInnerSubscriber<T>[] subscribers;

      SortedJoinSubscription(Subscriber<? super T> var1, int var2, Comparator<? super T> var3) {
         this.remaining = new AtomicInteger();
         this.error = new AtomicReference<>();
         this.downstream = var1;
         this.comparator = var3;
         ParallelSortedJoin.SortedJoinInnerSubscriber[] var5 = new ParallelSortedJoin.SortedJoinInnerSubscriber[var2];

         for (int var4 = 0; var4 < var2; var4++) {
            var5[var4] = new ParallelSortedJoin.SortedJoinInnerSubscriber<>(this, var4);
         }

         this.subscribers = var5;
         this.lists = new List[var2];
         this.indexes = new int[var2];
         this.remaining.lazySet(var2);
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.cancelAll();
            if (this.getAndIncrement() == 0) {
               Arrays.fill(this.lists, null);
            }
         }
      }

      void cancelAll() {
         ParallelSortedJoin.SortedJoinInnerSubscriber[] var3 = this.subscribers;
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].cancel();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            Subscriber var15 = this.downstream;
            List[] var14 = this.lists;
            int[] var16 = this.indexes;
            int var5 = var16.length;
            int var2 = 1;

            while (true) {
               long var9 = this.requested.get();
               long var7 = 0L;

               while (true) {
                  int var3 = 0;
                  int var1 = 0;
                  long var30;
                  int var4 = (var30 = var7 - var9) == 0L ? 0 : (var30 < 0L ? -1 : 1);
                  if (!var4) {
                     if (!var4) {
                        if (this.cancelled) {
                           Arrays.fill(var14, null);
                           return;
                        }

                        Throwable var28 = this.error.get();
                        var1 = var3;
                        if (var28 != null) {
                           this.cancelAll();
                           Arrays.fill(var14, null);
                           var15.onError(var28);
                           return;
                        }

                        while (true) {
                           if (var1 >= var5) {
                              Arrays.fill(var14, null);
                              var15.onComplete();
                              return;
                           }

                           if (var16[var1] != var14[var1].size()) {
                              break;
                           }

                           var1++;
                        }
                     }

                     if (var7 != 0L && var9 != Long.MAX_VALUE) {
                        this.requested.addAndGet(-var7);
                     }

                     var3 = this.get();
                     var1 = var3;
                     if (var3 == var2) {
                        var2 = this.addAndGet(-var2);
                        var1 = var2;
                        if (var2 == 0) {
                           return;
                        }
                     }

                     var2 = var1;
                     break;
                  }

                  if (this.cancelled) {
                     Arrays.fill(var14, null);
                     return;
                  }

                  Throwable var11 = this.error.get();
                  if (var11 != null) {
                     this.cancelAll();
                     Arrays.fill(var14, null);
                     var15.onError(var11);
                     return;
                  }

                  var4 = -1;
                  var11 = null;

                  while (var1 < var5) {
                     Object var12;
                     List var13 = var14[var1];
                     int var6 = var16[var1];
                     var12 = var11;
                     var3 = var4;
                     label151:
                     if (var13.size() != var6) {
                        if (var11 == null) {
                           var11 = (Throwable)var13.get(var6);
                        } else {
                           Object var29 = var13.get(var6);

                           try {
                              var6 = this.comparator.compare((T)var11, (T)var29);
                           } catch (Throwable var18) {
                              Exceptions.throwIfFatal(var18);
                              this.cancelAll();
                              Arrays.fill(var14, null);
                              if (!ExternalSyntheticBackportWithForwarding0.m(this.error, null, var18)) {
                                 RxJavaPlugins.onError(var18);
                              }

                              var15.onError(this.error.get());
                              return;
                           }

                           var12 = var11;
                           var3 = var4;
                           if (var6 <= 0) {
                              break label151;
                           }

                           var11 = (Throwable)var29;
                        }

                        var3 = var1;
                        var12 = var11;
                     }

                     var1++;
                     var11 = (Throwable)var12;
                     var4 = var3;
                  }

                  if (var11 == null) {
                     Arrays.fill(var14, null);
                     var15.onComplete();
                     return;
                  }

                  var15.onNext(var11);
                  var16[var4]++;
                  var7++;
               }
            }
         }
      }

      void innerError(Throwable var1) {
         if (ExternalSyntheticBackportWithForwarding0.m(this.error, null, var1)) {
            this.drain();
         } else if (var1 != this.error.get()) {
            RxJavaPlugins.onError(var1);
         }
      }

      void innerNext(List<T> var1, int var2) {
         this.lists[var2] = var1;
         if (this.remaining.decrementAndGet() == 0) {
            this.drain();
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            if (this.remaining.get() == 0) {
               this.drain();
            }
         }
      }
   }
}
