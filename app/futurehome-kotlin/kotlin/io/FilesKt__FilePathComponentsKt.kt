package kotlin.io

import java.io.File
import java.util.ArrayList

internal class FilesKt__FilePathComponentsKt {
   internal final val rootName: String
      internal final get() {
         val var1: java.lang.String = var0.getPath();
         val var2: java.lang.String = var0.getPath();
         val var3: java.lang.String = var1.substring(0, getRootLength$FilesKt__FilePathComponentsKt(var2));
         return var3;
      }


   internal final val root: File
      internal final get() {
         return new File(FilesKt.getRootName(var0));
      }


   public final val isRooted: Boolean
      public final get() {
         val var2: java.lang.String = var0.getPath();
         val var1: Boolean;
         if (getRootLength$FilesKt__FilePathComponentsKt(var2) > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   open fun FilesKt__FilePathComponentsKt() {
   }

   @JvmStatic
   private fun String.getRootLength(): Int {
      val var2: java.lang.CharSequence = var0;
      var var1: Int = StringsKt.indexOf$default(var0, File.separatorChar, 0, false, 4, null);
      if (var1 == 0) {
         if (var0.length() > 1 && var0.charAt(1) == File.separatorChar) {
            var1 = StringsKt.indexOf$default(var2, File.separatorChar, 2, false, 4, null);
            if (var1 >= 0) {
               var1 = StringsKt.indexOf$default(var2, File.separatorChar, var1 + 1, false, 4, null);
               if (var1 >= 0) {
                  return var1 + 1;
               }

               return var0.length();
            }
         }

         return 1;
      } else if (var1 > 0 && var0.charAt(var1 - 1) == ':') {
         return var1 + 1;
      } else {
         return if (var1 == -1 && StringsKt.endsWith$default(var2, (char)58, false, 2, null)) var0.length() else 0;
      }
   }

   @JvmStatic
   internal fun File.subPath(beginIndex: Int, endIndex: Int): File {
      return FilesKt.toComponents(var0).subPath(var1, var2);
   }

   @JvmStatic
   internal fun File.toComponents(): FilePathComponents {
      val var4: java.lang.String = var0.getPath();
      val var1: Int = getRootLength$FilesKt__FilePathComponentsKt(var4);
      val var2: java.lang.String = var4.substring(0, var1);
      val var5: java.lang.String = var4.substring(var1);
      val var6: java.lang.CharSequence = var5;
      val var7: java.util.List;
      if (var5.length() == 0) {
         var7 = CollectionsKt.emptyList();
      } else {
         val var3: java.lang.Iterable = StringsKt.split$default(var6, new char[]{File.separatorChar}, false, 0, 6, null);
         val var8: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));
         val var9: java.util.Iterator = var3.iterator();

         while (var9.hasNext()) {
            var8.add(new File(var9.next() as java.lang.String));
         }

         var7 = var8 as java.util.List;
      }

      return new FilePathComponents(new File(var2), var7);
   }
}
