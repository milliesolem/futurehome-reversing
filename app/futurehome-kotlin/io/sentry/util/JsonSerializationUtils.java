package io.sentry.util;

import io.sentry.ILogger;
import io.sentry.ISerializer;
import io.sentry.JsonSerializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class JsonSerializationUtils {
   private static final Charset UTF_8 = Charset.forName("UTF-8");

   public static List<Object> atomicIntegerArrayToList(AtomicIntegerArray var0) {
      int var2 = var0.length();
      ArrayList var3 = new ArrayList(var2);

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(var0.get(var1));
      }

      return var3;
   }

   public static byte[] bytesFrom(ISerializer param0, ILogger param1, JsonSerializable param2) {
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
      // 03: astore 3
      // 04: aload 3
      // 05: invokespecial java/io/ByteArrayOutputStream.<init> ()V
      // 08: new java/io/BufferedWriter
      // 0b: astore 4
      // 0d: new java/io/OutputStreamWriter
      // 10: astore 5
      // 12: aload 5
      // 14: aload 3
      // 15: getstatic io/sentry/util/JsonSerializationUtils.UTF_8 Ljava/nio/charset/Charset;
      // 18: invokespecial java/io/OutputStreamWriter.<init> (Ljava/io/OutputStream;Ljava/nio/charset/Charset;)V
      // 1b: aload 4
      // 1d: aload 5
      // 1f: invokespecial java/io/BufferedWriter.<init> (Ljava/io/Writer;)V
      // 22: aload 0
      // 23: aload 2
      // 24: aload 4
      // 26: invokeinterface io/sentry/ISerializer.serialize (Ljava/lang/Object;Ljava/io/Writer;)V 3
      // 2b: aload 3
      // 2c: invokevirtual java/io/ByteArrayOutputStream.toByteArray ()[B
      // 2f: astore 0
      // 30: aload 4
      // 32: invokevirtual java/io/Writer.close ()V
      // 35: aload 3
      // 36: invokevirtual java/io/ByteArrayOutputStream.close ()V
      // 39: aload 0
      // 3a: areturn
      // 3b: astore 0
      // 3c: aload 4
      // 3e: invokevirtual java/io/Writer.close ()V
      // 41: goto 4a
      // 44: astore 2
      // 45: aload 0
      // 46: aload 2
      // 47: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 4a: aload 0
      // 4b: athrow
      // 4c: astore 0
      // 4d: aload 3
      // 4e: invokevirtual java/io/ByteArrayOutputStream.close ()V
      // 51: goto 5a
      // 54: astore 2
      // 55: aload 0
      // 56: aload 2
      // 57: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 5a: aload 0
      // 5b: athrow
      // 5c: astore 0
      // 5d: aload 1
      // 5e: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 61: ldc "Could not serialize serializable"
      // 63: aload 0
      // 64: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 69: aconst_null
      // 6a: areturn
   }

   public static Map<String, Object> calendarToMap(Calendar var0) {
      HashMap var1 = new HashMap();
      var1.put("year", var0.get(1));
      var1.put("month", var0.get(2));
      var1.put("dayOfMonth", var0.get(5));
      var1.put("hourOfDay", var0.get(11));
      var1.put("minute", var0.get(12));
      var1.put("second", var0.get(13));
      return var1;
   }
}
