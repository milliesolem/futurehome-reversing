package io.reactivex.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class TestScheduler extends Scheduler {
   long counter;
   final Queue<TestScheduler.TimedRunnable> queue = new PriorityBlockingQueue<>(11);
   volatile long time;

   public TestScheduler() {
   }

   public TestScheduler(long var1, TimeUnit var3) {
      this.time = var3.toNanos(var1);
   }

   private void triggerActions(long var1) {
      while (true) {
         TestScheduler.TimedRunnable var5 = this.queue.peek();
         if (var5 == null || var5.time > var1) {
            this.time = var1;
            return;
         }

         long var3;
         if (var5.time == 0L) {
            var3 = this.time;
         } else {
            var3 = var5.time;
         }

         this.time = var3;
         this.queue.remove(var5);
         if (!var5.scheduler.disposed) {
            var5.run.run();
         }
      }
   }

   public void advanceTimeBy(long var1, TimeUnit var3) {
      this.advanceTimeTo(this.time + var3.toNanos(var1), TimeUnit.NANOSECONDS);
   }

   public void advanceTimeTo(long var1, TimeUnit var3) {
      this.triggerActions(var3.toNanos(var1));
   }

   @Override
   public Scheduler.Worker createWorker() {
      return new TestScheduler.TestWorker(this);
   }

   @Override
   public long now(TimeUnit var1) {
      return var1.convert(this.time, TimeUnit.NANOSECONDS);
   }

   public void triggerActions() {
      this.triggerActions(this.time);
   }

   final class TestWorker extends Scheduler.Worker {
      volatile boolean disposed;
      final TestScheduler this$0;

      TestWorker(TestScheduler var1) {
         this.this$0 = var1;
      }

      @Override
      public void dispose() {
         this.disposed = true;
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      @Override
      public long now(TimeUnit var1) {
         return this.this$0.now(var1);
      }

      @Override
      public Disposable schedule(Runnable var1) {
         if (this.disposed) {
            return EmptyDisposable.INSTANCE;
         } else {
            TestScheduler var4 = this.this$0;
            long var2 = (long)(var4.counter++);
            TestScheduler.TimedRunnable var5 = new TestScheduler.TimedRunnable(this, 0L, var1, var2);
            this.this$0.queue.add(var5);
            return Disposables.fromRunnable(new TestScheduler.TestWorker.QueueRemove(this, var5));
         }
      }

      @Override
      public Disposable schedule(Runnable var1, long var2, TimeUnit var4) {
         if (this.disposed) {
            return EmptyDisposable.INSTANCE;
         } else {
            long var5 = this.this$0.time;
            long var7 = var4.toNanos(var2);
            TestScheduler var11 = this.this$0;
            var2 = (long)(var11.counter++);
            TestScheduler.TimedRunnable var9 = new TestScheduler.TimedRunnable(this, var5 + var7, var1, var2);
            this.this$0.queue.add(var9);
            return Disposables.fromRunnable(new TestScheduler.TestWorker.QueueRemove(this, var9));
         }
      }

      final class QueueRemove implements Runnable {
         final TestScheduler.TestWorker this$1;
         final TestScheduler.TimedRunnable timedAction;

         QueueRemove(TestScheduler.TestWorker var1, TestScheduler.TimedRunnable var2) {
            this.this$1 = var1;
            this.timedAction = var2;
         }

         @Override
         public void run() {
            this.this$1.this$0.queue.remove(this.timedAction);
         }
      }
   }

   static final class TimedRunnable implements Comparable<TestScheduler.TimedRunnable> {
      final long count;
      final Runnable run;
      final TestScheduler.TestWorker scheduler;
      final long time;

      TimedRunnable(TestScheduler.TestWorker var1, long var2, Runnable var4, long var5) {
         this.time = var2;
         this.run = var4;
         this.scheduler = var1;
         this.count = var5;
      }

      public int compareTo(TestScheduler.TimedRunnable var1) {
         long var2 = this.time;
         long var4 = var1.time;
         return var2 == var4 ? ObjectHelper.compare(this.count, var1.count) : ObjectHelper.compare(var2, var4);
      }

      @Override
      public String toString() {
         return String.format("TimedRunnable(time = %d, run = %s)", this.time, this.run.toString());
      }
   }
}
