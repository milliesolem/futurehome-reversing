package io.sentry.android.replay.video

import android.media.MediaFormat
import android.media.MediaCodec.BufferInfo
import java.nio.ByteBuffer

public interface SimpleFrameMuxer {
   public abstract fun getVideoTime(): Long {
   }

   public abstract fun isStarted(): Boolean {
   }

   public abstract fun muxVideoFrame(encodedData: ByteBuffer, bufferInfo: BufferInfo) {
   }

   public abstract fun release() {
   }

   public abstract fun start(videoFormat: MediaFormat) {
   }
}
