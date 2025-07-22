package com.it_nomads.fluttersecurestorage;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FlutterSecureStoragePlugin implements MethodChannel.MethodCallHandler, FlutterPlugin {
   private static final String TAG = "FlutterSecureStoragePl";
   private MethodChannel channel;
   private FlutterSecureStorage secureStorage;
   private HandlerThread workerThread;
   private Handler workerThreadHandler;

   private String getKeyFromCall(MethodCall var1) {
      Map var2 = (Map)var1.arguments;
      return this.secureStorage.addPrefixToKey((String)var2.get("key"));
   }

   private String getValueFromCall(MethodCall var1) {
      return (String)((Map)var1.arguments).get("value");
   }

   public void initInstance(BinaryMessenger var1, Context var2) {
      try {
         HashMap var4 = new HashMap();
         FlutterSecureStorage var3 = new FlutterSecureStorage(var2, var4);
         this.secureStorage = var3;
         HandlerThread var6 = new HandlerThread("com.it_nomads.fluttersecurestorage.worker");
         this.workerThread = var6;
         var6.start();
         Handler var7 = new Handler(this.workerThread.getLooper());
         this.workerThreadHandler = var7;
         MethodChannel var8 = new MethodChannel(var1, "plugins.it_nomads.com/flutter_secure_storage");
         this.channel = var8;
         var8.setMethodCallHandler(this);
      } catch (Exception var5) {
         Log.e("FlutterSecureStoragePl", "Registration failed", var5);
      }
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.initInstance(var1.getBinaryMessenger(), var1.getApplicationContext());
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
      if (this.channel != null) {
         this.workerThread.quitSafely();
         this.workerThread = null;
         this.channel.setMethodCallHandler(null);
         this.channel = null;
      }

      this.secureStorage = null;
   }

   @Override
   public void onMethodCall(MethodCall var1, MethodChannel.Result var2) {
      FlutterSecureStoragePlugin.MethodResultWrapper var3 = new FlutterSecureStoragePlugin.MethodResultWrapper(var2);
      this.workerThreadHandler.post(new FlutterSecureStoragePlugin.MethodRunner(this, var1, var3));
   }

   static class MethodResultWrapper implements MethodChannel.Result {
      private final Handler handler = new Handler(Looper.getMainLooper());
      private final MethodChannel.Result methodResult;

      MethodResultWrapper(MethodChannel.Result var1) {
         this.methodResult = var1;
      }

      @Override
      public void error(String var1, String var2, Object var3) {
         this.handler.post(new FlutterSecureStoragePlugin$MethodResultWrapper$$ExternalSyntheticLambda2(this, var1, var2, var3));
      }

      @Override
      public void notImplemented() {
         Handler var1 = this.handler;
         MethodChannel.Result var2 = this.methodResult;
         Objects.requireNonNull(var2);
         var1.post(new FlutterSecureStoragePlugin$MethodResultWrapper$$ExternalSyntheticLambda0(var2));
      }

      @Override
      public void success(Object var1) {
         this.handler.post(new FlutterSecureStoragePlugin$MethodResultWrapper$$ExternalSyntheticLambda1(this, var1));
      }
   }

   class MethodRunner implements Runnable {
      private final MethodCall call;
      private final MethodChannel.Result result;
      final FlutterSecureStoragePlugin this$0;

      MethodRunner(FlutterSecureStoragePlugin var1, MethodCall var2, MethodChannel.Result var3) {
         this.this$0 = var1;
         this.call = var2;
         this.result = var3;
      }

      private void handleException(Exception var1) {
         StringWriter var2 = new StringWriter();
         var1.printStackTrace(new PrintWriter(var2));
         this.result.error("Exception encountered", this.call.method, var2.toString());
      }

      @Override
      public void run() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
         //
         // Bytecode:
         // 000: bipush 0
         // 001: istore 1
         // 002: aload 0
         // 003: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 006: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$000 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;)Lcom/it_nomads/fluttersecurestorage/FlutterSecureStorage;
         // 009: aload 0
         // 00a: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.call Lio/flutter/plugin/common/MethodCall;
         // 00d: getfield io/flutter/plugin/common/MethodCall.arguments Ljava/lang/Object;
         // 010: checkcast java/util/Map
         // 013: ldc "options"
         // 015: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
         // 01a: checkcast java/util/Map
         // 01d: putfield com/it_nomads/fluttersecurestorage/FlutterSecureStorage.options Ljava/util/Map;
         // 020: aload 0
         // 021: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 024: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$000 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;)Lcom/it_nomads/fluttersecurestorage/FlutterSecureStorage;
         // 027: invokevirtual com/it_nomads/fluttersecurestorage/FlutterSecureStorage.ensureOptions ()V
         // 02a: aload 0
         // 02b: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 02e: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$000 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;)Lcom/it_nomads/fluttersecurestorage/FlutterSecureStorage;
         // 031: invokevirtual com/it_nomads/fluttersecurestorage/FlutterSecureStorage.getResetOnError ()Z
         // 034: istore 2
         // 035: aload 0
         // 036: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.call Lio/flutter/plugin/common/MethodCall;
         // 039: getfield io/flutter/plugin/common/MethodCall.method Ljava/lang/String;
         // 03c: astore 4
         // 03e: aload 4
         // 040: invokevirtual java/lang/String.hashCode ()I
         // 043: lookupswitch 57 6 -1335458389 133 -358737930 118 3496342 103 113399775 90 208013248 75 1080375339 60
         // 07c: goto 0d7
         // 07f: aload 4
         // 081: ldc "readAll"
         // 083: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
         // 086: ifeq 0d7
         // 089: bipush 2
         // 08a: istore 1
         // 08b: goto 0d9
         // 08e: aload 4
         // 090: ldc "containsKey"
         // 092: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
         // 095: ifeq 0d7
         // 098: bipush 3
         // 099: istore 1
         // 09a: goto 0d9
         // 09d: aload 4
         // 09f: ldc "write"
         // 0a1: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
         // 0a4: ifeq 0d7
         // 0a7: goto 0d9
         // 0aa: aload 4
         // 0ac: ldc "read"
         // 0ae: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
         // 0b1: ifeq 0d7
         // 0b4: bipush 1
         // 0b5: istore 1
         // 0b6: goto 0d9
         // 0b9: aload 4
         // 0bb: ldc "deleteAll"
         // 0bd: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
         // 0c0: ifeq 0d7
         // 0c3: bipush 5
         // 0c4: istore 1
         // 0c5: goto 0d9
         // 0c8: aload 4
         // 0ca: ldc "delete"
         // 0cc: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
         // 0cf: ifeq 0d7
         // 0d2: bipush 4
         // 0d3: istore 1
         // 0d4: goto 0d9
         // 0d7: bipush -1
         // 0d8: istore 1
         // 0d9: iload 1
         // 0da: ifeq 1c4
         // 0dd: iload 1
         // 0de: bipush 1
         // 0df: if_icmpeq 17f
         // 0e2: iload 1
         // 0e3: bipush 2
         // 0e4: if_icmpeq 169
         // 0e7: iload 1
         // 0e8: bipush 3
         // 0e9: if_icmpeq 13f
         // 0ec: iload 1
         // 0ed: bipush 4
         // 0ee: if_icmpeq 119
         // 0f1: iload 1
         // 0f2: bipush 5
         // 0f3: if_icmpeq 102
         // 0f6: aload 0
         // 0f7: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.result Lio/flutter/plugin/common/MethodChannel$Result;
         // 0fa: invokeinterface io/flutter/plugin/common/MethodChannel$Result.notImplemented ()V 1
         // 0ff: goto 254
         // 102: aload 0
         // 103: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 106: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$000 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;)Lcom/it_nomads/fluttersecurestorage/FlutterSecureStorage;
         // 109: invokevirtual com/it_nomads/fluttersecurestorage/FlutterSecureStorage.deleteAll ()V
         // 10c: aload 0
         // 10d: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.result Lio/flutter/plugin/common/MethodChannel$Result;
         // 110: aconst_null
         // 111: invokeinterface io/flutter/plugin/common/MethodChannel$Result.success (Ljava/lang/Object;)V 2
         // 116: goto 254
         // 119: aload 0
         // 11a: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 11d: aload 0
         // 11e: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.call Lio/flutter/plugin/common/MethodCall;
         // 121: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$100 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;Lio/flutter/plugin/common/MethodCall;)Ljava/lang/String;
         // 124: astore 4
         // 126: aload 0
         // 127: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 12a: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$000 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;)Lcom/it_nomads/fluttersecurestorage/FlutterSecureStorage;
         // 12d: aload 4
         // 12f: invokevirtual com/it_nomads/fluttersecurestorage/FlutterSecureStorage.delete (Ljava/lang/String;)V
         // 132: aload 0
         // 133: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.result Lio/flutter/plugin/common/MethodChannel$Result;
         // 136: aconst_null
         // 137: invokeinterface io/flutter/plugin/common/MethodChannel$Result.success (Ljava/lang/Object;)V 2
         // 13c: goto 254
         // 13f: aload 0
         // 140: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 143: aload 0
         // 144: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.call Lio/flutter/plugin/common/MethodCall;
         // 147: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$100 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;Lio/flutter/plugin/common/MethodCall;)Ljava/lang/String;
         // 14a: astore 4
         // 14c: aload 0
         // 14d: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 150: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$000 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;)Lcom/it_nomads/fluttersecurestorage/FlutterSecureStorage;
         // 153: aload 4
         // 155: invokevirtual com/it_nomads/fluttersecurestorage/FlutterSecureStorage.containsKey (Ljava/lang/String;)Z
         // 158: istore 3
         // 159: aload 0
         // 15a: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.result Lio/flutter/plugin/common/MethodChannel$Result;
         // 15d: iload 3
         // 15e: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
         // 161: invokeinterface io/flutter/plugin/common/MethodChannel$Result.success (Ljava/lang/Object;)V 2
         // 166: goto 254
         // 169: aload 0
         // 16a: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.result Lio/flutter/plugin/common/MethodChannel$Result;
         // 16d: aload 0
         // 16e: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 171: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$000 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;)Lcom/it_nomads/fluttersecurestorage/FlutterSecureStorage;
         // 174: invokevirtual com/it_nomads/fluttersecurestorage/FlutterSecureStorage.readAll ()Ljava/util/Map;
         // 177: invokeinterface io/flutter/plugin/common/MethodChannel$Result.success (Ljava/lang/Object;)V 2
         // 17c: goto 254
         // 17f: aload 0
         // 180: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 183: aload 0
         // 184: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.call Lio/flutter/plugin/common/MethodCall;
         // 187: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$100 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;Lio/flutter/plugin/common/MethodCall;)Ljava/lang/String;
         // 18a: astore 4
         // 18c: aload 0
         // 18d: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 190: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$000 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;)Lcom/it_nomads/fluttersecurestorage/FlutterSecureStorage;
         // 193: aload 4
         // 195: invokevirtual com/it_nomads/fluttersecurestorage/FlutterSecureStorage.containsKey (Ljava/lang/String;)Z
         // 198: ifeq 1b7
         // 19b: aload 0
         // 19c: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 19f: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$000 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;)Lcom/it_nomads/fluttersecurestorage/FlutterSecureStorage;
         // 1a2: aload 4
         // 1a4: invokevirtual com/it_nomads/fluttersecurestorage/FlutterSecureStorage.read (Ljava/lang/String;)Ljava/lang/String;
         // 1a7: astore 4
         // 1a9: aload 0
         // 1aa: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.result Lio/flutter/plugin/common/MethodChannel$Result;
         // 1ad: aload 4
         // 1af: invokeinterface io/flutter/plugin/common/MethodChannel$Result.success (Ljava/lang/Object;)V 2
         // 1b4: goto 254
         // 1b7: aload 0
         // 1b8: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.result Lio/flutter/plugin/common/MethodChannel$Result;
         // 1bb: aconst_null
         // 1bc: invokeinterface io/flutter/plugin/common/MethodChannel$Result.success (Ljava/lang/Object;)V 2
         // 1c1: goto 254
         // 1c4: aload 0
         // 1c5: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 1c8: aload 0
         // 1c9: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.call Lio/flutter/plugin/common/MethodCall;
         // 1cc: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$100 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;Lio/flutter/plugin/common/MethodCall;)Ljava/lang/String;
         // 1cf: astore 4
         // 1d1: aload 0
         // 1d2: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 1d5: aload 0
         // 1d6: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.call Lio/flutter/plugin/common/MethodCall;
         // 1d9: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$200 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;Lio/flutter/plugin/common/MethodCall;)Ljava/lang/String;
         // 1dc: astore 5
         // 1de: aload 5
         // 1e0: ifnull 1fe
         // 1e3: aload 0
         // 1e4: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 1e7: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$000 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;)Lcom/it_nomads/fluttersecurestorage/FlutterSecureStorage;
         // 1ea: aload 4
         // 1ec: aload 5
         // 1ee: invokevirtual com/it_nomads/fluttersecurestorage/FlutterSecureStorage.write (Ljava/lang/String;Ljava/lang/String;)V
         // 1f1: aload 0
         // 1f2: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.result Lio/flutter/plugin/common/MethodChannel$Result;
         // 1f5: aconst_null
         // 1f6: invokeinterface io/flutter/plugin/common/MethodChannel$Result.success (Ljava/lang/Object;)V 2
         // 1fb: goto 254
         // 1fe: aload 0
         // 1ff: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.result Lio/flutter/plugin/common/MethodChannel$Result;
         // 202: ldc "null"
         // 204: aconst_null
         // 205: aconst_null
         // 206: invokeinterface io/flutter/plugin/common/MethodChannel$Result.error (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V 4
         // 20b: goto 254
         // 20e: astore 4
         // 210: goto 217
         // 213: astore 4
         // 215: bipush 0
         // 216: istore 2
         // 217: iload 2
         // 218: ifeq 23e
         // 21b: aload 0
         // 21c: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.this$0 Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;
         // 21f: invokestatic com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin.access$000 (Lcom/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin;)Lcom/it_nomads/fluttersecurestorage/FlutterSecureStorage;
         // 222: invokevirtual com/it_nomads/fluttersecurestorage/FlutterSecureStorage.deleteAll ()V
         // 225: aload 0
         // 226: getfield com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.result Lio/flutter/plugin/common/MethodChannel$Result;
         // 229: ldc "Data has been reset"
         // 22b: invokeinterface io/flutter/plugin/common/MethodChannel$Result.success (Ljava/lang/Object;)V 2
         // 230: goto 254
         // 233: astore 4
         // 235: aload 0
         // 236: aload 4
         // 238: invokespecial com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.handleException (Ljava/lang/Exception;)V
         // 23b: goto 254
         // 23e: aload 0
         // 23f: aload 4
         // 241: invokespecial com/it_nomads/fluttersecurestorage/FlutterSecureStoragePlugin$MethodRunner.handleException (Ljava/lang/Exception;)V
         // 244: goto 254
         // 247: astore 4
         // 249: ldc "Creating sharedPrefs"
         // 24b: aload 4
         // 24d: invokevirtual java/io/FileNotFoundException.getLocalizedMessage ()Ljava/lang/String;
         // 250: invokestatic android/util/Log.i (Ljava/lang/String;Ljava/lang/String;)I
         // 253: pop
         // 254: return
      }
   }
}
