package io.sentry;

import io.sentry.protocol.Contexts;
import io.sentry.protocol.Request;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.User;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public interface IScope {
   void addAttachment(Attachment var1);

   void addBreadcrumb(Breadcrumb var1);

   void addBreadcrumb(Breadcrumb var1, Hint var2);

   void addEventProcessor(EventProcessor var1);

   void clear();

   void clearAttachments();

   void clearBreadcrumbs();

   void clearSession();

   void clearTransaction();

   IScope clone();

   Session endSession();

   List<Attachment> getAttachments();

   Queue<Breadcrumb> getBreadcrumbs();

   Contexts getContexts();

   List<EventProcessor> getEventProcessors();

   Map<String, Object> getExtras();

   List<String> getFingerprint();

   SentryLevel getLevel();

   SentryOptions getOptions();

   PropagationContext getPropagationContext();

   SentryId getReplayId();

   Request getRequest();

   String getScreen();

   Session getSession();

   ISpan getSpan();

   Map<String, String> getTags();

   ITransaction getTransaction();

   String getTransactionName();

   User getUser();

   void removeContexts(String var1);

   void removeExtra(String var1);

   void removeTag(String var1);

   void setContexts(String var1, Boolean var2);

   void setContexts(String var1, Character var2);

   void setContexts(String var1, Number var2);

   void setContexts(String var1, Object var2);

   void setContexts(String var1, String var2);

   void setContexts(String var1, Collection<?> var2);

   void setContexts(String var1, Object[] var2);

   void setExtra(String var1, String var2);

   void setFingerprint(List<String> var1);

   void setLevel(SentryLevel var1);

   void setPropagationContext(PropagationContext var1);

   void setReplayId(SentryId var1);

   void setRequest(Request var1);

   void setScreen(String var1);

   void setTag(String var1, String var2);

   void setTransaction(ITransaction var1);

   void setTransaction(String var1);

   void setUser(User var1);

   Scope.SessionPair startSession();

   PropagationContext withPropagationContext(Scope.IWithPropagationContext var1);

   Session withSession(Scope.IWithSession var1);

   void withTransaction(Scope.IWithTransaction var1);
}
