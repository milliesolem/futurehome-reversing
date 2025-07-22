package kotlin.io.path

import java.nio.file.FileVisitOption
import java.nio.file.LinkOption

internal object LinkFollowing {
   private final val nofollowLinkOption: Array<LinkOption>
   private final val followLinkOption: Array<LinkOption>
   private final val nofollowVisitOption: Set<FileVisitOption> = SetsKt.emptySet()
   private final val followVisitOption: Set<FileVisitOption> = SetsKt.setOf(PathTreeWalk$$ExternalSyntheticApiModelOutline0.m())

   public fun toLinkOptions(followLinks: Boolean): Array<LinkOption> {
      val var2: Array<LinkOption>;
      if (var1) {
         var2 = followLinkOption;
      } else {
         var2 = nofollowLinkOption;
      }

      return var2;
   }

   public fun toVisitOptions(followLinks: Boolean): Set<FileVisitOption> {
      val var2: java.util.Set;
      if (var1) {
         var2 = followVisitOption;
      } else {
         var2 = nofollowVisitOption;
      }

      return var2;
   }
}
