package io.flutter.plugin.common;

import io.flutter.Log;

public class ErrorLogResult implements MethodChannel.Result {
   private int level;
   private String tag;

   public ErrorLogResult(String var1) {
      this(var1, Log.WARN);
   }

   public ErrorLogResult(String var1, int var2) {
      this.tag = var1;
      this.level = var2;
   }

   @Override
   public void error(String var1, String var2, Object var3) {
      if (var3 != null) {
         StringBuilder var6 = new StringBuilder(" details: ");
         var6.append((Object)var3);
         var1 = var6.toString();
      } else {
         var1 = "";
      }

      if (this.level >= Log.WARN) {
         int var4 = this.level;
         var3 = this.tag;
         StringBuilder var5 = new StringBuilder();
         var5.append(var2);
         var5.append(var1);
         Log.println(var4, var3, var5.toString());
      }
   }

   @Override
   public void notImplemented() {
      if (this.level >= Log.WARN) {
         Log.println(this.level, this.tag, "method not implemented");
      }
   }

   @Override
   public void success(Object var1) {
   }
}
