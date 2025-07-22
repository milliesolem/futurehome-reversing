package io.sentry;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;

public final class EnvelopeReader implements IEnvelopeReader {
   private static final Charset UTF_8 = Charset.forName("UTF-8");
   private final ISerializer serializer;

   public EnvelopeReader(ISerializer var1) {
      this.serializer = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private SentryEnvelopeHeader deserializeEnvelopeHeader(byte[] var1, int var2, int var3) {
      StringReader var11 = new StringReader(new String(var1, var2, var3, UTF_8));

      SentryEnvelopeHeader var4;
      try {
         var4 = this.serializer.deserialize(var11, SentryEnvelopeHeader.class);
      } catch (Throwable var10) {
         try {
            var11.close();
         } catch (Throwable var9) {
            var10.addSuppressed(var9);
            throw var10;
         }

         throw var10;
      }

      var11.close();
      return var4;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private SentryEnvelopeItemHeader deserializeEnvelopeItemHeader(byte[] var1, int var2, int var3) {
      StringReader var4 = new StringReader(new String(var1, var2, var3, UTF_8));

      try {
         var11 = this.serializer.deserialize(var4, SentryEnvelopeItemHeader.class);
      } catch (Throwable var10) {
         try {
            var4.close();
         } catch (Throwable var9) {
            var10.addSuppressed(var9);
            throw var10;
         }

         throw var10;
      }

      var4.close();
      return var11;
   }

   @Override
   public SentryEnvelope read(InputStream param1) throws IOException {
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
      // 000: sipush 1024
      // 003: newarray 8
      // 005: astore 8
      // 007: new java/io/ByteArrayOutputStream
      // 00a: dup
      // 00b: invokespecial java/io/ByteArrayOutputStream.<init> ()V
      // 00e: astore 7
      // 010: bipush -1
      // 011: istore 3
      // 012: bipush 0
      // 013: istore 2
      // 014: aload 1
      // 015: aload 8
      // 017: invokevirtual java/io/InputStream.read ([B)I
      // 01a: istore 6
      // 01c: iload 6
      // 01e: ifle 064
      // 021: bipush 0
      // 022: istore 5
      // 024: iload 3
      // 025: istore 4
      // 027: iload 3
      // 028: bipush -1
      // 029: if_icmpne 04f
      // 02c: iload 3
      // 02d: istore 4
      // 02f: iload 5
      // 031: iload 6
      // 033: if_icmpge 04f
      // 036: aload 8
      // 038: iload 5
      // 03a: baload
      // 03b: bipush 10
      // 03d: if_icmpne 049
      // 040: iload 2
      // 041: iload 5
      // 043: iadd
      // 044: istore 4
      // 046: goto 04f
      // 049: iinc 5 1
      // 04c: goto 024
      // 04f: aload 7
      // 051: aload 8
      // 053: bipush 0
      // 054: iload 6
      // 056: invokevirtual java/io/ByteArrayOutputStream.write ([BII)V
      // 059: iload 2
      // 05a: iload 6
      // 05c: iadd
      // 05d: istore 2
      // 05e: iload 4
      // 060: istore 3
      // 061: goto 014
      // 064: aload 7
      // 066: invokevirtual java/io/ByteArrayOutputStream.toByteArray ()[B
      // 069: astore 8
      // 06b: aload 8
      // 06d: arraylength
      // 06e: ifeq 225
      // 071: iload 3
      // 072: bipush -1
      // 073: if_icmpeq 219
      // 076: aload 0
      // 077: aload 8
      // 079: bipush 0
      // 07a: iload 3
      // 07b: invokespecial io/sentry/EnvelopeReader.deserializeEnvelopeHeader ([BII)Lio/sentry/SentryEnvelopeHeader;
      // 07e: astore 11
      // 080: aload 11
      // 082: ifnull 20d
      // 085: iload 3
      // 086: bipush 1
      // 087: iadd
      // 088: istore 2
      // 089: new java/util/ArrayList
      // 08c: astore 1
      // 08d: aload 1
      // 08e: invokespecial java/util/ArrayList.<init> ()V
      // 091: iload 2
      // 092: istore 3
      // 093: iload 3
      // 094: aload 8
      // 096: arraylength
      // 097: if_icmpge 0ac
      // 09a: aload 8
      // 09c: iload 3
      // 09d: baload
      // 09e: bipush 10
      // 0a0: if_icmpne 0a6
      // 0a3: goto 0ae
      // 0a6: iinc 3 1
      // 0a9: goto 093
      // 0ac: bipush -1
      // 0ad: istore 3
      // 0ae: iload 3
      // 0af: bipush -1
      // 0b0: if_icmpeq 1d5
      // 0b3: aload 0
      // 0b4: aload 8
      // 0b6: iload 2
      // 0b7: iload 3
      // 0b8: iload 2
      // 0b9: isub
      // 0ba: invokespecial io/sentry/EnvelopeReader.deserializeEnvelopeItemHeader ([BII)Lio/sentry/SentryEnvelopeItemHeader;
      // 0bd: astore 12
      // 0bf: aload 12
      // 0c1: ifnull 19d
      // 0c4: aload 12
      // 0c6: invokevirtual io/sentry/SentryEnvelopeItemHeader.getLength ()I
      // 0c9: ifle 19d
      // 0cc: aload 12
      // 0ce: invokevirtual io/sentry/SentryEnvelopeItemHeader.getLength ()I
      // 0d1: iload 3
      // 0d2: iadd
      // 0d3: istore 2
      // 0d4: iload 2
      // 0d5: bipush 1
      // 0d6: iadd
      // 0d7: istore 4
      // 0d9: iload 4
      // 0db: aload 8
      // 0dd: arraylength
      // 0de: if_icmpgt 144
      // 0e1: aload 8
      // 0e3: iload 3
      // 0e4: bipush 1
      // 0e5: iadd
      // 0e6: iload 4
      // 0e8: invokestatic java/util/Arrays.copyOfRange ([BII)[B
      // 0eb: astore 10
      // 0ed: new io/sentry/SentryEnvelopeItem
      // 0f0: astore 9
      // 0f2: aload 9
      // 0f4: aload 12
      // 0f6: aload 10
      // 0f8: invokespecial io/sentry/SentryEnvelopeItem.<init> (Lio/sentry/SentryEnvelopeItemHeader;[B)V
      // 0fb: aload 1
      // 0fc: aload 9
      // 0fe: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 103: pop
      // 104: iload 4
      // 106: aload 8
      // 108: arraylength
      // 109: if_icmpne 10f
      // 10c: goto 126
      // 10f: iload 2
      // 110: bipush 2
      // 111: iadd
      // 112: istore 3
      // 113: iload 3
      // 114: istore 2
      // 115: iload 3
      // 116: aload 8
      // 118: arraylength
      // 119: if_icmpne 091
      // 11c: aload 8
      // 11e: iload 4
      // 120: baload
      // 121: bipush 10
      // 123: if_icmpne 138
      // 126: new io/sentry/SentryEnvelope
      // 129: dup
      // 12a: aload 11
      // 12c: aload 1
      // 12d: invokespecial io/sentry/SentryEnvelope.<init> (Lio/sentry/SentryEnvelopeHeader;Ljava/lang/Iterable;)V
      // 130: astore 1
      // 131: aload 7
      // 133: invokevirtual java/io/ByteArrayOutputStream.close ()V
      // 136: aload 1
      // 137: areturn
      // 138: new java/lang/IllegalArgumentException
      // 13b: astore 1
      // 13c: aload 1
      // 13d: ldc "Envelope has invalid data following an item."
      // 13f: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 142: aload 1
      // 143: athrow
      // 144: new java/lang/IllegalArgumentException
      // 147: astore 9
      // 149: new java/lang/StringBuilder
      // 14c: astore 10
      // 14e: aload 10
      // 150: invokespecial java/lang/StringBuilder.<init> ()V
      // 153: aload 10
      // 155: ldc "Invalid length for item at index '"
      // 157: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 15a: pop
      // 15b: aload 10
      // 15d: aload 1
      // 15e: invokeinterface java/util/List.size ()I 1
      // 163: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 166: pop
      // 167: aload 10
      // 169: ldc "'. Item is '"
      // 16b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 16e: pop
      // 16f: aload 10
      // 171: iload 4
      // 173: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 176: pop
      // 177: aload 10
      // 179: ldc "' bytes. There are '"
      // 17b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 17e: pop
      // 17f: aload 10
      // 181: aload 8
      // 183: arraylength
      // 184: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 187: pop
      // 188: aload 10
      // 18a: ldc "' in the buffer."
      // 18c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 18f: pop
      // 190: aload 9
      // 192: aload 10
      // 194: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 197: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 19a: aload 9
      // 19c: athrow
      // 19d: new java/lang/IllegalArgumentException
      // 1a0: astore 8
      // 1a2: new java/lang/StringBuilder
      // 1a5: astore 9
      // 1a7: aload 9
      // 1a9: invokespecial java/lang/StringBuilder.<init> ()V
      // 1ac: aload 9
      // 1ae: ldc "Item header at index '"
      // 1b0: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1b3: pop
      // 1b4: aload 9
      // 1b6: aload 1
      // 1b7: invokeinterface java/util/List.size ()I 1
      // 1bc: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 1bf: pop
      // 1c0: aload 9
      // 1c2: ldc "' is null or empty."
      // 1c4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1c7: pop
      // 1c8: aload 8
      // 1ca: aload 9
      // 1cc: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 1cf: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 1d2: aload 8
      // 1d4: athrow
      // 1d5: new java/lang/IllegalArgumentException
      // 1d8: astore 8
      // 1da: new java/lang/StringBuilder
      // 1dd: astore 9
      // 1df: aload 9
      // 1e1: invokespecial java/lang/StringBuilder.<init> ()V
      // 1e4: aload 9
      // 1e6: ldc "Invalid envelope. Item at index '"
      // 1e8: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1eb: pop
      // 1ec: aload 9
      // 1ee: aload 1
      // 1ef: invokeinterface java/util/List.size ()I 1
      // 1f4: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 1f7: pop
      // 1f8: aload 9
      // 1fa: ldc "'. has no header delimiter."
      // 1fc: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 1ff: pop
      // 200: aload 8
      // 202: aload 9
      // 204: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 207: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 20a: aload 8
      // 20c: athrow
      // 20d: new java/lang/IllegalArgumentException
      // 210: astore 1
      // 211: aload 1
      // 212: ldc "Envelope header is null."
      // 214: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 217: aload 1
      // 218: athrow
      // 219: new java/lang/IllegalArgumentException
      // 21c: astore 1
      // 21d: aload 1
      // 21e: ldc "Envelope contains no header."
      // 220: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 223: aload 1
      // 224: athrow
      // 225: new java/lang/IllegalArgumentException
      // 228: astore 1
      // 229: aload 1
      // 22a: ldc "Empty stream."
      // 22c: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 22f: aload 1
      // 230: athrow
      // 231: astore 1
      // 232: aload 7
      // 234: invokevirtual java/io/ByteArrayOutputStream.close ()V
      // 237: goto 242
      // 23a: astore 7
      // 23c: aload 1
      // 23d: aload 7
      // 23f: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 242: aload 1
      // 243: athrow
   }
}
