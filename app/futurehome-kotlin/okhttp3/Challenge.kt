package okhttp3

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.Collections
import java.util.LinkedHashMap
import java.util.Locale
import java.util.Map.Entry
import kotlin.jvm.internal.Intrinsics

public class Challenge(scheme: String, authParams: Map<String?, String>) {
   public final val authParams: Map<String?, String>
      public final get() {
         return this.authParams;
      }


   public final val charset: Charset
      public final get() {
         val var1: java.lang.String = this.authParams.get("charset");
         if (var1 != null) {
            try {
               val var4: Charset = Charset.forName(var1);
               Intrinsics.checkExpressionValueIsNotNull(var4, "Charset.forName(charset)");
               return var4;
            } catch (var2: Exception) {
            }
         }

         val var3: Charset = StandardCharsets.ISO_8859_1;
         Intrinsics.checkExpressionValueIsNotNull(StandardCharsets.ISO_8859_1, "ISO_8859_1");
         return var3;
      }


   public final val realm: String?
      public final get() {
         return this.authParams.get("realm");
      }


   public final val scheme: String

   public constructor(scheme: String, realm: String) : Intrinsics.checkParameterIsNotNull(var1, "scheme") {
      Intrinsics.checkParameterIsNotNull(var2, "realm");
      val var3: java.util.Map = Collections.singletonMap("realm", var2);
      Intrinsics.checkExpressionValueIsNotNull(var3, "singletonMap(\"realm\", realm)");
      this(var1, var3);
   }

   init {
      Intrinsics.checkParameterIsNotNull(var1, "scheme");
      Intrinsics.checkParameterIsNotNull(var2, "authParams");
      super();
      this.scheme = var1;
      val var3: java.util.Map = new LinkedHashMap();

      for (Entry var4 : var2.entrySet()) {
         var1 = var4.getKey() as java.lang.String;
         val var10: java.lang.String = var4.getValue() as java.lang.String;
         if (var1 != null) {
            val var5: Locale = Locale.US;
            Intrinsics.checkExpressionValueIsNotNull(Locale.US, "US");
            if (var1 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var1 = var1.toLowerCase(var5);
            Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).toLowerCase(locale)");
         } else {
            var1 = null;
         }

         var3.put(var1, var10);
      }

      val var8: java.util.Map = Collections.unmodifiableMap(var3);
      Intrinsics.checkExpressionValueIsNotNull(var8, "unmodifiableMap<String?, String>(newAuthParams)");
      this.authParams = var8;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "authParams", imports = []))
   public fun authParams(): Map<String?, String> {
      return this.authParams;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "charset", imports = []))
   public fun charset(): Charset {
      return this.charset();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "realm", imports = []))
   public fun realm(): String? {
      return this.realm();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "scheme", imports = []))
   public fun scheme(): String {
      return this.scheme;
   }

   public override operator fun equals(other: Any?): Boolean {
      return var1 is Challenge && (var1 as Challenge).scheme == this.scheme && (var1 as Challenge).authParams == this.authParams;
   }

   public override fun hashCode(): Int {
      return (899 + this.scheme.hashCode()) * 31 + this.authParams.hashCode();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this.scheme);
      var1.append(" authParams=");
      var1.append(this.authParams);
      return var1.toString();
   }

   public fun withCharset(charset: Charset): Challenge {
      Intrinsics.checkParameterIsNotNull(var1, "charset");
      val var2: java.util.Map = MapsKt.toMutableMap(this.authParams);
      val var3: java.lang.String = var1.name();
      Intrinsics.checkExpressionValueIsNotNull(var3, "charset.name()");
      var2.put("charset", var3);
      return new Challenge(this.scheme, var2);
   }
}
