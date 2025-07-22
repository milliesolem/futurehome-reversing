package kotlin.io.path

import java.nio.file.FileVisitResult
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

private class DirectoryEntriesReader(followLinks: Boolean) : SimpleFileVisitor<Path> {
   public final val followLinks: Boolean
   private final var directoryNode: PathNode?
   private final var entries: ArrayDeque<PathNode>

   init {
      this.followLinks = var1;
      this.entries = new ArrayDeque<>();
   }

   public open fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
      this.entries.add(new PathNode(var1, PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var2), this.directoryNode));
      val var4: FileVisitResult = super.preVisitDirectory(var1, var2);
      return var4;
   }

   public fun readEntries(directoryNode: PathNode): List<PathNode> {
      this.directoryNode = var1;
      PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(
         var1.getPath(), LinkFollowing.INSTANCE.toVisitOptions(this.followLinks), 1, PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(this)
      );
      this.entries.removeFirst();
      val var2: ArrayDeque = this.entries;
      this.entries = new ArrayDeque<>();
      return var2;
   }

   public open fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
      this.entries.add(new PathNode(var1, null, this.directoryNode));
      val var4: FileVisitResult = super.visitFile(var1, var2);
      return var4;
   }
}
