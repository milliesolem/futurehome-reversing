package io.flutter.embedding.engine.systemchannels;

import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.JSONMethodCodec;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class LocalizationChannel {
   private static final String TAG = "LocalizationChannel";
   public final MethodChannel channel;
   public final MethodChannel.MethodCallHandler handler;
   private LocalizationChannel.LocalizationMessageHandler localizationMessageHandler;

   public LocalizationChannel(DartExecutor var1) {
      MethodChannel.MethodCallHandler var2 = new MethodChannel.MethodCallHandler(this) {
         final LocalizationChannel this$0;

         {
            this.this$0 = var1;
         }

         // $VF: Duplicated exception handlers to handle obfuscated exceptions
         @Override
         public void onMethodCall(MethodCall var1, MethodChannel.Result var2x) {
            if (this.this$0.localizationMessageHandler != null) {
               String var3 = var1.method;
               var3.hashCode();
               if (!var3.equals("Localization.getStringResource")) {
                  var2x.notImplemented();
               } else {
                  JSONObject var6 = var1.arguments();

                  label30: {
                     try {
                        var3 = var6.getString("key");
                        if (var6.has("locale")) {
                           var7 = var6.getString("locale");
                           break label30;
                        }
                     } catch (JSONException var5) {
                        var2x.error("error", var5.getMessage(), null);
                        return;
                     }

                     var7 = null;
                  }

                  try {
                     var2x.success(this.this$0.localizationMessageHandler.getStringResource(var3, var7));
                  } catch (JSONException var4) {
                     var2x.error("error", var4.getMessage(), null);
                  }
               }
            }
         }
      };
      this.handler = var2;
      MethodChannel var3 = new MethodChannel(var1, "flutter/localization", JSONMethodCodec.INSTANCE);
      this.channel = var3;
      var3.setMethodCallHandler(var2);
   }

   public void sendLocales(List<Locale> var1) {
      Log.v("LocalizationChannel", "Sending Locales to Flutter.");
      ArrayList var2 = new ArrayList();

      for (Locale var5 : var1) {
         StringBuilder var3 = new StringBuilder("Locale (Language: ");
         var3.append(var5.getLanguage());
         var3.append(", Country: ");
         var3.append(var5.getCountry());
         var3.append(", Variant: ");
         var3.append(var5.getVariant());
         var3.append(")");
         Log.v("LocalizationChannel", var3.toString());
         var2.add(var5.getLanguage());
         var2.add(var5.getCountry());
         var2.add(var5.getScript());
         var2.add(var5.getVariant());
      }

      this.channel.invokeMethod("setLocale", var2);
   }

   public void setLocalizationMessageHandler(LocalizationChannel.LocalizationMessageHandler var1) {
      this.localizationMessageHandler = var1;
   }

   public interface LocalizationMessageHandler {
      String getStringResource(String var1, String var2);
   }
}
