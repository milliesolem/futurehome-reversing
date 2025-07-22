package kotlin.io.path

import java.nio.file.Path

private object PathRelativizer {
   private final val emptyPath: Path = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m("", new java.lang.String[0])
   private final val parentPath: Path = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m("..", new java.lang.String[0])

   public fun tryRelativeTo(path: Path, base: Path): Path {
      val var5: Path = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$2(var2);
      var2 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$2(var1);
      var1 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(var5, var2);
      val var4: Int = Math.min(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var5), PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var2));

      for (int var3 = 0; var3 < var4; var3++) {
         val var6: Path = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var5, var3);
         val var7: Path = parentPath;
         if (!(var6 == parentPath)) {
            break;
         }

         if (!(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var2, var3) == var7)) {
            throw new IllegalArgumentException("Unable to compute relative path");
         }
      }

      if (!(var2 == var5) && var5 == emptyPath) {
         var1 = var2;
      } else {
         val var10: java.lang.String = var1.toString();
         val var11: java.lang.String = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var1));
         if (StringsKt.endsWith$default(var10, var11, false, 2, null)) {
            var1 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(
               PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var1),
               StringsKt.dropLast(var10, PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var1)).length()),
               new java.lang.String[0]
            );
         }
      }

      return var1;
   }
}
