package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.internal.ScopeCoroutine
import kotlinx.coroutines.internal.ThreadContextKt

internal class UndispatchedCoroutine<T>(context: CoroutineContext, uCont: Continuation<Any>) : ScopeCoroutine<T> {
   private final var threadLocalIsSet: Boolean
   private final val threadStateToRecover: ThreadLocal<Pair<CoroutineContext, Any?>>

   init {
      val var3: CoroutineContext;
      if (var1.get(UndispatchedMarker.INSTANCE) == null) {
         var3 = var1.plus(UndispatchedMarker.INSTANCE);
      } else {
         var3 = var1;
      }

      super(var3, var2);
      this.threadStateToRecover = new ThreadLocal<>();
      if (var2.getContext().get(ContinuationInterceptor.Key) !is CoroutineDispatcher) {
         val var4: Any = ThreadContextKt.updateThreadContext(var1, null);
         ThreadContextKt.restoreThreadContext(var1, var4);
         this.saveThreadContext(var1, var4);
      }
   }

   protected override fun afterResume(state: Any?) {
      label57: {
         if (this.threadLocalIsSet) {
            val var2: Pair = this.threadStateToRecover.get();
            if (var2 != null) {
               ThreadContextKt.restoreThreadContext(var2.component1() as CoroutineContext, var2.component2());
            }

            this.threadStateToRecover.remove();
         }

         val var4: Any = CompletionStateKt.recoverResult(var1, this.uCont);
         val var5: Continuation = this.uCont;
         val var3: CoroutineContext = this.uCont.getContext();
         var1 = null;
         val var9: Any = ThreadContextKt.updateThreadContext(var3, null);
         if (var9 != ThreadContextKt.NO_THREAD_ELEMENTS) {
            var1 = CoroutineContextKt.updateUndispatchedCompletion(var5, var3, var9);
         }

         try {
            this.uCont.resumeWith(var4);
         } catch (var6: java.lang.Throwable) {
            if (var1 == null || var1.clearThreadContext()) {
               ThreadContextKt.restoreThreadContext(var3, var9);
            }
         }

         if (var1 == null || var1.clearThreadContext()) {
            ThreadContextKt.restoreThreadContext(var3, var9);
         }
      }
   }

   public fun clearThreadContext(): Boolean {
      val var1: Boolean;
      if (this.threadLocalIsSet && this.threadStateToRecover.get() == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      this.threadStateToRecover.remove();
      return var1 xor true;
   }

   public fun saveThreadContext(context: CoroutineContext, oldValue: Any?) {
      this.threadLocalIsSet = true;
      this.threadStateToRecover.set(TuplesKt.to(var1, var2));
   }
}
