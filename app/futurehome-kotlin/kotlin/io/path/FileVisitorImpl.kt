package kotlin.io.path

import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

private class FileVisitorImpl(onPreVisitDirectory: ((Path, BasicFileAttributes) -> FileVisitResult)?,
      onVisitFile: ((Path, BasicFileAttributes) -> FileVisitResult)?,
      onVisitFileFailed: ((Path, IOException) -> FileVisitResult)?,
      onPostVisitDirectory: ((Path, IOException?) -> FileVisitResult)?
   )
   : SimpleFileVisitor<Path> {
   private final val onPreVisitDirectory: ((Path, BasicFileAttributes) -> FileVisitResult)?
   private final val onVisitFile: ((Path, BasicFileAttributes) -> FileVisitResult)?
   private final val onVisitFileFailed: ((Path, IOException) -> FileVisitResult)?
   private final val onPostVisitDirectory: ((Path, IOException?) -> FileVisitResult)?

   init {
      this.onPreVisitDirectory = var1;
      this.onVisitFile = var2;
      this.onVisitFileFailed = var3;
      this.onPostVisitDirectory = var4;
   }

   public open fun postVisitDirectory(dir: Path, exc: IOException?): FileVisitResult {
      if (this.onPostVisitDirectory != null) {
         val var4: FileVisitResult = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(this.onPostVisitDirectory.invoke(var1, var2));
         if (var4 != null) {
            return var4;
         }
      }

      val var5: FileVisitResult = super.postVisitDirectory(var1, var2);
      return var5;
   }

   public open fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
      if (this.onPreVisitDirectory != null) {
         val var4: FileVisitResult = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(this.onPreVisitDirectory.invoke(var1, var2));
         if (var4 != null) {
            return var4;
         }
      }

      val var5: FileVisitResult = super.preVisitDirectory(var1, var2);
      return var5;
   }

   public open fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
      if (this.onVisitFile != null) {
         val var4: FileVisitResult = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(this.onVisitFile.invoke(var1, var2));
         if (var4 != null) {
            return var4;
         }
      }

      val var5: FileVisitResult = super.visitFile(var1, var2);
      return var5;
   }

   public open fun visitFileFailed(file: Path, exc: IOException): FileVisitResult {
      if (this.onVisitFileFailed != null) {
         val var4: FileVisitResult = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(this.onVisitFileFailed.invoke(var1, var2));
         if (var4 != null) {
            return var4;
         }
      }

      val var5: FileVisitResult = super.visitFileFailed(var1, var2);
      return var5;
   }
}
