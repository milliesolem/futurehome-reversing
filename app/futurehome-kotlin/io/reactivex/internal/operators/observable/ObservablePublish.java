package io.reactivex.internal.operators.observable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservablePublish<T> extends ConnectableObservable<T> implements HasUpstreamObservableSource<T>, ObservablePublishClassic<T> {
   final AtomicReference<ObservablePublish.PublishObserver<T>> current;
   final ObservableSource<T> onSubscribe;
   final ObservableSource<T> source;

   private ObservablePublish(ObservableSource<T> var1, ObservableSource<T> var2, AtomicReference<ObservablePublish.PublishObserver<T>> var3) {
      this.onSubscribe = var1;
      this.source = var2;
      this.current = var3;
   }

   public static <T> ConnectableObservable<T> create(ObservableSource<T> var0) {
      AtomicReference var1 = new AtomicReference();
      return RxJavaPlugins.onAssembly(new ObservablePublish<>(new ObservablePublish.PublishSource<>(var1), var0, var1));
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void connect(Consumer<? super Disposable> var1) {
      ObservablePublish.PublishObserver var5;
      ObservablePublish.PublishObserver var6;
      do {
         var6 = this.current.get();
         if (var6 != null) {
            var5 = var6;
            if (!var6.isDisposed()) {
               break;
            }
         }

         var5 = new ObservablePublish.PublishObserver<>(this.current);
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.current, var6, var5));

      boolean var4 = var5.shouldConnect.get();
      boolean var3 = false;
      boolean var2 = var3;
      if (!var4) {
         var2 = var3;
         if (var5.shouldConnect.compareAndSet(false, true)) {
            var2 = true;
         }
      }

      try {
         var1.accept(var5);
      } catch (Throwable var8) {
         Exceptions.throwIfFatal(var8);
         throw ExceptionHelper.wrapOrThrow(var8);
      }

      if (var2) {
         this.source.subscribe(var5);
      }
   }

   @Override
   public ObservableSource<T> publishSource() {
      return this.source;
   }

   @Override
   public ObservableSource<T> source() {
      return this.source;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.onSubscribe.subscribe(var1);
   }

   static final class InnerDisposable<T> extends AtomicReference<Object> implements Disposable {
      private static final long serialVersionUID = -1100270633763673112L;
      final Observer<? super T> child;

      InnerDisposable(Observer<? super T> var1) {
         this.child = var1;
      }

      @Override
      public void dispose() {
         Object var1 = this.getAndSet(this);
         if (var1 != null && var1 != this) {
            ((ObservablePublish.PublishObserver)var1).remove(this);
         }
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == this) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      void setParent(ObservablePublish.PublishObserver<T> var1) {
         if (!this.compareAndSet(null, var1)) {
            var1.remove(this);
         }
      }
   }

   static final class PublishObserver<T> implements Observer<T>, Disposable {
      static final ObservablePublish.InnerDisposable[] EMPTY = new ObservablePublish.InnerDisposable[0];
      static final ObservablePublish.InnerDisposable[] TERMINATED = new ObservablePublish.InnerDisposable[0];
      final AtomicReference<ObservablePublish.PublishObserver<T>> current;
      final AtomicReference<ObservablePublish.InnerDisposable<T>[]> observers;
      final AtomicBoolean shouldConnect;
      final AtomicReference<Disposable> upstream = new AtomicReference<>();

      PublishObserver(AtomicReference<ObservablePublish.PublishObserver<T>> var1) {
         this.observers = new AtomicReference<>(EMPTY);
         this.current = var1;
         this.shouldConnect = new AtomicBoolean();
      }

      boolean add(ObservablePublish.InnerDisposable<T> var1) {
         ObservablePublish.InnerDisposable[] var3;
         ObservablePublish.InnerDisposable[] var4;
         do {
            var4 = this.observers.get();
            if (var4 == TERMINATED) {
               return false;
            }

            int var2 = var4.length;
            var3 = new ObservablePublish.InnerDisposable[var2 + 1];
            System.arraycopy(var4, 0, var3, 0, var2);
            var3[var2] = var1;
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var4, var3));

         return true;
      }

      @Override
      public void dispose() {
         AtomicReference var2 = this.observers;
         ObservablePublish.InnerDisposable[] var1 = TERMINATED;
         if (var2.getAndSet(var1) != var1) {
            ExternalSyntheticBackportWithForwarding0.m(this.current, this, null);
            DisposableHelper.dispose(this.upstream);
         }
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
         ExternalSyntheticBackportWithForwarding0.m(this.current, this, null);
         ObservablePublish.InnerDisposable[] var3 = this.observers.getAndSet(TERMINATED);
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].child.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         ExternalSyntheticBackportWithForwarding0.m(this.current, this, null);
         ObservablePublish.InnerDisposable[] var4 = this.observers.getAndSet(TERMINATED);
         if (var4.length != 0) {
            int var3 = var4.length;

            for (int var2 = 0; var2 < var3; var2++) {
               var4[var2].child.onError(var1);
            }
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         ObservablePublish.InnerDisposable[] var4 = this.observers.get();
         int var3 = var4.length;

         for (int var2 = 0; var2 < var3; var2++) {
            var4[var2].child.onNext((T)var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.upstream, var1);
      }

      void remove(ObservablePublish.InnerDisposable<T> var1) {
         ObservablePublish.InnerDisposable[] var4;
         ObservablePublish.InnerDisposable[] var5;
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
               var4 = new ObservablePublish.InnerDisposable[var3 - 1];
               System.arraycopy(var5, 0, var4, 0, var2);
               System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
            }
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var5, var4));
      }
   }

   static final class PublishSource<T> implements ObservableSource<T> {
      private final AtomicReference<ObservablePublish.PublishObserver<T>> curr;

      PublishSource(AtomicReference<ObservablePublish.PublishObserver<T>> var1) {
         this.curr = var1;
      }

      @Override
      public void subscribe(Observer<? super T> var1) {
         ObservablePublish.InnerDisposable var3 = new ObservablePublish.InnerDisposable<>(var1);
         var1.onSubscribe(var3);

         ObservablePublish.PublishObserver var2;
         do {
            do {
               var2 = this.curr.get();
               if (var2 != null) {
                  var1 = var2;
                  if (!var2.isDisposed()) {
                     break;
                  }
               }

               var1 = new ObservablePublish.PublishObserver<>(this.curr);
            } while (!ExternalSyntheticBackportWithForwarding0.m(this.curr, var2, var1));
         } while (!var1.add(var3));

         var3.setParent(var1);
      }
   }
}
