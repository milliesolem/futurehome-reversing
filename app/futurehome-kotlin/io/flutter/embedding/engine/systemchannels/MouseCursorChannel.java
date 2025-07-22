package io.flutter.embedding.engine.systemchannels;

import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import java.util.HashMap;

public class MouseCursorChannel {
   private static final String TAG = "MouseCursorChannel";
   public final MethodChannel channel;
   private MouseCursorChannel.MouseCursorMethodHandler mouseCursorMethodHandler;
   private final MethodChannel.MethodCallHandler parsingMethodCallHandler;

   public MouseCursorChannel(DartExecutor var1) {
      MethodChannel.MethodCallHandler var2 = new MethodChannel.MethodCallHandler(this) {
         final MouseCursorChannel this$0;

         {
            this.this$0 = var1;
         }

         // $VF: Duplicated exception handlers to handle obfuscated exceptions
         @Override
         public void onMethodCall(MethodCall var1, MethodChannel.Result var2x) {
            if (this.this$0.mouseCursorMethodHandler != null) {
               String var3 = var1.method;
               StringBuilder var4 = new StringBuilder("Received '");
               var4.append(var3);
               var4.append("' message.");
               Log.v("MouseCursorChannel", var4.toString());

               try {
                  if (var3.hashCode() != -1307105544) {
                     return;
                  }
               } catch (Exception var9) {
                  StringBuilder var12 = new StringBuilder("Unhandled error: ");
                  var12.append(var9.getMessage());
                  var2x.error("error", var12.toString(), null);
                  return;
               }

               try {
                  if (!var3.equals("activateSystemCursor")) {
                     return;
                  }

                  var10 = (String)((HashMap)var1.arguments).get("kind");
               } catch (Exception var8) {
                  StringBuilder var13 = new StringBuilder("Unhandled error: ");
                  var13.append(var8.getMessage());
                  var2x.error("error", var13.toString(), null);
                  return;
               }

               try {
                  this.this$0.mouseCursorMethodHandler.activateSystemCursor(var10);
               } catch (Exception var7) {
                  Exception var11 = var7;

                  try {
                     StringBuilder var15 = new StringBuilder("Error when setting cursors: ");
                     var15.append(var11.getMessage());
                     var2x.error("error", var15.toString(), null);
                  } catch (Exception var5) {
                     StringBuilder var14 = new StringBuilder("Unhandled error: ");
                     var14.append(var5.getMessage());
                     var2x.error("error", var14.toString(), null);
                  }

                  return;
               }

               try {
                  var2x.success(true);
               } catch (Exception var6) {
                  StringBuilder var16 = new StringBuilder("Unhandled error: ");
                  var16.append(var6.getMessage());
                  var2x.error("error", var16.toString(), null);
               }
            }
         }
      };
      this.parsingMethodCallHandler = var2;
      MethodChannel var3 = new MethodChannel(var1, "flutter/mousecursor", StandardMethodCodec.INSTANCE);
      this.channel = var3;
      var3.setMethodCallHandler(var2);
   }

   public void setMethodHandler(MouseCursorChannel.MouseCursorMethodHandler var1) {
      this.mouseCursorMethodHandler = var1;
   }

   public void synthesizeMethodCall(MethodCall var1, MethodChannel.Result var2) {
      this.parsingMethodCallHandler.onMethodCall(var1, var2);
   }

   public interface MouseCursorMethodHandler {
      void activateSystemCursor(String var1);
   }
}
