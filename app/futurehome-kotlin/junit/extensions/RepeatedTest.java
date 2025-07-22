package junit.extensions;

import junit.framework.Test;
import junit.framework.TestResult;

public class RepeatedTest extends TestDecorator {
   private int fTimesRepeat;

   public RepeatedTest(Test var1, int var2) {
      super(var1);
      if (var2 >= 0) {
         this.fTimesRepeat = var2;
      } else {
         throw new IllegalArgumentException("Repetition count must be >= 0");
      }
   }

   @Override
   public int countTestCases() {
      return super.countTestCases() * this.fTimesRepeat;
   }

   @Override
   public void run(TestResult var1) {
      for (int var2 = 0; var2 < this.fTimesRepeat && !var1.shouldStop(); var2++) {
         super.run(var1);
      }
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(super.toString());
      var1.append("(repeated)");
      return var1.toString();
   }
}
