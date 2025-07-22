package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import java.util.concurrent.TimeUnit;

public final class ImmediateThinScheduler extends Scheduler {
   static final Disposable DISPOSED;
   public static final Scheduler INSTANCE = new ImmediateThinScheduler();
   static final Scheduler.Worker WORKER = new ImmediateThinScheduler.ImmediateThinWorker();

   static {
      Disposable var0 = Disposables.empty();
      DISPOSED = var0;
      var0.dispose();
   }

   private ImmediateThinScheduler() {
   }

   @Override
   public Scheduler.Worker createWorker() {
      return WORKER;
   }

   @Override
   public Disposable scheduleDirect(Runnable var1) {
      var1.run();
      return DISPOSED;
   }

   @Override
   public Disposable scheduleDirect(Runnable var1, long var2, TimeUnit var4) {
      throw new UnsupportedOperationException("This scheduler doesn't support delayed execution");
   }

   @Override
   public Disposable schedulePeriodicallyDirect(Runnable var1, long var2, long var4, TimeUnit var6) {
      throw new UnsupportedOperationException("This scheduler doesn't support periodic execution");
   }

   static final class ImmediateThinWorker extends Scheduler.Worker {
      @Override
      public void dispose() {
      }

      @Override
      public boolean isDisposed() {
         return false;
      }

      @Override
      public Disposable schedule(Runnable var1) {
         var1.run();
         return ImmediateThinScheduler.DISPOSED;
      }

      @Override
      public Disposable schedule(Runnable var1, long var2, TimeUnit var4) {
         throw new UnsupportedOperationException("This scheduler doesn't support delayed execution");
      }

      @Override
      public Disposable schedulePeriodically(Runnable var1, long var2, long var4, TimeUnit var6) {
         throw new UnsupportedOperationException("This scheduler doesn't support periodic execution");
      }
   }
}
