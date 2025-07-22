package io.sentry.android.replay

import io.sentry.Breadcrumb
import io.sentry.ReplayBreadcrumbConverter
import io.sentry.SentryLevel
import io.sentry.rrweb.RRWebBreadcrumbEvent
import io.sentry.rrweb.RRWebEvent
import io.sentry.rrweb.RRWebSpanEvent
import java.util.HashSet
import java.util.LinkedHashMap
import java.util.Map.Entry

public open class DefaultReplayBreadcrumbConverter : ReplayBreadcrumbConverter {
   private final var lastConnectivityState: String?

   @JvmStatic
   fun {
      val var0: HashSet = new HashSet();
      var0.add("status_code");
      var0.add("method");
      var0.add("response_content_length");
      var0.add("request_content_length");
      var0.add("http.response_content_length");
      var0.add("http.request_content_length");
      supportedNetworkData = var0;
   }

   private fun Breadcrumb.isValidForRRWebSpan(): Boolean {
      var var3: Any = var1.getData().get("url");
      if (var3 is java.lang.String) {
         var3 = var3 as java.lang.String;
      } else {
         var3 = null;
      }

      if (var3 as java.lang.CharSequence != null && (var3 as java.lang.CharSequence).length() != 0) {
         var3 = var1.getData();
         if (var3.containsKey("http.start_timestamp")) {
            val var4: java.util.Map = var1.getData();
            if (var4.containsKey("http.end_timestamp")) {
               return true;
            }
         }
      }

      return false;
   }

   private fun String.snakeToCamelCase(): String {
      return DefaultReplayBreadcrumbConverter.Companion.access$getSnakecasePattern(Companion).replace(var1, <unrepresentable>.INSTANCE);
   }

   private fun Breadcrumb.toRRWebSpanEvent(): RRWebSpanEvent {
      var var5: java.util.Map = (java.util.Map)var1.getData().get("http.start_timestamp");
      var var6: Any = var1.getData().get("http.end_timestamp");
      val var4: RRWebSpanEvent = new RRWebSpanEvent();
      var4.setTimestamp(var1.getTimestamp().getTime());
      var4.setOp("resource.http");
      var var7: Entry = (Entry)var1.getData().get("url");
      var4.setDescription(var7 as java.lang.String);
      var var2: Double;
      if (var5 is java.lang.Double) {
         var2 = (var5 as java.lang.Number).doubleValue();
      } else {
         var2 = (var5 as java.lang.Long).longValue();
      }

      var4.setStartTimestamp(var2 / 1000.0);
      if (var6 is java.lang.Double) {
         var2 = (var6 as java.lang.Number).doubleValue();
      } else {
         var2 = (var6 as java.lang.Long).longValue();
      }

      var4.setEndTimestamp(var2 / 1000.0);
      var5 = new LinkedHashMap();
      val var8: java.util.Map = var1.getData();

      for (var7 : var8.entrySet()) {
         var6 = var7.getKey() as java.lang.String;
         var7 = var7.getValue();
         if (supportedNetworkData.contains(var6)) {
            var5.put(
               this.snakeToCamelCase(
                  StringsKt.substringAfter$default(
                     StringsKt.replace$default((java.lang.String)var6, "content_length", "body_size", false, 4, null), ".", null, 2, null
                  )
               ),
               var7
            );
         }
      }

      var4.setData(var5);
      return var4;
   }

   public override fun convert(breadcrumb: Breadcrumb): RRWebEvent? {
      val var8: java.util.Map = new LinkedHashMap();
      val var2: Boolean = var1.getCategory() == "http";
      var var3: RRWebEvent = null;
      if (var2) {
         if (this.isValidForRRWebSpan(var1)) {
            var3 = this.toRRWebSpanEvent(var1);
         }

         return var3;
      } else {
         var var30: java.lang.String;
         var var34: SentryLevel;
         label162: {
            if (var1.getType() == "navigation" && var1.getCategory() == "app.lifecycle") {
               val var27: StringBuilder = new StringBuilder("app.");
               var27.append(var1.getData().get("state"));
               var16 = var27.toString();
            } else if (var1.getType() == "navigation" && var1.getCategory() == "device.orientation") {
               var16 = var1.getCategory();
               var30 = (java.lang.String)var1.getData().get("position");
               if (!(var30 == "landscape") && !(var30 == "portrait")) {
                  return null;
               }

               var8.put("position", var30);
            } else if (var1.getType() == "navigation") {
               label124: {
                  if (var1.getData().get("state") == "resumed") {
                     var var11: Any = var1.getData().get("screen");
                     if (var11 is java.lang.String) {
                        var11 = var11 as java.lang.String;
                     } else {
                        var11 = null;
                     }

                     if (var11 != null) {
                        var13 = StringsKt.substringAfterLast$default((java.lang.String)var11, '.', null, 2, null);
                        break label124;
                     }
                  } else {
                     val var14: java.util.Map = var1.getData();
                     if (var14.containsKey("to")) {
                        val var15: Any = var1.getData().get("to");
                        if (var15 is java.lang.String) {
                           var13 = var15 as java.lang.String;
                           break label124;
                        }
                     }
                  }

                  var13 = null;
               }

               if (var13 == null) {
                  return null;
               }

               var8.put("to", var13);
               var16 = "navigation";
            } else {
               if (var1.getCategory() == "ui.click") {
                  var30 = (java.lang.String)var1.getData().get("view.id");
                  var var25: Any = var30;
                  if (var30 == null) {
                     var30 = (java.lang.String)var1.getData().get("view.tag");
                     var25 = var30;
                     if (var30 == null) {
                        var25 = var1.getData().get("view.class");
                     }
                  }

                  if (var25 is java.lang.String) {
                     var30 = var25 as java.lang.String;
                  } else {
                     var30 = null;
                  }

                  if (var30 == null) {
                     return null;
                  }

                  var25 = var1.getData();
                  var8.putAll((java.util.Map)var25);
                  var16 = "ui.tap";
                  var34 = null;
                  break label162;
               }

               if (var1.getType() == "system" && var1.getCategory() == "network.event") {
                  var var18: Any;
                  if (var1.getData().get("action") == "NETWORK_LOST") {
                     var18 = "offline";
                  } else {
                     var18 = var1.getData();
                     if (!var18.containsKey("network_type")) {
                        return null;
                     }

                     var18 = var1.getData().get("network_type");
                     if (var18 is java.lang.String) {
                        var18 = var18 as java.lang.String;
                     } else {
                        var18 = null;
                     }

                     if (var18 as java.lang.CharSequence == null || (var18 as java.lang.CharSequence).length() == 0) {
                        return null;
                     }

                     var18 = var1.getData().get("network_type");
                  }

                  var8.put("state", var18);
                  if (this.lastConnectivityState == var8.get("state")) {
                     return null;
                  }

                  var18 = var8.get("state");
                  if (var18 is java.lang.String) {
                     var18 = var18 as java.lang.String;
                  } else {
                     var18 = null;
                  }

                  this.lastConnectivityState = (java.lang.String)var18;
                  var16 = "device.connectivity";
               } else {
                  if (!(var1.getData().get("action") == "BATTERY_CHANGED")) {
                     var16 = var1.getCategory();
                     var30 = var1.getMessage();
                     var34 = var1.getLevel();
                     val var35: java.util.Map = var1.getData();
                     var8.putAll(var35);
                     break label162;
                  }

                  val var28: java.util.Map = var1.getData();
                  val var17: LinkedHashMap = new LinkedHashMap();

                  for (Entry var29 : var28.entrySet()) {
                     val var6: java.lang.String = var29.getKey() as java.lang.String;
                     if (var6 == "level" || var6 == "charging") {
                        var17.put(var29.getKey(), var29.getValue());
                     }
                  }

                  var8.putAll(var17);
                  var16 = "device.battery";
               }
            }

            var30 = null;
            var34 = null;
         }

         val var9: java.lang.CharSequence = var16;
         var var36: RRWebEvent = null;
         if (var9 != null) {
            if (var9.length() == 0) {
               var36 = null;
            } else {
               val var37: RRWebBreadcrumbEvent = new RRWebBreadcrumbEvent();
               var37.setTimestamp(var1.getTimestamp().getTime());
               var37.setBreadcrumbTimestamp((double)var1.getTimestamp().getTime() / 1000.0);
               var37.setBreadcrumbType("default");
               var37.setCategory(var16);
               var37.setMessage(var30);
               var37.setLevel(var34);
               var37.setData(var8);
               var36 = var37;
            }
         }

         return var36;
      }
   }

   internal companion object {
      private final val snakecasePattern: Regex
         private final get() {
            return DefaultReplayBreadcrumbConverter.access$getSnakecasePattern$delegate$cp().getValue() as Regex;
         }


      private final val supportedNetworkData: HashSet<String>
   }
}
