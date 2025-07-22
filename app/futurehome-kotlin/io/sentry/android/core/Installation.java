package io.sentry.android.core;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.UUID;

final class Installation {
   static final String INSTALLATION = "INSTALLATION";
   private static final Charset UTF_8 = Charset.forName("UTF-8");
   static String deviceId;

   private Installation() {
   }

   public static String id(Context param0) throws RuntimeException {
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
      // 00: ldc io/sentry/android/core/Installation
      // 02: monitorenter
      // 03: getstatic io/sentry/android/core/Installation.deviceId Ljava/lang/String;
      // 06: ifnonnull 42
      // 09: new java/io/File
      // 0c: astore 1
      // 0d: aload 1
      // 0e: aload 0
      // 0f: invokevirtual android/content/Context.getFilesDir ()Ljava/io/File;
      // 12: ldc "INSTALLATION"
      // 14: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 17: aload 1
      // 18: invokevirtual java/io/File.exists ()Z
      // 1b: ifne 2c
      // 1e: aload 1
      // 1f: invokestatic io/sentry/android/core/Installation.writeInstallationFile (Ljava/io/File;)Ljava/lang/String;
      // 22: astore 0
      // 23: aload 0
      // 24: putstatic io/sentry/android/core/Installation.deviceId Ljava/lang/String;
      // 27: ldc io/sentry/android/core/Installation
      // 29: monitorexit
      // 2a: aload 0
      // 2b: areturn
      // 2c: aload 1
      // 2d: invokestatic io/sentry/android/core/Installation.readInstallationFile (Ljava/io/File;)Ljava/lang/String;
      // 30: putstatic io/sentry/android/core/Installation.deviceId Ljava/lang/String;
      // 33: goto 42
      // 36: astore 0
      // 37: new java/lang/RuntimeException
      // 3a: astore 1
      // 3b: aload 1
      // 3c: aload 0
      // 3d: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/Throwable;)V
      // 40: aload 1
      // 41: athrow
      // 42: getstatic io/sentry/android/core/Installation.deviceId Ljava/lang/String;
      // 45: astore 0
      // 46: ldc io/sentry/android/core/Installation
      // 48: monitorexit
      // 49: aload 0
      // 4a: areturn
      // 4b: astore 0
      // 4c: ldc io/sentry/android/core/Installation
      // 4e: monitorexit
      // 4f: aload 0
      // 50: athrow
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static String readInstallationFile(File var0) throws IOException {
      RandomAccessFile var8 = new RandomAccessFile(var0, "r");

      String var9;
      try {
         byte[] var1 = new byte[(int)var8.length()];
         var8.readFully(var1);
         var9 = new String(var1, UTF_8);
      } catch (Throwable var7) {
         try {
            var8.close();
         } catch (Throwable var6) {
            var7.addSuppressed(var6);
            throw var7;
         }

         throw var7;
      }

      var8.close();
      return var9;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static String writeInstallationFile(File var0) throws IOException {
      FileOutputStream var8 = new FileOutputStream(var0);

      String var1;
      try {
         var1 = UUID.randomUUID().toString();
         var8.write(var1.getBytes(UTF_8));
         var8.flush();
      } catch (Throwable var7) {
         try {
            var8.close();
         } catch (Throwable var6) {
            var7.addSuppressed(var6);
            throw var7;
         }

         throw var7;
      }

      var8.close();
      return var1;
   }
}
