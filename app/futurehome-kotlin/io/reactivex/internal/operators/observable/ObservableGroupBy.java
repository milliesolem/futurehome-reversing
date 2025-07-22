package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.observables.GroupedObservable;
import j..util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableGroupBy<T, K, V> extends AbstractObservableWithUpstream<T, GroupedObservable<K, V>> {
   final int bufferSize;
   final boolean delayError;
   final Function<? super T, ? extends K> keySelector;
   final Function<? super T, ? extends V> valueSelector;

   public ObservableGroupBy(ObservableSource<T> var1, Function<? super T, ? extends K> var2, Function<? super T, ? extends V> var3, int var4, boolean var5) {
      super(var1);
      this.keySelector = var2;
      this.valueSelector = var3;
      this.bufferSize = var4;
      this.delayError = var5;
   }

   @Override
   public void subscribeActual(Observer<? super GroupedObservable<K, V>> var1) {
      this.source.subscribe(new ObservableGroupBy.GroupByObserver<>(var1, this.keySelector, this.valueSelector, this.bufferSize, this.delayError));
   }

   public static final class GroupByObserver<T, K, V> extends AtomicInteger implements Observer<T>, Disposable {
      static final Object NULL_KEY = new Object();
      private static final long serialVersionUID = -3688291656102519502L;
      final int bufferSize;
      final AtomicBoolean cancelled = new AtomicBoolean();
      final boolean delayError;
      final Observer<? super GroupedObservable<K, V>> downstream;
      final Map<Object, ObservableGroupBy.GroupedUnicast<K, V>> groups;
      final Function<? super T, ? extends K> keySelector;
      Disposable upstream;
      final Function<? super T, ? extends V> valueSelector;

      public GroupByObserver(
         Observer<? super GroupedObservable<K, V>> var1, Function<? super T, ? extends K> var2, Function<? super T, ? extends V> var3, int var4, boolean var5
      ) {
         this.downstream = var1;
         this.keySelector = var2;
         this.valueSelector = var3;
         this.bufferSize = var4;
         this.delayError = var5;
         this.groups = new ConcurrentHashMap();
         this.lazySet(1);
      }

      public void cancel(K var1) {
         if (var1 == null) {
            var1 = NULL_KEY;
         }

         this.groups.remove(var1);
         if (this.decrementAndGet() == 0) {
            this.upstream.dispose();
         }
      }

      @Override
      public void dispose() {
         if (this.cancelled.compareAndSet(false, true) && this.decrementAndGet() == 0) {
            this.upstream.dispose();
         }
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled.get();
      }

      @Override
      public void onComplete() {
         ArrayList var1 = new ArrayList<>(this.groups.values());
         this.groups.clear();
         Iterator var2 = var1.iterator();

         while (var2.hasNext()) {
            ((ObservableGroupBy.GroupedUnicast)var2.next()).onComplete();
         }

         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         ArrayList var2 = new ArrayList<>(this.groups.values());
         this.groups.clear();
         Iterator var3 = var2.iterator();

         while (var3.hasNext()) {
            ((ObservableGroupBy.GroupedUnicast)var3.next()).onError(var1);
         }

         this.downstream.onError(var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         Object var4;
         try {
            var4 = this.keySelector.apply((T)var1);
         } catch (Throwable var11) {
            Exceptions.throwIfFatal(var11);
            this.upstream.dispose();
            this.onError(var11);
            return;
         }

         Object var3;
         if (var4 != null) {
            var3 = var4;
         } else {
            var3 = NULL_KEY;
         }

         ObservableGroupBy.GroupedUnicast var5 = this.groups.get(var3);
         ObservableGroupBy.GroupedUnicast var2 = var5;
         if (var5 == null) {
            if (this.cancelled.get()) {
               return;
            }

            var2 = ObservableGroupBy.GroupedUnicast.createWith(var4, this.bufferSize, this, this.delayError);
            this.groups.put(var3, var2);
            this.getAndIncrement();
            this.downstream.onNext(var2);
         }

         try {
            var1 = ObjectHelper.requireNonNull(this.valueSelector.apply((T)var1), "The value supplied is null");
         } catch (Throwable var10) {
            Exceptions.throwIfFatal(var10);
            this.upstream.dispose();
            this.onError(var10);
            return;
         }

         var2.onNext(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }

   static final class GroupedUnicast<K, T> extends GroupedObservable<K, T> {
      final ObservableGroupBy.State<T, K> state;

      protected GroupedUnicast(K var1, ObservableGroupBy.State<T, K> var2) {
         super((K)var1);
         this.state = var2;
      }

      public static <T, K> ObservableGroupBy.GroupedUnicast<K, T> createWith(K var0, int var1, ObservableGroupBy.GroupByObserver<?, K, T> var2, boolean var3) {
         return new ObservableGroupBy.GroupedUnicast<>((K)var0, new ObservableGroupBy.State<>(var1, var2, (K)var0, var3));
      }

      public void onComplete() {
         this.state.onComplete();
      }

      public void onError(Throwable var1) {
         this.state.onError(var1);
      }

      public void onNext(T var1) {
         this.state.onNext((T)var1);
      }

      @Override
      protected void subscribeActual(Observer<? super T> var1) {
         this.state.subscribe(var1);
      }
   }

   static final class State<T, K> extends AtomicInteger implements Disposable, ObservableSource<T> {
      private static final long serialVersionUID = -3852313036005250360L;
      final AtomicReference<Observer<? super T>> actual;
      final AtomicBoolean cancelled = new AtomicBoolean();
      final boolean delayError;
      volatile boolean done;
      Throwable error;
      final K key;
      final AtomicBoolean once = new AtomicBoolean();
      final ObservableGroupBy.GroupByObserver<?, K, T> parent;
      final SpscLinkedArrayQueue<T> queue;

      State(int var1, ObservableGroupBy.GroupByObserver<?, K, T> var2, K var3, boolean var4) {
         this.actual = new AtomicReference<>();
         this.queue = new SpscLinkedArrayQueue<>(var1);
         this.parent = var2;
         this.key = (K)var3;
         this.delayError = var4;
      }

      boolean checkTerminated(boolean var1, boolean var2, Observer<? super T> var3, boolean var4) {
         if (this.cancelled.get()) {
            this.queue.clear();
            this.parent.cancel(this.key);
            this.actual.lazySet(null);
            return true;
         } else {
            if (var1) {
               if (var4) {
                  if (var2) {
                     Throwable var5 = this.error;
                     this.actual.lazySet(null);
                     if (var5 != null) {
                        var3.onError(var5);
                     } else {
                        var3.onComplete();
                     }

                     return true;
                  }
               } else {
                  Throwable var6 = this.error;
                  if (var6 != null) {
                     this.queue.clear();
                     this.actual.lazySet(null);
                     var3.onError(var6);
                     return true;
                  }

                  if (var2) {
                     this.actual.lazySet(null);
                     var3.onComplete();
                     return true;
                  }
               }
            }

            return false;
         }
      }

      @Override
      public void dispose() {
         if (this.cancelled.compareAndSet(false, true) && this.getAndIncrement() == 0) {
            this.actual.lazySet(null);
            this.parent.cancel(this.key);
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            SpscLinkedArrayQueue var7 = this.queue;
            boolean var4 = this.delayError;
            Observer var6 = this.actual.get();
            int var1 = 1;

            while (true) {
               if (var6 != null) {
                  while (true) {
                     boolean var5 = this.done;
                     Object var8 = var7.poll();
                     boolean var3;
                     if (var8 == null) {
                        var3 = true;
                     } else {
                        var3 = false;
                     }

                     if (this.checkTerminated(var5, var3, var6, var4)) {
                        return;
                     }

                     if (var3) {
                        break;
                     }

                     var6.onNext(var8);
                  }
               }

               int var2 = this.addAndGet(-var1);
               if (var2 == 0) {
                  return;
               }

               var1 = var2;
               if (var6 == null) {
                  var6 = this.actual.get();
                  var1 = var2;
               }
            }
         }
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled.get();
      }

      public void onComplete() {
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         this.drain();
      }

      public void onNext(T var1) {
         this.queue.offer((T)var1);
         this.drain();
      }

      @Override
      public void subscribe(Observer<? super T> var1) {
         if (this.once.compareAndSet(false, true)) {
            var1.onSubscribe(this);
            this.actual.lazySet(var1);
            if (this.cancelled.get()) {
               this.actual.lazySet(null);
            } else {
               this.drain();
            }
         } else {
            EmptyDisposable.error(new IllegalStateException("Only one Observer allowed!"), var1);
         }
      }
   }
}
