package io.sentry;

import io.sentry.util.Objects;
import j..util.DesugarCollections;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public final class DuplicateEventDetectionEventProcessor implements EventProcessor {
   private final Map<Throwable, Object> capturedObjects = DesugarCollections.synchronizedMap(new WeakHashMap());
   private final SentryOptions options;

   public DuplicateEventDetectionEventProcessor(SentryOptions var1) {
      this.options = Objects.requireNonNull(var1, "options are required");
   }

   private static List<Throwable> allCauses(Throwable var0) {
      ArrayList var1 = new ArrayList();

      while (var0.getCause() != null) {
         var1.add(var0.getCause());
         var0 = var0.getCause();
      }

      return var1;
   }

   private static <T> boolean containsAnyKey(Map<T, Object> var0, List<T> var1) {
      Iterator var2 = var1.iterator();

      while (var2.hasNext()) {
         if (var0.containsKey(var2.next())) {
            return true;
         }
      }

      return false;
   }

   @Override
   public SentryEvent process(SentryEvent var1, Hint var2) {
      if (this.options.isEnableDeduplication()) {
         Throwable var3 = var1.getThrowable();
         if (var3 != null) {
            if (this.capturedObjects.containsKey(var3) || containsAnyKey(this.capturedObjects, allCauses(var3))) {
               this.options.getLogger().log(SentryLevel.DEBUG, "Duplicate Exception detected. Event %s will be discarded.", var1.getEventId());
               return null;
            }

            this.capturedObjects.put(var3, null);
         }
      } else {
         this.options.getLogger().log(SentryLevel.DEBUG, "Event deduplication is disabled.");
      }

      return var1;
   }
}
