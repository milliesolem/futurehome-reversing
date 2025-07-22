package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFlatMapMaybe<T, R> extends AbstractFlowableWithUpstream<T, R> {
   final boolean delayErrors;
   final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
   final int maxConcurrency;

   public FlowableFlatMapMaybe(Flowable<T> var1, Function<? super T, ? extends MaybeSource<? extends R>> var2, boolean var3, int var4) {
      super(var1);
      this.mapper = var2;
      this.delayErrors = var3;
      this.maxConcurrency = var4;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      this.source.subscribe(new FlowableFlatMapMaybe.FlatMapMaybeSubscriber<>(var1, this.mapper, this.delayErrors, this.maxConcurrency));
   }

   static final class FlatMapMaybeSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = 8600231336733376951L;
      final AtomicInteger active;
      volatile boolean cancelled;
      final boolean delayErrors;
      final Subscriber<? super R> downstream;
      final AtomicThrowable errors;
      final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
      final int maxConcurrency;
      final AtomicReference<SpscLinkedArrayQueue<R>> queue;
      final AtomicLong requested;
      final CompositeDisposable set;
      Subscription upstream;

      FlatMapMaybeSubscriber(Subscriber<? super R> var1, Function<? super T, ? extends MaybeSource<? extends R>> var2, boolean var3, int var4) {
         this.downstream = var1;
         this.mapper = var2;
         this.delayErrors = var3;
         this.maxConcurrency = var4;
         this.requested = new AtomicLong();
         this.set = new CompositeDisposable();
         this.errors = new AtomicThrowable();
         this.active = new AtomicInteger(1);
         this.queue = new AtomicReference<>();
      }

      public void cancel() {
         this.cancelled = true;
         this.upstream.cancel();
         this.set.dispose();
      }

      void clear() {
         SpscLinkedArrayQueue var1 = this.queue.get();
         if (var1 != null) {
            var1.clear();
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            this.drainLoop();
         }
      }

      void drainLoop() {
         Subscriber var11 = this.downstream;
         AtomicInteger var12 = this.active;
         AtomicReference var13 = this.queue;
         int var1 = 1;

         int var15;
         do {
            long var8 = this.requested.get();
            long var6 = 0L;

            boolean var4;
            int var5;
            while (true) {
               var4 = false;
               long var23;
               var5 = (var23 = var6 - var8) == 0L ? 0 : (var23 < 0L ? -1 : 1);
               if (!var5) {
                  break;
               }

               if (this.cancelled) {
                  this.clear();
                  return;
               }

               if (!this.delayErrors && this.errors.get() != null) {
                  Throwable var19 = this.errors.terminate();
                  this.clear();
                  var11.onError(var19);
                  return;
               }

               boolean var2;
               if (var12.get() == 0) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               SpscLinkedArrayQueue var10 = (SpscLinkedArrayQueue)var13.get();
               Object var17;
               if (var10 != null) {
                  var17 = var10.poll();
               } else {
                  var17 = null;
               }

               boolean var3;
               if (var17 == null) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (var2 && var3) {
                  var17 = this.errors.terminate();
                  if (var17 != null) {
                     var11.onError((Throwable)var17);
                  } else {
                     var11.onComplete();
                  }

                  return;
               }

               if (var3) {
                  break;
               }

               var11.onNext(var17);
               var6++;
            }

            if (!var5) {
               if (this.cancelled) {
                  this.clear();
                  return;
               }

               if (!this.delayErrors && this.errors.get() != null) {
                  Throwable var22 = this.errors.terminate();
                  this.clear();
                  var11.onError(var22);
                  return;
               }

               boolean var14;
               if (var12.get() == 0) {
                  var14 = true;
               } else {
                  var14 = false;
               }

               boolean var16;
               label91: {
                  SpscLinkedArrayQueue var20 = (SpscLinkedArrayQueue)var13.get();
                  if (var20 != null) {
                     var16 = var4;
                     if (!var20.isEmpty()) {
                        break label91;
                     }
                  }

                  var16 = true;
               }

               if (var14 && var16) {
                  Throwable var21 = this.errors.terminate();
                  if (var21 != null) {
                     var11.onError(var21);
                  } else {
                     var11.onComplete();
                  }

                  return;
               }
            }

            if (var6 != 0L) {
               BackpressureHelper.produced(this.requested, var6);
               if (this.maxConcurrency != Integer.MAX_VALUE) {
                  this.upstream.request(var6);
               }
            }

            var15 = this.addAndGet(-var1);
            var1 = var15;
         } while (var15 != 0);
      }

      SpscLinkedArrayQueue<R> getOrCreateQueue() {
         SpscLinkedArrayQueue var2;
         do {
            var2 = this.queue.get();
            if (var2 != null) {
               return var2;
            }

            var2 = new SpscLinkedArrayQueue(Flowable.bufferSize());
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.queue, null, var2));

         return var2;
      }

      void innerComplete(FlowableFlatMapMaybe.FlatMapMaybeSubscriber<T, R>.InnerObserver var1) {
         this.set.delete(var1);
         if (this.get() == 0) {
            boolean var2 = false;
            if (this.compareAndSet(0, 1)) {
               if (this.active.decrementAndGet() == 0) {
                  var2 = true;
               }

               SpscLinkedArrayQueue var3 = this.queue.get();
               if (!var2 || var3 != null && !var3.isEmpty()) {
                  if (this.maxConcurrency != Integer.MAX_VALUE) {
                     this.upstream.request(1L);
                  }

                  if (this.decrementAndGet() == 0) {
                     return;
                  }

                  this.drainLoop();
                  return;
               }

               Throwable var4 = this.errors.terminate();
               if (var4 != null) {
                  this.downstream.onError(var4);
               } else {
                  this.downstream.onComplete();
               }

               return;
            }
         }

         this.active.decrementAndGet();
         if (this.maxConcurrency != Integer.MAX_VALUE) {
            this.upstream.request(1L);
         }

         this.drain();
      }

      void innerError(FlowableFlatMapMaybe.FlatMapMaybeSubscriber<T, R>.InnerObserver var1, Throwable var2) {
         this.set.delete(var1);
         if (this.errors.addThrowable(var2)) {
            if (!this.delayErrors) {
               this.upstream.cancel();
               this.set.dispose();
            } else if (this.maxConcurrency != Integer.MAX_VALUE) {
               this.upstream.request(1L);
            }

            this.active.decrementAndGet();
            this.drain();
         } else {
            RxJavaPlugins.onError(var2);
         }
      }

      void innerSuccess(FlowableFlatMapMaybe.FlatMapMaybeSubscriber<T, R>.InnerObserver param1, R param2) {
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
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.set Lio/reactivex/disposables/CompositeDisposable;
         // 04: aload 1
         // 05: invokevirtual io/reactivex/disposables/CompositeDisposable.delete (Lio/reactivex/disposables/Disposable;)Z
         // 08: pop
         // 09: aload 0
         // 0a: invokevirtual io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.get ()I
         // 0d: ifne b5
         // 10: bipush 0
         // 11: istore 3
         // 12: aload 0
         // 13: bipush 0
         // 14: bipush 1
         // 15: invokevirtual io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.compareAndSet (II)Z
         // 18: ifeq b5
         // 1b: aload 0
         // 1c: getfield io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.active Ljava/util/concurrent/atomic/AtomicInteger;
         // 1f: invokevirtual java/util/concurrent/atomic/AtomicInteger.decrementAndGet ()I
         // 22: ifne 27
         // 25: bipush 1
         // 26: istore 3
         // 27: aload 0
         // 28: getfield io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.requested Ljava/util/concurrent/atomic/AtomicLong;
         // 2b: invokevirtual java/util/concurrent/atomic/AtomicLong.get ()J
         // 2e: lconst_0
         // 2f: lcmp
         // 30: ifeq 99
         // 33: aload 0
         // 34: getfield io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 37: aload 2
         // 38: invokeinterface org/reactivestreams/Subscriber.onNext (Ljava/lang/Object;)V 2
         // 3d: aload 0
         // 3e: getfield io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.queue Ljava/util/concurrent/atomic/AtomicReference;
         // 41: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
         // 44: checkcast io/reactivex/internal/queue/SpscLinkedArrayQueue
         // 47: astore 1
         // 48: iload 3
         // 49: ifeq 7a
         // 4c: aload 1
         // 4d: ifnull 57
         // 50: aload 1
         // 51: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.isEmpty ()Z
         // 54: ifeq 7a
         // 57: aload 0
         // 58: getfield io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.errors Lio/reactivex/internal/util/AtomicThrowable;
         // 5b: invokevirtual io/reactivex/internal/util/AtomicThrowable.terminate ()Ljava/lang/Throwable;
         // 5e: astore 1
         // 5f: aload 1
         // 60: ifnull 70
         // 63: aload 0
         // 64: getfield io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 67: aload 1
         // 68: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
         // 6d: goto 79
         // 70: aload 0
         // 71: getfield io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 74: invokeinterface org/reactivestreams/Subscriber.onComplete ()V 1
         // 79: return
         // 7a: aload 0
         // 7b: getfield io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.requested Ljava/util/concurrent/atomic/AtomicLong;
         // 7e: lconst_1
         // 7f: invokestatic io/reactivex/internal/util/BackpressureHelper.produced (Ljava/util/concurrent/atomic/AtomicLong;J)J
         // 82: pop2
         // 83: aload 0
         // 84: getfield io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.maxConcurrency I
         // 87: ldc 2147483647
         // 89: if_icmpeq a8
         // 8c: aload 0
         // 8d: getfield io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.upstream Lorg/reactivestreams/Subscription;
         // 90: lconst_1
         // 91: invokeinterface org/reactivestreams/Subscription.request (J)V 3
         // 96: goto a8
         // 99: aload 0
         // 9a: invokevirtual io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.getOrCreateQueue ()Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 9d: astore 1
         // 9e: aload 1
         // 9f: monitorenter
         // a0: aload 1
         // a1: aload 2
         // a2: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;)Z
         // a5: pop
         // a6: aload 1
         // a7: monitorexit
         // a8: aload 0
         // a9: invokevirtual io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.decrementAndGet ()I
         // ac: ifne d4
         // af: return
         // b0: astore 2
         // b1: aload 1
         // b2: monitorexit
         // b3: aload 2
         // b4: athrow
         // b5: aload 0
         // b6: invokevirtual io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.getOrCreateQueue ()Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // b9: astore 1
         // ba: aload 1
         // bb: monitorenter
         // bc: aload 1
         // bd: aload 2
         // be: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;)Z
         // c1: pop
         // c2: aload 1
         // c3: monitorexit
         // c4: aload 0
         // c5: getfield io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.active Ljava/util/concurrent/atomic/AtomicInteger;
         // c8: invokevirtual java/util/concurrent/atomic/AtomicInteger.decrementAndGet ()I
         // cb: pop
         // cc: aload 0
         // cd: invokevirtual io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.getAndIncrement ()I
         // d0: ifeq d4
         // d3: return
         // d4: aload 0
         // d5: invokevirtual io/reactivex/internal/operators/flowable/FlowableFlatMapMaybe$FlatMapMaybeSubscriber.drainLoop ()V
         // d8: return
         // d9: astore 2
         // da: aload 1
         // db: monitorexit
         // dc: aload 2
         // dd: athrow
      }

      public void onComplete() {
         this.active.decrementAndGet();
         this.drain();
      }

      public void onError(Throwable var1) {
         this.active.decrementAndGet();
         if (this.errors.addThrowable(var1)) {
            if (!this.delayErrors) {
               this.set.dispose();
            }

            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         try {
            var1 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null MaybeSource");
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.upstream.cancel();
            this.onError(var4);
            return;
         }

         this.active.getAndIncrement();
         FlowableFlatMapMaybe.FlatMapMaybeSubscriber.InnerObserver var2 = new FlowableFlatMapMaybe.FlatMapMaybeSubscriber.InnerObserver(this);
         if (!this.cancelled && this.set.add(var2)) {
            var1.subscribe(var2);
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            int var2 = this.maxConcurrency;
            if (var2 == Integer.MAX_VALUE) {
               var1.request(Long.MAX_VALUE);
            } else {
               var1.request(var2);
            }
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.drain();
         }
      }

      final class InnerObserver extends AtomicReference<Disposable> implements MaybeObserver<R>, Disposable {
         private static final long serialVersionUID = -502562646270949838L;
         final FlowableFlatMapMaybe.FlatMapMaybeSubscriber this$0;

         InnerObserver(FlowableFlatMapMaybe.FlatMapMaybeSubscriber var1) {
            this.this$0 = var1;
         }

         @Override
         public void dispose() {
            DisposableHelper.dispose(this);
         }

         @Override
         public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.get());
         }

         @Override
         public void onComplete() {
            this.this$0.innerComplete(this);
         }

         @Override
         public void onError(Throwable var1) {
            this.this$0.innerError(this, var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }

         @Override
         public void onSuccess(R var1) {
            this.this$0.innerSuccess(this, var1);
         }
      }
   }
}
