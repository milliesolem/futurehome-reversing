package io.reactivex.internal.operators.observable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableFlatMap<T, U> extends AbstractObservableWithUpstream<T, U> {
   final int bufferSize;
   final boolean delayErrors;
   final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
   final int maxConcurrency;

   public ObservableFlatMap(ObservableSource<T> var1, Function<? super T, ? extends ObservableSource<? extends U>> var2, boolean var3, int var4, int var5) {
      super(var1);
      this.mapper = var2;
      this.delayErrors = var3;
      this.maxConcurrency = var4;
      this.bufferSize = var5;
   }

   @Override
   public void subscribeActual(Observer<? super U> var1) {
      if (!ObservableScalarXMap.tryScalarXMapSubscribe(this.source, var1, this.mapper)) {
         this.source.subscribe(new ObservableFlatMap.MergeObserver<>(var1, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
      }
   }

   static final class InnerObserver<T, U> extends AtomicReference<Disposable> implements Observer<U> {
      private static final long serialVersionUID = -4606175640614850599L;
      volatile boolean done;
      int fusionMode;
      final long id;
      final ObservableFlatMap.MergeObserver<T, U> parent;
      volatile SimpleQueue<U> queue;

      InnerObserver(ObservableFlatMap.MergeObserver<T, U> var1, long var2) {
         this.id = var2;
         this.parent = var1;
      }

      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public void onComplete() {
         this.done = true;
         this.parent.drain();
      }

      @Override
      public void onError(Throwable var1) {
         if (this.parent.errors.addThrowable(var1)) {
            if (!this.parent.delayErrors) {
               this.parent.disposeAll();
            }

            this.done = true;
            this.parent.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(U var1) {
         if (this.fusionMode == 0) {
            this.parent.tryEmit((U)var1, this);
         } else {
            this.parent.drain();
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1) && var1 instanceof QueueDisposable) {
            QueueDisposable var3 = (QueueDisposable)var1;
            int var2 = var3.requestFusion(7);
            if (var2 == 1) {
               this.fusionMode = var2;
               this.queue = var3;
               this.done = true;
               this.parent.drain();
               return;
            }

            if (var2 == 2) {
               this.fusionMode = var2;
               this.queue = var3;
            }
         }
      }
   }

   static final class MergeObserver<T, U> extends AtomicInteger implements Disposable, Observer<T> {
      static final ObservableFlatMap.InnerObserver<?, ?>[] CANCELLED = new ObservableFlatMap.InnerObserver[0];
      static final ObservableFlatMap.InnerObserver<?, ?>[] EMPTY = new ObservableFlatMap.InnerObserver[0];
      private static final long serialVersionUID = -2117620485640801370L;
      final int bufferSize;
      volatile boolean cancelled;
      final boolean delayErrors;
      volatile boolean done;
      final Observer<? super U> downstream;
      final AtomicThrowable errors = new AtomicThrowable();
      long lastId;
      int lastIndex;
      final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
      final int maxConcurrency;
      final AtomicReference<ObservableFlatMap.InnerObserver<?, ?>[]> observers;
      volatile SimplePlainQueue<U> queue;
      Queue<ObservableSource<? extends U>> sources;
      long uniqueId;
      Disposable upstream;
      int wip;

      MergeObserver(Observer<? super U> var1, Function<? super T, ? extends ObservableSource<? extends U>> var2, boolean var3, int var4, int var5) {
         this.downstream = var1;
         this.mapper = var2;
         this.delayErrors = var3;
         this.maxConcurrency = var4;
         this.bufferSize = var5;
         if (var4 != Integer.MAX_VALUE) {
            this.sources = new ArrayDeque<>(var4);
         }

         this.observers = new AtomicReference<>(EMPTY);
      }

      boolean addInner(ObservableFlatMap.InnerObserver<T, U> var1) {
         ObservableFlatMap.InnerObserver[] var3;
         ObservableFlatMap.InnerObserver[] var4;
         do {
            var3 = this.observers.get();
            if (var3 == CANCELLED) {
               var1.dispose();
               return false;
            }

            int var2 = var3.length;
            var4 = new ObservableFlatMap.InnerObserver[var2 + 1];
            System.arraycopy(var3, 0, var4, 0, var2);
            var4[var2] = var1;
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var3, var4));

         return true;
      }

      boolean checkTerminate() {
         if (this.cancelled) {
            return true;
         } else {
            Throwable var1 = this.errors.get();
            if (!this.delayErrors && var1 != null) {
               this.disposeAll();
               var1 = this.errors.terminate();
               if (var1 != ExceptionHelper.TERMINATED) {
                  this.downstream.onError(var1);
               }

               return true;
            } else {
               return false;
            }
         }
      }

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            if (this.disposeAll()) {
               Throwable var1 = this.errors.terminate();
               if (var1 != null && var1 != ExceptionHelper.TERMINATED) {
                  RxJavaPlugins.onError(var1);
               }
            }
         }
      }

      boolean disposeAll() {
         this.upstream.dispose();
         ObservableFlatMap.InnerObserver[] var4 = this.observers.get();
         ObservableFlatMap.InnerObserver[] var3 = CANCELLED;
         int var1 = 0;
         if (var4 != var3) {
            var4 = this.observers.getAndSet(var3);
            if (var4 != var3) {
               for (int var2 = var4.length; var1 < var2; var1++) {
                  var4[var1].dispose();
               }

               return true;
            }
         }

         return false;
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            this.drainLoop();
         }
      }

      void drainLoop() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 000: aload 0
         // 001: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.downstream Lio/reactivex/Observer;
         // 004: astore 11
         // 006: bipush 1
         // 007: istore 4
         // 009: aload 0
         // 00a: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.checkTerminate ()Z
         // 00d: ifeq 011
         // 010: return
         // 011: aload 0
         // 012: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 015: astore 13
         // 017: aload 13
         // 019: ifnull 041
         // 01c: aload 0
         // 01d: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.checkTerminate ()Z
         // 020: ifeq 024
         // 023: return
         // 024: aload 13
         // 026: invokeinterface io/reactivex/internal/fuseable/SimplePlainQueue.poll ()Ljava/lang/Object; 1
         // 02b: astore 12
         // 02d: aload 12
         // 02f: ifnonnull 035
         // 032: goto 041
         // 035: aload 11
         // 037: aload 12
         // 039: invokeinterface io/reactivex/Observer.onNext (Ljava/lang/Object;)V 2
         // 03e: goto 01c
         // 041: aload 0
         // 042: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.done Z
         // 045: istore 8
         // 047: aload 0
         // 048: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 04b: astore 13
         // 04d: aload 0
         // 04e: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.observers Ljava/util/concurrent/atomic/AtomicReference;
         // 051: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
         // 054: checkcast [Lio/reactivex/internal/operators/observable/ObservableFlatMap$InnerObserver;
         // 057: astore 12
         // 059: aload 12
         // 05b: arraylength
         // 05c: istore 7
         // 05e: aload 0
         // 05f: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.maxConcurrency I
         // 062: istore 1
         // 063: bipush 0
         // 064: istore 2
         // 065: iload 1
         // 066: ldc 2147483647
         // 068: if_icmpeq 083
         // 06b: aload 0
         // 06c: monitorenter
         // 06d: aload 0
         // 06e: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.sources Ljava/util/Queue;
         // 071: invokeinterface java/util/Queue.size ()I 1
         // 076: istore 1
         // 077: aload 0
         // 078: monitorexit
         // 079: goto 085
         // 07c: astore 11
         // 07e: aload 0
         // 07f: monitorexit
         // 080: aload 11
         // 082: athrow
         // 083: bipush 0
         // 084: istore 1
         // 085: iload 8
         // 087: ifeq 0cc
         // 08a: aload 13
         // 08c: ifnull 099
         // 08f: aload 13
         // 091: invokeinterface io/reactivex/internal/fuseable/SimplePlainQueue.isEmpty ()Z 1
         // 096: ifeq 0cc
         // 099: iload 7
         // 09b: ifne 0cc
         // 09e: iload 1
         // 09f: ifne 0cc
         // 0a2: aload 0
         // 0a3: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.errors Lio/reactivex/internal/util/AtomicThrowable;
         // 0a6: invokevirtual io/reactivex/internal/util/AtomicThrowable.terminate ()Ljava/lang/Throwable;
         // 0a9: astore 12
         // 0ab: aload 12
         // 0ad: getstatic io/reactivex/internal/util/ExceptionHelper.TERMINATED Ljava/lang/Throwable;
         // 0b0: if_acmpeq 0cb
         // 0b3: aload 12
         // 0b5: ifnonnull 0c2
         // 0b8: aload 11
         // 0ba: invokeinterface io/reactivex/Observer.onComplete ()V 1
         // 0bf: goto 0cb
         // 0c2: aload 11
         // 0c4: aload 12
         // 0c6: invokeinterface io/reactivex/Observer.onError (Ljava/lang/Throwable;)V 2
         // 0cb: return
         // 0cc: iload 2
         // 0cd: istore 1
         // 0ce: iload 7
         // 0d0: ifeq 224
         // 0d3: aload 0
         // 0d4: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.lastId J
         // 0d7: lstore 9
         // 0d9: aload 0
         // 0da: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.lastIndex I
         // 0dd: istore 2
         // 0de: iload 7
         // 0e0: iload 2
         // 0e1: if_icmple 0f3
         // 0e4: iload 2
         // 0e5: istore 1
         // 0e6: aload 12
         // 0e8: iload 2
         // 0e9: aaload
         // 0ea: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$InnerObserver.id J
         // 0ed: lload 9
         // 0ef: lcmp
         // 0f0: ifeq 139
         // 0f3: iload 2
         // 0f4: istore 1
         // 0f5: iload 7
         // 0f7: iload 2
         // 0f8: if_icmpgt 0fd
         // 0fb: bipush 0
         // 0fc: istore 1
         // 0fd: bipush 0
         // 0fe: istore 2
         // 0ff: iload 2
         // 100: iload 7
         // 102: if_icmpge 129
         // 105: aload 12
         // 107: iload 1
         // 108: aaload
         // 109: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$InnerObserver.id J
         // 10c: lload 9
         // 10e: lcmp
         // 10f: ifne 115
         // 112: goto 129
         // 115: iload 1
         // 116: bipush 1
         // 117: iadd
         // 118: istore 3
         // 119: iload 3
         // 11a: istore 1
         // 11b: iload 3
         // 11c: iload 7
         // 11e: if_icmpne 123
         // 121: bipush 0
         // 122: istore 1
         // 123: iinc 2 1
         // 126: goto 0ff
         // 129: aload 0
         // 12a: iload 1
         // 12b: putfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.lastIndex I
         // 12e: aload 0
         // 12f: aload 12
         // 131: iload 1
         // 132: aaload
         // 133: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$InnerObserver.id J
         // 136: putfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.lastId J
         // 139: bipush 0
         // 13a: istore 5
         // 13c: bipush 0
         // 13d: istore 2
         // 13e: iload 5
         // 140: iload 7
         // 142: if_icmpge 212
         // 145: aload 0
         // 146: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.checkTerminate ()Z
         // 149: ifeq 14d
         // 14c: return
         // 14d: aload 12
         // 14f: iload 1
         // 150: aaload
         // 151: astore 13
         // 153: aload 13
         // 155: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$InnerObserver.queue Lio/reactivex/internal/fuseable/SimpleQueue;
         // 158: astore 15
         // 15a: aload 15
         // 15c: ifnull 1bf
         // 15f: aload 15
         // 161: invokeinterface io/reactivex/internal/fuseable/SimpleQueue.poll ()Ljava/lang/Object; 1
         // 166: astore 14
         // 168: aload 14
         // 16a: ifnonnull 170
         // 16d: goto 1bf
         // 170: aload 11
         // 172: aload 14
         // 174: invokeinterface io/reactivex/Observer.onNext (Ljava/lang/Object;)V 2
         // 179: aload 0
         // 17a: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.checkTerminate ()Z
         // 17d: ifeq 15f
         // 180: return
         // 181: astore 14
         // 183: aload 14
         // 185: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 188: aload 13
         // 18a: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$InnerObserver.dispose ()V
         // 18d: aload 0
         // 18e: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.errors Lio/reactivex/internal/util/AtomicThrowable;
         // 191: aload 14
         // 193: invokevirtual io/reactivex/internal/util/AtomicThrowable.addThrowable (Ljava/lang/Throwable;)Z
         // 196: pop
         // 197: aload 0
         // 198: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.checkTerminate ()Z
         // 19b: ifeq 19f
         // 19e: return
         // 19f: aload 0
         // 1a0: aload 13
         // 1a2: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.removeInner (Lio/reactivex/internal/operators/observable/ObservableFlatMap$InnerObserver;)V
         // 1a5: iload 2
         // 1a6: bipush 1
         // 1a7: iadd
         // 1a8: istore 3
         // 1a9: iload 1
         // 1aa: bipush 1
         // 1ab: iadd
         // 1ac: istore 6
         // 1ae: iload 3
         // 1af: istore 2
         // 1b0: iload 6
         // 1b2: istore 1
         // 1b3: iload 6
         // 1b5: iload 7
         // 1b7: if_icmpne 20c
         // 1ba: iload 3
         // 1bb: istore 2
         // 1bc: goto 20a
         // 1bf: aload 13
         // 1c1: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$InnerObserver.done Z
         // 1c4: istore 8
         // 1c6: aload 13
         // 1c8: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$InnerObserver.queue Lio/reactivex/internal/fuseable/SimpleQueue;
         // 1cb: astore 14
         // 1cd: iload 2
         // 1ce: istore 3
         // 1cf: iload 8
         // 1d1: ifeq 1f7
         // 1d4: aload 14
         // 1d6: ifnull 1e5
         // 1d9: iload 2
         // 1da: istore 3
         // 1db: aload 14
         // 1dd: invokeinterface io/reactivex/internal/fuseable/SimpleQueue.isEmpty ()Z 1
         // 1e2: ifeq 1f7
         // 1e5: aload 0
         // 1e6: aload 13
         // 1e8: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.removeInner (Lio/reactivex/internal/operators/observable/ObservableFlatMap$InnerObserver;)V
         // 1eb: aload 0
         // 1ec: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.checkTerminate ()Z
         // 1ef: ifeq 1f3
         // 1f2: return
         // 1f3: iload 2
         // 1f4: bipush 1
         // 1f5: iadd
         // 1f6: istore 3
         // 1f7: iload 1
         // 1f8: bipush 1
         // 1f9: iadd
         // 1fa: istore 6
         // 1fc: iload 3
         // 1fd: istore 2
         // 1fe: iload 6
         // 200: istore 1
         // 201: iload 6
         // 203: iload 7
         // 205: if_icmpne 20c
         // 208: iload 3
         // 209: istore 2
         // 20a: bipush 0
         // 20b: istore 1
         // 20c: iinc 5 1
         // 20f: goto 13e
         // 212: aload 0
         // 213: iload 1
         // 214: putfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.lastIndex I
         // 217: aload 0
         // 218: aload 12
         // 21a: iload 1
         // 21b: aaload
         // 21c: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$InnerObserver.id J
         // 21f: putfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.lastId J
         // 222: iload 2
         // 223: istore 1
         // 224: iload 1
         // 225: ifeq 26e
         // 228: aload 0
         // 229: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.maxConcurrency I
         // 22c: ldc 2147483647
         // 22e: if_icmpeq 009
         // 231: iload 1
         // 232: ifeq 009
         // 235: aload 0
         // 236: monitorenter
         // 237: aload 0
         // 238: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.sources Ljava/util/Queue;
         // 23b: invokeinterface java/util/Queue.poll ()Ljava/lang/Object; 1
         // 240: checkcast io/reactivex/ObservableSource
         // 243: astore 12
         // 245: aload 12
         // 247: ifnonnull 259
         // 24a: aload 0
         // 24b: aload 0
         // 24c: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.wip I
         // 24f: bipush 1
         // 250: isub
         // 251: putfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.wip I
         // 254: aload 0
         // 255: monitorexit
         // 256: goto 261
         // 259: aload 0
         // 25a: monitorexit
         // 25b: aload 0
         // 25c: aload 12
         // 25e: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.subscribeInner (Lio/reactivex/ObservableSource;)V
         // 261: iinc 1 -1
         // 264: goto 231
         // 267: astore 11
         // 269: aload 0
         // 26a: monitorexit
         // 26b: aload 11
         // 26d: athrow
         // 26e: aload 0
         // 26f: iload 4
         // 271: ineg
         // 272: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.addAndGet (I)I
         // 275: istore 1
         // 276: iload 1
         // 277: istore 4
         // 279: iload 1
         // 27a: ifne 009
         // 27d: return
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.drain();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            if (this.errors.addThrowable(var1)) {
               this.done = true;
               this.drain();
            } else {
               RxJavaPlugins.onError(var1);
            }
         }
      }

      @Override
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
         // 01: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.done Z
         // 04: ifeq 08
         // 07: return
         // 08: aload 0
         // 09: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.mapper Lio/reactivex/functions/Function;
         // 0c: aload 1
         // 0d: invokeinterface io/reactivex/functions/Function.apply (Ljava/lang/Object;)Ljava/lang/Object; 2
         // 12: ldc "The mapper returned a null ObservableSource"
         // 14: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 17: checkcast io/reactivex/ObservableSource
         // 1a: astore 1
         // 1b: aload 0
         // 1c: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.maxConcurrency I
         // 1f: ldc 2147483647
         // 21: if_icmpeq 52
         // 24: aload 0
         // 25: monitorenter
         // 26: aload 0
         // 27: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.wip I
         // 2a: istore 2
         // 2b: iload 2
         // 2c: aload 0
         // 2d: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.maxConcurrency I
         // 30: if_icmpne 41
         // 33: aload 0
         // 34: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.sources Ljava/util/Queue;
         // 37: aload 1
         // 38: invokeinterface java/util/Queue.offer (Ljava/lang/Object;)Z 2
         // 3d: pop
         // 3e: aload 0
         // 3f: monitorexit
         // 40: return
         // 41: aload 0
         // 42: iload 2
         // 43: bipush 1
         // 44: iadd
         // 45: putfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.wip I
         // 48: aload 0
         // 49: monitorexit
         // 4a: goto 52
         // 4d: astore 1
         // 4e: aload 0
         // 4f: monitorexit
         // 50: aload 1
         // 51: athrow
         // 52: aload 0
         // 53: aload 1
         // 54: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.subscribeInner (Lio/reactivex/ObservableSource;)V
         // 57: return
         // 58: astore 1
         // 59: aload 1
         // 5a: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 5d: aload 0
         // 5e: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.upstream Lio/reactivex/disposables/Disposable;
         // 61: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
         // 66: aload 0
         // 67: aload 1
         // 68: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.onError (Ljava/lang/Throwable;)V
         // 6b: return
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      void removeInner(ObservableFlatMap.InnerObserver<T, U> var1) {
         ObservableFlatMap.InnerObserver[] var4;
         ObservableFlatMap.InnerObserver[] var5;
         do {
            var5 = this.observers.get();
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

               if (var5[var2] == var1) {
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
               var4 = new ObservableFlatMap.InnerObserver[var3 - 1];
               System.arraycopy(var5, 0, var4, 0, var2);
               System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
            }
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var5, var4));
      }

      void subscribeInner(ObservableSource<? extends U> param1) {
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
         // 01: instanceof java/util/concurrent/Callable
         // 04: ifeq 53
         // 07: aload 0
         // 08: aload 1
         // 09: checkcast java/util/concurrent/Callable
         // 0c: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.tryEmitScalar (Ljava/util/concurrent/Callable;)Z
         // 0f: ifeq 7e
         // 12: aload 0
         // 13: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.maxConcurrency I
         // 16: ldc 2147483647
         // 18: if_icmpeq 7e
         // 1b: aload 0
         // 1c: monitorenter
         // 1d: aload 0
         // 1e: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.sources Ljava/util/Queue;
         // 21: invokeinterface java/util/Queue.poll ()Ljava/lang/Object; 1
         // 26: checkcast io/reactivex/ObservableSource
         // 29: astore 1
         // 2a: aload 1
         // 2b: ifnonnull 3f
         // 2e: aload 0
         // 2f: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.wip I
         // 32: istore 3
         // 33: bipush 1
         // 34: istore 2
         // 35: aload 0
         // 36: iload 3
         // 37: bipush 1
         // 38: isub
         // 39: putfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.wip I
         // 3c: goto 41
         // 3f: bipush 0
         // 40: istore 2
         // 41: aload 0
         // 42: monitorexit
         // 43: iload 2
         // 44: ifeq 00
         // 47: aload 0
         // 48: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.drain ()V
         // 4b: goto 7e
         // 4e: astore 1
         // 4f: aload 0
         // 50: monitorexit
         // 51: aload 1
         // 52: athrow
         // 53: aload 0
         // 54: getfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.uniqueId J
         // 57: lstore 4
         // 59: aload 0
         // 5a: lconst_1
         // 5b: lload 4
         // 5d: ladd
         // 5e: putfield io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.uniqueId J
         // 61: new io/reactivex/internal/operators/observable/ObservableFlatMap$InnerObserver
         // 64: dup
         // 65: aload 0
         // 66: lload 4
         // 68: invokespecial io/reactivex/internal/operators/observable/ObservableFlatMap$InnerObserver.<init> (Lio/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver;J)V
         // 6b: astore 6
         // 6d: aload 0
         // 6e: aload 6
         // 70: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMap$MergeObserver.addInner (Lio/reactivex/internal/operators/observable/ObservableFlatMap$InnerObserver;)Z
         // 73: ifeq 7e
         // 76: aload 1
         // 77: aload 6
         // 79: invokeinterface io/reactivex/ObservableSource.subscribe (Lio/reactivex/Observer;)V 2
         // 7e: return
      }

      void tryEmit(U var1, ObservableFlatMap.InnerObserver<T, U> var2) {
         if (this.get() == 0 && this.compareAndSet(0, 1)) {
            this.downstream.onNext((U)var1);
            if (this.decrementAndGet() == 0) {
               return;
            }
         } else {
            SimpleQueue var4 = var2.queue;
            Object var3 = var4;
            if (var4 == null) {
               var3 = new SpscLinkedArrayQueue(this.bufferSize);
               var2.queue = (SimpleQueue<U>)var3;
            }

            ((SimpleQueue<Object>)var3).offer(var1);
            if (this.getAndIncrement() != 0) {
               return;
            }
         }

         this.drainLoop();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      boolean tryEmitScalar(Callable<? extends U> var1) {
         Object var3;
         try {
            var3 = var1.call();
         } catch (Throwable var5) {
            Exceptions.throwIfFatal(var5);
            this.errors.addThrowable(var5);
            this.drain();
            return true;
         }

         if (var3 == null) {
            return true;
         } else {
            if (this.get() == 0 && this.compareAndSet(0, 1)) {
               this.downstream.onNext((U)var3);
               if (this.decrementAndGet() == 0) {
                  return true;
               }
            } else {
               SimplePlainQueue var2 = this.queue;
               Object var6 = var2;
               if (var2 == null) {
                  if (this.maxConcurrency == Integer.MAX_VALUE) {
                     var6 = new SpscLinkedArrayQueue(this.bufferSize);
                  } else {
                     var6 = new SpscArrayQueue(this.maxConcurrency);
                  }

                  this.queue = (SimplePlainQueue<U>)var6;
               }

               if (!((SimplePlainQueue<Object>)var6).offer(var3)) {
                  this.onError(new IllegalStateException("Scalar queue full?!"));
                  return true;
               }

               if (this.getAndIncrement() != 0) {
                  return false;
               }
            }

            this.drainLoop();
            return true;
         }
      }
   }
}
