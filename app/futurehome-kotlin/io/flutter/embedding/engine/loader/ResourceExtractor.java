package io.flutter.embedding.engine.loader;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import io.flutter.Log;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

class ResourceExtractor {
   private static final String[] SUPPORTED_ABIS = Build.SUPPORTED_ABIS;
   private static final String TAG = "ResourceExtractor";
   private static final String TIMESTAMP_PREFIX = "res_timestamp-";
   private final AssetManager mAssetManager;
   private final String mDataDirPath;
   private ResourceExtractor.ExtractTask mExtractTask;
   private final PackageManager mPackageManager;
   private final String mPackageName;
   private final HashSet<String> mResources;

   ResourceExtractor(String var1, String var2, PackageManager var3, AssetManager var4) {
      this.mDataDirPath = var1;
      this.mPackageName = var2;
      this.mPackageManager = var3;
      this.mAssetManager = var4;
      this.mResources = new HashSet<>();
   }

   private static String checkTimestamp(File var0, PackageManager var1, String var2) {
      try {
         var6 = var1.getPackageInfo(var2, 0);
      } catch (NameNotFoundException var4) {
         return "res_timestamp-";
      }

      if (var6 == null) {
         return "res_timestamp-";
      } else {
         StringBuilder var8 = new StringBuilder("res_timestamp-");
         var8.append(getVersionCode(var6));
         var8.append("-");
         var8.append(var6.lastUpdateTime);
         String var7 = var8.toString();
         String[] var5 = getExistingTimestamps(var0);
         if (var5 == null) {
            return var7;
         } else {
            int var3 = var5.length;
            return var5.length == 1 && var7.equals(var5[0]) ? null : var7;
         }
      }
   }

   private static void copy(InputStream var0, OutputStream var1) throws IOException {
      byte[] var3 = new byte[16384];

      while (true) {
         int var2 = var0.read(var3);
         if (var2 < 0) {
            return;
         }

         var1.write(var3, 0, var2);
      }
   }

   private static void deleteFiles(String var0, HashSet<String> var1) {
      File var5 = new File(var0);
      Iterator var4 = var1.iterator();

      while (var4.hasNext()) {
         File var6 = new File(var5, (String)var4.next());
         if (var6.exists()) {
            var6.delete();
         }
      }

      String[] var7 = getExistingTimestamps(var5);
      if (var7 != null) {
         int var3 = var7.length;

         for (int var2 = 0; var2 < var3; var2++) {
            new File(var5, var7[var2]).delete();
         }
      }
   }

   private static String[] getExistingTimestamps(File var0) {
      return var0.list(new FilenameFilter() {
         @Override
         public boolean accept(File var1, String var2) {
            return var2.startsWith("res_timestamp-");
         }
      });
   }

   static long getVersionCode(PackageInfo var0) {
      return VERSION.SDK_INT >= 28 ? AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var0) : var0.versionCode;
   }

   ResourceExtractor addResource(String var1) {
      this.mResources.add(var1);
      return this;
   }

   ResourceExtractor addResources(Collection<String> var1) {
      this.mResources.addAll(var1);
      return this;
   }

   ResourceExtractor start() {
      ResourceExtractor.ExtractTask var1 = new ResourceExtractor.ExtractTask(
         this.mDataDirPath, this.mResources, this.mPackageName, this.mPackageManager, this.mAssetManager
      );
      this.mExtractTask = var1;
      var1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
      return this;
   }

   void waitForCompletion() {
      ResourceExtractor.ExtractTask var1 = this.mExtractTask;
      if (var1 != null) {
         try {
            var1.get();
         } catch (ExecutionException | InterruptedException | CancellationException var2) {
            deleteFiles(this.mDataDirPath, this.mResources);
         }
      }
   }

   private static class ExtractTask extends AsyncTask<Void, Void, Void> {
      private final AssetManager mAssetManager;
      private final String mDataDirPath;
      private final PackageManager mPackageManager;
      private final String mPackageName;
      private final HashSet<String> mResources;

      ExtractTask(String var1, HashSet<String> var2, String var3, PackageManager var4, AssetManager var5) {
         this.mDataDirPath = var1;
         this.mResources = var2;
         this.mAssetManager = var5;
         this.mPackageName = var3;
         this.mPackageManager = var4;
      }

      private boolean extractAPK(File param1) {
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
         // 00: aload 0
         // 01: getfield io/flutter/embedding/engine/loader/ResourceExtractor$ExtractTask.mResources Ljava/util/HashSet;
         // 04: invokevirtual java/util/HashSet.iterator ()Ljava/util/Iterator;
         // 07: astore 2
         // 08: aload 2
         // 09: invokeinterface java/util/Iterator.hasNext ()Z 1
         // 0e: ifeq c9
         // 11: aload 2
         // 12: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
         // 17: checkcast java/lang/String
         // 1a: astore 3
         // 1b: new java/io/File
         // 1e: astore 4
         // 20: aload 4
         // 22: aload 1
         // 23: aload 3
         // 24: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
         // 27: aload 4
         // 29: invokevirtual java/io/File.exists ()Z
         // 2c: ifeq 32
         // 2f: goto 08
         // 32: aload 4
         // 34: invokevirtual java/io/File.getParentFile ()Ljava/io/File;
         // 37: ifnull 43
         // 3a: aload 4
         // 3c: invokevirtual java/io/File.getParentFile ()Ljava/io/File;
         // 3f: invokevirtual java/io/File.mkdirs ()Z
         // 42: pop
         // 43: aload 0
         // 44: getfield io/flutter/embedding/engine/loader/ResourceExtractor$ExtractTask.mAssetManager Landroid/content/res/AssetManager;
         // 47: aload 3
         // 48: invokevirtual android/content/res/AssetManager.open (Ljava/lang/String;)Ljava/io/InputStream;
         // 4b: astore 3
         // 4c: new java/io/FileOutputStream
         // 4f: astore 5
         // 51: aload 5
         // 53: aload 4
         // 55: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
         // 58: aload 3
         // 59: aload 5
         // 5b: invokestatic io/flutter/embedding/engine/loader/ResourceExtractor.access$200 (Ljava/io/InputStream;Ljava/io/OutputStream;)V
         // 5e: aload 5
         // 60: invokevirtual java/io/OutputStream.close ()V
         // 63: aload 3
         // 64: ifnull 08
         // 67: aload 3
         // 68: invokevirtual java/io/InputStream.close ()V
         // 6b: goto 08
         // 6e: astore 4
         // 70: aload 5
         // 72: invokevirtual java/io/OutputStream.close ()V
         // 75: goto 81
         // 78: astore 5
         // 7a: aload 4
         // 7c: aload 5
         // 7e: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
         // 81: aload 4
         // 83: athrow
         // 84: astore 4
         // 86: aload 3
         // 87: ifnull 98
         // 8a: aload 3
         // 8b: invokevirtual java/io/InputStream.close ()V
         // 8e: goto 98
         // 91: astore 3
         // 92: aload 4
         // 94: aload 3
         // 95: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
         // 98: aload 4
         // 9a: athrow
         // 9b: astore 2
         // 9c: new java/lang/StringBuilder
         // 9f: dup
         // a0: ldc "Exception unpacking resources: "
         // a2: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
         // a5: astore 1
         // a6: aload 1
         // a7: aload 2
         // a8: invokevirtual java/io/IOException.getMessage ()Ljava/lang/String;
         // ab: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // ae: pop
         // af: ldc "ResourceExtractor"
         // b1: aload 1
         // b2: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // b5: invokestatic io/flutter/Log.w (Ljava/lang/String;Ljava/lang/String;)V
         // b8: aload 0
         // b9: getfield io/flutter/embedding/engine/loader/ResourceExtractor$ExtractTask.mDataDirPath Ljava/lang/String;
         // bc: aload 0
         // bd: getfield io/flutter/embedding/engine/loader/ResourceExtractor$ExtractTask.mResources Ljava/util/HashSet;
         // c0: invokestatic io/flutter/embedding/engine/loader/ResourceExtractor.access$100 (Ljava/lang/String;Ljava/util/HashSet;)V
         // c3: bipush 0
         // c4: ireturn
         // c5: astore 3
         // c6: goto 08
         // c9: bipush 1
         // ca: ireturn
      }

      protected Void doInBackground(Void... var1) {
         File var2 = new File(this.mDataDirPath);
         String var5 = ResourceExtractor.checkTimestamp(var2, this.mPackageManager, this.mPackageName);
         if (var5 == null) {
            return null;
         } else {
            ResourceExtractor.deleteFiles(this.mDataDirPath, this.mResources);
            if (!this.extractAPK(var2)) {
               return null;
            } else {
               if (var5 != null) {
                  try {
                     File var3 = new File(var2, var5);
                     var3.createNewFile();
                  } catch (IOException var4) {
                     Log.w("ResourceExtractor", "Failed to write resource timestamp");
                  }
               }

               return null;
            }
         }
      }
   }
}
