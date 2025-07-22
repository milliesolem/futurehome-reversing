package io.sentry.instrumentation.file;

import io.sentry.ISpan;
import io.sentry.SentryOptions;
import java.io.File;
import java.io.FileInputStream;

final class FileInputStreamInitData {
   final FileInputStream delegate;
   final File file;
   final SentryOptions options;
   final ISpan span;

   FileInputStreamInitData(File var1, ISpan var2, FileInputStream var3, SentryOptions var4) {
      this.file = var1;
      this.span = var2;
      this.delegate = var3;
      this.options = var4;
   }
}
