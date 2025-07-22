package kotlinx.coroutines

import java.util.concurrent.locks.LockSupport
import kotlinx.coroutines.EventLoopImplBase.DelayedTask

internal abstract class EventLoopImplPlatform : EventLoop {
   protected abstract val thread: Thread

   protected open fun reschedule(now: Long, delayedTask: DelayedTask) {
      DefaultExecutor.INSTANCE.schedule(var1, var3);
   }

   protected fun unpark() {
      val var2: Thread = this.getThread();
      if (Thread.currentThread() != var2) {
         val var1: AbstractTimeSource = AbstractTimeSourceKt.getTimeSource();
         val var3: Unit;
         if (var1 != null) {
            var1.unpark(var2);
            var3 = Unit.INSTANCE;
         } else {
            var3 = null;
         }

         if (var3 == null) {
            LockSupport.unpark(var2);
         }
      }
   }
}
