package io.flutter.embedding.engine.systemchannels;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import java.util.HashMap;
import java.util.Map;

public class KeyboardChannel {
   public final MethodChannel channel;
   private KeyboardChannel.KeyboardMethodHandler keyboardMethodHandler;
   public final MethodChannel.MethodCallHandler parsingMethodHandler;

   public KeyboardChannel(BinaryMessenger var1) {
      MethodChannel.MethodCallHandler var2 = new MethodChannel.MethodCallHandler(this) {
         Map<Long, Long> pressedState;
         final KeyboardChannel this$0;

         {
            this.this$0 = var1;
            this.pressedState = new HashMap<>();
         }

         @Override
         public void onMethodCall(MethodCall var1, MethodChannel.Result var2x) {
            if (this.this$0.keyboardMethodHandler == null) {
               var2x.success(this.pressedState);
            } else {
               String var4 = var1.method;
               var4.hashCode();
               if (!var4.equals("getKeyboardState")) {
                  var2x.notImplemented();
               } else {
                  try {
                     this.pressedState = this.this$0.keyboardMethodHandler.getKeyboardState();
                  } catch (IllegalStateException var3) {
                     var2x.error("error", var3.getMessage(), null);
                  }

                  var2x.success(this.pressedState);
               }
            }
         }
      };
      this.parsingMethodHandler = var2;
      MethodChannel var3 = new MethodChannel(var1, "flutter/keyboard", StandardMethodCodec.INSTANCE);
      this.channel = var3;
      var3.setMethodCallHandler(var2);
   }

   public void setKeyboardMethodHandler(KeyboardChannel.KeyboardMethodHandler var1) {
      this.keyboardMethodHandler = var1;
   }

   public interface KeyboardMethodHandler {
      Map<Long, Long> getKeyboardState();
   }
}
