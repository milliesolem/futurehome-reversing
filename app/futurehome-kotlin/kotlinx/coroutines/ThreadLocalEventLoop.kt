package kotlinx.coroutines

import kotlinx.coroutines.internal.Symbol
import kotlinx.coroutines.internal.ThreadLocalKt

internal object ThreadLocalEventLoop {
   internal final val eventLoop: EventLoop
      internal final get() {
         val var3: ThreadLocal = ref;
         val var2: EventLoop = ref.get();
         var var1: EventLoop = var2;
         if (var2 == null) {
            var1 = EventLoopKt.createEventLoop();
            var3.set(var1);
         }

         return var1;
      }


   private final val ref: ThreadLocal<EventLoop?> = ThreadLocalKt.commonThreadLocal(new Symbol("ThreadLocalEventLoop"))

   internal fun currentOrNull(): EventLoop? {
      return ref.get();
   }

   internal fun resetEventLoop() {
      ref.set(null);
   }

   internal fun setEventLoop(eventLoop: EventLoop) {
      ref.set(var1);
   }
}
