package junit.extensions;

import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestResult;

public class TestSetup extends TestDecorator {
   public TestSetup(Test var1) {
      super(var1);
   }

   @Override
   public void run(TestResult var1) {
      var1.runProtected(this, new Protectable(this, var1) {
         final TestSetup this$0;
         final TestResult val$result;

         {
            this.this$0 = var1;
            this.val$result = var2;
         }

         @Override
         public void protect() throws Exception {
            this.this$0.setUp();
            this.this$0.basicRun(this.val$result);
            this.this$0.tearDown();
         }
      });
   }

   protected void setUp() throws Exception {
   }

   protected void tearDown() throws Exception {
   }
}
