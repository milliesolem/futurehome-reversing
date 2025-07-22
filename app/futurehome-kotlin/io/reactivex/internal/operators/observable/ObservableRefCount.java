package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableRefCount<T> extends Observable<T> {
   ObservableRefCount.RefConnection connection;
   final int n;
   final Scheduler scheduler;
   final ConnectableObservable<T> source;
   final long timeout;
   final TimeUnit unit;

   public ObservableRefCount(ConnectableObservable<T> var1) {
      this(var1, 1, 0L, TimeUnit.NANOSECONDS, null);
   }

   public ObservableRefCount(ConnectableObservable<T> var1, int var2, long var3, TimeUnit var5, Scheduler var6) {
      this.source = var1;
      this.n = var2;
      this.timeout = var3;
      this.unit = var5;
      this.scheduler = var6;
   }

   void cancel(ObservableRefCount.RefConnection param1) {
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
      // 03: getfield io/reactivex/internal/operators/observable/ObservableRefCount.connection Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;
      // 06: astore 4
      // 08: aload 4
      // 0a: ifnull 6f
      // 0d: aload 4
      // 0f: aload 1
      // 10: if_acmpeq 16
      // 13: goto 6f
      // 16: aload 1
      // 17: getfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.subscriberCount J
      // 1a: lconst_1
      // 1b: lsub
      // 1c: lstore 2
      // 1d: aload 1
      // 1e: lload 2
      // 1f: putfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.subscriberCount J
      // 22: lload 2
      // 23: lconst_0
      // 24: lcmp
      // 25: ifne 6c
      // 28: aload 1
      // 29: getfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.connected Z
      // 2c: ifne 32
      // 2f: goto 6c
      // 32: aload 0
      // 33: getfield io/reactivex/internal/operators/observable/ObservableRefCount.timeout J
      // 36: lconst_0
      // 37: lcmp
      // 38: ifne 43
      // 3b: aload 0
      // 3c: aload 1
      // 3d: invokevirtual io/reactivex/internal/operators/observable/ObservableRefCount.timeout (Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;)V
      // 40: aload 0
      // 41: monitorexit
      // 42: return
      // 43: new io/reactivex/internal/disposables/SequentialDisposable
      // 46: astore 4
      // 48: aload 4
      // 4a: invokespecial io/reactivex/internal/disposables/SequentialDisposable.<init> ()V
      // 4d: aload 1
      // 4e: aload 4
      // 50: putfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.timer Lio/reactivex/disposables/Disposable;
      // 53: aload 0
      // 54: monitorexit
      // 55: aload 4
      // 57: aload 0
      // 58: getfield io/reactivex/internal/operators/observable/ObservableRefCount.scheduler Lio/reactivex/Scheduler;
      // 5b: aload 1
      // 5c: aload 0
      // 5d: getfield io/reactivex/internal/operators/observable/ObservableRefCount.timeout J
      // 60: aload 0
      // 61: getfield io/reactivex/internal/operators/observable/ObservableRefCount.unit Ljava/util/concurrent/TimeUnit;
      // 64: invokevirtual io/reactivex/Scheduler.scheduleDirect (Ljava/lang/Runnable;JLjava/util/concurrent/TimeUnit;)Lio/reactivex/disposables/Disposable;
      // 67: invokevirtual io/reactivex/internal/disposables/SequentialDisposable.replace (Lio/reactivex/disposables/Disposable;)Z
      // 6a: pop
      // 6b: return
      // 6c: aload 0
      // 6d: monitorexit
      // 6e: return
      // 6f: aload 0
      // 70: monitorexit
      // 71: return
      // 72: astore 1
      // 73: aload 0
      // 74: monitorexit
      // 75: aload 1
      // 76: athrow
   }

   void clearTimer(ObservableRefCount.RefConnection var1) {
      if (var1.timer != null) {
         var1.timer.dispose();
         var1.timer = null;
      }
   }

   void reset(ObservableRefCount.RefConnection var1) {
      ConnectableObservable var2 = this.source;
      if (var2 instanceof Disposable) {
         ((Disposable)var2).dispose();
      } else if (var2 instanceof ResettableConnectable) {
         ((ResettableConnectable)var2).resetIf(var1.get());
      }
   }

   @Override
   protected void subscribeActual(Observer<? super T> param1) {
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
      // 03: getfield io/reactivex/internal/operators/observable/ObservableRefCount.connection Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;
      // 06: astore 6
      // 08: aload 6
      // 0a: astore 5
      // 0c: aload 6
      // 0e: ifnonnull 22
      // 11: new io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection
      // 14: astore 5
      // 16: aload 5
      // 18: aload 0
      // 19: invokespecial io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.<init> (Lio/reactivex/internal/operators/observable/ObservableRefCount;)V
      // 1c: aload 0
      // 1d: aload 5
      // 1f: putfield io/reactivex/internal/operators/observable/ObservableRefCount.connection Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;
      // 22: aload 5
      // 24: getfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.subscriberCount J
      // 27: lstore 3
      // 28: lload 3
      // 29: lconst_0
      // 2a: lcmp
      // 2b: ifne 40
      // 2e: aload 5
      // 30: getfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.timer Lio/reactivex/disposables/Disposable;
      // 33: ifnull 40
      // 36: aload 5
      // 38: getfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.timer Lio/reactivex/disposables/Disposable;
      // 3b: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
      // 40: lload 3
      // 41: lconst_1
      // 42: ladd
      // 43: lstore 3
      // 44: aload 5
      // 46: lload 3
      // 47: putfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.subscriberCount J
      // 4a: aload 5
      // 4c: getfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.connected Z
      // 4f: ifne 67
      // 52: lload 3
      // 53: aload 0
      // 54: getfield io/reactivex/internal/operators/observable/ObservableRefCount.n I
      // 57: i2l
      // 58: lcmp
      // 59: ifne 67
      // 5c: bipush 1
      // 5d: istore 2
      // 5e: aload 5
      // 60: bipush 1
      // 61: putfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.connected Z
      // 64: goto 69
      // 67: bipush 0
      // 68: istore 2
      // 69: aload 0
      // 6a: monitorexit
      // 6b: aload 0
      // 6c: getfield io/reactivex/internal/operators/observable/ObservableRefCount.source Lio/reactivex/observables/ConnectableObservable;
      // 6f: new io/reactivex/internal/operators/observable/ObservableRefCount$RefCountObserver
      // 72: dup
      // 73: aload 1
      // 74: aload 0
      // 75: aload 5
      // 77: invokespecial io/reactivex/internal/operators/observable/ObservableRefCount$RefCountObserver.<init> (Lio/reactivex/Observer;Lio/reactivex/internal/operators/observable/ObservableRefCount;Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;)V
      // 7a: invokevirtual io/reactivex/observables/ConnectableObservable.subscribe (Lio/reactivex/Observer;)V
      // 7d: iload 2
      // 7e: ifeq 8a
      // 81: aload 0
      // 82: getfield io/reactivex/internal/operators/observable/ObservableRefCount.source Lio/reactivex/observables/ConnectableObservable;
      // 85: aload 5
      // 87: invokevirtual io/reactivex/observables/ConnectableObservable.connect (Lio/reactivex/functions/Consumer;)V
      // 8a: return
      // 8b: astore 1
      // 8c: aload 0
      // 8d: monitorexit
      // 8e: aload 1
      // 8f: athrow
   }

   void terminated(ObservableRefCount.RefConnection param1) {
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
      // 03: getfield io/reactivex/internal/operators/observable/ObservableRefCount.source Lio/reactivex/observables/ConnectableObservable;
      // 06: instanceof io/reactivex/internal/operators/observable/ObservablePublishClassic
      // 09: ifeq 41
      // 0c: aload 0
      // 0d: getfield io/reactivex/internal/operators/observable/ObservableRefCount.connection Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;
      // 10: astore 4
      // 12: aload 4
      // 14: ifnull 27
      // 17: aload 4
      // 19: aload 1
      // 1a: if_acmpne 27
      // 1d: aload 0
      // 1e: aconst_null
      // 1f: putfield io/reactivex/internal/operators/observable/ObservableRefCount.connection Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;
      // 22: aload 0
      // 23: aload 1
      // 24: invokevirtual io/reactivex/internal/operators/observable/ObservableRefCount.clearTimer (Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;)V
      // 27: aload 1
      // 28: getfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.subscriberCount J
      // 2b: lconst_1
      // 2c: lsub
      // 2d: lstore 2
      // 2e: aload 1
      // 2f: lload 2
      // 30: putfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.subscriberCount J
      // 33: lload 2
      // 34: lconst_0
      // 35: lcmp
      // 36: ifne 73
      // 39: aload 0
      // 3a: aload 1
      // 3b: invokevirtual io/reactivex/internal/operators/observable/ObservableRefCount.reset (Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;)V
      // 3e: goto 73
      // 41: aload 0
      // 42: getfield io/reactivex/internal/operators/observable/ObservableRefCount.connection Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;
      // 45: astore 4
      // 47: aload 4
      // 49: ifnull 73
      // 4c: aload 4
      // 4e: aload 1
      // 4f: if_acmpne 73
      // 52: aload 0
      // 53: aload 1
      // 54: invokevirtual io/reactivex/internal/operators/observable/ObservableRefCount.clearTimer (Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;)V
      // 57: aload 1
      // 58: getfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.subscriberCount J
      // 5b: lconst_1
      // 5c: lsub
      // 5d: lstore 2
      // 5e: aload 1
      // 5f: lload 2
      // 60: putfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.subscriberCount J
      // 63: lload 2
      // 64: lconst_0
      // 65: lcmp
      // 66: ifne 73
      // 69: aload 0
      // 6a: aconst_null
      // 6b: putfield io/reactivex/internal/operators/observable/ObservableRefCount.connection Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;
      // 6e: aload 0
      // 6f: aload 1
      // 70: invokevirtual io/reactivex/internal/operators/observable/ObservableRefCount.reset (Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;)V
      // 73: aload 0
      // 74: monitorexit
      // 75: return
      // 76: astore 1
      // 77: aload 0
      // 78: monitorexit
      // 79: aload 1
      // 7a: athrow
   }

   void timeout(ObservableRefCount.RefConnection param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 1
      // 03: getfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.subscriberCount J
      // 06: lconst_0
      // 07: lcmp
      // 08: ifne 5a
      // 0b: aload 1
      // 0c: aload 0
      // 0d: getfield io/reactivex/internal/operators/observable/ObservableRefCount.connection Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;
      // 10: if_acmpne 5a
      // 13: aload 0
      // 14: aconst_null
      // 15: putfield io/reactivex/internal/operators/observable/ObservableRefCount.connection Lio/reactivex/internal/operators/observable/ObservableRefCount$RefConnection;
      // 18: aload 1
      // 19: invokevirtual io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.get ()Ljava/lang/Object;
      // 1c: checkcast io/reactivex/disposables/Disposable
      // 1f: astore 3
      // 20: aload 1
      // 21: invokestatic io/reactivex/internal/disposables/DisposableHelper.dispose (Ljava/util/concurrent/atomic/AtomicReference;)Z
      // 24: pop
      // 25: aload 0
      // 26: getfield io/reactivex/internal/operators/observable/ObservableRefCount.source Lio/reactivex/observables/ConnectableObservable;
      // 29: astore 2
      // 2a: aload 2
      // 2b: instanceof io/reactivex/disposables/Disposable
      // 2e: ifeq 3d
      // 31: aload 2
      // 32: checkcast io/reactivex/disposables/Disposable
      // 35: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
      // 3a: goto 5a
      // 3d: aload 2
      // 3e: instanceof io/reactivex/internal/disposables/ResettableConnectable
      // 41: ifeq 5a
      // 44: aload 3
      // 45: ifnonnull 50
      // 48: aload 1
      // 49: bipush 1
      // 4a: putfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.disconnectedEarly Z
      // 4d: goto 5a
      // 50: aload 2
      // 51: checkcast io/reactivex/internal/disposables/ResettableConnectable
      // 54: aload 3
      // 55: invokeinterface io/reactivex/internal/disposables/ResettableConnectable.resetIf (Lio/reactivex/disposables/Disposable;)V 2
      // 5a: aload 0
      // 5b: monitorexit
      // 5c: return
      // 5d: astore 1
      // 5e: aload 0
      // 5f: monitorexit
      // 60: aload 1
      // 61: athrow
   }

   static final class RefConnection extends AtomicReference<Disposable> implements Runnable, Consumer<Disposable> {
      private static final long serialVersionUID = -4552101107598366241L;
      boolean connected;
      boolean disconnectedEarly;
      final ObservableRefCount<?> parent;
      long subscriberCount;
      Disposable timer;

      RefConnection(ObservableRefCount<?> var1) {
         this.parent = var1;
      }

      public void accept(Disposable param1) throws Exception {
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
         // 01: aload 1
         // 02: invokestatic io/reactivex/internal/disposables/DisposableHelper.replace (Ljava/util/concurrent/atomic/AtomicReference;Lio/reactivex/disposables/Disposable;)Z
         // 05: pop
         // 06: aload 0
         // 07: getfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.parent Lio/reactivex/internal/operators/observable/ObservableRefCount;
         // 0a: astore 2
         // 0b: aload 2
         // 0c: monitorenter
         // 0d: aload 0
         // 0e: getfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.disconnectedEarly Z
         // 11: ifeq 24
         // 14: aload 0
         // 15: getfield io/reactivex/internal/operators/observable/ObservableRefCount$RefConnection.parent Lio/reactivex/internal/operators/observable/ObservableRefCount;
         // 18: getfield io/reactivex/internal/operators/observable/ObservableRefCount.source Lio/reactivex/observables/ConnectableObservable;
         // 1b: checkcast io/reactivex/internal/disposables/ResettableConnectable
         // 1e: aload 1
         // 1f: invokeinterface io/reactivex/internal/disposables/ResettableConnectable.resetIf (Lio/reactivex/disposables/Disposable;)V 2
         // 24: aload 2
         // 25: monitorexit
         // 26: return
         // 27: astore 1
         // 28: aload 2
         // 29: monitorexit
         // 2a: aload 1
         // 2b: athrow
      }

      @Override
      public void run() {
         this.parent.timeout(this);
      }
   }

   static final class RefCountObserver<T> extends AtomicBoolean implements Observer<T>, Disposable {
      private static final long serialVersionUID = -7419642935409022375L;
      final ObservableRefCount.RefConnection connection;
      final Observer<? super T> downstream;
      final ObservableRefCount<T> parent;
      Disposable upstream;

      RefCountObserver(Observer<? super T> var1, ObservableRefCount<T> var2, ObservableRefCount.RefConnection var3) {
         this.downstream = var1;
         this.parent = var2;
         this.connection = var3;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         if (this.compareAndSet(false, true)) {
            this.parent.cancel(this.connection);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         if (this.compareAndSet(false, true)) {
            this.parent.terminated(this.connection);
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.compareAndSet(false, true)) {
            this.parent.terminated(this.connection);
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         this.downstream.onNext((T)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }
}
