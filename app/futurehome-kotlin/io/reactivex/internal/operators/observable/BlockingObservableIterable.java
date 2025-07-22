package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class BlockingObservableIterable<T> implements Iterable<T> {
   final int bufferSize;
   final ObservableSource<? extends T> source;

   public BlockingObservableIterable(ObservableSource<? extends T> var1, int var2) {
      this.source = var1;
      this.bufferSize = var2;
   }

   @Override
   public Iterator<T> iterator() {
      BlockingObservableIterable.BlockingObservableIterator var1 = new BlockingObservableIterable.BlockingObservableIterator(this.bufferSize);
      this.source.subscribe(var1);
      return var1;
   }

   static final class BlockingObservableIterator<T> extends AtomicReference<Disposable> implements Observer<T>, Iterator<T>, Disposable {
      private static final long serialVersionUID = 6695226475494099826L;
      final Condition condition;
      volatile boolean done;
      volatile Throwable error;
      final Lock lock;
      final SpscLinkedArrayQueue<T> queue;

      BlockingObservableIterator(int var1) {
         this.queue = new SpscLinkedArrayQueue<>(var1);
         ReentrantLock var2 = new ReentrantLock();
         this.lock = var2;
         this.condition = var2.newCondition();
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
         this.signalConsumer();
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean hasNext() {
         while (!this.isDisposed()) {
            boolean var2 = this.done;
            boolean var1 = this.queue.isEmpty();
            if (var2) {
               Throwable var3 = this.error;
               if (var3 != null) {
                  throw ExceptionHelper.wrapOrThrow(var3);
               }

               if (var1) {
                  return false;
               }
            }

            if (!var1) {
               return true;
            }

            try {
               BlockingHelper.verifyNonBlocking();
               this.lock.lock();
            } catch (InterruptedException var11) {
               DisposableHelper.dispose(this);
               this.signalConsumer();
               throw ExceptionHelper.wrapOrThrow(var11);
            }

            while (true) {
               boolean var8 = false /* VF: Semaphore variable */;

               try {
                  var8 = true;
                  if (!this.done) {
                     if (this.queue.isEmpty()) {
                        if (!this.isDisposed()) {
                           this.condition.await();
                           var8 = false;
                           continue;
                        }

                        var8 = false;
                        break;
                     }

                     var8 = false;
                     break;
                  }

                  var8 = false;
                  break;
               } finally {
                  if (var8) {
                     try {
                        this.lock.unlock();
                     } catch (InterruptedException var9) {
                        DisposableHelper.dispose(this);
                        this.signalConsumer();
                        throw ExceptionHelper.wrapOrThrow(var9);
                     }
                  }
               }
            }

            try {
               this.lock.unlock();
            } catch (InterruptedException var10) {
               DisposableHelper.dispose(this);
               this.signalConsumer();
               throw ExceptionHelper.wrapOrThrow(var10);
            }
         }

         Throwable var13 = this.error;
         if (var13 == null) {
            return false;
         } else {
            throw ExceptionHelper.wrapOrThrow(var13);
         }
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public T next() {
         if (this.hasNext()) {
            return this.queue.poll();
         } else {
            throw new NoSuchElementException();
         }
      }

      @Override
      public void onComplete() {
         this.done = true;
         this.signalConsumer();
      }

      @Override
      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         this.signalConsumer();
      }

      @Override
      public void onNext(T var1) {
         this.queue.offer((T)var1);
         this.signalConsumer();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException("remove");
      }

      void signalConsumer() {
         this.lock.lock();

         try {
            this.condition.signalAll();
         } finally {
            this.lock.unlock();
         }
      }
   }
}
