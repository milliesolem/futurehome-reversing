package io.reactivex.internal.operators.observable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableCache<T> extends AbstractObservableWithUpstream<T, T> implements Observer<T> {
   static final ObservableCache.CacheDisposable[] EMPTY = new ObservableCache.CacheDisposable[0];
   static final ObservableCache.CacheDisposable[] TERMINATED = new ObservableCache.CacheDisposable[0];
   final int capacityHint;
   volatile boolean done;
   Throwable error;
   final ObservableCache.Node<T> head;
   final AtomicReference<ObservableCache.CacheDisposable<T>[]> observers;
   final AtomicBoolean once;
   volatile long size;
   ObservableCache.Node<T> tail;
   int tailOffset;

   public ObservableCache(Observable<T> var1, int var2) {
      super(var1);
      this.capacityHint = var2;
      this.once = new AtomicBoolean();
      ObservableCache.Node var3 = new ObservableCache.Node(var2);
      this.head = var3;
      this.tail = var3;
      this.observers = new AtomicReference<>(EMPTY);
   }

   void add(ObservableCache.CacheDisposable<T> var1) {
      ObservableCache.CacheDisposable[] var3;
      ObservableCache.CacheDisposable[] var4;
      do {
         var3 = this.observers.get();
         if (var3 == TERMINATED) {
            return;
         }

         int var2 = var3.length;
         var4 = new ObservableCache.CacheDisposable[var2 + 1];
         System.arraycopy(var3, 0, var4, 0, var2);
         var4[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var3, var4));
   }

   long cachedEventCount() {
      return this.size;
   }

   boolean hasObservers() {
      boolean var1;
      if (((ObservableCache.CacheDisposable[])this.observers.get()).length != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   boolean isConnected() {
      return this.once.get();
   }

   @Override
   public void onComplete() {
      this.done = true;
      ObservableCache.CacheDisposable[] var3 = this.observers.getAndSet(TERMINATED);
      int var2 = var3.length;

      for (int var1 = 0; var1 < var2; var1++) {
         this.replay(var3[var1]);
      }
   }

   @Override
   public void onError(Throwable var1) {
      this.error = var1;
      this.done = true;
      ObservableCache.CacheDisposable[] var4 = this.observers.getAndSet(TERMINATED);
      int var3 = var4.length;

      for (int var2 = 0; var2 < var3; var2++) {
         this.replay(var4[var2]);
      }
   }

   @Override
   public void onNext(T var1) {
      int var4 = this.tailOffset;
      int var3 = this.capacityHint;
      int var2 = 0;
      if (var4 == var3) {
         ObservableCache.Node var5 = new ObservableCache.Node(var4);
         var5.values[0] = (T)var1;
         this.tailOffset = 1;
         this.tail.next = var5;
         this.tail = var5;
      } else {
         this.tail.values[var4] = (T)var1;
         this.tailOffset = var4 + 1;
      }

      this.size++;
      var1 = this.observers.get();

      for (int var7 = var1.length; var2 < var7; var2++) {
         this.replay(var1[var2]);
      }
   }

   @Override
   public void onSubscribe(Disposable var1) {
   }

   void remove(ObservableCache.CacheDisposable<T> var1) {
      ObservableCache.CacheDisposable[] var4;
      ObservableCache.CacheDisposable[] var5;
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
            var4 = new ObservableCache.CacheDisposable[var3 - 1];
            System.arraycopy(var5, 0, var4, 0, var2);
            System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var5, var4));
   }

   void replay(ObservableCache.CacheDisposable<T> var1) {
      if (var1.getAndIncrement() == 0) {
         long var6 = var1.index;
         int var2 = var1.offset;
         ObservableCache.Node var10 = var1.node;
         Observer var11 = var1.downstream;
         int var5 = this.capacityHint;
         int var3 = 1;

         while (!var1.disposed) {
            boolean var8 = this.done;
            boolean var4;
            if (this.size == var6) {
               var4 = 1;
            } else {
               var4 = 0;
            }

            if (var8 && var4) {
               var1.node = null;
               Throwable var12 = this.error;
               if (var12 != null) {
                  var11.onError(var12);
               } else {
                  var11.onComplete();
               }

               return;
            }

            if (!var4) {
               var4 = var2;
               ObservableCache.Node var9 = var10;
               if (var2 == var5) {
                  var9 = var10.next;
                  var4 = 0;
               }

               var11.onNext(var9.values[var4]);
               var2 = var4 + 1;
               var6++;
               var10 = var9;
            } else {
               var1.index = var6;
               var1.offset = var2;
               var1.node = var10;
               var4 = var1.addAndGet(-var3);
               var3 = var4;
               if (var4 == 0) {
                  return;
               }
            }
         }

         var1.node = null;
      }
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      ObservableCache.CacheDisposable var2 = new ObservableCache.CacheDisposable<>(var1, this);
      var1.onSubscribe(var2);
      this.add(var2);
      if (!this.once.get() && this.once.compareAndSet(false, true)) {
         this.source.subscribe(this);
      } else {
         this.replay(var2);
      }
   }

   static final class CacheDisposable<T> extends AtomicInteger implements Disposable {
      private static final long serialVersionUID = 6770240836423125754L;
      volatile boolean disposed;
      final Observer<? super T> downstream;
      long index;
      ObservableCache.Node<T> node;
      int offset;
      final ObservableCache<T> parent;

      CacheDisposable(Observer<? super T> var1, ObservableCache<T> var2) {
         this.downstream = var1;
         this.parent = var2;
         this.node = var2.head;
      }

      @Override
      public void dispose() {
         if (!this.disposed) {
            this.disposed = true;
            this.parent.remove(this);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }
   }

   static final class Node<T> {
      volatile ObservableCache.Node<T> next;
      final T[] values;

      Node(int var1) {
         this.values = (T[])(new Object[var1]);
      }
   }
}
