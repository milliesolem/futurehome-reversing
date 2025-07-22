package io.sentry.android.replay

import android.graphics.Bitmap
import java.io.File

public interface ScreenshotRecorderCallback {
   public abstract fun onScreenshotRecorded(bitmap: Bitmap) {
   }

   public abstract fun onScreenshotRecorded(screenshot: File, frameTimestamp: Long) {
   }
}
