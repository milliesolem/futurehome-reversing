package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.subjects.UnicastSubject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class ObservableWindowTimed<T> extends AbstractObservableWithUpstream<T, Observable<T>> {
   final int bufferSize;
   final long maxSize;
   final boolean restartTimerOnMaxSize;
   final Scheduler scheduler;
   final long timeskip;
   final long timespan;
   final TimeUnit unit;

   public ObservableWindowTimed(ObservableSource<T> var1, long var2, long var4, TimeUnit var6, Scheduler var7, long var8, int var10, boolean var11) {
      super(var1);
      this.timespan = var2;
      this.timeskip = var4;
      this.unit = var6;
      this.scheduler = var7;
      this.maxSize = var8;
      this.bufferSize = var10;
      this.restartTimerOnMaxSize = var11;
   }

   @Override
   public void subscribeActual(Observer<? super Observable<T>> var1) {
      SerializedObserver var2 = new SerializedObserver(var1);
      if (this.timespan == this.timeskip) {
         if (this.maxSize == Long.MAX_VALUE) {
            this.source.subscribe(new ObservableWindowTimed.WindowExactUnboundedObserver<>(var2, this.timespan, this.unit, this.scheduler, this.bufferSize));
         } else {
            this.source
               .subscribe(
                  new ObservableWindowTimed.WindowExactBoundedObserver<>(
                     var2, this.timespan, this.unit, this.scheduler, this.bufferSize, this.maxSize, this.restartTimerOnMaxSize
                  )
               );
         }
      } else {
         this.source
            .subscribe(
               new ObservableWindowTimed.WindowSkipObserver<>(var2, this.timespan, this.timeskip, this.unit, this.scheduler.createWorker(), this.bufferSize)
            );
      }
   }

   static final class WindowExactBoundedObserver<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable {
      final int bufferSize;
      long count;
      final long maxSize;
      long producerIndex;
      final boolean restartTimerOnMaxSize;
      final Scheduler scheduler;
      volatile boolean terminated;
      final SequentialDisposable timer = new SequentialDisposable();
      final long timespan;
      final TimeUnit unit;
      Disposable upstream;
      UnicastSubject<T> window;
      final Scheduler.Worker worker;

      WindowExactBoundedObserver(Observer<? super Observable<T>> var1, long var2, TimeUnit var4, Scheduler var5, int var6, long var7, boolean var9) {
         super(var1, new MpscLinkedQueue<>());
         this.timespan = var2;
         this.unit = var4;
         this.scheduler = var5;
         this.bufferSize = var6;
         this.maxSize = var7;
         this.restartTimerOnMaxSize = var9;
         if (var9) {
            this.worker = var5.createWorker();
         } else {
            this.worker = null;
         }
      }

      @Override
      public void dispose() {
         this.cancelled = true;
      }

      void disposeTimer() {
         DisposableHelper.dispose(this.timer);
         Scheduler.Worker var1 = this.worker;
         if (var1 != null) {
            var1.dispose();
         }
      }

      void drainLoop() {
         MpscLinkedQueue var10 = (MpscLinkedQueue)this.queue;
         Observer var9 = this.downstream;
         UnicastSubject var7 = this.window;
         int var1 = 1;

         while (!this.terminated) {
            boolean var6 = this.done;
            Object var8 = var10.poll();
            boolean var2;
            if (var8 == null) {
               var2 = 1;
            } else {
               var2 = 0;
            }

            boolean var5 = var8 instanceof ObservableWindowTimed.WindowExactBoundedObserver.ConsumerIndexHolder;
            if (var6 && (var2 || var5)) {
               this.window = null;
               var10.clear();
               var8 = this.error;
               if (var8 != null) {
                  var7.onError((Throwable)var8);
               } else {
                  var7.onComplete();
               }

               this.disposeTimer();
               return;
            }

            if (var2) {
               var2 = this.leave(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else if (var5) {
               var8 = (ObservableWindowTimed.WindowExactBoundedObserver.ConsumerIndexHolder)var8;
               if (!this.restartTimerOnMaxSize || this.producerIndex == ((ObservableWindowTimed.WindowExactBoundedObserver.ConsumerIndexHolder)var8).index) {
                  var7.onComplete();
                  this.count = 0L;
                  var7 = UnicastSubject.create(this.bufferSize);
                  this.window = var7;
                  var9.onNext(var7);
               }
            } else {
               var7.onNext(NotificationLite.getValue(var8));
               long var3 = this.count + 1L;
               if (var3 >= this.maxSize) {
                  this.producerIndex++;
                  this.count = 0L;
                  var7.onComplete();
                  var8 = UnicastSubject.create(this.bufferSize);
                  this.window = (UnicastSubject<T>)var8;
                  this.downstream.onNext((Observable<T>)var8);
                  var7 = (UnicastSubject)var8;
                  if (this.restartTimerOnMaxSize) {
                     Disposable var11 = this.timer.get();
                     var11.dispose();
                     Scheduler.Worker var15 = this.worker;
                     ObservableWindowTimed.WindowExactBoundedObserver.ConsumerIndexHolder var12 = new ObservableWindowTimed.WindowExactBoundedObserver.ConsumerIndexHolder(
                        this.producerIndex, this
                     );
                     var3 = this.timespan;
                     Disposable var19 = var15.schedulePeriodically(var12, var3, var3, this.unit);
                     var7 = (UnicastSubject)var8;
                     if (!this.timer.compareAndSet(var11, var19)) {
                        var19.dispose();
                        var7 = (UnicastSubject)var8;
                     }
                  }
               } else {
                  this.count = var3;
               }
            }
         }

         this.upstream.dispose();
         var10.clear();
         this.disposeTimer();
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         this.done = true;
         if (this.enter()) {
            this.drainLoop();
         }

         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         if (this.enter()) {
            this.drainLoop();
         }

         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         if (!this.terminated) {
            if (this.fastEnter()) {
               UnicastSubject var4 = this.window;
               var4.onNext(var1);
               long var2 = this.count + 1L;
               if (var2 >= this.maxSize) {
                  this.producerIndex++;
                  this.count = 0L;
                  var4.onComplete();
                  var1 = UnicastSubject.create(this.bufferSize);
                  this.window = var1;
                  this.downstream.onNext(var1);
                  if (this.restartTimerOnMaxSize) {
                     this.timer.get().dispose();
                     Scheduler.Worker var9 = this.worker;
                     ObservableWindowTimed.WindowExactBoundedObserver.ConsumerIndexHolder var6 = new ObservableWindowTimed.WindowExactBoundedObserver.ConsumerIndexHolder(
                        this.producerIndex, this
                     );
                     var2 = this.timespan;
                     Disposable var7 = var9.schedulePeriodically(var6, var2, var2, this.unit);
                     DisposableHelper.replace(this.timer, var7);
                  }
               } else {
                  this.count = var2;
               }

               if (this.leave(-1) == 0) {
                  return;
               }
            } else {
               this.queue.offer(NotificationLite.next(var1));
               if (!this.enter()) {
                  return;
               }
            }

            this.drainLoop();
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            Observer var5 = this.downstream;
            var5.onSubscribe(this);
            if (this.cancelled) {
               return;
            }

            UnicastSubject var4 = UnicastSubject.create(this.bufferSize);
            this.window = var4;
            var5.onNext(var4);
            ObservableWindowTimed.WindowExactBoundedObserver.ConsumerIndexHolder var6 = new ObservableWindowTimed.WindowExactBoundedObserver.ConsumerIndexHolder(
               this.producerIndex, this
            );
            if (this.restartTimerOnMaxSize) {
               Scheduler.Worker var9 = this.worker;
               long var2 = this.timespan;
               var1 = var9.schedulePeriodically(var6, var2, var2, this.unit);
            } else {
               Scheduler var10 = this.scheduler;
               long var8 = this.timespan;
               var1 = var10.schedulePeriodicallyDirect(var6, var8, var8, this.unit);
            }

            this.timer.replace(var1);
         }
      }

      static final class ConsumerIndexHolder implements Runnable {
         final long index;
         final ObservableWindowTimed.WindowExactBoundedObserver<?> parent;

         ConsumerIndexHolder(long var1, ObservableWindowTimed.WindowExactBoundedObserver<?> var3) {
            this.index = var1;
            this.parent = var3;
         }

         @Override
         public void run() {
            ObservableWindowTimed.WindowExactBoundedObserver var1 = this.parent;
            if (!var1.cancelled) {
               var1.queue.offer(this);
            } else {
               var1.terminated = true;
            }

            if (var1.enter()) {
               var1.drainLoop();
            }
         }
      }
   }

   static final class WindowExactUnboundedObserver<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Observer<T>, Disposable, Runnable {
      static final Object NEXT = new Object();
      final int bufferSize;
      final Scheduler scheduler;
      volatile boolean terminated;
      final SequentialDisposable timer = new SequentialDisposable();
      final long timespan;
      final TimeUnit unit;
      Disposable upstream;
      UnicastSubject<T> window;

      WindowExactUnboundedObserver(Observer<? super Observable<T>> var1, long var2, TimeUnit var4, Scheduler var5, int var6) {
         super(var1, new MpscLinkedQueue<>());
         this.timespan = var2;
         this.unit = var4;
         this.scheduler = var5;
         this.bufferSize = var6;
      }

      @Override
      public void dispose() {
         this.cancelled = true;
      }

      void drainLoop() {
         MpscLinkedQueue var7 = (MpscLinkedQueue)this.queue;
         Observer var6 = this.downstream;
         UnicastSubject var5 = this.window;
         int var1 = 1;

         while (true) {
            boolean var3 = this.terminated;
            boolean var4 = this.done;
            Object var8 = var7.poll();
            if (var4 && (var8 == null || var8 == NEXT)) {
               this.window = null;
               var7.clear();
               Throwable var9 = this.error;
               if (var9 != null) {
                  var5.onError(var9);
               } else {
                  var5.onComplete();
               }

               this.timer.dispose();
               return;
            }

            if (var8 == null) {
               int var2 = this.leave(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else if (var8 == NEXT) {
               var5.onComplete();
               if (!var3) {
                  var5 = UnicastSubject.create(this.bufferSize);
                  this.window = var5;
                  var6.onNext(var5);
               } else {
                  this.upstream.dispose();
               }
            } else {
               var5.onNext(NotificationLite.getValue(var8));
            }
         }
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         this.done = true;
         if (this.enter()) {
            this.drainLoop();
         }

         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         if (this.enter()) {
            this.drainLoop();
         }

         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         if (!this.terminated) {
            if (this.fastEnter()) {
               this.window.onNext((T)var1);
               if (this.leave(-1) == 0) {
                  return;
               }
            } else {
               this.queue.offer(NotificationLite.next(var1));
               if (!this.enter()) {
                  return;
               }
            }

            this.drainLoop();
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.window = UnicastSubject.create(this.bufferSize);
            Observer var4 = this.downstream;
            var4.onSubscribe(this);
            var4.onNext(this.window);
            if (!this.cancelled) {
               Scheduler var5 = this.scheduler;
               long var2 = this.timespan;
               var1 = var5.schedulePeriodicallyDirect(this, var2, var2, this.unit);
               this.timer.replace(var1);
            }
         }
      }

      @Override
      public void run() {
         if (this.cancelled) {
            this.terminated = true;
         }

         this.queue.offer(NEXT);
         if (this.enter()) {
            this.drainLoop();
         }
      }
   }

   static final class WindowSkipObserver<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable, Runnable {
      final int bufferSize;
      volatile boolean terminated;
      final long timeskip;
      final long timespan;
      final TimeUnit unit;
      Disposable upstream;
      final List<UnicastSubject<T>> windows;
      final Scheduler.Worker worker;

      WindowSkipObserver(Observer<? super Observable<T>> var1, long var2, long var4, TimeUnit var6, Scheduler.Worker var7, int var8) {
         super(var1, new MpscLinkedQueue<>());
         this.timespan = var2;
         this.timeskip = var4;
         this.unit = var6;
         this.worker = var7;
         this.bufferSize = var8;
         this.windows = new LinkedList<>();
      }

      void complete(UnicastSubject<T> var1) {
         this.queue.offer(new ObservableWindowTimed.WindowSkipObserver.SubjectWork(var1, false));
         if (this.enter()) {
            this.drainLoop();
         }
      }

      @Override
      public void dispose() {
         this.cancelled = true;
      }

      void drainLoop() {
         MpscLinkedQueue var6 = (MpscLinkedQueue)this.queue;
         Observer var7 = this.downstream;
         List var5 = this.windows;
         int var1 = 1;

         while (!this.terminated) {
            boolean var3 = this.done;
            Object var8 = var6.poll();
            boolean var2;
            if (var8 == null) {
               var2 = 1;
            } else {
               var2 = 0;
            }

            boolean var4 = var8 instanceof ObservableWindowTimed.WindowSkipObserver.SubjectWork;
            if (var3 && (var2 || var4)) {
               var6.clear();
               Throwable var11 = this.error;
               if (var11 != null) {
                  Iterator var13 = var5.iterator();

                  while (var13.hasNext()) {
                     ((UnicastSubject)var13.next()).onError(var11);
                  }
               } else {
                  Iterator var12 = var5.iterator();

                  while (var12.hasNext()) {
                     ((UnicastSubject)var12.next()).onComplete();
                  }
               }

               var5.clear();
               this.worker.dispose();
               return;
            }

            if (var2) {
               var2 = this.leave(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else if (var4) {
               var8 = (ObservableWindowTimed.WindowSkipObserver.SubjectWork)var8;
               if (((ObservableWindowTimed.WindowSkipObserver.SubjectWork)var8).open) {
                  if (!this.cancelled) {
                     var8 = UnicastSubject.create(this.bufferSize);
                     var5.add(var8);
                     var7.onNext(var8);
                     this.worker.schedule(new ObservableWindowTimed.WindowSkipObserver.CompletionTask(this, (UnicastSubject)var8), this.timespan, this.unit);
                  }
               } else {
                  var5.remove(((ObservableWindowTimed.WindowSkipObserver.SubjectWork)var8).w);
                  ((ObservableWindowTimed.WindowSkipObserver.SubjectWork)var8).w.onComplete();
                  if (var5.isEmpty() && this.cancelled) {
                     this.terminated = true;
                  }
               }
            } else {
               Iterator var9 = var5.iterator();

               while (var9.hasNext()) {
                  ((UnicastSubject)var9.next()).onNext(var8);
               }
            }
         }

         this.upstream.dispose();
         var6.clear();
         var5.clear();
         this.worker.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         this.done = true;
         if (this.enter()) {
            this.drainLoop();
         }

         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         if (this.enter()) {
            this.drainLoop();
         }

         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         if (this.fastEnter()) {
            Iterator var2 = this.windows.iterator();

            while (var2.hasNext()) {
               ((UnicastSubject)var2.next()).onNext(var1);
            }

            if (this.leave(-1) == 0) {
               return;
            }
         } else {
            this.queue.offer(var1);
            if (!this.enter()) {
               return;
            }
         }

         this.drainLoop();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            if (this.cancelled) {
               return;
            }

            UnicastSubject var4 = UnicastSubject.create(this.bufferSize);
            this.windows.add(var4);
            this.downstream.onNext(var4);
            this.worker.schedule(new ObservableWindowTimed.WindowSkipObserver.CompletionTask(this, var4), this.timespan, this.unit);
            Scheduler.Worker var5 = this.worker;
            long var2 = this.timeskip;
            var5.schedulePeriodically(this, var2, var2, this.unit);
         }
      }

      @Override
      public void run() {
         ObservableWindowTimed.WindowSkipObserver.SubjectWork var1 = new ObservableWindowTimed.WindowSkipObserver.SubjectWork(
            UnicastSubject.create(this.bufferSize), true
         );
         if (!this.cancelled) {
            this.queue.offer(var1);
         }

         if (this.enter()) {
            this.drainLoop();
         }
      }

      final class CompletionTask implements Runnable {
         final ObservableWindowTimed.WindowSkipObserver this$0;
         private final UnicastSubject<T> w;

         CompletionTask(UnicastSubject<T> var1, UnicastSubject var2) {
            this.this$0 = var1;
            this.w = var2;
         }

         @Override
         public void run() {
            this.this$0.complete(this.w);
         }
      }

      static final class SubjectWork<T> {
         final boolean open;
         final UnicastSubject<T> w;

         SubjectWork(UnicastSubject<T> var1, boolean var2) {
            this.w = var1;
            this.open = var2;
         }
      }
   }
}
