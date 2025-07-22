package dev.flutter.plugins.integration_test;

import android.graphics.Bitmap;
import android.view.PixelCopy.OnPixelCopyFinishedListener;
import io.flutter.plugin.common.MethodChannel;

// $VF: synthetic class
public final class FlutterDeviceScreenshot$$ExternalSyntheticLambda1 implements OnPixelCopyFinishedListener {
   public final Bitmap f$0;
   public final MethodChannel.Result f$1;

   public final void onPixelCopyFinished(int var1) {
      FlutterDeviceScreenshot.lambda$convertViewToBitmap$5(this.f$0, this.f$1, var1);
   }
}
