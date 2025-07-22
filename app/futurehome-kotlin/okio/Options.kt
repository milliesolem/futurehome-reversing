package okio

import java.util.ArrayList
import java.util.Arrays
import java.util.RandomAccess

public class Options private constructor(vararg byteStrings: Any, trie: IntArray) : AbstractList<ByteString>, RandomAccess {
   internal final val byteStrings: Array<out ByteString>

   public open val size: Int
      public open get() {
         return this.byteStrings.length;
      }


   internal final val trie: IntArray

   init {
      this.byteStrings = var1;
      this.trie = var2;
   }

   public open operator fun get(index: Int): ByteString {
      return this.byteStrings[var1];
   }

   public companion object {
      private final val intCount: Long
         private final get() {
            return var1.size() / 4;
         }


      private fun buildTrieRecursive(
         nodeOffset: Long = 0L,
         node: Buffer,
         byteStringOffset: Int = 0,
         byteStrings: List<ByteString>,
         fromIndex: Int = 0,
         toIndex: Int = var5.size(),
         indexes: List<Int>
      ) {
         var var9: Int = var4;
         if (var6 >= var7) {
            throw new IllegalArgumentException("Failed requirement.".toString());
         } else {
            for (int var17 = var6; var17 < var7; var17++) {
               if ((var5.get(var17) as ByteString).size() < var9) {
                  throw new IllegalArgumentException("Failed requirement.".toString());
               }
            }

            var var13: ByteString = var5.get(var6) as ByteString;
            val var14: ByteString = var5.get(var7 - 1) as ByteString;
            if (var9 == var13.size()) {
               val var10: Int = (var8.get(var6) as java.lang.Number).intValue();
               var4 = var6 + 1;
               var13 = var5.get(var6 + 1) as ByteString;
               var6 = var10;
            } else {
               var4 = var6;
               var6 = -1;
            }

            if (var13.getByte(var9) != var14.getByte(var9)) {
               var var11: Int = var4 + 1;
               var var24: Int = 1;

               while (var11 < var7) {
                  var var12: Int = var24;
                  if ((var5.get(var11 - 1) as ByteString).getByte(var9) != (var5.get(var11) as ByteString).getByte(var9)) {
                     var12 = var24 + 1;
                  }

                  var11++;
                  var24 = var12;
               }

               var1 = var1 + this.getIntCount(var3) + 2 + var24 * 2;
               var3.writeInt(var24);
               var3.writeInt(var6);

               for (int var21 = var4; var21 < var7; var21++) {
                  val var25: Byte = (var5.get(var21) as ByteString).getByte(var9);
                  if (var21 == var4 || var25 != (var5.get(var21 - 1) as ByteString).getByte(var9)) {
                     var3.writeInt(var25 and 255);
                  }
               }

               val var31: Buffer = new Buffer();
               var6 = var4;

               while (var6 < var7) {
                  val var28: Byte = (var5.get(var6) as ByteString).getByte(var9);
                  var24 = var6 + 1;
                  var4 = var6 + 1;

                  while (true) {
                     if (var4 >= var7) {
                        var4 = var7;
                        break;
                     }

                     if (var28 != (var5.get(var4) as ByteString).getByte(var9)) {
                        break;
                     }

                     var4++;
                  }

                  if (var24 == var4 && var9 + 1 == (var5.get(var6) as ByteString).size()) {
                     var3.writeInt((var8.get(var6) as java.lang.Number).intValue());
                  } else {
                     var3.writeInt((int)(var1 + this.getIntCount(var31)) * -1);
                     this.buildTrieRecursive(var1, var31, var9 + 1, var5, var6, var4, var8);
                  }

                  var6 = var4;
               }

               var3.writeAll(var31);
            } else {
               val var30: Int = Math.min(var13.size(), var14.size());
               var var29: Int = 0;

               for (int var27 = var9; var27 < var30 && var13.getByte(var27) == var14.getByte(var27); var27++) {
                  var29++;
               }

               var1 = var1 + this.getIntCount(var3) + 2 + var29 + 1L;
               var3.writeInt(-var29);
               var3.writeInt(var6);

               for (var6 = var29 + var9; var9 < var6; var9++) {
                  var3.writeInt(var13.getByte(var9) and 255);
               }

               if (var4 + 1 == var7) {
                  if (var6 != (var5.get(var4) as ByteString).size()) {
                     throw new IllegalStateException("Check failed.".toString());
                  }

                  var3.writeInt((var8.get(var4) as java.lang.Number).intValue());
               } else {
                  val var32: Buffer = new Buffer();
                  var3.writeInt((int)(this.getIntCount(var32) + var1) * -1);
                  this.buildTrieRecursive(var1, var32, var6, var5, var4, var7, var8);
                  var3.writeAll(var32);
               }
            }
         }
      }

      public fun of(vararg byteStrings: ByteString): Options {
         if (var1.length == 0) {
            return new Options(new ByteString[0], new int[]{0, -1}, null);
         } else {
            val var6: java.util.List = ArraysKt.toMutableList(var1);
            CollectionsKt.sort(var6);
            val var7: java.util.Collection = new ArrayList(var1.length);
            var var3: Int = var1.length;

            for (int var12 = 0; var12 < var3; var12++) {
               val var8: ByteString = var1[var12];
               var7.add(-1);
            }

            val var20: Array<Int> = (var7 as java.util.List).toArray(new Integer[0]);
            val var23: java.util.List = CollectionsKt.mutableListOf(Arrays.copyOf(var20, var20.length));
            var var4: Int = var1.length;
            var3 = 0;

            for (int var13 = 0; var3 < var4; var13++) {
               var23.set(CollectionsKt.binarySearch$default(var6, var1[var3], 0, 0, 6, null), var13);
               var3++;
            }

            if ((var6.get(0) as ByteString).size() <= 0) {
               throw new IllegalArgumentException("the empty byte string is not a supported option".toString());
            } else {
               var var14: Int = 0;

               while (var14 < var6.size()) {
                  val var9: ByteString = var6.get(var14) as ByteString;
                  var3 = var14 + 1;
                  var4 = var14 + 1;

                  while (var4 < var6.size()) {
                     val var21: ByteString = var6.get(var4) as ByteString;
                     if (!var21.startsWith(var9)) {
                        break;
                     }

                     if (var21.size() == var9.size()) {
                        val var10: StringBuilder = new StringBuilder("duplicate option: ");
                        var10.append(var21);
                        throw new IllegalArgumentException(var10.toString().toString());
                     }

                     if ((var23.get(var4) as java.lang.Number).intValue() > (var23.get(var14) as java.lang.Number).intValue()) {
                        var6.remove(var4);
                        var23.remove(var4);
                     } else {
                        var4++;
                     }
                  }

                  var14 = var3;
               }

               val var22: Buffer = new Buffer();
               buildTrieRecursive$default(this, 0L, var22, 0, var6, 0, 0, var23, 53, null);
               val var19: IntArray = new int[(int)this.getIntCount(var22)];

               for (int var15 = 0; !var22.exhausted(); var15++) {
                  var19[var15] = var22.readInt();
               }

               val var11: Array<Any> = Arrays.copyOf(var1, var1.length);
               return new Options(var11 as Array<ByteString>, var19, null);
            }
         }
      }
   }
}
