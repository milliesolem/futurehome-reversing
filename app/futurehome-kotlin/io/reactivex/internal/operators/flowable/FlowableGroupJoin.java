package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
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
import io.reactivex.processors.UnicastProcessor;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableGroupJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AbstractFlowableWithUpstream<TLeft, R> {
   final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
   final Publisher<? extends TRight> other;
   final BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> resultSelector;
   final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;

   public FlowableGroupJoin(
      Flowable<TLeft> var1,
      Publisher<? extends TRight> var2,
      Function<? super TLeft, ? extends Publisher<TLeftEnd>> var3,
      Function<? super TRight, ? extends Publisher<TRightEnd>> var4,
      BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> var5
   ) {
      super(var1);
      this.other = var2;
      this.leftEnd = var3;
      this.rightEnd = var4;
      this.resultSelector = var5;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      FlowableGroupJoin.GroupJoinSubscription var2 = new FlowableGroupJoin.GroupJoinSubscription<>(var1, this.leftEnd, this.rightEnd, this.resultSelector);
      var1.onSubscribe(var2);
      FlowableGroupJoin.LeftRightSubscriber var3 = new FlowableGroupJoin.LeftRightSubscriber(var2, true);
      var2.disposables.add(var3);
      FlowableGroupJoin.LeftRightSubscriber var4 = new FlowableGroupJoin.LeftRightSubscriber(var2, false);
      var2.disposables.add(var4);
      this.source.subscribe(var3);
      this.other.subscribe(var4);
   }

   static final class GroupJoinSubscription<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements Subscription, FlowableGroupJoin.JoinSupport {
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
      final Map<Integer, UnicastProcessor<TRight>> lefts;
      final SpscLinkedArrayQueue<Object> queue;
      final AtomicLong requested;
      final BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> resultSelector;
      final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
      int rightIndex;
      final Map<Integer, TRight> rights;

      GroupJoinSubscription(
         Subscriber<? super R> var1,
         Function<? super TLeft, ? extends Publisher<TLeftEnd>> var2,
         Function<? super TRight, ? extends Publisher<TRightEnd>> var3,
         BiFunction<? super TLeft, ? super Flowable<TRight>, ? extends R> var4
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
            SpscLinkedArrayQueue var5 = this.queue;
            Subscriber var4 = this.downstream;
            int var1 = 1;

            while (!this.cancelled) {
               if (this.error.get() != null) {
                  var5.clear();
                  this.cancelAll();
                  this.errorAll(var4);
                  return;
               }

               boolean var2;
               if (this.active.get() == 0) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               Integer var7 = (Integer)var5.poll();
               boolean var3;
               if (var7 == null) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (var2 && var3) {
                  Iterator var25 = this.lefts.values().iterator();

                  while (var25.hasNext()) {
                     ((UnicastProcessor)var25.next()).onComplete();
                  }

                  this.lefts.clear();
                  this.rights.clear();
                  this.disposables.dispose();
                  var4.onComplete();
                  return;
               }

               if (var3) {
                  var2 = this.addAndGet(-var1);
                  var1 = var2;
                  if (var2 == 0) {
                     return;
                  }
               } else {
                  Object var6 = var5.poll();
                  if (var7 == LEFT_VALUE) {
                     UnicastProcessor var33 = UnicastProcessor.create();
                     var2 = this.leftIndex++;
                     this.lefts.put(var2, var33);

                     Publisher var9;
                     try {
                        var9 = ObjectHelper.requireNonNull(this.leftEnd.apply((TLeft)var6), "The leftEnd returned a null Publisher");
                     } catch (Throwable var19) {
                        this.fail(var19, var4, var5);
                        return;
                     }

                     FlowableGroupJoin.LeftRightEndSubscriber var34 = new FlowableGroupJoin.LeftRightEndSubscriber(this, true, var2);
                     this.disposables.add(var34);
                     var9.subscribe(var34);
                     if (this.error.get() != null) {
                        var5.clear();
                        this.cancelAll();
                        this.errorAll(var4);
                        return;
                     }

                     try {
                        var6 = ObjectHelper.requireNonNull(this.resultSelector.apply((TLeft)var6, var33), "The resultSelector returned a null value");
                     } catch (Throwable var21) {
                        this.fail(var21, var4, var5);
                        return;
                     }

                     if (this.requested.get() == 0L) {
                        this.fail(new MissingBackpressureException("Could not emit value due to lack of requests"), var4, var5);
                        return;
                     }

                     var4.onNext(var6);
                     BackpressureHelper.produced(this.requested, 1L);
                     var6 = this.rights.values().iterator();

                     while (var6.hasNext()) {
                        var33.onNext(var6.next());
                     }
                  } else if (var7 == RIGHT_VALUE) {
                     var2 = this.rightIndex++;
                     this.rights.put(var2, (TRight)var6);

                     try {
                        var31 = ObjectHelper.requireNonNull(this.rightEnd.apply((TRight)var6), "The rightEnd returned a null Publisher");
                     } catch (Throwable var20) {
                        this.fail(var20, var4, var5);
                        return;
                     }

                     FlowableGroupJoin.LeftRightEndSubscriber var8 = new FlowableGroupJoin.LeftRightEndSubscriber(this, false, var2);
                     this.disposables.add(var8);
                     var31.subscribe(var8);
                     if (this.error.get() != null) {
                        var5.clear();
                        this.cancelAll();
                        this.errorAll(var4);
                        return;
                     }

                     Iterator var32 = this.lefts.values().iterator();

                     while (var32.hasNext()) {
                        ((UnicastProcessor)var32.next()).onNext(var6);
                     }
                  } else if (var7 == LEFT_CLOSE) {
                     var6 = (FlowableGroupJoin.LeftRightEndSubscriber)var6;
                     UnicastProcessor var30 = this.lefts.remove(((FlowableGroupJoin.LeftRightEndSubscriber)var6).index);
                     this.disposables.remove((Disposable)var6);
                     if (var30 != null) {
                        var30.onComplete();
                     }
                  } else if (var7 == RIGHT_CLOSE) {
                     var6 = (FlowableGroupJoin.LeftRightEndSubscriber)var6;
                     this.rights.remove(((FlowableGroupJoin.LeftRightEndSubscriber)var6).index);
                     this.disposables.remove((Disposable)var6);
                  }
               }
            }

            var5.clear();
         }
      }

      void errorAll(Subscriber<?> var1) {
         Throwable var2 = ExceptionHelper.terminate(this.error);
         Iterator var3 = this.lefts.values().iterator();

         while (var3.hasNext()) {
            ((UnicastProcessor)var3.next()).onError(var2);
         }

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
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 06: astore 4
         // 08: iload 1
         // 09: ifeq 13
         // 0c: getstatic io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription.LEFT_CLOSE Ljava/lang/Integer;
         // 0f: astore 3
         // 10: goto 17
         // 13: getstatic io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription.RIGHT_CLOSE Ljava/lang/Integer;
         // 16: astore 3
         // 17: aload 4
         // 19: aload 3
         // 1a: aload 2
         // 1b: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;Ljava/lang/Object;)Z
         // 1e: pop
         // 1f: aload 0
         // 20: monitorexit
         // 21: aload 0
         // 22: invokevirtual io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription.drain ()V
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
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 06: astore 4
         // 08: iload 1
         // 09: ifeq 13
         // 0c: getstatic io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription.LEFT_VALUE Ljava/lang/Integer;
         // 0f: astore 3
         // 10: goto 17
         // 13: getstatic io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription.RIGHT_VALUE Ljava/lang/Integer;
         // 16: astore 3
         // 17: aload 4
         // 19: aload 3
         // 1a: aload 2
         // 1b: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;Ljava/lang/Object;)Z
         // 1e: pop
         // 1f: aload 0
         // 20: monitorexit
         // 21: aload 0
         // 22: invokevirtual io/reactivex/internal/operators/flowable/FlowableGroupJoin$GroupJoinSubscription.drain ()V
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

   interface JoinSupport {
      void innerClose(boolean var1, FlowableGroupJoin.LeftRightEndSubscriber var2);

      void innerCloseError(Throwable var1);

      void innerComplete(FlowableGroupJoin.LeftRightSubscriber var1);

      void innerError(Throwable var1);

      void innerValue(boolean var1, Object var2);
   }

   static final class LeftRightEndSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
      private static final long serialVersionUID = 1883890389173668373L;
      final int index;
      final boolean isLeft;
      final FlowableGroupJoin.JoinSupport parent;

      LeftRightEndSubscriber(FlowableGroupJoin.JoinSupport var1, boolean var2, int var3) {
         this.parent = var1;
         this.isLeft = var2;
         this.index = var3;
      }

      @Override
      public void dispose() {
         SubscriptionHelper.cancel(this);
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == SubscriptionHelper.CANCELLED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void onComplete() {
         this.parent.innerClose(this.isLeft, this);
      }

      public void onError(Throwable var1) {
         this.parent.innerCloseError(var1);
      }

      public void onNext(Object var1) {
         if (SubscriptionHelper.cancel(this)) {
            this.parent.innerClose(this.isLeft, this);
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.setOnce(this, var1, Long.MAX_VALUE);
      }
   }

   static final class LeftRightSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
      private static final long serialVersionUID = 1883890389173668373L;
      final boolean isLeft;
      final FlowableGroupJoin.JoinSupport parent;

      LeftRightSubscriber(FlowableGroupJoin.JoinSupport var1, boolean var2) {
         this.parent = var1;
         this.isLeft = var2;
      }

      @Override
      public void dispose() {
         SubscriptionHelper.cancel(this);
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == SubscriptionHelper.CANCELLED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void onComplete() {
         this.parent.innerComplete(this);
      }

      public void onError(Throwable var1) {
         this.parent.innerError(var1);
      }

      public void onNext(Object var1) {
         this.parent.innerValue(this.isLeft, var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.setOnce(this, var1, Long.MAX_VALUE);
      }
   }
}
