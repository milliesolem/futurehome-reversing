package io.sentry.cache;

import io.sentry.Breadcrumb;
import io.sentry.IScope;
import io.sentry.ISentryExecutorService;
import io.sentry.JsonDeserializer;
import io.sentry.ScopeObserverAdapter;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.SpanContext;
import io.sentry.protocol.Contexts;
import io.sentry.protocol.Request;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.User;
import java.util.Collection;
import java.util.Map;

public final class PersistingScopeObserver extends ScopeObserverAdapter {
   public static final String BREADCRUMBS_FILENAME = "breadcrumbs.json";
   public static final String CONTEXTS_FILENAME = "contexts.json";
   public static final String EXTRAS_FILENAME = "extras.json";
   public static final String FINGERPRINT_FILENAME = "fingerprint.json";
   public static final String LEVEL_FILENAME = "level.json";
   public static final String REPLAY_FILENAME = "replay.json";
   public static final String REQUEST_FILENAME = "request.json";
   public static final String SCOPE_CACHE = ".scope-cache";
   public static final String TAGS_FILENAME = "tags.json";
   public static final String TRACE_FILENAME = "trace.json";
   public static final String TRANSACTION_FILENAME = "transaction.json";
   public static final String USER_FILENAME = "user.json";
   private final SentryOptions options;

   public PersistingScopeObserver(SentryOptions var1) {
      this.options = var1;
   }

   private void delete(String var1) {
      CacheUtils.delete(this.options, ".scope-cache", var1);
   }

   public static <T> T read(SentryOptions var0, String var1, Class<T> var2) {
      return read(var0, var1, var2, null);
   }

   public static <T, R> T read(SentryOptions var0, String var1, Class<T> var2, JsonDeserializer<R> var3) {
      return CacheUtils.read(var0, ".scope-cache", var1, var2, var3);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void serializeToDisk(Runnable var1) {
      if (Thread.currentThread().getName().contains("SentryExecutor")) {
         var1.run();
      } else {
         try {
            ISentryExecutorService var3 = this.options.getExecutorService();
            PersistingScopeObserver$$ExternalSyntheticLambda6 var2 = new PersistingScopeObserver$$ExternalSyntheticLambda6(this, var1);
            var3.submit(var2);
         } catch (Throwable var5) {
            this.options.getLogger().log(SentryLevel.ERROR, "Serialization task could not be scheduled", var5);
            return;
         }
      }
   }

   public static <T> void store(SentryOptions var0, T var1, String var2) {
      CacheUtils.store(var0, var1, ".scope-cache", var2);
   }

   private <T> void store(T var1, String var2) {
      store(this.options, var1, var2);
   }

   @Override
   public void setBreadcrumbs(Collection<Breadcrumb> var1) {
      this.serializeToDisk(new PersistingScopeObserver$$ExternalSyntheticLambda2(this, var1));
   }

   @Override
   public void setContexts(Contexts var1) {
      this.serializeToDisk(new PersistingScopeObserver$$ExternalSyntheticLambda10(this, var1));
   }

   @Override
   public void setExtras(Map<String, Object> var1) {
      this.serializeToDisk(new PersistingScopeObserver$$ExternalSyntheticLambda11(this, var1));
   }

   @Override
   public void setFingerprint(Collection<String> var1) {
      this.serializeToDisk(new PersistingScopeObserver$$ExternalSyntheticLambda9(this, var1));
   }

   @Override
   public void setLevel(SentryLevel var1) {
      this.serializeToDisk(new PersistingScopeObserver$$ExternalSyntheticLambda5(this, var1));
   }

   @Override
   public void setReplayId(SentryId var1) {
      this.serializeToDisk(new PersistingScopeObserver$$ExternalSyntheticLambda0(this, var1));
   }

   @Override
   public void setRequest(Request var1) {
      this.serializeToDisk(new PersistingScopeObserver$$ExternalSyntheticLambda8(this, var1));
   }

   @Override
   public void setTags(Map<String, String> var1) {
      this.serializeToDisk(new PersistingScopeObserver$$ExternalSyntheticLambda7(this, var1));
   }

   @Override
   public void setTrace(SpanContext var1, IScope var2) {
      this.serializeToDisk(new PersistingScopeObserver$$ExternalSyntheticLambda1(this, var1, var2));
   }

   @Override
   public void setTransaction(String var1) {
      this.serializeToDisk(new PersistingScopeObserver$$ExternalSyntheticLambda3(this, var1));
   }

   @Override
   public void setUser(User var1) {
      this.serializeToDisk(new PersistingScopeObserver$$ExternalSyntheticLambda4(this, var1));
   }
}
