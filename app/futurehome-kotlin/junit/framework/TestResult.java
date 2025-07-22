package junit.framework;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class TestResult {
   protected List<TestFailure> fErrors;
   protected List<TestFailure> fFailures = new ArrayList<>();
   protected List<TestListener> fListeners;
   protected int fRunTests;
   private boolean fStop;

   public TestResult() {
      this.fErrors = new ArrayList<>();
      this.fListeners = new ArrayList<>();
      this.fRunTests = 0;
      this.fStop = false;
   }

   private List<TestListener> cloneListeners() {
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
      // 02: new java/util/ArrayList
      // 05: astore 1
      // 06: aload 1
      // 07: invokespecial java/util/ArrayList.<init> ()V
      // 0a: aload 1
      // 0b: aload 0
      // 0c: getfield junit/framework/TestResult.fListeners Ljava/util/List;
      // 0f: invokeinterface java/util/List.addAll (Ljava/util/Collection;)Z 2
      // 14: pop
      // 15: aload 0
      // 16: monitorexit
      // 17: aload 1
      // 18: areturn
      // 19: astore 1
      // 1a: aload 0
      // 1b: monitorexit
      // 1c: aload 1
      // 1d: athrow
   }

   public void addError(Test param1, Throwable param2) {
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
      // 03: getfield junit/framework/TestResult.fErrors Ljava/util/List;
      // 06: astore 4
      // 08: new junit/framework/TestFailure
      // 0b: astore 3
      // 0c: aload 3
      // 0d: aload 1
      // 0e: aload 2
      // 0f: invokespecial junit/framework/TestFailure.<init> (Ljunit/framework/Test;Ljava/lang/Throwable;)V
      // 12: aload 4
      // 14: aload 3
      // 15: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 1a: pop
      // 1b: aload 0
      // 1c: invokespecial junit/framework/TestResult.cloneListeners ()Ljava/util/List;
      // 1f: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 24: astore 3
      // 25: aload 3
      // 26: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 2b: ifeq 41
      // 2e: aload 3
      // 2f: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 34: checkcast junit/framework/TestListener
      // 37: aload 1
      // 38: aload 2
      // 39: invokeinterface junit/framework/TestListener.addError (Ljunit/framework/Test;Ljava/lang/Throwable;)V 3
      // 3e: goto 25
      // 41: aload 0
      // 42: monitorexit
      // 43: return
      // 44: astore 1
      // 45: aload 0
      // 46: monitorexit
      // 47: aload 1
      // 48: athrow
   }

   public void addFailure(Test param1, AssertionFailedError param2) {
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
      // 03: getfield junit/framework/TestResult.fFailures Ljava/util/List;
      // 06: astore 3
      // 07: new junit/framework/TestFailure
      // 0a: astore 4
      // 0c: aload 4
      // 0e: aload 1
      // 0f: aload 2
      // 10: invokespecial junit/framework/TestFailure.<init> (Ljunit/framework/Test;Ljava/lang/Throwable;)V
      // 13: aload 3
      // 14: aload 4
      // 16: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 1b: pop
      // 1c: aload 0
      // 1d: invokespecial junit/framework/TestResult.cloneListeners ()Ljava/util/List;
      // 20: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 25: astore 3
      // 26: aload 3
      // 27: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 2c: ifeq 42
      // 2f: aload 3
      // 30: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 35: checkcast junit/framework/TestListener
      // 38: aload 1
      // 39: aload 2
      // 3a: invokeinterface junit/framework/TestListener.addFailure (Ljunit/framework/Test;Ljunit/framework/AssertionFailedError;)V 3
      // 3f: goto 26
      // 42: aload 0
      // 43: monitorexit
      // 44: return
      // 45: astore 1
      // 46: aload 0
      // 47: monitorexit
      // 48: aload 1
      // 49: athrow
   }

   public void addListener(TestListener param1) {
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
      // 03: getfield junit/framework/TestResult.fListeners Ljava/util/List;
      // 06: aload 1
      // 07: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 0c: pop
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: return
      // 10: astore 1
      // 11: aload 0
      // 12: monitorexit
      // 13: aload 1
      // 14: athrow
   }

   public void endTest(Test var1) {
      Iterator var2 = this.cloneListeners().iterator();

      while (var2.hasNext()) {
         ((TestListener)var2.next()).endTest(var1);
      }
   }

   public int errorCount() {
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
      // 03: getfield junit/framework/TestResult.fErrors Ljava/util/List;
      // 06: invokeinterface java/util/List.size ()I 1
      // 0b: istore 1
      // 0c: aload 0
      // 0d: monitorexit
      // 0e: iload 1
      // 0f: ireturn
      // 10: astore 2
      // 11: aload 0
      // 12: monitorexit
      // 13: aload 2
      // 14: athrow
   }

   public Enumeration<TestFailure> errors() {
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
      // 03: getfield junit/framework/TestResult.fErrors Ljava/util/List;
      // 06: invokestatic java/util/Collections.enumeration (Ljava/util/Collection;)Ljava/util/Enumeration;
      // 09: astore 1
      // 0a: aload 0
      // 0b: monitorexit
      // 0c: aload 1
      // 0d: areturn
      // 0e: astore 1
      // 0f: aload 0
      // 10: monitorexit
      // 11: aload 1
      // 12: athrow
   }

   public int failureCount() {
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
      // 03: getfield junit/framework/TestResult.fFailures Ljava/util/List;
      // 06: invokeinterface java/util/List.size ()I 1
      // 0b: istore 1
      // 0c: aload 0
      // 0d: monitorexit
      // 0e: iload 1
      // 0f: ireturn
      // 10: astore 2
      // 11: aload 0
      // 12: monitorexit
      // 13: aload 2
      // 14: athrow
   }

   public Enumeration<TestFailure> failures() {
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
      // 03: getfield junit/framework/TestResult.fFailures Ljava/util/List;
      // 06: invokestatic java/util/Collections.enumeration (Ljava/util/Collection;)Ljava/util/Enumeration;
      // 09: astore 1
      // 0a: aload 0
      // 0b: monitorexit
      // 0c: aload 1
      // 0d: areturn
      // 0e: astore 1
      // 0f: aload 0
      // 10: monitorexit
      // 11: aload 1
      // 12: athrow
   }

   public void removeListener(TestListener param1) {
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
      // 03: getfield junit/framework/TestResult.fListeners Ljava/util/List;
      // 06: aload 1
      // 07: invokeinterface java/util/List.remove (Ljava/lang/Object;)Z 2
      // 0c: pop
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: return
      // 10: astore 1
      // 11: aload 0
      // 12: monitorexit
      // 13: aload 1
      // 14: athrow
   }

   protected void run(TestCase var1) {
      this.startTest(var1);
      this.runProtected(var1, new Protectable(this, var1) {
         final TestResult this$0;
         final TestCase val$test;

         {
            this.this$0 = var1;
            this.val$test = var2;
         }

         @Override
         public void protect() throws Throwable {
            this.val$test.runBare();
         }
      });
      this.endTest(var1);
   }

   public int runCount() {
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
      // 0: aload 0
      // 1: monitorenter
      // 2: aload 0
      // 3: getfield junit/framework/TestResult.fRunTests I
      // 6: istore 1
      // 7: aload 0
      // 8: monitorexit
      // 9: iload 1
      // a: ireturn
      // b: astore 2
      // c: aload 0
      // d: monitorexit
      // e: aload 2
      // f: athrow
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void runProtected(Test var1, Protectable var2) {
      try {
         try {
            var2.protect();
            return;
         } catch (AssertionFailedError var6) {
            var9 = var6;
         } catch (ThreadDeath var7) {
            var1 = var7;
            throw var7;
         }
      } catch (Throwable var8) {
         this.addError((Test)var1, var8);
         return;
      }

      this.addFailure((Test)var1, var9);
   }

   public boolean shouldStop() {
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
      // 0: aload 0
      // 1: monitorenter
      // 2: aload 0
      // 3: getfield junit/framework/TestResult.fStop Z
      // 6: istore 1
      // 7: aload 0
      // 8: monitorexit
      // 9: iload 1
      // a: ireturn
      // b: astore 2
      // c: aload 0
      // d: monitorexit
      // e: aload 2
      // f: athrow
   }

   public void startTest(Test param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 1
      // 01: invokeinterface junit/framework/Test.countTestCases ()I 1
      // 06: istore 2
      // 07: aload 0
      // 08: monitorenter
      // 09: aload 0
      // 0a: aload 0
      // 0b: getfield junit/framework/TestResult.fRunTests I
      // 0e: iload 2
      // 0f: iadd
      // 10: putfield junit/framework/TestResult.fRunTests I
      // 13: aload 0
      // 14: monitorexit
      // 15: aload 0
      // 16: invokespecial junit/framework/TestResult.cloneListeners ()Ljava/util/List;
      // 19: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 1e: astore 3
      // 1f: aload 3
      // 20: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 25: ifeq 3a
      // 28: aload 3
      // 29: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 2e: checkcast junit/framework/TestListener
      // 31: aload 1
      // 32: invokeinterface junit/framework/TestListener.startTest (Ljunit/framework/Test;)V 2
      // 37: goto 1f
      // 3a: return
      // 3b: astore 1
      // 3c: aload 0
      // 3d: monitorexit
      // 3e: aload 1
      // 3f: athrow
   }

   public void stop() {
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
      // 0: aload 0
      // 1: monitorenter
      // 2: aload 0
      // 3: bipush 1
      // 4: putfield junit/framework/TestResult.fStop Z
      // 7: aload 0
      // 8: monitorexit
      // 9: return
      // a: astore 1
      // b: aload 0
      // c: monitorexit
      // d: aload 1
      // e: athrow
   }

   public boolean wasSuccessful() {
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
      // 03: invokevirtual junit/framework/TestResult.failureCount ()I
      // 06: ifne 17
      // 09: aload 0
      // 0a: invokevirtual junit/framework/TestResult.errorCount ()I
      // 0d: istore 1
      // 0e: iload 1
      // 0f: ifne 17
      // 12: bipush 1
      // 13: istore 2
      // 14: goto 19
      // 17: bipush 0
      // 18: istore 2
      // 19: aload 0
      // 1a: monitorexit
      // 1b: iload 2
      // 1c: ireturn
      // 1d: astore 3
      // 1e: aload 0
      // 1f: monitorexit
      // 20: aload 3
      // 21: athrow
   }
}
