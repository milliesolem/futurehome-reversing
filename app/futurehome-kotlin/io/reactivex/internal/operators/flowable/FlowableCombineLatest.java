package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableCombineLatest<T, R> extends Flowable<R> {
   final Publisher<? extends T>[] array;
   final int bufferSize;
   final Function<? super Object[], ? extends R> combiner;
   final boolean delayErrors;
   final Iterable<? extends Publisher<? extends T>> iterable;

   public FlowableCombineLatest(Iterable<? extends Publisher<? extends T>> var1, Function<? super Object[], ? extends R> var2, int var3, boolean var4) {
      this.array = null;
      this.iterable = var1;
      this.combiner = var2;
      this.bufferSize = var3;
      this.delayErrors = var4;
   }

   public FlowableCombineLatest(Publisher<? extends T>[] var1, Function<? super Object[], ? extends R> var2, int var3, boolean var4) {
      this.array = var1;
      this.iterable = null;
      this.combiner = var2;
      this.bufferSize = var3;
      this.delayErrors = var4;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Subscriber<? super R> var1) {
      Publisher[] var4 = this.array;
      int var2;
      if (var4 == null) {
         var4 = new Publisher[8];

         Iterator var6;
         try {
            var6 = ObjectHelper.requireNonNull(this.iterable.iterator(), "The iterator returned is null");
         } catch (Throwable var19) {
            Exceptions.throwIfFatal(var19);
            EmptySubscription.error(var19, var1);
            return;
         }

         var2 = 0;

         while (true) {
            boolean var3;
            try {
               var3 = var6.hasNext();
            } catch (Throwable var17) {
               Exceptions.throwIfFatal(var17);
               EmptySubscription.error(var17, var1);
               return;
            }

            if (!var3) {
               break;
            }

            Publisher var7;
            try {
               var7 = ObjectHelper.requireNonNull((Publisher)var6.next(), "The publisher returned by the iterator is null");
            } catch (Throwable var18) {
               Exceptions.throwIfFatal(var18);
               EmptySubscription.error(var18, var1);
               return;
            }

            Publisher[] var5 = var4;
            if (var2 == var4.length) {
               var5 = new Publisher[(var2 >> 2) + var2];
               System.arraycopy(var4, 0, var5, 0, var2);
            }

            var5[var2] = var7;
            var2++;
            var4 = var5;
         }
      } else {
         var2 = var4.length;
      }

      if (var2 == 0) {
         EmptySubscription.complete(var1);
      } else if (var2 == 1) {
         var4[0].subscribe(new FlowableMap.MapSubscriber<>(var1, new FlowableCombineLatest.SingletonArrayFunc(this)));
      } else {
         FlowableCombineLatest.CombineLatestCoordinator var20 = new FlowableCombineLatest.CombineLatestCoordinator<>(
            var1, this.combiner, var2, this.bufferSize, this.delayErrors
         );
         var1.onSubscribe(var20);
         var20.subscribe(var4, var2);
      }
   }

   static final class CombineLatestCoordinator<T, R> extends BasicIntQueueSubscription<R> {
      private static final long serialVersionUID = -5082275438355852221L;
      volatile boolean cancelled;
      final Function<? super Object[], ? extends R> combiner;
      int completedSources;
      final boolean delayErrors;
      volatile boolean done;
      final Subscriber<? super R> downstream;
      final AtomicReference<Throwable> error;
      final Object[] latest;
      int nonEmptySources;
      boolean outputFused;
      final SpscLinkedArrayQueue<Object> queue;
      final AtomicLong requested;
      final FlowableCombineLatest.CombineLatestInnerSubscriber<T>[] subscribers;

      CombineLatestCoordinator(Subscriber<? super R> var1, Function<? super Object[], ? extends R> var2, int var3, int var4, boolean var5) {
         this.downstream = var1;
         this.combiner = var2;
         FlowableCombineLatest.CombineLatestInnerSubscriber[] var7 = new FlowableCombineLatest.CombineLatestInnerSubscriber[var3];

         for (int var6 = 0; var6 < var3; var6++) {
            var7[var6] = new FlowableCombineLatest.CombineLatestInnerSubscriber<>(this, var6, var4);
         }

         this.subscribers = var7;
         this.latest = new Object[var3];
         this.queue = new SpscLinkedArrayQueue<>(var4);
         this.requested = new AtomicLong();
         this.error = new AtomicReference<>();
         this.delayErrors = var5;
      }

      public void cancel() {
         this.cancelled = true;
         this.cancelAll();
      }

      void cancelAll() {
         FlowableCombineLatest.CombineLatestInnerSubscriber[] var3 = this.subscribers;
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].cancel();
         }
      }

      boolean checkTerminated(boolean var1, boolean var2, Subscriber<?> var3, SpscLinkedArrayQueue<?> var4) {
         if (this.cancelled) {
            this.cancelAll();
            var4.clear();
            return true;
         } else {
            if (var1) {
               if (this.delayErrors) {
                  if (var2) {
                     this.cancelAll();
                     Throwable var6 = ExceptionHelper.terminate(this.error);
                     if (var6 != null && var6 != ExceptionHelper.TERMINATED) {
                        var3.onError(var6);
                     } else {
                        var3.onComplete();
                     }

                     return true;
                  }
               } else {
                  Throwable var5 = ExceptionHelper.terminate(this.error);
                  if (var5 != null && var5 != ExceptionHelper.TERMINATED) {
                     this.cancelAll();
                     var4.clear();
                     var3.onError(var5);
                     return true;
                  }

                  if (var2) {
                     this.cancelAll();
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
               this.drainOutput();
            } else {
               this.drainAsync();
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drainAsync() {
         Subscriber var9 = this.downstream;
         SpscLinkedArrayQueue var11 = this.queue;
         int var1 = 1;

         int var15;
         do {
            long var7 = this.requested.get();
            long var5 = 0L;

            while (true) {
               long var17;
               var15 = (var17 = var5 - var7) == 0L ? 0 : (var17 < 0L ? -1 : 1);
               if (!var15) {
                  break;
               }

               boolean var4 = this.done;
               Object var10 = var11.poll();
               boolean var3;
               if (var10 == null) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (this.checkTerminated(var4, var3, var9, var11)) {
                  return;
               }

               if (var3) {
                  break;
               }

               Object[] var12 = (Object[])var11.poll();

               try {
                  var12 = ObjectHelper.requireNonNull(this.combiner.apply(var12), "The combiner returned a null value");
               } catch (Throwable var14) {
                  Exceptions.throwIfFatal(var14);
                  this.cancelAll();
                  ExceptionHelper.addThrowable(this.error, var14);
                  var9.onError(ExceptionHelper.terminate(this.error));
                  return;
               }

               var9.onNext(var12);
               ((FlowableCombineLatest.CombineLatestInnerSubscriber)var10).requestOne();
               var5++;
            }

            if (!var15 && this.checkTerminated(this.done, var11.isEmpty(), var9, var11)) {
               return;
            }

            if (var5 != 0L && var7 != Long.MAX_VALUE) {
               this.requested.addAndGet(-var5);
            }

            var15 = this.addAndGet(-var1);
            var1 = var15;
         } while (var15 != 0);
      }

      void drainOutput() {
         Subscriber var6 = this.downstream;
         SpscLinkedArrayQueue var5 = this.queue;
         int var1 = 1;

         while (!this.cancelled) {
            Throwable var7 = this.error.get();
            if (var7 != null) {
               var5.clear();
               var6.onError(var7);
               return;
            }

            boolean var4 = this.done;
            boolean var3 = var5.isEmpty();
            if (!var3) {
               var6.onNext(null);
            }

            if (var4 && var3) {
               var6.onComplete();
               return;
            }

            int var2 = this.addAndGet(-var1);
            var1 = var2;
            if (var2 == 0) {
               return;
            }
         }

         var5.clear();
      }

      void innerComplete(int param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator.latest [Ljava/lang/Object;
         // 06: astore 2
         // 07: aload 2
         // 08: iload 1
         // 09: aaload
         // 0a: ifnull 2a
         // 0d: aload 0
         // 0e: getfield io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator.completedSources I
         // 11: bipush 1
         // 12: iadd
         // 13: istore 1
         // 14: iload 1
         // 15: aload 2
         // 16: arraylength
         // 17: if_icmpne 22
         // 1a: aload 0
         // 1b: bipush 1
         // 1c: putfield io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator.done Z
         // 1f: goto 2f
         // 22: aload 0
         // 23: iload 1
         // 24: putfield io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator.completedSources I
         // 27: aload 0
         // 28: monitorexit
         // 29: return
         // 2a: aload 0
         // 2b: bipush 1
         // 2c: putfield io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator.done Z
         // 2f: aload 0
         // 30: monitorexit
         // 31: aload 0
         // 32: invokevirtual io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator.drain ()V
         // 35: return
         // 36: astore 2
         // 37: aload 0
         // 38: monitorexit
         // 39: aload 2
         // 3a: athrow
      }

      void innerError(int var1, Throwable var2) {
         if (ExceptionHelper.addThrowable(this.error, var2)) {
            if (!this.delayErrors) {
               this.cancelAll();
               this.done = true;
               this.drain();
            } else {
               this.innerComplete(var1);
            }
         } else {
            RxJavaPlugins.onError(var2);
         }
      }

      void innerValue(int param1, T param2) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator.latest [Ljava/lang/Object;
         // 06: astore 5
         // 08: aload 0
         // 09: getfield io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator.nonEmptySources I
         // 0c: istore 4
         // 0e: iload 4
         // 10: istore 3
         // 11: aload 5
         // 13: iload 1
         // 14: aaload
         // 15: ifnonnull 22
         // 18: iload 4
         // 1a: bipush 1
         // 1b: iadd
         // 1c: istore 3
         // 1d: aload 0
         // 1e: iload 3
         // 1f: putfield io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator.nonEmptySources I
         // 22: aload 5
         // 24: iload 1
         // 25: aload 2
         // 26: aastore
         // 27: aload 5
         // 29: arraylength
         // 2a: iload 3
         // 2b: if_icmpne 46
         // 2e: aload 0
         // 2f: getfield io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 32: aload 0
         // 33: getfield io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator.subscribers [Lio/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestInnerSubscriber;
         // 36: iload 1
         // 37: aaload
         // 38: aload 5
         // 3a: invokevirtual [Ljava/lang/Object;.clone ()Ljava/lang/Object;
         // 3d: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;Ljava/lang/Object;)Z
         // 40: pop
         // 41: bipush 0
         // 42: istore 3
         // 43: goto 48
         // 46: bipush 1
         // 47: istore 3
         // 48: aload 0
         // 49: monitorexit
         // 4a: iload 3
         // 4b: ifeq 5a
         // 4e: aload 0
         // 4f: getfield io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator.subscribers [Lio/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestInnerSubscriber;
         // 52: iload 1
         // 53: aaload
         // 54: invokevirtual io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestInnerSubscriber.requestOne ()V
         // 57: goto 5e
         // 5a: aload 0
         // 5b: invokevirtual io/reactivex/internal/operators/flowable/FlowableCombineLatest$CombineLatestCoordinator.drain ()V
         // 5e: return
         // 5f: astore 2
         // 60: aload 0
         // 61: monitorexit
         // 62: aload 2
         // 63: athrow
      }

      @Override
      public boolean isEmpty() {
         return this.queue.isEmpty();
      }

      @Override
      public R poll() throws Exception {
         Object var1 = this.queue.poll();
         if (var1 == null) {
            return null;
         } else {
            Object[] var2 = (Object[])this.queue.poll();
            var2 = ObjectHelper.requireNonNull(this.combiner.apply(var2), "The combiner returned a null value");
            ((FlowableCombineLatest.CombineLatestInnerSubscriber)var1).requestOne();
            return (R)var2;
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
         boolean var2 = false;
         if ((var1 & 4) != 0) {
            return 0;
         } else {
            var1 &= 2;
            if (var1 != 0) {
               var2 = true;
            }

            this.outputFused = var2;
            return var1;
         }
      }

      void subscribe(Publisher<? extends T>[] var1, int var2) {
         FlowableCombineLatest.CombineLatestInnerSubscriber[] var4 = this.subscribers;

         for (int var3 = 0; var3 < var2 && !this.done && !this.cancelled; var3++) {
            var1[var3].subscribe(var4[var3]);
         }
      }
   }

   static final class CombineLatestInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -8730235182291002949L;
      final int index;
      final int limit;
      final FlowableCombineLatest.CombineLatestCoordinator<T, ?> parent;
      final int prefetch;
      int produced;

      CombineLatestInnerSubscriber(FlowableCombineLatest.CombineLatestCoordinator<T, ?> var1, int var2, int var3) {
         this.parent = var1;
         this.index = var2;
         this.prefetch = var3;
         this.limit = var3 - (var3 >> 2);
      }

      public void cancel() {
         SubscriptionHelper.cancel(this);
      }

      public void onComplete() {
         this.parent.innerComplete(this.index);
      }

      public void onError(Throwable var1) {
         this.parent.innerError(this.index, var1);
      }

      public void onNext(T var1) {
         this.parent.innerValue(this.index, (T)var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.setOnce(this, var1, this.prefetch);
      }

      public void requestOne() {
         int var1 = this.produced + 1;
         if (var1 == this.limit) {
            this.produced = 0;
            this.get().request(var1);
         } else {
            this.produced = var1;
         }
      }
   }

   final class SingletonArrayFunc implements Function<T, R> {
      final FlowableCombineLatest this$0;

      SingletonArrayFunc(FlowableCombineLatest var1) {
         this.this$0 = var1;
      }

      @Override
      public R apply(T var1) throws Exception {
         return (R)this.this$0.combiner.apply(new Object[]{var1});
      }
   }
}
