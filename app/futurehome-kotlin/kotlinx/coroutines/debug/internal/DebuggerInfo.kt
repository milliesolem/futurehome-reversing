package kotlinx.coroutines.debug.internal

import java.io.Serializable
import java.lang.Thread.State
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineId
import kotlinx.coroutines.CoroutineName

internal class DebuggerInfo(source: DebugCoroutineInfoImpl, context: CoroutineContext) : Serializable {
   public final val coroutineId: Long?
   public final val dispatcher: String?
   public final val lastObservedStackTrace: List<StackTraceElement>
   public final val lastObservedThreadName: String?
   public final val lastObservedThreadState: String?
   public final val name: String?
   public final val sequenceNumber: Long
   public final val state: String

   init {
      val var3: CoroutineId = var2.get(CoroutineId.Key);
      val var11: java.lang.Long;
      if (var3 != null) {
         var11 = var3.getId();
      } else {
         var11 = null;
      }

      this.coroutineId = var11;
      val var12: ContinuationInterceptor = var2.get(ContinuationInterceptor.Key);
      val var13: java.lang.String;
      if (var12 != null) {
         var13 = var12.toString();
      } else {
         var13 = null;
      }

      this.dispatcher = var13;
      val var5: CoroutineName = var2.get(CoroutineName.Key);
      val var6: java.lang.String;
      if (var5 != null) {
         var6 = var5.getName();
      } else {
         var6 = null;
      }

      label30: {
         this.name = var6;
         this.state = var1.getState$kotlinx_coroutines_core();
         if (var1.lastObservedThread != null) {
            val var8: State = var1.lastObservedThread.getState();
            if (var8 != null) {
               var9 = var8.toString();
               break label30;
            }
         }

         var9 = null;
      }

      this.lastObservedThreadState = var9;
      var var10: java.lang.String = null;
      if (var1.lastObservedThread != null) {
         var10 = var1.lastObservedThread.getName();
      }

      this.lastObservedThreadName = var10;
      this.lastObservedStackTrace = var1.lastObservedStackTrace$kotlinx_coroutines_core();
      this.sequenceNumber = var1.sequenceNumber;
   }
}
