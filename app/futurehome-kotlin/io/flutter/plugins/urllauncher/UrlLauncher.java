package io.flutter.plugins.urllauncher;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsIntent.Builder;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

final class UrlLauncher implements Messages.UrlLauncherApi {
   static final boolean $assertionsDisabled = false;
   private static final String TAG = "UrlLauncher";
   private Activity activity;
   private final Context applicationContext;
   private final UrlLauncher.IntentResolver intentResolver;

   UrlLauncher(Context var1) {
      this(var1, new UrlLauncher$$ExternalSyntheticLambda0(var1));
   }

   UrlLauncher(Context var1, UrlLauncher.IntentResolver var2) {
      this.applicationContext = var1;
      this.intentResolver = var2;
   }

   private static boolean containsRestrictedHeader(Map<String, String> var0) {
      Iterator var3 = var0.keySet().iterator();

      while (true) {
         boolean var2 = var3.hasNext();
         byte var1 = 0;
         if (!var2) {
            return false;
         }

         label33: {
            String var4 = ((String)var3.next()).toLowerCase(Locale.US);
            var4.hashCode();
            switch (var4.hashCode()) {
               case -1423461112:
                  if (var4.equals("accept")) {
                     break label33;
                  }
                  break;
               case -1229727188:
                  if (var4.equals("content-language")) {
                     var1 = 1;
                     break label33;
                  }
                  break;
               case 785670158:
                  if (var4.equals("content-type")) {
                     var1 = 2;
                     break label33;
                  }
                  break;
               case 802785917:
                  if (var4.equals("accept-language")) {
                     var1 = 3;
                     break label33;
                  }
            }

            var1 = -1;
         }

         switch (var1) {
            case 0:
            case 1:
            case 2:
            case 3:
               break;
            default:
               return true;
         }
      }
   }

   private void ensureActivity() {
      if (this.activity == null) {
         throw new Messages.FlutterError("NO_ACTIVITY", "Launching a URL requires a foreground activity.", null);
      }
   }

   private static Bundle extractBundle(Map<String, String> var0) {
      Bundle var1 = new Bundle();

      for (String var2 : var0.keySet()) {
         var1.putString(var2, (String)var0.get(var2));
      }

      return var1;
   }

   private static boolean openCustomTab(Context var0, Uri var1, Bundle var2, Messages.BrowserOptions var3) {
      CustomTabsIntent var5 = new Builder().setShowTitle(var3.getShowTitle()).build();
      var5.intent.putExtra("com.android.browser.headers", var2);

      try {
         var5.launchUrl(var0, var1);
         return true;
      } catch (ActivityNotFoundException var4) {
         return false;
      }
   }

   @Override
   public Boolean canLaunchUrl(String var1) {
      Intent var2 = new Intent("android.intent.action.VIEW");
      var2.setData(Uri.parse(var1));
      var1 = this.intentResolver.getHandlerComponentName(var2);
      return var1 == null ? false : "{com.android.fallback/com.android.fallback.Fallback}".equals(var1) ^ true;
   }

   @Override
   public void closeWebView() {
      this.applicationContext.sendBroadcast(new Intent("close action"));
   }

   @Override
   public Boolean launchUrl(String var1, Map<String, String> var2) {
      this.ensureActivity();
      Intent var4 = new Intent("android.intent.action.VIEW").setData(Uri.parse(var1)).putExtra("com.android.browser.headers", extractBundle(var2));

      try {
         this.activity.startActivity(var4);
      } catch (ActivityNotFoundException var3) {
         return false;
      }

      return true;
   }

   @Override
   public Boolean openUrlInApp(String var1, Boolean var2, Messages.WebViewOptions var3, Messages.BrowserOptions var4) {
      this.ensureActivity();
      Bundle var5 = extractBundle(var3.getHeaders());
      if (var2 && !containsRestrictedHeader(var3.getHeaders())) {
         Uri var8 = Uri.parse(var1);
         if (openCustomTab(this.activity, var8, var5, var4)) {
            return true;
         }
      }

      Intent var7 = WebViewActivity.createIntent(this.activity, var1, var3.getEnableJavaScript(), var3.getEnableDomStorage(), var5);

      try {
         this.activity.startActivity(var7);
      } catch (ActivityNotFoundException var6) {
         return false;
      }

      return true;
   }

   void setActivity(Activity var1) {
      this.activity = var1;
   }

   @Override
   public Boolean supportsCustomTabs() {
      boolean var1;
      if (CustomTabsClient.getPackageName(this.applicationContext, Collections.emptyList()) != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   interface IntentResolver {
      String getHandlerComponentName(Intent var1);
   }
}
