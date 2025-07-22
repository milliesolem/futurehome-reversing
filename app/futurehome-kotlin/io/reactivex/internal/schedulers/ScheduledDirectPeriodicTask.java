package io.reactivex.internal.schedulers;

import io.reactivex.plugins.RxJavaPlugins;

public final class ScheduledDirectPeriodicTask extends AbstractDirectTask implements Runnable {
   private static final long serialVersionUID = 1811839108042568751L;

   public ScheduledDirectPeriodicTask(Runnable var1) {
      super(var1);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void run() {
      this.runner = Thread.currentThread();

      try {
         this.runnable.run();
         this.runner = null;
      } catch (Throwable var3) {
         this.runner = null;
         this.lazySet(FINISHED);
         RxJavaPlugins.onError(var3);
         return;
      }
   }
}
