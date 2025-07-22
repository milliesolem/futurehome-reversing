package okhttp3

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import kotlin.jvm.internal.Intrinsics
import okio.ByteString

public object Credentials {
   @JvmStatic
   fun basic(var0: java.lang.String, var1: java.lang.String): java.lang.String {
      return basic$default(var0, var1, null, 4, null);
   }

   @JvmStatic
   public fun basic(username: String, password: String, charset: Charset = StandardCharsets.ISO_8859_1): String {
      Intrinsics.checkParameterIsNotNull(var0, "username");
      Intrinsics.checkParameterIsNotNull(var1, "password");
      Intrinsics.checkParameterIsNotNull(var2, "charset");
      val var3: StringBuilder = new StringBuilder();
      var3.append(var0);
      var3.append(':');
      var3.append(var1);
      var0 = ByteString.Companion.encodeString(var3.toString(), var2).base64();
      val var6: StringBuilder = new StringBuilder("Basic ");
      var6.append(var0);
      return var6.toString();
   }
}
