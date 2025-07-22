package okio

import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.CopyOption
import java.nio.file.FileSystemException
import java.nio.file.LinkOption
import java.nio.file.NoSuchFileException
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileAttribute
import java.nio.file.attribute.FileTime
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0

internal open class NioSystemFileSystem : JvmSystemFileSystem {
   private fun FileTime.zeroToNull(): Long? {
      var var2: java.lang.Long = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var1);
      if (var2.longValue() == 0L) {
         var2 = null;
      }

      return var2;
   }

   public override fun atomicMove(source: Path, target: Path) {
      try {
         PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(
            var1.toNioPath(),
            var2.toNioPath(),
            new CopyOption[]{
               NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m()),
               NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m())
            }
         );
      } catch (var3: NoSuchFileException) {
         throw new FileNotFoundException(NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var3));
      } catch (var4: UnsupportedOperationException) {
         throw new IOException("atomic move not supported");
      }
   }

   public override fun createSymlink(source: Path, target: Path) {
      PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var1.toNioPath(), var2.toNioPath(), new FileAttribute[0]);
   }

   protected fun metadataOrNull(nioPath: java.nio.file.Path): FileMetadata? {
      var var8: java.lang.Long = null;

      var var9: BasicFileAttributes;
      try {
         var9 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(
            var1, PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(), new LinkOption[]{PathTreeWalk$$ExternalSyntheticApiModelOutline0.m()}
         );
      } catch (FileSystemException | var10: NoSuchFileException) {
         return null;
      }

      if (NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var9)) {
         var1 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$3(var1);
      } else {
         var1 = null;
      }

      val var2: Boolean = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$1(var9);
      val var3: Boolean = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var9);
      val var12: Path;
      if (var1 != null) {
         var12 = Path.Companion.get$default(Path.Companion, var1, false, 1, null);
      } else {
         var12 = null;
      }

      val var4: Long = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var9);
      val var6: FileTime = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var9);
      val var13: java.lang.Long;
      if (var6 != null) {
         var13 = this.zeroToNull(var6);
      } else {
         var13 = null;
      }

      val var7: FileTime = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$1(var9);
      val var14: java.lang.Long;
      if (var7 != null) {
         var14 = this.zeroToNull(var7);
      } else {
         var14 = null;
      }

      val var15: FileTime = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$2(var9);
      if (var15 != null) {
         var8 = this.zeroToNull(var15);
      }

      return new FileMetadata(var2, var3, var12, var4, var13, var14, var8, null, 128, null);
   }

   public override fun metadataOrNull(path: Path): FileMetadata? {
      return this.metadataOrNull(var1.toNioPath());
   }

   public override fun toString(): String {
      return "NioSystemFileSystem";
   }
}
