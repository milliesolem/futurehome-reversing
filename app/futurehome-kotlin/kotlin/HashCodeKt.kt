package kotlin

public inline fun Any?.hashCode(): Int {
   val var1: Int;
   if (var0 != null) {
      var1 = var0.hashCode();
   } else {
      var1 = 0;
   }

   return var1;
}
