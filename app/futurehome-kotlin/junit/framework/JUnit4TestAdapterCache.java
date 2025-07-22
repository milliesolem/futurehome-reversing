package junit.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

public class JUnit4TestAdapterCache extends HashMap<Description, Test> {
   private static final JUnit4TestAdapterCache fInstance = new JUnit4TestAdapterCache();
   private static final long serialVersionUID = 1L;

   public static JUnit4TestAdapterCache getDefault() {
      return fInstance;
   }

   public Test asTest(Description var1) {
      if (var1.isSuite()) {
         return this.createTest(var1);
      } else {
         if (!this.containsKey(var1)) {
            this.put(var1, this.createTest(var1));
         }

         return this.get(var1);
      }
   }

   public List<Test> asTestList(Description var1) {
      if (var1.isTest()) {
         return Arrays.asList(this.asTest(var1));
      } else {
         ArrayList var2 = new ArrayList();
         Iterator var3 = var1.getChildren().iterator();

         while (var3.hasNext()) {
            var2.add(this.asTest((Description)var3.next()));
         }

         return var2;
      }
   }

   Test createTest(Description var1) {
      if (var1.isTest()) {
         return new JUnit4TestCaseFacade(var1);
      } else {
         TestSuite var2 = new TestSuite(var1.getDisplayName());
         Iterator var3 = var1.getChildren().iterator();

         while (var3.hasNext()) {
            var2.addTest(this.asTest((Description)var3.next()));
         }

         return var2;
      }
   }

   public RunNotifier getNotifier(TestResult var1, JUnit4TestAdapter var2) {
      RunNotifier var3 = new RunNotifier();
      var3.addListener(new RunListener(this, var1) {
         final JUnit4TestAdapterCache this$0;
         final TestResult val$result;

         {
            this.this$0 = var1;
            this.val$result = var2x;
         }

         public void testFailure(Failure var1) throws Exception {
            this.val$result.addError(this.this$0.asTest(var1.getDescription()), var1.getException());
         }

         public void testFinished(Description var1) throws Exception {
            this.val$result.endTest(this.this$0.asTest(var1));
         }

         public void testStarted(Description var1) throws Exception {
            this.val$result.startTest(this.this$0.asTest(var1));
         }
      });
      return var3;
   }
}
