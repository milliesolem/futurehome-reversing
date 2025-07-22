package okio.internal

import java.util.ArrayList
import okio.Buffer
import okio.ByteString
import okio.Path

private final val ANY_SLASH: ByteString = ByteString.Companion.encodeUtf8("/\\")
private final val BACKSLASH: ByteString = ByteString.Companion.encodeUtf8("\\")
private final val DOT: ByteString = ByteString.Companion.encodeUtf8(".")
private final val DOT_DOT: ByteString = ByteString.Companion.encodeUtf8("..")
private final val SLASH: ByteString = ByteString.Companion.encodeUtf8("/")

private final val indexOfLastSlash: Int
   private final get() {
      val var1: Int = ByteString.lastIndexOf$default(var0.getBytes$okio(), SLASH, 0, 2, null);
      return if (var1 != -1) var1 else ByteString.lastIndexOf$default(var0.getBytes$okio(), BACKSLASH, 0, 2, null);
   }


private final val slash: ByteString?
   private final get() {
      val var2: ByteString = var0.getBytes$okio();
      var var1: ByteString = SLASH;
      var var3: ByteString;
      if (ByteString.indexOf$default(var2, SLASH, 0, 2, null) != -1) {
         var3 = var1;
      } else {
         var1 = var0.getBytes$okio();
         var3 = BACKSLASH;
         if (ByteString.indexOf$default(var1, BACKSLASH, 0, 2, null) == -1) {
            var3 = null;
         }
      }

      return var3;
   }


@JvmSynthetic
fun `access$getBACKSLASH$p`(): ByteString {
   return BACKSLASH;
}

@JvmSynthetic
fun `access$getDOT$p`(): ByteString {
   return DOT;
}

@JvmSynthetic
fun `access$getDOT_DOT$p`(): ByteString {
   return DOT_DOT;
}

@JvmSynthetic
fun `access$getIndexOfLastSlash`(var0: Path): Int {
   return getIndexOfLastSlash(var0);
}

@JvmSynthetic
fun `access$getSLASH$p`(): ByteString {
   return SLASH;
}

@JvmSynthetic
fun `access$getSlash`(var0: Path): ByteString {
   return getSlash(var0);
}

@JvmSynthetic
fun `access$lastSegmentIsDotDot`(var0: Path): Boolean {
   return lastSegmentIsDotDot(var0);
}

@JvmSynthetic
fun `access$rootLength`(var0: Path): Int {
   return rootLength(var0);
}

@JvmSynthetic
fun `access$toSlash`(var0: java.lang.String): ByteString {
   return toSlash(var0);
}

internal inline fun Path.commonCompareTo(other: Path): Int {
   return var0.getBytes$okio().compareTo(var1.getBytes$okio());
}

internal inline fun Path.commonEquals(other: Any?): Boolean {
   val var2: Boolean;
   if (var1 is Path && (var1 as Path).getBytes$okio() == var0.getBytes$okio()) {
      var2 = true;
   } else {
      var2 = false;
   }

   return var2;
}

internal inline fun Path.commonHashCode(): Int {
   return var0.getBytes$okio().hashCode();
}

internal inline fun Path.commonIsAbsolute(): Boolean {
   val var1: Boolean;
   if (access$rootLength(var0) != -1) {
      var1 = true;
   } else {
      var1 = false;
   }

   return var1;
}

internal inline fun Path.commonIsRelative(): Boolean {
   val var1: Boolean;
   if (access$rootLength(var0) == -1) {
      var1 = true;
   } else {
      var1 = false;
   }

   return var1;
}

internal inline fun Path.commonIsRoot(): Boolean {
   val var1: Boolean;
   if (access$rootLength(var0) == var0.getBytes$okio().size()) {
      var1 = true;
   } else {
      var1 = false;
   }

   return var1;
}

internal inline fun Path.commonName(): String {
   return var0.nameBytes().utf8();
}

internal inline fun Path.commonNameBytes(): ByteString {
   val var1: Int = access$getIndexOfLastSlash(var0);
   val var2: ByteString;
   if (var1 != -1) {
      var2 = ByteString.substring$default(var0.getBytes$okio(), var1 + 1, 0, 2, null);
   } else if (var0.volumeLetter() != null && var0.getBytes$okio().size() == 2) {
      var2 = ByteString.EMPTY;
   } else {
      var2 = var0.getBytes$okio();
   }

   return var2;
}

internal inline fun Path.commonNormalized(): Path {
   return Path.Companion.get(var0.toString(), true);
}

internal inline fun Path.commonParent(): Path? {
   if (!(var0.getBytes$okio() == access$getDOT$p())
      && !(var0.getBytes$okio() == access$getSLASH$p())
      && !(var0.getBytes$okio() == access$getBACKSLASH$p())
      && !access$lastSegmentIsDotDot(var0)) {
      val var1: Int = access$getIndexOfLastSlash(var0);
      if (var1 == 2 && var0.volumeLetter() != null) {
         return if (var0.getBytes$okio().size() == 3) null else new Path(ByteString.substring$default(var0.getBytes$okio(), 0, 3, 1, null));
      } else if (var1 == 1 && var0.getBytes$okio().startsWith(access$getBACKSLASH$p())) {
         return null;
      } else if (var1 == -1 && var0.volumeLetter() != null) {
         return if (var0.getBytes$okio().size() == 2) null else new Path(ByteString.substring$default(var0.getBytes$okio(), 0, 2, 1, null));
      } else if (var1 == -1) {
         return new Path(access$getDOT$p());
      } else {
         return if (var1 == 0)
            new Path(ByteString.substring$default(var0.getBytes$okio(), 0, 1, 1, null))
            else
            new Path(ByteString.substring$default(var0.getBytes$okio(), 0, var1, 1, null));
      }
   } else {
      return null;
   }
}

internal inline fun Path.commonRelativeTo(other: Path): Path {
   if (!(var0.getRoot() == var1.getRoot())) {
      val var14: StringBuilder = new StringBuilder("Paths of different roots cannot be relative to each other: ");
      var14.append(var0);
      var14.append(" and ");
      var14.append(var1);
      throw new IllegalArgumentException(var14.toString().toString());
   } else {
      val var6: java.util.List = var0.getSegmentsBytes();
      val var7: java.util.List = var1.getSegmentsBytes();
      val var3: Int = Math.min(var6.size(), var7.size());
      var var2: Int = 0;

      while (var2 < var3 && var6.get(var2) == var7.get(var2)) {
         var2++;
      }

      if (var2 == var3 && var0.getBytes$okio().size() == var1.getBytes$okio().size()) {
         return Path.Companion.get$default(Path.Companion, ".", false, 1, null);
      } else if (var7.subList(var2, var7.size()).indexOf(access$getDOT_DOT$p()) != -1) {
         val var13: StringBuilder = new StringBuilder("Impossible relative path to resolve: ");
         var13.append(var0);
         var13.append(" and ");
         var13.append(var1);
         throw new IllegalArgumentException(var13.toString().toString());
      } else {
         val var8: Buffer = new Buffer();
         val var5: ByteString = access$getSlash(var1);
         var var10: ByteString = var5;
         if (var5 == null) {
            val var9: ByteString = access$getSlash(var0);
            var10 = var9;
            if (var9 == null) {
               var10 = access$toSlash(Path.DIRECTORY_SEPARATOR);
            }
         }

         val var4: Int = var7.size();

         for (int var11 = var2; var11 < var4; var11++) {
            var8.write(access$getDOT_DOT$p());
            var8.write(var10);
         }

         for (int var12 = var6.size(); var2 < var12; var2++) {
            var8.write(var6.get(var2) as ByteString);
            var8.write(var10);
         }

         return toPath(var8, false);
      }
   }
}

internal inline fun Path.commonResolve(child: String, normalize: Boolean): Path {
   return commonResolve(var0, toPath(new Buffer().writeUtf8(var1), false), var2);
}

internal inline fun Path.commonResolve(child: Buffer, normalize: Boolean): Path {
   return commonResolve(var0, toPath(var1, false), var2);
}

internal inline fun Path.commonResolve(child: ByteString, normalize: Boolean): Path {
   return commonResolve(var0, toPath(new Buffer().write(var1), false), var2);
}

internal fun Path.commonResolve(child: Path, normalize: Boolean): Path {
   if (!var1.isAbsolute() && var1.volumeLetter() == null) {
      var var4: ByteString = getSlash(var0);
      var var3: ByteString = var4;
      if (var4 == null) {
         var4 = getSlash(var1);
         var3 = var4;
         if (var4 == null) {
            var3 = toSlash(Path.DIRECTORY_SEPARATOR);
         }
      }

      val var6: Buffer = new Buffer();
      var6.write(var0.getBytes$okio());
      if (var6.size() > 0L) {
         var6.write(var3);
      }

      var6.write(var1.getBytes$okio());
      return toPath(var6, var2);
   } else {
      return var1;
   }
}

internal inline fun Path.commonRoot(): Path? {
   val var1: Int = access$rootLength(var0);
   if (var1 == -1) {
      var0 = null;
   } else {
      var0 = new Path(var0.getBytes$okio().substring(0, var1));
   }

   return var0;
}

internal inline fun Path.commonSegments(): List<String> {
   val var5: java.util.List = new ArrayList();
   var var2: Int = access$rootLength(var0);
   var var1: Int;
   if (var2 == -1) {
      var1 = 0;
   } else {
      var1 = var2;
      if (var2 < var0.getBytes$okio().size()) {
         var1 = var2;
         if (var0.getBytes$okio().getByte(var2) == 92) {
            var1 = var2 + 1;
         }
      }
   }

   val var4: Int = var0.getBytes$okio().size();
   var2 = var1;

   while (var1 < var4) {
      var var3: Int;
      label38: {
         if (var0.getBytes$okio().getByte(var1) != 47) {
            var3 = var2;
            if (var0.getBytes$okio().getByte(var1) != 92) {
               break label38;
            }
         }

         var5.add(var0.getBytes$okio().substring(var2, var1));
         var3 = var1 + 1;
      }

      var1++;
      var2 = var3;
   }

   if (var2 < var0.getBytes$okio().size()) {
      var5.add(var0.getBytes$okio().substring(var2, var0.getBytes$okio().size()));
   }

   val var6: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
   val var9: java.util.Iterator = var5.iterator();

   while (var9.hasNext()) {
      var6.add((var9.next() as ByteString).utf8());
   }

   return var6 as MutableList<java.lang.String>;
}

internal inline fun Path.commonSegmentsBytes(): List<ByteString> {
   val var5: java.util.List = new ArrayList();
   var var2: Int = access$rootLength(var0);
   var var1: Int;
   if (var2 == -1) {
      var1 = 0;
   } else {
      var1 = var2;
      if (var2 < var0.getBytes$okio().size()) {
         var1 = var2;
         if (var0.getBytes$okio().getByte(var2) == 92) {
            var1 = var2 + 1;
         }
      }
   }

   val var4: Int = var0.getBytes$okio().size();
   var2 = var1;
   var var3: Int = var1;

   while (var3 < var4) {
      label29: {
         if (var0.getBytes$okio().getByte(var3) != 47) {
            var1 = var2;
            if (var0.getBytes$okio().getByte(var3) != 92) {
               break label29;
            }
         }

         var5.add(var0.getBytes$okio().substring(var2, var3));
         var1 = var3 + 1;
      }

      var3++;
      var2 = var1;
   }

   if (var2 < var0.getBytes$okio().size()) {
      var5.add(var0.getBytes$okio().substring(var2, var0.getBytes$okio().size()));
   }

   return var5;
}

internal fun String.commonToPath(normalize: Boolean): Path {
   return toPath(new Buffer().writeUtf8(var0), var1);
}

internal inline fun Path.commonToString(): String {
   return var0.getBytes$okio().utf8();
}

internal inline fun Path.commonVolumeLetter(): Char? {
   if (ByteString.indexOf$default(var0.getBytes$okio(), access$getSLASH$p(), 0, 2, null) != -1) {
      return null;
   } else if (var0.getBytes$okio().size() < 2) {
      return null;
   } else if (var0.getBytes$okio().getByte(1) != 58) {
      return null;
   } else {
      val var1: Char = (char)var0.getBytes$okio().getByte(0);
      return if (('a' > var1 || var1 >= '{') && ('A' > var1 || var1 >= '[')) null else var1;
   }
}

private fun Path.lastSegmentIsDotDot(): Boolean {
   if (var0.getBytes$okio().endsWith(DOT_DOT)) {
      if (var0.getBytes$okio().size() == 2) {
         return true;
      }

      if (var0.getBytes$okio().rangeEquals(var0.getBytes$okio().size() - 3, SLASH, 0, 1)) {
         return true;
      }

      if (var0.getBytes$okio().rangeEquals(var0.getBytes$okio().size() - 3, BACKSLASH, 0, 1)) {
         return true;
      }
   }

   return false;
}

private fun Path.rootLength(): Int {
   if (var0.getBytes$okio().size() == 0) {
      return -1;
   } else if (var0.getBytes$okio().getByte(0) == 47) {
      return 1;
   } else if (var0.getBytes$okio().getByte(0) == 92) {
      if (var0.getBytes$okio().size() > 2 && var0.getBytes$okio().getByte(1) == 92) {
         val var2: Int = var0.getBytes$okio().indexOf(BACKSLASH, 2);
         var var3: Int = var2;
         if (var2 == -1) {
            var3 = var0.getBytes$okio().size();
         }

         return var3;
      } else {
         return 1;
      }
   } else {
      if (var0.getBytes$okio().size() > 2 && var0.getBytes$okio().getByte(1) == 58 && var0.getBytes$okio().getByte(2) == 92) {
         val var1: Char = (char)var0.getBytes$okio().getByte(0);
         if ('a' <= var1 && var1 < '{' || 'A' <= var1 && var1 < '[') {
            return 3;
         }
      }

      return -1;
   }
}

private fun Buffer.startsWithVolumeLetterAndColon(slash: ByteString): Boolean {
   if (!(var1 == BACKSLASH)) {
      return false;
   } else if (var0.size() < 2L) {
      return false;
   } else if (var0.getByte(1L) != 58) {
      return false;
   } else {
      val var2: Char = (char)var0.getByte(0L);
      if ('a' > var2 || var2 >= '{') {
         if ('A' > var2) {
            return false;
         }

         if (var2 >= '[') {
            return false;
         }
      }

      return true;
   }
}

internal fun Buffer.toPath(normalize: Boolean): Path {
   val var10: Buffer = new Buffer();
   var var8: ByteString = null;
   var var4: Int = 0;

   while (true) {
      if (!var0.rangeEquals(0L, SLASH)) {
         var var9: ByteString = BACKSLASH;
         if (!var0.rangeEquals(0L, BACKSLASH)) {
            val var3: Boolean;
            if (var4 >= 2 && var8 == var9) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var3) {
               var10.write(var8);
               var10.write(var8);
            } else if (var4 > 0) {
               var10.write(var8);
            } else {
               val var6: Long = var0.indexOfElement(ANY_SLASH);
               var9 = var8;
               if (var8 == null) {
                  if (var6 == -1L) {
                     var9 = toSlash(Path.DIRECTORY_SEPARATOR);
                  } else {
                     var9 = toSlash(var0.getByte(var6));
                  }
               }

               var8 = var9;
               if (startsWithVolumeLetterAndColon(var0, var9)) {
                  if (var6 == 2L) {
                     var10.write(var0, 3L);
                     var8 = var9;
                  } else {
                     var10.write(var0, 2L);
                     var8 = var9;
                  }
               }
            }

            var var14: Boolean;
            if (var10.size() > 0L) {
               var14 = 1;
            } else {
               var14 = 0;
            }

            val var11: java.util.List = new ArrayList();

            while (!var0.exhausted()) {
               val var16: Long = var0.indexOfElement(ANY_SLASH);
               if (var16 == -1L) {
                  var9 = var0.readByteString();
               } else {
                  var9 = var0.readByteString(var16);
                  var0.readByte();
               }

               val var12: ByteString = DOT_DOT;
               if (var9 == DOT_DOT) {
                  if (!var14 || !var11.isEmpty()) {
                     if (var1 && (var14 || !var11.isEmpty() && !(CollectionsKt.last(var11) == var12))) {
                        if (!var3 || var11.size() != 1) {
                           CollectionsKt.removeLastOrNull(var11);
                        }
                     } else {
                        var11.add(var9);
                     }
                  }
               } else if (!(var9 == DOT) && !(var9 == ByteString.EMPTY)) {
                  var11.add(var9);
               }
            }

            var14 = var11.size();

            for (int var13 = 0; var13 < var14; var13++) {
               if (var13 > 0) {
                  var10.write(var8);
               }

               var10.write(var11.get(var13) as ByteString);
            }

            if (var10.size() == 0L) {
               var10.write(DOT);
            }

            return new Path(var10.readByteString());
         }
      }

      val var2: Byte = var0.readByte();
      var var17: ByteString = var8;
      if (var8 == null) {
         var17 = toSlash(var2);
      }

      var4++;
      var8 = var17;
   }
}

private fun Byte.toSlash(): ByteString {
   val var1: ByteString;
   if (var0 != 47) {
      if (var0 != 92) {
         val var2: StringBuilder = new StringBuilder("not a directory separator: ");
         var2.append((int)var0);
         throw new IllegalArgumentException(var2.toString());
      }

      var1 = BACKSLASH;
   } else {
      var1 = SLASH;
   }

   return var1;
}

private fun String.toSlash(): ByteString {
   val var2: ByteString;
   if (var0 == "/") {
      var2 = SLASH;
   } else {
      if (!(var0 == "\\")) {
         val var1: StringBuilder = new StringBuilder("not a directory separator: ");
         var1.append(var0);
         throw new IllegalArgumentException(var1.toString());
      }

      var2 = BACKSLASH;
   }

   return var2;
}
