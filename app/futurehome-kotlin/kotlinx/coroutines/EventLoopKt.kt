package kotlinx.coroutines

import kotlinx.coroutines.scheduling.CoroutineScheduler

internal fun createEventLoop(): EventLoop {
   return new BlockingEventLoop(Thread.currentThread());
}

internal fun Thread.isIoDispatcherThread(): Boolean {
   return var0 is CoroutineScheduler.Worker && (var0 as CoroutineScheduler.Worker).isIo();
}

internal inline fun platformAutoreleasePool(crossinline block: () -> Unit) {
   var0.invoke();
}

public fun processNextEventInCurrentThread(): Long {
   val var2: EventLoop = ThreadLocalEventLoop.INSTANCE.currentOrNull$kotlinx_coroutines_core();
   val var0: Long;
   if (var2 != null) {
      var0 = var2.processNextEvent();
   } else {
      var0 = java.lang.Long.MAX_VALUE;
   }

   return var0;
}

internal fun runSingleTaskFromCurrentSystemDispatcher(): Long {
   val var0: Thread = Thread.currentThread();
   if (var0 is CoroutineScheduler.Worker) {
      return (var0 as CoroutineScheduler.Worker).runSingleTask();
   } else {
      val var1: StringBuilder = new StringBuilder("Expected CoroutineScheduler.Worker, but got ");
      var1.append(var0);
      throw new IllegalStateException(var1.toString());
   }
}
