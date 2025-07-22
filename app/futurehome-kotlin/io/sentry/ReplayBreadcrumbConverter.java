package io.sentry;

import io.sentry.rrweb.RRWebEvent;

public interface ReplayBreadcrumbConverter {
   RRWebEvent convert(Breadcrumb var1);
}
