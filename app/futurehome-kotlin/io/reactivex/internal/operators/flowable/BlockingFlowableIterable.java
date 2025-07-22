package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.reactivestreams.Subscription;

public final class BlockingFlowableIterable<T> implements Iterable<T> {
   final int bufferSize;
   final Flowable<T> source;

   public BlockingFlowableIterable(Flowable<T> var1, int var2) {
      this.source = var1;
      this.bufferSize = var2;
   }

   @Override
   public Iterator<T> iterator() {
      BlockingFlowableIterable.BlockingFlowableIterator var1 = new BlockingFlowableIterable.BlockingFlowableIterator(this.bufferSize);
      this.source.subscribe(var1);
      return var1;
   }

   static final class BlockingFlowableIterator<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Iterator<T>, Runnable, Disposable {
      private static final long serialVersionUID = 6695226475494099826L;
      final long batchSize;
      final Condition condition;
      volatile boolean done;
      volatile Throwable error;
      final long limit;
      final Lock lock;
      long produced;
      final SpscArrayQueue<T> queue;

      BlockingFlowableIterator(int var1) {
         this.queue = new SpscArrayQueue<>(var1);
         this.batchSize = var1;
         this.limit = var1 - (var1 >> 2);
         ReentrantLock var2 = new ReentrantLock();
         this.lock = var2;
         this.condition = var2.newCondition();
      }

      @Override
      public void dispose() {
         SubscriptionHelper.cancel(this);
         this.signalConsumer();
      }

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

            BlockingHelper.verifyNonBlocking();
            this.lock.lock();

            while (true) {
               boolean var6 = false /* VF: Semaphore variable */;

               try {
                  var6 = true;
                  if (!this.done) {
                     if (this.queue.isEmpty()) {
                        if (!this.isDisposed()) {
                           this.condition.await();
                           var6 = false;
                           continue;
                        }

                        var6 = false;
                     } else {
                        var6 = false;
                     }
                  } else {
                     var6 = false;
                  }
               } catch (InterruptedException var7) {
                  this.run();
                  throw ExceptionHelper.wrapOrThrow(var7);
               } finally {
                  if (var6) {
                     this.lock.unlock();
                  }
               }

               this.lock.unlock();
               break;
            }
         }

         Throwable var9 = this.error;
         if (var9 == null) {
            return false;
         } else {
            throw ExceptionHelper.wrapOrThrow(var9);
         }
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == SubscriptionHelper.CANCELLED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public T next() {
         if (this.hasNext()) {
            Object var3 = this.queue.poll();
            long var1 = this.produced + 1L;
            if (var1 == this.limit) {
               this.produced = 0L;
               this.get().request(var1);
            } else {
               this.produced = var1;
            }

            return (T)var3;
         } else {
            throw new NoSuchElementException();
         }
      }

      public void onComplete() {
         this.done = true;
         this.signalConsumer();
      }

      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         this.signalConsumer();
      }

      public void onNext(T var1) {
         if (!this.queue.offer((T)var1)) {
            SubscriptionHelper.cancel(this);
            this.onError(new MissingBackpressureException("Queue full?!"));
         } else {
            this.signalConsumer();
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.setOnce(this, var1, this.batchSize);
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException("remove");
      }

      @Override
      public void run() {
         SubscriptionHelper.cancel(this);
         this.signalConsumer();
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
