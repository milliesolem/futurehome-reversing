package io.sentry.flutter

import io.sentry.Breadcrumb
import io.sentry.android.replay.DefaultReplayBreadcrumbConverter
import io.sentry.rrweb.RRWebBreadcrumbEvent
import io.sentry.rrweb.RRWebEvent
import io.sentry.rrweb.RRWebSpanEvent
import java.util.Date
import java.util.LinkedHashMap
import java.util.Map.Entry

public class SentryFlutterReplayBreadcrumbConverter : DefaultReplayBreadcrumbConverter {
   private fun convertNetworkBreadcrumb(breadcrumb: Breadcrumb): RRWebEvent? {
      var var3: RRWebEvent = super.convert(var1);
      var var2: RRWebEvent = var3;
      if (var3 == null) {
         var2 = var3;
         if (var1.getData().containsKey("start_timestamp")) {
            var2 = var3;
            if (var1.getData().containsKey("end_timestamp")) {
               val var8: RRWebSpanEvent = new RRWebSpanEvent();
               var8.setOp("resource.http");
               var8.setTimestamp(var1.getTimestamp().getTime());
               var3 = (RRWebEvent)var1.getData().get("url");
               var8.setDescription(var3 as java.lang.String);
               var3 = (RRWebEvent)var1.getData().get("start_timestamp");
               var8.setStartTimestamp(this.doubleTimestamp(var3 as java.lang.Long));
               var3 = (RRWebEvent)var1.getData().get("end_timestamp");
               var8.setEndTimestamp(this.doubleTimestamp(var3 as java.lang.Long));
               val var12: java.util.Map = var1.getData();
               val var6: LinkedHashMap = new LinkedHashMap();

               for (Entry var4 : var12.entrySet()) {
                  if (supportedNetworkData.containsKey(var4.getKey() as java.lang.String)) {
                     var6.put(var4.getKey(), var4.getValue());
                  }
               }

               val var14: java.util.Map = var6;
               val var7: java.util.Map = new LinkedHashMap(MapsKt.mapCapacity(var6.size()));

               for (Entry var16 : var14.entrySet()) {
                  var7.put(supportedNetworkData.get(var16.getKey() as java.lang.String), var16.getValue());
               }

               var8.setData(var7);
               var2 = var8;
            }
         }
      }

      return var2;
   }

   private fun doubleTimestamp(timestamp: Long): Double {
      return var1 / 1000.0;
   }

   private fun doubleTimestamp(date: Date): Double {
      return this.doubleTimestamp(var1.getTime());
   }

   private fun getTouchPathMessage(maybePath: Any?): String? {
      if (var1 is java.util.List) {
         val var4: java.util.List = var1 as java.util.List;
         if (!(var1 as java.util.List).isEmpty()) {
            val var5: StringBuilder = new StringBuilder();

            for (int var2 = Math.min(4, var4.size()) - 1; -1 < var2; var2--) {
               var1 = var4.get(var2);
               if (var1 is java.util.Map) {
                  val var6: java.util.Map = var1 as java.util.Map;
                  var var3: java.lang.String = (java.lang.String)(var1 as java.util.Map).get("element");
                  var1 = var3;
                  if (var3 == null) {
                     var1 = "?";
                  }

                  var5.append(var1);
                  var3 = var6.get("label");
                  var1 = var3;
                  if (var3 == null) {
                     var1 = var6.get("name");
                  }

                  if (var1 is java.lang.String && (var1 as java.lang.CharSequence).length() > 0) {
                     var3 = var1 as java.lang.String;
                     if ((var1 as java.lang.String).length() > 20) {
                        var1 = new StringBuilder();
                        var3 = var3.substring(0, 17);
                        var1.append(var3);
                        var1.append("...");
                        var1 = var1.toString();
                     }

                     var5.append("(");
                     var5.append(var1 as java.lang.String);
                     var5.append(")");
                  }

                  if (var2 > 0) {
                     var5.append(" > ");
                  }
               }
            }

            return var5.toString();
         }
      }

      return null;
   }

   private fun newRRWebBreadcrumb(breadcrumb: Breadcrumb): RRWebBreadcrumbEvent {
      val var2: RRWebBreadcrumbEvent = new RRWebBreadcrumbEvent();
      var2.setCategory(var1.getCategory());
      var2.setLevel(var1.getLevel());
      var2.setData(var1.getData());
      var2.setTimestamp(var1.getTimestamp().getTime());
      val var3: Date = var1.getTimestamp();
      var2.setBreadcrumbTimestamp(this.doubleTimestamp(var3));
      var2.setBreadcrumbType("default");
      return var2;
   }

   public override fun convert(breadcrumb: Breadcrumb): RRWebEvent? {
      val var5: java.lang.String = var1.getCategory();
      var var2: RRWebEvent = null;
      if (var5 != null) {
         switch (var5.hashCode()) {
            case -2139323986:
               if (var5.equals("ui.click")) {
                  val var7: RRWebBreadcrumbEvent = this.newRRWebBreadcrumb(var1);
                  var7.setCategory("ui.tap");
                  var7.setMessage(this.getTouchPathMessage(var1.getData().get("path")));
                  return var7;
               }
               break;
            case -252308533:
               if (var5.equals("sentry.event")) {
                  return null;
               }
               break;
            case -152761521:
               if (var5.equals("sentry.transaction")) {
                  return null;
               }
               break;
            case 3213448:
               if (var5.equals("http")) {
                  return this.convertNetworkBreadcrumb(var1);
               }
               break;
            case 1862666772:
               if (var5.equals("navigation")) {
                  var var6: RRWebBreadcrumbEvent = null;
                  if (var1.getData().containsKey("to")) {
                     var6 = null;
                     if (var1.getData().get("to") is java.lang.String) {
                        var6 = this.newRRWebBreadcrumb(var1);
                     }
                  }

                  return var6;
               }
            default:
         }

         var2 = super.convert(var1);
         if (var2 is RRWebBreadcrumbEvent && (var2 as RRWebBreadcrumbEvent).getCategory() == "navigation") {
            return null;
         }
      }

      return var2;
   }

   internal companion object {
      private final val supportedNetworkData: Map<String, String>
   }
}
