package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.util.CollectionUtils;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Mechanism implements JsonUnknown, JsonSerializable {
   private Map<String, Object> data;
   private String description;
   private Boolean handled;
   private String helpLink;
   private Map<String, Object> meta;
   private Boolean synthetic;
   private final transient Thread thread;
   private String type;
   private Map<String, Object> unknown;

   public Mechanism() {
      this(null);
   }

   public Mechanism(Thread var1) {
      this.thread = var1;
   }

   public Map<String, Object> getData() {
      return this.data;
   }

   public String getDescription() {
      return this.description;
   }

   public String getHelpLink() {
      return this.helpLink;
   }

   public Map<String, Object> getMeta() {
      return this.meta;
   }

   public Boolean getSynthetic() {
      return this.synthetic;
   }

   Thread getThread() {
      return this.thread;
   }

   public String getType() {
      return this.type;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public Boolean isHandled() {
      return this.handled;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.type != null) {
         var1.name("type").value(this.type);
      }

      if (this.description != null) {
         var1.name("description").value(this.description);
      }

      if (this.helpLink != null) {
         var1.name("help_link").value(this.helpLink);
      }

      if (this.handled != null) {
         var1.name("handled").value(this.handled);
      }

      if (this.meta != null) {
         var1.name("meta").value(var2, this.meta);
      }

      if (this.data != null) {
         var1.name("data").value(var2, this.data);
      }

      if (this.synthetic != null) {
         var1.name("synthetic").value(this.synthetic);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            var3 = (Map)this.unknown.get(var4);
            var1.name(var4).value(var2, var3);
         }
      }

      var1.endObject();
   }

   public void setData(Map<String, Object> var1) {
      this.data = CollectionUtils.newHashMap(var1);
   }

   public void setDescription(String var1) {
      this.description = var1;
   }

   public void setHandled(Boolean var1) {
      this.handled = var1;
   }

   public void setHelpLink(String var1) {
      this.helpLink = var1;
   }

   public void setMeta(Map<String, Object> var1) {
      this.meta = CollectionUtils.newHashMap(var1);
   }

   public void setSynthetic(Boolean var1) {
      this.synthetic = var1;
   }

   public void setType(String var1) {
      this.type = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<Mechanism> {
      public Mechanism deserialize(ObjectReader var1, ILogger var2) throws Exception {
         Mechanism var7 = new Mechanism();
         var1.beginObject();
         HashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1724546052:
                  if (var8.equals("description")) {
                     var3 = 0;
                  }
                  break;
               case 3076010:
                  if (var8.equals("data")) {
                     var3 = 1;
                  }
                  break;
               case 3347973:
                  if (var8.equals("meta")) {
                     var3 = 2;
                  }
                  break;
               case 3575610:
                  if (var8.equals("type")) {
                     var3 = 3;
                  }
                  break;
               case 692803388:
                  if (var8.equals("handled")) {
                     var3 = 4;
                  }
                  break;
               case 989128517:
                  if (var8.equals("synthetic")) {
                     var3 = 5;
                  }
                  break;
               case 1297152568:
                  if (var8.equals("help_link")) {
                     var3 = 6;
                  }
            }

            switch (var3) {
               case 0:
                  var7.description = var1.nextStringOrNull();
                  break;
               case 1:
                  var7.data = CollectionUtils.newConcurrentHashMap((Map<String, Object>)var1.nextObjectOrNull());
                  break;
               case 2:
                  var7.meta = CollectionUtils.newConcurrentHashMap((Map<String, Object>)var1.nextObjectOrNull());
                  break;
               case 3:
                  var7.type = var1.nextStringOrNull();
                  break;
               case 4:
                  var7.handled = var1.nextBooleanOrNull();
                  break;
               case 5:
                  var7.synthetic = var1.nextBooleanOrNull();
                  break;
               case 6:
                  var7.helpLink = var1.nextStringOrNull();
                  break;
               default:
                  HashMap var6 = var5;
                  if (var5 == null) {
                     var6 = new HashMap();
                  }

                  var1.nextUnknown(var2, var6, var8);
                  var5 = var6;
            }
         }

         var1.endObject();
         var7.setUnknown(var5);
         return var7;
      }
   }

   public static final class JsonKeys {
      public static final String DATA = "data";
      public static final String DESCRIPTION = "description";
      public static final String HANDLED = "handled";
      public static final String HELP_LINK = "help_link";
      public static final String META = "meta";
      public static final String SYNTHETIC = "synthetic";
      public static final String TYPE = "type";
   }
}
