package io.sentry;

import io.sentry.protocol.Contexts;
import io.sentry.protocol.Request;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.User;
import java.util.Collection;
import java.util.Map;

public abstract class ScopeObserverAdapter implements IScopeObserver {
   @Override
   public void addBreadcrumb(Breadcrumb var1) {
   }

   @Override
   public void removeExtra(String var1) {
   }

   @Override
   public void removeTag(String var1) {
   }

   @Override
   public void setBreadcrumbs(Collection<Breadcrumb> var1) {
   }

   @Override
   public void setContexts(Contexts var1) {
   }

   @Override
   public void setExtra(String var1, String var2) {
   }

   @Override
   public void setExtras(Map<String, Object> var1) {
   }

   @Override
   public void setFingerprint(Collection<String> var1) {
   }

   @Override
   public void setLevel(SentryLevel var1) {
   }

   @Override
   public void setReplayId(SentryId var1) {
   }

   @Override
   public void setRequest(Request var1) {
   }

   @Override
   public void setTag(String var1, String var2) {
   }

   @Override
   public void setTags(Map<String, String> var1) {
   }

   @Override
   public void setTrace(SpanContext var1, IScope var2) {
   }

   @Override
   public void setTransaction(String var1) {
   }

   @Override
   public void setUser(User var1) {
   }
}
