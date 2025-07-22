package io.sentry.clientreport;

import java.util.List;

public interface IClientReportStorage {
   void addCount(ClientReportKey var1, Long var2);

   List<DiscardedEvent> resetCountsAndGet();
}
