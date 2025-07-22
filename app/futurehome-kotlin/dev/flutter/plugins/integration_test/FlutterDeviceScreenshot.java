package dev.flutter.plugins.integration_test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Build.VERSION;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import com.baseflow.geocoding.Geocoding..ExternalSyntheticApiModelOutline0;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.FlutterFragment;
import io.flutter.embedding.android.FlutterFragmentActivity;
import io.flutter.embedding.android.FlutterView;
import io.flutter.plugin.common.MethodChannel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

class FlutterDeviceScreenshot {
   private static Handler backgroundHandler;
   private static boolean flutterSurfaceConvertedToImage;
   private static Handler mainHandler;

   static void captureView(Activity var0, MethodChannel var1, MethodChannel.Result var2) {
      FlutterView var3 = getFlutterView(var0);
      if (var3 == null) {
         var2.error("Could not copy the pixels", "FlutterView is null", null);
      } else if (!flutterSurfaceConvertedToImage) {
         var2.error("Could not copy the pixels", "Flutter surface must be converted to image first", null);
      } else {
         var1.invokeMethod("scheduleFrame", null);
         if (backgroundHandler == null) {
            HandlerThread var4 = new HandlerThread("screenshot");
            var4.start();
            backgroundHandler = new Handler(var4.getLooper());
         }

         if (mainHandler == null) {
            mainHandler = new Handler(Looper.getMainLooper());
         }

         takeScreenshot(backgroundHandler, mainHandler, var3, var2);
      }
   }

   static byte[] captureWithUiAutomation() throws IOException {
      return new byte[0];
   }

   static void convertFlutterSurfaceToImage(Activity var0) {
      FlutterView var1 = getFlutterView(var0);
      if (var1 != null && !flutterSurfaceConvertedToImage) {
         var1.convertToImageView();
         flutterSurfaceConvertedToImage = true;
      }
   }

   private static void convertViewToBitmap(FlutterView var0, MethodChannel.Result var1, Handler var2) {
      if (VERSION.SDK_INT < 26) {
         Bitmap var8 = Bitmap.createBitmap(var0.getWidth(), var0.getHeight(), Config.RGB_565);
         var0.draw(new Canvas(var8));
         ByteArrayOutputStream var7 = new ByteArrayOutputStream();
         var8.compress(CompressFormat.PNG, 100, var7);
         var1.success(var7.toByteArray());
      } else {
         Bitmap var5 = Bitmap.createBitmap(var0.getWidth(), var0.getHeight(), Config.ARGB_8888);
         int[] var6 = new int[2];
         var0.getLocationInWindow(var6);
         int var3 = var6[0];
         int var4 = var6[1];
         Rect var9 = new Rect(var3, var4, var0.getWidth() + var3, var0.getHeight() + var4);
         ExternalSyntheticApiModelOutline0.m(
            ((Activity)var0.getContext()).getWindow(), var9, var5, new FlutterDeviceScreenshot$$ExternalSyntheticLambda1(var5, var1), var2
         );
      }
   }

   public static FlutterView getFlutterView(Activity var0) {
      if (var0 instanceof FlutterActivity) {
         return (FlutterView)var0.findViewById(FlutterActivity.FLUTTER_VIEW_ID);
      } else {
         return var0 instanceof FlutterFragmentActivity ? (FlutterView)var0.findViewById(FlutterFragment.FLUTTER_VIEW_ID) : null;
      }
   }

   static boolean hasInstrumentation() {
      return false;
   }

   static void revertFlutterImage(Activity var0) {
      FlutterView var1 = getFlutterView(var0);
      if (var1 != null && flutterSurfaceConvertedToImage) {
         var1.revertImageView(new FlutterDeviceScreenshot$$ExternalSyntheticLambda3());
      }
   }

   private static void takeScreenshot(Handler var0, Handler var1, FlutterView var2, MethodChannel.Result var3) {
      waitForAndroidFrame(new FlutterDeviceScreenshot$$ExternalSyntheticLambda2(var2.acquireLatestImageViewFrame(), var2, var3, var0, var1));
   }

   private static void waitForAndroidFrame(Runnable var0) {
      Choreographer.getInstance().postFrameCallback(new FrameCallback(var0) {
         final Runnable val$r;

         {
            this.val$r = var1;
         }

         public void doFrame(long var1) {
            this.val$r.run();
         }
      });
   }
}
