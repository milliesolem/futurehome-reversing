package io.sentry.android.replay.video

import android.media.MediaFormat
import android.media.MediaMuxer
import android.media.MediaCodec.BufferInfo
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit

public class SimpleMp4FrameMuxer(path: String, fps: Float) : SimpleFrameMuxer {
   private final var finalVideoTime: Long
   private final val frameDurationUsec: Long
   private final val muxer: MediaMuxer
   private final var started: Boolean
   private final var videoFrames: Int
   private final var videoTrackIndex: Int

   init {
      this.frameDurationUsec = (long)((float)TimeUnit.SECONDS.toMicros(1L) / var2);
      this.muxer = new MediaMuxer(var1, 0);
   }

   public override fun getVideoTime(): Long {
      return if (this.videoFrames == 0) 0L else TimeUnit.MILLISECONDS.convert(this.finalVideoTime + this.frameDurationUsec, TimeUnit.MICROSECONDS);
   }

   public override fun isStarted(): Boolean {
      return this.started;
   }

   public override fun muxVideoFrame(encodedData: ByteBuffer, bufferInfo: BufferInfo) {
      val var6: Long = this.frameDurationUsec * this.videoFrames++;
      this.finalVideoTime = var6;
      var2.presentationTimeUs = var6;
      this.muxer.writeSampleData(this.videoTrackIndex, var1, var2);
   }

   public override fun release() {
      this.muxer.stop();
      this.muxer.release();
   }

   public override fun start(videoFormat: MediaFormat) {
      this.videoTrackIndex = this.muxer.addTrack(var1);
      this.muxer.start();
      this.started = true;
   }
}
