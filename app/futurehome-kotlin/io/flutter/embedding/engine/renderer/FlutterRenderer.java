package io.flutter.embedding.engine.renderer;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageReader.Builder;
import android.os.Handler;
import android.os.Looper;
import android.os.Build.VERSION;
import android.view.Surface;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import io.flutter.view.TextureRegistry;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class FlutterRenderer implements TextureRegistry {
   private static final String TAG = "FlutterRenderer";
   public static boolean debugDisableSurfaceClear;
   public static boolean debugForceSurfaceProducerGlTextures;
   private final FlutterJNI flutterJNI;
   private final FlutterUiDisplayListener flutterUiDisplayListener;
   private final Handler handler;
   private final List<FlutterRenderer.ImageReaderSurfaceProducer> imageReaderProducers;
   private boolean isDisplayingFlutterUi;
   private final AtomicLong nextTextureId = new AtomicLong(0L);
   private final Set<WeakReference<TextureRegistry.OnTrimMemoryListener>> onTrimMemoryListeners;
   private Surface surface;

   public FlutterRenderer(FlutterJNI var1) {
      this.isDisplayingFlutterUi = false;
      this.handler = new Handler();
      this.onTrimMemoryListeners = new HashSet<>();
      this.imageReaderProducers = new ArrayList<>();
      FlutterUiDisplayListener var2 = new FlutterUiDisplayListener(this) {
         final FlutterRenderer this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onFlutterUiDisplayed() {
            this.this$0.isDisplayingFlutterUi = true;
         }

         @Override
         public void onFlutterUiNoLongerDisplayed() {
            this.this$0.isDisplayingFlutterUi = false;
         }
      };
      this.flutterUiDisplayListener = var2;
      this.flutterJNI = var1;
      var1.addIsDisplayingFlutterUiListener(var2);
      ProcessLifecycleOwner.get().getLifecycle().addObserver(new DefaultLifecycleObserver(this) {
         final FlutterRenderer this$0;

         {
            this.this$0 = var1;
         }

         public void onResume(LifecycleOwner var1) {
            Log.v("FlutterRenderer", "onResume called; notifying SurfaceProducers");

            for (FlutterRenderer.ImageReaderSurfaceProducer var3 : this.this$0.imageReaderProducers) {
               if (var3.callback != null && var3.notifiedDestroy) {
                  var3.notifiedDestroy = false;
                  var3.callback.onSurfaceAvailable();
               }
            }
         }
      });
   }

   private void clearDeadListeners() {
      Iterator var1 = this.onTrimMemoryListeners.iterator();

      while (var1.hasNext()) {
         if ((TextureRegistry.OnTrimMemoryListener)((WeakReference)var1.next()).get() == null) {
            var1.remove();
         }
      }
   }

   private void registerImageTexture(long var1, TextureRegistry.ImageConsumer var3) {
      this.flutterJNI.registerImageTexture(var1, var3);
   }

   private TextureRegistry.SurfaceTextureEntry registerSurfaceTexture(long var1, SurfaceTexture var3) {
      var3.detachFromGLContext();
      FlutterRenderer.SurfaceTextureRegistryEntry var5 = new FlutterRenderer.SurfaceTextureRegistryEntry(this, var1, var3);
      StringBuilder var4 = new StringBuilder("New SurfaceTexture ID: ");
      var4.append(var5.id());
      Log.v("FlutterRenderer", var4.toString());
      this.registerTexture(var5.id(), var5.textureWrapper());
      this.addOnTrimMemoryListener(var5);
      return var5;
   }

   private void registerTexture(long var1, SurfaceTextureWrapper var3) {
      this.flutterJNI.registerTexture(var1, var3);
   }

   private void scheduleEngineFrame() {
      this.flutterJNI.scheduleFrame();
   }

   private void translateFeatureBounds(int[] var1, int var2, Rect var3) {
      var1[var2] = var3.left;
      var1[var2 + 1] = var3.top;
      var1[var2 + 2] = var3.right;
      var1[var2 + 3] = var3.bottom;
   }

   private void unregisterTexture(long var1) {
      this.flutterJNI.unregisterTexture(var1);
   }

   public void addIsDisplayingFlutterUiListener(FlutterUiDisplayListener var1) {
      this.flutterJNI.addIsDisplayingFlutterUiListener(var1);
      if (this.isDisplayingFlutterUi) {
         var1.onFlutterUiDisplayed();
      }
   }

   void addOnTrimMemoryListener(TextureRegistry.OnTrimMemoryListener var1) {
      this.clearDeadListeners();
      this.onTrimMemoryListeners.add(new WeakReference<>(var1));
   }

   @Override
   public TextureRegistry.ImageTextureEntry createImageTexture() {
      FlutterRenderer.ImageTextureRegistryEntry var1 = new FlutterRenderer.ImageTextureRegistryEntry(this, this.nextTextureId.getAndIncrement());
      StringBuilder var2 = new StringBuilder("New ImageTextureEntry ID: ");
      var2.append(var1.id());
      Log.v("FlutterRenderer", var2.toString());
      this.registerImageTexture(var1.id(), var1);
      return var1;
   }

   @Override
   public TextureRegistry.SurfaceProducer createSurfaceProducer() {
      Object var3;
      if (!debugForceSurfaceProducerGlTextures && VERSION.SDK_INT >= 29 && !this.flutterJNI.ShouldDisableAHB()) {
         long var1 = this.nextTextureId.getAndIncrement();
         var3 = new FlutterRenderer.ImageReaderSurfaceProducer(this, var1);
         this.registerImageTexture(var1, (TextureRegistry.ImageConsumer)var3);
         this.addOnTrimMemoryListener((TextureRegistry.OnTrimMemoryListener)var3);
         this.imageReaderProducers.add((FlutterRenderer.ImageReaderSurfaceProducer)var3);
         StringBuilder var6 = new StringBuilder("New ImageReaderSurfaceProducer ID: ");
         var6.append(var1);
         Log.v("FlutterRenderer", var6.toString());
      } else {
         TextureRegistry.SurfaceTextureEntry var4 = this.createSurfaceTexture();
         var3 = new SurfaceTextureSurfaceProducer(var4.id(), this.handler, this.flutterJNI, var4);
         StringBuilder var5 = new StringBuilder("New SurfaceTextureSurfaceProducer ID: ");
         var5.append(var4.id());
         Log.v("FlutterRenderer", var5.toString());
      }

      return (TextureRegistry.SurfaceProducer)var3;
   }

   @Override
   public TextureRegistry.SurfaceTextureEntry createSurfaceTexture() {
      Log.v("FlutterRenderer", "Creating a SurfaceTexture.");
      return this.registerSurfaceTexture(new SurfaceTexture(0));
   }

   public void dispatchPointerDataPacket(ByteBuffer var1, int var2) {
      this.flutterJNI.dispatchPointerDataPacket(var1, var2);
   }

   public void dispatchSemanticsAction(int var1, int var2, ByteBuffer var3, int var4) {
      this.flutterJNI.dispatchSemanticsAction(var1, var2, var3, var4);
   }

   public Bitmap getBitmap() {
      return this.flutterJNI.getBitmap();
   }

   public boolean isDisplayingFlutterUi() {
      return this.isDisplayingFlutterUi;
   }

   public boolean isSoftwareRenderingEnabled() {
      return this.flutterJNI.getIsSoftwareRenderingEnabled();
   }

   @Override
   public void onTrimMemory(int var1) {
      Iterator var2 = this.onTrimMemoryListeners.iterator();

      while (var2.hasNext()) {
         TextureRegistry.OnTrimMemoryListener var3 = (TextureRegistry.OnTrimMemoryListener)((WeakReference)var2.next()).get();
         if (var3 != null) {
            var3.onTrimMemory(var1);
         } else {
            var2.remove();
         }
      }
   }

   @Override
   public TextureRegistry.SurfaceTextureEntry registerSurfaceTexture(SurfaceTexture var1) {
      return this.registerSurfaceTexture(this.nextTextureId.getAndIncrement(), var1);
   }

   public void removeIsDisplayingFlutterUiListener(FlutterUiDisplayListener var1) {
      this.flutterJNI.removeIsDisplayingFlutterUiListener(var1);
   }

   void removeOnTrimMemoryListener(TextureRegistry.OnTrimMemoryListener var1) {
      for (WeakReference var3 : this.onTrimMemoryListeners) {
         if (var3.get() == var1) {
            this.onTrimMemoryListeners.remove(var3);
            break;
         }
      }
   }

   public void setAccessibilityFeatures(int var1) {
      this.flutterJNI.setAccessibilityFeatures(var1);
   }

   public void setSemanticsEnabled(boolean var1) {
      this.flutterJNI.setSemanticsEnabled(var1);
   }

   public void setViewportMetrics(FlutterRenderer.ViewportMetrics var1) {
      if (var1.validate()) {
         StringBuilder var5 = new StringBuilder("Setting viewport metrics\nSize: ");
         var5.append(var1.width);
         var5.append(" x ");
         var5.append(var1.height);
         var5.append("\nPadding - L: ");
         var5.append(var1.viewPaddingLeft);
         var5.append(", T: ");
         var5.append(var1.viewPaddingTop);
         var5.append(", R: ");
         var5.append(var1.viewPaddingRight);
         var5.append(", B: ");
         var5.append(var1.viewPaddingBottom);
         var5.append("\nInsets - L: ");
         var5.append(var1.viewInsetLeft);
         var5.append(", T: ");
         var5.append(var1.viewInsetTop);
         var5.append(", R: ");
         var5.append(var1.viewInsetRight);
         var5.append(", B: ");
         var5.append(var1.viewInsetBottom);
         var5.append("\nSystem Gesture Insets - L: ");
         var5.append(var1.systemGestureInsetLeft);
         var5.append(", T: ");
         var5.append(var1.systemGestureInsetTop);
         var5.append(", R: ");
         var5.append(var1.systemGestureInsetRight);
         var5.append(", B: ");
         var5.append(var1.systemGestureInsetRight);
         var5.append("\nDisplay Features: ");
         var5.append(var1.displayFeatures.size());
         var5.append("\nDisplay Cutouts: ");
         var5.append(var1.displayCutouts.size());
         Log.v("FlutterRenderer", var5.toString());
         int var2 = var1.displayFeatures.size() + var1.displayCutouts.size();
         int[] var11 = new int[var2 * 4];
         int[] var7 = new int[var2];
         int[] var6 = new int[var2];
         byte var3 = 0;

         for (int var9 = 0; var9 < var1.displayFeatures.size(); var9++) {
            FlutterRenderer.DisplayFeature var8 = var1.displayFeatures.get(var9);
            this.translateFeatureBounds(var11, var9 * 4, var8.bounds);
            var7[var9] = var8.type.encodedValue;
            var6[var9] = var8.state.encodedValue;
         }

         int var4 = var1.displayFeatures.size();

         for (int var10 = var3; var10 < var1.displayCutouts.size(); var10++) {
            FlutterRenderer.DisplayFeature var12 = var1.displayCutouts.get(var10);
            this.translateFeatureBounds(var11, var10 * 4 + var4 * 4, var12.bounds);
            var7[var1.displayFeatures.size() + var10] = var12.type.encodedValue;
            var6[var1.displayFeatures.size() + var10] = var12.state.encodedValue;
         }

         this.flutterJNI
            .setViewportMetrics(
               var1.devicePixelRatio,
               var1.width,
               var1.height,
               var1.viewPaddingTop,
               var1.viewPaddingRight,
               var1.viewPaddingBottom,
               var1.viewPaddingLeft,
               var1.viewInsetTop,
               var1.viewInsetRight,
               var1.viewInsetBottom,
               var1.viewInsetLeft,
               var1.systemGestureInsetTop,
               var1.systemGestureInsetRight,
               var1.systemGestureInsetBottom,
               var1.systemGestureInsetLeft,
               var1.physicalTouchSlop,
               var11,
               var7,
               var6
            );
      }
   }

   public void startRenderingToSurface(Surface var1, boolean var2) {
      if (!var2) {
         this.stopRenderingToSurface();
      }

      this.surface = var1;
      if (var2) {
         this.flutterJNI.onSurfaceWindowChanged(var1);
      } else {
         this.flutterJNI.onSurfaceCreated(var1);
      }
   }

   public void stopRenderingToSurface() {
      if (this.surface != null) {
         this.flutterJNI.onSurfaceDestroyed();
         if (this.isDisplayingFlutterUi) {
            this.flutterUiDisplayListener.onFlutterUiNoLongerDisplayed();
         }

         this.isDisplayingFlutterUi = false;
         this.surface = null;
      }
   }

   public void surfaceChanged(int var1, int var2) {
      this.flutterJNI.onSurfaceChanged(var1, var2);
   }

   public void swapSurface(Surface var1) {
      this.surface = var1;
      this.flutterJNI.onSurfaceWindowChanged(var1);
   }

   public static final class DisplayFeature {
      public final Rect bounds;
      public final FlutterRenderer.DisplayFeatureState state;
      public final FlutterRenderer.DisplayFeatureType type;

      public DisplayFeature(Rect var1, FlutterRenderer.DisplayFeatureType var2, FlutterRenderer.DisplayFeatureState var3) {
         this.bounds = var1;
         this.type = var2;
         this.state = var3;
      }
   }

   public static enum DisplayFeatureState {
      POSTURE_FLAT(1),
      POSTURE_HALF_OPENED(2),
      UNKNOWN(0);

      private static final FlutterRenderer.DisplayFeatureState[] $VALUES = $values();
      public final int encodedValue;

      private DisplayFeatureState(int var3) {
         this.encodedValue = var3;
      }
   }

   public static enum DisplayFeatureType {
      CUTOUT(3),
      FOLD(1),
      HINGE(2),
      UNKNOWN(0);

      private static final FlutterRenderer.DisplayFeatureType[] $VALUES = $values();
      public final int encodedValue;

      private DisplayFeatureType(int var3) {
         this.encodedValue = var3;
      }
   }

   final class ImageReaderSurfaceProducer implements TextureRegistry.SurfaceProducer, TextureRegistry.ImageConsumer, TextureRegistry.OnTrimMemoryListener {
      private static final boolean CLEANUP_ON_MEMORY_PRESSURE = true;
      private static final int MAX_IMAGES = 5;
      private static final String TAG = "ImageReaderSurfaceProducer";
      private static final boolean VERBOSE_LOGS = false;
      private static final boolean trimOnMemoryPressure = true;
      private TextureRegistry.SurfaceProducer.Callback callback;
      private boolean createNewReader;
      private final long id;
      private boolean ignoringFence;
      private final ArrayDeque<FlutterRenderer.ImageReaderSurfaceProducer.PerImageReader> imageReaderQueue;
      private long lastDequeueTime;
      private FlutterRenderer.ImageReaderSurfaceProducer.PerImage lastDequeuedImage;
      private long lastQueueTime;
      private FlutterRenderer.ImageReaderSurfaceProducer.PerImageReader lastReaderDequeuedFrom;
      private long lastScheduleTime;
      private final Object lock;
      private boolean notifiedDestroy;
      private int numTrims;
      private final HashMap<ImageReader, FlutterRenderer.ImageReaderSurfaceProducer.PerImageReader> perImageReaders;
      private boolean released;
      private int requestedHeight;
      private int requestedWidth;
      final FlutterRenderer this$0;

      ImageReaderSurfaceProducer(FlutterRenderer var1, long var2) {
         this.this$0 = var1;
         this.ignoringFence = false;
         this.requestedWidth = 1;
         this.requestedHeight = 1;
         this.createNewReader = true;
         this.notifiedDestroy = false;
         this.lastDequeueTime = 0L;
         this.lastQueueTime = 0L;
         this.lastScheduleTime = 0L;
         this.numTrims = 0;
         this.lock = new Object();
         this.imageReaderQueue = new ArrayDeque<>();
         this.perImageReaders = new HashMap<>();
         this.lastDequeuedImage = null;
         this.lastReaderDequeuedFrom = null;
         this.callback = null;
         this.id = var2;
      }

      private void cleanup() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lock Ljava/lang/Object;
         // 04: astore 1
         // 05: aload 1
         // 06: monitorenter
         // 07: aload 0
         // 08: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.perImageReaders Ljava/util/HashMap;
         // 0b: invokevirtual java/util/HashMap.values ()Ljava/util/Collection;
         // 0e: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
         // 13: astore 2
         // 14: aload 2
         // 15: invokeinterface java/util/Iterator.hasNext ()Z 1
         // 1a: ifeq 3b
         // 1d: aload 2
         // 1e: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
         // 23: checkcast io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader
         // 26: astore 3
         // 27: aload 0
         // 28: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lastReaderDequeuedFrom Lio/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader;
         // 2b: aload 3
         // 2c: if_acmpne 34
         // 2f: aload 0
         // 30: aconst_null
         // 31: putfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lastReaderDequeuedFrom Lio/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader;
         // 34: aload 3
         // 35: invokevirtual io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader.close ()V
         // 38: goto 14
         // 3b: aload 0
         // 3c: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.perImageReaders Ljava/util/HashMap;
         // 3f: invokevirtual java/util/HashMap.clear ()V
         // 42: aload 0
         // 43: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lastDequeuedImage Lio/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImage;
         // 46: astore 2
         // 47: aload 2
         // 48: ifnull 57
         // 4b: aload 2
         // 4c: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImage.image Landroid/media/Image;
         // 4f: invokevirtual android/media/Image.close ()V
         // 52: aload 0
         // 53: aconst_null
         // 54: putfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lastDequeuedImage Lio/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImage;
         // 57: aload 0
         // 58: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lastReaderDequeuedFrom Lio/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader;
         // 5b: astore 2
         // 5c: aload 2
         // 5d: ifnull 69
         // 60: aload 2
         // 61: invokevirtual io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader.close ()V
         // 64: aload 0
         // 65: aconst_null
         // 66: putfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lastReaderDequeuedFrom Lio/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader;
         // 69: aload 0
         // 6a: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.imageReaderQueue Ljava/util/ArrayDeque;
         // 6d: invokevirtual java/util/ArrayDeque.clear ()V
         // 70: aload 1
         // 71: monitorexit
         // 72: return
         // 73: astore 2
         // 74: aload 1
         // 75: monitorexit
         // 76: aload 2
         // 77: athrow
      }

      private ImageReader createImageReader() {
         if (VERSION.SDK_INT >= 33) {
            return this.createImageReader33();
         } else if (VERSION.SDK_INT >= 29) {
            return this.createImageReader29();
         } else {
            throw new UnsupportedOperationException("ImageReaderPlatformViewRenderTarget requires API version 29+");
         }
      }

      private ImageReader createImageReader29() {
         return AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.requestedWidth, this.requestedHeight, 34, 5, 256L);
      }

      private ImageReader createImageReader33() {
         AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m();
         Builder var1 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.requestedWidth, this.requestedHeight);
         AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1, 5);
         AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$1(var1, 34);
         AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1, 256L);
         return AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1);
      }

      private FlutterRenderer.ImageReaderSurfaceProducer.PerImageReader getActiveReader() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lock Ljava/lang/Object;
         // 04: astore 1
         // 05: aload 1
         // 06: monitorenter
         // 07: aload 0
         // 08: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.createNewReader Z
         // 0b: ifeq 20
         // 0e: aload 0
         // 0f: bipush 0
         // 10: putfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.createNewReader Z
         // 13: aload 0
         // 14: aload 0
         // 15: invokespecial io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.createImageReader ()Landroid/media/ImageReader;
         // 18: invokevirtual io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.getOrCreatePerImageReader (Landroid/media/ImageReader;)Lio/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader;
         // 1b: astore 2
         // 1c: aload 1
         // 1d: monitorexit
         // 1e: aload 2
         // 1f: areturn
         // 20: aload 0
         // 21: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.imageReaderQueue Ljava/util/ArrayDeque;
         // 24: invokevirtual java/util/ArrayDeque.peekLast ()Ljava/lang/Object;
         // 27: checkcast io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader
         // 2a: astore 2
         // 2b: aload 1
         // 2c: monitorexit
         // 2d: aload 2
         // 2e: areturn
         // 2f: astore 2
         // 30: aload 1
         // 31: monitorexit
         // 32: aload 2
         // 33: athrow
      }

      private void maybeWaitOnFence(Image var1) {
         if (var1 != null) {
            if (!this.ignoringFence) {
               if (VERSION.SDK_INT >= 33) {
                  this.waitOnFence(var1);
               } else {
                  this.ignoringFence = true;
                  Log.d("ImageReaderSurfaceProducer", "ImageTextureEntry can't wait on the fence on Android < 33");
               }
            }
         }
      }

      private void releaseInternal() {
         this.cleanup();
         this.released = true;
         this.this$0.removeOnTrimMemoryListener(this);
         this.this$0.imageReaderProducers.remove(this);
      }

      private void waitOnFence(Image var1) {
         try {
            AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1));
         } catch (IOException var2) {
         }
      }

      @Override
      public Image acquireLatestImage() {
         FlutterRenderer.ImageReaderSurfaceProducer.PerImage var1 = this.dequeueImage();
         if (var1 == null) {
            return null;
         } else {
            this.maybeWaitOnFence(var1.image);
            return var1.image;
         }
      }

      double deltaMillis(long var1) {
         return var1 / 1000000.0;
      }

      FlutterRenderer.ImageReaderSurfaceProducer.PerImage dequeueImage() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lock Ljava/lang/Object;
         // 04: astore 2
         // 05: aload 2
         // 06: monitorenter
         // 07: aload 0
         // 08: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.imageReaderQueue Ljava/util/ArrayDeque;
         // 0b: invokevirtual java/util/ArrayDeque.iterator ()Ljava/util/Iterator;
         // 0e: astore 3
         // 0f: aconst_null
         // 10: astore 1
         // 11: aload 3
         // 12: invokeinterface java/util/Iterator.hasNext ()Z 1
         // 17: ifeq 4d
         // 1a: aload 3
         // 1b: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
         // 20: checkcast io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader
         // 23: astore 4
         // 25: aload 4
         // 27: invokevirtual io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader.dequeueImage ()Lio/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImage;
         // 2a: astore 1
         // 2b: aload 1
         // 2c: ifnonnull 32
         // 2f: goto 11
         // 32: aload 0
         // 33: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lastDequeuedImage Lio/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImage;
         // 36: astore 3
         // 37: aload 3
         // 38: ifnull 42
         // 3b: aload 3
         // 3c: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImage.image Landroid/media/Image;
         // 3f: invokevirtual android/media/Image.close ()V
         // 42: aload 0
         // 43: aload 1
         // 44: putfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lastDequeuedImage Lio/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImage;
         // 47: aload 0
         // 48: aload 4
         // 4a: putfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lastReaderDequeuedFrom Lio/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader;
         // 4d: aload 0
         // 4e: invokevirtual io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.pruneImageReaderQueue ()V
         // 51: aload 2
         // 52: monitorexit
         // 53: aload 1
         // 54: areturn
         // 55: astore 1
         // 56: aload 2
         // 57: monitorexit
         // 58: aload 1
         // 59: athrow
      }

      public void disableFenceForTest() {
         this.ignoringFence = true;
      }

      @Override
      protected void finalize() throws Throwable {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.released Z
         // 04: istore 1
         // 05: iload 1
         // 06: ifeq 0e
         // 09: aload 0
         // 0a: invokespecial java/lang/Object.finalize ()V
         // 0d: return
         // 0e: aload 0
         // 0f: invokespecial io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.releaseInternal ()V
         // 12: aload 0
         // 13: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.this$0 Lio/flutter/embedding/engine/renderer/FlutterRenderer;
         // 16: invokestatic io/flutter/embedding/engine/renderer/FlutterRenderer.access$600 (Lio/flutter/embedding/engine/renderer/FlutterRenderer;)Landroid/os/Handler;
         // 19: astore 3
         // 1a: new io/flutter/embedding/engine/renderer/FlutterRenderer$TextureFinalizerRunnable
         // 1d: astore 2
         // 1e: aload 2
         // 1f: aload 0
         // 20: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.id J
         // 23: aload 0
         // 24: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.this$0 Lio/flutter/embedding/engine/renderer/FlutterRenderer;
         // 27: invokestatic io/flutter/embedding/engine/renderer/FlutterRenderer.access$500 (Lio/flutter/embedding/engine/renderer/FlutterRenderer;)Lio/flutter/embedding/engine/FlutterJNI;
         // 2a: invokespecial io/flutter/embedding/engine/renderer/FlutterRenderer$TextureFinalizerRunnable.<init> (JLio/flutter/embedding/engine/FlutterJNI;)V
         // 2d: aload 3
         // 2e: aload 2
         // 2f: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
         // 32: pop
         // 33: aload 0
         // 34: invokespecial java/lang/Object.finalize ()V
         // 37: return
         // 38: astore 2
         // 39: aload 0
         // 3a: invokespecial java/lang/Object.finalize ()V
         // 3d: aload 2
         // 3e: athrow
      }

      @Override
      public int getHeight() {
         return this.requestedHeight;
      }

      FlutterRenderer.ImageReaderSurfaceProducer.PerImageReader getOrCreatePerImageReader(ImageReader var1) {
         FlutterRenderer.ImageReaderSurfaceProducer.PerImageReader var3 = this.perImageReaders.get(var1);
         FlutterRenderer.ImageReaderSurfaceProducer.PerImageReader var2 = var3;
         if (var3 == null) {
            var2 = new FlutterRenderer.ImageReaderSurfaceProducer.PerImageReader(this, var1);
            this.perImageReaders.put(var1, var2);
            this.imageReaderQueue.add(var2);
         }

         return var2;
      }

      @Override
      public Surface getSurface() {
         return this.getActiveReader().reader.getSurface();
      }

      @Override
      public int getWidth() {
         return this.requestedWidth;
      }

      @Override
      public boolean handlesCropAndRotation() {
         return false;
      }

      @Override
      public long id() {
         return this.id;
      }

      public int numImageReaders() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lock Ljava/lang/Object;
         // 04: astore 3
         // 05: aload 3
         // 06: monitorenter
         // 07: aload 0
         // 08: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.imageReaderQueue Ljava/util/ArrayDeque;
         // 0b: invokevirtual java/util/ArrayDeque.size ()I
         // 0e: istore 1
         // 0f: aload 3
         // 10: monitorexit
         // 11: iload 1
         // 12: ireturn
         // 13: astore 2
         // 14: aload 3
         // 15: monitorexit
         // 16: aload 2
         // 17: athrow
      }

      public int numImages() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lock Ljava/lang/Object;
         // 04: astore 2
         // 05: aload 2
         // 06: monitorenter
         // 07: aload 0
         // 08: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.imageReaderQueue Ljava/util/ArrayDeque;
         // 0b: invokevirtual java/util/ArrayDeque.iterator ()Ljava/util/Iterator;
         // 0e: astore 3
         // 0f: bipush 0
         // 10: istore 1
         // 11: aload 3
         // 12: invokeinterface java/util/Iterator.hasNext ()Z 1
         // 17: ifeq 2f
         // 1a: iload 1
         // 1b: aload 3
         // 1c: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
         // 21: checkcast io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader
         // 24: invokestatic io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader.access$1000 (Lio/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader;)Ljava/util/ArrayDeque;
         // 27: invokevirtual java/util/ArrayDeque.size ()I
         // 2a: iadd
         // 2b: istore 1
         // 2c: goto 11
         // 2f: aload 2
         // 30: monitorexit
         // 31: iload 1
         // 32: ireturn
         // 33: astore 3
         // 34: aload 2
         // 35: monitorexit
         // 36: aload 3
         // 37: athrow
      }

      public int numTrims() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lock Ljava/lang/Object;
         // 04: astore 3
         // 05: aload 3
         // 06: monitorenter
         // 07: aload 0
         // 08: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.numTrims I
         // 0b: istore 1
         // 0c: aload 3
         // 0d: monitorexit
         // 0e: iload 1
         // 0f: ireturn
         // 10: astore 2
         // 11: aload 3
         // 12: monitorexit
         // 13: aload 2
         // 14: athrow
      }

      void onImage(ImageReader param1, Image param2) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lock Ljava/lang/Object;
         // 04: astore 3
         // 05: aload 3
         // 06: monitorenter
         // 07: aload 0
         // 08: aload 1
         // 09: invokevirtual io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.getOrCreatePerImageReader (Landroid/media/ImageReader;)Lio/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader;
         // 0c: aload 2
         // 0d: invokevirtual io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader.queueImage (Landroid/media/Image;)Lio/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer$PerImage;
         // 10: astore 1
         // 11: aload 3
         // 12: monitorexit
         // 13: aload 1
         // 14: ifnonnull 18
         // 17: return
         // 18: aload 0
         // 19: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.this$0 Lio/flutter/embedding/engine/renderer/FlutterRenderer;
         // 1c: invokestatic io/flutter/embedding/engine/renderer/FlutterRenderer.access$700 (Lio/flutter/embedding/engine/renderer/FlutterRenderer;)V
         // 1f: return
         // 20: astore 1
         // 21: aload 3
         // 22: monitorexit
         // 23: aload 1
         // 24: athrow
      }

      @Override
      public void onTrimMemory(int param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: iload 1
         // 01: bipush 40
         // 03: if_icmpge 07
         // 06: return
         // 07: aload 0
         // 08: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.lock Ljava/lang/Object;
         // 0b: astore 2
         // 0c: aload 2
         // 0d: monitorenter
         // 0e: aload 0
         // 0f: aload 0
         // 10: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.numTrims I
         // 13: bipush 1
         // 14: iadd
         // 15: putfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.numTrims I
         // 18: aload 2
         // 19: monitorexit
         // 1a: aload 0
         // 1b: invokespecial io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.cleanup ()V
         // 1e: aload 0
         // 1f: bipush 1
         // 20: putfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.createNewReader Z
         // 23: aload 0
         // 24: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.callback Lio/flutter/view/TextureRegistry$SurfaceProducer$Callback;
         // 27: astore 2
         // 28: aload 2
         // 29: ifnull 37
         // 2c: aload 0
         // 2d: bipush 1
         // 2e: putfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageReaderSurfaceProducer.notifiedDestroy Z
         // 31: aload 2
         // 32: invokeinterface io/flutter/view/TextureRegistry$SurfaceProducer$Callback.onSurfaceDestroyed ()V 1
         // 37: return
         // 38: astore 3
         // 39: aload 2
         // 3a: monitorexit
         // 3b: aload 3
         // 3c: athrow
      }

      void pruneImageReaderQueue() {
         while (this.imageReaderQueue.size() > 1) {
            FlutterRenderer.ImageReaderSurfaceProducer.PerImageReader var1 = this.imageReaderQueue.peekFirst();
            if (var1 != null && var1.canPrune()) {
               this.imageReaderQueue.removeFirst();
               this.perImageReaders.remove(var1.reader);
               var1.close();
               continue;
            }
            break;
         }
      }

      @Override
      public void release() {
         if (!this.released) {
            this.releaseInternal();
            this.this$0.unregisterTexture(this.id);
         }
      }

      @Override
      public void scheduleFrame() {
         this.this$0.scheduleEngineFrame();
      }

      @Override
      public void setCallback(TextureRegistry.SurfaceProducer.Callback var1) {
         this.callback = var1;
      }

      @Override
      public void setSize(int var1, int var2) {
         var1 = Math.max(1, var1);
         var2 = Math.max(1, var2);
         if (this.requestedWidth != var1 || this.requestedHeight != var2) {
            this.createNewReader = true;
            this.requestedHeight = var2;
            this.requestedWidth = var1;
         }
      }

      private class PerImage {
         public final Image image;
         public final long queuedTime;
         final FlutterRenderer.ImageReaderSurfaceProducer this$1;

         public PerImage(FlutterRenderer.ImageReaderSurfaceProducer var1, Image var2, long var3) {
            this.this$1 = var1;
            this.image = var2;
            this.queuedTime = var3;
         }
      }

      private class PerImageReader {
         private boolean closed;
         private final ArrayDeque<FlutterRenderer.ImageReaderSurfaceProducer.PerImage> imageQueue;
         public final ImageReader reader;
         final FlutterRenderer.ImageReaderSurfaceProducer this$1;

         public PerImageReader(FlutterRenderer.ImageReaderSurfaceProducer var1, ImageReader var2) {
            this.this$1 = var1;
            this.imageQueue = new ArrayDeque<>();
            this.closed = false;
            this.reader = var2;
            var2.setOnImageAvailableListener(
               new FlutterRenderer$ImageReaderSurfaceProducer$PerImageReader$$ExternalSyntheticLambda0(this), new Handler(Looper.getMainLooper())
            );
         }

         boolean canPrune() {
            boolean var1;
            if (this.imageQueue.isEmpty() && this.this$1.lastReaderDequeuedFrom != this) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         void close() {
            this.closed = true;
            this.reader.close();
            this.imageQueue.clear();
         }

         FlutterRenderer.ImageReaderSurfaceProducer.PerImage dequeueImage() {
            return this.imageQueue.isEmpty() ? null : this.imageQueue.removeFirst();
         }

         FlutterRenderer.ImageReaderSurfaceProducer.PerImage queueImage(Image var1) {
            if (this.closed) {
               return null;
            } else {
               FlutterRenderer.ImageReaderSurfaceProducer.PerImage var2 = this.this$1.new PerImage(this.this$1, var1, System.nanoTime());
               this.imageQueue.add(var2);

               while (this.imageQueue.size() > 2) {
                  this.imageQueue.removeFirst().image.close();
               }

               return var2;
            }
         }
      }
   }

   final class ImageTextureRegistryEntry implements TextureRegistry.ImageTextureEntry, TextureRegistry.ImageConsumer {
      private static final String TAG = "ImageTextureRegistryEntry";
      private final long id;
      private boolean ignoringFence;
      private Image image;
      private boolean released;
      final FlutterRenderer this$0;

      ImageTextureRegistryEntry(FlutterRenderer var1, long var2) {
         this.this$0 = var1;
         this.ignoringFence = false;
         this.id = var2;
      }

      private void maybeWaitOnFence(Image var1) {
         if (var1 != null) {
            if (!this.ignoringFence) {
               if (VERSION.SDK_INT >= 33) {
                  this.waitOnFence(var1);
               } else {
                  this.ignoringFence = true;
                  Log.d("ImageTextureRegistryEntry", "ImageTextureEntry can't wait on the fence on Android < 33");
               }
            }
         }
      }

      private void waitOnFence(Image var1) {
         try {
            AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1));
         } catch (IOException var2) {
         }
      }

      @Override
      public Image acquireLatestImage() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageTextureRegistryEntry.image Landroid/media/Image;
         // 06: astore 1
         // 07: aload 0
         // 08: aconst_null
         // 09: putfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageTextureRegistryEntry.image Landroid/media/Image;
         // 0c: aload 0
         // 0d: monitorexit
         // 0e: aload 0
         // 0f: aload 1
         // 10: invokespecial io/flutter/embedding/engine/renderer/FlutterRenderer$ImageTextureRegistryEntry.maybeWaitOnFence (Landroid/media/Image;)V
         // 13: aload 1
         // 14: areturn
         // 15: astore 1
         // 16: aload 0
         // 17: monitorexit
         // 18: aload 1
         // 19: athrow
      }

      @Override
      protected void finalize() throws Throwable {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageTextureRegistryEntry.released Z
         // 04: istore 1
         // 05: iload 1
         // 06: ifeq 0e
         // 09: aload 0
         // 0a: invokespecial java/lang/Object.finalize ()V
         // 0d: return
         // 0e: aload 0
         // 0f: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageTextureRegistryEntry.image Landroid/media/Image;
         // 12: astore 2
         // 13: aload 2
         // 14: ifnull 20
         // 17: aload 2
         // 18: invokevirtual android/media/Image.close ()V
         // 1b: aload 0
         // 1c: aconst_null
         // 1d: putfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageTextureRegistryEntry.image Landroid/media/Image;
         // 20: aload 0
         // 21: bipush 1
         // 22: putfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageTextureRegistryEntry.released Z
         // 25: aload 0
         // 26: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageTextureRegistryEntry.this$0 Lio/flutter/embedding/engine/renderer/FlutterRenderer;
         // 29: invokestatic io/flutter/embedding/engine/renderer/FlutterRenderer.access$600 (Lio/flutter/embedding/engine/renderer/FlutterRenderer;)Landroid/os/Handler;
         // 2c: astore 3
         // 2d: new io/flutter/embedding/engine/renderer/FlutterRenderer$TextureFinalizerRunnable
         // 30: astore 2
         // 31: aload 2
         // 32: aload 0
         // 33: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageTextureRegistryEntry.id J
         // 36: aload 0
         // 37: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageTextureRegistryEntry.this$0 Lio/flutter/embedding/engine/renderer/FlutterRenderer;
         // 3a: invokestatic io/flutter/embedding/engine/renderer/FlutterRenderer.access$500 (Lio/flutter/embedding/engine/renderer/FlutterRenderer;)Lio/flutter/embedding/engine/FlutterJNI;
         // 3d: invokespecial io/flutter/embedding/engine/renderer/FlutterRenderer$TextureFinalizerRunnable.<init> (JLio/flutter/embedding/engine/FlutterJNI;)V
         // 40: aload 3
         // 41: aload 2
         // 42: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
         // 45: pop
         // 46: aload 0
         // 47: invokespecial java/lang/Object.finalize ()V
         // 4a: return
         // 4b: astore 2
         // 4c: aload 0
         // 4d: invokespecial java/lang/Object.finalize ()V
         // 50: aload 2
         // 51: athrow
      }

      @Override
      public long id() {
         return this.id;
      }

      @Override
      public void pushImage(Image param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageTextureRegistryEntry.released Z
         // 04: ifeq 08
         // 07: return
         // 08: aload 0
         // 09: monitorenter
         // 0a: aload 0
         // 0b: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageTextureRegistryEntry.image Landroid/media/Image;
         // 0e: astore 2
         // 0f: aload 0
         // 10: aload 1
         // 11: putfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageTextureRegistryEntry.image Landroid/media/Image;
         // 14: aload 0
         // 15: monitorexit
         // 16: aload 2
         // 17: ifnull 25
         // 1a: ldc "ImageTextureRegistryEntry"
         // 1c: ldc "Dropping PlatformView Frame"
         // 1e: invokestatic io/flutter/Log.e (Ljava/lang/String;Ljava/lang/String;)V
         // 21: aload 2
         // 22: invokevirtual android/media/Image.close ()V
         // 25: aload 1
         // 26: ifnull 30
         // 29: aload 0
         // 2a: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$ImageTextureRegistryEntry.this$0 Lio/flutter/embedding/engine/renderer/FlutterRenderer;
         // 2d: invokestatic io/flutter/embedding/engine/renderer/FlutterRenderer.access$700 (Lio/flutter/embedding/engine/renderer/FlutterRenderer;)V
         // 30: return
         // 31: astore 1
         // 32: aload 0
         // 33: monitorexit
         // 34: aload 1
         // 35: athrow
      }

      @Override
      public void release() {
         if (!this.released) {
            this.released = true;
            Image var1 = this.image;
            if (var1 != null) {
               var1.close();
               this.image = null;
            }

            this.this$0.unregisterTexture(this.id);
         }
      }
   }

   final class SurfaceTextureRegistryEntry implements TextureRegistry.SurfaceTextureEntry, TextureRegistry.OnTrimMemoryListener {
      private TextureRegistry.OnFrameConsumedListener frameConsumedListener;
      private final long id;
      private boolean released;
      private final SurfaceTextureWrapper textureWrapper;
      final FlutterRenderer this$0;
      private TextureRegistry.OnTrimMemoryListener trimMemoryListener;

      SurfaceTextureRegistryEntry(FlutterRenderer var1, long var2, SurfaceTexture var4) {
         this.this$0 = var1;
         this.id = var2;
         this.textureWrapper = new SurfaceTextureWrapper(var4, new FlutterRenderer$SurfaceTextureRegistryEntry$$ExternalSyntheticLambda0(this));
         FlutterRenderer$SurfaceTextureRegistryEntry$$ExternalSyntheticLambda1 var5 = new FlutterRenderer$SurfaceTextureRegistryEntry$$ExternalSyntheticLambda1(
            this
         );
         this.surfaceTexture().setOnFrameAvailableListener(var5, new Handler());
      }

      private void removeListener() {
         this.this$0.removeOnTrimMemoryListener(this);
      }

      @Override
      protected void finalize() throws Throwable {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$SurfaceTextureRegistryEntry.released Z
         // 04: istore 1
         // 05: iload 1
         // 06: ifeq 0e
         // 09: aload 0
         // 0a: invokespecial java/lang/Object.finalize ()V
         // 0d: return
         // 0e: aload 0
         // 0f: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$SurfaceTextureRegistryEntry.this$0 Lio/flutter/embedding/engine/renderer/FlutterRenderer;
         // 12: invokestatic io/flutter/embedding/engine/renderer/FlutterRenderer.access$600 (Lio/flutter/embedding/engine/renderer/FlutterRenderer;)Landroid/os/Handler;
         // 15: astore 3
         // 16: new io/flutter/embedding/engine/renderer/FlutterRenderer$TextureFinalizerRunnable
         // 19: astore 2
         // 1a: aload 2
         // 1b: aload 0
         // 1c: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$SurfaceTextureRegistryEntry.id J
         // 1f: aload 0
         // 20: getfield io/flutter/embedding/engine/renderer/FlutterRenderer$SurfaceTextureRegistryEntry.this$0 Lio/flutter/embedding/engine/renderer/FlutterRenderer;
         // 23: invokestatic io/flutter/embedding/engine/renderer/FlutterRenderer.access$500 (Lio/flutter/embedding/engine/renderer/FlutterRenderer;)Lio/flutter/embedding/engine/FlutterJNI;
         // 26: invokespecial io/flutter/embedding/engine/renderer/FlutterRenderer$TextureFinalizerRunnable.<init> (JLio/flutter/embedding/engine/FlutterJNI;)V
         // 29: aload 3
         // 2a: aload 2
         // 2b: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
         // 2e: pop
         // 2f: aload 0
         // 30: invokespecial java/lang/Object.finalize ()V
         // 33: return
         // 34: astore 2
         // 35: aload 0
         // 36: invokespecial java/lang/Object.finalize ()V
         // 39: aload 2
         // 3a: athrow
      }

      @Override
      public long id() {
         return this.id;
      }

      @Override
      public void onTrimMemory(int var1) {
         TextureRegistry.OnTrimMemoryListener var2 = this.trimMemoryListener;
         if (var2 != null) {
            var2.onTrimMemory(var1);
         }
      }

      @Override
      public void release() {
         if (!this.released) {
            StringBuilder var1 = new StringBuilder("Releasing a SurfaceTexture (");
            var1.append(this.id);
            var1.append(").");
            Log.v("FlutterRenderer", var1.toString());
            this.textureWrapper.release();
            this.this$0.unregisterTexture(this.id);
            this.removeListener();
            this.released = true;
         }
      }

      @Override
      public void setOnFrameConsumedListener(TextureRegistry.OnFrameConsumedListener var1) {
         this.frameConsumedListener = var1;
      }

      @Override
      public void setOnTrimMemoryListener(TextureRegistry.OnTrimMemoryListener var1) {
         this.trimMemoryListener = var1;
      }

      @Override
      public SurfaceTexture surfaceTexture() {
         return this.textureWrapper.surfaceTexture();
      }

      public SurfaceTextureWrapper textureWrapper() {
         return this.textureWrapper;
      }
   }

   static final class TextureFinalizerRunnable implements Runnable {
      private final FlutterJNI flutterJNI;
      private final long id;

      TextureFinalizerRunnable(long var1, FlutterJNI var3) {
         this.id = var1;
         this.flutterJNI = var3;
      }

      @Override
      public void run() {
         if (this.flutterJNI.isAttached()) {
            StringBuilder var1 = new StringBuilder("Releasing a Texture (");
            var1.append(this.id);
            var1.append(").");
            Log.v("FlutterRenderer", var1.toString());
            this.flutterJNI.unregisterTexture(this.id);
         }
      }
   }

   public static final class ViewportMetrics {
      public static final int unsetValue = -1;
      public float devicePixelRatio = 1.0F;
      private final List<FlutterRenderer.DisplayFeature> displayCutouts;
      private final List<FlutterRenderer.DisplayFeature> displayFeatures;
      public int height;
      public int physicalTouchSlop;
      public int systemGestureInsetBottom;
      public int systemGestureInsetLeft;
      public int systemGestureInsetRight;
      public int systemGestureInsetTop;
      public int viewInsetBottom;
      public int viewInsetLeft;
      public int viewInsetRight;
      public int viewInsetTop;
      public int viewPaddingBottom;
      public int viewPaddingLeft;
      public int viewPaddingRight;
      public int viewPaddingTop;
      public int width = 0;

      public ViewportMetrics() {
         this.height = 0;
         this.viewPaddingTop = 0;
         this.viewPaddingRight = 0;
         this.viewPaddingBottom = 0;
         this.viewPaddingLeft = 0;
         this.viewInsetTop = 0;
         this.viewInsetRight = 0;
         this.viewInsetBottom = 0;
         this.viewInsetLeft = 0;
         this.systemGestureInsetTop = 0;
         this.systemGestureInsetRight = 0;
         this.systemGestureInsetBottom = 0;
         this.systemGestureInsetLeft = 0;
         this.physicalTouchSlop = -1;
         this.displayFeatures = new ArrayList<>();
         this.displayCutouts = new ArrayList<>();
      }

      public List<FlutterRenderer.DisplayFeature> getDisplayCutouts() {
         return this.displayCutouts;
      }

      public List<FlutterRenderer.DisplayFeature> getDisplayFeatures() {
         return this.displayFeatures;
      }

      public void setDisplayCutouts(List<FlutterRenderer.DisplayFeature> var1) {
         this.displayCutouts.clear();
         this.displayCutouts.addAll(var1);
      }

      public void setDisplayFeatures(List<FlutterRenderer.DisplayFeature> var1) {
         this.displayFeatures.clear();
         this.displayFeatures.addAll(var1);
      }

      boolean validate() {
         boolean var1;
         if (this.width > 0 && this.height > 0 && this.devicePixelRatio > 0.0F) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }
}
