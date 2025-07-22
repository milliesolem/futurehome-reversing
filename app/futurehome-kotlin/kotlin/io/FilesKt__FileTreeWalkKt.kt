package kotlin.io

import java.io.File

internal class FilesKt__FileTreeWalkKt : FilesKt__FileReadWriteKt {
   open fun FilesKt__FileTreeWalkKt() {
   }

   @JvmStatic
   public fun File.walk(direction: FileWalkDirection = FileWalkDirection.TOP_DOWN): FileTreeWalk {
      return new FileTreeWalk(var0, var1);
   }

   @JvmStatic
   public fun File.walkBottomUp(): FileTreeWalk {
      return FilesKt.walk(var0, FileWalkDirection.BOTTOM_UP);
   }

   @JvmStatic
   public fun File.walkTopDown(): FileTreeWalk {
      return FilesKt.walk(var0, FileWalkDirection.TOP_DOWN);
   }
}
