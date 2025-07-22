package okio

@Deprecated(message = "changed in Okio 2.x")
public object _DeprecatedUtf8 {
   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "string.utf8Size()", imports = ["okio.utf8Size"]))
   public fun size(string: String): Long {
      return Utf8.size$default(var1, 0, 0, 3, null);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "string.utf8Size(beginIndex, endIndex)", imports = ["okio.utf8Size"]))
   public fun size(string: String, beginIndex: Int, endIndex: Int): Long {
      return Utf8.size(var1, var2, var3);
   }
}
