package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBufferBoundary<T, U extends Collection<? super T>, Open, Close> extends AbstractFlowableWithUpstream<T, U> {
   final Function<? super Open, ? extends Publisher<? extends Close>> bufferClose;
   final Publisher<? extends Open> bufferOpen;
   final Callable<U> bufferSupplier;

   public FlowableBufferBoundary(
      Flowable<T> var1, Publisher<? extends Open> var2, Function<? super Open, ? extends Publisher<? extends Close>> var3, Callable<U> var4
   ) {
      super(var1);
      this.bufferOpen = var2;
      this.bufferClose = var3;
      this.bufferSupplier = var4;
   }

   @Override
   protected void subscribeActual(Subscriber<? super U> var1) {
      FlowableBufferBoundary.BufferBoundarySubscriber var2 = new FlowableBufferBoundary.BufferBoundarySubscriber<>(
         var1, this.bufferOpen, this.bufferClose, this.bufferSupplier
      );
      var1.onSubscribe(var2);
      this.source.subscribe(var2);
   }

   static final class BufferBoundarySubscriber<T, C extends Collection<? super T>, Open, Close>
      extends AtomicInteger
      implements FlowableSubscriber<T>,
      Subscription {
      private static final long serialVersionUID = -8466418554264089604L;
      final Function<? super Open, ? extends Publisher<? extends Close>> bufferClose;
      final Publisher<? extends Open> bufferOpen;
      final Callable<C> bufferSupplier;
      Map<Long, C> buffers;
      volatile boolean cancelled;
      volatile boolean done;
      final Subscriber<? super C> downstream;
      long emitted;
      final AtomicThrowable errors;
      long index;
      final SpscLinkedArrayQueue<C> queue;
      final AtomicLong requested;
      final CompositeDisposable subscribers;
      final AtomicReference<Subscription> upstream;

      BufferBoundarySubscriber(
         Subscriber<? super C> var1, Publisher<? extends Open> var2, Function<? super Open, ? extends Publisher<? extends Close>> var3, Callable<C> var4
      ) {
         this.downstream = var1;
         this.bufferSupplier = var4;
         this.bufferOpen = var2;
         this.bufferClose = var3;
         this.queue = new SpscLinkedArrayQueue(Flowable.bufferSize());
         this.subscribers = new CompositeDisposable();
         this.requested = new AtomicLong();
         this.upstream = new AtomicReference<>();
         this.buffers = new LinkedHashMap<>();
         this.errors = new AtomicThrowable();
      }

      void boundaryError(Disposable var1, Throwable var2) {
         SubscriptionHelper.cancel(this.upstream);
         this.subscribers.delete(var1);
         this.onError(var2);
      }

      public void cancel() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.upstream Ljava/util/concurrent/atomic/AtomicReference;
         // 04: invokestatic io/reactivex/internal/subscriptions/SubscriptionHelper.cancel (Ljava/util/concurrent/atomic/AtomicReference;)Z
         // 07: ifeq 35
         // 0a: aload 0
         // 0b: bipush 1
         // 0c: putfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.cancelled Z
         // 0f: aload 0
         // 10: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.subscribers Lio/reactivex/disposables/CompositeDisposable;
         // 13: invokevirtual io/reactivex/disposables/CompositeDisposable.dispose ()V
         // 16: aload 0
         // 17: monitorenter
         // 18: aload 0
         // 19: aconst_null
         // 1a: putfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.buffers Ljava/util/Map;
         // 1d: aload 0
         // 1e: monitorexit
         // 1f: aload 0
         // 20: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.getAndIncrement ()I
         // 23: ifeq 35
         // 26: aload 0
         // 27: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 2a: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.clear ()V
         // 2d: goto 35
         // 30: astore 1
         // 31: aload 0
         // 32: monitorexit
         // 33: aload 1
         // 34: athrow
         // 35: return
      }

      void close(FlowableBufferBoundary.BufferCloseSubscriber<T, C> param1, long param2) {
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
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.subscribers Lio/reactivex/disposables/CompositeDisposable;
         // 04: aload 1
         // 05: invokevirtual io/reactivex/disposables/CompositeDisposable.delete (Lio/reactivex/disposables/Disposable;)Z
         // 08: pop
         // 09: aload 0
         // 0a: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.subscribers Lio/reactivex/disposables/CompositeDisposable;
         // 0d: invokevirtual io/reactivex/disposables/CompositeDisposable.size ()I
         // 10: ifne 21
         // 13: aload 0
         // 14: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.upstream Ljava/util/concurrent/atomic/AtomicReference;
         // 17: invokestatic io/reactivex/internal/subscriptions/SubscriptionHelper.cancel (Ljava/util/concurrent/atomic/AtomicReference;)Z
         // 1a: pop
         // 1b: bipush 1
         // 1c: istore 4
         // 1e: goto 24
         // 21: bipush 0
         // 22: istore 4
         // 24: aload 0
         // 25: monitorenter
         // 26: aload 0
         // 27: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.buffers Ljava/util/Map;
         // 2a: astore 1
         // 2b: aload 1
         // 2c: ifnonnull 32
         // 2f: aload 0
         // 30: monitorexit
         // 31: return
         // 32: aload 0
         // 33: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 36: aload 1
         // 37: lload 2
         // 38: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
         // 3b: invokeinterface java/util/Map.remove (Ljava/lang/Object;)Ljava/lang/Object; 2
         // 40: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;)Z
         // 43: pop
         // 44: aload 0
         // 45: monitorexit
         // 46: iload 4
         // 48: ifeq 50
         // 4b: aload 0
         // 4c: bipush 1
         // 4d: putfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.done Z
         // 50: aload 0
         // 51: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.drain ()V
         // 54: return
         // 55: astore 1
         // 56: aload 0
         // 57: monitorexit
         // 58: aload 1
         // 59: athrow
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            long var4 = this.emitted;
            Subscriber var9 = this.downstream;
            SpscLinkedArrayQueue var11 = this.queue;
            int var1 = 1;

            int var12;
            do {
               long var6 = this.requested.get();

               int var3;
               while (true) {
                  long var13;
                  var3 = (var13 = var4 - var6) == 0L ? 0 : (var13 < 0L ? -1 : 1);
                  if (!var3) {
                     break;
                  }

                  if (this.cancelled) {
                     var11.clear();
                     return;
                  }

                  boolean var8 = this.done;
                  if (var8 && this.errors.get() != null) {
                     var11.clear();
                     var9.onError(this.errors.terminate());
                     return;
                  }

                  Collection var10 = (Collection)var11.poll();
                  boolean var2;
                  if (var10 == null) {
                     var2 = true;
                  } else {
                     var2 = false;
                  }

                  if (var8 && var2) {
                     var9.onComplete();
                     return;
                  }

                  if (var2) {
                     break;
                  }

                  var9.onNext(var10);
                  var4++;
               }

               if (!var3) {
                  if (this.cancelled) {
                     var11.clear();
                     return;
                  }

                  if (this.done) {
                     if (this.errors.get() != null) {
                        var11.clear();
                        var9.onError(this.errors.terminate());
                        return;
                     }

                     if (var11.isEmpty()) {
                        var9.onComplete();
                        return;
                     }
                  }
               }

               this.emitted = var4;
               var12 = this.addAndGet(-var1);
               var1 = var12;
            } while (var12 != 0);
         }
      }

      public void onComplete() {
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
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.subscribers Lio/reactivex/disposables/CompositeDisposable;
         // 04: invokevirtual io/reactivex/disposables/CompositeDisposable.dispose ()V
         // 07: aload 0
         // 08: monitorenter
         // 09: aload 0
         // 0a: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.buffers Ljava/util/Map;
         // 0d: astore 1
         // 0e: aload 1
         // 0f: ifnonnull 15
         // 12: aload 0
         // 13: monitorexit
         // 14: return
         // 15: aload 1
         // 16: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
         // 1b: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
         // 20: astore 1
         // 21: aload 1
         // 22: invokeinterface java/util/Iterator.hasNext ()Z 1
         // 27: ifeq 40
         // 2a: aload 1
         // 2b: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
         // 30: checkcast java/util/Collection
         // 33: astore 2
         // 34: aload 0
         // 35: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 38: aload 2
         // 39: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;)Z
         // 3c: pop
         // 3d: goto 21
         // 40: aload 0
         // 41: aconst_null
         // 42: putfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.buffers Ljava/util/Map;
         // 45: aload 0
         // 46: monitorexit
         // 47: aload 0
         // 48: bipush 1
         // 49: putfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.done Z
         // 4c: aload 0
         // 4d: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.drain ()V
         // 50: return
         // 51: astore 1
         // 52: aload 0
         // 53: monitorexit
         // 54: aload 1
         // 55: athrow
      }

      public void onError(Throwable param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.errors Lio/reactivex/internal/util/AtomicThrowable;
         // 04: aload 1
         // 05: invokevirtual io/reactivex/internal/util/AtomicThrowable.addThrowable (Ljava/lang/Throwable;)Z
         // 08: ifeq 2c
         // 0b: aload 0
         // 0c: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.subscribers Lio/reactivex/disposables/CompositeDisposable;
         // 0f: invokevirtual io/reactivex/disposables/CompositeDisposable.dispose ()V
         // 12: aload 0
         // 13: monitorenter
         // 14: aload 0
         // 15: aconst_null
         // 16: putfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.buffers Ljava/util/Map;
         // 19: aload 0
         // 1a: monitorexit
         // 1b: aload 0
         // 1c: bipush 1
         // 1d: putfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.done Z
         // 20: aload 0
         // 21: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.drain ()V
         // 24: goto 30
         // 27: astore 1
         // 28: aload 0
         // 29: monitorexit
         // 2a: aload 1
         // 2b: athrow
         // 2c: aload 1
         // 2d: invokestatic io/reactivex/plugins/RxJavaPlugins.onError (Ljava/lang/Throwable;)V
         // 30: return
      }

      public void onNext(T param1) {
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
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.buffers Ljava/util/Map;
         // 06: astore 2
         // 07: aload 2
         // 08: ifnonnull 0e
         // 0b: aload 0
         // 0c: monitorexit
         // 0d: return
         // 0e: aload 2
         // 0f: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
         // 14: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
         // 19: astore 2
         // 1a: aload 2
         // 1b: invokeinterface java/util/Iterator.hasNext ()Z 1
         // 20: ifeq 36
         // 23: aload 2
         // 24: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
         // 29: checkcast java/util/Collection
         // 2c: aload 1
         // 2d: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
         // 32: pop
         // 33: goto 1a
         // 36: aload 0
         // 37: monitorexit
         // 38: return
         // 39: astore 1
         // 3a: aload 0
         // 3b: monitorexit
         // 3c: aload 1
         // 3d: athrow
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.setOnce(this.upstream, var1)) {
            FlowableBufferBoundary.BufferBoundarySubscriber.BufferOpenSubscriber var2 = new FlowableBufferBoundary.BufferBoundarySubscriber.BufferOpenSubscriber<>(
               this
            );
            this.subscribers.add(var2);
            this.bufferOpen.subscribe(var2);
            var1.request(Long.MAX_VALUE);
         }
      }

      void open(Open param1) {
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
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.bufferSupplier Ljava/util/concurrent/Callable;
         // 04: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 09: ldc "The bufferSupplier returned a null Collection"
         // 0b: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 0e: checkcast java/util/Collection
         // 11: astore 4
         // 13: aload 0
         // 14: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.bufferClose Lio/reactivex/functions/Function;
         // 17: aload 1
         // 18: invokeinterface io/reactivex/functions/Function.apply (Ljava/lang/Object;)Ljava/lang/Object; 2
         // 1d: ldc_w "The bufferClose returned a null Publisher"
         // 20: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 23: checkcast org/reactivestreams/Publisher
         // 26: astore 1
         // 27: aload 0
         // 28: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.index J
         // 2b: lstore 2
         // 2c: aload 0
         // 2d: lconst_1
         // 2e: lload 2
         // 2f: ladd
         // 30: putfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.index J
         // 33: aload 0
         // 34: monitorenter
         // 35: aload 0
         // 36: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.buffers Ljava/util/Map;
         // 39: astore 5
         // 3b: aload 5
         // 3d: ifnonnull 43
         // 40: aload 0
         // 41: monitorexit
         // 42: return
         // 43: aload 5
         // 45: lload 2
         // 46: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
         // 49: aload 4
         // 4b: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
         // 50: pop
         // 51: aload 0
         // 52: monitorexit
         // 53: new io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferCloseSubscriber
         // 56: dup
         // 57: aload 0
         // 58: lload 2
         // 59: invokespecial io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferCloseSubscriber.<init> (Lio/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber;J)V
         // 5c: astore 4
         // 5e: aload 0
         // 5f: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.subscribers Lio/reactivex/disposables/CompositeDisposable;
         // 62: aload 4
         // 64: invokevirtual io/reactivex/disposables/CompositeDisposable.add (Lio/reactivex/disposables/Disposable;)Z
         // 67: pop
         // 68: aload 1
         // 69: aload 4
         // 6b: invokeinterface org/reactivestreams/Publisher.subscribe (Lorg/reactivestreams/Subscriber;)V 2
         // 70: return
         // 71: astore 1
         // 72: aload 0
         // 73: monitorexit
         // 74: aload 1
         // 75: athrow
         // 76: astore 1
         // 77: aload 1
         // 78: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 7b: aload 0
         // 7c: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.upstream Ljava/util/concurrent/atomic/AtomicReference;
         // 7f: invokestatic io/reactivex/internal/subscriptions/SubscriptionHelper.cancel (Ljava/util/concurrent/atomic/AtomicReference;)Z
         // 82: pop
         // 83: aload 0
         // 84: aload 1
         // 85: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferBoundary$BufferBoundarySubscriber.onError (Ljava/lang/Throwable;)V
         // 88: return
      }

      void openComplete(FlowableBufferBoundary.BufferBoundarySubscriber.BufferOpenSubscriber<Open> var1) {
         this.subscribers.delete(var1);
         if (this.subscribers.size() == 0) {
            SubscriptionHelper.cancel(this.upstream);
            this.done = true;
            this.drain();
         }
      }

      public void request(long var1) {
         BackpressureHelper.add(this.requested, var1);
         this.drain();
      }

      static final class BufferOpenSubscriber<Open> extends AtomicReference<Subscription> implements FlowableSubscriber<Open>, Disposable {
         private static final long serialVersionUID = -8498650778633225126L;
         final FlowableBufferBoundary.BufferBoundarySubscriber<?, ?, Open, ?> parent;

         BufferOpenSubscriber(FlowableBufferBoundary.BufferBoundarySubscriber<?, ?, Open, ?> var1) {
            this.parent = var1;
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
            this.lazySet(SubscriptionHelper.CANCELLED);
            this.parent.openComplete(this);
         }

         public void onError(Throwable var1) {
            this.lazySet(SubscriptionHelper.CANCELLED);
            this.parent.boundaryError(this, var1);
         }

         public void onNext(Open var1) {
            this.parent.open((Open)var1);
         }

         @Override
         public void onSubscribe(Subscription var1) {
            SubscriptionHelper.setOnce(this, var1, Long.MAX_VALUE);
         }
      }
   }

   static final class BufferCloseSubscriber<T, C extends Collection<? super T>>
      extends AtomicReference<Subscription>
      implements FlowableSubscriber<Object>,
      Disposable {
      private static final long serialVersionUID = -8498650778633225126L;
      final long index;
      final FlowableBufferBoundary.BufferBoundarySubscriber<T, C, ?, ?> parent;

      BufferCloseSubscriber(FlowableBufferBoundary.BufferBoundarySubscriber<T, C, ?, ?> var1, long var2) {
         this.parent = var1;
         this.index = var2;
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
         if (this.get() != SubscriptionHelper.CANCELLED) {
            this.lazySet(SubscriptionHelper.CANCELLED);
            this.parent.close(this, this.index);
         }
      }

      public void onError(Throwable var1) {
         if (this.get() != SubscriptionHelper.CANCELLED) {
            this.lazySet(SubscriptionHelper.CANCELLED);
            this.parent.boundaryError(this, var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(Object var1) {
         var1 = this.get();
         if (var1 != SubscriptionHelper.CANCELLED) {
            this.lazySet(SubscriptionHelper.CANCELLED);
            var1.cancel();
            this.parent.close(this, this.index);
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.setOnce(this, var1, Long.MAX_VALUE);
      }
   }
}
