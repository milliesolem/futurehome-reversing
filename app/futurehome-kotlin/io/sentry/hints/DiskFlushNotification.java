package io.sentry.hints;

import io.sentry.protocol.SentryId;

public interface DiskFlushNotification {
   boolean isFlushable(SentryId var1);

   void markFlushed();

   void setFlushable(SentryId var1);
}
