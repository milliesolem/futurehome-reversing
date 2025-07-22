package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import java.util.concurrent.ThreadFactory;

public final class NewThreadScheduler extends Scheduler {
   private static final String KEY_NEWTHREAD_PRIORITY = "rx2.newthread-priority";
   private static final RxThreadFactory THREAD_FACTORY = new RxThreadFactory(
      "RxNewThreadScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx2.newthread-priority", 5)))
   );
   private static final String THREAD_NAME_PREFIX = "RxNewThreadScheduler";
   final ThreadFactory threadFactory;

   public NewThreadScheduler() {
      this(THREAD_FACTORY);
   }

   public NewThreadScheduler(ThreadFactory var1) {
      this.threadFactory = var1;
   }

   @Override
   public Scheduler.Worker createWorker() {
      return new NewThreadWorker(this.threadFactory);
   }
}
