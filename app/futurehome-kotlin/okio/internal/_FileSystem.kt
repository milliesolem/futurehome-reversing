package okio.internal

import java.io.FileNotFoundException
import java.io.IOException
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function2
import okio.FileMetadata
import okio.FileSystem
import okio.Path

internal suspend fun SequenceScope<Path>.collectRecursively(
   fileSystem: FileSystem,
   stack: ArrayDeque<Path>,
   path: Path,
   followSymlinks: Boolean,
   postorder: Boolean
) {
   // $VF: Couldn't be decompiled
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
   //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
   //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
   //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
   //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
   //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
   //
   // Bytecode:
   // 000: aload 3
   // 001: astore 10
   // 003: iload 5
   // 005: istore 9
   // 007: aload 6
   // 009: instanceof okio/internal/_FileSystem$collectRecursively$1
   // 00c: ifeq 030
   // 00f: aload 6
   // 011: checkcast okio/internal/_FileSystem$collectRecursively$1
   // 014: astore 3
   // 015: aload 3
   // 016: getfield okio/internal/_FileSystem$collectRecursively$1.label I
   // 019: ldc -2147483648
   // 01b: iand
   // 01c: ifeq 030
   // 01f: aload 3
   // 020: aload 3
   // 021: getfield okio/internal/_FileSystem$collectRecursively$1.label I
   // 024: ldc -2147483648
   // 026: iadd
   // 027: putfield okio/internal/_FileSystem$collectRecursively$1.label I
   // 02a: aload 3
   // 02b: astore 6
   // 02d: goto 03b
   // 030: new okio/internal/_FileSystem$collectRecursively$1
   // 033: dup
   // 034: aload 6
   // 036: invokespecial okio/internal/_FileSystem$collectRecursively$1.<init> (Lkotlin/coroutines/Continuation;)V
   // 039: astore 6
   // 03b: aload 6
   // 03d: getfield okio/internal/_FileSystem$collectRecursively$1.result Ljava/lang/Object;
   // 040: astore 12
   // 042: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
   // 045: astore 14
   // 047: aload 6
   // 049: getfield okio/internal/_FileSystem$collectRecursively$1.label I
   // 04c: istore 8
   // 04e: bipush 0
   // 04f: istore 7
   // 051: iload 8
   // 053: ifeq 101
   // 056: iload 8
   // 058: bipush 1
   // 059: if_icmpeq 0c7
   // 05c: iload 8
   // 05e: bipush 2
   // 05f: if_icmpeq 07a
   // 062: iload 8
   // 064: bipush 3
   // 065: if_icmpne 070
   // 068: aload 12
   // 06a: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
   // 06d: goto 2c5
   // 070: new java/lang/IllegalStateException
   // 073: dup
   // 074: ldc "call to 'resume' before 'invoke' with coroutine"
   // 076: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
   // 079: athrow
   // 07a: aload 6
   // 07c: getfield okio/internal/_FileSystem$collectRecursively$1.Z$1 Z
   // 07f: istore 4
   // 081: aload 6
   // 083: getfield okio/internal/_FileSystem$collectRecursively$1.Z$0 Z
   // 086: istore 5
   // 088: aload 6
   // 08a: getfield okio/internal/_FileSystem$collectRecursively$1.L$4 Ljava/lang/Object;
   // 08d: checkcast java/util/Iterator
   // 090: astore 11
   // 092: aload 6
   // 094: getfield okio/internal/_FileSystem$collectRecursively$1.L$3 Ljava/lang/Object;
   // 097: checkcast okio/Path
   // 09a: astore 2
   // 09b: aload 6
   // 09d: getfield okio/internal/_FileSystem$collectRecursively$1.L$2 Ljava/lang/Object;
   // 0a0: checkcast kotlin/collections/ArrayDeque
   // 0a3: astore 3
   // 0a4: aload 6
   // 0a6: getfield okio/internal/_FileSystem$collectRecursively$1.L$1 Ljava/lang/Object;
   // 0a9: checkcast okio/FileSystem
   // 0ac: astore 10
   // 0ae: aload 6
   // 0b0: getfield okio/internal/_FileSystem$collectRecursively$1.L$0 Ljava/lang/Object;
   // 0b3: checkcast kotlin/sequences/SequenceScope
   // 0b6: astore 1
   // 0b7: aload 3
   // 0b8: astore 0
   // 0b9: aload 12
   // 0bb: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
   // 0be: goto 1ea
   // 0c1: astore 1
   // 0c2: aload 0
   // 0c3: astore 2
   // 0c4: goto 27a
   // 0c7: aload 6
   // 0c9: getfield okio/internal/_FileSystem$collectRecursively$1.Z$1 Z
   // 0cc: istore 4
   // 0ce: aload 6
   // 0d0: getfield okio/internal/_FileSystem$collectRecursively$1.Z$0 Z
   // 0d3: istore 5
   // 0d5: aload 6
   // 0d7: getfield okio/internal/_FileSystem$collectRecursively$1.L$3 Ljava/lang/Object;
   // 0da: checkcast okio/Path
   // 0dd: astore 0
   // 0de: aload 6
   // 0e0: getfield okio/internal/_FileSystem$collectRecursively$1.L$2 Ljava/lang/Object;
   // 0e3: checkcast kotlin/collections/ArrayDeque
   // 0e6: astore 2
   // 0e7: aload 6
   // 0e9: getfield okio/internal/_FileSystem$collectRecursively$1.L$1 Ljava/lang/Object;
   // 0ec: checkcast okio/FileSystem
   // 0ef: astore 3
   // 0f0: aload 6
   // 0f2: getfield okio/internal/_FileSystem$collectRecursively$1.L$0 Ljava/lang/Object;
   // 0f5: checkcast kotlin/sequences/SequenceScope
   // 0f8: astore 1
   // 0f9: aload 12
   // 0fb: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
   // 0fe: goto 157
   // 101: aload 12
   // 103: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
   // 106: iload 9
   // 108: ifne 148
   // 10b: aload 6
   // 10d: aload 0
   // 10e: putfield okio/internal/_FileSystem$collectRecursively$1.L$0 Ljava/lang/Object;
   // 111: aload 6
   // 113: aload 1
   // 114: putfield okio/internal/_FileSystem$collectRecursively$1.L$1 Ljava/lang/Object;
   // 117: aload 6
   // 119: aload 2
   // 11a: putfield okio/internal/_FileSystem$collectRecursively$1.L$2 Ljava/lang/Object;
   // 11d: aload 6
   // 11f: aload 10
   // 121: putfield okio/internal/_FileSystem$collectRecursively$1.L$3 Ljava/lang/Object;
   // 124: aload 6
   // 126: iload 4
   // 128: putfield okio/internal/_FileSystem$collectRecursively$1.Z$0 Z
   // 12b: aload 6
   // 12d: iload 9
   // 12f: putfield okio/internal/_FileSystem$collectRecursively$1.Z$1 Z
   // 132: aload 6
   // 134: bipush 1
   // 135: putfield okio/internal/_FileSystem$collectRecursively$1.label I
   // 138: aload 0
   // 139: aload 10
   // 13b: aload 6
   // 13d: invokevirtual kotlin/sequences/SequenceScope.yield (Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
   // 140: aload 14
   // 142: if_acmpne 148
   // 145: aload 14
   // 147: areturn
   // 148: aload 1
   // 149: astore 3
   // 14a: iload 4
   // 14c: istore 5
   // 14e: aload 0
   // 14f: astore 1
   // 150: iload 9
   // 152: istore 4
   // 154: aload 10
   // 156: astore 0
   // 157: aload 3
   // 158: aload 0
   // 159: invokevirtual okio/FileSystem.listOrNull (Lokio/Path;)Ljava/util/List;
   // 15c: astore 11
   // 15e: aload 11
   // 160: astore 10
   // 162: aload 11
   // 164: ifnonnull 16c
   // 167: invokestatic kotlin/collections/CollectionsKt.emptyList ()Ljava/util/List;
   // 16a: astore 10
   // 16c: aload 0
   // 16d: astore 11
   // 16f: iload 4
   // 171: istore 9
   // 173: aload 1
   // 174: astore 13
   // 176: aload 10
   // 178: checkcast java/util/Collection
   // 17b: invokeinterface java/util/Collection.isEmpty ()Z 1
   // 180: ifne 28b
   // 183: aload 0
   // 184: astore 12
   // 186: iload 5
   // 188: ifeq 1b3
   // 18b: aload 2
   // 18c: aload 12
   // 18e: invokevirtual kotlin/collections/ArrayDeque.contains (Ljava/lang/Object;)Z
   // 191: ifne 197
   // 194: goto 1b3
   // 197: new java/lang/StringBuilder
   // 19a: dup
   // 19b: ldc "symlink cycle at "
   // 19d: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
   // 1a0: astore 1
   // 1a1: aload 1
   // 1a2: aload 0
   // 1a3: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
   // 1a6: pop
   // 1a7: new java/io/IOException
   // 1aa: dup
   // 1ab: aload 1
   // 1ac: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
   // 1af: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
   // 1b2: athrow
   // 1b3: aload 3
   // 1b4: aload 12
   // 1b6: invokestatic okio/internal/_FileSystem.symlinkTarget (Lokio/FileSystem;Lokio/Path;)Lokio/Path;
   // 1b9: astore 11
   // 1bb: aload 11
   // 1bd: ifnonnull 281
   // 1c0: iload 5
   // 1c2: ifne 1d4
   // 1c5: aload 0
   // 1c6: astore 11
   // 1c8: iload 4
   // 1ca: istore 9
   // 1cc: aload 1
   // 1cd: astore 13
   // 1cf: iload 7
   // 1d1: ifne 28b
   // 1d4: aload 2
   // 1d5: aload 12
   // 1d7: invokevirtual kotlin/collections/ArrayDeque.addLast (Ljava/lang/Object;)V
   // 1da: aload 10
   // 1dc: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
   // 1e1: astore 11
   // 1e3: aload 3
   // 1e4: astore 10
   // 1e6: aload 2
   // 1e7: astore 3
   // 1e8: aload 0
   // 1e9: astore 2
   // 1ea: aload 3
   // 1eb: astore 0
   // 1ec: aload 11
   // 1ee: invokeinterface java/util/Iterator.hasNext ()Z 1
   // 1f3: ifeq 265
   // 1f6: aload 3
   // 1f7: astore 0
   // 1f8: aload 11
   // 1fa: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
   // 1ff: checkcast okio/Path
   // 202: astore 12
   // 204: aload 3
   // 205: astore 0
   // 206: aload 6
   // 208: aload 1
   // 209: putfield okio/internal/_FileSystem$collectRecursively$1.L$0 Ljava/lang/Object;
   // 20c: aload 3
   // 20d: astore 0
   // 20e: aload 6
   // 210: aload 10
   // 212: putfield okio/internal/_FileSystem$collectRecursively$1.L$1 Ljava/lang/Object;
   // 215: aload 3
   // 216: astore 0
   // 217: aload 6
   // 219: aload 3
   // 21a: putfield okio/internal/_FileSystem$collectRecursively$1.L$2 Ljava/lang/Object;
   // 21d: aload 3
   // 21e: astore 0
   // 21f: aload 6
   // 221: aload 2
   // 222: putfield okio/internal/_FileSystem$collectRecursively$1.L$3 Ljava/lang/Object;
   // 225: aload 3
   // 226: astore 0
   // 227: aload 6
   // 229: aload 11
   // 22b: putfield okio/internal/_FileSystem$collectRecursively$1.L$4 Ljava/lang/Object;
   // 22e: aload 3
   // 22f: astore 0
   // 230: aload 6
   // 232: iload 5
   // 234: putfield okio/internal/_FileSystem$collectRecursively$1.Z$0 Z
   // 237: aload 3
   // 238: astore 0
   // 239: aload 6
   // 23b: iload 4
   // 23d: putfield okio/internal/_FileSystem$collectRecursively$1.Z$1 Z
   // 240: aload 3
   // 241: astore 0
   // 242: aload 6
   // 244: bipush 2
   // 245: putfield okio/internal/_FileSystem$collectRecursively$1.label I
   // 248: aload 3
   // 249: astore 0
   // 24a: aload 1
   // 24b: aload 10
   // 24d: aload 3
   // 24e: aload 12
   // 250: iload 5
   // 252: iload 4
   // 254: aload 6
   // 256: invokestatic okio/internal/_FileSystem.collectRecursively (Lkotlin/sequences/SequenceScope;Lokio/FileSystem;Lkotlin/collections/ArrayDeque;Lokio/Path;ZZLkotlin/coroutines/Continuation;)Ljava/lang/Object;
   // 259: astore 12
   // 25b: aload 12
   // 25d: aload 14
   // 25f: if_acmpne 1ea
   // 262: aload 14
   // 264: areturn
   // 265: aload 3
   // 266: invokevirtual kotlin/collections/ArrayDeque.removeLast ()Ljava/lang/Object;
   // 269: pop
   // 26a: aload 2
   // 26b: astore 11
   // 26d: iload 4
   // 26f: istore 9
   // 271: aload 1
   // 272: astore 13
   // 274: goto 28b
   // 277: astore 0
   // 278: aload 0
   // 279: astore 1
   // 27a: aload 2
   // 27b: invokevirtual kotlin/collections/ArrayDeque.removeLast ()Ljava/lang/Object;
   // 27e: pop
   // 27f: aload 1
   // 280: athrow
   // 281: iinc 7 1
   // 284: aload 11
   // 286: astore 12
   // 288: goto 186
   // 28b: iload 9
   // 28d: ifeq 2c9
   // 290: aload 6
   // 292: aconst_null
   // 293: putfield okio/internal/_FileSystem$collectRecursively$1.L$0 Ljava/lang/Object;
   // 296: aload 6
   // 298: aconst_null
   // 299: putfield okio/internal/_FileSystem$collectRecursively$1.L$1 Ljava/lang/Object;
   // 29c: aload 6
   // 29e: aconst_null
   // 29f: putfield okio/internal/_FileSystem$collectRecursively$1.L$2 Ljava/lang/Object;
   // 2a2: aload 6
   // 2a4: aconst_null
   // 2a5: putfield okio/internal/_FileSystem$collectRecursively$1.L$3 Ljava/lang/Object;
   // 2a8: aload 6
   // 2aa: aconst_null
   // 2ab: putfield okio/internal/_FileSystem$collectRecursively$1.L$4 Ljava/lang/Object;
   // 2ae: aload 6
   // 2b0: bipush 3
   // 2b1: putfield okio/internal/_FileSystem$collectRecursively$1.label I
   // 2b4: aload 13
   // 2b6: aload 11
   // 2b8: aload 6
   // 2ba: invokevirtual kotlin/sequences/SequenceScope.yield (Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
   // 2bd: aload 14
   // 2bf: if_acmpne 2c5
   // 2c2: aload 14
   // 2c4: areturn
   // 2c5: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
   // 2c8: areturn
   // 2c9: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
   // 2cc: areturn
}

@Throws(java/io/IOException::class)
internal fun FileSystem.commonCopy(source: Path, target: Path) {
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
   //
   // Bytecode:
   // 00: aload 0
   // 01: ldc "<this>"
   // 03: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
   // 06: aload 1
   // 07: ldc "source"
   // 09: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
   // 0c: aload 2
   // 0d: ldc "target"
   // 0f: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
   // 12: aload 0
   // 13: aload 1
   // 14: invokevirtual okio/FileSystem.source (Lokio/Path;)Lokio/Source;
   // 17: checkcast java/io/Closeable
   // 1a: astore 4
   // 1c: aconst_null
   // 1d: astore 3
   // 1e: aload 4
   // 20: checkcast okio/Source
   // 23: astore 1
   // 24: aload 0
   // 25: aload 2
   // 26: invokevirtual okio/FileSystem.sink (Lokio/Path;)Lokio/Sink;
   // 29: invokestatic okio/Okio.buffer (Lokio/Sink;)Lokio/BufferedSink;
   // 2c: checkcast java/io/Closeable
   // 2f: astore 2
   // 30: aload 2
   // 31: checkcast okio/BufferedSink
   // 34: aload 1
   // 35: invokeinterface okio/BufferedSink.writeAll (Lokio/Source;)J 2
   // 3a: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
   // 3d: astore 1
   // 3e: aload 2
   // 3f: ifnull 4f
   // 42: aload 2
   // 43: invokeinterface java/io/Closeable.close ()V 1
   // 48: goto 4f
   // 4b: astore 0
   // 4c: goto 6a
   // 4f: aconst_null
   // 50: astore 0
   // 51: goto 6a
   // 54: astore 0
   // 55: aload 2
   // 56: ifnull 68
   // 59: aload 2
   // 5a: invokeinterface java/io/Closeable.close ()V 1
   // 5f: goto 68
   // 62: astore 1
   // 63: aload 0
   // 64: aload 1
   // 65: invokestatic kotlin/ExceptionsKt.addSuppressed (Ljava/lang/Throwable;Ljava/lang/Throwable;)V
   // 68: aconst_null
   // 69: astore 1
   // 6a: aload 0
   // 6b: ifnonnull 9a
   // 6e: aload 1
   // 6f: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
   // 72: aload 1
   // 73: checkcast java/lang/Number
   // 76: invokevirtual java/lang/Number.longValue ()J
   // 79: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
   // 7c: astore 2
   // 7d: aload 3
   // 7e: astore 0
   // 7f: aload 2
   // 80: astore 1
   // 81: aload 4
   // 83: ifnull b4
   // 86: aload 4
   // 88: invokeinterface java/io/Closeable.close ()V 1
   // 8d: aload 3
   // 8e: astore 0
   // 8f: aload 2
   // 90: astore 1
   // 91: goto b4
   // 94: astore 0
   // 95: aload 2
   // 96: astore 1
   // 97: goto b4
   // 9a: aload 0
   // 9b: athrow
   // 9c: astore 0
   // 9d: aload 4
   // 9f: ifnull b2
   // a2: aload 4
   // a4: invokeinterface java/io/Closeable.close ()V 1
   // a9: goto b2
   // ac: astore 1
   // ad: aload 0
   // ae: aload 1
   // af: invokestatic kotlin/ExceptionsKt.addSuppressed (Ljava/lang/Throwable;Ljava/lang/Throwable;)V
   // b2: aconst_null
   // b3: astore 1
   // b4: aload 0
   // b5: ifnonnull bd
   // b8: aload 1
   // b9: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
   // bc: return
   // bd: aload 0
   // be: athrow
}

@Throws(java/io/IOException::class)
internal fun FileSystem.commonCreateDirectories(dir: Path, mustCreate: Boolean) {
   val var4: ArrayDeque = new ArrayDeque();

   for (Path var3 = var1; var3 != null && !var0.exists(var3); var3 = var3.parent()) {
      var4.addFirst(var3);
   }

   if (var2 && var4.isEmpty()) {
      val var5: StringBuilder = new StringBuilder();
      var5.append(var1);
      var5.append(" already exist.");
      throw new IOException(var5.toString());
   } else {
      val var6: java.util.Iterator = var4.iterator();

      while (var6.hasNext()) {
         var0.createDirectory(var6.next() as Path);
      }
   }
}

@Throws(java/io/IOException::class)
internal fun FileSystem.commonDeleteRecursively(fileOrDirectory: Path, mustExist: Boolean) {
   val var5: java.util.Iterator = SequencesKt.sequence((new Function2<SequenceScope<? super Path>, Continuation<? super Unit>, Object>(var0, var1, null) {
      final Path $fileOrDirectory;
      final FileSystem $this_commonDeleteRecursively;
      private Object L$0;
      int label;

      {
         super(2, var3x);
         this.$this_commonDeleteRecursively = var1;
         this.$fileOrDirectory = var2x;
      }

      @Override
      public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
         val var3: Function2 = new <anonymous constructor>(this.$this_commonDeleteRecursively, this.$fileOrDirectory, var2);
         var3.L$0 = var1;
         return var3 as Continuation<Unit>;
      }

      public final Object invoke(SequenceScope<? super Path> var1, Continuation<? super Unit> var2x) {
         return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
      }

      @Override
      public final Object invokeSuspend(Object var1) {
         val var3x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
         if (this.label != 0) {
            if (this.label != 1) {
               throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            ResultKt.throwOnFailure(var1);
         } else {
            ResultKt.throwOnFailure(var1);
            val var7: SequenceScope = this.L$0 as SequenceScope;
            var1 = this.$this_commonDeleteRecursively;
            val var4: ArrayDeque = new ArrayDeque();
            val var6: Path = this.$fileOrDirectory;
            val var5: Continuation = this;
            this.label = 1;
            if (_FileSystem.collectRecursively(var7, var1, var4, var6, false, true, var5) === var3x) {
               return var3x;
            }
         }

         return Unit.INSTANCE;
      }
   }) as Function2).iterator();

   while (var5.hasNext()) {
      val var4: Path = var5.next() as Path;
      val var3: Boolean;
      if (var2 && !var5.hasNext()) {
         var3 = true;
      } else {
         var3 = false;
      }

      var0.delete(var4, var3);
   }
}

@Throws(java/io/IOException::class)
internal fun FileSystem.commonExists(path: Path): Boolean {
   val var2: Boolean;
   if (var0.metadataOrNull(var1) != null) {
      var2 = true;
   } else {
      var2 = false;
   }

   return var2;
}

@Throws(java/io/IOException::class)
internal fun FileSystem.commonListRecursively(dir: Path, followSymlinks: Boolean): Sequence<Path> {
   return SequencesKt.sequence((new Function2<SequenceScope<? super Path>, Continuation<? super Unit>, Object>(var1, var0, var2, null) {
      final Path $dir;
      final boolean $followSymlinks;
      final FileSystem $this_commonListRecursively;
      private Object L$0;
      Object L$1;
      Object L$2;
      int label;

      {
         super(2, var4);
         this.$dir = var1;
         this.$this_commonListRecursively = var2x;
         this.$followSymlinks = var3x;
      }

      @Override
      public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
         val var3: Function2 = new <anonymous constructor>(this.$dir, this.$this_commonListRecursively, this.$followSymlinks, var2);
         var3.L$0 = var1;
         return var3 as Continuation<Unit>;
      }

      public final Object invoke(SequenceScope<? super Path> var1, Continuation<? super Unit> var2x) {
         return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
      }

      @Override
      public final Object invokeSuspend(Object var1) {
         val var7: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
         val var4: ArrayDeque;
         val var5: SequenceScope;
         if (this.label != 0) {
            if (this.label != 1) {
               throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            val var6: java.util.Iterator = this.L$2 as java.util.Iterator;
            var4 = this.L$1 as ArrayDeque;
            var5 = this.L$0 as SequenceScope;
            ResultKt.throwOnFailure(var1);
            var1 = var6;
         } else {
            ResultKt.throwOnFailure(var1);
            var5 = this.L$0 as SequenceScope;
            var4 = new ArrayDeque();
            var4.addLast(this.$dir);
            var1 = this.$this_commonListRecursively.list(this.$dir).iterator();
         }

         while (var1.hasNext()) {
            val var11: Path = var1.next() as Path;
            val var9: FileSystem = this.$this_commonListRecursively;
            val var3x: Boolean = this.$followSymlinks;
            val var8: Continuation = this;
            this.L$0 = var5;
            this.L$1 = var4;
            this.L$2 = var1;
            this.label = 1;
            if (_FileSystem.collectRecursively(var5, var9, var4, var11, var3x, false, var8) === var7) {
               return var7;
            }
         }

         return Unit.INSTANCE;
      }
   }) as (SequenceScope<? super Path>?, Continuation<? super Unit>?) -> Any);
}

@Throws(java/io/IOException::class)
internal fun FileSystem.commonMetadata(path: Path): FileMetadata {
   val var2: FileMetadata = var0.metadataOrNull(var1);
   if (var2 != null) {
      return var2;
   } else {
      val var3: StringBuilder = new StringBuilder("no such file: ");
      var3.append(var1);
      throw new FileNotFoundException(var3.toString());
   }
}

@Throws(java/io/IOException::class)
internal fun FileSystem.symlinkTarget(path: Path): Path? {
   val var2: Path = var0.metadata(var1).getSymlinkTarget();
   if (var2 == null) {
      return null;
   } else {
      var1 = var1.parent();
      return var1.resolve(var2);
   }
}
