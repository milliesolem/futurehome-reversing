package io.flutter.plugin.platform;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.os.Build.VERSION;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.MotionEvent.PointerCoords;
import android.view.MotionEvent.PointerProperties;
import android.widget.FrameLayout.LayoutParams;
import io.flutter.Log;
import io.flutter.embedding.android.AndroidTouchProcessor;
import io.flutter.embedding.android.FlutterView;
import io.flutter.embedding.android.MotionEventTracker;
import io.flutter.embedding.engine.FlutterOverlaySurface;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.mutatorsstack.FlutterMutatorView;
import io.flutter.embedding.engine.mutatorsstack.FlutterMutatorsStack;
import io.flutter.embedding.engine.renderer.FlutterRenderer;
import io.flutter.embedding.engine.systemchannels.PlatformViewsChannel;
import io.flutter.plugin.editing.TextInputPlugin;
import io.flutter.util.ViewUtils;
import io.flutter.view.AccessibilityBridge;
import io.flutter.view.TextureRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class PlatformViewsController implements PlatformViewsAccessibilityDelegate {
   private static final String TAG = "PlatformViewsController";
   private static Class[] VIEW_TYPES_REQUIRE_VIRTUAL_DISPLAY = new Class[]{SurfaceView.class};
   private static boolean enableImageRenderTarget = true;
   private static boolean enableSurfaceProducerRenderTarget = true;
   private final AccessibilityEventsDelegate accessibilityEventsDelegate;
   private AndroidTouchProcessor androidTouchProcessor;
   private final PlatformViewsChannel.PlatformViewsHandler channelHandler;
   private Context context;
   final HashMap<Context, View> contextToEmbeddedView;
   private final HashSet<Integer> currentFrameUsedOverlayLayerIds;
   private final HashSet<Integer> currentFrameUsedPlatformViewIds;
   private FlutterView flutterView;
   private boolean flutterViewConvertedToImageView;
   private final MotionEventTracker motionEventTracker;
   private int nextOverlayLayerId = 0;
   private final SparseArray<PlatformOverlayView> overlayLayerViews;
   private final SparseArray<FlutterMutatorView> platformViewParent;
   private final SparseArray<PlatformView> platformViews;
   private PlatformViewsChannel platformViewsChannel;
   private final PlatformViewRegistryImpl registry;
   private boolean synchronizeToNativeViewHierarchy;
   private TextInputPlugin textInputPlugin;
   private TextureRegistry textureRegistry;
   private boolean usesSoftwareRendering;
   final HashMap<Integer, VirtualDisplayController> vdControllers;
   private final SparseArray<PlatformViewWrapper> viewWrappers;

   public PlatformViewsController() {
      this.flutterViewConvertedToImageView = false;
      this.synchronizeToNativeViewHierarchy = true;
      this.usesSoftwareRendering = false;
      this.channelHandler = new PlatformViewsChannel.PlatformViewsHandler(this) {
         final PlatformViewsController this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void clearFocus(int var1) {
            View var2;
            if (this.this$0.usesVirtualDisplay(var1)) {
               var2 = this.this$0.vdControllers.get(var1).getView();
            } else {
               PlatformView var3 = (PlatformView)this.this$0.platformViews.get(var1);
               if (var3 == null) {
                  StringBuilder var5 = new StringBuilder("Clearing focus on an unknown view with id: ");
                  var5.append(var1);
                  Log.e("PlatformViewsController", var5.toString());
                  return;
               }

               var2 = var3.getView();
            }

            if (var2 == null) {
               StringBuilder var4 = new StringBuilder("Clearing focus on a null view with id: ");
               var4.append(var1);
               Log.e("PlatformViewsController", var4.toString());
            } else {
               var2.clearFocus();
            }
         }

         @Override
         public void createForPlatformViewLayer(PlatformViewsChannel.PlatformViewCreationRequest var1) {
            this.this$0.enforceMinimumAndroidApiVersion(19);
            this.this$0.ensureValidRequest(var1);
            PlatformView var2 = this.this$0.createPlatformView(var1, false);
            this.this$0.configureForHybridComposition(var2, var1);
         }

         @Override
         public long createForTextureLayer(PlatformViewsChannel.PlatformViewCreationRequest var1) {
            this.this$0.ensureValidRequest(var1);
            int var2 = var1.viewId;
            if (this.this$0.viewWrappers.get(var2) == null) {
               if (this.this$0.textureRegistry == null) {
                  StringBuilder var7 = new StringBuilder("Texture registry is null. This means that platform views controller was detached, view id: ");
                  var7.append(var2);
                  throw new IllegalStateException(var7.toString());
               } else if (this.this$0.flutterView != null) {
                  PlatformView var4 = this.this$0.createPlatformView(var1, true);
                  View var3 = var4.getView();
                  if (var3.getParent() != null) {
                     throw new IllegalStateException("The Android view returned from PlatformView#getView() was already added to a parent view.");
                  } else {
                     if (VERSION.SDK_INT < 23 || ViewUtils.hasChildViewOfType(var3, PlatformViewsController.VIEW_TYPES_REQUIRE_VIRTUAL_DISPLAY)) {
                        if (var1.displayMode == PlatformViewsChannel.PlatformViewCreationRequest.RequestedDisplayMode.TEXTURE_WITH_HYBRID_FALLBACK) {
                           this.this$0.configureForHybridComposition(var4, var1);
                           return -2L;
                        }

                        if (!this.this$0.usesSoftwareRendering) {
                           return this.this$0.configureForVirtualDisplay(var4, var1);
                        }
                     }

                     return this.this$0.configureForTextureLayerComposition(var4, var1);
                  }
               } else {
                  StringBuilder var6 = new StringBuilder(
                     "Flutter view is null. This means the platform views controller doesn't have an attached view, view id: "
                  );
                  var6.append(var2);
                  throw new IllegalStateException(var6.toString());
               }
            } else {
               StringBuilder var5 = new StringBuilder("Trying to create an already created platform view, view id: ");
               var5.append(var2);
               throw new IllegalStateException(var5.toString());
            }
         }

         @Override
         public void dispose(int var1) {
            PlatformView var3 = (PlatformView)this.this$0.platformViews.get(var1);
            if (var3 == null) {
               StringBuilder var9 = new StringBuilder("Disposing unknown platform view with id: ");
               var9.append(var1);
               Log.e("PlatformViewsController", var9.toString());
            } else {
               if (var3.getView() != null) {
                  View var2 = var3.getView();
                  ViewGroup var4 = (ViewGroup)var2.getParent();
                  if (var4 != null) {
                     var4.removeView(var2);
                  }
               }

               this.this$0.platformViews.remove(var1);

               try {
                  var3.dispose();
               } catch (RuntimeException var5) {
                  Log.e("PlatformViewsController", "Disposing platform view threw an exception", var5);
               }

               if (this.this$0.usesVirtualDisplay(var1)) {
                  VirtualDisplayController var12 = this.this$0.vdControllers.get(var1);
                  View var8 = var12.getView();
                  if (var8 != null) {
                     this.this$0.contextToEmbeddedView.remove(var8.getContext());
                  }

                  var12.dispose();
                  this.this$0.vdControllers.remove(var1);
               } else {
                  PlatformViewWrapper var6 = (PlatformViewWrapper)this.this$0.viewWrappers.get(var1);
                  if (var6 != null) {
                     var6.removeAllViews();
                     var6.release();
                     var6.unsetOnDescendantFocusChangeListener();
                     ViewGroup var11 = (ViewGroup)var6.getParent();
                     if (var11 != null) {
                        var11.removeView(var6);
                     }

                     this.this$0.viewWrappers.remove(var1);
                  } else {
                     FlutterMutatorView var10 = (FlutterMutatorView)this.this$0.platformViewParent.get(var1);
                     if (var10 != null) {
                        var10.removeAllViews();
                        var10.unsetOnDescendantFocusChangeListener();
                        ViewGroup var7 = (ViewGroup)var10.getParent();
                        if (var7 != null) {
                           var7.removeView(var10);
                        }

                        this.this$0.platformViewParent.remove(var1);
                     }
                  }
               }
            }
         }

         @Override
         public void offset(int var1, double var2, double var4) {
            if (!this.this$0.usesVirtualDisplay(var1)) {
               PlatformViewWrapper var7 = (PlatformViewWrapper)this.this$0.viewWrappers.get(var1);
               if (var7 == null) {
                  StringBuilder var10 = new StringBuilder("Setting offset for unknown platform view with id: ");
                  var10.append(var1);
                  Log.e("PlatformViewsController", var10.toString());
               } else {
                  int var6 = this.this$0.toPhysicalPixels(var2);
                  var1 = this.this$0.toPhysicalPixels(var4);
                  LayoutParams var8 = (LayoutParams)var7.getLayoutParams();
                  var8.topMargin = var6;
                  var8.leftMargin = var1;
                  var7.setLayoutParams(var8);
               }
            }
         }

         @Override
         public void onTouch(PlatformViewsChannel.PlatformViewTouch var1) {
            int var3 = var1.viewId;
            float var2 = this.this$0.context.getResources().getDisplayMetrics().density;
            if (this.this$0.usesVirtualDisplay(var3)) {
               this.this$0.vdControllers.get(var3).dispatchTouchEvent(this.this$0.toMotionEvent(var2, var1, true));
            } else {
               PlatformView var4 = (PlatformView)this.this$0.platformViews.get(var3);
               if (var4 == null) {
                  StringBuilder var6 = new StringBuilder("Sending touch to an unknown view with id: ");
                  var6.append(var3);
                  Log.e("PlatformViewsController", var6.toString());
               } else {
                  View var7 = var4.getView();
                  if (var7 == null) {
                     StringBuilder var5 = new StringBuilder("Sending touch to a null view with id: ");
                     var5.append(var3);
                     Log.e("PlatformViewsController", var5.toString());
                  } else {
                     var7.dispatchTouchEvent(this.this$0.toMotionEvent(var2, var1, false));
                  }
               }
            }
         }

         @Override
         public void resize(PlatformViewsChannel.PlatformViewResizeRequest var1, PlatformViewsChannel.PlatformViewBufferResized var2) {
            int var4 = this.this$0.toPhysicalPixels(var1.newLogicalWidth);
            int var5 = this.this$0.toPhysicalPixels(var1.newLogicalHeight);
            int var6 = var1.viewId;
            if (this.this$0.usesVirtualDisplay(var6)) {
               float var3 = this.this$0.getDisplayDensity();
               VirtualDisplayController var11 = this.this$0.vdControllers.get(var6);
               this.this$0.lockInputConnection(var11);
               var11.resize(var4, var5, new PlatformViewsController$1$$ExternalSyntheticLambda0(this, var11, var3, var2));
            } else {
               PlatformView var7 = (PlatformView)this.this$0.platformViews.get(var6);
               PlatformViewWrapper var9 = (PlatformViewWrapper)this.this$0.viewWrappers.get(var6);
               if (var7 != null && var9 != null) {
                  if (var4 > var9.getRenderTargetWidth() || var5 > var9.getRenderTargetHeight()) {
                     var9.resizeRenderTarget(var4, var5);
                  }

                  android.view.ViewGroup.LayoutParams var8 = var9.getLayoutParams();
                  var8.width = var4;
                  var8.height = var5;
                  var9.setLayoutParams(var8);
                  View var13 = var7.getView();
                  if (var13 != null) {
                     android.view.ViewGroup.LayoutParams var12 = var13.getLayoutParams();
                     var12.width = var4;
                     var12.height = var5;
                     var13.setLayoutParams(var12);
                  }

                  var2.run(
                     new PlatformViewsChannel.PlatformViewBufferSize(
                        this.this$0.toLogicalPixels((double)var9.getRenderTargetWidth()), this.this$0.toLogicalPixels((double)var9.getRenderTargetHeight())
                     )
                  );
               } else {
                  StringBuilder var10 = new StringBuilder("Resizing unknown platform view with id: ");
                  var10.append(var6);
                  Log.e("PlatformViewsController", var10.toString());
               }
            }
         }

         @Override
         public void setDirection(int var1, int var2) {
            if (PlatformViewsController.validateDirection(var2)) {
               View var4;
               if (this.this$0.usesVirtualDisplay(var1)) {
                  var4 = this.this$0.vdControllers.get(var1).getView();
               } else {
                  PlatformView var5 = (PlatformView)this.this$0.platformViews.get(var1);
                  if (var5 == null) {
                     StringBuilder var7 = new StringBuilder("Setting direction to an unknown view with id: ");
                     var7.append(var1);
                     Log.e("PlatformViewsController", var7.toString());
                     return;
                  }

                  var4 = var5.getView();
               }

               if (var4 == null) {
                  StringBuilder var6 = new StringBuilder("Setting direction to a null view with id: ");
                  var6.append(var1);
                  Log.e("PlatformViewsController", var6.toString());
               } else {
                  var4.setLayoutDirection(var2);
               }
            } else {
               StringBuilder var3 = new StringBuilder("Trying to set unknown direction value: ");
               var3.append(var2);
               var3.append("(view id: ");
               var3.append(var1);
               var3.append(")");
               throw new IllegalStateException(var3.toString());
            }
         }

         @Override
         public void synchronizeToNativeViewHierarchy(boolean var1) {
            this.this$0.synchronizeToNativeViewHierarchy = var1;
         }
      };
      this.registry = new PlatformViewRegistryImpl();
      this.vdControllers = new HashMap<>();
      this.accessibilityEventsDelegate = new AccessibilityEventsDelegate();
      this.contextToEmbeddedView = new HashMap<>();
      this.overlayLayerViews = new SparseArray();
      this.currentFrameUsedOverlayLayerIds = new HashSet<>();
      this.currentFrameUsedPlatformViewIds = new HashSet<>();
      this.viewWrappers = new SparseArray();
      this.platformViews = new SparseArray();
      this.platformViewParent = new SparseArray();
      this.motionEventTracker = MotionEventTracker.getInstance();
   }

   private void configureForHybridComposition(PlatformView var1, PlatformViewsChannel.PlatformViewCreationRequest var2) {
      this.enforceMinimumAndroidApiVersion(19);
      StringBuilder var3 = new StringBuilder("Using hybrid composition for platform view: ");
      var3.append(var2.viewId);
      Log.i("PlatformViewsController", var3.toString());
   }

   private long configureForVirtualDisplay(PlatformView var1, PlatformViewsChannel.PlatformViewCreationRequest var2) {
      this.enforceMinimumAndroidApiVersion(20);
      StringBuilder var5 = new StringBuilder("Hosting view in a virtual display for platform view: ");
      var5.append(var2.viewId);
      Log.i("PlatformViewsController", var5.toString());
      PlatformViewRenderTarget var9 = makePlatformViewRenderTarget(this.textureRegistry);
      int var3 = this.toPhysicalPixels(var2.logicalWidth);
      int var4 = this.toPhysicalPixels(var2.logicalHeight);
      VirtualDisplayController var6 = VirtualDisplayController.create(
         this.context,
         this.accessibilityEventsDelegate,
         var1,
         var9,
         var3,
         var4,
         var2.viewId,
         null,
         new PlatformViewsController$$ExternalSyntheticLambda2(this, var2)
      );
      if (var6 != null) {
         this.vdControllers.put(var2.viewId, var6);
         View var8 = var1.getView();
         this.contextToEmbeddedView.put(var8.getContext(), var8);
         return var9.getId();
      } else {
         StringBuilder var7 = new StringBuilder("Failed creating virtual display for a ");
         var7.append(var2.viewType);
         var7.append(" with id: ");
         var7.append(var2.viewId);
         throw new IllegalStateException(var7.toString());
      }
   }

   private void diposeAllViews() {
      while (this.platformViews.size() > 0) {
         int var1 = this.platformViews.keyAt(0);
         this.channelHandler.dispose(var1);
      }
   }

   private void enforceMinimumAndroidApiVersion(int var1) {
      if (VERSION.SDK_INT < var1) {
         StringBuilder var2 = new StringBuilder("Trying to use platform views with API ");
         var2.append(VERSION.SDK_INT);
         var2.append(", required API level is: ");
         var2.append(var1);
         throw new IllegalStateException(var2.toString());
      }
   }

   private void ensureValidRequest(PlatformViewsChannel.PlatformViewCreationRequest var1) {
      if (!validateDirection(var1.direction)) {
         StringBuilder var2 = new StringBuilder("Trying to create a view with unknown direction value: ");
         var2.append(var1.direction);
         var2.append("(view id: ");
         var2.append(var1.viewId);
         var2.append(")");
         throw new IllegalStateException(var2.toString());
      }
   }

   private void finishFrame(boolean var1) {
      for (int var2 = 0; var2 < this.overlayLayerViews.size(); var2++) {
         int var3 = this.overlayLayerViews.keyAt(var2);
         PlatformOverlayView var4 = (PlatformOverlayView)this.overlayLayerViews.valueAt(var2);
         if (this.currentFrameUsedOverlayLayerIds.contains(var3)) {
            this.flutterView.attachOverlaySurfaceToRender(var4);
            var1 &= var4.acquireLatestImage();
         } else {
            if (!this.flutterViewConvertedToImageView) {
               var4.detachFromRenderer();
            }

            var4.setVisibility(8);
            this.flutterView.removeView(var4);
         }
      }

      for (int var5 = 0; var5 < this.platformViewParent.size(); var5++) {
         int var6 = this.platformViewParent.keyAt(var5);
         View var7 = (View)this.platformViewParent.get(var6);
         if (!this.currentFrameUsedPlatformViewIds.contains(var6) || !var1 && this.synchronizeToNativeViewHierarchy) {
            var7.setVisibility(8);
         } else {
            var7.setVisibility(0);
         }
      }
   }

   private float getDisplayDensity() {
      return this.context.getResources().getDisplayMetrics().density;
   }

   private void initializeRootImageViewIfNeeded() {
      if (this.synchronizeToNativeViewHierarchy && !this.flutterViewConvertedToImageView) {
         this.flutterView.convertToImageView();
         this.flutterViewConvertedToImageView = true;
      }
   }

   private void lockInputConnection(VirtualDisplayController var1) {
      TextInputPlugin var2 = this.textInputPlugin;
      if (var2 != null) {
         var2.lockPlatformViewInputConnection();
         var1.onInputConnectionLocked();
      }
   }

   private static PlatformViewRenderTarget makePlatformViewRenderTarget(TextureRegistry var0) {
      if (enableSurfaceProducerRenderTarget && VERSION.SDK_INT >= 29) {
         TextureRegistry.SurfaceProducer var3 = var0.createSurfaceProducer();
         Log.i("PlatformViewsController", "PlatformView is using SurfaceProducer backend");
         return new SurfaceProducerPlatformViewRenderTarget(var3);
      } else if (enableImageRenderTarget && VERSION.SDK_INT >= 29) {
         TextureRegistry.ImageTextureEntry var2 = var0.createImageTexture();
         Log.i("PlatformViewsController", "PlatformView is using ImageReader backend");
         return new ImageReaderPlatformViewRenderTarget(var2);
      } else {
         TextureRegistry.SurfaceTextureEntry var1 = var0.createSurfaceTexture();
         Log.i("PlatformViewsController", "PlatformView is using SurfaceTexture backend");
         return new SurfaceTexturePlatformViewRenderTarget(var1);
      }
   }

   private void maybeInvokeOnFlutterViewAttached(PlatformView var1) {
      FlutterView var2 = this.flutterView;
      if (var2 == null) {
         Log.i("PlatformViewsController", "null flutterView");
      } else {
         var1.onFlutterViewAttached(var2);
      }
   }

   private static PointerCoords parsePointerCoords(Object var0, float var1) {
      var0 = var0;
      PointerCoords var6 = new PointerCoords();
      var6.orientation = (float)((Double)var0.get(0)).doubleValue();
      var6.pressure = (float)((Double)var0.get(1)).doubleValue();
      var6.size = (float)((Double)var0.get(2)).doubleValue();
      double var4 = (Double)var0.get(3);
      double var2 = var1;
      var6.toolMajor = (float)(var4 * var2);
      var6.toolMinor = (float)((Double)var0.get(4) * var2);
      var6.touchMajor = (float)((Double)var0.get(5) * var2);
      var6.touchMinor = (float)((Double)var0.get(6) * var2);
      var6.x = (float)((Double)var0.get(7) * var2);
      var6.y = (float)((Double)var0.get(8) * var2);
      return var6;
   }

   private static List<PointerCoords> parsePointerCoordsList(Object var0, float var1) {
      List var2 = var0;
      var0 = new ArrayList();
      Iterator var4 = var2.iterator();

      while (var4.hasNext()) {
         var0.add(parsePointerCoords(var4.next(), var1));
      }

      return var0;
   }

   private static PointerProperties parsePointerProperties(Object var0) {
      var0 = var0;
      PointerProperties var1 = new PointerProperties();
      var1.id = (Integer)var0.get(0);
      var1.toolType = (Integer)var0.get(1);
      return var1;
   }

   private static List<PointerProperties> parsePointerPropertiesList(Object var0) {
      List var1 = var0;
      var0 = new ArrayList();
      Iterator var3 = var1.iterator();

      while (var3.hasNext()) {
         var0.add(parsePointerProperties(var3.next()));
      }

      return var0;
   }

   private void removeOverlaySurfaces() {
      if (this.flutterView == null) {
         Log.e("PlatformViewsController", "removeOverlaySurfaces called while flutter view is null");
      } else {
         for (int var1 = 0; var1 < this.overlayLayerViews.size(); var1++) {
            this.flutterView.removeView((View)this.overlayLayerViews.valueAt(var1));
         }

         this.overlayLayerViews.clear();
      }
   }

   private int toLogicalPixels(double var1) {
      return this.toLogicalPixels(var1, this.getDisplayDensity());
   }

   private int toLogicalPixels(double var1, float var3) {
      return (int)Math.round(var1 / var3);
   }

   private int toPhysicalPixels(double var1) {
      return (int)Math.round(var1 * this.getDisplayDensity());
   }

   private static void translateMotionEvent(MotionEvent var0, PointerCoords[] var1) {
      if (var1.length >= 1) {
         var0.offsetLocation(var1[0].x - var0.getX(), var1[0].y - var0.getY());
      }
   }

   private void unlockInputConnection(VirtualDisplayController var1) {
      TextInputPlugin var2 = this.textInputPlugin;
      if (var2 != null) {
         var2.unlockPlatformViewInputConnection();
         var1.onInputConnectionUnlocked();
      }
   }

   private static boolean validateDirection(int var0) {
      boolean var2 = true;
      boolean var1 = var2;
      if (var0 != 0) {
         if (var0 == 1) {
            var1 = var2;
         } else {
            var1 = false;
         }
      }

      return var1;
   }

   public void attach(Context var1, TextureRegistry var2, DartExecutor var3) {
      if (this.context == null) {
         this.context = var1;
         this.textureRegistry = var2;
         PlatformViewsChannel var4 = new PlatformViewsChannel(var3);
         this.platformViewsChannel = var4;
         var4.setPlatformViewsHandler(this.channelHandler);
      } else {
         throw new AssertionError(
            "A PlatformViewsController can only be attached to a single output target.\nattach was called while the PlatformViewsController was already attached."
         );
      }
   }

   @Override
   public void attachAccessibilityBridge(AccessibilityBridge var1) {
      this.accessibilityEventsDelegate.setAccessibilityBridge(var1);
   }

   public void attachTextInputPlugin(TextInputPlugin var1) {
      this.textInputPlugin = var1;
   }

   public void attachToFlutterRenderer(FlutterRenderer var1) {
      this.androidTouchProcessor = new AndroidTouchProcessor(var1, true);
   }

   public void attachToView(FlutterView var1) {
      this.flutterView = var1;
      byte var4 = 0;

      for (int var2 = 0; var2 < this.viewWrappers.size(); var2++) {
         PlatformViewWrapper var5 = (PlatformViewWrapper)this.viewWrappers.valueAt(var2);
         this.flutterView.addView(var5);
      }

      int var7 = 0;

      while (true) {
         int var3 = var4;
         if (var7 >= this.platformViewParent.size()) {
            while (var3 < this.platformViews.size()) {
               ((PlatformView)this.platformViews.valueAt(var3)).onFlutterViewAttached(this.flutterView);
               var3++;
            }

            return;
         }

         FlutterMutatorView var6 = (FlutterMutatorView)this.platformViewParent.valueAt(var7);
         this.flutterView.addView(var6);
         var7++;
      }
   }

   public boolean checkInputConnectionProxy(View var1) {
      if (var1 == null) {
         return false;
      } else if (!this.contextToEmbeddedView.containsKey(var1.getContext())) {
         return false;
      } else {
         View var2 = this.contextToEmbeddedView.get(var1.getContext());
         return var2 == var1 ? true : var2.checkInputConnectionProxy(var1);
      }
   }

   public long configureForTextureLayerComposition(PlatformView var1, PlatformViewsChannel.PlatformViewCreationRequest var2) {
      this.enforceMinimumAndroidApiVersion(23);
      StringBuilder var9 = new StringBuilder("Hosting view in view hierarchy for platform view: ");
      var9.append(var2.viewId);
      Log.i("PlatformViewsController", var9.toString());
      int var6 = this.toPhysicalPixels(var2.logicalWidth);
      int var4 = this.toPhysicalPixels(var2.logicalHeight);
      long var7;
      PlatformViewWrapper var11;
      if (this.usesSoftwareRendering) {
         var11 = new PlatformViewWrapper(this.context);
         var7 = -1L;
      } else {
         PlatformViewRenderTarget var10 = makePlatformViewRenderTarget(this.textureRegistry);
         var11 = new PlatformViewWrapper(this.context, var10);
         var7 = var10.getId();
      }

      var11.setTouchProcessor(this.androidTouchProcessor);
      var11.resizeRenderTarget(var6, var4);
      LayoutParams var12 = new LayoutParams(var6, var4);
      int var3 = this.toPhysicalPixels(var2.logicalTop);
      int var5 = this.toPhysicalPixels(var2.logicalLeft);
      var12.topMargin = var3;
      var12.leftMargin = var5;
      var11.setLayoutParams(var12);
      View var13 = var1.getView();
      var13.setLayoutParams(new LayoutParams(var6, var4));
      var13.setImportantForAccessibility(4);
      var11.addView(var13);
      var11.setOnDescendantFocusChangeListener(new PlatformViewsController$$ExternalSyntheticLambda1(this, var2));
      this.flutterView.addView(var11);
      this.viewWrappers.append(var2.viewId, var11);
      this.maybeInvokeOnFlutterViewAttached(var1);
      return var7;
   }

   public FlutterOverlaySurface createOverlaySurface() {
      return this.createOverlaySurface(
         new PlatformOverlayView(this.flutterView.getContext(), this.flutterView.getWidth(), this.flutterView.getHeight(), this.accessibilityEventsDelegate)
      );
   }

   public FlutterOverlaySurface createOverlaySurface(PlatformOverlayView var1) {
      int var2 = this.nextOverlayLayerId++;
      this.overlayLayerViews.put(var2, var1);
      return new FlutterOverlaySurface(var2, var1.getSurface());
   }

   public PlatformView createPlatformView(PlatformViewsChannel.PlatformViewCreationRequest var1, boolean var2) {
      PlatformViewFactory var5 = this.registry.getFactory(var1.viewType);
      if (var5 != null) {
         Object var6;
         if (var1.params != null) {
            var6 = (View)var5.getCreateArgsCodec().decodeMessage(var1.params);
         } else {
            var6 = null;
         }

         Object var4;
         if (var2) {
            var4 = new MutableContextWrapper(this.context);
         } else {
            var4 = this.context;
         }

         var4 = var5.create(var4, var1.viewId, var6);
         var6 = var4.getView();
         if (var6 != null) {
            var6.setLayoutDirection(var1.direction);
            this.platformViews.put(var1.viewId, var4);
            this.maybeInvokeOnFlutterViewAttached(var4);
            return var4;
         } else {
            throw new IllegalStateException("PlatformView#getView() returned null, but an Android view reference was expected.");
         }
      } else {
         StringBuilder var3 = new StringBuilder("Trying to create a platform view of unregistered type: ");
         var3.append(var1.viewType);
         throw new IllegalStateException(var3.toString());
      }
   }

   public void destroyOverlaySurfaces() {
      for (int var1 = 0; var1 < this.overlayLayerViews.size(); var1++) {
         PlatformOverlayView var2 = (PlatformOverlayView)this.overlayLayerViews.valueAt(var1);
         var2.detachFromRenderer();
         var2.closeImageReader();
      }
   }

   public void detach() {
      PlatformViewsChannel var1 = this.platformViewsChannel;
      if (var1 != null) {
         var1.setPlatformViewsHandler(null);
      }

      this.destroyOverlaySurfaces();
      this.platformViewsChannel = null;
      this.context = null;
      this.textureRegistry = null;
   }

   @Override
   public void detachAccessibilityBridge() {
      this.accessibilityEventsDelegate.setAccessibilityBridge(null);
   }

   public void detachFromView() {
      byte var2 = 0;

      for (int var1 = 0; var1 < this.viewWrappers.size(); var1++) {
         PlatformViewWrapper var3 = (PlatformViewWrapper)this.viewWrappers.valueAt(var1);
         this.flutterView.removeView(var3);
      }

      for (int var4 = 0; var4 < this.platformViewParent.size(); var4++) {
         FlutterMutatorView var6 = (FlutterMutatorView)this.platformViewParent.valueAt(var4);
         this.flutterView.removeView(var6);
      }

      this.destroyOverlaySurfaces();
      this.removeOverlaySurfaces();
      this.flutterView = null;
      this.flutterViewConvertedToImageView = false;

      for (int var5 = var2; var5 < this.platformViews.size(); var5++) {
         ((PlatformView)this.platformViews.valueAt(var5)).onFlutterViewDetached();
      }
   }

   public void detachTextInputPlugin() {
      this.textInputPlugin = null;
   }

   public void disposePlatformView(int var1) {
      this.channelHandler.dispose(var1);
   }

   public SparseArray<PlatformOverlayView> getOverlayLayerViews() {
      return this.overlayLayerViews;
   }

   @Override
   public View getPlatformViewById(int var1) {
      if (this.usesVirtualDisplay(var1)) {
         return this.vdControllers.get(var1).getView();
      } else {
         PlatformView var2 = (PlatformView)this.platformViews.get(var1);
         return var2 == null ? null : var2.getView();
      }
   }

   public PlatformViewRegistry getRegistry() {
      return this.registry;
   }

   boolean initializePlatformViewIfNeeded(int var1) {
      PlatformView var2 = (PlatformView)this.platformViews.get(var1);
      if (var2 == null) {
         return false;
      } else if (this.platformViewParent.get(var1) != null) {
         return true;
      } else {
         View var4 = var2.getView();
         if (var4 != null) {
            if (var4.getParent() == null) {
               Context var3 = this.context;
               FlutterMutatorView var5 = new FlutterMutatorView(var3, var3.getResources().getDisplayMetrics().density, this.androidTouchProcessor);
               var5.setOnDescendantFocusChangeListener(new PlatformViewsController$$ExternalSyntheticLambda0(this, var1));
               this.platformViewParent.put(var1, var5);
               var4.setImportantForAccessibility(4);
               var5.addView(var4);
               this.flutterView.addView(var5);
               return true;
            } else {
               throw new IllegalStateException("The Android view returned from PlatformView#getView() was already added to a parent view.");
            }
         } else {
            throw new IllegalStateException("PlatformView#getView() returned null, but an Android view reference was expected.");
         }
      }
   }

   public void onAttachedToJNI() {
   }

   public void onBeginFrame() {
      this.currentFrameUsedOverlayLayerIds.clear();
      this.currentFrameUsedPlatformViewIds.clear();
   }

   public void onDetachedFromJNI() {
      this.diposeAllViews();
   }

   public void onDisplayOverlaySurface(int var1, int var2, int var3, int var4, int var5) {
      if (this.overlayLayerViews.get(var1) != null) {
         this.initializeRootImageViewIfNeeded();
         PlatformOverlayView var8 = (PlatformOverlayView)this.overlayLayerViews.get(var1);
         if (var8.getParent() == null) {
            this.flutterView.addView(var8);
         }

         LayoutParams var7 = new LayoutParams(var4, var5);
         var7.leftMargin = var2;
         var7.topMargin = var3;
         var8.setLayoutParams(var7);
         var8.setVisibility(0);
         var8.bringToFront();
         this.currentFrameUsedOverlayLayerIds.add(var1);
      } else {
         StringBuilder var6 = new StringBuilder("The overlay surface (id:");
         var6.append(var1);
         var6.append(") doesn't exist");
         throw new IllegalStateException(var6.toString());
      }
   }

   public void onDisplayPlatformView(int var1, int var2, int var3, int var4, int var5, int var6, int var7, FlutterMutatorsStack var8) {
      this.initializeRootImageViewIfNeeded();
      if (this.initializePlatformViewIfNeeded(var1)) {
         FlutterMutatorView var9 = (FlutterMutatorView)this.platformViewParent.get(var1);
         var9.readyToDisplay(var8, var2, var3, var4, var5);
         var9.setVisibility(0);
         var9.bringToFront();
         LayoutParams var11 = new LayoutParams(var6, var7);
         View var10 = ((PlatformView)this.platformViews.get(var1)).getView();
         if (var10 != null) {
            var10.setLayoutParams(var11);
            var10.bringToFront();
         }

         this.currentFrameUsedPlatformViewIds.add(var1);
      }
   }

   public void onEndFrame() {
      boolean var1 = this.flutterViewConvertedToImageView;
      boolean var2 = false;
      if (var1 && this.currentFrameUsedPlatformViewIds.isEmpty()) {
         this.flutterViewConvertedToImageView = false;
         this.flutterView.revertImageView(new PlatformViewsController$$ExternalSyntheticLambda3(this));
      } else {
         var1 = var2;
         if (this.flutterViewConvertedToImageView) {
            var1 = var2;
            if (this.flutterView.acquireLatestImageViewFrame()) {
               var1 = true;
            }
         }

         this.finishFrame(var1);
      }
   }

   public void onPreEngineRestart() {
      this.diposeAllViews();
   }

   public void onResume() {
      Iterator var1 = this.vdControllers.values().iterator();

      while (var1.hasNext()) {
         ((VirtualDisplayController)var1.next()).resetSurface();
      }
   }

   public void onTrimMemory(int var1) {
      if (var1 >= 40) {
         Iterator var2 = this.vdControllers.values().iterator();

         while (var2.hasNext()) {
            ((VirtualDisplayController)var2.next()).clearSurface();
         }
      }
   }

   public void setSoftwareRendering(boolean var1) {
      this.usesSoftwareRendering = var1;
   }

   public MotionEvent toMotionEvent(float var1, PlatformViewsChannel.PlatformViewTouch var2, boolean var3) {
      MotionEventTracker.MotionEventId var4 = MotionEventTracker.MotionEventId.from(var2.motionEventId);
      MotionEvent var5 = this.motionEventTracker.pop(var4);
      PointerCoords[] var6 = parsePointerCoordsList(var2.rawPointerCoords, var1).toArray(new PointerCoords[var2.pointerCount]);
      if (!var3 && var5 != null) {
         translateMotionEvent(var5, var6);
         return var5;
      } else {
         PointerProperties[] var7 = parsePointerPropertiesList(var2.rawPointerPropertiesList).toArray(new PointerProperties[var2.pointerCount]);
         return MotionEvent.obtain(
            var2.downTime.longValue(),
            var2.eventTime.longValue(),
            var2.action,
            var2.pointerCount,
            var7,
            var6,
            var2.metaState,
            var2.buttonState,
            var2.xPrecision,
            var2.yPrecision,
            var2.deviceId,
            var2.edgeFlags,
            var2.source,
            var2.flags
         );
      }
   }

   @Override
   public boolean usesVirtualDisplay(int var1) {
      return this.vdControllers.containsKey(var1);
   }
}
