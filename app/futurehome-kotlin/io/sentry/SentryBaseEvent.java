package io.sentry;

import io.sentry.exception.ExceptionMechanismException;
import io.sentry.protocol.Contexts;
import io.sentry.protocol.DebugMeta;
import io.sentry.protocol.Request;
import io.sentry.protocol.SdkVersion;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.User;
import io.sentry.util.CollectionUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SentryBaseEvent {
   public static final String DEFAULT_PLATFORM = "java";
   private List<Breadcrumb> breadcrumbs;
   private final Contexts contexts = new Contexts();
   private DebugMeta debugMeta;
   private String dist;
   private String environment;
   private SentryId eventId;
   private Map<String, Object> extra;
   private String platform;
   private String release;
   private Request request;
   private SdkVersion sdk;
   private String serverName;
   private Map<String, String> tags;
   protected transient Throwable throwable;
   private User user;

   protected SentryBaseEvent() {
      this(new SentryId());
   }

   protected SentryBaseEvent(SentryId var1) {
      this.eventId = var1;
   }

   public void addBreadcrumb(Breadcrumb var1) {
      if (this.breadcrumbs == null) {
         this.breadcrumbs = new ArrayList<>();
      }

      this.breadcrumbs.add(var1);
   }

   public void addBreadcrumb(String var1) {
      this.addBreadcrumb(new Breadcrumb(var1));
   }

   public List<Breadcrumb> getBreadcrumbs() {
      return this.breadcrumbs;
   }

   public Contexts getContexts() {
      return this.contexts;
   }

   public DebugMeta getDebugMeta() {
      return this.debugMeta;
   }

   public String getDist() {
      return this.dist;
   }

   public String getEnvironment() {
      return this.environment;
   }

   public SentryId getEventId() {
      return this.eventId;
   }

   public Object getExtra(String var1) {
      Map var2 = this.extra;
      return var2 != null ? var2.get(var1) : null;
   }

   public Map<String, Object> getExtras() {
      return this.extra;
   }

   public String getPlatform() {
      return this.platform;
   }

   public String getRelease() {
      return this.release;
   }

   public Request getRequest() {
      return this.request;
   }

   public SdkVersion getSdk() {
      return this.sdk;
   }

   public String getServerName() {
      return this.serverName;
   }

   public String getTag(String var1) {
      Map var2 = this.tags;
      return var2 != null ? (String)var2.get(var1) : null;
   }

   public Map<String, String> getTags() {
      return this.tags;
   }

   public Throwable getThrowable() {
      Throwable var2 = this.throwable;
      Throwable var1 = var2;
      if (var2 instanceof ExceptionMechanismException) {
         var1 = ((ExceptionMechanismException)var2).getThrowable();
      }

      return var1;
   }

   public Throwable getThrowableMechanism() {
      return this.throwable;
   }

   public User getUser() {
      return this.user;
   }

   public void removeExtra(String var1) {
      Map var2 = this.extra;
      if (var2 != null) {
         var2.remove(var1);
      }
   }

   public void removeTag(String var1) {
      Map var2 = this.tags;
      if (var2 != null) {
         var2.remove(var1);
      }
   }

   public void setBreadcrumbs(List<Breadcrumb> var1) {
      this.breadcrumbs = CollectionUtils.newArrayList(var1);
   }

   public void setDebugMeta(DebugMeta var1) {
      this.debugMeta = var1;
   }

   public void setDist(String var1) {
      this.dist = var1;
   }

   public void setEnvironment(String var1) {
      this.environment = var1;
   }

   public void setEventId(SentryId var1) {
      this.eventId = var1;
   }

   public void setExtra(String var1, Object var2) {
      if (this.extra == null) {
         this.extra = new HashMap<>();
      }

      this.extra.put(var1, var2);
   }

   public void setExtras(Map<String, Object> var1) {
      this.extra = CollectionUtils.newHashMap(var1);
   }

   public void setPlatform(String var1) {
      this.platform = var1;
   }

   public void setRelease(String var1) {
      this.release = var1;
   }

   public void setRequest(Request var1) {
      this.request = var1;
   }

   public void setSdk(SdkVersion var1) {
      this.sdk = var1;
   }

   public void setServerName(String var1) {
      this.serverName = var1;
   }

   public void setTag(String var1, String var2) {
      if (this.tags == null) {
         this.tags = new HashMap<>();
      }

      this.tags.put(var1, var2);
   }

   public void setTags(Map<String, String> var1) {
      this.tags = CollectionUtils.newHashMap(var1);
   }

   public void setThrowable(Throwable var1) {
      this.throwable = var1;
   }

   public void setUser(User var1) {
      this.user = var1;
   }

   public static final class Deserializer {
      public boolean deserializeValue(SentryBaseEvent var1, String var2, ObjectReader var3, ILogger var4) throws Exception {
         var2.hashCode();
         int var6 = var2.hashCode();
         byte var5 = -1;
         switch (var6) {
            case -1840434063:
               if (var2.equals("debug_meta")) {
                  var5 = 0;
               }
               break;
            case -758770169:
               if (var2.equals("server_name")) {
                  var5 = 1;
               }
               break;
            case -567312220:
               if (var2.equals("contexts")) {
                  var5 = 2;
               }
               break;
            case -85904877:
               if (var2.equals("environment")) {
                  var5 = 3;
               }
               break;
            case -51457840:
               if (var2.equals("breadcrumbs")) {
                  var5 = 4;
               }
               break;
            case 113722:
               if (var2.equals("sdk")) {
                  var5 = 5;
               }
               break;
            case 3083686:
               if (var2.equals("dist")) {
                  var5 = 6;
               }
               break;
            case 3552281:
               if (var2.equals("tags")) {
                  var5 = 7;
               }
               break;
            case 3599307:
               if (var2.equals("user")) {
                  var5 = 8;
               }
               break;
            case 96965648:
               if (var2.equals("extra")) {
                  var5 = 9;
               }
               break;
            case 278118624:
               if (var2.equals("event_id")) {
                  var5 = 10;
               }
               break;
            case 1090594823:
               if (var2.equals("release")) {
                  var5 = 11;
               }
               break;
            case 1095692943:
               if (var2.equals("request")) {
                  var5 = 12;
               }
               break;
            case 1874684019:
               if (var2.equals("platform")) {
                  var5 = 13;
               }
         }

         switch (var5) {
            case 0:
               var1.debugMeta = var3.nextOrNull(var4, new DebugMeta.Deserializer());
               return true;
            case 1:
               var1.serverName = var3.nextStringOrNull();
               return true;
            case 2:
               Contexts var7 = new Contexts.Deserializer().deserialize(var3, var4);
               var1.contexts.putAll(var7);
               return true;
            case 3:
               var1.environment = var3.nextStringOrNull();
               return true;
            case 4:
               var1.breadcrumbs = var3.nextListOrNull(var4, new Breadcrumb.Deserializer());
               return true;
            case 5:
               var1.sdk = var3.nextOrNull(var4, new SdkVersion.Deserializer());
               return true;
            case 6:
               var1.dist = var3.nextStringOrNull();
               return true;
            case 7:
               var1.tags = CollectionUtils.newConcurrentHashMap((Map<String, String>)var3.nextObjectOrNull());
               return true;
            case 8:
               var1.user = var3.nextOrNull(var4, new User.Deserializer());
               return true;
            case 9:
               var1.extra = CollectionUtils.newConcurrentHashMap((Map<String, Object>)var3.nextObjectOrNull());
               return true;
            case 10:
               var1.eventId = var3.nextOrNull(var4, new SentryId.Deserializer());
               return true;
            case 11:
               var1.release = var3.nextStringOrNull();
               return true;
            case 12:
               var1.request = var3.nextOrNull(var4, new Request.Deserializer());
               return true;
            case 13:
               var1.platform = var3.nextStringOrNull();
               return true;
            default:
               return false;
         }
      }
   }

   public static final class JsonKeys {
      public static final String BREADCRUMBS = "breadcrumbs";
      public static final String CONTEXTS = "contexts";
      public static final String DEBUG_META = "debug_meta";
      public static final String DIST = "dist";
      public static final String ENVIRONMENT = "environment";
      public static final String EVENT_ID = "event_id";
      public static final String EXTRA = "extra";
      public static final String PLATFORM = "platform";
      public static final String RELEASE = "release";
      public static final String REQUEST = "request";
      public static final String SDK = "sdk";
      public static final String SERVER_NAME = "server_name";
      public static final String TAGS = "tags";
      public static final String USER = "user";
   }

   public static final class Serializer {
      public void serialize(SentryBaseEvent var1, ObjectWriter var2, ILogger var3) throws IOException {
         if (var1.eventId != null) {
            var2.name("event_id").value(var3, var1.eventId);
         }

         var2.name("contexts").value(var3, var1.contexts);
         if (var1.sdk != null) {
            var2.name("sdk").value(var3, var1.sdk);
         }

         if (var1.request != null) {
            var2.name("request").value(var3, var1.request);
         }

         if (var1.tags != null && !var1.tags.isEmpty()) {
            var2.name("tags").value(var3, var1.tags);
         }

         if (var1.release != null) {
            var2.name("release").value(var1.release);
         }

         if (var1.environment != null) {
            var2.name("environment").value(var1.environment);
         }

         if (var1.platform != null) {
            var2.name("platform").value(var1.platform);
         }

         if (var1.user != null) {
            var2.name("user").value(var3, var1.user);
         }

         if (var1.serverName != null) {
            var2.name("server_name").value(var1.serverName);
         }

         if (var1.dist != null) {
            var2.name("dist").value(var1.dist);
         }

         if (var1.breadcrumbs != null && !var1.breadcrumbs.isEmpty()) {
            var2.name("breadcrumbs").value(var3, var1.breadcrumbs);
         }

         if (var1.debugMeta != null) {
            var2.name("debug_meta").value(var3, var1.debugMeta);
         }

         if (var1.extra != null && !var1.extra.isEmpty()) {
            var2.name("extra").value(var3, var1.extra);
         }
      }
   }
}
