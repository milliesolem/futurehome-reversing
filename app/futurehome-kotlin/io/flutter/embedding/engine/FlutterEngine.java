package io.flutter.embedding.engine;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import io.flutter.FlutterInjector;
import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.deferredcomponents.DeferredComponentManager;
import io.flutter.embedding.engine.loader.FlutterLoader;
import io.flutter.embedding.engine.plugins.PluginRegistry;
import io.flutter.embedding.engine.plugins.activity.ActivityControlSurface;
import io.flutter.embedding.engine.plugins.broadcastreceiver.BroadcastReceiverControlSurface;
import io.flutter.embedding.engine.plugins.contentprovider.ContentProviderControlSurface;
import io.flutter.embedding.engine.plugins.service.ServiceControlSurface;
import io.flutter.embedding.engine.plugins.util.GeneratedPluginRegister;
import io.flutter.embedding.engine.renderer.FlutterRenderer;
import io.flutter.embedding.engine.systemchannels.AccessibilityChannel;
import io.flutter.embedding.engine.systemchannels.BackGestureChannel;
import io.flutter.embedding.engine.systemchannels.DeferredComponentChannel;
import io.flutter.embedding.engine.systemchannels.LifecycleChannel;
import io.flutter.embedding.engine.systemchannels.LocalizationChannel;
import io.flutter.embedding.engine.systemchannels.MouseCursorChannel;
import io.flutter.embedding.engine.systemchannels.NavigationChannel;
import io.flutter.embedding.engine.systemchannels.PlatformChannel;
import io.flutter.embedding.engine.systemchannels.ProcessTextChannel;
import io.flutter.embedding.engine.systemchannels.RestorationChannel;
import io.flutter.embedding.engine.systemchannels.SettingsChannel;
import io.flutter.embedding.engine.systemchannels.SpellCheckChannel;
import io.flutter.embedding.engine.systemchannels.SystemChannel;
import io.flutter.embedding.engine.systemchannels.TextInputChannel;
import io.flutter.plugin.localization.LocalizationPlugin;
import io.flutter.plugin.platform.PlatformViewsController;
import io.flutter.plugin.text.ProcessTextPlugin;
import io.flutter.util.ViewUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FlutterEngine implements ViewUtils.DisplayUpdater {
   private static final String TAG = "FlutterEngine";
   private final AccessibilityChannel accessibilityChannel;
   private final BackGestureChannel backGestureChannel;
   private final DartExecutor dartExecutor;
   private final DeferredComponentChannel deferredComponentChannel;
   private final FlutterEngine.EngineLifecycleListener engineLifecycleListener;
   private final Set<FlutterEngine.EngineLifecycleListener> engineLifecycleListeners = new HashSet<>();
   private final FlutterJNI flutterJNI;
   private final LifecycleChannel lifecycleChannel;
   private final LocalizationChannel localizationChannel;
   private final LocalizationPlugin localizationPlugin;
   private final MouseCursorChannel mouseCursorChannel;
   private final NavigationChannel navigationChannel;
   private final PlatformChannel platformChannel;
   private final PlatformViewsController platformViewsController;
   private final FlutterEngineConnectionRegistry pluginRegistry;
   private final ProcessTextChannel processTextChannel;
   private final FlutterRenderer renderer;
   private final RestorationChannel restorationChannel;
   private final SettingsChannel settingsChannel;
   private final SpellCheckChannel spellCheckChannel;
   private final SystemChannel systemChannel;
   private final TextInputChannel textInputChannel;

   public FlutterEngine(Context var1) {
      this(var1, null);
   }

   public FlutterEngine(Context var1, FlutterLoader var2, FlutterJNI var3) {
      this(var1, var2, var3, null, true);
   }

   public FlutterEngine(Context var1, FlutterLoader var2, FlutterJNI var3, PlatformViewsController var4, String[] var5, boolean var6) {
      this(var1, var2, var3, var4, var5, var6, false);
   }

   public FlutterEngine(Context var1, FlutterLoader var2, FlutterJNI var3, PlatformViewsController var4, String[] var5, boolean var6, boolean var7) {
      this(var1, var2, var3, var4, var5, var6, var7, null);
   }

   public FlutterEngine(
      Context var1, FlutterLoader var2, FlutterJNI var3, PlatformViewsController var4, String[] var5, boolean var6, boolean var7, FlutterEngineGroup var8
   ) {
      this.engineLifecycleListener = new FlutterEngine.EngineLifecycleListener(this) {
         final FlutterEngine this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onEngineWillDestroy() {
         }

         @Override
         public void onPreEngineRestart() {
            Log.v("FlutterEngine", "onPreEngineRestart()");
            Iterator var1x = this.this$0.engineLifecycleListeners.iterator();

            while (var1x.hasNext()) {
               ((FlutterEngine.EngineLifecycleListener)var1x.next()).onPreEngineRestart();
            }

            this.this$0.platformViewsController.onPreEngineRestart();
            this.this$0.restorationChannel.clearData();
         }
      };

      AssetManager var10;
      try {
         var10 = var1.createPackageContext(var1.getPackageName(), 0).getAssets();
      } catch (NameNotFoundException var14) {
         var10 = var1.getAssets();
      }

      FlutterInjector var11 = FlutterInjector.instance();
      FlutterJNI var9 = var3;
      if (var3 == null) {
         var9 = var11.getFlutterJNIFactory().provideFlutterJNI();
      }

      this.flutterJNI = var9;
      DartExecutor var12 = new DartExecutor(var9, var10);
      this.dartExecutor = var12;
      var12.onAttachedToJNI();
      DeferredComponentManager var18 = FlutterInjector.instance().deferredComponentManager();
      this.accessibilityChannel = new AccessibilityChannel(var12, var9);
      DeferredComponentChannel var16 = new DeferredComponentChannel(var12);
      this.deferredComponentChannel = var16;
      this.lifecycleChannel = new LifecycleChannel(var12);
      LocalizationChannel var13 = new LocalizationChannel(var12);
      this.localizationChannel = var13;
      this.mouseCursorChannel = new MouseCursorChannel(var12);
      this.navigationChannel = new NavigationChannel(var12);
      this.backGestureChannel = new BackGestureChannel(var12);
      this.platformChannel = new PlatformChannel(var12);
      this.processTextChannel = new ProcessTextChannel(var12, var1.getPackageManager());
      this.restorationChannel = new RestorationChannel(var12, var7);
      this.settingsChannel = new SettingsChannel(var12);
      this.spellCheckChannel = new SpellCheckChannel(var12);
      this.systemChannel = new SystemChannel(var12);
      this.textInputChannel = new TextInputChannel(var12);
      if (var18 != null) {
         var18.setDeferredComponentChannel(var16);
      }

      LocalizationPlugin var19 = new LocalizationPlugin(var1, var13);
      this.localizationPlugin = var19;
      FlutterLoader var17 = var2;
      if (var2 == null) {
         var17 = var11.flutterLoader();
      }

      if (!var9.isAttached()) {
         var17.startInitialization(var1.getApplicationContext());
         var17.ensureInitializationComplete(var1, var5);
      }

      var9.addEngineLifecycleListener(this.engineLifecycleListener);
      var9.setPlatformViewsController(var4);
      var9.setLocalizationPlugin(var19);
      var9.setDeferredComponentManager(var11.deferredComponentManager());
      if (!var9.isAttached()) {
         this.attachToJni();
      }

      this.renderer = new FlutterRenderer(var9);
      this.platformViewsController = var4;
      var4.onAttachedToJNI();
      FlutterEngineConnectionRegistry var15 = new FlutterEngineConnectionRegistry(var1.getApplicationContext(), this, var17, var8);
      this.pluginRegistry = var15;
      var19.sendLocalesToFlutter(var1.getResources().getConfiguration());
      if (var6 && var17.automaticallyRegisterPlugins()) {
         GeneratedPluginRegister.registerGeneratedPlugins(this);
      }

      ViewUtils.calculateMaximumDisplayMetrics(var1, this);
      var15.add(new ProcessTextPlugin(this.getProcessTextChannel()));
   }

   public FlutterEngine(Context var1, FlutterLoader var2, FlutterJNI var3, String[] var4, boolean var5) {
      this(var1, var2, var3, new PlatformViewsController(), var4, var5);
   }

   public FlutterEngine(Context var1, String[] var2) {
      this(var1, null, null, var2, true);
   }

   public FlutterEngine(Context var1, String[] var2, boolean var3) {
      this(var1, null, null, var2, var3);
   }

   public FlutterEngine(Context var1, String[] var2, boolean var3, boolean var4) {
      this(var1, null, null, new PlatformViewsController(), var2, var3, var4);
   }

   private void attachToJni() {
      Log.v("FlutterEngine", "Attaching to JNI.");
      this.flutterJNI.attachToNative();
      if (!this.isAttachedToJni()) {
         throw new RuntimeException("FlutterEngine failed to attach to its native Object reference.");
      }
   }

   private boolean isAttachedToJni() {
      return this.flutterJNI.isAttached();
   }

   public void addEngineLifecycleListener(FlutterEngine.EngineLifecycleListener var1) {
      this.engineLifecycleListeners.add(var1);
   }

   public void destroy() {
      Log.v("FlutterEngine", "Destroying.");
      Iterator var1 = this.engineLifecycleListeners.iterator();

      while (var1.hasNext()) {
         ((FlutterEngine.EngineLifecycleListener)var1.next()).onEngineWillDestroy();
      }

      this.pluginRegistry.destroy();
      this.platformViewsController.onDetachedFromJNI();
      this.dartExecutor.onDetachedFromJNI();
      this.flutterJNI.removeEngineLifecycleListener(this.engineLifecycleListener);
      this.flutterJNI.setDeferredComponentManager(null);
      this.flutterJNI.detachFromNativeAndReleaseResources();
      if (FlutterInjector.instance().deferredComponentManager() != null) {
         FlutterInjector.instance().deferredComponentManager().destroy();
         this.deferredComponentChannel.setDeferredComponentManager(null);
      }
   }

   public AccessibilityChannel getAccessibilityChannel() {
      return this.accessibilityChannel;
   }

   public ActivityControlSurface getActivityControlSurface() {
      return this.pluginRegistry;
   }

   public BackGestureChannel getBackGestureChannel() {
      return this.backGestureChannel;
   }

   public BroadcastReceiverControlSurface getBroadcastReceiverControlSurface() {
      return this.pluginRegistry;
   }

   public ContentProviderControlSurface getContentProviderControlSurface() {
      return this.pluginRegistry;
   }

   public DartExecutor getDartExecutor() {
      return this.dartExecutor;
   }

   public DeferredComponentChannel getDeferredComponentChannel() {
      return this.deferredComponentChannel;
   }

   public LifecycleChannel getLifecycleChannel() {
      return this.lifecycleChannel;
   }

   public LocalizationChannel getLocalizationChannel() {
      return this.localizationChannel;
   }

   public LocalizationPlugin getLocalizationPlugin() {
      return this.localizationPlugin;
   }

   public MouseCursorChannel getMouseCursorChannel() {
      return this.mouseCursorChannel;
   }

   public NavigationChannel getNavigationChannel() {
      return this.navigationChannel;
   }

   public PlatformChannel getPlatformChannel() {
      return this.platformChannel;
   }

   public PlatformViewsController getPlatformViewsController() {
      return this.platformViewsController;
   }

   public PluginRegistry getPlugins() {
      return this.pluginRegistry;
   }

   public ProcessTextChannel getProcessTextChannel() {
      return this.processTextChannel;
   }

   public FlutterRenderer getRenderer() {
      return this.renderer;
   }

   public RestorationChannel getRestorationChannel() {
      return this.restorationChannel;
   }

   public ServiceControlSurface getServiceControlSurface() {
      return this.pluginRegistry;
   }

   public SettingsChannel getSettingsChannel() {
      return this.settingsChannel;
   }

   public SpellCheckChannel getSpellCheckChannel() {
      return this.spellCheckChannel;
   }

   public SystemChannel getSystemChannel() {
      return this.systemChannel;
   }

   public TextInputChannel getTextInputChannel() {
      return this.textInputChannel;
   }

   public void removeEngineLifecycleListener(FlutterEngine.EngineLifecycleListener var1) {
      this.engineLifecycleListeners.remove(var1);
   }

   FlutterEngine spawn(Context var1, DartExecutor.DartEntrypoint var2, String var3, List<String> var4, PlatformViewsController var5, boolean var6, boolean var7) {
      if (this.isAttachedToJni()) {
         return new FlutterEngine(
            var1, null, this.flutterJNI.spawn(var2.dartEntrypointFunctionName, var2.dartEntrypointLibrary, var3, var4), var5, null, var6, var7
         );
      } else {
         throw new IllegalStateException("Spawn can only be called on a fully constructed FlutterEngine");
      }
   }

   @Override
   public void updateDisplayMetrics(float var1, float var2, float var3) {
      this.flutterJNI.updateDisplayMetrics(0, var1, var2, var3);
   }

   public interface EngineLifecycleListener {
      void onEngineWillDestroy();

      void onPreEngineRestart();
   }
}
