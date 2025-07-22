package kotlinx.coroutines.internal

import java.util.WeakHashMap
import java.util.concurrent.locks.ReentrantReadWriteLock

private object WeakMapCtorCache : CtorCache {
   private final val cacheLock: ReentrantReadWriteLock = new ReentrantReadWriteLock()
   private final val exceptionCtors: WeakHashMap<Class<out Throwable>, (Throwable) -> Throwable?> = new WeakHashMap()

   public override fun get(key: Class<out Throwable>): (Throwable) -> Throwable? {
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
      // 00: getstatic kotlinx/coroutines/internal/WeakMapCtorCache.cacheLock Ljava/util/concurrent/locks/ReentrantReadWriteLock;
      // 03: astore 8
      // 05: aload 8
      // 07: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock.readLock ()Ljava/util/concurrent/locks/ReentrantReadWriteLock$ReadLock;
      // 0a: astore 7
      // 0c: aload 7
      // 0e: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock.lock ()V
      // 11: getstatic kotlinx/coroutines/internal/WeakMapCtorCache.exceptionCtors Ljava/util/WeakHashMap;
      // 14: aload 1
      // 15: invokevirtual java/util/WeakHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 18: checkcast kotlin/jvm/functions/Function1
      // 1b: astore 9
      // 1d: aload 7
      // 1f: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock.unlock ()V
      // 22: aload 9
      // 24: ifnull 2a
      // 27: aload 9
      // 29: areturn
      // 2a: aload 8
      // 2c: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock.readLock ()Ljava/util/concurrent/locks/ReentrantReadWriteLock$ReadLock;
      // 2f: astore 7
      // 31: aload 8
      // 33: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock.getWriteHoldCount ()I
      // 36: istore 2
      // 37: bipush 0
      // 38: istore 4
      // 3a: bipush 0
      // 3b: istore 5
      // 3d: bipush 0
      // 3e: istore 6
      // 40: iload 2
      // 41: ifne 4d
      // 44: aload 8
      // 46: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock.getReadHoldCount ()I
      // 49: istore 2
      // 4a: goto 4f
      // 4d: bipush 0
      // 4e: istore 2
      // 4f: bipush 0
      // 50: istore 3
      // 51: iload 3
      // 52: iload 2
      // 53: if_icmpge 61
      // 56: aload 7
      // 58: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock.unlock ()V
      // 5b: iinc 3 1
      // 5e: goto 51
      // 61: aload 8
      // 63: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock.writeLock ()Ljava/util/concurrent/locks/ReentrantReadWriteLock$WriteLock;
      // 66: astore 8
      // 68: aload 8
      // 6a: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock$WriteLock.lock ()V
      // 6d: getstatic kotlinx/coroutines/internal/WeakMapCtorCache.exceptionCtors Ljava/util/WeakHashMap;
      // 70: astore 9
      // 72: aload 9
      // 74: aload 1
      // 75: invokevirtual java/util/WeakHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 78: checkcast kotlin/jvm/functions/Function1
      // 7b: astore 10
      // 7d: aload 10
      // 7f: ifnull 9d
      // 82: iload 6
      // 84: istore 3
      // 85: iload 3
      // 86: iload 2
      // 87: if_icmpge 95
      // 8a: aload 7
      // 8c: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock.lock ()V
      // 8f: iinc 3 1
      // 92: goto 85
      // 95: aload 8
      // 97: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock$WriteLock.unlock ()V
      // 9a: aload 10
      // 9c: areturn
      // 9d: aload 1
      // 9e: invokestatic kotlinx/coroutines/internal/ExceptionsConstructorKt.access$createConstructor (Ljava/lang/Class;)Lkotlin/jvm/functions/Function1;
      // a1: astore 10
      // a3: aload 9
      // a5: checkcast java/util/Map
      // a8: aload 1
      // a9: aload 10
      // ab: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // b0: pop
      // b1: iload 4
      // b3: istore 3
      // b4: iload 3
      // b5: iload 2
      // b6: if_icmpge c4
      // b9: aload 7
      // bb: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock.lock ()V
      // be: iinc 3 1
      // c1: goto b4
      // c4: aload 8
      // c6: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock$WriteLock.unlock ()V
      // c9: aload 10
      // cb: areturn
      // cc: astore 1
      // cd: iload 5
      // cf: istore 3
      // d0: iload 3
      // d1: iload 2
      // d2: if_icmpge e0
      // d5: aload 7
      // d7: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock.lock ()V
      // da: iinc 3 1
      // dd: goto d0
      // e0: aload 8
      // e2: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock$WriteLock.unlock ()V
      // e5: aload 1
      // e6: athrow
      // e7: astore 1
      // e8: aload 7
      // ea: invokevirtual java/util/concurrent/locks/ReentrantReadWriteLock$ReadLock.unlock ()V
      // ed: aload 1
      // ee: athrow
   }
}
