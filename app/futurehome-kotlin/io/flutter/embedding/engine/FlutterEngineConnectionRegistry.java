package io.flutter.embedding.engine;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import io.flutter.Log;
import io.flutter.embedding.android.ExclusiveAppComponent;
import io.flutter.embedding.engine.loader.FlutterLoader;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.PluginRegistry;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityControlSurface;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.broadcastreceiver.BroadcastReceiverAware;
import io.flutter.embedding.engine.plugins.broadcastreceiver.BroadcastReceiverControlSurface;
import io.flutter.embedding.engine.plugins.broadcastreceiver.BroadcastReceiverPluginBinding;
import io.flutter.embedding.engine.plugins.contentprovider.ContentProviderAware;
import io.flutter.embedding.engine.plugins.contentprovider.ContentProviderControlSurface;
import io.flutter.embedding.engine.plugins.contentprovider.ContentProviderPluginBinding;
import io.flutter.embedding.engine.plugins.lifecycle.HiddenLifecycleReference;
import io.flutter.embedding.engine.plugins.service.ServiceAware;
import io.flutter.embedding.engine.plugins.service.ServiceControlSurface;
import io.flutter.embedding.engine.plugins.service.ServicePluginBinding;
import io.flutter.util.TraceSection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class FlutterEngineConnectionRegistry
   implements PluginRegistry,
   ActivityControlSurface,
   ServiceControlSurface,
   BroadcastReceiverControlSurface,
   ContentProviderControlSurface {
   private static final String TAG = "FlutterEngineCxnRegstry";
   private final Map<Class<? extends FlutterPlugin>, ActivityAware> activityAwarePlugins;
   private FlutterEngineConnectionRegistry.FlutterEngineActivityPluginBinding activityPluginBinding;
   private BroadcastReceiver broadcastReceiver;
   private final Map<Class<? extends FlutterPlugin>, BroadcastReceiverAware> broadcastReceiverAwarePlugins;
   private FlutterEngineConnectionRegistry.FlutterEngineBroadcastReceiverPluginBinding broadcastReceiverPluginBinding;
   private ContentProvider contentProvider;
   private final Map<Class<? extends FlutterPlugin>, ContentProviderAware> contentProviderAwarePlugins;
   private FlutterEngineConnectionRegistry.FlutterEngineContentProviderPluginBinding contentProviderPluginBinding;
   private ExclusiveAppComponent<Activity> exclusiveActivity;
   private final FlutterEngine flutterEngine;
   private boolean isWaitingForActivityReattachment;
   private final FlutterPlugin.FlutterPluginBinding pluginBinding;
   private final Map<Class<? extends FlutterPlugin>, FlutterPlugin> plugins = new HashMap<>();
   private Service service;
   private final Map<Class<? extends FlutterPlugin>, ServiceAware> serviceAwarePlugins;
   private FlutterEngineConnectionRegistry.FlutterEngineServicePluginBinding servicePluginBinding;

   FlutterEngineConnectionRegistry(Context var1, FlutterEngine var2, FlutterLoader var3, FlutterEngineGroup var4) {
      this.activityAwarePlugins = new HashMap<>();
      this.isWaitingForActivityReattachment = false;
      this.serviceAwarePlugins = new HashMap<>();
      this.broadcastReceiverAwarePlugins = new HashMap<>();
      this.contentProviderAwarePlugins = new HashMap<>();
      this.flutterEngine = var2;
      this.pluginBinding = new FlutterPlugin.FlutterPluginBinding(
         var1,
         var2,
         var2.getDartExecutor(),
         var2.getRenderer(),
         var2.getPlatformViewsController().getRegistry(),
         new FlutterEngineConnectionRegistry.DefaultFlutterAssets(var3),
         var4
      );
   }

   private void attachToActivityInternal(Activity var1, Lifecycle var2) {
      this.activityPluginBinding = new FlutterEngineConnectionRegistry.FlutterEngineActivityPluginBinding(var1, var2);
      boolean var3;
      if (var1.getIntent() != null) {
         var3 = var1.getIntent().getBooleanExtra("enable-software-rendering", false);
      } else {
         var3 = false;
      }

      this.flutterEngine.getPlatformViewsController().setSoftwareRendering(var3);
      this.flutterEngine.getPlatformViewsController().attach(var1, this.flutterEngine.getRenderer(), this.flutterEngine.getDartExecutor());

      for (ActivityAware var5 : this.activityAwarePlugins.values()) {
         if (this.isWaitingForActivityReattachment) {
            var5.onReattachedToActivityForConfigChanges(this.activityPluginBinding);
         } else {
            var5.onAttachedToActivity(this.activityPluginBinding);
         }
      }

      this.isWaitingForActivityReattachment = false;
   }

   private Activity attachedActivity() {
      ExclusiveAppComponent var1 = this.exclusiveActivity;
      Activity var2;
      if (var1 != null) {
         var2 = (Activity)var1.getAppComponent();
      } else {
         var2 = null;
      }

      return var2;
   }

   private void detachFromActivityInternal() {
      this.flutterEngine.getPlatformViewsController().detach();
      this.exclusiveActivity = null;
      this.activityPluginBinding = null;
   }

   private void detachFromAppComponent() {
      if (this.isAttachedToActivity()) {
         this.detachFromActivity();
      } else if (this.isAttachedToService()) {
         this.detachFromService();
      } else if (this.isAttachedToBroadcastReceiver()) {
         this.detachFromBroadcastReceiver();
      } else if (this.isAttachedToContentProvider()) {
         this.detachFromContentProvider();
      }
   }

   private boolean isAttachedToActivity() {
      boolean var1;
      if (this.exclusiveActivity != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean isAttachedToBroadcastReceiver() {
      boolean var1;
      if (this.broadcastReceiver != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean isAttachedToContentProvider() {
      boolean var1;
      if (this.contentProvider != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean isAttachedToService() {
      boolean var1;
      if (this.service != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void add(FlutterPlugin param1) {
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
      // 000: new java/lang/StringBuilder
      // 003: dup
      // 004: ldc "FlutterEngineConnectionRegistry#add "
      // 006: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 009: astore 3
      // 00a: aload 3
      // 00b: aload 1
      // 00c: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 00f: invokevirtual java/lang/Class.getSimpleName ()Ljava/lang/String;
      // 012: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 015: pop
      // 016: aload 3
      // 017: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 01a: invokestatic io/flutter/util/TraceSection.scoped (Ljava/lang/String;)Lio/flutter/util/TraceSection;
      // 01d: astore 3
      // 01e: aload 0
      // 01f: aload 1
      // 020: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 023: invokevirtual io/flutter/embedding/engine/FlutterEngineConnectionRegistry.has (Ljava/lang/Class;)Z
      // 026: istore 2
      // 027: iload 2
      // 028: ifeq 06e
      // 02b: new java/lang/StringBuilder
      // 02e: astore 4
      // 030: aload 4
      // 032: ldc_w "Attempted to register plugin ("
      // 035: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 038: aload 4
      // 03a: aload 1
      // 03b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 03e: pop
      // 03f: aload 4
      // 041: ldc_w ") but it was already registered with this FlutterEngine ("
      // 044: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 047: pop
      // 048: aload 4
      // 04a: aload 0
      // 04b: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.flutterEngine Lio/flutter/embedding/engine/FlutterEngine;
      // 04e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 051: pop
      // 052: aload 4
      // 054: ldc_w ")."
      // 057: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 05a: pop
      // 05b: ldc "FlutterEngineCxnRegstry"
      // 05d: aload 4
      // 05f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 062: invokestatic io/flutter/Log.w (Ljava/lang/String;Ljava/lang/String;)V
      // 065: aload 3
      // 066: ifnull 06d
      // 069: aload 3
      // 06a: invokevirtual io/flutter/util/TraceSection.close ()V
      // 06d: return
      // 06e: new java/lang/StringBuilder
      // 071: astore 4
      // 073: aload 4
      // 075: ldc_w "Adding plugin: "
      // 078: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 07b: aload 4
      // 07d: aload 1
      // 07e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 081: pop
      // 082: ldc "FlutterEngineCxnRegstry"
      // 084: aload 4
      // 086: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 089: invokestatic io/flutter/Log.v (Ljava/lang/String;Ljava/lang/String;)V
      // 08c: aload 0
      // 08d: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.plugins Ljava/util/Map;
      // 090: aload 1
      // 091: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 094: aload 1
      // 095: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 09a: pop
      // 09b: aload 1
      // 09c: aload 0
      // 09d: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.pluginBinding Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;
      // 0a0: invokeinterface io/flutter/embedding/engine/plugins/FlutterPlugin.onAttachedToEngine (Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;)V 2
      // 0a5: aload 1
      // 0a6: instanceof io/flutter/embedding/engine/plugins/activity/ActivityAware
      // 0a9: ifeq 0d4
      // 0ac: aload 1
      // 0ad: checkcast io/flutter/embedding/engine/plugins/activity/ActivityAware
      // 0b0: astore 4
      // 0b2: aload 0
      // 0b3: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.activityAwarePlugins Ljava/util/Map;
      // 0b6: aload 1
      // 0b7: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 0ba: aload 4
      // 0bc: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 0c1: pop
      // 0c2: aload 0
      // 0c3: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.isAttachedToActivity ()Z
      // 0c6: ifeq 0d4
      // 0c9: aload 4
      // 0cb: aload 0
      // 0cc: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.activityPluginBinding Lio/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineActivityPluginBinding;
      // 0cf: invokeinterface io/flutter/embedding/engine/plugins/activity/ActivityAware.onAttachedToActivity (Lio/flutter/embedding/engine/plugins/activity/ActivityPluginBinding;)V 2
      // 0d4: aload 1
      // 0d5: instanceof io/flutter/embedding/engine/plugins/service/ServiceAware
      // 0d8: ifeq 103
      // 0db: aload 1
      // 0dc: checkcast io/flutter/embedding/engine/plugins/service/ServiceAware
      // 0df: astore 4
      // 0e1: aload 0
      // 0e2: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.serviceAwarePlugins Ljava/util/Map;
      // 0e5: aload 1
      // 0e6: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 0e9: aload 4
      // 0eb: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 0f0: pop
      // 0f1: aload 0
      // 0f2: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.isAttachedToService ()Z
      // 0f5: ifeq 103
      // 0f8: aload 4
      // 0fa: aload 0
      // 0fb: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.servicePluginBinding Lio/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineServicePluginBinding;
      // 0fe: invokeinterface io/flutter/embedding/engine/plugins/service/ServiceAware.onAttachedToService (Lio/flutter/embedding/engine/plugins/service/ServicePluginBinding;)V 2
      // 103: aload 1
      // 104: instanceof io/flutter/embedding/engine/plugins/broadcastreceiver/BroadcastReceiverAware
      // 107: ifeq 132
      // 10a: aload 1
      // 10b: checkcast io/flutter/embedding/engine/plugins/broadcastreceiver/BroadcastReceiverAware
      // 10e: astore 4
      // 110: aload 0
      // 111: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.broadcastReceiverAwarePlugins Ljava/util/Map;
      // 114: aload 1
      // 115: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 118: aload 4
      // 11a: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 11f: pop
      // 120: aload 0
      // 121: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.isAttachedToBroadcastReceiver ()Z
      // 124: ifeq 132
      // 127: aload 4
      // 129: aload 0
      // 12a: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.broadcastReceiverPluginBinding Lio/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineBroadcastReceiverPluginBinding;
      // 12d: invokeinterface io/flutter/embedding/engine/plugins/broadcastreceiver/BroadcastReceiverAware.onAttachedToBroadcastReceiver (Lio/flutter/embedding/engine/plugins/broadcastreceiver/BroadcastReceiverPluginBinding;)V 2
      // 132: aload 1
      // 133: instanceof io/flutter/embedding/engine/plugins/contentprovider/ContentProviderAware
      // 136: ifeq 161
      // 139: aload 1
      // 13a: checkcast io/flutter/embedding/engine/plugins/contentprovider/ContentProviderAware
      // 13d: astore 4
      // 13f: aload 0
      // 140: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.contentProviderAwarePlugins Ljava/util/Map;
      // 143: aload 1
      // 144: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 147: aload 4
      // 149: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 14e: pop
      // 14f: aload 0
      // 150: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.isAttachedToContentProvider ()Z
      // 153: ifeq 161
      // 156: aload 4
      // 158: aload 0
      // 159: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.contentProviderPluginBinding Lio/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineContentProviderPluginBinding;
      // 15c: invokeinterface io/flutter/embedding/engine/plugins/contentprovider/ContentProviderAware.onAttachedToContentProvider (Lio/flutter/embedding/engine/plugins/contentprovider/ContentProviderPluginBinding;)V 2
      // 161: aload 3
      // 162: ifnull 169
      // 165: aload 3
      // 166: invokevirtual io/flutter/util/TraceSection.close ()V
      // 169: return
      // 16a: astore 1
      // 16b: aload 3
      // 16c: ifnull 17c
      // 16f: aload 3
      // 170: invokevirtual io/flutter/util/TraceSection.close ()V
      // 173: goto 17c
      // 176: astore 3
      // 177: aload 1
      // 178: aload 3
      // 179: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 17c: aload 1
      // 17d: athrow
   }

   @Override
   public void add(Set<FlutterPlugin> var1) {
      Iterator var2 = var1.iterator();

      while (var2.hasNext()) {
         this.add((FlutterPlugin)var2.next());
      }
   }

   @Override
   public void attachToActivity(ExclusiveAppComponent<Activity> param1, Lifecycle param2) {
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
      // 00: ldc_w "FlutterEngineConnectionRegistry#attachToActivity"
      // 03: invokestatic io/flutter/util/TraceSection.scoped (Ljava/lang/String;)Lio/flutter/util/TraceSection;
      // 06: astore 3
      // 07: aload 0
      // 08: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.exclusiveActivity Lio/flutter/embedding/android/ExclusiveAppComponent;
      // 0b: astore 4
      // 0d: aload 4
      // 0f: ifnull 19
      // 12: aload 4
      // 14: invokeinterface io/flutter/embedding/android/ExclusiveAppComponent.detachFromFlutterEngine ()V 1
      // 19: aload 0
      // 1a: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.detachFromAppComponent ()V
      // 1d: aload 0
      // 1e: aload 1
      // 1f: putfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.exclusiveActivity Lio/flutter/embedding/android/ExclusiveAppComponent;
      // 22: aload 0
      // 23: aload 1
      // 24: invokeinterface io/flutter/embedding/android/ExclusiveAppComponent.getAppComponent ()Ljava/lang/Object; 1
      // 29: checkcast android/app/Activity
      // 2c: aload 2
      // 2d: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.attachToActivityInternal (Landroid/app/Activity;Landroidx/lifecycle/Lifecycle;)V
      // 30: aload 3
      // 31: ifnull 38
      // 34: aload 3
      // 35: invokevirtual io/flutter/util/TraceSection.close ()V
      // 38: return
      // 39: astore 1
      // 3a: aload 3
      // 3b: ifnull 4b
      // 3e: aload 3
      // 3f: invokevirtual io/flutter/util/TraceSection.close ()V
      // 42: goto 4b
      // 45: astore 2
      // 46: aload 1
      // 47: aload 2
      // 48: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 4b: aload 1
      // 4c: athrow
   }

   @Override
   public void attachToBroadcastReceiver(BroadcastReceiver param1, Lifecycle param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:350)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:99)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: ldc_w "FlutterEngineConnectionRegistry#attachToBroadcastReceiver"
      // 03: invokestatic io/flutter/util/TraceSection.scoped (Ljava/lang/String;)Lio/flutter/util/TraceSection;
      // 06: astore 2
      // 07: aload 0
      // 08: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.detachFromAppComponent ()V
      // 0b: aload 0
      // 0c: aload 1
      // 0d: putfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.broadcastReceiver Landroid/content/BroadcastReceiver;
      // 10: new io/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineBroadcastReceiverPluginBinding
      // 13: astore 3
      // 14: aload 3
      // 15: aload 1
      // 16: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineBroadcastReceiverPluginBinding.<init> (Landroid/content/BroadcastReceiver;)V
      // 19: aload 0
      // 1a: aload 3
      // 1b: putfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.broadcastReceiverPluginBinding Lio/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineBroadcastReceiverPluginBinding;
      // 1e: aload 0
      // 1f: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.broadcastReceiverAwarePlugins Ljava/util/Map;
      // 22: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
      // 27: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
      // 2c: astore 1
      // 2d: aload 1
      // 2e: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 33: ifeq 4b
      // 36: aload 1
      // 37: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 3c: checkcast io/flutter/embedding/engine/plugins/broadcastreceiver/BroadcastReceiverAware
      // 3f: aload 0
      // 40: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.broadcastReceiverPluginBinding Lio/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineBroadcastReceiverPluginBinding;
      // 43: invokeinterface io/flutter/embedding/engine/plugins/broadcastreceiver/BroadcastReceiverAware.onAttachedToBroadcastReceiver (Lio/flutter/embedding/engine/plugins/broadcastreceiver/BroadcastReceiverPluginBinding;)V 2
      // 48: goto 2d
      // 4b: aload 2
      // 4c: ifnull 53
      // 4f: aload 2
      // 50: invokevirtual io/flutter/util/TraceSection.close ()V
      // 53: return
      // 54: astore 1
      // 55: aload 2
      // 56: ifnull 66
      // 59: aload 2
      // 5a: invokevirtual io/flutter/util/TraceSection.close ()V
      // 5d: goto 66
      // 60: astore 2
      // 61: aload 1
      // 62: aload 2
      // 63: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 66: aload 1
      // 67: athrow
   }

   @Override
   public void attachToContentProvider(ContentProvider param1, Lifecycle param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:350)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:99)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: ldc_w "FlutterEngineConnectionRegistry#attachToContentProvider"
      // 03: invokestatic io/flutter/util/TraceSection.scoped (Ljava/lang/String;)Lio/flutter/util/TraceSection;
      // 06: astore 2
      // 07: aload 0
      // 08: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.detachFromAppComponent ()V
      // 0b: aload 0
      // 0c: aload 1
      // 0d: putfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.contentProvider Landroid/content/ContentProvider;
      // 10: new io/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineContentProviderPluginBinding
      // 13: astore 3
      // 14: aload 3
      // 15: aload 1
      // 16: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineContentProviderPluginBinding.<init> (Landroid/content/ContentProvider;)V
      // 19: aload 0
      // 1a: aload 3
      // 1b: putfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.contentProviderPluginBinding Lio/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineContentProviderPluginBinding;
      // 1e: aload 0
      // 1f: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.contentProviderAwarePlugins Ljava/util/Map;
      // 22: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
      // 27: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
      // 2c: astore 1
      // 2d: aload 1
      // 2e: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 33: ifeq 4b
      // 36: aload 1
      // 37: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 3c: checkcast io/flutter/embedding/engine/plugins/contentprovider/ContentProviderAware
      // 3f: aload 0
      // 40: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.contentProviderPluginBinding Lio/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineContentProviderPluginBinding;
      // 43: invokeinterface io/flutter/embedding/engine/plugins/contentprovider/ContentProviderAware.onAttachedToContentProvider (Lio/flutter/embedding/engine/plugins/contentprovider/ContentProviderPluginBinding;)V 2
      // 48: goto 2d
      // 4b: aload 2
      // 4c: ifnull 53
      // 4f: aload 2
      // 50: invokevirtual io/flutter/util/TraceSection.close ()V
      // 53: return
      // 54: astore 1
      // 55: aload 2
      // 56: ifnull 66
      // 59: aload 2
      // 5a: invokevirtual io/flutter/util/TraceSection.close ()V
      // 5d: goto 66
      // 60: astore 2
      // 61: aload 1
      // 62: aload 2
      // 63: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 66: aload 1
      // 67: athrow
   }

   @Override
   public void attachToService(Service param1, Lifecycle param2, boolean param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:350)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:99)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: ldc_w "FlutterEngineConnectionRegistry#attachToService"
      // 03: invokestatic io/flutter/util/TraceSection.scoped (Ljava/lang/String;)Lio/flutter/util/TraceSection;
      // 06: astore 4
      // 08: aload 0
      // 09: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.detachFromAppComponent ()V
      // 0c: aload 0
      // 0d: aload 1
      // 0e: putfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.service Landroid/app/Service;
      // 11: new io/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineServicePluginBinding
      // 14: astore 5
      // 16: aload 5
      // 18: aload 1
      // 19: aload 2
      // 1a: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineServicePluginBinding.<init> (Landroid/app/Service;Landroidx/lifecycle/Lifecycle;)V
      // 1d: aload 0
      // 1e: aload 5
      // 20: putfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.servicePluginBinding Lio/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineServicePluginBinding;
      // 23: aload 0
      // 24: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.serviceAwarePlugins Ljava/util/Map;
      // 27: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
      // 2c: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
      // 31: astore 1
      // 32: aload 1
      // 33: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 38: ifeq 50
      // 3b: aload 1
      // 3c: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 41: checkcast io/flutter/embedding/engine/plugins/service/ServiceAware
      // 44: aload 0
      // 45: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.servicePluginBinding Lio/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineServicePluginBinding;
      // 48: invokeinterface io/flutter/embedding/engine/plugins/service/ServiceAware.onAttachedToService (Lio/flutter/embedding/engine/plugins/service/ServicePluginBinding;)V 2
      // 4d: goto 32
      // 50: aload 4
      // 52: ifnull 5a
      // 55: aload 4
      // 57: invokevirtual io/flutter/util/TraceSection.close ()V
      // 5a: return
      // 5b: astore 1
      // 5c: aload 4
      // 5e: ifnull 6f
      // 61: aload 4
      // 63: invokevirtual io/flutter/util/TraceSection.close ()V
      // 66: goto 6f
      // 69: astore 2
      // 6a: aload 1
      // 6b: aload 2
      // 6c: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 6f: aload 1
      // 70: athrow
   }

   public void destroy() {
      Log.v("FlutterEngineCxnRegstry", "Destroying.");
      this.detachFromAppComponent();
      this.removeAll();
   }

   @Override
   public void detachFromActivity() {
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
      // 01: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.isAttachedToActivity ()Z
      // 04: ifeq 5a
      // 07: ldc_w "FlutterEngineConnectionRegistry#detachFromActivity"
      // 0a: invokestatic io/flutter/util/TraceSection.scoped (Ljava/lang/String;)Lio/flutter/util/TraceSection;
      // 0d: astore 1
      // 0e: aload 0
      // 0f: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.activityAwarePlugins Ljava/util/Map;
      // 12: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
      // 17: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
      // 1c: astore 2
      // 1d: aload 2
      // 1e: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 23: ifeq 37
      // 26: aload 2
      // 27: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 2c: checkcast io/flutter/embedding/engine/plugins/activity/ActivityAware
      // 2f: invokeinterface io/flutter/embedding/engine/plugins/activity/ActivityAware.onDetachedFromActivity ()V 1
      // 34: goto 1d
      // 37: aload 0
      // 38: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.detachFromActivityInternal ()V
      // 3b: aload 1
      // 3c: ifnull 62
      // 3f: aload 1
      // 40: invokevirtual io/flutter/util/TraceSection.close ()V
      // 43: goto 62
      // 46: astore 2
      // 47: aload 1
      // 48: ifnull 58
      // 4b: aload 1
      // 4c: invokevirtual io/flutter/util/TraceSection.close ()V
      // 4f: goto 58
      // 52: astore 1
      // 53: aload 2
      // 54: aload 1
      // 55: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 58: aload 2
      // 59: athrow
      // 5a: ldc "FlutterEngineCxnRegstry"
      // 5c: ldc_w "Attempted to detach plugins from an Activity when no Activity was attached."
      // 5f: invokestatic io/flutter/Log.e (Ljava/lang/String;Ljava/lang/String;)V
      // 62: return
   }

   @Override
   public void detachFromActivityForConfigChanges() {
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
      // 01: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.isAttachedToActivity ()Z
      // 04: ifeq 5f
      // 07: ldc_w "FlutterEngineConnectionRegistry#detachFromActivityForConfigChanges"
      // 0a: invokestatic io/flutter/util/TraceSection.scoped (Ljava/lang/String;)Lio/flutter/util/TraceSection;
      // 0d: astore 2
      // 0e: aload 0
      // 0f: bipush 1
      // 10: putfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.isWaitingForActivityReattachment Z
      // 13: aload 0
      // 14: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.activityAwarePlugins Ljava/util/Map;
      // 17: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
      // 1c: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
      // 21: astore 1
      // 22: aload 1
      // 23: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 28: ifeq 3c
      // 2b: aload 1
      // 2c: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 31: checkcast io/flutter/embedding/engine/plugins/activity/ActivityAware
      // 34: invokeinterface io/flutter/embedding/engine/plugins/activity/ActivityAware.onDetachedFromActivityForConfigChanges ()V 1
      // 39: goto 22
      // 3c: aload 0
      // 3d: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.detachFromActivityInternal ()V
      // 40: aload 2
      // 41: ifnull 67
      // 44: aload 2
      // 45: invokevirtual io/flutter/util/TraceSection.close ()V
      // 48: goto 67
      // 4b: astore 1
      // 4c: aload 2
      // 4d: ifnull 5d
      // 50: aload 2
      // 51: invokevirtual io/flutter/util/TraceSection.close ()V
      // 54: goto 5d
      // 57: astore 2
      // 58: aload 1
      // 59: aload 2
      // 5a: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 5d: aload 1
      // 5e: athrow
      // 5f: ldc "FlutterEngineCxnRegstry"
      // 61: ldc_w "Attempted to detach plugins from an Activity when no Activity was attached."
      // 64: invokestatic io/flutter/Log.e (Ljava/lang/String;Ljava/lang/String;)V
      // 67: return
   }

   @Override
   public void detachFromBroadcastReceiver() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:350)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:99)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.isAttachedToBroadcastReceiver ()Z
      // 04: ifeq 56
      // 07: ldc_w "FlutterEngineConnectionRegistry#detachFromBroadcastReceiver"
      // 0a: invokestatic io/flutter/util/TraceSection.scoped (Ljava/lang/String;)Lio/flutter/util/TraceSection;
      // 0d: astore 2
      // 0e: aload 0
      // 0f: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.broadcastReceiverAwarePlugins Ljava/util/Map;
      // 12: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
      // 17: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
      // 1c: astore 1
      // 1d: aload 1
      // 1e: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 23: ifeq 37
      // 26: aload 1
      // 27: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 2c: checkcast io/flutter/embedding/engine/plugins/broadcastreceiver/BroadcastReceiverAware
      // 2f: invokeinterface io/flutter/embedding/engine/plugins/broadcastreceiver/BroadcastReceiverAware.onDetachedFromBroadcastReceiver ()V 1
      // 34: goto 1d
      // 37: aload 2
      // 38: ifnull 5e
      // 3b: aload 2
      // 3c: invokevirtual io/flutter/util/TraceSection.close ()V
      // 3f: goto 5e
      // 42: astore 1
      // 43: aload 2
      // 44: ifnull 54
      // 47: aload 2
      // 48: invokevirtual io/flutter/util/TraceSection.close ()V
      // 4b: goto 54
      // 4e: astore 2
      // 4f: aload 1
      // 50: aload 2
      // 51: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 54: aload 1
      // 55: athrow
      // 56: ldc "FlutterEngineCxnRegstry"
      // 58: ldc_w "Attempted to detach plugins from a BroadcastReceiver when no BroadcastReceiver was attached."
      // 5b: invokestatic io/flutter/Log.e (Ljava/lang/String;Ljava/lang/String;)V
      // 5e: return
   }

   @Override
   public void detachFromContentProvider() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:350)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:99)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.isAttachedToContentProvider ()Z
      // 04: ifeq 56
      // 07: ldc_w "FlutterEngineConnectionRegistry#detachFromContentProvider"
      // 0a: invokestatic io/flutter/util/TraceSection.scoped (Ljava/lang/String;)Lio/flutter/util/TraceSection;
      // 0d: astore 1
      // 0e: aload 0
      // 0f: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.contentProviderAwarePlugins Ljava/util/Map;
      // 12: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
      // 17: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
      // 1c: astore 2
      // 1d: aload 2
      // 1e: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 23: ifeq 37
      // 26: aload 2
      // 27: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 2c: checkcast io/flutter/embedding/engine/plugins/contentprovider/ContentProviderAware
      // 2f: invokeinterface io/flutter/embedding/engine/plugins/contentprovider/ContentProviderAware.onDetachedFromContentProvider ()V 1
      // 34: goto 1d
      // 37: aload 1
      // 38: ifnull 5e
      // 3b: aload 1
      // 3c: invokevirtual io/flutter/util/TraceSection.close ()V
      // 3f: goto 5e
      // 42: astore 2
      // 43: aload 1
      // 44: ifnull 54
      // 47: aload 1
      // 48: invokevirtual io/flutter/util/TraceSection.close ()V
      // 4b: goto 54
      // 4e: astore 1
      // 4f: aload 2
      // 50: aload 1
      // 51: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 54: aload 2
      // 55: athrow
      // 56: ldc "FlutterEngineCxnRegstry"
      // 58: ldc_w "Attempted to detach plugins from a ContentProvider when no ContentProvider was attached."
      // 5b: invokestatic io/flutter/Log.e (Ljava/lang/String;Ljava/lang/String;)V
      // 5e: return
   }

   @Override
   public void detachFromService() {
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
      // 01: invokespecial io/flutter/embedding/engine/FlutterEngineConnectionRegistry.isAttachedToService ()Z
      // 04: ifeq 60
      // 07: ldc_w "FlutterEngineConnectionRegistry#detachFromService"
      // 0a: invokestatic io/flutter/util/TraceSection.scoped (Ljava/lang/String;)Lio/flutter/util/TraceSection;
      // 0d: astore 1
      // 0e: aload 0
      // 0f: getfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.serviceAwarePlugins Ljava/util/Map;
      // 12: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
      // 17: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
      // 1c: astore 2
      // 1d: aload 2
      // 1e: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 23: ifeq 37
      // 26: aload 2
      // 27: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 2c: checkcast io/flutter/embedding/engine/plugins/service/ServiceAware
      // 2f: invokeinterface io/flutter/embedding/engine/plugins/service/ServiceAware.onDetachedFromService ()V 1
      // 34: goto 1d
      // 37: aload 0
      // 38: aconst_null
      // 39: putfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.service Landroid/app/Service;
      // 3c: aload 0
      // 3d: aconst_null
      // 3e: putfield io/flutter/embedding/engine/FlutterEngineConnectionRegistry.servicePluginBinding Lio/flutter/embedding/engine/FlutterEngineConnectionRegistry$FlutterEngineServicePluginBinding;
      // 41: aload 1
      // 42: ifnull 68
      // 45: aload 1
      // 46: invokevirtual io/flutter/util/TraceSection.close ()V
      // 49: goto 68
      // 4c: astore 2
      // 4d: aload 1
      // 4e: ifnull 5e
      // 51: aload 1
      // 52: invokevirtual io/flutter/util/TraceSection.close ()V
      // 55: goto 5e
      // 58: astore 1
      // 59: aload 2
      // 5a: aload 1
      // 5b: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 5e: aload 2
      // 5f: athrow
      // 60: ldc "FlutterEngineCxnRegstry"
      // 62: ldc_w "Attempted to detach plugins from a Service when no Service was attached."
      // 65: invokestatic io/flutter/Log.e (Ljava/lang/String;Ljava/lang/String;)V
      // 68: return
   }

   @Override
   public FlutterPlugin get(Class<? extends FlutterPlugin> var1) {
      return this.plugins.get(var1);
   }

   @Override
   public boolean has(Class<? extends FlutterPlugin> var1) {
      return this.plugins.containsKey(var1);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public boolean onActivityResult(int var1, int var2, Intent var3) {
      if (this.isAttachedToActivity()) {
         TraceSection var5 = TraceSection.scoped("FlutterEngineConnectionRegistry#onActivityResult");

         boolean var4;
         try {
            var4 = this.activityPluginBinding.onActivityResult(var1, var2, var3);
         } catch (Throwable var11) {
            if (var5 != null) {
               try {
                  var5.close();
               } catch (Throwable var10) {
                  var11.addSuppressed(var10);
                  throw var11;
               }
            }

            throw var11;
         }

         if (var5 != null) {
            var5.close();
         }

         return var4;
      } else {
         Log.e("FlutterEngineCxnRegstry", "Attempted to notify ActivityAware plugins of onActivityResult, but no Activity was attached.");
         return false;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onMoveToBackground() {
      if (this.isAttachedToService()) {
         TraceSection var2 = TraceSection.scoped("FlutterEngineConnectionRegistry#onMoveToBackground");

         try {
            this.servicePluginBinding.onMoveToBackground();
         } catch (Throwable var8) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
                  throw var8;
               }
            }

            throw var8;
         }

         if (var2 != null) {
            var2.close();
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onMoveToForeground() {
      if (this.isAttachedToService()) {
         TraceSection var2 = TraceSection.scoped("FlutterEngineConnectionRegistry#onMoveToForeground");

         try {
            this.servicePluginBinding.onMoveToForeground();
         } catch (Throwable var8) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
                  throw var8;
               }
            }

            throw var8;
         }

         if (var2 != null) {
            var2.close();
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onNewIntent(Intent var1) {
      if (this.isAttachedToActivity()) {
         TraceSection var2 = TraceSection.scoped("FlutterEngineConnectionRegistry#onNewIntent");

         try {
            this.activityPluginBinding.onNewIntent(var1);
         } catch (Throwable var8) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
                  throw var8;
               }
            }

            throw var8;
         }

         if (var2 != null) {
            var2.close();
         }
      } else {
         Log.e("FlutterEngineCxnRegstry", "Attempted to notify ActivityAware plugins of onNewIntent, but no Activity was attached.");
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public boolean onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
      if (this.isAttachedToActivity()) {
         TraceSection var5 = TraceSection.scoped("FlutterEngineConnectionRegistry#onRequestPermissionsResult");

         boolean var4;
         try {
            var4 = this.activityPluginBinding.onRequestPermissionsResult(var1, var2, var3);
         } catch (Throwable var11) {
            if (var5 != null) {
               try {
                  var5.close();
               } catch (Throwable var10) {
                  var11.addSuppressed(var10);
                  throw var11;
               }
            }

            throw var11;
         }

         if (var5 != null) {
            var5.close();
         }

         return var4;
      } else {
         Log.e("FlutterEngineCxnRegstry", "Attempted to notify ActivityAware plugins of onRequestPermissionsResult, but no Activity was attached.");
         return false;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onRestoreInstanceState(Bundle var1) {
      if (this.isAttachedToActivity()) {
         TraceSection var2 = TraceSection.scoped("FlutterEngineConnectionRegistry#onRestoreInstanceState");

         try {
            this.activityPluginBinding.onRestoreInstanceState(var1);
         } catch (Throwable var8) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
                  throw var8;
               }
            }

            throw var8;
         }

         if (var2 != null) {
            var2.close();
         }
      } else {
         Log.e("FlutterEngineCxnRegstry", "Attempted to notify ActivityAware plugins of onRestoreInstanceState, but no Activity was attached.");
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onSaveInstanceState(Bundle var1) {
      if (this.isAttachedToActivity()) {
         TraceSection var2 = TraceSection.scoped("FlutterEngineConnectionRegistry#onSaveInstanceState");

         try {
            this.activityPluginBinding.onSaveInstanceState(var1);
         } catch (Throwable var8) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
                  throw var8;
               }
            }

            throw var8;
         }

         if (var2 != null) {
            var2.close();
         }
      } else {
         Log.e("FlutterEngineCxnRegstry", "Attempted to notify ActivityAware plugins of onSaveInstanceState, but no Activity was attached.");
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onUserLeaveHint() {
      if (this.isAttachedToActivity()) {
         TraceSection var2 = TraceSection.scoped("FlutterEngineConnectionRegistry#onUserLeaveHint");

         try {
            this.activityPluginBinding.onUserLeaveHint();
         } catch (Throwable var8) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
                  throw var8;
               }
            }

            throw var8;
         }

         if (var2 != null) {
            var2.close();
         }
      } else {
         Log.e("FlutterEngineCxnRegstry", "Attempted to notify ActivityAware plugins of onUserLeaveHint, but no Activity was attached.");
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void remove(Class<? extends FlutterPlugin> var1) {
      FlutterPlugin var3 = this.plugins.get(var1);
      if (var3 != null) {
         StringBuilder var2 = new StringBuilder("FlutterEngineConnectionRegistry#remove ");
         var2.append(var1.getSimpleName());
         TraceSection var10 = TraceSection.scoped(var2.toString());

         try {
            if (var3 instanceof ActivityAware) {
               if (this.isAttachedToActivity()) {
                  ((ActivityAware)var3).onDetachedFromActivity();
               }

               this.activityAwarePlugins.remove(var1);
            }

            if (var3 instanceof ServiceAware) {
               if (this.isAttachedToService()) {
                  ((ServiceAware)var3).onDetachedFromService();
               }

               this.serviceAwarePlugins.remove(var1);
            }

            if (var3 instanceof BroadcastReceiverAware) {
               if (this.isAttachedToBroadcastReceiver()) {
                  ((BroadcastReceiverAware)var3).onDetachedFromBroadcastReceiver();
               }

               this.broadcastReceiverAwarePlugins.remove(var1);
            }

            if (var3 instanceof ContentProviderAware) {
               if (this.isAttachedToContentProvider()) {
                  ((ContentProviderAware)var3).onDetachedFromContentProvider();
               }

               this.contentProviderAwarePlugins.remove(var1);
            }

            var3.onDetachedFromEngine(this.pluginBinding);
            this.plugins.remove(var1);
         } catch (Throwable var9) {
            if (var10 != null) {
               try {
                  var10.close();
               } catch (Throwable var8) {
                  var9.addSuppressed(var8);
                  throw var9;
               }
            }

            throw var9;
         }

         if (var10 != null) {
            var10.close();
         }
      }
   }

   @Override
   public void remove(Set<Class<? extends FlutterPlugin>> var1) {
      Iterator var2 = var1.iterator();

      while (var2.hasNext()) {
         this.remove((Class<? extends FlutterPlugin>)var2.next());
      }
   }

   @Override
   public void removeAll() {
      this.remove(new HashSet<>(this.plugins.keySet()));
      this.plugins.clear();
   }

   private static class DefaultFlutterAssets implements FlutterPlugin.FlutterAssets {
      final FlutterLoader flutterLoader;

      private DefaultFlutterAssets(FlutterLoader var1) {
         this.flutterLoader = var1;
      }

      @Override
      public String getAssetFilePathByName(String var1) {
         return this.flutterLoader.getLookupKeyForAsset(var1);
      }

      @Override
      public String getAssetFilePathByName(String var1, String var2) {
         return this.flutterLoader.getLookupKeyForAsset(var1, var2);
      }

      @Override
      public String getAssetFilePathBySubpath(String var1) {
         return this.flutterLoader.getLookupKeyForAsset(var1);
      }

      @Override
      public String getAssetFilePathBySubpath(String var1, String var2) {
         return this.flutterLoader.getLookupKeyForAsset(var1, var2);
      }
   }

   private static class FlutterEngineActivityPluginBinding implements ActivityPluginBinding {
      private final Activity activity;
      private final HiddenLifecycleReference hiddenLifecycleReference;
      private final Set<io.flutter.plugin.common.PluginRegistry.ActivityResultListener> onActivityResultListeners;
      private final Set<io.flutter.plugin.common.PluginRegistry.NewIntentListener> onNewIntentListeners;
      private final Set<io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener> onRequestPermissionsResultListeners = new HashSet<>();
      private final Set<ActivityPluginBinding.OnSaveInstanceStateListener> onSaveInstanceStateListeners;
      private final Set<io.flutter.plugin.common.PluginRegistry.UserLeaveHintListener> onUserLeaveHintListeners;
      private final Set<io.flutter.plugin.common.PluginRegistry.WindowFocusChangedListener> onWindowFocusChangedListeners;

      public FlutterEngineActivityPluginBinding(Activity var1, Lifecycle var2) {
         this.onActivityResultListeners = new HashSet<>();
         this.onNewIntentListeners = new HashSet<>();
         this.onUserLeaveHintListeners = new HashSet<>();
         this.onWindowFocusChangedListeners = new HashSet<>();
         this.onSaveInstanceStateListeners = new HashSet<>();
         this.activity = var1;
         this.hiddenLifecycleReference = new HiddenLifecycleReference(var2);
      }

      @Override
      public void addActivityResultListener(io.flutter.plugin.common.PluginRegistry.ActivityResultListener var1) {
         this.onActivityResultListeners.add(var1);
      }

      @Override
      public void addOnNewIntentListener(io.flutter.plugin.common.PluginRegistry.NewIntentListener var1) {
         this.onNewIntentListeners.add(var1);
      }

      @Override
      public void addOnSaveStateListener(ActivityPluginBinding.OnSaveInstanceStateListener var1) {
         this.onSaveInstanceStateListeners.add(var1);
      }

      @Override
      public void addOnUserLeaveHintListener(io.flutter.plugin.common.PluginRegistry.UserLeaveHintListener var1) {
         this.onUserLeaveHintListeners.add(var1);
      }

      @Override
      public void addOnWindowFocusChangedListener(io.flutter.plugin.common.PluginRegistry.WindowFocusChangedListener var1) {
         this.onWindowFocusChangedListeners.add(var1);
      }

      @Override
      public void addRequestPermissionsResultListener(io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener var1) {
         this.onRequestPermissionsResultListeners.add(var1);
      }

      @Override
      public Activity getActivity() {
         return this.activity;
      }

      @Override
      public Object getLifecycle() {
         return this.hiddenLifecycleReference;
      }

      boolean onActivityResult(int var1, int var2, Intent var3) {
         Iterator var5 = new HashSet<>(this.onActivityResultListeners).iterator();

         label19:
         while (true) {
            boolean var4;
            for (var4 = false; var5.hasNext(); var4 = true) {
               if (!((io.flutter.plugin.common.PluginRegistry.ActivityResultListener)var5.next()).onActivityResult(var1, var2, var3) && !var4) {
                  continue label19;
               }
            }

            return var4;
         }
      }

      void onNewIntent(Intent var1) {
         Iterator var2 = this.onNewIntentListeners.iterator();

         while (var2.hasNext()) {
            ((io.flutter.plugin.common.PluginRegistry.NewIntentListener)var2.next()).onNewIntent(var1);
         }
      }

      boolean onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
         Iterator var5 = this.onRequestPermissionsResultListeners.iterator();

         label19:
         while (true) {
            boolean var4;
            for (var4 = false; var5.hasNext(); var4 = true) {
               if (!((io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener)var5.next()).onRequestPermissionsResult(var1, var2, var3)
                  && !var4) {
                  continue label19;
               }
            }

            return var4;
         }
      }

      void onRestoreInstanceState(Bundle var1) {
         Iterator var2 = this.onSaveInstanceStateListeners.iterator();

         while (var2.hasNext()) {
            ((ActivityPluginBinding.OnSaveInstanceStateListener)var2.next()).onRestoreInstanceState(var1);
         }
      }

      void onSaveInstanceState(Bundle var1) {
         Iterator var2 = this.onSaveInstanceStateListeners.iterator();

         while (var2.hasNext()) {
            ((ActivityPluginBinding.OnSaveInstanceStateListener)var2.next()).onSaveInstanceState(var1);
         }
      }

      void onUserLeaveHint() {
         Iterator var1 = this.onUserLeaveHintListeners.iterator();

         while (var1.hasNext()) {
            ((io.flutter.plugin.common.PluginRegistry.UserLeaveHintListener)var1.next()).onUserLeaveHint();
         }
      }

      void onWindowFocusChanged(boolean var1) {
         Iterator var2 = this.onWindowFocusChangedListeners.iterator();

         while (var2.hasNext()) {
            ((io.flutter.plugin.common.PluginRegistry.WindowFocusChangedListener)var2.next()).onWindowFocusChanged(var1);
         }
      }

      @Override
      public void removeActivityResultListener(io.flutter.plugin.common.PluginRegistry.ActivityResultListener var1) {
         this.onActivityResultListeners.remove(var1);
      }

      @Override
      public void removeOnNewIntentListener(io.flutter.plugin.common.PluginRegistry.NewIntentListener var1) {
         this.onNewIntentListeners.remove(var1);
      }

      @Override
      public void removeOnSaveStateListener(ActivityPluginBinding.OnSaveInstanceStateListener var1) {
         this.onSaveInstanceStateListeners.remove(var1);
      }

      @Override
      public void removeOnUserLeaveHintListener(io.flutter.plugin.common.PluginRegistry.UserLeaveHintListener var1) {
         this.onUserLeaveHintListeners.remove(var1);
      }

      @Override
      public void removeOnWindowFocusChangedListener(io.flutter.plugin.common.PluginRegistry.WindowFocusChangedListener var1) {
         this.onWindowFocusChangedListeners.remove(var1);
      }

      @Override
      public void removeRequestPermissionsResultListener(io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener var1) {
         this.onRequestPermissionsResultListeners.remove(var1);
      }
   }

   private static class FlutterEngineBroadcastReceiverPluginBinding implements BroadcastReceiverPluginBinding {
      private final BroadcastReceiver broadcastReceiver;

      FlutterEngineBroadcastReceiverPluginBinding(BroadcastReceiver var1) {
         this.broadcastReceiver = var1;
      }

      @Override
      public BroadcastReceiver getBroadcastReceiver() {
         return this.broadcastReceiver;
      }
   }

   private static class FlutterEngineContentProviderPluginBinding implements ContentProviderPluginBinding {
      private final ContentProvider contentProvider;

      FlutterEngineContentProviderPluginBinding(ContentProvider var1) {
         this.contentProvider = var1;
      }

      @Override
      public ContentProvider getContentProvider() {
         return this.contentProvider;
      }
   }

   private static class FlutterEngineServicePluginBinding implements ServicePluginBinding {
      private final HiddenLifecycleReference hiddenLifecycleReference;
      private final Set<ServiceAware.OnModeChangeListener> onModeChangeListeners = new HashSet<>();
      private final Service service;

      FlutterEngineServicePluginBinding(Service var1, Lifecycle var2) {
         this.service = var1;
         HiddenLifecycleReference var3;
         if (var2 != null) {
            var3 = new HiddenLifecycleReference(var2);
         } else {
            var3 = null;
         }

         this.hiddenLifecycleReference = var3;
      }

      @Override
      public void addOnModeChangeListener(ServiceAware.OnModeChangeListener var1) {
         this.onModeChangeListeners.add(var1);
      }

      @Override
      public Object getLifecycle() {
         return this.hiddenLifecycleReference;
      }

      @Override
      public Service getService() {
         return this.service;
      }

      void onMoveToBackground() {
         Iterator var1 = this.onModeChangeListeners.iterator();

         while (var1.hasNext()) {
            ((ServiceAware.OnModeChangeListener)var1.next()).onMoveToBackground();
         }
      }

      void onMoveToForeground() {
         Iterator var1 = this.onModeChangeListeners.iterator();

         while (var1.hasNext()) {
            ((ServiceAware.OnModeChangeListener)var1.next()).onMoveToForeground();
         }
      }

      @Override
      public void removeOnModeChangeListener(ServiceAware.OnModeChangeListener var1) {
         this.onModeChangeListeners.remove(var1);
      }
   }
}
