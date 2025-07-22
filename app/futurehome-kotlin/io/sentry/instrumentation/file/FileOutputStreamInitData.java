package io.sentry.instrumentation.file;

import io.sentry.ISpan;
import io.sentry.SentryOptions;
import java.io.File;
import java.io.FileOutputStream;

final class FileOutputStreamInitData {
   final boolean append;
   final FileOutputStream delegate;
   final File file;
   final SentryOptions options;
   final ISpan span;

   FileOutputStreamInitData(File var1, boolean var2, ISpan var3, FileOutputStream var4, SentryOptions var5) {
      this.file = var1;
      this.append = var2;
      this.span = var3;
      this.delegate = var4;
      this.options = var5;
   }
}
