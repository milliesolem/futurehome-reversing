package io.sentry.android.core.internal.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.android.core.BuildInfoProvider;
import io.sentry.util.Objects;
import java.io.File;
import java.nio.charset.Charset;

public final class RootChecker {
   private static final Charset UTF_8 = Charset.forName("UTF-8");
   private final BuildInfoProvider buildInfoProvider;
   private final Context context;
   private final ILogger logger;
   private final String[] rootFiles;
   private final String[] rootPackages;
   private final Runtime runtime;

   public RootChecker(Context var1, BuildInfoProvider var2, ILogger var3) {
      Runtime var4 = Runtime.getRuntime();
      this(
         var1,
         var2,
         var3,
         new String[]{
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin/su",
            "/su/bin",
            "/system/xbin/daemonsu"
         },
         new String[]{
            "com.devadvance.rootcloak",
            "com.devadvance.rootcloakplus",
            "com.koushikdutta.superuser",
            "com.thirdparty.superuser",
            "eu.chainfire.supersu",
            "com.noshufou.android.su"
         },
         var4
      );
   }

   RootChecker(Context var1, BuildInfoProvider var2, ILogger var3, String[] var4, String[] var5, Runtime var6) {
      this.context = Objects.requireNonNull(var1, "The application context is required.");
      this.buildInfoProvider = Objects.requireNonNull(var2, "The BuildInfoProvider is required.");
      this.logger = Objects.requireNonNull(var3, "The Logger is required.");
      this.rootFiles = Objects.requireNonNull(var4, "The root Files are required.");
      this.rootPackages = Objects.requireNonNull(var5, "The root packages are required.");
      this.runtime = Objects.requireNonNull(var6, "The Runtime is required.");
   }

   private boolean checkRootFiles() {
      for (String var5 : this.rootFiles) {
         boolean var3;
         try {
            File var6 = new File(var5);
            var3 = var6.exists();
         } catch (RuntimeException var7) {
            this.logger.log(SentryLevel.ERROR, var7, "Error when trying to check if root file %s exists.", var5);
            continue;
         }

         if (var3) {
            return true;
         }
      }

      return false;
   }

   private boolean checkRootPackages(ILogger var1) {
      BuildInfoProvider var4 = new BuildInfoProvider(var1);
      PackageManager var8 = this.context.getPackageManager();
      if (var8 != null) {
         for (String var6 : this.rootPackages) {
            try {
               if (var4.getSdkInfoVersion() >= 33) {
                  AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var8, var6, AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(0L));
               } else {
                  var8.getPackageInfo(var6, 0);
               }

               return true;
            } catch (NameNotFoundException var7) {
            }
         }
      }

      return false;
   }

   private boolean checkSUExist() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aconst_null
      // 01: astore 3
      // 02: aconst_null
      // 03: astore 2
      // 04: aload 0
      // 05: getfield io/sentry/android/core/internal/util/RootChecker.runtime Ljava/lang/Runtime;
      // 08: bipush 2
      // 09: anewarray 40
      // 0c: dup
      // 0d: bipush 0
      // 0e: ldc "/system/xbin/which"
      // 10: aastore
      // 11: dup
      // 12: bipush 1
      // 13: ldc "su"
      // 15: aastore
      // 16: invokevirtual java/lang/Runtime.exec ([Ljava/lang/String;)Ljava/lang/Process;
      // 19: astore 4
      // 1b: aload 4
      // 1d: astore 2
      // 1e: aload 4
      // 20: astore 3
      // 21: new java/io/BufferedReader
      // 24: astore 5
      // 26: aload 4
      // 28: astore 2
      // 29: aload 4
      // 2b: astore 3
      // 2c: new java/io/InputStreamReader
      // 2f: astore 6
      // 31: aload 4
      // 33: astore 2
      // 34: aload 4
      // 36: astore 3
      // 37: aload 6
      // 39: aload 4
      // 3b: invokevirtual java/lang/Process.getInputStream ()Ljava/io/InputStream;
      // 3e: getstatic io/sentry/android/core/internal/util/RootChecker.UTF_8 Ljava/nio/charset/Charset;
      // 41: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
      // 44: aload 4
      // 46: astore 2
      // 47: aload 4
      // 49: astore 3
      // 4a: aload 5
      // 4c: aload 6
      // 4e: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 51: aload 5
      // 53: invokevirtual java/io/BufferedReader.readLine ()Ljava/lang/String;
      // 56: astore 2
      // 57: aload 2
      // 58: ifnull 60
      // 5b: bipush 1
      // 5c: istore 1
      // 5d: goto 62
      // 60: bipush 0
      // 61: istore 1
      // 62: aload 4
      // 64: astore 2
      // 65: aload 4
      // 67: astore 3
      // 68: aload 5
      // 6a: invokevirtual java/io/BufferedReader.close ()V
      // 6d: aload 4
      // 6f: ifnull 77
      // 72: aload 4
      // 74: invokevirtual java/lang/Process.destroy ()V
      // 77: iload 1
      // 78: ireturn
      // 79: astore 6
      // 7b: aload 5
      // 7d: invokevirtual java/io/BufferedReader.close ()V
      // 80: goto 92
      // 83: astore 5
      // 85: aload 4
      // 87: astore 2
      // 88: aload 4
      // 8a: astore 3
      // 8b: aload 6
      // 8d: aload 5
      // 8f: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 92: aload 4
      // 94: astore 2
      // 95: aload 4
      // 97: astore 3
      // 98: aload 6
      // 9a: athrow
      // 9b: astore 3
      // 9c: aload 2
      // 9d: astore 4
      // 9f: aload 0
      // a0: getfield io/sentry/android/core/internal/util/RootChecker.logger Lio/sentry/ILogger;
      // a3: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // a6: ldc "Error when trying to check if SU exists."
      // a8: aload 3
      // a9: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // ae: aload 2
      // af: ifnull d8
      // b2: aload 2
      // b3: invokevirtual java/lang/Process.destroy ()V
      // b6: goto d8
      // b9: astore 2
      // ba: aload 3
      // bb: astore 4
      // bd: aload 0
      // be: getfield io/sentry/android/core/internal/util/RootChecker.logger Lio/sentry/ILogger;
      // c1: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // c4: ldc "SU isn't found on this Device."
      // c6: bipush 0
      // c7: anewarray 4
      // ca: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // cf: aload 3
      // d0: ifnull d8
      // d3: aload 3
      // d4: astore 2
      // d5: goto b2
      // d8: bipush 0
      // d9: ireturn
      // da: astore 2
      // db: aload 4
      // dd: ifnull e5
      // e0: aload 4
      // e2: invokevirtual java/lang/Process.destroy ()V
      // e5: aload 2
      // e6: athrow
   }

   private boolean checkTestKeys() {
      String var2 = this.buildInfoProvider.getBuildTags();
      boolean var1;
      if (var2 != null && var2.contains("test-keys")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isDeviceRooted() {
      boolean var1;
      if (!this.checkTestKeys() && !this.checkRootFiles() && !this.checkSUExist() && !this.checkRootPackages(this.logger)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }
}
