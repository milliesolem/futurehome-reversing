package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableCombineLatest<T, R> extends Observable<R> {
   final int bufferSize;
   final Function<? super Object[], ? extends R> combiner;
   final boolean delayError;
   final ObservableSource<? extends T>[] sources;
   final Iterable<? extends ObservableSource<? extends T>> sourcesIterable;

   public ObservableCombineLatest(
      ObservableSource<? extends T>[] var1,
      Iterable<? extends ObservableSource<? extends T>> var2,
      Function<? super Object[], ? extends R> var3,
      int var4,
      boolean var5
   ) {
      this.sources = var1;
      this.sourcesIterable = var2;
      this.combiner = var3;
      this.bufferSize = var4;
      this.delayError = var5;
   }

   @Override
   public void subscribeActual(Observer<? super R> var1) {
      ObservableSource[] var5 = this.sources;
      int var3;
      if (var5 == null) {
         ObservableSource[] var4 = new ObservableSource[8];
         Iterator var6 = this.sourcesIterable.iterator();
         int var2 = 0;

         while (true) {
            var5 = var4;
            var3 = var2;
            if (!var6.hasNext()) {
               break;
            }

            ObservableSource var7 = (ObservableSource)var6.next();
            var5 = var4;
            if (var2 == var4.length) {
               var5 = new ObservableSource[(var2 >> 2) + var2];
               System.arraycopy(var4, 0, var5, 0, var2);
            }

            var5[var2] = var7;
            var2++;
            var4 = var5;
         }
      } else {
         var3 = var5.length;
      }

      if (var3 == 0) {
         EmptyDisposable.complete(var1);
      } else {
         new ObservableCombineLatest.LatestCoordinator<T, R>(var1, this.combiner, var3, this.bufferSize, this.delayError).subscribe(var5);
      }
   }

   static final class CombinerObserver<T, R> extends AtomicReference<Disposable> implements Observer<T> {
      private static final long serialVersionUID = -4823716997131257941L;
      final int index;
      final ObservableCombineLatest.LatestCoordinator<T, R> parent;

      CombinerObserver(ObservableCombineLatest.LatestCoordinator<T, R> var1, int var2) {
         this.parent = var1;
         this.index = var2;
      }

      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public void onComplete() {
         this.parent.innerComplete(this.index);
      }

      @Override
      public void onError(Throwable var1) {
         this.parent.innerError(this.index, var1);
      }

      @Override
      public void onNext(T var1) {
         this.parent.innerNext(this.index, (T)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }
   }

   static final class LatestCoordinator<T, R> extends AtomicInteger implements Disposable {
      private static final long serialVersionUID = 8567835998786448817L;
      int active;
      volatile boolean cancelled;
      final Function<? super Object[], ? extends R> combiner;
      int complete;
      final boolean delayError;
      volatile boolean done;
      final Observer<? super R> downstream;
      final AtomicThrowable errors = new AtomicThrowable();
      Object[] latest;
      final ObservableCombineLatest.CombinerObserver<T, R>[] observers;
      final SpscLinkedArrayQueue<Object[]> queue;

      LatestCoordinator(Observer<? super R> var1, Function<? super Object[], ? extends R> var2, int var3, int var4, boolean var5) {
         this.downstream = var1;
         this.combiner = var2;
         this.delayError = var5;
         this.latest = new Object[var3];
         ObservableCombineLatest.CombinerObserver[] var7 = new ObservableCombineLatest.CombinerObserver[var3];

         for (int var6 = 0; var6 < var3; var6++) {
            var7[var6] = new ObservableCombineLatest.CombinerObserver<>(this, var6);
         }

         this.observers = var7;
         this.queue = new SpscLinkedArrayQueue<>(var4);
      }

      void cancelSources() {
         ObservableCombineLatest.CombinerObserver[] var3 = this.observers;
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].dispose();
         }
      }

      void clear(SpscLinkedArrayQueue<?> param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: aconst_null
         // 04: putfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.latest [Ljava/lang/Object;
         // 07: aload 0
         // 08: monitorexit
         // 09: aload 1
         // 0a: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.clear ()V
         // 0d: return
         // 0e: astore 1
         // 0f: aload 0
         // 10: monitorexit
         // 11: aload 1
         // 12: athrow
      }

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.cancelSources();
            if (this.getAndIncrement() == 0) {
               this.clear(this.queue);
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            SpscLinkedArrayQueue var6 = this.queue;
            Observer var5 = this.downstream;
            boolean var4 = this.delayError;
            int var1 = 1;

            while (!this.cancelled) {
               if (!var4 && this.errors.get() != null) {
                  this.cancelSources();
                  this.clear(var6);
                  var5.onError(this.errors.terminate());
                  return;
               }

               boolean var3 = this.done;
               Object[] var7 = (Object[])var6.poll();
               boolean var2;
               if (var7 == null) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               if (var3 && var2) {
                  this.clear(var6);
                  Throwable var11 = this.errors.terminate();
                  if (var11 == null) {
                     var5.onComplete();
                  } else {
                     var5.onError(var11);
                  }

                  return;
               }

               if (var2) {
                  var2 = this.addAndGet(-var1);
                  var1 = var2;
                  if (var2 == 0) {
                     return;
                  }
               } else {
                  try {
                     var7 = ObjectHelper.requireNonNull(this.combiner.apply(var7), "The combiner returned a null value");
                  } catch (Throwable var9) {
                     Exceptions.throwIfFatal(var9);
                     this.errors.addThrowable(var9);
                     this.cancelSources();
                     this.clear(var6);
                     var5.onError(this.errors.terminate());
                     return;
                  }

                  var5.onNext(var7);
               }
            }

            this.clear(var6);
         }
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
         // 03: getfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.latest [Ljava/lang/Object;
         // 06: astore 3
         // 07: aload 3
         // 08: ifnonnull 0e
         // 0b: aload 0
         // 0c: monitorexit
         // 0d: return
         // 0e: aload 3
         // 0f: iload 1
         // 10: aaload
         // 11: ifnonnull 19
         // 14: bipush 1
         // 15: istore 1
         // 16: goto 1b
         // 19: bipush 0
         // 1a: istore 1
         // 1b: iload 1
         // 1c: ifne 31
         // 1f: aload 0
         // 20: getfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.complete I
         // 23: bipush 1
         // 24: iadd
         // 25: istore 2
         // 26: aload 0
         // 27: iload 2
         // 28: putfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.complete I
         // 2b: iload 2
         // 2c: aload 3
         // 2d: arraylength
         // 2e: if_icmpne 36
         // 31: aload 0
         // 32: bipush 1
         // 33: putfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.done Z
         // 36: aload 0
         // 37: monitorexit
         // 38: iload 1
         // 39: ifeq 40
         // 3c: aload 0
         // 3d: invokevirtual io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.cancelSources ()V
         // 40: aload 0
         // 41: invokevirtual io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.drain ()V
         // 44: return
         // 45: astore 3
         // 46: aload 0
         // 47: monitorexit
         // 48: aload 3
         // 49: athrow
      }

      void innerError(int param1, Throwable param2) {
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
         // 01: getfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.errors Lio/reactivex/internal/util/AtomicThrowable;
         // 04: aload 2
         // 05: invokevirtual io/reactivex/internal/util/AtomicThrowable.addThrowable (Ljava/lang/Throwable;)Z
         // 08: ifeq 69
         // 0b: aload 0
         // 0c: getfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.delayError Z
         // 0f: istore 4
         // 11: bipush 1
         // 12: istore 3
         // 13: iload 4
         // 15: ifeq 5a
         // 18: aload 0
         // 19: monitorenter
         // 1a: aload 0
         // 1b: getfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.latest [Ljava/lang/Object;
         // 1e: astore 2
         // 1f: aload 2
         // 20: ifnonnull 26
         // 23: aload 0
         // 24: monitorexit
         // 25: return
         // 26: aload 2
         // 27: iload 1
         // 28: aaload
         // 29: ifnonnull 31
         // 2c: bipush 1
         // 2d: istore 1
         // 2e: goto 33
         // 31: bipush 0
         // 32: istore 1
         // 33: iload 1
         // 34: ifne 49
         // 37: aload 0
         // 38: getfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.complete I
         // 3b: bipush 1
         // 3c: iadd
         // 3d: istore 3
         // 3e: aload 0
         // 3f: iload 3
         // 40: putfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.complete I
         // 43: iload 3
         // 44: aload 2
         // 45: arraylength
         // 46: if_icmpne 4e
         // 49: aload 0
         // 4a: bipush 1
         // 4b: putfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.done Z
         // 4e: aload 0
         // 4f: monitorexit
         // 50: iload 1
         // 51: istore 3
         // 52: goto 5a
         // 55: astore 2
         // 56: aload 0
         // 57: monitorexit
         // 58: aload 2
         // 59: athrow
         // 5a: iload 3
         // 5b: ifeq 62
         // 5e: aload 0
         // 5f: invokevirtual io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.cancelSources ()V
         // 62: aload 0
         // 63: invokevirtual io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.drain ()V
         // 66: goto 6d
         // 69: aload 2
         // 6a: invokestatic io/reactivex/plugins/RxJavaPlugins.onError (Ljava/lang/Throwable;)V
         // 6d: return
      }

      void innerNext(int param1, T param2) {
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
         // 03: getfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.latest [Ljava/lang/Object;
         // 06: astore 5
         // 08: aload 5
         // 0a: ifnonnull 10
         // 0d: aload 0
         // 0e: monitorexit
         // 0f: return
         // 10: aload 5
         // 12: iload 1
         // 13: aaload
         // 14: astore 6
         // 16: aload 0
         // 17: getfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.active I
         // 1a: istore 4
         // 1c: iload 4
         // 1e: istore 3
         // 1f: aload 6
         // 21: ifnonnull 2e
         // 24: iload 4
         // 26: bipush 1
         // 27: iadd
         // 28: istore 3
         // 29: aload 0
         // 2a: iload 3
         // 2b: putfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.active I
         // 2e: aload 5
         // 30: iload 1
         // 31: aload 2
         // 32: aastore
         // 33: iload 3
         // 34: aload 5
         // 36: arraylength
         // 37: if_icmpne 4c
         // 3a: aload 0
         // 3b: getfield io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 3e: aload 5
         // 40: invokevirtual [Ljava/lang/Object;.clone ()Ljava/lang/Object;
         // 43: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;)Z
         // 46: pop
         // 47: bipush 1
         // 48: istore 1
         // 49: goto 4e
         // 4c: bipush 0
         // 4d: istore 1
         // 4e: aload 0
         // 4f: monitorexit
         // 50: iload 1
         // 51: ifeq 58
         // 54: aload 0
         // 55: invokevirtual io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator.drain ()V
         // 58: return
         // 59: astore 2
         // 5a: aload 0
         // 5b: monitorexit
         // 5c: aload 2
         // 5d: athrow
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      public void subscribe(ObservableSource<? extends T>[] var1) {
         ObservableCombineLatest.CombinerObserver[] var4 = this.observers;
         int var3 = var4.length;
         this.downstream.onSubscribe(this);

         for (int var2 = 0; var2 < var3 && !this.done && !this.cancelled; var2++) {
            var1[var2].subscribe(var4[var2]);
         }
      }
   }
}
