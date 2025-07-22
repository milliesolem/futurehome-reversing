package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableZip<T, R> extends Flowable<R> {
   final int bufferSize;
   final boolean delayError;
   final Publisher<? extends T>[] sources;
   final Iterable<? extends Publisher<? extends T>> sourcesIterable;
   final Function<? super Object[], ? extends R> zipper;

   public FlowableZip(
      Publisher<? extends T>[] var1, Iterable<? extends Publisher<? extends T>> var2, Function<? super Object[], ? extends R> var3, int var4, boolean var5
   ) {
      this.sources = var1;
      this.sourcesIterable = var2;
      this.zipper = var3;
      this.bufferSize = var4;
      this.delayError = var5;
   }

   @Override
   public void subscribeActual(Subscriber<? super R> var1) {
      Publisher[] var5 = this.sources;
      int var3;
      if (var5 == null) {
         Publisher[] var4 = new Publisher[8];
         Iterator var6 = this.sourcesIterable.iterator();
         int var2 = 0;

         while (true) {
            var5 = var4;
            var3 = var2;
            if (!var6.hasNext()) {
               break;
            }

            Publisher var7 = (Publisher)var6.next();
            var5 = var4;
            if (var2 == var4.length) {
               var5 = new Publisher[(var2 >> 2) + var2];
               System.arraycopy(var4, 0, var5, 0, var2);
            }

            var5[var2] = var7;
            var2++;
            var4 = var5;
         }
      } else {
         var3 = var5.length;
      }

      if (var3 == 0) {
         EmptySubscription.complete(var1);
      } else {
         FlowableZip.ZipCoordinator var8 = new FlowableZip.ZipCoordinator<>(var1, this.zipper, var3, this.bufferSize, this.delayError);
         var1.onSubscribe(var8);
         var8.subscribe(var5, var3);
      }
   }

   static final class ZipCoordinator<T, R> extends AtomicInteger implements Subscription {
      private static final long serialVersionUID = -2434867452883857743L;
      volatile boolean cancelled;
      final Object[] current;
      final boolean delayErrors;
      final Subscriber<? super R> downstream;
      final AtomicThrowable errors;
      final AtomicLong requested;
      final FlowableZip.ZipSubscriber<T, R>[] subscribers;
      final Function<? super Object[], ? extends R> zipper;

      ZipCoordinator(Subscriber<? super R> var1, Function<? super Object[], ? extends R> var2, int var3, int var4, boolean var5) {
         this.downstream = var1;
         this.zipper = var2;
         this.delayErrors = var5;
         FlowableZip.ZipSubscriber[] var7 = new FlowableZip.ZipSubscriber[var3];

         for (int var6 = 0; var6 < var3; var6++) {
            var7[var6] = new FlowableZip.ZipSubscriber<>(this, var4);
         }

         this.current = new Object[var3];
         this.subscribers = var7;
         this.requested = new AtomicLong();
         this.errors = new AtomicThrowable();
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.cancelAll();
         }
      }

      void cancelAll() {
         FlowableZip.ZipSubscriber[] var3 = this.subscribers;
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].cancel();
         }
      }

      // $VF: Handled exception range with multiple entry points by splitting it
      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            Subscriber var13 = this.downstream;
            FlowableZip.ZipSubscriber[] var15 = this.subscribers;
            int var5 = var15.length;
            Object[] var14 = this.current;
            int var1 = 1;

            while (true) {
               long var10 = this.requested.get();
               long var8 = 0L;

               label1718: {
                  int var6;
                  while (true) {
                     long var161;
                     var6 = (var161 = var10 - var8) == 0L ? 0 : (var161 < 0L ? -1 : 1);
                     if (!var6) {
                        break;
                     }

                     if (this.cancelled) {
                        return;
                     }

                     if (!this.delayErrors && this.errors.get() != null) {
                        this.cancelAll();
                        var13.onError(this.errors.terminate());
                        return;
                     }

                     boolean var3 = false;
                     int var2 = 0;

                     while (var2 < var5) {
                        boolean var4;
                        FlowableZip.ZipSubscriber var12 = var15[var2];
                        var4 = var3;
                        label1705:
                        if (var14[var2] == null) {
                           label1721: {
                              boolean var7;
                              try {
                                 var7 = var12.done;
                                 var155 = var12.queue;
                              } catch (Throwable var147) {
                                 Exceptions.throwIfFatal(var147);
                                 this.errors.addThrowable(var147);
                                 if (!this.delayErrors) {
                                    break label1718;
                                 }
                                 break label1721;
                              }

                              Object var156;
                              if (var155 != null) {
                                 try {
                                    var156 = var155.poll();
                                 } catch (Throwable var146) {
                                    Exceptions.throwIfFatal(var146);
                                    this.errors.addThrowable(var146);
                                    if (!this.delayErrors) {
                                       break label1718;
                                    }
                                    break label1721;
                                 }
                              } else {
                                 var156 = null;
                              }

                              if (var156 == null) {
                                 var4 = true;
                              } else {
                                 var4 = false;
                              }

                              label1694:
                              if (var7 && var4) {
                                 label1690: {
                                    try {
                                       this.cancelAll();
                                       if (this.errors.get() != null) {
                                          var13.onError(this.errors.terminate());
                                          break label1690;
                                       }
                                    } catch (Throwable var145) {
                                       Exceptions.throwIfFatal(var145);
                                       this.errors.addThrowable(var145);
                                       if (!this.delayErrors) {
                                          break label1718;
                                       }
                                       break label1694;
                                    }

                                    try {
                                       var13.onComplete();
                                    } catch (Throwable var144) {
                                       Exceptions.throwIfFatal(var144);
                                       this.errors.addThrowable(var144);
                                       if (!this.delayErrors) {
                                          break label1718;
                                       }
                                       break label1694;
                                    }
                                 }

                                 label1680:
                                 try {
                                    return;
                                 } catch (Throwable var143) {
                                    Exceptions.throwIfFatal(var143);
                                    this.errors.addThrowable(var143);
                                    if (!this.delayErrors) {
                                       break label1718;
                                    }
                                    break label1680;
                                 }
                              } else if (!var4) {
                                 var14[var2] = var156;
                                 var4 = var3;
                                 break label1705;
                              }
                           }

                           var4 = true;
                        }

                        var2++;
                        var3 = var4;
                     }

                     if (var3) {
                        break;
                     }

                     Object var157;
                     try {
                        var157 = ObjectHelper.requireNonNull(this.zipper.apply((Object[])var14.clone()), "The zipper returned a null value");
                     } catch (Throwable var137) {
                        Exceptions.throwIfFatal(var137);
                        this.cancelAll();
                        this.errors.addThrowable(var137);
                        var13.onError(this.errors.terminate());
                        return;
                     }

                     var13.onNext(var157);
                     var8++;
                     Arrays.fill(var14, null);
                  }

                  label1729: {
                     if (!var6) {
                        if (this.cancelled) {
                           return;
                        }

                        if (!this.delayErrors && this.errors.get() != null) {
                           this.cancelAll();
                           var13.onError(this.errors.terminate());
                           return;
                        }

                        for (int var148 = 0; var148 < var5; var148++) {
                           FlowableZip.ZipSubscriber var158 = var15[var148];
                           if (var14[var148] == null) {
                              boolean var154;
                              try {
                                 var154 = var158.done;
                                 var159 = var158.queue;
                              } catch (Throwable var142) {
                                 Exceptions.throwIfFatal(var142);
                                 this.errors.addThrowable(var142);
                                 if (!this.delayErrors) {
                                    break label1729;
                                 }
                                 continue;
                              }

                              Object var160;
                              if (var159 != null) {
                                 try {
                                    var160 = var159.poll();
                                 } catch (Throwable var141) {
                                    Exceptions.throwIfFatal(var141);
                                    this.errors.addThrowable(var141);
                                    if (!this.delayErrors) {
                                       break label1729;
                                    }
                                    continue;
                                 }
                              } else {
                                 var160 = null;
                              }

                              boolean var151;
                              if (var160 == null) {
                                 var151 = true;
                              } else {
                                 var151 = false;
                              }

                              if (var154 && var151) {
                                 label1644: {
                                    try {
                                       this.cancelAll();
                                       if (this.errors.get() != null) {
                                          var13.onError(this.errors.terminate());
                                          break label1644;
                                       }
                                    } catch (Throwable var140) {
                                       Exceptions.throwIfFatal(var140);
                                       this.errors.addThrowable(var140);
                                       if (!this.delayErrors) {
                                          break label1729;
                                       }
                                       continue;
                                    }

                                    try {
                                       var13.onComplete();
                                    } catch (Throwable var139) {
                                       Exceptions.throwIfFatal(var139);
                                       this.errors.addThrowable(var139);
                                       if (!this.delayErrors) {
                                          break label1729;
                                       }
                                       continue;
                                    }
                                 }

                                 try {
                                    return;
                                 } catch (Throwable var138) {
                                    Exceptions.throwIfFatal(var138);
                                    this.errors.addThrowable(var138);
                                    if (!this.delayErrors) {
                                       break label1729;
                                    }
                                    continue;
                                 }
                              } else if (!var151) {
                                 var14[var148] = var160;
                              }
                           }
                        }
                     }

                     if (var8 != 0L) {
                        int var152 = var15.length;

                        for (int var149 = 0; var149 < var152; var149++) {
                           var15[var149].request(var8);
                        }

                        if (var10 != Long.MAX_VALUE) {
                           this.requested.addAndGet(-var8);
                        }
                     }

                     int var150 = this.addAndGet(-var1);
                     var1 = var150;
                     if (var150 == 0) {
                        return;
                     }
                     continue;
                  }

                  this.cancelAll();
                  var13.onError(this.errors.terminate());
                  return;
               }

               this.cancelAll();
               var13.onError(this.errors.terminate());
               return;
            }
         }
      }

      void error(FlowableZip.ZipSubscriber<T, R> var1, Throwable var2) {
         if (this.errors.addThrowable(var2)) {
            var1.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var2);
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.drain();
         }
      }

      void subscribe(Publisher<? extends T>[] var1, int var2) {
         FlowableZip.ZipSubscriber[] var4 = this.subscribers;

         for (int var3 = 0; var3 < var2 && !this.cancelled && (this.delayErrors || this.errors.get() == null); var3++) {
            var1[var3].subscribe(var4[var3]);
         }
      }
   }

   static final class ZipSubscriber<T, R> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = -4627193790118206028L;
      volatile boolean done;
      final int limit;
      final FlowableZip.ZipCoordinator<T, R> parent;
      final int prefetch;
      long produced;
      SimpleQueue<T> queue;
      int sourceMode;

      ZipSubscriber(FlowableZip.ZipCoordinator<T, R> var1, int var2) {
         this.parent = var1;
         this.prefetch = var2;
         this.limit = var2 - (var2 >> 2);
      }

      public void cancel() {
         SubscriptionHelper.cancel(this);
      }

      public void onComplete() {
         this.done = true;
         this.parent.drain();
      }

      public void onError(Throwable var1) {
         this.parent.error(this, var1);
      }

      public void onNext(T var1) {
         if (this.sourceMode != 2) {
            this.queue.offer((T)var1);
         }

         this.parent.drain();
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.setOnce(this, var1)) {
            if (var1 instanceof QueueSubscription) {
               QueueSubscription var3 = (QueueSubscription)var1;
               int var2 = var3.requestFusion(7);
               if (var2 == 1) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  this.done = true;
                  this.parent.drain();
                  return;
               }

               if (var2 == 2) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  var1.request(this.prefetch);
                  return;
               }
            }

            this.queue = new SpscArrayQueue<>(this.prefetch);
            var1.request(this.prefetch);
         }
      }

      public void request(long var1) {
         if (this.sourceMode != 1) {
            var1 = this.produced + var1;
            if (var1 >= this.limit) {
               this.produced = 0L;
               this.get().request(var1);
            } else {
               this.produced = var1;
            }
         }
      }
   }
}
