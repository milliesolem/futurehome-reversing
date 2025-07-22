package kotlin.io.path

import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes

internal class FileVisitorBuilderImpl : FileVisitorBuilder {
   private final var onPreVisitDirectory: ((Path, BasicFileAttributes) -> FileVisitResult)?
   private final var onVisitFile: ((Path, BasicFileAttributes) -> FileVisitResult)?
   private final var onVisitFileFailed: ((Path, IOException) -> FileVisitResult)?
   private final var onPostVisitDirectory: ((Path, IOException?) -> FileVisitResult)?
   private final var isBuilt: Boolean

   private fun checkIsNotBuilt() {
      if (this.isBuilt) {
         throw new IllegalStateException("This builder was already built");
      }
   }

   private fun checkNotDefined(function: Any?, name: String) {
      if (var1 != null) {
         var1 = new StringBuilder();
         var1.append(var2);
         var1.append(" was already defined");
         throw new IllegalStateException(var1.toString());
      }
   }

   public fun build(): FileVisitor<Path> {
      this.checkIsNotBuilt();
      this.isBuilt = true;
      return PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(
         new FileVisitorImpl(this.onPreVisitDirectory, this.onVisitFile, this.onVisitFileFailed, this.onPostVisitDirectory)
      );
   }

   public override fun onPostVisitDirectory(function: (Path, IOException?) -> FileVisitResult) {
      this.checkIsNotBuilt();
      this.checkNotDefined(this.onPostVisitDirectory, "onPostVisitDirectory");
      this.onPostVisitDirectory = var1;
   }

   public override fun onPreVisitDirectory(function: (Path, BasicFileAttributes) -> FileVisitResult) {
      this.checkIsNotBuilt();
      this.checkNotDefined(this.onPreVisitDirectory, "onPreVisitDirectory");
      this.onPreVisitDirectory = var1;
   }

   public override fun onVisitFile(function: (Path, BasicFileAttributes) -> FileVisitResult) {
      this.checkIsNotBuilt();
      this.checkNotDefined(this.onVisitFile, "onVisitFile");
      this.onVisitFile = var1;
   }

   public override fun onVisitFileFailed(function: (Path, IOException) -> FileVisitResult) {
      this.checkIsNotBuilt();
      this.checkNotDefined(this.onVisitFileFailed, "onVisitFileFailed");
      this.onVisitFileFailed = var1;
   }
}
