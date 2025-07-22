package okio.internal

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.net.URI
import java.net.URL
import java.util.ArrayList
import java.util.Collections
import java.util.Enumeration
import java.util.LinkedHashSet
import kotlin.jvm.functions.Function0
import okio.FileHandle
import okio.FileMetadata
import okio.FileSystem
import okio.Path
import okio.Sink
import okio.Source

internal class ResourceFileSystem internal constructor(classLoader: ClassLoader, indexEagerly: Boolean) : FileSystem {
   private final val roots: List<Pair<FileSystem, Path>>
      private final get() {
         return this.roots$delegate.getValue() as MutableList<Pair<FileSystem, Path>>;
      }


   init {
      this.roots$delegate = LazyKt.lazy((new Function0<java.util.List<? extends Pair<? extends FileSystem, ? extends Path>>>(var1) {
         final ClassLoader $classLoader;

         {
            super(0);
            this.$classLoader = var1;
         }

         public final java.util.List<Pair<FileSystem, Path>> invoke() {
            return ResourceFileSystem.access$getCompanion$p().toClasspathRoots(this.$classLoader);
         }
      }) as Function0);
      if (var2) {
         this.getRoots().size();
      }
   }

   private fun canonicalizeInternal(path: Path): Path {
      return ROOT.resolve(var1, true);
   }

   private fun Path.toRelativePath(): String {
      return this.canonicalizeInternal(var1).relativeTo(ROOT).toString();
   }

   public override fun appendingSink(file: Path, mustExist: Boolean): Sink {
      val var3: StringBuilder = new StringBuilder();
      var3.append(this);
      var3.append(" is read-only");
      throw new IOException(var3.toString());
   }

   public override fun atomicMove(source: Path, target: Path) {
      val var3: StringBuilder = new StringBuilder();
      var3.append(this);
      var3.append(" is read-only");
      throw new IOException(var3.toString());
   }

   public override fun canonicalize(path: Path): Path {
      return this.canonicalizeInternal(var1);
   }

   public override fun createDirectory(dir: Path, mustCreate: Boolean) {
      val var3: StringBuilder = new StringBuilder();
      var3.append(this);
      var3.append(" is read-only");
      throw new IOException(var3.toString());
   }

   public override fun createSymlink(source: Path, target: Path) {
      val var3: StringBuilder = new StringBuilder();
      var3.append(this);
      var3.append(" is read-only");
      throw new IOException(var3.toString());
   }

   public override fun delete(path: Path, mustExist: Boolean) {
      val var3: StringBuilder = new StringBuilder();
      var3.append(this);
      var3.append(" is read-only");
      throw new IOException(var3.toString());
   }

   public override fun list(dir: Path): List<Path> {
      val var3: java.lang.String = this.toRelativePath(var1);
      val var5: java.util.Set = new LinkedHashSet();
      val var4: java.util.Iterator = this.getRoots().iterator();
      var var2: Boolean = false;

      label62:
      while (var4.hasNext()) {
         val var6: Pair = var4.next() as Pair;
         val var8: FileSystem = var6.component1() as FileSystem;
         val var7: Path = var6.component2() as Path;

         var var11: java.util.Iterator;
         try {
            var18 = var5;
            val var9: java.lang.Iterable = var8.list(var7.resolve(var3));
            var20 = new ArrayList();
            var11 = var9.iterator();
         } catch (var14: IOException) {
            continue;
         }

         while (true) {
            try {
               if (!var11.hasNext()) {
                  break;
               }

               val var23: Any = var11.next();
               if (ResourceFileSystem.Companion.access$keepPath(Companion, var23 as Path)) {
                  var20.add(var23);
               }
            } catch (var16: IOException) {
               continue label62;
            }
         }

         var var26: java.util.Iterator;
         try {
            var22 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var20 as java.util.List, 10));
            var26 = (var20 as java.util.List).iterator();
         } catch (var13: IOException) {
            continue;
         }

         while (true) {
            try {
               if (!var26.hasNext()) {
                  break;
               }

               var22.add(Companion.removeBase(var26.next() as Path, var7));
            } catch (var15: IOException) {
               continue label62;
            }
         }

         try {
            CollectionsKt.addAll(var18, var22 as java.util.List);
         } catch (var12: IOException) {
            continue;
         }

         var2 = true;
      }

      if (var2) {
         return CollectionsKt.toList(var5);
      } else {
         val var17: StringBuilder = new StringBuilder("file not found: ");
         var17.append(var1);
         throw new FileNotFoundException(var17.toString());
      }
   }

   public override fun listOrNull(dir: Path): List<Path>? {
      val var6: java.lang.String = this.toRelativePath(var1);
      val var5: java.util.Set = new LinkedHashSet();
      val var7: java.util.Iterator = this.getRoots().iterator();
      var var2: Boolean = false;

      while (true) {
         val var3: Boolean = var7.hasNext();
         var var11: java.util.List = null;
         if (!var3) {
            var var14: java.util.List = null;
            if (var2) {
               var14 = CollectionsKt.toList(var5);
            }

            return var14;
         }

         val var15: Pair = var7.next() as Pair;
         val var8: FileSystem = var15.component1() as FileSystem;
         val var16: Path = var15.component2() as Path;
         val var17: java.util.List = var8.listOrNull(var16.resolve(var6));
         if (var17 != null) {
            val var18: java.lang.Iterable = var17;
            val var12: java.util.Collection = new ArrayList();

            for (Object var19 : var18) {
               if (ResourceFileSystem.Companion.access$keepPath(Companion, var19 as Path)) {
                  var12.add(var19);
               }
            }

            val var20: java.lang.Iterable = var12 as java.util.List;
            val var13: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var12 as java.util.List, 10));

            for (Path var21 : var20) {
               var13.add(Companion.removeBase(var21, var16));
            }

            var11 = var13 as java.util.List;
         }

         if (var11 != null) {
            CollectionsKt.addAll(var5, var11);
            var2 = true;
         }
      }
   }

   public override fun metadataOrNull(path: Path): FileMetadata? {
      if (!ResourceFileSystem.Companion.access$keepPath(Companion, var1)) {
         return null;
      } else {
         val var4: java.lang.String = this.toRelativePath(var1);

         for (Pair var3 : this.getRoots()) {
            val var5: FileMetadata = (var3.component1() as FileSystem).metadataOrNull((var3.component2() as Path).resolve(var4));
            if (var5 != null) {
               return var5;
            }
         }

         return null;
      }
   }

   public override fun openReadOnly(file: Path): FileHandle {
      if (!ResourceFileSystem.Companion.access$keepPath(Companion, var1)) {
         val var8: StringBuilder = new StringBuilder("file not found: ");
         var8.append(var1);
         throw new FileNotFoundException(var8.toString());
      } else {
         val var2: java.lang.String = this.toRelativePath(var1);

         for (Pair var5 : this.getRoots()) {
            val var4: FileSystem = var5.component1() as FileSystem;
            val var9: Path = var5.component2() as Path;

            try {
               return var4.openReadOnly(var9.resolve(var2));
            } catch (var6: FileNotFoundException) {
            }
         }

         val var7: StringBuilder = new StringBuilder("file not found: ");
         var7.append(var1);
         throw new FileNotFoundException(var7.toString());
      }
   }

   public override fun openReadWrite(file: Path, mustCreate: Boolean, mustExist: Boolean): FileHandle {
      throw new IOException("resources are not writable");
   }

   public override fun sink(file: Path, mustCreate: Boolean): Sink {
      val var3: StringBuilder = new StringBuilder();
      var3.append(this);
      var3.append(" is read-only");
      throw new IOException(var3.toString());
   }

   public override fun source(file: Path): Source {
      if (!ResourceFileSystem.Companion.access$keepPath(Companion, var1)) {
         val var8: StringBuilder = new StringBuilder("file not found: ");
         var8.append(var1);
         throw new FileNotFoundException(var8.toString());
      } else {
         val var3: java.lang.String = this.toRelativePath(var1);

         for (Pair var5 : this.getRoots()) {
            val var4: FileSystem = var5.component1() as FileSystem;
            val var9: Path = var5.component2() as Path;

            try {
               return var4.source(var9.resolve(var3));
            } catch (var6: FileNotFoundException) {
            }
         }

         val var7: StringBuilder = new StringBuilder("file not found: ");
         var7.append(var1);
         throw new FileNotFoundException(var7.toString());
      }
   }

   private companion object {
      public final val ROOT: Path

      private fun keepPath(path: Path): Boolean {
         return StringsKt.endsWith(var1.name(), ".class", true) xor true;
      }

      public fun Path.removeBase(base: Path): Path {
         return this.getROOT().resolve(StringsKt.replace$default(StringsKt.removePrefix(var1.toString(), var2.toString()), '\\', '/', false, 4, null));
      }

      public fun ClassLoader.toClasspathRoots(): List<Pair<FileSystem, Path>> {
         val var2: Enumeration = var1.getResources("");
         val var9: ArrayList = Collections.list(var2);
         var var3: java.lang.Iterable = var9;
         val var10: java.util.Collection = new ArrayList();

         for (URL var4 : var3) {
            val var5: ResourceFileSystem.Companion = ResourceFileSystem.access$getCompanion$p();
            val var15: Pair = var5.toFileRoot(var4);
            if (var15 != null) {
               var10.add(var15);
            }
         }

         val var11: java.util.Collection = var10 as java.util.List;
         val var6: Enumeration = var1.getResources("META-INF/MANIFEST.MF");
         val var7: ArrayList = Collections.list(var6);
         var3 = var7;
         val var8: java.util.Collection = new ArrayList();

         for (URL var18 : var3) {
            val var16: ResourceFileSystem.Companion = ResourceFileSystem.access$getCompanion$p();
            val var17: Pair = var16.toJarRoot(var18);
            if (var17 != null) {
               var8.add(var17);
            }
         }

         return CollectionsKt.plus(var11, var8);
      }

      public fun URL.toFileRoot(): Pair<FileSystem, Path>? {
         return if (!(var1.getProtocol() == "file"))
            null
            else
            TuplesKt.to(FileSystem.SYSTEM, Path.Companion.get$default(Path.Companion, new File(var1.toURI()), false, 1, null));
      }

      public fun URL.toJarRoot(): Pair<FileSystem, Path>? {
         var var3: java.lang.String = var1.toString();
         if (!StringsKt.startsWith$default(var3, "jar:file:", false, 2, null)) {
            return null;
         } else {
            val var2: Int = StringsKt.lastIndexOf$default(var3, "!", 0, false, 6, null);
            if (var2 == -1) {
               return null;
            } else {
               val var4: Path.Companion = Path.Companion;
               var3 = var3.substring(4, var2);
               return TuplesKt.to(
                  ZipFilesKt.openZip(
                     Path.Companion.get$default(var4, new File(URI.create(var3)), false, 1, null), FileSystem.SYSTEM, <unrepresentable>.INSTANCE
                  ),
                  this.getROOT()
               );
            }
         }
      }
   }
}
