package io.flutter.embedding.engine.systemchannels;

import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import java.util.ArrayList;

public class SpellCheckChannel {
   private static final String TAG = "SpellCheckChannel";
   public final MethodChannel channel;
   public final MethodChannel.MethodCallHandler parsingMethodHandler;
   private SpellCheckChannel.SpellCheckMethodHandler spellCheckMethodHandler;

   public SpellCheckChannel(DartExecutor var1) {
      MethodChannel.MethodCallHandler var2 = new MethodChannel.MethodCallHandler(this) {
         final SpellCheckChannel this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onMethodCall(MethodCall var1, MethodChannel.Result var2x) {
            if (this.this$0.spellCheckMethodHandler == null) {
               Log.v("SpellCheckChannel", "No SpellCheckeMethodHandler registered, call not forwarded to spell check API.");
            } else {
               String var3 = var1.method;
               Object var6 = var1.arguments;
               StringBuilder var4 = new StringBuilder("Received '");
               var4.append(var3);
               var4.append("' message.");
               Log.v("SpellCheckChannel", var4.toString());
               var3.hashCode();
               if (!var3.equals("SpellCheck.initiateSpellCheck")) {
                  var2x.notImplemented();
               } else {
                  try {
                     ArrayList var8 = (ArrayList)var6;
                     var6 = (String)var8.get(0);
                     var3 = (String)var8.get(1);
                     this.this$0.spellCheckMethodHandler.initiateSpellCheck((String)var6, var3, var2x);
                  } catch (IllegalStateException var5) {
                     var2x.error("error", var5.getMessage(), null);
                  }
               }
            }
         }
      };
      this.parsingMethodHandler = var2;
      MethodChannel var3 = new MethodChannel(var1, "flutter/spellcheck", StandardMethodCodec.INSTANCE);
      this.channel = var3;
      var3.setMethodCallHandler(var2);
   }

   public void setSpellCheckMethodHandler(SpellCheckChannel.SpellCheckMethodHandler var1) {
      this.spellCheckMethodHandler = var1;
   }

   public interface SpellCheckMethodHandler {
      void initiateSpellCheck(String var1, String var2, MethodChannel.Result var3);
   }
}
