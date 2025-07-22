package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AbstractFlowableWithUpstream<TLeft, R> {
   final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
   final Publisher<? extends TRight> other;
   final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
   final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;

   public FlowableJoin(
      Flowable<TLeft> var1,
      Publisher<? extends TRight> var2,
      Function<? super TLeft, ? extends Publisher<TLeftEnd>> var3,
      Function<? super TRight, ? extends Publisher<TRightEnd>> var4,
      BiFunction<? super TLeft, ? super TRight, ? extends R> var5
   ) {
      super(var1);
      this.other = var2;
      this.leftEnd = var3;
      this.rightEnd = var4;
      this.resultSelector = var5;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      FlowableJoin.JoinSubscription var2 = new FlowableJoin.JoinSubscription<>(var1, this.leftEnd, this.rightEnd, this.resultSelector);
      var1.onSubscribe(var2);
      FlowableGroupJoin.LeftRightSubscriber var4 = new FlowableGroupJoin.LeftRightSubscriber(var2, true);
      var2.disposables.add(var4);
      FlowableGroupJoin.LeftRightSubscriber var3 = new FlowableGroupJoin.LeftRightSubscriber(var2, false);
      var2.disposables.add(var3);
      this.source.subscribe(var4);
      this.other.subscribe(var3);
   }

   static final class JoinSubscription<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements Subscription, FlowableGroupJoin.JoinSupport {
      static final Integer LEFT_CLOSE = 3;
      static final Integer LEFT_VALUE = 1;
      static final Integer RIGHT_CLOSE = 4;
      static final Integer RIGHT_VALUE = 2;
      private static final long serialVersionUID = -6071216598687999801L;
      final AtomicInteger active;
      volatile boolean cancelled;
      final CompositeDisposable disposables;
      final Subscriber<? super R> downstream;
      final AtomicReference<Throwable> error;
      final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
      int leftIndex;
      final Map<Integer, TLeft> lefts;
      final SpscLinkedArrayQueue<Object> queue;
      final AtomicLong requested;
      final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
      final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
      int rightIndex;
      final Map<Integer, TRight> rights;

      JoinSubscription(
         Subscriber<? super R> var1,
         Function<? super TLeft, ? extends Publisher<TLeftEnd>> var2,
         Function<? super TRight, ? extends Publisher<TRightEnd>> var3,
         BiFunction<? super TLeft, ? super TRight, ? extends R> var4
      ) {
         this.downstream = var1;
         this.requested = new AtomicLong();
         this.disposables = new CompositeDisposable();
         this.queue = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
         this.lefts = new LinkedHashMap<>();
         this.rights = new LinkedHashMap<>();
         this.error = new AtomicReference<>();
         this.leftEnd = var2;
         this.rightEnd = var3;
         this.resultSelector = var4;
         this.active = new AtomicInteger(2);
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.cancelAll();
            if (this.getAndIncrement() == 0) {
               this.queue.clear();
            }
         }
      }

      void cancelAll() {
         this.disposables.dispose();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            SpscLinkedArrayQueue var8 = this.queue;
            Subscriber var9 = this.downstream;
            int var1 = 1;

            while (!this.cancelled) {
               if (this.error.get() != null) {
                  var8.clear();
                  this.cancelAll();
                  this.errorAll(var9);
                  return;
               }

               boolean var2;
               if (this.active.get() == 0) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               Integer var11 = (Integer)var8.poll();
               boolean var3;
               if (var11 == null) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (var2 && var3) {
                  this.lefts.clear();
                  this.rights.clear();
                  this.disposables.dispose();
                  var9.onComplete();
                  return;
               }

               if (var3) {
                  var2 = this.addAndGet(-var1);
                  var1 = var2;
                  if (var2 == 0) {
                     return;
                  }
               } else {
                  Object var10 = var8.poll();
                  if (var11 == LEFT_VALUE) {
                     var2 = this.leftIndex++;
                     this.lefts.put(var2, (TLeft)var10);

                     try {
                        var42 = ObjectHelper.requireNonNull(this.leftEnd.apply((TLeft)var10), "The leftEnd returned a null Publisher");
                     } catch (Throwable var32) {
                        this.fail(var32, var9, var8);
                        return;
                     }

                     FlowableGroupJoin.LeftRightEndSubscriber var46 = new FlowableGroupJoin.LeftRightEndSubscriber(this, true, var2);
                     this.disposables.add(var46);
                     var42.subscribe(var46);
                     if (this.error.get() != null) {
                        var8.clear();
                        this.cancelAll();
                        this.errorAll(var9);
                        return;
                     }

                     long var37 = this.requested.get();
                     Iterator var43 = this.rights.values().iterator();

                     long var36;
                     for (var36 = 0L; var43.hasNext(); var36++) {
                        var46 = (FlowableGroupJoin.LeftRightEndSubscriber)var43.next();

                        try {
                           var46 = ObjectHelper.requireNonNull(
                              this.resultSelector.apply((TLeft)var10, (TRight)var46), "The resultSelector returned a null value"
                           );
                        } catch (Throwable var31) {
                           this.fail(var31, var9, var8);
                           return;
                        }

                        if (var36 == var37) {
                           ExceptionHelper.addThrowable(this.error, new MissingBackpressureException("Could not emit value due to lack of requests"));
                           var8.clear();
                           this.cancelAll();
                           this.errorAll(var9);
                           return;
                        }

                        var9.onNext(var46);
                     }

                     if (var36 != 0L) {
                        BackpressureHelper.produced(this.requested, var36);
                     }
                  } else if (var11 != RIGHT_VALUE) {
                     if (var11 == LEFT_CLOSE) {
                        var10 = (FlowableGroupJoin.LeftRightEndSubscriber)var10;
                        this.lefts.remove(((FlowableGroupJoin.LeftRightEndSubscriber)var10).index);
                        this.disposables.remove((Disposable)var10);
                     } else if (var11 == RIGHT_CLOSE) {
                        var10 = (FlowableGroupJoin.LeftRightEndSubscriber)var10;
                        this.rights.remove(((FlowableGroupJoin.LeftRightEndSubscriber)var10).index);
                        this.disposables.remove((Disposable)var10);
                     }
                  } else {
                     var2 = this.rightIndex++;
                     this.rights.put(var2, (TRight)var10);

                     Publisher var12;
                     try {
                        var12 = ObjectHelper.requireNonNull(this.rightEnd.apply((TRight)var10), "The rightEnd returned a null Publisher");
                     } catch (Throwable var30) {
                        this.fail(var30, var9, var8);
                        return;
                     }

                     FlowableGroupJoin.LeftRightEndSubscriber var40 = new FlowableGroupJoin.LeftRightEndSubscriber(this, false, var2);
                     this.disposables.add(var40);
                     var12.subscribe(var40);
                     if (this.error.get() != null) {
                        var8.clear();
                        this.cancelAll();
                        this.errorAll(var9);
                        return;
                     }

                     long var6 = this.requested.get();
                     Iterator var41 = this.lefts.values().iterator();

                     long var4;
                     for (var4 = 0L; var41.hasNext(); var4++) {
                        Object var44 = var41.next();

                        try {
                           var44 = ObjectHelper.requireNonNull(
                              this.resultSelector.apply((TLeft)var44, (TRight)var10), "The resultSelector returned a null value"
                           );
                        } catch (Throwable var29) {
                           this.fail(var29, var9, var8);
                           return;
                        }

                        if (var4 == var6) {
                           ExceptionHelper.addThrowable(this.error, new MissingBackpressureException("Could not emit value due to lack of requests"));
                           var8.clear();
                           this.cancelAll();
                           this.errorAll(var9);
                           return;
                        }

                        var9.onNext(var44);
                     }

                     if (var4 != 0L) {
                        BackpressureHelper.produced(this.requested, var4);
                     }
                  }
               }
            }

            var8.clear();
         }
      }

      void errorAll(Subscriber<?> var1) {
         Throwable var2 = ExceptionHelper.terminate(this.error);
         this.lefts.clear();
         this.rights.clear();
         var1.onError(var2);
      }

      void fail(Throwable var1, Subscriber<?> var2, SimpleQueue<?> var3) {
         Exceptions.throwIfFatal(var1);
         ExceptionHelper.addThrowable(this.error, var1);
         var3.clear();
         this.cancelAll();
         this.errorAll(var2);
      }

      @Override
      public void innerClose(boolean param1, FlowableGroupJoin.LeftRightEndSubscriber param2) {
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
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 06: astore 4
         // 08: iload 1
         // 09: ifeq 13
         // 0c: getstatic io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription.LEFT_CLOSE Ljava/lang/Integer;
         // 0f: astore 3
         // 10: goto 17
         // 13: getstatic io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription.RIGHT_CLOSE Ljava/lang/Integer;
         // 16: astore 3
         // 17: aload 4
         // 19: aload 3
         // 1a: aload 2
         // 1b: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;Ljava/lang/Object;)Z
         // 1e: pop
         // 1f: aload 0
         // 20: monitorexit
         // 21: aload 0
         // 22: invokevirtual io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription.drain ()V
         // 25: return
         // 26: astore 2
         // 27: aload 0
         // 28: monitorexit
         // 29: aload 2
         // 2a: athrow
      }

      @Override
      public void innerCloseError(Throwable var1) {
         if (ExceptionHelper.addThrowable(this.error, var1)) {
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void innerComplete(FlowableGroupJoin.LeftRightSubscriber var1) {
         this.disposables.delete(var1);
         this.active.decrementAndGet();
         this.drain();
      }

      @Override
      public void innerError(Throwable var1) {
         if (ExceptionHelper.addThrowable(this.error, var1)) {
            this.active.decrementAndGet();
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void innerValue(boolean param1, Object param2) {
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
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 06: astore 4
         // 08: iload 1
         // 09: ifeq 13
         // 0c: getstatic io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription.LEFT_VALUE Ljava/lang/Integer;
         // 0f: astore 3
         // 10: goto 17
         // 13: getstatic io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription.RIGHT_VALUE Ljava/lang/Integer;
         // 16: astore 3
         // 17: aload 4
         // 19: aload 3
         // 1a: aload 2
         // 1b: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;Ljava/lang/Object;)Z
         // 1e: pop
         // 1f: aload 0
         // 20: monitorexit
         // 21: aload 0
         // 22: invokevirtual io/reactivex/internal/operators/flowable/FlowableJoin$JoinSubscription.drain ()V
         // 25: return
         // 26: astore 2
         // 27: aload 0
         // 28: monitorexit
         // 29: aload 2
         // 2a: athrow
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
         }
      }
   }
}
