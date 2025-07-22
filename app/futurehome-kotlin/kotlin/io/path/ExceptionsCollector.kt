package kotlin.io.path

import java.nio.file.Path
import java.util.ArrayList

private class ExceptionsCollector(limit: Int = 64) {
   private final val limit: Int

   public final var totalExceptions: Int
      private set

   public final val collectedExceptions: MutableList<Exception>

   public final var path: Path?
      internal set

   fun ExceptionsCollector() {
      this(0, 1, null);
   }

   init {
      this.limit = var1;
      this.collectedExceptions = new ArrayList<>();
   }

   public fun collect(exception: Exception) {
      this.totalExceptions++;
      if (this.collectedExceptions.size() < this.limit) {
         var var2: Exception = var1;
         if (this.path != null) {
            PathTreeWalk$$ExternalSyntheticApiModelOutline0.m();
            val var3: java.lang.Throwable = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(
               PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(java.lang.String.valueOf(this.path)), var1
            );
            var2 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var3);
         }

         this.collectedExceptions.add(var2);
      }
   }

   public fun enterEntry(name: Path) {
      if (this.path != null) {
         var1 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(this.path, var1);
      } else {
         var1 = null;
      }

      this.path = var1;
   }

   public fun exitEntry(name: Path) {
      val var5: Path;
      if (this.path != null) {
         var5 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(this.path);
      } else {
         var5 = null;
      }

      if (var1 == var5) {
         var1 = null;
         if (this.path != null) {
            var1 = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(this.path);
         }

         this.path = var1;
      } else {
         throw new IllegalArgumentException("Failed requirement.".toString());
      }
   }
}
