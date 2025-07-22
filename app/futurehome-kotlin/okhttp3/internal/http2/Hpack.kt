package okhttp3.internal.http2

import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.LinkedHashMap
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util
import okio.Buffer
import okio.BufferedSource
import okio.ByteString
import okio.Okio
import okio.Source

public object Hpack {
   public final val NAME_TO_FIRST_INDEX: Map<ByteString, Int>
   private const val PREFIX_4_BITS: Int = 15
   private const val PREFIX_5_BITS: Int = 31
   private const val PREFIX_6_BITS: Int = 63
   private const val PREFIX_7_BITS: Int = 127
   private const val SETTINGS_HEADER_TABLE_SIZE: Int = 4096
   private const val SETTINGS_HEADER_TABLE_SIZE_LIMIT: Int = 16384
   public final val STATIC_HEADER_TABLE: Array<Header>

   @JvmStatic
   fun {
      val var0: Hpack = new Hpack();
      INSTANCE = var0;
      NAME_TO_FIRST_INDEX = var0.nameToFirstIndex();
   }

   private fun nameToFirstIndex(): Map<ByteString, Int> {
      var var4: Array<Header> = STATIC_HEADER_TABLE;
      val var3: LinkedHashMap = new LinkedHashMap(STATIC_HEADER_TABLE.length);
      val var2: Int = var4.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var4 = STATIC_HEADER_TABLE;
         if (!var3.containsKey(STATIC_HEADER_TABLE[var1].name)) {
            var3.put(var4[var1].name, var1);
         }
      }

      val var5: java.util.Map = Collections.unmodifiableMap(var3);
      Intrinsics.checkExpressionValueIsNotNull(var5, "Collections.unmodifiableMap(result)");
      return var5;
   }

   @Throws(java/io/IOException::class)
   public fun checkLowercase(name: ByteString): ByteString {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      val var3: Int = var1.size();

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Byte = 65;
         val var5: Byte = 90;
         val var6: Byte = var1.getByte(var2);
         if (var4 <= var6 && var5 >= var6) {
            val var7: StringBuilder = new StringBuilder("PROTOCOL_ERROR response malformed: mixed case name: ");
            var7.append(var1.utf8());
            throw (new IOException(var7.toString())) as java.lang.Throwable;
         }
      }

      return var1;
   }

   public class Reader  public constructor(source: Source, headerTableSizeSetting: Int, maxDynamicTableByteCount: Int = var2) {
      public final var dynamicTable: Array<Header?>
         private set

      public final var dynamicTableByteCount: Int
         private set

      public final var headerCount: Int
         private set

      private final val headerList: MutableList<Header>
      private final val headerTableSizeSetting: Int
      private final var maxDynamicTableByteCount: Int
      private final var nextHeaderIndex: Int
      private final val source: BufferedSource

      fun Reader(var1: Source, var2: Int) {
         this(var1, var2, 0, 4, null);
      }

      init {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         super();
         this.headerTableSizeSetting = var2;
         this.maxDynamicTableByteCount = var3;
         this.headerList = new ArrayList<>();
         this.source = Okio.buffer(var1);
         val var4: Array<Header> = new Header[8];
         this.dynamicTable = var4;
         this.nextHeaderIndex = var4.length - 1;
      }

      private fun adjustDynamicTableByteCount() {
         if (this.maxDynamicTableByteCount < this.dynamicTableByteCount) {
            if (this.maxDynamicTableByteCount == 0) {
               this.clearDynamicTable();
            } else {
               this.evictToRecoverBytes(this.dynamicTableByteCount - this.maxDynamicTableByteCount);
            }
         }
      }

      private fun clearDynamicTable() {
         ArraysKt.fill$default(this.dynamicTable, null, 0, 0, 6, null);
         this.nextHeaderIndex = this.dynamicTable.length - 1;
         this.headerCount = 0;
         this.dynamicTableByteCount = 0;
      }

      private fun dynamicTableIndex(index: Int): Int {
         return this.nextHeaderIndex + 1 + var1;
      }

      private fun evictToRecoverBytes(bytesToRecover: Int): Int {
         var var2: Int = 0;
         if (var1 > 0) {
            var2 = this.dynamicTable.length - 1;
            var var3: Int = var1;
            var1 = 0;

            while (true) {
               if (var2 < this.nextHeaderIndex || var3 <= 0) {
                  System.arraycopy(this.dynamicTable, this.nextHeaderIndex + 1, this.dynamicTable, this.nextHeaderIndex + 1 + var1, this.headerCount);
                  this.nextHeaderIndex += var1;
                  var2 = var1;
                  break;
               }

               val var5: Header = this.dynamicTable[var2];
               if (this.dynamicTable[var2] == null) {
                  Intrinsics.throwNpe();
               }

               var3 -= var5.hpackSize;
               this.dynamicTableByteCount = this.dynamicTableByteCount - var5.hpackSize;
               this.headerCount--;
               var1++;
               var2--;
            }
         }

         return var2;
      }

      @Throws(java/io/IOException::class)
      private fun getName(index: Int): ByteString {
         if (this.isStaticHeader(var1)) {
            return Hpack.INSTANCE.getSTATIC_HEADER_TABLE()[var1].name;
         } else {
            val var2: Int = this.dynamicTableIndex(var1 - Hpack.INSTANCE.getSTATIC_HEADER_TABLE().length);
            if (var2 >= 0 && var2 < this.dynamicTable.length) {
               val var5: Header = this.dynamicTable[var2];
               if (this.dynamicTable[var2] == null) {
                  Intrinsics.throwNpe();
               }

               return var5.name;
            } else {
               val var4: StringBuilder = new StringBuilder("Header index too large ");
               var4.append(var1 + 1);
               throw (new IOException(var4.toString())) as java.lang.Throwable;
            }
         }
      }

      private fun insertIntoDynamicTable(index: Int, entry: Header) {
         this.headerList.add(var2);
         val var4: Int = var2.hpackSize;
         var var3: Int = var2.hpackSize;
         if (var1 != -1) {
            val var6: Header = this.dynamicTable[this.dynamicTableIndex(var1)];
            if (var6 == null) {
               Intrinsics.throwNpe();
            }

            var3 = var4 - var6.hpackSize;
         }

         if (var3 > this.maxDynamicTableByteCount) {
            this.clearDynamicTable();
         } else {
            val var5: Int = this.evictToRecoverBytes(this.dynamicTableByteCount + var3 - this.maxDynamicTableByteCount);
            if (var1 == -1) {
               val var7: Array<Header> = this.dynamicTable;
               if (this.headerCount + 1 > this.dynamicTable.length) {
                  val var12: Array<Header> = new Header[this.dynamicTable.length * 2];
                  System.arraycopy(var7, 0, var12, var7.length, var7.length);
                  this.nextHeaderIndex = this.dynamicTable.length - 1;
                  this.dynamicTable = var12;
               }

               this.dynamicTable[this.nextHeaderIndex--] = var2;
               this.headerCount++;
            } else {
               this.dynamicTable[var1 + this.dynamicTableIndex(var1) + var5] = var2;
            }

            this.dynamicTableByteCount += var3;
         }
      }

      private fun isStaticHeader(index: Int): Boolean {
         return var1 >= 0 && var1 <= Hpack.INSTANCE.getSTATIC_HEADER_TABLE().length - 1;
      }

      @Throws(java/io/IOException::class)
      private fun readByte(): Int {
         return Util.and(this.source.readByte(), 255);
      }

      @Throws(java/io/IOException::class)
      private fun readIndexedHeader(index: Int) {
         if (this.isStaticHeader(var1)) {
            this.headerList.add(Hpack.INSTANCE.getSTATIC_HEADER_TABLE()[var1]);
         } else {
            val var2: Int = this.dynamicTableIndex(var1 - Hpack.INSTANCE.getSTATIC_HEADER_TABLE().length);
            if (var2 >= 0 && var2 < this.dynamicTable.length) {
               val var5: java.util.Collection = this.headerList;
               val var7: Header = this.dynamicTable[var2];
               if (this.dynamicTable[var2] == null) {
                  Intrinsics.throwNpe();
               }

               var5.add(var7);
               return;
            } else {
               val var3: StringBuilder = new StringBuilder("Header index too large ");
               var3.append(var1 + 1);
               throw (new IOException(var3.toString())) as java.lang.Throwable;
            }
         }
      }

      @Throws(java/io/IOException::class)
      private fun readLiteralHeaderWithIncrementalIndexingIndexedName(nameIndex: Int) {
         this.insertIntoDynamicTable(-1, new Header(this.getName(var1), this.readByteString()));
      }

      @Throws(java/io/IOException::class)
      private fun readLiteralHeaderWithIncrementalIndexingNewName() {
         this.insertIntoDynamicTable(-1, new Header(Hpack.INSTANCE.checkLowercase(this.readByteString()), this.readByteString()));
      }

      @Throws(java/io/IOException::class)
      private fun readLiteralHeaderWithoutIndexingIndexedName(index: Int) {
         this.headerList.add(new Header(this.getName(var1), this.readByteString()));
      }

      @Throws(java/io/IOException::class)
      private fun readLiteralHeaderWithoutIndexingNewName() {
         this.headerList.add(new Header(Hpack.INSTANCE.checkLowercase(this.readByteString()), this.readByteString()));
      }

      public fun getAndResetHeaderList(): List<Header> {
         val var1: java.util.List = CollectionsKt.toList(this.headerList);
         this.headerList.clear();
         return var1;
      }

      public fun maxDynamicTableByteCount(): Int {
         return this.maxDynamicTableByteCount;
      }

      @Throws(java/io/IOException::class)
      public fun readByteString(): ByteString {
         val var2: Int = this.readByte();
         val var1: Boolean;
         if ((var2 and 128) == 128) {
            var1 = true;
         } else {
            var1 = false;
         }

         val var3: Long = this.readInt(var2, 127);
         val var6: ByteString;
         if (var1) {
            val var5: Buffer = new Buffer();
            Huffman.INSTANCE.decode(this.source, var3, var5);
            var6 = var5.readByteString();
         } else {
            var6 = this.source.readByteString(var3);
         }

         return var6;
      }

      @Throws(java/io/IOException::class)
      public fun readHeaders() {
         while (!this.source.exhausted()) {
            var var1: Int = Util.and(this.source.readByte(), 255);
            if (var1 == 128) {
               throw (new IOException("index == 0")) as java.lang.Throwable;
            }

            if ((var1 and 128) == 128) {
               this.readIndexedHeader(this.readInt(var1, 127) - 1);
            } else if (var1 == 64) {
               this.readLiteralHeaderWithIncrementalIndexingNewName();
            } else if ((var1 and 64) == 64) {
               this.readLiteralHeaderWithIncrementalIndexingIndexedName(this.readInt(var1, 63) - 1);
            } else if ((var1 and 32) == 32) {
               var1 = this.readInt(var1, 31);
               this.maxDynamicTableByteCount = var1;
               if (var1 < 0 || var1 > this.headerTableSizeSetting) {
                  val var2: StringBuilder = new StringBuilder("Invalid dynamic table size update ");
                  var2.append(this.maxDynamicTableByteCount);
                  throw (new IOException(var2.toString())) as java.lang.Throwable;
               }

               this.adjustDynamicTableByteCount();
            } else if (var1 != 16 && var1 != 0) {
               this.readLiteralHeaderWithoutIndexingIndexedName(this.readInt(var1, 15) - 1);
            } else {
               this.readLiteralHeaderWithoutIndexingNewName();
            }
         }
      }

      @Throws(java/io/IOException::class)
      public fun readInt(firstByte: Int, prefixMask: Int): Int {
         var1 = var1 and var2;
         if ((var1 and var2) < var2) {
            return var1;
         } else {
            var1 = 0;

            while (true) {
               val var3: Int = this.readByte();
               if ((var3 and 128) == 0) {
                  return var2 + (var3 shl var1);
               }

               var2 += (var3 and 127) shl var1;
               var1 += 7;
            }
         }
      }
   }

   public class Writer  public constructor(headerTableSizeSetting: Int = 4096, useCompression: Boolean = true, out: Buffer) {
      public final var dynamicTable: Array<Header?>
         private set

      public final var dynamicTableByteCount: Int
         private set

      private final var emitDynamicTableSizeUpdate: Boolean

      public final var headerCount: Int
         private set

      public final var headerTableSizeSetting: Int
         private set

      public final var maxDynamicTableByteCount: Int
         private set

      private final var nextHeaderIndex: Int
      private final val out: Buffer
      private final var smallestHeaderTableSizeSetting: Int
      private final val useCompression: Boolean

      fun Writer(var1: Int, var2: Buffer) {
         this(var1, false, var2, 2, null);
      }

      init {
         Intrinsics.checkParameterIsNotNull(var3, "out");
         super();
         this.headerTableSizeSetting = var1;
         this.useCompression = var2;
         this.out = var3;
         this.smallestHeaderTableSizeSetting = Integer.MAX_VALUE;
         this.maxDynamicTableByteCount = var1;
         val var4: Array<Header> = new Header[8];
         this.dynamicTable = var4;
         this.nextHeaderIndex = var4.length - 1;
      }

      fun Writer(var1: Buffer) {
         this(0, false, var1, 3, null);
      }

      private fun adjustDynamicTableByteCount() {
         if (this.maxDynamicTableByteCount < this.dynamicTableByteCount) {
            if (this.maxDynamicTableByteCount == 0) {
               this.clearDynamicTable();
            } else {
               this.evictToRecoverBytes(this.dynamicTableByteCount - this.maxDynamicTableByteCount);
            }
         }
      }

      private fun clearDynamicTable() {
         ArraysKt.fill$default(this.dynamicTable, null, 0, 0, 6, null);
         this.nextHeaderIndex = this.dynamicTable.length - 1;
         this.headerCount = 0;
         this.dynamicTableByteCount = 0;
      }

      private fun evictToRecoverBytes(bytesToRecover: Int): Int {
         var var2: Int = 0;
         if (var1 > 0) {
            var2 = this.dynamicTable.length - 1;
            var var3: Int = var1;
            var1 = 0;

            while (true) {
               if (var2 < this.nextHeaderIndex || var3 <= 0) {
                  System.arraycopy(this.dynamicTable, this.nextHeaderIndex + 1, this.dynamicTable, this.nextHeaderIndex + 1 + var1, this.headerCount);
                  Arrays.fill(this.dynamicTable, this.nextHeaderIndex + 1, this.nextHeaderIndex + 1 + var1, null);
                  this.nextHeaderIndex += var1;
                  var2 = var1;
                  break;
               }

               var var5: Header = this.dynamicTable[var2];
               if (this.dynamicTable[var2] == null) {
                  Intrinsics.throwNpe();
               }

               var3 -= var5.hpackSize;
               var5 = this.dynamicTable[var2];
               if (this.dynamicTable[var2] == null) {
                  Intrinsics.throwNpe();
               }

               this.dynamicTableByteCount = this.dynamicTableByteCount - var5.hpackSize;
               this.headerCount--;
               var1++;
               var2--;
            }
         }

         return var2;
      }

      private fun insertIntoDynamicTable(entry: Header) {
         val var2: Int = var1.hpackSize;
         if (var1.hpackSize > this.maxDynamicTableByteCount) {
            this.clearDynamicTable();
         } else {
            this.evictToRecoverBytes(this.dynamicTableByteCount + var1.hpackSize - this.maxDynamicTableByteCount);
            val var4: Array<Header> = this.dynamicTable;
            if (this.headerCount + 1 > this.dynamicTable.length) {
               val var5: Array<Header> = new Header[this.dynamicTable.length * 2];
               System.arraycopy(var4, 0, var5, var4.length, var4.length);
               this.nextHeaderIndex = this.dynamicTable.length - 1;
               this.dynamicTable = var5;
            }

            this.dynamicTable[this.nextHeaderIndex--] = var1;
            this.headerCount++;
            this.dynamicTableByteCount += var2;
         }
      }

      public fun resizeHeaderTable(headerTableSizeSetting: Int) {
         this.headerTableSizeSetting = var1;
         val var2: Int = Math.min(var1, 16384);
         if (this.maxDynamicTableByteCount != var2) {
            if (var2 < this.maxDynamicTableByteCount) {
               this.smallestHeaderTableSizeSetting = Math.min(this.smallestHeaderTableSizeSetting, var2);
            }

            this.emitDynamicTableSizeUpdate = true;
            this.maxDynamicTableByteCount = var2;
            this.adjustDynamicTableByteCount();
         }
      }

      @Throws(java/io/IOException::class)
      public fun writeByteString(data: ByteString) {
         Intrinsics.checkParameterIsNotNull(var1, "data");
         if (this.useCompression && Huffman.INSTANCE.encodedLength(var1) < var1.size()) {
            val var2: Buffer = new Buffer();
            Huffman.INSTANCE.encode(var1, var2);
            var1 = var2.readByteString();
            this.writeInt(var1.size(), 127, 128);
            this.out.write(var1);
         } else {
            this.writeInt(var1.size(), 127, 0);
            this.out.write(var1);
         }
      }

      @Throws(java/io/IOException::class)
      public fun writeHeaders(headerBlock: List<Header>) {
         Intrinsics.checkParameterIsNotNull(var1, "headerBlock");
         if (this.emitDynamicTableSizeUpdate) {
            if (this.smallestHeaderTableSizeSetting < this.maxDynamicTableByteCount) {
               this.writeInt(this.smallestHeaderTableSizeSetting, 31, 32);
            }

            this.emitDynamicTableSizeUpdate = false;
            this.smallestHeaderTableSizeSetting = Integer.MAX_VALUE;
            this.writeInt(this.maxDynamicTableByteCount, 31, 32);
         }

         val var8: Int = var1.size();

         for (int var4 = 0; var4 < var8; var4++) {
            var var3: Int;
            var var10: Header;
            var var11: ByteString;
            var var12: ByteString;
            var var15: Int;
            label60: {
               var10 = var1.get(var4) as Header;
               var12 = var10.name.toAsciiLowercase();
               var11 = var10.value;
               val var13: Int = Hpack.INSTANCE.getNAME_TO_FIRST_INDEX().get(var12);
               if (var13 != null) {
                  var15 = var13;
                  var3 = var15 + 1;
                  if (2 <= var15 + 1 && 7 >= var15 + 1) {
                     if (Hpack.INSTANCE.getSTATIC_HEADER_TABLE()[var15].value == var11) {
                        var15 = var3;
                        break label60;
                     }

                     if (Hpack.INSTANCE.getSTATIC_HEADER_TABLE()[var3].value == var11) {
                        val var5: Int = var15 + 2;
                        var15 = var3;
                        var3 = var5;
                        break label60;
                     }
                  }

                  var15 = var3;
               } else {
                  var15 = -1;
               }

               var3 = -1;
            }

            var var6: Int = var15;
            var var7: Int = var3;
            if (var3 == -1) {
               var var17: Int = this.nextHeaderIndex + 1;
               val var9: Int = this.dynamicTable.length;

               while (true) {
                  var6 = var15;
                  var7 = var3;
                  if (var17 >= var9) {
                     break;
                  }

                  var var19: Header = this.dynamicTable[var17];
                  if (this.dynamicTable[var17] == null) {
                     Intrinsics.throwNpe();
                  }

                  var6 = var15;
                  if (var19.name == var12) {
                     var19 = this.dynamicTable[var17];
                     if (this.dynamicTable[var17] == null) {
                        Intrinsics.throwNpe();
                     }

                     if (var19.value == var11) {
                        var3 = this.nextHeaderIndex;
                        var7 = Hpack.INSTANCE.getSTATIC_HEADER_TABLE().length + (var17 - var3);
                        var6 = var15;
                        break;
                     }

                     var6 = var15;
                     if (var15 == -1) {
                        var6 = var17 - this.nextHeaderIndex + Hpack.INSTANCE.getSTATIC_HEADER_TABLE().length;
                     }
                  }

                  var17++;
                  var15 = var6;
               }
            }

            if (var7 != -1) {
               this.writeInt(var7, 127, 128);
            } else if (var6 == -1) {
               this.out.writeByte(64);
               this.writeByteString(var12);
               this.writeByteString(var11);
               this.insertIntoDynamicTable(var10);
            } else if (var12.startsWith(Header.PSEUDO_PREFIX) && !(Header.TARGET_AUTHORITY == var12)) {
               this.writeInt(var6, 15, 0);
               this.writeByteString(var11);
            } else {
               this.writeInt(var6, 63, 64);
               this.writeByteString(var11);
               this.insertIntoDynamicTable(var10);
            }
         }
      }

      public fun writeInt(value: Int, prefixMask: Int, bits: Int) {
         if (var1 < var2) {
            this.out.writeByte(var1 or var3);
         } else {
            this.out.writeByte(var3 or var2);

            for (var1 = var1 - var2; var1 >= 128; var1 >>>= 7) {
               this.out.writeByte(128 or var1 and 127);
            }

            this.out.writeByte(var1);
         }
      }
   }
}
