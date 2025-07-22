package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowablePublish<T> extends ConnectableFlowable<T> implements HasUpstreamPublisher<T>, FlowablePublishClassic<T> {
   static final long CANCELLED = Long.MIN_VALUE;
   final int bufferSize;
   final AtomicReference<FlowablePublish.PublishSubscriber<T>> current;
   final Publisher<T> onSubscribe;
   final Flowable<T> source;

   private FlowablePublish(Publisher<T> var1, Flowable<T> var2, AtomicReference<FlowablePublish.PublishSubscriber<T>> var3, int var4) {
      this.onSubscribe = var1;
      this.source = var2;
      this.current = var3;
      this.bufferSize = var4;
   }

   public static <T> ConnectableFlowable<T> create(Flowable<T> var0, int var1) {
      AtomicReference var2 = new AtomicReference();
      return RxJavaPlugins.onAssembly(new FlowablePublish<>(new FlowablePublish.FlowablePublisher<>(var2, var1), var0, var2, var1));
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void connect(Consumer<? super Disposable> var1) {
      FlowablePublish.PublishSubscriber var5;
      FlowablePublish.PublishSubscriber var6;
      do {
         var6 = this.current.get();
         if (var6 != null) {
            var5 = var6;
            if (!var6.isDisposed()) {
               break;
            }
         }

         var5 = new FlowablePublish.PublishSubscriber<>(this.current, this.bufferSize);
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.current, var6, var5));

      boolean var4 = var5.shouldConnect.get();
      boolean var3 = false;
      boolean var2 = var3;
      if (!var4) {
         var2 = var3;
         if (var5.shouldConnect.compareAndSet(false, true)) {
            var2 = true;
         }
      }

      try {
         var1.accept(var5);
      } catch (Throwable var8) {
         Exceptions.throwIfFatal(var8);
         throw ExceptionHelper.wrapOrThrow(var8);
      }

      if (var2) {
         this.source.subscribe(var5);
      }
   }

   @Override
   public int publishBufferSize() {
      return this.bufferSize;
   }

   @Override
   public Publisher<T> publishSource() {
      return this.source;
   }

   @Override
   public Publisher<T> source() {
      return this.source;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.onSubscribe.subscribe(var1);
   }

   static final class FlowablePublisher<T> implements Publisher<T> {
      private final int bufferSize;
      private final AtomicReference<FlowablePublish.PublishSubscriber<T>> curr;

      FlowablePublisher(AtomicReference<FlowablePublish.PublishSubscriber<T>> var1, int var2) {
         this.curr = var1;
         this.bufferSize = var2;
      }

      public void subscribe(Subscriber<? super T> var1) {
         FlowablePublish.InnerSubscriber var3 = new FlowablePublish.InnerSubscriber(var1);
         var1.onSubscribe(var3);

         FlowablePublish.PublishSubscriber var2;
         do {
            do {
               var2 = this.curr.get();
               if (var2 != null) {
                  var1 = var2;
                  if (!var2.isDisposed()) {
                     break;
                  }
               }

               var1 = new FlowablePublish.PublishSubscriber<>(this.curr, this.bufferSize);
            } while (!ExternalSyntheticBackportWithForwarding0.m(this.curr, var2, var1));
         } while (!var1.add(var3));

         if (var3.get() == Long.MIN_VALUE) {
            var1.remove(var3);
         } else {
            var3.parent = var1;
         }

         var1.dispatch();
      }
   }

   static final class InnerSubscriber<T> extends AtomicLong implements Subscription {
      private static final long serialVersionUID = -4453897557930727610L;
      final Subscriber<? super T> child;
      long emitted;
      volatile FlowablePublish.PublishSubscriber<T> parent;

      InnerSubscriber(Subscriber<? super T> var1) {
         this.child = var1;
      }

      public void cancel() {
         if (this.get() != Long.MIN_VALUE && this.getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
            FlowablePublish.PublishSubscriber var1 = this.parent;
            if (var1 != null) {
               var1.remove(this);
               var1.dispatch();
            }
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.addCancel(this, var1);
            FlowablePublish.PublishSubscriber var3 = this.parent;
            if (var3 != null) {
               var3.dispatch();
            }
         }
      }
   }

   static final class PublishSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Disposable {
      static final FlowablePublish.InnerSubscriber[] EMPTY = new FlowablePublish.InnerSubscriber[0];
      static final FlowablePublish.InnerSubscriber[] TERMINATED = new FlowablePublish.InnerSubscriber[0];
      private static final long serialVersionUID = -202316842419149694L;
      final int bufferSize;
      final AtomicReference<FlowablePublish.PublishSubscriber<T>> current;
      volatile SimpleQueue<T> queue;
      final AtomicBoolean shouldConnect;
      int sourceMode;
      final AtomicReference<FlowablePublish.InnerSubscriber<T>[]> subscribers;
      volatile Object terminalEvent;
      final AtomicReference<Subscription> upstream = new AtomicReference<>();

      PublishSubscriber(AtomicReference<FlowablePublish.PublishSubscriber<T>> var1, int var2) {
         this.subscribers = new AtomicReference<>(EMPTY);
         this.current = var1;
         this.shouldConnect = new AtomicBoolean();
         this.bufferSize = var2;
      }

      boolean add(FlowablePublish.InnerSubscriber<T> var1) {
         FlowablePublish.InnerSubscriber[] var3;
         FlowablePublish.InnerSubscriber[] var4;
         do {
            var4 = this.subscribers.get();
            if (var4 == TERMINATED) {
               return false;
            }

            int var2 = var4.length;
            var3 = new FlowablePublish.InnerSubscriber[var2 + 1];
            System.arraycopy(var4, 0, var3, 0, var2);
            var3[var2] = var1;
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var4, var3));

         return true;
      }

      boolean checkTerminated(Object var1, boolean var2) {
         byte var4 = 0;
         int var3 = 0;
         if (var1 != null) {
            if (!NotificationLite.isComplete(var1)) {
               Throwable var6 = NotificationLite.getError(var1);
               ExternalSyntheticBackportWithForwarding0.m(this.current, this, null);
               var1 = this.subscribers.getAndSet(TERMINATED);
               if (var1.length != 0) {
                  int var5 = var1.length;

                  for (int var9 = var4; var9 < var5; var9++) {
                     var1[var9].child.onError(var6);
                  }
               } else {
                  RxJavaPlugins.onError(var6);
               }

               return true;
            }

            if (var2) {
               ExternalSyntheticBackportWithForwarding0.m(this.current, this, null);
               var1 = this.subscribers.getAndSet(TERMINATED);

               for (int var10 = var1.length; var3 < var10; var3++) {
                  var1[var3].child.onComplete();
               }

               return true;
            }
         }

         return false;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void dispatch() {
         if (this.getAndIncrement() == 0) {
            AtomicReference var16 = this.subscribers;
            FlowablePublish.InnerSubscriber[] var12 = (FlowablePublish.InnerSubscriber[])var16.get();
            int var1 = 1;
            int var2 = 1;

            label324:
            while (true) {
               FlowablePublish.InnerSubscriber var14 = (FlowablePublish.InnerSubscriber)this.terminalEvent;
               SimpleQueue var13 = this.queue;
               boolean var7;
               if (var13 != null && !var13.isEmpty()) {
                  var7 = false;
               } else {
                  var7 = true;
               }

               if (this.checkTerminated(var14, var7)) {
                  return;
               }

               int var3 = var1;
               if (!var7) {
                  int var6 = var12.length;
                  int var5 = var12.length;
                  var3 = 0;
                  int var4 = 0;

                  long var8;
                  for (var8 = Long.MAX_VALUE; var3 < var5; var3++) {
                     var14 = var12[var3];
                     long var10 = var14.get();
                     if (var10 != Long.MIN_VALUE) {
                        var8 = Math.min(var8, var10 - var14.emitted);
                     } else {
                        var4++;
                     }
                  }

                  if (var6 == var4) {
                     var14 = (FlowablePublish.InnerSubscriber)this.terminalEvent;

                     label321:
                     try {
                        var33 = var13.poll();
                     } catch (Throwable var22) {
                        Exceptions.throwIfFatal(var22);
                        this.upstream.get().cancel();
                        var14 = (FlowablePublish.InnerSubscriber)NotificationLite.error(var22);
                        this.terminalEvent = var14;
                        var33 = null;
                        break label321;
                     }

                     if (var33 == null) {
                        var7 = true;
                     } else {
                        var7 = false;
                     }

                     if (this.checkTerminated(var14, var7)) {
                        return;
                     }

                     if (this.sourceMode != var1) {
                        this.upstream.get().request(1L);
                     }
                     continue;
                  }

                  var1 = 0;

                  while (true) {
                     long var31 = var1;
                     if (var31 < var8) {
                        var14 = (FlowablePublish.InnerSubscriber)this.terminalEvent;

                        Object var15;
                        label302:
                        try {
                           var15 = (FlowablePublish.InnerSubscriber)var13.poll();
                        } catch (Throwable var21) {
                           Exceptions.throwIfFatal(var21);
                           this.upstream.get().cancel();
                           var14 = (FlowablePublish.InnerSubscriber)NotificationLite.error(var21);
                           this.terminalEvent = var14;
                           var15 = null;
                           break label302;
                        }

                        if (var15 == null) {
                           var7 = true;
                        } else {
                           var7 = false;
                        }

                        if (this.checkTerminated(var14, var7)) {
                           return;
                        }

                        if (!var7) {
                           var14 = NotificationLite.getValue(var15);
                           var5 = var12.length;
                           var3 = 0;

                           for (var28 = false; var3 < var5; var3++) {
                              var15 = var12[var3];
                              var31 = var15.get();
                              if (var31 != Long.MIN_VALUE) {
                                 if (var31 != Long.MAX_VALUE) {
                                    var15.emitted++;
                                 }

                                 var15.child.onNext(var14);
                              } else {
                                 var28 = true;
                              }
                           }

                           var1++;
                           FlowablePublish.InnerSubscriber[] var37 = (FlowablePublish.InnerSubscriber[])var16.get();
                           if (!var28 && var37 == var12) {
                              continue;
                           }

                           if (var1 != 0 && this.sourceMode != 1) {
                              this.upstream.get().request(var1);
                           }

                           var12 = var37;
                           var1 = 1;
                           continue label324;
                        }
                     }

                     if (var1 != 0 && this.sourceMode != 1) {
                        this.upstream.get().request(var31);
                     }

                     byte var24 = 1;
                     byte var27 = 1;
                     var3 = var24;
                     if (var8 != 0L) {
                        var3 = var24;
                        if (!var7) {
                           var1 = var27;
                           continue label324;
                        }
                     }
                     break;
                  }
               }

               var2 = this.addAndGet(-var2);
               if (var2 == 0) {
                  return;
               }

               var12 = (FlowablePublish.InnerSubscriber[])var16.get();
               var1 = var3;
            }
         }
      }

      @Override
      public void dispose() {
         Object var1 = this.subscribers.get();
         FlowablePublish.InnerSubscriber[] var2 = TERMINATED;
         if (var1 != var2 && (FlowablePublish.InnerSubscriber[])this.subscribers.getAndSet(var2) != var2) {
            ExternalSyntheticBackportWithForwarding0.m(this.current, this, null);
            SubscriptionHelper.cancel(this.upstream);
         }
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.subscribers.get() == TERMINATED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void onComplete() {
         if (this.terminalEvent == null) {
            this.terminalEvent = NotificationLite.complete();
            this.dispatch();
         }
      }

      public void onError(Throwable var1) {
         if (this.terminalEvent == null) {
            this.terminalEvent = NotificationLite.error(var1);
            this.dispatch();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (this.sourceMode == 0 && !this.queue.offer((T)var1)) {
            this.onError(new MissingBackpressureException("Prefetch queue is full?!"));
         } else {
            this.dispatch();
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.setOnce(this.upstream, var1)) {
            if (var1 instanceof QueueSubscription) {
               QueueSubscription var3 = (QueueSubscription)var1;
               int var2 = var3.requestFusion(7);
               if (var2 == 1) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  this.terminalEvent = NotificationLite.complete();
                  this.dispatch();
                  return;
               }

               if (var2 == 2) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  var1.request(this.bufferSize);
                  return;
               }
            }

            this.queue = new SpscArrayQueue<>(this.bufferSize);
            var1.request(this.bufferSize);
         }
      }

      void remove(FlowablePublish.InnerSubscriber<T> var1) {
         while (true) {
            FlowablePublish.InnerSubscriber[] var5 = this.subscribers.get();
            int var3 = var5.length;
            if (var3 != 0) {
               int var2 = 0;

               while (true) {
                  if (var2 >= var3) {
                     var2 = -1;
                     break;
                  }

                  if (var5[var2].equals(var1)) {
                     break;
                  }

                  var2++;
               }

               if (var2 < 0) {
                  return;
               }

               FlowablePublish.InnerSubscriber[] var4;
               if (var3 == 1) {
                  var4 = EMPTY;
               } else {
                  var4 = new FlowablePublish.InnerSubscriber[var3 - 1];
                  System.arraycopy(var5, 0, var4, 0, var2);
                  System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
               }

               if (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, var4)) {
                  continue;
               }
            }

            return;
         }
      }
   }
}
