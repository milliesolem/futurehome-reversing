package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.subscribers.SubscriberResourceWrapper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Timed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableReplay<T> extends ConnectableFlowable<T> implements HasUpstreamPublisher<T>, ResettableConnectable {
   static final Callable DEFAULT_UNBOUNDED_FACTORY = new FlowableReplay.DefaultUnboundedFactory();
   final Callable<? extends FlowableReplay.ReplayBuffer<T>> bufferFactory;
   final AtomicReference<FlowableReplay.ReplaySubscriber<T>> current;
   final Publisher<T> onSubscribe;
   final Flowable<T> source;

   private FlowableReplay(
      Publisher<T> var1, Flowable<T> var2, AtomicReference<FlowableReplay.ReplaySubscriber<T>> var3, Callable<? extends FlowableReplay.ReplayBuffer<T>> var4
   ) {
      this.onSubscribe = var1;
      this.source = var2;
      this.current = var3;
      this.bufferFactory = var4;
   }

   public static <T> ConnectableFlowable<T> create(Flowable<T> var0, int var1) {
      return var1 == Integer.MAX_VALUE ? createFrom(var0) : create(var0, new FlowableReplay.ReplayBufferTask<>(var1));
   }

   public static <T> ConnectableFlowable<T> create(Flowable<T> var0, long var1, TimeUnit var3, Scheduler var4) {
      return create(var0, var1, var3, var4, Integer.MAX_VALUE);
   }

   public static <T> ConnectableFlowable<T> create(Flowable<T> var0, long var1, TimeUnit var3, Scheduler var4, int var5) {
      return create(var0, new FlowableReplay.ScheduledReplayBufferTask<>(var5, var1, var3, var4));
   }

   static <T> ConnectableFlowable<T> create(Flowable<T> var0, Callable<? extends FlowableReplay.ReplayBuffer<T>> var1) {
      AtomicReference var2 = new AtomicReference();
      return RxJavaPlugins.onAssembly(new FlowableReplay<>(new FlowableReplay.ReplayPublisher<>(var2, var1), var0, var2, var1));
   }

   public static <T> ConnectableFlowable<T> createFrom(Flowable<? extends T> var0) {
      return create(var0, DEFAULT_UNBOUNDED_FACTORY);
   }

   public static <U, R> Flowable<R> multicastSelector(
      Callable<? extends ConnectableFlowable<U>> var0, Function<? super Flowable<U>, ? extends Publisher<R>> var1
   ) {
      return new FlowableReplay.MulticastFlowable<>(var0, var1);
   }

   public static <T> ConnectableFlowable<T> observeOn(ConnectableFlowable<T> var0, Scheduler var1) {
      return RxJavaPlugins.onAssembly(new FlowableReplay.ConnectableFlowableReplay<>(var0, var0.observeOn(var1)));
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void connect(Consumer<? super Disposable> var1) {
      FlowableReplay.ReplaySubscriber var3;
      FlowableReplay.ReplaySubscriber var4;
      do {
         var4 = this.current.get();
         if (var4 != null) {
            var3 = var4;
            if (!var4.isDisposed()) {
               break;
            }
         }

         try {
            var11 = this.bufferFactory.call();
         } catch (Throwable var9) {
            Exceptions.throwIfFatal(var9);
            throw ExceptionHelper.wrapOrThrow(var9);
         }

         var3 = new FlowableReplay.ReplaySubscriber(var11);
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.current, var4, var3));

      boolean var2;
      if (!var3.shouldConnect.get() && var3.shouldConnect.compareAndSet(false, true)) {
         var2 = true;
      } else {
         var2 = false;
      }

      try {
         var1.accept(var3);
      } catch (Throwable var10) {
         if (var2) {
            var3.shouldConnect.compareAndSet(true, false);
         }

         Exceptions.throwIfFatal(var10);
         throw ExceptionHelper.wrapOrThrow(var10);
      }

      if (var2) {
         this.source.subscribe(var3);
      }
   }

   @Override
   public void resetIf(Disposable var1) {
      ExternalSyntheticBackportWithForwarding0.m(this.current, (FlowableReplay.ReplaySubscriber)var1, null);
   }

   @Override
   public Publisher<T> source() {
      return this.source;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.onSubscribe.subscribe(var1);
   }

   static class BoundedReplayBuffer<T> extends AtomicReference<FlowableReplay.Node> implements FlowableReplay.ReplayBuffer<T> {
      private static final long serialVersionUID = 2346567790059478686L;
      long index;
      int size;
      FlowableReplay.Node tail;

      BoundedReplayBuffer() {
         FlowableReplay.Node var1 = new FlowableReplay.Node(null, 0L);
         this.tail = var1;
         this.set(var1);
      }

      final void addLast(FlowableReplay.Node var1) {
         this.tail.set(var1);
         this.tail = var1;
         this.size++;
      }

      final void collect(Collection<? super T> var1) {
         FlowableReplay.Node var2 = this.getHead();

         while (true) {
            var2 = var2.get();
            if (var2 == null) {
               break;
            }

            Object var3 = this.leaveTransform(var2.value);
            if (NotificationLite.isComplete(var3) || NotificationLite.isError(var3)) {
               break;
            }

            var1.add(NotificationLite.getValue(var3));
         }
      }

      @Override
      public final void complete() {
         Object var3 = this.enterTransform(NotificationLite.complete());
         long var1 = this.index + 1L;
         this.index = var1;
         this.addLast(new FlowableReplay.Node(var3, var1));
         this.truncateFinal();
      }

      Object enterTransform(Object var1) {
         return var1;
      }

      @Override
      public final void error(Throwable var1) {
         Object var4 = this.enterTransform(NotificationLite.error(var1));
         long var2 = this.index + 1L;
         this.index = var2;
         this.addLast(new FlowableReplay.Node(var4, var2));
         this.truncateFinal();
      }

      FlowableReplay.Node getHead() {
         return this.get();
      }

      boolean hasCompleted() {
         boolean var1;
         if (this.tail.value != null && NotificationLite.isComplete(this.leaveTransform(this.tail.value))) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      boolean hasError() {
         boolean var1;
         if (this.tail.value != null && NotificationLite.isError(this.leaveTransform(this.tail.value))) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      Object leaveTransform(Object var1) {
         return var1;
      }

      @Override
      public final void next(T var1) {
         var1 = this.enterTransform(NotificationLite.next(var1));
         long var2 = this.index + 1L;
         this.index = var2;
         this.addLast(new FlowableReplay.Node(var1, var2));
         this.truncate();
      }

      final void removeFirst() {
         FlowableReplay.Node var1 = this.get().get();
         if (var1 != null) {
            this.size--;
            this.setFirst(var1);
         } else {
            throw new IllegalStateException("Empty list!");
         }
      }

      final void removeSome(int var1) {
         FlowableReplay.Node var2;
         for (var2 = this.get(); var1 > 0; this.size--) {
            var2 = var2.get();
            var1--;
         }

         this.setFirst(var2);
         var2 = this.get();
         if (var2.get() == null) {
            this.tail = var2;
         }
      }

      @Override
      public final void replay(FlowableReplay.InnerSubscription<T> param1) {
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
         // 000: aload 1
         // 001: monitorenter
         // 002: aload 1
         // 003: getfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.emitting Z
         // 006: ifeq 011
         // 009: aload 1
         // 00a: bipush 1
         // 00b: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.missed Z
         // 00e: aload 1
         // 00f: monitorexit
         // 010: return
         // 011: aload 1
         // 012: bipush 1
         // 013: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.emitting Z
         // 016: aload 1
         // 017: monitorexit
         // 018: aload 1
         // 019: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.isDisposed ()Z
         // 01c: ifeq 025
         // 01f: aload 1
         // 020: aconst_null
         // 021: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.index Ljava/lang/Object;
         // 024: return
         // 025: aload 1
         // 026: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.get ()J
         // 029: lstore 3
         // 02a: lload 3
         // 02b: ldc2_w 9223372036854775807
         // 02e: lcmp
         // 02f: ifne 037
         // 032: bipush 1
         // 033: istore 2
         // 034: goto 039
         // 037: bipush 0
         // 038: istore 2
         // 039: aload 1
         // 03a: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.index ()Ljava/lang/Object;
         // 03d: checkcast io/reactivex/internal/operators/flowable/FlowableReplay$Node
         // 040: astore 8
         // 042: aload 8
         // 044: astore 7
         // 046: aload 8
         // 048: ifnonnull 064
         // 04b: aload 0
         // 04c: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$BoundedReplayBuffer.getHead ()Lio/reactivex/internal/operators/flowable/FlowableReplay$Node;
         // 04f: astore 7
         // 051: aload 1
         // 052: aload 7
         // 054: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.index Ljava/lang/Object;
         // 057: aload 1
         // 058: getfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.totalRequested Ljava/util/concurrent/atomic/AtomicLong;
         // 05b: aload 7
         // 05d: getfield io/reactivex/internal/operators/flowable/FlowableReplay$Node.index J
         // 060: invokestatic io/reactivex/internal/util/BackpressureHelper.add (Ljava/util/concurrent/atomic/AtomicLong;J)J
         // 063: pop2
         // 064: lconst_0
         // 065: lstore 5
         // 067: lload 3
         // 068: lconst_0
         // 069: lcmp
         // 06a: ifeq 0e3
         // 06d: aload 7
         // 06f: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$Node.get ()Ljava/lang/Object;
         // 072: checkcast io/reactivex/internal/operators/flowable/FlowableReplay$Node
         // 075: astore 8
         // 077: aload 8
         // 079: ifnull 0e3
         // 07c: aload 0
         // 07d: aload 8
         // 07f: getfield io/reactivex/internal/operators/flowable/FlowableReplay$Node.value Ljava/lang/Object;
         // 082: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$BoundedReplayBuffer.leaveTransform (Ljava/lang/Object;)Ljava/lang/Object;
         // 085: astore 7
         // 087: aload 7
         // 089: aload 1
         // 08a: getfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.child Lorg/reactivestreams/Subscriber;
         // 08d: invokestatic io/reactivex/internal/util/NotificationLite.accept (Ljava/lang/Object;Lorg/reactivestreams/Subscriber;)Z
         // 090: ifeq 099
         // 093: aload 1
         // 094: aconst_null
         // 095: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.index Ljava/lang/Object;
         // 098: return
         // 099: lload 5
         // 09b: lconst_1
         // 09c: ladd
         // 09d: lstore 5
         // 09f: lload 3
         // 0a0: lconst_1
         // 0a1: lsub
         // 0a2: lstore 3
         // 0a3: aload 1
         // 0a4: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.isDisposed ()Z
         // 0a7: ifeq 0b0
         // 0aa: aload 1
         // 0ab: aconst_null
         // 0ac: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.index Ljava/lang/Object;
         // 0af: return
         // 0b0: aload 8
         // 0b2: astore 7
         // 0b4: goto 067
         // 0b7: astore 8
         // 0b9: aload 8
         // 0bb: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 0be: aload 1
         // 0bf: aconst_null
         // 0c0: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.index Ljava/lang/Object;
         // 0c3: aload 1
         // 0c4: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.dispose ()V
         // 0c7: aload 7
         // 0c9: invokestatic io/reactivex/internal/util/NotificationLite.isError (Ljava/lang/Object;)Z
         // 0cc: ifne 0e2
         // 0cf: aload 7
         // 0d1: invokestatic io/reactivex/internal/util/NotificationLite.isComplete (Ljava/lang/Object;)Z
         // 0d4: ifne 0e2
         // 0d7: aload 1
         // 0d8: getfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.child Lorg/reactivestreams/Subscriber;
         // 0db: aload 8
         // 0dd: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
         // 0e2: return
         // 0e3: lload 5
         // 0e5: lconst_0
         // 0e6: lcmp
         // 0e7: ifeq 0fb
         // 0ea: aload 1
         // 0eb: aload 7
         // 0ed: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.index Ljava/lang/Object;
         // 0f0: iload 2
         // 0f1: ifne 0fb
         // 0f4: aload 1
         // 0f5: lload 5
         // 0f7: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.produced (J)J
         // 0fa: pop2
         // 0fb: aload 1
         // 0fc: monitorenter
         // 0fd: aload 1
         // 0fe: getfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.missed Z
         // 101: ifne 10c
         // 104: aload 1
         // 105: bipush 0
         // 106: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.emitting Z
         // 109: aload 1
         // 10a: monitorexit
         // 10b: return
         // 10c: aload 1
         // 10d: bipush 0
         // 10e: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.missed Z
         // 111: aload 1
         // 112: monitorexit
         // 113: goto 018
         // 116: astore 7
         // 118: aload 1
         // 119: monitorexit
         // 11a: aload 7
         // 11c: athrow
         // 11d: astore 7
         // 11f: aload 1
         // 120: monitorexit
         // 121: aload 7
         // 123: athrow
      }

      final void setFirst(FlowableReplay.Node var1) {
         this.set(var1);
      }

      final void trimHead() {
         FlowableReplay.Node var1 = this.get();
         if (var1.value != null) {
            FlowableReplay.Node var2 = new FlowableReplay.Node(null, 0L);
            var2.lazySet(var1.get());
            this.set(var2);
         }
      }

      void truncate() {
      }

      void truncateFinal() {
         this.trimHead();
      }
   }

   static final class ConnectableFlowableReplay<T> extends ConnectableFlowable<T> {
      private final ConnectableFlowable<T> cf;
      private final Flowable<T> flowable;

      ConnectableFlowableReplay(ConnectableFlowable<T> var1, Flowable<T> var2) {
         this.cf = var1;
         this.flowable = var2;
      }

      @Override
      public void connect(Consumer<? super Disposable> var1) {
         this.cf.connect(var1);
      }

      @Override
      protected void subscribeActual(Subscriber<? super T> var1) {
         this.flowable.subscribe(var1);
      }
   }

   static final class DefaultUnboundedFactory implements Callable<Object> {
      @Override
      public Object call() {
         return new FlowableReplay.UnboundedReplayBuffer(16);
      }
   }

   static final class InnerSubscription<T> extends AtomicLong implements Subscription, Disposable {
      static final long CANCELLED = Long.MIN_VALUE;
      private static final long serialVersionUID = -4453897557930727610L;
      final Subscriber<? super T> child;
      boolean emitting;
      Object index;
      boolean missed;
      final FlowableReplay.ReplaySubscriber<T> parent;
      final AtomicLong totalRequested;

      InnerSubscription(FlowableReplay.ReplaySubscriber<T> var1, Subscriber<? super T> var2) {
         this.parent = var1;
         this.child = var2;
         this.totalRequested = new AtomicLong();
      }

      public void cancel() {
         this.dispose();
      }

      @Override
      public void dispose() {
         if (this.getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
            this.parent.remove(this);
            this.parent.manageRequests();
            this.index = null;
         }
      }

      <U> U index() {
         return (U)this.index;
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == Long.MIN_VALUE) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public long produced(long var1) {
         return BackpressureHelper.producedCancel(this, var1);
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1) && BackpressureHelper.addCancel(this, var1) != Long.MIN_VALUE) {
            BackpressureHelper.add(this.totalRequested, var1);
            this.parent.manageRequests();
            this.parent.buffer.replay(this);
         }
      }
   }

   static final class MulticastFlowable<R, U> extends Flowable<R> {
      private final Callable<? extends ConnectableFlowable<U>> connectableFactory;
      private final Function<? super Flowable<U>, ? extends Publisher<R>> selector;

      MulticastFlowable(Callable<? extends ConnectableFlowable<U>> var1, Function<? super Flowable<U>, ? extends Publisher<R>> var2) {
         this.connectableFactory = var1;
         this.selector = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      protected void subscribeActual(Subscriber<? super R> var1) {
         ConnectableFlowable var3;
         try {
            var3 = ObjectHelper.requireNonNull(this.connectableFactory.call(), "The connectableFactory returned null");
         } catch (Throwable var9) {
            Exceptions.throwIfFatal(var9);
            EmptySubscription.error(var9, var1);
            return;
         }

         Publisher var2;
         try {
            var2 = ObjectHelper.requireNonNull(this.selector.apply(var3), "The selector returned a null Publisher");
         } catch (Throwable var8) {
            Exceptions.throwIfFatal(var8);
            EmptySubscription.error(var8, var1);
            return;
         }

         SubscriberResourceWrapper var10 = new SubscriberResourceWrapper(var1);
         var2.subscribe(var10);
         var3.connect(new FlowableReplay.MulticastFlowable.DisposableConsumer(this, var10));
      }

      final class DisposableConsumer implements Consumer<Disposable> {
         private final SubscriberResourceWrapper<R> srw;
         final FlowableReplay.MulticastFlowable this$0;

         DisposableConsumer(SubscriberResourceWrapper<R> var1, SubscriberResourceWrapper var2) {
            this.this$0 = var1;
            this.srw = var2;
         }

         public void accept(Disposable var1) {
            this.srw.setResource(var1);
         }
      }
   }

   static final class Node extends AtomicReference<FlowableReplay.Node> {
      private static final long serialVersionUID = 245354315435971818L;
      final long index;
      final Object value;

      Node(Object var1, long var2) {
         this.value = var1;
         this.index = var2;
      }
   }

   interface ReplayBuffer<T> {
      void complete();

      void error(Throwable var1);

      void next(T var1);

      void replay(FlowableReplay.InnerSubscription<T> var1);
   }

   static final class ReplayBufferTask<T> implements Callable<FlowableReplay.ReplayBuffer<T>> {
      private final int bufferSize;

      ReplayBufferTask(int var1) {
         this.bufferSize = var1;
      }

      public FlowableReplay.ReplayBuffer<T> call() {
         return new FlowableReplay.SizeBoundReplayBuffer<>(this.bufferSize);
      }
   }

   static final class ReplayPublisher<T> implements Publisher<T> {
      private final Callable<? extends FlowableReplay.ReplayBuffer<T>> bufferFactory;
      private final AtomicReference<FlowableReplay.ReplaySubscriber<T>> curr;

      ReplayPublisher(AtomicReference<FlowableReplay.ReplaySubscriber<T>> var1, Callable<? extends FlowableReplay.ReplayBuffer<T>> var2) {
         this.curr = var1;
         this.bufferFactory = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void subscribe(Subscriber<? super T> var1) {
         while (true) {
            FlowableReplay.ReplaySubscriber var3 = this.curr.get();
            FlowableReplay.ReplaySubscriber var2 = var3;
            if (var3 == null) {
               try {
                  var6 = this.bufferFactory.call();
               } catch (Throwable var5) {
                  Exceptions.throwIfFatal(var5);
                  EmptySubscription.error(var5, var1);
                  return;
               }

               var2 = new FlowableReplay.ReplaySubscriber(var6);
               if (!ExternalSyntheticBackportWithForwarding0.m(this.curr, null, var2)) {
                  continue;
               }
            }

            FlowableReplay.InnerSubscription var7 = new FlowableReplay.InnerSubscription(var2, var1);
            var1.onSubscribe(var7);
            var2.add(var7);
            if (var7.isDisposed()) {
               var2.remove(var7);
               return;
            }

            var2.manageRequests();
            var2.buffer.replay(var7);
            return;
         }
      }
   }

   static final class ReplaySubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Disposable {
      static final FlowableReplay.InnerSubscription[] EMPTY = new FlowableReplay.InnerSubscription[0];
      static final FlowableReplay.InnerSubscription[] TERMINATED = new FlowableReplay.InnerSubscription[0];
      private static final long serialVersionUID = 7224554242710036740L;
      final FlowableReplay.ReplayBuffer<T> buffer;
      boolean done;
      final AtomicInteger management;
      long maxChildRequested;
      long maxUpstreamRequested;
      final AtomicBoolean shouldConnect;
      final AtomicReference<FlowableReplay.InnerSubscription<T>[]> subscribers;

      ReplaySubscriber(FlowableReplay.ReplayBuffer<T> var1) {
         this.buffer = var1;
         this.management = new AtomicInteger();
         this.subscribers = new AtomicReference<>(EMPTY);
         this.shouldConnect = new AtomicBoolean();
      }

      boolean add(FlowableReplay.InnerSubscription<T> var1) {
         var1.getClass();

         FlowableReplay.InnerSubscription[] var3;
         FlowableReplay.InnerSubscription[] var4;
         do {
            var4 = this.subscribers.get();
            if (var4 == TERMINATED) {
               return false;
            }

            int var2 = var4.length;
            var3 = new FlowableReplay.InnerSubscription[var2 + 1];
            System.arraycopy(var4, 0, var3, 0, var2);
            var3[var2] = var1;
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var4, var3));

         return true;
      }

      @Override
      public void dispose() {
         this.subscribers.set(TERMINATED);
         SubscriptionHelper.cancel(this);
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

      void manageRequests() {
         if (this.management.getAndIncrement() == 0) {
            int var1 = 1;

            while (!this.isDisposed()) {
               FlowableReplay.InnerSubscription[] var10 = this.subscribers.get();
               long var6 = this.maxChildRequested;
               int var3 = var10.length;
               int var2 = 0;

               long var4;
               for (var4 = var6; var2 < var3; var2++) {
                  var4 = Math.max(var4, var10[var2].totalRequested.get());
               }

               long var8 = this.maxUpstreamRequested;
               Subscription var15 = this.get();
               var6 = var4 - var6;
               if (var6 != 0L) {
                  this.maxChildRequested = var4;
                  if (var15 != null) {
                     if (var8 != 0L) {
                        this.maxUpstreamRequested = 0L;
                        var15.request(var8 + var6);
                     } else {
                        var15.request(var6);
                     }
                  } else {
                     var6 = var8 + var6;
                     var4 = var6;
                     if (var6 < 0L) {
                        var4 = Long.MAX_VALUE;
                     }

                     this.maxUpstreamRequested = var4;
                  }
               } else if (var8 != 0L && var15 != null) {
                  this.maxUpstreamRequested = 0L;
                  var15.request(var8);
               }

               var2 = this.management.addAndGet(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            }
         }
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.buffer.complete();

            for (FlowableReplay.InnerSubscription var4 : this.subscribers.getAndSet(TERMINATED)) {
               this.buffer.replay(var4);
            }
         }
      }

      public void onError(Throwable var1) {
         if (!this.done) {
            this.done = true;
            this.buffer.error(var1);

            for (FlowableReplay.InnerSubscription var4 : this.subscribers.getAndSet(TERMINATED)) {
               this.buffer.replay(var4);
            }
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (!this.done) {
            this.buffer.next((T)var1);

            for (FlowableReplay.InnerSubscription var4 : this.subscribers.get()) {
               this.buffer.replay(var4);
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.setOnce(this, var1)) {
            this.manageRequests();

            for (FlowableReplay.InnerSubscription var4 : this.subscribers.get()) {
               this.buffer.replay(var4);
            }
         }
      }

      void remove(FlowableReplay.InnerSubscription<T> var1) {
         FlowableReplay.InnerSubscription[] var4;
         FlowableReplay.InnerSubscription[] var5;
         do {
            var5 = this.subscribers.get();
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

               if (var5[var2].equals(var1)) {
                  break;
               }

               var2++;
            }

            if (var2 < 0) {
               return;
            }

            if (var3 == 1) {
               var4 = EMPTY;
            } else {
               var4 = new FlowableReplay.InnerSubscription[var3 - 1];
               System.arraycopy(var5, 0, var4, 0, var2);
               System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
            }
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, var4));
      }
   }

   static final class ScheduledReplayBufferTask<T> implements Callable<FlowableReplay.ReplayBuffer<T>> {
      private final int bufferSize;
      private final long maxAge;
      private final Scheduler scheduler;
      private final TimeUnit unit;

      ScheduledReplayBufferTask(int var1, long var2, TimeUnit var4, Scheduler var5) {
         this.bufferSize = var1;
         this.maxAge = var2;
         this.unit = var4;
         this.scheduler = var5;
      }

      public FlowableReplay.ReplayBuffer<T> call() {
         return new FlowableReplay.SizeAndTimeBoundReplayBuffer<>(this.bufferSize, this.maxAge, this.unit, this.scheduler);
      }
   }

   static final class SizeAndTimeBoundReplayBuffer<T> extends FlowableReplay.BoundedReplayBuffer<T> {
      private static final long serialVersionUID = 3457957419649567404L;
      final int limit;
      final long maxAge;
      final Scheduler scheduler;
      final TimeUnit unit;

      SizeAndTimeBoundReplayBuffer(int var1, long var2, TimeUnit var4, Scheduler var5) {
         this.scheduler = var5;
         this.limit = var1;
         this.maxAge = var2;
         this.unit = var4;
      }

      @Override
      Object enterTransform(Object var1) {
         return new Timed<>(var1, this.scheduler.now(this.unit), this.unit);
      }

      @Override
      FlowableReplay.Node getHead() {
         long var3 = this.scheduler.now(this.unit);
         long var1 = this.maxAge;
         FlowableReplay.Node var6 = this.get();
         FlowableReplay.Node var5 = var6.get();

         while (var5 != null) {
            Timed var7 = (Timed)var5.value;
            if (NotificationLite.isComplete(var7.value()) || NotificationLite.isError(var7.value()) || var7.time() > var3 - var1) {
               break;
            }

            FlowableReplay.Node var8 = var5.get();
            var6 = var5;
            var5 = var8;
         }

         return var6;
      }

      @Override
      Object leaveTransform(Object var1) {
         return ((Timed)var1).value();
      }

      @Override
      void truncate() {
         long var2 = this.scheduler.now(this.unit);
         long var4 = this.maxAge;
         FlowableReplay.Node var7 = this.get();
         FlowableReplay.Node var6 = var7.get();
         int var1 = 0;

         while (var6 != null) {
            if (this.size > this.limit && this.size > 1) {
               var1++;
               this.size--;
               FlowableReplay.Node var9 = var6.get();
               var7 = var6;
               var6 = var9;
            } else {
               if (((Timed)var6.value).time() > var2 - var4) {
                  break;
               }

               var1++;
               this.size--;
               FlowableReplay.Node var8 = var6.get();
               var7 = var6;
               var6 = var8;
            }
         }

         if (var1 != 0) {
            this.setFirst(var7);
         }
      }

      @Override
      void truncateFinal() {
         long var4 = this.scheduler.now(this.unit);
         long var2 = this.maxAge;
         FlowableReplay.Node var7 = this.get();
         FlowableReplay.Node var6 = var7.get();
         int var1 = 0;

         while (var6 != null && this.size > 1 && ((Timed)var6.value).time() <= var4 - var2) {
            var1++;
            this.size--;
            FlowableReplay.Node var8 = var6.get();
            var7 = var6;
            var6 = var8;
         }

         if (var1 != 0) {
            this.setFirst(var7);
         }
      }
   }

   static final class SizeBoundReplayBuffer<T> extends FlowableReplay.BoundedReplayBuffer<T> {
      private static final long serialVersionUID = -5898283885385201806L;
      final int limit;

      SizeBoundReplayBuffer(int var1) {
         this.limit = var1;
      }

      @Override
      void truncate() {
         if (this.size > this.limit) {
            this.removeFirst();
         }
      }
   }

   static final class UnboundedReplayBuffer<T> extends ArrayList<Object> implements FlowableReplay.ReplayBuffer<T> {
      private static final long serialVersionUID = 7063189396499112664L;
      volatile int size;

      UnboundedReplayBuffer(int var1) {
         super(var1);
      }

      @Override
      public void complete() {
         this.add(NotificationLite.complete());
         this.size++;
      }

      @Override
      public void error(Throwable var1) {
         this.add(NotificationLite.error(var1));
         this.size++;
      }

      @Override
      public void next(T var1) {
         this.add(NotificationLite.next(var1));
         this.size++;
      }

      @Override
      public void replay(FlowableReplay.InnerSubscription<T> param1) {
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
         // 00: aload 1
         // 01: monitorenter
         // 02: aload 1
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.emitting Z
         // 06: ifeq 11
         // 09: aload 1
         // 0a: bipush 1
         // 0b: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.missed Z
         // 0e: aload 1
         // 0f: monitorexit
         // 10: return
         // 11: aload 1
         // 12: bipush 1
         // 13: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.emitting Z
         // 16: aload 1
         // 17: monitorexit
         // 18: aload 1
         // 19: getfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.child Lorg/reactivestreams/Subscriber;
         // 1c: astore 11
         // 1e: aload 1
         // 1f: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.isDisposed ()Z
         // 22: ifeq 26
         // 25: return
         // 26: aload 0
         // 27: getfield io/reactivex/internal/operators/flowable/FlowableReplay$UnboundedReplayBuffer.size I
         // 2a: istore 3
         // 2b: aload 1
         // 2c: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.index ()Ljava/lang/Object;
         // 2f: checkcast java/lang/Integer
         // 32: astore 12
         // 34: aload 12
         // 36: ifnull 42
         // 39: aload 12
         // 3b: invokevirtual java/lang/Integer.intValue ()I
         // 3e: istore 2
         // 3f: goto 44
         // 42: bipush 0
         // 43: istore 2
         // 44: aload 1
         // 45: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.get ()J
         // 48: lstore 8
         // 4a: lload 8
         // 4c: lstore 6
         // 4e: lconst_0
         // 4f: lstore 4
         // 51: lload 6
         // 53: lconst_0
         // 54: lcmp
         // 55: ifeq b2
         // 58: iload 2
         // 59: iload 3
         // 5a: if_icmpge b2
         // 5d: aload 0
         // 5e: iload 2
         // 5f: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$UnboundedReplayBuffer.get (I)Ljava/lang/Object;
         // 62: astore 12
         // 64: aload 12
         // 66: aload 11
         // 68: invokestatic io/reactivex/internal/util/NotificationLite.accept (Ljava/lang/Object;Lorg/reactivestreams/Subscriber;)Z
         // 6b: istore 10
         // 6d: iload 10
         // 6f: ifeq 73
         // 72: return
         // 73: aload 1
         // 74: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.isDisposed ()Z
         // 77: ifeq 7b
         // 7a: return
         // 7b: iinc 2 1
         // 7e: lload 6
         // 80: lconst_1
         // 81: lsub
         // 82: lstore 6
         // 84: lload 4
         // 86: lconst_1
         // 87: ladd
         // 88: lstore 4
         // 8a: goto 51
         // 8d: astore 13
         // 8f: aload 13
         // 91: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 94: aload 1
         // 95: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.dispose ()V
         // 98: aload 12
         // 9a: invokestatic io/reactivex/internal/util/NotificationLite.isError (Ljava/lang/Object;)Z
         // 9d: ifne b1
         // a0: aload 12
         // a2: invokestatic io/reactivex/internal/util/NotificationLite.isComplete (Ljava/lang/Object;)Z
         // a5: ifne b1
         // a8: aload 11
         // aa: aload 13
         // ac: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
         // b1: return
         // b2: lload 4
         // b4: lconst_0
         // b5: lcmp
         // b6: ifeq d1
         // b9: aload 1
         // ba: iload 2
         // bb: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
         // be: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.index Ljava/lang/Object;
         // c1: lload 8
         // c3: ldc2_w 9223372036854775807
         // c6: lcmp
         // c7: ifeq d1
         // ca: aload 1
         // cb: lload 4
         // cd: invokevirtual io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.produced (J)J
         // d0: pop2
         // d1: aload 1
         // d2: monitorenter
         // d3: aload 1
         // d4: getfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.missed Z
         // d7: ifne e2
         // da: aload 1
         // db: bipush 0
         // dc: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.emitting Z
         // df: aload 1
         // e0: monitorexit
         // e1: return
         // e2: aload 1
         // e3: bipush 0
         // e4: putfield io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription.missed Z
         // e7: aload 1
         // e8: monitorexit
         // e9: goto 1e
         // ec: astore 11
         // ee: aload 1
         // ef: monitorexit
         // f0: aload 11
         // f2: athrow
         // f3: astore 11
         // f5: aload 1
         // f6: monitorexit
         // f7: aload 11
         // f9: athrow
      }
   }
}
