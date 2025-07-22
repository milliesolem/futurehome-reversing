package kotlin.io

import java.io.File
import java.io.IOException
import java.util.ArrayList
import kotlin.jvm.functions.Function2

internal class FilesKt__UtilsKt : FilesKt__FileTreeWalkKt {
   public final val extension: String
      public final get() {
         val var1: java.lang.String = var0.getName();
         return StringsKt.substringAfterLast(var1, '.', "");
      }


   public final val invariantSeparatorsPath: String
      public final get() {
         val var2: java.lang.String;
         if (File.separatorChar != '/') {
            val var1: java.lang.String = var0.getPath();
            var2 = StringsKt.replace$default(var1, File.separatorChar, '/', false, 4, null);
         } else {
            var2 = var0.getPath();
         }

         return var2;
      }


   public final val nameWithoutExtension: String
      public final get() {
         val var1: java.lang.String = var0.getName();
         return StringsKt.substringBeforeLast$default(var1, ".", null, 2, null);
      }


   open fun FilesKt__UtilsKt() {
   }

   @JvmStatic
   public fun File.copyRecursively(
      target: File,
      overwrite: Boolean = false,
      onError: (File, IOException) -> OnErrorAction = <unrepresentable>.INSTANCE as Function2
   ): Boolean {
      if (!var0.exists()) {
         if (var3.invoke(var0, new NoSuchFileException(var0, null, "The source file doesn't exist.", 2, null)) != OnErrorAction.TERMINATE) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      } else {
         var var19: java.util.Iterator;
         try {
            var19 = FilesKt.walkTopDown(var0).onFail(new FilesKt__UtilsKt$$ExternalSyntheticLambda0(var3)).iterator();
         } catch (var11: TerminateException) {
            return false;
         }

         while (true) {
            var var20: File;
            try {
               while (true) {
                  if (!var19.hasNext()) {
                     return true;
                  }

                  var20 = var19.next() as File;
                  if (var20.exists()) {
                     break;
                  }

                  if (var3.invoke(var20, new NoSuchFileException(var20, null, "The source file doesn't exist.", 2, null)) === OnErrorAction.TERMINATE) {
                     return false;
                  }
               }
            } catch (var12: TerminateException) {
               return false;
            }

            var var22: File;
            label114: {
               label115: {
                  try {
                     var22 = new File(var1, FilesKt.toRelativeString(var20, var0));
                     if (!var22.exists() || var20.isDirectory() && var22.isDirectory()) {
                        break label115;
                     }
                  } catch (var17: TerminateException) {
                     return false;
                  }

                  if (!var2) {
                     break label114;
                  }

                  try {
                     if (var22.isDirectory()) {
                        if (FilesKt.deleteRecursively(var22)) {
                           break label115;
                        }
                        break label114;
                     }
                  } catch (var16: TerminateException) {
                     return false;
                  }

                  try {
                     if (!var22.delete()) {
                        break label114;
                     }
                  } catch (var15: TerminateException) {
                     return false;
                  }
               }

               try {
                  if (var20.isDirectory()) {
                     var22.mkdirs();
                     continue;
                  }
               } catch (var14: TerminateException) {
                  return false;
               }

               try {
                  if (FilesKt.copyTo$default(var20, var22, var2, 0, 4, null).length() == var20.length()) {
                     continue;
                  }

                  var24 = var3.invoke(var20, new IOException("Source file wasn't copied completely, length of destination file differs."));
                  var21 = OnErrorAction.TERMINATE;
               } catch (var13: TerminateException) {
                  return false;
               }

               if (var24 === var21) {
                  return false;
               }
               continue;
            }

            try {
               if (var3.invoke(var22, new FileAlreadyExistsException(var20, var22, "The destination file already exists.")) === OnErrorAction.TERMINATE) {
                  return false;
               }
            } catch (var10: TerminateException) {
               return false;
            }
         }
      }
   }

   @JvmStatic
   fun `copyRecursively$lambda$4$FilesKt__UtilsKt`(var0: Function2, var1: File, var2: IOException): Unit {
      if (var0.invoke(var1, var2) != OnErrorAction.TERMINATE) {
         return Unit.INSTANCE;
      } else {
         throw new TerminateException(var1);
      }
   }

   @JvmStatic
   public fun File.copyTo(target: File, overwrite: Boolean = false, bufferSize: Int = 8192): File {
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
      // 01: ldc "<this>"
      // 03: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 06: aload 1
      // 07: ldc "target"
      // 09: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 0c: aload 0
      // 0d: invokevirtual java/io/File.exists ()Z
      // 10: ifeq d1
      // 13: aload 1
      // 14: invokevirtual java/io/File.exists ()Z
      // 17: ifeq 40
      // 1a: iload 2
      // 1b: ifeq 34
      // 1e: aload 1
      // 1f: invokevirtual java/io/File.delete ()Z
      // 22: ifeq 28
      // 25: goto 40
      // 28: new kotlin/io/FileAlreadyExistsException
      // 2b: dup
      // 2c: aload 0
      // 2d: aload 1
      // 2e: ldc "Tried to overwrite the destination, but failed to delete it."
      // 30: invokespecial kotlin/io/FileAlreadyExistsException.<init> (Ljava/io/File;Ljava/io/File;Ljava/lang/String;)V
      // 33: athrow
      // 34: new kotlin/io/FileAlreadyExistsException
      // 37: dup
      // 38: aload 0
      // 39: aload 1
      // 3a: ldc "The destination file already exists."
      // 3c: invokespecial kotlin/io/FileAlreadyExistsException.<init> (Ljava/io/File;Ljava/io/File;Ljava/lang/String;)V
      // 3f: athrow
      // 40: aload 0
      // 41: invokevirtual java/io/File.isDirectory ()Z
      // 44: ifeq 5d
      // 47: aload 1
      // 48: invokevirtual java/io/File.mkdirs ()Z
      // 4b: ifeq 51
      // 4e: goto b3
      // 51: new kotlin/io/FileSystemException
      // 54: dup
      // 55: aload 0
      // 56: aload 1
      // 57: ldc "Failed to create target directory."
      // 59: invokespecial kotlin/io/FileSystemException.<init> (Ljava/io/File;Ljava/io/File;Ljava/lang/String;)V
      // 5c: athrow
      // 5d: aload 1
      // 5e: invokevirtual java/io/File.getParentFile ()Ljava/io/File;
      // 61: astore 4
      // 63: aload 4
      // 65: ifnull 6e
      // 68: aload 4
      // 6a: invokevirtual java/io/File.mkdirs ()Z
      // 6d: pop
      // 6e: new java/io/FileInputStream
      // 71: dup
      // 72: aload 0
      // 73: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 76: checkcast java/io/Closeable
      // 79: astore 0
      // 7a: aload 0
      // 7b: checkcast java/io/FileInputStream
      // 7e: astore 5
      // 80: new java/io/FileOutputStream
      // 83: astore 4
      // 85: aload 4
      // 87: aload 1
      // 88: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 8b: aload 4
      // 8d: checkcast java/io/Closeable
      // 90: astore 4
      // 92: aload 4
      // 94: checkcast java/io/FileOutputStream
      // 97: astore 6
      // 99: aload 5
      // 9b: checkcast java/io/InputStream
      // 9e: aload 6
      // a0: checkcast java/io/OutputStream
      // a3: iload 3
      // a4: invokestatic kotlin/io/ByteStreamsKt.copyTo (Ljava/io/InputStream;Ljava/io/OutputStream;I)J
      // a7: pop2
      // a8: aload 4
      // aa: aconst_null
      // ab: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // ae: aload 0
      // af: aconst_null
      // b0: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // b3: aload 1
      // b4: areturn
      // b5: astore 1
      // b6: aload 1
      // b7: athrow
      // b8: astore 5
      // ba: aload 4
      // bc: aload 1
      // bd: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // c0: aload 5
      // c2: athrow
      // c3: astore 4
      // c5: aload 4
      // c7: athrow
      // c8: astore 1
      // c9: aload 0
      // ca: aload 4
      // cc: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // cf: aload 1
      // d0: athrow
      // d1: new kotlin/io/NoSuchFileException
      // d4: dup
      // d5: aload 0
      // d6: aconst_null
      // d7: ldc "The source file doesn't exist."
      // d9: bipush 2
      // da: aconst_null
      // db: invokespecial kotlin/io/NoSuchFileException.<init> (Ljava/io/File;Ljava/io/File;Ljava/lang/String;ILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // de: athrow
   }

   @Deprecated(message = "Avoid creating temporary directories in the default temp location with this function due to too wide permissions on the newly created directory. Use kotlin.io.path.createTempDirectory instead.")
   @JvmStatic
   public fun createTempDir(prefix: String = "tmp", suffix: String? = null, directory: File? = null): File {
      val var4: File = File.createTempFile(var0, var1, var2);
      var4.delete();
      if (var4.mkdir()) {
         return var4;
      } else {
         val var3: StringBuilder = new StringBuilder("Unable to create temporary directory ");
         var3.append(var4);
         var3.append('.');
         throw new IOException(var3.toString());
      }
   }

   @Deprecated(message = "Avoid creating temporary files in the default temp location with this function due to too wide permissions on the newly created file. Use kotlin.io.path.createTempFile instead or resort to java.io.File.createTempFile.")
   @JvmStatic
   public fun createTempFile(prefix: String = "tmp", suffix: String? = null, directory: File? = null): File {
      val var3: File = File.createTempFile(var0, var1, var2);
      return var3;
   }

   @JvmStatic
   public fun File.deleteRecursively(): Boolean {
      val var3: java.util.Iterator = FilesKt.walkBottomUp(var0).iterator();

      label25:
      while (true) {
         var var1: Boolean;
         for (var1 = true; var3.hasNext(); var1 = false) {
            val var2: File = var3.next() as File;
            if ((var2.delete() || !var2.exists()) && var1) {
               continue label25;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun File.endsWith(other: File): Boolean {
      val var5: FilePathComponents = FilesKt.toComponents(var0);
      val var4: FilePathComponents = FilesKt.toComponents(var1);
      if (var4.isRooted()) {
         return var0 == var1;
      } else {
         val var2: Int = var5.getSize() - var4.getSize();
         val var3: Boolean;
         if (var2 < 0) {
            var3 = false;
         } else {
            var3 = var5.getSegments().subList(var2, var5.getSize()).equals(var4.getSegments());
         }

         return var3;
      }
   }

   @JvmStatic
   public fun File.endsWith(other: String): Boolean {
      return FilesKt.endsWith(var0, new File(var1));
   }

   @JvmStatic
   public fun File.normalize(): File {
      val var1: FilePathComponents = FilesKt.toComponents(var0);
      var0 = var1.getRoot();
      val var2: java.lang.Iterable = normalize$FilesKt__UtilsKt(var1.getSegments());
      val var4: java.lang.String = File.separator;
      return FilesKt.resolve(var0, CollectionsKt.joinToString$default(var2, var4, null, null, 0, null, null, 62, null));
   }

   @JvmStatic
   private fun List<File>.normalize(): List<File> {
      val var1: java.util.List = new ArrayList(var0.size());

      for (File var3 : var0) {
         val var2: java.lang.String = var3.getName();
         if (!(var2 == ".")) {
            if (var2 == "..") {
               if (!var1.isEmpty() && !(CollectionsKt.<File>last(var1).getName() == "..")) {
                  var1.remove(var1.size() - 1);
               } else {
                  var1.add(var3);
               }
            } else {
               var1.add(var3);
            }
         }
      }

      return var1;
   }

   @JvmStatic
   private fun FilePathComponents.normalize(): FilePathComponents {
      return new FilePathComponents(var0.getRoot(), normalize$FilesKt__UtilsKt(var0.getSegments()));
   }

   @JvmStatic
   public fun File.relativeTo(base: File): File {
      return new File(FilesKt.toRelativeString(var0, var1));
   }

   @JvmStatic
   public fun File.relativeToOrNull(base: File): File? {
      val var2: java.lang.String = toRelativeStringOrNull$FilesKt__UtilsKt(var0, var1);
      if (var2 != null) {
         var0 = new File(var2);
      } else {
         var0 = null;
      }

      return var0;
   }

   @JvmStatic
   public fun File.relativeToOrSelf(base: File): File {
      val var2: java.lang.String = toRelativeStringOrNull$FilesKt__UtilsKt(var0, var1);
      if (var2 != null) {
         var0 = new File(var2);
      }

      return var0;
   }

   @JvmStatic
   public fun File.resolve(relative: File): File {
      if (FilesKt.isRooted(var1)) {
         return var1;
      } else {
         val var3: java.lang.String = var0.toString();
         if (var3.length() != 0 && !StringsKt.endsWith$default(var3, File.separatorChar, false, 2, null)) {
            val var6: StringBuilder = new StringBuilder();
            var6.append(var3);
            var6.append(File.separatorChar);
            var6.append(var1);
            var0 = new File(var6.toString());
         } else {
            val var5: StringBuilder = new StringBuilder();
            var5.append(var3);
            var5.append(var1);
            var0 = new File(var5.toString());
         }

         return var0;
      }
   }

   @JvmStatic
   public fun File.resolve(relative: String): File {
      return FilesKt.resolve(var0, new File(var1));
   }

   @JvmStatic
   public fun File.resolveSibling(relative: File): File {
      val var2: FilePathComponents = FilesKt.toComponents(var0);
      if (var2.getSize() == 0) {
         var0 = new File("..");
      } else {
         var0 = var2.subPath(0, var2.getSize() - 1);
      }

      return FilesKt.resolve(FilesKt.resolve(var2.getRoot(), var0), var1);
   }

   @JvmStatic
   public fun File.resolveSibling(relative: String): File {
      return FilesKt.resolveSibling(var0, new File(var1));
   }

   @JvmStatic
   public fun File.startsWith(other: File): Boolean {
      val var4: FilePathComponents = FilesKt.toComponents(var0);
      val var5: FilePathComponents = FilesKt.toComponents(var1);
      val var3: Boolean = var4.getRoot() == var5.getRoot();
      var var2: Boolean = false;
      if (!var3) {
         return false;
      } else {
         if (var4.getSize() >= var5.getSize()) {
            var2 = var4.getSegments().subList(0, var5.getSize()).equals(var5.getSegments());
         }

         return var2;
      }
   }

   @JvmStatic
   public fun File.startsWith(other: String): Boolean {
      return FilesKt.startsWith(var0, new File(var1));
   }

   @JvmStatic
   public fun File.toRelativeString(base: File): String {
      val var2: java.lang.String = toRelativeStringOrNull$FilesKt__UtilsKt(var0, var1);
      if (var2 != null) {
         return var2;
      } else {
         val var3: StringBuilder = new StringBuilder("this and base files have different roots: ");
         var3.append(var0);
         var3.append(" and ");
         var3.append(var1);
         var3.append('.');
         throw new IllegalArgumentException(var3.toString());
      }
   }

   @JvmStatic
   private fun File.toRelativeStringOrNull(base: File): String? {
      val var6: FilePathComponents = normalize$FilesKt__UtilsKt(FilesKt.toComponents(var0));
      val var9: FilePathComponents = normalize$FilesKt__UtilsKt(FilesKt.toComponents(var1));
      if (!(var6.getRoot() == var9.getRoot())) {
         return null;
      } else {
         val var5: Int = var9.getSize();
         val var4: Int = var6.getSize();
         var var3: Int = Math.min(var4, var5);
         var var2: Int = 0;

         while (var2 < var3 && var6.getSegments().get(var2) == var9.getSegments().get(var2)) {
            var2++;
         }

         val var8: StringBuilder = new StringBuilder();
         var3 = var5 - 1;
         if (var2 <= var5 - 1) {
            while (true) {
               if (var9.getSegments().get(var3).getName() == "..") {
                  return null;
               }

               var8.append("..");
               if (var3 != var2) {
                  var8.append(File.separatorChar);
               }

               if (var3 == var2) {
                  break;
               }

               var3--;
            }
         }

         if (var2 < var4) {
            if (var2 < var5) {
               var8.append(File.separatorChar);
            }

            val var7: java.lang.Iterable = CollectionsKt.drop(var6.getSegments(), var2);
            val var10: Appendable = var8;
            val var12: java.lang.String = File.separator;
            CollectionsKt.joinTo$default(var7, var10, var12, null, null, 0, null, null, 124, null);
         }

         return var8.toString();
      }
   }
}
