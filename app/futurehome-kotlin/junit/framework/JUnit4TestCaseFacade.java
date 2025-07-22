package junit.framework;

import org.junit.runner.Describable;
import org.junit.runner.Description;

public class JUnit4TestCaseFacade implements Test, Describable {
   private final Description fDescription;

   JUnit4TestCaseFacade(Description var1) {
      this.fDescription = var1;
   }

   @Override
   public int countTestCases() {
      return 1;
   }

   public Description getDescription() {
      return this.fDescription;
   }

   @Override
   public void run(TestResult var1) {
      throw new RuntimeException("This test stub created only for informational purposes.");
   }

   @Override
   public String toString() {
      return this.getDescription().toString();
   }
}
