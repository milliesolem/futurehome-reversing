package okio.internal

import java.io.IOException
import java.util.Comparator
import java.util.GregorianCalendar
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Ref
import okio.BufferedSource
import okio.FileMetadata
import okio.FileSystem
import okio.Path
import okio.ZipFileSystem

private const val BIT_FLAG_ENCRYPTED: Int = 1
private const val BIT_FLAG_UNSUPPORTED_MASK: Int = 1
private const val CENTRAL_FILE_HEADER_SIGNATURE: Int = 33639248
internal const val COMPRESSION_METHOD_DEFLATED: Int = 8
internal const val COMPRESSION_METHOD_STORED: Int = 0
private const val END_OF_CENTRAL_DIRECTORY_SIGNATURE: Int = 101010256
private const val HEADER_ID_EXTENDED_TIMESTAMP: Int = 21589
private const val HEADER_ID_ZIP64_EXTENDED_INFO: Int = 1
private const val LOCAL_FILE_HEADER_SIGNATURE: Int = 67324752
private const val MAX_ZIP_ENTRY_AND_ARCHIVE_SIZE: Long = 4294967295L
private const val ZIP64_EOCD_RECORD_SIGNATURE: Int = 101075792
private const val ZIP64_LOCATOR_SIGNATURE: Int = 117853008

private final val hex: String
   private final get() {
      val var1: StringBuilder = new StringBuilder("0x");
      val var2: java.lang.String = Integer.toString(var0, CharsKt.checkRadix(16));
      var1.append(var2);
      return var1.toString();
   }


private fun buildIndex(entries: List<ZipEntry>): Map<Path, ZipEntry> {
   val var1: Path = Path.Companion.get$default(Path.Companion, "/", false, 1, null);
   val var2: java.util.Map = MapsKt.mutableMapOf(new Pair[]{TuplesKt.to(var1, new ZipEntry(var1, true, null, 0L, 0L, 0L, 0, null, 0L, 508, null))});

   for (ZipEntry var5 : CollectionsKt.sortedWith(var0, new Comparator() {
      @Override
      public final int compare(T var1, T var2) {
         return ComparisonsKt.compareValues((var1 as ZipEntry).getCanonicalPath(), (var2 as ZipEntry).getCanonicalPath());
      }
   })) {
      if (var2.put(var5.getCanonicalPath(), var5) == null) {
         while (true) {
            val var4: Path = var5.getCanonicalPath().parent();
            if (var4 == null) {
               break;
            }

            val var6: ZipEntry = var2.get(var4) as ZipEntry;
            if (var6 != null) {
               var6.getChildren().add(var5.getCanonicalPath());
               break;
            }

            val var7: ZipEntry = new ZipEntry(var4, true, null, 0L, 0L, 0L, 0, null, 0L, 508, null);
            var2.put(var4, var7);
            var7.getChildren().add(var5.getCanonicalPath());
            var5 = var7;
         }
      }
   }

   return var2;
}

private fun dosDateTimeToEpochMillis(date: Int, time: Int): Long? {
   if (var1 == -1) {
      return null;
   } else {
      val var2: GregorianCalendar = new GregorianCalendar();
      var2.set(14, 0);
      var2.set((var0 shr 9 and 127) + 1980, (var0 shr 5 and 15) - 1, var0 and 31, var1 shr 11 and 31, var1 shr 5 and 63, (var1 and 31) shl 1);
      return var2.getTime().getTime();
   }
}

@Throws(java/io/IOException::class)
internal fun openZip(zipPath: Path, fileSystem: FileSystem, predicate: (ZipEntry) -> Boolean = <unrepresentable>.INSTANCE as Function1): ZipFileSystem {
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
   // 000: aload 0
   // 001: ldc "zipPath"
   // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
   // 006: aload 1
   // 007: ldc "fileSystem"
   // 009: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
   // 00c: aload 2
   // 00d: ldc "predicate"
   // 00f: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
   // 012: aload 1
   // 013: aload 0
   // 014: invokevirtual okio/FileSystem.openReadOnly (Lokio/Path;)Lokio/FileHandle;
   // 017: checkcast java/io/Closeable
   // 01a: astore 12
   // 01c: aload 12
   // 01e: checkcast okio/FileHandle
   // 021: astore 15
   // 023: aload 15
   // 025: invokevirtual okio/FileHandle.size ()J
   // 028: bipush 22
   // 02a: i2l
   // 02b: lsub
   // 02c: lstore 4
   // 02e: lconst_0
   // 02f: lstore 6
   // 031: lload 4
   // 033: lconst_0
   // 034: lcmp
   // 035: iflt 25d
   // 038: lload 4
   // 03a: ldc2_w 65536
   // 03d: lsub
   // 03e: lconst_0
   // 03f: invokestatic java/lang/Math.max (JJ)J
   // 042: lstore 8
   // 044: aload 15
   // 046: lload 4
   // 048: invokevirtual okio/FileHandle.source (J)Lokio/Source;
   // 04b: invokestatic okio/Okio.buffer (Lokio/Source;)Lokio/BufferedSource;
   // 04e: astore 10
   // 050: aload 10
   // 052: invokeinterface okio/BufferedSource.readIntLe ()I 1
   // 057: ldc 101010256
   // 059: if_icmpne 22e
   // 05c: aload 10
   // 05e: invokestatic okio/internal/ZipFilesKt.readEocdRecord (Lokio/BufferedSource;)Lokio/internal/EocdRecord;
   // 061: astore 11
   // 063: aload 10
   // 065: aload 11
   // 067: invokevirtual okio/internal/EocdRecord.getCommentByteCount ()I
   // 06a: i2l
   // 06b: invokeinterface okio/BufferedSource.readUtf8 (J)Ljava/lang/String; 3
   // 070: astore 13
   // 072: aload 10
   // 074: invokeinterface okio/BufferedSource.close ()V 1
   // 079: lload 4
   // 07b: bipush 20
   // 07d: i2l
   // 07e: lsub
   // 07f: lstore 4
   // 081: aload 11
   // 083: astore 10
   // 085: lload 4
   // 087: lconst_0
   // 088: lcmp
   // 089: ifle 179
   // 08c: aload 15
   // 08e: lload 4
   // 090: invokevirtual okio/FileHandle.source (J)Lokio/Source;
   // 093: invokestatic okio/Okio.buffer (Lokio/Source;)Lokio/BufferedSource;
   // 096: checkcast java/io/Closeable
   // 099: astore 14
   // 09b: aload 14
   // 09d: checkcast okio/BufferedSource
   // 0a0: astore 16
   // 0a2: aload 11
   // 0a4: astore 10
   // 0a6: aload 16
   // 0a8: invokeinterface okio/BufferedSource.readIntLe ()I 1
   // 0ad: ldc 117853008
   // 0af: if_icmpne 15f
   // 0b2: aload 16
   // 0b4: invokeinterface okio/BufferedSource.readIntLe ()I 1
   // 0b9: istore 3
   // 0ba: aload 16
   // 0bc: invokeinterface okio/BufferedSource.readLongLe ()J 1
   // 0c1: lstore 4
   // 0c3: aload 16
   // 0c5: invokeinterface okio/BufferedSource.readIntLe ()I 1
   // 0ca: bipush 1
   // 0cb: if_icmpne 152
   // 0ce: iload 3
   // 0cf: ifne 152
   // 0d2: aload 15
   // 0d4: lload 4
   // 0d6: invokevirtual okio/FileHandle.source (J)Lokio/Source;
   // 0d9: invokestatic okio/Okio.buffer (Lokio/Source;)Lokio/BufferedSource;
   // 0dc: checkcast java/io/Closeable
   // 0df: astore 16
   // 0e1: aload 16
   // 0e3: checkcast okio/BufferedSource
   // 0e6: astore 10
   // 0e8: aload 10
   // 0ea: invokeinterface okio/BufferedSource.readIntLe ()I 1
   // 0ef: istore 3
   // 0f0: iload 3
   // 0f1: ldc 101075792
   // 0f3: if_icmpne 10d
   // 0f6: aload 10
   // 0f8: aload 11
   // 0fa: invokestatic okio/internal/ZipFilesKt.readZip64EocdRecord (Lokio/BufferedSource;Lokio/internal/EocdRecord;)Lokio/internal/EocdRecord;
   // 0fd: astore 10
   // 0ff: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
   // 102: astore 11
   // 104: aload 16
   // 106: aconst_null
   // 107: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
   // 10a: goto 15f
   // 10d: new java/io/IOException
   // 110: astore 0
   // 111: new java/lang/StringBuilder
   // 114: astore 1
   // 115: aload 1
   // 116: invokespecial java/lang/StringBuilder.<init> ()V
   // 119: aload 1
   // 11a: ldc_w "bad zip: expected "
   // 11d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
   // 120: pop
   // 121: aload 1
   // 122: ldc 101075792
   // 124: invokestatic okio/internal/ZipFilesKt.getHex (I)Ljava/lang/String;
   // 127: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
   // 12a: pop
   // 12b: aload 1
   // 12c: ldc_w " but was "
   // 12f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
   // 132: pop
   // 133: aload 1
   // 134: iload 3
   // 135: invokestatic okio/internal/ZipFilesKt.getHex (I)Ljava/lang/String;
   // 138: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
   // 13b: pop
   // 13c: aload 0
   // 13d: aload 1
   // 13e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
   // 141: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
   // 144: aload 0
   // 145: athrow
   // 146: astore 0
   // 147: aload 0
   // 148: athrow
   // 149: astore 1
   // 14a: aload 16
   // 14c: aload 0
   // 14d: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
   // 150: aload 1
   // 151: athrow
   // 152: new java/io/IOException
   // 155: astore 0
   // 156: aload 0
   // 157: ldc_w "unsupported zip: spanned"
   // 15a: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
   // 15d: aload 0
   // 15e: athrow
   // 15f: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
   // 162: astore 11
   // 164: aload 14
   // 166: aconst_null
   // 167: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
   // 16a: goto 179
   // 16d: astore 1
   // 16e: aload 1
   // 16f: athrow
   // 170: astore 0
   // 171: aload 14
   // 173: aload 1
   // 174: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
   // 177: aload 0
   // 178: athrow
   // 179: new java/util/ArrayList
   // 17c: astore 11
   // 17e: aload 11
   // 180: invokespecial java/util/ArrayList.<init> ()V
   // 183: aload 11
   // 185: checkcast java/util/List
   // 188: astore 14
   // 18a: aload 15
   // 18c: aload 10
   // 18e: invokevirtual okio/internal/EocdRecord.getCentralDirectoryOffset ()J
   // 191: invokevirtual okio/FileHandle.source (J)Lokio/Source;
   // 194: invokestatic okio/Okio.buffer (Lokio/Source;)Lokio/BufferedSource;
   // 197: checkcast java/io/Closeable
   // 19a: astore 11
   // 19c: aload 11
   // 19e: checkcast okio/BufferedSource
   // 1a1: astore 16
   // 1a3: aload 10
   // 1a5: invokevirtual okio/internal/EocdRecord.getEntryCount ()J
   // 1a8: lstore 8
   // 1aa: lload 6
   // 1ac: lstore 4
   // 1ae: lload 4
   // 1b0: lload 8
   // 1b2: lcmp
   // 1b3: ifge 1ff
   // 1b6: aload 16
   // 1b8: invokestatic okio/internal/ZipFilesKt.readEntry (Lokio/BufferedSource;)Lokio/internal/ZipEntry;
   // 1bb: astore 15
   // 1bd: aload 15
   // 1bf: invokevirtual okio/internal/ZipEntry.getOffset ()J
   // 1c2: aload 10
   // 1c4: invokevirtual okio/internal/EocdRecord.getCentralDirectoryOffset ()J
   // 1c7: lcmp
   // 1c8: ifge 1f2
   // 1cb: aload 2
   // 1cc: aload 15
   // 1ce: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
   // 1d3: checkcast java/lang/Boolean
   // 1d6: invokevirtual java/lang/Boolean.booleanValue ()Z
   // 1d9: ifeq 1e9
   // 1dc: aload 14
   // 1de: checkcast java/util/Collection
   // 1e1: aload 15
   // 1e3: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
   // 1e8: pop
   // 1e9: lload 4
   // 1eb: lconst_1
   // 1ec: ladd
   // 1ed: lstore 4
   // 1ef: goto 1ae
   // 1f2: new java/io/IOException
   // 1f5: astore 0
   // 1f6: aload 0
   // 1f7: ldc_w "bad zip: local file header offset >= central directory offset"
   // 1fa: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
   // 1fd: aload 0
   // 1fe: athrow
   // 1ff: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
   // 202: astore 2
   // 203: aload 11
   // 205: aconst_null
   // 206: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
   // 209: new okio/ZipFileSystem
   // 20c: dup
   // 20d: aload 0
   // 20e: aload 1
   // 20f: aload 14
   // 211: invokestatic okio/internal/ZipFilesKt.buildIndex (Ljava/util/List;)Ljava/util/Map;
   // 214: aload 13
   // 216: invokespecial okio/ZipFileSystem.<init> (Lokio/Path;Lokio/FileSystem;Ljava/util/Map;Ljava/lang/String;)V
   // 219: astore 0
   // 21a: aload 12
   // 21c: aconst_null
   // 21d: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
   // 220: aload 0
   // 221: areturn
   // 222: astore 0
   // 223: aload 0
   // 224: athrow
   // 225: astore 1
   // 226: aload 11
   // 228: aload 0
   // 229: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
   // 22c: aload 1
   // 22d: athrow
   // 22e: aload 10
   // 230: invokeinterface okio/BufferedSource.close ()V 1
   // 235: lload 4
   // 237: lconst_1
   // 238: lsub
   // 239: lstore 4
   // 23b: lload 4
   // 23d: lload 8
   // 23f: lcmp
   // 240: iflt 246
   // 243: goto 044
   // 246: new java/io/IOException
   // 249: astore 0
   // 24a: aload 0
   // 24b: ldc_w "not a zip: end of central directory signature not found"
   // 24e: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
   // 251: aload 0
   // 252: athrow
   // 253: astore 0
   // 254: aload 10
   // 256: invokeinterface okio/BufferedSource.close ()V 1
   // 25b: aload 0
   // 25c: athrow
   // 25d: new java/io/IOException
   // 260: astore 0
   // 261: new java/lang/StringBuilder
   // 264: astore 1
   // 265: aload 1
   // 266: ldc_w "not a zip: size="
   // 269: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
   // 26c: aload 1
   // 26d: aload 15
   // 26f: invokevirtual okio/FileHandle.size ()J
   // 272: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
   // 275: pop
   // 276: aload 0
   // 277: aload 1
   // 278: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
   // 27b: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
   // 27e: aload 0
   // 27f: athrow
   // 280: astore 0
   // 281: aload 0
   // 282: athrow
   // 283: astore 1
   // 284: aload 12
   // 286: aload 0
   // 287: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
   // 28a: aload 1
   // 28b: athrow
}

@Throws(java/io/IOException::class)
@JvmSynthetic
fun `openZip$default`(var0: Path, var1: FileSystem, var2: Function1, var3: Int, var4: Any): ZipFileSystem {
   if ((var3 and 4) != 0) {
      var2 = <unrepresentable>.INSTANCE;
   }

   return openZip(var0, var1, var2);
}

@Throws(java/io/IOException::class)
internal fun BufferedSource.readEntry(): ZipEntry {
   val var1: Int = var0.readIntLe();
   if (var1 == 33639248) {
      var0.skip(4L);
      var var20: Short = var0.readShortLe();
      if ((var20 and 1) == 0) {
         var20 = var0.readShortLe();
         val var14: java.lang.Long = dosDateTimeToEpochMillis(var0.readShortLe() and '\uffff', var0.readShortLe() and '\uffff');
         val var9: Long = var0.readIntLe();
         val var12: Ref.LongRef = new Ref.LongRef();
         var12.element = var0.readIntLe() and 4294967295L;
         val var11: Ref.LongRef = new Ref.LongRef();
         var11.element = var0.readIntLe() and 4294967295L;
         val var4: Short = var0.readShortLe();
         val var22: Short = var0.readShortLe();
         val var3: Short = var0.readShortLe();
         var0.skip(8L);
         val var13: Ref.LongRef = new Ref.LongRef();
         var13.element = var0.readIntLe() and 4294967295L;
         val var15: java.lang.String = var0.readUtf8((long)(var4 and '\uffff'));
         if (!StringsKt.contains$default(var15, '\u0000', false, 2, null)) {
            var var5: Long;
            if (var11.element == 4294967295L) {
               var5 = 8;
            } else {
               var5 = 0L;
            }

            if (var12.element == 4294967295L) {
               var5 += 8;
            }

            var var7: Long = var5;
            if (var13.element == 4294967295L) {
               var7 = var5 + 8;
            }

            val var16: Ref.BooleanRef = new Ref.BooleanRef();
            readExtra(var0, var22 and '\uffff', (new Function2<Integer, java.lang.Long, Unit>(var16, var7, var11, var0, var12, var13) {
               final Ref.LongRef $compressedSize;
               final Ref.BooleanRef $hasZip64Extra;
               final Ref.LongRef $offset;
               final long $requiredZip64ExtraSize;
               final Ref.LongRef $size;
               final BufferedSource $this_readEntry;

               {
                  super(2);
                  this.$hasZip64Extra = var1;
                  this.$requiredZip64ExtraSize = var2;
                  this.$size = var4;
                  this.$this_readEntry = var5;
                  this.$compressedSize = var6;
                  this.$offset = var7;
               }

               public final void invoke(int var1, long var2) {
                  if (var1 == 1) {
                     if (this.$hasZip64Extra.element) {
                        throw new IOException("bad zip: zip64 extra repeated");
                     }

                     this.$hasZip64Extra.element = true;
                     if (var2 < this.$requiredZip64ExtraSize) {
                        throw new IOException("bad zip: zip64 extra too short");
                     }

                     if (this.$size.element == 4294967295L) {
                        var2 = this.$this_readEntry.readLongLe();
                     } else {
                        var2 = this.$size.element;
                     }

                     this.$size.element = var2;
                     if (this.$compressedSize.element == 4294967295L) {
                        var2 = this.$this_readEntry.readLongLe();
                     } else {
                        var2 = 0L;
                     }

                     this.$compressedSize.element = var2;
                     var2 = 0L;
                     if (this.$offset.element == 4294967295L) {
                        var2 = this.$this_readEntry.readLongLe();
                     }

                     this.$offset.element = var2;
                  }
               }
            }) as (Int?, java.lang.Long?) -> Unit);
            if (var7 > 0L && !var16.element) {
               throw new IOException("bad zip: zip64 extra required but absent");
            } else {
               return new ZipEntry(
                  Path.Companion.get$default(Path.Companion, "/", false, 1, null).resolve(var15),
                  StringsKt.endsWith$default(var15, "/", false, 2, null),
                  var0.readUtf8((long)(var3 and '\uffff')),
                  var9 and 4294967295L,
                  var12.element,
                  var11.element,
                  var20 and '\uffff',
                  var14,
                  var13.element
               );
            }
         } else {
            throw new IOException("bad zip: filename contains 0x00");
         }
      } else {
         val var18: StringBuilder = new StringBuilder("unsupported zip: general purpose bit flag=");
         var18.append(getHex(var20 and '\uffff'));
         throw new IOException(var18.toString());
      }
   } else {
      val var17: StringBuilder = new StringBuilder("bad zip: expected ");
      var17.append(getHex(33639248));
      var17.append(" but was ");
      var17.append(getHex(var1));
      throw new IOException(var17.toString());
   }
}

@Throws(java/io/IOException::class)
private fun BufferedSource.readEocdRecord(): EocdRecord {
   val var2: Short = var0.readShortLe();
   val var1: Short = var0.readShortLe();
   val var3: Long = var0.readShortLe() and '\uffff';
   if (var3 == (var0.readShortLe() and '\uffff') && (var2 and '\uffff') == 0 && (var1 and '\uffff') == 0) {
      var0.skip(4L);
      return new EocdRecord(var3, 4294967295L and var0.readIntLe(), var0.readShortLe() and '\uffff');
   } else {
      throw new IOException("unsupported zip: spanned");
   }
}

private fun BufferedSource.readExtra(extraSize: Int, block: (Int, Long) -> Unit) {
   var var4: Long = var1;

   while (var4 != 0L) {
      if (var4 < 4L) {
         throw new IOException("bad zip: truncated header in extra field");
      }

      var1 = var0.readShortLe() and '\uffff';
      val var6: Long = var0.readShortLe() and 65535L;
      var4 = var4 - 4;
      if (var4 - 4 < var6) {
         throw new IOException("bad zip: truncated value in extra field");
      }

      var0.require(var6);
      var var8: Long = var0.getBuffer().size();
      var2.invoke(var1, var6);
      var8 = var0.getBuffer().size() + var6 - var8;
      val var14: Long;
      val var3: Byte = (byte)(if ((var14 = var8 - 0L) == 0L) 0 else (if (var14 < 0L) -1 else 1));
      if (var8 < 0L) {
         val var10: StringBuilder = new StringBuilder("unsupported zip: too many bytes processed for ");
         var10.append(var1);
         throw new IOException(var10.toString());
      }

      if (var3 > 0) {
         var0.getBuffer().skip(var8);
      }

      var4 = var4 - var6;
   }
}

internal fun BufferedSource.readLocalHeader(basicMetadata: FileMetadata): FileMetadata {
   val var2: FileMetadata = readOrSkipLocalHeader(var0, var1);
   return var2;
}

private fun BufferedSource.readOrSkipLocalHeader(basicMetadata: FileMetadata?): FileMetadata? {
   val var6: Ref.ObjectRef = new Ref.ObjectRef();
   val var5: java.lang.Long;
   if (var1 != null) {
      var5 = var1.getLastModifiedAtMillis();
   } else {
      var5 = null;
   }

   var6.element = (T)var5;
   val var12: Ref.ObjectRef = new Ref.ObjectRef();
   val var7: Ref.ObjectRef = new Ref.ObjectRef();
   val var2: Int = var0.readIntLe();
   if (var2 == 67324752) {
      var0.skip(2L);
      var var10: Int = var0.readShortLe();
      if ((var10 and 1) == 0) {
         var0.skip(18L);
         val var3: Long = var0.readShortLe();
         var10 = var0.readShortLe() and '\uffff';
         var0.skip(var3 and 65535L);
         if (var1 == null) {
            var0.skip((long)var10);
            return null;
         } else {
            readExtra(var0, var10, (new Function2<Integer, java.lang.Long, Unit>(var0, var6, var12, var7) {
               final Ref.ObjectRef<java.lang.Long> $createdAtMillis;
               final Ref.ObjectRef<java.lang.Long> $lastAccessedAtMillis;
               final Ref.ObjectRef<java.lang.Long> $lastModifiedAtMillis;
               final BufferedSource $this_readOrSkipLocalHeader;

               {
                  super(2);
                  this.$this_readOrSkipLocalHeader = var1;
                  this.$lastModifiedAtMillis = var2;
                  this.$lastAccessedAtMillis = var3;
                  this.$createdAtMillis = var4;
               }

               public final void invoke(int var1, long var2) {
                  if (var1 == 21589) {
                     var var9: Long = 1L;
                     if (var2 < 1L) {
                        throw new IOException("bad zip: extended timestamp extra too short");
                     }

                     val var6: Byte = this.$this_readOrSkipLocalHeader.readByte();
                     var var5: Boolean = false;
                     val var12: Boolean;
                     if ((var6 and 1) == 1) {
                        var12 = true;
                     } else {
                        var12 = false;
                     }

                     val var4: Boolean;
                     if ((var6 and 2) == 2) {
                        var4 = true;
                     } else {
                        var4 = false;
                     }

                     if ((var6 and 4) == 4) {
                        var5 = true;
                     }

                     if (var12) {
                        var9 = 5L;
                     }

                     var var7: Long = var9;
                     if (var4) {
                        var7 = var9 + 4L;
                     }

                     var9 = var7;
                     if (var5) {
                        var9 = var7 + 4L;
                     }

                     if (var2 < var9) {
                        throw new IOException("bad zip: extended timestamp extra too short");
                     }

                     if (var12) {
                        this.$lastModifiedAtMillis.element = (long)this.$this_readOrSkipLocalHeader.readIntLe() * 1000L;
                     }

                     if (var4) {
                        this.$lastAccessedAtMillis.element = (long)this.$this_readOrSkipLocalHeader.readIntLe() * 1000L;
                     }

                     if (var5) {
                        this.$createdAtMillis.element = (long)this.$this_readOrSkipLocalHeader.readIntLe() * 1000L;
                     }
                  }
               }
            }) as (Int?, java.lang.Long?) -> Unit);
            return new FileMetadata(
               var1.isRegularFile(),
               var1.isDirectory(),
               null,
               var1.getSize(),
               var7.element as java.lang.Long,
               var6.element as java.lang.Long,
               var12.element as java.lang.Long,
               null,
               128,
               null
            );
         }
      } else {
         val var9: StringBuilder = new StringBuilder("unsupported zip: general purpose bit flag=");
         var9.append(getHex(var10 and '\uffff'));
         throw new IOException(var9.toString());
      }
   } else {
      val var8: StringBuilder = new StringBuilder("bad zip: expected ");
      var8.append(getHex(67324752));
      var8.append(" but was ");
      var8.append(getHex(var2));
      throw new IOException(var8.toString());
   }
}

@Throws(java/io/IOException::class)
private fun BufferedSource.readZip64EocdRecord(regularRecord: EocdRecord): EocdRecord {
   var0.skip(12L);
   val var3: Int = var0.readIntLe();
   val var2: Int = var0.readIntLe();
   val var4: Long = var0.readLongLe();
   if (var4 == var0.readLongLe() && var3 == 0 && var2 == 0) {
      var0.skip(8L);
      return new EocdRecord(var4, var0.readLongLe(), var1.getCommentByteCount());
   } else {
      throw new IOException("unsupported zip: spanned");
   }
}

internal fun BufferedSource.skipLocalHeader() {
   readOrSkipLocalHeader(var0, null);
}
