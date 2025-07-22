package io.sentry.clientreport;

import io.sentry.util.LazyEvaluator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

final class AtomicClientReportStorage implements IClientReportStorage {
   private final LazyEvaluator<Map<ClientReportKey, AtomicLong>> lostEventCounts = new LazyEvaluator<>(
      new AtomicClientReportStorage$$ExternalSyntheticLambda0()
   );

   public AtomicClientReportStorage() {
   }

   @Override
   public void addCount(ClientReportKey var1, Long var2) {
      AtomicLong var3 = this.lostEventCounts.getValue().get(var1);
      if (var3 != null) {
         var3.addAndGet(var2);
      }
   }

   @Override
   public List<DiscardedEvent> resetCountsAndGet() {
      ArrayList var6 = new ArrayList();

      for (Entry var4 : this.lostEventCounts.getValue().entrySet()) {
         long var1 = ((AtomicLong)var4.getValue()).getAndSet(0L);
         Long var5 = var1;
         var5.getClass();
         if (var1 > 0L) {
            var6.add(new DiscardedEvent(((ClientReportKey)var4.getKey()).getReason(), ((ClientReportKey)var4.getKey()).getCategory(), var5));
         }
      }

      return var6;
   }
}
