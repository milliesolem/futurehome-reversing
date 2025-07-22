package okio

import java.util.ArrayList
import kotlin.jvm.functions.Function1

public abstract class ForwardingFileSystem : FileSystem {
   public final val delegate: FileSystem

   open fun ForwardingFileSystem(var1: FileSystem) {
      this.delegate = var1;
   }

   @Throws(java/io/IOException::class)
   public override fun appendingSink(file: Path, mustExist: Boolean): Sink {
      return this.delegate.appendingSink(this.onPathParameter(var1, "appendingSink", "file"), var2);
   }

   @Throws(java/io/IOException::class)
   public override fun atomicMove(source: Path, target: Path) {
      this.delegate.atomicMove(this.onPathParameter(var1, "atomicMove", "source"), this.onPathParameter(var2, "atomicMove", "target"));
   }

   @Throws(java/io/IOException::class)
   public override fun canonicalize(path: Path): Path {
      return this.onPathResult(this.delegate.canonicalize(this.onPathParameter(var1, "canonicalize", "path")), "canonicalize");
   }

   @Throws(java/io/IOException::class)
   public override fun createDirectory(dir: Path, mustCreate: Boolean) {
      this.delegate.createDirectory(this.onPathParameter(var1, "createDirectory", "dir"), var2);
   }

   @Throws(java/io/IOException::class)
   public override fun createSymlink(source: Path, target: Path) {
      this.delegate.createSymlink(this.onPathParameter(var1, "createSymlink", "source"), this.onPathParameter(var2, "createSymlink", "target"));
   }

   @Throws(java/io/IOException::class)
   public override fun delete(path: Path, mustExist: Boolean) {
      this.delegate.delete(this.onPathParameter(var1, "delete", "path"), var2);
   }

   @Throws(java/io/IOException::class)
   public override fun list(dir: Path): List<Path> {
      val var2: java.lang.Iterable = this.delegate.list(this.onPathParameter(var1, "list", "dir"));
      val var4: java.util.Collection = new ArrayList();
      val var6: java.util.Iterator = var2.iterator();

      while (var6.hasNext()) {
         var4.add(this.onPathResult(var6.next() as Path, "list"));
      }

      val var5: java.util.List = var4 as java.util.List;
      CollectionsKt.sort(var4 as java.util.List);
      return var5;
   }

   public override fun listOrNull(dir: Path): List<Path>? {
      val var4: java.util.List = this.delegate.listOrNull(this.onPathParameter(var1, "listOrNull", "dir"));
      if (var4 == null) {
         return null;
      } else {
         val var2: java.lang.Iterable = var4;
         val var5: java.util.Collection = new ArrayList();
         val var7: java.util.Iterator = var2.iterator();

         while (var7.hasNext()) {
            var5.add(this.onPathResult(var7.next() as Path, "listOrNull"));
         }

         val var6: java.util.List = var5 as java.util.List;
         CollectionsKt.sort(var5 as java.util.List);
         return var6;
      }
   }

   public override fun listRecursively(dir: Path, followSymlinks: Boolean): Sequence<Path> {
      return SequencesKt.map(this.delegate.listRecursively(this.onPathParameter(var1, "listRecursively", "dir"), var2), (new Function1<Path, Path>(this) {
         final ForwardingFileSystem this$0;

         {
            super(1);
            this.this$0 = var1;
         }

         public final Path invoke(Path var1) {
            return this.this$0.onPathResult(var1, "listRecursively");
         }
      }) as (Path?) -> Path);
   }

   @Throws(java/io/IOException::class)
   public override fun metadataOrNull(path: Path): FileMetadata? {
      val var3: FileMetadata = this.delegate.metadataOrNull(this.onPathParameter(var1, "metadataOrNull", "path"));
      if (var3 == null) {
         return null;
      } else {
         return if (var3.getSymlinkTarget() == null)
            var3
            else
            FileMetadata.copy$default(var3, false, false, this.onPathResult(var3.getSymlinkTarget(), "metadataOrNull"), null, null, null, null, null, 251, null);
      }
   }

   public open fun onPathParameter(path: Path, functionName: String, parameterName: String): Path {
      return var1;
   }

   public open fun onPathResult(path: Path, functionName: String): Path {
      return var1;
   }

   @Throws(java/io/IOException::class)
   public override fun openReadOnly(file: Path): FileHandle {
      return this.delegate.openReadOnly(this.onPathParameter(var1, "openReadOnly", "file"));
   }

   @Throws(java/io/IOException::class)
   public override fun openReadWrite(file: Path, mustCreate: Boolean, mustExist: Boolean): FileHandle {
      return this.delegate.openReadWrite(this.onPathParameter(var1, "openReadWrite", "file"), var2, var3);
   }

   @Throws(java/io/IOException::class)
   public override fun sink(file: Path, mustCreate: Boolean): Sink {
      return this.delegate.sink(this.onPathParameter(var1, "sink", "file"), var2);
   }

   @Throws(java/io/IOException::class)
   public override fun source(file: Path): Source {
      return this.delegate.source(this.onPathParameter(var1, "source", "file"));
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append((this.getClass()::class).getSimpleName());
      var1.append('(');
      var1.append(this.delegate);
      var1.append(')');
      return var1.toString();
   }
}
