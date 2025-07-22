package io.sentry.android.core.util;

import android.content.Context;

public final class AndroidLazyEvaluator<T> {
   private final AndroidLazyEvaluator.AndroidEvaluator<T> evaluator;
   private volatile T value = (T)null;

   public AndroidLazyEvaluator(AndroidLazyEvaluator.AndroidEvaluator<T> var1) {
      this.evaluator = var1;
   }

   public T getValue(Context param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/android/core/util/AndroidLazyEvaluator.value Ljava/lang/Object;
      // 04: ifnonnull 28
      // 07: aload 0
      // 08: monitorenter
      // 09: aload 0
      // 0a: getfield io/sentry/android/core/util/AndroidLazyEvaluator.value Ljava/lang/Object;
      // 0d: ifnonnull 1e
      // 10: aload 0
      // 11: aload 0
      // 12: getfield io/sentry/android/core/util/AndroidLazyEvaluator.evaluator Lio/sentry/android/core/util/AndroidLazyEvaluator$AndroidEvaluator;
      // 15: aload 1
      // 16: invokeinterface io/sentry/android/core/util/AndroidLazyEvaluator$AndroidEvaluator.evaluate (Landroid/content/Context;)Ljava/lang/Object; 2
      // 1b: putfield io/sentry/android/core/util/AndroidLazyEvaluator.value Ljava/lang/Object;
      // 1e: aload 0
      // 1f: monitorexit
      // 20: goto 28
      // 23: astore 1
      // 24: aload 0
      // 25: monitorexit
      // 26: aload 1
      // 27: athrow
      // 28: aload 0
      // 29: getfield io/sentry/android/core/util/AndroidLazyEvaluator.value Ljava/lang/Object;
      // 2c: areturn
   }

   public void resetValue() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 0: aload 0
      // 1: monitorenter
      // 2: aload 0
      // 3: aconst_null
      // 4: putfield io/sentry/android/core/util/AndroidLazyEvaluator.value Ljava/lang/Object;
      // 7: aload 0
      // 8: monitorexit
      // 9: return
      // a: astore 1
      // b: aload 0
      // c: monitorexit
      // d: aload 1
      // e: athrow
   }

   public void setValue(T param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 0: aload 0
      // 1: monitorenter
      // 2: aload 0
      // 3: aload 1
      // 4: putfield io/sentry/android/core/util/AndroidLazyEvaluator.value Ljava/lang/Object;
      // 7: aload 0
      // 8: monitorexit
      // 9: return
      // a: astore 1
      // b: aload 0
      // c: monitorexit
      // d: aload 1
      // e: athrow
   }

   public interface AndroidEvaluator<T> {
      T evaluate(Context var1);
   }
}
