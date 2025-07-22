package io.sentry.android.replay.video

import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.MediaCodec
import android.media.MediaFormat
import android.media.MediaCodec.BufferInfo
import android.media.MediaCodecInfo.VideoCapabilities
import android.os.Build
import android.view.Surface
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0
import io.sentry.ILogger
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import java.nio.ByteBuffer
import kotlin.jvm.functions.Function0

internal class SimpleVideoEncoder(options: SentryOptions, muxerConfig: MuxerConfig, onClose: (() -> Unit)? = null) {
   private final val bufferInfo: BufferInfo

   public final val duration: Long
      public final get() {
         return this.frameMuxer.getVideoTime();
      }


   private final val frameMuxer: SimpleMp4FrameMuxer

   private final val hasExynosCodec: Boolean
      private final get() {
         return this.hasExynosCodec$delegate.getValue() as java.lang.Boolean;
      }


   internal final val mediaCodec: MediaCodec

   private final val mediaFormat: MediaFormat
      private final get() {
         return this.mediaFormat$delegate.getValue() as MediaFormat;
      }


   public final val muxerConfig: MuxerConfig
   public final val onClose: (() -> Unit)?
   public final val options: SentryOptions
   private final var surface: Surface?

   init {
      this.options = var1;
      this.muxerConfig = var2;
      this.onClose = var3;
      this.hasExynosCodec$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, <unrepresentable>.INSTANCE);
      val var4: SimpleVideoEncoder = this;
      val var5: MediaCodec;
      if (this.getHasExynosCodec()) {
         var5 = MediaCodec.createByCodecName("c2.android.avc.encoder");
      } else {
         var5 = MediaCodec.createEncoderByType(this.muxerConfig.getMimeType());
      }

      this.mediaCodec = var5;
      this.mediaFormat$delegate = LazyKt.lazy(
         LazyThreadSafetyMode.NONE,
         (
            new Function0<MediaFormat>(this) {
               final SimpleVideoEncoder this$0;

               {
                  super(0);
                  this.this$0 = var1;
               }

               // $VF: Duplicated exception handlers to handle obfuscated exceptions
               // $VF: Could not inline inconsistent finally blocks
               // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
               public final MediaFormat invoke() {
                  val var2: Int = this.this$0.getMuxerConfig().getBitRate();

                  var var1: Int;
                  label26: {
                     var var3: VideoCapabilities;
                     try {
                        var3 = this.this$0
                           .getMediaCodec$sentry_android_replay_release()
                           .getCodecInfo()
                           .getCapabilitiesForType(this.this$0.getMuxerConfig().getMimeType())
                           .getVideoCapabilities();
                     } catch (var8: java.lang.Throwable) {
                        this.this$0.getOptions().getLogger().log(SentryLevel.DEBUG, "Could not retrieve MediaCodec info", var8);
                        var1 = var2;
                        break label26;
                     }

                     var1 = var2;

                     label18:
                     try {
                        if (!var3.getBitrateRange().contains(var2)) {
                           val var6: ILogger = this.this$0.getOptions().getLogger();
                           val var4: SentryLevel = SentryLevel.DEBUG;
                           val var5: StringBuilder = new StringBuilder("Encoder doesn't support the provided bitRate: ");
                           var5.append(var2);
                           var5.append(", the value will be clamped to the closest one");
                           var6.log(var4, var5.toString());
                           val var13: java.lang.Comparable = var3.getBitrateRange().clamp(var2);
                           var1 = (var13 as java.lang.Number).intValue();
                        }
                     } catch (var7: java.lang.Throwable) {
                        this.this$0.getOptions().getLogger().log(SentryLevel.DEBUG, "Could not retrieve MediaCodec info", var7);
                        var1 = var2;
                        break label18;
                     }
                  }

                  val var14: MediaFormat = MediaFormat.createVideoFormat(
                     this.this$0.getMuxerConfig().getMimeType(),
                     this.this$0.getMuxerConfig().getRecordingWidth(),
                     this.this$0.getMuxerConfig().getRecordingHeight()
                  );
                  var14.setInteger("color-format", 2130708361);
                  var14.setInteger("bitrate", var1);
                  var14.setFloat("frame-rate", (float)this.this$0.getMuxerConfig().getFrameRate());
                  var14.setInteger("i-frame-interval", 6);
                  return var14;
               }
            }
         ) as Function0
      );
      this.bufferInfo = new BufferInfo();
      val var6: java.lang.String = var2.getFile().getAbsolutePath();
      this.frameMuxer = new SimpleMp4FrameMuxer(var6, var2.getFrameRate());
   }

   private fun drainCodec(endOfStream: Boolean) {
      val var4: ILogger = this.options.getLogger();
      var var5: SentryLevel = SentryLevel.DEBUG;
      var var3: StringBuilder = new StringBuilder("[Encoder]: drainCodec(");
      var3.append(var1);
      var3.append(')');
      var4.log(var5, var3.toString());
      if (var1) {
         this.options.getLogger().log(SentryLevel.DEBUG, "[Encoder]: sending EOS to encoder");
         this.mediaCodec.signalEndOfInputStream();
      }

      var var8: Array<ByteBuffer> = this.mediaCodec.getOutputBuffers();

      while (true) {
         val var2: Int = this.mediaCodec.dequeueOutputBuffer(this.bufferInfo, 100000L);
         if (var2 == -1) {
            if (var1) {
               this.options.getLogger().log(SentryLevel.DEBUG, "[Encoder]: no output available, spinning to await EOS");
               continue;
            }
            break;
         } else if (var2 == -3) {
            var8 = this.mediaCodec.getOutputBuffers();
         } else if (var2 == -2) {
            if (this.frameMuxer.isStarted()) {
               throw new RuntimeException("format changed twice");
            }

            val var18: MediaFormat = this.mediaCodec.getOutputFormat();
            val var16: ILogger = this.options.getLogger();
            val var13: SentryLevel = SentryLevel.DEBUG;
            val var7: StringBuilder = new StringBuilder("[Encoder]: encoder output format changed: ");
            var7.append(var18);
            var16.log(var13, var7.toString());
            this.frameMuxer.start(var18);
         } else if (var2 < 0) {
            val var17: ILogger = this.options.getLogger();
            var5 = SentryLevel.DEBUG;
            val var12: StringBuilder = new StringBuilder("[Encoder]: unexpected result from encoder.dequeueOutputBuffer: ");
            var12.append(var2);
            var17.log(var5, var12.toString());
         } else {
            if (var8 != null) {
               val var10: ByteBuffer = var8[var2];
               if (var8[var2] != null) {
                  if ((this.bufferInfo.flags and 2) != 0) {
                     this.options.getLogger().log(SentryLevel.DEBUG, "[Encoder]: ignoring BUFFER_FLAG_CODEC_CONFIG");
                     this.bufferInfo.size = 0;
                  }

                  if (this.bufferInfo.size != 0) {
                     if (!this.frameMuxer.isStarted()) {
                        throw new RuntimeException("muxer hasn't started");
                     }

                     this.frameMuxer.muxVideoFrame(var10, this.bufferInfo);
                     val var14: ILogger = this.options.getLogger();
                     val var6: SentryLevel = SentryLevel.DEBUG;
                     val var11: StringBuilder = new StringBuilder("[Encoder]: sent ");
                     var11.append(this.bufferInfo.size);
                     var11.append(" bytes to muxer");
                     var14.log(var6, var11.toString());
                  }

                  this.mediaCodec.releaseOutputBuffer(var2, false);
                  if ((this.bufferInfo.flags and 4) == 0) {
                     continue;
                  }

                  if (!var1) {
                     this.options.getLogger().log(SentryLevel.DEBUG, "[Encoder]: reached end of stream unexpectedly");
                  } else {
                     this.options.getLogger().log(SentryLevel.DEBUG, "[Encoder]: end of stream reached");
                  }
                  break;
               }
            }

            var3 = new StringBuilder("encoderOutputBuffer ");
            var3.append(var2);
            var3.append(" was null");
            throw new RuntimeException(var3.toString());
         }
      }
   }

   public fun encode(image: Bitmap) {
      var var6: Canvas;
      label28: {
         label27: {
            val var2: java.lang.String = Build.MANUFACTURER;
            if (!StringsKt.contains(var2, "xiaomi", true)) {
               val var4: java.lang.String = Build.MANUFACTURER;
               if (!StringsKt.contains(var4, "motorola", true)) {
                  if (this.surface != null) {
                     var6 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.surface);
                     break label28;
                  }
                  break label27;
               }
            }

            if (this.surface != null) {
               var6 = this.surface.lockCanvas(null);
               break label28;
            }
         }

         var6 = null;
      }

      if (var6 != null) {
         var6.drawBitmap(var1, 0.0F, 0.0F, null);
      }

      if (this.surface != null) {
         this.surface.unlockCanvasAndPost(var6);
      }

      this.drainCodec(false);
   }

   public fun release() {
      var var1: Function0;
      try {
         var1 = this.onClose;
      } catch (var6: java.lang.Throwable) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Failed to properly release video encoder", var6);
         return;
      }

      if (var1 != null) {
         try {
            var1.invoke();
         } catch (var5: java.lang.Throwable) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Failed to properly release video encoder", var5);
            return;
         }
      }

      try {
         this.drainCodec(true);
         this.mediaCodec.stop();
         this.mediaCodec.release();
         var32 = this.surface;
      } catch (var4: java.lang.Throwable) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Failed to properly release video encoder", var4);
         return;
      }

      if (var32 != null) {
         try {
            var32.release();
         } catch (var3: java.lang.Throwable) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Failed to properly release video encoder", var3);
            return;
         }
      }

      try {
         this.frameMuxer.release();
      } catch (var2: java.lang.Throwable) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Failed to properly release video encoder", var2);
         return;
      }
   }

   public fun start() {
      this.mediaCodec.configure(this.getMediaFormat(), null, null, 1);
      this.surface = this.mediaCodec.createInputSurface();
      this.mediaCodec.start();
      this.drainCodec(false);
   }
}
