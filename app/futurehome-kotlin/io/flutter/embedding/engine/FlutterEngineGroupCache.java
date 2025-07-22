package io.flutter.embedding.engine;

import java.util.HashMap;
import java.util.Map;

public class FlutterEngineGroupCache {
   private static volatile FlutterEngineGroupCache instance;
   private final Map<String, FlutterEngineGroup> cachedEngineGroups = new HashMap<>();

   FlutterEngineGroupCache() {
   }

   public static FlutterEngineGroupCache getInstance() {
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
      // 00: getstatic io/flutter/embedding/engine/FlutterEngineGroupCache.instance Lio/flutter/embedding/engine/FlutterEngineGroupCache;
      // 03: ifnonnull 27
      // 06: ldc io/flutter/embedding/engine/FlutterEngineGroupCache
      // 08: monitorenter
      // 09: getstatic io/flutter/embedding/engine/FlutterEngineGroupCache.instance Lio/flutter/embedding/engine/FlutterEngineGroupCache;
      // 0c: ifnonnull 1b
      // 0f: new io/flutter/embedding/engine/FlutterEngineGroupCache
      // 12: astore 0
      // 13: aload 0
      // 14: invokespecial io/flutter/embedding/engine/FlutterEngineGroupCache.<init> ()V
      // 17: aload 0
      // 18: putstatic io/flutter/embedding/engine/FlutterEngineGroupCache.instance Lio/flutter/embedding/engine/FlutterEngineGroupCache;
      // 1b: ldc io/flutter/embedding/engine/FlutterEngineGroupCache
      // 1d: monitorexit
      // 1e: goto 27
      // 21: astore 0
      // 22: ldc io/flutter/embedding/engine/FlutterEngineGroupCache
      // 24: monitorexit
      // 25: aload 0
      // 26: athrow
      // 27: getstatic io/flutter/embedding/engine/FlutterEngineGroupCache.instance Lio/flutter/embedding/engine/FlutterEngineGroupCache;
      // 2a: areturn
   }

   public void clear() {
      this.cachedEngineGroups.clear();
   }

   public boolean contains(String var1) {
      return this.cachedEngineGroups.containsKey(var1);
   }

   public FlutterEngineGroup get(String var1) {
      return this.cachedEngineGroups.get(var1);
   }

   public void put(String var1, FlutterEngineGroup var2) {
      if (var2 != null) {
         this.cachedEngineGroups.put(var1, var2);
      } else {
         this.cachedEngineGroups.remove(var1);
      }
   }

   public void remove(String var1) {
      this.put(var1, null);
   }
}
