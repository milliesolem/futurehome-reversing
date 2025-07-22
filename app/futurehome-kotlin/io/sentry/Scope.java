package io.sentry;

import io.sentry.protocol.App;
import io.sentry.protocol.Contexts;
import io.sentry.protocol.Request;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.TransactionNameSource;
import io.sentry.protocol.User;
import io.sentry.util.CollectionUtils;
import io.sentry.util.Objects;
import j..util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Scope implements IScope {
   private List<Attachment> attachments;
   private final Queue<Breadcrumb> breadcrumbs;
   private Contexts contexts;
   private List<EventProcessor> eventProcessors;
   private Map<String, Object> extra;
   private List<String> fingerprint = new ArrayList<>();
   private SentryLevel level;
   private final SentryOptions options;
   private PropagationContext propagationContext;
   private final Object propagationContextLock;
   private SentryId replayId;
   private Request request;
   private String screen;
   private volatile Session session;
   private final Object sessionLock;
   private Map<String, String> tags = new ConcurrentHashMap();
   private ITransaction transaction;
   private final Object transactionLock;
   private String transactionName;
   private User user;

   private Scope(Scope var1) {
      this.extra = new ConcurrentHashMap();
      this.eventProcessors = new CopyOnWriteArrayList<>();
      this.sessionLock = new Object();
      this.transactionLock = new Object();
      this.propagationContextLock = new Object();
      this.contexts = new Contexts();
      this.attachments = new CopyOnWriteArrayList<>();
      this.replayId = SentryId.EMPTY_ID;
      this.transaction = var1.transaction;
      this.transactionName = var1.transactionName;
      this.session = var1.session;
      this.options = var1.options;
      this.level = var1.level;
      User var4 = var1.user;
      Object var5 = null;
      if (var4 != null) {
         var4 = new User(var4);
      } else {
         var4 = null;
      }

      this.user = var4;
      this.screen = var1.screen;
      this.replayId = var1.replayId;
      Request var6 = var1.request;
      Request var8 = (Request)var5;
      if (var6 != null) {
         var8 = new Request(var6);
      }

      this.request = var8;
      this.fingerprint = new ArrayList<>(var1.fingerprint);
      this.eventProcessors = new CopyOnWriteArrayList<>(var1.eventProcessors);
      Queue var9 = var1.breadcrumbs;
      int var2 = 0;
      var5 = var9.toArray(new Breadcrumb[0]);
      Queue var10 = this.createBreadcrumbsList(var1.options.getMaxBreadcrumbs());

      for (int var3 = ((Object[])var5).length; var2 < var3; var2++) {
         var10.add(new Breadcrumb((Breadcrumb)((Object[])var5)[var2]));
      }

      this.breadcrumbs = var10;
      var5 = var1.tags;
      ConcurrentHashMap var11 = new ConcurrentHashMap();

      for (var5 : var5.entrySet()) {
         if (var5 != null) {
            var11.put((String)var5.getKey(), (String)var5.getValue());
         }
      }

      this.tags = var11;
      var5 = var1.extra;
      ConcurrentHashMap var12 = new ConcurrentHashMap();

      for (var5 : var5.entrySet()) {
         if (var5 != null) {
            var12.put((String)var5.getKey(), var5.getValue());
         }
      }

      this.extra = var12;
      this.contexts = new Contexts(var1.contexts);
      this.attachments = new CopyOnWriteArrayList<>(var1.attachments);
      this.propagationContext = new PropagationContext(var1.propagationContext);
   }

   public Scope(SentryOptions var1) {
      this.extra = new ConcurrentHashMap();
      this.eventProcessors = new CopyOnWriteArrayList<>();
      this.sessionLock = new Object();
      this.transactionLock = new Object();
      this.propagationContextLock = new Object();
      this.contexts = new Contexts();
      this.attachments = new CopyOnWriteArrayList<>();
      this.replayId = SentryId.EMPTY_ID;
      var1 = Objects.requireNonNull(var1, "SentryOptions is required.");
      this.options = var1;
      this.breadcrumbs = this.createBreadcrumbsList(var1.getMaxBreadcrumbs());
      this.propagationContext = new PropagationContext();
   }

   private Queue<Breadcrumb> createBreadcrumbsList(int var1) {
      SynchronizedQueue var2;
      if (var1 > 0) {
         var2 = SynchronizedQueue.synchronizedQueue(new CircularFifoQueue(var1));
      } else {
         var2 = SynchronizedQueue.synchronizedQueue(new DisabledQueue());
      }

      return var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private Breadcrumb executeBeforeBreadcrumb(SentryOptions.BeforeBreadcrumbCallback var1, Breadcrumb var2, Hint var3) {
      label24:
      try {
         var6 = var1.execute(var2, var3);
      } catch (Throwable var5) {
         this.options
            .getLogger()
            .log(SentryLevel.ERROR, "The BeforeBreadcrumbCallback callback threw an exception. Exception details will be added to the breadcrumb.", var5);
         var6 = var2;
         if (var5.getMessage() != null) {
            var2.setData("sentry:message", var5.getMessage());
            var6 = var2;
         }
         break label24;
      }

      return var6;
   }

   @Override
   public void addAttachment(Attachment var1) {
      this.attachments.add(var1);
   }

   @Override
   public void addBreadcrumb(Breadcrumb var1) {
      this.addBreadcrumb(var1, null);
   }

   @Override
   public void addBreadcrumb(Breadcrumb var1, Hint var2) {
      if (var1 != null) {
         Hint var3 = var2;
         if (var2 == null) {
            var3 = new Hint();
         }

         SentryOptions.BeforeBreadcrumbCallback var4 = this.options.getBeforeBreadcrumb();
         Breadcrumb var6 = var1;
         if (var4 != null) {
            var6 = this.executeBeforeBreadcrumb(var4, var1, var3);
         }

         if (var6 != null) {
            this.breadcrumbs.add(var6);

            for (IScopeObserver var7 : this.options.getScopeObservers()) {
               var7.addBreadcrumb(var6);
               var7.setBreadcrumbs(this.breadcrumbs);
            }
         } else {
            this.options.getLogger().log(SentryLevel.INFO, "Breadcrumb was dropped by beforeBreadcrumb");
         }
      }
   }

   @Override
   public void addEventProcessor(EventProcessor var1) {
      this.eventProcessors.add(var1);
   }

   @Override
   public void clear() {
      this.level = null;
      this.user = null;
      this.request = null;
      this.screen = null;
      this.fingerprint.clear();
      this.clearBreadcrumbs();
      this.tags.clear();
      this.extra.clear();
      this.eventProcessors.clear();
      this.clearTransaction();
      this.clearAttachments();
   }

   @Override
   public void clearAttachments() {
      this.attachments.clear();
   }

   @Override
   public void clearBreadcrumbs() {
      this.breadcrumbs.clear();
      Iterator var1 = this.options.getScopeObservers().iterator();

      while (var1.hasNext()) {
         ((IScopeObserver)var1.next()).setBreadcrumbs(this.breadcrumbs);
      }
   }

   @Override
   public void clearSession() {
      this.session = null;
   }

   @Override
   public void clearTransaction() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/Scope.transactionLock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: aconst_null
      // 09: putfield io/sentry/Scope.transaction Lio/sentry/ITransaction;
      // 0c: aload 1
      // 0d: monitorexit
      // 0e: aload 0
      // 0f: aconst_null
      // 10: putfield io/sentry/Scope.transactionName Ljava/lang/String;
      // 13: aload 0
      // 14: getfield io/sentry/Scope.options Lio/sentry/SentryOptions;
      // 17: invokevirtual io/sentry/SentryOptions.getScopeObservers ()Ljava/util/List;
      // 1a: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 1f: astore 2
      // 20: aload 2
      // 21: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 26: ifeq 45
      // 29: aload 2
      // 2a: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 2f: checkcast io/sentry/IScopeObserver
      // 32: astore 1
      // 33: aload 1
      // 34: aconst_null
      // 35: invokeinterface io/sentry/IScopeObserver.setTransaction (Ljava/lang/String;)V 2
      // 3a: aload 1
      // 3b: aconst_null
      // 3c: aload 0
      // 3d: invokeinterface io/sentry/IScopeObserver.setTrace (Lio/sentry/SpanContext;Lio/sentry/IScope;)V 3
      // 42: goto 20
      // 45: return
      // 46: astore 2
      // 47: aload 1
      // 48: monitorexit
      // 49: aload 2
      // 4a: athrow
   }

   @Override
   public IScope clone() {
      return new Scope(this);
   }

   @Override
   public Session endSession() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/Scope.sessionLock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/Scope.session Lio/sentry/Session;
      // 0b: astore 3
      // 0c: aconst_null
      // 0d: astore 1
      // 0e: aload 3
      // 0f: ifnull 26
      // 12: aload 0
      // 13: getfield io/sentry/Scope.session Lio/sentry/Session;
      // 16: invokevirtual io/sentry/Session.end ()V
      // 19: aload 0
      // 1a: getfield io/sentry/Scope.session Lio/sentry/Session;
      // 1d: invokevirtual io/sentry/Session.clone ()Lio/sentry/Session;
      // 20: astore 1
      // 21: aload 0
      // 22: aconst_null
      // 23: putfield io/sentry/Scope.session Lio/sentry/Session;
      // 26: aload 2
      // 27: monitorexit
      // 28: aload 1
      // 29: areturn
      // 2a: astore 1
      // 2b: aload 2
      // 2c: monitorexit
      // 2d: aload 1
      // 2e: athrow
   }

   @Override
   public List<Attachment> getAttachments() {
      return new CopyOnWriteArrayList<>(this.attachments);
   }

   @Override
   public Queue<Breadcrumb> getBreadcrumbs() {
      return this.breadcrumbs;
   }

   @Override
   public Contexts getContexts() {
      return this.contexts;
   }

   @Override
   public List<EventProcessor> getEventProcessors() {
      return this.eventProcessors;
   }

   @Override
   public Map<String, Object> getExtras() {
      return this.extra;
   }

   @Override
   public List<String> getFingerprint() {
      return this.fingerprint;
   }

   @Override
   public SentryLevel getLevel() {
      return this.level;
   }

   @Override
   public SentryOptions getOptions() {
      return this.options;
   }

   @Override
   public PropagationContext getPropagationContext() {
      return this.propagationContext;
   }

   @Override
   public SentryId getReplayId() {
      return this.replayId;
   }

   @Override
   public Request getRequest() {
      return this.request;
   }

   @Override
   public String getScreen() {
      return this.screen;
   }

   @Override
   public Session getSession() {
      return this.session;
   }

   @Override
   public ISpan getSpan() {
      ITransaction var1 = this.transaction;
      if (var1 != null) {
         Span var2 = var1.getLatestActiveSpan();
         if (var2 != null) {
            return var2;
         }
      }

      return var1;
   }

   @Override
   public Map<String, String> getTags() {
      return CollectionUtils.newConcurrentHashMap(this.tags);
   }

   @Override
   public ITransaction getTransaction() {
      return this.transaction;
   }

   @Override
   public String getTransactionName() {
      ITransaction var1 = this.transaction;
      String var2;
      if (var1 != null) {
         var2 = var1.getName();
      } else {
         var2 = this.transactionName;
      }

      return var2;
   }

   @Override
   public User getUser() {
      return this.user;
   }

   @Override
   public void removeContexts(String var1) {
      this.contexts.remove(var1);
   }

   @Override
   public void removeExtra(String var1) {
      this.extra.remove(var1);

      for (IScopeObserver var3 : this.options.getScopeObservers()) {
         var3.removeExtra(var1);
         var3.setExtras(this.extra);
      }
   }

   @Override
   public void removeTag(String var1) {
      this.tags.remove(var1);

      for (IScopeObserver var2 : this.options.getScopeObservers()) {
         var2.removeTag(var1);
         var2.setTags(this.tags);
      }
   }

   @Override
   public void setContexts(String var1, Boolean var2) {
      HashMap var3 = new HashMap();
      var3.put("value", var2);
      this.setContexts(var1, var3);
   }

   @Override
   public void setContexts(String var1, Character var2) {
      HashMap var3 = new HashMap();
      var3.put("value", var2);
      this.setContexts(var1, var3);
   }

   @Override
   public void setContexts(String var1, Number var2) {
      HashMap var3 = new HashMap();
      var3.put("value", var2);
      this.setContexts(var1, var3);
   }

   @Override
   public void setContexts(String var1, Object var2) {
      this.contexts.put(var1, var2);
      Iterator var3 = this.options.getScopeObservers().iterator();

      while (var3.hasNext()) {
         ((IScopeObserver)var3.next()).setContexts(this.contexts);
      }
   }

   @Override
   public void setContexts(String var1, String var2) {
      HashMap var3 = new HashMap();
      var3.put("value", var2);
      this.setContexts(var1, var3);
   }

   @Override
   public void setContexts(String var1, Collection<?> var2) {
      HashMap var3 = new HashMap();
      var3.put("value", var2);
      this.setContexts(var1, var3);
   }

   @Override
   public void setContexts(String var1, Object[] var2) {
      HashMap var3 = new HashMap();
      var3.put("value", var2);
      this.setContexts(var1, var3);
   }

   @Override
   public void setExtra(String var1, String var2) {
      this.extra.put(var1, var2);

      for (IScopeObserver var4 : this.options.getScopeObservers()) {
         var4.setExtra(var1, var2);
         var4.setExtras(this.extra);
      }
   }

   @Override
   public void setFingerprint(List<String> var1) {
      if (var1 != null) {
         this.fingerprint = new ArrayList<>(var1);
         Iterator var2 = this.options.getScopeObservers().iterator();

         while (var2.hasNext()) {
            ((IScopeObserver)var2.next()).setFingerprint(var1);
         }
      }
   }

   @Override
   public void setLevel(SentryLevel var1) {
      this.level = var1;
      Iterator var2 = this.options.getScopeObservers().iterator();

      while (var2.hasNext()) {
         ((IScopeObserver)var2.next()).setLevel(var1);
      }
   }

   @Override
   public void setPropagationContext(PropagationContext var1) {
      this.propagationContext = var1;
      SpanContext var3 = var1.toSpanContext();
      Iterator var2 = this.options.getScopeObservers().iterator();

      while (var2.hasNext()) {
         ((IScopeObserver)var2.next()).setTrace(var3, this);
      }
   }

   @Override
   public void setReplayId(SentryId var1) {
      this.replayId = var1;
      Iterator var2 = this.options.getScopeObservers().iterator();

      while (var2.hasNext()) {
         ((IScopeObserver)var2.next()).setReplayId(var1);
      }
   }

   @Override
   public void setRequest(Request var1) {
      this.request = var1;
      Iterator var2 = this.options.getScopeObservers().iterator();

      while (var2.hasNext()) {
         ((IScopeObserver)var2.next()).setRequest(var1);
      }
   }

   @Override
   public void setScreen(String var1) {
      this.screen = var1;
      Contexts var4 = this.getContexts();
      App var3 = var4.getApp();
      App var2 = var3;
      if (var3 == null) {
         var2 = new App();
         var4.setApp(var2);
      }

      if (var1 == null) {
         var2.setViewNames(null);
      } else {
         ArrayList var6 = new ArrayList(1);
         var6.add(var1);
         var2.setViewNames(var6);
      }

      Iterator var5 = this.options.getScopeObservers().iterator();

      while (var5.hasNext()) {
         ((IScopeObserver)var5.next()).setContexts(var4);
      }
   }

   @Override
   public void setTag(String var1, String var2) {
      this.tags.put(var1, var2);

      for (IScopeObserver var3 : this.options.getScopeObservers()) {
         var3.setTag(var1, var2);
         var3.setTags(this.tags);
      }
   }

   @Override
   public void setTransaction(ITransaction param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/Scope.transactionLock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: aload 1
      // 09: putfield io/sentry/Scope.transaction Lio/sentry/ITransaction;
      // 0c: aload 0
      // 0d: getfield io/sentry/Scope.options Lio/sentry/SentryOptions;
      // 10: invokevirtual io/sentry/SentryOptions.getScopeObservers ()Ljava/util/List;
      // 13: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 18: astore 4
      // 1a: aload 4
      // 1c: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 21: ifeq 61
      // 24: aload 4
      // 26: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 2b: checkcast io/sentry/IScopeObserver
      // 2e: astore 3
      // 2f: aload 1
      // 30: ifnull 4f
      // 33: aload 3
      // 34: aload 1
      // 35: invokeinterface io/sentry/ITransaction.getName ()Ljava/lang/String; 1
      // 3a: invokeinterface io/sentry/IScopeObserver.setTransaction (Ljava/lang/String;)V 2
      // 3f: aload 3
      // 40: aload 1
      // 41: invokeinterface io/sentry/ITransaction.getSpanContext ()Lio/sentry/SpanContext; 1
      // 46: aload 0
      // 47: invokeinterface io/sentry/IScopeObserver.setTrace (Lio/sentry/SpanContext;Lio/sentry/IScope;)V 3
      // 4c: goto 1a
      // 4f: aload 3
      // 50: aconst_null
      // 51: invokeinterface io/sentry/IScopeObserver.setTransaction (Ljava/lang/String;)V 2
      // 56: aload 3
      // 57: aconst_null
      // 58: aload 0
      // 59: invokeinterface io/sentry/IScopeObserver.setTrace (Lio/sentry/SpanContext;Lio/sentry/IScope;)V 3
      // 5e: goto 1a
      // 61: aload 2
      // 62: monitorexit
      // 63: return
      // 64: astore 1
      // 65: aload 2
      // 66: monitorexit
      // 67: aload 1
      // 68: athrow
   }

   @Override
   public void setTransaction(String var1) {
      if (var1 != null) {
         ITransaction var2 = this.transaction;
         if (var2 != null) {
            var2.setName(var1, TransactionNameSource.CUSTOM);
         }

         this.transactionName = var1;
         Iterator var3 = this.options.getScopeObservers().iterator();

         while (var3.hasNext()) {
            ((IScopeObserver)var3.next()).setTransaction(var1);
         }
      } else {
         this.options.getLogger().log(SentryLevel.WARNING, "Transaction cannot be null");
      }
   }

   @Override
   public void setUser(User var1) {
      this.user = var1;
      Iterator var2 = this.options.getScopeObservers().iterator();

      while (var2.hasNext()) {
         ((IScopeObserver)var2.next()).setUser(var1);
      }
   }

   @Override
   public Scope.SessionPair startSession() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/Scope.sessionLock Ljava/lang/Object;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/Scope.session Lio/sentry/Session;
      // 0b: ifnull 15
      // 0e: aload 0
      // 0f: getfield io/sentry/Scope.session Lio/sentry/Session;
      // 12: invokevirtual io/sentry/Session.end ()V
      // 15: aload 0
      // 16: getfield io/sentry/Scope.session Lio/sentry/Session;
      // 19: astore 4
      // 1b: aload 0
      // 1c: getfield io/sentry/Scope.options Lio/sentry/SentryOptions;
      // 1f: invokevirtual io/sentry/SentryOptions.getRelease ()Ljava/lang/String;
      // 22: astore 5
      // 24: aconst_null
      // 25: astore 2
      // 26: aconst_null
      // 27: astore 1
      // 28: aload 5
      // 2a: ifnull 73
      // 2d: new io/sentry/Session
      // 30: astore 2
      // 31: aload 2
      // 32: aload 0
      // 33: getfield io/sentry/Scope.options Lio/sentry/SentryOptions;
      // 36: invokevirtual io/sentry/SentryOptions.getDistinctId ()Ljava/lang/String;
      // 39: aload 0
      // 3a: getfield io/sentry/Scope.user Lio/sentry/protocol/User;
      // 3d: aload 0
      // 3e: getfield io/sentry/Scope.options Lio/sentry/SentryOptions;
      // 41: invokevirtual io/sentry/SentryOptions.getEnvironment ()Ljava/lang/String;
      // 44: aload 0
      // 45: getfield io/sentry/Scope.options Lio/sentry/SentryOptions;
      // 48: invokevirtual io/sentry/SentryOptions.getRelease ()Ljava/lang/String;
      // 4b: invokespecial io/sentry/Session.<init> (Ljava/lang/String;Lio/sentry/protocol/User;Ljava/lang/String;Ljava/lang/String;)V
      // 4e: aload 0
      // 4f: aload 2
      // 50: putfield io/sentry/Scope.session Lio/sentry/Session;
      // 53: aload 4
      // 55: ifnull 5e
      // 58: aload 4
      // 5a: invokevirtual io/sentry/Session.clone ()Lio/sentry/Session;
      // 5d: astore 1
      // 5e: new io/sentry/Scope$SessionPair
      // 61: astore 2
      // 62: aload 2
      // 63: aload 0
      // 64: getfield io/sentry/Scope.session Lio/sentry/Session;
      // 67: invokevirtual io/sentry/Session.clone ()Lio/sentry/Session;
      // 6a: aload 1
      // 6b: invokespecial io/sentry/Scope$SessionPair.<init> (Lio/sentry/Session;Lio/sentry/Session;)V
      // 6e: aload 2
      // 6f: astore 1
      // 70: goto 8b
      // 73: aload 0
      // 74: getfield io/sentry/Scope.options Lio/sentry/SentryOptions;
      // 77: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 7a: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 7d: ldc_w "Release is not set on SentryOptions. Session could not be started"
      // 80: bipush 0
      // 81: anewarray 4
      // 84: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 89: aload 2
      // 8a: astore 1
      // 8b: aload 3
      // 8c: monitorexit
      // 8d: aload 1
      // 8e: areturn
      // 8f: astore 1
      // 90: aload 3
      // 91: monitorexit
      // 92: aload 1
      // 93: athrow
   }

   @Override
   public PropagationContext withPropagationContext(Scope.IWithPropagationContext param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/Scope.propagationContextLock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 1
      // 08: aload 0
      // 09: getfield io/sentry/Scope.propagationContext Lio/sentry/PropagationContext;
      // 0c: invokeinterface io/sentry/Scope$IWithPropagationContext.accept (Lio/sentry/PropagationContext;)V 2
      // 11: new io/sentry/PropagationContext
      // 14: astore 1
      // 15: aload 1
      // 16: aload 0
      // 17: getfield io/sentry/Scope.propagationContext Lio/sentry/PropagationContext;
      // 1a: invokespecial io/sentry/PropagationContext.<init> (Lio/sentry/PropagationContext;)V
      // 1d: aload 2
      // 1e: monitorexit
      // 1f: aload 1
      // 20: areturn
      // 21: astore 1
      // 22: aload 2
      // 23: monitorexit
      // 24: aload 1
      // 25: athrow
   }

   @Override
   public Session withSession(Scope.IWithSession param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/Scope.sessionLock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 1
      // 08: aload 0
      // 09: getfield io/sentry/Scope.session Lio/sentry/Session;
      // 0c: invokeinterface io/sentry/Scope$IWithSession.accept (Lio/sentry/Session;)V 2
      // 11: aload 0
      // 12: getfield io/sentry/Scope.session Lio/sentry/Session;
      // 15: ifnull 23
      // 18: aload 0
      // 19: getfield io/sentry/Scope.session Lio/sentry/Session;
      // 1c: invokevirtual io/sentry/Session.clone ()Lio/sentry/Session;
      // 1f: astore 1
      // 20: goto 25
      // 23: aconst_null
      // 24: astore 1
      // 25: aload 2
      // 26: monitorexit
      // 27: aload 1
      // 28: areturn
      // 29: astore 1
      // 2a: aload 2
      // 2b: monitorexit
      // 2c: aload 1
      // 2d: athrow
   }

   @Override
   public void withTransaction(Scope.IWithTransaction param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/Scope.transactionLock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 1
      // 08: aload 0
      // 09: getfield io/sentry/Scope.transaction Lio/sentry/ITransaction;
      // 0c: invokeinterface io/sentry/Scope$IWithTransaction.accept (Lio/sentry/ITransaction;)V 2
      // 11: aload 2
      // 12: monitorexit
      // 13: return
      // 14: astore 1
      // 15: aload 2
      // 16: monitorexit
      // 17: aload 1
      // 18: athrow
   }

   public interface IWithPropagationContext {
      void accept(PropagationContext var1);
   }

   interface IWithSession {
      void accept(Session var1);
   }

   public interface IWithTransaction {
      void accept(ITransaction var1);
   }

   static final class SessionPair {
      private final Session current;
      private final Session previous;

      public SessionPair(Session var1, Session var2) {
         this.current = var1;
         this.previous = var2;
      }

      public Session getCurrent() {
         return this.current;
      }

      public Session getPrevious() {
         return this.previous;
      }
   }
}
