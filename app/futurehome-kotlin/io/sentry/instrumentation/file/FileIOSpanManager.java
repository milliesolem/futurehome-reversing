package io.sentry.instrumentation.file;

import io.sentry.IHub;
import io.sentry.ISpan;
import io.sentry.SentryIntegrationPackageStorage;
import io.sentry.SentryOptions;
import io.sentry.SentryStackTraceFactory;
import io.sentry.SpanStatus;
import io.sentry.util.Platform;
import io.sentry.util.StringUtils;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;

final class FileIOSpanManager {
   private long byteCount;
   private final ISpan currentSpan;
   private final File file;
   private final SentryOptions options;
   private SpanStatus spanStatus = SpanStatus.OK;
   private final SentryStackTraceFactory stackTraceFactory;

   FileIOSpanManager(ISpan var1, File var2, SentryOptions var3) {
      this.currentSpan = var1;
      this.file = var2;
      this.options = var3;
      this.stackTraceFactory = new SentryStackTraceFactory(var3);
      SentryIntegrationPackageStorage.getInstance().addIntegration("FileIO");
   }

   private void finishSpan() {
      if (this.currentSpan != null) {
         String var2 = StringUtils.byteCountToString(this.byteCount);
         if (this.file != null) {
            StringBuilder var3 = new StringBuilder();
            var3.append(this.file.getName());
            var3.append(" (");
            var3.append(var2);
            var3.append(")");
            var2 = var3.toString();
            this.currentSpan.setDescription(var2);
            if (Platform.isAndroid() || this.options.isSendDefaultPii()) {
               this.currentSpan.setData("file.path", this.file.getAbsolutePath());
            }
         } else {
            this.currentSpan.setDescription(var2);
         }

         this.currentSpan.setData("file.size", this.byteCount);
         boolean var1 = this.options.getMainThreadChecker().isMainThread();
         this.currentSpan.setData("blocked_main_thread", var1);
         if (var1) {
            this.currentSpan.setData("call_stack", this.stackTraceFactory.getInAppCallStack());
         }

         this.currentSpan.finish(this.spanStatus);
      }
   }

   static ISpan startSpan(IHub var0, String var1) {
      Object var2;
      if (Platform.isAndroid()) {
         var2 = var0.getTransaction();
      } else {
         var2 = var0.getSpan();
      }

      if (var2 != null) {
         var2 = var2.startChild(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   void finish(Closeable var1) throws IOException {
      try {
         var1.close();
      } catch (IOException var4) {
         this.spanStatus = SpanStatus.INTERNAL_ERROR;
         if (this.currentSpan != null) {
            this.currentSpan.setThrowable(var4);
         }

         throw var4;
      } finally {
         this.finishSpan();
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   <T> T performIO(FileIOSpanManager.FileIOCallable<T> var1) throws IOException {
      ISpan var5;
      label55: {
         int var2;
         label56: {
            try {
               var11 = var1.call();
               if (var11 instanceof Integer) {
                  var2 = (Integer)var11;
                  break label56;
               }
            } catch (IOException var9) {
               var10 = var9;
               this.spanStatus = SpanStatus.INTERNAL_ERROR;
               var5 = this.currentSpan;
               if (var5 == null) {
                  throw var9;
               }
               break label55;
            }

            long var3;
            try {
               if (!(var11 instanceof Long)) {
                  return (T)var11;
               }

               var3 = (Long)var11;
            } catch (IOException var8) {
               var10 = var8;
               this.spanStatus = SpanStatus.INTERNAL_ERROR;
               var5 = this.currentSpan;
               if (var5 == null) {
                  throw var8;
               }
               break label55;
            }

            if (var3 == -1L) {
               return (T)var11;
            }

            try {
               this.byteCount += var3;
               return (T)var11;
            } catch (IOException var7) {
               var10 = var7;
               this.spanStatus = SpanStatus.INTERNAL_ERROR;
               var5 = this.currentSpan;
               if (var5 == null) {
                  throw var7;
               }
               break label55;
            }
         }

         if (var2 == -1) {
            return (T)var11;
         }

         try {
            this.byteCount += var2;
            return (T)var11;
         } catch (IOException var6) {
            var10 = var6;
            this.spanStatus = SpanStatus.INTERNAL_ERROR;
            var5 = this.currentSpan;
            if (var5 == null) {
               throw var6;
            }
         }
      }

      var5.setThrowable(var10);
      throw var10;
   }

   @FunctionalInterface
   interface FileIOCallable<T> {
      T call() throws IOException;
   }
}
