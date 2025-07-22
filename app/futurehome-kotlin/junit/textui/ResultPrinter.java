package junit.textui;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Enumeration;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestFailure;
import junit.framework.TestListener;
import junit.framework.TestResult;
import junit.runner.BaseTestRunner;

public class ResultPrinter implements TestListener {
   int fColumn = 0;
   PrintStream fWriter;

   public ResultPrinter(PrintStream var1) {
      this.fWriter = var1;
   }

   @Override
   public void addError(Test var1, Throwable var2) {
      this.getWriter().print("E");
   }

   @Override
   public void addFailure(Test var1, AssertionFailedError var2) {
      this.getWriter().print("F");
   }

   protected String elapsedTimeAsString(long var1) {
      return NumberFormat.getInstance().format(var1 / 1000.0);
   }

   @Override
   public void endTest(Test var1) {
   }

   public PrintStream getWriter() {
      return this.fWriter;
   }

   void print(TestResult param1, long param2) {
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
      // 03: lload 2
      // 04: invokevirtual junit/textui/ResultPrinter.printHeader (J)V
      // 07: aload 0
      // 08: aload 1
      // 09: invokevirtual junit/textui/ResultPrinter.printErrors (Ljunit/framework/TestResult;)V
      // 0c: aload 0
      // 0d: aload 1
      // 0e: invokevirtual junit/textui/ResultPrinter.printFailures (Ljunit/framework/TestResult;)V
      // 11: aload 0
      // 12: aload 1
      // 13: invokevirtual junit/textui/ResultPrinter.printFooter (Ljunit/framework/TestResult;)V
      // 16: aload 0
      // 17: monitorexit
      // 18: return
      // 19: astore 1
      // 1a: aload 0
      // 1b: monitorexit
      // 1c: aload 1
      // 1d: athrow
   }

   public void printDefect(TestFailure var1, int var2) {
      this.printDefectHeader(var1, var2);
      this.printDefectTrace(var1);
   }

   protected void printDefectHeader(TestFailure var1, int var2) {
      PrintStream var3 = this.getWriter();
      StringBuilder var4 = new StringBuilder();
      var4.append(var2);
      var4.append(") ");
      var4.append(var1.failedTest());
      var3.print(var4.toString());
   }

   protected void printDefectTrace(TestFailure var1) {
      this.getWriter().print(BaseTestRunner.getFilteredTrace(var1.trace()));
   }

   protected void printDefects(Enumeration<TestFailure> var1, int var2, String var3) {
      if (var2 != 0) {
         if (var2 == 1) {
            PrintStream var4 = this.getWriter();
            StringBuilder var5 = new StringBuilder("There was ");
            var5.append(var2);
            var5.append(" ");
            var5.append(var3);
            var5.append(":");
            var4.println(var5.toString());
         } else {
            PrintStream var7 = this.getWriter();
            StringBuilder var8 = new StringBuilder("There were ");
            var8.append(var2);
            var8.append(" ");
            var8.append(var3);
            var8.append("s:");
            var7.println(var8.toString());
         }

         for (int var6 = 1; var1.hasMoreElements(); var6++) {
            this.printDefect((TestFailure)var1.nextElement(), var6);
         }
      }
   }

   protected void printErrors(TestResult var1) {
      this.printDefects(var1.errors(), var1.errorCount(), "error");
   }

   protected void printFailures(TestResult var1) {
      this.printDefects(var1.failures(), var1.failureCount(), "failure");
   }

   protected void printFooter(TestResult var1) {
      if (var1.wasSuccessful()) {
         this.getWriter().println();
         this.getWriter().print("OK");
         PrintStream var3 = this.getWriter();
         StringBuilder var2 = new StringBuilder(" (");
         var2.append(var1.runCount());
         var2.append(" test");
         String var4;
         if (var1.runCount() == 1) {
            var4 = "";
         } else {
            var4 = "s";
         }

         var2.append(var4);
         var2.append(")");
         var3.println(var2.toString());
      } else {
         this.getWriter().println();
         this.getWriter().println("FAILURES!!!");
         PrintStream var5 = this.getWriter();
         StringBuilder var6 = new StringBuilder("Tests run: ");
         var6.append(var1.runCount());
         var6.append(",  Failures: ");
         var6.append(var1.failureCount());
         var6.append(",  Errors: ");
         var6.append(var1.errorCount());
         var5.println(var6.toString());
      }

      this.getWriter().println();
   }

   protected void printHeader(long var1) {
      this.getWriter().println();
      PrintStream var4 = this.getWriter();
      StringBuilder var3 = new StringBuilder("Time: ");
      var3.append(this.elapsedTimeAsString(var1));
      var4.println(var3.toString());
   }

   void printWaitPrompt() {
      this.getWriter().println();
      this.getWriter().println("<RETURN> to continue");
   }

   @Override
   public void startTest(Test var1) {
      this.getWriter().print(".");
      int var2 = this.fColumn++;
      if (var2 >= 40) {
         this.getWriter().println();
         this.fColumn = 0;
      }
   }
}
