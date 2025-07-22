package org.junit.experimental.max;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import junit.framework.TestSuite;
import org.junit.internal.requests.SortingRequest;
import org.junit.internal.runners.ErrorReportingRunner;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

public class MaxCore {
   private static final String MALFORMED_JUNIT_3_TEST_CLASS_PREFIX = "malformed JUnit 3 test class: ";
   private final MaxHistory history;

   private MaxCore(File var1) {
      this.history = MaxHistory.forFolder(var1);
   }

   private Runner buildRunner(Description var1) {
      if (var1.toString().equals("TestSuite with 0 tests")) {
         return Suite.emptySuite();
      } else if (var1.toString().startsWith("malformed JUnit 3 test class: ")) {
         return new JUnit38ClassRunner(new TestSuite(this.getMalformedTestClass(var1)));
      } else {
         Class var2 = var1.getTestClass();
         if (var2 != null) {
            String var3 = var1.getMethodName();
            return var3 == null ? Request.aClass(var2).getRunner() : Request.method(var2, var3).getRunner();
         } else {
            StringBuilder var4 = new StringBuilder("Can't build a runner from description [");
            var4.append(var1);
            var4.append("]");
            throw new RuntimeException(var4.toString());
         }
      }
   }

   private Request constructLeafRequest(List<Description> var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = var1.iterator();

      while (var3.hasNext()) {
         var2.add(this.buildRunner((Description)var3.next()));
      }

      return new Request(this, var2) {
         final MaxCore this$0;
         final List val$runners;

         {
            this.this$0 = var1;
            this.val$runners = var2x;
         }

         public Runner getRunner() {
            try {
               Class var1x = (Class)null;
               return new Suite(this, null, this.val$runners) {
                  final <unrepresentable> this$1;

                  {
                     this.this$1 = var1;
                  }
               };
            } catch (InitializationError var2x) {
               return new ErrorReportingRunner(null, var2x);
            }
         }
      };
   }

   private List<Description> findLeaves(Request var1) {
      ArrayList var2 = new ArrayList();
      this.findLeaves(null, var1.getRunner().getDescription(), var2);
      return var2;
   }

   private void findLeaves(Description var1, Description var2, List<Description> var3) {
      if (var2.getChildren().isEmpty()) {
         if (var2.toString().equals("warning(junit.framework.TestSuite$1)")) {
            StringBuilder var5 = new StringBuilder("malformed JUnit 3 test class: ");
            var5.append(var1);
            var3.add(Description.createSuiteDescription(var5.toString(), new Annotation[0]));
         } else {
            var3.add(var2);
         }
      } else {
         Iterator var4 = var2.getChildren().iterator();

         while (var4.hasNext()) {
            this.findLeaves(var2, (Description)var4.next(), var3);
         }
      }
   }

   @Deprecated
   public static MaxCore forFolder(String var0) {
      return storedLocally(new File(var0));
   }

   private Class<?> getMalformedTestClass(Description var1) {
      try {
         return Class.forName(var1.toString().replace("malformed JUnit 3 test class: ", ""));
      } catch (ClassNotFoundException var2) {
         return null;
      }
   }

   public static MaxCore storedLocally(File var0) {
      return new MaxCore(var0);
   }

   public Result run(Class<?> var1) {
      return this.run(Request.aClass(var1));
   }

   public Result run(Request var1) {
      return this.run(var1, new JUnitCore());
   }

   public Result run(Request var1, JUnitCore var2) {
      var2.addListener(this.history.listener());
      return var2.run(this.sortRequest(var1).getRunner());
   }

   public Request sortRequest(Request var1) {
      if (var1 instanceof SortingRequest) {
         return var1;
      } else {
         List var2 = this.findLeaves(var1);
         Collections.sort(var2, this.history.testComparator());
         return this.constructLeafRequest(var2);
      }
   }

   public List<Description> sortedLeavesForTest(Request var1) {
      return this.findLeaves(this.sortRequest(var1));
   }
}
