package junit.extensions;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestResult;

public class TestDecorator extends Assert implements Test {
   protected Test fTest;

   public TestDecorator(Test var1) {
      this.fTest = var1;
   }

   public void basicRun(TestResult var1) {
      this.fTest.run(var1);
   }

   @Override
   public int countTestCases() {
      return this.fTest.countTestCases();
   }

   public Test getTest() {
      return this.fTest;
   }

   @Override
   public void run(TestResult var1) {
      this.basicRun(var1);
   }

   @Override
   public String toString() {
      return this.fTest.toString();
   }
}
