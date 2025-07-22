package io.flutter.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Build.VERSION;
import androidx.transition.CanvasUtils..ExternalSyntheticApiModelOutline0;

public final class HandlerCompat {
   public static Handler createAsyncHandler(Looper var0) {
      return VERSION.SDK_INT >= 28 ? ExternalSyntheticApiModelOutline0.m(var0) : new Handler(var0);
   }
}
