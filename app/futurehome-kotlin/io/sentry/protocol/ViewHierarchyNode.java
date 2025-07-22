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
import java.util.List;
import java.util.Map;

public final class ViewHierarchyNode implements JsonUnknown, JsonSerializable {
   private Double alpha;
   private List<ViewHierarchyNode> children;
   private Double height;
   private String identifier;
   private String renderingSystem;
   private String tag;
   private String type;
   private Map<String, Object> unknown;
   private String visibility;
   private Double width;
   private Double x;
   private Double y;

   public Double getAlpha() {
      return this.alpha;
   }

   public List<ViewHierarchyNode> getChildren() {
      return this.children;
   }

   public Double getHeight() {
      return this.height;
   }

   public String getIdentifier() {
      return this.identifier;
   }

   public String getRenderingSystem() {
      return this.renderingSystem;
   }

   public String getTag() {
      return this.tag;
   }

   public String getType() {
      return this.type;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public String getVisibility() {
      return this.visibility;
   }

   public Double getWidth() {
      return this.width;
   }

   public Double getX() {
      return this.x;
   }

   public Double getY() {
      return this.y;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.renderingSystem != null) {
         var1.name("rendering_system").value(this.renderingSystem);
      }

      if (this.type != null) {
         var1.name("type").value(this.type);
      }

      if (this.identifier != null) {
         var1.name("identifier").value(this.identifier);
      }

      if (this.tag != null) {
         var1.name("tag").value(this.tag);
      }

      if (this.width != null) {
         var1.name("width").value(this.width);
      }

      if (this.height != null) {
         var1.name("height").value(this.height);
      }

      if (this.x != null) {
         var1.name("x").value(this.x);
      }

      if (this.y != null) {
         var1.name("y").value(this.y);
      }

      if (this.visibility != null) {
         var1.name("visibility").value(this.visibility);
      }

      if (this.alpha != null) {
         var1.name("alpha").value(this.alpha);
      }

      List var3 = this.children;
      if (var3 != null && !var3.isEmpty()) {
         var1.name("children").value(var2, this.children);
      }

      Map var6 = this.unknown;
      if (var6 != null) {
         for (String var7 : var6.keySet()) {
            Object var5 = this.unknown.get(var7);
            var1.name(var7).value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setAlpha(Double var1) {
      this.alpha = var1;
   }

   public void setChildren(List<ViewHierarchyNode> var1) {
      this.children = var1;
   }

   public void setHeight(Double var1) {
      this.height = var1;
   }

   public void setIdentifier(String var1) {
      this.identifier = var1;
   }

   public void setRenderingSystem(String var1) {
      this.renderingSystem = var1;
   }

   public void setTag(String var1) {
      this.tag = var1;
   }

   public void setType(String var1) {
      this.type = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setVisibility(String var1) {
      this.visibility = var1;
   }

   public void setWidth(Double var1) {
      this.width = var1;
   }

   public void setX(Double var1) {
      this.x = var1;
   }

   public void setY(Double var1) {
      this.y = var1;
   }

   public static final class Deserializer implements JsonDeserializer<ViewHierarchyNode> {
      public ViewHierarchyNode deserialize(ObjectReader var1, ILogger var2) throws Exception {
         ViewHierarchyNode var7 = new ViewHierarchyNode();
         var1.beginObject();
         HashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1784982718:
                  if (var8.equals("rendering_system")) {
                     var3 = 0;
                  }
                  break;
               case -1618432855:
                  if (var8.equals("identifier")) {
                     var3 = 1;
                  }
                  break;
               case -1221029593:
                  if (var8.equals("height")) {
                     var3 = 2;
                  }
                  break;
               case 120:
                  if (var8.equals("x")) {
                     var3 = 3;
                  }
                  break;
               case 121:
                  if (var8.equals("y")) {
                     var3 = 4;
                  }
                  break;
               case 114586:
                  if (var8.equals("tag")) {
                     var3 = 5;
                  }
                  break;
               case 3575610:
                  if (var8.equals("type")) {
                     var3 = 6;
                  }
                  break;
               case 92909918:
                  if (var8.equals("alpha")) {
                     var3 = 7;
                  }
                  break;
               case 113126854:
                  if (var8.equals("width")) {
                     var3 = 8;
                  }
                  break;
               case 1659526655:
                  if (var8.equals("children")) {
                     var3 = 9;
                  }
                  break;
               case 1941332754:
                  if (var8.equals("visibility")) {
                     var3 = 10;
                  }
            }

            switch (var3) {
               case 0:
                  var7.renderingSystem = var1.nextStringOrNull();
                  break;
               case 1:
                  var7.identifier = var1.nextStringOrNull();
                  break;
               case 2:
                  var7.height = var1.nextDoubleOrNull();
                  break;
               case 3:
                  var7.x = var1.nextDoubleOrNull();
                  break;
               case 4:
                  var7.y = var1.nextDoubleOrNull();
                  break;
               case 5:
                  var7.tag = var1.nextStringOrNull();
                  break;
               case 6:
                  var7.type = var1.nextStringOrNull();
                  break;
               case 7:
                  var7.alpha = var1.nextDoubleOrNull();
                  break;
               case 8:
                  var7.width = var1.nextDoubleOrNull();
                  break;
               case 9:
                  var7.children = var1.nextListOrNull(var2, this);
                  break;
               case 10:
                  var7.visibility = var1.nextStringOrNull();
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
      public static final String ALPHA = "alpha";
      public static final String CHILDREN = "children";
      public static final String HEIGHT = "height";
      public static final String IDENTIFIER = "identifier";
      public static final String RENDERING_SYSTEM = "rendering_system";
      public static final String TAG = "tag";
      public static final String TYPE = "type";
      public static final String VISIBILITY = "visibility";
      public static final String WIDTH = "width";
      public static final String X = "x";
      public static final String Y = "y";
   }
}
