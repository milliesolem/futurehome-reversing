package io.reactivex.internal.util;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class QueueDrainHelper {
   static final long COMPLETED_MASK = Long.MIN_VALUE;
   static final long REQUESTED_MASK = Long.MAX_VALUE;

   private QueueDrainHelper() {
      throw new IllegalStateException("No instances!");
   }

   public static <T, U> boolean checkTerminated(
      boolean var0, boolean var1, Observer<?> var2, boolean var3, SimpleQueue<?> var4, Disposable var5, ObservableQueueDrain<T, U> var6
   ) {
      if (var6.cancelled()) {
         var4.clear();
         var5.dispose();
         return true;
      } else {
         if (var0) {
            if (var3) {
               if (var1) {
                  if (var5 != null) {
                     var5.dispose();
                  }

                  Throwable var7 = var6.error();
                  if (var7 != null) {
                     var2.onError(var7);
                  } else {
                     var2.onComplete();
                  }

                  return true;
               }
            } else {
               Throwable var8 = var6.error();
               if (var8 != null) {
                  var4.clear();
                  if (var5 != null) {
                     var5.dispose();
                  }

                  var2.onError(var8);
                  return true;
               }

               if (var1) {
                  if (var5 != null) {
                     var5.dispose();
                  }

                  var2.onComplete();
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static <T, U> boolean checkTerminated(boolean var0, boolean var1, Subscriber<?> var2, boolean var3, SimpleQueue<?> var4, QueueDrain<T, U> var5) {
      if (var5.cancelled()) {
         var4.clear();
         return true;
      } else {
         if (var0) {
            if (var3) {
               if (var1) {
                  Throwable var6 = var5.error();
                  if (var6 != null) {
                     var2.onError(var6);
                  } else {
                     var2.onComplete();
                  }

                  return true;
               }
            } else {
               Throwable var7 = var5.error();
               if (var7 != null) {
                  var4.clear();
                  var2.onError(var7);
                  return true;
               }

               if (var1) {
                  var2.onComplete();
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static <T> SimpleQueue<T> createQueue(int var0) {
      return (SimpleQueue<T>)(var0 < 0 ? new SpscLinkedArrayQueue<>(-var0) : new SpscArrayQueue<>(var0));
   }

   public static <T, U> void drainLoop(SimplePlainQueue<T> var0, Observer<? super U> var1, boolean var2, Disposable var3, ObservableQueueDrain<T, U> var4) {
      int var5 = 1;

      while (!checkTerminated(var4.done(), var0.isEmpty(), var1, var2, var0, var3, var4)) {
         boolean var8 = var4.done();
         Object var9 = var0.poll();
         boolean var7;
         if (var9 == null) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (checkTerminated(var8, var7, var1, var2, var0, var3, var4)) {
            return;
         }

         if (var7) {
            int var6 = var4.leave(-var5);
            var5 = var6;
            if (var6 == 0) {
               return;
            }
         } else {
            var4.accept(var1, var9);
         }
      }
   }

   public static <T, U> void drainMaxLoop(SimplePlainQueue<T> var0, Subscriber<? super U> var1, boolean var2, Disposable var3, QueueDrain<T, U> var4) {
      int var5 = 1;

      while (true) {
         boolean var8 = var4.done();
         Object var11 = var0.poll();
         boolean var7;
         if (var11 == null) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (checkTerminated(var8, var7, var1, var2, var0, var4)) {
            if (var3 != null) {
               var3.dispose();
            }

            return;
         }

         if (var7) {
            int var6 = var4.leave(-var5);
            var5 = var6;
            if (var6 == 0) {
               return;
            }
         } else {
            long var9 = var4.requested();
            if (var9 == 0L) {
               var0.clear();
               if (var3 != null) {
                  var3.dispose();
               }

               var1.onError(new MissingBackpressureException("Could not emit value due to lack of requests."));
               return;
            }

            if (var4.accept(var1, var11) && var9 != Long.MAX_VALUE) {
               var4.produced(1L);
            }
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static boolean isCancelled(BooleanSupplier var0) {
      try {
         return var0.getAsBoolean();
      } catch (Throwable var3) {
         Exceptions.throwIfFatal(var3);
         return true;
      }
   }

   public static <T> void postComplete(Subscriber<? super T> var0, Queue<T> var1, AtomicLong var2, BooleanSupplier var3) {
      if (var1.isEmpty()) {
         var0.onComplete();
      } else if (!postCompleteDrain(var2.get(), var0, var1, var2, var3)) {
         long var4;
         long var6;
         do {
            var4 = var2.get();
            if ((var4 & Long.MIN_VALUE) != 0L) {
               return;
            }

            var6 = var4 | Long.MIN_VALUE;
         } while (!var2.compareAndSet(var4, var6));

         if (var4 != 0L) {
            postCompleteDrain(var6, var0, var1, var2, var3);
         }
      }
   }

   static <T> boolean postCompleteDrain(long var0, Subscriber<? super T> var2, Queue<T> var3, AtomicLong var4, BooleanSupplier var5) {
      long var6 = var0 & Long.MIN_VALUE;

      while (true) {
         while (var6 == var0) {
            if (isCancelled(var5)) {
               return true;
            }

            if (var3.isEmpty()) {
               var2.onComplete();
               return true;
            }

            long var8 = var4.get();
            var0 = var8;
            if (var8 == var6) {
               var6 = var4.addAndGet(-(var6 & Long.MAX_VALUE));
               if ((Long.MAX_VALUE & var6) == 0L) {
                  return false;
               }

               var0 = var6;
               var6 &= Long.MIN_VALUE;
            }
         }

         if (isCancelled(var5)) {
            return true;
         }

         Object var10 = var3.poll();
         if (var10 == null) {
            var2.onComplete();
            return true;
         }

         var2.onNext(var10);
         var6++;
      }
   }

   public static <T> boolean postCompleteRequest(long var0, Subscriber<? super T> var2, Queue<T> var3, AtomicLong var4, BooleanSupplier var5) {
      long var6;
      do {
         var6 = var4.get();
      } while (!var4.compareAndSet(var6, BackpressureHelper.addCap(Long.MAX_VALUE & var6, var0) | var6 & Long.MIN_VALUE));

      if (var6 == Long.MIN_VALUE) {
         postCompleteDrain(var0 | Long.MIN_VALUE, var2, var3, var4, var5);
         return true;
      } else {
         return false;
      }
   }

   public static void request(Subscription var0, int var1) {
      long var2;
      if (var1 < 0) {
         var2 = Long.MAX_VALUE;
      } else {
         var2 = var1;
      }

      var0.request(var2);
   }
}
