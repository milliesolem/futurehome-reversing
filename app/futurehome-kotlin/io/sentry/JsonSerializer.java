package io.sentry;

import io.sentry.clientreport.ClientReport;
import io.sentry.profilemeasurements.ProfileMeasurement;
import io.sentry.profilemeasurements.ProfileMeasurementValue;
import io.sentry.protocol.App;
import io.sentry.protocol.Browser;
import io.sentry.protocol.Contexts;
import io.sentry.protocol.DebugImage;
import io.sentry.protocol.DebugMeta;
import io.sentry.protocol.Device;
import io.sentry.protocol.Geo;
import io.sentry.protocol.Gpu;
import io.sentry.protocol.MeasurementValue;
import io.sentry.protocol.Mechanism;
import io.sentry.protocol.Message;
import io.sentry.protocol.MetricSummary;
import io.sentry.protocol.OperatingSystem;
import io.sentry.protocol.Request;
import io.sentry.protocol.SdkInfo;
import io.sentry.protocol.SdkVersion;
import io.sentry.protocol.SentryException;
import io.sentry.protocol.SentryPackage;
import io.sentry.protocol.SentryRuntime;
import io.sentry.protocol.SentrySpan;
import io.sentry.protocol.SentryStackFrame;
import io.sentry.protocol.SentryStackTrace;
import io.sentry.protocol.SentryThread;
import io.sentry.protocol.SentryTransaction;
import io.sentry.protocol.User;
import io.sentry.protocol.ViewHierarchy;
import io.sentry.protocol.ViewHierarchyNode;
import io.sentry.rrweb.RRWebBreadcrumbEvent;
import io.sentry.rrweb.RRWebEventType;
import io.sentry.rrweb.RRWebInteractionEvent;
import io.sentry.rrweb.RRWebInteractionMoveEvent;
import io.sentry.rrweb.RRWebMetaEvent;
import io.sentry.rrweb.RRWebSpanEvent;
import io.sentry.rrweb.RRWebVideoEvent;
import io.sentry.util.Objects;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class JsonSerializer implements ISerializer {
   private static final Charset UTF_8 = Charset.forName("UTF-8");
   private final Map<Class<?>, JsonDeserializer<?>> deserializersByClass;
   private final SentryOptions options;

   public JsonSerializer(SentryOptions var1) {
      this.options = var1;
      HashMap var2 = new HashMap();
      this.deserializersByClass = var2;
      var2.put(App.class, new App.Deserializer());
      var2.put(Breadcrumb.class, new Breadcrumb.Deserializer());
      var2.put(Browser.class, new Browser.Deserializer());
      var2.put(Contexts.class, new Contexts.Deserializer());
      var2.put(DebugImage.class, new DebugImage.Deserializer());
      var2.put(DebugMeta.class, new DebugMeta.Deserializer());
      var2.put(Device.class, new Device.Deserializer());
      var2.put(Device.DeviceOrientation.class, new Device.DeviceOrientation.Deserializer());
      var2.put(Gpu.class, new Gpu.Deserializer());
      var2.put(MeasurementValue.class, new MeasurementValue.Deserializer());
      var2.put(Mechanism.class, new Mechanism.Deserializer());
      var2.put(Message.class, new Message.Deserializer());
      var2.put(MetricSummary.class, new MetricSummary.Deserializer());
      var2.put(OperatingSystem.class, new OperatingSystem.Deserializer());
      var2.put(ProfilingTraceData.class, new ProfilingTraceData.Deserializer());
      var2.put(ProfilingTransactionData.class, new ProfilingTransactionData.Deserializer());
      var2.put(ProfileMeasurement.class, new ProfileMeasurement.Deserializer());
      var2.put(ProfileMeasurementValue.class, new ProfileMeasurementValue.Deserializer());
      var2.put(Request.class, new Request.Deserializer());
      var2.put(ReplayRecording.class, new ReplayRecording.Deserializer());
      var2.put(RRWebBreadcrumbEvent.class, new RRWebBreadcrumbEvent.Deserializer());
      var2.put(RRWebEventType.class, new RRWebEventType.Deserializer());
      var2.put(RRWebInteractionEvent.class, new RRWebInteractionEvent.Deserializer());
      var2.put(RRWebInteractionMoveEvent.class, new RRWebInteractionMoveEvent.Deserializer());
      var2.put(RRWebMetaEvent.class, new RRWebMetaEvent.Deserializer());
      var2.put(RRWebSpanEvent.class, new RRWebSpanEvent.Deserializer());
      var2.put(RRWebVideoEvent.class, new RRWebVideoEvent.Deserializer());
      var2.put(SdkInfo.class, new SdkInfo.Deserializer());
      var2.put(SdkVersion.class, new SdkVersion.Deserializer());
      var2.put(SentryEnvelopeHeader.class, new SentryEnvelopeHeader.Deserializer());
      var2.put(SentryEnvelopeItemHeader.class, new SentryEnvelopeItemHeader.Deserializer());
      var2.put(SentryEvent.class, new SentryEvent.Deserializer());
      var2.put(SentryException.class, new SentryException.Deserializer());
      var2.put(SentryItemType.class, new SentryItemType.Deserializer());
      var2.put(SentryLevel.class, new SentryLevel.Deserializer());
      var2.put(SentryLockReason.class, new SentryLockReason.Deserializer());
      var2.put(SentryPackage.class, new SentryPackage.Deserializer());
      var2.put(SentryRuntime.class, new SentryRuntime.Deserializer());
      var2.put(SentryReplayEvent.class, new SentryReplayEvent.Deserializer());
      var2.put(SentrySpan.class, new SentrySpan.Deserializer());
      var2.put(SentryStackFrame.class, new SentryStackFrame.Deserializer());
      var2.put(SentryStackTrace.class, new SentryStackTrace.Deserializer());
      var2.put(SentryAppStartProfilingOptions.class, new SentryAppStartProfilingOptions.Deserializer());
      var2.put(SentryThread.class, new SentryThread.Deserializer());
      var2.put(SentryTransaction.class, new SentryTransaction.Deserializer());
      var2.put(Session.class, new Session.Deserializer());
      var2.put(SpanContext.class, new SpanContext.Deserializer());
      var2.put(SpanId.class, new SpanId.Deserializer());
      var2.put(SpanStatus.class, new SpanStatus.Deserializer());
      var2.put(User.class, new User.Deserializer());
      var2.put(Geo.class, new Geo.Deserializer());
      var2.put(UserFeedback.class, new UserFeedback.Deserializer());
      var2.put(ClientReport.class, new ClientReport.Deserializer());
      var2.put(ViewHierarchyNode.class, new ViewHierarchyNode.Deserializer());
      var2.put(ViewHierarchy.class, new ViewHierarchy.Deserializer());
   }

   private <T> boolean isKnownPrimitive(Class<T> var1) {
      boolean var2;
      if (!var1.isArray() && !Collection.class.isAssignableFrom(var1) && !String.class.isAssignableFrom(var1) && !Map.class.isAssignableFrom(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   private String serializeToString(Object var1, boolean var2) throws IOException {
      StringWriter var3 = new StringWriter();
      JsonObjectWriter var4 = new JsonObjectWriter(var3, this.options.getMaxDepth());
      if (var2) {
         var4.setIndent("\t");
      }

      var4.value(this.options.getLogger(), var1);
      return var3.toString();
   }

   @Override
   public <T> T deserialize(Reader param1, Class<T> param2) {
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
      // 00: new io/sentry/JsonObjectReader
      // 03: astore 3
      // 04: aload 3
      // 05: aload 1
      // 06: invokespecial io/sentry/JsonObjectReader.<init> (Ljava/io/Reader;)V
      // 09: aload 0
      // 0a: getfield io/sentry/JsonSerializer.deserializersByClass Ljava/util/Map;
      // 0d: aload 2
      // 0e: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 13: checkcast io/sentry/JsonDeserializer
      // 16: astore 1
      // 17: aload 1
      // 18: ifnull 34
      // 1b: aload 2
      // 1c: aload 1
      // 1d: aload 3
      // 1e: aload 0
      // 1f: getfield io/sentry/JsonSerializer.options Lio/sentry/SentryOptions;
      // 22: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 25: invokeinterface io/sentry/JsonDeserializer.deserialize (Lio/sentry/ObjectReader;Lio/sentry/ILogger;)Ljava/lang/Object; 3
      // 2a: invokevirtual java/lang/Class.cast (Ljava/lang/Object;)Ljava/lang/Object;
      // 2d: astore 1
      // 2e: aload 3
      // 2f: invokevirtual io/sentry/JsonObjectReader.close ()V
      // 32: aload 1
      // 33: areturn
      // 34: aload 0
      // 35: aload 2
      // 36: invokespecial io/sentry/JsonSerializer.isKnownPrimitive (Ljava/lang/Class;)Z
      // 39: ifeq 47
      // 3c: aload 3
      // 3d: invokevirtual io/sentry/JsonObjectReader.nextObjectOrNull ()Ljava/lang/Object;
      // 40: astore 1
      // 41: aload 3
      // 42: invokevirtual io/sentry/JsonObjectReader.close ()V
      // 45: aload 1
      // 46: areturn
      // 47: aload 3
      // 48: invokevirtual io/sentry/JsonObjectReader.close ()V
      // 4b: aconst_null
      // 4c: areturn
      // 4d: astore 1
      // 4e: aload 3
      // 4f: invokevirtual io/sentry/JsonObjectReader.close ()V
      // 52: goto 5b
      // 55: astore 2
      // 56: aload 1
      // 57: aload 2
      // 58: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 5b: aload 1
      // 5c: athrow
      // 5d: astore 1
      // 5e: aload 0
      // 5f: getfield io/sentry/JsonSerializer.options Lio/sentry/SentryOptions;
      // 62: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 65: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 68: ldc_w "Error when deserializing"
      // 6b: aload 1
      // 6c: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 71: aconst_null
      // 72: areturn
   }

   @Override
   public <T, R> T deserializeCollection(Reader param1, Class<T> param2, JsonDeserializer<R> param3) {
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
      // 00: new io/sentry/JsonObjectReader
      // 03: astore 4
      // 05: aload 4
      // 07: aload 1
      // 08: invokespecial io/sentry/JsonObjectReader.<init> (Ljava/io/Reader;)V
      // 0b: ldc_w java/util/Collection
      // 0e: aload 2
      // 0f: invokevirtual java/lang/Class.isAssignableFrom (Ljava/lang/Class;)Z
      // 12: ifeq 3b
      // 15: aload 3
      // 16: ifnonnull 26
      // 19: aload 4
      // 1b: invokevirtual io/sentry/JsonObjectReader.nextObjectOrNull ()Ljava/lang/Object;
      // 1e: astore 1
      // 1f: aload 4
      // 21: invokevirtual io/sentry/JsonObjectReader.close ()V
      // 24: aload 1
      // 25: areturn
      // 26: aload 4
      // 28: aload 0
      // 29: getfield io/sentry/JsonSerializer.options Lio/sentry/SentryOptions;
      // 2c: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 2f: aload 3
      // 30: invokevirtual io/sentry/JsonObjectReader.nextListOrNull (Lio/sentry/ILogger;Lio/sentry/JsonDeserializer;)Ljava/util/List;
      // 33: astore 1
      // 34: aload 4
      // 36: invokevirtual io/sentry/JsonObjectReader.close ()V
      // 39: aload 1
      // 3a: areturn
      // 3b: aload 4
      // 3d: invokevirtual io/sentry/JsonObjectReader.nextObjectOrNull ()Ljava/lang/Object;
      // 40: astore 1
      // 41: aload 4
      // 43: invokevirtual io/sentry/JsonObjectReader.close ()V
      // 46: aload 1
      // 47: areturn
      // 48: astore 1
      // 49: aload 4
      // 4b: invokevirtual io/sentry/JsonObjectReader.close ()V
      // 4e: goto 57
      // 51: astore 2
      // 52: aload 1
      // 53: aload 2
      // 54: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 57: aload 1
      // 58: athrow
      // 59: astore 1
      // 5a: aload 0
      // 5b: getfield io/sentry/JsonSerializer.options Lio/sentry/SentryOptions;
      // 5e: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 61: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 64: ldc_w "Error when deserializing"
      // 67: aload 1
      // 68: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 6d: aconst_null
      // 6e: areturn
   }

   @Override
   public SentryEnvelope deserializeEnvelope(InputStream var1) {
      Objects.requireNonNull(var1, "The InputStream object is required.");

      try {
         return this.options.getEnvelopeReader().read(var1);
      } catch (IOException var2) {
         this.options.getLogger().log(SentryLevel.ERROR, "Error deserializing envelope.", var2);
         return null;
      }
   }

   @Override
   public String serialize(Map<String, Object> var1) throws Exception {
      return this.serializeToString(var1, false);
   }

   @Override
   public void serialize(SentryEnvelope param1, OutputStream param2) throws Exception {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 1
      // 01: ldc_w "The SentryEnvelope object is required."
      // 04: invokestatic io/sentry/util/Objects.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      // 07: pop
      // 08: aload 2
      // 09: ldc_w "The Stream object is required."
      // 0c: invokestatic io/sentry/util/Objects.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      // 0f: pop
      // 10: new java/io/BufferedWriter
      // 13: dup
      // 14: new java/io/OutputStreamWriter
      // 17: dup
      // 18: new java/io/BufferedOutputStream
      // 1b: dup
      // 1c: aload 2
      // 1d: invokespecial java/io/BufferedOutputStream.<init> (Ljava/io/OutputStream;)V
      // 20: getstatic io/sentry/JsonSerializer.UTF_8 Ljava/nio/charset/Charset;
      // 23: invokespecial java/io/OutputStreamWriter.<init> (Ljava/io/OutputStream;Ljava/nio/charset/Charset;)V
      // 26: invokespecial java/io/BufferedWriter.<init> (Ljava/io/Writer;)V
      // 29: astore 3
      // 2a: aload 1
      // 2b: invokevirtual io/sentry/SentryEnvelope.getHeader ()Lio/sentry/SentryEnvelopeHeader;
      // 2e: astore 5
      // 30: new io/sentry/JsonObjectWriter
      // 33: astore 4
      // 35: aload 4
      // 37: aload 3
      // 38: aload 0
      // 39: getfield io/sentry/JsonSerializer.options Lio/sentry/SentryOptions;
      // 3c: invokevirtual io/sentry/SentryOptions.getMaxDepth ()I
      // 3f: invokespecial io/sentry/JsonObjectWriter.<init> (Ljava/io/Writer;I)V
      // 42: aload 5
      // 44: aload 4
      // 46: aload 0
      // 47: getfield io/sentry/JsonSerializer.options Lio/sentry/SentryOptions;
      // 4a: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 4d: invokevirtual io/sentry/SentryEnvelopeHeader.serialize (Lio/sentry/ObjectWriter;Lio/sentry/ILogger;)V
      // 50: aload 3
      // 51: ldc_w "\n"
      // 54: invokevirtual java/io/Writer.write (Ljava/lang/String;)V
      // 57: aload 1
      // 58: invokevirtual io/sentry/SentryEnvelope.getItems ()Ljava/lang/Iterable;
      // 5b: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 60: astore 1
      // 61: aload 1
      // 62: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 67: ifeq d7
      // 6a: aload 1
      // 6b: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 70: checkcast io/sentry/SentryEnvelopeItem
      // 73: astore 5
      // 75: aload 5
      // 77: invokevirtual io/sentry/SentryEnvelopeItem.getData ()[B
      // 7a: astore 4
      // 7c: aload 5
      // 7e: invokevirtual io/sentry/SentryEnvelopeItem.getHeader ()Lio/sentry/SentryEnvelopeItemHeader;
      // 81: astore 5
      // 83: new io/sentry/JsonObjectWriter
      // 86: astore 6
      // 88: aload 6
      // 8a: aload 3
      // 8b: aload 0
      // 8c: getfield io/sentry/JsonSerializer.options Lio/sentry/SentryOptions;
      // 8f: invokevirtual io/sentry/SentryOptions.getMaxDepth ()I
      // 92: invokespecial io/sentry/JsonObjectWriter.<init> (Ljava/io/Writer;I)V
      // 95: aload 5
      // 97: aload 6
      // 99: aload 0
      // 9a: getfield io/sentry/JsonSerializer.options Lio/sentry/SentryOptions;
      // 9d: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // a0: invokevirtual io/sentry/SentryEnvelopeItemHeader.serialize (Lio/sentry/ObjectWriter;Lio/sentry/ILogger;)V
      // a3: aload 3
      // a4: ldc_w "\n"
      // a7: invokevirtual java/io/Writer.write (Ljava/lang/String;)V
      // aa: aload 3
      // ab: invokevirtual java/io/Writer.flush ()V
      // ae: aload 2
      // af: aload 4
      // b1: invokevirtual java/io/OutputStream.write ([B)V
      // b4: aload 3
      // b5: ldc_w "\n"
      // b8: invokevirtual java/io/Writer.write (Ljava/lang/String;)V
      // bb: goto 61
      // be: astore 4
      // c0: aload 0
      // c1: getfield io/sentry/JsonSerializer.options Lio/sentry/SentryOptions;
      // c4: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // c7: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // ca: ldc_w "Failed to create envelope item. Dropping it."
      // cd: aload 4
      // cf: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // d4: goto 61
      // d7: aload 3
      // d8: invokevirtual java/io/Writer.flush ()V
      // db: return
      // dc: astore 1
      // dd: aload 3
      // de: invokevirtual java/io/Writer.flush ()V
      // e1: aload 1
      // e2: athrow
   }

   @Override
   public <T> void serialize(T var1, Writer var2) throws IOException {
      Objects.requireNonNull(var1, "The entity is required.");
      Objects.requireNonNull(var2, "The Writer object is required.");
      if (this.options.getLogger().isEnabled(SentryLevel.DEBUG)) {
         String var3 = this.serializeToString(var1, this.options.isEnablePrettySerializationOutput());
         this.options.getLogger().log(SentryLevel.DEBUG, "Serializing object: %s", var3);
      }

      new JsonObjectWriter(var2, this.options.getMaxDepth()).value(this.options.getLogger(), var1);
      var2.flush();
   }
}
