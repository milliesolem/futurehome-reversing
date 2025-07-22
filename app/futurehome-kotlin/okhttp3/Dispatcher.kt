package okhttp3

import java.util.ArrayDeque
import java.util.Deque
import java.util.concurrent.ExecutorService
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.connection.RealCall
import okhttp3.internal.connection.RealCall.AsyncCall

public class Dispatcher {
   public final val executorService: ExecutorService
      public final get() {
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
         // 02: aload 0
         // 03: getfield okhttp3/Dispatcher.executorServiceOrNull Ljava/util/concurrent/ExecutorService;
         // 06: ifnonnull 55
         // 09: new java/util/concurrent/ThreadPoolExecutor
         // 0c: astore 2
         // 0d: getstatic java/util/concurrent/TimeUnit.SECONDS Ljava/util/concurrent/TimeUnit;
         // 10: astore 1
         // 11: new java/util/concurrent/SynchronousQueue
         // 14: astore 3
         // 15: aload 3
         // 16: invokespecial java/util/concurrent/SynchronousQueue.<init> ()V
         // 19: aload 3
         // 1a: checkcast java/util/concurrent/BlockingQueue
         // 1d: astore 4
         // 1f: new java/lang/StringBuilder
         // 22: astore 3
         // 23: aload 3
         // 24: invokespecial java/lang/StringBuilder.<init> ()V
         // 27: aload 3
         // 28: getstatic okhttp3/internal/Util.okHttpName Ljava/lang/String;
         // 2b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 2e: pop
         // 2f: aload 3
         // 30: ldc_w " Dispatcher"
         // 33: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 36: pop
         // 37: aload 2
         // 38: bipush 0
         // 39: ldc_w 2147483647
         // 3c: ldc2_w 60
         // 3f: aload 1
         // 40: aload 4
         // 42: aload 3
         // 43: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 46: bipush 0
         // 47: invokestatic okhttp3/internal/Util.threadFactory (Ljava/lang/String;Z)Ljava/util/concurrent/ThreadFactory;
         // 4a: invokespecial java/util/concurrent/ThreadPoolExecutor.<init> (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/ThreadFactory;)V
         // 4d: aload 0
         // 4e: aload 2
         // 4f: checkcast java/util/concurrent/ExecutorService
         // 52: putfield okhttp3/Dispatcher.executorServiceOrNull Ljava/util/concurrent/ExecutorService;
         // 55: aload 0
         // 56: getfield okhttp3/Dispatcher.executorServiceOrNull Ljava/util/concurrent/ExecutorService;
         // 59: astore 1
         // 5a: aload 1
         // 5b: ifnonnull 61
         // 5e: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
         // 61: aload 0
         // 62: monitorexit
         // 63: aload 1
         // 64: areturn
         // 65: astore 1
         // 66: aload 0
         // 67: monitorexit
         // 68: aload 1
         // 69: athrow
      }


   private final var executorServiceOrNull: ExecutorService?

   public final var idleCallback: Runnable?
      public final get() {
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
         // 0: aload 0
         // 1: monitorenter
         // 2: aload 0
         // 3: getfield okhttp3/Dispatcher.idleCallback Ljava/lang/Runnable;
         // 6: astore 1
         // 7: aload 0
         // 8: monitorexit
         // 9: aload 1
         // a: areturn
         // b: astore 1
         // c: aload 0
         // d: monitorexit
         // e: aload 1
         // f: athrow
      }

      public final set(<set-?>) {
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
         // 0: aload 0
         // 1: monitorenter
         // 2: aload 0
         // 3: aload 1
         // 4: putfield okhttp3/Dispatcher.idleCallback Ljava/lang/Runnable;
         // 7: aload 0
         // 8: monitorexit
         // 9: return
         // a: astore 1
         // b: aload 0
         // c: monitorexit
         // d: aload 1
         // e: athrow
      }


   public final var maxRequests: Int = 64
      public final get() {
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
         // 0: aload 0
         // 1: monitorenter
         // 2: aload 0
         // 3: getfield okhttp3/Dispatcher.maxRequests I
         // 6: istore 1
         // 7: aload 0
         // 8: monitorexit
         // 9: iload 1
         // a: ireturn
         // b: astore 2
         // c: aload 0
         // d: monitorexit
         // e: aload 2
         // f: athrow
      }

      public final set(maxRequests) {
         var var2: Boolean = true;
         if (var1 < 1) {
            var2 = false;
         }

         label18:
         if (var2) {
            synchronized (this){} // $VF: monitorenter 

            try {
               this.maxRequests = var1;
            } catch (var4: java.lang.Throwable) {
               // $VF: monitorexit
            }

            // $VF: monitorexit
         } else {
            val var3: StringBuilder = new StringBuilder("max < 1: ");
            var3.append(var1);
            throw (new IllegalArgumentException(var3.toString().toString())) as java.lang.Throwable;
         }
      }


   public final var maxRequestsPerHost: Int = 5
      public final get() {
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
         // 0: aload 0
         // 1: monitorenter
         // 2: aload 0
         // 3: getfield okhttp3/Dispatcher.maxRequestsPerHost I
         // 6: istore 1
         // 7: aload 0
         // 8: monitorexit
         // 9: iload 1
         // a: ireturn
         // b: astore 2
         // c: aload 0
         // d: monitorexit
         // e: aload 2
         // f: athrow
      }

      public final set(maxRequestsPerHost) {
         var var2: Boolean = true;
         if (var1 < 1) {
            var2 = false;
         }

         label18:
         if (var2) {
            synchronized (this){} // $VF: monitorenter 

            try {
               this.maxRequestsPerHost = var1;
            } catch (var4: java.lang.Throwable) {
               // $VF: monitorexit
            }

            // $VF: monitorexit
         } else {
            val var3: StringBuilder = new StringBuilder("max < 1: ");
            var3.append(var1);
            throw (new IllegalArgumentException(var3.toString().toString())) as java.lang.Throwable;
         }
      }


   private final val readyAsyncCalls: ArrayDeque<AsyncCall> = new ArrayDeque()
   private final val runningAsyncCalls: ArrayDeque<AsyncCall> = new ArrayDeque()
   private final val runningSyncCalls: ArrayDeque<RealCall> = new ArrayDeque()

   public constructor(executorService: ExecutorService) : Intrinsics.checkParameterIsNotNull(var1, "executorService") {
      this();
      this.executorServiceOrNull = var1;
   }

   private fun findExistingCallWithHost(host: String): AsyncCall? {
      for (RealCall.AsyncCall var2 : this.runningAsyncCalls) {
         if (var2.getHost() == var1) {
            return var2;
         }
      }

      for (RealCall.AsyncCall var4 : this.readyAsyncCalls) {
         if (var4.getHost() == var1) {
            return var4;
         }
      }

      return null;
   }

   private fun <T> finished(calls: Deque<T>, call: T) {
      val var4: Runnable;
      synchronized (this) {
         if (!var1.remove(var2)) {
            throw (new AssertionError("Call wasn't in-flight!")) as java.lang.Throwable;
         }

         var4 = this.idleCallback;
      }

      if (!this.promoteAndExecute() && var4 != null) {
         var4.run();
      }
   }

   private fun promoteAndExecute(): Boolean {
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
      // 000: getstatic okhttp3/internal/Util.assertionsEnabled Z
      // 003: ifeq 051
      // 006: aload 0
      // 007: invokestatic java/lang/Thread.holdsLock (Ljava/lang/Object;)Z
      // 00a: ifne 010
      // 00d: goto 051
      // 010: new java/lang/StringBuilder
      // 013: dup
      // 014: ldc "Thread "
      // 016: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 019: astore 4
      // 01b: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 01e: astore 5
      // 020: aload 5
      // 022: ldc "Thread.currentThread()"
      // 024: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 027: aload 4
      // 029: aload 5
      // 02b: invokevirtual java/lang/Thread.getName ()Ljava/lang/String;
      // 02e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 031: pop
      // 032: aload 4
      // 034: ldc " MUST NOT hold lock on "
      // 036: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 039: pop
      // 03a: aload 4
      // 03c: aload 0
      // 03d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 040: pop
      // 041: new java/lang/AssertionError
      // 044: dup
      // 045: aload 4
      // 047: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 04a: invokespecial java/lang/AssertionError.<init> (Ljava/lang/Object;)V
      // 04d: checkcast java/lang/Throwable
      // 050: athrow
      // 051: new java/util/ArrayList
      // 054: dup
      // 055: invokespecial java/util/ArrayList.<init> ()V
      // 058: checkcast java/util/List
      // 05b: astore 4
      // 05d: aload 0
      // 05e: monitorenter
      // 05f: aload 0
      // 060: getfield okhttp3/Dispatcher.readyAsyncCalls Ljava/util/ArrayDeque;
      // 063: invokevirtual java/util/ArrayDeque.iterator ()Ljava/util/Iterator;
      // 066: astore 5
      // 068: aload 5
      // 06a: ldc "readyAsyncCalls.iterator()"
      // 06c: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 06f: aload 5
      // 071: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 076: ifeq 0d6
      // 079: aload 5
      // 07b: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 080: checkcast okhttp3/internal/connection/RealCall$AsyncCall
      // 083: astore 6
      // 085: aload 0
      // 086: getfield okhttp3/Dispatcher.runningAsyncCalls Ljava/util/ArrayDeque;
      // 089: invokevirtual java/util/ArrayDeque.size ()I
      // 08c: aload 0
      // 08d: getfield okhttp3/Dispatcher.maxRequests I
      // 090: if_icmplt 096
      // 093: goto 0d6
      // 096: aload 6
      // 098: invokevirtual okhttp3/internal/connection/RealCall$AsyncCall.getCallsPerHost ()Ljava/util/concurrent/atomic/AtomicInteger;
      // 09b: invokevirtual java/util/concurrent/atomic/AtomicInteger.get ()I
      // 09e: aload 0
      // 09f: getfield okhttp3/Dispatcher.maxRequestsPerHost I
      // 0a2: if_icmplt 0a8
      // 0a5: goto 06f
      // 0a8: aload 5
      // 0aa: invokeinterface java/util/Iterator.remove ()V 1
      // 0af: aload 6
      // 0b1: invokevirtual okhttp3/internal/connection/RealCall$AsyncCall.getCallsPerHost ()Ljava/util/concurrent/atomic/AtomicInteger;
      // 0b4: invokevirtual java/util/concurrent/atomic/AtomicInteger.incrementAndGet ()I
      // 0b7: pop
      // 0b8: aload 6
      // 0ba: ldc "asyncCall"
      // 0bc: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 0bf: aload 4
      // 0c1: aload 6
      // 0c3: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 0c8: pop
      // 0c9: aload 0
      // 0ca: getfield okhttp3/Dispatcher.runningAsyncCalls Ljava/util/ArrayDeque;
      // 0cd: aload 6
      // 0cf: invokevirtual java/util/ArrayDeque.add (Ljava/lang/Object;)Z
      // 0d2: pop
      // 0d3: goto 06f
      // 0d6: aload 0
      // 0d7: invokevirtual okhttp3/Dispatcher.runningCallsCount ()I
      // 0da: istore 2
      // 0db: bipush 0
      // 0dc: istore 1
      // 0dd: iload 2
      // 0de: ifle 0e6
      // 0e1: bipush 1
      // 0e2: istore 3
      // 0e3: goto 0e8
      // 0e6: bipush 0
      // 0e7: istore 3
      // 0e8: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0eb: astore 5
      // 0ed: aload 0
      // 0ee: monitorexit
      // 0ef: aload 4
      // 0f1: invokeinterface java/util/List.size ()I 1
      // 0f6: istore 2
      // 0f7: iload 1
      // 0f8: iload 2
      // 0f9: if_icmpge 114
      // 0fc: aload 4
      // 0fe: iload 1
      // 0ff: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 104: checkcast okhttp3/internal/connection/RealCall$AsyncCall
      // 107: aload 0
      // 108: invokevirtual okhttp3/Dispatcher.executorService ()Ljava/util/concurrent/ExecutorService;
      // 10b: invokevirtual okhttp3/internal/connection/RealCall$AsyncCall.executeOn (Ljava/util/concurrent/ExecutorService;)V
      // 10e: iinc 1 1
      // 111: goto 0f7
      // 114: iload 3
      // 115: ireturn
      // 116: astore 4
      // 118: aload 0
      // 119: monitorexit
      // 11a: aload 4
      // 11c: athrow
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "executorService", imports = []))
   public fun executorService(): ExecutorService {
      return this.executorService();
   }

   public fun cancelAll() {
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
      // 02: aload 0
      // 03: getfield okhttp3/Dispatcher.readyAsyncCalls Ljava/util/ArrayDeque;
      // 06: invokevirtual java/util/ArrayDeque.iterator ()Ljava/util/Iterator;
      // 09: astore 1
      // 0a: aload 1
      // 0b: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 10: ifeq 25
      // 13: aload 1
      // 14: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 19: checkcast okhttp3/internal/connection/RealCall$AsyncCall
      // 1c: invokevirtual okhttp3/internal/connection/RealCall$AsyncCall.getCall ()Lokhttp3/internal/connection/RealCall;
      // 1f: invokevirtual okhttp3/internal/connection/RealCall.cancel ()V
      // 22: goto 0a
      // 25: aload 0
      // 26: getfield okhttp3/Dispatcher.runningAsyncCalls Ljava/util/ArrayDeque;
      // 29: invokevirtual java/util/ArrayDeque.iterator ()Ljava/util/Iterator;
      // 2c: astore 1
      // 2d: aload 1
      // 2e: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 33: ifeq 48
      // 36: aload 1
      // 37: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 3c: checkcast okhttp3/internal/connection/RealCall$AsyncCall
      // 3f: invokevirtual okhttp3/internal/connection/RealCall$AsyncCall.getCall ()Lokhttp3/internal/connection/RealCall;
      // 42: invokevirtual okhttp3/internal/connection/RealCall.cancel ()V
      // 45: goto 2d
      // 48: aload 0
      // 49: getfield okhttp3/Dispatcher.runningSyncCalls Ljava/util/ArrayDeque;
      // 4c: invokevirtual java/util/ArrayDeque.iterator ()Ljava/util/Iterator;
      // 4f: astore 1
      // 50: aload 1
      // 51: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 56: ifeq 68
      // 59: aload 1
      // 5a: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 5f: checkcast okhttp3/internal/connection/RealCall
      // 62: invokevirtual okhttp3/internal/connection/RealCall.cancel ()V
      // 65: goto 50
      // 68: aload 0
      // 69: monitorexit
      // 6a: return
      // 6b: astore 1
      // 6c: aload 0
      // 6d: monitorexit
      // 6e: aload 1
      // 6f: athrow
   }

   internal fun enqueue(call: AsyncCall) {
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
      // 00: aload 1
      // 01: ldc_w "call"
      // 04: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 07: aload 0
      // 08: monitorenter
      // 09: aload 0
      // 0a: getfield okhttp3/Dispatcher.readyAsyncCalls Ljava/util/ArrayDeque;
      // 0d: aload 1
      // 0e: invokevirtual java/util/ArrayDeque.add (Ljava/lang/Object;)Z
      // 11: pop
      // 12: aload 1
      // 13: invokevirtual okhttp3/internal/connection/RealCall$AsyncCall.getCall ()Lokhttp3/internal/connection/RealCall;
      // 16: invokevirtual okhttp3/internal/connection/RealCall.getForWebSocket ()Z
      // 19: ifne 2e
      // 1c: aload 0
      // 1d: aload 1
      // 1e: invokevirtual okhttp3/internal/connection/RealCall$AsyncCall.getHost ()Ljava/lang/String;
      // 21: invokespecial okhttp3/Dispatcher.findExistingCallWithHost (Ljava/lang/String;)Lokhttp3/internal/connection/RealCall$AsyncCall;
      // 24: astore 2
      // 25: aload 2
      // 26: ifnull 2e
      // 29: aload 1
      // 2a: aload 2
      // 2b: invokevirtual okhttp3/internal/connection/RealCall$AsyncCall.reuseCallsPerHostFrom (Lokhttp3/internal/connection/RealCall$AsyncCall;)V
      // 2e: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 31: astore 1
      // 32: aload 0
      // 33: monitorexit
      // 34: aload 0
      // 35: invokespecial okhttp3/Dispatcher.promoteAndExecute ()Z
      // 38: pop
      // 39: return
      // 3a: astore 1
      // 3b: aload 0
      // 3c: monitorexit
      // 3d: aload 1
      // 3e: athrow
   }

   internal fun executed(call: RealCall) {
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
      // 03: ldc_w "call"
      // 06: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 09: aload 0
      // 0a: getfield okhttp3/Dispatcher.runningSyncCalls Ljava/util/ArrayDeque;
      // 0d: aload 1
      // 0e: invokevirtual java/util/ArrayDeque.add (Ljava/lang/Object;)Z
      // 11: pop
      // 12: aload 0
      // 13: monitorexit
      // 14: return
      // 15: astore 1
      // 16: aload 0
      // 17: monitorexit
      // 18: aload 1
      // 19: athrow
   }

   internal fun finished(call: AsyncCall) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      var1.getCallsPerHost().decrementAndGet();
      this.finished(this.runningAsyncCalls, var1);
   }

   internal fun finished(call: RealCall) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      this.finished(this.runningSyncCalls, var1);
   }

   public fun queuedCalls(): List<Call> {
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
      // 02: aload 0
      // 03: getfield okhttp3/Dispatcher.readyAsyncCalls Ljava/util/ArrayDeque;
      // 06: checkcast java/lang/Iterable
      // 09: astore 1
      // 0a: new java/util/ArrayList
      // 0d: astore 2
      // 0e: aload 2
      // 0f: aload 1
      // 10: bipush 10
      // 12: invokestatic kotlin/collections/CollectionsKt.collectionSizeOrDefault (Ljava/lang/Iterable;I)I
      // 15: invokespecial java/util/ArrayList.<init> (I)V
      // 18: aload 2
      // 19: checkcast java/util/Collection
      // 1c: astore 2
      // 1d: aload 1
      // 1e: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 23: astore 1
      // 24: aload 1
      // 25: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 2a: ifeq 43
      // 2d: aload 2
      // 2e: aload 1
      // 2f: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 34: checkcast okhttp3/internal/connection/RealCall$AsyncCall
      // 37: invokevirtual okhttp3/internal/connection/RealCall$AsyncCall.getCall ()Lokhttp3/internal/connection/RealCall;
      // 3a: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 3f: pop
      // 40: goto 24
      // 43: aload 2
      // 44: checkcast java/util/List
      // 47: invokestatic java/util/Collections.unmodifiableList (Ljava/util/List;)Ljava/util/List;
      // 4a: astore 1
      // 4b: aload 1
      // 4c: ldc_w "Collections.unmodifiable…yncCalls.map { it.call })"
      // 4f: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 52: aload 0
      // 53: monitorexit
      // 54: aload 1
      // 55: areturn
      // 56: astore 1
      // 57: aload 0
      // 58: monitorexit
      // 59: aload 1
      // 5a: athrow
   }

   public fun queuedCallsCount(): Int {
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
      // 02: aload 0
      // 03: getfield okhttp3/Dispatcher.readyAsyncCalls Ljava/util/ArrayDeque;
      // 06: invokevirtual java/util/ArrayDeque.size ()I
      // 09: istore 1
      // 0a: aload 0
      // 0b: monitorexit
      // 0c: iload 1
      // 0d: ireturn
      // 0e: astore 2
      // 0f: aload 0
      // 10: monitorexit
      // 11: aload 2
      // 12: athrow
   }

   public fun runningCalls(): List<Call> {
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
      // 02: aload 0
      // 03: getfield okhttp3/Dispatcher.runningSyncCalls Ljava/util/ArrayDeque;
      // 06: checkcast java/util/Collection
      // 09: astore 1
      // 0a: aload 0
      // 0b: getfield okhttp3/Dispatcher.runningAsyncCalls Ljava/util/ArrayDeque;
      // 0e: checkcast java/lang/Iterable
      // 11: astore 2
      // 12: new java/util/ArrayList
      // 15: astore 3
      // 16: aload 3
      // 17: aload 2
      // 18: bipush 10
      // 1a: invokestatic kotlin/collections/CollectionsKt.collectionSizeOrDefault (Ljava/lang/Iterable;I)I
      // 1d: invokespecial java/util/ArrayList.<init> (I)V
      // 20: aload 3
      // 21: checkcast java/util/Collection
      // 24: astore 3
      // 25: aload 2
      // 26: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 2b: astore 2
      // 2c: aload 2
      // 2d: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 32: ifeq 4b
      // 35: aload 3
      // 36: aload 2
      // 37: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 3c: checkcast okhttp3/internal/connection/RealCall$AsyncCall
      // 3f: invokevirtual okhttp3/internal/connection/RealCall$AsyncCall.getCall ()Lokhttp3/internal/connection/RealCall;
      // 42: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 47: pop
      // 48: goto 2c
      // 4b: aload 1
      // 4c: aload 3
      // 4d: checkcast java/util/List
      // 50: checkcast java/lang/Iterable
      // 53: invokestatic kotlin/collections/CollectionsKt.plus (Ljava/util/Collection;Ljava/lang/Iterable;)Ljava/util/List;
      // 56: invokestatic java/util/Collections.unmodifiableList (Ljava/util/List;)Ljava/util/List;
      // 59: astore 1
      // 5a: aload 1
      // 5b: ldc_w "Collections.unmodifiable…yncCalls.map { it.call })"
      // 5e: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 61: aload 0
      // 62: monitorexit
      // 63: aload 1
      // 64: areturn
      // 65: astore 1
      // 66: aload 0
      // 67: monitorexit
      // 68: aload 1
      // 69: athrow
   }

   public fun runningCallsCount(): Int {
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
      // 02: aload 0
      // 03: getfield okhttp3/Dispatcher.runningAsyncCalls Ljava/util/ArrayDeque;
      // 06: invokevirtual java/util/ArrayDeque.size ()I
      // 09: istore 2
      // 0a: aload 0
      // 0b: getfield okhttp3/Dispatcher.runningSyncCalls Ljava/util/ArrayDeque;
      // 0e: invokevirtual java/util/ArrayDeque.size ()I
      // 11: istore 1
      // 12: aload 0
      // 13: monitorexit
      // 14: iload 2
      // 15: iload 1
      // 16: iadd
      // 17: ireturn
      // 18: astore 3
      // 19: aload 0
      // 1a: monitorexit
      // 1b: aload 3
      // 1c: athrow
   }
}
