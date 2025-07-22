package io.sentry;

import io.sentry.protocol.Contexts;
import io.sentry.protocol.Request;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.User;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public final class NoOpScope implements IScope {
   private static final NoOpScope instance = new NoOpScope();
   private final SentryOptions emptyOptions = SentryOptions.empty();

   private NoOpScope() {
   }

   public static NoOpScope getInstance() {
      return instance;
   }

   @Override
   public void addAttachment(Attachment var1) {
   }

   @Override
   public void addBreadcrumb(Breadcrumb var1) {
   }

   @Override
   public void addBreadcrumb(Breadcrumb var1, Hint var2) {
   }

   @Override
   public void addEventProcessor(EventProcessor var1) {
   }

   @Override
   public void clear() {
   }

   @Override
   public void clearAttachments() {
   }

   @Override
   public void clearBreadcrumbs() {
   }

   @Override
   public void clearSession() {
   }

   @Override
   public void clearTransaction() {
   }

   @Override
   public IScope clone() {
      return getInstance();
   }

   @Override
   public Session endSession() {
      return null;
   }

   @Override
   public List<Attachment> getAttachments() {
      return new ArrayList<>();
   }

   @Override
   public Queue<Breadcrumb> getBreadcrumbs() {
      return new ArrayDeque<>();
   }

   @Override
   public Contexts getContexts() {
      return new Contexts();
   }

   @Override
   public List<EventProcessor> getEventProcessors() {
      return new ArrayList<>();
   }

   @Override
   public Map<String, Object> getExtras() {
      return new HashMap<>();
   }

   @Override
   public List<String> getFingerprint() {
      return new ArrayList<>();
   }

   @Override
   public SentryLevel getLevel() {
      return null;
   }

   @Override
   public SentryOptions getOptions() {
      return this.emptyOptions;
   }

   @Override
   public PropagationContext getPropagationContext() {
      return new PropagationContext();
   }

   @Override
   public SentryId getReplayId() {
      return SentryId.EMPTY_ID;
   }

   @Override
   public Request getRequest() {
      return null;
   }

   @Override
   public String getScreen() {
      return null;
   }

   @Override
   public Session getSession() {
      return null;
   }

   @Override
   public ISpan getSpan() {
      return null;
   }

   @Override
   public Map<String, String> getTags() {
      return new HashMap<>();
   }

   @Override
   public ITransaction getTransaction() {
      return null;
   }

   @Override
   public String getTransactionName() {
      return null;
   }

   @Override
   public User getUser() {
      return null;
   }

   @Override
   public void removeContexts(String var1) {
   }

   @Override
   public void removeExtra(String var1) {
   }

   @Override
   public void removeTag(String var1) {
   }

   @Override
   public void setContexts(String var1, Boolean var2) {
   }

   @Override
   public void setContexts(String var1, Character var2) {
   }

   @Override
   public void setContexts(String var1, Number var2) {
   }

   @Override
   public void setContexts(String var1, Object var2) {
   }

   @Override
   public void setContexts(String var1, String var2) {
   }

   @Override
   public void setContexts(String var1, Collection<?> var2) {
   }

   @Override
   public void setContexts(String var1, Object[] var2) {
   }

   @Override
   public void setExtra(String var1, String var2) {
   }

   @Override
   public void setFingerprint(List<String> var1) {
   }

   @Override
   public void setLevel(SentryLevel var1) {
   }

   @Override
   public void setPropagationContext(PropagationContext var1) {
   }

   @Override
   public void setReplayId(SentryId var1) {
   }

   @Override
   public void setRequest(Request var1) {
   }

   @Override
   public void setScreen(String var1) {
   }

   @Override
   public void setTag(String var1, String var2) {
   }

   @Override
   public void setTransaction(ITransaction var1) {
   }

   @Override
   public void setTransaction(String var1) {
   }

   @Override
   public void setUser(User var1) {
   }

   @Override
   public Scope.SessionPair startSession() {
      return null;
   }

   @Override
   public PropagationContext withPropagationContext(Scope.IWithPropagationContext var1) {
      return new PropagationContext();
   }

   @Override
   public Session withSession(Scope.IWithSession var1) {
      return null;
   }

   @Override
   public void withTransaction(Scope.IWithTransaction var1) {
   }
}
