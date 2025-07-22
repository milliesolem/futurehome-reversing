package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeZipArray<T, R> extends Maybe<R> {
   final MaybeSource<? extends T>[] sources;
   final Function<? super Object[], ? extends R> zipper;

   public MaybeZipArray(MaybeSource<? extends T>[] var1, Function<? super Object[], ? extends R> var2) {
      this.sources = var1;
      this.zipper = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super R> var1) {
      MaybeSource[] var5 = this.sources;
      int var3 = var5.length;
      int var2 = 0;
      if (var3 == 1) {
         var5[0].subscribe(new MaybeMap.MapMaybeObserver<>(var1, new MaybeZipArray.SingletonArrayFunc(this)));
      } else {
         MaybeZipArray.ZipCoordinator var4 = new MaybeZipArray.ZipCoordinator<>(var1, var3, this.zipper);
         var1.onSubscribe(var4);

         while (var2 < var3) {
            if (var4.isDisposed()) {
               return;
            }

            MaybeSource var6 = var5[var2];
            if (var6 == null) {
               var4.innerError(new NullPointerException("One of the sources is null"), var2);
               return;
            }

            var6.subscribe(var4.observers[var2]);
            var2++;
         }
      }
   }

   final class SingletonArrayFunc implements Function<T, R> {
      final MaybeZipArray this$0;

      SingletonArrayFunc(MaybeZipArray var1) {
         this.this$0 = var1;
      }

      @Override
      public R apply(T var1) throws Exception {
         return ObjectHelper.requireNonNull((R)this.this$0.zipper.apply(new Object[]{var1}), "The zipper returned a null value");
      }
   }

   static final class ZipCoordinator<T, R> extends AtomicInteger implements Disposable {
      private static final long serialVersionUID = -5556924161382950569L;
      final MaybeObserver<? super R> downstream;
      final MaybeZipArray.ZipMaybeObserver<T>[] observers;
      final Object[] values;
      final Function<? super Object[], ? extends R> zipper;

      ZipCoordinator(MaybeObserver<? super R> var1, int var2, Function<? super Object[], ? extends R> var3) {
         super(var2);
         this.downstream = var1;
         this.zipper = var3;
         MaybeZipArray.ZipMaybeObserver[] var5 = new MaybeZipArray.ZipMaybeObserver[var2];

         for (int var4 = 0; var4 < var2; var4++) {
            var5[var4] = new MaybeZipArray.ZipMaybeObserver<>(this, var4);
         }

         this.observers = var5;
         this.values = new Object[var2];
      }

      @Override
      public void dispose() {
         int var1 = 0;
         if (this.getAndSet(0) > 0) {
            MaybeZipArray.ZipMaybeObserver[] var3 = this.observers;

            for (int var2 = var3.length; var1 < var2; var1++) {
               var3[var1].dispose();
            }
         }
      }

      void disposeExcept(int var1) {
         MaybeZipArray.ZipMaybeObserver[] var5 = this.observers;
         int var4 = var5.length;
         int var3 = 0;

         while (true) {
            int var2 = var1;
            if (var3 >= var1) {
               while (++var2 < var4) {
                  var5[var2].dispose();
               }

               return;
            }

            var5[var3].dispose();
            var3++;
         }
      }

      void innerComplete(int var1) {
         if (this.getAndSet(0) > 0) {
            this.disposeExcept(var1);
            this.downstream.onComplete();
         }
      }

      void innerError(Throwable var1, int var2) {
         if (this.getAndSet(0) > 0) {
            this.disposeExcept(var2);
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void innerSuccess(T var1, int var2) {
         this.values[var2] = var1;
         if (this.decrementAndGet() == 0) {
            try {
               var1 = ObjectHelper.requireNonNull(this.zipper.apply(this.values), "The zipper returned a null value");
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.downstream.onError(var4);
               return;
            }

            this.downstream.onSuccess((R)var1);
         }
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() <= 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   static final class ZipMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
      private static final long serialVersionUID = 3323743579927613702L;
      final int index;
      final MaybeZipArray.ZipCoordinator<T, ?> parent;

      ZipMaybeObserver(MaybeZipArray.ZipCoordinator<T, ?> var1, int var2) {
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
         this.parent.innerError(var1, this.index);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.parent.innerSuccess((T)var1, this.index);
      }
   }
}
