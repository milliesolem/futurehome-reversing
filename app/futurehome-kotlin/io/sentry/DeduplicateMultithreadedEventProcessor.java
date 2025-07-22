package io.sentry;

import io.sentry.hints.EventDropReason;
import io.sentry.protocol.SentryException;
import io.sentry.util.HintUtils;
import j..util.DesugarCollections;
import java.util.HashMap;
import java.util.Map;

public final class DeduplicateMultithreadedEventProcessor implements EventProcessor {
   private final SentryOptions options;
   private final Map<String, Long> processedEvents = DesugarCollections.synchronizedMap(new HashMap());

   public DeduplicateMultithreadedEventProcessor(SentryOptions var1) {
      this.options = var1;
   }

   @Override
   public SentryEvent process(SentryEvent var1, Hint var2) {
      if (!HintUtils.hasType(var2, UncaughtExceptionHandlerIntegration.UncaughtExceptionHint.class)) {
         return var1;
      } else {
         SentryException var4 = var1.getUnhandledException();
         if (var4 == null) {
            return var1;
         } else {
            String var3 = var4.getType();
            if (var3 == null) {
               return var1;
            } else {
               Long var6 = var4.getThreadId();
               if (var6 == null) {
                  return var1;
               } else {
                  Long var5 = this.processedEvents.get(var3);
                  if (var5 != null && !var5.equals(var6)) {
                     this.options.getLogger().log(SentryLevel.INFO, "Event %s has been dropped due to multi-threaded deduplication", var1.getEventId());
                     HintUtils.setEventDropReason(var2, EventDropReason.MULTITHREADED_DEDUPLICATION);
                     return null;
                  } else {
                     this.processedEvents.put(var3, var6);
                     return var1;
                  }
               }
            }
         }
      }
   }
}
