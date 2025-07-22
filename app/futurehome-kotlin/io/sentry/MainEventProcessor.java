package io.sentry;

import io.sentry.hints.AbnormalExit;
import io.sentry.hints.Cached;
import io.sentry.protocol.DebugImage;
import io.sentry.protocol.DebugMeta;
import io.sentry.protocol.SdkVersion;
import io.sentry.protocol.SentryException;
import io.sentry.protocol.SentryTransaction;
import io.sentry.protocol.User;
import io.sentry.util.HintUtils;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class MainEventProcessor implements EventProcessor, Closeable {
   private volatile HostnameCache hostnameCache = null;
   private final SentryOptions options;
   private final SentryExceptionFactory sentryExceptionFactory;
   private final SentryThreadFactory sentryThreadFactory;

   public MainEventProcessor(SentryOptions var1) {
      SentryOptions var2 = Objects.requireNonNull(var1, "The SentryOptions is required.");
      this.options = var2;
      SentryStackTraceFactory var3 = new SentryStackTraceFactory(var2);
      this.sentryExceptionFactory = new SentryExceptionFactory(var3);
      this.sentryThreadFactory = new SentryThreadFactory(var3, var2);
   }

   MainEventProcessor(SentryOptions var1, SentryThreadFactory var2, SentryExceptionFactory var3) {
      this.options = Objects.requireNonNull(var1, "The SentryOptions is required.");
      this.sentryThreadFactory = Objects.requireNonNull(var2, "The SentryThreadFactory is required.");
      this.sentryExceptionFactory = Objects.requireNonNull(var3, "The SentryExceptionFactory is required.");
   }

   private void ensureHostnameCache() {
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
      // 01: getfield io/sentry/MainEventProcessor.hostnameCache Lio/sentry/HostnameCache;
      // 04: ifnonnull 21
      // 07: aload 0
      // 08: monitorenter
      // 09: aload 0
      // 0a: getfield io/sentry/MainEventProcessor.hostnameCache Lio/sentry/HostnameCache;
      // 0d: ifnonnull 17
      // 10: aload 0
      // 11: invokestatic io/sentry/HostnameCache.getInstance ()Lio/sentry/HostnameCache;
      // 14: putfield io/sentry/MainEventProcessor.hostnameCache Lio/sentry/HostnameCache;
      // 17: aload 0
      // 18: monitorexit
      // 19: goto 21
      // 1c: astore 1
      // 1d: aload 0
      // 1e: monitorexit
      // 1f: aload 1
      // 20: athrow
      // 21: return
   }

   private boolean isCachedHint(Hint var1) {
      return HintUtils.hasType(var1, Cached.class);
   }

   private void mergeUser(SentryBaseEvent var1) {
      User var3 = var1.getUser();
      User var2 = var3;
      if (var3 == null) {
         var2 = new User();
         var1.setUser(var2);
      }

      if (var2.getIpAddress() == null && this.options.isSendDefaultPii()) {
         var2.setIpAddress("{{auto}}");
      }
   }

   private void processNonCachedEvent(SentryBaseEvent var1) {
      this.setRelease(var1);
      this.setEnvironment(var1);
      this.setServerName(var1);
      this.setDist(var1);
      this.setSdk(var1);
      this.setTags(var1);
      this.mergeUser(var1);
   }

   private void setCommons(SentryBaseEvent var1) {
      this.setPlatform(var1);
   }

   private void setDebugMeta(SentryBaseEvent var1) {
      ArrayList var4 = new ArrayList();
      if (this.options.getProguardUuid() != null) {
         DebugImage var2 = new DebugImage();
         var2.setType("proguard");
         var2.setUuid(this.options.getProguardUuid());
         var4.add(var2);
      }

      for (String var3 : this.options.getBundleIds()) {
         DebugImage var5 = new DebugImage();
         var5.setType("jvm");
         var5.setDebugId(var3);
         var4.add(var5);
      }

      if (!var4.isEmpty()) {
         DebugMeta var8 = var1.getDebugMeta();
         DebugMeta var7 = var8;
         if (var8 == null) {
            var7 = new DebugMeta();
         }

         if (var7.getImages() == null) {
            var7.setImages(var4);
         } else {
            var7.getImages().addAll(var4);
         }

         var1.setDebugMeta(var7);
      }
   }

   private void setDist(SentryBaseEvent var1) {
      if (var1.getDist() == null) {
         var1.setDist(this.options.getDist());
      }
   }

   private void setEnvironment(SentryBaseEvent var1) {
      if (var1.getEnvironment() == null) {
         var1.setEnvironment(this.options.getEnvironment());
      }
   }

   private void setExceptions(SentryEvent var1) {
      Throwable var2 = var1.getThrowableMechanism();
      if (var2 != null) {
         var1.setExceptions(this.sentryExceptionFactory.getSentryExceptions(var2));
      }
   }

   private void setModules(SentryEvent var1) {
      Map var2 = this.options.getModulesLoader().getOrLoadModules();
      if (var2 != null) {
         Map var3 = var1.getModules();
         if (var3 == null) {
            var1.setModules(var2);
         } else {
            var3.putAll(var2);
         }
      }
   }

   private void setPlatform(SentryBaseEvent var1) {
      if (var1.getPlatform() == null) {
         var1.setPlatform("java");
      }
   }

   private void setRelease(SentryBaseEvent var1) {
      if (var1.getRelease() == null) {
         var1.setRelease(this.options.getRelease());
      }
   }

   private void setSdk(SentryBaseEvent var1) {
      if (var1.getSdk() == null) {
         var1.setSdk(this.options.getSdkVersion());
      }
   }

   private void setServerName(SentryBaseEvent var1) {
      if (var1.getServerName() == null) {
         var1.setServerName(this.options.getServerName());
      }

      if (this.options.isAttachServerName() && var1.getServerName() == null) {
         this.ensureHostnameCache();
         if (this.hostnameCache != null) {
            var1.setServerName(this.hostnameCache.getHostname());
         }
      }
   }

   private void setTags(SentryBaseEvent var1) {
      if (var1.getTags() == null) {
         var1.setTags(new HashMap<>(this.options.getTags()));
      } else {
         for (Entry var2 : this.options.getTags().entrySet()) {
            if (!var1.getTags().containsKey(var2.getKey())) {
               var1.setTag((String)var2.getKey(), (String)var2.getValue());
            }
         }
      }
   }

   private void setThreads(SentryEvent var1, Hint var2) {
      if (var1.getThreads() == null) {
         List var7 = var1.getExceptions();
         Iterator var6 = null;
         ArrayList var4 = null;
         ArrayList var5 = var6;
         if (var7 != null) {
            var5 = var6;
            if (!var7.isEmpty()) {
               var6 = var7.iterator();

               while (true) {
                  var5 = var4;
                  if (!var6.hasNext()) {
                     break;
                  }

                  SentryException var8 = (SentryException)var6.next();
                  if (var8.getMechanism() != null && var8.getThreadId() != null) {
                     var5 = var4;
                     if (var4 == null) {
                        var5 = new ArrayList();
                     }

                     var5.add(var8.getThreadId());
                     var4 = var5;
                  }
               }
            }
         }

         if (this.options.isAttachThreads() || HintUtils.hasType(var2, AbnormalExit.class)) {
            Object var9 = HintUtils.getSentrySdkHint(var2);
            boolean var3;
            if (var9 instanceof AbnormalExit) {
               var3 = ((AbnormalExit)var9).ignoreCurrentThread();
            } else {
               var3 = false;
            }

            var1.setThreads(this.sentryThreadFactory.getCurrentThreads(var5, var3));
         } else if (this.options.isAttachStacktrace() && (var7 == null || var7.isEmpty()) && !this.isCachedHint(var2)) {
            var1.setThreads(this.sentryThreadFactory.getCurrentThread());
         }
      }
   }

   private boolean shouldApplyScopeData(SentryBaseEvent var1, Hint var2) {
      if (HintUtils.shouldApplyScopeData(var2)) {
         return true;
      } else {
         this.options
            .getLogger()
            .log(SentryLevel.DEBUG, "Event was cached so not applying data relevant to the current app execution/version: %s", var1.getEventId());
         return false;
      }
   }

   @Override
   public void close() throws IOException {
      if (this.hostnameCache != null) {
         this.hostnameCache.close();
      }
   }

   HostnameCache getHostnameCache() {
      return this.hostnameCache;
   }

   boolean isClosed() {
      return this.hostnameCache != null ? this.hostnameCache.isClosed() : true;
   }

   @Override
   public SentryEvent process(SentryEvent var1, Hint var2) {
      this.setCommons(var1);
      this.setExceptions(var1);
      this.setDebugMeta(var1);
      this.setModules(var1);
      if (this.shouldApplyScopeData(var1, var2)) {
         this.processNonCachedEvent(var1);
         this.setThreads(var1, var2);
      }

      return var1;
   }

   @Override
   public SentryReplayEvent process(SentryReplayEvent var1, Hint var2) {
      this.setCommons(var1);
      if (this.shouldApplyScopeData(var1, var2)) {
         this.processNonCachedEvent(var1);
         SdkVersion var3 = this.options.getSessionReplay().getSdkVersion();
         if (var3 != null) {
            var1.setSdk(var3);
         }
      }

      return var1;
   }

   @Override
   public SentryTransaction process(SentryTransaction var1, Hint var2) {
      this.setCommons(var1);
      this.setDebugMeta(var1);
      if (this.shouldApplyScopeData(var1, var2)) {
         this.processNonCachedEvent(var1);
      }

      return var1;
   }
}
