package dev.fluttercommunity.plus.share

import android.os.Build.VERSION
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result

internal class MethodCallHandler(share: Share, manager: ShareSuccessManager) : MethodChannel.MethodCallHandler {
   private final val share: Share
   private final val manager: ShareSuccessManager

   init {
      this.share = var1;
      this.manager = var2;
   }

   @Throws(java/lang/IllegalArgumentException::class)
   private fun expectMapArguments(call: MethodCall) {
      if (var1.arguments !is java.util.Map) {
         throw new IllegalArgumentException("Map arguments expected".toString());
      }
   }

   private fun success(isWithResult: Boolean, result: Result) {
      if (!var1) {
         var2.success("dev.fluttercommunity.plus/share/unavailable");
      }
   }

   public override fun onMethodCall(call: MethodCall, result: Result) {
      this.expectMapArguments(var1);
      val var4: Boolean;
      if (VERSION.SDK_INT >= 22) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         this.manager.setCallback(var2);
      }

      var var5: java.lang.String;
      try {
         var5 = var1.method;
      } catch (var15: java.lang.Throwable) {
         this.manager.clear();
         var2.error("Share failed", var15.getMessage(), var15);
         return;
      }

      if (var5 != null) {
         var var3: Int;
         try {
            var3 = var5.hashCode();
         } catch (var11: java.lang.Throwable) {
            this.manager.clear();
            var2.error("Share failed", var11.getMessage(), var11);
            return;
         }

         if (var3 != -1811378728) {
            if (var3 != -743768819) {
               label62:
               if (var3 == 109400031) {
                  try {
                     if (!var5.equals("share")) {
                        break label62;
                     }
                  } catch (var12: java.lang.Throwable) {
                     this.manager.clear();
                     var2.error("Share failed", var12.getMessage(), var12);
                     return;
                  }

                  try {
                     val var100: Share = this.share;
                     val var101: Any = var1.argument("text");
                     var100.share(var101 as java.lang.String, var1.argument("subject"), var4);
                     this.success(var4, var2);
                     return;
                  } catch (var10: java.lang.Throwable) {
                     this.manager.clear();
                     var2.error("Share failed", var10.getMessage(), var10);
                     return;
                  }
               }
            } else {
               label602: {
                  try {
                     if (!var5.equals("shareUri")) {
                        break label602;
                     }
                  } catch (var13: java.lang.Throwable) {
                     this.manager.clear();
                     var2.error("Share failed", var13.getMessage(), var13);
                     return;
                  }

                  try {
                     val var99: Share = this.share;
                     val var97: Any = var1.argument("uri");
                     var99.share(var97 as java.lang.String, null, var4);
                     this.success(var4, var2);
                     return;
                  } catch (var9: java.lang.Throwable) {
                     this.manager.clear();
                     var2.error("Share failed", var9.getMessage(), var9);
                     return;
                  }
               }
            }
         } else {
            label601: {
               try {
                  if (!var5.equals("shareFiles")) {
                     break label601;
                  }
               } catch (var14: java.lang.Throwable) {
                  this.manager.clear();
                  var2.error("Share failed", var14.getMessage(), var14);
                  return;
               }

               try {
                  val var6: Share = this.share;
                  val var98: Any = var1.argument("paths");
                  var6.shareFiles(var98 as MutableList<java.lang.String>, var1.argument("mimeTypes"), var1.argument("text"), var1.argument("subject"), var4);
                  this.success(var4, var2);
                  return;
               } catch (var8: java.lang.Throwable) {
                  this.manager.clear();
                  var2.error("Share failed", var8.getMessage(), var8);
                  return;
               }
            }
         }
      }

      try {
         var2.notImplemented();
      } catch (var7: java.lang.Throwable) {
         this.manager.clear();
         var2.error("Share failed", var7.getMessage(), var7);
         return;
      }
   }
}
