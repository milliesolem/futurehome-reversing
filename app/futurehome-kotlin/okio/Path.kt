package okio

import java.io.File
import java.util.ArrayList
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0
import okio.internal._Path

public class Path internal constructor(bytes: ByteString) : java.lang.Comparable<Path> {
   internal final val bytes: ByteString

   public final val isAbsolute: Boolean
      public final get() {
         val var1: Boolean;
         if (_Path.access$rootLength(this) != -1) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public final val isRelative: Boolean
      public final get() {
         val var1: Boolean;
         if (_Path.access$rootLength(this) == -1) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public final val isRoot: Boolean
      public final get() {
         val var1: Boolean;
         if (_Path.access$rootLength(this) == this.getBytes$okio().size()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public final val name: String
      public final get() {
         return this.nameBytes().utf8();
      }


   public final val nameBytes: ByteString
      public final get() {
         val var1: Int = _Path.access$getIndexOfLastSlash(this);
         val var2: ByteString;
         if (var1 != -1) {
            var2 = ByteString.substring$default(this.getBytes$okio(), var1 + 1, 0, 2, null);
         } else if (this.volumeLetter() != null && this.getBytes$okio().size() == 2) {
            var2 = ByteString.EMPTY;
         } else {
            var2 = this.getBytes$okio();
         }

         return var2;
      }


   public final val parent: Path?
      public final get() {
         val var2: Boolean = this.getBytes$okio() == _Path.access$getDOT$p();
         var var3: Path = null;
         if (!var2) {
            var3 = null;
            if (!(this.getBytes$okio() == _Path.access$getSLASH$p())) {
               var3 = null;
               if (!(this.getBytes$okio() == _Path.access$getBACKSLASH$p())) {
                  if (_Path.access$lastSegmentIsDotDot(this)) {
                     var3 = null;
                  } else {
                     val var1: Int = _Path.access$getIndexOfLastSlash(this);
                     if (var1 == 2 && this.volumeLetter() != null) {
                        if (this.getBytes$okio().size() == 3) {
                           var3 = null;
                        } else {
                           var3 = new Path(ByteString.substring$default(this.getBytes$okio(), 0, 3, 1, null));
                        }
                     } else if (var1 == 1 && this.getBytes$okio().startsWith(_Path.access$getBACKSLASH$p())) {
                        var3 = null;
                     } else if (var1 == -1 && this.volumeLetter() != null) {
                        if (this.getBytes$okio().size() == 2) {
                           var3 = null;
                        } else {
                           var3 = new Path(ByteString.substring$default(this.getBytes$okio(), 0, 2, 1, null));
                        }
                     } else if (var1 == -1) {
                        var3 = new Path(_Path.access$getDOT$p());
                     } else if (var1 == 0) {
                        var3 = new Path(ByteString.substring$default(this.getBytes$okio(), 0, 1, 1, null));
                     } else {
                        var3 = new Path(ByteString.substring$default(this.getBytes$okio(), 0, var1, 1, null));
                     }
                  }
               }
            }
         }

         return var3;
      }


   public final val root: Path?
      public final get() {
         val var1: Int = _Path.access$rootLength(this);
         val var2: Path;
         if (var1 == -1) {
            var2 = null;
         } else {
            var2 = new Path(this.getBytes$okio().substring(0, var1));
         }

         return var2;
      }


   public final val segments: List<String>
      public final get() {
         val var5: java.util.List = new ArrayList();
         var var2: Int = _Path.access$rootLength(this);
         var var1: Int;
         if (var2 == -1) {
            var1 = 0;
         } else {
            var1 = var2;
            if (var2 < this.getBytes$okio().size()) {
               var1 = var2;
               if (this.getBytes$okio().getByte(var2) == 92) {
                  var1 = var2 + 1;
               }
            }
         }

         val var4: Int = this.getBytes$okio().size();
         var2 = var1;

         while (var1 < var4) {
            var var3: Int;
            label38: {
               if (this.getBytes$okio().getByte(var1) != 47) {
                  var3 = var2;
                  if (this.getBytes$okio().getByte(var1) != 92) {
                     break label38;
                  }
               }

               var5.add(this.getBytes$okio().substring(var2, var1));
               var3 = var1 + 1;
            }

            var1++;
            var2 = var3;
         }

         if (var2 < this.getBytes$okio().size()) {
            var5.add(this.getBytes$okio().substring(var2, this.getBytes$okio().size()));
         }

         val var8: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
         val var9: java.util.Iterator = var5.iterator();

         while (var9.hasNext()) {
            var8.add((var9.next() as ByteString).utf8());
         }

         return var8 as MutableList<java.lang.String>;
      }


   public final val segmentsBytes: List<ByteString>
      public final get() {
         val var5: java.util.List = new ArrayList();
         var var2: Int = _Path.access$rootLength(this);
         var var1: Int;
         if (var2 == -1) {
            var1 = 0;
         } else {
            var1 = var2;
            if (var2 < this.getBytes$okio().size()) {
               var1 = var2;
               if (this.getBytes$okio().getByte(var2) == 92) {
                  var1 = var2 + 1;
               }
            }
         }

         val var4: Int = this.getBytes$okio().size();
         var var3: Int = var1;
         var2 = var1;

         while (var2 < var4) {
            label29: {
               if (this.getBytes$okio().getByte(var2) != 47) {
                  var1 = var3;
                  if (this.getBytes$okio().getByte(var2) != 92) {
                     break label29;
                  }
               }

               var5.add(this.getBytes$okio().substring(var3, var2));
               var1 = var2 + 1;
            }

            var2++;
            var3 = var1;
         }

         if (var3 < this.getBytes$okio().size()) {
            var5.add(this.getBytes$okio().substring(var3, this.getBytes$okio().size()));
         }

         return var5;
      }


   public final val volumeLetter: Char?
      public final get() {
         val var5: Character;
         if (ByteString.indexOf$default(this.getBytes$okio(), _Path.access$getSLASH$p(), 0, 2, null) != -1) {
            var5 = null;
         } else if (this.getBytes$okio().size() < 2) {
            var5 = null;
         } else if (this.getBytes$okio().getByte(1) != 58) {
            var5 = null;
         } else {
            val var1: Char = (char)this.getBytes$okio().getByte(0);
            if ('a' > var1 || var1 >= '{') {
               if ('A' > var1) {
                  return null;
               }

               if (var1 >= '[') {
                  return null;
               }
            }

            var5 = var1;
         }

         return var5;
      }


   @JvmStatic
   fun {
      val var0: java.lang.String = File.separator;
      DIRECTORY_SEPARATOR = var0;
   }

   init {
      this.bytes = var1;
   }

   public open operator fun compareTo(other: Path): Int {
      return this.getBytes$okio().compareTo(var1.getBytes$okio());
   }

   public override operator fun equals(other: Any?): Boolean {
      val var2: Boolean;
      if (var1 is Path && (var1 as Path).getBytes$okio() == this.getBytes$okio()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun hashCode(): Int {
      return this.getBytes$okio().hashCode();
   }

   public fun normalized(): Path {
      return Companion.get(this.toString(), true);
   }

   public fun relativeTo(other: Path): Path {
      if (!(this.getRoot() == var1.getRoot())) {
         val var15: StringBuilder = new StringBuilder("Paths of different roots cannot be relative to each other: ");
         var15.append(this);
         var15.append(" and ");
         var15.append(var1);
         throw new IllegalArgumentException(var15.toString().toString());
      } else {
         val var7: java.util.List = this.getSegmentsBytes();
         val var8: java.util.List = var1.getSegmentsBytes();
         val var3: Int = Math.min(var7.size(), var8.size());
         var var2: Int = 0;

         while (var2 < var3 && var7.get(var2) == var8.get(var2)) {
            var2++;
         }

         if (var2 == var3 && this.getBytes$okio().size() == var1.getBytes$okio().size()) {
            var1 = Path.Companion.get$default(Companion, ".", false, 1, null);
         } else {
            if (var8.subList(var2, var8.size()).indexOf(_Path.access$getDOT_DOT$p()) != -1) {
               val var14: StringBuilder = new StringBuilder("Impossible relative path to resolve: ");
               var14.append(this);
               var14.append(" and ");
               var14.append(var1);
               throw new IllegalArgumentException(var14.toString().toString());
            }

            val var6: Buffer = new Buffer();
            var var5: ByteString = _Path.access$getSlash(var1);
            var var9: ByteString = var5;
            if (var5 == null) {
               var5 = _Path.access$getSlash(this);
               var9 = var5;
               if (var5 == null) {
                  var9 = _Path.access$toSlash(DIRECTORY_SEPARATOR);
               }
            }

            val var4: Int = var8.size();

            for (int var11 = var2; var11 < var4; var11++) {
               var6.write(_Path.access$getDOT_DOT$p());
               var6.write(var9);
            }

            for (int var12 = var7.size(); var2 < var12; var2++) {
               var6.write(var7.get(var2) as ByteString);
               var6.write(var9);
            }

            var1 = _Path.toPath(var6, false);
         }

         return var1;
      }
   }

   public operator fun div(child: String): Path {
      return _Path.commonResolve(this, _Path.toPath(new Buffer().writeUtf8(var1), false), false);
   }

   public fun resolve(child: String, normalize: Boolean = false): Path {
      return _Path.commonResolve(this, _Path.toPath(new Buffer().writeUtf8(var1), false), var2);
   }

   public operator fun div(child: ByteString): Path {
      return _Path.commonResolve(this, _Path.toPath(new Buffer().write(var1), false), false);
   }

   public fun resolve(child: ByteString, normalize: Boolean = false): Path {
      return _Path.commonResolve(this, _Path.toPath(new Buffer().write(var1), false), var2);
   }

   public operator fun div(child: Path): Path {
      return _Path.commonResolve(this, var1, false);
   }

   public fun resolve(child: Path, normalize: Boolean = false): Path {
      return _Path.commonResolve(this, var1, var2);
   }

   public fun toFile(): File {
      return new File(this.toString());
   }

   public fun toNioPath(): java.nio.file.Path {
      val var1: java.nio.file.Path = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(this.toString(), new java.lang.String[0]);
      return var1;
   }

   public override fun toString(): String {
      return this.getBytes$okio().utf8();
   }

   public companion object {
      public final val DIRECTORY_SEPARATOR: String

      fun get(var1: File): Path {
         return get$default(this, var1, false, 1, null);
      }

      public fun File.toOkioPath(normalize: Boolean = ...): Path {
         val var3: java.lang.String = var1.toString();
         return this.get(var3, var2);
      }

      fun get(var1: java.lang.String): Path {
         return get$default(this, var1, false, 1, null);
      }

      public fun String.toPath(normalize: Boolean = ...): Path {
         return _Path.commonToPath(var1, var2);
      }

      fun get(var1: java.nio.file.Path): Path {
         return get$default(this, var1, false, 1, null);
      }

      public fun java.nio.file.Path.toOkioPath(normalize: Boolean = ...): Path {
         return this.get(var1.toString(), var2);
      }
   }
}
