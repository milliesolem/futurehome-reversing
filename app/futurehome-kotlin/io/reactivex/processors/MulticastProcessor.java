package io.reactivex.processors;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@BackpressureSupport(BackpressureKind.FULL)
@SchedulerSupport("none")
public final class MulticastProcessor<T> extends FlowableProcessor<T> {
   static final MulticastProcessor.MulticastSubscription[] EMPTY = new MulticastProcessor.MulticastSubscription[0];
   static final MulticastProcessor.MulticastSubscription[] TERMINATED = new MulticastProcessor.MulticastSubscription[0];
   final int bufferSize;
   int consumed;
   volatile boolean done;
   volatile Throwable error;
   int fusionMode;
   final int limit;
   final AtomicBoolean once;
   volatile SimpleQueue<T> queue;
   final boolean refcount;
   final AtomicReference<MulticastProcessor.MulticastSubscription<T>[]> subscribers;
   final AtomicReference<Subscription> upstream;
   final AtomicInteger wip;

   MulticastProcessor(int var1, boolean var2) {
      ObjectHelper.verifyPositive(var1, "bufferSize");
      this.bufferSize = var1;
      this.limit = var1 - (var1 >> 2);
      this.wip = new AtomicInteger();
      this.subscribers = new AtomicReference<>(EMPTY);
      this.upstream = new AtomicReference<>();
      this.refcount = var2;
      this.once = new AtomicBoolean();
   }

   @CheckReturnValue
   public static <T> MulticastProcessor<T> create() {
      return new MulticastProcessor<>(bufferSize(), false);
   }

   @CheckReturnValue
   public static <T> MulticastProcessor<T> create(int var0) {
      return new MulticastProcessor<>(var0, false);
   }

   @CheckReturnValue
   public static <T> MulticastProcessor<T> create(int var0, boolean var1) {
      return new MulticastProcessor<>(var0, var1);
   }

   @CheckReturnValue
   public static <T> MulticastProcessor<T> create(boolean var0) {
      return new MulticastProcessor<>(bufferSize(), var0);
   }

   boolean add(MulticastProcessor.MulticastSubscription<T> var1) {
      MulticastProcessor.MulticastSubscription[] var3;
      MulticastProcessor.MulticastSubscription[] var4;
      do {
         var4 = this.subscribers.get();
         if (var4 == TERMINATED) {
            return false;
         }

         int var2 = var4.length;
         var3 = new MulticastProcessor.MulticastSubscription[var2 + 1];
         System.arraycopy(var4, 0, var3, 0, var2);
         var3[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var4, var3));

      return true;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   void drain() {
      if (this.wip.getAndIncrement() == 0) {
         AtomicReference var15 = this.subscribers;
         int var1 = this.consumed;
         int var6 = this.limit;
         int var5 = this.fusionMode;
         int var2 = 1;

         while (true) {
            int var3;
            label230:
            while (true) {
               SimpleQueue var16 = this.queue;
               var3 = var1;
               if (var16 == null) {
                  break;
               }

               MulticastProcessor.MulticastSubscription[] var17 = (MulticastProcessor.MulticastSubscription[])var15.get();
               var3 = var1;
               if (var17.length == 0) {
                  break;
               }

               int var4 = var17.length;
               long var7 = -1L;
               var3 = 0;

               while (var3 < var4) {
                  MulticastProcessor.MulticastSubscription var14 = var17[var3];
                  long var11 = var14.get();
                  long var9 = var7;
                  if (var11 >= 0L) {
                     if (var7 == -1L) {
                        var9 = var11 - var14.emitted;
                     } else {
                        var9 = Math.min(var7, var11 - var14.emitted);
                     }
                  }

                  var3++;
                  var7 = var9;
               }

               while (true) {
                  long var46;
                  var4 = (var46 = var7 - 0L) == 0L ? 0 : (var46 < 0L ? -1 : 1);
                  if (var4 <= 0) {
                     break;
                  }

                  MulticastProcessor.MulticastSubscription[] var37 = (MulticastProcessor.MulticastSubscription[])var15.get();
                  if (var37 == TERMINATED) {
                     var16.clear();
                     return;
                  }

                  if (var17 != var37) {
                     continue label230;
                  }

                  boolean var13 = this.done;

                  label219:
                  try {
                     var37 = (MulticastProcessor.MulticastSubscription[])var16.poll();
                  } catch (Throwable var20) {
                     Exceptions.throwIfFatal(var20);
                     SubscriptionHelper.cancel(this.upstream);
                     this.error = var20;
                     this.done = true;
                     var37 = null;
                     var13 = true;
                     break label219;
                  }

                  boolean var30;
                  if (var37 == null) {
                     var30 = 1;
                  } else {
                     var30 = 0;
                  }

                  if (var13 && var30) {
                     Throwable var39 = this.error;
                     if (var39 != null) {
                        MulticastProcessor.MulticastSubscription[] var44 = var15.getAndSet(TERMINATED);
                        var2 = var44.length;

                        for (int var21 = 0; var21 < var2; var21++) {
                           var44[var21].onError(var39);
                        }
                     } else {
                        var37 = var15.getAndSet(TERMINATED);
                        var2 = var37.length;

                        for (int var22 = 0; var22 < var2; var22++) {
                           var37[var22].onComplete();
                        }
                     }

                     return;
                  }

                  if (var30) {
                     break;
                  }

                  var4 = var17.length;

                  for (int var31 = 0; var31 < var4; var31++) {
                     var17[var31].onNext(var37);
                  }

                  long var36 = var7 - 1L;
                  var7 = var36;
                  if (var5 != 1) {
                     var30 = var1 + 1;
                     var1 = var30;
                     var7 = var36;
                     if (var30 == var6) {
                        this.upstream.get().request(var6);
                        var1 = 0;
                        var7 = var36;
                     }
                  }
               }

               if (var4 == 0) {
                  MulticastProcessor.MulticastSubscription[] var18 = (MulticastProcessor.MulticastSubscription[])var15.get();
                  MulticastProcessor.MulticastSubscription[] var41 = TERMINATED;
                  if (var18 == var41) {
                     var16.clear();
                     return;
                  }

                  if (var17 != var18) {
                     continue;
                  }

                  if (this.done && var16.isEmpty()) {
                     Throwable var45 = this.error;
                     if (var45 != null) {
                        var41 = var15.getAndSet(var41);
                        var2 = var41.length;

                        for (int var23 = 0; var23 < var2; var23++) {
                           var41[var23].onError(var45);
                        }
                     } else {
                        var41 = var15.getAndSet(var41);
                        var2 = var41.length;

                        for (int var24 = 0; var24 < var2; var24++) {
                           var41[var24].onComplete();
                        }
                     }

                     return;
                  }
               }

               var3 = var1;
               break;
            }

            this.consumed = var3;
            int var35 = this.wip.addAndGet(-var2);
            var1 = var3;
            var2 = var35;
            if (var35 == 0) {
               return;
            }
         }
      }
   }

   @Override
   public Throwable getThrowable() {
      Throwable var1;
      if (this.once.get()) {
         var1 = this.error;
      } else {
         var1 = null;
      }

      return var1;
   }

   @Override
   public boolean hasComplete() {
      boolean var1;
      if (this.once.get() && this.error == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasSubscribers() {
      boolean var1;
      if (((MulticastProcessor.MulticastSubscription[])this.subscribers.get()).length != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasThrowable() {
      boolean var1;
      if (this.once.get() && this.error != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean offer(T var1) {
      if (this.once.get()) {
         return false;
      } else {
         ObjectHelper.requireNonNull(var1, "offer called with null. Null values are generally not allowed in 2.x operators and sources.");
         if (this.fusionMode == 0 && this.queue.offer((T)var1)) {
            this.drain();
            return true;
         } else {
            return false;
         }
      }
   }

   public void onComplete() {
      if (this.once.compareAndSet(false, true)) {
         this.done = true;
         this.drain();
      }
   }

   public void onError(Throwable var1) {
      ObjectHelper.requireNonNull(var1, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      if (this.once.compareAndSet(false, true)) {
         this.error = var1;
         this.done = true;
         this.drain();
      } else {
         RxJavaPlugins.onError(var1);
      }
   }

   public void onNext(T var1) {
      if (!this.once.get()) {
         if (this.fusionMode == 0) {
            ObjectHelper.requireNonNull(var1, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
            if (!this.queue.offer((T)var1)) {
               SubscriptionHelper.cancel(this.upstream);
               this.onError(new MissingBackpressureException());
               return;
            }
         }

         this.drain();
      }
   }

   @Override
   public void onSubscribe(Subscription var1) {
      if (SubscriptionHelper.setOnce(this.upstream, var1)) {
         if (var1 instanceof QueueSubscription) {
            QueueSubscription var3 = (QueueSubscription)var1;
            int var2 = var3.requestFusion(3);
            if (var2 == 1) {
               this.fusionMode = var2;
               this.queue = var3;
               this.done = true;
               this.drain();
               return;
            }

            if (var2 == 2) {
               this.fusionMode = var2;
               this.queue = var3;
               var1.request(this.bufferSize);
               return;
            }
         }

         this.queue = new SpscArrayQueue<>(this.bufferSize);
         var1.request(this.bufferSize);
      }
   }

   void remove(MulticastProcessor.MulticastSubscription<T> var1) {
      while (true) {
         MulticastProcessor.MulticastSubscription[] var5 = this.subscribers.get();
         int var3 = var5.length;
         if (var3 == 0) {
            return;
         }

         int var2 = 0;

         while (true) {
            if (var2 >= var3) {
               var2 = -1;
               break;
            }

            if (var5[var2] == var1) {
               break;
            }

            var2++;
         }

         if (var2 >= 0) {
            if (var3 == 1) {
               if (this.refcount) {
                  if (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, TERMINATED)) {
                     continue;
                  }

                  SubscriptionHelper.cancel(this.upstream);
                  this.once.set(true);
               } else if (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, EMPTY)) {
                  continue;
               }
            } else {
               MulticastProcessor.MulticastSubscription[] var4 = new MulticastProcessor.MulticastSubscription[var3 - 1];
               System.arraycopy(var5, 0, var4, 0, var2);
               System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
               if (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, var4)) {
                  continue;
               }
            }
         }

         return;
      }
   }

   public void start() {
      if (SubscriptionHelper.setOnce(this.upstream, EmptySubscription.INSTANCE)) {
         this.queue = new SpscArrayQueue<>(this.bufferSize);
      }
   }

   public void startUnbounded() {
      if (SubscriptionHelper.setOnce(this.upstream, EmptySubscription.INSTANCE)) {
         this.queue = new SpscLinkedArrayQueue<>(this.bufferSize);
      }
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      MulticastProcessor.MulticastSubscription var2 = new MulticastProcessor.MulticastSubscription<>(var1, this);
      var1.onSubscribe(var2);
      if (this.add(var2)) {
         if (var2.get() == Long.MIN_VALUE) {
            this.remove(var2);
         } else {
            this.drain();
         }
      } else {
         if (this.once.get() || !this.refcount) {
            Throwable var3 = this.error;
            if (var3 != null) {
               var1.onError(var3);
               return;
            }
         }

         var1.onComplete();
      }
   }

   static final class MulticastSubscription<T> extends AtomicLong implements Subscription {
      private static final long serialVersionUID = -363282618957264509L;
      final Subscriber<? super T> downstream;
      long emitted;
      final MulticastProcessor<T> parent;

      MulticastSubscription(Subscriber<? super T> var1, MulticastProcessor<T> var2) {
         this.downstream = var1;
         this.parent = var2;
      }

      public void cancel() {
         if (this.getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
            this.parent.remove(this);
         }
      }

      void onComplete() {
         if (this.get() != Long.MIN_VALUE) {
            this.downstream.onComplete();
         }
      }

      void onError(Throwable var1) {
         if (this.get() != Long.MIN_VALUE) {
            this.downstream.onError(var1);
         }
      }

      void onNext(T var1) {
         if (this.get() != Long.MIN_VALUE) {
            this.emitted++;
            this.downstream.onNext(var1);
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            while (true) {
               long var7 = this.get();
               if (var7 == Long.MIN_VALUE) {
                  break;
               }

               long var3 = Long.MAX_VALUE;
               if (var7 == Long.MAX_VALUE) {
                  break;
               }

               long var5 = var7 + var1;
               if (var5 >= 0L) {
                  var3 = var5;
               }

               if (this.compareAndSet(var7, var3)) {
                  this.parent.drain();
                  break;
               }
            }
         }
      }
   }
}
