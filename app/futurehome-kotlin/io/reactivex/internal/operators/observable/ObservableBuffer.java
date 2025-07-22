package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ObservableBuffer<T, U extends Collection<? super T>> extends AbstractObservableWithUpstream<T, U> {
   final Callable<U> bufferSupplier;
   final int count;
   final int skip;

   public ObservableBuffer(ObservableSource<T> var1, int var2, int var3, Callable<U> var4) {
      super(var1);
      this.count = var2;
      this.skip = var3;
      this.bufferSupplier = var4;
   }

   @Override
   protected void subscribeActual(Observer<? super U> var1) {
      if (this.skip == this.count) {
         ObservableBuffer.BufferExactObserver var2 = new ObservableBuffer.BufferExactObserver<>(var1, this.count, this.bufferSupplier);
         if (var2.createBuffer()) {
            this.source.subscribe(var2);
         }
      } else {
         this.source.subscribe(new ObservableBuffer.BufferSkipObserver<>(var1, this.count, this.skip, this.bufferSupplier));
      }
   }

   static final class BufferExactObserver<T, U extends Collection<? super T>> implements Observer<T>, Disposable {
      U buffer;
      final Callable<U> bufferSupplier;
      final int count;
      final Observer<? super U> downstream;
      int size;
      Disposable upstream;

      BufferExactObserver(Observer<? super U> var1, int var2, Callable<U> var3) {
         this.downstream = var1;
         this.count = var2;
         this.bufferSupplier = var3;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      boolean createBuffer() {
         Collection var1;
         try {
            var1 = ObjectHelper.requireNonNull(this.bufferSupplier.call(), "Empty buffer supplied");
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.buffer = null;
            Disposable var2 = this.upstream;
            if (var2 == null) {
               EmptyDisposable.error(var4, this.downstream);
            } else {
               var2.dispose();
               this.downstream.onError(var4);
            }

            return false;
         }

         this.buffer = (U)var1;
         return true;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         Collection var1 = this.buffer;
         if (var1 != null) {
            this.buffer = null;
            if (!var1.isEmpty()) {
               this.downstream.onNext((U)var1);
            }

            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.buffer = null;
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         Collection var3 = this.buffer;
         if (var3 != null) {
            var3.add(var1);
            int var2 = this.size + 1;
            this.size = var2;
            if (var2 >= this.count) {
               this.downstream.onNext((U)var3);
               this.size = 0;
               this.createBuffer();
            }
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }

   static final class BufferSkipObserver<T, U extends Collection<? super T>> extends AtomicBoolean implements Observer<T>, Disposable {
      private static final long serialVersionUID = -8223395059921494546L;
      final Callable<U> bufferSupplier;
      final ArrayDeque<U> buffers;
      final int count;
      final Observer<? super U> downstream;
      long index;
      final int skip;
      Disposable upstream;

      BufferSkipObserver(Observer<? super U> var1, int var2, int var3, Callable<U> var4) {
         this.downstream = var1;
         this.count = var2;
         this.skip = var3;
         this.bufferSupplier = var4;
         this.buffers = new ArrayDeque<>();
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         while (!this.buffers.isEmpty()) {
            this.downstream.onNext(this.buffers.poll());
         }

         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.buffers.clear();
         this.downstream.onError(var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         int var2 = this.index++;
         if (var2 % this.skip == 0L) {
            Collection var4;
            try {
               var4 = ObjectHelper.requireNonNull(
                  this.bufferSupplier.call(),
                  "The bufferSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources."
               );
            } catch (Throwable var7) {
               this.buffers.clear();
               this.upstream.dispose();
               this.downstream.onError(var7);
               return;
            }

            this.buffers.offer((U)var4);
         }

         Iterator var5 = this.buffers.iterator();

         while (var5.hasNext()) {
            Collection var8 = (Collection)var5.next();
            var8.add(var1);
            if (this.count <= var8.size()) {
               var5.remove();
               this.downstream.onNext((U)var8);
            }
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }
}
