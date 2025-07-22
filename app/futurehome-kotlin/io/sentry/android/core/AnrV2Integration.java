package io.sentry.android.core;

import android.app.ActivityManager;
import android.app.ApplicationExitInfo;
import android.content.Context;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import io.sentry.Attachment;
import io.sentry.DateUtils;
import io.sentry.Hint;
import io.sentry.IHub;
import io.sentry.ILogger;
import io.sentry.ISentryExecutorService;
import io.sentry.Integration;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.android.core.cache.AndroidEnvelopeCache;
import io.sentry.cache.EnvelopeCache;
import io.sentry.cache.IEnvelopeCache;
import io.sentry.hints.AbnormalExit;
import io.sentry.hints.Backfillable;
import io.sentry.hints.BlockingFlushHint;
import io.sentry.protocol.Message;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentryThread;
import io.sentry.transport.CurrentDateProvider;
import io.sentry.transport.ICurrentDateProvider;
import io.sentry.util.HintUtils;
import io.sentry.util.IntegrationUtils;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AnrV2Integration implements Integration, Closeable {
   static final long NINETY_DAYS_THRESHOLD = TimeUnit.DAYS.toMillis(91L);
   private final Context context;
   private final ICurrentDateProvider dateProvider;
   private SentryAndroidOptions options;

   public AnrV2Integration(Context var1) {
      this(var1, CurrentDateProvider.getInstance());
   }

   AnrV2Integration(Context var1, ICurrentDateProvider var2) {
      this.context = ContextUtils.getApplicationContext(var1);
      this.dateProvider = var2;
   }

   @Override
   public void close() throws IOException {
      SentryAndroidOptions var1 = this.options;
      if (var1 != null) {
         var1.getLogger().log(SentryLevel.DEBUG, "AnrV2Integration removed.");
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void register(IHub var1, SentryOptions var2) {
      SentryAndroidOptions var3;
      if (var2 instanceof SentryAndroidOptions) {
         var3 = (SentryAndroidOptions)var2;
      } else {
         var3 = null;
      }

      var3 = Objects.requireNonNull(var3, "SentryAndroidOptions is required");
      this.options = var3;
      var3.getLogger().log(SentryLevel.DEBUG, "AnrIntegration enabled: %s", this.options.isAnrEnabled());
      if (this.options.getCacheDirPath() == null) {
         this.options.getLogger().log(SentryLevel.INFO, "Cache dir is not set, unable to process ANRs");
      } else {
         if (this.options.isAnrEnabled()) {
            label34:
            try {
               ISentryExecutorService var4 = var2.getExecutorService();
               AnrV2Integration.AnrProcessor var8 = new AnrV2Integration.AnrProcessor(this.context, var1, this.options, this.dateProvider);
               var4.submit(var8);
            } catch (Throwable var6) {
               var2.getLogger().log(SentryLevel.DEBUG, "Failed to start AnrProcessor.", var6);
               break label34;
            }

            var2.getLogger().log(SentryLevel.DEBUG, "AnrV2Integration installed.");
            IntegrationUtils.addIntegrationToSdkVersion("AnrV2");
         }
      }
   }

   static class AnrProcessor implements Runnable {
      private final Context context;
      private final IHub hub;
      private final SentryAndroidOptions options;
      private final long threshold;

      AnrProcessor(Context var1, IHub var2, SentryAndroidOptions var3, ICurrentDateProvider var4) {
         this.context = var1;
         this.hub = var2;
         this.options = var3;
         this.threshold = var4.getCurrentTimeMillis() - AnrV2Integration.NINETY_DAYS_THRESHOLD;
      }

      private byte[] getDumpBytes(InputStream param1) throws IOException {
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
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: new java/io/ByteArrayOutputStream
         // 03: dup
         // 04: invokespecial java/io/ByteArrayOutputStream.<init> ()V
         // 07: astore 3
         // 08: sipush 1024
         // 0b: newarray 8
         // 0d: astore 4
         // 0f: aload 1
         // 10: aload 4
         // 12: bipush 0
         // 13: sipush 1024
         // 16: invokevirtual java/io/InputStream.read ([BII)I
         // 19: istore 2
         // 1a: iload 2
         // 1b: bipush -1
         // 1c: if_icmpeq 2a
         // 1f: aload 3
         // 20: aload 4
         // 22: bipush 0
         // 23: iload 2
         // 24: invokevirtual java/io/ByteArrayOutputStream.write ([BII)V
         // 27: goto 0f
         // 2a: aload 3
         // 2b: invokevirtual java/io/ByteArrayOutputStream.toByteArray ()[B
         // 2e: astore 1
         // 2f: aload 3
         // 30: invokevirtual java/io/ByteArrayOutputStream.close ()V
         // 33: aload 1
         // 34: areturn
         // 35: astore 1
         // 36: aload 3
         // 37: invokevirtual java/io/ByteArrayOutputStream.close ()V
         // 3a: goto 43
         // 3d: astore 3
         // 3e: aload 1
         // 3f: aload 3
         // 40: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
         // 43: aload 1
         // 44: athrow
      }

      private AnrV2Integration.ParseResult parseThreadDump(ApplicationExitInfo param1, boolean param2) {
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
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 000: aload 1
         // 001: invokestatic io/flutter/view/AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m (Landroid/app/ApplicationExitInfo;)Ljava/io/InputStream;
         // 004: astore 3
         // 005: aload 3
         // 006: ifnonnull 01e
         // 009: new io/sentry/android/core/AnrV2Integration$ParseResult
         // 00c: astore 1
         // 00d: aload 1
         // 00e: getstatic io/sentry/android/core/AnrV2Integration$ParseResult$Type.NO_DUMP Lio/sentry/android/core/AnrV2Integration$ParseResult$Type;
         // 011: invokespecial io/sentry/android/core/AnrV2Integration$ParseResult.<init> (Lio/sentry/android/core/AnrV2Integration$ParseResult$Type;)V
         // 014: aload 3
         // 015: ifnull 01c
         // 018: aload 3
         // 019: invokevirtual java/io/InputStream.close ()V
         // 01c: aload 1
         // 01d: areturn
         // 01e: aload 0
         // 01f: aload 3
         // 020: invokespecial io/sentry/android/core/AnrV2Integration$AnrProcessor.getDumpBytes (Ljava/io/InputStream;)[B
         // 023: astore 1
         // 024: aload 3
         // 025: ifnull 02c
         // 028: aload 3
         // 029: invokevirtual java/io/InputStream.close ()V
         // 02c: new java/io/BufferedReader
         // 02f: astore 3
         // 030: new java/io/InputStreamReader
         // 033: astore 5
         // 035: new java/io/ByteArrayInputStream
         // 038: astore 4
         // 03a: aload 4
         // 03c: aload 1
         // 03d: invokespecial java/io/ByteArrayInputStream.<init> ([B)V
         // 040: aload 5
         // 042: aload 4
         // 044: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;)V
         // 047: aload 3
         // 048: aload 5
         // 04a: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
         // 04d: aload 3
         // 04e: invokestatic io/sentry/android/core/internal/threaddump/Lines.readLines (Ljava/io/BufferedReader;)Lio/sentry/android/core/internal/threaddump/Lines;
         // 051: astore 5
         // 053: new io/sentry/android/core/internal/threaddump/ThreadDumpParser
         // 056: astore 4
         // 058: aload 4
         // 05a: aload 0
         // 05b: getfield io/sentry/android/core/AnrV2Integration$AnrProcessor.options Lio/sentry/android/core/SentryAndroidOptions;
         // 05e: iload 2
         // 05f: invokespecial io/sentry/android/core/internal/threaddump/ThreadDumpParser.<init> (Lio/sentry/SentryOptions;Z)V
         // 062: aload 4
         // 064: aload 5
         // 066: invokevirtual io/sentry/android/core/internal/threaddump/ThreadDumpParser.parse (Lio/sentry/android/core/internal/threaddump/Lines;)Ljava/util/List;
         // 069: astore 4
         // 06b: aload 4
         // 06d: invokeinterface java/util/List.isEmpty ()Z 1
         // 072: ifeq 089
         // 075: new io/sentry/android/core/AnrV2Integration$ParseResult
         // 078: astore 4
         // 07a: aload 4
         // 07c: getstatic io/sentry/android/core/AnrV2Integration$ParseResult$Type.NO_DUMP Lio/sentry/android/core/AnrV2Integration$ParseResult$Type;
         // 07f: invokespecial io/sentry/android/core/AnrV2Integration$ParseResult.<init> (Lio/sentry/android/core/AnrV2Integration$ParseResult$Type;)V
         // 082: aload 3
         // 083: invokevirtual java/io/BufferedReader.close ()V
         // 086: aload 4
         // 088: areturn
         // 089: new io/sentry/android/core/AnrV2Integration$ParseResult
         // 08c: dup
         // 08d: getstatic io/sentry/android/core/AnrV2Integration$ParseResult$Type.DUMP Lio/sentry/android/core/AnrV2Integration$ParseResult$Type;
         // 090: aload 1
         // 091: aload 4
         // 093: invokespecial io/sentry/android/core/AnrV2Integration$ParseResult.<init> (Lio/sentry/android/core/AnrV2Integration$ParseResult$Type;[BLjava/util/List;)V
         // 096: astore 4
         // 098: aload 3
         // 099: invokevirtual java/io/BufferedReader.close ()V
         // 09c: aload 4
         // 09e: areturn
         // 09f: astore 4
         // 0a1: aload 3
         // 0a2: invokevirtual java/io/BufferedReader.close ()V
         // 0a5: goto 0af
         // 0a8: astore 3
         // 0a9: aload 4
         // 0ab: aload 3
         // 0ac: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
         // 0af: aload 4
         // 0b1: athrow
         // 0b2: astore 3
         // 0b3: aload 0
         // 0b4: getfield io/sentry/android/core/AnrV2Integration$AnrProcessor.options Lio/sentry/android/core/SentryAndroidOptions;
         // 0b7: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
         // 0ba: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
         // 0bd: ldc "Failed to parse ANR thread dump"
         // 0bf: aload 3
         // 0c0: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
         // 0c5: new io/sentry/android/core/AnrV2Integration$ParseResult
         // 0c8: dup
         // 0c9: getstatic io/sentry/android/core/AnrV2Integration$ParseResult$Type.ERROR Lio/sentry/android/core/AnrV2Integration$ParseResult$Type;
         // 0cc: aload 1
         // 0cd: invokespecial io/sentry/android/core/AnrV2Integration$ParseResult.<init> (Lio/sentry/android/core/AnrV2Integration$ParseResult$Type;[B)V
         // 0d0: areturn
         // 0d1: astore 1
         // 0d2: aload 3
         // 0d3: ifnull 0e3
         // 0d6: aload 3
         // 0d7: invokevirtual java/io/InputStream.close ()V
         // 0da: goto 0e3
         // 0dd: astore 3
         // 0de: aload 1
         // 0df: aload 3
         // 0e0: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
         // 0e3: aload 1
         // 0e4: athrow
         // 0e5: astore 1
         // 0e6: aload 0
         // 0e7: getfield io/sentry/android/core/AnrV2Integration$AnrProcessor.options Lio/sentry/android/core/SentryAndroidOptions;
         // 0ea: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
         // 0ed: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
         // 0f0: ldc "Failed to read ANR thread dump"
         // 0f2: aload 1
         // 0f3: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
         // 0f8: new io/sentry/android/core/AnrV2Integration$ParseResult
         // 0fb: dup
         // 0fc: getstatic io/sentry/android/core/AnrV2Integration$ParseResult$Type.NO_DUMP Lio/sentry/android/core/AnrV2Integration$ParseResult$Type;
         // 0ff: invokespecial io/sentry/android/core/AnrV2Integration$ParseResult.<init> (Lio/sentry/android/core/AnrV2Integration$ParseResult$Type;)V
         // 102: areturn
      }

      private void reportAsSentryEvent(ApplicationExitInfo var1, boolean var2) {
         long var4 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1);
         boolean var3;
         if (AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1) != 100) {
            var3 = true;
         } else {
            var3 = false;
         }

         AnrV2Integration.ParseResult var6 = this.parseThreadDump(var1, var3);
         if (var6.type == AnrV2Integration.ParseResult.Type.NO_DUMP) {
            this.options
               .getLogger()
               .log(
                  SentryLevel.WARNING,
                  "Not reporting ANR event as there was no thread dump for the ANR %s",
                  AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1)
               );
         } else {
            AnrV2Integration.AnrV2Hint var7 = new AnrV2Integration.AnrV2Hint(this.options.getFlushTimeoutMillis(), this.options.getLogger(), var4, var2, var3);
            Hint var8 = HintUtils.createWithTypeCheckHint(var7);
            SentryEvent var10 = new SentryEvent();
            if (var6.type == AnrV2Integration.ParseResult.Type.ERROR) {
               Message var9 = new Message();
               var9.setFormatted(
                  "Sentry Android SDK failed to parse system thread dump for this ANR. We recommend enabling [SentryOptions.isAttachAnrThreadDump] option to attach the thread dump as plain text and report this issue on GitHub."
               );
               var10.setMessage(var9);
            } else if (var6.type == AnrV2Integration.ParseResult.Type.DUMP) {
               var10.setThreads(var6.threads);
            }

            var10.setLevel(SentryLevel.FATAL);
            var10.setTimestamp(DateUtils.getDateTime(var4));
            if (this.options.isAttachAnrThreadDump() && var6.dump != null) {
               var8.setThreadDump(Attachment.fromThreadDump(var6.dump));
            }

            if (!this.hub.captureEvent(var10, var8).equals(SentryId.EMPTY_ID) && !var7.waitFlush()) {
               this.options.getLogger().log(SentryLevel.WARNING, "Timed out waiting to flush ANR event to disk. Event: %s", var10.getEventId());
            }
         }
      }

      private void reportNonEnrichedHistoricalAnrs(List<ApplicationExitInfo> var1, Long var2) {
         Collections.reverse(var1);
         Iterator var4 = var1.iterator();

         while (var4.hasNext()) {
            ApplicationExitInfo var3 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var4.next());
            if (AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$1(var3) == 6) {
               if (AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var3) < this.threshold) {
                  this.options.getLogger().log(SentryLevel.DEBUG, "ANR happened too long ago %s.", var3);
               } else if (var2 != null && AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var3) <= var2) {
                  this.options.getLogger().log(SentryLevel.DEBUG, "ANR has already been reported %s.", var3);
               } else {
                  this.reportAsSentryEvent(var3, false);
               }
            }
         }
      }

      @Override
      public void run() {
         ActivityManager var1 = (ActivityManager)this.context.getSystemService("activity");
         Object var2 = null;
         List var6 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1, null, 0, 0);
         if (var6.size() == 0) {
            this.options.getLogger().log(SentryLevel.DEBUG, "No records in historical exit reasons.");
         } else {
            IEnvelopeCache var3 = this.options.getEnvelopeDiskCache();
            if (var3 instanceof EnvelopeCache && this.options.isEnableAutoSessionTracking()) {
               EnvelopeCache var8 = (EnvelopeCache)var3;
               if (!var8.waitPreviousSessionFlush()) {
                  this.options.getLogger().log(SentryLevel.WARNING, "Timed out waiting to flush previous session to its own file.");
                  var8.flushPreviousSession();
               }
            }

            ArrayList var9 = new ArrayList(var6);
            Long var4 = AndroidEnvelopeCache.lastReportedAnr(this.options);
            Iterator var5 = var9.iterator();

            while (true) {
               var7 = (ApplicationExitInfo)var2;
               if (!var5.hasNext()) {
                  break;
               }

               var7 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var5.next());
               if (AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$1(var7) == 6) {
                  var9.remove(var7);
                  break;
               }
            }

            if (var7 == null) {
               this.options.getLogger().log(SentryLevel.DEBUG, "No ANRs have been found in the historical exit reasons list.");
            } else if (AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var7) < this.threshold) {
               this.options.getLogger().log(SentryLevel.DEBUG, "Latest ANR happened too long ago, returning early.");
            } else if (var4 != null && AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var7) <= var4) {
               this.options.getLogger().log(SentryLevel.DEBUG, "Latest ANR has already been reported, returning early.");
            } else {
               if (this.options.isReportHistoricalAnrs()) {
                  this.reportNonEnrichedHistoricalAnrs(var9, var4);
               }

               this.reportAsSentryEvent(var7, true);
            }
         }
      }
   }

   public static final class AnrV2Hint extends BlockingFlushHint implements Backfillable, AbnormalExit {
      private final boolean isBackgroundAnr;
      private final boolean shouldEnrich;
      private final long timestamp;

      public AnrV2Hint(long var1, ILogger var3, long var4, boolean var6, boolean var7) {
         super(var1, var3);
         this.timestamp = var4;
         this.shouldEnrich = var6;
         this.isBackgroundAnr = var7;
      }

      @Override
      public boolean ignoreCurrentThread() {
         return false;
      }

      @Override
      public boolean isFlushable(SentryId var1) {
         return true;
      }

      @Override
      public String mechanism() {
         String var1;
         if (this.isBackgroundAnr) {
            var1 = "anr_background";
         } else {
            var1 = "anr_foreground";
         }

         return var1;
      }

      @Override
      public void setFlushable(SentryId var1) {
      }

      @Override
      public boolean shouldEnrich() {
         return this.shouldEnrich;
      }

      @Override
      public Long timestamp() {
         return this.timestamp;
      }
   }

   static final class ParseResult {
      final byte[] dump;
      final List<SentryThread> threads;
      final AnrV2Integration.ParseResult.Type type;

      ParseResult(AnrV2Integration.ParseResult.Type var1) {
         this.type = var1;
         this.dump = null;
         this.threads = null;
      }

      ParseResult(AnrV2Integration.ParseResult.Type var1, byte[] var2) {
         this.type = var1;
         this.dump = var2;
         this.threads = null;
      }

      ParseResult(AnrV2Integration.ParseResult.Type var1, byte[] var2, List<SentryThread> var3) {
         this.type = var1;
         this.dump = var2;
         this.threads = var3;
      }

      static enum Type {
         DUMP,
         ERROR,
         NO_DUMP;
         private static final AnrV2Integration.ParseResult.Type[] $VALUES = $values();
      }
   }
}
