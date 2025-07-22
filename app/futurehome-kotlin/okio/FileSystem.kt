package okio

import java.io.Closeable
import okio.internal.ResourceFileSystem
import okio.internal._FileSystem

public abstract class FileSystem {
   @JvmStatic
   fun {
      var var0: JvmSystemFileSystem;
      try {
         Class.forName("java.nio.file.Files");
         var0 = new NioSystemFileSystem();
      } catch (var2: ClassNotFoundException) {
         var0 = new JvmSystemFileSystem();
      }

      SYSTEM = var0;
      val var1: Path.Companion = Path.Companion;
      val var4: java.lang.String = System.getProperty("java.io.tmpdir");
      SYSTEM_TEMPORARY_DIRECTORY = Path.Companion.get$default(var1, var4, false, 1, null);
      val var5: ClassLoader = ResourceFileSystem.class.getClassLoader();
      RESOURCES = new ResourceFileSystem(var5, false);
   }

   @Throws(java/io/IOException::class)
   public inline fun <T> read(file: Path, readerAction: (BufferedSource) -> T): T {
      val var5: Closeable = Okio.buffer(this.source(var1));

      label35: {
         var var3: Any;
         try {
            var3 = var2.invoke(var5 as BufferedSource);
         } catch (var8: java.lang.Throwable) {
            var18 = var8;
            if (var5 != null) {
               label25:
               try {
                  var5.close();
               } catch (var6: java.lang.Throwable) {
                  ExceptionsKt.addSuppressed(var8, var6);
                  break label25;
               }
            }

            var19 = null;
            break label35;
         }

         var18 = null;
         var19 = var3;
         label32:
         if (var5 != null) {
            try {
               var5.close();
            } catch (var7: java.lang.Throwable) {
               break label32;
            }

            var18 = null;
            var19 = var3;
         }
      }

      if (var18 == null) {
         return (T)var19;
      } else {
         throw var18;
      }
   }

   @Throws(java/io/IOException::class)
   public inline fun <T> write(file: Path, mustCreate: Boolean = ..., writerAction: (BufferedSink) -> T): T {
      val var6: Closeable = Okio.buffer(this.sink(var1, var2));

      label35: {
         var var4: Any;
         try {
            var4 = var3.invoke(var6 as BufferedSink);
         } catch (var9: java.lang.Throwable) {
            var19 = var9;
            if (var6 != null) {
               label25:
               try {
                  var6.close();
               } catch (var7: java.lang.Throwable) {
                  ExceptionsKt.addSuppressed(var9, var7);
                  break label25;
               }
            }

            var20 = null;
            break label35;
         }

         var19 = null;
         var20 = var4;
         label32:
         if (var6 != null) {
            try {
               var6.close();
            } catch (var8: java.lang.Throwable) {
               break label32;
            }

            var19 = null;
            var20 = var4;
         }
      }

      if (var19 == null) {
         return (T)var20;
      } else {
         throw var19;
      }
   }

   @Throws(java/io/IOException::class)
   public fun appendingSink(file: Path): Sink {
      return this.appendingSink(var1, false);
   }

   @Throws(java/io/IOException::class)
   public abstract fun appendingSink(file: Path, mustExist: Boolean = false): Sink {
   }

   @Throws(java/io/IOException::class)
   public abstract fun atomicMove(source: Path, target: Path) {
   }

   @Throws(java/io/IOException::class)
   public abstract fun canonicalize(path: Path): Path {
   }

   @Throws(java/io/IOException::class)
   public open fun copy(source: Path, target: Path) {
      _FileSystem.commonCopy(this, var1, var2);
   }

   @Throws(java/io/IOException::class)
   public fun createDirectories(dir: Path) {
      this.createDirectories(var1, false);
   }

   @Throws(java/io/IOException::class)
   public fun createDirectories(dir: Path, mustCreate: Boolean = false) {
      _FileSystem.commonCreateDirectories(this, var1, var2);
   }

   @Throws(java/io/IOException::class)
   public fun createDirectory(dir: Path) {
      this.createDirectory(var1, false);
   }

   @Throws(java/io/IOException::class)
   public abstract fun createDirectory(dir: Path, mustCreate: Boolean = false) {
   }

   @Throws(java/io/IOException::class)
   public abstract fun createSymlink(source: Path, target: Path) {
   }

   @Throws(java/io/IOException::class)
   public fun delete(path: Path) {
      this.delete(var1, false);
   }

   @Throws(java/io/IOException::class)
   public abstract fun delete(path: Path, mustExist: Boolean = false) {
   }

   @Throws(java/io/IOException::class)
   public fun deleteRecursively(fileOrDirectory: Path) {
      this.deleteRecursively(var1, false);
   }

   @Throws(java/io/IOException::class)
   public open fun deleteRecursively(fileOrDirectory: Path, mustExist: Boolean = false) {
      _FileSystem.commonDeleteRecursively(this, var1, var2);
   }

   @Throws(java/io/IOException::class)
   public fun exists(path: Path): Boolean {
      return _FileSystem.commonExists(this, var1);
   }

   @Throws(java/io/IOException::class)
   public abstract fun list(dir: Path): List<Path> {
   }

   public abstract fun listOrNull(dir: Path): List<Path>? {
   }

   public fun listRecursively(dir: Path): Sequence<Path> {
      return this.listRecursively(var1, false);
   }

   public open fun listRecursively(dir: Path, followSymlinks: Boolean = false): Sequence<Path> {
      return _FileSystem.commonListRecursively(this, var1, var2);
   }

   @Throws(java/io/IOException::class)
   public fun metadata(path: Path): FileMetadata {
      return _FileSystem.commonMetadata(this, var1);
   }

   @Throws(java/io/IOException::class)
   public abstract fun metadataOrNull(path: Path): FileMetadata? {
   }

   @Throws(java/io/IOException::class)
   public abstract fun openReadOnly(file: Path): FileHandle {
   }

   @Throws(java/io/IOException::class)
   public fun openReadWrite(file: Path): FileHandle {
      return this.openReadWrite(var1, false, false);
   }

   @Throws(java/io/IOException::class)
   public abstract fun openReadWrite(file: Path, mustCreate: Boolean = false, mustExist: Boolean = false): FileHandle {
   }

   @Throws(java/io/IOException::class)
   public fun sink(file: Path): Sink {
      return this.sink(var1, false);
   }

   @Throws(java/io/IOException::class)
   public abstract fun sink(file: Path, mustCreate: Boolean = false): Sink {
   }

   @Throws(java/io/IOException::class)
   public abstract fun source(file: Path): Source {
   }

   public companion object {
      public final val RESOURCES: FileSystem
      public final val SYSTEM: FileSystem
      public final val SYSTEM_TEMPORARY_DIRECTORY: Path

      public fun java.nio.file.FileSystem.asOkioFileSystem(): FileSystem {
         return new NioFileSystemWrappingFileSystem(var1);
      }
   }
}
