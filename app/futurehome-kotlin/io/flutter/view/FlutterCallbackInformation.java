package io.flutter.view;

import io.flutter.embedding.engine.FlutterJNI;

public final class FlutterCallbackInformation {
   public final String callbackClassName;
   public final String callbackLibraryPath;
   public final String callbackName;

   private FlutterCallbackInformation(String var1, String var2, String var3) {
      this.callbackName = var1;
      this.callbackClassName = var2;
      this.callbackLibraryPath = var3;
   }

   public static FlutterCallbackInformation lookupCallbackInformation(long var0) {
      return FlutterJNI.nativeLookupCallbackInformation(var0);
   }
}
