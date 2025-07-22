package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AbstractObservableWithUpstream<TLeft, R> {
   final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> leftEnd;
   final ObservableSource<? extends TRight> other;
   final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
   final Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd;

   public ObservableJoin(
      ObservableSource<TLeft> var1,
      ObservableSource<? extends TRight> var2,
      Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> var3,
      Function<? super TRight, ? extends ObservableSource<TRightEnd>> var4,
      BiFunction<? super TLeft, ? super TRight, ? extends R> var5
   ) {
      super(var1);
      this.other = var2;
      this.leftEnd = var3;
      this.rightEnd = var4;
      this.resultSelector = var5;
   }

   @Override
   protected void subscribeActual(Observer<? super R> var1) {
      ObservableJoin.JoinDisposable var2 = new ObservableJoin.JoinDisposable<>(var1, this.leftEnd, this.rightEnd, this.resultSelector);
      var1.onSubscribe(var2);
      ObservableGroupJoin.LeftRightObserver var4 = new ObservableGroupJoin.LeftRightObserver(var2, true);
      var2.disposables.add(var4);
      ObservableGroupJoin.LeftRightObserver var3 = new ObservableGroupJoin.LeftRightObserver(var2, false);
      var2.disposables.add(var3);
      this.source.subscribe(var4);
      this.other.subscribe(var3);
   }

   static final class JoinDisposable<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements Disposable, ObservableGroupJoin.JoinSupport {
      static final Integer LEFT_CLOSE = 3;
      static final Integer LEFT_VALUE = 1;
      static final Integer RIGHT_CLOSE = 4;
      static final Integer RIGHT_VALUE = 2;
      private static final long serialVersionUID = -6071216598687999801L;
      final AtomicInteger active;
      volatile boolean cancelled;
      final CompositeDisposable disposables;
      final Observer<? super R> downstream;
      final AtomicReference<Throwable> error;
      final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> leftEnd;
      int leftIndex;
      final Map<Integer, TLeft> lefts;
      final SpscLinkedArrayQueue<Object> queue;
      final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
      final Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd;
      int rightIndex;
      final Map<Integer, TRight> rights;

      JoinDisposable(
         Observer<? super R> var1,
         Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> var2,
         Function<? super TRight, ? extends ObservableSource<TRightEnd>> var3,
         BiFunction<? super TLeft, ? super TRight, ? extends R> var4
      ) {
         this.downstream = var1;
         this.disposables = new CompositeDisposable();
         this.queue = new SpscLinkedArrayQueue<>(Observable.bufferSize());
         this.lefts = new LinkedHashMap<>();
         this.rights = new LinkedHashMap<>();
         this.error = new AtomicReference<>();
         this.leftEnd = var2;
         this.rightEnd = var3;
         this.resultSelector = var4;
         this.active = new AtomicInteger(2);
      }

      void cancelAll() {
         this.disposables.dispose();
      }

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.cancelAll();
            if (this.getAndIncrement() == 0) {
               this.queue.clear();
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            SpscLinkedArrayQueue var4 = this.queue;
            Observer var5 = this.downstream;
            int var1 = 1;

            while (!this.cancelled) {
               if (this.error.get() != null) {
                  var4.clear();
                  this.cancelAll();
                  this.errorAll(var5);
                  return;
               }

               boolean var2;
               if (this.active.get() == 0) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               Integer var7 = (Integer)var4.poll();
               boolean var3;
               if (var7 == null) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (var2 && var3) {
                  this.lefts.clear();
                  this.rights.clear();
                  this.disposables.dispose();
                  var5.onComplete();
                  return;
               }

               if (var3) {
                  var2 = this.addAndGet(-var1);
                  var1 = var2;
                  if (var2 == 0) {
                     return;
                  }
               } else {
                  Object var6 = var4.poll();
                  if (var7 == LEFT_VALUE) {
                     var2 = this.leftIndex++;
                     this.lefts.put(var2, (TLeft)var6);

                     try {
                        var36 = ObjectHelper.requireNonNull(this.leftEnd.apply((TLeft)var6), "The leftEnd returned a null ObservableSource");
                     } catch (Throwable var28) {
                        this.fail(var28, var5, var4);
                        return;
                     }

                     ObservableGroupJoin.LeftRightEndObserver var40 = new ObservableGroupJoin.LeftRightEndObserver(this, true, var2);
                     this.disposables.add(var40);
                     var36.subscribe(var40);
                     if (this.error.get() != null) {
                        var4.clear();
                        this.cancelAll();
                        this.errorAll(var5);
                        return;
                     }

                     for (var40 : this.rights.values()) {
                        try {
                           var40 = ObjectHelper.requireNonNull(
                              this.resultSelector.apply((TLeft)var6, (TRight)var40), "The resultSelector returned a null value"
                           );
                        } catch (Throwable var27) {
                           this.fail(var27, var5, var4);
                           return;
                        }

                        var5.onNext(var40);
                     }
                  } else if (var7 == RIGHT_VALUE) {
                     var2 = this.rightIndex++;
                     this.rights.put(var2, (TRight)var6);

                     try {
                        var34 = ObjectHelper.requireNonNull(this.rightEnd.apply((TRight)var6), "The rightEnd returned a null ObservableSource");
                     } catch (Throwable var26) {
                        this.fail(var26, var5, var4);
                        return;
                     }

                     ObservableGroupJoin.LeftRightEndObserver var8 = new ObservableGroupJoin.LeftRightEndObserver(this, false, var2);
                     this.disposables.add(var8);
                     var34.subscribe(var8);
                     if (this.error.get() != null) {
                        var4.clear();
                        this.cancelAll();
                        this.errorAll(var5);
                        return;
                     }

                     for (var8 : this.lefts.values()) {
                        try {
                           var8 = ObjectHelper.requireNonNull(this.resultSelector.apply((TLeft)var8, (TRight)var6), "The resultSelector returned a null value");
                        } catch (Throwable var25) {
                           this.fail(var25, var5, var4);
                           return;
                        }

                        var5.onNext(var8);
                     }
                  } else if (var7 == LEFT_CLOSE) {
                     var6 = (ObservableGroupJoin.LeftRightEndObserver)var6;
                     this.lefts.remove(((ObservableGroupJoin.LeftRightEndObserver)var6).index);
                     this.disposables.remove((Disposable)var6);
                  } else {
                     var6 = (ObservableGroupJoin.LeftRightEndObserver)var6;
                     this.rights.remove(((ObservableGroupJoin.LeftRightEndObserver)var6).index);
                     this.disposables.remove((Disposable)var6);
                  }
               }
            }

            var4.clear();
         }
      }

      void errorAll(Observer<?> var1) {
         Throwable var2 = ExceptionHelper.terminate(this.error);
         this.lefts.clear();
         this.rights.clear();
         var1.onError(var2);
      }

      void fail(Throwable var1, Observer<?> var2, SpscLinkedArrayQueue<?> var3) {
         Exceptions.throwIfFatal(var1);
         ExceptionHelper.addThrowable(this.error, var1);
         var3.clear();
         this.cancelAll();
         this.errorAll(var2);
      }

      @Override
      public void innerClose(boolean param1, ObservableGroupJoin.LeftRightEndObserver param2) {
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
         // 03: getfield io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 06: astore 4
         // 08: iload 1
         // 09: ifeq 13
         // 0c: getstatic io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable.LEFT_CLOSE Ljava/lang/Integer;
         // 0f: astore 3
         // 10: goto 17
         // 13: getstatic io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable.RIGHT_CLOSE Ljava/lang/Integer;
         // 16: astore 3
         // 17: aload 4
         // 19: aload 3
         // 1a: aload 2
         // 1b: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;Ljava/lang/Object;)Z
         // 1e: pop
         // 1f: aload 0
         // 20: monitorexit
         // 21: aload 0
         // 22: invokevirtual io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable.drain ()V
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
      public void innerComplete(ObservableGroupJoin.LeftRightObserver var1) {
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
         // 03: getfield io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 06: astore 4
         // 08: iload 1
         // 09: ifeq 13
         // 0c: getstatic io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable.LEFT_VALUE Ljava/lang/Integer;
         // 0f: astore 3
         // 10: goto 17
         // 13: getstatic io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable.RIGHT_VALUE Ljava/lang/Integer;
         // 16: astore 3
         // 17: aload 4
         // 19: aload 3
         // 1a: aload 2
         // 1b: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;Ljava/lang/Object;)Z
         // 1e: pop
         // 1f: aload 0
         // 20: monitorexit
         // 21: aload 0
         // 22: invokevirtual io/reactivex/internal/operators/observable/ObservableJoin$JoinDisposable.drain ()V
         // 25: return
         // 26: astore 2
         // 27: aload 0
         // 28: monitorexit
         // 29: aload 2
         // 2a: athrow
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }
   }
}
