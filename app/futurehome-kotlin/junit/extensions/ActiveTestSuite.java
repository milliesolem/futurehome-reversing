package junit.extensions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class ActiveTestSuite extends TestSuite {
   private volatile int fActiveTestDeathCount;

   public ActiveTestSuite() {
   }

   public ActiveTestSuite(Class<? extends TestCase> var1) {
      super(var1);
   }

   public ActiveTestSuite(Class<? extends TestCase> var1, String var2) {
      super(var1, var2);
   }

   public ActiveTestSuite(String var1) {
      super(var1);
   }

   @Override
   public void run(TestResult var1) {
      this.fActiveTestDeathCount = 0;
      super.run(var1);
      this.waitUntilFinished();
   }

   public void runFinished() {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: aload 0
      // 04: getfield junit/extensions/ActiveTestSuite.fActiveTestDeathCount I
      // 07: bipush 1
      // 08: iadd
      // 09: putfield junit/extensions/ActiveTestSuite.fActiveTestDeathCount I
      // 0c: aload 0
      // 0d: invokevirtual java/lang/Object.notifyAll ()V
      // 10: aload 0
      // 11: monitorexit
      // 12: return
      // 13: astore 1
      // 14: aload 0
      // 15: monitorexit
      // 16: aload 1
      // 17: athrow
   }

   @Override
   public void runTest(Test var1, TestResult var2) {
      (new Thread(this, var1, var2) {
         final ActiveTestSuite this$0;
         final TestResult val$result;
         final Test val$test;

         {
            this.this$0 = var1;
            this.val$test = var2x;
            this.val$result = var3;
         }

         @Override
         public void run() {
            try {
               this.val$test.run(this.val$result);
            } finally {
               this.this$0.runFinished();
            }
         }
      }).start();
   }

   void waitUntilFinished() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield junit/extensions/ActiveTestSuite.fActiveTestDeathCount I
      // 06: istore 2
      // 07: aload 0
      // 08: invokevirtual junit/extensions/ActiveTestSuite.testCount ()I
      // 0b: istore 1
      // 0c: iload 2
      // 0d: iload 1
      // 0e: if_icmpge 1c
      // 11: aload 0
      // 12: invokevirtual java/lang/Object.wait ()V
      // 15: goto 02
      // 18: astore 3
      // 19: aload 0
      // 1a: monitorexit
      // 1b: return
      // 1c: aload 0
      // 1d: monitorexit
      // 1e: return
      // 1f: astore 3
      // 20: aload 0
      // 21: monitorexit
      // 22: aload 3
      // 23: athrow
   }
}
