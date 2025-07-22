package io.sentry.instrumentation.file;

import io.sentry.HubAdapter;
import io.sentry.IHub;
import io.sentry.ISpan;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public final class SentryFileOutputStream extends FileOutputStream {
   private final FileOutputStream delegate;
   private final FileIOSpanManager spanManager;

   private SentryFileOutputStream(FileOutputStreamInitData var1) throws FileNotFoundException {
      super(getFileDescriptor(var1.delegate));
      this.spanManager = new FileIOSpanManager(var1.span, var1.file, var1.options);
      this.delegate = var1.delegate;
   }

   private SentryFileOutputStream(FileOutputStreamInitData var1, FileDescriptor var2) {
      super(var2);
      this.spanManager = new FileIOSpanManager(var1.span, var1.file, var1.options);
      this.delegate = var1.delegate;
   }

   public SentryFileOutputStream(File var1) throws FileNotFoundException {
      this(var1, false, HubAdapter.getInstance());
   }

   public SentryFileOutputStream(File var1, boolean var2) throws FileNotFoundException {
      this(init(var1, var2, null, HubAdapter.getInstance()));
   }

   SentryFileOutputStream(File var1, boolean var2, IHub var3) throws FileNotFoundException {
      this(init(var1, var2, null, var3));
   }

   public SentryFileOutputStream(FileDescriptor var1) {
      this(init(var1, null, HubAdapter.getInstance()), var1);
   }

   public SentryFileOutputStream(String var1) throws FileNotFoundException {
      File var2;
      if (var1 != null) {
         var2 = new File(var1);
      } else {
         var2 = null;
      }

      this(var2, false, HubAdapter.getInstance());
   }

   public SentryFileOutputStream(String var1, boolean var2) throws FileNotFoundException {
      File var3;
      if (var1 != null) {
         var3 = new File(var1);
      } else {
         var3 = null;
      }

      this(init(var3, var2, null, HubAdapter.getInstance()));
   }

   private static FileDescriptor getFileDescriptor(FileOutputStream var0) throws FileNotFoundException {
      try {
         return var0.getFD();
      } catch (IOException var1) {
         throw new FileNotFoundException("No file descriptor");
      }
   }

   private static FileOutputStreamInitData init(File var0, boolean var1, FileOutputStream var2, IHub var3) throws FileNotFoundException {
      ISpan var5 = FileIOSpanManager.startSpan(var3, "file.write");
      FileOutputStream var4 = var2;
      if (var2 == null) {
         var4 = new FileOutputStream(var0, var1);
      }

      return new FileOutputStreamInitData(var0, var1, var5, var4, var3.getOptions());
   }

   private static FileOutputStreamInitData init(FileDescriptor var0, FileOutputStream var1, IHub var2) {
      ISpan var4 = FileIOSpanManager.startSpan(var2, "file.write");
      FileOutputStream var3 = var1;
      if (var1 == null) {
         var3 = new FileOutputStream(var0);
      }

      return new FileOutputStreamInitData(null, false, var4, var3, var2.getOptions());
   }

   @Override
   public void close() throws IOException {
      this.spanManager.finish(this.delegate);
   }

   @Override
   public void write(int var1) throws IOException {
      this.spanManager.performIO(new SentryFileOutputStream$$ExternalSyntheticLambda1(this, var1));
   }

   @Override
   public void write(byte[] var1) throws IOException {
      this.spanManager.performIO(new SentryFileOutputStream$$ExternalSyntheticLambda2(this, var1));
   }

   @Override
   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.spanManager.performIO(new SentryFileOutputStream$$ExternalSyntheticLambda0(this, var1, var2, var3));
   }

   public static final class Factory {
      public static FileOutputStream create(FileOutputStream var0, File var1) throws FileNotFoundException {
         HubAdapter var3 = HubAdapter.getInstance();
         Object var2 = var0;
         if (isTracingEnabled(var3)) {
            var2 = new SentryFileOutputStream(SentryFileOutputStream.init(var1, false, var0, var3));
         }

         return (FileOutputStream)var2;
      }

      public static FileOutputStream create(FileOutputStream var0, File var1, IHub var2) throws FileNotFoundException {
         Object var3 = var0;
         if (isTracingEnabled(var2)) {
            var3 = new SentryFileOutputStream(SentryFileOutputStream.init(var1, false, var0, var2));
         }

         return (FileOutputStream)var3;
      }

      public static FileOutputStream create(FileOutputStream var0, File var1, boolean var2) throws FileNotFoundException {
         HubAdapter var4 = HubAdapter.getInstance();
         Object var3 = var0;
         if (isTracingEnabled(var4)) {
            var3 = new SentryFileOutputStream(SentryFileOutputStream.init(var1, var2, var0, var4));
         }

         return (FileOutputStream)var3;
      }

      public static FileOutputStream create(FileOutputStream var0, FileDescriptor var1) {
         HubAdapter var3 = HubAdapter.getInstance();
         Object var2 = var0;
         if (isTracingEnabled(var3)) {
            var2 = new SentryFileOutputStream(SentryFileOutputStream.init(var1, var0, var3), var1);
         }

         return (FileOutputStream)var2;
      }

      public static FileOutputStream create(FileOutputStream var0, String var1) throws FileNotFoundException {
         HubAdapter var3 = HubAdapter.getInstance();
         Object var2 = var0;
         if (isTracingEnabled(var3)) {
            File var4;
            if (var1 != null) {
               var4 = new File(var1);
            } else {
               var4 = null;
            }

            var2 = new SentryFileOutputStream(SentryFileOutputStream.init(var4, false, var0, var3));
         }

         return (FileOutputStream)var2;
      }

      public static FileOutputStream create(FileOutputStream var0, String var1, boolean var2) throws FileNotFoundException {
         HubAdapter var4 = HubAdapter.getInstance();
         Object var3 = var0;
         if (isTracingEnabled(var4)) {
            File var5;
            if (var1 != null) {
               var5 = new File(var1);
            } else {
               var5 = null;
            }

            var3 = new SentryFileOutputStream(SentryFileOutputStream.init(var5, var2, var0, var4));
         }

         return (FileOutputStream)var3;
      }

      private static boolean isTracingEnabled(IHub var0) {
         return var0.getOptions().isTracingEnabled();
      }
   }
}
