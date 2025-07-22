package io.sentry;

import io.sentry.hints.Flushable;
import io.sentry.protocol.SentryId;
import io.sentry.util.HintUtils;
import io.sentry.util.LogUtils;
import io.sentry.util.Objects;
import io.sentry.util.SampleRateUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public final class OutboxSender extends DirectoryProcessor implements IEnvelopeSender {
   private static final Charset UTF_8 = Charset.forName("UTF-8");
   private final IEnvelopeReader envelopeReader;
   private final IHub hub;
   private final ILogger logger;
   private final ISerializer serializer;

   public OutboxSender(IHub var1, IEnvelopeReader var2, ISerializer var3, ILogger var4, long var5, int var7) {
      super(var1, var4, var5, var7);
      this.hub = Objects.requireNonNull(var1, "Hub is required.");
      this.envelopeReader = Objects.requireNonNull(var2, "Envelope reader is required.");
      this.serializer = Objects.requireNonNull(var3, "Serializer is required.");
      this.logger = Objects.requireNonNull(var4, "Logger is required.");
   }

   private TracesSamplingDecision extractSamplingDecision(TraceContext var1) {
      Boolean var2 = true;
      if (var1 != null) {
         String var5 = var1.getSampleRate();
         if (var5 != null) {
            try {
               Double var3 = Double.parseDouble(var5);
               if (SampleRateUtils.isValidTracesSampleRate(var3, false)) {
                  return new TracesSamplingDecision(var2, var3);
               }

               this.logger.log(SentryLevel.ERROR, "Invalid sample rate parsed from TraceContext: %s", var5);
            } catch (Exception var4) {
               this.logger.log(SentryLevel.ERROR, "Unable to parse sample rate from TraceContext: %s", var5);
            }
         }
      }

      return new TracesSamplingDecision(var2);
   }

   private void logEnvelopeItemNull(SentryEnvelopeItem var1, int var2) {
      this.logger.log(SentryLevel.ERROR, "Item %d of type %s returned null by the parser.", var2, var1.getHeader().getType());
   }

   private void logItemCaptured(int var1) {
      this.logger.log(SentryLevel.DEBUG, "Item %d is being captured.", var1);
   }

   private void logTimeout(SentryId var1) {
      this.logger.log(SentryLevel.WARNING, "Timed out waiting for event id submission: %s", var1);
   }

   private void logUnexpectedEventId(SentryEnvelope var1, SentryId var2, int var3) {
      this.logger.log(SentryLevel.ERROR, "Item %d of has a different event id (%s) to the envelope header (%s)", var3, var1.getHeader().getEventId(), var2);
   }

   private void processEnvelope(SentryEnvelope param1, Hint param2) throws IOException {
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
      // 000: aload 0
      // 001: getfield io/sentry/OutboxSender.logger Lio/sentry/ILogger;
      // 004: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 007: ldc "Processing Envelope with %d item(s)"
      // 009: bipush 1
      // 00a: anewarray 105
      // 00d: dup
      // 00e: bipush 0
      // 00f: aload 1
      // 010: invokevirtual io/sentry/SentryEnvelope.getItems ()Ljava/lang/Iterable;
      // 013: invokestatic io/sentry/util/CollectionUtils.size (Ljava/lang/Iterable;)I
      // 016: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 019: aastore
      // 01a: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 01f: aload 1
      // 020: invokevirtual io/sentry/SentryEnvelope.getItems ()Ljava/lang/Iterable;
      // 023: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 028: astore 4
      // 02a: bipush 0
      // 02b: istore 3
      // 02c: aload 4
      // 02e: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 033: ifeq 33b
      // 036: aload 4
      // 038: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 03d: checkcast io/sentry/SentryEnvelopeItem
      // 040: astore 5
      // 042: iinc 3 1
      // 045: aload 5
      // 047: invokevirtual io/sentry/SentryEnvelopeItem.getHeader ()Lio/sentry/SentryEnvelopeItemHeader;
      // 04a: ifnonnull 069
      // 04d: aload 0
      // 04e: getfield io/sentry/OutboxSender.logger Lio/sentry/ILogger;
      // 051: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 054: ldc "Item %d has no header"
      // 056: bipush 1
      // 057: anewarray 105
      // 05a: dup
      // 05b: bipush 0
      // 05c: iload 3
      // 05d: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 060: aastore
      // 061: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 066: goto 02c
      // 069: getstatic io/sentry/SentryItemType.Event Lio/sentry/SentryItemType;
      // 06c: aload 5
      // 06e: invokevirtual io/sentry/SentryEnvelopeItem.getHeader ()Lio/sentry/SentryEnvelopeItemHeader;
      // 071: invokevirtual io/sentry/SentryEnvelopeItemHeader.getType ()Lio/sentry/SentryItemType;
      // 074: invokevirtual io/sentry/SentryItemType.equals (Ljava/lang/Object;)Z
      // 077: ifeq 167
      // 07a: new java/io/BufferedReader
      // 07d: astore 6
      // 07f: new java/io/InputStreamReader
      // 082: astore 8
      // 084: new java/io/ByteArrayInputStream
      // 087: astore 7
      // 089: aload 7
      // 08b: aload 5
      // 08d: invokevirtual io/sentry/SentryEnvelopeItem.getData ()[B
      // 090: invokespecial java/io/ByteArrayInputStream.<init> ([B)V
      // 093: aload 8
      // 095: aload 7
      // 097: getstatic io/sentry/OutboxSender.UTF_8 Ljava/nio/charset/Charset;
      // 09a: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
      // 09d: aload 6
      // 09f: aload 8
      // 0a1: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 0a4: aload 0
      // 0a5: getfield io/sentry/OutboxSender.serializer Lio/sentry/ISerializer;
      // 0a8: aload 6
      // 0aa: ldc io/sentry/SentryEvent
      // 0ac: invokeinterface io/sentry/ISerializer.deserialize (Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object; 3
      // 0b1: checkcast io/sentry/SentryEvent
      // 0b4: astore 7
      // 0b6: aload 7
      // 0b8: ifnonnull 0c5
      // 0bb: aload 0
      // 0bc: aload 5
      // 0be: iload 3
      // 0bf: invokespecial io/sentry/OutboxSender.logEnvelopeItemNull (Lio/sentry/SentryEnvelopeItem;I)V
      // 0c2: goto 133
      // 0c5: aload 7
      // 0c7: invokevirtual io/sentry/SentryEvent.getSdk ()Lio/sentry/protocol/SdkVersion;
      // 0ca: ifnull 0d9
      // 0cd: aload 2
      // 0ce: aload 7
      // 0d0: invokevirtual io/sentry/SentryEvent.getSdk ()Lio/sentry/protocol/SdkVersion;
      // 0d3: invokevirtual io/sentry/protocol/SdkVersion.getName ()Ljava/lang/String;
      // 0d6: invokestatic io/sentry/util/HintUtils.setIsFromHybridSdk (Lio/sentry/Hint;Ljava/lang/String;)V
      // 0d9: aload 1
      // 0da: invokevirtual io/sentry/SentryEnvelope.getHeader ()Lio/sentry/SentryEnvelopeHeader;
      // 0dd: invokevirtual io/sentry/SentryEnvelopeHeader.getEventId ()Lio/sentry/protocol/SentryId;
      // 0e0: ifnull 108
      // 0e3: aload 1
      // 0e4: invokevirtual io/sentry/SentryEnvelope.getHeader ()Lio/sentry/SentryEnvelopeHeader;
      // 0e7: invokevirtual io/sentry/SentryEnvelopeHeader.getEventId ()Lio/sentry/protocol/SentryId;
      // 0ea: aload 7
      // 0ec: invokevirtual io/sentry/SentryEvent.getEventId ()Lio/sentry/protocol/SentryId;
      // 0ef: invokevirtual io/sentry/protocol/SentryId.equals (Ljava/lang/Object;)Z
      // 0f2: ifne 108
      // 0f5: aload 0
      // 0f6: aload 1
      // 0f7: aload 7
      // 0f9: invokevirtual io/sentry/SentryEvent.getEventId ()Lio/sentry/protocol/SentryId;
      // 0fc: iload 3
      // 0fd: invokespecial io/sentry/OutboxSender.logUnexpectedEventId (Lio/sentry/SentryEnvelope;Lio/sentry/protocol/SentryId;I)V
      // 100: aload 6
      // 102: invokevirtual java/io/Reader.close ()V
      // 105: goto 02c
      // 108: aload 0
      // 109: getfield io/sentry/OutboxSender.hub Lio/sentry/IHub;
      // 10c: aload 7
      // 10e: aload 2
      // 10f: invokeinterface io/sentry/IHub.captureEvent (Lio/sentry/SentryEvent;Lio/sentry/Hint;)Lio/sentry/protocol/SentryId; 3
      // 114: pop
      // 115: aload 0
      // 116: iload 3
      // 117: invokespecial io/sentry/OutboxSender.logItemCaptured (I)V
      // 11a: aload 0
      // 11b: aload 2
      // 11c: invokespecial io/sentry/OutboxSender.waitFlush (Lio/sentry/Hint;)Z
      // 11f: ifne 133
      // 122: aload 0
      // 123: aload 7
      // 125: invokevirtual io/sentry/SentryEvent.getEventId ()Lio/sentry/protocol/SentryId;
      // 128: invokespecial io/sentry/OutboxSender.logTimeout (Lio/sentry/protocol/SentryId;)V
      // 12b: aload 6
      // 12d: invokevirtual java/io/Reader.close ()V
      // 130: goto 33b
      // 133: aload 6
      // 135: invokevirtual java/io/Reader.close ()V
      // 138: goto 2f3
      // 13b: astore 5
      // 13d: aload 6
      // 13f: invokevirtual java/io/Reader.close ()V
      // 142: goto 14e
      // 145: astore 6
      // 147: aload 5
      // 149: aload 6
      // 14b: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 14e: aload 5
      // 150: athrow
      // 151: astore 5
      // 153: aload 0
      // 154: getfield io/sentry/OutboxSender.logger Lio/sentry/ILogger;
      // 157: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 15a: ldc_w "Item failed to process."
      // 15d: aload 5
      // 15f: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 164: goto 2f3
      // 167: getstatic io/sentry/SentryItemType.Transaction Lio/sentry/SentryItemType;
      // 16a: aload 5
      // 16c: invokevirtual io/sentry/SentryEnvelopeItem.getHeader ()Lio/sentry/SentryEnvelopeItemHeader;
      // 16f: invokevirtual io/sentry/SentryEnvelopeItemHeader.getType ()Lio/sentry/SentryItemType;
      // 172: invokevirtual io/sentry/SentryItemType.equals (Ljava/lang/Object;)Z
      // 175: ifeq 279
      // 178: new java/io/BufferedReader
      // 17b: astore 6
      // 17d: new java/io/InputStreamReader
      // 180: astore 7
      // 182: new java/io/ByteArrayInputStream
      // 185: astore 8
      // 187: aload 8
      // 189: aload 5
      // 18b: invokevirtual io/sentry/SentryEnvelopeItem.getData ()[B
      // 18e: invokespecial java/io/ByteArrayInputStream.<init> ([B)V
      // 191: aload 7
      // 193: aload 8
      // 195: getstatic io/sentry/OutboxSender.UTF_8 Ljava/nio/charset/Charset;
      // 198: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
      // 19b: aload 6
      // 19d: aload 7
      // 19f: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 1a2: aload 0
      // 1a3: getfield io/sentry/OutboxSender.serializer Lio/sentry/ISerializer;
      // 1a6: aload 6
      // 1a8: ldc_w io/sentry/protocol/SentryTransaction
      // 1ab: invokeinterface io/sentry/ISerializer.deserialize (Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object; 3
      // 1b0: checkcast io/sentry/protocol/SentryTransaction
      // 1b3: astore 7
      // 1b5: aload 7
      // 1b7: ifnonnull 1c4
      // 1ba: aload 0
      // 1bb: aload 5
      // 1bd: iload 3
      // 1be: invokespecial io/sentry/OutboxSender.logEnvelopeItemNull (Lio/sentry/SentryEnvelopeItem;I)V
      // 1c1: goto 245
      // 1c4: aload 1
      // 1c5: invokevirtual io/sentry/SentryEnvelope.getHeader ()Lio/sentry/SentryEnvelopeHeader;
      // 1c8: invokevirtual io/sentry/SentryEnvelopeHeader.getEventId ()Lio/sentry/protocol/SentryId;
      // 1cb: ifnull 1f3
      // 1ce: aload 1
      // 1cf: invokevirtual io/sentry/SentryEnvelope.getHeader ()Lio/sentry/SentryEnvelopeHeader;
      // 1d2: invokevirtual io/sentry/SentryEnvelopeHeader.getEventId ()Lio/sentry/protocol/SentryId;
      // 1d5: aload 7
      // 1d7: invokevirtual io/sentry/protocol/SentryTransaction.getEventId ()Lio/sentry/protocol/SentryId;
      // 1da: invokevirtual io/sentry/protocol/SentryId.equals (Ljava/lang/Object;)Z
      // 1dd: ifne 1f3
      // 1e0: aload 0
      // 1e1: aload 1
      // 1e2: aload 7
      // 1e4: invokevirtual io/sentry/protocol/SentryTransaction.getEventId ()Lio/sentry/protocol/SentryId;
      // 1e7: iload 3
      // 1e8: invokespecial io/sentry/OutboxSender.logUnexpectedEventId (Lio/sentry/SentryEnvelope;Lio/sentry/protocol/SentryId;I)V
      // 1eb: aload 6
      // 1ed: invokevirtual java/io/Reader.close ()V
      // 1f0: goto 02c
      // 1f3: aload 1
      // 1f4: invokevirtual io/sentry/SentryEnvelope.getHeader ()Lio/sentry/SentryEnvelopeHeader;
      // 1f7: invokevirtual io/sentry/SentryEnvelopeHeader.getTraceContext ()Lio/sentry/TraceContext;
      // 1fa: astore 5
      // 1fc: aload 7
      // 1fe: invokevirtual io/sentry/protocol/SentryTransaction.getContexts ()Lio/sentry/protocol/Contexts;
      // 201: invokevirtual io/sentry/protocol/Contexts.getTrace ()Lio/sentry/SpanContext;
      // 204: ifnull 218
      // 207: aload 7
      // 209: invokevirtual io/sentry/protocol/SentryTransaction.getContexts ()Lio/sentry/protocol/Contexts;
      // 20c: invokevirtual io/sentry/protocol/Contexts.getTrace ()Lio/sentry/SpanContext;
      // 20f: aload 0
      // 210: aload 5
      // 212: invokespecial io/sentry/OutboxSender.extractSamplingDecision (Lio/sentry/TraceContext;)Lio/sentry/TracesSamplingDecision;
      // 215: invokevirtual io/sentry/SpanContext.setSamplingDecision (Lio/sentry/TracesSamplingDecision;)V
      // 218: aload 0
      // 219: getfield io/sentry/OutboxSender.hub Lio/sentry/IHub;
      // 21c: aload 7
      // 21e: aload 5
      // 220: aload 2
      // 221: invokeinterface io/sentry/IHub.captureTransaction (Lio/sentry/protocol/SentryTransaction;Lio/sentry/TraceContext;Lio/sentry/Hint;)Lio/sentry/protocol/SentryId; 4
      // 226: pop
      // 227: aload 0
      // 228: iload 3
      // 229: invokespecial io/sentry/OutboxSender.logItemCaptured (I)V
      // 22c: aload 0
      // 22d: aload 2
      // 22e: invokespecial io/sentry/OutboxSender.waitFlush (Lio/sentry/Hint;)Z
      // 231: ifne 245
      // 234: aload 0
      // 235: aload 7
      // 237: invokevirtual io/sentry/protocol/SentryTransaction.getEventId ()Lio/sentry/protocol/SentryId;
      // 23a: invokespecial io/sentry/OutboxSender.logTimeout (Lio/sentry/protocol/SentryId;)V
      // 23d: aload 6
      // 23f: invokevirtual java/io/Reader.close ()V
      // 242: goto 33b
      // 245: aload 6
      // 247: invokevirtual java/io/Reader.close ()V
      // 24a: goto 2f3
      // 24d: astore 5
      // 24f: aload 6
      // 251: invokevirtual java/io/Reader.close ()V
      // 254: goto 260
      // 257: astore 6
      // 259: aload 5
      // 25b: aload 6
      // 25d: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 260: aload 5
      // 262: athrow
      // 263: astore 5
      // 265: aload 0
      // 266: getfield io/sentry/OutboxSender.logger Lio/sentry/ILogger;
      // 269: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 26c: ldc_w "Item failed to process."
      // 26f: aload 5
      // 271: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 276: goto 2f3
      // 279: new io/sentry/SentryEnvelope
      // 27c: dup
      // 27d: aload 1
      // 27e: invokevirtual io/sentry/SentryEnvelope.getHeader ()Lio/sentry/SentryEnvelopeHeader;
      // 281: invokevirtual io/sentry/SentryEnvelopeHeader.getEventId ()Lio/sentry/protocol/SentryId;
      // 284: aload 1
      // 285: invokevirtual io/sentry/SentryEnvelope.getHeader ()Lio/sentry/SentryEnvelopeHeader;
      // 288: invokevirtual io/sentry/SentryEnvelopeHeader.getSdkVersion ()Lio/sentry/protocol/SdkVersion;
      // 28b: aload 5
      // 28d: invokespecial io/sentry/SentryEnvelope.<init> (Lio/sentry/protocol/SentryId;Lio/sentry/protocol/SdkVersion;Lio/sentry/SentryEnvelopeItem;)V
      // 290: astore 6
      // 292: aload 0
      // 293: getfield io/sentry/OutboxSender.hub Lio/sentry/IHub;
      // 296: aload 6
      // 298: aload 2
      // 299: invokeinterface io/sentry/IHub.captureEnvelope (Lio/sentry/SentryEnvelope;Lio/sentry/Hint;)Lio/sentry/protocol/SentryId; 3
      // 29e: pop
      // 29f: aload 0
      // 2a0: getfield io/sentry/OutboxSender.logger Lio/sentry/ILogger;
      // 2a3: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 2a6: ldc_w "%s item %d is being captured."
      // 2a9: bipush 2
      // 2aa: anewarray 105
      // 2ad: dup
      // 2ae: bipush 0
      // 2af: aload 5
      // 2b1: invokevirtual io/sentry/SentryEnvelopeItem.getHeader ()Lio/sentry/SentryEnvelopeItemHeader;
      // 2b4: invokevirtual io/sentry/SentryEnvelopeItemHeader.getType ()Lio/sentry/SentryItemType;
      // 2b7: invokevirtual io/sentry/SentryItemType.getItemType ()Ljava/lang/String;
      // 2ba: aastore
      // 2bb: dup
      // 2bc: bipush 1
      // 2bd: iload 3
      // 2be: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 2c1: aastore
      // 2c2: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 2c7: aload 0
      // 2c8: aload 2
      // 2c9: invokespecial io/sentry/OutboxSender.waitFlush (Lio/sentry/Hint;)Z
      // 2cc: ifne 2f3
      // 2cf: aload 0
      // 2d0: getfield io/sentry/OutboxSender.logger Lio/sentry/ILogger;
      // 2d3: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 2d6: ldc_w "Timed out waiting for item type submission: %s"
      // 2d9: bipush 1
      // 2da: anewarray 105
      // 2dd: dup
      // 2de: bipush 0
      // 2df: aload 5
      // 2e1: invokevirtual io/sentry/SentryEnvelopeItem.getHeader ()Lio/sentry/SentryEnvelopeItemHeader;
      // 2e4: invokevirtual io/sentry/SentryEnvelopeItemHeader.getType ()Lio/sentry/SentryItemType;
      // 2e7: invokevirtual io/sentry/SentryItemType.getItemType ()Ljava/lang/String;
      // 2ea: aastore
      // 2eb: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 2f0: goto 33b
      // 2f3: aload 2
      // 2f4: invokestatic io/sentry/util/HintUtils.getSentrySdkHint (Lio/sentry/Hint;)Ljava/lang/Object;
      // 2f7: astore 5
      // 2f9: aload 5
      // 2fb: instanceof io/sentry/hints/SubmissionResult
      // 2fe: ifeq 32b
      // 301: aload 5
      // 303: checkcast io/sentry/hints/SubmissionResult
      // 306: invokeinterface io/sentry/hints/SubmissionResult.isSuccess ()Z 1
      // 30b: ifne 32b
      // 30e: aload 0
      // 30f: getfield io/sentry/OutboxSender.logger Lio/sentry/ILogger;
      // 312: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 315: ldc_w "Envelope had a failed capture at item %d. No more items will be sent."
      // 318: bipush 1
      // 319: anewarray 105
      // 31c: dup
      // 31d: bipush 0
      // 31e: iload 3
      // 31f: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 322: aastore
      // 323: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 328: goto 33b
      // 32b: aload 2
      // 32c: ldc io/sentry/hints/Resettable
      // 32e: new io/sentry/OutboxSender$$ExternalSyntheticLambda0
      // 331: dup
      // 332: invokespecial io/sentry/OutboxSender$$ExternalSyntheticLambda0.<init> ()V
      // 335: invokestatic io/sentry/util/HintUtils.runIfHasType (Lio/sentry/Hint;Ljava/lang/Class;Lio/sentry/util/HintUtils$SentryConsumer;)V
      // 338: goto 02c
      // 33b: return
   }

   private boolean waitFlush(Hint var1) {
      Object var2 = HintUtils.getSentrySdkHint(var1);
      if (var2 instanceof Flushable) {
         return ((Flushable)var2).waitFlush();
      } else {
         LogUtils.logNotInstanceOf(Flushable.class, var2, this.logger);
         return true;
      }
   }

   @Override
   protected boolean isRelevantFileName(String var1) {
      boolean var2;
      if (var1 != null && !var1.startsWith("session") && !var1.startsWith("previous_session") && !var1.startsWith("startup_crash")) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public void processEnvelopeFile(String var1, Hint var2) {
      Objects.requireNonNull(var1, "Path is required.");
      this.processFile(new File(var1), var2);
   }

   @Override
   protected void processFile(File param1, Hint param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 1
      // 001: ldc_w "File is required."
      // 004: invokestatic io/sentry/util/Objects.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      // 007: pop
      // 008: aload 0
      // 009: aload 1
      // 00a: invokevirtual java/io/File.getName ()Ljava/lang/String;
      // 00d: invokevirtual io/sentry/OutboxSender.isRelevantFileName (Ljava/lang/String;)Z
      // 010: ifne 02e
      // 013: aload 0
      // 014: getfield io/sentry/OutboxSender.logger Lio/sentry/ILogger;
      // 017: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 01a: ldc_w "File '%s' should be ignored."
      // 01d: bipush 1
      // 01e: anewarray 105
      // 021: dup
      // 022: bipush 0
      // 023: aload 1
      // 024: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 027: aastore
      // 028: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 02d: return
      // 02e: new java/io/BufferedInputStream
      // 031: astore 3
      // 032: new java/io/FileInputStream
      // 035: astore 4
      // 037: aload 4
      // 039: aload 1
      // 03a: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 03d: aload 3
      // 03e: aload 4
      // 040: invokespecial java/io/BufferedInputStream.<init> (Ljava/io/InputStream;)V
      // 043: aload 0
      // 044: getfield io/sentry/OutboxSender.envelopeReader Lio/sentry/IEnvelopeReader;
      // 047: aload 3
      // 048: invokeinterface io/sentry/IEnvelopeReader.read (Ljava/io/InputStream;)Lio/sentry/SentryEnvelope; 2
      // 04d: astore 4
      // 04f: aload 4
      // 051: ifnonnull 071
      // 054: aload 0
      // 055: getfield io/sentry/OutboxSender.logger Lio/sentry/ILogger;
      // 058: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 05b: ldc_w "Stream from path %s resulted in a null envelope."
      // 05e: bipush 1
      // 05f: anewarray 105
      // 062: dup
      // 063: bipush 0
      // 064: aload 1
      // 065: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 068: aastore
      // 069: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 06e: goto 092
      // 071: aload 0
      // 072: aload 4
      // 074: aload 2
      // 075: invokespecial io/sentry/OutboxSender.processEnvelope (Lio/sentry/SentryEnvelope;Lio/sentry/Hint;)V
      // 078: aload 0
      // 079: getfield io/sentry/OutboxSender.logger Lio/sentry/ILogger;
      // 07c: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 07f: ldc_w "File '%s' is done."
      // 082: bipush 1
      // 083: anewarray 105
      // 086: dup
      // 087: bipush 0
      // 088: aload 1
      // 089: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 08c: aastore
      // 08d: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 092: aload 3
      // 093: invokevirtual java/io/InputStream.close ()V
      // 096: ldc_w io/sentry/hints/Retryable
      // 099: astore 5
      // 09b: aload 0
      // 09c: getfield io/sentry/OutboxSender.logger Lio/sentry/ILogger;
      // 09f: astore 3
      // 0a0: new io/sentry/OutboxSender$$ExternalSyntheticLambda1
      // 0a3: dup
      // 0a4: aload 0
      // 0a5: aload 1
      // 0a6: invokespecial io/sentry/OutboxSender$$ExternalSyntheticLambda1.<init> (Lio/sentry/OutboxSender;Ljava/io/File;)V
      // 0a9: astore 4
      // 0ab: aload 5
      // 0ad: astore 1
      // 0ae: goto 0f1
      // 0b1: astore 4
      // 0b3: aload 3
      // 0b4: invokevirtual java/io/InputStream.close ()V
      // 0b7: goto 0c1
      // 0ba: astore 3
      // 0bb: aload 4
      // 0bd: aload 3
      // 0be: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 0c1: aload 4
      // 0c3: athrow
      // 0c4: astore 3
      // 0c5: goto 0fa
      // 0c8: astore 3
      // 0c9: aload 0
      // 0ca: getfield io/sentry/OutboxSender.logger Lio/sentry/ILogger;
      // 0cd: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 0d0: ldc_w "Error processing envelope."
      // 0d3: aload 3
      // 0d4: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 0d9: ldc_w io/sentry/hints/Retryable
      // 0dc: astore 5
      // 0de: aload 0
      // 0df: getfield io/sentry/OutboxSender.logger Lio/sentry/ILogger;
      // 0e2: astore 3
      // 0e3: new io/sentry/OutboxSender$$ExternalSyntheticLambda1
      // 0e6: dup
      // 0e7: aload 0
      // 0e8: aload 1
      // 0e9: invokespecial io/sentry/OutboxSender$$ExternalSyntheticLambda1.<init> (Lio/sentry/OutboxSender;Ljava/io/File;)V
      // 0ec: astore 4
      // 0ee: aload 5
      // 0f0: astore 1
      // 0f1: aload 2
      // 0f2: aload 1
      // 0f3: aload 3
      // 0f4: aload 4
      // 0f6: invokestatic io/sentry/util/HintUtils.runIfHasTypeLogIfNot (Lio/sentry/Hint;Ljava/lang/Class;Lio/sentry/ILogger;Lio/sentry/util/HintUtils$SentryConsumer;)V
      // 0f9: return
      // 0fa: aload 2
      // 0fb: ldc_w io/sentry/hints/Retryable
      // 0fe: aload 0
      // 0ff: getfield io/sentry/OutboxSender.logger Lio/sentry/ILogger;
      // 102: new io/sentry/OutboxSender$$ExternalSyntheticLambda1
      // 105: dup
      // 106: aload 0
      // 107: aload 1
      // 108: invokespecial io/sentry/OutboxSender$$ExternalSyntheticLambda1.<init> (Lio/sentry/OutboxSender;Ljava/io/File;)V
      // 10b: invokestatic io/sentry/util/HintUtils.runIfHasTypeLogIfNot (Lio/sentry/Hint;Ljava/lang/Class;Lio/sentry/ILogger;Lio/sentry/util/HintUtils$SentryConsumer;)V
      // 10e: aload 3
      // 10f: athrow
   }
}
