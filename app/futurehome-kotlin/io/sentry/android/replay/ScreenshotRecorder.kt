package io.sentry.android.replay

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Bitmap.Config
import android.view.View
import android.view.Window
import android.view.ViewTreeObserver.OnDrawListener
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import io.sentry.android.replay.util.ExecutorsKt
import io.sentry.android.replay.util.MainLooperHandler
import io.sentry.android.replay.util.TextLayout
import io.sentry.android.replay.util.ViewsKt
import io.sentry.android.replay.viewhierarchy.ViewHierarchyNode
import java.lang.ref.WeakReference
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1

internal class ScreenshotRecorder(config: ScreenshotRecorderConfig,
      options: SentryOptions,
      mainLooperHandler: MainLooperHandler,
      recorder: ScheduledExecutorService,
      screenshotRecorderCallback: ScreenshotRecorderCallback?
   ) :
   OnDrawListener {
   public final val config: ScreenshotRecorderConfig
   private final val contentChanged: AtomicBoolean
   private final val isCapturing: AtomicBoolean
   private final val lastCaptureSuccessful: AtomicBoolean
   private final val mainLooperHandler: MainLooperHandler

   private final val maskingPaint: Paint
      private final get() {
         return this.maskingPaint$delegate.getValue() as Paint;
      }


   public final val options: SentryOptions

   private final val prescaledMatrix: Matrix
      private final get() {
         return this.prescaledMatrix$delegate.getValue() as Matrix;
      }


   private final val recorder: ScheduledExecutorService
   private final var rootView: WeakReference<View>?
   private final val screenshot: Bitmap
   private final val screenshotRecorderCallback: ScreenshotRecorderCallback?

   private final val singlePixelBitmap: Bitmap
      private final get() {
         return this.singlePixelBitmap$delegate.getValue() as Bitmap;
      }


   private final val singlePixelBitmapCanvas: Canvas
      private final get() {
         return this.singlePixelBitmapCanvas$delegate.getValue() as Canvas;
      }


   init {
      this.config = var1;
      this.options = var2;
      this.mainLooperHandler = var3;
      this.recorder = var4;
      this.screenshotRecorderCallback = var5;
      this.maskingPaint$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, <unrepresentable>.INSTANCE);
      this.singlePixelBitmap$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, <unrepresentable>.INSTANCE);
      val var6: Bitmap = Bitmap.createBitmap(var1.getRecordingWidth(), var1.getRecordingHeight(), Config.RGB_565);
      this.screenshot = var6;
      this.singlePixelBitmapCanvas$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, (new Function0<Canvas>(this) {
         final ScreenshotRecorder this$0;

         {
            super(0);
            this.this$0 = var1;
         }

         public final Canvas invoke() {
            return new Canvas(ScreenshotRecorder.access$getSinglePixelBitmap(this.this$0));
         }
      }) as Function0);
      this.prescaledMatrix$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, (new Function0<Matrix>(this) {
         final ScreenshotRecorder this$0;

         {
            super(0);
            this.this$0 = var1;
         }

         public final Matrix invoke() {
            val var2: Matrix = new Matrix();
            val var1: ScreenshotRecorder = this.this$0;
            var2.preScale(this.this$0.getConfig().getScaleFactorX(), var1.getConfig().getScaleFactorY());
            return var2;
         }
      }) as Function0);
      this.contentChanged = new AtomicBoolean(false);
      this.isCapturing = new AtomicBoolean(true);
      this.lastCaptureSuccessful = new AtomicBoolean(false);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @JvmStatic
   fun `capture$lambda$2`(var0: ScreenshotRecorder, var1: Window, var2: View) {
      try {
         var0.contentChanged.set(false);
         AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(
            var1, var0.screenshot, new ScreenshotRecorder$$ExternalSyntheticLambda1(var0, var2), var0.mainLooperHandler.getHandler()
         );
      } catch (var5: java.lang.Throwable) {
         var0.options.getLogger().log(SentryLevel.WARNING, "Failed to capture replay recording", var5);
         var0.lastCaptureSuccessful.set(false);
         return;
      }
   }

   @JvmStatic
   fun `capture$lambda$2$lambda$1`(var0: ScreenshotRecorder, var1: View, var2: Int) {
      if (var2 != 0) {
         var0.options.getLogger().log(SentryLevel.INFO, "Failed to capture replay recording: %d", var2);
         var0.lastCaptureSuccessful.set(false);
      } else if (var0.contentChanged.get()) {
         var0.options.getLogger().log(SentryLevel.INFO, "Failed to determine view hierarchy, not capturing");
         var0.lastCaptureSuccessful.set(false);
      } else {
         val var3: ViewHierarchyNode = ViewHierarchyNode.Companion.fromView(var1, null, 0, var0.options);
         ViewsKt.traverse(var1, var3, var0.options);
         ExecutorsKt.submitSafely(var0.recorder, var0.options, "screenshot_recorder.mask", new ScreenshotRecorder$$ExternalSyntheticLambda2(var0, var3));
      }
   }

   @JvmStatic
   fun `capture$lambda$2$lambda$1$lambda$0`(var0: ScreenshotRecorder, var1: ViewHierarchyNode) {
      val var2: Canvas = new Canvas(var0.screenshot);
      var2.setMatrix(var0.getPrescaledMatrix());
      var1.traverse(
         (
            new Function1<ViewHierarchyNode, java.lang.Boolean>(var0, var2) {
               final Canvas $canvas;
               final ScreenshotRecorder this$0;

               {
                  super(1);
                  this.this$0 = var1;
                  this.$canvas = var2;
               }

               public final java.lang.Boolean invoke(ViewHierarchyNode var1) {
                  if (var1.getShouldMask() && var1.getWidth() > 0 && var1.getHeight() > 0) {
                     if (var1.getVisibleRect() == null) {
                        return false;
                     }

                     val var6: Pair;
                     if (var1 is ViewHierarchyNode.ImageViewHierarchyNode) {
                        var6 = TuplesKt.to(
                           CollectionsKt.listOf(var1.getVisibleRect()),
                           ScreenshotRecorder.access$dominantColorForRect(
                              this.this$0, ScreenshotRecorder.access$getScreenshot$p(this.this$0), var1.getVisibleRect()
                           )
                        );
                     } else {
                        val var3: Boolean = var1 is ViewHierarchyNode.TextViewHierarchyNode;
                        var var2: Int = -16777216;
                        if (!var3) {
                           var6 = TuplesKt.to(CollectionsKt.listOf(var1.getVisibleRect()), -16777216);
                        } else {
                           var var5: ViewHierarchyNode.TextViewHierarchyNode;
                           label37: {
                              var var9: Int;
                              label36: {
                                 var5 = var1 as ViewHierarchyNode.TextViewHierarchyNode;
                                 val var4: TextLayout = (var1 as ViewHierarchyNode.TextViewHierarchyNode).getLayout();
                                 if (var4 != null) {
                                    var9 = var4.getDominantTextColor();
                                    if (var9 != null) {
                                       break label36;
                                    }
                                 }

                                 var9 = var5.getDominantColor();
                                 if (var9 == null) {
                                    break label37;
                                 }
                              }

                              var2 = var9;
                           }

                           var6 = TuplesKt.to(
                              ViewsKt.getVisibleRects(var5.getLayout(), var1.getVisibleRect(), var5.getPaddingLeft(), var5.getPaddingTop()), var2
                           );
                        }
                     }

                     val var11: java.util.List = var6.component1() as java.util.List;
                     ScreenshotRecorder.access$getMaskingPaint(this.this$0).setColor((var6.component2() as java.lang.Number).intValue());
                     val var14: java.lang.Iterable = var11;
                     val var7: Canvas = this.$canvas;
                     val var12: ScreenshotRecorder = this.this$0;
                     val var15: java.util.Iterator = var14.iterator();

                     while (var15.hasNext()) {
                        var7.drawRoundRect(new RectF(var15.next() as Rect), 10.0F, 10.0F, ScreenshotRecorder.access$getMaskingPaint(var12));
                     }
                  }

                  return true;
               }
            }
         ) as (ViewHierarchyNode?) -> java.lang.Boolean
      );
      if (var0.screenshotRecorderCallback != null) {
         var0.screenshotRecorderCallback.onScreenshotRecorded(var0.screenshot);
      }

      var0.lastCaptureSuccessful.set(true);
      var0.contentChanged.set(false);
   }

   private fun Bitmap.dominantColorForRect(rect: Rect): Int {
      val var3: Rect = new Rect(var2);
      val var4: RectF = new RectF(var3);
      this.getPrescaledMatrix().mapRect(var4);
      var4.round(var3);
      this.getSinglePixelBitmapCanvas().drawBitmap(var1, var3, new Rect(0, 0, 1, 1), null);
      return this.getSinglePixelBitmap().getPixel(0, 0);
   }

   public fun bind(root: View) {
      val var3: View;
      if (this.rootView != null) {
         var3 = this.rootView.get();
      } else {
         var3 = null;
      }

      this.unbind(var3);
      if (this.rootView != null) {
         this.rootView.clear();
      }

      this.rootView = new WeakReference<>(var1);
      ViewsKt.addOnDrawListenerSafe(var1, this);
      this.contentChanged.set(true);
   }

   public fun capture() {
      if (!this.isCapturing.get()) {
         this.options.getLogger().log(SentryLevel.DEBUG, "ScreenshotRecorder is paused, not capturing screenshot");
      } else if (!this.contentChanged.get() && this.lastCaptureSuccessful.get()) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Content hasn't changed, repeating last known frame");
         if (this.screenshotRecorderCallback != null) {
            this.screenshotRecorderCallback.onScreenshotRecorded(this.screenshot);
         }
      } else {
         val var3: View;
         if (this.rootView != null) {
            var3 = this.rootView.get();
         } else {
            var3 = null;
         }

         if (var3 != null && var3.getWidth() > 0 && var3.getHeight() > 0 && var3.isShown()) {
            val var2: Window = WindowsKt.getPhoneWindow(var3);
            if (var2 == null) {
               this.options.getLogger().log(SentryLevel.DEBUG, "Window is invalid, not capturing screenshot");
            } else {
               this.mainLooperHandler.post(new ScreenshotRecorder$$ExternalSyntheticLambda0(this, var2, var3));
            }
         } else {
            this.options.getLogger().log(SentryLevel.DEBUG, "Root view is invalid, not capturing screenshot");
         }
      }
   }

   public fun close() {
      val var2: View;
      if (this.rootView != null) {
         var2 = this.rootView.get();
      } else {
         var2 = null;
      }

      this.unbind(var2);
      if (this.rootView != null) {
         this.rootView.clear();
      }

      this.screenshot.recycle();
      this.isCapturing.set(false);
   }

   public open fun onDraw() {
      val var2: View;
      if (this.rootView != null) {
         var2 = this.rootView.get();
      } else {
         var2 = null;
      }

      if (var2 != null && var2.getWidth() > 0 && var2.getHeight() > 0 && var2.isShown()) {
         this.contentChanged.set(true);
      } else {
         this.options.getLogger().log(SentryLevel.DEBUG, "Root view is invalid, not capturing screenshot");
      }
   }

   public fun pause() {
      this.isCapturing.set(false);
      val var2: View;
      if (this.rootView != null) {
         var2 = this.rootView.get();
      } else {
         var2 = null;
      }

      this.unbind(var2);
   }

   public fun resume() {
      if (this.rootView != null) {
         val var2: View = this.rootView.get();
         if (var2 != null) {
            ViewsKt.addOnDrawListenerSafe(var2, this);
         }
      }

      this.isCapturing.set(true);
   }

   public fun unbind(root: View?) {
      if (var1 != null) {
         ViewsKt.removeOnDrawListenerSafe(var1, this);
      }
   }

   private class RecorderExecutorServiceThreadFactory : ThreadFactory {
      private final var cnt: Int

      public override fun newThread(r: Runnable): Thread {
         val var3: StringBuilder = new StringBuilder("SentryReplayRecorder-");
         var3.append(this.cnt++);
         val var4: Thread = new Thread(var1, var3.toString());
         var4.setDaemon(true);
         return var4;
      }
   }
}
