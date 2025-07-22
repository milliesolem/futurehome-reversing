package io.sentry.instrumentation.file;

import io.sentry.IHub;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public final class SentryFileReader extends InputStreamReader {
   public SentryFileReader(File var1) throws FileNotFoundException {
      super(new SentryFileInputStream(var1));
   }

   SentryFileReader(File var1, IHub var2) throws FileNotFoundException {
      super(new SentryFileInputStream(var1, var2));
   }

   public SentryFileReader(FileDescriptor var1) {
      super(new SentryFileInputStream(var1));
   }

   public SentryFileReader(String var1) throws FileNotFoundException {
      super(new SentryFileInputStream(var1));
   }
}
