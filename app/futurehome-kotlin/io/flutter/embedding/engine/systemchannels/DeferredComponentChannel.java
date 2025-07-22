package io.flutter.embedding.engine.systemchannels;

import io.flutter.FlutterInjector;
import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.deferredcomponents.DeferredComponentManager;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DeferredComponentChannel {
   private static final String TAG = "DeferredComponentChannel";
   private final MethodChannel channel;
   private Map<String, List<MethodChannel.Result>> componentNameToResults;
   private DeferredComponentManager deferredComponentManager;
   final MethodChannel.MethodCallHandler parsingMethodHandler;

   public DeferredComponentChannel(DartExecutor var1) {
      MethodChannel.MethodCallHandler var2 = new MethodChannel.MethodCallHandler(this) {
         final DeferredComponentChannel this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onMethodCall(MethodCall var1, MethodChannel.Result var2x) {
            if (this.this$0.deferredComponentManager != null) {
               String var6 = var1.method;
               Map var8 = var1.arguments();
               StringBuilder var7 = new StringBuilder("Received '");
               var7.append(var6);
               var7.append("' message.");
               Log.v("DeferredComponentChannel", var7.toString());
               int var4 = (Integer)var8.get("loadingUnitId");
               String var9 = (String)var8.get("componentName");
               var6.hashCode();
               int var5 = var6.hashCode();
               byte var3 = -1;
               switch (var5) {
                  case -1004447972:
                     if (var6.equals("uninstallDeferredComponent")) {
                        var3 = 0;
                     }
                     break;
                  case 399701758:
                     if (var6.equals("getDeferredComponentInstallState")) {
                        var3 = 1;
                     }
                     break;
                  case 520962947:
                     if (var6.equals("installDeferredComponent")) {
                        var3 = 2;
                     }
               }

               switch (var3) {
                  case 0:
                     this.this$0.deferredComponentManager.uninstallDeferredComponent(var4, var9);
                     var2x.success(null);
                     break;
                  case 1:
                     var2x.success(this.this$0.deferredComponentManager.getDeferredComponentInstallState(var4, var9));
                     break;
                  case 2:
                     this.this$0.deferredComponentManager.installDeferredComponent(var4, var9);
                     if (!this.this$0.componentNameToResults.containsKey(var9)) {
                        this.this$0.componentNameToResults.put(var9, new ArrayList<>());
                     }

                     this.this$0.componentNameToResults.get(var9).add(var2x);
                     break;
                  default:
                     var2x.notImplemented();
               }
            }
         }
      };
      this.parsingMethodHandler = var2;
      MethodChannel var3 = new MethodChannel(var1, "flutter/deferredcomponent", StandardMethodCodec.INSTANCE);
      this.channel = var3;
      var3.setMethodCallHandler(var2);
      this.deferredComponentManager = FlutterInjector.instance().deferredComponentManager();
      this.componentNameToResults = new HashMap<>();
   }

   public void completeInstallError(String var1, String var2) {
      if (this.componentNameToResults.containsKey(var1)) {
         Iterator var3 = this.componentNameToResults.get(var1).iterator();

         while (var3.hasNext()) {
            ((MethodChannel.Result)var3.next()).error("DeferredComponent Install failure", var2, null);
         }

         this.componentNameToResults.get(var1).clear();
      }
   }

   public void completeInstallSuccess(String var1) {
      if (this.componentNameToResults.containsKey(var1)) {
         Iterator var2 = this.componentNameToResults.get(var1).iterator();

         while (var2.hasNext()) {
            ((MethodChannel.Result)var2.next()).success(null);
         }

         this.componentNameToResults.get(var1).clear();
      }
   }

   public void setDeferredComponentManager(DeferredComponentManager var1) {
      this.deferredComponentManager = var1;
   }
}
