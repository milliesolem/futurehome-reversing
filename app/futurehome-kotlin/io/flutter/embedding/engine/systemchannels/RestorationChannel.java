package io.flutter.embedding.engine.systemchannels;

import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import java.util.HashMap;
import java.util.Map;

public class RestorationChannel {
   private static final String TAG = "RestorationChannel";
   private MethodChannel channel;
   private boolean engineHasProvidedData = false;
   private boolean frameworkHasRequestedData = false;
   private final MethodChannel.MethodCallHandler handler;
   private MethodChannel.Result pendingFrameworkRestorationChannelRequest;
   private byte[] restorationData;
   public final boolean waitForRestorationData;

   public RestorationChannel(DartExecutor var1, boolean var2) {
      this(new MethodChannel(var1, "flutter/restoration", StandardMethodCodec.INSTANCE), var2);
   }

   RestorationChannel(MethodChannel var1, boolean var2) {
      MethodChannel.MethodCallHandler var3 = new MethodChannel.MethodCallHandler(this) {
         final RestorationChannel this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onMethodCall(MethodCall var1, MethodChannel.Result var2x) {
            String var3x = var1.method;
            Object var4 = var1.arguments;
            var3x.hashCode();
            if (!var3x.equals("get")) {
               if (!var3x.equals("put")) {
                  var2x.notImplemented();
               } else {
                  this.this$0.restorationData = (byte[])var4;
                  var2x.success(null);
               }
            } else {
               this.this$0.frameworkHasRequestedData = true;
               if (!this.this$0.engineHasProvidedData && this.this$0.waitForRestorationData) {
                  this.this$0.pendingFrameworkRestorationChannelRequest = var2x;
               } else {
                  var4 = this.this$0;
                  var2x.success(((RestorationChannel)var4).packageData(((RestorationChannel)var4).restorationData));
               }
            }
         }
      };
      this.handler = var3;
      this.channel = var1;
      this.waitForRestorationData = var2;
      var1.setMethodCallHandler(var3);
   }

   private Map<String, Object> packageData(byte[] var1) {
      HashMap var2 = new HashMap();
      var2.put("enabled", true);
      var2.put("data", var1);
      return var2;
   }

   public void clearData() {
      this.restorationData = null;
   }

   public byte[] getRestorationData() {
      return this.restorationData;
   }

   public void setRestorationData(byte[] var1) {
      this.engineHasProvidedData = true;
      MethodChannel.Result var2 = this.pendingFrameworkRestorationChannelRequest;
      if (var2 != null) {
         var2.success(this.packageData(var1));
         this.pendingFrameworkRestorationChannelRequest = null;
         this.restorationData = var1;
      } else if (this.frameworkHasRequestedData) {
         this.channel.invokeMethod("push", this.packageData(var1), new MethodChannel.Result(this, var1) {
            final RestorationChannel this$0;
            final byte[] val$data;

            {
               this.this$0 = var1;
               this.val$data = var2x;
            }

            @Override
            public void error(String var1, String var2x, Object var3) {
               var3 = new StringBuilder("Error ");
               var3.append(var1);
               var3.append(" while sending restoration data to framework: ");
               var3.append(var2x);
               Log.e("RestorationChannel", var3.toString());
            }

            @Override
            public void notImplemented() {
            }

            @Override
            public void success(Object var1) {
               this.this$0.restorationData = this.val$data;
            }
         });
      } else {
         this.restorationData = var1;
      }
   }
}
