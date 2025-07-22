package org.junit.experimental;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.runner.Computer;
import org.junit.runner.Runner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.RunnerScheduler;

public class ParallelComputer extends Computer {
   private final boolean classes;
   private final boolean methods;

   public ParallelComputer(boolean var1, boolean var2) {
      this.classes = var1;
      this.methods = var2;
   }

   public static Computer classes() {
      return new ParallelComputer(true, false);
   }

   public static Computer methods() {
      return new ParallelComputer(false, true);
   }

   private static Runner parallelize(Runner var0) {
      if (var0 instanceof ParentRunner) {
         ((ParentRunner)var0).setScheduler(new RunnerScheduler() {
            private final ExecutorService fService = Executors.newCachedThreadPool();

            public void finished() {
               try {
                  this.fService.shutdown();
                  this.fService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
               } catch (InterruptedException var2) {
                  var2.printStackTrace(System.err);
               }
            }

            public void schedule(Runnable var1) {
               this.fService.submit(var1);
            }
         });
      }

      return var0;
   }

   protected Runner getRunner(RunnerBuilder var1, Class<?> var2) throws Throwable {
      Runner var4 = super.getRunner(var1, var2);
      Runner var3 = var4;
      if (this.methods) {
         var3 = parallelize(var4);
      }

      return var3;
   }

   public Runner getSuite(RunnerBuilder var1, Class<?>[] var2) throws InitializationError {
      Runner var4 = super.getSuite(var1, var2);
      Runner var3 = var4;
      if (this.classes) {
         var3 = parallelize(var4);
      }

      return var3;
   }
}
