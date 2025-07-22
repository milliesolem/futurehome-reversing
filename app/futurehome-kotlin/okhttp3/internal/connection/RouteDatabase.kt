package okhttp3.internal.connection

import java.util.LinkedHashSet
import okhttp3.Route

public class RouteDatabase {
   private final val failedRoutes: MutableSet<Route> = (new LinkedHashSet()) as java.util.Set

   public fun connected(route: Route) {
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
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 1
      // 03: ldc "route"
      // 05: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 08: aload 0
      // 09: getfield okhttp3/internal/connection/RouteDatabase.failedRoutes Ljava/util/Set;
      // 0c: aload 1
      // 0d: invokeinterface java/util/Set.remove (Ljava/lang/Object;)Z 2
      // 12: pop
      // 13: aload 0
      // 14: monitorexit
      // 15: return
      // 16: astore 1
      // 17: aload 0
      // 18: monitorexit
      // 19: aload 1
      // 1a: athrow
   }

   public fun failed(failedRoute: Route) {
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
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 1
      // 03: ldc "failedRoute"
      // 05: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 08: aload 0
      // 09: getfield okhttp3/internal/connection/RouteDatabase.failedRoutes Ljava/util/Set;
      // 0c: aload 1
      // 0d: invokeinterface java/util/Set.add (Ljava/lang/Object;)Z 2
      // 12: pop
      // 13: aload 0
      // 14: monitorexit
      // 15: return
      // 16: astore 1
      // 17: aload 0
      // 18: monitorexit
      // 19: aload 1
      // 1a: athrow
   }

   public fun shouldPostpone(route: Route): Boolean {
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
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 1
      // 03: ldc "route"
      // 05: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 08: aload 0
      // 09: getfield okhttp3/internal/connection/RouteDatabase.failedRoutes Ljava/util/Set;
      // 0c: aload 1
      // 0d: invokeinterface java/util/Set.contains (Ljava/lang/Object;)Z 2
      // 12: istore 2
      // 13: aload 0
      // 14: monitorexit
      // 15: iload 2
      // 16: ireturn
      // 17: astore 1
      // 18: aload 0
      // 19: monitorexit
      // 1a: aload 1
      // 1b: athrow
   }
}
