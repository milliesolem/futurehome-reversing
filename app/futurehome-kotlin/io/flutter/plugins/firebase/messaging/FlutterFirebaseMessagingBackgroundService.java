package io.flutter.plugins.firebase.messaging;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import io.flutter.embedding.engine.FlutterShellArgs;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FlutterFirebaseMessagingBackgroundService extends JobIntentService {
   private static final String TAG = "FLTFireMsgService";
   private static FlutterFirebaseMessagingBackgroundExecutor flutterBackgroundExecutor;
   private static final List<Intent> messagingQueue = Collections.synchronizedList(new LinkedList<>());

   public static void enqueueMessageProcessing(Context var0, Intent var1, boolean var2) {
      enqueueWork(var0, FlutterFirebaseMessagingBackgroundService.class, 2020, var1, var2);
   }

   static void onInitialized() {
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
      // 00: ldc "FLTFireMsgService"
      // 02: ldc "FlutterFirebaseMessagingBackgroundService started!"
      // 04: invokestatic android/util/Log.i (Ljava/lang/String;Ljava/lang/String;)I
      // 07: pop
      // 08: getstatic io/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundService.messagingQueue Ljava/util/List;
      // 0b: astore 0
      // 0c: aload 0
      // 0d: monitorenter
      // 0e: aload 0
      // 0f: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 14: astore 1
      // 15: aload 1
      // 16: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 1b: ifeq 33
      // 1e: aload 1
      // 1f: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 24: checkcast android/content/Intent
      // 27: astore 2
      // 28: getstatic io/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundService.flutterBackgroundExecutor Lio/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundExecutor;
      // 2b: aload 2
      // 2c: aconst_null
      // 2d: invokevirtual io/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundExecutor.executeDartCallbackInBackgroundIsolate (Landroid/content/Intent;Ljava/util/concurrent/CountDownLatch;)V
      // 30: goto 15
      // 33: getstatic io/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundService.messagingQueue Ljava/util/List;
      // 36: invokeinterface java/util/List.clear ()V 1
      // 3b: aload 0
      // 3c: monitorexit
      // 3d: return
      // 3e: astore 1
      // 3f: aload 0
      // 40: monitorexit
      // 41: aload 1
      // 42: athrow
   }

   public static void setCallbackDispatcher(long var0) {
      FlutterFirebaseMessagingBackgroundExecutor.setCallbackDispatcher(var0);
   }

   public static void setUserCallbackHandle(long var0) {
      FlutterFirebaseMessagingBackgroundExecutor.setUserCallbackHandle(var0);
   }

   public static void startBackgroundIsolate(long var0, FlutterShellArgs var2) {
      if (flutterBackgroundExecutor != null) {
         Log.w("FLTFireMsgService", "Attempted to start a duplicate background isolate. Returning...");
      } else {
         FlutterFirebaseMessagingBackgroundExecutor var3 = new FlutterFirebaseMessagingBackgroundExecutor();
         flutterBackgroundExecutor = var3;
         var3.startBackgroundIsolate(var0, var2);
      }
   }

   @Override
   public void onCreate() {
      super.onCreate();
      if (flutterBackgroundExecutor == null) {
         flutterBackgroundExecutor = new FlutterFirebaseMessagingBackgroundExecutor();
      }

      flutterBackgroundExecutor.startBackgroundIsolate();
   }

   @Override
   protected void onHandleWork(Intent param1) {
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
      // 00: getstatic io/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundService.flutterBackgroundExecutor Lio/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundExecutor;
      // 03: invokevirtual io/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundExecutor.isDartBackgroundHandlerRegistered ()Z
      // 06: ifne 12
      // 09: ldc "FLTFireMsgService"
      // 0b: ldc "A background message could not be handled in Dart as no onBackgroundMessage handler has been registered."
      // 0d: invokestatic android/util/Log.w (Ljava/lang/String;Ljava/lang/String;)I
      // 10: pop
      // 11: return
      // 12: getstatic io/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundService.messagingQueue Ljava/util/List;
      // 15: astore 2
      // 16: aload 2
      // 17: monitorenter
      // 18: getstatic io/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundService.flutterBackgroundExecutor Lio/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundExecutor;
      // 1b: invokevirtual io/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundExecutor.isNotRunning ()Z
      // 1e: ifeq 34
      // 21: ldc "FLTFireMsgService"
      // 23: ldc "Service has not yet started, messages will be queued."
      // 25: invokestatic android/util/Log.i (Ljava/lang/String;Ljava/lang/String;)I
      // 28: pop
      // 29: aload 2
      // 2a: aload 1
      // 2b: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 30: pop
      // 31: aload 2
      // 32: monitorexit
      // 33: return
      // 34: aload 2
      // 35: monitorexit
      // 36: new java/util/concurrent/CountDownLatch
      // 39: dup
      // 3a: bipush 1
      // 3b: invokespecial java/util/concurrent/CountDownLatch.<init> (I)V
      // 3e: astore 2
      // 3f: new android/os/Handler
      // 42: dup
      // 43: aload 0
      // 44: invokevirtual io/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundService.getMainLooper ()Landroid/os/Looper;
      // 47: invokespecial android/os/Handler.<init> (Landroid/os/Looper;)V
      // 4a: new io/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundService$$ExternalSyntheticLambda0
      // 4d: dup
      // 4e: aload 1
      // 4f: aload 2
      // 50: invokespecial io/flutter/plugins/firebase/messaging/FlutterFirebaseMessagingBackgroundService$$ExternalSyntheticLambda0.<init> (Landroid/content/Intent;Ljava/util/concurrent/CountDownLatch;)V
      // 53: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 56: pop
      // 57: aload 2
      // 58: invokevirtual java/util/concurrent/CountDownLatch.await ()V
      // 5b: goto 68
      // 5e: astore 1
      // 5f: ldc "FLTFireMsgService"
      // 61: ldc "Exception waiting to execute Dart callback"
      // 63: aload 1
      // 64: invokestatic android/util/Log.i (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      // 67: pop
      // 68: return
      // 69: astore 1
      // 6a: aload 2
      // 6b: monitorexit
      // 6c: aload 1
      // 6d: athrow
   }
}
