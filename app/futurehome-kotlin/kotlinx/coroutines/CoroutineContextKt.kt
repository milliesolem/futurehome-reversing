package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.jvm.internal.CoroutineStackFrame
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Ref
import kotlinx.coroutines.internal.ThreadContextKt

private const val DEBUG_THREAD_NAME_SEPARATOR: String = " @"

internal final val coroutineName: String?
   internal final get() {
      if (!DebugKt.getDEBUG()) {
         return null;
      } else {
         val var2: CoroutineId = var0.get(CoroutineId.Key);
         if (var2 == null) {
            return null;
         } else {
            label16: {
               val var3: CoroutineName = var0.get(CoroutineName.Key);
               if (var3 != null) {
                  val var1: java.lang.String = var3.getName();
                  var4 = var1;
                  if (var1 != null) {
                     break label16;
                  }
               }

               var4 = "coroutine";
            }

            val var5: StringBuilder = new StringBuilder();
            var5.append(var4);
            var5.append('#');
            var5.append(var2.getId());
            return var5.toString();
         }
      }
   }


private fun foldCopies(originalContext: CoroutineContext, appendContext: CoroutineContext, isNewCoroutine: Boolean): CoroutineContext {
   val var4: Boolean = hasCopyableElements(var0);
   val var3: Boolean = hasCopyableElements(var1);
   if (!var4 && !var3) {
      return var0.plus(var1);
   } else {
      val var5: Ref.ObjectRef = new Ref.ObjectRef();
      var5.element = (T)var1;
      var0 = var0.fold(EmptyCoroutineContext.INSTANCE, (new Function2<CoroutineContext, CoroutineContext.Element, CoroutineContext>(var5, var2) {
         final boolean $isNewCoroutine;
         final Ref.ObjectRef<CoroutineContext> $leftoverContext;

         {
            super(2);
            this.$leftoverContext = var1;
            this.$isNewCoroutine = var2;
         }

         public final CoroutineContext invoke(CoroutineContext var1, CoroutineContext.Element var2) {
            if (var2 !is CopyableThreadContextElement) {
               return var1.plus(var2);
            } else {
               val var5: CoroutineContext.Element = this.$leftoverContext.element.get((CoroutineContext.Key<CoroutineContext.Element>)var2.getKey());
               if (var5 == null) {
                  val var7: CopyableThreadContextElement = var2 as CopyableThreadContextElement;
                  var var6: CopyableThreadContextElement = var2 as CopyableThreadContextElement;
                  if (this.$isNewCoroutine) {
                     var6 = var7.copyForChild();
                  }

                  return var1.plus(var6);
               } else {
                  this.$leftoverContext.element = this.$leftoverContext.element.minusKey(var2.getKey());
                  return var1.plus((var2 as CopyableThreadContextElement).mergeForChild(var5));
               }
            }
         }
      }) as (CoroutineContext?, CoroutineContext.Element?) -> CoroutineContext);
      if (var3) {
         var5.element = (T)(var5.element as CoroutineContext).fold(EmptyCoroutineContext.INSTANCE, <unrepresentable>.INSTANCE);
      }

      return var0.plus(var5.element as CoroutineContext);
   }
}

private fun CoroutineContext.hasCopyableElements(): Boolean {
   return var0.fold(false, <unrepresentable>.INSTANCE);
}

public fun CoroutineContext.newCoroutineContext(addedContext: CoroutineContext): CoroutineContext {
   return if (!hasCopyableElements(var1)) var0.plus(var1) else foldCopies(var0, var1, false);
}

public fun CoroutineScope.newCoroutineContext(context: CoroutineContext): CoroutineContext {
   val var2: CoroutineContext = foldCopies(var0.getCoroutineContext(), var1, true);
   val var3: CoroutineContext;
   if (DebugKt.getDEBUG()) {
      var3 = var2.plus(new CoroutineId(DebugKt.getCOROUTINE_ID().incrementAndGet()));
   } else {
      var3 = var2;
   }

   var1 = var3;
   if (var2 != Dispatchers.getDefault()) {
      var1 = var3;
      if (var2.get(ContinuationInterceptor.Key) == null) {
         var1 = var3.plus(Dispatchers.getDefault());
      }
   }

   return var1;
}

internal tailrec fun CoroutineStackFrame.undispatchedCompletion(): UndispatchedCoroutine<*>? {
   while (!(var0 instanceof DispatchedCoroutine)) {
      val var1: CoroutineStackFrame = var0.getCallerFrame();
      if (var1 == null) {
         return null;
      }

      var0 = var1;
      if (var1 is UndispatchedCoroutine) {
         return var1 as UndispatchedCoroutine<?>;
      }
   }

   return null;
}

internal fun Continuation<*>.updateUndispatchedCompletion(context: CoroutineContext, oldValue: Any?): UndispatchedCoroutine<*>? {
   if (var0 !is CoroutineStackFrame) {
      return null;
   } else if (var1.get(UndispatchedMarker.INSTANCE) != null) {
      val var3: UndispatchedCoroutine = undispatchedCompletion(var0 as CoroutineStackFrame);
      if (var3 != null) {
         var3.saveThreadContext(var1, var2);
      }

      return var3;
   } else {
      return null;
   }
}

internal inline fun <T> withContinuationContext(continuation: Continuation<*>, countOrElement: Any?, block: () -> T): T {
   label46: {
      val var3: CoroutineContext = var0.getContext();
      var1 = ThreadContextKt.updateThreadContext(var3, var1);
      val var6: UndispatchedCoroutine;
      if (var1 != ThreadContextKt.NO_THREAD_ELEMENTS) {
         var6 = updateUndispatchedCompletion(var0, var3, var1);
      } else {
         var6 = null;
      }

      try {
         val var8: Any = var2.invoke();
      } catch (var4: java.lang.Throwable) {
         if (var6 == null || var6.clearThreadContext()) {
            ThreadContextKt.restoreThreadContext(var3, var1);
         }
      }

      if (var6 == null || var6.clearThreadContext()) {
         ThreadContextKt.restoreThreadContext(var3, var1);
      }
   }
}

internal inline fun <T> withCoroutineContext(context: CoroutineContext, countOrElement: Any?, block: () -> T): T {
   label13: {
      var1 = ThreadContextKt.updateThreadContext(var0, var1);

      try {
         val var6: Any = var2.invoke();
      } catch (var3: java.lang.Throwable) {
         ThreadContextKt.restoreThreadContext(var0, var1);
      }

      ThreadContextKt.restoreThreadContext(var0, var1);
   }
}
