package okhttp3.internal.http

import kotlin.jvm.internal.Intrinsics

public object HttpMethod {
   @JvmStatic
   public fun permitsRequestBody(method: String): Boolean {
      Intrinsics.checkParameterIsNotNull(var0, "method");
      val var1: Boolean;
      if (!(var0 == "GET") && !(var0 == "HEAD")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public fun requiresRequestBody(method: String): Boolean {
      Intrinsics.checkParameterIsNotNull(var0, "method");
      val var1: Boolean;
      if (!(var0 == "POST") && !(var0 == "PUT") && !(var0 == "PATCH") && !(var0 == "PROPPATCH") && !(var0 == "REPORT")) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public fun invalidatesCache(method: String): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "method");
      val var2: Boolean;
      if (!(var1 == "POST") && !(var1 == "PATCH") && !(var1 == "PUT") && !(var1 == "DELETE") && !(var1 == "MOVE")) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public fun redirectsToGet(method: String): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "method");
      return var1 == "PROPFIND" xor true;
   }

   public fun redirectsWithBody(method: String): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "method");
      return var1 == "PROPFIND";
   }
}
