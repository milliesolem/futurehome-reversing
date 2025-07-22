package io.sentry;

import io.sentry.protocol.Contexts;
import io.sentry.protocol.Request;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.User;
import java.util.Collection;
import java.util.Map;

public interface IScopeObserver {
   void addBreadcrumb(Breadcrumb var1);

   void removeExtra(String var1);

   void removeTag(String var1);

   void setBreadcrumbs(Collection<Breadcrumb> var1);

   void setContexts(Contexts var1);

   void setExtra(String var1, String var2);

   void setExtras(Map<String, Object> var1);

   void setFingerprint(Collection<String> var1);

   void setLevel(SentryLevel var1);

   void setReplayId(SentryId var1);

   void setRequest(Request var1);

   void setTag(String var1, String var2);

   void setTags(Map<String, String> var1);

   void setTrace(SpanContext var1, IScope var2);

   void setTransaction(String var1);

   void setUser(User var1);
}
