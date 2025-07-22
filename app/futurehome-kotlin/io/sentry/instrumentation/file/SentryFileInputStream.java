package io.sentry.instrumentation.file;

import io.sentry.HubAdapter;
import io.sentry.IHub;
import io.sentry.ISpan;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public final class SentryFileInputStream extends FileInputStream {
   private final FileInputStream delegate;
   private final FileIOSpanManager spanManager;

   private SentryFileInputStream(FileInputStreamInitData var1) throws FileNotFoundException {
      super(getFileDescriptor(var1.delegate));
      this.spanManager = new FileIOSpanManager(var1.span, var1.file, var1.options);
      this.delegate = var1.delegate;
   }

   private SentryFileInputStream(FileInputStreamInitData var1, FileDescriptor var2) {
      super(var2);
      this.spanManager = new FileIOSpanManager(var1.span, var1.file, var1.options);
      this.delegate = var1.delegate;
   }

   public SentryFileInputStream(File var1) throws FileNotFoundException {
      this(var1, HubAdapter.getInstance());
   }

   SentryFileInputStream(File var1, IHub var2) throws FileNotFoundException {
      this(init(var1, null, var2));
   }

   public SentryFileInputStream(FileDescriptor var1) {
      this(var1, HubAdapter.getInstance());
   }

   SentryFileInputStream(FileDescriptor var1, IHub var2) {
      this(init(var1, null, var2), var1);
   }

   public SentryFileInputStream(String var1) throws FileNotFoundException {
      File var2;
      if (var1 != null) {
         var2 = new File(var1);
      } else {
         var2 = null;
      }

      this(var2, HubAdapter.getInstance());
   }

   private static FileDescriptor getFileDescriptor(FileInputStream var0) throws FileNotFoundException {
      try {
         return var0.getFD();
      } catch (IOException var1) {
         throw new FileNotFoundException("No file descriptor");
      }
   }

   private static FileInputStreamInitData init(File var0, FileInputStream var1, IHub var2) throws FileNotFoundException {
      ISpan var4 = FileIOSpanManager.startSpan(var2, "file.read");
      FileInputStream var3 = var1;
      if (var1 == null) {
         var3 = new FileInputStream(var0);
      }

      return new FileInputStreamInitData(var0, var4, var3, var2.getOptions());
   }

   private static FileInputStreamInitData init(FileDescriptor var0, FileInputStream var1, IHub var2) {
      ISpan var4 = FileIOSpanManager.startSpan(var2, "file.read");
      FileInputStream var3 = var1;
      if (var1 == null) {
         var3 = new FileInputStream(var0);
      }

      return new FileInputStreamInitData(null, var4, var3, var2.getOptions());
   }

   @Override
   public void close() throws IOException {
      this.spanManager.finish(this.delegate);
   }

   @Override
   public int read() throws IOException {
      AtomicInteger var1 = new AtomicInteger(0);
      this.spanManager.performIO(new SentryFileInputStream$$ExternalSyntheticLambda3(this, var1));
      return var1.get();
   }

   @Override
   public int read(byte[] var1) throws IOException {
      return this.spanManager.performIO(new SentryFileInputStream$$ExternalSyntheticLambda2(this, var1));
   }

   @Override
   public int read(byte[] var1, int var2, int var3) throws IOException {
      return this.spanManager.performIO(new SentryFileInputStream$$ExternalSyntheticLambda1(this, var1, var2, var3));
   }

   @Override
   public long skip(long var1) throws IOException {
      return this.spanManager.performIO(new SentryFileInputStream$$ExternalSyntheticLambda0(this, var1));
   }

   public static final class Factory {
      public static FileInputStream create(FileInputStream var0, File var1) throws FileNotFoundException {
         HubAdapter var3 = HubAdapter.getInstance();
         Object var2 = var0;
         if (isTracingEnabled(var3)) {
            var2 = new SentryFileInputStream(SentryFileInputStream.init(var1, var0, var3));
         }

         return (FileInputStream)var2;
      }

      static FileInputStream create(FileInputStream var0, File var1, IHub var2) throws FileNotFoundException {
         Object var3 = var0;
         if (isTracingEnabled(var2)) {
            var3 = new SentryFileInputStream(SentryFileInputStream.init(var1, var0, var2));
         }

         return (FileInputStream)var3;
      }

      public static FileInputStream create(FileInputStream var0, FileDescriptor var1) {
         HubAdapter var3 = HubAdapter.getInstance();
         Object var2 = var0;
         if (isTracingEnabled(var3)) {
            var2 = new SentryFileInputStream(SentryFileInputStream.init(var1, var0, var3), var1);
         }

         return (FileInputStream)var2;
      }

      public static FileInputStream create(FileInputStream var0, String var1) throws FileNotFoundException {
         HubAdapter var3 = HubAdapter.getInstance();
         Object var2 = var0;
         if (isTracingEnabled(var3)) {
            File var4;
            if (var1 != null) {
               var4 = new File(var1);
            } else {
               var4 = null;
            }

            var2 = new SentryFileInputStream(SentryFileInputStream.init(var4, var0, var3));
         }

         return (FileInputStream)var2;
      }

      private static boolean isTracingEnabled(IHub var0) {
         return var0.getOptions().isTracingEnabled();
      }
   }
}
