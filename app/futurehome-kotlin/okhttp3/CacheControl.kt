package okhttp3

import java.util.concurrent.TimeUnit
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util

public class CacheControl private constructor(noCache: Boolean,
   noStore: Boolean,
   maxAgeSeconds: Int,
   sMaxAgeSeconds: Int,
   isPrivate: Boolean,
   isPublic: Boolean,
   mustRevalidate: Boolean,
   maxStaleSeconds: Int,
   minFreshSeconds: Int,
   onlyIfCached: Boolean,
   noTransform: Boolean,
   immutable: Boolean,
   headerValue: String?
) {
   private final var headerValue: String?
   public final val immutable: Boolean
   public final val isPrivate: Boolean
   public final val isPublic: Boolean
   public final val maxAgeSeconds: Int
   public final val maxStaleSeconds: Int
   public final val minFreshSeconds: Int
   public final val mustRevalidate: Boolean
   public final val noCache: Boolean
   public final val noStore: Boolean
   public final val noTransform: Boolean
   public final val onlyIfCached: Boolean
   public final val sMaxAgeSeconds: Int

   init {
      this.noCache = var1;
      this.noStore = var2;
      this.maxAgeSeconds = var3;
      this.sMaxAgeSeconds = var4;
      this.isPrivate = var5;
      this.isPublic = var6;
      this.mustRevalidate = var7;
      this.maxStaleSeconds = var8;
      this.minFreshSeconds = var9;
      this.onlyIfCached = var10;
      this.noTransform = var11;
      this.immutable = var12;
      this.headerValue = var13;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "immutable", imports = []))
   public fun immutable(): Boolean {
      return this.immutable;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "maxAgeSeconds", imports = []))
   public fun maxAgeSeconds(): Int {
      return this.maxAgeSeconds;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "maxStaleSeconds", imports = []))
   public fun maxStaleSeconds(): Int {
      return this.maxStaleSeconds;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "minFreshSeconds", imports = []))
   public fun minFreshSeconds(): Int {
      return this.minFreshSeconds;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "mustRevalidate", imports = []))
   public fun mustRevalidate(): Boolean {
      return this.mustRevalidate;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "noCache", imports = []))
   public fun noCache(): Boolean {
      return this.noCache;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "noStore", imports = []))
   public fun noStore(): Boolean {
      return this.noStore;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "noTransform", imports = []))
   public fun noTransform(): Boolean {
      return this.noTransform;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "onlyIfCached", imports = []))
   public fun onlyIfCached(): Boolean {
      return this.onlyIfCached;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "sMaxAgeSeconds", imports = []))
   public fun sMaxAgeSeconds(): Int {
      return this.sMaxAgeSeconds;
   }

   public override fun toString(): String {
      var var1: java.lang.String = this.headerValue;
      if (this.headerValue == null) {
         val var3: StringBuilder = new StringBuilder();
         if (this.noCache) {
            var3.append("no-cache, ");
         }

         if (this.noStore) {
            var3.append("no-store, ");
         }

         if (this.maxAgeSeconds != -1) {
            var3.append("max-age=");
            var3.append(this.maxAgeSeconds);
            var3.append(", ");
         }

         if (this.sMaxAgeSeconds != -1) {
            var3.append("s-maxage=");
            var3.append(this.sMaxAgeSeconds);
            var3.append(", ");
         }

         if (this.isPrivate) {
            var3.append("private, ");
         }

         if (this.isPublic) {
            var3.append("public, ");
         }

         if (this.mustRevalidate) {
            var3.append("must-revalidate, ");
         }

         if (this.maxStaleSeconds != -1) {
            var3.append("max-stale=");
            var3.append(this.maxStaleSeconds);
            var3.append(", ");
         }

         if (this.minFreshSeconds != -1) {
            var3.append("min-fresh=");
            var3.append(this.minFreshSeconds);
            var3.append(", ");
         }

         if (this.onlyIfCached) {
            var3.append("only-if-cached, ");
         }

         if (this.noTransform) {
            var3.append("no-transform, ");
         }

         if (this.immutable) {
            var3.append("immutable, ");
         }

         if (var3.length() == 0) {
            return "";
         }

         var3.delete(var3.length() - 2, var3.length());
         var1 = var3.toString();
         Intrinsics.checkExpressionValueIsNotNull(var1, "StringBuilder().apply(builderAction).toString()");
         this.headerValue = var1;
      }

      return var1;
   }

   public class Builder {
      private final var immutable: Boolean
      private final var maxAgeSeconds: Int = -1
      private final var maxStaleSeconds: Int = -1
      private final var minFreshSeconds: Int = -1
      private final var noCache: Boolean
      private final var noStore: Boolean
      private final var noTransform: Boolean
      private final var onlyIfCached: Boolean

      private fun Long.clampToInt(): Int {
         var var3: Int = Integer.MAX_VALUE;
         if (var1 <= Integer.MAX_VALUE) {
            var3 = (int)var1;
         }

         return var3;
      }

      public fun build(): CacheControl {
         return new CacheControl(
            this.noCache,
            this.noStore,
            this.maxAgeSeconds,
            -1,
            false,
            false,
            false,
            this.maxStaleSeconds,
            this.minFreshSeconds,
            this.onlyIfCached,
            this.noTransform,
            this.immutable,
            null,
            null
         );
      }

      public fun immutable(): okhttp3.CacheControl.Builder {
         val var1: CacheControl.Builder = this;
         this.immutable = true;
         return this;
      }

      public fun maxAge(maxAge: Int, timeUnit: TimeUnit): okhttp3.CacheControl.Builder {
         Intrinsics.checkParameterIsNotNull(var2, "timeUnit");
         val var4: CacheControl.Builder = this;
         val var3: Boolean;
         if (var1 >= 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            this.maxAgeSeconds = this.clampToInt(var2.toSeconds((long)var1));
            return this;
         } else {
            val var5: StringBuilder = new StringBuilder("maxAge < 0: ");
            var5.append(var1);
            throw (new IllegalArgumentException(var5.toString().toString())) as java.lang.Throwable;
         }
      }

      public fun maxStale(maxStale: Int, timeUnit: TimeUnit): okhttp3.CacheControl.Builder {
         Intrinsics.checkParameterIsNotNull(var2, "timeUnit");
         val var4: CacheControl.Builder = this;
         val var3: Boolean;
         if (var1 >= 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            this.maxStaleSeconds = this.clampToInt(var2.toSeconds((long)var1));
            return this;
         } else {
            val var5: StringBuilder = new StringBuilder("maxStale < 0: ");
            var5.append(var1);
            throw (new IllegalArgumentException(var5.toString().toString())) as java.lang.Throwable;
         }
      }

      public fun minFresh(minFresh: Int, timeUnit: TimeUnit): okhttp3.CacheControl.Builder {
         Intrinsics.checkParameterIsNotNull(var2, "timeUnit");
         val var4: CacheControl.Builder = this;
         val var3: Boolean;
         if (var1 >= 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            this.minFreshSeconds = this.clampToInt(var2.toSeconds((long)var1));
            return this;
         } else {
            val var5: StringBuilder = new StringBuilder("minFresh < 0: ");
            var5.append(var1);
            throw (new IllegalArgumentException(var5.toString().toString())) as java.lang.Throwable;
         }
      }

      public fun noCache(): okhttp3.CacheControl.Builder {
         val var1: CacheControl.Builder = this;
         this.noCache = true;
         return this;
      }

      public fun noStore(): okhttp3.CacheControl.Builder {
         val var1: CacheControl.Builder = this;
         this.noStore = true;
         return this;
      }

      public fun noTransform(): okhttp3.CacheControl.Builder {
         val var1: CacheControl.Builder = this;
         this.noTransform = true;
         return this;
      }

      public fun onlyIfCached(): okhttp3.CacheControl.Builder {
         val var1: CacheControl.Builder = this;
         this.onlyIfCached = true;
         return this;
      }
   }

   public companion object {
      public final val FORCE_CACHE: CacheControl
      public final val FORCE_NETWORK: CacheControl

      private fun String.indexOfElement(characters: String, startIndex: Int = 0): Int {
         val var4: Int = var1.length();

         while (var3 < var4) {
            if (StringsKt.contains$default(var2, var1.charAt(var3), false, 2, null)) {
               return var3;
            }

            var3++;
         }

         return var1.length();
      }

      public fun parse(headers: Headers): CacheControl {
         Intrinsics.checkParameterIsNotNull(var1, "headers");
         var var30: java.lang.String = null as java.lang.String;
         var var2: Int = var1.size();
         var var22: Boolean = true;
         var30 = null;
         var var9: Int = 0;
         var var6: Boolean = true;
         var var21: Boolean = false;
         var var20: Boolean = false;
         var var7: Int = -1;
         var var5: Int = -1;
         var var19: Boolean = false;
         var var18: Boolean = false;
         var var17: Boolean = false;
         var var4: Int = -1;
         var var3: Int = -1;
         var var16: Boolean = false;
         var var15: Boolean = false;

         var var14: Boolean;
         for (var14 = false; var9 < var2; var9++) {
            var var32: java.lang.String;
            label111: {
               val var31: java.lang.String = var1.name(var9);
               var32 = var1.value(var9);
               if (StringsKt.equals(var31, "Cache-Control", var22)) {
                  if (var30 == null) {
                     var30 = var32;
                     break label111;
                  }
               } else if (!StringsKt.equals(var31, "Pragma", var22)) {
                  continue;
               }

               var6 = false;
            }

            var2 = 0;

            while (var2 < var32.length()) {
               val var42: CacheControl.Companion = this;
               var var37: Int = this.indexOfElement(var32, "=,;", var2);
               if (var32 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               val var43: java.lang.String = var32.substring(var2, var37);
               Intrinsics.checkExpressionValueIsNotNull(var43, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               if (var43 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
               }

               val var33: java.lang.String = StringsKt.trim(var43).toString();
               val var44: java.lang.String;
               if (var37 != var32.length() && var32.charAt(var37) != ',' && var32.charAt(var37) != ';') {
                  var37 = Util.indexOfNonWhitespace(var32, var37 + 1);
                  if (var37 < var32.length() && var32.charAt(var37) == '"') {
                     var2 = StringsKt.indexOf$default(var32, '"', ++var37, false, 4, null);
                     if (var32 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                     }

                     var44 = var32.substring(var37, var2);
                     Intrinsics.checkExpressionValueIsNotNull(var44, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                     var2++;
                  } else {
                     var2 = this.indexOfElement(var32, ",;", var37);
                     if (var32 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                     }

                     val var45: java.lang.String = var32.substring(var37, var2);
                     Intrinsics.checkExpressionValueIsNotNull(var45, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                     if (var45 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                     }

                     var44 = StringsKt.trim(var45).toString();
                  }
               } else {
                  var2 = var37 + 1;
                  var44 = null;
               }

               var22 = true;
               var var11: Int;
               var var12: Int;
               var var13: Int;
               var var23: Boolean;
               var var24: Boolean;
               var var25: Boolean;
               var var26: Boolean;
               var var27: Boolean;
               var var28: Boolean;
               var var29: Boolean;
               if (StringsKt.equals("no-cache", var33, true)) {
                  var23 = true;
                  var24 = var20;
                  var37 = var7;
                  var11 = var5;
                  var25 = var19;
                  var26 = var18;
                  var27 = var17;
                  var12 = var4;
                  var13 = var3;
                  var28 = var16;
                  var29 = var15;
               } else if (StringsKt.equals("no-store", var33, true)) {
                  var24 = true;
                  var23 = var21;
                  var37 = var7;
                  var11 = var5;
                  var25 = var19;
                  var26 = var18;
                  var27 = var17;
                  var12 = var4;
                  var13 = var3;
                  var28 = var16;
                  var29 = var15;
               } else if (StringsKt.equals("max-age", var33, true)) {
                  var37 = Util.toNonNegativeInt(var44, -1);
                  var23 = var21;
                  var24 = var20;
                  var11 = var5;
                  var25 = var19;
                  var26 = var18;
                  var27 = var17;
                  var12 = var4;
                  var13 = var3;
                  var28 = var16;
                  var29 = var15;
               } else if (StringsKt.equals("s-maxage", var33, true)) {
                  var11 = Util.toNonNegativeInt(var44, -1);
                  var23 = var21;
                  var24 = var20;
                  var37 = var7;
                  var25 = var19;
                  var26 = var18;
                  var27 = var17;
                  var12 = var4;
                  var13 = var3;
                  var28 = var16;
                  var29 = var15;
               } else if (StringsKt.equals("private", var33, true)) {
                  var25 = true;
                  var23 = var21;
                  var24 = var20;
                  var37 = var7;
                  var11 = var5;
                  var26 = var18;
                  var27 = var17;
                  var12 = var4;
                  var13 = var3;
                  var28 = var16;
                  var29 = var15;
               } else if (StringsKt.equals("public", var33, true)) {
                  var26 = true;
                  var23 = var21;
                  var24 = var20;
                  var37 = var7;
                  var11 = var5;
                  var25 = var19;
                  var27 = var17;
                  var12 = var4;
                  var13 = var3;
                  var28 = var16;
                  var29 = var15;
               } else if (StringsKt.equals("must-revalidate", var33, true)) {
                  var27 = true;
                  var23 = var21;
                  var24 = var20;
                  var37 = var7;
                  var11 = var5;
                  var25 = var19;
                  var26 = var18;
                  var12 = var4;
                  var13 = var3;
                  var28 = var16;
                  var29 = var15;
               } else if (StringsKt.equals("max-stale", var33, true)) {
                  var12 = Util.toNonNegativeInt(var44, Integer.MAX_VALUE);
                  var23 = var21;
                  var24 = var20;
                  var37 = var7;
                  var11 = var5;
                  var25 = var19;
                  var26 = var18;
                  var27 = var17;
                  var13 = var3;
                  var28 = var16;
                  var29 = var15;
               } else if (StringsKt.equals("min-fresh", var33, true)) {
                  var13 = Util.toNonNegativeInt(var44, -1);
                  var23 = var21;
                  var24 = var20;
                  var37 = var7;
                  var11 = var5;
                  var25 = var19;
                  var26 = var18;
                  var27 = var17;
                  var12 = var4;
                  var28 = var16;
                  var29 = var15;
               } else if (StringsKt.equals("only-if-cached", var33, true)) {
                  var28 = true;
                  var23 = var21;
                  var24 = var20;
                  var37 = var7;
                  var11 = var5;
                  var25 = var19;
                  var26 = var18;
                  var27 = var17;
                  var12 = var4;
                  var13 = var3;
                  var29 = var15;
               } else if (StringsKt.equals("no-transform", var33, true)) {
                  var29 = true;
                  var23 = var21;
                  var24 = var20;
                  var37 = var7;
                  var11 = var5;
                  var25 = var19;
                  var26 = var18;
                  var27 = var17;
                  var12 = var4;
                  var13 = var3;
                  var28 = var16;
               } else {
                  var23 = var21;
                  var24 = var20;
                  var37 = var7;
                  var11 = var5;
                  var25 = var19;
                  var26 = var18;
                  var27 = var17;
                  var12 = var4;
                  var13 = var3;
                  var28 = var16;
                  var29 = var15;
                  if (StringsKt.equals("immutable", var33, true)) {
                     var14 = true;
                     var29 = var15;
                     var28 = var16;
                     var13 = var3;
                     var12 = var4;
                     var27 = var17;
                     var26 = var18;
                     var25 = var19;
                     var11 = var5;
                     var37 = var7;
                     var24 = var20;
                     var23 = var21;
                  }
               }

               var21 = var23;
               var20 = var24;
               var7 = var37;
               var5 = var11;
               var19 = var25;
               var18 = var26;
               var17 = var27;
               var4 = var12;
               var3 = var13;
               var16 = var28;
               var15 = var29;
            }

            var2 = var2;
         }

         val var34: java.lang.String;
         if (!var6) {
            var34 = null;
         } else {
            var34 = var30;
         }

         return new CacheControl(var21, var20, var7, var5, var19, var18, var17, var4, var3, var16, var15, var14, var34, null);
      }
   }
}
