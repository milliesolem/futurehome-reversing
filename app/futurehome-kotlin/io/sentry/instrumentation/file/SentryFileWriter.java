package io.sentry.instrumentation.file;

import io.sentry.IHub;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;

public final class SentryFileWriter extends OutputStreamWriter {
   public SentryFileWriter(File var1) throws FileNotFoundException {
      super(new SentryFileOutputStream(var1));
   }

   public SentryFileWriter(File var1, boolean var2) throws FileNotFoundException {
      super(new SentryFileOutputStream(var1, var2));
   }

   SentryFileWriter(File var1, boolean var2, IHub var3) throws FileNotFoundException {
      super(new SentryFileOutputStream(var1, var2, var3));
   }

   public SentryFileWriter(FileDescriptor var1) {
      super(new SentryFileOutputStream(var1));
   }

   public SentryFileWriter(String var1) throws FileNotFoundException {
      super(new SentryFileOutputStream(var1));
   }

   public SentryFileWriter(String var1, boolean var2) throws FileNotFoundException {
      super(new SentryFileOutputStream(var1, var2));
   }
}
