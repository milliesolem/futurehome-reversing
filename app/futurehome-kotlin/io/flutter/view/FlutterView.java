package io.flutter.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.os.Handler;
import android.os.Build.VERSION;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.DisplayCutout;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewStructure;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.window.BackEvent;
import androidx.core.view.WindowInsetsCompat.Impl28..ExternalSyntheticApiModelOutline0;
import androidx.webkit.internal.ApiHelperForN..ExternalSyntheticApiModelOutline4;
import io.flutter.Log;
import io.flutter.app.FlutterPluginRegistry;
import io.flutter.embedding.android.AndroidTouchProcessor;
import io.flutter.embedding.android.KeyboardManager;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.renderer.FlutterRenderer;
import io.flutter.embedding.engine.renderer.SurfaceTextureWrapper;
import io.flutter.embedding.engine.systemchannels.AccessibilityChannel;
import io.flutter.embedding.engine.systemchannels.BackGestureChannel;
import io.flutter.embedding.engine.systemchannels.LifecycleChannel;
import io.flutter.embedding.engine.systemchannels.LocalizationChannel;
import io.flutter.embedding.engine.systemchannels.MouseCursorChannel;
import io.flutter.embedding.engine.systemchannels.NavigationChannel;
import io.flutter.embedding.engine.systemchannels.PlatformChannel;
import io.flutter.embedding.engine.systemchannels.SettingsChannel;
import io.flutter.embedding.engine.systemchannels.SystemChannel;
import io.flutter.embedding.engine.systemchannels.TextInputChannel;
import io.flutter.plugin.common.ActivityLifecycleListener;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.editing.TextInputPlugin;
import io.flutter.plugin.localization.LocalizationPlugin;
import io.flutter.plugin.mouse.MouseCursorPlugin;
import io.flutter.plugin.platform.PlatformPlugin;
import io.flutter.plugin.platform.PlatformViewsController;
import io.flutter.util.ViewUtils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Deprecated
public class FlutterView
   extends SurfaceView
   implements BinaryMessenger,
   TextureRegistry,
   MouseCursorPlugin.MouseCursorViewDelegate,
   KeyboardManager.ViewDelegate {
   private static final String TAG = "FlutterView";
   private final AndroidTouchProcessor androidTouchProcessor;
   private final BackGestureChannel backGestureChannel;
   private final DartExecutor dartExecutor;
   private boolean didRenderFirstFrame;
   private final FlutterRenderer flutterRenderer;
   private final LifecycleChannel lifecycleChannel;
   private final LocalizationChannel localizationChannel;
   private AccessibilityBridge mAccessibilityNodeProvider;
   private final List<ActivityLifecycleListener> mActivityLifecycleListeners;
   private final List<FlutterView.FirstFrameListener> mFirstFrameListeners;
   private final InputMethodManager mImm;
   private boolean mIsSoftwareRenderingEnabled;
   private final KeyboardManager mKeyboardManager;
   private final LocalizationPlugin mLocalizationPlugin;
   private final FlutterView.ViewportMetrics mMetrics;
   private final MouseCursorPlugin mMouseCursorPlugin;
   private FlutterNativeView mNativeView;
   private final android.view.SurfaceHolder.Callback mSurfaceCallback;
   private final TextInputPlugin mTextInputPlugin;
   private final NavigationChannel navigationChannel;
   private final AtomicLong nextTextureId = new AtomicLong(0L);
   private final AccessibilityBridge.OnAccessibilityChangeListener onAccessibilityChangeListener;
   private final PlatformChannel platformChannel;
   private final SettingsChannel settingsChannel;
   private final SystemChannel systemChannel;

   public FlutterView(Context var1) {
      this(var1, null);
   }

   public FlutterView(Context var1, AttributeSet var2) {
      this(var1, var2, null);
   }

   public FlutterView(Context var1, AttributeSet var2, FlutterNativeView var3) {
      super(var1, var2);
      this.mIsSoftwareRenderingEnabled = false;
      this.didRenderFirstFrame = false;
      this.onAccessibilityChangeListener = new AccessibilityBridge.OnAccessibilityChangeListener(this) {
         final FlutterView this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onAccessibilityChanged(boolean var1, boolean var2x) {
            this.this$0.resetWillNotDraw(var1, var2x);
         }
      };
      Activity var4 = ViewUtils.getActivity(this.getContext());
      if (var4 != null) {
         if (var3 == null) {
            this.mNativeView = new FlutterNativeView(var4.getApplicationContext());
         } else {
            this.mNativeView = var3;
         }

         DartExecutor var9 = this.mNativeView.getDartExecutor();
         this.dartExecutor = var9;
         FlutterRenderer var8 = new FlutterRenderer(this.mNativeView.getFlutterJNI());
         this.flutterRenderer = var8;
         this.mIsSoftwareRenderingEnabled = this.mNativeView.getFlutterJNI().getIsSoftwareRenderingEnabled();
         FlutterView.ViewportMetrics var5 = new FlutterView.ViewportMetrics();
         this.mMetrics = var5;
         var5.devicePixelRatio = var1.getResources().getDisplayMetrics().density;
         var5.physicalTouchSlop = ViewConfiguration.get(var1).getScaledTouchSlop();
         this.setFocusable(true);
         this.setFocusableInTouchMode(true);
         this.mNativeView.attachViewAndActivity(this, var4);
         android.view.SurfaceHolder.Callback var11 = new android.view.SurfaceHolder.Callback(this) {
            final FlutterView this$0;

            {
               this.this$0 = var1;
            }

            public void surfaceChanged(SurfaceHolder var1, int var2x, int var3x, int var4x) {
               this.this$0.assertAttached();
               this.this$0.mNativeView.getFlutterJNI().onSurfaceChanged(var3x, var4x);
            }

            public void surfaceCreated(SurfaceHolder var1) {
               this.this$0.assertAttached();
               this.this$0.mNativeView.getFlutterJNI().onSurfaceCreated(var1.getSurface());
            }

            public void surfaceDestroyed(SurfaceHolder var1) {
               this.this$0.assertAttached();
               this.this$0.mNativeView.getFlutterJNI().onSurfaceDestroyed();
            }
         };
         this.mSurfaceCallback = var11;
         this.getHolder().addCallback(var11);
         this.mActivityLifecycleListeners = new ArrayList<>();
         this.mFirstFrameListeners = new ArrayList<>();
         this.navigationChannel = new NavigationChannel(var9);
         this.backGestureChannel = new BackGestureChannel(var9);
         this.lifecycleChannel = new LifecycleChannel(var9);
         LocalizationChannel var12 = new LocalizationChannel(var9);
         this.localizationChannel = var12;
         PlatformChannel var6 = new PlatformChannel(var9);
         this.platformChannel = var6;
         this.systemChannel = new SystemChannel(var9);
         this.settingsChannel = new SettingsChannel(var9);
         this.addActivityLifecycleListener(new ActivityLifecycleListener(this, new PlatformPlugin(var4, var6)) {
            final FlutterView this$0;
            final PlatformPlugin val$platformPlugin;

            {
               this.this$0 = var1;
               this.val$platformPlugin = var2x;
            }

            @Override
            public void onPostResume() {
               this.val$platformPlugin.updateSystemUiOverlays();
            }
         });
         this.mImm = (InputMethodManager)this.getContext().getSystemService("input_method");
         PlatformViewsController var10 = this.mNativeView.getPluginRegistry().getPlatformViewsController();
         TextInputPlugin var13 = new TextInputPlugin(this, new TextInputChannel(var9), var10);
         this.mTextInputPlugin = var13;
         this.mKeyboardManager = new KeyboardManager(this);
         if (VERSION.SDK_INT >= 24) {
            this.mMouseCursorPlugin = new MouseCursorPlugin(this, new MouseCursorChannel(var9));
         } else {
            this.mMouseCursorPlugin = null;
         }

         LocalizationPlugin var7 = new LocalizationPlugin(var1, var12);
         this.mLocalizationPlugin = var7;
         this.androidTouchProcessor = new AndroidTouchProcessor(var8, false);
         var10.attachToFlutterRenderer(var8);
         this.mNativeView.getPluginRegistry().getPlatformViewsController().attachTextInputPlugin(var13);
         this.mNativeView.getFlutterJNI().setLocalizationPlugin(var7);
         var7.sendLocalesToFlutter(this.getResources().getConfiguration());
         this.sendUserPlatformSettingsToDart();
      } else {
         throw new IllegalArgumentException("Bad context");
      }
   }

   private FlutterView.ZeroSides calculateShouldZeroSides() {
      Context var3 = this.getContext();
      int var2 = var3.getResources().getConfiguration().orientation;
      int var1 = ((WindowManager)var3.getSystemService("window")).getDefaultDisplay().getRotation();
      if (var2 == 2) {
         if (var1 == 1) {
            return FlutterView.ZeroSides.RIGHT;
         }

         if (var1 == 3) {
            FlutterView.ZeroSides var4;
            if (VERSION.SDK_INT >= 23) {
               var4 = FlutterView.ZeroSides.LEFT;
            } else {
               var4 = FlutterView.ZeroSides.RIGHT;
            }

            return var4;
         }

         if (var1 == 0 || var1 == 2) {
            return FlutterView.ZeroSides.BOTH;
         }
      }

      return FlutterView.ZeroSides.NONE;
   }

   private int guessBottomKeyboardInset(WindowInsets var1) {
      int var2 = this.getRootView().getHeight();
      return var1.getSystemWindowInsetBottom() < var2 * 0.18 ? 0 : var1.getSystemWindowInsetBottom();
   }

   private boolean isAttached() {
      FlutterNativeView var2 = this.mNativeView;
      boolean var1;
      if (var2 != null && var2.isAttached()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private void postRun() {
   }

   private void preRun() {
      this.resetAccessibilityTree();
   }

   private void releaseAccessibilityNodeProvider() {
      AccessibilityBridge var1 = this.mAccessibilityNodeProvider;
      if (var1 != null) {
         var1.release();
         this.mAccessibilityNodeProvider = null;
      }
   }

   private void resetWillNotDraw(boolean var1, boolean var2) {
      boolean var3 = this.mIsSoftwareRenderingEnabled;
      boolean var4 = false;
      if (!var3) {
         var3 = var4;
         if (!var1) {
            var3 = var4;
            if (!var2) {
               var3 = true;
            }
         }

         this.setWillNotDraw(var3);
      } else {
         this.setWillNotDraw(false);
      }
   }

   private void sendUserPlatformSettingsToDart() {
      SettingsChannel.PlatformBrightness var1;
      if ((this.getResources().getConfiguration().uiMode & 48) == 32) {
         var1 = SettingsChannel.PlatformBrightness.dark;
      } else {
         var1 = SettingsChannel.PlatformBrightness.light;
      }

      this.settingsChannel
         .startMessage()
         .setTextScaleFactor(this.getResources().getConfiguration().fontScale)
         .setUse24HourFormat(DateFormat.is24HourFormat(this.getContext()))
         .setPlatformBrightness(var1)
         .send();
   }

   private void updateViewportMetrics() {
      if (this.isAttached()) {
         this.mNativeView
            .getFlutterJNI()
            .setViewportMetrics(
               this.mMetrics.devicePixelRatio,
               this.mMetrics.physicalWidth,
               this.mMetrics.physicalHeight,
               this.mMetrics.physicalViewPaddingTop,
               this.mMetrics.physicalViewPaddingRight,
               this.mMetrics.physicalViewPaddingBottom,
               this.mMetrics.physicalViewPaddingLeft,
               this.mMetrics.physicalViewInsetTop,
               this.mMetrics.physicalViewInsetRight,
               this.mMetrics.physicalViewInsetBottom,
               this.mMetrics.physicalViewInsetLeft,
               this.mMetrics.systemGestureInsetTop,
               this.mMetrics.systemGestureInsetRight,
               this.mMetrics.systemGestureInsetBottom,
               this.mMetrics.systemGestureInsetLeft,
               this.mMetrics.physicalTouchSlop,
               new int[0],
               new int[0],
               new int[0]
            );
      }
   }

   public void addActivityLifecycleListener(ActivityLifecycleListener var1) {
      this.mActivityLifecycleListeners.add(var1);
   }

   public void addFirstFrameListener(FlutterView.FirstFrameListener var1) {
      this.mFirstFrameListeners.add(var1);
   }

   void assertAttached() {
      if (!this.isAttached()) {
         throw new AssertionError("Platform view is not attached");
      }
   }

   public void autofill(SparseArray<AutofillValue> var1) {
      this.mTextInputPlugin.autofill(var1);
   }

   public void cancelBackGesture() {
      this.backGestureChannel.cancelBackGesture();
   }

   public boolean checkInputConnectionProxy(View var1) {
      return this.mNativeView.getPluginRegistry().getPlatformViewsController().checkInputConnectionProxy(var1);
   }

   public void commitBackGesture() {
      this.backGestureChannel.commitBackGesture();
   }

   @Override
   public TextureRegistry.ImageTextureEntry createImageTexture() {
      throw new UnsupportedOperationException("Image textures are not supported in this mode.");
   }

   @Override
   public TextureRegistry.SurfaceProducer createSurfaceProducer() {
      throw new UnsupportedOperationException("SurfaceProducer textures are not supported in this mode.");
   }

   @Override
   public TextureRegistry.SurfaceTextureEntry createSurfaceTexture() {
      return this.registerSurfaceTexture(new SurfaceTexture(0));
   }

   public void destroy() {
      if (this.isAttached()) {
         this.getHolder().removeCallback(this.mSurfaceCallback);
         this.releaseAccessibilityNodeProvider();
         this.mNativeView.destroy();
         this.mNativeView = null;
      }
   }

   public FlutterNativeView detach() {
      if (!this.isAttached()) {
         return null;
      } else {
         this.getHolder().removeCallback(this.mSurfaceCallback);
         this.mNativeView.detachFromFlutterView();
         FlutterNativeView var1 = this.mNativeView;
         this.mNativeView = null;
         return var1;
      }
   }

   @Override
   public void disableBufferingIncomingMessages() {
   }

   public void disableTransparentBackground() {
      this.setZOrderOnTop(false);
      this.getHolder().setFormat(-1);
   }

   public boolean dispatchKeyEvent(KeyEvent var1) {
      StringBuilder var5 = new StringBuilder("dispatchKeyEvent: ");
      var5.append(var1.toString());
      Log.e("FlutterView", var5.toString());
      int var2 = var1.getAction();
      boolean var4 = true;
      if (var2 == 0 && var1.getRepeatCount() == 0) {
         this.getKeyDispatcherState().startTracking(var1, this);
      } else if (var1.getAction() == 1) {
         this.getKeyDispatcherState().handleUpEvent(var1);
      }

      if (this.isAttached() && this.mKeyboardManager.handleEvent(var1)) {
         return var4;
      } else {
         boolean var3;
         if (super.dispatchKeyEvent(var1)) {
            var3 = var4;
         } else {
            var3 = false;
         }

         return var3;
      }
   }

   @Override
   public void enableBufferingIncomingMessages() {
   }

   public AccessibilityNodeProvider getAccessibilityNodeProvider() {
      AccessibilityBridge var1 = this.mAccessibilityNodeProvider;
      return var1 != null && var1.isAccessibilityEnabled() ? this.mAccessibilityNodeProvider : null;
   }

   @Override
   public BinaryMessenger getBinaryMessenger() {
      return this;
   }

   public Bitmap getBitmap() {
      this.assertAttached();
      return this.mNativeView.getFlutterJNI().getBitmap();
   }

   public DartExecutor getDartExecutor() {
      return this.dartExecutor;
   }

   float getDevicePixelRatio() {
      return this.mMetrics.devicePixelRatio;
   }

   public FlutterNativeView getFlutterNativeView() {
      return this.mNativeView;
   }

   public String getLookupKeyForAsset(String var1) {
      return FlutterMain.getLookupKeyForAsset(var1);
   }

   public String getLookupKeyForAsset(String var1, String var2) {
      return FlutterMain.getLookupKeyForAsset(var1, var2);
   }

   public FlutterPluginRegistry getPluginRegistry() {
      return this.mNativeView.getPluginRegistry();
   }

   @Override
   public PointerIcon getSystemPointerIcon(int var1) {
      return AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.getContext(), var1);
   }

   public boolean hasRenderedFirstFrame() {
      return this.didRenderFirstFrame;
   }

   @Override
   public BinaryMessenger.TaskQueue makeBackgroundTaskQueue(BinaryMessenger.TaskQueueOptions var1) {
      return null;
   }

   public final WindowInsets onApplyWindowInsets(WindowInsets var1) {
      if (VERSION.SDK_INT == 29) {
         Insets var5 = ExternalSyntheticApiModelOutline0.m$2(var1);
         this.mMetrics.systemGestureInsetTop = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$2(var5);
         this.mMetrics.systemGestureInsetRight = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$1(var5);
         this.mMetrics.systemGestureInsetBottom = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$3(var5);
         this.mMetrics.systemGestureInsetLeft = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m(var5);
      }

      int var4 = this.getWindowSystemUiVisibility();
      int var3 = 1;
      int var2 = 0;
      boolean var13;
      if ((var4 & 4) == 0) {
         var13 = true;
      } else {
         var13 = false;
      }

      if ((this.getWindowSystemUiVisibility() & 2) != 0) {
         var3 = 0;
      }

      if (VERSION.SDK_INT >= 30) {
         if (var3) {
            var2 = ExternalSyntheticApiModelOutline0.m$1();
         }

         var3 = var2;
         if (var13) {
            var3 = var2 | ExternalSyntheticApiModelOutline0.m();
         }

         Insets var14 = ExternalSyntheticApiModelOutline0.m$1(var1, var3);
         this.mMetrics.physicalViewPaddingTop = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$2(var14);
         this.mMetrics.physicalViewPaddingRight = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$1(var14);
         this.mMetrics.physicalViewPaddingBottom = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$3(var14);
         this.mMetrics.physicalViewPaddingLeft = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m(var14);
         var14 = ExternalSyntheticApiModelOutline0.m$1(var1, androidx.core.util.HalfKt..ExternalSyntheticApiModelOutline0.m());
         this.mMetrics.physicalViewInsetTop = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$2(var14);
         this.mMetrics.physicalViewInsetRight = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$1(var14);
         this.mMetrics.physicalViewInsetBottom = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$3(var14);
         this.mMetrics.physicalViewInsetLeft = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m(var14);
         var14 = ExternalSyntheticApiModelOutline0.m$1(var1, ExternalSyntheticApiModelOutline0.m$3());
         this.mMetrics.systemGestureInsetTop = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$2(var14);
         this.mMetrics.systemGestureInsetRight = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$1(var14);
         this.mMetrics.systemGestureInsetBottom = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$3(var14);
         this.mMetrics.systemGestureInsetLeft = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m(var14);
         DisplayCutout var6 = ExternalSyntheticApiModelOutline0.m(var1);
         if (var6 != null) {
            var14 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var6);
            FlutterView.ViewportMetrics var7 = this.mMetrics;
            var7.physicalViewPaddingTop = Math.max(
               Math.max(var7.physicalViewPaddingTop, androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$2(var14)),
               ExternalSyntheticApiModelOutline4.m(var6)
            );
            var7 = this.mMetrics;
            var7.physicalViewPaddingRight = Math.max(
               Math.max(var7.physicalViewPaddingRight, androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$1(var14)),
               ExternalSyntheticApiModelOutline4.m$3(var6)
            );
            var7 = this.mMetrics;
            var7.physicalViewPaddingBottom = Math.max(
               Math.max(var7.physicalViewPaddingBottom, androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$3(var14)),
               ExternalSyntheticApiModelOutline4.m$2(var6)
            );
            var7 = this.mMetrics;
            var7.physicalViewPaddingLeft = Math.max(
               Math.max(var7.physicalViewPaddingLeft, androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m(var14)),
               ExternalSyntheticApiModelOutline4.m$1(var6)
            );
         }
      } else {
         FlutterView.ZeroSides var18 = FlutterView.ZeroSides.NONE;
         if (!var3) {
            var18 = this.calculateShouldZeroSides();
         }

         FlutterView.ViewportMetrics var19 = this.mMetrics;
         if (var13) {
            var2 = var1.getSystemWindowInsetTop();
         } else {
            var2 = 0;
         }

         var19.physicalViewPaddingTop = var2;
         var19 = this.mMetrics;
         if (var18 != FlutterView.ZeroSides.RIGHT && var18 != FlutterView.ZeroSides.BOTH) {
            var2 = var1.getSystemWindowInsetRight();
         } else {
            var2 = 0;
         }

         var19.physicalViewPaddingRight = var2;
         var19 = this.mMetrics;
         if (var3 && this.guessBottomKeyboardInset(var1) == 0) {
            var2 = var1.getSystemWindowInsetBottom();
         } else {
            var2 = 0;
         }

         var19.physicalViewPaddingBottom = var2;
         var19 = this.mMetrics;
         if (var18 != FlutterView.ZeroSides.LEFT && var18 != FlutterView.ZeroSides.BOTH) {
            var2 = var1.getSystemWindowInsetLeft();
         } else {
            var2 = 0;
         }

         var19.physicalViewPaddingLeft = var2;
         this.mMetrics.physicalViewInsetTop = 0;
         this.mMetrics.physicalViewInsetRight = 0;
         this.mMetrics.physicalViewInsetBottom = this.guessBottomKeyboardInset(var1);
         this.mMetrics.physicalViewInsetLeft = 0;
      }

      this.updateViewportMetrics();
      return super.onApplyWindowInsets(var1);
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      PlatformViewsController var1 = this.getPluginRegistry().getPlatformViewsController();
      AccessibilityBridge var2 = new AccessibilityBridge(
         this,
         new AccessibilityChannel(this.dartExecutor, this.getFlutterNativeView().getFlutterJNI()),
         (AccessibilityManager)this.getContext().getSystemService("accessibility"),
         this.getContext().getContentResolver(),
         var1
      );
      this.mAccessibilityNodeProvider = var2;
      var2.setOnAccessibilityChangeListener(this.onAccessibilityChangeListener);
      this.resetWillNotDraw(this.mAccessibilityNodeProvider.isAccessibilityEnabled(), this.mAccessibilityNodeProvider.isTouchExplorationEnabled());
   }

   protected void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      this.mLocalizationPlugin.sendLocalesToFlutter(var1);
      this.sendUserPlatformSettingsToDart();
   }

   public InputConnection onCreateInputConnection(EditorInfo var1) {
      return this.mTextInputPlugin.createInputConnection(this, this.mKeyboardManager, var1);
   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      this.releaseAccessibilityNodeProvider();
   }

   public void onFirstFrame() {
      this.didRenderFirstFrame = true;
      Iterator var1 = new ArrayList<>(this.mFirstFrameListeners).iterator();

      while (var1.hasNext()) {
         ((FlutterView.FirstFrameListener)var1.next()).onFirstFrame();
      }
   }

   public boolean onGenericMotionEvent(MotionEvent var1) {
      boolean var2;
      if (this.isAttached() && this.androidTouchProcessor.onGenericMotionEvent(var1, this.getContext())) {
         var2 = true;
      } else {
         var2 = super.onGenericMotionEvent(var1);
      }

      return var2;
   }

   public boolean onHoverEvent(MotionEvent var1) {
      return !this.isAttached() ? super.onHoverEvent(var1) : this.mAccessibilityNodeProvider.onAccessibilityHoverEvent(var1);
   }

   public void onMemoryPressure() {
      this.mNativeView.getFlutterJNI().notifyLowMemoryWarning();
      this.systemChannel.sendMemoryPressureWarning();
   }

   public void onPause() {
      this.lifecycleChannel.appIsInactive();
   }

   public void onPostResume() {
      Iterator var1 = this.mActivityLifecycleListeners.iterator();

      while (var1.hasNext()) {
         ((ActivityLifecycleListener)var1.next()).onPostResume();
      }

      this.lifecycleChannel.appIsResumed();
   }

   public void onProvideAutofillVirtualStructure(ViewStructure var1, int var2) {
      super.onProvideAutofillVirtualStructure(var1, var2);
      this.mTextInputPlugin.onProvideAutofillVirtualStructure(var1, var2);
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      this.mMetrics.physicalWidth = var1;
      this.mMetrics.physicalHeight = var2;
      this.updateViewportMetrics();
      super.onSizeChanged(var1, var2, var3, var4);
   }

   public void onStart() {
      this.lifecycleChannel.appIsInactive();
   }

   public void onStop() {
      this.lifecycleChannel.appIsPaused();
   }

   @Override
   public boolean onTextInputKeyEvent(KeyEvent var1) {
      return this.mTextInputPlugin.handleKeyEvent(var1);
   }

   public boolean onTouchEvent(MotionEvent var1) {
      if (!this.isAttached()) {
         return super.onTouchEvent(var1);
      } else {
         this.requestUnbufferedDispatch(var1);
         return this.androidTouchProcessor.onTouchEvent(var1);
      }
   }

   public void popRoute() {
      this.navigationChannel.popRoute();
   }

   public void pushRoute(String var1) {
      this.navigationChannel.pushRoute(var1);
   }

   @Override
   public void redispatch(KeyEvent var1) {
      this.getRootView().dispatchKeyEvent(var1);
   }

   @Override
   public TextureRegistry.SurfaceTextureEntry registerSurfaceTexture(SurfaceTexture var1) {
      var1.detachFromGLContext();
      FlutterView.SurfaceTextureRegistryEntry var2 = new FlutterView.SurfaceTextureRegistryEntry(this, this.nextTextureId.getAndIncrement(), var1);
      this.mNativeView.getFlutterJNI().registerTexture(var2.id(), var2.textureWrapper());
      return var2;
   }

   public void removeFirstFrameListener(FlutterView.FirstFrameListener var1) {
      this.mFirstFrameListeners.remove(var1);
   }

   void resetAccessibilityTree() {
      AccessibilityBridge var1 = this.mAccessibilityNodeProvider;
      if (var1 != null) {
         var1.reset();
      }
   }

   public void runFromBundle(FlutterRunArguments var1) {
      this.assertAttached();
      this.preRun();
      this.mNativeView.runFromBundle(var1);
      this.postRun();
   }

   @Override
   public void send(String var1, ByteBuffer var2) {
      this.send(var1, var2, null);
   }

   @Override
   public void send(String var1, ByteBuffer var2, BinaryMessenger.BinaryReply var3) {
      if (!this.isAttached()) {
         StringBuilder var4 = new StringBuilder("FlutterView.send called on a detached view, channel=");
         var4.append(var1);
         Log.d("FlutterView", var4.toString());
      } else {
         this.mNativeView.send(var1, var2, var3);
      }
   }

   public void setInitialRoute(String var1) {
      this.navigationChannel.setInitialRoute(var1);
   }

   @Override
   public void setMessageHandler(String var1, BinaryMessenger.BinaryMessageHandler var2) {
      this.mNativeView.setMessageHandler(var1, var2);
   }

   @Override
   public void setMessageHandler(String var1, BinaryMessenger.BinaryMessageHandler var2, BinaryMessenger.TaskQueue var3) {
      this.mNativeView.setMessageHandler(var1, var2, var3);
   }

   public void startBackGesture(BackEvent var1) {
      this.backGestureChannel.startBackGesture(var1);
   }

   public void updateBackGestureProgress(BackEvent var1) {
      this.backGestureChannel.updateBackGestureProgress(var1);
   }

   public interface FirstFrameListener {
      void onFirstFrame();
   }

   public interface Provider {
      FlutterView getFlutterView();
   }

   final class SurfaceTextureRegistryEntry implements TextureRegistry.SurfaceTextureEntry {
      private final long id;
      private OnFrameAvailableListener onFrameListener;
      private boolean released;
      private final SurfaceTextureWrapper textureWrapper;
      final FlutterView this$0;

      SurfaceTextureRegistryEntry(FlutterView var1, long var2, SurfaceTexture var4) {
         this.this$0 = var1;
         this.onFrameListener = new OnFrameAvailableListener(this) {
            final FlutterView.SurfaceTextureRegistryEntry this$1;

            {
               this.this$1 = var1;
            }

            public void onFrameAvailable(SurfaceTexture var1) {
               if (!this.this$1.released && this.this$1.this$0.mNativeView != null) {
                  this.this$1.this$0.mNativeView.getFlutterJNI().markTextureFrameAvailable(this.this$1.id);
               }
            }
         };
         this.id = var2;
         this.textureWrapper = new SurfaceTextureWrapper(var4);
         this.surfaceTexture().setOnFrameAvailableListener(this.onFrameListener, new Handler());
      }

      @Override
      public long id() {
         return this.id;
      }

      @Override
      public void release() {
         if (!this.released) {
            this.released = true;
            this.surfaceTexture().setOnFrameAvailableListener(null);
            this.textureWrapper.release();
            this.this$0.mNativeView.getFlutterJNI().unregisterTexture(this.id);
         }
      }

      @Override
      public SurfaceTexture surfaceTexture() {
         return this.textureWrapper.surfaceTexture();
      }

      public SurfaceTextureWrapper textureWrapper() {
         return this.textureWrapper;
      }
   }

   static final class ViewportMetrics {
      float devicePixelRatio = 1.0F;
      int physicalHeight;
      int physicalTouchSlop;
      int physicalViewInsetBottom;
      int physicalViewInsetLeft;
      int physicalViewInsetRight;
      int physicalViewInsetTop;
      int physicalViewPaddingBottom;
      int physicalViewPaddingLeft;
      int physicalViewPaddingRight;
      int physicalViewPaddingTop;
      int physicalWidth = 0;
      int systemGestureInsetBottom;
      int systemGestureInsetLeft;
      int systemGestureInsetRight;
      int systemGestureInsetTop;

      ViewportMetrics() {
         this.physicalHeight = 0;
         this.physicalViewPaddingTop = 0;
         this.physicalViewPaddingRight = 0;
         this.physicalViewPaddingBottom = 0;
         this.physicalViewPaddingLeft = 0;
         this.physicalViewInsetTop = 0;
         this.physicalViewInsetRight = 0;
         this.physicalViewInsetBottom = 0;
         this.physicalViewInsetLeft = 0;
         this.systemGestureInsetTop = 0;
         this.systemGestureInsetRight = 0;
         this.systemGestureInsetBottom = 0;
         this.systemGestureInsetLeft = 0;
         this.physicalTouchSlop = -1;
      }
   }

   private static enum ZeroSides {
      BOTH,
      LEFT,
      NONE,
      RIGHT;
      private static final FlutterView.ZeroSides[] $VALUES = $values();
   }
}
