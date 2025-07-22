package org.junit.experimental.results;

import java.util.List;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

class FailureList {
   private final List<Failure> failures;

   public FailureList(List<Failure> var1) {
      this.failures = var1;
   }

   public Result result() {
      Result var2 = new Result();
      RunListener var4 = var2.createListener();

      for (Failure var3 : this.failures) {
         try {
            var4.testFailure(var3);
         } catch (Exception var5) {
            throw new RuntimeException("I can't believe this happened");
         }
      }

      return var2;
   }
}
