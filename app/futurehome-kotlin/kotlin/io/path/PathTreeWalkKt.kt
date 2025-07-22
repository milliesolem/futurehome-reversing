package kotlin.io.path

import java.io.IOException
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import java.util.Arrays

@JvmSynthetic
fun `access$createsCycle`(var0: PathNode): Boolean {
   return createsCycle(var0);
}

@JvmSynthetic
fun `access$keyOf`(var0: Path, var1: Array<LinkOption>): Any {
   return keyOf(var0, var1);
}

private fun PathNode.createsCycle(): Boolean {
   for (PathNode var2 = var0.getParent(); var2 != null; var2 = var2.getParent()) {
      if (var2.getKey() != null && var0.getKey() != null) {
         if (var2.getKey() == var0.getKey()) {
            return true;
         }
      } else {
         var var1: Boolean;
         try {
            var1 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var2.getPath(), var0.getPath());
         } catch (SecurityException | var4: IOException) {
            continue;
         }

         if (var1) {
            return true;
         }
      }
   }

   return false;
}

private fun keyOf(path: Path, linkOptions: Array<LinkOption>): Any? {
   try {
      var1 = Arrays.copyOf(var1, var1.length);
      var0 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(
         (Path)var0, PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(), Arrays.copyOf(var1, var1.length)
      );
      var0 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m((BasicFileAttributes)var0);
   } catch (var2: java.lang.Throwable) {
      return var0;
   }

   return var0;
}
