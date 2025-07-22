package io.sentry.android.core;

import android.content.Context;
import io.sentry.HubAdapter;
import io.sentry.IHub;
import io.sentry.IScope;
import io.sentry.ISentryExecutorService;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.Session;
import io.sentry.android.core.performance.ActivityLifecycleTimeSpan;
import io.sentry.android.core.performance.AppStartMetrics;
import io.sentry.android.core.performance.TimeSpan;
import io.sentry.cache.EnvelopeCache;
import io.sentry.protocol.SentryId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public final class InternalSentrySdk {
   private static void addTimeSpanToSerializedSpans(TimeSpan var0, List<Map<String, Object>> var1) {
      if (var0.hasNotStarted()) {
         HubAdapter.getInstance().getOptions().getLogger().log(SentryLevel.WARNING, "Can not convert not-started TimeSpan to Map for Hybrid SDKs.");
      } else if (var0.hasNotStopped()) {
         HubAdapter.getInstance().getOptions().getLogger().log(SentryLevel.WARNING, "Can not convert not-stopped TimeSpan to Map for Hybrid SDKs.");
      } else {
         HashMap var2 = new HashMap();
         var2.put("description", var0.getDescription());
         var2.put("start_timestamp_ms", var0.getStartTimestampMs());
         var2.put("end_timestamp_ms", var0.getProjectedStopTimestampMs());
         var1.add(var2);
      }
   }

   public static SentryId captureEnvelope(byte[] param0, boolean param1) {
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
      // 000: invokestatic io/sentry/HubAdapter.getInstance ()Lio/sentry/HubAdapter;
      // 003: astore 7
      // 005: aload 7
      // 007: invokeinterface io/sentry/IHub.getOptions ()Lio/sentry/SentryOptions; 1
      // 00c: astore 5
      // 00e: new java/io/ByteArrayInputStream
      // 011: astore 6
      // 013: aload 6
      // 015: aload 0
      // 016: invokespecial java/io/ByteArrayInputStream.<init> ([B)V
      // 019: aload 5
      // 01b: invokevirtual io/sentry/SentryOptions.getSerializer ()Lio/sentry/ISerializer;
      // 01e: astore 10
      // 020: aload 5
      // 022: invokevirtual io/sentry/SentryOptions.getEnvelopeReader ()Lio/sentry/IEnvelopeReader;
      // 025: aload 6
      // 027: invokeinterface io/sentry/IEnvelopeReader.read (Ljava/io/InputStream;)Lio/sentry/SentryEnvelope; 2
      // 02c: astore 8
      // 02e: aload 8
      // 030: ifnonnull 03a
      // 033: aload 6
      // 035: invokevirtual java/io/InputStream.close ()V
      // 038: aconst_null
      // 039: areturn
      // 03a: new java/util/ArrayList
      // 03d: astore 9
      // 03f: aload 9
      // 041: invokespecial java/util/ArrayList.<init> ()V
      // 044: aload 8
      // 046: invokevirtual io/sentry/SentryEnvelope.getItems ()Ljava/lang/Iterable;
      // 049: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 04e: astore 11
      // 050: bipush 0
      // 051: istore 3
      // 052: aconst_null
      // 053: astore 0
      // 054: bipush 0
      // 055: istore 2
      // 056: aload 11
      // 058: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 05d: ifeq 0af
      // 060: aload 11
      // 062: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 067: checkcast io/sentry/SentryEnvelopeItem
      // 06a: astore 4
      // 06c: aload 9
      // 06e: aload 4
      // 070: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 075: pop
      // 076: aload 4
      // 078: aload 10
      // 07a: invokevirtual io/sentry/SentryEnvelopeItem.getEvent (Lio/sentry/ISerializer;)Lio/sentry/SentryEvent;
      // 07d: astore 12
      // 07f: aload 12
      // 081: ifnull 056
      // 084: aload 0
      // 085: astore 4
      // 087: aload 12
      // 089: invokevirtual io/sentry/SentryEvent.isCrashed ()Z
      // 08c: ifeq 094
      // 08f: getstatic io/sentry/Session$State.Crashed Lio/sentry/Session$State;
      // 092: astore 4
      // 094: aload 12
      // 096: invokevirtual io/sentry/SentryEvent.isCrashed ()Z
      // 099: ifne 0a7
      // 09c: aload 4
      // 09e: astore 0
      // 09f: aload 12
      // 0a1: invokevirtual io/sentry/SentryEvent.isErrored ()Z
      // 0a4: ifeq 056
      // 0a7: bipush 1
      // 0a8: istore 2
      // 0a9: aload 4
      // 0ab: astore 0
      // 0ac: goto 056
      // 0af: aload 7
      // 0b1: aload 5
      // 0b3: aload 0
      // 0b4: iload 2
      // 0b5: invokestatic io/sentry/android/core/InternalSentrySdk.updateSession (Lio/sentry/IHub;Lio/sentry/SentryOptions;Lio/sentry/Session$State;Z)Lio/sentry/Session;
      // 0b8: astore 0
      // 0b9: aload 0
      // 0ba: ifnull 0f6
      // 0bd: aload 9
      // 0bf: aload 10
      // 0c1: aload 0
      // 0c2: invokestatic io/sentry/SentryEnvelopeItem.fromSession (Lio/sentry/ISerializer;Lio/sentry/Session;)Lio/sentry/SentryEnvelopeItem;
      // 0c5: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 0ca: pop
      // 0cb: iload 1
      // 0cc: ifeq 0e3
      // 0cf: iload 3
      // 0d0: istore 2
      // 0d1: aload 7
      // 0d3: invokeinterface io/sentry/IHub.getOptions ()Lio/sentry/SentryOptions; 1
      // 0d8: invokevirtual io/sentry/SentryOptions.getMainThreadChecker ()Lio/sentry/util/thread/IMainThreadChecker;
      // 0db: invokeinterface io/sentry/util/thread/IMainThreadChecker.isMainThread ()Z 1
      // 0e0: ifne 0e5
      // 0e3: bipush 1
      // 0e4: istore 2
      // 0e5: aload 5
      // 0e7: iload 2
      // 0e8: invokestatic io/sentry/android/core/InternalSentrySdk.deleteCurrentSessionFile (Lio/sentry/SentryOptions;Z)V
      // 0eb: iload 1
      // 0ec: ifeq 0f6
      // 0ef: aload 7
      // 0f1: invokeinterface io/sentry/IHub.startSession ()V 1
      // 0f6: new io/sentry/SentryEnvelope
      // 0f9: astore 0
      // 0fa: aload 0
      // 0fb: aload 8
      // 0fd: invokevirtual io/sentry/SentryEnvelope.getHeader ()Lio/sentry/SentryEnvelopeHeader;
      // 100: aload 9
      // 102: invokespecial io/sentry/SentryEnvelope.<init> (Lio/sentry/SentryEnvelopeHeader;Ljava/lang/Iterable;)V
      // 105: aload 7
      // 107: aload 0
      // 108: invokeinterface io/sentry/IHub.captureEnvelope (Lio/sentry/SentryEnvelope;)Lio/sentry/protocol/SentryId; 2
      // 10d: astore 0
      // 10e: aload 6
      // 110: invokevirtual java/io/InputStream.close ()V
      // 113: aload 0
      // 114: areturn
      // 115: astore 0
      // 116: aload 6
      // 118: invokevirtual java/io/InputStream.close ()V
      // 11b: goto 126
      // 11e: astore 4
      // 120: aload 0
      // 121: aload 4
      // 123: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 126: aload 0
      // 127: athrow
      // 128: astore 0
      // 129: aload 5
      // 12b: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 12e: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 131: ldc "Failed to capture envelope"
      // 133: aload 0
      // 134: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 139: aconst_null
      // 13a: areturn
   }

   private static void deleteCurrentSessionFile(SentryOptions var0) {
      String var1 = var0.getCacheDirPath();
      if (var1 == null) {
         var0.getLogger().log(SentryLevel.INFO, "Cache dir is not set, not deleting the current session.");
      } else if (!var0.isEnableAutoSessionTracking()) {
         var0.getLogger().log(SentryLevel.DEBUG, "Session tracking is disabled, bailing from deleting current session file.");
      } else {
         if (!EnvelopeCache.getCurrentSessionFile(var1).delete()) {
            var0.getLogger().log(SentryLevel.WARNING, "Failed to delete the current session file.");
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static void deleteCurrentSessionFile(SentryOptions var0, boolean var1) {
      if (!var1) {
         try {
            ISentryExecutorService var3 = var0.getExecutorService();
            InternalSentrySdk$$ExternalSyntheticLambda1 var2 = new InternalSentrySdk$$ExternalSyntheticLambda1(var0);
            var3.submit(var2);
         } catch (Throwable var5) {
            var0.getLogger().log(SentryLevel.WARNING, "Submission of deletion of the current session file rejected.", var5);
            return;
         }
      } else {
         deleteCurrentSessionFile(var0);
      }
   }

   public static Map<String, Object> getAppStartMeasurement() {
      AppStartMetrics var1 = AppStartMetrics.getInstance();
      ArrayList var0 = new ArrayList();
      TimeSpan var2 = new TimeSpan();
      var2.setStartedAt(var1.getAppStartTimeSpan().getStartUptimeMs());
      var2.setStartUnixTimeMs(var1.getAppStartTimeSpan().getStartTimestampMs());
      var2.setStoppedAt(var1.getClassLoadedUptimeMs());
      var2.setDescription("Process Initialization");
      addTimeSpanToSerializedSpans(var2, var0);
      addTimeSpanToSerializedSpans(var1.getApplicationOnCreateTimeSpan(), var0);
      Iterator var4 = var1.getContentProviderOnCreateTimeSpans().iterator();

      while (var4.hasNext()) {
         addTimeSpanToSerializedSpans((TimeSpan)var4.next(), var0);
      }

      for (ActivityLifecycleTimeSpan var5 : var1.getActivityLifecycleTimeSpans()) {
         addTimeSpanToSerializedSpans(var5.getOnCreate(), var0);
         addTimeSpanToSerializedSpans(var5.getOnStart(), var0);
      }

      HashMap var6 = new HashMap();
      var6.put("spans", var0);
      var6.put("type", var1.getAppStartType().toString().toLowerCase(Locale.ROOT));
      if (var1.getAppStartTimeSpan().hasStarted()) {
         var6.put("app_start_timestamp_ms", var1.getAppStartTimeSpan().getStartTimestampMs());
      }

      return var6;
   }

   public static IScope getCurrentScope() {
      AtomicReference var0 = new AtomicReference();
      HubAdapter.getInstance().configureScope(new InternalSentrySdk$$ExternalSyntheticLambda0(var0));
      return (IScope)var0.get();
   }

   public static Map<String, Object> serializeScope(Context param0, SentryAndroidOptions param1, IScope param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: new java/util/HashMap
      // 003: dup
      // 004: invokespecial java/util/HashMap.<init> ()V
      // 007: astore 5
      // 009: aload 2
      // 00a: ifnonnull 010
      // 00d: aload 5
      // 00f: areturn
      // 010: aload 1
      // 011: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 014: astore 6
      // 016: new io/sentry/util/MapObjectWriter
      // 019: astore 7
      // 01b: aload 7
      // 01d: aload 5
      // 01f: invokespecial io/sentry/util/MapObjectWriter.<init> (Ljava/util/Map;)V
      // 022: aload 0
      // 023: aload 1
      // 024: invokestatic io/sentry/android/core/DeviceInfoUtil.getInstance (Landroid/content/Context;Lio/sentry/android/core/SentryAndroidOptions;)Lio/sentry/android/core/DeviceInfoUtil;
      // 027: astore 3
      // 028: aload 3
      // 029: bipush 1
      // 02a: bipush 1
      // 02b: invokevirtual io/sentry/android/core/DeviceInfoUtil.collectDeviceInformation (ZZ)Lio/sentry/protocol/Device;
      // 02e: astore 4
      // 030: aload 2
      // 031: invokeinterface io/sentry/IScope.getContexts ()Lio/sentry/protocol/Contexts; 1
      // 036: aload 4
      // 038: invokevirtual io/sentry/protocol/Contexts.setDevice (Lio/sentry/protocol/Device;)V
      // 03b: aload 2
      // 03c: invokeinterface io/sentry/IScope.getContexts ()Lio/sentry/protocol/Contexts; 1
      // 041: aload 3
      // 042: invokevirtual io/sentry/android/core/DeviceInfoUtil.getOperatingSystem ()Lio/sentry/protocol/OperatingSystem;
      // 045: invokevirtual io/sentry/protocol/Contexts.setOperatingSystem (Lio/sentry/protocol/OperatingSystem;)V
      // 048: aload 2
      // 049: invokeinterface io/sentry/IScope.getUser ()Lio/sentry/protocol/User; 1
      // 04e: astore 4
      // 050: aload 4
      // 052: astore 3
      // 053: aload 4
      // 055: ifnonnull 067
      // 058: new io/sentry/protocol/User
      // 05b: astore 3
      // 05c: aload 3
      // 05d: invokespecial io/sentry/protocol/User.<init> ()V
      // 060: aload 2
      // 061: aload 3
      // 062: invokeinterface io/sentry/IScope.setUser (Lio/sentry/protocol/User;)V 2
      // 067: aload 3
      // 068: invokevirtual io/sentry/protocol/User.getId ()Ljava/lang/String;
      // 06b: astore 4
      // 06d: aload 4
      // 06f: ifnonnull 08c
      // 072: aload 3
      // 073: aload 0
      // 074: invokestatic io/sentry/android/core/Installation.id (Landroid/content/Context;)Ljava/lang/String;
      // 077: invokevirtual io/sentry/protocol/User.setId (Ljava/lang/String;)V
      // 07a: goto 08c
      // 07d: astore 3
      // 07e: aload 6
      // 080: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 083: ldc_w "Could not retrieve installation ID"
      // 086: aload 3
      // 087: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 08c: aload 2
      // 08d: invokeinterface io/sentry/IScope.getContexts ()Lio/sentry/protocol/Contexts; 1
      // 092: invokevirtual io/sentry/protocol/Contexts.getApp ()Lio/sentry/protocol/App;
      // 095: astore 4
      // 097: aload 4
      // 099: astore 3
      // 09a: aload 4
      // 09c: ifnonnull 0a7
      // 09f: new io/sentry/protocol/App
      // 0a2: astore 3
      // 0a3: aload 3
      // 0a4: invokespecial io/sentry/protocol/App.<init> ()V
      // 0a7: aload 3
      // 0a8: aload 0
      // 0a9: invokestatic io/sentry/android/core/ContextUtils.getApplicationName (Landroid/content/Context;)Ljava/lang/String;
      // 0ac: invokevirtual io/sentry/protocol/App.setAppName (Ljava/lang/String;)V
      // 0af: invokestatic io/sentry/android/core/performance/AppStartMetrics.getInstance ()Lio/sentry/android/core/performance/AppStartMetrics;
      // 0b2: aload 1
      // 0b3: invokevirtual io/sentry/android/core/performance/AppStartMetrics.getAppStartTimeSpanWithFallback (Lio/sentry/android/core/SentryAndroidOptions;)Lio/sentry/android/core/performance/TimeSpan;
      // 0b6: astore 4
      // 0b8: aload 4
      // 0ba: invokevirtual io/sentry/android/core/performance/TimeSpan.hasStarted ()Z
      // 0bd: ifeq 0cc
      // 0c0: aload 3
      // 0c1: aload 4
      // 0c3: invokevirtual io/sentry/android/core/performance/TimeSpan.getStartTimestamp ()Lio/sentry/SentryDate;
      // 0c6: invokestatic io/sentry/DateUtils.toUtilDate (Lio/sentry/SentryDate;)Ljava/util/Date;
      // 0c9: invokevirtual io/sentry/protocol/App.setAppStartTime (Ljava/util/Date;)V
      // 0cc: new io/sentry/android/core/BuildInfoProvider
      // 0cf: astore 4
      // 0d1: aload 4
      // 0d3: aload 1
      // 0d4: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 0d7: invokespecial io/sentry/android/core/BuildInfoProvider.<init> (Lio/sentry/ILogger;)V
      // 0da: aload 0
      // 0db: sipush 4096
      // 0de: aload 1
      // 0df: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 0e2: aload 4
      // 0e4: invokestatic io/sentry/android/core/ContextUtils.getPackageInfo (Landroid/content/Context;ILio/sentry/ILogger;Lio/sentry/android/core/BuildInfoProvider;)Landroid/content/pm/PackageInfo;
      // 0e7: astore 0
      // 0e8: aload 0
      // 0e9: ifnull 0f3
      // 0ec: aload 0
      // 0ed: aload 4
      // 0ef: aload 3
      // 0f0: invokestatic io/sentry/android/core/ContextUtils.setAppPackageInfo (Landroid/content/pm/PackageInfo;Lio/sentry/android/core/BuildInfoProvider;Lio/sentry/protocol/App;)V
      // 0f3: aload 2
      // 0f4: invokeinterface io/sentry/IScope.getContexts ()Lio/sentry/protocol/Contexts; 1
      // 0f9: aload 3
      // 0fa: invokevirtual io/sentry/protocol/Contexts.setApp (Lio/sentry/protocol/App;)V
      // 0fd: aload 7
      // 0ff: ldc_w "user"
      // 102: invokeinterface io/sentry/ObjectWriter.name (Ljava/lang/String;)Lio/sentry/ObjectWriter; 2
      // 107: aload 6
      // 109: aload 2
      // 10a: invokeinterface io/sentry/IScope.getUser ()Lio/sentry/protocol/User; 1
      // 10f: invokeinterface io/sentry/ObjectWriter.value (Lio/sentry/ILogger;Ljava/lang/Object;)Lio/sentry/ObjectWriter; 3
      // 114: pop
      // 115: aload 7
      // 117: ldc_w "contexts"
      // 11a: invokeinterface io/sentry/ObjectWriter.name (Ljava/lang/String;)Lio/sentry/ObjectWriter; 2
      // 11f: aload 6
      // 121: aload 2
      // 122: invokeinterface io/sentry/IScope.getContexts ()Lio/sentry/protocol/Contexts; 1
      // 127: invokeinterface io/sentry/ObjectWriter.value (Lio/sentry/ILogger;Ljava/lang/Object;)Lio/sentry/ObjectWriter; 3
      // 12c: pop
      // 12d: aload 7
      // 12f: ldc_w "tags"
      // 132: invokeinterface io/sentry/ObjectWriter.name (Ljava/lang/String;)Lio/sentry/ObjectWriter; 2
      // 137: aload 6
      // 139: aload 2
      // 13a: invokeinterface io/sentry/IScope.getTags ()Ljava/util/Map; 1
      // 13f: invokeinterface io/sentry/ObjectWriter.value (Lio/sentry/ILogger;Ljava/lang/Object;)Lio/sentry/ObjectWriter; 3
      // 144: pop
      // 145: aload 7
      // 147: ldc_w "extras"
      // 14a: invokeinterface io/sentry/ObjectWriter.name (Ljava/lang/String;)Lio/sentry/ObjectWriter; 2
      // 14f: aload 6
      // 151: aload 2
      // 152: invokeinterface io/sentry/IScope.getExtras ()Ljava/util/Map; 1
      // 157: invokeinterface io/sentry/ObjectWriter.value (Lio/sentry/ILogger;Ljava/lang/Object;)Lio/sentry/ObjectWriter; 3
      // 15c: pop
      // 15d: aload 7
      // 15f: ldc_w "fingerprint"
      // 162: invokeinterface io/sentry/ObjectWriter.name (Ljava/lang/String;)Lio/sentry/ObjectWriter; 2
      // 167: aload 6
      // 169: aload 2
      // 16a: invokeinterface io/sentry/IScope.getFingerprint ()Ljava/util/List; 1
      // 16f: invokeinterface io/sentry/ObjectWriter.value (Lio/sentry/ILogger;Ljava/lang/Object;)Lio/sentry/ObjectWriter; 3
      // 174: pop
      // 175: aload 7
      // 177: ldc_w "level"
      // 17a: invokeinterface io/sentry/ObjectWriter.name (Ljava/lang/String;)Lio/sentry/ObjectWriter; 2
      // 17f: aload 6
      // 181: aload 2
      // 182: invokeinterface io/sentry/IScope.getLevel ()Lio/sentry/SentryLevel; 1
      // 187: invokeinterface io/sentry/ObjectWriter.value (Lio/sentry/ILogger;Ljava/lang/Object;)Lio/sentry/ObjectWriter; 3
      // 18c: pop
      // 18d: aload 7
      // 18f: ldc_w "breadcrumbs"
      // 192: invokeinterface io/sentry/ObjectWriter.name (Ljava/lang/String;)Lio/sentry/ObjectWriter; 2
      // 197: aload 6
      // 199: aload 2
      // 19a: invokeinterface io/sentry/IScope.getBreadcrumbs ()Ljava/util/Queue; 1
      // 19f: invokeinterface io/sentry/ObjectWriter.value (Lio/sentry/ILogger;Ljava/lang/Object;)Lio/sentry/ObjectWriter; 3
      // 1a4: pop
      // 1a5: aload 5
      // 1a7: areturn
      // 1a8: astore 0
      // 1a9: aload 1
      // 1aa: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 1ad: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 1b0: ldc_w "Could not serialize scope."
      // 1b3: aload 0
      // 1b4: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 1b9: new java/util/HashMap
      // 1bc: dup
      // 1bd: invokespecial java/util/HashMap.<init> ()V
      // 1c0: areturn
   }

   private static Session updateSession(IHub var0, SentryOptions var1, Session.State var2, boolean var3) {
      AtomicReference var4 = new AtomicReference();
      var0.configureScope(new InternalSentrySdk$$ExternalSyntheticLambda2(var2, var3, var4, var1));
      return (Session)var4.get();
   }
}
