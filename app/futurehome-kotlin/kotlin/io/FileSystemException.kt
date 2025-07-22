package kotlin.io

import java.io.File
import java.io.IOException

public open class FileSystemException(file: File, other: File? = null, reason: String? = null) : IOException(
      ExceptionsKt.access$constructMessage(var1, var2, var3)
   ) {
   public final val file: File
   public final val other: File?
   public final val reason: String?

   init {
      this.file = var1;
      this.other = var2;
      this.reason = var3;
   }
}
