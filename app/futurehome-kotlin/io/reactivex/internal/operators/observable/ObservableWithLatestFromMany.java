package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class ObservableWithLatestFromMany<T, R> extends AbstractObservableWithUpstream<T, R> {
   final Function<? super Object[], R> combiner;
   final ObservableSource<?>[] otherArray;
   final Iterable<? extends ObservableSource<?>> otherIterable;

   public ObservableWithLatestFromMany(ObservableSource<T> var1, Iterable<? extends ObservableSource<?>> var2, Function<? super Object[], R> var3) {
      super(var1);
      this.otherArray = null;
      this.otherIterable = var2;
      this.combiner = var3;
   }

   public ObservableWithLatestFromMany(ObservableSource<T> var1, ObservableSource<?>[] var2, Function<? super Object[], R> var3) {
      super(var1);
      this.otherArray = var2;
      this.otherIterable = null;
      this.combiner = var3;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(Observer<? super R> var1) {
      ObservableSource[] var5 = this.otherArray;
      int var3;
      if (var5 == null) {
         ObservableSource[] var4 = new ObservableSource[8];

         Iterator var6;
         try {
            var6 = this.otherIterable.iterator();
         } catch (Throwable var17) {
            Exceptions.throwIfFatal(var17);
            EmptyDisposable.error(var17, var1);
            return;
         }

         int var2 = 0;

         while (true) {
            var5 = var4;
            var3 = var2;

            ObservableSource var7;
            try {
               if (!var6.hasNext()) {
                  break;
               }

               var7 = (ObservableSource)var6.next();
            } catch (Throwable var18) {
               Exceptions.throwIfFatal(var18);
               EmptyDisposable.error(var18, var1);
               return;
            }

            var5 = var4;

            try {
               if (var2 == var4.length) {
                  var5 = Arrays.copyOf(var4, (var2 >> 1) + var2);
               }
            } catch (Throwable var19) {
               Exceptions.throwIfFatal(var19);
               EmptyDisposable.error(var19, var1);
               return;
            }

            var5[var2] = var7;
            var2++;
            var4 = var5;
         }
      } else {
         var3 = var5.length;
      }

      if (var3 == 0) {
         new ObservableMap<>(this.source, new ObservableWithLatestFromMany.SingletonArrayFunc(this)).subscribeActual(var1);
      } else {
         ObservableWithLatestFromMany.WithLatestFromObserver var20 = new ObservableWithLatestFromMany.WithLatestFromObserver<>(var1, this.combiner, var3);
         var1.onSubscribe(var20);
         var20.subscribe(var5, var3);
         this.source.subscribe(var20);
      }
   }

   final class SingletonArrayFunc implements Function<T, R> {
      final ObservableWithLatestFromMany this$0;

      SingletonArrayFunc(ObservableWithLatestFromMany var1) {
         this.this$0 = var1;
      }

      @Override
      public R apply(T var1) throws Exception {
         return ObjectHelper.requireNonNull(this.this$0.combiner.apply(new Object[]{var1}), "The combiner returned a null value");
      }
   }

   static final class WithLatestFromObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
      private static final long serialVersionUID = 1577321883966341961L;
      final Function<? super Object[], R> combiner;
      volatile boolean done;
      final Observer<? super R> downstream;
      final AtomicThrowable error;
      final ObservableWithLatestFromMany.WithLatestInnerObserver[] observers;
      final AtomicReference<Disposable> upstream;
      final AtomicReferenceArray<Object> values;

      WithLatestFromObserver(Observer<? super R> var1, Function<? super Object[], R> var2, int var3) {
         this.downstream = var1;
         this.combiner = var2;
         ObservableWithLatestFromMany.WithLatestInnerObserver[] var5 = new ObservableWithLatestFromMany.WithLatestInnerObserver[var3];

         for (int var4 = 0; var4 < var3; var4++) {
            var5[var4] = new ObservableWithLatestFromMany.WithLatestInnerObserver(this, var4);
         }

         this.observers = var5;
         this.values = new AtomicReferenceArray<>(var3);
         this.upstream = new AtomicReference<>();
         this.error = new AtomicThrowable();
      }

      void cancelAllBut(int var1) {
         ObservableWithLatestFromMany.WithLatestInnerObserver[] var3 = this.observers;

         for (int var2 = 0; var2 < var3.length; var2++) {
            if (var2 != var1) {
               var3[var2].dispose();
            }
         }
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this.upstream);
         ObservableWithLatestFromMany.WithLatestInnerObserver[] var3 = this.observers;
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].dispose();
         }
      }

      void innerComplete(int var1, boolean var2) {
         if (!var2) {
            this.done = true;
            this.cancelAllBut(var1);
            HalfSerializer.onComplete(this.downstream, this, this.error);
         }
      }

      void innerError(int var1, Throwable var2) {
         this.done = true;
         DisposableHelper.dispose(this.upstream);
         this.cancelAllBut(var1);
         HalfSerializer.onError(this.downstream, var2, this, this.error);
      }

      void innerNext(int var1, Object var2) {
         this.values.set(var1, var2);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.upstream.get());
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.cancelAllBut(-1);
            HalfSerializer.onComplete(this.downstream, this, this.error);
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.cancelAllBut(-1);
            HalfSerializer.onError(this.downstream, var1, this, this.error);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         if (!this.done) {
            AtomicReferenceArray var4 = this.values;
            int var3 = var4.length();
            Object[] var5 = new Object[var3 + 1];
            int var2 = 0;
            var5[0] = var1;

            while (var2 < var3) {
               var1 = var4.get(var2);
               if (var1 == null) {
                  return;
               }

               var5[++var2] = var1;
            }

            try {
               var1 = ObjectHelper.requireNonNull(this.combiner.apply(var5), "combiner returned a null value");
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               this.dispose();
               this.onError(var7);
               return;
            }

            HalfSerializer.onNext(this.downstream, (R)var1, this, this.error);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.upstream, var1);
      }

      void subscribe(ObservableSource<?>[] var1, int var2) {
         ObservableWithLatestFromMany.WithLatestInnerObserver[] var5 = this.observers;
         AtomicReference var4 = this.upstream;

         for (int var3 = 0; var3 < var2 && !DisposableHelper.isDisposed((Disposable)var4.get()) && !this.done; var3++) {
            var1[var3].subscribe(var5[var3]);
         }
      }
   }

   static final class WithLatestInnerObserver extends AtomicReference<Disposable> implements Observer<Object> {
      private static final long serialVersionUID = 3256684027868224024L;
      boolean hasValue;
      final int index;
      final ObservableWithLatestFromMany.WithLatestFromObserver<?, ?> parent;

      WithLatestInnerObserver(ObservableWithLatestFromMany.WithLatestFromObserver<?, ?> var1, int var2) {
         this.parent = var1;
         this.index = var2;
      }

      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public void onComplete() {
         this.parent.innerComplete(this.index, this.hasValue);
      }

      @Override
      public void onError(Throwable var1) {
         this.parent.innerError(this.index, var1);
      }

      @Override
      public void onNext(Object var1) {
         if (!this.hasValue) {
            this.hasValue = true;
         }

         this.parent.innerNext(this.index, var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }
   }
}
