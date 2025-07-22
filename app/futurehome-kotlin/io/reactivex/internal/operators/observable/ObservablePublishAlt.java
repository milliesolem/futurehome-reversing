package io.reactivex.internal.operators.observable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observables.ConnectableObservable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservablePublishAlt<T> extends ConnectableObservable<T> implements HasUpstreamObservableSource<T>, ResettableConnectable {
   final AtomicReference<ObservablePublishAlt.PublishConnection<T>> current;
   final ObservableSource<T> source;

   public ObservablePublishAlt(ObservableSource<T> var1) {
      this.source = var1;
      this.current = new AtomicReference<>();
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void connect(Consumer<? super Disposable> var1) {
      ObservablePublishAlt.PublishConnection var5;
      ObservablePublishAlt.PublishConnection var6;
      do {
         var6 = this.current.get();
         if (var6 != null) {
            var5 = var6;
            if (!var6.isDisposed()) {
               break;
            }
         }

         var5 = new ObservablePublishAlt.PublishConnection<>(this.current);
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.current, var6, var5));

      boolean var4 = var5.connect.get();
      boolean var3 = false;
      boolean var2 = var3;
      if (!var4) {
         var2 = var3;
         if (var5.connect.compareAndSet(false, true)) {
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
   public void resetIf(Disposable var1) {
      ExternalSyntheticBackportWithForwarding0.m(this.current, (ObservablePublishAlt.PublishConnection)var1, null);
   }

   @Override
   public ObservableSource<T> source() {
      return this.source;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      while (true) {
         ObservablePublishAlt.PublishConnection var3 = this.current.get();
         ObservablePublishAlt.PublishConnection var2 = var3;
         if (var3 == null) {
            var2 = new ObservablePublishAlt.PublishConnection<>(this.current);
            if (!ExternalSyntheticBackportWithForwarding0.m(this.current, var3, var2)) {
               continue;
            }
         }

         ObservablePublishAlt.InnerDisposable var5 = new ObservablePublishAlt.InnerDisposable(var1, var2);
         var1.onSubscribe(var5);
         if (var2.add(var5)) {
            if (var5.isDisposed()) {
               var2.remove(var5);
            }

            return;
         }

         Throwable var4 = var2.error;
         if (var4 != null) {
            var1.onError(var4);
         } else {
            var1.onComplete();
         }

         return;
      }
   }

   static final class InnerDisposable<T> extends AtomicReference<ObservablePublishAlt.PublishConnection<T>> implements Disposable {
      private static final long serialVersionUID = 7463222674719692880L;
      final Observer<? super T> downstream;

      InnerDisposable(Observer<? super T> var1, ObservablePublishAlt.PublishConnection<T> var2) {
         this.downstream = var1;
         this.lazySet(var2);
      }

      @Override
      public void dispose() {
         ObservablePublishAlt.PublishConnection var1 = this.getAndSet(null);
         if (var1 != null) {
            var1.remove(this);
         }
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   static final class PublishConnection<T> extends AtomicReference<ObservablePublishAlt.InnerDisposable<T>[]> implements Observer<T>, Disposable {
      static final ObservablePublishAlt.InnerDisposable[] EMPTY = new ObservablePublishAlt.InnerDisposable[0];
      static final ObservablePublishAlt.InnerDisposable[] TERMINATED = new ObservablePublishAlt.InnerDisposable[0];
      private static final long serialVersionUID = -3251430252873581268L;
      final AtomicBoolean connect = new AtomicBoolean();
      final AtomicReference<ObservablePublishAlt.PublishConnection<T>> current;
      Throwable error;
      final AtomicReference<Disposable> upstream;

      PublishConnection(AtomicReference<ObservablePublishAlt.PublishConnection<T>> var1) {
         this.current = var1;
         this.upstream = new AtomicReference<>();
         this.lazySet(EMPTY);
      }

      public boolean add(ObservablePublishAlt.InnerDisposable<T> var1) {
         ObservablePublishAlt.InnerDisposable[] var3;
         ObservablePublishAlt.InnerDisposable[] var4;
         do {
            var4 = this.get();
            if (var4 == TERMINATED) {
               return false;
            }

            int var2 = var4.length;
            var3 = new ObservablePublishAlt.InnerDisposable[var2 + 1];
            System.arraycopy(var4, 0, var3, 0, var2);
            var3[var2] = var1;
         } while (!this.compareAndSet(var4, var3));

         return true;
      }

      @Override
      public void dispose() {
         this.getAndSet(TERMINATED);
         ExternalSyntheticBackportWithForwarding0.m(this.current, this, null);
         DisposableHelper.dispose(this.upstream);
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == TERMINATED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void onComplete() {
         this.upstream.lazySet(DisposableHelper.DISPOSED);
         ObservablePublishAlt.InnerDisposable[] var3 = this.getAndSet(TERMINATED);
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.error = var1;
         this.upstream.lazySet(DisposableHelper.DISPOSED);
         ObservablePublishAlt.InnerDisposable[] var4 = this.getAndSet(TERMINATED);
         int var3 = var4.length;

         for (int var2 = 0; var2 < var3; var2++) {
            var4[var2].downstream.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         ObservablePublishAlt.InnerDisposable[] var4 = this.get();
         int var3 = var4.length;

         for (int var2 = 0; var2 < var3; var2++) {
            var4[var2].downstream.onNext((T)var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.upstream, var1);
      }

      public void remove(ObservablePublishAlt.InnerDisposable<T> var1) {
         ObservablePublishAlt.InnerDisposable[] var4;
         ObservablePublishAlt.InnerDisposable[] var5;
         do {
            var5 = this.get();
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

            var4 = EMPTY;
            if (var3 != 1) {
               var4 = new ObservablePublishAlt.InnerDisposable[var3 - 1];
               System.arraycopy(var5, 0, var4, 0, var2);
               System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
            }
         } while (!this.compareAndSet(var5, var4));
      }
   }
}
