package io.sentry.util;

import java.io.File;
import java.io.IOException;

public final class FileUtils {
   public static boolean deleteRecursively(File var0) {
      if (var0 != null && var0.exists()) {
         if (var0.isFile()) {
            return var0.delete();
         } else {
            File[] var3 = var0.listFiles();
            if (var3 == null) {
               return true;
            } else {
               int var2 = var3.length;

               for (int var1 = 0; var1 < var2; var1++) {
                  if (!deleteRecursively(var3[var1])) {
                     return false;
                  }
               }

               return var0.delete();
            }
         }
      } else {
         return true;
      }
   }

   public static byte[] readBytesFromFile(String param0, long param1) throws IOException, SecurityException {
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
      // 000: new java/io/File
      // 003: dup
      // 004: aload 0
      // 005: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 008: astore 4
      // 00a: aload 4
      // 00c: invokevirtual java/io/File.exists ()Z
      // 00f: ifeq 112
      // 012: aload 4
      // 014: invokevirtual java/io/File.isFile ()Z
      // 017: ifeq 0fd
      // 01a: aload 4
      // 01c: invokevirtual java/io/File.canRead ()Z
      // 01f: ifeq 0e8
      // 022: aload 4
      // 024: invokevirtual java/io/File.length ()J
      // 027: lload 1
      // 028: lcmp
      // 029: ifgt 0c1
      // 02c: new java/io/FileInputStream
      // 02f: dup
      // 030: aload 0
      // 031: invokespecial java/io/FileInputStream.<init> (Ljava/lang/String;)V
      // 034: astore 0
      // 035: new java/io/BufferedInputStream
      // 038: astore 4
      // 03a: aload 4
      // 03c: aload 0
      // 03d: invokespecial java/io/BufferedInputStream.<init> (Ljava/io/InputStream;)V
      // 040: new java/io/ByteArrayOutputStream
      // 043: astore 5
      // 045: aload 5
      // 047: invokespecial java/io/ByteArrayOutputStream.<init> ()V
      // 04a: sipush 1024
      // 04d: newarray 8
      // 04f: astore 6
      // 051: aload 4
      // 053: aload 6
      // 055: invokevirtual java/io/BufferedInputStream.read ([B)I
      // 058: istore 3
      // 059: iload 3
      // 05a: bipush -1
      // 05b: if_icmpeq 06a
      // 05e: aload 5
      // 060: aload 6
      // 062: bipush 0
      // 063: iload 3
      // 064: invokevirtual java/io/ByteArrayOutputStream.write ([BII)V
      // 067: goto 051
      // 06a: aload 5
      // 06c: invokevirtual java/io/ByteArrayOutputStream.toByteArray ()[B
      // 06f: astore 6
      // 071: aload 5
      // 073: invokevirtual java/io/ByteArrayOutputStream.close ()V
      // 076: aload 4
      // 078: invokevirtual java/io/BufferedInputStream.close ()V
      // 07b: aload 0
      // 07c: invokevirtual java/io/FileInputStream.close ()V
      // 07f: aload 6
      // 081: areturn
      // 082: astore 6
      // 084: aload 5
      // 086: invokevirtual java/io/ByteArrayOutputStream.close ()V
      // 089: goto 095
      // 08c: astore 5
      // 08e: aload 6
      // 090: aload 5
      // 092: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 095: aload 6
      // 097: athrow
      // 098: astore 5
      // 09a: aload 4
      // 09c: invokevirtual java/io/BufferedInputStream.close ()V
      // 09f: goto 0ab
      // 0a2: astore 4
      // 0a4: aload 5
      // 0a6: aload 4
      // 0a8: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 0ab: aload 5
      // 0ad: athrow
      // 0ae: astore 4
      // 0b0: aload 0
      // 0b1: invokevirtual java/io/FileInputStream.close ()V
      // 0b4: goto 0be
      // 0b7: astore 0
      // 0b8: aload 4
      // 0ba: aload 0
      // 0bb: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 0be: aload 4
      // 0c0: athrow
      // 0c1: new java/io/IOException
      // 0c4: dup
      // 0c5: ldc "Reading file failed, because size located at '%s' with %d bytes is bigger than the maximum allowed size of %d bytes."
      // 0c7: bipush 3
      // 0c8: anewarray 4
      // 0cb: dup
      // 0cc: bipush 0
      // 0cd: aload 0
      // 0ce: aastore
      // 0cf: dup
      // 0d0: bipush 1
      // 0d1: aload 4
      // 0d3: invokevirtual java/io/File.length ()J
      // 0d6: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 0d9: aastore
      // 0da: dup
      // 0db: bipush 2
      // 0dc: lload 1
      // 0dd: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 0e0: aastore
      // 0e1: invokestatic java/lang/String.format (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 0e4: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 0e7: athrow
      // 0e8: new java/io/IOException
      // 0eb: dup
      // 0ec: ldc "Reading the item %s failed, because can't read the file."
      // 0ee: bipush 1
      // 0ef: anewarray 4
      // 0f2: dup
      // 0f3: bipush 0
      // 0f4: aload 0
      // 0f5: aastore
      // 0f6: invokestatic java/lang/String.format (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 0f9: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 0fc: athrow
      // 0fd: new java/io/IOException
      // 100: dup
      // 101: ldc "Reading path %s failed, because it's not a file."
      // 103: bipush 1
      // 104: anewarray 4
      // 107: dup
      // 108: bipush 0
      // 109: aload 0
      // 10a: aastore
      // 10b: invokestatic java/lang/String.format (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 10e: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 111: athrow
      // 112: new java/io/IOException
      // 115: dup
      // 116: ldc "File '%s' doesn't exists"
      // 118: bipush 1
      // 119: anewarray 4
      // 11c: dup
      // 11d: bipush 0
      // 11e: aload 4
      // 120: invokevirtual java/io/File.getName ()Ljava/lang/String;
      // 123: aastore
      // 124: invokestatic java/lang/String.format (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      // 127: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // 12a: athrow
   }

   public static String readText(File param0) throws IOException {
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
      // 01: ifnull 75
      // 04: aload 0
      // 05: invokevirtual java/io/File.exists ()Z
      // 08: ifeq 75
      // 0b: aload 0
      // 0c: invokevirtual java/io/File.isFile ()Z
      // 0f: ifeq 75
      // 12: aload 0
      // 13: invokevirtual java/io/File.canRead ()Z
      // 16: ifne 1c
      // 19: goto 75
      // 1c: new java/lang/StringBuilder
      // 1f: dup
      // 20: invokespecial java/lang/StringBuilder.<init> ()V
      // 23: astore 1
      // 24: new java/io/BufferedReader
      // 27: dup
      // 28: new java/io/FileReader
      // 2b: dup
      // 2c: aload 0
      // 2d: invokespecial java/io/FileReader.<init> (Ljava/io/File;)V
      // 30: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 33: astore 0
      // 34: aload 0
      // 35: invokevirtual java/io/BufferedReader.readLine ()Ljava/lang/String;
      // 38: astore 2
      // 39: aload 2
      // 3a: ifnull 43
      // 3d: aload 1
      // 3e: aload 2
      // 3f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 42: pop
      // 43: aload 0
      // 44: invokevirtual java/io/BufferedReader.readLine ()Ljava/lang/String;
      // 47: astore 2
      // 48: aload 2
      // 49: ifnull 5c
      // 4c: aload 1
      // 4d: ldc "\n"
      // 4f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 52: pop
      // 53: aload 1
      // 54: aload 2
      // 55: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 58: pop
      // 59: goto 43
      // 5c: aload 0
      // 5d: invokevirtual java/io/BufferedReader.close ()V
      // 60: aload 1
      // 61: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 64: areturn
      // 65: astore 1
      // 66: aload 0
      // 67: invokevirtual java/io/BufferedReader.close ()V
      // 6a: goto 73
      // 6d: astore 0
      // 6e: aload 1
      // 6f: aload 0
      // 70: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 73: aload 1
      // 74: athrow
      // 75: aconst_null
      // 76: areturn
   }
}
