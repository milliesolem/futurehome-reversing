package okhttp3

import java.nio.charset.Charset
import java.util.ArrayList
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.jvm.internal.Intrinsics

public class MediaType private constructor(mediaType: String, type: String, subtype: String, vararg parameterNamesAndValues: Any) {
   private final val mediaType: String
   private final val parameterNamesAndValues: Array<String>
   public final val subtype: String
   public final val type: String

   init {
      this.mediaType = var1;
      this.type = var2;
      this.subtype = var3;
      this.parameterNamesAndValues = var4;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "subtype", imports = []))
   public fun subtype(): String {
      return this.subtype;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "type", imports = []))
   public fun type(): String {
      return this.type;
   }

   fun charset(): Charset {
      return charset$default(this, null, 1, null);
   }

   public fun charset(defaultValue: Charset? = null): Charset? {
      val var3: java.lang.String = this.parameter("charset");
      var var2: Charset = var1;
      if (var3 != null) {
         try {
            var2 = Charset.forName(var3);
         } catch (var4: IllegalArgumentException) {
            var2 = var1;
         }
      }

      return var2;
   }

   public override operator fun equals(other: Any?): Boolean {
      val var2: Boolean;
      if (var1 is MediaType && (var1 as MediaType).mediaType == this.mediaType) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun hashCode(): Int {
      return this.mediaType.hashCode();
   }

   public fun parameter(name: String): String? {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      val var5: IntProgression = RangesKt.step(ArraysKt.getIndices(this.parameterNamesAndValues), 2);
      var var2: Int = var5.getFirst();
      val var4: Int = var5.getLast();
      val var3: Int = var5.getStep();
      if (if (var3 >= 0) var2 <= var4 else var2 >= var4) {
         while (true) {
            if (StringsKt.equals(this.parameterNamesAndValues[var2], var1, true)) {
               return this.parameterNamesAndValues[var2 + 1];
            }

            if (var2 == var4) {
               break;
            }

            var2 += var3;
         }
      }

      return null;
   }

   public override fun toString(): String {
      return this.mediaType;
   }

   public companion object {
      private final val PARAMETER: Pattern
      private const val QUOTED: String
      private const val TOKEN: String
      private final val TYPE_SUBTYPE: Pattern

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "mediaType.toMediaType()", imports = ["okhttp3.MediaType.Companion.toMediaType"]))
      public fun get(mediaType: String): MediaType {
         Intrinsics.checkParameterIsNotNull(var1, "mediaType");
         val var2: MediaType.Companion = this;
         return this.get(var1);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "mediaType.toMediaTypeOrNull()", imports = ["okhttp3.MediaType.Companion.toMediaTypeOrNull"]))
      public fun parse(mediaType: String): MediaType? {
         Intrinsics.checkParameterIsNotNull(var1, "mediaType");
         val var2: MediaType.Companion = this;
         return this.parse(var1);
      }

      public fun String.toMediaType(): MediaType {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toMediaType");
         val var3: Pattern = MediaType.access$getTYPE_SUBTYPE$cp();
         val var4: java.lang.CharSequence = var1;
         val var10: Matcher = var3.matcher(var1);
         if (var10.lookingAt()) {
            var var6: java.lang.String = var10.group(1);
            Intrinsics.checkExpressionValueIsNotNull(var6, "typeSubtype.group(1)");
            val var5: Locale = Locale.US;
            Intrinsics.checkExpressionValueIsNotNull(Locale.US, "Locale.US");
            if (var6 != null) {
               val var18: java.lang.String = var6.toLowerCase(var5);
               Intrinsics.checkExpressionValueIsNotNull(var18, "(this as java.lang.String).toLowerCase(locale)");
               val var7: java.lang.String = var10.group(2);
               Intrinsics.checkExpressionValueIsNotNull(var7, "typeSubtype.group(2)");
               val var19: Locale = Locale.US;
               Intrinsics.checkExpressionValueIsNotNull(Locale.US, "Locale.US");
               if (var7 != null) {
                  var6 = var7.toLowerCase(var19);
                  Intrinsics.checkExpressionValueIsNotNull(var6, "(this as java.lang.String).toLowerCase(locale)");
                  val var21: java.util.List = new ArrayList();
                  val var8: Matcher = MediaType.access$getPARAMETER$cp().matcher(var4);
                  var var2: Int = var10.end();

                  while (var2 < var1.length()) {
                     var8.region(var2, var1.length());
                     if (!var8.lookingAt()) {
                        val var17: StringBuilder = new StringBuilder("Parameter is not formatted correctly: \"");
                        val var13: java.lang.String = var1.substring(var2);
                        Intrinsics.checkExpressionValueIsNotNull(var13, "(this as java.lang.String).substring(startIndex)");
                        var17.append(var13);
                        var17.append("\" for: \"");
                        var17.append(var1);
                        var17.append('"');
                        throw (new IllegalArgumentException(var17.toString().toString())) as java.lang.Throwable;
                     }

                     val var9: java.lang.String = var8.group(1);
                     if (var9 == null) {
                        var2 = var8.end();
                     } else {
                        val var15: java.lang.String = var8.group(2);
                        var var12: java.lang.String;
                        if (var15 == null) {
                           var12 = var8.group(3);
                        } else {
                           var12 = var15;
                           if (StringsKt.startsWith$default(var15, "'", false, 2, null)) {
                              var12 = var15;
                              if (StringsKt.endsWith$default(var15, "'", false, 2, null)) {
                                 var12 = var15;
                                 if (var15.length() > 2) {
                                    var12 = var15.substring(1, var15.length() - 1);
                                    Intrinsics.checkExpressionValueIsNotNull(var12, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                                 }
                              }
                           }
                        }

                        val var16: java.util.Collection = var21;
                        var21.add(var9);
                        var16.add(var12);
                        var2 = var8.end();
                     }
                  }

                  val var14: Array<Any> = var21.toArray(new java.lang.String[0]);
                  if (var14 != null) {
                     return new MediaType(var1, var18, var6, var14 as Array<java.lang.String>, null);
                  } else {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                  }
               } else {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }
            } else {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
         } else {
            val var11: StringBuilder = new StringBuilder("No subtype found for: \"");
            var11.append(var1);
            var11.append('"');
            throw (new IllegalArgumentException(var11.toString().toString())) as java.lang.Throwable;
         }
      }

      public fun String.toMediaTypeOrNull(): MediaType? {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toMediaTypeOrNull");

         try {
            val var2: MediaType.Companion = this;
            var4 = this.get(var1);
         } catch (var3: IllegalArgumentException) {
            var4 = null;
         }

         return var4;
      }
   }
}
