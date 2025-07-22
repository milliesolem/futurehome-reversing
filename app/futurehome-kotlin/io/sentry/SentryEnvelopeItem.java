package io.sentry;

import io.sentry.clientreport.ClientReport;
import io.sentry.exception.SentryEnvelopeException;
import io.sentry.metrics.EncodedMetrics;
import io.sentry.protocol.SentryTransaction;
import io.sentry.util.Objects;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.Callable;

public final class SentryEnvelopeItem {
   private static final Charset UTF_8 = Charset.forName("UTF-8");
   private byte[] data;
   private final Callable<byte[]> dataFactory;
   private final SentryEnvelopeItemHeader header;

   SentryEnvelopeItem(SentryEnvelopeItemHeader var1, Callable<byte[]> var2) {
      this.header = Objects.requireNonNull(var1, "SentryEnvelopeItemHeader is required.");
      this.dataFactory = Objects.requireNonNull(var2, "DataFactory is required.");
      this.data = null;
   }

   SentryEnvelopeItem(SentryEnvelopeItemHeader var1, byte[] var2) {
      this.header = Objects.requireNonNull(var1, "SentryEnvelopeItemHeader is required.");
      this.data = var2;
      this.dataFactory = null;
   }

   private static void ensureAttachmentSizeLimit(long var0, long var2, String var4) throws SentryEnvelopeException {
      if (var0 > var2) {
         throw new SentryEnvelopeException(
            String.format(
               "Dropping attachment with filename '%s', because the size of the passed bytes with %d bytes is bigger than the maximum allowed attachment size of %d bytes.",
               var4,
               var0,
               var2
            )
         );
      }
   }

   public static SentryEnvelopeItem fromAttachment(ISerializer var0, ILogger var1, Attachment var2, long var3) {
      SentryEnvelopeItem.CachedItem var5 = new SentryEnvelopeItem.CachedItem(new SentryEnvelopeItem$$ExternalSyntheticLambda20(var2, var3, var0, var1));
      return new SentryEnvelopeItem(
         new SentryEnvelopeItemHeader(
            SentryItemType.Attachment,
            new SentryEnvelopeItem$$ExternalSyntheticLambda21(var5),
            var2.getContentType(),
            var2.getFilename(),
            var2.getAttachmentType()
         ),
         new SentryEnvelopeItem$$ExternalSyntheticLambda22(var5)
      );
   }

   public static SentryEnvelopeItem fromCheckIn(ISerializer var0, CheckIn var1) {
      Objects.requireNonNull(var0, "ISerializer is required.");
      Objects.requireNonNull(var1, "CheckIn is required.");
      SentryEnvelopeItem.CachedItem var2 = new SentryEnvelopeItem.CachedItem(new SentryEnvelopeItem$$ExternalSyntheticLambda16(var0, var1));
      return new SentryEnvelopeItem(
         new SentryEnvelopeItemHeader(SentryItemType.CheckIn, new SentryEnvelopeItem$$ExternalSyntheticLambda17(var2), "application/json", null),
         new SentryEnvelopeItem$$ExternalSyntheticLambda18(var2)
      );
   }

   public static SentryEnvelopeItem fromClientReport(ISerializer var0, ClientReport var1) throws IOException {
      Objects.requireNonNull(var0, "ISerializer is required.");
      Objects.requireNonNull(var1, "ClientReport is required.");
      SentryEnvelopeItem.CachedItem var2 = new SentryEnvelopeItem.CachedItem(new SentryEnvelopeItem$$ExternalSyntheticLambda13(var0, var1));
      return new SentryEnvelopeItem(
         new SentryEnvelopeItemHeader(SentryItemType.resolve(var1), new SentryEnvelopeItem$$ExternalSyntheticLambda14(var2), "application/json", null),
         new SentryEnvelopeItem$$ExternalSyntheticLambda15(var2)
      );
   }

   public static SentryEnvelopeItem fromEvent(ISerializer var0, SentryBaseEvent var1) {
      Objects.requireNonNull(var0, "ISerializer is required.");
      Objects.requireNonNull(var1, "SentryEvent is required.");
      SentryEnvelopeItem.CachedItem var2 = new SentryEnvelopeItem.CachedItem(new SentryEnvelopeItem$$ExternalSyntheticLambda3(var0, var1));
      return new SentryEnvelopeItem(
         new SentryEnvelopeItemHeader(SentryItemType.resolve(var1), new SentryEnvelopeItem$$ExternalSyntheticLambda4(var2), "application/json", null),
         new SentryEnvelopeItem$$ExternalSyntheticLambda5(var2)
      );
   }

   public static SentryEnvelopeItem fromMetrics(EncodedMetrics var0) {
      SentryEnvelopeItem.CachedItem var1 = new SentryEnvelopeItem.CachedItem(new SentryEnvelopeItem$$ExternalSyntheticLambda9(var0));
      return new SentryEnvelopeItem(
         new SentryEnvelopeItemHeader(SentryItemType.Statsd, new SentryEnvelopeItem$$ExternalSyntheticLambda10(var1), "application/octet-stream", null),
         new SentryEnvelopeItem$$ExternalSyntheticLambda12(var1)
      );
   }

   public static SentryEnvelopeItem fromProfilingTrace(ProfilingTraceData var0, long var1, ISerializer var3) throws SentryEnvelopeException {
      File var4 = var0.getTraceFile();
      SentryEnvelopeItem.CachedItem var5 = new SentryEnvelopeItem.CachedItem(new SentryEnvelopeItem$$ExternalSyntheticLambda23(var4, var1, var0, var3));
      return new SentryEnvelopeItem(
         new SentryEnvelopeItemHeader(SentryItemType.Profile, new SentryEnvelopeItem$$ExternalSyntheticLambda24(var5), "application-json", var4.getName()),
         new SentryEnvelopeItem$$ExternalSyntheticLambda25(var5)
      );
   }

   public static SentryEnvelopeItem fromReplay(ISerializer var0, ILogger var1, SentryReplayEvent var2, ReplayRecording var3, boolean var4) {
      SentryEnvelopeItem.CachedItem var5 = new SentryEnvelopeItem.CachedItem(
         new SentryEnvelopeItem$$ExternalSyntheticLambda0(var0, var2, var3, var2.getVideoFile(), var1, var4)
      );
      return new SentryEnvelopeItem(
         new SentryEnvelopeItemHeader(SentryItemType.ReplayVideo, new SentryEnvelopeItem$$ExternalSyntheticLambda11(var5), null, null),
         new SentryEnvelopeItem$$ExternalSyntheticLambda19(var5)
      );
   }

   public static SentryEnvelopeItem fromSession(ISerializer var0, Session var1) throws IOException {
      Objects.requireNonNull(var0, "ISerializer is required.");
      Objects.requireNonNull(var1, "Session is required.");
      SentryEnvelopeItem.CachedItem var2 = new SentryEnvelopeItem.CachedItem(new SentryEnvelopeItem$$ExternalSyntheticLambda26(var0, var1));
      return new SentryEnvelopeItem(
         new SentryEnvelopeItemHeader(SentryItemType.Session, new SentryEnvelopeItem$$ExternalSyntheticLambda1(var2), "application/json", null),
         new SentryEnvelopeItem$$ExternalSyntheticLambda2(var2)
      );
   }

   public static SentryEnvelopeItem fromUserFeedback(ISerializer var0, UserFeedback var1) {
      Objects.requireNonNull(var0, "ISerializer is required.");
      Objects.requireNonNull(var1, "UserFeedback is required.");
      SentryEnvelopeItem.CachedItem var2 = new SentryEnvelopeItem.CachedItem(new SentryEnvelopeItem$$ExternalSyntheticLambda6(var0, var1));
      return new SentryEnvelopeItem(
         new SentryEnvelopeItemHeader(SentryItemType.UserFeedback, new SentryEnvelopeItem$$ExternalSyntheticLambda7(var2), "application/json", null),
         new SentryEnvelopeItem$$ExternalSyntheticLambda8(var2)
      );
   }

   private static byte[] serializeToMsgpack(Map<String, byte[]> param0) throws IOException {
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
      // 00: new java/io/ByteArrayOutputStream
      // 03: dup
      // 04: invokespecial java/io/ByteArrayOutputStream.<init> ()V
      // 07: astore 2
      // 08: aload 2
      // 09: aload 0
      // 0a: invokeinterface java/util/Map.size ()I 1
      // 0f: sipush 128
      // 12: ior
      // 13: i2b
      // 14: invokevirtual java/io/ByteArrayOutputStream.write (I)V
      // 17: aload 0
      // 18: invokeinterface java/util/Map.entrySet ()Ljava/util/Set; 1
      // 1d: invokeinterface java/util/Set.iterator ()Ljava/util/Iterator; 1
      // 22: astore 0
      // 23: aload 0
      // 24: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 29: ifeq 8d
      // 2c: aload 0
      // 2d: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 32: checkcast java/util/Map$Entry
      // 35: astore 3
      // 36: aload 3
      // 37: invokeinterface java/util/Map$Entry.getKey ()Ljava/lang/Object; 1
      // 3c: checkcast java/lang/String
      // 3f: getstatic io/sentry/SentryEnvelopeItem.UTF_8 Ljava/nio/charset/Charset;
      // 42: invokevirtual java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
      // 45: astore 4
      // 47: aload 4
      // 49: arraylength
      // 4a: istore 1
      // 4b: aload 2
      // 4c: bipush -39
      // 4e: invokevirtual java/io/ByteArrayOutputStream.write (I)V
      // 51: aload 2
      // 52: iload 1
      // 53: i2b
      // 54: invokevirtual java/io/ByteArrayOutputStream.write (I)V
      // 57: aload 2
      // 58: aload 4
      // 5a: invokevirtual java/io/ByteArrayOutputStream.write ([B)V
      // 5d: aload 3
      // 5e: invokeinterface java/util/Map$Entry.getValue ()Ljava/lang/Object; 1
      // 63: checkcast [B
      // 66: astore 3
      // 67: aload 3
      // 68: arraylength
      // 69: istore 1
      // 6a: aload 2
      // 6b: bipush -58
      // 6d: invokevirtual java/io/ByteArrayOutputStream.write (I)V
      // 70: aload 2
      // 71: bipush 4
      // 72: invokestatic java/nio/ByteBuffer.allocate (I)Ljava/nio/ByteBuffer;
      // 75: getstatic java/nio/ByteOrder.BIG_ENDIAN Ljava/nio/ByteOrder;
      // 78: invokevirtual java/nio/ByteBuffer.order (Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
      // 7b: iload 1
      // 7c: invokevirtual java/nio/ByteBuffer.putInt (I)Ljava/nio/ByteBuffer;
      // 7f: invokevirtual java/nio/ByteBuffer.array ()[B
      // 82: invokevirtual java/io/ByteArrayOutputStream.write ([B)V
      // 85: aload 2
      // 86: aload 3
      // 87: invokevirtual java/io/ByteArrayOutputStream.write ([B)V
      // 8a: goto 23
      // 8d: aload 2
      // 8e: invokevirtual java/io/ByteArrayOutputStream.toByteArray ()[B
      // 91: astore 0
      // 92: aload 2
      // 93: invokevirtual java/io/ByteArrayOutputStream.close ()V
      // 96: aload 0
      // 97: areturn
      // 98: astore 0
      // 99: aload 2
      // 9a: invokevirtual java/io/ByteArrayOutputStream.close ()V
      // 9d: goto a6
      // a0: astore 2
      // a1: aload 0
      // a2: aload 2
      // a3: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // a6: aload 0
      // a7: athrow
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public ClientReport getClientReport(ISerializer var1) throws Exception {
      SentryEnvelopeItemHeader var2 = this.header;
      if (var2 != null && var2.getType() == SentryItemType.ClientReport) {
         BufferedReader var10 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(this.getData()), UTF_8));

         try {
            var9 = var1.deserialize(var10, ClientReport.class);
         } catch (Throwable var8) {
            try {
               var10.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
               throw var8;
            }

            throw var8;
         }

         var10.close();
         return var9;
      } else {
         return null;
      }
   }

   public byte[] getData() throws Exception {
      if (this.data == null) {
         Callable var1 = this.dataFactory;
         if (var1 != null) {
            this.data = (byte[])var1.call();
         }
      }

      return this.data;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public SentryEvent getEvent(ISerializer var1) throws Exception {
      SentryEnvelopeItemHeader var2 = this.header;
      if (var2 != null && var2.getType() == SentryItemType.Event) {
         BufferedReader var10 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(this.getData()), UTF_8));

         try {
            var9 = var1.deserialize(var10, SentryEvent.class);
         } catch (Throwable var8) {
            try {
               var10.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
               throw var8;
            }

            throw var8;
         }

         var10.close();
         return var9;
      } else {
         return null;
      }
   }

   public SentryEnvelopeItemHeader getHeader() {
      return this.header;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public SentryTransaction getTransaction(ISerializer var1) throws Exception {
      SentryEnvelopeItemHeader var2 = this.header;
      if (var2 != null && var2.getType() == SentryItemType.Transaction) {
         BufferedReader var10 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(this.getData()), UTF_8));

         try {
            var9 = var1.deserialize(var10, SentryTransaction.class);
         } catch (Throwable var8) {
            try {
               var10.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
               throw var8;
            }

            throw var8;
         }

         var10.close();
         return var9;
      } else {
         return null;
      }
   }

   private static class CachedItem {
      private byte[] bytes;
      private final Callable<byte[]> dataFactory;

      public CachedItem(Callable<byte[]> var1) {
         this.dataFactory = var1;
      }

      private static byte[] orEmptyArray(byte[] var0) {
         if (var0 == null) {
            var0 = new byte[0];
         }

         return var0;
      }

      public byte[] getBytes() throws Exception {
         if (this.bytes == null) {
            Callable var1 = this.dataFactory;
            if (var1 != null) {
               this.bytes = (byte[])var1.call();
            }
         }

         return orEmptyArray(this.bytes);
      }
   }
}
