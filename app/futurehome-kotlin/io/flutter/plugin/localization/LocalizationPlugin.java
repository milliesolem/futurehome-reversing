package io.flutter.plugin.localization;

import android.content.Context;
import android.content.res.Configuration;
import android.os.LocaleList;
import android.os.Build.VERSION;
import androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0;
import io.flutter.embedding.engine.systemchannels.LocalizationChannel;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocalizationPlugin {
   private final Context context;
   private final LocalizationChannel localizationChannel;
   final LocalizationChannel.LocalizationMessageHandler localizationMessageHandler;

   public LocalizationPlugin(Context var1, LocalizationChannel var2) {
      LocalizationChannel.LocalizationMessageHandler var3 = new LocalizationChannel.LocalizationMessageHandler(this) {
         final LocalizationPlugin this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public String getStringResource(String var1, String var2x) {
            Context var4 = this.this$0.context;
            if (var2x != null) {
               Locale var6 = LocalizationPlugin.localeFromString(var2x);
               Configuration var8 = new Configuration(this.this$0.context.getResources().getConfiguration());
               var8.setLocale(var6);
               var4 = this.this$0.context.createConfigurationContext(var8);
            }

            var2x = this.this$0.context.getPackageName();
            int var3x = var4.getResources().getIdentifier(var1, "string", var2x);
            if (var3x != 0) {
               var1 = var4.getResources().getString(var3x);
            } else {
               var1 = null;
            }

            return var1;
         }
      };
      this.localizationMessageHandler = var3;
      this.context = var1;
      this.localizationChannel = var2;
      var2.setLocalizationMessageHandler(var3);
   }

   public static Locale localeFromString(String var0) {
      String[] var6 = var0.replace('_', '-').split("-", -1);
      String var5 = var6[0];
      int var2 = var6.length;
      String var4 = "";
      byte var1 = 1;
      if (var2 > 1 && var6[1].length() == 4) {
         var0 = var6[1];
         var1 = 2;
      } else {
         var0 = "";
      }

      String var3 = var4;
      if (var6.length > var1) {
         var3 = var4;
         if (var6[var1].length() >= 2) {
            var3 = var4;
            if (var6[var1].length() <= 3) {
               var3 = var6[var1];
            }
         }
      }

      return new Locale(var5, var3, var0);
   }

   public Locale resolveNativeLocale(List<Locale> var1) {
      if (var1 != null && !var1.isEmpty()) {
         if (VERSION.SDK_INT >= 26) {
            ArrayList var24 = new ArrayList();
            LocaleList var8 = ExternalSyntheticApiModelOutline0.m(this.context.getResources().getConfiguration());
            int var3 = androidx.core.util.HalfKt..ExternalSyntheticApiModelOutline0.m(var8);

            for (int var9 = 0; var9 < var3; var9++) {
               Locale var27 = androidx.core.util.HalfKt..ExternalSyntheticApiModelOutline0.m(var8, var9);
               String var17 = var27.getLanguage();
               String var11 = var17;
               if (!var27.getScript().isEmpty()) {
                  StringBuilder var12 = new StringBuilder();
                  var12.append(var17);
                  var12.append("-");
                  var12.append(var27.getScript());
                  var11 = var12.toString();
               }

               var17 = var11;
               if (!var27.getCountry().isEmpty()) {
                  StringBuilder var19 = new StringBuilder();
                  var19.append(var11);
                  var19.append("-");
                  var19.append(var27.getCountry());
                  var17 = var19.toString();
               }

               var24.add(AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var17));
               AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$2();
               var24.add(AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var27.getLanguage()));
               AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$2();
               StringBuilder var13 = new StringBuilder();
               var13.append(var27.getLanguage());
               var13.append("-*");
               var24.add(AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var13.toString()));
            }

            Locale var14 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var24, var1);
            return var14 != null ? var14 : (Locale)var1.get(0);
         } else if (VERSION.SDK_INT >= 24) {
            LocaleList var16 = ExternalSyntheticApiModelOutline0.m(this.context.getResources().getConfiguration());

            for (int var2 = 0; var2 < androidx.core.util.HalfKt..ExternalSyntheticApiModelOutline0.m(var16); var2++) {
               Locale var10 = androidx.core.util.HalfKt..ExternalSyntheticApiModelOutline0.m(var16, var2);

               for (Locale var7 : var1) {
                  if (var10.equals(var7)) {
                     return var7;
                  }
               }

               for (Locale var22 : var1) {
                  if (var10.getLanguage().equals(var22.toLanguageTag())) {
                     return var22;
                  }
               }

               for (Locale var26 : var1) {
                  if (var10.getLanguage().equals(var26.getLanguage())) {
                     return var26;
                  }
               }
            }

            return (Locale)var1.get(0);
         } else {
            Locale var4 = this.context.getResources().getConfiguration().locale;
            if (var4 != null) {
               for (Locale var6 : var1) {
                  if (var4.equals(var6)) {
                     return var6;
                  }
               }

               for (Locale var20 : var1) {
                  if (var4.getLanguage().equals(var20.toString())) {
                     return var20;
                  }
               }
            }

            return (Locale)var1.get(0);
         }
      } else {
         return null;
      }
   }

   public void sendLocalesToFlutter(Configuration var1) {
      ArrayList var4 = new ArrayList();
      if (VERSION.SDK_INT >= 24) {
         LocaleList var5 = ExternalSyntheticApiModelOutline0.m(var1);
         int var3 = androidx.core.util.HalfKt..ExternalSyntheticApiModelOutline0.m(var5);

         for (int var2 = 0; var2 < var3; var2++) {
            var4.add(androidx.core.util.HalfKt..ExternalSyntheticApiModelOutline0.m(var5, var2));
         }
      } else {
         var4.add(var1.locale);
      }

      this.localizationChannel.sendLocales(var4);
   }
}
