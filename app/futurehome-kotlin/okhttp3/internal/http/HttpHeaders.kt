package okhttp3.internal.http

import java.io.EOFException
import java.util.ArrayList
import java.util.Collections
import java.util.LinkedHashMap
import kotlin.jvm.internal.Intrinsics
import okhttp3.Challenge
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.Response
import okhttp3.internal.Util
import okhttp3.internal.platform.Platform
import okio.Buffer
import okio.ByteString

private final val QUOTED_STRING_DELIMITERS: ByteString = ByteString.Companion.encodeUtf8("\"\\")
private final val TOKEN_DELIMITERS: ByteString = ByteString.Companion.encodeUtf8("\t ,=")

@Deprecated(level = DeprecationLevel.ERROR, message = "No longer supported", replaceWith = @ReplaceWith(expression = "response.promisesBody()", imports = []))
public fun hasBody(response: Response): Boolean {
   Intrinsics.checkParameterIsNotNull(var0, "response");
   return promisesBody(var0);
}

public fun Headers.parseChallenges(headerName: String): List<Challenge> {
   Intrinsics.checkParameterIsNotNull(var0, "$this$parseChallenges");
   Intrinsics.checkParameterIsNotNull(var1, "headerName");
   val var4: java.util.List = new ArrayList();
   val var3: Int = var0.size();

   for (int var2 = 0; var2 < var3; var2++) {
      if (StringsKt.equals(var1, var0.name(var2), true)) {
         val var5: Buffer = new Buffer().writeUtf8(var0.value(var2));

         try {
            readChallengeHeader(var5, var4);
         } catch (var6: EOFException) {
            Platform.Companion.get().log("Unable to parse challenge", 5, var6);
         }
      }
   }

   return var4;
}

public fun Response.promisesBody(): Boolean {
   Intrinsics.checkParameterIsNotNull(var0, "$this$promisesBody");
   if (var0.request().method() == "HEAD") {
      return false;
   } else {
      val var1: Int = var0.code();
      if ((var1 < 100 || var1 >= 200) && var1 != 204 && var1 != 304) {
         return true;
      } else {
         return Util.headersContentLength(var0) != -1L || StringsKt.equals("chunked", Response.header$default(var0, "Transfer-Encoding", null, 2, null), true);
      }
   }
}

@Throws(java/io/EOFException::class)
private fun Buffer.readChallengeHeader(result: MutableList<Challenge>) {
   var var6: java.lang.String = null as java.lang.String;

   while (true) {
      var6 = null;

      while (true) {
         var var8: java.lang.String = var6;
         if (var6 == null) {
            skipCommasAndWhitespace(var0);
            var6 = readToken(var0);
            var8 = var6;
            if (var6 == null) {
               return;
            }
         }

         val var4: Boolean = skipCommasAndWhitespace(var0);
         var var7: java.lang.String = readToken(var0);
         if (var7 == null) {
            if (!var0.exhausted()) {
               return;
            }

            var1.add(new Challenge(var8, MapsKt.emptyMap()));
            return;
         }

         val var2: Byte = 61;
         var var3: Int = Util.skipAll(var0, (byte)61);
         if (!var4 && (skipCommasAndWhitespace(var0) || var0.exhausted())) {
            val var13: StringBuilder = new StringBuilder();
            var13.append(var7);
            var13.append(StringsKt.repeat("=", var3));
            val var14: java.util.Map = Collections.singletonMap(null, var13.toString());
            Intrinsics.checkExpressionValueIsNotNull(var14, "Collections.singletonMap…ek + \"=\".repeat(eqCount))");
            var1.add(new Challenge(var8, var14));
            break;
         }

         val var9: java.util.Map = new LinkedHashMap();
         var3 = var3 + Util.skipAll(var0, var2);

         while (true) {
            var6 = var7;
            if (var7 == null) {
               var6 = readToken(var0);
               if (skipCommasAndWhitespace(var0)) {
                  break;
               }

               var3 = Util.skipAll(var0, var2);
            }

            if (var3 == 0) {
               break;
            }

            if (var3 > 1) {
               return;
            }

            if (skipCommasAndWhitespace(var0)) {
               return;
            }

            if (startsWith(var0, (byte)34)) {
               var7 = readQuotedString(var0);
            } else {
               var7 = readToken(var0);
            }

            if (var7 == null) {
               return;
            }

            if (var9.put(var6, var7) != null) {
               return;
            }

            if (!skipCommasAndWhitespace(var0) && !var0.exhausted()) {
               return;
            }

            var7 = null;
         }

         var1.add(new Challenge(var8, var9));
      }
   }
}

@Throws(java/io/EOFException::class)
private fun Buffer.readQuotedString(): String? {
   val var1: Byte = var0.readByte();
   val var2: Byte = 34;
   val var6: Boolean;
   if (var1 == 34) {
      var6 = true;
   } else {
      var6 = false;
   }

   if (var6) {
      val var5: Buffer = new Buffer();

      while (true) {
         val var3: Long = var0.indexOfElement(QUOTED_STRING_DELIMITERS);
         if (var3 == -1L) {
            return null;
         }

         if (var0.getByte(var3) == var2) {
            var5.write(var0, var3);
            var0.readByte();
            return var5.readUtf8();
         }

         if (var0.size() == var3 + 1L) {
            return null;
         }

         var5.write(var0, var3);
         var0.readByte();
         var5.write(var0, 1L);
      }
   } else {
      throw (new IllegalArgumentException("Failed requirement.".toString())) as java.lang.Throwable;
   }
}

private fun Buffer.readToken(): String? {
   val var3: Long = var0.indexOfElement(TOKEN_DELIMITERS);
   var var1: Long = var3;
   if (var3 == -1L) {
      var1 = var0.size();
   }

   val var5: java.lang.String;
   if (var1 != 0L) {
      var5 = var0.readUtf8(var1);
   } else {
      var5 = null;
   }

   return var5;
}

public fun CookieJar.receiveHeaders(url: HttpUrl, headers: Headers) {
   Intrinsics.checkParameterIsNotNull(var0, "$this$receiveHeaders");
   Intrinsics.checkParameterIsNotNull(var1, "url");
   Intrinsics.checkParameterIsNotNull(var2, "headers");
   if (var0 != CookieJar.NO_COOKIES) {
      val var3: java.util.List = Cookie.Companion.parseAll(var1, var2);
      if (!var3.isEmpty()) {
         var0.saveFromResponse(var1, var3);
      }
   }
}

private fun Buffer.skipCommasAndWhitespace(): Boolean {
   var var2: Boolean = false;

   while (!var0.exhausted()) {
      val var1: Byte = var0.getByte(0L);
      if (var1 != 9 && var1 != 32) {
         if (var1 != 44) {
            break;
         }

         var0.readByte();
         var2 = true;
      } else {
         var0.readByte();
      }
   }

   return var2;
}

private fun Buffer.startsWith(prefix: Byte): Boolean {
   val var2: Boolean;
   if (!var0.exhausted() && var0.getByte(0L) == var1) {
      var2 = true;
   } else {
      var2 = false;
   }

   return var2;
}
