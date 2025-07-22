package okio.internal

import java.util.ArrayList
import okio.Path

internal class ZipEntry(canonicalPath: Path,
   isDirectory: Boolean = false,
   comment: String = "",
   crc: Long = -1L,
   compressedSize: Long = -1L,
   size: Long = -1L,
   compressionMethod: Int = -1,
   lastModifiedAtMillis: Long? = null,
   offset: Long = -1L
) {
   public final val canonicalPath: Path
   public final val children: MutableList<Path>
   public final val comment: String
   public final val compressedSize: Long
   public final val compressionMethod: Int
   public final val crc: Long
   public final val isDirectory: Boolean
   public final val lastModifiedAtMillis: Long?
   public final val offset: Long
   public final val size: Long

   init {
      this.canonicalPath = var1;
      this.isDirectory = var2;
      this.comment = var3;
      this.crc = var4;
      this.compressedSize = var6;
      this.size = var8;
      this.compressionMethod = var10;
      this.lastModifiedAtMillis = var11;
      this.offset = var12;
      this.children = new ArrayList<>();
   }
}
