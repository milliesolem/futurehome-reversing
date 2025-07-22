package io.sentry;

import io.sentry.rrweb.RRWebEvent;

public final class NoOpReplayBreadcrumbConverter implements ReplayBreadcrumbConverter {
   private static final NoOpReplayBreadcrumbConverter instance = new NoOpReplayBreadcrumbConverter();

   private NoOpReplayBreadcrumbConverter() {
   }

   public static NoOpReplayBreadcrumbConverter getInstance() {
      return instance;
   }

   @Override
   public RRWebEvent convert(Breadcrumb var1) {
      return null;
   }
}
