package io.flutter.embedding.engine;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder.Source;
import android.os.Looper;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Surface;
import androidx.core.util.HalfKt..ExternalSyntheticApiModelOutline0;
import com.getkeepsafe.relinker.ReLinker;
import io.flutter.Log;
import io.flutter.embedding.engine.dart.PlatformMessageHandler;
import io.flutter.embedding.engine.deferredcomponents.DeferredComponentManager;
import io.flutter.embedding.engine.mutatorsstack.FlutterMutatorsStack;
import io.flutter.embedding.engine.renderer.FlutterUiDisplayListener;
import io.flutter.embedding.engine.renderer.SurfaceTextureWrapper;
import io.flutter.embedding.engine.systemchannels.SettingsChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.localization.LocalizationPlugin;
import io.flutter.plugin.platform.PlatformViewsController;
import io.flutter.util.Preconditions;
import io.flutter.view.AccessibilityBridge;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import io.flutter.view.FlutterCallbackInformation;
import io.flutter.view.TextureRegistry;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Locale.Builder;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FlutterJNI {
   private static final String TAG = "FlutterJNI";
   private static FlutterJNI.AsyncWaitForVsyncDelegate asyncWaitForVsyncDelegate;
   private static float displayDensity;
   private static float displayHeight;
   private static float displayWidth;
   private static boolean initCalled;
   private static boolean loadLibraryCalled;
   private static boolean prefetchDefaultFontManagerCalled;
   private static float refreshRateFPS;
   private static String vmServiceUri;
   private FlutterJNI.AccessibilityDelegate accessibilityDelegate;
   private DeferredComponentManager deferredComponentManager;
   private final Set<FlutterEngine.EngineLifecycleListener> engineLifecycleListeners;
   private final Set<FlutterUiDisplayListener> flutterUiDisplayListeners;
   private LocalizationPlugin localizationPlugin;
   private final Looper mainLooper;
   private Long nativeShellHolderId;
   private PlatformMessageHandler platformMessageHandler;
   private PlatformViewsController platformViewsController;
   private ReentrantReadWriteLock shellHolderLock = new ReentrantReadWriteLock();

   public FlutterJNI() {
      this.engineLifecycleListeners = new CopyOnWriteArraySet<>();
      this.flutterUiDisplayListeners = new CopyOnWriteArraySet<>();
      this.mainLooper = Looper.getMainLooper();
   }

   private static void asyncWaitForVsync(long var0) {
      FlutterJNI.AsyncWaitForVsyncDelegate var2 = asyncWaitForVsyncDelegate;
      if (var2 != null) {
         var2.asyncWaitForVsync(var0);
      } else {
         throw new IllegalStateException("An AsyncWaitForVsyncDelegate must be registered with FlutterJNI before asyncWaitForVsync() is invoked.");
      }
   }

   public static Bitmap decodeImage(ByteBuffer var0, long var1) {
      if (VERSION.SDK_INT >= 28) {
         Source var3 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var0);

         try {
            FlutterJNI$$ExternalSyntheticLambda4 var5 = new FlutterJNI$$ExternalSyntheticLambda4(var1);
            return ExternalSyntheticApiModelOutline0.m(var3, var5);
         } catch (IOException var4) {
            Log.e("FlutterJNI", "Failed to decode image", var4);
         }
      }

      return null;
   }

   private void ensureAttachedToNative() {
      if (this.nativeShellHolderId == null) {
         throw new RuntimeException("Cannot execute operation because FlutterJNI is not attached to native.");
      }
   }

   private void ensureNotAttachedToNative() {
      if (this.nativeShellHolderId != null) {
         throw new RuntimeException("Cannot execute operation because FlutterJNI is attached to native.");
      }
   }

   private void ensureRunningOnMainThread() {
      if (Looper.myLooper() != this.mainLooper) {
         StringBuilder var1 = new StringBuilder("Methods marked with @UiThread must be executed on the main thread. Current thread: ");
         var1.append(Thread.currentThread().getName());
         throw new RuntimeException(var1.toString());
      }
   }

   @Deprecated
   public static String getObservatoryUri() {
      return vmServiceUri;
   }

   public static String getVMServiceUri() {
      return vmServiceUri;
   }

   private void handlePlatformMessageResponse(int var1, ByteBuffer var2) {
      PlatformMessageHandler var3 = this.platformMessageHandler;
      if (var3 != null) {
         var3.handlePlatformMessageResponse(var1, var2);
      }
   }

   private native long nativeAttach(FlutterJNI var1);

   private native void nativeCleanupMessageData(long var1);

   private native void nativeDeferredComponentInstallFailure(int var1, String var2, boolean var3);

   private native void nativeDestroy(long var1);

   private native void nativeDispatchEmptyPlatformMessage(long var1, String var3, int var4);

   private native void nativeDispatchPlatformMessage(long var1, String var3, ByteBuffer var4, int var5, int var6);

   private native void nativeDispatchPointerDataPacket(long var1, ByteBuffer var3, int var4);

   private native void nativeDispatchSemanticsAction(long var1, int var3, int var4, ByteBuffer var5, int var6);

   private native boolean nativeFlutterTextUtilsIsEmoji(int var1);

   private native boolean nativeFlutterTextUtilsIsEmojiModifier(int var1);

   private native boolean nativeFlutterTextUtilsIsEmojiModifierBase(int var1);

   private native boolean nativeFlutterTextUtilsIsRegionalIndicator(int var1);

   private native boolean nativeFlutterTextUtilsIsVariationSelector(int var1);

   private native Bitmap nativeGetBitmap(long var1);

   private native boolean nativeGetIsSoftwareRenderingEnabled();

   public static native void nativeImageHeaderCallback(long var0, int var2, int var3);

   private static native void nativeInit(Context var0, String[] var1, String var2, String var3, String var4, long var5);

   private native void nativeInvokePlatformMessageEmptyResponseCallback(long var1, int var3);

   private native void nativeInvokePlatformMessageResponseCallback(long var1, int var3, ByteBuffer var4, int var5);

   private native void nativeLoadDartDeferredLibrary(long var1, int var3, String[] var4);

   @Deprecated
   public static native FlutterCallbackInformation nativeLookupCallbackInformation(long var0);

   private native void nativeMarkTextureFrameAvailable(long var1, long var3);

   private native void nativeNotifyLowMemoryWarning(long var1);

   private native void nativeOnVsync(long var1, long var3, long var5);

   private static native void nativePrefetchDefaultFontManager();

   private native void nativeRegisterImageTexture(long var1, long var3, WeakReference<TextureRegistry.ImageConsumer> var5);

   private native void nativeRegisterTexture(long var1, long var3, WeakReference<SurfaceTextureWrapper> var5);

   private native void nativeRunBundleAndSnapshotFromLibrary(long var1, String var3, String var4, String var5, AssetManager var6, List<String> var7);

   private native void nativeScheduleFrame(long var1);

   private native void nativeSetAccessibilityFeatures(long var1, int var3);

   private native void nativeSetSemanticsEnabled(long var1, boolean var3);

   private native void nativeSetViewportMetrics(
      long var1,
      float var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      int var16,
      int var17,
      int var18,
      int[] var19,
      int[] var20,
      int[] var21
   );

   private native boolean nativeShouldDisableAHB();

   private native FlutterJNI nativeSpawn(long var1, String var3, String var4, String var5, List<String> var6);

   private native void nativeSurfaceChanged(long var1, int var3, int var4);

   private native void nativeSurfaceCreated(long var1, Surface var3);

   private native void nativeSurfaceDestroyed(long var1);

   private native void nativeSurfaceWindowChanged(long var1, Surface var3);

   private native void nativeUnregisterTexture(long var1, long var3);

   private native void nativeUpdateDisplayMetrics(long var1);

   private native void nativeUpdateJavaAssetManager(long var1, AssetManager var3, String var4);

   private native void nativeUpdateRefreshRate(float var1);

   private void onPreEngineRestart() {
      Iterator var1 = this.engineLifecycleListeners.iterator();

      while (var1.hasNext()) {
         ((FlutterEngine.EngineLifecycleListener)var1.next()).onPreEngineRestart();
      }
   }

   private void updateCustomAccessibilityActions(ByteBuffer var1, String[] var2) {
      this.ensureRunningOnMainThread();
      FlutterJNI.AccessibilityDelegate var3 = this.accessibilityDelegate;
      if (var3 != null) {
         var3.updateCustomAccessibilityActions(var1, var2);
      }
   }

   private void updateSemantics(ByteBuffer var1, String[] var2, ByteBuffer[] var3) {
      this.ensureRunningOnMainThread();
      FlutterJNI.AccessibilityDelegate var4 = this.accessibilityDelegate;
      if (var4 != null) {
         var4.updateSemantics(var1, var2, var3);
      }
   }

   public boolean ShouldDisableAHB() {
      return this.nativeShouldDisableAHB();
   }

   public void addEngineLifecycleListener(FlutterEngine.EngineLifecycleListener var1) {
      this.ensureRunningOnMainThread();
      this.engineLifecycleListeners.add(var1);
   }

   public void addIsDisplayingFlutterUiListener(FlutterUiDisplayListener var1) {
      this.ensureRunningOnMainThread();
      this.flutterUiDisplayListeners.add(var1);
   }

   public void attachToNative() {
      this.ensureRunningOnMainThread();
      this.ensureNotAttachedToNative();
      this.shellHolderLock.writeLock().lock();

      try {
         this.nativeShellHolderId = this.performNativeAttach(this);
      } finally {
         this.shellHolderLock.writeLock().unlock();
      }
   }

   public void cleanupMessageData(long var1) {
      this.nativeCleanupMessageData(var1);
   }

   public String[] computePlatformResolvedLocale(String[] var1) {
      if (this.localizationPlugin == null) {
         return new String[0];
      } else {
         ArrayList var5 = new ArrayList();

         for (byte var2 = 0; var2 < var1.length; var2 += 3) {
            String var3 = var1[var2];
            String var7 = var1[var2 + 1];
            String var4 = var1[var2 + 2];
            Builder var6 = new Builder();
            if (!var3.isEmpty()) {
               var6.setLanguage(var3);
            }

            if (!var7.isEmpty()) {
               var6.setRegion(var7);
            }

            if (!var4.isEmpty()) {
               var6.setScript(var4);
            }

            var5.add(var6.build());
         }

         Locale var8 = this.localizationPlugin.resolveNativeLocale(var5);
         return var8 == null ? new String[0] : new String[]{var8.getLanguage(), var8.getCountry(), var8.getScript()};
      }
   }

   public FlutterOverlaySurface createOverlaySurface() {
      this.ensureRunningOnMainThread();
      PlatformViewsController var1 = this.platformViewsController;
      if (var1 != null) {
         return var1.createOverlaySurface();
      } else {
         throw new RuntimeException("platformViewsController must be set before attempting to position an overlay surface");
      }
   }

   public void deferredComponentInstallFailure(int var1, String var2, boolean var3) {
      this.ensureRunningOnMainThread();
      this.nativeDeferredComponentInstallFailure(var1, var2, var3);
   }

   public void destroyOverlaySurfaces() {
      this.ensureRunningOnMainThread();
      PlatformViewsController var1 = this.platformViewsController;
      if (var1 != null) {
         var1.destroyOverlaySurfaces();
      } else {
         throw new RuntimeException("platformViewsController must be set before attempting to destroy an overlay surface");
      }
   }

   public void detachFromNativeAndReleaseResources() {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.shellHolderLock.writeLock().lock();

      try {
         this.nativeDestroy(this.nativeShellHolderId);
         this.nativeShellHolderId = null;
      } finally {
         this.shellHolderLock.writeLock().unlock();
      }
   }

   public void dispatchEmptyPlatformMessage(String var1, int var2) {
      this.ensureRunningOnMainThread();
      if (this.isAttached()) {
         this.nativeDispatchEmptyPlatformMessage(this.nativeShellHolderId, var1, var2);
      } else {
         StringBuilder var3 = new StringBuilder(
            "Tried to send a platform message to Flutter, but FlutterJNI was detached from native C++. Could not send. Channel: "
         );
         var3.append(var1);
         var3.append(". Response ID: ");
         var3.append(var2);
         Log.w("FlutterJNI", var3.toString());
      }
   }

   public void dispatchPlatformMessage(String var1, ByteBuffer var2, int var3, int var4) {
      this.ensureRunningOnMainThread();
      if (this.isAttached()) {
         this.nativeDispatchPlatformMessage(this.nativeShellHolderId, var1, var2, var3, var4);
      } else {
         StringBuilder var5 = new StringBuilder(
            "Tried to send a platform message to Flutter, but FlutterJNI was detached from native C++. Could not send. Channel: "
         );
         var5.append(var1);
         var5.append(". Response ID: ");
         var5.append(var4);
         Log.w("FlutterJNI", var5.toString());
      }
   }

   public void dispatchPointerDataPacket(ByteBuffer var1, int var2) {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeDispatchPointerDataPacket(this.nativeShellHolderId, var1, var2);
   }

   public void dispatchSemanticsAction(int var1, int var2, ByteBuffer var3, int var4) {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeDispatchSemanticsAction(this.nativeShellHolderId, var1, var2, var3, var4);
   }

   public void dispatchSemanticsAction(int var1, AccessibilityBridge.Action var2) {
      this.dispatchSemanticsAction(var1, var2, null);
   }

   public void dispatchSemanticsAction(int var1, AccessibilityBridge.Action var2, Object var3) {
      this.ensureAttachedToNative();
      int var4;
      if (var3 != null) {
         var3 = StandardMessageCodec.INSTANCE.encodeMessage(var3);
         var4 = var3.position();
      } else {
         var3 = null;
         var4 = 0;
      }

      this.dispatchSemanticsAction(var1, var2.value, var3, var4);
   }

   public Bitmap getBitmap() {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      return this.nativeGetBitmap(this.nativeShellHolderId);
   }

   public boolean getIsSoftwareRenderingEnabled() {
      return this.nativeGetIsSoftwareRenderingEnabled();
   }

   public float getScaledFontSize(float var1, int var2) {
      DisplayMetrics var3 = SettingsChannel.getPastDisplayMetrics(var2);
      if (var3 == null) {
         StringBuilder var4 = new StringBuilder("getScaledFontSize called with configurationId ");
         var4.append(String.valueOf(var2));
         var4.append(", which can't be found.");
         Log.e("FlutterJNI", var4.toString());
         return -1.0F;
      } else {
         return TypedValue.applyDimension(2, var1, var3) / var3.density;
      }
   }

   public void handlePlatformMessage(String var1, ByteBuffer var2, int var3, long var4) {
      PlatformMessageHandler var6 = this.platformMessageHandler;
      if (var6 != null) {
         var6.handleMessageFromDart(var1, var2, var3, var4);
      } else {
         this.nativeCleanupMessageData(var4);
      }
   }

   public void init(Context var1, String[] var2, String var3, String var4, String var5, long var6) {
      if (initCalled) {
         Log.w("FlutterJNI", "FlutterJNI.init called more than once");
      }

      nativeInit(var1, var2, var3, var4, var5, var6);
      initCalled = true;
   }

   public void invokePlatformMessageEmptyResponseCallback(int var1) {
      this.shellHolderLock.readLock().lock();

      try {
         if (this.isAttached()) {
            this.nativeInvokePlatformMessageEmptyResponseCallback(this.nativeShellHolderId, var1);
         } else {
            StringBuilder var2 = new StringBuilder(
               "Tried to send a platform message response, but FlutterJNI was detached from native C++. Could not send. Response ID: "
            );
            var2.append(var1);
            Log.w("FlutterJNI", var2.toString());
         }
      } finally {
         this.shellHolderLock.readLock().unlock();
      }
   }

   public void invokePlatformMessageResponseCallback(int var1, ByteBuffer var2, int var3) {
      if (var2.isDirect()) {
         this.shellHolderLock.readLock().lock();

         try {
            if (this.isAttached()) {
               this.nativeInvokePlatformMessageResponseCallback(this.nativeShellHolderId, var1, var2, var3);
            } else {
               StringBuilder var6 = new StringBuilder(
                  "Tried to send a platform message response, but FlutterJNI was detached from native C++. Could not send. Response ID: "
               );
               var6.append(var1);
               Log.w("FlutterJNI", var6.toString());
            }
         } finally {
            this.shellHolderLock.readLock().unlock();
         }
      } else {
         throw new IllegalArgumentException("Expected a direct ByteBuffer.");
      }
   }

   public boolean isAttached() {
      boolean var1;
      if (this.nativeShellHolderId != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isCodePointEmoji(int var1) {
      return this.nativeFlutterTextUtilsIsEmoji(var1);
   }

   public boolean isCodePointEmojiModifier(int var1) {
      return this.nativeFlutterTextUtilsIsEmojiModifier(var1);
   }

   public boolean isCodePointEmojiModifierBase(int var1) {
      return this.nativeFlutterTextUtilsIsEmojiModifierBase(var1);
   }

   public boolean isCodePointRegionalIndicator(int var1) {
      return this.nativeFlutterTextUtilsIsRegionalIndicator(var1);
   }

   public boolean isCodePointVariantSelector(int var1) {
      return this.nativeFlutterTextUtilsIsVariationSelector(var1);
   }

   public void loadDartDeferredLibrary(int var1, String[] var2) {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeLoadDartDeferredLibrary(this.nativeShellHolderId, var1, var2);
   }

   public void loadLibrary(Context var1) {
      if (loadLibraryCalled) {
         Log.w("FlutterJNI", "FlutterJNI.loadLibrary called more than once");
      }

      ReLinker.loadLibrary(var1, "flutter");
      loadLibraryCalled = true;
   }

   public void markTextureFrameAvailable(long var1) {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeMarkTextureFrameAvailable(this.nativeShellHolderId, var1);
   }

   public void notifyLowMemoryWarning() {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeNotifyLowMemoryWarning(this.nativeShellHolderId);
   }

   public void onBeginFrame() {
      this.ensureRunningOnMainThread();
      PlatformViewsController var1 = this.platformViewsController;
      if (var1 != null) {
         var1.onBeginFrame();
      } else {
         throw new RuntimeException("platformViewsController must be set before attempting to begin the frame");
      }
   }

   public void onDisplayOverlaySurface(int var1, int var2, int var3, int var4, int var5) {
      this.ensureRunningOnMainThread();
      PlatformViewsController var6 = this.platformViewsController;
      if (var6 != null) {
         var6.onDisplayOverlaySurface(var1, var2, var3, var4, var5);
      } else {
         throw new RuntimeException("platformViewsController must be set before attempting to position an overlay surface");
      }
   }

   public void onDisplayPlatformView(int var1, int var2, int var3, int var4, int var5, int var6, int var7, FlutterMutatorsStack var8) {
      this.ensureRunningOnMainThread();
      PlatformViewsController var9 = this.platformViewsController;
      if (var9 != null) {
         var9.onDisplayPlatformView(var1, var2, var3, var4, var5, var6, var7, var8);
      } else {
         throw new RuntimeException("platformViewsController must be set before attempting to position a platform view");
      }
   }

   public void onEndFrame() {
      this.ensureRunningOnMainThread();
      PlatformViewsController var1 = this.platformViewsController;
      if (var1 != null) {
         var1.onEndFrame();
      } else {
         throw new RuntimeException("platformViewsController must be set before attempting to end the frame");
      }
   }

   public void onFirstFrame() {
      this.ensureRunningOnMainThread();
      Iterator var1 = this.flutterUiDisplayListeners.iterator();

      while (var1.hasNext()) {
         ((FlutterUiDisplayListener)var1.next()).onFlutterUiDisplayed();
      }
   }

   void onRenderingStopped() {
      this.ensureRunningOnMainThread();
      Iterator var1 = this.flutterUiDisplayListeners.iterator();

      while (var1.hasNext()) {
         ((FlutterUiDisplayListener)var1.next()).onFlutterUiNoLongerDisplayed();
      }
   }

   public void onSurfaceChanged(int var1, int var2) {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeSurfaceChanged(this.nativeShellHolderId, var1, var2);
   }

   public void onSurfaceCreated(Surface var1) {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeSurfaceCreated(this.nativeShellHolderId, var1);
   }

   public void onSurfaceDestroyed() {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.onRenderingStopped();
      this.nativeSurfaceDestroyed(this.nativeShellHolderId);
   }

   public void onSurfaceWindowChanged(Surface var1) {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeSurfaceWindowChanged(this.nativeShellHolderId, var1);
   }

   public void onVsync(long var1, long var3, long var5) {
      this.nativeOnVsync(var1, var3, var5);
   }

   public long performNativeAttach(FlutterJNI var1) {
      return this.nativeAttach(var1);
   }

   public void prefetchDefaultFontManager() {
      if (prefetchDefaultFontManagerCalled) {
         Log.w("FlutterJNI", "FlutterJNI.prefetchDefaultFontManager called more than once");
      }

      nativePrefetchDefaultFontManager();
      prefetchDefaultFontManagerCalled = true;
   }

   public void registerImageTexture(long var1, TextureRegistry.ImageConsumer var3) {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeRegisterImageTexture(this.nativeShellHolderId, var1, new WeakReference<>(var3));
   }

   public void registerTexture(long var1, SurfaceTextureWrapper var3) {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeRegisterTexture(this.nativeShellHolderId, var1, new WeakReference<>(var3));
   }

   public void removeEngineLifecycleListener(FlutterEngine.EngineLifecycleListener var1) {
      this.ensureRunningOnMainThread();
      this.engineLifecycleListeners.remove(var1);
   }

   public void removeIsDisplayingFlutterUiListener(FlutterUiDisplayListener var1) {
      this.ensureRunningOnMainThread();
      this.flutterUiDisplayListeners.remove(var1);
   }

   public void requestDartDeferredLibrary(int var1) {
      DeferredComponentManager var2 = this.deferredComponentManager;
      if (var2 != null) {
         var2.installDeferredComponent(var1, null);
      } else {
         Log.e("FlutterJNI", "No DeferredComponentManager found. Android setup must be completed before using split AOT deferred components.");
      }
   }

   public void runBundleAndSnapshotFromLibrary(String var1, String var2, String var3, AssetManager var4, List<String> var5) {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeRunBundleAndSnapshotFromLibrary(this.nativeShellHolderId, var1, var2, var3, var4, var5);
   }

   public void scheduleFrame() {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeScheduleFrame(this.nativeShellHolderId);
   }

   public void setAccessibilityDelegate(FlutterJNI.AccessibilityDelegate var1) {
      this.ensureRunningOnMainThread();
      this.accessibilityDelegate = var1;
   }

   public void setAccessibilityFeatures(int var1) {
      this.ensureRunningOnMainThread();
      if (this.isAttached()) {
         this.setAccessibilityFeaturesInNative(var1);
      }
   }

   public void setAccessibilityFeaturesInNative(int var1) {
      this.nativeSetAccessibilityFeatures(this.nativeShellHolderId, var1);
   }

   public void setAsyncWaitForVsyncDelegate(FlutterJNI.AsyncWaitForVsyncDelegate var1) {
      asyncWaitForVsyncDelegate = var1;
   }

   public void setDeferredComponentManager(DeferredComponentManager var1) {
      this.ensureRunningOnMainThread();
      this.deferredComponentManager = var1;
      if (var1 != null) {
         var1.setJNI(this);
      }
   }

   public void setLocalizationPlugin(LocalizationPlugin var1) {
      this.ensureRunningOnMainThread();
      this.localizationPlugin = var1;
   }

   public void setPlatformMessageHandler(PlatformMessageHandler var1) {
      this.ensureRunningOnMainThread();
      this.platformMessageHandler = var1;
   }

   public void setPlatformViewsController(PlatformViewsController var1) {
      this.ensureRunningOnMainThread();
      this.platformViewsController = var1;
   }

   public void setRefreshRateFPS(float var1) {
      refreshRateFPS = var1;
      this.updateRefreshRate();
   }

   public void setSemanticsEnabled(boolean var1) {
      this.ensureRunningOnMainThread();
      if (this.isAttached()) {
         this.setSemanticsEnabledInNative(var1);
      }
   }

   public void setSemanticsEnabledInNative(boolean var1) {
      this.nativeSetSemanticsEnabled(this.nativeShellHolderId, var1);
   }

   public void setViewportMetrics(
      float var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      int var16,
      int[] var17,
      int[] var18,
      int[] var19
   ) {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeSetViewportMetrics(
         this.nativeShellHolderId, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19
      );
   }

   public FlutterJNI spawn(String var1, String var2, String var3, List<String> var4) {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      FlutterJNI var6 = this.nativeSpawn(this.nativeShellHolderId, var1, var2, var3, var4);
      Long var7 = var6.nativeShellHolderId;
      boolean var5;
      if (var7 != null && var7 != 0L) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkState(var5, "Failed to spawn new JNI connected shell from existing shell.");
      return var6;
   }

   public void unregisterTexture(long var1) {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeUnregisterTexture(this.nativeShellHolderId, var1);
   }

   public void updateDisplayMetrics(int var1, float var2, float var3, float var4) {
      displayWidth = var2;
      displayHeight = var3;
      displayDensity = var4;
      if (loadLibraryCalled) {
         this.nativeUpdateDisplayMetrics(this.nativeShellHolderId);
      }
   }

   public void updateJavaAssetManager(AssetManager var1, String var2) {
      this.ensureRunningOnMainThread();
      this.ensureAttachedToNative();
      this.nativeUpdateJavaAssetManager(this.nativeShellHolderId, var1, var2);
   }

   public void updateRefreshRate() {
      if (loadLibraryCalled) {
         this.nativeUpdateRefreshRate(refreshRateFPS);
      }
   }

   public interface AccessibilityDelegate {
      void updateCustomAccessibilityActions(ByteBuffer var1, String[] var2);

      void updateSemantics(ByteBuffer var1, String[] var2, ByteBuffer[] var3);
   }

   public interface AsyncWaitForVsyncDelegate {
      void asyncWaitForVsync(long var1);
   }

   public static class Factory {
      public FlutterJNI provideFlutterJNI() {
         return new FlutterJNI();
      }
   }
}
