package io.flutter.view;

import android.app.Activity;
import android.content.Context;
import io.flutter.Log;
import io.flutter.app.FlutterPluginRegistry;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.renderer.FlutterUiDisplayListener;
import io.flutter.plugin.common.BinaryMessenger;
import java.nio.ByteBuffer;

@Deprecated
public class FlutterNativeView implements BinaryMessenger {
   private static final String TAG = "FlutterNativeView";
   private boolean applicationIsRunning;
   private final DartExecutor dartExecutor;
   private final FlutterUiDisplayListener flutterUiDisplayListener;
   private final Context mContext;
   private final FlutterJNI mFlutterJNI;
   private FlutterView mFlutterView;
   private final FlutterPluginRegistry mPluginRegistry;

   public FlutterNativeView(Context var1) {
      this(var1, false);
   }

   public FlutterNativeView(Context var1, boolean var2) {
      FlutterUiDisplayListener var4 = new FlutterUiDisplayListener(this) {
         final FlutterNativeView this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onFlutterUiDisplayed() {
            if (this.this$0.mFlutterView != null) {
               this.this$0.mFlutterView.onFirstFrame();
            }
         }

         @Override
         public void onFlutterUiNoLongerDisplayed() {
         }
      };
      this.flutterUiDisplayListener = var4;
      if (var2) {
         Log.w("FlutterNativeView", "'isBackgroundView' is no longer supported and will be ignored");
      }

      this.mContext = var1;
      this.mPluginRegistry = new FlutterPluginRegistry(this, var1);
      FlutterJNI var3 = new FlutterJNI();
      this.mFlutterJNI = var3;
      var3.addIsDisplayingFlutterUiListener(var4);
      this.dartExecutor = new DartExecutor(var3, var1.getAssets());
      var3.addEngineLifecycleListener(new FlutterNativeView.EngineLifecycleListenerImpl(this));
      this.attach(this);
      this.assertAttached();
   }

   private void attach(FlutterNativeView var1) {
      this.mFlutterJNI.attachToNative();
      this.dartExecutor.onAttachedToJNI();
   }

   @Deprecated
   public static String getObservatoryUri() {
      return FlutterJNI.getVMServiceUri();
   }

   public static String getVMServiceUri() {
      return FlutterJNI.getVMServiceUri();
   }

   public void assertAttached() {
      if (!this.isAttached()) {
         throw new AssertionError("Platform view is not attached");
      }
   }

   public void attachViewAndActivity(FlutterView var1, Activity var2) {
      this.mFlutterView = var1;
      this.mPluginRegistry.attach(var1, var2);
   }

   public void destroy() {
      this.mPluginRegistry.destroy();
      this.dartExecutor.onDetachedFromJNI();
      this.mFlutterView = null;
      this.mFlutterJNI.removeIsDisplayingFlutterUiListener(this.flutterUiDisplayListener);
      this.mFlutterJNI.detachFromNativeAndReleaseResources();
      this.applicationIsRunning = false;
   }

   public void detachFromFlutterView() {
      this.mPluginRegistry.detach();
      this.mFlutterView = null;
   }

   @Override
   public void disableBufferingIncomingMessages() {
   }

   @Override
   public void enableBufferingIncomingMessages() {
   }

   public DartExecutor getDartExecutor() {
      return this.dartExecutor;
   }

   FlutterJNI getFlutterJNI() {
      return this.mFlutterJNI;
   }

   public FlutterPluginRegistry getPluginRegistry() {
      return this.mPluginRegistry;
   }

   public boolean isApplicationRunning() {
      return this.applicationIsRunning;
   }

   public boolean isAttached() {
      return this.mFlutterJNI.isAttached();
   }

   @Override
   public BinaryMessenger.TaskQueue makeBackgroundTaskQueue(BinaryMessenger.TaskQueueOptions var1) {
      return this.dartExecutor.getBinaryMessenger().makeBackgroundTaskQueue(var1);
   }

   public void runFromBundle(FlutterRunArguments var1) {
      if (var1.entrypoint != null) {
         this.assertAttached();
         if (!this.applicationIsRunning) {
            this.mFlutterJNI
               .runBundleAndSnapshotFromLibrary(var1.bundlePath, var1.entrypoint, var1.libraryPath, this.mContext.getResources().getAssets(), null);
            this.applicationIsRunning = true;
         } else {
            throw new AssertionError("This Flutter engine instance is already running an application");
         }
      } else {
         throw new AssertionError("An entrypoint must be specified");
      }
   }

   @Override
   public void send(String var1, ByteBuffer var2) {
      this.dartExecutor.getBinaryMessenger().send(var1, var2);
   }

   @Override
   public void send(String var1, ByteBuffer var2, BinaryMessenger.BinaryReply var3) {
      if (!this.isAttached()) {
         StringBuilder var4 = new StringBuilder("FlutterView.send called on a detached view, channel=");
         var4.append(var1);
         Log.d("FlutterNativeView", var4.toString());
      } else {
         this.dartExecutor.getBinaryMessenger().send(var1, var2, var3);
      }
   }

   @Override
   public void setMessageHandler(String var1, BinaryMessenger.BinaryMessageHandler var2) {
      this.dartExecutor.getBinaryMessenger().setMessageHandler(var1, var2);
   }

   @Override
   public void setMessageHandler(String var1, BinaryMessenger.BinaryMessageHandler var2, BinaryMessenger.TaskQueue var3) {
      this.dartExecutor.getBinaryMessenger().setMessageHandler(var1, var2, var3);
   }

   private final class EngineLifecycleListenerImpl implements FlutterEngine.EngineLifecycleListener {
      final FlutterNativeView this$0;

      private EngineLifecycleListenerImpl(FlutterNativeView var1) {
         this.this$0 = var1;
      }

      @Override
      public void onEngineWillDestroy() {
      }

      @Override
      public void onPreEngineRestart() {
         if (this.this$0.mFlutterView != null) {
            this.this$0.mFlutterView.resetAccessibilityTree();
         }

         if (this.this$0.mPluginRegistry != null) {
            this.this$0.mPluginRegistry.onPreEngineRestart();
         }
      }
   }
}
