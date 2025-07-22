package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;

public interface SchedulerMultiWorkerSupport {
   void createWorkers(int var1, SchedulerMultiWorkerSupport.WorkerCallback var2);

   public interface WorkerCallback {
      void onWorker(int var1, Scheduler.Worker var2);
   }
}
