package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class DebugImage implements JsonUnknown, JsonSerializable {
   public static final String JVM = "jvm";
   public static final String PROGUARD = "proguard";
   private String arch;
   private String codeFile;
   private String codeId;
   private String debugFile;
   private String debugId;
   private String imageAddr;
   private Long imageSize;
   private String type;
   private Map<String, Object> unknown;
   private String uuid;

   public String getArch() {
      return this.arch;
   }

   public String getCodeFile() {
      return this.codeFile;
   }

   public String getCodeId() {
      return this.codeId;
   }

   public String getDebugFile() {
      return this.debugFile;
   }

   public String getDebugId() {
      return this.debugId;
   }

   public String getImageAddr() {
      return this.imageAddr;
   }

   public Long getImageSize() {
      return this.imageSize;
   }

   public String getType() {
      return this.type;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public String getUuid() {
      return this.uuid;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.uuid != null) {
         var1.name("uuid").value(this.uuid);
      }

      if (this.type != null) {
         var1.name("type").value(this.type);
      }

      if (this.debugId != null) {
         var1.name("debug_id").value(this.debugId);
      }

      if (this.debugFile != null) {
         var1.name("debug_file").value(this.debugFile);
      }

      if (this.codeId != null) {
         var1.name("code_id").value(this.codeId);
      }

      if (this.codeFile != null) {
         var1.name("code_file").value(this.codeFile);
      }

      if (this.imageAddr != null) {
         var1.name("image_addr").value(this.imageAddr);
      }

      if (this.imageSize != null) {
         var1.name("image_size").value(this.imageSize);
      }

      if (this.arch != null) {
         var1.name("arch").value(this.arch);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            Object var5 = this.unknown.get(var4);
            var1.name(var4).value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setArch(String var1) {
      this.arch = var1;
   }

   public void setCodeFile(String var1) {
      this.codeFile = var1;
   }

   public void setCodeId(String var1) {
      this.codeId = var1;
   }

   public void setDebugFile(String var1) {
      this.debugFile = var1;
   }

   public void setDebugId(String var1) {
      this.debugId = var1;
   }

   public void setImageAddr(String var1) {
      this.imageAddr = var1;
   }

   public void setImageSize(long var1) {
      this.imageSize = var1;
   }

   public void setImageSize(Long var1) {
      this.imageSize = var1;
   }

   public void setType(String var1) {
      this.type = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setUuid(String var1) {
      this.uuid = var1;
   }

   public static final class Deserializer implements JsonDeserializer<DebugImage> {
      public DebugImage deserialize(ObjectReader var1, ILogger var2) throws Exception {
         DebugImage var7 = new DebugImage();
         var1.beginObject();
         HashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1840639000:
                  if (var8.equals("debug_file")) {
                     var3 = 0;
                  }
                  break;
               case -1443345323:
                  if (var8.equals("image_addr")) {
                     var3 = 1;
                  }
                  break;
               case -1442803611:
                  if (var8.equals("image_size")) {
                     var3 = 2;
                  }
                  break;
               case -1127437170:
                  if (var8.equals("code_file")) {
                     var3 = 3;
                  }
                  break;
               case 3002454:
                  if (var8.equals("arch")) {
                     var3 = 4;
                  }
                  break;
               case 3575610:
                  if (var8.equals("type")) {
                     var3 = 5;
                  }
                  break;
               case 3601339:
                  if (var8.equals("uuid")) {
                     var3 = 6;
                  }
                  break;
               case 547804807:
                  if (var8.equals("debug_id")) {
                     var3 = 7;
                  }
                  break;
               case 941842605:
                  if (var8.equals("code_id")) {
                     var3 = 8;
                  }
            }

            switch (var3) {
               case 0:
                  var7.debugFile = var1.nextStringOrNull();
                  break;
               case 1:
                  var7.imageAddr = var1.nextStringOrNull();
                  break;
               case 2:
                  var7.imageSize = var1.nextLongOrNull();
                  break;
               case 3:
                  var7.codeFile = var1.nextStringOrNull();
                  break;
               case 4:
                  var7.arch = var1.nextStringOrNull();
                  break;
               case 5:
                  var7.type = var1.nextStringOrNull();
                  break;
               case 6:
                  var7.uuid = var1.nextStringOrNull();
                  break;
               case 7:
                  var7.debugId = var1.nextStringOrNull();
                  break;
               case 8:
                  var7.codeId = var1.nextStringOrNull();
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
      public static final String ARCH = "arch";
      public static final String CODE_FILE = "code_file";
      public static final String CODE_ID = "code_id";
      public static final String DEBUG_FILE = "debug_file";
      public static final String DEBUG_ID = "debug_id";
      public static final String IMAGE_ADDR = "image_addr";
      public static final String IMAGE_SIZE = "image_size";
      public static final String TYPE = "type";
      public static final String UUID = "uuid";
   }
}
