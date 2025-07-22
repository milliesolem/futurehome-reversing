package io.reactivex.internal.operators.observable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Timed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableReplay<T> extends ConnectableObservable<T> implements HasUpstreamObservableSource<T>, ResettableConnectable {
   static final ObservableReplay.BufferSupplier DEFAULT_UNBOUNDED_FACTORY = new ObservableReplay.UnBoundedFactory();
   final ObservableReplay.BufferSupplier<T> bufferFactory;
   final AtomicReference<ObservableReplay.ReplayObserver<T>> current;
   final ObservableSource<T> onSubscribe;
   final ObservableSource<T> source;

   private ObservableReplay(
      ObservableSource<T> var1, ObservableSource<T> var2, AtomicReference<ObservableReplay.ReplayObserver<T>> var3, ObservableReplay.BufferSupplier<T> var4
   ) {
      this.onSubscribe = var1;
      this.source = var2;
      this.current = var3;
      this.bufferFactory = var4;
   }

   public static <T> ConnectableObservable<T> create(ObservableSource<T> var0, int var1) {
      return var1 == Integer.MAX_VALUE ? createFrom(var0) : create(var0, new ObservableReplay.ReplayBufferSupplier<>(var1));
   }

   public static <T> ConnectableObservable<T> create(ObservableSource<T> var0, long var1, TimeUnit var3, Scheduler var4) {
      return create(var0, var1, var3, var4, Integer.MAX_VALUE);
   }

   public static <T> ConnectableObservable<T> create(ObservableSource<T> var0, long var1, TimeUnit var3, Scheduler var4, int var5) {
      return create(var0, new ObservableReplay.ScheduledReplaySupplier<>(var5, var1, var3, var4));
   }

   static <T> ConnectableObservable<T> create(ObservableSource<T> var0, ObservableReplay.BufferSupplier<T> var1) {
      AtomicReference var2 = new AtomicReference();
      return RxJavaPlugins.onAssembly(new ObservableReplay<>(new ObservableReplay.ReplaySource<>(var2, var1), var0, var2, var1));
   }

   public static <T> ConnectableObservable<T> createFrom(ObservableSource<? extends T> var0) {
      return create(var0, DEFAULT_UNBOUNDED_FACTORY);
   }

   public static <U, R> Observable<R> multicastSelector(
      Callable<? extends ConnectableObservable<U>> var0, Function<? super Observable<U>, ? extends ObservableSource<R>> var1
   ) {
      return RxJavaPlugins.onAssembly(new ObservableReplay.MulticastReplay<>(var0, var1));
   }

   public static <T> ConnectableObservable<T> observeOn(ConnectableObservable<T> var0, Scheduler var1) {
      return RxJavaPlugins.onAssembly(new ObservableReplay.Replay<>(var0, var0.observeOn(var1)));
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void connect(Consumer<? super Disposable> var1) {
      ObservableReplay.ReplayObserver var3;
      ObservableReplay.ReplayObserver var4;
      do {
         var4 = this.current.get();
         if (var4 != null) {
            var3 = var4;
            if (!var4.isDisposed()) {
               break;
            }
         }

         var3 = new ObservableReplay.ReplayObserver<>(this.bufferFactory.call());
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.current, var4, var3));

      boolean var2;
      if (!var3.shouldConnect.get() && var3.shouldConnect.compareAndSet(false, true)) {
         var2 = true;
      } else {
         var2 = false;
      }

      try {
         var1.accept(var3);
      } catch (Throwable var6) {
         if (var2) {
            var3.shouldConnect.compareAndSet(true, false);
         }

         Exceptions.throwIfFatal(var6);
         throw ExceptionHelper.wrapOrThrow(var6);
      }

      if (var2) {
         this.source.subscribe(var3);
      }
   }

   @Override
   public void resetIf(Disposable var1) {
      ExternalSyntheticBackportWithForwarding0.m(this.current, (ObservableReplay.ReplayObserver)var1, null);
   }

   @Override
   public ObservableSource<T> source() {
      return this.source;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.onSubscribe.subscribe(var1);
   }

   abstract static class BoundedReplayBuffer<T> extends AtomicReference<ObservableReplay.Node> implements ObservableReplay.ReplayBuffer<T> {
      private static final long serialVersionUID = 2346567790059478686L;
      int size;
      ObservableReplay.Node tail;

      BoundedReplayBuffer() {
         ObservableReplay.Node var1 = new ObservableReplay.Node(null);
         this.tail = var1;
         this.set(var1);
      }

      final void addLast(ObservableReplay.Node var1) {
         this.tail.set(var1);
         this.tail = var1;
         this.size++;
      }

      final void collect(Collection<? super T> var1) {
         ObservableReplay.Node var2 = this.getHead();

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
         this.addLast(new ObservableReplay.Node(this.enterTransform(NotificationLite.complete())));
         this.truncateFinal();
      }

      Object enterTransform(Object var1) {
         return var1;
      }

      @Override
      public final void error(Throwable var1) {
         this.addLast(new ObservableReplay.Node(this.enterTransform(NotificationLite.error(var1))));
         this.truncateFinal();
      }

      ObservableReplay.Node getHead() {
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
         this.addLast(new ObservableReplay.Node(this.enterTransform(NotificationLite.next(var1))));
         this.truncate();
      }

      final void removeFirst() {
         ObservableReplay.Node var1 = this.get().get();
         this.size--;
         this.setFirst(var1);
      }

      final void removeSome(int var1) {
         ObservableReplay.Node var2;
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
      public final void replay(ObservableReplay.InnerDisposable<T> var1) {
         if (var1.getAndIncrement() == 0) {
            int var2 = 1;

            label31:
            while (true) {
               ObservableReplay.Node var5 = var1.index();
               ObservableReplay.Node var4 = var5;
               if (var5 == null) {
                  var4 = this.getHead();
                  var1.index = var4;
               }

               while (!var1.isDisposed()) {
                  var5 = var4.get();
                  if (var5 == null) {
                     var1.index = var4;
                     int var3 = var1.addAndGet(-var2);
                     var2 = var3;
                     if (var3 == 0) {
                        break label31;
                     }
                     continue label31;
                  }

                  if (NotificationLite.accept(this.leaveTransform(var5.value), var1.child)) {
                     var1.index = null;
                     return;
                  }

                  var4 = var5;
               }

               var1.index = null;
               return;
            }
         }
      }

      final void setFirst(ObservableReplay.Node var1) {
         this.set(var1);
      }

      final void trimHead() {
         ObservableReplay.Node var2 = this.get();
         if (var2.value != null) {
            ObservableReplay.Node var1 = new ObservableReplay.Node(null);
            var1.lazySet(var2.get());
            this.set(var1);
         }
      }

      abstract void truncate();

      void truncateFinal() {
         this.trimHead();
      }
   }

   interface BufferSupplier<T> {
      ObservableReplay.ReplayBuffer<T> call();
   }

   static final class DisposeConsumer<R> implements Consumer<Disposable> {
      private final ObserverResourceWrapper<R> srw;

      DisposeConsumer(ObserverResourceWrapper<R> var1) {
         this.srw = var1;
      }

      public void accept(Disposable var1) {
         this.srw.setResource(var1);
      }
   }

   static final class InnerDisposable<T> extends AtomicInteger implements Disposable {
      private static final long serialVersionUID = 2728361546769921047L;
      volatile boolean cancelled;
      final Observer<? super T> child;
      Object index;
      final ObservableReplay.ReplayObserver<T> parent;

      InnerDisposable(ObservableReplay.ReplayObserver<T> var1, Observer<? super T> var2) {
         this.parent = var1;
         this.child = var2;
      }

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.parent.remove(this);
            this.index = null;
         }
      }

      <U> U index() {
         return (U)this.index;
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }
   }

   static final class MulticastReplay<R, U> extends Observable<R> {
      private final Callable<? extends ConnectableObservable<U>> connectableFactory;
      private final Function<? super Observable<U>, ? extends ObservableSource<R>> selector;

      MulticastReplay(Callable<? extends ConnectableObservable<U>> var1, Function<? super Observable<U>, ? extends ObservableSource<R>> var2) {
         this.connectableFactory = var1;
         this.selector = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      protected void subscribeActual(Observer<? super R> var1) {
         ObservableSource var2;
         ConnectableObservable var3;
         try {
            var3 = ObjectHelper.requireNonNull(this.connectableFactory.call(), "The connectableFactory returned a null ConnectableObservable");
            var2 = ObjectHelper.requireNonNull(this.selector.apply(var3), "The selector returned a null ObservableSource");
         } catch (Throwable var5) {
            Exceptions.throwIfFatal(var5);
            EmptyDisposable.error(var5, var1);
            return;
         }

         ObserverResourceWrapper var6 = new ObserverResourceWrapper(var1);
         var2.subscribe(var6);
         var3.connect(new ObservableReplay.DisposeConsumer(var6));
      }
   }

   static final class Node extends AtomicReference<ObservableReplay.Node> {
      private static final long serialVersionUID = 245354315435971818L;
      final Object value;

      Node(Object var1) {
         this.value = var1;
      }
   }

   static final class Replay<T> extends ConnectableObservable<T> {
      private final ConnectableObservable<T> co;
      private final Observable<T> observable;

      Replay(ConnectableObservable<T> var1, Observable<T> var2) {
         this.co = var1;
         this.observable = var2;
      }

      @Override
      public void connect(Consumer<? super Disposable> var1) {
         this.co.connect(var1);
      }

      @Override
      protected void subscribeActual(Observer<? super T> var1) {
         this.observable.subscribe(var1);
      }
   }

   interface ReplayBuffer<T> {
      void complete();

      void error(Throwable var1);

      void next(T var1);

      void replay(ObservableReplay.InnerDisposable<T> var1);
   }

   static final class ReplayBufferSupplier<T> implements ObservableReplay.BufferSupplier<T> {
      private final int bufferSize;

      ReplayBufferSupplier(int var1) {
         this.bufferSize = var1;
      }

      @Override
      public ObservableReplay.ReplayBuffer<T> call() {
         return new ObservableReplay.SizeBoundReplayBuffer<>(this.bufferSize);
      }
   }

   static final class ReplayObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
      static final ObservableReplay.InnerDisposable[] EMPTY = new ObservableReplay.InnerDisposable[0];
      static final ObservableReplay.InnerDisposable[] TERMINATED = new ObservableReplay.InnerDisposable[0];
      private static final long serialVersionUID = -533785617179540163L;
      final ObservableReplay.ReplayBuffer<T> buffer;
      boolean done;
      final AtomicReference<ObservableReplay.InnerDisposable[]> observers;
      final AtomicBoolean shouldConnect;

      ReplayObserver(ObservableReplay.ReplayBuffer<T> var1) {
         this.buffer = var1;
         this.observers = new AtomicReference<>(EMPTY);
         this.shouldConnect = new AtomicBoolean();
      }

      boolean add(ObservableReplay.InnerDisposable<T> var1) {
         ObservableReplay.InnerDisposable[] var3;
         ObservableReplay.InnerDisposable[] var4;
         do {
            var4 = this.observers.get();
            if (var4 == TERMINATED) {
               return false;
            }

            int var2 = var4.length;
            var3 = new ObservableReplay.InnerDisposable[var2 + 1];
            System.arraycopy(var4, 0, var3, 0, var2);
            var3[var2] = var1;
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var4, var3));

         return true;
      }

      @Override
      public void dispose() {
         this.observers.set(TERMINATED);
         DisposableHelper.dispose(this);
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.observers.get() == TERMINATED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.buffer.complete();
            this.replayFinal();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (!this.done) {
            this.done = true;
            this.buffer.error(var1);
            this.replayFinal();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         if (!this.done) {
            this.buffer.next((T)var1);
            this.replay();
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1)) {
            this.replay();
         }
      }

      void remove(ObservableReplay.InnerDisposable<T> var1) {
         ObservableReplay.InnerDisposable[] var4;
         ObservableReplay.InnerDisposable[] var5;
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
               var4 = new ObservableReplay.InnerDisposable[var3 - 1];
               System.arraycopy(var5, 0, var4, 0, var2);
               System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
            }
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var5, var4));
      }

      void replay() {
         for (ObservableReplay.InnerDisposable var4 : this.observers.get()) {
            this.buffer.replay(var4);
         }
      }

      void replayFinal() {
         for (ObservableReplay.InnerDisposable var4 : this.observers.getAndSet(TERMINATED)) {
            this.buffer.replay(var4);
         }
      }
   }

   static final class ReplaySource<T> implements ObservableSource<T> {
      private final ObservableReplay.BufferSupplier<T> bufferFactory;
      private final AtomicReference<ObservableReplay.ReplayObserver<T>> curr;

      ReplaySource(AtomicReference<ObservableReplay.ReplayObserver<T>> var1, ObservableReplay.BufferSupplier<T> var2) {
         this.curr = var1;
         this.bufferFactory = var2;
      }

      @Override
      public void subscribe(Observer<? super T> var1) {
         while (true) {
            ObservableReplay.ReplayObserver var3 = this.curr.get();
            ObservableReplay.ReplayObserver var2 = var3;
            if (var3 == null) {
               var2 = new ObservableReplay.ReplayObserver<>(this.bufferFactory.call());
               if (!ExternalSyntheticBackportWithForwarding0.m(this.curr, null, var2)) {
                  continue;
               }
            }

            ObservableReplay.InnerDisposable var4 = new ObservableReplay.InnerDisposable(var2, var1);
            var1.onSubscribe(var4);
            var2.add(var4);
            if (var4.isDisposed()) {
               var2.remove(var4);
               return;
            }

            var2.buffer.replay(var4);
            return;
         }
      }
   }

   static final class ScheduledReplaySupplier<T> implements ObservableReplay.BufferSupplier<T> {
      private final int bufferSize;
      private final long maxAge;
      private final Scheduler scheduler;
      private final TimeUnit unit;

      ScheduledReplaySupplier(int var1, long var2, TimeUnit var4, Scheduler var5) {
         this.bufferSize = var1;
         this.maxAge = var2;
         this.unit = var4;
         this.scheduler = var5;
      }

      @Override
      public ObservableReplay.ReplayBuffer<T> call() {
         return new ObservableReplay.SizeAndTimeBoundReplayBuffer<>(this.bufferSize, this.maxAge, this.unit, this.scheduler);
      }
   }

   static final class SizeAndTimeBoundReplayBuffer<T> extends ObservableReplay.BoundedReplayBuffer<T> {
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
      ObservableReplay.Node getHead() {
         long var1 = this.scheduler.now(this.unit);
         long var3 = this.maxAge;
         ObservableReplay.Node var6 = this.get();
         ObservableReplay.Node var5 = var6.get();

         while (var5 != null) {
            Timed var7 = (Timed)var5.value;
            if (NotificationLite.isComplete(var7.value()) || NotificationLite.isError(var7.value()) || var7.time() > var1 - var3) {
               break;
            }

            ObservableReplay.Node var8 = var5.get();
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
         ObservableReplay.Node var7 = this.get();
         ObservableReplay.Node var6 = var7.get();
         int var1 = 0;

         while (var6 != null) {
            if (this.size > this.limit && this.size > 1) {
               var1++;
               this.size--;
               ObservableReplay.Node var9 = var6.get();
               var7 = var6;
               var6 = var9;
            } else {
               if (((Timed)var6.value).time() > var2 - var4) {
                  break;
               }

               var1++;
               this.size--;
               ObservableReplay.Node var8 = var6.get();
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
         long var2 = this.scheduler.now(this.unit);
         long var4 = this.maxAge;
         ObservableReplay.Node var7 = this.get();
         ObservableReplay.Node var6 = var7.get();
         int var1 = 0;

         while (var6 != null && this.size > 1 && ((Timed)var6.value).time() <= var2 - var4) {
            var1++;
            this.size--;
            ObservableReplay.Node var8 = var6.get();
            var7 = var6;
            var6 = var8;
         }

         if (var1 != 0) {
            this.setFirst(var7);
         }
      }
   }

   static final class SizeBoundReplayBuffer<T> extends ObservableReplay.BoundedReplayBuffer<T> {
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

   static final class UnBoundedFactory implements ObservableReplay.BufferSupplier<Object> {
      @Override
      public ObservableReplay.ReplayBuffer<Object> call() {
         return new ObservableReplay.UnboundedReplayBuffer<>(16);
      }
   }

   static final class UnboundedReplayBuffer<T> extends ArrayList<Object> implements ObservableReplay.ReplayBuffer<T> {
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
      public void replay(ObservableReplay.InnerDisposable<T> var1) {
         if (var1.getAndIncrement() == 0) {
            Observer var5 = var1.child;
            int var3 = 1;

            while (!var1.isDisposed()) {
               int var4 = this.size;
               Integer var6 = var1.index();
               int var2;
               if (var6 != null) {
                  var2 = var6;
               } else {
                  var2 = 0;
               }

               while (var2 < var4) {
                  if (NotificationLite.accept(this.get(var2), var5)) {
                     return;
                  }

                  if (var1.isDisposed()) {
                     return;
                  }

                  var2++;
               }

               var1.index = var2;
               var2 = var1.addAndGet(-var3);
               var3 = var2;
               if (var2 == 0) {
                  return;
               }
            }
         }
      }
   }
}
