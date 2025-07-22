package junit.framework;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TestFailure {
   protected Test fFailedTest;
   protected Throwable fThrownException;

   public TestFailure(Test var1, Throwable var2) {
      this.fFailedTest = var1;
      this.fThrownException = var2;
   }

   public String exceptionMessage() {
      return this.thrownException().getMessage();
   }

   public Test failedTest() {
      return this.fFailedTest;
   }

   public boolean isFailure() {
      return this.thrownException() instanceof AssertionFailedError;
   }

   public Throwable thrownException() {
      return this.fThrownException;
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.fFailedTest);
      var1.append(": ");
      var1.append(this.fThrownException.getMessage());
      return var1.toString();
   }

   public String trace() {
      StringWriter var1 = new StringWriter();
      PrintWriter var2 = new PrintWriter(var1);
      this.thrownException().printStackTrace(var2);
      return var1.toString();
   }
}
