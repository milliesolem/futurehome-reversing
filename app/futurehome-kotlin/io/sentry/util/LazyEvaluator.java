package io.sentry.util;

public final class LazyEvaluator<T> {
   private final LazyEvaluator.Evaluator<T> evaluator;
   private volatile T value = (T)null;

   public LazyEvaluator(LazyEvaluator.Evaluator<T> var1) {
      this.evaluator = var1;
   }

   public T getValue() {
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
      // 01: getfield io/sentry/util/LazyEvaluator.value Ljava/lang/Object;
      // 04: ifnonnull 27
      // 07: aload 0
      // 08: monitorenter
      // 09: aload 0
      // 0a: getfield io/sentry/util/LazyEvaluator.value Ljava/lang/Object;
      // 0d: ifnonnull 1d
      // 10: aload 0
      // 11: aload 0
      // 12: getfield io/sentry/util/LazyEvaluator.evaluator Lio/sentry/util/LazyEvaluator$Evaluator;
      // 15: invokeinterface io/sentry/util/LazyEvaluator$Evaluator.evaluate ()Ljava/lang/Object; 1
      // 1a: putfield io/sentry/util/LazyEvaluator.value Ljava/lang/Object;
      // 1d: aload 0
      // 1e: monitorexit
      // 1f: goto 27
      // 22: astore 1
      // 23: aload 0
      // 24: monitorexit
      // 25: aload 1
      // 26: athrow
      // 27: aload 0
      // 28: getfield io/sentry/util/LazyEvaluator.value Ljava/lang/Object;
      // 2b: areturn
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
      // 4: putfield io/sentry/util/LazyEvaluator.value Ljava/lang/Object;
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
      // 4: putfield io/sentry/util/LazyEvaluator.value Ljava/lang/Object;
      // 7: aload 0
      // 8: monitorexit
      // 9: return
      // a: astore 1
      // b: aload 0
      // c: monitorexit
      // d: aload 1
      // e: athrow
   }

   public interface Evaluator<T> {
      T evaluate();
   }
}
