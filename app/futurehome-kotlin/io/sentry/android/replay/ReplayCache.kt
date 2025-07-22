package io.sentry.android.replay

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import io.sentry.android.replay.video.SimpleVideoEncoder
import io.sentry.protocol.SentryId
import java.io.Closeable
import java.io.File
import java.io.FileOutputStream
import java.util.ArrayList
import java.util.LinkedHashMap
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Ref

public class ReplayCache(options: SentryOptions, replayId: SentryId) : Closeable {
   private final var encoder: SimpleVideoEncoder?
   private final val encoderLock: Any
   internal final val frames: MutableList<ReplayFrame>
   private final val isClosed: AtomicBoolean
   private final val ongoingSegment: LinkedHashMap<String, String>

   internal final val ongoingSegmentFile: File?
      internal final get() {
         return this.ongoingSegmentFile$delegate.getValue() as File;
      }


   private final val options: SentryOptions

   internal final val replayCacheDir: File?
      internal final get() {
         return this.replayCacheDir$delegate.getValue() as File;
      }


   private final val replayId: SentryId

   init {
      this.options = var1;
      this.replayId = var2;
      this.isClosed = new AtomicBoolean(false);
      this.encoderLock = new Object();
      this.replayCacheDir$delegate = LazyKt.lazy((new Function0<File>(this) {
         final ReplayCache this$0;

         {
            super(0);
            this.this$0 = var1;
         }

         public final File invoke() {
            return ReplayCache.Companion.makeReplayCacheDir(ReplayCache.access$getOptions$p(this.this$0), ReplayCache.access$getReplayId$p(this.this$0));
         }
      }) as Function0);
      this.frames = new ArrayList<>();
      this.ongoingSegment = new LinkedHashMap<>();
      this.ongoingSegmentFile$delegate = LazyKt.lazy((new Function0<File>(this) {
         final ReplayCache this$0;

         {
            super(0);
            this.this$0 = var1;
         }

         public final File invoke() {
            if (this.this$0.getReplayCacheDir$sentry_android_replay_release() == null) {
               return null;
            } else {
               val var1: File = new File(this.this$0.getReplayCacheDir$sentry_android_replay_release(), ".ongoing_segment");
               if (!var1.exists()) {
                  var1.createNewFile();
               }

               return var1;
            }
         }
      }) as Function0);
   }

   private fun deleteFile(file: File) {
      try {
         if (!var1.delete()) {
            this.options.getLogger().log(SentryLevel.ERROR, "Failed to delete replay frame: %s", var1.getAbsolutePath());
         }
      } catch (var3: java.lang.Throwable) {
         this.options.getLogger().log(SentryLevel.ERROR, var3, "Failed to delete replay frame: %s", var1.getAbsolutePath());
         return;
      }
   }

   private fun encode(frame: ReplayFrame?): Boolean {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
      //
      // Bytecode:
      // 00: bipush 0
      // 01: istore 2
      // 02: aload 1
      // 03: ifnonnull 08
      // 06: bipush 0
      // 07: ireturn
      // 08: aload 1
      // 09: invokevirtual io/sentry/android/replay/ReplayFrame.getScreenshot ()Ljava/io/File;
      // 0c: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 0f: invokestatic android/graphics/BitmapFactory.decodeFile (Ljava/lang/String;)Landroid/graphics/Bitmap;
      // 12: astore 3
      // 13: aload 0
      // 14: getfield io/sentry/android/replay/ReplayCache.encoderLock Ljava/lang/Object;
      // 17: astore 1
      // 18: aload 1
      // 19: monitorenter
      // 1a: aload 0
      // 1b: getfield io/sentry/android/replay/ReplayCache.encoder Lio/sentry/android/replay/video/SimpleVideoEncoder;
      // 1e: astore 4
      // 20: aload 4
      // 22: ifnull 37
      // 25: aload 3
      // 26: ldc_w "bitmap"
      // 29: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 2c: aload 4
      // 2e: aload 3
      // 2f: invokevirtual io/sentry/android/replay/video/SimpleVideoEncoder.encode (Landroid/graphics/Bitmap;)V
      // 32: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 35: astore 4
      // 37: aload 1
      // 38: monitorexit
      // 39: aload 3
      // 3a: invokevirtual android/graphics/Bitmap.recycle ()V
      // 3d: bipush 1
      // 3e: istore 2
      // 3f: goto 5b
      // 42: astore 3
      // 43: aload 1
      // 44: monitorexit
      // 45: aload 3
      // 46: athrow
      // 47: astore 1
      // 48: aload 0
      // 49: getfield io/sentry/android/replay/ReplayCache.options Lio/sentry/SentryOptions;
      // 4c: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 4f: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 52: ldc_w "Unable to decode bitmap and encode it into a video, skipping frame"
      // 55: aload 1
      // 56: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 5b: iload 2
      // 5c: ireturn
   }

   public fun addFrame(screenshot: File, frameTimestamp: Long, screen: String? = null) {
      this.frames.add(new ReplayFrame(var1, var2, var4));
   }

   internal fun addFrame(bitmap: Bitmap, frameTimestamp: Long, screen: String? = ...) {
      label27:
      if (this.getReplayCacheDir$sentry_android_replay_release() != null && !var1.isRecycled()) {
         var var5: File = this.getReplayCacheDir$sentry_android_replay_release();
         if (var5 != null) {
            var5.mkdirs();
         }

         var5 = this.getReplayCacheDir$sentry_android_replay_release();
         val var6: StringBuilder = new StringBuilder();
         var6.append(var2);
         var6.append(".jpg");
         val var18: File = new File(var5, var6.toString());
         var18.createNewFile();
         val var17: Closeable = new FileOutputStream(var18);

         try {
            val var7: FileOutputStream = var17 as FileOutputStream;
            var1.compress(CompressFormat.JPEG, this.options.getSessionReplay().getQuality().screenshotQuality, var17 as FileOutputStream);
            var7.flush();
         } catch (var9: java.lang.Throwable) {
            val var14: java.lang.Throwable = var9;

            try {
               throw var14;
            } catch (var8: java.lang.Throwable) {
               CloseableKt.closeFinally(var17, var9);
            }
         }

         CloseableKt.closeFinally(var17, null);
         this.addFrame(var18, var2, var4);
      }
   }

   public override fun close() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/android/replay/ReplayCache.encoderLock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/android/replay/ReplayCache.encoder Lio/sentry/android/replay/video/SimpleVideoEncoder;
      // 0b: astore 2
      // 0c: aload 2
      // 0d: ifnull 14
      // 10: aload 2
      // 11: invokevirtual io/sentry/android/replay/video/SimpleVideoEncoder.release ()V
      // 14: aload 0
      // 15: aconst_null
      // 16: putfield io/sentry/android/replay/ReplayCache.encoder Lio/sentry/android/replay/video/SimpleVideoEncoder;
      // 19: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1c: astore 2
      // 1d: aload 1
      // 1e: monitorexit
      // 1f: aload 0
      // 20: getfield io/sentry/android/replay/ReplayCache.isClosed Ljava/util/concurrent/atomic/AtomicBoolean;
      // 23: bipush 1
      // 24: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 27: return
      // 28: astore 2
      // 29: aload 1
      // 2a: monitorexit
      // 2b: aload 2
      // 2c: athrow
   }

   public fun createVideoOf(
      duration: Long,
      from: Long,
      segmentId: Int,
      height: Int,
      width: Int,
      frameRate: Int,
      bitRate: Int,
      videoFile: File = var0.getReplayCacheDir$sentry_android_replay_release()
   ): GeneratedVideo? {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 000: aload 10
      // 002: ldc_w "videoFile"
      // 005: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 008: aload 10
      // 00a: invokevirtual java/io/File.exists ()Z
      // 00d: ifeq 020
      // 010: aload 10
      // 012: invokevirtual java/io/File.length ()J
      // 015: lconst_0
      // 016: lcmp
      // 017: ifle 020
      // 01a: aload 10
      // 01c: invokevirtual java/io/File.delete ()Z
      // 01f: pop
      // 020: aload 0
      // 021: getfield io/sentry/android/replay/ReplayCache.frames Ljava/util/List;
      // 024: invokeinterface java/util/List.isEmpty ()Z 1
      // 029: ifeq 044
      // 02c: aload 0
      // 02d: getfield io/sentry/android/replay/ReplayCache.options Lio/sentry/SentryOptions;
      // 030: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 033: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 036: ldc_w "No captured frames, skipping generating a video segment"
      // 039: bipush 0
      // 03a: anewarray 4
      // 03d: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 042: aconst_null
      // 043: areturn
      // 044: aload 0
      // 045: getfield io/sentry/android/replay/ReplayCache.encoderLock Ljava/lang/Object;
      // 048: astore 21
      // 04a: aload 21
      // 04c: monitorenter
      // 04d: new io/sentry/android/replay/video/SimpleVideoEncoder
      // 050: astore 22
      // 052: aload 0
      // 053: getfield io/sentry/android/replay/ReplayCache.options Lio/sentry/SentryOptions;
      // 056: astore 23
      // 058: new io/sentry/android/replay/video/MuxerConfig
      // 05b: astore 24
      // 05d: aload 24
      // 05f: aload 10
      // 061: iload 7
      // 063: iload 6
      // 065: iload 8
      // 067: iload 9
      // 069: aconst_null
      // 06a: bipush 32
      // 06c: aconst_null
      // 06d: invokespecial io/sentry/android/replay/video/MuxerConfig.<init> (Ljava/io/File;IIIILjava/lang/String;ILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // 070: aload 22
      // 072: aload 23
      // 074: aload 24
      // 076: aconst_null
      // 077: bipush 4
      // 078: aconst_null
      // 079: invokespecial io/sentry/android/replay/video/SimpleVideoEncoder.<init> (Lio/sentry/SentryOptions;Lio/sentry/android/replay/video/MuxerConfig;Lkotlin/jvm/functions/Function0;ILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // 07c: aload 22
      // 07e: invokevirtual io/sentry/android/replay/video/SimpleVideoEncoder.start ()V
      // 081: aload 21
      // 083: monitorexit
      // 084: aload 0
      // 085: aload 22
      // 087: putfield io/sentry/android/replay/ReplayCache.encoder Lio/sentry/android/replay/video/SimpleVideoEncoder;
      // 08a: sipush 1000
      // 08d: i2l
      // 08e: iload 8
      // 090: i2l
      // 091: ldiv
      // 092: lstore 13
      // 094: aload 0
      // 095: getfield io/sentry/android/replay/ReplayCache.frames Ljava/util/List;
      // 098: invokestatic kotlin/collections/CollectionsKt.first (Ljava/util/List;)Ljava/lang/Object;
      // 09b: checkcast io/sentry/android/replay/ReplayFrame
      // 09e: astore 21
      // 0a0: lload 3
      // 0a1: lload 1
      // 0a2: ladd
      // 0a3: lstore 11
      // 0a5: lload 3
      // 0a6: lload 11
      // 0a8: invokestatic kotlin/ranges/RangesKt.until (JJ)Lkotlin/ranges/LongRange;
      // 0ab: checkcast kotlin/ranges/LongProgression
      // 0ae: lload 13
      // 0b0: invokestatic kotlin/ranges/RangesKt.step (Lkotlin/ranges/LongProgression;J)Lkotlin/ranges/LongProgression;
      // 0b3: astore 22
      // 0b5: aload 22
      // 0b7: invokevirtual kotlin/ranges/LongProgression.getFirst ()J
      // 0ba: lstore 1
      // 0bb: aload 22
      // 0bd: invokevirtual kotlin/ranges/LongProgression.getLast ()J
      // 0c0: lstore 3
      // 0c1: aload 22
      // 0c3: invokevirtual kotlin/ranges/LongProgression.getStep ()J
      // 0c6: lstore 15
      // 0c8: lload 15
      // 0ca: lconst_0
      // 0cb: lcmp
      // 0cc: istore 5
      // 0ce: iload 5
      // 0d0: ifle 0d9
      // 0d3: lload 1
      // 0d4: lload 3
      // 0d5: lcmp
      // 0d6: ifle 0e4
      // 0d9: iload 5
      // 0db: ifge 18e
      // 0de: lload 3
      // 0df: lload 1
      // 0e0: lcmp
      // 0e1: ifgt 18e
      // 0e4: bipush 0
      // 0e5: istore 6
      // 0e7: aload 0
      // 0e8: getfield io/sentry/android/replay/ReplayCache.frames Ljava/util/List;
      // 0eb: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 0f0: astore 23
      // 0f2: aload 21
      // 0f4: astore 22
      // 0f6: aload 23
      // 0f8: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0fd: ifeq 13a
      // 100: aload 23
      // 102: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 107: checkcast io/sentry/android/replay/ReplayFrame
      // 10a: astore 22
      // 10c: lload 1
      // 10d: lload 13
      // 10f: ladd
      // 110: lstore 19
      // 112: aload 22
      // 114: invokevirtual io/sentry/android/replay/ReplayFrame.getTimestamp ()J
      // 117: lstore 17
      // 119: lload 1
      // 11a: lload 17
      // 11c: lcmp
      // 11d: ifgt 12b
      // 120: lload 17
      // 122: lload 19
      // 124: lcmp
      // 125: ifgt 12b
      // 128: goto 13a
      // 12b: aload 22
      // 12d: invokevirtual io/sentry/android/replay/ReplayFrame.getTimestamp ()J
      // 130: lload 19
      // 132: lcmp
      // 133: ifle 0f2
      // 136: aload 21
      // 138: astore 22
      // 13a: aload 0
      // 13b: aload 22
      // 13d: invokespecial io/sentry/android/replay/ReplayCache.encode (Lio/sentry/android/replay/ReplayFrame;)Z
      // 140: ifeq 150
      // 143: iload 6
      // 145: bipush 1
      // 146: iadd
      // 147: istore 5
      // 149: aload 22
      // 14b: astore 21
      // 14d: goto 179
      // 150: aload 22
      // 152: astore 21
      // 154: iload 6
      // 156: istore 5
      // 158: aload 22
      // 15a: ifnull 179
      // 15d: aload 0
      // 15e: aload 22
      // 160: invokevirtual io/sentry/android/replay/ReplayFrame.getScreenshot ()Ljava/io/File;
      // 163: invokespecial io/sentry/android/replay/ReplayCache.deleteFile (Ljava/io/File;)V
      // 166: aload 0
      // 167: getfield io/sentry/android/replay/ReplayCache.frames Ljava/util/List;
      // 16a: aload 22
      // 16c: invokeinterface java/util/List.remove (Ljava/lang/Object;)Z 2
      // 171: pop
      // 172: aconst_null
      // 173: astore 21
      // 175: iload 6
      // 177: istore 5
      // 179: lload 1
      // 17a: lload 3
      // 17b: lcmp
      // 17c: ifeq 18b
      // 17f: lload 1
      // 180: lload 15
      // 182: ladd
      // 183: lstore 1
      // 184: iload 5
      // 186: istore 6
      // 188: goto 0e7
      // 18b: goto 191
      // 18e: bipush 0
      // 18f: istore 5
      // 191: iload 5
      // 193: ifne 1b4
      // 196: aload 0
      // 197: getfield io/sentry/android/replay/ReplayCache.options Lio/sentry/SentryOptions;
      // 19a: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 19d: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 1a0: ldc_w "Generated a video with no frames, not capturing a replay segment"
      // 1a3: bipush 0
      // 1a4: anewarray 4
      // 1a7: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 1ac: aload 0
      // 1ad: aload 10
      // 1af: invokespecial io/sentry/android/replay/ReplayCache.deleteFile (Ljava/io/File;)V
      // 1b2: aconst_null
      // 1b3: areturn
      // 1b4: aload 0
      // 1b5: getfield io/sentry/android/replay/ReplayCache.encoderLock Ljava/lang/Object;
      // 1b8: astore 21
      // 1ba: aload 21
      // 1bc: monitorenter
      // 1bd: aload 0
      // 1be: getfield io/sentry/android/replay/ReplayCache.encoder Lio/sentry/android/replay/video/SimpleVideoEncoder;
      // 1c1: astore 22
      // 1c3: aload 22
      // 1c5: ifnull 1cd
      // 1c8: aload 22
      // 1ca: invokevirtual io/sentry/android/replay/video/SimpleVideoEncoder.release ()V
      // 1cd: aload 0
      // 1ce: getfield io/sentry/android/replay/ReplayCache.encoder Lio/sentry/android/replay/video/SimpleVideoEncoder;
      // 1d1: astore 22
      // 1d3: aload 22
      // 1d5: ifnull 1e1
      // 1d8: aload 22
      // 1da: invokevirtual io/sentry/android/replay/video/SimpleVideoEncoder.getDuration ()J
      // 1dd: lstore 1
      // 1de: goto 1e3
      // 1e1: lconst_0
      // 1e2: lstore 1
      // 1e3: aload 0
      // 1e4: aconst_null
      // 1e5: putfield io/sentry/android/replay/ReplayCache.encoder Lio/sentry/android/replay/video/SimpleVideoEncoder;
      // 1e8: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1eb: astore 22
      // 1ed: aload 21
      // 1ef: monitorexit
      // 1f0: aload 0
      // 1f1: lload 11
      // 1f3: invokevirtual io/sentry/android/replay/ReplayCache.rotate (J)Ljava/lang/String;
      // 1f6: pop
      // 1f7: new io/sentry/android/replay/GeneratedVideo
      // 1fa: dup
      // 1fb: aload 10
      // 1fd: iload 5
      // 1ff: lload 1
      // 200: invokespecial io/sentry/android/replay/GeneratedVideo.<init> (Ljava/io/File;IJ)V
      // 203: areturn
      // 204: astore 10
      // 206: aload 21
      // 208: monitorexit
      // 209: aload 10
      // 20b: athrow
      // 20c: astore 10
      // 20e: goto 213
      // 211: astore 10
      // 213: aload 21
      // 215: monitorexit
      // 216: aload 10
      // 218: athrow
   }

   public fun persistSegmentValues(key: String, value: String?) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 1
      // 003: ldc_w "key"
      // 006: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 009: aload 0
      // 00a: getfield io/sentry/android/replay/ReplayCache.isClosed Ljava/util/concurrent/atomic/AtomicBoolean;
      // 00d: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 010: istore 3
      // 011: iload 3
      // 012: ifeq 018
      // 015: aload 0
      // 016: monitorexit
      // 017: return
      // 018: aload 0
      // 019: invokevirtual io/sentry/android/replay/ReplayCache.getOngoingSegmentFile$sentry_android_replay_release ()Ljava/io/File;
      // 01c: astore 4
      // 01e: aload 4
      // 020: ifnull 02f
      // 023: aload 4
      // 025: invokevirtual java/io/File.exists ()Z
      // 028: bipush 1
      // 029: if_icmpne 02f
      // 02c: goto 040
      // 02f: aload 0
      // 030: invokevirtual io/sentry/android/replay/ReplayCache.getOngoingSegmentFile$sentry_android_replay_release ()Ljava/io/File;
      // 033: astore 4
      // 035: aload 4
      // 037: ifnull 040
      // 03a: aload 4
      // 03c: invokevirtual java/io/File.createNewFile ()Z
      // 03f: pop
      // 040: aload 0
      // 041: getfield io/sentry/android/replay/ReplayCache.ongoingSegment Ljava/util/LinkedHashMap;
      // 044: invokevirtual java/util/LinkedHashMap.isEmpty ()Z
      // 047: ifeq 137
      // 04a: aload 0
      // 04b: invokevirtual io/sentry/android/replay/ReplayCache.getOngoingSegmentFile$sentry_android_replay_release ()Ljava/io/File;
      // 04e: astore 7
      // 050: aload 7
      // 052: ifnull 137
      // 055: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
      // 058: astore 4
      // 05a: new java/io/InputStreamReader
      // 05d: astore 5
      // 05f: new java/io/FileInputStream
      // 062: astore 6
      // 064: aload 6
      // 066: aload 7
      // 068: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 06b: aload 5
      // 06d: aload 6
      // 06f: checkcast java/io/InputStream
      // 072: aload 4
      // 074: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
      // 077: aload 5
      // 079: checkcast java/io/Reader
      // 07c: astore 4
      // 07e: aload 4
      // 080: instanceof java/io/BufferedReader
      // 083: ifeq 090
      // 086: aload 4
      // 088: checkcast java/io/BufferedReader
      // 08b: astore 4
      // 08d: goto 09e
      // 090: new java/io/BufferedReader
      // 093: dup
      // 094: aload 4
      // 096: sipush 8192
      // 099: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;I)V
      // 09c: astore 4
      // 09e: aload 4
      // 0a0: checkcast java/io/Closeable
      // 0a3: astore 4
      // 0a5: aload 4
      // 0a7: checkcast java/io/BufferedReader
      // 0aa: invokestatic kotlin/io/TextStreamsKt.lineSequence (Ljava/io/BufferedReader;)Lkotlin/sequences/Sequence;
      // 0ad: astore 6
      // 0af: aload 0
      // 0b0: getfield io/sentry/android/replay/ReplayCache.ongoingSegment Ljava/util/LinkedHashMap;
      // 0b3: checkcast java/util/Map
      // 0b6: astore 5
      // 0b8: aload 6
      // 0ba: invokeinterface kotlin/sequences/Sequence.iterator ()Ljava/util/Iterator; 1
      // 0bf: astore 6
      // 0c1: aload 6
      // 0c3: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0c8: ifeq 11b
      // 0cb: aload 6
      // 0cd: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0d2: checkcast java/lang/String
      // 0d5: checkcast java/lang/CharSequence
      // 0d8: bipush 1
      // 0d9: anewarray 518
      // 0dc: dup
      // 0dd: bipush 0
      // 0de: ldc_w "="
      // 0e1: aastore
      // 0e2: bipush 0
      // 0e3: bipush 2
      // 0e4: bipush 2
      // 0e5: aconst_null
      // 0e6: invokestatic kotlin/text/StringsKt.split$default (Ljava/lang/CharSequence;[Ljava/lang/String;ZIILjava/lang/Object;)Ljava/util/List;
      // 0e9: astore 7
      // 0eb: aload 7
      // 0ed: bipush 0
      // 0ee: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 0f3: checkcast java/lang/String
      // 0f6: aload 7
      // 0f8: bipush 1
      // 0f9: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 0fe: checkcast java/lang/String
      // 101: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
      // 104: astore 7
      // 106: aload 5
      // 108: aload 7
      // 10a: invokevirtual kotlin/Pair.getFirst ()Ljava/lang/Object;
      // 10d: aload 7
      // 10f: invokevirtual kotlin/Pair.getSecond ()Ljava/lang/Object;
      // 112: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 117: pop
      // 118: goto 0c1
      // 11b: aload 5
      // 11d: checkcast java/util/LinkedHashMap
      // 120: astore 5
      // 122: aload 4
      // 124: aconst_null
      // 125: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 128: goto 137
      // 12b: astore 1
      // 12c: aload 1
      // 12d: athrow
      // 12e: astore 2
      // 12f: aload 4
      // 131: aload 1
      // 132: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 135: aload 2
      // 136: athrow
      // 137: aload 2
      // 138: ifnonnull 147
      // 13b: aload 0
      // 13c: getfield io/sentry/android/replay/ReplayCache.ongoingSegment Ljava/util/LinkedHashMap;
      // 13f: aload 1
      // 140: invokevirtual java/util/LinkedHashMap.remove (Ljava/lang/Object;)Ljava/lang/Object;
      // 143: pop
      // 144: goto 156
      // 147: aload 0
      // 148: getfield io/sentry/android/replay/ReplayCache.ongoingSegment Ljava/util/LinkedHashMap;
      // 14b: checkcast java/util/Map
      // 14e: aload 1
      // 14f: aload 2
      // 150: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 155: pop
      // 156: aload 0
      // 157: invokevirtual io/sentry/android/replay/ReplayCache.getOngoingSegmentFile$sentry_android_replay_release ()Ljava/io/File;
      // 15a: astore 1
      // 15b: aload 1
      // 15c: ifnull 18f
      // 15f: aload 0
      // 160: getfield io/sentry/android/replay/ReplayCache.ongoingSegment Ljava/util/LinkedHashMap;
      // 163: invokevirtual java/util/LinkedHashMap.entrySet ()Ljava/util/Set;
      // 166: astore 2
      // 167: aload 2
      // 168: ldc_w "ongoingSegment.entries"
      // 16b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 16e: aload 1
      // 16f: aload 2
      // 170: checkcast java/lang/Iterable
      // 173: ldc_w "\n"
      // 176: checkcast java/lang/CharSequence
      // 179: aconst_null
      // 17a: aconst_null
      // 17b: bipush 0
      // 17c: aconst_null
      // 17d: getstatic io/sentry/android/replay/ReplayCache$persistSegmentValues$2.INSTANCE Lio/sentry/android/replay/ReplayCache$persistSegmentValues$2;
      // 180: checkcast kotlin/jvm/functions/Function1
      // 183: bipush 30
      // 185: aconst_null
      // 186: invokestatic kotlin/collections/CollectionsKt.joinToString$default (Ljava/lang/Iterable;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;
      // 189: aconst_null
      // 18a: bipush 2
      // 18b: aconst_null
      // 18c: invokestatic kotlin/io/FilesKt.writeText$default (Ljava/io/File;Ljava/lang/String;Ljava/nio/charset/Charset;ILjava/lang/Object;)V
      // 18f: aload 0
      // 190: monitorexit
      // 191: return
      // 192: astore 1
      // 193: aload 0
      // 194: monitorexit
      // 195: aload 1
      // 196: athrow
   }

   public fun rotate(until: Long): String? {
      val var3: Ref.ObjectRef = new Ref.ObjectRef();
      CollectionsKt.removeAll(this.frames, (new Function1<ReplayFrame, java.lang.Boolean>(var1, this, var3) {
         final Ref.ObjectRef<java.lang.String> $screen;
         final long $until;
         final ReplayCache this$0;

         {
            super(1);
            this.$until = var1;
            this.this$0 = var3;
            this.$screen = var4;
         }

         public final java.lang.Boolean invoke(ReplayFrame var1) {
            if (var1.getTimestamp() < this.$until) {
               ReplayCache.access$deleteFile(this.this$0, var1.getScreenshot());
               return true;
            } else {
               if (this.$screen.element == null) {
                  this.$screen.element = var1.getScreen();
               }

               return false;
            }
         }
      }) as (ReplayFrame?) -> java.lang.Boolean);
      return var3.element as java.lang.String;
   }

   public companion object {
      internal const val ONGOING_SEGMENT: String
      internal const val SEGMENT_KEY_BIT_RATE: String
      internal const val SEGMENT_KEY_FRAME_RATE: String
      internal const val SEGMENT_KEY_HEIGHT: String
      internal const val SEGMENT_KEY_ID: String
      internal const val SEGMENT_KEY_REPLAY_ID: String
      internal const val SEGMENT_KEY_REPLAY_RECORDING: String
      internal const val SEGMENT_KEY_REPLAY_SCREEN_AT_START: String
      internal const val SEGMENT_KEY_REPLAY_TYPE: String
      internal const val SEGMENT_KEY_TIMESTAMP: String
      internal const val SEGMENT_KEY_WIDTH: String

      @JvmStatic
      fun `fromDisk$lambda$3`(var0: ReplayCache, var1: File, var2: java.lang.String): Boolean {
         if (StringsKt.endsWith$default(var2, ".jpg", false, 2, null)) {
            var1 = new File(var1, var2);
            val var4: java.lang.Long = StringsKt.toLongOrNull(FilesKt.getNameWithoutExtension(var1));
            if (var4 != null) {
               ReplayCache.addFrame$default(var0, var1, var4, null, 4, null);
            }
         }

         return false;
      }

      internal fun fromDisk(options: SentryOptions, replayId: SentryId, replayCacheProvider: ((SentryId) -> ReplayCache)? = ...): LastSegmentData? {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //
         // Bytecode:
         // 000: ldc ""
         // 002: astore 15
         // 004: aload 1
         // 005: ldc "options"
         // 007: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
         // 00a: aload 2
         // 00b: ldc "replayId"
         // 00d: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
         // 010: aload 0
         // 011: aload 1
         // 012: aload 2
         // 013: invokevirtual io/sentry/android/replay/ReplayCache$Companion.makeReplayCacheDir (Lio/sentry/SentryOptions;Lio/sentry/protocol/SentryId;)Ljava/io/File;
         // 016: astore 20
         // 018: new java/io/File
         // 01b: dup
         // 01c: aload 20
         // 01e: ldc ".ongoing_segment"
         // 020: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
         // 023: astore 11
         // 025: aload 11
         // 027: invokevirtual java/io/File.exists ()Z
         // 02a: ifne 04b
         // 02d: aload 1
         // 02e: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
         // 031: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
         // 034: ldc "No ongoing segment found for replay: %s"
         // 036: bipush 1
         // 037: anewarray 4
         // 03a: dup
         // 03b: bipush 0
         // 03c: aload 2
         // 03d: aastore
         // 03e: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
         // 043: aload 20
         // 045: invokestatic io/sentry/util/FileUtils.deleteRecursively (Ljava/io/File;)Z
         // 048: pop
         // 049: aconst_null
         // 04a: areturn
         // 04b: new java/util/LinkedHashMap
         // 04e: dup
         // 04f: invokespecial java/util/LinkedHashMap.<init> ()V
         // 052: astore 19
         // 054: getstatic kotlin/text/Charsets.UTF_8 Ljava/nio/charset/Charset;
         // 057: astore 12
         // 059: new java/io/InputStreamReader
         // 05c: dup
         // 05d: new java/io/FileInputStream
         // 060: dup
         // 061: aload 11
         // 063: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
         // 066: checkcast java/io/InputStream
         // 069: aload 12
         // 06b: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
         // 06e: checkcast java/io/Reader
         // 071: astore 11
         // 073: aload 11
         // 075: instanceof java/io/BufferedReader
         // 078: ifeq 085
         // 07b: aload 11
         // 07d: checkcast java/io/BufferedReader
         // 080: astore 11
         // 082: goto 093
         // 085: new java/io/BufferedReader
         // 088: dup
         // 089: aload 11
         // 08b: sipush 8192
         // 08e: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;I)V
         // 091: astore 11
         // 093: aload 11
         // 095: checkcast java/io/Closeable
         // 098: astore 11
         // 09a: aload 11
         // 09c: checkcast java/io/BufferedReader
         // 09f: invokestatic kotlin/io/TextStreamsKt.lineSequence (Ljava/io/BufferedReader;)Lkotlin/sequences/Sequence;
         // 0a2: invokeinterface kotlin/sequences/Sequence.iterator ()Ljava/util/Iterator; 1
         // 0a7: astore 12
         // 0a9: aload 12
         // 0ab: invokeinterface java/util/Iterator.hasNext ()Z 1
         // 0b0: ifeq 10d
         // 0b3: aload 12
         // 0b5: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
         // 0ba: astore 14
         // 0bc: aload 19
         // 0be: checkcast java/util/Map
         // 0c1: astore 13
         // 0c3: aload 14
         // 0c5: checkcast java/lang/String
         // 0c8: checkcast java/lang/CharSequence
         // 0cb: bipush 1
         // 0cc: anewarray 206
         // 0cf: dup
         // 0d0: bipush 0
         // 0d1: ldc "="
         // 0d3: aastore
         // 0d4: bipush 0
         // 0d5: bipush 2
         // 0d6: bipush 2
         // 0d7: aconst_null
         // 0d8: invokestatic kotlin/text/StringsKt.split$default (Ljava/lang/CharSequence;[Ljava/lang/String;ZIILjava/lang/Object;)Ljava/util/List;
         // 0db: astore 14
         // 0dd: aload 14
         // 0df: bipush 0
         // 0e0: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
         // 0e5: checkcast java/lang/String
         // 0e8: aload 14
         // 0ea: bipush 1
         // 0eb: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
         // 0f0: checkcast java/lang/String
         // 0f3: invokestatic kotlin/TuplesKt.to (Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;
         // 0f6: astore 14
         // 0f8: aload 13
         // 0fa: aload 14
         // 0fc: invokevirtual kotlin/Pair.getFirst ()Ljava/lang/Object;
         // 0ff: aload 14
         // 101: invokevirtual kotlin/Pair.getSecond ()Ljava/lang/Object;
         // 104: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
         // 109: pop
         // 10a: goto 0a9
         // 10d: aload 19
         // 10f: checkcast java/util/Map
         // 112: checkcast java/util/LinkedHashMap
         // 115: astore 12
         // 117: aload 11
         // 119: aconst_null
         // 11a: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // 11d: aload 19
         // 11f: ldc "config.height"
         // 121: invokevirtual java/util/LinkedHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
         // 124: checkcast java/lang/String
         // 127: astore 11
         // 129: aload 11
         // 12b: ifnull 138
         // 12e: aload 11
         // 130: invokestatic kotlin/text/StringsKt.toIntOrNull (Ljava/lang/String;)Ljava/lang/Integer;
         // 133: astore 13
         // 135: goto 13b
         // 138: aconst_null
         // 139: astore 13
         // 13b: aload 19
         // 13d: ldc "config.width"
         // 13f: invokevirtual java/util/LinkedHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
         // 142: checkcast java/lang/String
         // 145: astore 11
         // 147: aload 11
         // 149: ifnull 156
         // 14c: aload 11
         // 14e: invokestatic kotlin/text/StringsKt.toIntOrNull (Ljava/lang/String;)Ljava/lang/Integer;
         // 151: astore 14
         // 153: goto 159
         // 156: aconst_null
         // 157: astore 14
         // 159: aload 19
         // 15b: ldc_w "config.frame-rate"
         // 15e: invokevirtual java/util/LinkedHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
         // 161: checkcast java/lang/String
         // 164: astore 11
         // 166: aload 11
         // 168: ifnull 175
         // 16b: aload 11
         // 16d: invokestatic kotlin/text/StringsKt.toIntOrNull (Ljava/lang/String;)Ljava/lang/Integer;
         // 170: astore 12
         // 172: goto 178
         // 175: aconst_null
         // 176: astore 12
         // 178: aload 19
         // 17a: ldc_w "config.bit-rate"
         // 17d: invokevirtual java/util/LinkedHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
         // 180: checkcast java/lang/String
         // 183: astore 11
         // 185: aload 11
         // 187: ifnull 194
         // 18a: aload 11
         // 18c: invokestatic kotlin/text/StringsKt.toIntOrNull (Ljava/lang/String;)Ljava/lang/Integer;
         // 18f: astore 17
         // 191: goto 197
         // 194: aconst_null
         // 195: astore 17
         // 197: aload 19
         // 199: ldc_w "segment.id"
         // 19c: invokevirtual java/util/LinkedHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
         // 19f: checkcast java/lang/String
         // 1a2: astore 11
         // 1a4: aload 11
         // 1a6: ifnull 1b3
         // 1a9: aload 11
         // 1ab: invokestatic kotlin/text/StringsKt.toIntOrNull (Ljava/lang/String;)Ljava/lang/Integer;
         // 1ae: astore 16
         // 1b0: goto 1b6
         // 1b3: aconst_null
         // 1b4: astore 16
         // 1b6: aload 19
         // 1b8: ldc_w "segment.timestamp"
         // 1bb: invokevirtual java/util/LinkedHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
         // 1be: checkcast java/lang/String
         // 1c1: astore 18
         // 1c3: aload 18
         // 1c5: astore 11
         // 1c7: aload 18
         // 1c9: ifnonnull 1d0
         // 1cc: ldc ""
         // 1ce: astore 11
         // 1d0: aload 11
         // 1d2: invokestatic io/sentry/DateUtils.getDateTime (Ljava/lang/String;)Ljava/util/Date;
         // 1d5: astore 11
         // 1d7: goto 1df
         // 1da: astore 11
         // 1dc: aconst_null
         // 1dd: astore 11
         // 1df: aload 19
         // 1e1: ldc_w "replay.type"
         // 1e4: invokevirtual java/util/LinkedHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
         // 1e7: checkcast java/lang/String
         // 1ea: astore 18
         // 1ec: aload 18
         // 1ee: ifnonnull 1f4
         // 1f1: goto 1f8
         // 1f4: aload 18
         // 1f6: astore 15
         // 1f8: aload 15
         // 1fa: invokestatic io/sentry/SentryReplayEvent$ReplayType.valueOf (Ljava/lang/String;)Lio/sentry/SentryReplayEvent$ReplayType;
         // 1fd: astore 15
         // 1ff: goto 207
         // 202: astore 15
         // 204: aconst_null
         // 205: astore 15
         // 207: aload 13
         // 209: ifnull 3d2
         // 20c: aload 14
         // 20e: ifnull 3d2
         // 211: aload 12
         // 213: ifnull 3d2
         // 216: aload 17
         // 218: ifnull 3d2
         // 21b: aload 16
         // 21d: ifnull 3d2
         // 220: aload 16
         // 222: invokevirtual java/lang/Integer.intValue ()I
         // 225: bipush -1
         // 226: if_icmpeq 3d2
         // 229: aload 11
         // 22b: ifnull 3d2
         // 22e: aload 15
         // 230: ifnonnull 236
         // 233: goto 3d2
         // 236: new io/sentry/android/replay/ScreenshotRecorderConfig
         // 239: dup
         // 23a: aload 14
         // 23c: invokevirtual java/lang/Integer.intValue ()I
         // 23f: aload 13
         // 241: invokevirtual java/lang/Integer.intValue ()I
         // 244: fconst_1
         // 245: fconst_1
         // 246: aload 12
         // 248: invokevirtual java/lang/Integer.intValue ()I
         // 24b: aload 17
         // 24d: invokevirtual java/lang/Integer.intValue ()I
         // 250: invokespecial io/sentry/android/replay/ScreenshotRecorderConfig.<init> (IIFFII)V
         // 253: astore 14
         // 255: aload 3
         // 256: ifnull 26d
         // 259: aload 3
         // 25a: aload 2
         // 25b: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
         // 260: checkcast io/sentry/android/replay/ReplayCache
         // 263: astore 13
         // 265: aload 13
         // 267: astore 3
         // 268: aload 13
         // 26a: ifnonnull 277
         // 26d: new io/sentry/android/replay/ReplayCache
         // 270: dup
         // 271: aload 1
         // 272: aload 2
         // 273: invokespecial io/sentry/android/replay/ReplayCache.<init> (Lio/sentry/SentryOptions;Lio/sentry/protocol/SentryId;)V
         // 276: astore 3
         // 277: aload 3
         // 278: invokevirtual io/sentry/android/replay/ReplayCache.getReplayCacheDir$sentry_android_replay_release ()Ljava/io/File;
         // 27b: astore 13
         // 27d: aload 13
         // 27f: ifnull 290
         // 282: aload 13
         // 284: new io/sentry/android/replay/ReplayCache$Companion$$ExternalSyntheticLambda0
         // 287: dup
         // 288: aload 3
         // 289: invokespecial io/sentry/android/replay/ReplayCache$Companion$$ExternalSyntheticLambda0.<init> (Lio/sentry/android/replay/ReplayCache;)V
         // 28c: invokevirtual java/io/File.listFiles (Ljava/io/FilenameFilter;)[Ljava/io/File;
         // 28f: pop
         // 290: aload 3
         // 291: invokevirtual io/sentry/android/replay/ReplayCache.getFrames$sentry_android_replay_release ()Ljava/util/List;
         // 294: invokeinterface java/util/List.isEmpty ()Z 1
         // 299: ifeq 2bb
         // 29c: aload 1
         // 29d: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
         // 2a0: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
         // 2a3: ldc_w "No frames found for replay: %s, deleting the replay"
         // 2a6: bipush 1
         // 2a7: anewarray 4
         // 2aa: dup
         // 2ab: bipush 0
         // 2ac: aload 2
         // 2ad: aastore
         // 2ae: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
         // 2b3: aload 20
         // 2b5: invokestatic io/sentry/util/FileUtils.deleteRecursively (Ljava/io/File;)Z
         // 2b8: pop
         // 2b9: aconst_null
         // 2ba: areturn
         // 2bb: aload 3
         // 2bc: invokevirtual io/sentry/android/replay/ReplayCache.getFrames$sentry_android_replay_release ()Ljava/util/List;
         // 2bf: astore 2
         // 2c0: aload 2
         // 2c1: invokeinterface java/util/List.size ()I 1
         // 2c6: bipush 1
         // 2c7: if_icmple 2d8
         // 2ca: aload 2
         // 2cb: new io/sentry/android/replay/ReplayCache$Companion$fromDisk$$inlined$sortBy$1
         // 2ce: dup
         // 2cf: invokespecial io/sentry/android/replay/ReplayCache$Companion$fromDisk$$inlined$sortBy$1.<init> ()V
         // 2d2: checkcast java/util/Comparator
         // 2d5: invokestatic kotlin/collections/CollectionsKt.sortWith (Ljava/util/List;Ljava/util/Comparator;)V
         // 2d8: aload 15
         // 2da: getstatic io/sentry/SentryReplayEvent$ReplayType.SESSION Lio/sentry/SentryReplayEvent$ReplayType;
         // 2dd: if_acmpne 2ea
         // 2e0: aload 16
         // 2e2: invokevirtual java/lang/Integer.intValue ()I
         // 2e5: istore 4
         // 2e7: goto 2ed
         // 2ea: bipush 0
         // 2eb: istore 4
         // 2ed: aload 15
         // 2ef: getstatic io/sentry/SentryReplayEvent$ReplayType.SESSION Lio/sentry/SentryReplayEvent$ReplayType;
         // 2f2: if_acmpne 2f8
         // 2f5: goto 312
         // 2f8: aload 3
         // 2f9: invokevirtual io/sentry/android/replay/ReplayCache.getFrames$sentry_android_replay_release ()Ljava/util/List;
         // 2fc: invokestatic kotlin/collections/CollectionsKt.first (Ljava/util/List;)Ljava/lang/Object;
         // 2ff: checkcast io/sentry/android/replay/ReplayFrame
         // 302: invokevirtual io/sentry/android/replay/ReplayFrame.getTimestamp ()J
         // 305: invokestatic io/sentry/DateUtils.getDateTime (J)Ljava/util/Date;
         // 308: astore 11
         // 30a: aload 11
         // 30c: ldc_w "{\n                // in ….timestamp)\n            }"
         // 30f: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
         // 312: aload 3
         // 313: invokevirtual io/sentry/android/replay/ReplayCache.getFrames$sentry_android_replay_release ()Ljava/util/List;
         // 316: invokestatic kotlin/collections/CollectionsKt.last (Ljava/util/List;)Ljava/lang/Object;
         // 319: checkcast io/sentry/android/replay/ReplayFrame
         // 31c: invokevirtual io/sentry/android/replay/ReplayFrame.getTimestamp ()J
         // 31f: lstore 5
         // 321: aload 11
         // 323: invokevirtual java/util/Date.getTime ()J
         // 326: lstore 7
         // 328: sipush 1000
         // 32b: aload 12
         // 32d: invokevirtual java/lang/Integer.intValue ()I
         // 330: idiv
         // 331: i2l
         // 332: lstore 9
         // 334: aload 19
         // 336: ldc_w "replay.recording"
         // 339: invokevirtual java/util/LinkedHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
         // 33c: checkcast java/lang/String
         // 33f: astore 2
         // 340: aload 2
         // 341: ifnull 399
         // 344: new java/io/StringReader
         // 347: dup
         // 348: aload 2
         // 349: invokespecial java/io/StringReader.<init> (Ljava/lang/String;)V
         // 34c: astore 2
         // 34d: aload 1
         // 34e: invokevirtual io/sentry/SentryOptions.getSerializer ()Lio/sentry/ISerializer;
         // 351: aload 2
         // 352: checkcast java/io/Reader
         // 355: ldc_w io/sentry/ReplayRecording
         // 358: invokeinterface io/sentry/ISerializer.deserialize (Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object; 3
         // 35d: checkcast io/sentry/ReplayRecording
         // 360: astore 2
         // 361: aload 2
         // 362: ifnull 36d
         // 365: aload 2
         // 366: invokevirtual io/sentry/ReplayRecording.getPayload ()Ljava/util/List;
         // 369: astore 1
         // 36a: goto 36f
         // 36d: aconst_null
         // 36e: astore 1
         // 36f: aload 1
         // 370: ifnull 38b
         // 373: aload 2
         // 374: invokevirtual io/sentry/ReplayRecording.getPayload ()Ljava/util/List;
         // 377: astore 1
         // 378: aload 1
         // 379: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
         // 37c: new java/util/LinkedList
         // 37f: dup
         // 380: aload 1
         // 381: checkcast java/util/Collection
         // 384: invokespecial java/util/LinkedList.<init> (Ljava/util/Collection;)V
         // 387: astore 1
         // 388: goto 38d
         // 38b: aconst_null
         // 38c: astore 1
         // 38d: aload 1
         // 38e: ifnull 399
         // 391: aload 1
         // 392: checkcast java/util/List
         // 395: astore 1
         // 396: goto 39d
         // 399: invokestatic kotlin/collections/CollectionsKt.emptyList ()Ljava/util/List;
         // 39c: astore 1
         // 39d: new io/sentry/android/replay/LastSegmentData
         // 3a0: dup
         // 3a1: aload 14
         // 3a3: aload 3
         // 3a4: aload 11
         // 3a6: iload 4
         // 3a8: lload 5
         // 3aa: lload 7
         // 3ac: lsub
         // 3ad: lload 9
         // 3af: ladd
         // 3b0: aload 15
         // 3b2: aload 19
         // 3b4: ldc_w "replay.screen-at-start"
         // 3b7: invokevirtual java/util/LinkedHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
         // 3ba: checkcast java/lang/String
         // 3bd: aload 1
         // 3be: checkcast java/lang/Iterable
         // 3c1: new io/sentry/android/replay/ReplayCache$Companion$fromDisk$$inlined$sortedBy$1
         // 3c4: dup
         // 3c5: invokespecial io/sentry/android/replay/ReplayCache$Companion$fromDisk$$inlined$sortedBy$1.<init> ()V
         // 3c8: checkcast java/util/Comparator
         // 3cb: invokestatic kotlin/collections/CollectionsKt.sortedWith (Ljava/lang/Iterable;Ljava/util/Comparator;)Ljava/util/List;
         // 3ce: invokespecial io/sentry/android/replay/LastSegmentData.<init> (Lio/sentry/android/replay/ScreenshotRecorderConfig;Lio/sentry/android/replay/ReplayCache;Ljava/util/Date;IJLio/sentry/SentryReplayEvent$ReplayType;Ljava/lang/String;Ljava/util/List;)V
         // 3d1: areturn
         // 3d2: aload 1
         // 3d3: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
         // 3d6: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
         // 3d9: ldc_w "Incorrect segment values found for replay: %s, deleting the replay"
         // 3dc: bipush 1
         // 3dd: anewarray 4
         // 3e0: dup
         // 3e1: bipush 0
         // 3e2: aload 2
         // 3e3: aastore
         // 3e4: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
         // 3e9: aload 20
         // 3eb: invokestatic io/sentry/util/FileUtils.deleteRecursively (Ljava/io/File;)Z
         // 3ee: pop
         // 3ef: aconst_null
         // 3f0: areturn
         // 3f1: astore 1
         // 3f2: aload 1
         // 3f3: athrow
         // 3f4: astore 2
         // 3f5: aload 11
         // 3f7: aload 1
         // 3f8: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
         // 3fb: aload 2
         // 3fc: athrow
      }

      public fun makeReplayCacheDir(options: SentryOptions, replayId: SentryId): File? {
         val var3: java.lang.CharSequence = var1.getCacheDirPath();
         val var4: File;
         if (var3 != null && var3.length() != 0) {
            val var6: java.lang.String = var1.getCacheDirPath();
            val var5: StringBuilder = new StringBuilder("replay_");
            var5.append(var2);
            var4 = new File(var6, var5.toString());
            var4.mkdirs();
         } else {
            var1.getLogger().log(SentryLevel.WARNING, "SentryOptions.cacheDirPath is not set, session replay is no-op");
            var4 = null;
         }

         return var4;
      }
   }
}
