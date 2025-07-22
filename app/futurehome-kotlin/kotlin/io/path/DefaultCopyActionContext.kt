package kotlin.io.path

import java.nio.file.CopyOption
import java.nio.file.LinkOption
import java.nio.file.Path
import java.util.Arrays

private object DefaultCopyActionContext : CopyActionContext {
   public override fun Path.copyToIgnoringExistingDirectory(target: Path, followLinks: Boolean): CopyActionResult {
      val var5: Array<LinkOption> = LinkFollowing.INSTANCE.toLinkOptions(var3);
      val var4: Array<LinkOption> = Arrays.copyOf(var5, var5.length);
      if (!PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var1, Arrays.copyOf(var4, var4.length))
         || !PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var2, Arrays.copyOf(new LinkOption[]{PathTreeWalk$$ExternalSyntheticApiModelOutline0.m()}, 1))) {
         val var7: Array<CopyOption> = Arrays.copyOf(var5, var5.length);
      }

      return CopyActionResult.CONTINUE;
   }
}
