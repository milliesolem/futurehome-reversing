package okhttp3.internal.http

import java.net.ProtocolException
import kotlin.jvm.internal.Intrinsics
import okhttp3.Protocol
import okhttp3.Response

public class StatusLine(protocol: Protocol, code: Int, message: String) {
   public final val code: Int
   public final val message: String
   public final val protocol: Protocol

   init {
      Intrinsics.checkParameterIsNotNull(var1, "protocol");
      Intrinsics.checkParameterIsNotNull(var3, "message");
      super();
      this.protocol = var1;
      this.code = var2;
      this.message = var3;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      if (this.protocol === Protocol.HTTP_1_0) {
         var1.append("HTTP/1.0");
      } else {
         var1.append("HTTP/1.1");
      }

      var1.append(' ');
      var1.append(this.code);
      var1.append(' ');
      var1.append(this.message);
      val var2: java.lang.String = var1.toString();
      Intrinsics.checkExpressionValueIsNotNull(var2, "StringBuilder().apply(builderAction).toString()");
      return var2;
   }

   public companion object {
      public const val HTTP_CONTINUE: Int
      public const val HTTP_MISDIRECTED_REQUEST: Int
      public const val HTTP_PERM_REDIRECT: Int
      public const val HTTP_TEMP_REDIRECT: Int

      public fun get(response: Response): StatusLine {
         Intrinsics.checkParameterIsNotNull(var1, "response");
         return new StatusLine(var1.protocol(), var1.code(), var1.message());
      }

      @Throws(java/io/IOException::class)
      public fun parse(statusLine: String): StatusLine {
         Intrinsics.checkParameterIsNotNull(var1, "statusLine");
         val var2: Byte;
         val var5: Protocol;
         if (StringsKt.startsWith$default(var1, "HTTP/1.", false, 2, null)) {
            var var3: Int = var1.length();
            var2 = 9;
            if (var3 < 9 || var1.charAt(8) != ' ') {
               val var13: StringBuilder = new StringBuilder("Unexpected status line: ");
               var13.append(var1);
               throw (new ProtocolException(var13.toString())) as java.lang.Throwable;
            }

            var3 = var1.charAt(7) - '0';
            if (var3 == 0) {
               var5 = Protocol.HTTP_1_0;
            } else {
               if (var3 != 1) {
                  val var12: StringBuilder = new StringBuilder("Unexpected status line: ");
                  var12.append(var1);
                  throw (new ProtocolException(var12.toString())) as java.lang.Throwable;
               }

               var5 = Protocol.HTTP_1_1;
            }
         } else {
            if (!StringsKt.startsWith$default(var1, "ICY ", false, 2, null)) {
               val var17: StringBuilder = new StringBuilder("Unexpected status line: ");
               var17.append(var1);
               throw (new ProtocolException(var17.toString())) as java.lang.Throwable;
            }

            var5 = Protocol.HTTP_1_0;
            var2 = 4;
         }

         var var4: Int = var1.length();
         val var10: Int = var2 + 3;
         if (var4 >= var2 + 3) {
            try {
               val var6: java.lang.String = var1.substring(var2, var10);
               Intrinsics.checkExpressionValueIsNotNull(var6, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               var4 = Integer.parseInt(var6);
            } catch (var7: NumberFormatException) {
               val var15: StringBuilder = new StringBuilder("Unexpected status line: ");
               var15.append(var1);
               throw (new ProtocolException(var15.toString())) as java.lang.Throwable;
            }

            if (var1.length() > var10) {
               if (var1.charAt(var10) != ' ') {
                  val var16: StringBuilder = new StringBuilder("Unexpected status line: ");
                  var16.append(var1);
                  throw (new ProtocolException(var16.toString())) as java.lang.Throwable;
               }

               var1 = var1.substring(var2 + 4);
               Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
            } else {
               var1 = "";
            }

            return new StatusLine(var5, var4, var1);
         } else {
            val var14: StringBuilder = new StringBuilder("Unexpected status line: ");
            var14.append(var1);
            throw (new ProtocolException(var14.toString())) as java.lang.Throwable;
         }
      }
   }
}
