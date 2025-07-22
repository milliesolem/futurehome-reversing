package kotlin.io.path

import java.nio.file.Path

public interface CopyActionContext {
   public abstract fun Path.copyToIgnoringExistingDirectory(target: Path, followLinks: Boolean): CopyActionResult {
   }
}
