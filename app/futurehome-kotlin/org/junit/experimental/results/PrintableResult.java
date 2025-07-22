package org.junit.experimental.results;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class PrintableResult {
   private Result result;

   public PrintableResult(List<Failure> var1) {
      this(new FailureList(var1).result());
   }

   private PrintableResult(Result var1) {
      this.result = var1;
   }

   public static PrintableResult testResult(Class<?> var0) {
      return testResult(Request.aClass(var0));
   }

   public static PrintableResult testResult(Request var0) {
      return new PrintableResult(new JUnitCore().run(var0));
   }

   public int failureCount() {
      return this.result.getFailures().size();
   }

   @Override
   public String toString() {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      new TextListener(new PrintStream(var1)).testRunFinished(this.result);
      return var1.toString();
   }
}
