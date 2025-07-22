package okio

import java.io.IOException
import java.net.Socket
import java.net.SocketTimeoutException
import java.util.logging.Level
import java.util.logging.Logger

private class SocketAsyncTimeout(socket: Socket) : AsyncTimeout {
   private final val socket: Socket

   init {
      this.socket = var1;
   }

   protected override fun newTimeoutException(cause: IOException?): IOException {
      val var2: SocketTimeoutException = new SocketTimeoutException("timeout");
      if (var1 != null) {
         var2.initCause(var1);
      }

      return var2;
   }

   protected override fun timedOut() {
      try {
         this.socket.close();
      } catch (var5: Exception) {
         val var7: Logger = Okio__JvmOkioKt.access$getLogger$p();
         val var8: Level = Level.WARNING;
         val var9: StringBuilder = new StringBuilder("Failed to close timed out socket ");
         var9.append(this.socket);
         var7.log(var8, var9.toString(), var5);
      } catch (var6: AssertionError) {
         if (!Okio.isAndroidGetsocknameError(var6)) {
            throw var6;
         }

         val var3: Logger = Okio__JvmOkioKt.access$getLogger$p();
         val var4: Level = Level.WARNING;
         val var1: StringBuilder = new StringBuilder("Failed to close timed out socket ");
         var1.append(this.socket);
         var3.log(var4, var1.toString(), var6);
      }
   }
}
