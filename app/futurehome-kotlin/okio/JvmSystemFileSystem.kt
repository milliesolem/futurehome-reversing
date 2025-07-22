package okio

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InterruptedIOException
import java.io.RandomAccessFile
import java.util.ArrayList

internal open class JvmSystemFileSystem : FileSystem {
   private fun list(dir: Path, throwOnFailure: Boolean): List<Path>? {
      val var6: File = var1.toFile();
      val var5: Array<java.lang.String> = var6.list();
      if (var5 == null) {
         if (var2) {
            if (!var6.exists()) {
               val var10: StringBuilder = new StringBuilder("no such file: ");
               var10.append(var1);
               throw new FileNotFoundException(var10.toString());
            } else {
               val var9: StringBuilder = new StringBuilder("failed to list ");
               var9.append(var1);
               throw new IOException(var9.toString());
            }
         } else {
            return null;
         }
      } else {
         val var7: java.util.Collection = new ArrayList();
         val var4: Int = var5.length;

         for (int var3 = 0; var3 < var4; var3++) {
            val var11: java.lang.String = var5[var3];
            var7.add(var1.resolve(var11));
         }

         val var8: java.util.List = var7 as java.util.List;
         CollectionsKt.sort(var7 as java.util.List);
         return var8;
      }
   }

   private fun Path.requireCreate() {
      if (this.exists(var1)) {
         val var2: StringBuilder = new StringBuilder();
         var2.append(var1);
         var2.append(" already exists.");
         throw new IOException(var2.toString());
      }
   }

   private fun Path.requireExist() {
      if (!this.exists(var1)) {
         val var2: StringBuilder = new StringBuilder();
         var2.append(var1);
         var2.append(" doesn't exist.");
         throw new IOException(var2.toString());
      }
   }

   public override fun appendingSink(file: Path, mustExist: Boolean): Sink {
      if (var2) {
         this.requireExist(var1);
      }

      return Okio.sink(var1.toFile(), true);
   }

   public override fun atomicMove(source: Path, target: Path) {
      if (!var1.toFile().renameTo(var2.toFile())) {
         val var3: StringBuilder = new StringBuilder("failed to move ");
         var3.append(var1);
         var3.append(" to ");
         var3.append(var2);
         throw new IOException(var3.toString());
      }
   }

   public override fun canonicalize(path: Path): Path {
      val var2: File = var1.toFile().getCanonicalFile();
      if (var2.exists()) {
         val var3: Path.Companion = Path.Companion;
         return Path.Companion.get$default(var3, var2, false, 1, null);
      } else {
         throw new FileNotFoundException("no such file");
      }
   }

   public override fun createDirectory(dir: Path, mustCreate: Boolean) {
      if (!var1.toFile().mkdir()) {
         val var3: FileMetadata = this.metadataOrNull(var1);
         if (var3 == null || !var3.isDirectory()) {
            val var5: StringBuilder = new StringBuilder("failed to create directory: ");
            var5.append(var1);
            throw new IOException(var5.toString());
         } else if (var2) {
            val var4: StringBuilder = new StringBuilder();
            var4.append(var1);
            var4.append(" already exist.");
            throw new IOException(var4.toString());
         }
      }
   }

   public override fun createSymlink(source: Path, target: Path) {
      throw new IOException("unsupported");
   }

   public override fun delete(path: Path, mustExist: Boolean) {
      if (!Thread.interrupted()) {
         val var3: File = var1.toFile();
         if (!var3.delete()) {
            if (var3.exists()) {
               val var5: StringBuilder = new StringBuilder("failed to delete ");
               var5.append(var1);
               throw new IOException(var5.toString());
            }

            if (var2) {
               val var4: StringBuilder = new StringBuilder("no such file: ");
               var4.append(var1);
               throw new FileNotFoundException(var4.toString());
            }
         }
      } else {
         throw new InterruptedIOException("interrupted");
      }
   }

   public override fun list(dir: Path): List<Path> {
      val var2: java.util.List = this.list(var1, true);
      return var2;
   }

   public override fun listOrNull(dir: Path): List<Path>? {
      return this.list(var1, false);
   }

   public override fun metadataOrNull(path: Path): FileMetadata? {
      val var8: File = var1.toFile();
      val var3: Boolean = var8.isFile();
      val var2: Boolean = var8.isDirectory();
      val var6: Long = var8.lastModified();
      val var4: Long = var8.length();
      return if (!var3 && !var2 && var6 == 0L && var4 == 0L && !var8.exists())
         null
         else
         new FileMetadata(var3, var2, null, var4, null, var6, null, null, 128, null);
   }

   public override fun openReadOnly(file: Path): FileHandle {
      return new JvmFileHandle(false, new RandomAccessFile(var1.toFile(), "r"));
   }

   public override fun openReadWrite(file: Path, mustCreate: Boolean, mustExist: Boolean): FileHandle {
      if (var2 && var3) {
         throw new IllegalArgumentException("Cannot require mustCreate and mustExist at the same time.".toString());
      } else {
         if (var2) {
            this.requireCreate(var1);
         }

         if (var3) {
            this.requireExist(var1);
         }

         return new JvmFileHandle(true, new RandomAccessFile(var1.toFile(), "rw"));
      }
   }

   public override fun sink(file: Path, mustCreate: Boolean): Sink {
      if (var2) {
         this.requireCreate(var1);
      }

      return Okio.sink$default(var1.toFile(), false, 1, null);
   }

   public override fun source(file: Path): Source {
      return Okio.source(var1.toFile());
   }

   public override fun toString(): String {
      return "JvmSystemFileSystem";
   }
}
