package io.flutter.embedding.engine.dart;

import io.flutter.FlutterInjector;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.util.TraceSection;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

class DartMessenger implements BinaryMessenger, PlatformMessageHandler {
   private static final String TAG = "DartMessenger";
   private Map<String, List<DartMessenger.BufferedMessageInfo>> bufferedMessages;
   private WeakHashMap<BinaryMessenger.TaskQueue, DartMessenger.DartMessengerTaskQueue> createdTaskQueues;
   private final AtomicBoolean enableBufferingIncomingMessages;
   private final FlutterJNI flutterJNI;
   private final Object handlersLock;
   private final Map<String, DartMessenger.HandlerInfo> messageHandlers = new HashMap<>();
   private int nextReplyId;
   private final Map<Integer, BinaryMessenger.BinaryReply> pendingReplies;
   private final DartMessenger.DartMessengerTaskQueue platformTaskQueue;
   private DartMessenger.TaskQueueFactory taskQueueFactory;

   DartMessenger(FlutterJNI var1) {
      this(var1, new DartMessenger.DefaultTaskQueueFactory());
   }

   DartMessenger(FlutterJNI var1, DartMessenger.TaskQueueFactory var2) {
      this.bufferedMessages = new HashMap<>();
      this.handlersLock = new Object();
      this.enableBufferingIncomingMessages = new AtomicBoolean(false);
      this.pendingReplies = new HashMap<>();
      this.nextReplyId = 1;
      this.platformTaskQueue = new PlatformTaskQueue();
      this.createdTaskQueues = new WeakHashMap<>();
      this.flutterJNI = var1;
      this.taskQueueFactory = var2;
   }

   private void dispatchMessageToQueue(String var1, DartMessenger.HandlerInfo var2, ByteBuffer var3, int var4, long var5) {
      DartMessenger.DartMessengerTaskQueue var7;
      if (var2 != null) {
         var7 = var2.taskQueue;
      } else {
         var7 = null;
      }

      StringBuilder var8 = new StringBuilder("PlatformChannel ScheduleHandler on ");
      var8.append(var1);
      TraceSection.beginAsyncSection(var8.toString(), var4);
      DartMessenger$$ExternalSyntheticLambda0 var10 = new DartMessenger$$ExternalSyntheticLambda0(this, var1, var4, var2, var3, var5);
      DartMessenger.DartMessengerTaskQueue var9 = var7;
      if (var7 == null) {
         var9 = this.platformTaskQueue;
      }

      var9.dispatch(var10);
   }

   private static void handleError(Error var0) {
      Thread var1 = Thread.currentThread();
      if (var1.getUncaughtExceptionHandler() != null) {
         var1.getUncaughtExceptionHandler().uncaughtException(var1, var0);
      } else {
         throw var0;
      }
   }

   private void invokeHandler(DartMessenger.HandlerInfo var1, ByteBuffer var2, int var3) {
      if (var1 != null) {
         try {
            Log.v("DartMessenger", "Deferring to registered handler to process message.");
            BinaryMessenger.BinaryMessageHandler var7 = var1.handler;
            DartMessenger.Reply var4 = new DartMessenger.Reply(this.flutterJNI, var3);
            var7.onMessage(var2, var4);
         } catch (Exception var5) {
            Log.e("DartMessenger", "Uncaught exception in binary message listener", var5);
            this.flutterJNI.invokePlatformMessageEmptyResponseCallback(var3);
         } catch (Error var6) {
            handleError(var6);
         }
      } else {
         Log.v("DartMessenger", "No registered handler for message. Responding to Dart with empty reply message.");
         this.flutterJNI.invokePlatformMessageEmptyResponseCallback(var3);
      }
   }

   @Override
   public void disableBufferingIncomingMessages() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/flutter/embedding/engine/dart/DartMessenger.handlersLock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/flutter/embedding/engine/dart/DartMessenger.enableBufferingIncomingMessages Ljava/util/concurrent/atomic/AtomicBoolean;
      // 0b: bipush 0
      // 0c: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 0f: aload 0
      // 10: getfield io/flutter/embedding/engine/dart/DartMessenger.bufferedMessages Ljava/util/Map;
      // 13: astore 3
      // 14: new java/util/HashMap
      // 17: astore 2
      // 18: aload 2
      // 19: invokespecial java/util/HashMap.<init> ()V
      // 1c: aload 0
      // 1d: aload 2
      // 1e: putfield io/flutter/embedding/engine/dart/DartMessenger.bufferedMessages Ljava/util/Map;
      // 21: aload 1
      // 22: monitorexit
      // 23: aload 3
      // 24: invokeinterface java/util/Map.entrySet ()Ljava/util/Set; 1
      // 29: invokeinterface java/util/Set.iterator ()Ljava/util/Iterator; 1
      // 2e: astore 1
      // 2f: aload 1
      // 30: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 35: ifeq 85
      // 38: aload 1
      // 39: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 3e: checkcast java/util/Map$Entry
      // 41: astore 3
      // 42: aload 3
      // 43: invokeinterface java/util/Map$Entry.getValue ()Ljava/lang/Object; 1
      // 48: checkcast java/util/List
      // 4b: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 50: astore 2
      // 51: aload 2
      // 52: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 57: ifeq 2f
      // 5a: aload 2
      // 5b: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 60: checkcast io/flutter/embedding/engine/dart/DartMessenger$BufferedMessageInfo
      // 63: astore 4
      // 65: aload 0
      // 66: aload 3
      // 67: invokeinterface java/util/Map$Entry.getKey ()Ljava/lang/Object; 1
      // 6c: checkcast java/lang/String
      // 6f: aconst_null
      // 70: aload 4
      // 72: getfield io/flutter/embedding/engine/dart/DartMessenger$BufferedMessageInfo.message Ljava/nio/ByteBuffer;
      // 75: aload 4
      // 77: getfield io/flutter/embedding/engine/dart/DartMessenger$BufferedMessageInfo.replyId I
      // 7a: aload 4
      // 7c: getfield io/flutter/embedding/engine/dart/DartMessenger$BufferedMessageInfo.messageData J
      // 7f: invokespecial io/flutter/embedding/engine/dart/DartMessenger.dispatchMessageToQueue (Ljava/lang/String;Lio/flutter/embedding/engine/dart/DartMessenger$HandlerInfo;Ljava/nio/ByteBuffer;IJ)V
      // 82: goto 51
      // 85: return
      // 86: astore 2
      // 87: aload 1
      // 88: monitorexit
      // 89: aload 2
      // 8a: athrow
   }

   @Override
   public void enableBufferingIncomingMessages() {
      this.enableBufferingIncomingMessages.set(true);
   }

   public int getPendingChannelResponseCount() {
      return this.pendingReplies.size();
   }

   @Override
   public void handleMessageFromDart(String param1, ByteBuffer param2, int param3, long param4) {
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
      // 00: new java/lang/StringBuilder
      // 03: dup
      // 04: ldc_w "Received message from Dart over channel '"
      // 07: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 0a: astore 7
      // 0c: aload 7
      // 0e: aload 1
      // 0f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 12: pop
      // 13: aload 7
      // 15: ldc_w "'"
      // 18: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1b: pop
      // 1c: ldc "DartMessenger"
      // 1e: aload 7
      // 20: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 23: invokestatic io/flutter/Log.v (Ljava/lang/String;Ljava/lang/String;)V
      // 26: aload 0
      // 27: getfield io/flutter/embedding/engine/dart/DartMessenger.handlersLock Ljava/lang/Object;
      // 2a: astore 7
      // 2c: aload 7
      // 2e: monitorenter
      // 2f: aload 0
      // 30: getfield io/flutter/embedding/engine/dart/DartMessenger.messageHandlers Ljava/util/Map;
      // 33: aload 1
      // 34: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 39: checkcast io/flutter/embedding/engine/dart/DartMessenger$HandlerInfo
      // 3c: astore 8
      // 3e: aload 0
      // 3f: getfield io/flutter/embedding/engine/dart/DartMessenger.enableBufferingIncomingMessages Ljava/util/concurrent/atomic/AtomicBoolean;
      // 42: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 45: ifeq 53
      // 48: aload 8
      // 4a: ifnonnull 53
      // 4d: bipush 1
      // 4e: istore 6
      // 50: goto 56
      // 53: bipush 0
      // 54: istore 6
      // 56: iload 6
      // 58: ifeq aa
      // 5b: aload 0
      // 5c: getfield io/flutter/embedding/engine/dart/DartMessenger.bufferedMessages Ljava/util/Map;
      // 5f: aload 1
      // 60: invokeinterface java/util/Map.containsKey (Ljava/lang/Object;)Z 2
      // 65: ifne 83
      // 68: aload 0
      // 69: getfield io/flutter/embedding/engine/dart/DartMessenger.bufferedMessages Ljava/util/Map;
      // 6c: astore 10
      // 6e: new java/util/LinkedList
      // 71: astore 9
      // 73: aload 9
      // 75: invokespecial java/util/LinkedList.<init> ()V
      // 78: aload 10
      // 7a: aload 1
      // 7b: aload 9
      // 7d: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 82: pop
      // 83: aload 0
      // 84: getfield io/flutter/embedding/engine/dart/DartMessenger.bufferedMessages Ljava/util/Map;
      // 87: aload 1
      // 88: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 8d: checkcast java/util/List
      // 90: astore 10
      // 92: new io/flutter/embedding/engine/dart/DartMessenger$BufferedMessageInfo
      // 95: astore 9
      // 97: aload 9
      // 99: aload 2
      // 9a: iload 3
      // 9b: lload 4
      // 9d: invokespecial io/flutter/embedding/engine/dart/DartMessenger$BufferedMessageInfo.<init> (Ljava/nio/ByteBuffer;IJ)V
      // a0: aload 10
      // a2: aload 9
      // a4: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // a9: pop
      // aa: aload 7
      // ac: monitorexit
      // ad: iload 6
      // af: ifne bd
      // b2: aload 0
      // b3: aload 1
      // b4: aload 8
      // b6: aload 2
      // b7: iload 3
      // b8: lload 4
      // ba: invokespecial io/flutter/embedding/engine/dart/DartMessenger.dispatchMessageToQueue (Ljava/lang/String;Lio/flutter/embedding/engine/dart/DartMessenger$HandlerInfo;Ljava/nio/ByteBuffer;IJ)V
      // bd: return
      // be: astore 1
      // bf: aload 7
      // c1: monitorexit
      // c2: aload 1
      // c3: athrow
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public void handlePlatformMessageResponse(int var1, ByteBuffer var2) {
      Log.v("DartMessenger", "Received message reply from Dart.");
      BinaryMessenger.BinaryReply var3 = this.pendingReplies.remove(var1);
      if (var3 != null) {
         try {
            Log.v("DartMessenger", "Invoking registered callback for reply from Dart.");
            var3.reply(var2);
         } catch (Exception var6) {
            Log.e("DartMessenger", "Uncaught exception in binary message reply handler", var6);
            return;
         } catch (Error var7) {
            handleError(var7);
            return;
         }

         if (var2 != null) {
            try {
               if (var2.isDirect()) {
                  ((Buffer)var2).limit(0);
               }
            } catch (Exception var4) {
               Log.e("DartMessenger", "Uncaught exception in binary message reply handler", var4);
            } catch (Error var5) {
               handleError(var5);
            }
         }
      }
   }

   @Override
   public BinaryMessenger.TaskQueue makeBackgroundTaskQueue(BinaryMessenger.TaskQueueOptions var1) {
      DartMessenger.DartMessengerTaskQueue var3 = this.taskQueueFactory.makeBackgroundTaskQueue(var1);
      DartMessenger.TaskQueueToken var2 = new DartMessenger.TaskQueueToken();
      this.createdTaskQueues.put(var2, var3);
      return var2;
   }

   @Override
   public void send(String var1, ByteBuffer var2) {
      StringBuilder var3 = new StringBuilder("Sending message over channel '");
      var3.append(var1);
      var3.append("'");
      Log.v("DartMessenger", var3.toString());
      this.send(var1, var2, null);
   }

   @Override
   public void send(String param1, ByteBuffer param2, BinaryMessenger.BinaryReply param3) {
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
      // 00: new java/lang/StringBuilder
      // 03: dup
      // 04: ldc_w "DartMessenger#send on "
      // 07: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 0a: astore 5
      // 0c: aload 5
      // 0e: aload 1
      // 0f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 12: pop
      // 13: aload 5
      // 15: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 18: invokestatic io/flutter/util/TraceSection.scoped (Ljava/lang/String;)Lio/flutter/util/TraceSection;
      // 1b: astore 5
      // 1d: new java/lang/StringBuilder
      // 20: astore 6
      // 22: aload 6
      // 24: ldc_w "Sending message with callback over channel '"
      // 27: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 2a: aload 6
      // 2c: aload 1
      // 2d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 30: pop
      // 31: aload 6
      // 33: ldc_w "'"
      // 36: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 39: pop
      // 3a: ldc "DartMessenger"
      // 3c: aload 6
      // 3e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 41: invokestatic io/flutter/Log.v (Ljava/lang/String;Ljava/lang/String;)V
      // 44: aload 0
      // 45: getfield io/flutter/embedding/engine/dart/DartMessenger.nextReplyId I
      // 48: istore 4
      // 4a: aload 0
      // 4b: iload 4
      // 4d: bipush 1
      // 4e: iadd
      // 4f: putfield io/flutter/embedding/engine/dart/DartMessenger.nextReplyId I
      // 52: aload 3
      // 53: ifnull 66
      // 56: aload 0
      // 57: getfield io/flutter/embedding/engine/dart/DartMessenger.pendingReplies Ljava/util/Map;
      // 5a: iload 4
      // 5c: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 5f: aload 3
      // 60: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 65: pop
      // 66: aload 2
      // 67: ifnonnull 77
      // 6a: aload 0
      // 6b: getfield io/flutter/embedding/engine/dart/DartMessenger.flutterJNI Lio/flutter/embedding/engine/FlutterJNI;
      // 6e: aload 1
      // 6f: iload 4
      // 71: invokevirtual io/flutter/embedding/engine/FlutterJNI.dispatchEmptyPlatformMessage (Ljava/lang/String;I)V
      // 74: goto 86
      // 77: aload 0
      // 78: getfield io/flutter/embedding/engine/dart/DartMessenger.flutterJNI Lio/flutter/embedding/engine/FlutterJNI;
      // 7b: aload 1
      // 7c: aload 2
      // 7d: aload 2
      // 7e: invokevirtual java/nio/ByteBuffer.position ()I
      // 81: iload 4
      // 83: invokevirtual io/flutter/embedding/engine/FlutterJNI.dispatchPlatformMessage (Ljava/lang/String;Ljava/nio/ByteBuffer;II)V
      // 86: aload 5
      // 88: ifnull 90
      // 8b: aload 5
      // 8d: invokevirtual io/flutter/util/TraceSection.close ()V
      // 90: return
      // 91: astore 1
      // 92: aload 5
      // 94: ifnull a5
      // 97: aload 5
      // 99: invokevirtual io/flutter/util/TraceSection.close ()V
      // 9c: goto a5
      // 9f: astore 2
      // a0: aload 1
      // a1: aload 2
      // a2: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // a5: aload 1
      // a6: athrow
   }

   @Override
   public void setMessageHandler(String var1, BinaryMessenger.BinaryMessageHandler var2) {
      this.setMessageHandler(var1, var2, null);
   }

   @Override
   public void setMessageHandler(String param1, BinaryMessenger.BinaryMessageHandler param2, BinaryMessenger.TaskQueue param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 000: aload 2
      // 001: ifnonnull 040
      // 004: new java/lang/StringBuilder
      // 007: dup
      // 008: ldc_w "Removing handler for channel '"
      // 00b: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 00e: astore 2
      // 00f: aload 2
      // 010: aload 1
      // 011: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 014: pop
      // 015: aload 2
      // 016: ldc_w "'"
      // 019: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 01c: pop
      // 01d: ldc "DartMessenger"
      // 01f: aload 2
      // 020: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 023: invokestatic io/flutter/Log.v (Ljava/lang/String;Ljava/lang/String;)V
      // 026: aload 0
      // 027: getfield io/flutter/embedding/engine/dart/DartMessenger.handlersLock Ljava/lang/Object;
      // 02a: astore 2
      // 02b: aload 2
      // 02c: monitorenter
      // 02d: aload 0
      // 02e: getfield io/flutter/embedding/engine/dart/DartMessenger.messageHandlers Ljava/util/Map;
      // 031: aload 1
      // 032: invokeinterface java/util/Map.remove (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 037: pop
      // 038: aload 2
      // 039: monitorexit
      // 03a: return
      // 03b: astore 1
      // 03c: aload 2
      // 03d: monitorexit
      // 03e: aload 1
      // 03f: athrow
      // 040: aload 3
      // 041: ifnull 062
      // 044: aload 0
      // 045: getfield io/flutter/embedding/engine/dart/DartMessenger.createdTaskQueues Ljava/util/WeakHashMap;
      // 048: aload 3
      // 049: invokevirtual java/util/WeakHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 04c: checkcast io/flutter/embedding/engine/dart/DartMessenger$DartMessengerTaskQueue
      // 04f: astore 3
      // 050: aload 3
      // 051: ifnull 057
      // 054: goto 064
      // 057: new java/lang/IllegalArgumentException
      // 05a: dup
      // 05b: ldc_w "Unrecognized TaskQueue, use BinaryMessenger to create your TaskQueue (ex makeBackgroundTaskQueue)."
      // 05e: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 061: athrow
      // 062: aconst_null
      // 063: astore 3
      // 064: new java/lang/StringBuilder
      // 067: dup
      // 068: ldc_w "Setting handler for channel '"
      // 06b: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 06e: astore 4
      // 070: aload 4
      // 072: aload 1
      // 073: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 076: pop
      // 077: aload 4
      // 079: ldc_w "'"
      // 07c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 07f: pop
      // 080: ldc "DartMessenger"
      // 082: aload 4
      // 084: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 087: invokestatic io/flutter/Log.v (Ljava/lang/String;Ljava/lang/String;)V
      // 08a: aload 0
      // 08b: getfield io/flutter/embedding/engine/dart/DartMessenger.handlersLock Ljava/lang/Object;
      // 08e: astore 4
      // 090: aload 4
      // 092: monitorenter
      // 093: aload 0
      // 094: getfield io/flutter/embedding/engine/dart/DartMessenger.messageHandlers Ljava/util/Map;
      // 097: astore 6
      // 099: new io/flutter/embedding/engine/dart/DartMessenger$HandlerInfo
      // 09c: astore 5
      // 09e: aload 5
      // 0a0: aload 2
      // 0a1: aload 3
      // 0a2: invokespecial io/flutter/embedding/engine/dart/DartMessenger$HandlerInfo.<init> (Lio/flutter/plugin/common/BinaryMessenger$BinaryMessageHandler;Lio/flutter/embedding/engine/dart/DartMessenger$DartMessengerTaskQueue;)V
      // 0a5: aload 6
      // 0a7: aload 1
      // 0a8: aload 5
      // 0aa: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 0af: pop
      // 0b0: aload 0
      // 0b1: getfield io/flutter/embedding/engine/dart/DartMessenger.bufferedMessages Ljava/util/Map;
      // 0b4: aload 1
      // 0b5: invokeinterface java/util/Map.remove (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 0ba: checkcast java/util/List
      // 0bd: astore 2
      // 0be: aload 2
      // 0bf: ifnonnull 0c6
      // 0c2: aload 4
      // 0c4: monitorexit
      // 0c5: return
      // 0c6: aload 4
      // 0c8: monitorexit
      // 0c9: aload 2
      // 0ca: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 0cf: astore 3
      // 0d0: aload 3
      // 0d1: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0d6: ifeq 104
      // 0d9: aload 3
      // 0da: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0df: checkcast io/flutter/embedding/engine/dart/DartMessenger$BufferedMessageInfo
      // 0e2: astore 2
      // 0e3: aload 0
      // 0e4: aload 1
      // 0e5: aload 0
      // 0e6: getfield io/flutter/embedding/engine/dart/DartMessenger.messageHandlers Ljava/util/Map;
      // 0e9: aload 1
      // 0ea: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 0ef: checkcast io/flutter/embedding/engine/dart/DartMessenger$HandlerInfo
      // 0f2: aload 2
      // 0f3: getfield io/flutter/embedding/engine/dart/DartMessenger$BufferedMessageInfo.message Ljava/nio/ByteBuffer;
      // 0f6: aload 2
      // 0f7: getfield io/flutter/embedding/engine/dart/DartMessenger$BufferedMessageInfo.replyId I
      // 0fa: aload 2
      // 0fb: getfield io/flutter/embedding/engine/dart/DartMessenger$BufferedMessageInfo.messageData J
      // 0fe: invokespecial io/flutter/embedding/engine/dart/DartMessenger.dispatchMessageToQueue (Ljava/lang/String;Lio/flutter/embedding/engine/dart/DartMessenger$HandlerInfo;Ljava/nio/ByteBuffer;IJ)V
      // 101: goto 0d0
      // 104: return
      // 105: astore 1
      // 106: aload 4
      // 108: monitorexit
      // 109: aload 1
      // 10a: athrow
   }

   private static class BufferedMessageInfo {
      public final ByteBuffer message;
      long messageData;
      int replyId;

      BufferedMessageInfo(ByteBuffer var1, int var2, long var3) {
         this.message = var1;
         this.replyId = var2;
         this.messageData = var3;
      }
   }

   static class ConcurrentTaskQueue implements DartMessenger.DartMessengerTaskQueue {
      private final ExecutorService executor;

      ConcurrentTaskQueue(ExecutorService var1) {
         this.executor = var1;
      }

      @Override
      public void dispatch(Runnable var1) {
         this.executor.execute(var1);
      }
   }

   interface DartMessengerTaskQueue {
      void dispatch(Runnable var1);
   }

   private static class DefaultTaskQueueFactory implements DartMessenger.TaskQueueFactory {
      ExecutorService executorService = FlutterInjector.instance().executorService();

      DefaultTaskQueueFactory() {
      }

      @Override
      public DartMessenger.DartMessengerTaskQueue makeBackgroundTaskQueue(BinaryMessenger.TaskQueueOptions var1) {
         return (DartMessenger.DartMessengerTaskQueue)(var1.getIsSerial()
            ? new DartMessenger.SerialTaskQueue(this.executorService)
            : new DartMessenger.ConcurrentTaskQueue(this.executorService));
      }
   }

   private static class HandlerInfo {
      public final BinaryMessenger.BinaryMessageHandler handler;
      public final DartMessenger.DartMessengerTaskQueue taskQueue;

      HandlerInfo(BinaryMessenger.BinaryMessageHandler var1, DartMessenger.DartMessengerTaskQueue var2) {
         this.handler = var1;
         this.taskQueue = var2;
      }
   }

   static class Reply implements BinaryMessenger.BinaryReply {
      private final AtomicBoolean done = new AtomicBoolean(false);
      private final FlutterJNI flutterJNI;
      private final int replyId;

      Reply(FlutterJNI var1, int var2) {
         this.flutterJNI = var1;
         this.replyId = var2;
      }

      @Override
      public void reply(ByteBuffer var1) {
         if (!this.done.getAndSet(true)) {
            if (var1 == null) {
               this.flutterJNI.invokePlatformMessageEmptyResponseCallback(this.replyId);
            } else {
               this.flutterJNI.invokePlatformMessageResponseCallback(this.replyId, var1, var1.position());
            }
         } else {
            throw new IllegalStateException("Reply already submitted");
         }
      }
   }

   static class SerialTaskQueue implements DartMessenger.DartMessengerTaskQueue {
      private final ExecutorService executor;
      private final AtomicBoolean isRunning;
      private final ConcurrentLinkedQueue<Runnable> queue;

      SerialTaskQueue(ExecutorService var1) {
         this.executor = var1;
         this.queue = new ConcurrentLinkedQueue<>();
         this.isRunning = new AtomicBoolean(false);
      }

      private void flush() {
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
         // 01: getfield io/flutter/embedding/engine/dart/DartMessenger$SerialTaskQueue.isRunning Ljava/util/concurrent/atomic/AtomicBoolean;
         // 04: bipush 0
         // 05: bipush 1
         // 06: invokevirtual java/util/concurrent/atomic/AtomicBoolean.compareAndSet (ZZ)Z
         // 09: ifeq 6d
         // 0c: aload 0
         // 0d: getfield io/flutter/embedding/engine/dart/DartMessenger$SerialTaskQueue.queue Ljava/util/concurrent/ConcurrentLinkedQueue;
         // 10: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.poll ()Ljava/lang/Object;
         // 13: checkcast java/lang/Runnable
         // 16: astore 1
         // 17: aload 1
         // 18: ifnull 21
         // 1b: aload 1
         // 1c: invokeinterface java/lang/Runnable.run ()V 1
         // 21: aload 0
         // 22: getfield io/flutter/embedding/engine/dart/DartMessenger$SerialTaskQueue.isRunning Ljava/util/concurrent/atomic/AtomicBoolean;
         // 25: bipush 0
         // 26: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
         // 29: aload 0
         // 2a: getfield io/flutter/embedding/engine/dart/DartMessenger$SerialTaskQueue.queue Ljava/util/concurrent/ConcurrentLinkedQueue;
         // 2d: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.isEmpty ()Z
         // 30: ifne 6d
         // 33: aload 0
         // 34: getfield io/flutter/embedding/engine/dart/DartMessenger$SerialTaskQueue.executor Ljava/util/concurrent/ExecutorService;
         // 37: new io/flutter/embedding/engine/dart/DartMessenger$SerialTaskQueue$$ExternalSyntheticLambda1
         // 3a: dup
         // 3b: aload 0
         // 3c: invokespecial io/flutter/embedding/engine/dart/DartMessenger$SerialTaskQueue$$ExternalSyntheticLambda1.<init> (Lio/flutter/embedding/engine/dart/DartMessenger$SerialTaskQueue;)V
         // 3f: invokeinterface java/util/concurrent/ExecutorService.execute (Ljava/lang/Runnable;)V 2
         // 44: goto 6d
         // 47: astore 1
         // 48: aload 0
         // 49: getfield io/flutter/embedding/engine/dart/DartMessenger$SerialTaskQueue.isRunning Ljava/util/concurrent/atomic/AtomicBoolean;
         // 4c: bipush 0
         // 4d: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
         // 50: aload 0
         // 51: getfield io/flutter/embedding/engine/dart/DartMessenger$SerialTaskQueue.queue Ljava/util/concurrent/ConcurrentLinkedQueue;
         // 54: invokevirtual java/util/concurrent/ConcurrentLinkedQueue.isEmpty ()Z
         // 57: ifne 6b
         // 5a: aload 0
         // 5b: getfield io/flutter/embedding/engine/dart/DartMessenger$SerialTaskQueue.executor Ljava/util/concurrent/ExecutorService;
         // 5e: new io/flutter/embedding/engine/dart/DartMessenger$SerialTaskQueue$$ExternalSyntheticLambda1
         // 61: dup
         // 62: aload 0
         // 63: invokespecial io/flutter/embedding/engine/dart/DartMessenger$SerialTaskQueue$$ExternalSyntheticLambda1.<init> (Lio/flutter/embedding/engine/dart/DartMessenger$SerialTaskQueue;)V
         // 66: invokeinterface java/util/concurrent/ExecutorService.execute (Ljava/lang/Runnable;)V 2
         // 6b: aload 1
         // 6c: athrow
         // 6d: return
      }

      @Override
      public void dispatch(Runnable var1) {
         this.queue.add(var1);
         this.executor.execute(new DartMessenger$SerialTaskQueue$$ExternalSyntheticLambda0(this));
      }
   }

   interface TaskQueueFactory {
      DartMessenger.DartMessengerTaskQueue makeBackgroundTaskQueue(BinaryMessenger.TaskQueueOptions var1);
   }

   private static class TaskQueueToken implements BinaryMessenger.TaskQueue {
      private TaskQueueToken() {
      }
   }
}
