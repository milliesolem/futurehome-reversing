package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableGroupJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AbstractObservableWithUpstream<TLeft, R> {
   final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> leftEnd;
   final ObservableSource<? extends TRight> other;
   final BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> resultSelector;
   final Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd;

   public ObservableGroupJoin(
      ObservableSource<TLeft> var1,
      ObservableSource<? extends TRight> var2,
      Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> var3,
      Function<? super TRight, ? extends ObservableSource<TRightEnd>> var4,
      BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> var5
   ) {
      super(var1);
      this.other = var2;
      this.leftEnd = var3;
      this.rightEnd = var4;
      this.resultSelector = var5;
   }

   @Override
   protected void subscribeActual(Observer<? super R> var1) {
      ObservableGroupJoin.GroupJoinDisposable var2 = new ObservableGroupJoin.GroupJoinDisposable<>(var1, this.leftEnd, this.rightEnd, this.resultSelector);
      var1.onSubscribe(var2);
      ObservableGroupJoin.LeftRightObserver var4 = new ObservableGroupJoin.LeftRightObserver(var2, true);
      var2.disposables.add(var4);
      ObservableGroupJoin.LeftRightObserver var3 = new ObservableGroupJoin.LeftRightObserver(var2, false);
      var2.disposables.add(var3);
      this.source.subscribe(var4);
      this.other.subscribe(var3);
   }

   static final class GroupJoinDisposable<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements Disposable, ObservableGroupJoin.JoinSupport {
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
      final Map<Integer, UnicastSubject<TRight>> lefts;
      final SpscLinkedArrayQueue<Object> queue;
      final BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> resultSelector;
      final Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd;
      int rightIndex;
      final Map<Integer, TRight> rights;

      GroupJoinDisposable(
         Observer<? super R> var1,
         Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> var2,
         Function<? super TRight, ? extends ObservableSource<TRightEnd>> var3,
         BiFunction<? super TLeft, ? super Observable<TRight>, ? extends R> var4
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
            SpscLinkedArrayQueue var5 = this.queue;
            Observer var4 = this.downstream;
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
                     ((UnicastSubject)var25.next()).onComplete();
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
                     UnicastSubject var33 = UnicastSubject.create();
                     var2 = this.leftIndex++;
                     this.lefts.put(var2, var33);

                     ObservableSource var9;
                     try {
                        var9 = ObjectHelper.requireNonNull(this.leftEnd.apply((TLeft)var6), "The leftEnd returned a null ObservableSource");
                     } catch (Throwable var19) {
                        this.fail(var19, var4, var5);
                        return;
                     }

                     ObservableGroupJoin.LeftRightEndObserver var34 = new ObservableGroupJoin.LeftRightEndObserver(this, true, var2);
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

                     var4.onNext(var6);
                     var6 = this.rights.values().iterator();

                     while (var6.hasNext()) {
                        var33.onNext(var6.next());
                     }
                  } else if (var7 == RIGHT_VALUE) {
                     var2 = this.rightIndex++;
                     this.rights.put(var2, (TRight)var6);

                     try {
                        var31 = ObjectHelper.requireNonNull(this.rightEnd.apply((TRight)var6), "The rightEnd returned a null ObservableSource");
                     } catch (Throwable var20) {
                        this.fail(var20, var4, var5);
                        return;
                     }

                     ObservableGroupJoin.LeftRightEndObserver var8 = new ObservableGroupJoin.LeftRightEndObserver(this, false, var2);
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
                        ((UnicastSubject)var32.next()).onNext(var6);
                     }
                  } else if (var7 == LEFT_CLOSE) {
                     var6 = (ObservableGroupJoin.LeftRightEndObserver)var6;
                     UnicastSubject var30 = this.lefts.remove(((ObservableGroupJoin.LeftRightEndObserver)var6).index);
                     this.disposables.remove((Disposable)var6);
                     if (var30 != null) {
                        var30.onComplete();
                     }
                  } else if (var7 == RIGHT_CLOSE) {
                     var6 = (ObservableGroupJoin.LeftRightEndObserver)var6;
                     this.rights.remove(((ObservableGroupJoin.LeftRightEndObserver)var6).index);
                     this.disposables.remove((Disposable)var6);
                  }
               }
            }

            var5.clear();
         }
      }

      void errorAll(Observer<?> var1) {
         Throwable var2 = ExceptionHelper.terminate(this.error);
         Iterator var3 = this.lefts.values().iterator();

         while (var3.hasNext()) {
            ((UnicastSubject)var3.next()).onError(var2);
         }

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
         // 03: getfield io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 06: astore 4
         // 08: iload 1
         // 09: ifeq 13
         // 0c: getstatic io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable.LEFT_CLOSE Ljava/lang/Integer;
         // 0f: astore 3
         // 10: goto 17
         // 13: getstatic io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable.RIGHT_CLOSE Ljava/lang/Integer;
         // 16: astore 3
         // 17: aload 4
         // 19: aload 3
         // 1a: aload 2
         // 1b: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;Ljava/lang/Object;)Z
         // 1e: pop
         // 1f: aload 0
         // 20: monitorexit
         // 21: aload 0
         // 22: invokevirtual io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable.drain ()V
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
         // 03: getfield io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 06: astore 4
         // 08: iload 1
         // 09: ifeq 13
         // 0c: getstatic io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable.LEFT_VALUE Ljava/lang/Integer;
         // 0f: astore 3
         // 10: goto 17
         // 13: getstatic io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable.RIGHT_VALUE Ljava/lang/Integer;
         // 16: astore 3
         // 17: aload 4
         // 19: aload 3
         // 1a: aload 2
         // 1b: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;Ljava/lang/Object;)Z
         // 1e: pop
         // 1f: aload 0
         // 20: monitorexit
         // 21: aload 0
         // 22: invokevirtual io/reactivex/internal/operators/observable/ObservableGroupJoin$GroupJoinDisposable.drain ()V
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

   interface JoinSupport {
      void innerClose(boolean var1, ObservableGroupJoin.LeftRightEndObserver var2);

      void innerCloseError(Throwable var1);

      void innerComplete(ObservableGroupJoin.LeftRightObserver var1);

      void innerError(Throwable var1);

      void innerValue(boolean var1, Object var2);
   }

   static final class LeftRightEndObserver extends AtomicReference<Disposable> implements Observer<Object>, Disposable {
      private static final long serialVersionUID = 1883890389173668373L;
      final int index;
      final boolean isLeft;
      final ObservableGroupJoin.JoinSupport parent;

      LeftRightEndObserver(ObservableGroupJoin.JoinSupport var1, boolean var2, int var3) {
         this.parent = var1;
         this.isLeft = var2;
         this.index = var3;
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
         this.parent.innerClose(this.isLeft, this);
      }

      @Override
      public void onError(Throwable var1) {
         this.parent.innerCloseError(var1);
      }

      @Override
      public void onNext(Object var1) {
         if (DisposableHelper.dispose(this)) {
            this.parent.innerClose(this.isLeft, this);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }
   }

   static final class LeftRightObserver extends AtomicReference<Disposable> implements Observer<Object>, Disposable {
      private static final long serialVersionUID = 1883890389173668373L;
      final boolean isLeft;
      final ObservableGroupJoin.JoinSupport parent;

      LeftRightObserver(ObservableGroupJoin.JoinSupport var1, boolean var2) {
         this.parent = var1;
         this.isLeft = var2;
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
         this.parent.innerComplete(this);
      }

      @Override
      public void onError(Throwable var1) {
         this.parent.innerError(var1);
      }

      @Override
      public void onNext(Object var1) {
         this.parent.innerValue(this.isLeft, var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }
   }
}
