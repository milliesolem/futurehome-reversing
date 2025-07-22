package kotlin.io.path

import java.io.IOException
import java.nio.file.CopyOption
import java.nio.file.FileSystemException
import java.nio.file.FileVisitResult
import java.nio.file.LinkOption
import java.nio.file.NoSuchFileException
import java.nio.file.Path
import java.nio.file.SecureDirectoryStream
import java.nio.file.attribute.BasicFileAttributes
import java.util.ArrayList
import java.util.Arrays
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.Intrinsics
import kotlin.jvm.internal.SpreadBuilder

internal class PathsKt__PathRecursiveFunctionsKt : PathsKt__PathReadWriteKt {
   open fun PathsKt__PathRecursiveFunctionsKt() {
   }

   @JvmStatic
   internal fun Path.checkFileName() {
      val var2: java.lang.String = PathsKt.getName(var0);
      val var1: Int = var2.hashCode();
      if (if (var1 != 46)
         (
            if (var1 != 1518)
               (
                  if (var1 != 45679)
                     (if (var1 != 45724) (if (var1 != 1472) var1 == 1473 && var2.equals("./") else var2.equals("..")) else var2.equals("..\\"))
                     else
                     var2.equals("../")
               )
               else
               var2.equals(".\\")
         )
         else
         var2.equals(".")) {
         throw new IllegalFileNameException(var0);
      }
   }

   @JvmStatic
   private fun Path.checkNotSameAs(parent: Path) {
      if (!PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var0) && PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var0, var1)) {
         PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1();
         throw PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var0.toString());
      }
   }

   @JvmStatic
   private inline fun collectIfThrows(collector: ExceptionsCollector, function: () -> Unit) {
      try {
         var1.invoke();
      } catch (var2: Exception) {
         var0.collect(var2);
      }
   }

   @JvmStatic
   public fun Path.copyToRecursively(
      target: Path,
      onError: (Path, Path, Exception) -> OnErrorResult = <unrepresentable>.INSTANCE as Function3,
      followLinks: Boolean,
      copyAction: (CopyActionContext, Path, Path) -> CopyActionResult = new PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticLambda26(var3)
   ): Path {
      var var8: Array<LinkOption> = LinkFollowing.INSTANCE.toLinkOptions(var3);
      var8 = Arrays.copyOf(var8, var8.length);
      if (!PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(var0, Arrays.copyOf(var8, var8.length))) {
         PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$2();
         throw PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var0.toString(), var1.toString(), "The source file doesn't exist.");
      } else {
         if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(var0, Arrays.copyOf(new LinkOption[0], 0))
            && (var3 || !PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var0))) {
            val var5: Boolean;
            if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(var1, Arrays.copyOf(new LinkOption[0], 0))
               && !PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var1)) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (!var5 || !PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var0, var1)) {
               var var6: Boolean;
               if (!(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var0) == PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var1))) {
                  var6 = false;
               } else if (var5) {
                  var6 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(
                     PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var1, new LinkOption[0]),
                     PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var0, new LinkOption[0])
                  );
               } else {
                  val var10: Path = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(var1);
                  var6 = false;
                  if (var10 != null) {
                     var6 = false;
                     if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(var10, Arrays.copyOf(new LinkOption[0], 0))) {
                        var6 = false;
                        if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(
                           PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var10, new LinkOption[0]),
                           PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var0, new LinkOption[0])
                        )) {
                           var6 = true;
                        }
                     }
                  }
               }

               if (var6) {
                  PathTreeWalk$$ExternalSyntheticApiModelOutline0.m();
                  throw PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(
                     var0.toString(), var1.toString(), "Recursively copying a directory into its subdirectory is prohibited."
                  );
               }
            }
         }

         PathsKt.visitFileTree$default(
            var0,
            0,
            var3,
            new PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticLambda28(
               new ArrayList(), var4, var0, var1, PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$2(var1), var2
            ),
            1,
            null
         );
         return var1;
      }
   }

   @JvmStatic
   public fun Path.copyToRecursively(
      target: Path,
      onError: (Path, Path, Exception) -> OnErrorResult = <unrepresentable>.INSTANCE as Function3,
      followLinks: Boolean,
      overwrite: Boolean
   ): Path {
      if (var4) {
         var0 = PathsKt.copyToRecursively(var0, var1, var2, var3, new PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticLambda27(var3));
      } else {
         var0 = PathsKt.copyToRecursively$default(var0, var1, var2, var3, null, 8, null);
      }

      return var0;
   }

   @JvmStatic
   fun `copyToRecursively$copy$PathsKt__PathRecursiveFunctionsKt`(
      var0: ArrayList<Path>,
      var1: (CopyActionContext?, Path?, Path?) -> CopyActionResult,
      var2: Path,
      var3: Path,
      var4: Path,
      var5: (Path?, Path?, Exception?) -> OnErrorResult,
      var6: Path,
      var7: BasicFileAttributes
   ): FileVisitResult {
      try {
         if (!var0.isEmpty()) {
            PathsKt.checkFileName(var6);
            val var10: Any = CollectionsKt.last(var0);
            checkNotSameAs$PathsKt__PathRecursiveFunctionsKt(var6, PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var10));
         }

         var9 = toFileVisitResult$PathsKt__PathRecursiveFunctionsKt(
            var1.invoke(DefaultCopyActionContext.INSTANCE, var6, copyToRecursively$destination$PathsKt__PathRecursiveFunctionsKt(var2, var3, var4, var6)) as CopyActionResult
         );
      } catch (var8: Exception) {
         var9 = copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt(var5, var2, var3, var4, var6, var8);
      }

      return var9;
   }

   @JvmStatic
   fun `copyToRecursively$destination$PathsKt__PathRecursiveFunctionsKt`(var0: Path, var1: Path, var2: Path, var3: Path): Path {
      var0 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var1, PathsKt.relativeTo(var3, var0).toString());
      if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$2(var0), var2)) {
         return var0;
      } else {
         throw new IllegalFileNameException(
            var3,
            var0,
            "Copying files to outside the specified target directory is prohibited. The directory being recursively copied might contain an entry with an illegal name."
         );
      }
   }

   @JvmStatic
   fun `copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt`(
      var0: (Path?, Path?, Exception?) -> OnErrorResult, var1: Path, var2: Path, var3: Path, var4: Path, var5: Exception
   ): FileVisitResult {
      return toFileVisitResult$PathsKt__PathRecursiveFunctionsKt(
         var0.invoke(var4, copyToRecursively$destination$PathsKt__PathRecursiveFunctionsKt(var1, var2, var3, var4), var5) as OnErrorResult
      );
   }

   @JvmStatic
   fun `copyToRecursively$lambda$0$PathsKt__PathRecursiveFunctionsKt`(var0: Boolean, var1: CopyActionContext, var2: Path, var3: Path): CopyActionResult {
      val var6: Array<LinkOption> = LinkFollowing.INSTANCE.toLinkOptions(var0);
      var0 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var3, Arrays.copyOf(new LinkOption[]{PathTreeWalk$$ExternalSyntheticApiModelOutline0.m()}, 1));
      val var4: Array<LinkOption> = Arrays.copyOf(var6, var6.length);
      if (!PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var2, Arrays.copyOf(var4, var4.length)) || !var0) {
         if (var0) {
            PathsKt.deleteRecursively(var3);
         }

         val var8: SpreadBuilder = new SpreadBuilder(2);
         var8.addSpread(var6);
         var8.add(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m());
         val var7: Array<CopyOption> = var8.toArray(new CopyOption[var8.size()]) as Array<CopyOption>;
      }

      return CopyActionResult.CONTINUE;
   }

   @JvmStatic
   fun `copyToRecursively$lambda$1$PathsKt__PathRecursiveFunctionsKt`(var0: Boolean, var1: CopyActionContext, var2: Path, var3: Path): CopyActionResult {
      return var1.copyToIgnoringExistingDirectory(var2, var3, var0);
   }

   @JvmStatic
   fun `copyToRecursively$lambda$6$PathsKt__PathRecursiveFunctionsKt`(
      var0: ArrayList, var1: Function3, var2: Path, var3: Path, var4: Path, var5: Function3, var6: FileVisitorBuilder
   ): Unit {
      var6.onPreVisitDirectory(new PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticLambda29(var0, var1, var2, var3, var4, var5));
      var6.onVisitFile(
         (
            new Function2<Path, BasicFileAttributes, FileVisitResult>(var0, var1, var2, var3, var4, var5) {
               final Function3<CopyActionContext, Path, Path, CopyActionResult> $copyAction;
               final Path $normalizedTarget;
               final Function3<Path, Path, Exception, OnErrorResult> $onError;
               final ArrayList<Path> $stack;
               final Path $target;
               final Path $this_copyToRecursively;

               {
                  super(
                     2,
                     Intrinsics.Kotlin::class.java,
                     "copy",
                     "copyToRecursively$copy$PathsKt__PathRecursiveFunctionsKt(Ljava/util/ArrayList;Lkotlin/jvm/functions/Function3;Ljava/nio/file/Path;Ljava/nio/file/Path;Ljava/nio/file/Path;Lkotlin/jvm/functions/Function3;Ljava/nio/file/Path;Ljava/nio/file/attribute/BasicFileAttributes;)Ljava/nio/file/FileVisitResult;",
                     0
                  );
                  this.$stack = var1;
                  this.$copyAction = var2;
                  this.$this_copyToRecursively = var3;
                  this.$target = var4;
                  this.$normalizedTarget = var5;
                  this.$onError = var6;
               }

               public final FileVisitResult invoke(Path var1, BasicFileAttributes var2) {
                  return PathsKt__PathRecursiveFunctionsKt.access$copyToRecursively$copy(
                     this.$stack, this.$copyAction, this.$this_copyToRecursively, this.$target, this.$normalizedTarget, this.$onError, var1, var2
                  );
               }
            }
         ) as (Path?, BasicFileAttributes?) -> FileVisitResult
      );
      var6.onVisitFileFailed(
         (
            new Function2<Path, Exception, FileVisitResult>(var5, var2, var3, var4) {
               final Path $normalizedTarget;
               final Function3<Path, Path, Exception, OnErrorResult> $onError;
               final Path $target;
               final Path $this_copyToRecursively;

               {
                  super(
                     2,
                     Intrinsics.Kotlin::class.java,
                     "error",
                     "copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt(Lkotlin/jvm/functions/Function3;Ljava/nio/file/Path;Ljava/nio/file/Path;Ljava/nio/file/Path;Ljava/nio/file/Path;Ljava/lang/Exception;)Ljava/nio/file/FileVisitResult;",
                     0
                  );
                  this.$onError = var1;
                  this.$this_copyToRecursively = var2;
                  this.$target = var3;
                  this.$normalizedTarget = var4;
               }

               public final FileVisitResult invoke(Path var1, Exception var2) {
                  return PathsKt__PathRecursiveFunctionsKt.access$copyToRecursively$error(
                     this.$onError, this.$this_copyToRecursively, this.$target, this.$normalizedTarget, var1, var2
                  );
               }
            }
         ) as (Path?, IOException?) -> FileVisitResult
      );
      var6.onPostVisitDirectory(new PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticLambda30(var0, var5, var2, var3, var4));
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `copyToRecursively$lambda$6$lambda$4$PathsKt__PathRecursiveFunctionsKt`(
      var0: ArrayList, var1: Function3, var2: Path, var3: Path, var4: Path, var5: Function3, var6: Path, var7: BasicFileAttributes
   ): FileVisitResult {
      val var8: FileVisitResult = copyToRecursively$copy$PathsKt__PathRecursiveFunctionsKt(var0, var1, var2, var3, var4, var5, var6, var7);
      if (var8 === PathTreeWalk$$ExternalSyntheticApiModelOutline0.m()) {
         var0.add(var6);
      }

      return var8;
   }

   @JvmStatic
   fun `copyToRecursively$lambda$6$lambda$5$PathsKt__PathRecursiveFunctionsKt`(
      var0: ArrayList, var1: Function3, var2: Path, var3: Path, var4: Path, var5: Path, var6: IOException
   ): FileVisitResult {
      CollectionsKt.removeLast(var0);
      val var7: FileVisitResult;
      if (var6 == null) {
         var7 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m();
      } else {
         var7 = copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt(var1, var2, var3, var4, var5, var6);
      }

      return var7;
   }

   @JvmStatic
   public fun Path.deleteRecursively() {
      val var1: java.util.List = deleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt(var0);
      if (!var1.isEmpty()) {
         val var3: FileSystemException = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(
            "Failed to delete one or more files. See suppressed exceptions for details."
         );

         for (Exception var2 : var1) {
            ExceptionsKt.addSuppressed(var3, var2);
         }

         throw var3 as java.lang.Throwable;
      }
   }

   @JvmStatic
   private fun Path.deleteRecursivelyImpl(): List<Exception> {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: bipush 0
      // 01: istore 2
      // 02: bipush 1
      // 03: istore 3
      // 04: new kotlin/io/path/ExceptionsCollector
      // 07: dup
      // 08: bipush 0
      // 09: bipush 1
      // 0a: aconst_null
      // 0b: invokespecial kotlin/io/path/ExceptionsCollector.<init> (IILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // 0e: astore 5
      // 10: aload 0
      // 11: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1 (Ljava/nio/file/Path;)Ljava/nio/file/Path;
      // 14: astore 6
      // 16: iload 3
      // 17: istore 1
      // 18: aload 6
      // 1a: ifnull 93
      // 1d: aload 6
      // 1f: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/nio/file/Path;)Ljava/nio/file/DirectoryStream;
      // 22: astore 4
      // 24: goto 2c
      // 27: astore 4
      // 29: aconst_null
      // 2a: astore 4
      // 2c: iload 3
      // 2d: istore 1
      // 2e: aload 4
      // 30: ifnull 93
      // 33: aload 4
      // 35: checkcast java/io/Closeable
      // 38: astore 4
      // 3a: aload 4
      // 3c: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/lang/Object;)Ljava/nio/file/DirectoryStream;
      // 3f: astore 7
      // 41: aload 7
      // 43: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/lang/Object;)Z
      // 46: ifeq 74
      // 49: aload 5
      // 4b: aload 6
      // 4d: invokevirtual kotlin/io/path/ExceptionsCollector.setPath (Ljava/nio/file/Path;)V
      // 50: aload 7
      // 52: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/lang/Object;)Ljava/nio/file/SecureDirectoryStream;
      // 55: astore 7
      // 57: aload 0
      // 58: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/nio/file/Path;)Ljava/nio/file/Path;
      // 5b: astore 6
      // 5d: aload 6
      // 5f: ldc_w "getFileName(...)"
      // 62: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 65: aload 7
      // 67: aload 6
      // 69: aconst_null
      // 6a: aload 5
      // 6c: invokestatic kotlin/io/path/PathsKt__PathRecursiveFunctionsKt.handleEntry$PathsKt__PathRecursiveFunctionsKt (Ljava/nio/file/SecureDirectoryStream;Ljava/nio/file/Path;Ljava/nio/file/Path;Lkotlin/io/path/ExceptionsCollector;)V
      // 6f: iload 2
      // 70: istore 1
      // 71: goto 76
      // 74: bipush 1
      // 75: istore 1
      // 76: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 79: astore 6
      // 7b: aload 4
      // 7d: aconst_null
      // 7e: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 81: goto 93
      // 84: astore 5
      // 86: aload 5
      // 88: athrow
      // 89: astore 0
      // 8a: aload 4
      // 8c: aload 5
      // 8e: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 91: aload 0
      // 92: athrow
      // 93: iload 1
      // 94: ifeq 9e
      // 97: aload 0
      // 98: aconst_null
      // 99: aload 5
      // 9b: invokestatic kotlin/io/path/PathsKt__PathRecursiveFunctionsKt.insecureHandleEntry$PathsKt__PathRecursiveFunctionsKt (Ljava/nio/file/Path;Ljava/nio/file/Path;Lkotlin/io/path/ExceptionsCollector;)V
      // 9e: aload 5
      // a0: invokevirtual kotlin/io/path/ExceptionsCollector.getCollectedExceptions ()Ljava/util/List;
      // a3: areturn
   }

   @JvmStatic
   private fun SecureDirectoryStream<Path>.enterDirectory(name: Path, collector: ExceptionsCollector) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: aload 1
      // 02: bipush 1
      // 03: anewarray 233
      // 06: dup
      // 07: bipush 0
      // 08: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m ()Ljava/nio/file/LinkOption;
      // 0b: aastore
      // 0c: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/nio/file/SecureDirectoryStream;Ljava/lang/Object;[Ljava/nio/file/LinkOption;)Ljava/nio/file/SecureDirectoryStream;
      // 0f: astore 0
      // 10: goto 1a
      // 13: astore 0
      // 14: goto 78
      // 17: astore 0
      // 18: aconst_null
      // 19: astore 0
      // 1a: aload 0
      // 1b: ifnull 7d
      // 1e: aload 0
      // 1f: checkcast java/io/Closeable
      // 22: astore 0
      // 23: aload 0
      // 24: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/lang/Object;)Ljava/nio/file/SecureDirectoryStream;
      // 27: astore 1
      // 28: aload 1
      // 29: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/nio/file/SecureDirectoryStream;)Ljava/util/Iterator;
      // 2c: astore 3
      // 2d: aload 3
      // 2e: ldc_w "iterator(...)"
      // 31: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 34: aload 3
      // 35: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 3a: ifeq 61
      // 3d: aload 3
      // 3e: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 43: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/lang/Object;)Ljava/nio/file/Path;
      // 46: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/nio/file/Path;)Ljava/nio/file/Path;
      // 49: astore 4
      // 4b: aload 4
      // 4d: ldc_w "getFileName(...)"
      // 50: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 53: aload 1
      // 54: aload 4
      // 56: aload 2
      // 57: invokevirtual kotlin/io/path/ExceptionsCollector.getPath ()Ljava/nio/file/Path;
      // 5a: aload 2
      // 5b: invokestatic kotlin/io/path/PathsKt__PathRecursiveFunctionsKt.handleEntry$PathsKt__PathRecursiveFunctionsKt (Ljava/nio/file/SecureDirectoryStream;Ljava/nio/file/Path;Ljava/nio/file/Path;Lkotlin/io/path/ExceptionsCollector;)V
      // 5e: goto 34
      // 61: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 64: astore 1
      // 65: aload 0
      // 66: aconst_null
      // 67: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 6a: goto 7d
      // 6d: astore 1
      // 6e: aload 1
      // 6f: athrow
      // 70: astore 3
      // 71: aload 0
      // 72: aload 1
      // 73: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 76: aload 3
      // 77: athrow
      // 78: aload 2
      // 79: aload 0
      // 7a: invokevirtual kotlin/io/path/ExceptionsCollector.collect (Ljava/lang/Exception;)V
      // 7d: return
   }

   @JvmStatic
   private fun SecureDirectoryStream<Path>.handleEntry(name: Path, parent: Path?, collector: ExceptionsCollector) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
      //
      // Bytecode:
      // 00: aload 3
      // 01: aload 1
      // 02: invokevirtual kotlin/io/path/ExceptionsCollector.enterEntry (Ljava/nio/file/Path;)V
      // 05: aload 2
      // 06: ifnull 1f
      // 09: aload 3
      // 0a: invokevirtual kotlin/io/path/ExceptionsCollector.getPath ()Ljava/nio/file/Path;
      // 0d: astore 6
      // 0f: aload 6
      // 11: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 14: aload 6
      // 16: invokestatic kotlin/io/path/PathsKt.checkFileName (Ljava/nio/file/Path;)V
      // 19: aload 6
      // 1b: aload 2
      // 1c: invokestatic kotlin/io/path/PathsKt__PathRecursiveFunctionsKt.checkNotSameAs$PathsKt__PathRecursiveFunctionsKt (Ljava/nio/file/Path;Ljava/nio/file/Path;)V
      // 1f: aload 0
      // 20: aload 1
      // 21: bipush 1
      // 22: anewarray 233
      // 25: dup
      // 26: bipush 0
      // 27: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m ()Ljava/nio/file/LinkOption;
      // 2a: aastore
      // 2b: invokestatic kotlin/io/path/PathsKt__PathRecursiveFunctionsKt.isDirectory$PathsKt__PathRecursiveFunctionsKt (Ljava/nio/file/SecureDirectoryStream;Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z
      // 2e: ifeq 56
      // 31: aload 3
      // 32: invokevirtual kotlin/io/path/ExceptionsCollector.getTotalExceptions ()I
      // 35: istore 5
      // 37: aload 0
      // 38: aload 1
      // 39: aload 3
      // 3a: invokestatic kotlin/io/path/PathsKt__PathRecursiveFunctionsKt.enterDirectory$PathsKt__PathRecursiveFunctionsKt (Ljava/nio/file/SecureDirectoryStream;Ljava/nio/file/Path;Lkotlin/io/path/ExceptionsCollector;)V
      // 3d: aload 3
      // 3e: invokevirtual kotlin/io/path/ExceptionsCollector.getTotalExceptions ()I
      // 41: istore 4
      // 43: iload 5
      // 45: iload 4
      // 47: if_icmpne 68
      // 4a: aload 0
      // 4b: aload 1
      // 4c: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/nio/file/SecureDirectoryStream;Ljava/lang/Object;)V
      // 4f: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 52: astore 0
      // 53: goto 68
      // 56: aload 0
      // 57: aload 1
      // 58: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1 (Ljava/nio/file/SecureDirectoryStream;Ljava/lang/Object;)V
      // 5b: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 5e: astore 0
      // 5f: goto 68
      // 62: astore 0
      // 63: aload 3
      // 64: aload 0
      // 65: invokevirtual kotlin/io/path/ExceptionsCollector.collect (Ljava/lang/Exception;)V
      // 68: aload 3
      // 69: aload 1
      // 6a: invokevirtual kotlin/io/path/ExceptionsCollector.exitEntry (Ljava/nio/file/Path;)V
      // 6d: return
      // 6e: astore 0
      // 6f: goto 68
   }

   @JvmStatic
   private fun insecureEnterDirectory(path: Path, collector: ExceptionsCollector) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/nio/file/Path;)Ljava/nio/file/DirectoryStream;
      // 04: astore 2
      // 05: goto 0f
      // 08: astore 0
      // 09: goto 62
      // 0c: astore 2
      // 0d: aconst_null
      // 0e: astore 2
      // 0f: aload 2
      // 10: ifnull 67
      // 13: aload 2
      // 14: checkcast java/io/Closeable
      // 17: astore 2
      // 18: aload 2
      // 19: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/lang/Object;)Ljava/nio/file/DirectoryStream;
      // 1c: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/nio/file/DirectoryStream;)Ljava/util/Iterator;
      // 1f: astore 4
      // 21: aload 4
      // 23: ldc_w "iterator(...)"
      // 26: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 29: aload 4
      // 2b: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 30: ifeq 4b
      // 33: aload 4
      // 35: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 3a: invokestatic kotlin/io/path/PathTreeWalk$$ExternalSyntheticApiModelOutline0.m (Ljava/lang/Object;)Ljava/nio/file/Path;
      // 3d: astore 3
      // 3e: aload 3
      // 3f: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 42: aload 3
      // 43: aload 0
      // 44: aload 1
      // 45: invokestatic kotlin/io/path/PathsKt__PathRecursiveFunctionsKt.insecureHandleEntry$PathsKt__PathRecursiveFunctionsKt (Ljava/nio/file/Path;Ljava/nio/file/Path;Lkotlin/io/path/ExceptionsCollector;)V
      // 48: goto 29
      // 4b: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 4e: astore 0
      // 4f: aload 2
      // 50: aconst_null
      // 51: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 54: goto 67
      // 57: astore 3
      // 58: aload 3
      // 59: athrow
      // 5a: astore 0
      // 5b: aload 2
      // 5c: aload 3
      // 5d: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 60: aload 0
      // 61: athrow
      // 62: aload 1
      // 63: aload 0
      // 64: invokevirtual kotlin/io/path/ExceptionsCollector.collect (Ljava/lang/Exception;)V
      // 67: return
   }

   @JvmStatic
   private fun insecureHandleEntry(entry: Path, parent: Path?, collector: ExceptionsCollector) {
      if (var1 != null) {
         try {
            PathsKt.checkFileName(var0);
            checkNotSameAs$PathsKt__PathRecursiveFunctionsKt(var0, var1);
         } catch (var6: Exception) {
            var2.collect(var6);
            return;
         }
      }

      try {
         if (PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var0, Arrays.copyOf(new LinkOption[]{PathTreeWalk$$ExternalSyntheticApiModelOutline0.m()}, 1))) {
            val var3: Int = var2.getTotalExceptions();
            insecureEnterDirectory$PathsKt__PathRecursiveFunctionsKt(var0, var2);
            if (var3 == var2.getTotalExceptions()) {
               PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(var0);
            }

            return;
         }
      } catch (var5: Exception) {
         var2.collect(var5);
         return;
      }

      try {
         PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(var0);
      } catch (var4: Exception) {
         var2.collect(var4);
      }
   }

   @JvmStatic
   private fun SecureDirectoryStream<Path>.isDirectory(entryName: Path, vararg options: LinkOption): Boolean {
      try {
         var5 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(
            PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(
               PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(
                  PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(
                     var0, var1, PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(), Arrays.copyOf(var2, var2.length)
                  )
               )
            )
         );
      } catch (var4: NoSuchFileException) {
         var5 = null;
      }

      val var3: Boolean;
      if (var5 != null) {
         var3 = var5;
      } else {
         var3 = false;
      }

      return var3;
   }

   @JvmStatic
   private fun CopyActionResult.toFileVisitResult(): FileVisitResult {
      val var1: Int = PathsKt__PathRecursiveFunctionsKt.WhenMappings.$EnumSwitchMapping$0[var0.ordinal()];
      val var2: FileVisitResult;
      if (var1 != 1) {
         if (var1 != 2) {
            if (var1 != 3) {
               throw new NoWhenBranchMatchedException();
            }

            var2 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1();
         } else {
            var2 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$2();
         }
      } else {
         var2 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m();
      }

      return var2;
   }

   @JvmStatic
   private fun OnErrorResult.toFileVisitResult(): FileVisitResult {
      val var1: Int = PathsKt__PathRecursiveFunctionsKt.WhenMappings.$EnumSwitchMapping$1[var0.ordinal()];
      val var2: FileVisitResult;
      if (var1 != 1) {
         if (var1 != 2) {
            throw new NoWhenBranchMatchedException();
         }

         var2 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1();
      } else {
         var2 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$2();
      }

      return var2;
   }

   @JvmStatic
   private inline fun <R> tryIgnoreNoSuchFileException(function: () -> R): R? {
      try {
         var2 = var0.invoke();
      } catch (var1: NoSuchFileException) {
         var2 = null;
      }

      return (R)var2;
   }
}
