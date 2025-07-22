package okio

import java.io.Closeable
import java.io.FileNotFoundException
import java.io.IOException
import java.util.zip.Inflater
import okio.internal.FixedLengthSource
import okio.internal.ZipEntry
import okio.internal.ZipFilesKt

internal class ZipFileSystem internal constructor(zipPath: Path, fileSystem: FileSystem, entries: Map<Path, ZipEntry>, comment: String?) : FileSystem {
   private final val comment: String?
   private final val entries: Map<Path, ZipEntry>
   private final val fileSystem: FileSystem
   private final val zipPath: Path

   init {
      this.zipPath = var1;
      this.fileSystem = var2;
      this.entries = var3;
      this.comment = var4;
   }

   private fun canonicalizeInternal(path: Path): Path {
      return ROOT.resolve(var1, true);
   }

   private fun list(dir: Path, throwOnFailure: Boolean): List<Path>? {
      val var4: ZipEntry = this.entries.get(this.canonicalizeInternal(var1));
      if (var4 == null) {
         if (!var2) {
            return null;
         } else {
            val var5: StringBuilder = new StringBuilder("not a directory: ");
            var5.append(var1);
            throw new IOException(var5.toString());
         }
      } else {
         return CollectionsKt.toList(var4.getChildren());
      }
   }

   public override fun appendingSink(file: Path, mustExist: Boolean): Sink {
      throw new IOException("zip file systems are read-only");
   }

   public override fun atomicMove(source: Path, target: Path) {
      throw new IOException("zip file systems are read-only");
   }

   public override fun canonicalize(path: Path): Path {
      val var2: Path = this.canonicalizeInternal(var1);
      if (this.entries.containsKey(var2)) {
         return var2;
      } else {
         throw new FileNotFoundException(java.lang.String.valueOf(var1));
      }
   }

   public override fun createDirectory(dir: Path, mustCreate: Boolean) {
      throw new IOException("zip file systems are read-only");
   }

   public override fun createSymlink(source: Path, target: Path) {
      throw new IOException("zip file systems are read-only");
   }

   public override fun delete(path: Path, mustExist: Boolean) {
      throw new IOException("zip file systems are read-only");
   }

   public override fun list(dir: Path): List<Path> {
      val var2: java.util.List = this.list(var1, true);
      return var2;
   }

   public override fun listOrNull(dir: Path): List<Path>? {
      return this.list(var1, false);
   }

   public override fun metadataOrNull(path: Path): FileMetadata? {
      val var4: ZipEntry = this.entries.get(this.canonicalizeInternal(var1));
      if (var4 == null) {
         return null;
      } else {
         val var3: Boolean = var4.isDirectory();
         val var2: Boolean = var4.isDirectory();
         val var22: java.lang.Long;
         if (var4.isDirectory()) {
            var22 = null;
         } else {
            var22 = var4.getSize();
         }

         val var7: FileMetadata = new FileMetadata(var3 xor true, var2, null, var22, null, var4.getLastModifiedAtMillis(), null, null, 128, null);
         if (var4.getOffset() == -1L) {
            return var7;
         } else {
            val var8: Closeable = this.fileSystem.openReadOnly(this.zipPath);

            label43: {
               var var6: BufferedSource;
               try {
                  var6 = Okio.buffer((var8 as FileHandle).source(var4.getOffset()));
               } catch (var11: java.lang.Throwable) {
                  var23 = var11;
                  if (var8 != null) {
                     label33:
                     try {
                        var8.close();
                     } catch (var9: java.lang.Throwable) {
                        ExceptionsKt.addSuppressed(var11, var9);
                        break label33;
                     }
                  }

                  var24 = null;
                  break label43;
               }

               var23 = null;
               var24 = var6;
               label40:
               if (var8 != null) {
                  try {
                     var8.close();
                  } catch (var10: java.lang.Throwable) {
                     break label40;
                  }

                  var23 = null;
                  var24 = var6;
               }
            }

            if (var23 == null) {
               return ZipFilesKt.readLocalHeader(var24, var7);
            } else {
               throw var23;
            }
         }
      }
   }

   public override fun openReadOnly(file: Path): FileHandle {
      throw new UnsupportedOperationException("not implemented yet!");
   }

   public override fun openReadWrite(file: Path, mustCreate: Boolean, mustExist: Boolean): FileHandle {
      throw new IOException("zip entries are not writable");
   }

   public override fun sink(file: Path, mustCreate: Boolean): Sink {
      throw new IOException("zip file systems are read-only");
   }

   @Throws(java/io/IOException::class)
   public override fun source(file: Path): Source {
      val var5: ZipEntry = this.entries.get(this.canonicalizeInternal(var1));
      if (var5 != null) {
         val var6: Closeable = this.fileSystem.openReadOnly(this.zipPath);

         var var22: BufferedSource;
         label42: {
            var var3: BufferedSource;
            try {
               var3 = Okio.buffer((var6 as FileHandle).source(var5.getOffset()));
            } catch (var9: java.lang.Throwable) {
               var19 = var9;
               if (var6 != null) {
                  label32:
                  try {
                     var6.close();
                  } catch (var7: java.lang.Throwable) {
                     ExceptionsKt.addSuppressed(var9, var7);
                     break label32;
                  }
               }

               var22 = null;
               break label42;
            }

            var19 = null;
            var22 = var3;
            label39:
            if (var6 != null) {
               try {
                  var6.close();
               } catch (var8: java.lang.Throwable) {
                  break label39;
               }

               var19 = null;
               var22 = var3;
            }
         }

         if (var19 == null) {
            ZipFilesKt.skipLocalHeader(var22);
            val var20: Source;
            if (var5.getCompressionMethod() == 0) {
               var20 = new FixedLengthSource(var22, var5.getSize(), true);
            } else {
               var20 = new FixedLengthSource(
                  new InflaterSource(new FixedLengthSource(var22, var5.getCompressedSize(), true), new Inflater(true)), var5.getSize(), false
               );
            }

            return var20;
         } else {
            throw var19;
         }
      } else {
         val var21: StringBuilder = new StringBuilder("no such file: ");
         var21.append(var1);
         throw new FileNotFoundException(var21.toString());
      }
   }

   private companion object {
      public final val ROOT: Path
   }
}
