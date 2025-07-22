package io.flutter.embedding.engine;

import android.content.Context;
import io.flutter.FlutterInjector;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.loader.FlutterLoader;
import io.flutter.plugin.platform.PlatformViewsController;
import java.util.ArrayList;
import java.util.List;

public class FlutterEngineGroup {
   final List<FlutterEngine> activeEngines = new ArrayList<>();

   public FlutterEngineGroup(Context var1) {
      this(var1, null);
   }

   public FlutterEngineGroup(Context var1, String[] var2) {
      FlutterLoader var3 = FlutterInjector.instance().flutterLoader();
      if (!var3.initialized()) {
         var3.startInitialization(var1.getApplicationContext());
         var3.ensureInitializationComplete(var1.getApplicationContext(), var2);
      }
   }

   public FlutterEngine createAndRunDefaultEngine(Context var1) {
      return this.createAndRunEngine(var1, null);
   }

   public FlutterEngine createAndRunEngine(Context var1, DartExecutor.DartEntrypoint var2) {
      return this.createAndRunEngine(var1, var2, null);
   }

   public FlutterEngine createAndRunEngine(Context var1, DartExecutor.DartEntrypoint var2, String var3) {
      return this.createAndRunEngine(new FlutterEngineGroup.Options(var1).setDartEntrypoint(var2).setInitialRoute(var3));
   }

   public FlutterEngine createAndRunEngine(FlutterEngineGroup.Options var1) {
      Context var8 = var1.getContext();
      DartExecutor.DartEntrypoint var5 = var1.getDartEntrypoint();
      String var6 = var1.getInitialRoute();
      List var7 = var1.getDartEntrypointArgs();
      PlatformViewsController var4 = var1.getPlatformViewsController();
      if (var4 == null) {
         var4 = new PlatformViewsController();
      }

      boolean var3 = var1.getAutomaticallyRegisterPlugins();
      boolean var2 = var1.getWaitForRestorationData();
      DartExecutor.DartEntrypoint var9;
      if (var5 == null) {
         var9 = DartExecutor.DartEntrypoint.createDefault();
      } else {
         var9 = var5;
      }

      FlutterEngine var10;
      if (this.activeEngines.size() == 0) {
         FlutterEngine var11 = this.createEngine(var8, var4, var3, var2);
         if (var6 != null) {
            var11.getNavigationChannel().setInitialRoute(var6);
         }

         var11.getDartExecutor().executeDartEntrypoint(var9, var7);
         var10 = var11;
      } else {
         var10 = this.activeEngines.get(0).spawn(var8, var9, var6, var7, var4, var3, var2);
      }

      this.activeEngines.add(var10);
      var10.addEngineLifecycleListener(new FlutterEngine.EngineLifecycleListener(this, var10) {
         final FlutterEngineGroup this$0;
         final FlutterEngine val$engineToCleanUpOnDestroy;

         {
            this.this$0 = var1;
            this.val$engineToCleanUpOnDestroy = var2x;
         }

         @Override
         public void onEngineWillDestroy() {
            this.this$0.activeEngines.remove(this.val$engineToCleanUpOnDestroy);
         }

         @Override
         public void onPreEngineRestart() {
         }
      });
      return var10;
   }

   FlutterEngine createEngine(Context var1, PlatformViewsController var2, boolean var3, boolean var4) {
      return new FlutterEngine(var1, null, null, var2, null, var3, var4, this);
   }

   public static class Options {
      private boolean automaticallyRegisterPlugins = true;
      private Context context;
      private DartExecutor.DartEntrypoint dartEntrypoint;
      private List<String> dartEntrypointArgs;
      private String initialRoute;
      private PlatformViewsController platformViewsController;
      private boolean waitForRestorationData = false;

      public Options(Context var1) {
         this.context = var1;
      }

      public boolean getAutomaticallyRegisterPlugins() {
         return this.automaticallyRegisterPlugins;
      }

      public Context getContext() {
         return this.context;
      }

      public DartExecutor.DartEntrypoint getDartEntrypoint() {
         return this.dartEntrypoint;
      }

      public List<String> getDartEntrypointArgs() {
         return this.dartEntrypointArgs;
      }

      public String getInitialRoute() {
         return this.initialRoute;
      }

      public PlatformViewsController getPlatformViewsController() {
         return this.platformViewsController;
      }

      public boolean getWaitForRestorationData() {
         return this.waitForRestorationData;
      }

      public FlutterEngineGroup.Options setAutomaticallyRegisterPlugins(boolean var1) {
         this.automaticallyRegisterPlugins = var1;
         return this;
      }

      public FlutterEngineGroup.Options setDartEntrypoint(DartExecutor.DartEntrypoint var1) {
         this.dartEntrypoint = var1;
         return this;
      }

      public FlutterEngineGroup.Options setDartEntrypointArgs(List<String> var1) {
         this.dartEntrypointArgs = var1;
         return this;
      }

      public FlutterEngineGroup.Options setInitialRoute(String var1) {
         this.initialRoute = var1;
         return this;
      }

      public FlutterEngineGroup.Options setPlatformViewsController(PlatformViewsController var1) {
         this.platformViewsController = var1;
         return this;
      }

      public FlutterEngineGroup.Options setWaitForRestorationData(boolean var1) {
         this.waitForRestorationData = var1;
         return this;
      }
   }
}
