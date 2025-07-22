package kotlin.io.path

import java.nio.file.Path

private class PathNode(path: Path, key: Any?, parent: PathNode?) {
   public final val path: Path
   public final val key: Any?
   public final val parent: PathNode?

   public final var contentIterator: Iterator<PathNode>?
      internal set

   init {
      this.path = var1;
      this.key = var2;
      this.parent = var3;
   }
}
