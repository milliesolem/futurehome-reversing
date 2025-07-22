package io.flutter.embedding.android;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Insets;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.DisplayCutout;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.textservice.TextServicesManager;
import android.widget.FrameLayout;
import androidx.core.content.ContextCompat;
import androidx.core.util.Consumer;
import androidx.core.view.WindowInsetsCompat.Impl28..ExternalSyntheticApiModelOutline0;
import androidx.webkit.internal.ApiHelperForN..ExternalSyntheticApiModelOutline4;
import androidx.window.java.layout.WindowInfoTrackerCallbackAdapter;
import androidx.window.layout.DisplayFeature;
import androidx.window.layout.FoldingFeature;
import androidx.window.layout.WindowInfoTracker;
import androidx.window.layout.WindowLayoutInfo;
import androidx.window.layout.FoldingFeature.OcclusionType;
import androidx.window.layout.FoldingFeature.State;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.renderer.FlutterRenderer;
import io.flutter.embedding.engine.renderer.FlutterUiDisplayListener;
import io.flutter.embedding.engine.renderer.RenderSurface;
import io.flutter.embedding.engine.systemchannels.SettingsChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.editing.SpellCheckPlugin;
import io.flutter.plugin.editing.TextInputPlugin;
import io.flutter.plugin.localization.LocalizationPlugin;
import io.flutter.plugin.mouse.MouseCursorPlugin;
import io.flutter.util.ViewUtils;
import io.flutter.view.AccessibilityBridge;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import j..util.Collection._EL;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FlutterView extends FrameLayout implements MouseCursorPlugin.MouseCursorViewDelegate, KeyboardManager.ViewDelegate {
   private static final String TAG = "FlutterView";
   private AccessibilityBridge accessibilityBridge;
   private AndroidTouchProcessor androidTouchProcessor;
   private FlutterViewDelegate delegate;
   private FlutterEngine flutterEngine;
   private final Set<FlutterView.FlutterEngineAttachmentListener> flutterEngineAttachmentListeners;
   private FlutterImageView flutterImageView;
   private FlutterSurfaceView flutterSurfaceView;
   private FlutterTextureView flutterTextureView;
   private final FlutterUiDisplayListener flutterUiDisplayListener;
   private final Set<FlutterUiDisplayListener> flutterUiDisplayListeners = new HashSet<>();
   private boolean isFlutterUiDisplayed;
   private KeyboardManager keyboardManager;
   private LocalizationPlugin localizationPlugin;
   private MouseCursorPlugin mouseCursorPlugin;
   private final AccessibilityBridge.OnAccessibilityChangeListener onAccessibilityChangeListener;
   private RenderSurface previousRenderSurface;
   RenderSurface renderSurface;
   private SpellCheckPlugin spellCheckPlugin;
   private final ContentObserver systemSettingsObserver;
   private TextInputPlugin textInputPlugin;
   private TextServicesManager textServicesManager;
   private final FlutterRenderer.ViewportMetrics viewportMetrics;
   private Consumer<WindowLayoutInfo> windowInfoListener;
   private WindowInfoRepositoryCallbackAdapterWrapper windowInfoRepo;

   public FlutterView(Context var1) {
      this(var1, null, new FlutterSurfaceView(var1));
   }

   public FlutterView(Context var1, AttributeSet var2) {
      this(var1, var2, new FlutterSurfaceView(var1));
   }

   private FlutterView(Context var1, AttributeSet var2, FlutterImageView var3) {
      super(var1, var2);
      this.flutterEngineAttachmentListeners = new HashSet<>();
      this.viewportMetrics = new FlutterRenderer.ViewportMetrics();
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
      this.systemSettingsObserver = new ContentObserver(this, new Handler(Looper.getMainLooper())) {
         final FlutterView this$0;

         {
            this.this$0 = var1;
         }

         public boolean deliverSelfNotifications() {
            return true;
         }

         public void onChange(boolean var1) {
            super.onChange(var1);
            if (this.this$0.flutterEngine != null) {
               Log.v("FlutterView", "System settings changed. Sending user settings to Flutter.");
               this.this$0.sendUserSettingsToFlutter();
            }
         }
      };
      this.flutterUiDisplayListener = new FlutterUiDisplayListener(this) {
         final FlutterView this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onFlutterUiDisplayed() {
            this.this$0.isFlutterUiDisplayed = true;
            Iterator var1x = this.this$0.flutterUiDisplayListeners.iterator();

            while (var1x.hasNext()) {
               ((FlutterUiDisplayListener)var1x.next()).onFlutterUiDisplayed();
            }
         }

         @Override
         public void onFlutterUiNoLongerDisplayed() {
            this.this$0.isFlutterUiDisplayed = false;
            Iterator var1x = this.this$0.flutterUiDisplayListeners.iterator();

            while (var1x.hasNext()) {
               ((FlutterUiDisplayListener)var1x.next()).onFlutterUiNoLongerDisplayed();
            }
         }
      };
      this.delegate = new FlutterViewDelegate();
      this.flutterImageView = var3;
      this.renderSurface = var3;
      this.init();
   }

   private FlutterView(Context var1, AttributeSet var2, FlutterSurfaceView var3) {
      super(var1, var2);
      this.flutterEngineAttachmentListeners = new HashSet<>();
      this.viewportMetrics = new FlutterRenderer.ViewportMetrics();
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
      this.systemSettingsObserver = new ContentObserver(this, new Handler(Looper.getMainLooper())) {
         final FlutterView this$0;

         {
            this.this$0 = var1;
         }

         public boolean deliverSelfNotifications() {
            return true;
         }

         public void onChange(boolean var1) {
            super.onChange(var1);
            if (this.this$0.flutterEngine != null) {
               Log.v("FlutterView", "System settings changed. Sending user settings to Flutter.");
               this.this$0.sendUserSettingsToFlutter();
            }
         }
      };
      this.flutterUiDisplayListener = new FlutterUiDisplayListener(this) {
         final FlutterView this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onFlutterUiDisplayed() {
            this.this$0.isFlutterUiDisplayed = true;
            Iterator var1x = this.this$0.flutterUiDisplayListeners.iterator();

            while (var1x.hasNext()) {
               ((FlutterUiDisplayListener)var1x.next()).onFlutterUiDisplayed();
            }
         }

         @Override
         public void onFlutterUiNoLongerDisplayed() {
            this.this$0.isFlutterUiDisplayed = false;
            Iterator var1x = this.this$0.flutterUiDisplayListeners.iterator();

            while (var1x.hasNext()) {
               ((FlutterUiDisplayListener)var1x.next()).onFlutterUiNoLongerDisplayed();
            }
         }
      };
      this.delegate = new FlutterViewDelegate();
      this.flutterSurfaceView = var3;
      this.renderSurface = var3;
      this.init();
   }

   private FlutterView(Context var1, AttributeSet var2, FlutterTextureView var3) {
      super(var1, var2);
      this.flutterEngineAttachmentListeners = new HashSet<>();
      this.viewportMetrics = new FlutterRenderer.ViewportMetrics();
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
      this.systemSettingsObserver = new ContentObserver(this, new Handler(Looper.getMainLooper())) {
         final FlutterView this$0;

         {
            this.this$0 = var1;
         }

         public boolean deliverSelfNotifications() {
            return true;
         }

         public void onChange(boolean var1) {
            super.onChange(var1);
            if (this.this$0.flutterEngine != null) {
               Log.v("FlutterView", "System settings changed. Sending user settings to Flutter.");
               this.this$0.sendUserSettingsToFlutter();
            }
         }
      };
      this.flutterUiDisplayListener = new FlutterUiDisplayListener(this) {
         final FlutterView this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onFlutterUiDisplayed() {
            this.this$0.isFlutterUiDisplayed = true;
            Iterator var1x = this.this$0.flutterUiDisplayListeners.iterator();

            while (var1x.hasNext()) {
               ((FlutterUiDisplayListener)var1x.next()).onFlutterUiDisplayed();
            }
         }

         @Override
         public void onFlutterUiNoLongerDisplayed() {
            this.this$0.isFlutterUiDisplayed = false;
            Iterator var1x = this.this$0.flutterUiDisplayListeners.iterator();

            while (var1x.hasNext()) {
               ((FlutterUiDisplayListener)var1x.next()).onFlutterUiNoLongerDisplayed();
            }
         }
      };
      this.delegate = new FlutterViewDelegate();
      this.flutterTextureView = var3;
      this.renderSurface = var3;
      this.init();
   }

   public FlutterView(Context var1, FlutterImageView var2) {
      this(var1, null, var2);
   }

   public FlutterView(Context var1, FlutterSurfaceView var2) {
      this(var1, null, var2);
   }

   public FlutterView(Context var1, FlutterTextureView var2) {
      this(var1, null, var2);
   }

   @Deprecated
   public FlutterView(Context var1, RenderMode var2) {
      super(var1, null);
      this.flutterEngineAttachmentListeners = new HashSet<>();
      this.viewportMetrics = new FlutterRenderer.ViewportMetrics();
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
      this.systemSettingsObserver = new ContentObserver(this, new Handler(Looper.getMainLooper())) {
         final FlutterView this$0;

         {
            this.this$0 = var1;
         }

         public boolean deliverSelfNotifications() {
            return true;
         }

         public void onChange(boolean var1) {
            super.onChange(var1);
            if (this.this$0.flutterEngine != null) {
               Log.v("FlutterView", "System settings changed. Sending user settings to Flutter.");
               this.this$0.sendUserSettingsToFlutter();
            }
         }
      };
      this.flutterUiDisplayListener = new FlutterUiDisplayListener(this) {
         final FlutterView this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onFlutterUiDisplayed() {
            this.this$0.isFlutterUiDisplayed = true;
            Iterator var1x = this.this$0.flutterUiDisplayListeners.iterator();

            while (var1x.hasNext()) {
               ((FlutterUiDisplayListener)var1x.next()).onFlutterUiDisplayed();
            }
         }

         @Override
         public void onFlutterUiNoLongerDisplayed() {
            this.this$0.isFlutterUiDisplayed = false;
            Iterator var1x = this.this$0.flutterUiDisplayListeners.iterator();

            while (var1x.hasNext()) {
               ((FlutterUiDisplayListener)var1x.next()).onFlutterUiNoLongerDisplayed();
            }
         }
      };
      this.delegate = new FlutterViewDelegate();
      if (var2 == RenderMode.surface) {
         FlutterSurfaceView var3 = new FlutterSurfaceView(var1);
         this.flutterSurfaceView = var3;
         this.renderSurface = var3;
      } else {
         if (var2 != RenderMode.texture) {
            StringBuilder var5 = new StringBuilder("RenderMode not supported with this constructor: ");
            var5.append(var2);
            throw new IllegalArgumentException(var5.toString());
         }

         FlutterTextureView var4 = new FlutterTextureView(var1);
         this.flutterTextureView = var4;
         this.renderSurface = var4;
      }

      this.init();
   }

   @Deprecated
   public FlutterView(Context var1, RenderMode var2, TransparencyMode var3) {
      super(var1, null);
      this.flutterEngineAttachmentListeners = new HashSet<>();
      this.viewportMetrics = new FlutterRenderer.ViewportMetrics();
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
      this.systemSettingsObserver = new ContentObserver(this, new Handler(Looper.getMainLooper())) {
         final FlutterView this$0;

         {
            this.this$0 = var1;
         }

         public boolean deliverSelfNotifications() {
            return true;
         }

         public void onChange(boolean var1) {
            super.onChange(var1);
            if (this.this$0.flutterEngine != null) {
               Log.v("FlutterView", "System settings changed. Sending user settings to Flutter.");
               this.this$0.sendUserSettingsToFlutter();
            }
         }
      };
      this.flutterUiDisplayListener = new FlutterUiDisplayListener(this) {
         final FlutterView this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onFlutterUiDisplayed() {
            this.this$0.isFlutterUiDisplayed = true;
            Iterator var1x = this.this$0.flutterUiDisplayListeners.iterator();

            while (var1x.hasNext()) {
               ((FlutterUiDisplayListener)var1x.next()).onFlutterUiDisplayed();
            }
         }

         @Override
         public void onFlutterUiNoLongerDisplayed() {
            this.this$0.isFlutterUiDisplayed = false;
            Iterator var1x = this.this$0.flutterUiDisplayListeners.iterator();

            while (var1x.hasNext()) {
               ((FlutterUiDisplayListener)var1x.next()).onFlutterUiNoLongerDisplayed();
            }
         }
      };
      this.delegate = new FlutterViewDelegate();
      if (var2 == RenderMode.surface) {
         boolean var4;
         if (var3 == TransparencyMode.transparent) {
            var4 = true;
         } else {
            var4 = false;
         }

         FlutterSurfaceView var5 = new FlutterSurfaceView(var1, var4);
         this.flutterSurfaceView = var5;
         this.renderSurface = var5;
      } else {
         if (var2 != RenderMode.texture) {
            StringBuilder var7 = new StringBuilder("RenderMode not supported with this constructor: ");
            var7.append(var2);
            throw new IllegalArgumentException(var7.toString());
         }

         FlutterTextureView var6 = new FlutterTextureView(var1);
         this.flutterTextureView = var6;
         this.renderSurface = var6;
      }

      this.init();
   }

   @Deprecated
   public FlutterView(Context var1, TransparencyMode var2) {
      boolean var3;
      if (var2 == TransparencyMode.transparent) {
         var3 = true;
      } else {
         var3 = false;
      }

      this(var1, null, new FlutterSurfaceView(var1, var3));
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private View findViewByAccessibilityIdRootedAtCurrentView(int var1, View var2) {
      Method var5;
      try {
         var5 = View.class.getDeclaredMethod("getAccessibilityViewId", null);
      } catch (NoSuchMethodException var7) {
         return null;
      }

      var5.setAccessible(true);

      boolean var4;
      try {
         var4 = var5.invoke(var2, null).equals(var1);
      } catch (InvocationTargetException | IllegalAccessException var6) {
         return null;
      }

      if (var4) {
         return var2;
      } else {
         if (var2 instanceof ViewGroup) {
            int var3 = 0;

            while (true) {
               ViewGroup var8 = (ViewGroup)var2;
               if (var3 >= var8.getChildCount()) {
                  break;
               }

               View var9 = this.findViewByAccessibilityIdRootedAtCurrentView(var1, var8.getChildAt(var3));
               if (var9 != null) {
                  return var9;
               }

               var3++;
            }
         }

         return null;
      }
   }

   private int guessBottomKeyboardInset(WindowInsets var1) {
      int var2 = this.getRootView().getHeight();
      return var1.getSystemWindowInsetBottom() < var2 * 0.18 ? 0 : var1.getSystemWindowInsetBottom();
   }

   private void init() {
      Log.v("FlutterView", "Initializing FlutterView");
      if (this.flutterSurfaceView != null) {
         Log.v("FlutterView", "Internally using a FlutterSurfaceView.");
         this.addView(this.flutterSurfaceView);
      } else if (this.flutterTextureView != null) {
         Log.v("FlutterView", "Internally using a FlutterTextureView.");
         this.addView(this.flutterTextureView);
      } else {
         Log.v("FlutterView", "Internally using a FlutterImageView.");
         this.addView(this.flutterImageView);
      }

      this.setFocusable(true);
      this.setFocusableInTouchMode(true);
      if (VERSION.SDK_INT >= 26) {
         AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this, 1);
      }
   }

   private void releaseImageView() {
      FlutterImageView var1 = this.flutterImageView;
      if (var1 != null) {
         var1.closeImageReader();
         this.removeView(this.flutterImageView);
         this.flutterImageView = null;
      }
   }

   private void resetWillNotDraw(boolean var1, boolean var2) {
      boolean var3 = this.flutterEngine.getRenderer().isSoftwareRenderingEnabled();
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

   private void sendViewportMetricsToFlutter() {
      if (!this.isAttachedToFlutterEngine()) {
         Log.w("FlutterView", "Tried to send viewport metrics from Android to Flutter but this FlutterView was not attached to a FlutterEngine.");
      } else {
         this.viewportMetrics.devicePixelRatio = this.getResources().getDisplayMetrics().density;
         this.viewportMetrics.physicalTouchSlop = ViewConfiguration.get(this.getContext()).getScaledTouchSlop();
         this.flutterEngine.getRenderer().setViewportMetrics(this.viewportMetrics);
      }
   }

   public boolean acquireLatestImageViewFrame() {
      FlutterImageView var1 = this.flutterImageView;
      return var1 != null ? var1.acquireLatestImage() : false;
   }

   public void addFlutterEngineAttachmentListener(FlutterView.FlutterEngineAttachmentListener var1) {
      this.flutterEngineAttachmentListeners.add(var1);
   }

   public void addOnFirstFrameRenderedListener(FlutterUiDisplayListener var1) {
      this.flutterUiDisplayListeners.add(var1);
   }

   public void attachOverlaySurfaceToRender(FlutterImageView var1) {
      FlutterEngine var2 = this.flutterEngine;
      if (var2 != null) {
         var1.attachToRenderer(var2.getRenderer());
      }
   }

   public void attachToFlutterEngine(FlutterEngine var1) {
      StringBuilder var2 = new StringBuilder("Attaching to a FlutterEngine: ");
      var2.append(var1);
      Log.v("FlutterView", var2.toString());
      if (this.isAttachedToFlutterEngine()) {
         if (var1 == this.flutterEngine) {
            Log.v("FlutterView", "Already attached to this engine. Doing nothing.");
            return;
         }

         Log.v("FlutterView", "Currently attached to a different engine. Detaching and then attaching to new engine.");
         this.detachFromFlutterEngine();
      }

      this.flutterEngine = var1;
      FlutterRenderer var4 = var1.getRenderer();
      this.isFlutterUiDisplayed = var4.isDisplayingFlutterUi();
      this.renderSurface.attachToRenderer(var4);
      var4.addIsDisplayingFlutterUiListener(this.flutterUiDisplayListener);
      if (VERSION.SDK_INT >= 24) {
         this.mouseCursorPlugin = new MouseCursorPlugin(this, this.flutterEngine.getMouseCursorChannel());
      }

      this.textInputPlugin = new TextInputPlugin(this, this.flutterEngine.getTextInputChannel(), this.flutterEngine.getPlatformViewsController());

      try {
         this.textServicesManager = (TextServicesManager)this.getContext().getSystemService("textservices");
         SpellCheckPlugin var5 = new SpellCheckPlugin(this.textServicesManager, this.flutterEngine.getSpellCheckChannel());
         this.spellCheckPlugin = var5;
      } catch (Exception var3) {
         Log.e("FlutterView", "TextServicesManager not supported by device, spell check disabled.");
      }

      this.localizationPlugin = this.flutterEngine.getLocalizationPlugin();
      this.keyboardManager = new KeyboardManager(this);
      this.androidTouchProcessor = new AndroidTouchProcessor(this.flutterEngine.getRenderer(), false);
      AccessibilityBridge var6 = new AccessibilityBridge(
         this,
         var1.getAccessibilityChannel(),
         (AccessibilityManager)this.getContext().getSystemService("accessibility"),
         this.getContext().getContentResolver(),
         this.flutterEngine.getPlatformViewsController()
      );
      this.accessibilityBridge = var6;
      var6.setOnAccessibilityChangeListener(this.onAccessibilityChangeListener);
      this.resetWillNotDraw(this.accessibilityBridge.isAccessibilityEnabled(), this.accessibilityBridge.isTouchExplorationEnabled());
      this.flutterEngine.getPlatformViewsController().attachAccessibilityBridge(this.accessibilityBridge);
      this.flutterEngine.getPlatformViewsController().attachToFlutterRenderer(this.flutterEngine.getRenderer());
      this.textInputPlugin.getInputMethodManager().restartInput(this);
      this.sendUserSettingsToFlutter();
      this.getContext().getContentResolver().registerContentObserver(System.getUriFor("show_password"), false, this.systemSettingsObserver);
      this.sendViewportMetricsToFlutter();
      var1.getPlatformViewsController().attachToView(this);
      Iterator var7 = this.flutterEngineAttachmentListeners.iterator();

      while (var7.hasNext()) {
         ((FlutterView.FlutterEngineAttachmentListener)var7.next()).onFlutterEngineAttachedToFlutterView(var1);
      }

      if (this.isFlutterUiDisplayed) {
         this.flutterUiDisplayListener.onFlutterUiDisplayed();
      }
   }

   public void autofill(SparseArray<AutofillValue> var1) {
      this.textInputPlugin.autofill(var1);
   }

   public FlutterView.ZeroSides calculateShouldZeroSides() {
      Context var2 = this.getContext();
      if (var2.getResources().getConfiguration().orientation == 2) {
         int var1 = ((DisplayManager)var2.getSystemService("display")).getDisplay(0).getRotation();
         if (var1 == 1) {
            return FlutterView.ZeroSides.RIGHT;
         }

         if (var1 == 3) {
            FlutterView.ZeroSides var3;
            if (VERSION.SDK_INT >= 23) {
               var3 = FlutterView.ZeroSides.LEFT;
            } else {
               var3 = FlutterView.ZeroSides.RIGHT;
            }

            return var3;
         }

         if (var1 == 0 || var1 == 2) {
            return FlutterView.ZeroSides.BOTH;
         }
      }

      return FlutterView.ZeroSides.NONE;
   }

   public boolean checkInputConnectionProxy(View var1) {
      FlutterEngine var3 = this.flutterEngine;
      boolean var2;
      if (var3 != null) {
         var2 = var3.getPlatformViewsController().checkInputConnectionProxy(var1);
      } else {
         var2 = super.checkInputConnectionProxy(var1);
      }

      return var2;
   }

   public void convertToImageView() {
      this.renderSurface.pause();
      FlutterImageView var1 = this.flutterImageView;
      if (var1 == null) {
         var1 = this.createImageView();
         this.flutterImageView = var1;
         this.addView(var1);
      } else {
         var1.resizeIfNeeded(this.getWidth(), this.getHeight());
      }

      this.previousRenderSurface = this.renderSurface;
      FlutterImageView var2 = this.flutterImageView;
      this.renderSurface = var2;
      FlutterEngine var4 = this.flutterEngine;
      if (var4 != null) {
         var2.attachToRenderer(var4.getRenderer());
      }
   }

   public FlutterImageView createImageView() {
      return new FlutterImageView(this.getContext(), this.getWidth(), this.getHeight(), FlutterImageView.SurfaceKind.background);
   }

   protected WindowInfoRepositoryCallbackAdapterWrapper createWindowInfoRepo() {
      try {
         WindowInfoTrackerCallbackAdapter var1 = new WindowInfoTrackerCallbackAdapter(WindowInfoTracker.Companion.getOrCreate(this.getContext()));
         return new WindowInfoRepositoryCallbackAdapterWrapper(var1);
      } catch (NoClassDefFoundError var2) {
         return null;
      }
   }

   public void detachFromFlutterEngine() {
      StringBuilder var1 = new StringBuilder("Detaching from a FlutterEngine: ");
      var1.append(this.flutterEngine);
      Log.v("FlutterView", var1.toString());
      if (!this.isAttachedToFlutterEngine()) {
         Log.v("FlutterView", "FlutterView not attached to an engine. Not detaching.");
      } else {
         Iterator var2 = this.flutterEngineAttachmentListeners.iterator();

         while (var2.hasNext()) {
            ((FlutterView.FlutterEngineAttachmentListener)var2.next()).onFlutterEngineDetachedFromFlutterView();
         }

         this.getContext().getContentResolver().unregisterContentObserver(this.systemSettingsObserver);
         this.flutterEngine.getPlatformViewsController().detachFromView();
         this.flutterEngine.getPlatformViewsController().detachAccessibilityBridge();
         this.accessibilityBridge.release();
         this.accessibilityBridge = null;
         this.textInputPlugin.getInputMethodManager().restartInput(this);
         this.textInputPlugin.destroy();
         this.keyboardManager.destroy();
         SpellCheckPlugin var3 = this.spellCheckPlugin;
         if (var3 != null) {
            var3.destroy();
         }

         MouseCursorPlugin var4 = this.mouseCursorPlugin;
         if (var4 != null) {
            var4.destroy();
         }

         FlutterRenderer var5 = this.flutterEngine.getRenderer();
         this.isFlutterUiDisplayed = false;
         var5.removeIsDisplayingFlutterUiListener(this.flutterUiDisplayListener);
         var5.stopRenderingToSurface();
         var5.setSemanticsEnabled(false);
         RenderSurface var6 = this.previousRenderSurface;
         if (var6 != null && this.renderSurface == this.flutterImageView) {
            this.renderSurface = var6;
         }

         this.renderSurface.detachFromRenderer();
         this.releaseImageView();
         this.previousRenderSurface = null;
         this.flutterEngine = null;
      }
   }

   public boolean dispatchKeyEvent(KeyEvent var1) {
      int var2 = var1.getAction();
      boolean var4 = true;
      if (var2 == 0 && var1.getRepeatCount() == 0) {
         this.getKeyDispatcherState().startTracking(var1, this);
      } else if (var1.getAction() == 1) {
         this.getKeyDispatcherState().handleUpEvent(var1);
      }

      if (this.isAttachedToFlutterEngine() && this.keyboardManager.handleEvent(var1)) {
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

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public View findViewByAccessibilityIdTraversal(int var1) {
      if (VERSION.SDK_INT < 29) {
         return this.findViewByAccessibilityIdRootedAtCurrentView(var1, this);
      } else {
         Method var2;
         try {
            var2 = View.class.getDeclaredMethod("findViewByAccessibilityIdTraversal", int.class);
         } catch (NoSuchMethodException var4) {
            return null;
         }

         var2.setAccessible(true);

         try {
            return (View)var2.invoke(this, var1);
         } catch (InvocationTargetException | IllegalAccessException var3) {
            return null;
         }
      }
   }

   public AccessibilityNodeProvider getAccessibilityNodeProvider() {
      AccessibilityBridge var1 = this.accessibilityBridge;
      return var1 != null && var1.isAccessibilityEnabled() ? this.accessibilityBridge : null;
   }

   public FlutterEngine getAttachedFlutterEngine() {
      return this.flutterEngine;
   }

   @Override
   public BinaryMessenger getBinaryMessenger() {
      return this.flutterEngine.getDartExecutor();
   }

   public FlutterImageView getCurrentImageSurface() {
      return this.flutterImageView;
   }

   @Override
   public PointerIcon getSystemPointerIcon(int var1) {
      return AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.getContext(), var1);
   }

   public FlutterRenderer.ViewportMetrics getViewportMetrics() {
      return this.viewportMetrics;
   }

   public boolean hasRenderedFirstFrame() {
      return this.isFlutterUiDisplayed;
   }

   public boolean isAttachedToFlutterEngine() {
      FlutterEngine var2 = this.flutterEngine;
      boolean var1;
      if (var2 != null && var2.getRenderer() == this.renderSurface.getAttachedRenderer()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final WindowInsets onApplyWindowInsets(WindowInsets var1) {
      WindowInsets var6 = super.onApplyWindowInsets(var1);
      if (VERSION.SDK_INT == 29) {
         Insets var5 = ExternalSyntheticApiModelOutline0.m$2(var1);
         this.viewportMetrics.systemGestureInsetTop = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$2(var5);
         this.viewportMetrics.systemGestureInsetRight = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$1(var5);
         this.viewportMetrics.systemGestureInsetBottom = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$3(var5);
         this.viewportMetrics.systemGestureInsetLeft = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m(var5);
      }

      int var4 = this.getWindowSystemUiVisibility();
      int var3 = 1;
      int var2 = 0;
      boolean var17;
      if ((var4 & 4) == 0) {
         var17 = true;
      } else {
         var17 = false;
      }

      if ((this.getWindowSystemUiVisibility() & 2) != 0) {
         var3 = 0;
      }

      if (VERSION.SDK_INT >= 30) {
         if (var3) {
            var2 = ExternalSyntheticApiModelOutline0.m$1();
         }

         var3 = var2;
         if (var17) {
            var3 = var2 | ExternalSyntheticApiModelOutline0.m();
         }

         Insets var18 = ExternalSyntheticApiModelOutline0.m$1(var1, var3);
         this.viewportMetrics.viewPaddingTop = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$2(var18);
         this.viewportMetrics.viewPaddingRight = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$1(var18);
         this.viewportMetrics.viewPaddingBottom = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$3(var18);
         this.viewportMetrics.viewPaddingLeft = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m(var18);
         var18 = ExternalSyntheticApiModelOutline0.m$1(var1, androidx.core.util.HalfKt..ExternalSyntheticApiModelOutline0.m());
         this.viewportMetrics.viewInsetTop = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$2(var18);
         this.viewportMetrics.viewInsetRight = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$1(var18);
         this.viewportMetrics.viewInsetBottom = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$3(var18);
         this.viewportMetrics.viewInsetLeft = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m(var18);
         var18 = ExternalSyntheticApiModelOutline0.m$1(var1, ExternalSyntheticApiModelOutline0.m$3());
         this.viewportMetrics.systemGestureInsetTop = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$2(var18);
         this.viewportMetrics.systemGestureInsetRight = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$1(var18);
         this.viewportMetrics.systemGestureInsetBottom = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$3(var18);
         this.viewportMetrics.systemGestureInsetLeft = androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m(var18);
         DisplayCutout var21 = ExternalSyntheticApiModelOutline0.m(var1);
         if (var21 != null) {
            Insets var7 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var21);
            FlutterRenderer.ViewportMetrics var8 = this.viewportMetrics;
            var8.viewPaddingTop = Math.max(
               Math.max(var8.viewPaddingTop, androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$2(var7)),
               ExternalSyntheticApiModelOutline4.m(var21)
            );
            var8 = this.viewportMetrics;
            var8.viewPaddingRight = Math.max(
               Math.max(var8.viewPaddingRight, androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$1(var7)),
               ExternalSyntheticApiModelOutline4.m$3(var21)
            );
            var8 = this.viewportMetrics;
            var8.viewPaddingBottom = Math.max(
               Math.max(var8.viewPaddingBottom, androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m$3(var7)),
               ExternalSyntheticApiModelOutline4.m$2(var21)
            );
            var8 = this.viewportMetrics;
            var8.viewPaddingLeft = Math.max(
               Math.max(var8.viewPaddingLeft, androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0.m(var7)),
               ExternalSyntheticApiModelOutline4.m$1(var21)
            );
         }
      } else {
         FlutterView.ZeroSides var22 = FlutterView.ZeroSides.NONE;
         if (!var3) {
            var22 = this.calculateShouldZeroSides();
         }

         FlutterRenderer.ViewportMetrics var24 = this.viewportMetrics;
         if (var17) {
            var2 = var1.getSystemWindowInsetTop();
         } else {
            var2 = 0;
         }

         var24.viewPaddingTop = var2;
         var24 = this.viewportMetrics;
         if (var22 != FlutterView.ZeroSides.RIGHT && var22 != FlutterView.ZeroSides.BOTH) {
            var2 = var1.getSystemWindowInsetRight();
         } else {
            var2 = 0;
         }

         var24.viewPaddingRight = var2;
         var24 = this.viewportMetrics;
         if (var3 && this.guessBottomKeyboardInset(var1) == 0) {
            var2 = var1.getSystemWindowInsetBottom();
         } else {
            var2 = 0;
         }

         var24.viewPaddingBottom = var2;
         var24 = this.viewportMetrics;
         if (var22 != FlutterView.ZeroSides.LEFT && var22 != FlutterView.ZeroSides.BOTH) {
            var2 = var1.getSystemWindowInsetLeft();
         } else {
            var2 = 0;
         }

         var24.viewPaddingLeft = var2;
         this.viewportMetrics.viewInsetTop = 0;
         this.viewportMetrics.viewInsetRight = 0;
         this.viewportMetrics.viewInsetBottom = this.guessBottomKeyboardInset(var1);
         this.viewportMetrics.viewInsetLeft = 0;
      }

      ArrayList var23 = new ArrayList();
      if (VERSION.SDK_INT >= 28) {
         DisplayCutout var9 = ExternalSyntheticApiModelOutline0.m(var1);
         if (var9 != null) {
            for (Rect var10 : AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var9)) {
               StringBuilder var32 = new StringBuilder("DisplayCutout area reported with bounds = ");
               var32.append(var10.toString());
               Log.v("FlutterView", var32.toString());
               var23.add(new FlutterRenderer.DisplayFeature(var10, FlutterRenderer.DisplayFeatureType.CUTOUT, FlutterRenderer.DisplayFeatureState.UNKNOWN));
            }
         }
      }

      this.viewportMetrics.setDisplayCutouts(var23);
      if (VERSION.SDK_INT >= 35) {
         this.delegate.growViewportMetricsToCaptionBar(this.getContext(), this.viewportMetrics);
      }

      StringBuilder var11 = new StringBuilder("Updating window insets (onApplyWindowInsets()):\nStatus bar insets: Top: ");
      var11.append(this.viewportMetrics.viewPaddingTop);
      var11.append(", Left: ");
      var11.append(this.viewportMetrics.viewPaddingLeft);
      var11.append(", Right: ");
      var11.append(this.viewportMetrics.viewPaddingRight);
      var11.append("\nKeyboard insets: Bottom: ");
      var11.append(this.viewportMetrics.viewInsetBottom);
      var11.append(", Left: ");
      var11.append(this.viewportMetrics.viewInsetLeft);
      var11.append(", Right: ");
      var11.append(this.viewportMetrics.viewInsetRight);
      var11.append("System Gesture Insets - Left: ");
      var11.append(this.viewportMetrics.systemGestureInsetLeft);
      var11.append(", Top: ");
      var11.append(this.viewportMetrics.systemGestureInsetTop);
      var11.append(", Right: ");
      var11.append(this.viewportMetrics.systemGestureInsetRight);
      var11.append(", Bottom: ");
      var11.append(this.viewportMetrics.viewInsetBottom);
      Log.v("FlutterView", var11.toString());
      this.sendViewportMetricsToFlutter();
      return var6;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      this.windowInfoRepo = this.createWindowInfoRepo();
      Activity var1 = ViewUtils.getActivity(this.getContext());
      if (this.windowInfoRepo != null && var1 != null) {
         this.windowInfoListener = new FlutterView$$ExternalSyntheticLambda7(this);
         this.windowInfoRepo.addWindowLayoutInfoListener(var1, ContextCompat.getMainExecutor(this.getContext()), this.windowInfoListener);
      }
   }

   protected void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      if (this.flutterEngine != null) {
         Log.v("FlutterView", "Configuration changed. Sending locales and user settings to Flutter.");
         this.localizationPlugin.sendLocalesToFlutter(var1);
         this.sendUserSettingsToFlutter();
         ViewUtils.calculateMaximumDisplayMetrics(this.getContext(), this.flutterEngine);
      }
   }

   public InputConnection onCreateInputConnection(EditorInfo var1) {
      return !this.isAttachedToFlutterEngine()
         ? super.onCreateInputConnection(var1)
         : this.textInputPlugin.createInputConnection(this, this.keyboardManager, var1);
   }

   protected void onDetachedFromWindow() {
      WindowInfoRepositoryCallbackAdapterWrapper var2 = this.windowInfoRepo;
      if (var2 != null) {
         Consumer var1 = this.windowInfoListener;
         if (var1 != null) {
            var2.removeWindowLayoutInfoListener(var1);
         }
      }

      this.windowInfoListener = null;
      this.windowInfoRepo = null;
      super.onDetachedFromWindow();
   }

   public boolean onGenericMotionEvent(MotionEvent var1) {
      boolean var2;
      if (this.isAttachedToFlutterEngine() && this.androidTouchProcessor.onGenericMotionEvent(var1, this.getContext())) {
         var2 = true;
      } else {
         var2 = super.onGenericMotionEvent(var1);
      }

      return var2;
   }

   public boolean onHoverEvent(MotionEvent var1) {
      return !this.isAttachedToFlutterEngine() ? super.onHoverEvent(var1) : this.accessibilityBridge.onAccessibilityHoverEvent(var1);
   }

   public void onProvideAutofillVirtualStructure(ViewStructure var1, int var2) {
      super.onProvideAutofillVirtualStructure(var1, var2);
      this.textInputPlugin.onProvideAutofillVirtualStructure(var1, var2);
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      super.onSizeChanged(var1, var2, var3, var4);
      StringBuilder var5 = new StringBuilder("Size changed. Sending Flutter new viewport metrics. FlutterView was ");
      var5.append(var3);
      var5.append(" x ");
      var5.append(var4);
      var5.append(", it is now ");
      var5.append(var1);
      var5.append(" x ");
      var5.append(var2);
      Log.v("FlutterView", var5.toString());
      this.viewportMetrics.width = var1;
      this.viewportMetrics.height = var2;
      this.sendViewportMetricsToFlutter();
   }

   @Override
   public boolean onTextInputKeyEvent(KeyEvent var1) {
      return this.textInputPlugin.handleKeyEvent(var1);
   }

   public boolean onTouchEvent(MotionEvent var1) {
      if (!this.isAttachedToFlutterEngine()) {
         return super.onTouchEvent(var1);
      } else {
         this.requestUnbufferedDispatch(var1);
         return this.androidTouchProcessor.onTouchEvent(var1);
      }
   }

   @Override
   public void redispatch(KeyEvent var1) {
      this.getRootView().dispatchKeyEvent(var1);
   }

   public void removeFlutterEngineAttachmentListener(FlutterView.FlutterEngineAttachmentListener var1) {
      this.flutterEngineAttachmentListeners.remove(var1);
   }

   public void removeOnFirstFrameRenderedListener(FlutterUiDisplayListener var1) {
      this.flutterUiDisplayListeners.remove(var1);
   }

   public void revertImageView(Runnable var1) {
      if (this.flutterImageView == null) {
         Log.v("FlutterView", "Tried to revert the image view, but no image view is used.");
      } else {
         RenderSurface var2 = this.previousRenderSurface;
         if (var2 == null) {
            Log.v("FlutterView", "Tried to revert the image view, but no previous surface was used.");
         } else {
            this.renderSurface = var2;
            this.previousRenderSurface = null;
            FlutterRenderer var3 = this.flutterEngine.getRenderer();
            if (this.flutterEngine != null && var3 != null) {
               this.renderSurface.resume();
               var3.addIsDisplayingFlutterUiListener(new FlutterUiDisplayListener(this, var3, var1) {
                  final FlutterView this$0;
                  final Runnable val$onDone;
                  final FlutterRenderer val$renderer;

                  {
                     this.this$0 = var1;
                     this.val$renderer = var2x;
                     this.val$onDone = var3x;
                  }

                  @Override
                  public void onFlutterUiDisplayed() {
                     this.val$renderer.removeIsDisplayingFlutterUiListener(this);
                     this.val$onDone.run();
                     if (!(this.this$0.renderSurface instanceof FlutterImageView) && this.this$0.flutterImageView != null) {
                        this.this$0.flutterImageView.detachFromRenderer();
                        this.this$0.releaseImageView();
                     }
                  }

                  @Override
                  public void onFlutterUiNoLongerDisplayed() {
                  }
               });
            } else {
               this.flutterImageView.detachFromRenderer();
               this.releaseImageView();
               var1.run();
            }
         }
      }
   }

   void sendUserSettingsToFlutter() {
      SettingsChannel.PlatformBrightness var3;
      if ((this.getResources().getConfiguration().uiMode & 48) == 32) {
         var3 = SettingsChannel.PlatformBrightness.dark;
      } else {
         var3 = SettingsChannel.PlatformBrightness.light;
      }

      boolean var2;
      boolean var5;
      label27: {
         label26: {
            TextServicesManager var4 = this.textServicesManager;
            var2 = false;
            if (var4 != null) {
               if (VERSION.SDK_INT < 31) {
                  break label26;
               }

               var5 = _EL.stream(AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.textServicesManager))
                  .anyMatch(new FlutterView$$ExternalSyntheticLambda6());
               if (AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.textServicesManager) && var5) {
                  break label26;
               }
            }

            var5 = false;
            break label27;
         }

         var5 = true;
      }

      SettingsChannel.MessageBuilder var7 = this.flutterEngine
         .getSettingsChannel()
         .startMessage()
         .setTextScaleFactor(this.getResources().getConfiguration().fontScale)
         .setDisplayMetrics(this.getResources().getDisplayMetrics())
         .setNativeSpellCheckServiceDefined(var5);
      var5 = var2;
      if (System.getInt(this.getContext().getContentResolver(), "show_password", 1) == 1) {
         var5 = true;
      }

      var7.setBrieflyShowPassword(var5).setUse24HourFormat(DateFormat.is24HourFormat(this.getContext())).setPlatformBrightness(var3).send();
   }

   public void setDelegate(FlutterViewDelegate var1) {
      this.delegate = var1;
   }

   public void setVisibility(int var1) {
      super.setVisibility(var1);
      RenderSurface var2 = this.renderSurface;
      if (var2 instanceof FlutterSurfaceView) {
         ((FlutterSurfaceView)var2).setVisibility(var1);
      }
   }

   protected void setWindowInfoListenerDisplayFeatures(WindowLayoutInfo var1) {
      List var6 = var1.getDisplayFeatures();
      ArrayList var3 = new ArrayList();

      for (DisplayFeature var5 : var6) {
         StringBuilder var7 = new StringBuilder("WindowInfoTracker Display Feature reported with bounds = ");
         var7.append(var5.getBounds().toString());
         var7.append(" and type = ");
         var7.append(var5.getClass().getSimpleName());
         Log.v("FlutterView", var7.toString());
         if (var5 instanceof FoldingFeature) {
            FoldingFeature var8 = (FoldingFeature)var5;
            FlutterRenderer.DisplayFeatureType var2;
            if (var8.getOcclusionType() == OcclusionType.FULL) {
               var2 = FlutterRenderer.DisplayFeatureType.HINGE;
            } else {
               var2 = FlutterRenderer.DisplayFeatureType.FOLD;
            }

            FlutterRenderer.DisplayFeatureState var9;
            if (var8.getState() == State.FLAT) {
               var9 = FlutterRenderer.DisplayFeatureState.POSTURE_FLAT;
            } else if (var8.getState() == State.HALF_OPENED) {
               var9 = FlutterRenderer.DisplayFeatureState.POSTURE_HALF_OPENED;
            } else {
               var9 = FlutterRenderer.DisplayFeatureState.UNKNOWN;
            }

            var3.add(new FlutterRenderer.DisplayFeature(var5.getBounds(), var2, var9));
         } else {
            var3.add(
               new FlutterRenderer.DisplayFeature(var5.getBounds(), FlutterRenderer.DisplayFeatureType.UNKNOWN, FlutterRenderer.DisplayFeatureState.UNKNOWN)
            );
         }
      }

      this.viewportMetrics.setDisplayFeatures(var3);
      this.sendViewportMetricsToFlutter();
   }

   public interface FlutterEngineAttachmentListener {
      void onFlutterEngineAttachedToFlutterView(FlutterEngine var1);

      void onFlutterEngineDetachedFromFlutterView();
   }

   public static enum ZeroSides {
      BOTH,
      LEFT,
      NONE,
      RIGHT;
      private static final FlutterView.ZeroSides[] $VALUES = $values();
   }
}
