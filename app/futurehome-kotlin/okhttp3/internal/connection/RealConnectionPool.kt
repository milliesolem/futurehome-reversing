package okhttp3.internal.connection

import java.lang.ref.Reference
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit
import kotlin.jvm.internal.Intrinsics
import okhttp3.Address
import okhttp3.ConnectionPool
import okhttp3.Route
import okhttp3.internal.Util
import okhttp3.internal.concurrent.Task
import okhttp3.internal.concurrent.TaskQueue
import okhttp3.internal.concurrent.TaskRunner
import okhttp3.internal.platform.Platform

public class RealConnectionPool(taskRunner: TaskRunner, maxIdleConnections: Int, keepAliveDuration: Long, timeUnit: TimeUnit) {
   private final val cleanupQueue: TaskQueue
   private final val cleanupTask: <unrepresentable>
   private final val connections: ConcurrentLinkedQueue<RealConnection>
   private final val keepAliveDurationNs: Long
   private final val maxIdleConnections: Int

   init {
      Intrinsics.checkParameterIsNotNull(var1, "taskRunner");
      Intrinsics.checkParameterIsNotNull(var5, "timeUnit");
      super();
      this.maxIdleConnections = var2;
      this.keepAliveDurationNs = var5.toNanos(var3);
      this.cleanupQueue = var1.newQueue();
      val var6: StringBuilder = new StringBuilder();
      var6.append(Util.okHttpName);
      var6.append(" ConnectionPool");
      this.cleanupTask = new Task(this, var6.toString()) {
         final RealConnectionPool this$0;

         {
            super(var2, false, 2, null);
            this.this$0 = var1;
         }

         @Override
         public long runOnce() {
            return this.this$0.cleanup(System.nanoTime());
         }
      };
      this.connections = new ConcurrentLinkedQueue<>();
      val var8: Boolean;
      if (var3 > 0L) {
         var8 = true;
      } else {
         var8 = false;
      }

      if (!var8) {
         val var7: StringBuilder = new StringBuilder("keepAliveDuration <= 0: ");
         var7.append(var3);
         throw (new IllegalArgumentException(var7.toString().toString())) as java.lang.Throwable;
      }
   }

   private fun pruneAndGetAllocationCount(connection: RealConnection, now: Long): Int {
      if (Util.assertionsEnabled && !Thread.holdsLock(var1)) {
         val var8: StringBuilder = new StringBuilder("Thread ");
         val var10: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var10, "Thread.currentThread()");
         var8.append(var10.getName());
         var8.append(" MUST hold lock on ");
         var8.append(var1);
         throw (new AssertionError(var8.toString())) as java.lang.Throwable;
      } else {
         val var5: java.util.List = var1.getCalls();
         var var4: Int = 0;

         while (var4 < var5.size()) {
            val var6: Reference = var5.get(var4) as Reference;
            if (var6.get() != null) {
               var4++;
            } else {
               if (var6 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type okhttp3.internal.connection.RealCall.CallReference");
               }

               val var9: RealCall.CallReference = var6 as RealCall.CallReference;
               val var7: StringBuilder = new StringBuilder("A connection to ");
               var7.append(var1.route().address().url());
               var7.append(" was leaked. Did you forget to close a response body?");
               Platform.Companion.get().logCloseableLeak(var7.toString(), var9.getCallStackTrace());
               var5.remove(var4);
               var1.setNoNewExchanges(true);
               if (var5.isEmpty()) {
                  var1.setIdleAtNs$okhttp(var2 - this.keepAliveDurationNs);
                  return 0;
               }
            }
         }

         return var5.size();
      }
   }

   public fun callAcquirePooledConnection(address: Address, call: RealCall, routes: List<Route>?, requireMultiplexed: Boolean): Boolean {
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
      // 01: ldc_w "address"
      // 04: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 07: aload 2
      // 08: ldc_w "call"
      // 0b: invokestatic kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 0e: aload 0
      // 0f: getfield okhttp3/internal/connection/RealConnectionPool.connections Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 12: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.iterator ()Ljava/util/Iterator;
      // 15: astore 6
      // 17: aload 6
      // 19: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 1e: ifeq 6e
      // 21: aload 6
      // 23: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 28: checkcast okhttp3/internal/connection/RealConnection
      // 2b: astore 5
      // 2d: aload 5
      // 2f: ldc_w "connection"
      // 32: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 35: aload 5
      // 37: monitorenter
      // 38: iload 4
      // 3a: ifeq 48
      // 3d: aload 5
      // 3f: invokevirtual okhttp3/internal/connection/RealConnection.isMultiplexed$okhttp ()Z
      // 42: ifne 48
      // 45: goto 52
      // 48: aload 5
      // 4a: aload 1
      // 4b: aload 3
      // 4c: invokevirtual okhttp3/internal/connection/RealConnection.isEligible$okhttp (Lokhttp3/Address;Ljava/util/List;)Z
      // 4f: ifne 5d
      // 52: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 55: astore 7
      // 57: aload 5
      // 59: monitorexit
      // 5a: goto 17
      // 5d: aload 2
      // 5e: aload 5
      // 60: invokevirtual okhttp3/internal/connection/RealCall.acquireConnectionNoEvents (Lokhttp3/internal/connection/RealConnection;)V
      // 63: aload 5
      // 65: monitorexit
      // 66: bipush 1
      // 67: ireturn
      // 68: astore 1
      // 69: aload 5
      // 6b: monitorexit
      // 6c: aload 1
      // 6d: athrow
      // 6e: bipush 0
      // 6f: ireturn
   }

   public fun cleanup(now: Long): Long {
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
      // 000: aconst_null
      // 001: astore 10
      // 003: aconst_null
      // 004: checkcast okhttp3/internal/connection/RealConnection
      // 007: astore 11
      // 009: aload 0
      // 00a: getfield okhttp3/internal/connection/RealConnectionPool.connections Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 00d: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.iterator ()Ljava/util/Iterator;
      // 010: astore 12
      // 012: bipush 0
      // 013: istore 4
      // 015: ldc2_w -9223372036854775808
      // 018: lstore 5
      // 01a: bipush 0
      // 01b: istore 3
      // 01c: aload 12
      // 01e: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 023: ifeq 084
      // 026: aload 12
      // 028: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 02d: checkcast okhttp3/internal/connection/RealConnection
      // 030: astore 11
      // 032: aload 11
      // 034: ldc_w "connection"
      // 037: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 03a: aload 11
      // 03c: monitorenter
      // 03d: aload 0
      // 03e: aload 11
      // 040: lload 1
      // 041: invokespecial okhttp3/internal/connection/RealConnectionPool.pruneAndGetAllocationCount (Lokhttp3/internal/connection/RealConnection;J)I
      // 044: ifle 04d
      // 047: iinc 3 1
      // 04a: goto 076
      // 04d: iinc 4 1
      // 050: lload 1
      // 051: aload 11
      // 053: invokevirtual okhttp3/internal/connection/RealConnection.getIdleAtNs$okhttp ()J
      // 056: lsub
      // 057: lstore 7
      // 059: lload 7
      // 05b: lload 5
      // 05d: lcmp
      // 05e: ifle 071
      // 061: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 064: astore 10
      // 066: aload 11
      // 068: astore 10
      // 06a: lload 7
      // 06c: lstore 5
      // 06e: goto 076
      // 071: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 074: astore 13
      // 076: aload 11
      // 078: monitorexit
      // 079: goto 01c
      // 07c: astore 10
      // 07e: aload 11
      // 080: monitorexit
      // 081: aload 10
      // 083: athrow
      // 084: aload 0
      // 085: getfield okhttp3/internal/connection/RealConnectionPool.keepAliveDurationNs J
      // 088: lstore 7
      // 08a: lload 5
      // 08c: lload 7
      // 08e: lcmp
      // 08f: ifge 0b4
      // 092: iload 4
      // 094: aload 0
      // 095: getfield okhttp3/internal/connection/RealConnectionPool.maxIdleConnections I
      // 098: if_icmple 09e
      // 09b: goto 0b4
      // 09e: iload 4
      // 0a0: ifle 0a9
      // 0a3: lload 7
      // 0a5: lload 5
      // 0a7: lsub
      // 0a8: lreturn
      // 0a9: iload 3
      // 0aa: ifle 0b0
      // 0ad: lload 7
      // 0af: lreturn
      // 0b0: ldc2_w -1
      // 0b3: lreturn
      // 0b4: aload 10
      // 0b6: ifnonnull 0bc
      // 0b9: invokestatic kotlin/jvm/internal/Intrinsics.throwNpe ()V
      // 0bc: aload 10
      // 0be: monitorenter
      // 0bf: aload 10
      // 0c1: invokevirtual okhttp3/internal/connection/RealConnection.getCalls ()Ljava/util/List;
      // 0c4: checkcast java/util/Collection
      // 0c7: invokeinterface java/util/Collection.isEmpty ()Z 1
      // 0cc: istore 9
      // 0ce: iload 9
      // 0d0: ifne 0d8
      // 0d3: aload 10
      // 0d5: monitorexit
      // 0d6: lconst_0
      // 0d7: lreturn
      // 0d8: aload 10
      // 0da: invokevirtual okhttp3/internal/connection/RealConnection.getIdleAtNs$okhttp ()J
      // 0dd: lstore 7
      // 0df: lload 7
      // 0e1: lload 5
      // 0e3: ladd
      // 0e4: lload 1
      // 0e5: lcmp
      // 0e6: ifeq 0ee
      // 0e9: aload 10
      // 0eb: monitorexit
      // 0ec: lconst_0
      // 0ed: lreturn
      // 0ee: aload 10
      // 0f0: bipush 1
      // 0f1: invokevirtual okhttp3/internal/connection/RealConnection.setNoNewExchanges (Z)V
      // 0f4: aload 0
      // 0f5: getfield okhttp3/internal/connection/RealConnectionPool.connections Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 0f8: aload 10
      // 0fa: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.remove (Ljava/lang/Object;)Z
      // 0fd: pop
      // 0fe: aload 10
      // 100: monitorexit
      // 101: aload 10
      // 103: invokevirtual okhttp3/internal/connection/RealConnection.socket ()Ljava/net/Socket;
      // 106: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/net/Socket;)V
      // 109: aload 0
      // 10a: getfield okhttp3/internal/connection/RealConnectionPool.connections Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 10d: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.isEmpty ()Z
      // 110: ifeq 11a
      // 113: aload 0
      // 114: getfield okhttp3/internal/connection/RealConnectionPool.cleanupQueue Lokhttp3/internal/concurrent/TaskQueue;
      // 117: invokevirtual okhttp3/internal/concurrent/TaskQueue.cancelAll ()V
      // 11a: lconst_0
      // 11b: lreturn
      // 11c: astore 11
      // 11e: aload 10
      // 120: monitorexit
      // 121: aload 11
      // 123: athrow
   }

   public fun connectionBecameIdle(connection: RealConnection): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "connection");
      if (Util.assertionsEnabled && !Thread.holdsLock(var1)) {
         val var4: StringBuilder = new StringBuilder("Thread ");
         val var3: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var3, "Thread.currentThread()");
         var4.append(var3.getName());
         var4.append(" MUST hold lock on ");
         var4.append(var1);
         throw (new AssertionError(var4.toString())) as java.lang.Throwable;
      } else {
         val var2: Boolean;
         if (!var1.getNoNewExchanges() && this.maxIdleConnections != 0) {
            TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
            var2 = false;
         } else {
            var1.setNoNewExchanges(true);
            this.connections.remove(var1);
            if (this.connections.isEmpty()) {
               this.cleanupQueue.cancelAll();
            }

            var2 = true;
         }

         return var2;
      }
   }

   public fun connectionCount(): Int {
      return this.connections.size();
   }

   public fun evictAll() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.initExprents(IfStatement.java:276)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:189)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:148)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield okhttp3/internal/connection/RealConnectionPool.connections Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 04: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.iterator ()Ljava/util/Iterator;
      // 07: astore 3
      // 08: aload 3
      // 09: ldc_w "connections.iterator()"
      // 0c: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 0f: aload 3
      // 10: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 15: ifeq 5e
      // 18: aload 3
      // 19: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 1e: checkcast okhttp3/internal/connection/RealConnection
      // 21: astore 2
      // 22: aload 2
      // 23: ldc_w "connection"
      // 26: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 29: aload 2
      // 2a: monitorenter
      // 2b: aload 2
      // 2c: invokevirtual okhttp3/internal/connection/RealConnection.getCalls ()Ljava/util/List;
      // 2f: invokeinterface java/util/List.isEmpty ()Z 1
      // 34: ifeq 4a
      // 37: aload 3
      // 38: invokeinterface java/util/Iterator.remove ()V 1
      // 3d: aload 2
      // 3e: bipush 1
      // 3f: invokevirtual okhttp3/internal/connection/RealConnection.setNoNewExchanges (Z)V
      // 42: aload 2
      // 43: invokevirtual okhttp3/internal/connection/RealConnection.socket ()Ljava/net/Socket;
      // 46: astore 1
      // 47: goto 4c
      // 4a: aconst_null
      // 4b: astore 1
      // 4c: aload 2
      // 4d: monitorexit
      // 4e: aload 1
      // 4f: ifnull 0f
      // 52: aload 1
      // 53: invokestatic okhttp3/internal/Util.closeQuietly (Ljava/net/Socket;)V
      // 56: goto 0f
      // 59: astore 1
      // 5a: aload 2
      // 5b: monitorexit
      // 5c: aload 1
      // 5d: athrow
      // 5e: aload 0
      // 5f: getfield okhttp3/internal/connection/RealConnectionPool.connections Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 62: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.isEmpty ()Z
      // 65: ifeq 6f
      // 68: aload 0
      // 69: getfield okhttp3/internal/connection/RealConnectionPool.cleanupQueue Lokhttp3/internal/concurrent/TaskQueue;
      // 6c: invokevirtual okhttp3/internal/concurrent/TaskQueue.cancelAll ()V
      // 6f: return
   }

   public fun idleConnectionCount(): Int {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.ClassCastException: class org.jetbrains.java.decompiler.modules.decompiler.exps.MonitorExprent cannot be cast to class org.jetbrains.java.decompiler.modules.decompiler.exps.IfExprent (org.jetbrains.java.decompiler.modules.decompiler.exps.MonitorExprent and org.jetbrains.java.decompiler.modules.decompiler.exps.IfExprent are in unnamed module of loader 'app')
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.initExprents(IfStatement.java:276)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:189)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:148)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield okhttp3/internal/connection/RealConnectionPool.connections Ljava/util/concurrent/ConcurrentLinkedQueue;
      // 04: checkcast java/lang/Iterable
      // 07: astore 4
      // 09: aload 4
      // 0b: instanceof java/util/Collection
      // 0e: istore 3
      // 0f: bipush 0
      // 10: istore 2
      // 11: bipush 0
      // 12: istore 1
      // 13: iload 3
      // 14: ifeq 27
      // 17: aload 4
      // 19: checkcast java/util/Collection
      // 1c: invokeinterface java/util/Collection.isEmpty ()Z 1
      // 21: ifeq 27
      // 24: goto 7f
      // 27: aload 4
      // 29: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 2e: astore 5
      // 30: iload 1
      // 31: istore 2
      // 32: aload 5
      // 34: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 39: ifeq 7f
      // 3c: aload 5
      // 3e: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 43: checkcast okhttp3/internal/connection/RealConnection
      // 46: astore 4
      // 48: aload 4
      // 4a: ldc_w "it"
      // 4d: invokestatic kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 50: aload 4
      // 52: monitorenter
      // 53: aload 4
      // 55: invokevirtual okhttp3/internal/connection/RealConnection.getCalls ()Ljava/util/List;
      // 58: invokeinterface java/util/List.isEmpty ()Z 1
      // 5d: istore 3
      // 5e: aload 4
      // 60: monitorexit
      // 61: iload 3
      // 62: ifeq 30
      // 65: iload 1
      // 66: bipush 1
      // 67: iadd
      // 68: istore 2
      // 69: iload 2
      // 6a: istore 1
      // 6b: iload 2
      // 6c: ifge 30
      // 6f: invokestatic kotlin/collections/CollectionsKt.throwCountOverflow ()V
      // 72: iload 2
      // 73: istore 1
      // 74: goto 30
      // 77: astore 5
      // 79: aload 4
      // 7b: monitorexit
      // 7c: aload 5
      // 7e: athrow
      // 7f: iload 2
      // 80: ireturn
   }

   public fun put(connection: RealConnection) {
      Intrinsics.checkParameterIsNotNull(var1, "connection");
      if (Util.assertionsEnabled && !Thread.holdsLock(var1)) {
         val var3: StringBuilder = new StringBuilder("Thread ");
         val var2: Thread = Thread.currentThread();
         Intrinsics.checkExpressionValueIsNotNull(var2, "Thread.currentThread()");
         var3.append(var2.getName());
         var3.append(" MUST hold lock on ");
         var3.append(var1);
         throw (new AssertionError(var3.toString())) as java.lang.Throwable;
      } else {
         this.connections.add(var1);
         TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
      }
   }

   public companion object {
      public fun get(connectionPool: ConnectionPool): RealConnectionPool {
         Intrinsics.checkParameterIsNotNull(var1, "connectionPool");
         return var1.getDelegate$okhttp();
      }
   }
}
