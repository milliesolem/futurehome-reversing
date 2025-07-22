package kotlin.io.path

import java.nio.file.FileSystemException
import java.nio.file.Path

internal class IllegalFileNameException(file: Path, other: Path?, message: String?) : FileSystemException {
   public constructor(file: Path) : this(var1, null, null)
   init {
      val var4: java.lang.String = var1.toString();
      val var5: java.lang.String;
      if (var2 != null) {
         var5 = var2.toString();
      } else {
         var5 = null;
      }

      super(var4, var5, var3);
   }
}
