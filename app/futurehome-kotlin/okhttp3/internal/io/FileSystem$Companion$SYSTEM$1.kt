package okhttp3.internal.io

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.jvm.internal.Intrinsics
import okio.Okio
import okio.Sink
import okio.Source

public class `FileSystem$Companion$SYSTEM$1` : FileSystem {
   fun `FileSystem$Companion$SYSTEM$1`() {
   }

   @Throws(java/io/FileNotFoundException::class)
   public override fun appendingSink(file: File): Sink {
      Intrinsics.checkParameterIsNotNull(var1, "file");

      var var2: Sink;
      try {
         var2 = Okio.appendingSink(var1);
      } catch (var3: FileNotFoundException) {
         var1.getParentFile().mkdirs();
         return Okio.appendingSink(var1);
      }

      return var2;
   }

   @Throws(java/io/IOException::class)
   public override fun delete(file: File) {
      Intrinsics.checkParameterIsNotNull(var1, "file");
      if (!var1.delete() && var1.exists()) {
         val var2: StringBuilder = new StringBuilder("failed to delete ");
         var2.append(var1);
         throw (new IOException(var2.toString())) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   public override fun deleteContents(directory: File) {
      Intrinsics.checkParameterIsNotNull(var1, "directory");
      val var4: Array<File> = var1.listFiles();
      if (var4 != null) {
         val var3: Int = var4.length;

         for (int var2 = 0; var2 < var3; var2++) {
            var1 = var4[var2];
            Intrinsics.checkExpressionValueIsNotNull(var4[var2], "file");
            if (var1.isDirectory()) {
               this.deleteContents(var1);
            }

            if (!var1.delete()) {
               val var7: StringBuilder = new StringBuilder("failed to delete ");
               var7.append(var1);
               throw (new IOException(var7.toString())) as java.lang.Throwable;
            }
         }
      } else {
         val var6: StringBuilder = new StringBuilder("not a readable directory: ");
         var6.append(var1);
         throw (new IOException(var6.toString())) as java.lang.Throwable;
      }
   }

   public override fun exists(file: File): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "file");
      return var1.exists();
   }

   @Throws(java/io/IOException::class)
   public override fun rename(from: File, to: File) {
      Intrinsics.checkParameterIsNotNull(var1, "from");
      Intrinsics.checkParameterIsNotNull(var2, "to");
      this.delete(var2);
      if (!var1.renameTo(var2)) {
         val var3: StringBuilder = new StringBuilder("failed to rename ");
         var3.append(var1);
         var3.append(" to ");
         var3.append(var2);
         throw (new IOException(var3.toString())) as java.lang.Throwable;
      }
   }

   @Throws(java/io/FileNotFoundException::class)
   public override fun sink(file: File): Sink {
      Intrinsics.checkParameterIsNotNull(var1, "file");

      var var2: Sink;
      try {
         var2 = Okio.sink$default(var1, false, 1, null);
      } catch (var3: FileNotFoundException) {
         var1.getParentFile().mkdirs();
         return Okio.sink$default(var1, false, 1, null);
      }

      return var2;
   }

   public override fun size(file: File): Long {
      Intrinsics.checkParameterIsNotNull(var1, "file");
      return var1.length();
   }

   @Throws(java/io/FileNotFoundException::class)
   public override fun source(file: File): Source {
      Intrinsics.checkParameterIsNotNull(var1, "file");
      return Okio.source(var1);
   }

   public override fun toString(): String {
      return "FileSystem.SYSTEM";
   }
}
