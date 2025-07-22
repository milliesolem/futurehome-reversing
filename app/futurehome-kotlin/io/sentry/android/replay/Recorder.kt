package io.sentry.android.replay

import java.io.Closeable

public interface Recorder : Closeable {
   public abstract fun pause() {
   }

   public abstract fun resume() {
   }

   public abstract fun start(recorderConfig: ScreenshotRecorderConfig) {
   }

   public abstract fun stop() {
   }
}
