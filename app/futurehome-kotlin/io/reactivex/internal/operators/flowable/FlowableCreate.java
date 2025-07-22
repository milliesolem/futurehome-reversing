package io.reactivex.internal.operators.flowable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.disposables.CancellableDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
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

public final class FlowableCreate<T> extends Flowable<T> {
   final BackpressureStrategy backpressure;
   final FlowableOnSubscribe<T> source;

   public FlowableCreate(FlowableOnSubscribe<T> var1, BackpressureStrategy var2) {
      this.source = var1;
      this.backpressure = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      int var2 = <unrepresentable>.$SwitchMap$io$reactivex$BackpressureStrategy[this.backpressure.ordinal()];
      Object var3;
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 != 3) {
               if (var2 != 4) {
                  var3 = new FlowableCreate.BufferAsyncEmitter(var1, bufferSize());
               } else {
                  var3 = new FlowableCreate.LatestAsyncEmitter(var1);
               }
            } else {
               var3 = new FlowableCreate.DropAsyncEmitter(var1);
            }
         } else {
            var3 = new FlowableCreate.ErrorAsyncEmitter(var1);
         }
      } else {
         var3 = new FlowableCreate.MissingEmitter(var1);
      }

      var1.onSubscribe((Subscription)var3);

      try {
         this.source.subscribe((FlowableEmitter<T>)var3);
      } catch (Throwable var5) {
         Exceptions.throwIfFatal(var5);
         ((FlowableCreate.BaseEmitter)var3).onError(var5);
         return;
      }
   }

   abstract static class BaseEmitter<T> extends AtomicLong implements FlowableEmitter<T>, Subscription {
      private static final long serialVersionUID = 7326289992464377023L;
      final Subscriber<? super T> downstream;
      final SequentialDisposable serial;

      BaseEmitter(Subscriber<? super T> var1) {
         this.downstream = var1;
         this.serial = new SequentialDisposable();
      }

      public final void cancel() {
         this.serial.dispose();
         this.onUnsubscribed();
      }

      protected void complete() {
         if (!this.isCancelled()) {
            try {
               this.downstream.onComplete();
            } finally {
               this.serial.dispose();
            }
         }
      }

      protected boolean error(Throwable var1) {
         Object var2 = var1;
         if (var1 == null) {
            var2 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
         }

         if (this.isCancelled()) {
            return false;
         } else {
            try {
               this.downstream.onError((Throwable)var2);
            } finally {
               this.serial.dispose();
            }

            return true;
         }
      }

      @Override
      public final boolean isCancelled() {
         return this.serial.isDisposed();
      }

      @Override
      public void onComplete() {
         this.complete();
      }

      @Override
      public final void onError(Throwable var1) {
         if (!this.tryOnError(var1)) {
            RxJavaPlugins.onError(var1);
         }
      }

      void onRequested() {
      }

      void onUnsubscribed() {
      }

      public final void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this, var1);
            this.onRequested();
         }
      }

      @Override
      public final long requested() {
         return this.get();
      }

      @Override
      public final FlowableEmitter<T> serialize() {
         return new FlowableCreate.SerializedEmitter<>(this);
      }

      @Override
      public final void setCancellable(Cancellable var1) {
         this.setDisposable(new CancellableDisposable(var1));
      }

      @Override
      public final void setDisposable(Disposable var1) {
         this.serial.update(var1);
      }

      @Override
      public String toString() {
         return String.format("%s{%s}", this.getClass().getSimpleName(), super.toString());
      }

      @Override
      public boolean tryOnError(Throwable var1) {
         return this.error(var1);
      }
   }

   static final class BufferAsyncEmitter<T> extends FlowableCreate.BaseEmitter<T> {
      private static final long serialVersionUID = 2427151001689639875L;
      volatile boolean done;
      Throwable error;
      final SpscLinkedArrayQueue<T> queue;
      final AtomicInteger wip;

      BufferAsyncEmitter(Subscriber<? super T> var1, int var2) {
         super(var1);
         this.queue = new SpscLinkedArrayQueue<>(var2);
         this.wip = new AtomicInteger();
      }

      void drain() {
         if (this.wip.getAndIncrement() == 0) {
            Subscriber var11 = this.downstream;
            SpscLinkedArrayQueue var10 = this.queue;
            int var1 = 1;

            int var13;
            do {
               long var8 = this.get();
               long var6 = 0L;

               int var3;
               while (true) {
                  long var17;
                  var3 = (var17 = var6 - var8) == 0L ? 0 : (var17 < 0L ? -1 : 1);
                  if (!var3) {
                     break;
                  }

                  if (this.isCancelled()) {
                     var10.clear();
                     return;
                  }

                  boolean var4 = this.done;
                  Object var12 = var10.poll();
                  boolean var2;
                  if (var12 == null) {
                     var2 = true;
                  } else {
                     var2 = false;
                  }

                  if (var4 && var2) {
                     Throwable var15 = this.error;
                     if (var15 != null) {
                        this.error(var15);
                     } else {
                        this.complete();
                     }

                     return;
                  }

                  if (var2) {
                     break;
                  }

                  var11.onNext(var12);
                  var6++;
               }

               if (!var3) {
                  if (this.isCancelled()) {
                     var10.clear();
                     return;
                  }

                  boolean var5 = this.done;
                  boolean var14 = var10.isEmpty();
                  if (var5 && var14) {
                     Throwable var16 = this.error;
                     if (var16 != null) {
                        this.error(var16);
                     } else {
                        this.complete();
                     }

                     return;
                  }
               }

               if (var6 != 0L) {
                  BackpressureHelper.produced(this, var6);
               }

               var13 = this.wip.addAndGet(-var1);
               var1 = var13;
            } while (var13 != 0);
         }
      }

      @Override
      public void onComplete() {
         this.done = true;
         this.drain();
      }

      @Override
      public void onNext(T var1) {
         if (!this.done && !this.isCancelled()) {
            if (var1 == null) {
               this.onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
               return;
            }

            this.queue.offer((T)var1);
            this.drain();
         }
      }

      @Override
      void onRequested() {
         this.drain();
      }

      @Override
      void onUnsubscribed() {
         if (this.wip.getAndIncrement() == 0) {
            this.queue.clear();
         }
      }

      @Override
      public boolean tryOnError(Throwable var1) {
         if (!this.done && !this.isCancelled()) {
            Object var2 = var1;
            if (var1 == null) {
               var2 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }

            this.error = (Throwable)var2;
            this.done = true;
            this.drain();
            return true;
         } else {
            return false;
         }
      }
   }

   static final class DropAsyncEmitter<T> extends FlowableCreate.NoOverflowBaseAsyncEmitter<T> {
      private static final long serialVersionUID = 8360058422307496563L;

      DropAsyncEmitter(Subscriber<? super T> var1) {
         super(var1);
      }

      @Override
      void onOverflow() {
      }
   }

   static final class ErrorAsyncEmitter<T> extends FlowableCreate.NoOverflowBaseAsyncEmitter<T> {
      private static final long serialVersionUID = 338953216916120960L;

      ErrorAsyncEmitter(Subscriber<? super T> var1) {
         super(var1);
      }

      @Override
      void onOverflow() {
         this.onError(new MissingBackpressureException("create: could not emit value due to lack of requests"));
      }
   }

   static final class LatestAsyncEmitter<T> extends FlowableCreate.BaseEmitter<T> {
      private static final long serialVersionUID = 4023437720691792495L;
      volatile boolean done;
      Throwable error;
      final AtomicReference<T> queue = new AtomicReference<>();
      final AtomicInteger wip = new AtomicInteger();

      LatestAsyncEmitter(Subscriber<? super T> var1) {
         super(var1);
      }

      void drain() {
         if (this.wip.getAndIncrement() == 0) {
            Subscriber var11 = this.downstream;
            AtomicReference var10 = this.queue;
            int var1 = 1;

            int var14;
            do {
               long var7 = this.get();
               long var5 = 0L;

               boolean var3;
               int var4;
               while (true) {
                  var3 = false;
                  long var18;
                  var4 = (var18 = var5 - var7) == 0L ? 0 : (var18 < 0L ? -1 : 1);
                  if (!var4) {
                     break;
                  }

                  if (this.isCancelled()) {
                     var10.lazySet(null);
                     return;
                  }

                  boolean var9 = this.done;
                  Object var12 = var10.getAndSet(null);
                  boolean var2;
                  if (var12 == null) {
                     var2 = true;
                  } else {
                     var2 = false;
                  }

                  if (var9 && var2) {
                     Throwable var16 = this.error;
                     if (var16 != null) {
                        this.error(var16);
                     } else {
                        this.complete();
                     }

                     return;
                  }

                  if (var2) {
                     break;
                  }

                  var11.onNext(var12);
                  var5++;
               }

               if (!var4) {
                  if (this.isCancelled()) {
                     var10.lazySet(null);
                     return;
                  }

                  boolean var15 = this.done;
                  boolean var13 = var3;
                  if (var10.get() == null) {
                     var13 = true;
                  }

                  if (var15 && var13) {
                     Throwable var17 = this.error;
                     if (var17 != null) {
                        this.error(var17);
                     } else {
                        this.complete();
                     }

                     return;
                  }
               }

               if (var5 != 0L) {
                  BackpressureHelper.produced(this, var5);
               }

               var14 = this.wip.addAndGet(-var1);
               var1 = var14;
            } while (var14 != 0);
         }
      }

      @Override
      public void onComplete() {
         this.done = true;
         this.drain();
      }

      @Override
      public void onNext(T var1) {
         if (!this.done && !this.isCancelled()) {
            if (var1 == null) {
               this.onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
               return;
            }

            this.queue.set((T)var1);
            this.drain();
         }
      }

      @Override
      void onRequested() {
         this.drain();
      }

      @Override
      void onUnsubscribed() {
         if (this.wip.getAndIncrement() == 0) {
            this.queue.lazySet(null);
         }
      }

      @Override
      public boolean tryOnError(Throwable var1) {
         if (!this.done && !this.isCancelled()) {
            if (var1 == null) {
               this.onError(new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources."));
            }

            this.error = var1;
            this.done = true;
            this.drain();
            return true;
         } else {
            return false;
         }
      }
   }

   static final class MissingEmitter<T> extends FlowableCreate.BaseEmitter<T> {
      private static final long serialVersionUID = 3776720187248809713L;

      MissingEmitter(Subscriber<? super T> var1) {
         super(var1);
      }

      @Override
      public void onNext(T var1) {
         if (!this.isCancelled()) {
            if (var1 == null) {
               this.onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            } else {
               this.downstream.onNext(var1);

               long var2;
               do {
                  var2 = this.get();
               } while (var2 != 0L && !this.compareAndSet(var2, var2 - 1L));
            }
         }
      }
   }

   abstract static class NoOverflowBaseAsyncEmitter<T> extends FlowableCreate.BaseEmitter<T> {
      private static final long serialVersionUID = 4127754106204442833L;

      NoOverflowBaseAsyncEmitter(Subscriber<? super T> var1) {
         super(var1);
      }

      @Override
      public final void onNext(T var1) {
         if (!this.isCancelled()) {
            if (var1 == null) {
               this.onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            } else {
               if (this.get() != 0L) {
                  this.downstream.onNext(var1);
                  BackpressureHelper.produced(this, 1L);
               } else {
                  this.onOverflow();
               }
            }
         }
      }

      abstract void onOverflow();
   }

   static final class SerializedEmitter<T> extends AtomicInteger implements FlowableEmitter<T> {
      private static final long serialVersionUID = 4883307006032401862L;
      volatile boolean done;
      final FlowableCreate.BaseEmitter<T> emitter;
      final AtomicThrowable error;
      final SimplePlainQueue<T> queue;

      SerializedEmitter(FlowableCreate.BaseEmitter<T> var1) {
         this.emitter = var1;
         this.error = new AtomicThrowable();
         this.queue = new SpscLinkedArrayQueue<>(16);
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            this.drainLoop();
         }
      }

      void drainLoop() {
         FlowableCreate.BaseEmitter var5 = this.emitter;
         SimplePlainQueue var4 = this.queue;
         AtomicThrowable var6 = this.error;
         int var1 = 1;

         while (!var5.isCancelled()) {
            if (var6.get() != null) {
               var4.clear();
               var5.onError(var6.terminate());
               return;
            }

            boolean var3 = this.done;
            Object var7 = var4.poll();
            boolean var2;
            if (var7 == null) {
               var2 = 1;
            } else {
               var2 = 0;
            }

            if (var3 && var2) {
               var5.onComplete();
               return;
            }

            if (var2) {
               var2 = this.addAndGet(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else {
               var5.onNext(var7);
            }
         }

         var4.clear();
      }

      @Override
      public boolean isCancelled() {
         return this.emitter.isCancelled();
      }

      @Override
      public void onComplete() {
         if (!this.emitter.isCancelled() && !this.done) {
            this.done = true;
            this.drain();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (!this.tryOnError(var1)) {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(T param1) {
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
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableCreate$SerializedEmitter.emitter Lio/reactivex/internal/operators/flowable/FlowableCreate$BaseEmitter;
         // 04: invokevirtual io/reactivex/internal/operators/flowable/FlowableCreate$BaseEmitter.isCancelled ()Z
         // 07: ifne 69
         // 0a: aload 0
         // 0b: getfield io/reactivex/internal/operators/flowable/FlowableCreate$SerializedEmitter.done Z
         // 0e: ifeq 14
         // 11: goto 69
         // 14: aload 1
         // 15: ifnonnull 26
         // 18: aload 0
         // 19: new java/lang/NullPointerException
         // 1c: dup
         // 1d: ldc "onNext called with null. Null values are generally not allowed in 2.x operators and sources."
         // 1f: invokespecial java/lang/NullPointerException.<init> (Ljava/lang/String;)V
         // 22: invokevirtual io/reactivex/internal/operators/flowable/FlowableCreate$SerializedEmitter.onError (Ljava/lang/Throwable;)V
         // 25: return
         // 26: aload 0
         // 27: invokevirtual io/reactivex/internal/operators/flowable/FlowableCreate$SerializedEmitter.get ()I
         // 2a: ifne 46
         // 2d: aload 0
         // 2e: bipush 0
         // 2f: bipush 1
         // 30: invokevirtual io/reactivex/internal/operators/flowable/FlowableCreate$SerializedEmitter.compareAndSet (II)Z
         // 33: ifeq 46
         // 36: aload 0
         // 37: getfield io/reactivex/internal/operators/flowable/FlowableCreate$SerializedEmitter.emitter Lio/reactivex/internal/operators/flowable/FlowableCreate$BaseEmitter;
         // 3a: aload 1
         // 3b: invokevirtual io/reactivex/internal/operators/flowable/FlowableCreate$BaseEmitter.onNext (Ljava/lang/Object;)V
         // 3e: aload 0
         // 3f: invokevirtual io/reactivex/internal/operators/flowable/FlowableCreate$SerializedEmitter.decrementAndGet ()I
         // 42: ifne 5f
         // 45: return
         // 46: aload 0
         // 47: getfield io/reactivex/internal/operators/flowable/FlowableCreate$SerializedEmitter.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 4a: astore 2
         // 4b: aload 2
         // 4c: monitorenter
         // 4d: aload 2
         // 4e: aload 1
         // 4f: invokeinterface io/reactivex/internal/fuseable/SimplePlainQueue.offer (Ljava/lang/Object;)Z 2
         // 54: pop
         // 55: aload 2
         // 56: monitorexit
         // 57: aload 0
         // 58: invokevirtual io/reactivex/internal/operators/flowable/FlowableCreate$SerializedEmitter.getAndIncrement ()I
         // 5b: ifeq 5f
         // 5e: return
         // 5f: aload 0
         // 60: invokevirtual io/reactivex/internal/operators/flowable/FlowableCreate$SerializedEmitter.drainLoop ()V
         // 63: return
         // 64: astore 1
         // 65: aload 2
         // 66: monitorexit
         // 67: aload 1
         // 68: athrow
         // 69: return
      }

      @Override
      public long requested() {
         return this.emitter.requested();
      }

      @Override
      public FlowableEmitter<T> serialize() {
         return this;
      }

      @Override
      public void setCancellable(Cancellable var1) {
         this.emitter.setCancellable(var1);
      }

      @Override
      public void setDisposable(Disposable var1) {
         this.emitter.setDisposable(var1);
      }

      @Override
      public String toString() {
         return this.emitter.toString();
      }

      @Override
      public boolean tryOnError(Throwable var1) {
         if (!this.emitter.isCancelled() && !this.done) {
            Object var2 = var1;
            if (var1 == null) {
               var2 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }

            if (this.error.addThrowable((Throwable)var2)) {
               this.done = true;
               this.drain();
               return true;
            }
         }

         return false;
      }
   }
}
