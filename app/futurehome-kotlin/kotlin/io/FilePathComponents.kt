package kotlin.io

import java.io.File

internal data class FilePathComponents internal constructor(root: File, segments: List<File>) {
   public final val root: File
   public final val segments: List<File>

   public final val rootName: String
      public final get() {
         val var1: java.lang.String = this.root.getPath();
         return var1;
      }


   public final val isRooted: Boolean
      public final get() {
         val var2: java.lang.String = this.root.getPath();
         val var1: Boolean;
         if (var2.length() > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public final val size: Int
      public final get() {
         return this.segments.size();
      }


   init {
      this.root = var1;
      this.segments = var2;
   }

   public operator fun component1(): File {
      return this.root;
   }

   public operator fun component2(): List<File> {
      return this.segments;
   }

   internal fun copy(root: File = ..., segments: List<File> = ...): FilePathComponents {
      return new FilePathComponents(var1, var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is FilePathComponents) {
         return false;
      } else {
         var1 = var1;
         if (!(this.root == var1.root)) {
            return false;
         } else {
            return this.segments == var1.segments;
         }
      }
   }

   public override fun hashCode(): Int {
      return this.root.hashCode() * 31 + this.segments.hashCode();
   }

   public fun subPath(beginIndex: Int, endIndex: Int): File {
      if (var1 >= 0 && var1 <= var2 && var2 <= this.getSize()) {
         val var4: java.lang.Iterable = this.segments.subList(var1, var2);
         val var3: java.lang.String = File.separator;
         return new File(CollectionsKt.joinToString$default(var4, var3, null, null, 0, null, null, 62, null));
      } else {
         throw new IllegalArgumentException();
      }
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("FilePathComponents(root=");
      var1.append(this.root);
      var1.append(", segments=");
      var1.append(this.segments);
      var1.append(')');
      return var1.toString();
   }
}
