package kotlinx.coroutines.internal

import _COROUTINE.ArtificialStackFrames
import _COROUTINE.CoroutineDebuggingKt
import java.util.ArrayDeque
import kotlin.coroutines.Continuation
import kotlin.coroutines.jvm.internal.CoroutineStackFrame
import kotlinx.coroutines.DebugKt

private final val ARTIFICIAL_FRAME: StackTraceElement = new ArtificialStackFrames().coroutineBoundary()
private const val baseContinuationImplClass: String = "kotlin.coroutines.jvm.internal.BaseContinuationImpl"
private final val baseContinuationImplClassName: String
private const val stackTraceRecoveryClass: String = "kotlinx.coroutines.internal.StackTraceRecoveryKt"
private final val stackTraceRecoveryClassName: String

@JvmSynthetic
fun `CoroutineStackFrame$annotations`() {
}

@JvmSynthetic
fun `StackTraceElement$annotations`() {
}

@JvmSynthetic
fun `access$recoverFromStackFrame`(var0: java.lang.Throwable, var1: CoroutineStackFrame): java.lang.Throwable {
   return recoverFromStackFrame(var0, var1);
}

private fun <E : Throwable> E.causeAndStacktrace(): Pair<E, Array<StackTraceElement>> {
   val var4: java.lang.Throwable = var0.getCause();
   var var5: Pair;
   if (var4 != null && var4.getClass() == var0.getClass()) {
      val var3: Array<StackTraceElement> = var0.getStackTrace();
      val var2: Int = var3.length;
      var var1: Int = 0;

      while (true) {
         if (var1 >= var2) {
            var5 = TuplesKt.to(var0, new StackTraceElement[0]);
            break;
         }

         if (isArtificial(var3[var1])) {
            var5 = TuplesKt.to(var4, var3);
            break;
         }

         var1++;
      }
   } else {
      var5 = TuplesKt.to(var0, new StackTraceElement[0]);
   }

   return var5;
}

private fun <E : Throwable> createFinalException(cause: E, result: E, resultStackTrace: ArrayDeque<StackTraceElement>): E {
   var2.addFirst(ARTIFICIAL_FRAME);
   val var6: Array<StackTraceElement> = var0.getStackTrace();
   val var5: Int = firstFrameIndex(var6, baseContinuationImplClassName);
   if (var5 == -1) {
      var1.setStackTrace(var2.toArray(new StackTraceElement[0]));
      return (E)var1;
   } else {
      val var7: Array<StackTraceElement> = new StackTraceElement[var2.size() + var5];

      for (int var3 = 0; var3 < var5; var3++) {
         var7[var3] = var6[var3];
      }

      val var8: java.util.Iterator = var2.iterator();

      for (int var9 = 0; var8.hasNext(); var9++) {
         var7[var9 + var5] = var8.next() as StackTraceElement;
      }

      var1.setStackTrace(var7);
      return (E)var1;
   }
}

private fun createStackTrace(continuation: CoroutineStackFrame): ArrayDeque<StackTraceElement> {
   val var2: ArrayDeque = new ArrayDeque();
   var var3: StackTraceElement = var0.getStackTraceElement();
   var var1: CoroutineStackFrame = var0;
   if (var3 != null) {
      var2.add(var3);
      var1 = var0;
   }

   while (true) {
      if (var1 !is CoroutineStackFrame) {
         var1 = null;
      }

      if (var1 == null) {
         break;
      }

      var0 = var1.getCallerFrame();
      if (var0 == null) {
         break;
      }

      var3 = var0.getStackTraceElement();
      var1 = var0;
      if (var3 != null) {
         var2.add(var3);
         var1 = var0;
      }
   }

   return var2;
}

private fun StackTraceElement.elementWiseEquals(e: StackTraceElement): Boolean {
   val var2: Boolean;
   if (var0.getLineNumber() == var1.getLineNumber()
      && var0.getMethodName() == var1.getMethodName()
      && var0.getFileName() == var1.getFileName()
      && var0.getClassName() == var1.getClassName()) {
      var2 = true;
   } else {
      var2 = false;
   }

   return var2;
}

private fun Array<StackTraceElement>.firstFrameIndex(methodName: String): Int {
   val var3: Int = var0.length;
   var var2: Int = 0;

   while (true) {
      if (var2 >= var3) {
         var2 = -1;
         break;
      }

      if (var1 == var0[var2].getClassName()) {
         break;
      }

      var2++;
   }

   return var2;
}

internal fun Throwable.initCause(cause: Throwable) {
   var0.initCause(var1);
}

internal fun StackTraceElement.isArtificial(): Boolean {
   return StringsKt.startsWith$default(var0.getClassName(), CoroutineDebuggingKt.getARTIFICIAL_FRAME_PACKAGE_NAME(), false, 2, null);
}

private fun mergeRecoveredTraces(recoveredStacktrace: Array<StackTraceElement>, result: ArrayDeque<StackTraceElement>) {
   var var3: Int = var0.length;
   var var2: Int = 0;

   while (true) {
      if (var2 >= var3) {
         var2 = -1;
         break;
      }

      if (isArtificial(var0[var2])) {
         break;
      }

      var2++;
   }

   var3 = var2 + 1;
   var2 = var0.length - 1;
   if (var3 <= var0.length - 1) {
      while (true) {
         if (elementWiseEquals(var0[var2], var1.getLast() as StackTraceElement)) {
            var1.removeLast();
         }

         var1.addFirst(var0[var2]);
         if (var2 == var3) {
            break;
         }

         var2--;
      }
   }
}

internal suspend inline fun recoverAndThrow(exception: Throwable): Nothing {
   if (DebugKt.getRECOVER_STACK_TRACES()) {
      if (var1 !is CoroutineStackFrame) {
         throw var0;
      } else {
         throw access$recoverFromStackFrame(var0, var1 as CoroutineStackFrame);
      }
   } else {
      throw var0;
   }
}

fun `recoverAndThrow$$forInline`(var0: java.lang.Throwable, var1: Continuation<?>): Any {
   if (DebugKt.getRECOVER_STACK_TRACES()) {
      val var2: Continuation = var1;
      if (var1 !is CoroutineStackFrame) {
         throw var0;
      } else {
         throw access$recoverFromStackFrame(var0, var1 as CoroutineStackFrame);
      }
   } else {
      throw var0;
   }
}

private fun <E : Throwable> recoverFromStackFrame(exception: E, continuation: CoroutineStackFrame): E {
   val var3: Pair = causeAndStacktrace(var0);
   val var2: java.lang.Throwable = var3.component1() as java.lang.Throwable;
   val var6: Array<StackTraceElement> = var3.component2() as Array<StackTraceElement>;
   val var4: java.lang.Throwable = ExceptionsConstructorKt.tryCopyException(var2);
   if (var4 == null) {
      return (E)var0;
   } else {
      val var5: ArrayDeque = createStackTrace(var1);
      if (var5.isEmpty()) {
         return (E)var0;
      } else {
         if (var2 != var0) {
            mergeRecoveredTraces(var6, var5);
         }

         return (E)createFinalException(var2, var4, var5);
      }
   }
}

internal fun <E : Throwable> recoverStackTrace(exception: E): E {
   if (!DebugKt.getRECOVER_STACK_TRACES()) {
      return (E)var0;
   } else {
      val var1: java.lang.Throwable = ExceptionsConstructorKt.tryCopyException(var0);
      return (E)(if (var1 == null) var0 else sanitizeStackTrace(var1));
   }
}

internal inline fun <E : Throwable> recoverStackTrace(exception: E, continuation: Continuation<*>): E {
   var var2: java.lang.Throwable = var0;
   if (DebugKt.getRECOVER_STACK_TRACES()) {
      if (var1 !is CoroutineStackFrame) {
         var2 = var0;
      } else {
         var2 = access$recoverFromStackFrame(var0, var1 as CoroutineStackFrame);
      }
   }

   return (E)var2;
}

private fun <E : Throwable> E.sanitizeStackTrace(): E {
   var var1: Int;
   var var4: Int;
   var var6: Array<StackTraceElement>;
   label43: {
      var6 = var0.getStackTrace();
      var4 = var6.length;
      var1 = var6.length - 1;
      if (var6.length - 1 >= 0) {
         while (true) {
            val var2: Int = var1 - 1;
            if (stackTraceRecoveryClassName == var6[var1].getClassName()) {
               break label43;
            }

            if (var2 < 0) {
               break;
            }

            var1 = var2;
         }
      }

      var1 = -1;
   }

   var var8: Int = firstFrameIndex(var6, baseContinuationImplClassName);
   if (var8 == -1) {
      var8 = 0;
   } else {
      var8 = var4 - var8;
   }

   val var11: Int = var4 - var1 - var8;
   val var7: Array<StackTraceElement> = new StackTraceElement[var4 - var1 - var8];

   for (int var10 = 0; var10 < var11; var10++) {
      val var12: StackTraceElement;
      if (var10 == 0) {
         var12 = ARTIFICIAL_FRAME;
      } else {
         var12 = var6[var1 + 1 + var10 - 1];
      }

      var7[var10] = var12;
   }

   var0.setStackTrace(var7);
   return (E)var0;
}

internal inline fun <E : Throwable> unwrap(exception: E): E {
   if (DebugKt.getRECOVER_STACK_TRACES()) {
      var0 = unwrapImpl(var0);
   }

   return (E)var0;
}

internal fun <E : Throwable> unwrapImpl(exception: E): E {
   val var4: java.lang.Throwable = var0.getCause();
   if (var4 != null && var4.getClass() == var0.getClass()) {
      val var3: Array<StackTraceElement> = var0.getStackTrace();
      val var2: Int = var3.length;

      for (int var1 = 0; var1 < var2; var1++) {
         if (isArtificial(var3[var1])) {
            return (E)var4;
         }
      }
   }

   return (E)var0;
}
