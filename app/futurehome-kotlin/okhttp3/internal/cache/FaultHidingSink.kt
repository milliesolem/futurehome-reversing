package okhttp3.internal.cache

import java.io.IOException
import kotlin.jvm.internal.Intrinsics
import okio.Buffer
import okio.ForwardingSink
import okio.Sink

internal open class FaultHidingSink(delegate: Sink, onException: (IOException) -> Unit) : ForwardingSink {
   private final var hasErrors: Boolean
   public final val onException: (IOException) -> Unit

   init {
      Intrinsics.checkParameterIsNotNull(var1, "delegate");
      Intrinsics.checkParameterIsNotNull(var2, "onException");
      super(var1);
      this.onException = var2;
   }

   public override fun close() {
      if (!this.hasErrors) {
         try {
            super.close();
         } catch (var2: IOException) {
            this.hasErrors = true;
            this.onException.invoke(var2);
         }
      }
   }

   public override fun flush() {
      if (!this.hasErrors) {
         try {
            super.flush();
         } catch (var2: IOException) {
            this.hasErrors = true;
            this.onException.invoke(var2);
         }
      }
   }

   public override fun write(source: Buffer, byteCount: Long) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      if (this.hasErrors) {
         var1.skip(var2);
      } else {
         try {
            super.write(var1, var2);
         } catch (var4: IOException) {
            this.hasErrors = true;
            this.onException.invoke(var4);
         }
      }
   }
}
