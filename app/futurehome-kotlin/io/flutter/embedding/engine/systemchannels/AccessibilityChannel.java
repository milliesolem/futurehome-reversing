package io.flutter.embedding.engine.systemchannels;

import io.flutter.Log;
import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.view.AccessibilityBridge;
import java.util.HashMap;

public class AccessibilityChannel {
   private static final String TAG = "AccessibilityChannel";
   public final BasicMessageChannel<Object> channel;
   public final FlutterJNI flutterJNI;
   private AccessibilityChannel.AccessibilityMessageHandler handler;
   public final BasicMessageChannel.MessageHandler<Object> parsingMessageHandler;

   public AccessibilityChannel(DartExecutor var1, FlutterJNI var2) {
      BasicMessageChannel.MessageHandler var3 = new BasicMessageChannel.MessageHandler<Object>(this) {
         final AccessibilityChannel this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onMessage(Object var1, BasicMessageChannel.Reply<Object> var2x) {
            if (this.this$0.handler == null) {
               var2x.reply(null);
            } else {
               var1 = var1;
               String var7 = (String)var1.get("type");
               HashMap var6 = (HashMap)var1.get("data");
               StringBuilder var5 = new StringBuilder("Received ");
               var5.append(var7);
               var5.append(" message.");
               Log.v("AccessibilityChannel", var5.toString());
               var7.hashCode();
               int var4 = var7.hashCode();
               byte var3x = -1;
               switch (var4) {
                  case -1140076541:
                     if (var7.equals("tooltip")) {
                        var3x = 0;
                     }
                     break;
                  case -649620375:
                     if (var7.equals("announce")) {
                        var3x = 1;
                     }
                     break;
                  case 114595:
                     if (var7.equals("tap")) {
                        var3x = 2;
                     }
                     break;
                  case 97604824:
                     if (var7.equals("focus")) {
                        var3x = 3;
                     }
                     break;
                  case 114203431:
                     if (var7.equals("longPress")) {
                        var3x = 4;
                     }
               }

               switch (var3x) {
                  case 0:
                     String var13 = (String)var6.get("message");
                     if (var13 != null) {
                        this.this$0.handler.onTooltip(var13);
                     }
                     break;
                  case 1:
                     String var12 = (String)var6.get("message");
                     if (var12 != null) {
                        this.this$0.handler.announce(var12);
                     }
                     break;
                  case 2:
                     Integer var11 = (Integer)var1.get("nodeId");
                     if (var11 != null) {
                        this.this$0.handler.onTap(var11);
                     }
                     break;
                  case 3:
                     Integer var10 = (Integer)var1.get("nodeId");
                     if (var10 != null) {
                        this.this$0.handler.onFocus(var10);
                     }
                     break;
                  case 4:
                     Integer var9 = (Integer)var1.get("nodeId");
                     if (var9 != null) {
                        this.this$0.handler.onLongPress(var9);
                     }
               }

               var2x.reply(null);
            }
         }
      };
      this.parsingMessageHandler = var3;
      BasicMessageChannel var4 = new BasicMessageChannel<>(var1, "flutter/accessibility", StandardMessageCodec.INSTANCE);
      this.channel = var4;
      var4.setMessageHandler(var3);
      this.flutterJNI = var2;
   }

   public AccessibilityChannel(BasicMessageChannel<Object> var1, FlutterJNI var2) {
      this.parsingMessageHandler = new BasicMessageChannel.MessageHandler<Object>(this) {
         final AccessibilityChannel this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onMessage(Object var1, BasicMessageChannel.Reply<Object> var2x) {
            if (this.this$0.handler == null) {
               var2x.reply(null);
            } else {
               var1 = var1;
               String var7 = (String)var1.get("type");
               HashMap var6 = (HashMap)var1.get("data");
               StringBuilder var5 = new StringBuilder("Received ");
               var5.append(var7);
               var5.append(" message.");
               Log.v("AccessibilityChannel", var5.toString());
               var7.hashCode();
               int var4 = var7.hashCode();
               byte var3x = -1;
               switch (var4) {
                  case -1140076541:
                     if (var7.equals("tooltip")) {
                        var3x = 0;
                     }
                     break;
                  case -649620375:
                     if (var7.equals("announce")) {
                        var3x = 1;
                     }
                     break;
                  case 114595:
                     if (var7.equals("tap")) {
                        var3x = 2;
                     }
                     break;
                  case 97604824:
                     if (var7.equals("focus")) {
                        var3x = 3;
                     }
                     break;
                  case 114203431:
                     if (var7.equals("longPress")) {
                        var3x = 4;
                     }
               }

               switch (var3x) {
                  case 0:
                     String var13 = (String)var6.get("message");
                     if (var13 != null) {
                        this.this$0.handler.onTooltip(var13);
                     }
                     break;
                  case 1:
                     String var12 = (String)var6.get("message");
                     if (var12 != null) {
                        this.this$0.handler.announce(var12);
                     }
                     break;
                  case 2:
                     Integer var11 = (Integer)var1.get("nodeId");
                     if (var11 != null) {
                        this.this$0.handler.onTap(var11);
                     }
                     break;
                  case 3:
                     Integer var10 = (Integer)var1.get("nodeId");
                     if (var10 != null) {
                        this.this$0.handler.onFocus(var10);
                     }
                     break;
                  case 4:
                     Integer var9 = (Integer)var1.get("nodeId");
                     if (var9 != null) {
                        this.this$0.handler.onLongPress(var9);
                     }
               }

               var2x.reply(null);
            }
         }
      };
      this.channel = var1;
      this.flutterJNI = var2;
   }

   public void dispatchSemanticsAction(int var1, AccessibilityBridge.Action var2) {
      this.flutterJNI.dispatchSemanticsAction(var1, var2);
   }

   public void dispatchSemanticsAction(int var1, AccessibilityBridge.Action var2, Object var3) {
      this.flutterJNI.dispatchSemanticsAction(var1, var2, var3);
   }

   public void onAndroidAccessibilityDisabled() {
      this.flutterJNI.setSemanticsEnabled(false);
   }

   public void onAndroidAccessibilityEnabled() {
      this.flutterJNI.setSemanticsEnabled(true);
   }

   public void setAccessibilityFeatures(int var1) {
      this.flutterJNI.setAccessibilityFeatures(var1);
   }

   public void setAccessibilityMessageHandler(AccessibilityChannel.AccessibilityMessageHandler var1) {
      this.handler = var1;
      this.flutterJNI.setAccessibilityDelegate(var1);
   }

   public interface AccessibilityMessageHandler extends FlutterJNI.AccessibilityDelegate {
      void announce(String var1);

      void onFocus(int var1);

      void onLongPress(int var1);

      void onTap(int var1);

      void onTooltip(String var1);
   }
}
