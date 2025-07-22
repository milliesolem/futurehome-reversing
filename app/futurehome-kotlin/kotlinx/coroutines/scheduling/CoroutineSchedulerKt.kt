package kotlinx.coroutines.scheduling

internal fun isSchedulerWorker(thread: Thread): Boolean {
   return var0 is CoroutineScheduler.Worker;
}

internal fun mayNotBlock(thread: Thread): Boolean {
   val var1: Boolean;
   if (var0 is CoroutineScheduler.Worker && (var0 as CoroutineScheduler.Worker).state === CoroutineScheduler.WorkerState.CPU_ACQUIRED) {
      var1 = true;
   } else {
      var1 = false;
   }

   return var1;
}
