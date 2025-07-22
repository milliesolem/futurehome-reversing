package kotlinx.coroutines

import java.util.concurrent.locks.LockSupport

internal final var timeSource: AbstractTimeSource?

internal inline fun currentTimeMillis(): Long {
   val var2: AbstractTimeSource = getTimeSource();
   val var0: Long;
   if (var2 != null) {
      var0 = var2.currentTimeMillis();
   } else {
      var0 = System.currentTimeMillis();
   }

   return var0;
}

internal inline fun nanoTime(): Long {
   val var2: AbstractTimeSource = getTimeSource();
   val var0: Long;
   if (var2 != null) {
      var0 = var2.nanoTime();
   } else {
      var0 = System.nanoTime();
   }

   return var0;
}

internal inline fun parkNanos(blocker: Any, nanos: Long) {
   val var3: AbstractTimeSource = getTimeSource();
   val var4: Unit;
   if (var3 != null) {
      var3.parkNanos(var0, var1);
      var4 = Unit.INSTANCE;
   } else {
      var4 = null;
   }

   if (var4 == null) {
      LockSupport.parkNanos(var0, var1);
   }
}

internal inline fun registerTimeLoopThread() {
   val var0: AbstractTimeSource = getTimeSource();
   if (var0 != null) {
      var0.registerTimeLoopThread();
   }
}

internal inline fun trackTask() {
   val var0: AbstractTimeSource = getTimeSource();
   if (var0 != null) {
      var0.trackTask();
   }
}

internal inline fun unTrackTask() {
   val var0: AbstractTimeSource = getTimeSource();
   if (var0 != null) {
      var0.unTrackTask();
   }
}

internal inline fun unpark(thread: Thread) {
   val var1: AbstractTimeSource = getTimeSource();
   val var2: Unit;
   if (var1 != null) {
      var1.unpark(var0);
      var2 = Unit.INSTANCE;
   } else {
      var2 = null;
   }

   if (var2 == null) {
      LockSupport.unpark(var0);
   }
}

internal inline fun unregisterTimeLoopThread() {
   val var0: AbstractTimeSource = getTimeSource();
   if (var0 != null) {
      var0.unregisterTimeLoopThread();
   }
}

internal inline fun wrapTask(block: Runnable): Runnable {
   val var2: AbstractTimeSource = getTimeSource();
   var var1: Runnable = var0;
   if (var2 != null) {
      var1 = var2.wrapTask(var0);
      if (var1 == null) {
         var1 = var0;
      }
   }

   return var1;
}
