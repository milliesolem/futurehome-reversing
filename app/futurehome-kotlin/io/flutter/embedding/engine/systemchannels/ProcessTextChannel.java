package io.flutter.embedding.engine.systemchannels;

import android.content.pm.PackageManager;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import java.util.ArrayList;
import java.util.Map;

public class ProcessTextChannel {
   private static final String CHANNEL_NAME = "flutter/processtext";
   private static final String METHOD_PROCESS_TEXT_ACTION = "ProcessText.processTextAction";
   private static final String METHOD_QUERY_TEXT_ACTIONS = "ProcessText.queryTextActions";
   private static final String TAG = "ProcessTextChannel";
   public final MethodChannel channel;
   public final PackageManager packageManager;
   public final MethodChannel.MethodCallHandler parsingMethodHandler;
   private ProcessTextChannel.ProcessTextMethodHandler processTextMethodHandler;

   public ProcessTextChannel(DartExecutor var1, PackageManager var2) {
      MethodChannel.MethodCallHandler var3 = new MethodChannel.MethodCallHandler(this) {
         final ProcessTextChannel this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onMethodCall(MethodCall var1, MethodChannel.Result var2x) {
            if (this.this$0.processTextMethodHandler != null) {
               String var4 = var1.method;
               Object var8 = var1.arguments;
               var4.hashCode();
               if (!var4.equals("ProcessText.processTextAction")) {
                  if (!var4.equals("ProcessText.queryTextActions")) {
                     var2x.notImplemented();
                  } else {
                     try {
                        var2x.success(this.this$0.processTextMethodHandler.queryTextActions());
                     } catch (IllegalStateException var7) {
                        var2x.error("error", var7.getMessage(), null);
                     }
                  }
               } else {
                  try {
                     ArrayList var10 = (ArrayList)var8;
                     String var5 = (String)var10.get(0);
                     var8 = (String)var10.get(1);
                     boolean var3x = (Boolean)var10.get(2);
                     this.this$0.processTextMethodHandler.processTextAction(var5, (String)var8, var3x, var2x);
                  } catch (IllegalStateException var6) {
                     var2x.error("error", var6.getMessage(), null);
                  }
               }
            }
         }
      };
      this.parsingMethodHandler = var3;
      this.packageManager = var2;
      MethodChannel var4 = new MethodChannel(var1, "flutter/processtext", StandardMethodCodec.INSTANCE);
      this.channel = var4;
      var4.setMethodCallHandler(var3);
   }

   public void setMethodHandler(ProcessTextChannel.ProcessTextMethodHandler var1) {
      this.processTextMethodHandler = var1;
   }

   public interface ProcessTextMethodHandler {
      void processTextAction(String var1, String var2, boolean var3, MethodChannel.Result var4);

      Map<String, String> queryTextActions();
   }
}
