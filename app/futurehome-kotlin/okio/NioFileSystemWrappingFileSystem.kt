package okio

import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InterruptedIOException
import java.io.OutputStream
import java.nio.channels.FileChannel
import java.nio.file.LinkOption
import java.nio.file.NoSuchFileException
import java.nio.file.OpenOption
import java.nio.file.StandardOpenOption
import java.util.ArrayList
import java.util.Arrays
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0
import kotlin.io.path.PathsKt

internal class NioFileSystemWrappingFileSystem(nioFileSystem: java.nio.file.FileSystem) : NioSystemFileSystem {
   private final val nioFileSystem: java.nio.file.FileSystem

   init {
      this.nioFileSystem = var1;
   }

   private fun list(dir: Path, throwOnFailure: Boolean): List<Path>? {
      val var3: java.nio.file.Path = this.resolve(var1);

      var var4: java.util.List;
      try {
         var4 = PathsKt.listDirectoryEntries$default(var3, null, 1, null);
      } catch (var5: Exception) {
         if (var2) {
            if (!PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(var3, Arrays.copyOf(new LinkOption[0], 0))) {
               val var8: StringBuilder = new StringBuilder("no such file: ");
               var8.append(var1);
               throw new FileNotFoundException(var8.toString());
            }

            val var7: StringBuilder = new StringBuilder("failed to list ");
            var7.append(var1);
            throw new IOException(var7.toString());
         }

         return null;
      }

      val var10: java.lang.Iterable = var4;
      val var9: java.util.Collection = new ArrayList();
      val var11: java.util.Iterator = var10.iterator();

      while (var11.hasNext()) {
         var9.add(var1.resolve(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var11.next()).toString()));
      }

      val var6: java.util.List = var9 as java.util.List;
      CollectionsKt.sort(var9 as java.util.List);
      return var6;
   }

   private fun Path.resolve(): java.nio.file.Path {
      val var2: java.nio.file.Path = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(this.nioFileSystem, var1.toString(), new java.lang.String[0]);
      return var2;
   }

   public override fun appendingSink(file: Path, mustExist: Boolean): Sink {
      var var3: java.util.List = CollectionsKt.createListBuilder();
      var3.add(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m());
      if (!var2) {
         var3.add(NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$3());
      }

      var3 = CollectionsKt.build(var3);
      val var4: java.nio.file.Path = this.resolve(var1);
      val var7: Array<StandardOpenOption> = var3.toArray(new StandardOpenOption[0]);
      val var8: Array<OpenOption> = Arrays.copyOf(var7, var7.length);
      val var5: OutputStream = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var4, Arrays.copyOf(var8, var8.length));
      return Okio.sink(var5);
   }

   public override fun atomicMove(source: Path, target: Path) {
      try {
         ;
      } catch (var3: NoSuchFileException) {
         throw new FileNotFoundException(NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var3));
      } catch (var4: UnsupportedOperationException) {
         throw new IOException("atomic move not supported");
      }
   }

   public override fun canonicalize(path: Path): Path {
      try {
         val var3: Path.Companion = Path.Companion;
         val var5: java.nio.file.Path = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(this.resolve(var1), new LinkOption[0]);
         return Path.Companion.get$default(var3, var5, false, 1, null);
      } catch (var4: NoSuchFileException) {
         val var2: StringBuilder = new StringBuilder("no such file: ");
         var2.append(var1);
         throw new FileNotFoundException(var2.toString());
      }
   }

   public override fun createDirectory(dir: Path, mustCreate: Boolean) {
      var var3: Boolean;
      label29: {
         val var5: FileMetadata = this.metadataOrNull(var1);
         if (var5 != null) {
            val var4: Boolean = var5.isDirectory();
            var3 = true;
            if (var4) {
               break label29;
            }
         }

         var3 = false;
      }

      if (var3 && var2) {
         val var8: StringBuilder = new StringBuilder();
         var8.append(var1);
         var8.append(" already exist.");
         throw new IOException(var8.toString());
      } else {
         try {
            ;
         } catch (var7: IOException) {
            if (!var3) {
               val var6: StringBuilder = new StringBuilder("failed to create directory: ");
               var6.append(var1);
               throw new IOException(var6.toString(), var7);
            }
         }
      }
   }

   public override fun createSymlink(source: Path, target: Path) {
   }

   public override fun delete(path: Path, mustExist: Boolean) {
      if (!Thread.interrupted()) {
         val var3: java.nio.file.Path = this.resolve(var1);

         try {
            PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var3);
         } catch (var5: NoSuchFileException) {
            if (var2) {
               val var8: StringBuilder = new StringBuilder("no such file: ");
               var8.append(var1);
               throw new FileNotFoundException(var8.toString());
            }
         } catch (var6: IOException) {
            if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(var3, Arrays.copyOf(new LinkOption[0], 0))) {
               val var7: StringBuilder = new StringBuilder("failed to delete ");
               var7.append(var1);
               throw new IOException(var7.toString());
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
      return this.metadataOrNull(this.resolve(var1));
   }

   public override fun openReadOnly(file: Path): FileHandle {
      var var4: FileChannel;
      try {
         var4 = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(
            this.resolve(var1),
            new OpenOption[]{NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m())}
         );
      } catch (var3: NoSuchFileException) {
         val var2: StringBuilder = new StringBuilder("no such file: ");
         var2.append(var1);
         throw new FileNotFoundException(var2.toString());
      }

      return new NioFileSystemFileHandle(false, var4);
   }

   public override fun openReadWrite(file: Path, mustCreate: Boolean, mustExist: Boolean): FileHandle {
      if (var2 && var3) {
         throw new IllegalArgumentException("Cannot require mustCreate and mustExist at the same time.".toString());
      } else {
         val var4: java.util.List = CollectionsKt.createListBuilder();
         var4.add(NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m());
         var4.add(NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$1());
         if (var2) {
            var4.add(NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$2());
         } else if (!var3) {
            var4.add(NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$3());
         }

         val var5: java.util.List = CollectionsKt.build(var4);

         try {
            val var8: java.nio.file.Path = this.resolve(var1);
            val var10: Array<StandardOpenOption> = var5.toArray(new StandardOpenOption[0]);
            var9 = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var8, Arrays.copyOf(var10, var10.length));
         } catch (var6: NoSuchFileException) {
            val var7: StringBuilder = new StringBuilder("no such file: ");
            var7.append(var1);
            throw new FileNotFoundException(var7.toString());
         }

         return new NioFileSystemFileHandle(true, var9);
      }
   }

   public override fun sink(file: Path, mustCreate: Boolean): Sink {
      val var3: java.util.List = CollectionsKt.createListBuilder();
      if (var2) {
         var3.add(NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m$2());
      }

      val var4: java.util.List = CollectionsKt.build(var3);

      try {
         val var7: java.nio.file.Path = this.resolve(var1);
         val var9: Array<StandardOpenOption> = var4.toArray(new StandardOpenOption[0]);
         val var10: Array<OpenOption> = Arrays.copyOf(var9, var9.length);
         val var8: OutputStream = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var7, Arrays.copyOf(var10, var10.length));
         return Okio.sink(var8);
      } catch (var5: NoSuchFileException) {
         val var6: StringBuilder = new StringBuilder("no such file: ");
         var6.append(var1);
         throw new FileNotFoundException(var6.toString());
      }
   }

   public override fun source(file: Path): Source {
      try {
         val var4: InputStream = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(this.resolve(var1), Arrays.copyOf(new OpenOption[0], 0));
         return Okio.source(var4);
      } catch (var3: NoSuchFileException) {
         val var2: StringBuilder = new StringBuilder("no such file: ");
         var2.append(var1);
         throw new FileNotFoundException(var2.toString());
      }
   }

   public override fun toString(): String {
      val var1: java.lang.String = (this.nioFileSystem.getClass()::class).getSimpleName();
      return var1;
   }
}
