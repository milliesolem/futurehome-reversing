package io.sentry.android.ndk;

import io.sentry.SentryOptions;
import io.sentry.android.core.IDebugImagesLoader;
import io.sentry.android.core.SentryAndroidOptions;
import io.sentry.protocol.DebugImage;
import io.sentry.util.Objects;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class DebugImagesLoader implements IDebugImagesLoader {
   private static volatile List<DebugImage> debugImages;
   private static final Object debugImagesLock = new Object();
   private final NativeModuleListLoader moduleListLoader;
   private final SentryOptions options;

   public DebugImagesLoader(SentryAndroidOptions var1, NativeModuleListLoader var2) {
      this.options = Objects.requireNonNull(var1, "The SentryAndroidOptions is required.");
      this.moduleListLoader = Objects.requireNonNull(var2, "The NativeModuleListLoader is required.");
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private Set<DebugImage> filterImagesByAddresses(List<DebugImage> var1, Set<String> var2) {
      HashSet var14 = new HashSet();
      int var3 = 0;

      label85:
      while (var3 < var1.size()) {
         DebugImage var13 = (DebugImage)var1.get(var3);
         int var4 = var3 + 1;
         var3 = var1.size();
         String var11 = null;
         DebugImage var12;
         if (var4 < var3) {
            var12 = (DebugImage)var1.get(var4);
         } else {
            var12 = null;
         }

         if (var12 != null) {
            var11 = var12.getImageAddr();
         }

         Iterator var22 = var2.iterator();

         while (true) {
            long var5;
            long var7;
            long var9;
            while (true) {
               Long var24;
               while (true) {
                  while (true) {
                     var3 = var4;
                     if (!var22.hasNext()) {
                        continue label85;
                     }

                     String var15 = (String)var22.next();

                     try {
                        var7 = Long.parseLong(var15.replace("0x", ""), 16);
                        var23 = var13.getImageAddr();
                        break;
                     } catch (NumberFormatException var16) {
                     }
                  }

                  if (var23 != null) {
                     try {
                        var9 = Long.parseLong(var23.replace("0x", ""), 16);
                        var24 = var13.getImageSize();
                        break;
                     } catch (NumberFormatException var17) {
                     }
                  }
               }

               if (var24 != null) {
                  try {
                     var5 = var24 + var9;
                     break;
                  } catch (NumberFormatException var18) {
                  }
               } else {
                  if (var11 == null) {
                     var5 = Long.MAX_VALUE;
                     break;
                  }

                  try {
                     var5 = Long.parseLong(var11.replace("0x", ""), 16);
                     break;
                  } catch (NumberFormatException var19) {
                  }
               }
            }

            if (var7 >= var9 && var7 < var5) {
               try {
                  var14.add(var13);
                  break;
               } catch (NumberFormatException var20) {
               }
            }
         }

         var3 = var4;
      }

      return var14;
   }

   @Override
   public void clearDebugImages() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: getstatic io/sentry/android/ndk/DebugImagesLoader.debugImagesLock Ljava/lang/Object;
      // 03: astore 1
      // 04: aload 1
      // 05: monitorenter
      // 06: aload 0
      // 07: getfield io/sentry/android/ndk/DebugImagesLoader.moduleListLoader Lio/sentry/android/ndk/NativeModuleListLoader;
      // 0a: invokevirtual io/sentry/android/ndk/NativeModuleListLoader.clearModuleList ()V
      // 0d: aload 0
      // 0e: getfield io/sentry/android/ndk/DebugImagesLoader.options Lio/sentry/SentryOptions;
      // 11: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 14: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 17: ldc "Debug images cleared."
      // 19: bipush 0
      // 1a: anewarray 4
      // 1d: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 22: goto 3c
      // 25: astore 2
      // 26: aload 0
      // 27: getfield io/sentry/android/ndk/DebugImagesLoader.options Lio/sentry/SentryOptions;
      // 2a: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 2d: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 30: aload 2
      // 31: ldc "Failed to clear debug images."
      // 33: bipush 0
      // 34: anewarray 4
      // 37: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // 3c: aconst_null
      // 3d: putstatic io/sentry/android/ndk/DebugImagesLoader.debugImages Ljava/util/List;
      // 40: aload 1
      // 41: monitorexit
      // 42: return
      // 43: astore 2
      // 44: aload 1
      // 45: monitorexit
      // 46: aload 2
      // 47: athrow
   }

   List<DebugImage> getCachedDebugImages() {
      return debugImages;
   }

   @Override
   public List<DebugImage> loadDebugImages() {
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
      // 00: getstatic io/sentry/android/ndk/DebugImagesLoader.debugImagesLock Ljava/lang/Object;
      // 03: astore 1
      // 04: aload 1
      // 05: monitorenter
      // 06: getstatic io/sentry/android/ndk/DebugImagesLoader.debugImages Ljava/util/List;
      // 09: astore 2
      // 0a: aload 2
      // 0b: ifnonnull 5e
      // 0e: aload 0
      // 0f: getfield io/sentry/android/ndk/DebugImagesLoader.moduleListLoader Lio/sentry/android/ndk/NativeModuleListLoader;
      // 12: invokevirtual io/sentry/android/ndk/NativeModuleListLoader.loadModuleList ()[Lio/sentry/protocol/DebugImage;
      // 15: astore 2
      // 16: aload 2
      // 17: ifnull 5e
      // 1a: aload 2
      // 1b: invokestatic java/util/Arrays.asList ([Ljava/lang/Object;)Ljava/util/List;
      // 1e: putstatic io/sentry/android/ndk/DebugImagesLoader.debugImages Ljava/util/List;
      // 21: aload 0
      // 22: getfield io/sentry/android/ndk/DebugImagesLoader.options Lio/sentry/SentryOptions;
      // 25: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 28: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 2b: ldc "Debug images loaded: %d"
      // 2d: bipush 1
      // 2e: anewarray 4
      // 31: dup
      // 32: bipush 0
      // 33: getstatic io/sentry/android/ndk/DebugImagesLoader.debugImages Ljava/util/List;
      // 36: invokeinterface java/util/List.size ()I 1
      // 3b: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 3e: aastore
      // 3f: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 44: goto 5e
      // 47: astore 2
      // 48: aload 0
      // 49: getfield io/sentry/android/ndk/DebugImagesLoader.options Lio/sentry/SentryOptions;
      // 4c: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 4f: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 52: aload 2
      // 53: ldc "Failed to load debug images."
      // 55: bipush 0
      // 56: anewarray 4
      // 59: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // 5e: aload 1
      // 5f: monitorexit
      // 60: getstatic io/sentry/android/ndk/DebugImagesLoader.debugImages Ljava/util/List;
      // 63: areturn
      // 64: astore 2
      // 65: aload 1
      // 66: monitorexit
      // 67: aload 2
      // 68: athrow
   }

   @Override
   public Set<DebugImage> loadDebugImagesForAddresses(Set<String> param1) {
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
      // 00: getstatic io/sentry/android/ndk/DebugImagesLoader.debugImagesLock Ljava/lang/Object;
      // 03: astore 2
      // 04: aload 2
      // 05: monitorenter
      // 06: aload 0
      // 07: invokevirtual io/sentry/android/ndk/DebugImagesLoader.loadDebugImages ()Ljava/util/List;
      // 0a: astore 3
      // 0b: aload 3
      // 0c: ifnonnull 13
      // 0f: aload 2
      // 10: monitorexit
      // 11: aconst_null
      // 12: areturn
      // 13: aload 1
      // 14: invokeinterface java/util/Set.isEmpty ()Z 1
      // 19: ifeq 20
      // 1c: aload 2
      // 1d: monitorexit
      // 1e: aconst_null
      // 1f: areturn
      // 20: aload 0
      // 21: aload 3
      // 22: aload 1
      // 23: invokespecial io/sentry/android/ndk/DebugImagesLoader.filterImagesByAddresses (Ljava/util/List;Ljava/util/Set;)Ljava/util/Set;
      // 26: astore 3
      // 27: aload 3
      // 28: invokeinterface java/util/Set.isEmpty ()Z 1
      // 2d: ifeq 55
      // 30: aload 0
      // 31: getfield io/sentry/android/ndk/DebugImagesLoader.options Lio/sentry/SentryOptions;
      // 34: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 37: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 3a: ldc "No debug images found for any of the %d addresses."
      // 3c: bipush 1
      // 3d: anewarray 4
      // 40: dup
      // 41: bipush 0
      // 42: aload 1
      // 43: invokeinterface java/util/Set.size ()I 1
      // 48: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 4b: aastore
      // 4c: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 51: aload 2
      // 52: monitorexit
      // 53: aconst_null
      // 54: areturn
      // 55: aload 2
      // 56: monitorexit
      // 57: aload 3
      // 58: areturn
      // 59: astore 1
      // 5a: aload 2
      // 5b: monitorexit
      // 5c: aload 1
      // 5d: athrow
   }
}
