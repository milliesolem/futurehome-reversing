package io.sentry.android.core.internal.util;

import android.app.Activity;
import io.sentry.ILogger;
import io.sentry.android.core.BuildInfoProvider;
import io.sentry.util.thread.IMainThreadChecker;

public class ScreenshotUtils {
   private static final long CAPTURE_TIMEOUT_MS = 1000L;

   private static boolean isActivityValid(Activity var0) {
      boolean var1;
      if (!var0.isFinishing() && !var0.isDestroyed()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static byte[] takeScreenshot(Activity var0, ILogger var1, BuildInfoProvider var2) {
      return takeScreenshot(var0, AndroidMainThreadChecker.getInstance(), var1, var2);
   }

   public static byte[] takeScreenshot(Activity param0, IMainThreadChecker param1, ILogger param2, BuildInfoProvider param3) {
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
      // 000: aload 0
      // 001: invokestatic io/sentry/android/core/internal/util/ScreenshotUtils.isActivityValid (Landroid/app/Activity;)Z
      // 004: ifne 018
      // 007: aload 2
      // 008: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 00b: ldc "Activity isn't valid, not taking screenshot."
      // 00d: bipush 0
      // 00e: anewarray 4
      // 011: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 016: aconst_null
      // 017: areturn
      // 018: aload 0
      // 019: invokevirtual android/app/Activity.getWindow ()Landroid/view/Window;
      // 01c: astore 9
      // 01e: aload 9
      // 020: ifnonnull 034
      // 023: aload 2
      // 024: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 027: ldc "Activity window is null, not taking screenshot."
      // 029: bipush 0
      // 02a: anewarray 4
      // 02d: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 032: aconst_null
      // 033: areturn
      // 034: aload 9
      // 036: invokevirtual android/view/Window.peekDecorView ()Landroid/view/View;
      // 039: astore 6
      // 03b: aload 6
      // 03d: ifnonnull 051
      // 040: aload 2
      // 041: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 044: ldc "DecorView is null, not taking screenshot."
      // 046: bipush 0
      // 047: anewarray 4
      // 04a: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 04f: aconst_null
      // 050: areturn
      // 051: aload 6
      // 053: invokevirtual android/view/View.getRootView ()Landroid/view/View;
      // 056: astore 10
      // 058: aload 10
      // 05a: ifnonnull 06e
      // 05d: aload 2
      // 05e: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 061: ldc "Root view is null, not taking screenshot."
      // 063: bipush 0
      // 064: anewarray 4
      // 067: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 06c: aconst_null
      // 06d: areturn
      // 06e: aload 10
      // 070: invokevirtual android/view/View.getWidth ()I
      // 073: ifle 1e3
      // 076: aload 10
      // 078: invokevirtual android/view/View.getHeight ()I
      // 07b: ifgt 081
      // 07e: goto 1e3
      // 081: new java/io/ByteArrayOutputStream
      // 084: astore 6
      // 086: aload 6
      // 088: invokespecial java/io/ByteArrayOutputStream.<init> ()V
      // 08b: aload 10
      // 08d: invokevirtual android/view/View.getWidth ()I
      // 090: aload 10
      // 092: invokevirtual android/view/View.getHeight ()I
      // 095: getstatic android/graphics/Bitmap$Config.ARGB_8888 Landroid/graphics/Bitmap$Config;
      // 098: invokestatic android/graphics/Bitmap.createBitmap (IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;
      // 09b: astore 7
      // 09d: new java/util/concurrent/CountDownLatch
      // 0a0: astore 8
      // 0a2: bipush 1
      // 0a3: istore 4
      // 0a5: aload 8
      // 0a7: bipush 1
      // 0a8: invokespecial java/util/concurrent/CountDownLatch.<init> (I)V
      // 0ab: aload 3
      // 0ac: invokevirtual io/sentry/android/core/BuildInfoProvider.getSdkInfoVersion ()I
      // 0af: bipush 26
      // 0b1: if_icmplt 13f
      // 0b4: new android/os/HandlerThread
      // 0b7: astore 0
      // 0b8: aload 0
      // 0b9: ldc "SentryScreenshot"
      // 0bb: invokespecial android/os/HandlerThread.<init> (Ljava/lang/String;)V
      // 0be: aload 0
      // 0bf: invokevirtual android/os/HandlerThread.start ()V
      // 0c2: new android/os/Handler
      // 0c5: astore 3
      // 0c6: aload 3
      // 0c7: aload 0
      // 0c8: invokevirtual android/os/HandlerThread.getLooper ()Landroid/os/Looper;
      // 0cb: invokespecial android/os/Handler.<init> (Landroid/os/Looper;)V
      // 0ce: new java/util/concurrent/atomic/AtomicBoolean
      // 0d1: astore 10
      // 0d3: aload 10
      // 0d5: bipush 0
      // 0d6: invokespecial java/util/concurrent/atomic/AtomicBoolean.<init> (Z)V
      // 0d9: new io/sentry/android/core/internal/util/ScreenshotUtils$$ExternalSyntheticLambda1
      // 0dc: astore 1
      // 0dd: aload 1
      // 0de: aload 10
      // 0e0: aload 8
      // 0e2: invokespecial io/sentry/android/core/internal/util/ScreenshotUtils$$ExternalSyntheticLambda1.<init> (Ljava/util/concurrent/atomic/AtomicBoolean;Ljava/util/concurrent/CountDownLatch;)V
      // 0e5: aload 9
      // 0e7: aload 7
      // 0e9: aload 1
      // 0ea: aload 3
      // 0eb: invokestatic io/flutter/view/AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m (Landroid/view/Window;Landroid/graphics/Bitmap;Landroid/view/PixelCopy$OnPixelCopyFinishedListener;Landroid/os/Handler;)V
      // 0ee: aload 8
      // 0f0: ldc2_w 1000
      // 0f3: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 0f6: invokevirtual java/util/concurrent/CountDownLatch.await (JLjava/util/concurrent/TimeUnit;)Z
      // 0f9: ifeq 10b
      // 0fc: aload 10
      // 0fe: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 101: istore 5
      // 103: iload 5
      // 105: ifeq 10b
      // 108: goto 10e
      // 10b: bipush 0
      // 10c: istore 4
      // 10e: aload 0
      // 10f: invokevirtual android/os/HandlerThread.quit ()Z
      // 112: pop
      // 113: goto 12b
      // 116: astore 1
      // 117: aload 2
      // 118: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 11b: ldc "Taking screenshot using PixelCopy failed."
      // 11d: aload 1
      // 11e: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 123: aload 0
      // 124: invokevirtual android/os/HandlerThread.quit ()Z
      // 127: pop
      // 128: bipush 0
      // 129: istore 4
      // 12b: iload 4
      // 12d: ifne 18c
      // 130: aload 6
      // 132: invokevirtual java/io/ByteArrayOutputStream.close ()V
      // 135: aconst_null
      // 136: areturn
      // 137: astore 1
      // 138: aload 0
      // 139: invokevirtual android/os/HandlerThread.quit ()Z
      // 13c: pop
      // 13d: aload 1
      // 13e: athrow
      // 13f: new android/graphics/Canvas
      // 142: astore 3
      // 143: aload 3
      // 144: aload 7
      // 146: invokespecial android/graphics/Canvas.<init> (Landroid/graphics/Bitmap;)V
      // 149: aload 1
      // 14a: invokeinterface io/sentry/util/thread/IMainThreadChecker.isMainThread ()Z 1
      // 14f: ifeq 160
      // 152: aload 10
      // 154: aload 3
      // 155: invokevirtual android/view/View.draw (Landroid/graphics/Canvas;)V
      // 158: aload 8
      // 15a: invokevirtual java/util/concurrent/CountDownLatch.countDown ()V
      // 15d: goto 173
      // 160: new io/sentry/android/core/internal/util/ScreenshotUtils$$ExternalSyntheticLambda2
      // 163: astore 1
      // 164: aload 1
      // 165: aload 10
      // 167: aload 3
      // 168: aload 2
      // 169: aload 8
      // 16b: invokespecial io/sentry/android/core/internal/util/ScreenshotUtils$$ExternalSyntheticLambda2.<init> (Landroid/view/View;Landroid/graphics/Canvas;Lio/sentry/ILogger;Ljava/util/concurrent/CountDownLatch;)V
      // 16e: aload 0
      // 16f: aload 1
      // 170: invokevirtual android/app/Activity.runOnUiThread (Ljava/lang/Runnable;)V
      // 173: aload 8
      // 175: ldc2_w 1000
      // 178: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 17b: invokevirtual java/util/concurrent/CountDownLatch.await (JLjava/util/concurrent/TimeUnit;)Z
      // 17e: istore 5
      // 180: iload 5
      // 182: ifne 18c
      // 185: aload 6
      // 187: invokevirtual java/io/ByteArrayOutputStream.close ()V
      // 18a: aconst_null
      // 18b: areturn
      // 18c: aload 7
      // 18e: getstatic android/graphics/Bitmap$CompressFormat.PNG Landroid/graphics/Bitmap$CompressFormat;
      // 191: bipush 0
      // 192: aload 6
      // 194: invokevirtual android/graphics/Bitmap.compress (Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
      // 197: pop
      // 198: aload 6
      // 19a: invokevirtual java/io/ByteArrayOutputStream.size ()I
      // 19d: ifgt 1b6
      // 1a0: aload 2
      // 1a1: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 1a4: ldc "Screenshot is 0 bytes, not attaching the image."
      // 1a6: bipush 0
      // 1a7: anewarray 4
      // 1aa: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 1af: aload 6
      // 1b1: invokevirtual java/io/ByteArrayOutputStream.close ()V
      // 1b4: aconst_null
      // 1b5: areturn
      // 1b6: aload 6
      // 1b8: invokevirtual java/io/ByteArrayOutputStream.toByteArray ()[B
      // 1bb: astore 0
      // 1bc: aload 6
      // 1be: invokevirtual java/io/ByteArrayOutputStream.close ()V
      // 1c1: aload 0
      // 1c2: areturn
      // 1c3: astore 0
      // 1c4: aload 6
      // 1c6: invokevirtual java/io/ByteArrayOutputStream.close ()V
      // 1c9: goto 1d2
      // 1cc: astore 1
      // 1cd: aload 0
      // 1ce: aload 1
      // 1cf: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 1d2: aload 0
      // 1d3: athrow
      // 1d4: astore 0
      // 1d5: aload 2
      // 1d6: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 1d9: ldc "Taking screenshot failed."
      // 1db: aload 0
      // 1dc: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 1e1: aconst_null
      // 1e2: areturn
      // 1e3: aload 2
      // 1e4: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 1e7: ldc "View's width and height is zeroed, not taking screenshot."
      // 1e9: bipush 0
      // 1ea: anewarray 4
      // 1ed: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 1f2: aconst_null
      // 1f3: areturn
   }
}
