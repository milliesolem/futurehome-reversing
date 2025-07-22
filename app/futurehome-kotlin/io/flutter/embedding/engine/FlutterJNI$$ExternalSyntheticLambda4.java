package io.flutter.embedding.engine;

import android.graphics.ImageDecoder;
import android.graphics.ImageDecoder.ImageInfo;
import android.graphics.ImageDecoder.OnHeaderDecodedListener;
import android.graphics.ImageDecoder.Source;

// $VF: synthetic class
public final class FlutterJNI$$ExternalSyntheticLambda4 implements OnHeaderDecodedListener {
   public final long f$0;

   public final void onHeaderDecoded(ImageDecoder var1, ImageInfo var2, Source var3) {
      FlutterJNI.lambda$decodeImage$0(this.f$0, var1, var2, var3);
   }
}
