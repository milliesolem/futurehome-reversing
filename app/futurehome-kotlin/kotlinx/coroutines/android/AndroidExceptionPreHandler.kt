package kotlinx.coroutines.android

import android.os.Build.VERSION
import java.lang.Thread.UncaughtExceptionHandler
import java.lang.reflect.Method
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineExceptionHandler

internal class AndroidExceptionPreHandler : AbstractCoroutineContextElement(CoroutineExceptionHandler.Key), CoroutineExceptionHandler {
   private final var _preHandler: Any? = this

   private fun preHandler(): Method? {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield kotlinx/coroutines/android/AndroidExceptionPreHandler._preHandler Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: aload 0
      // 07: if_acmpeq 0f
      // 0a: aload 2
      // 0b: checkcast java/lang/reflect/Method
      // 0e: areturn
      // 0f: aconst_null
      // 10: astore 3
      // 11: ldc java/lang/Thread
      // 13: ldc "getUncaughtExceptionPreHandler"
      // 15: aconst_null
      // 16: invokevirtual java/lang/Class.getDeclaredMethod (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
      // 19: astore 4
      // 1b: aload 3
      // 1c: astore 2
      // 1d: aload 4
      // 1f: invokevirtual java/lang/reflect/Method.getModifiers ()I
      // 22: invokestatic java/lang/reflect/Modifier.isPublic (I)Z
      // 25: ifeq 3a
      // 28: aload 4
      // 2a: invokevirtual java/lang/reflect/Method.getModifiers ()I
      // 2d: invokestatic java/lang/reflect/Modifier.isStatic (I)Z
      // 30: istore 1
      // 31: aload 3
      // 32: astore 2
      // 33: iload 1
      // 34: ifeq 3a
      // 37: aload 4
      // 39: astore 2
      // 3a: aload 0
      // 3b: aload 2
      // 3c: putfield kotlinx/coroutines/android/AndroidExceptionPreHandler._preHandler Ljava/lang/Object;
      // 3f: aload 2
      // 40: areturn
      // 41: astore 2
      // 42: aload 3
      // 43: astore 2
      // 44: goto 3a
   }

   public override fun handleException(context: CoroutineContext, exception: Throwable) {
      if (26 <= VERSION.SDK_INT && VERSION.SDK_INT < 28) {
         val var5: Method = this.preHandler();
         var var4: UncaughtExceptionHandler = null;
         val var6: Any;
         if (var5 != null) {
            var6 = var5.invoke(null, null);
         } else {
            var6 = null;
         }

         if (var6 is UncaughtExceptionHandler) {
            var4 = var6 as UncaughtExceptionHandler;
         }

         if (var4 != null) {
            var4.uncaughtException(Thread.currentThread(), var2);
         }
      }
   }
}
