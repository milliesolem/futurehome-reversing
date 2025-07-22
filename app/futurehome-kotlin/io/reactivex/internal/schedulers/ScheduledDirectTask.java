package io.reactivex.internal.schedulers;

import java.util.concurrent.Callable;

public final class ScheduledDirectTask extends AbstractDirectTask implements Callable<Void> {
   private static final long serialVersionUID = 1811839108042568751L;

   public ScheduledDirectTask(Runnable var1) {
      super(var1);
   }

   public Void call() throws Exception {
      this.runner = Thread.currentThread();

      try {
         this.runnable.run();
      } finally {
         this.lazySet(FINISHED);
         this.runner = null;
      }

      return null;
   }
}
