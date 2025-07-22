package io.flutter.plugins.urllauncher;

import android.util.Log;
import io.flutter.plugin.common.StandardMessageCodec;
import java.io.ByteArrayOutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Messages {
   protected static ArrayList<Object> wrapError(Throwable var0) {
      ArrayList var1 = new ArrayList(3);
      if (var0 instanceof Messages.FlutterError) {
         Messages.FlutterError var3 = (Messages.FlutterError)var0;
         var1.add(var3.code);
         var1.add(var3.getMessage());
         var1.add(var3.details);
      } else {
         var1.add(var0.toString());
         var1.add(var0.getClass().getSimpleName());
         StringBuilder var2 = new StringBuilder("Cause: ");
         var2.append(var0.getCause());
         var2.append(", Stacktrace: ");
         var2.append(Log.getStackTraceString(var0));
         var1.add(var2.toString());
      }

      return var1;
   }

   public static final class BrowserOptions {
      private Boolean showTitle;

      BrowserOptions() {
      }

      static Messages.BrowserOptions fromList(ArrayList<Object> var0) {
         Messages.BrowserOptions var1 = new Messages.BrowserOptions();
         var1.setShowTitle((Boolean)var0.get(0));
         return var1;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (var1 != null && this.getClass() == var1.getClass()) {
            var1 = var1;
            return this.showTitle.equals(var1.showTitle);
         } else {
            return false;
         }
      }

      public Boolean getShowTitle() {
         return this.showTitle;
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.showTitle);
      }

      public void setShowTitle(Boolean var1) {
         if (var1 != null) {
            this.showTitle = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"showTitle\" is null.");
         }
      }

      ArrayList<Object> toList() {
         ArrayList var1 = new ArrayList(1);
         var1.add(this.showTitle);
         return var1;
      }

      public static final class Builder {
         private Boolean showTitle;

         public Messages.BrowserOptions build() {
            Messages.BrowserOptions var1 = new Messages.BrowserOptions();
            var1.setShowTitle(this.showTitle);
            return var1;
         }

         public Messages.BrowserOptions.Builder setShowTitle(Boolean var1) {
            this.showTitle = var1;
            return this;
         }
      }
   }

   @Retention(RetentionPolicy.CLASS)
   @Target({ElementType.METHOD})
   @interface CanIgnoreReturnValue {
   }

   public static class FlutterError extends RuntimeException {
      public final String code;
      public final Object details;

      public FlutterError(String var1, String var2, Object var3) {
         super(var2);
         this.code = var1;
         this.details = var3;
      }
   }

   private static class PigeonCodec extends StandardMessageCodec {
      public static final Messages.PigeonCodec INSTANCE = new Messages.PigeonCodec();

      @Override
      protected Object readValueOfType(byte var1, ByteBuffer var2) {
         if (var1 != -127) {
            return var1 != -126 ? super.readValueOfType(var1, var2) : Messages.BrowserOptions.fromList((ArrayList<Object>)this.readValue(var2));
         } else {
            return Messages.WebViewOptions.fromList((ArrayList<Object>)this.readValue(var2));
         }
      }

      @Override
      protected void writeValue(ByteArrayOutputStream var1, Object var2) {
         if (var2 instanceof Messages.WebViewOptions) {
            var1.write(129);
            this.writeValue(var1, ((Messages.WebViewOptions)var2).toList());
         } else if (var2 instanceof Messages.BrowserOptions) {
            var1.write(130);
            this.writeValue(var1, ((Messages.BrowserOptions)var2).toList());
         } else {
            super.writeValue(var1, var2);
         }
      }
   }

   public interface UrlLauncherApi {
      Boolean canLaunchUrl(String var1);

      void closeWebView();

      Boolean launchUrl(String var1, Map<String, String> var2);

      Boolean openUrlInApp(String var1, Boolean var2, Messages.WebViewOptions var3, Messages.BrowserOptions var4);

      Boolean supportsCustomTabs();
   }

   public static final class WebViewOptions {
      private Boolean enableDomStorage;
      private Boolean enableJavaScript;
      private Map<String, String> headers;

      WebViewOptions() {
      }

      static Messages.WebViewOptions fromList(ArrayList<Object> var0) {
         Messages.WebViewOptions var1 = new Messages.WebViewOptions();
         var1.setEnableJavaScript((Boolean)var0.get(0));
         var1.setEnableDomStorage((Boolean)var0.get(1));
         var1.setHeaders((Map<String, String>)var0.get(2));
         return var1;
      }

      @Override
      public boolean equals(Object var1) {
         boolean var2 = true;
         if (this == var1) {
            return true;
         } else if (var1 != null && this.getClass() == var1.getClass()) {
            var1 = var1;
            if (!this.enableJavaScript.equals(var1.enableJavaScript)
               || !this.enableDomStorage.equals(var1.enableDomStorage)
               || !this.headers.equals(var1.headers)) {
               var2 = false;
            }

            return var2;
         } else {
            return false;
         }
      }

      public Boolean getEnableDomStorage() {
         return this.enableDomStorage;
      }

      public Boolean getEnableJavaScript() {
         return this.enableJavaScript;
      }

      public Map<String, String> getHeaders() {
         return this.headers;
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.enableJavaScript, this.enableDomStorage, this.headers);
      }

      public void setEnableDomStorage(Boolean var1) {
         if (var1 != null) {
            this.enableDomStorage = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"enableDomStorage\" is null.");
         }
      }

      public void setEnableJavaScript(Boolean var1) {
         if (var1 != null) {
            this.enableJavaScript = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"enableJavaScript\" is null.");
         }
      }

      public void setHeaders(Map<String, String> var1) {
         if (var1 != null) {
            this.headers = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"headers\" is null.");
         }
      }

      ArrayList<Object> toList() {
         ArrayList var1 = new ArrayList(3);
         var1.add(this.enableJavaScript);
         var1.add(this.enableDomStorage);
         var1.add(this.headers);
         return var1;
      }

      public static final class Builder {
         private Boolean enableDomStorage;
         private Boolean enableJavaScript;
         private Map<String, String> headers;

         public Messages.WebViewOptions build() {
            Messages.WebViewOptions var1 = new Messages.WebViewOptions();
            var1.setEnableJavaScript(this.enableJavaScript);
            var1.setEnableDomStorage(this.enableDomStorage);
            var1.setHeaders(this.headers);
            return var1;
         }

         public Messages.WebViewOptions.Builder setEnableDomStorage(Boolean var1) {
            this.enableDomStorage = var1;
            return this;
         }

         public Messages.WebViewOptions.Builder setEnableJavaScript(Boolean var1) {
            this.enableJavaScript = var1;
            return this;
         }

         public Messages.WebViewOptions.Builder setHeaders(Map<String, String> var1) {
            this.headers = var1;
            return this;
         }
      }
   }
}
