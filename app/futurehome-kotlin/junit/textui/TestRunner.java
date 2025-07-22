package junit.textui;

import java.io.PrintStream;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.runner.BaseTestRunner;
import junit.runner.Version;

public class TestRunner extends BaseTestRunner {
   public static final int EXCEPTION_EXIT = 2;
   public static final int FAILURE_EXIT = 1;
   public static final int SUCCESS_EXIT = 0;
   private ResultPrinter fPrinter;

   public TestRunner() {
      this(System.out);
   }

   public TestRunner(PrintStream var1) {
      this(new ResultPrinter(var1));
   }

   public TestRunner(ResultPrinter var1) {
      this.fPrinter = var1;
   }

   public static void main(String[] var0) {
      TestRunner var1 = new TestRunner();

      try {
         if (!var1.start(var0).wasSuccessful()) {
            System.exit(1);
         }

         System.exit(0);
      } catch (Exception var2) {
         System.err.println(var2.getMessage());
         System.exit(2);
      }
   }

   public static TestResult run(Test var0) {
      return new TestRunner().doRun(var0);
   }

   public static void run(Class<? extends TestCase> var0) {
      run(new TestSuite(var0));
   }

   public static void runAndWait(Test var0) {
      new TestRunner().doRun(var0, true);
   }

   protected TestResult createTestResult() {
      return new TestResult();
   }

   public TestResult doRun(Test var1) {
      return this.doRun(var1, false);
   }

   public TestResult doRun(Test var1, boolean var2) {
      TestResult var7 = this.createTestResult();
      var7.addListener(this.fPrinter);
      long var3 = System.currentTimeMillis();
      var1.run(var7);
      long var5 = System.currentTimeMillis();
      this.fPrinter.print(var7, var5 - var3);
      this.pause(var2);
      return var7;
   }

   protected void pause(boolean var1) {
      if (var1) {
         this.fPrinter.printWaitPrompt();

         try {
            System.in.read();
         } catch (Exception var3) {
         }
      }
   }

   @Override
   protected void runFailed(String var1) {
      System.err.println(var1);
      System.exit(1);
   }

   protected TestResult runSingleMethod(String var1, String var2, boolean var3) throws Exception {
      return this.doRun(TestSuite.createTest(this.loadSuiteClass(var1).asSubclass(TestCase.class), var2), var3);
   }

   public void setPrinter(ResultPrinter var1) {
      this.fPrinter = var1;
   }

   public TestResult start(String[] var1) throws Exception {
      String var5 = "";
      String var6 = "";
      int var2 = 0;

      boolean var4;
      for (var4 = false; var2 < var1.length; var2++) {
         if (var1[var2].equals("-wait")) {
            var4 = true;
         } else if (var1[var2].equals("-c")) {
            var5 = this.extractClassName(var1[++var2]);
         } else if (var1[var2].equals("-m")) {
            var6 = var1[++var2];
            int var3 = var6.lastIndexOf(46);
            var5 = var6.substring(0, var3);
            var6 = var6.substring(var3 + 1);
         } else if (var1[var2].equals("-v")) {
            PrintStream var7 = System.err;
            StringBuilder var8 = new StringBuilder("JUnit ");
            var8.append(Version.id());
            var8.append(" by Kent Beck and Erich Gamma");
            var7.println(var8.toString());
         } else {
            var5 = var1[var2];
         }
      }

      if (!var5.equals("")) {
         try {
            return !var6.equals("") ? this.runSingleMethod(var5, var6, var4) : this.doRun(this.getTest(var5), var4);
         } catch (Exception var9) {
            StringBuilder var10 = new StringBuilder("Could not create and run test suite: ");
            var10.append(var9);
            throw new Exception(var10.toString());
         }
      } else {
         throw new Exception("Usage: TestRunner [-wait] testCaseName, where name is the name of the TestCase class");
      }
   }

   @Override
   public void testEnded(String var1) {
   }

   @Override
   public void testFailed(int var1, Test var2, Throwable var3) {
   }

   @Override
   public void testStarted(String var1) {
   }
}
