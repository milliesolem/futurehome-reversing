package okhttp3

import java.net.MalformedURLException
import java.net.URI
import java.net.URISyntaxException
import java.net.URL
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.ArrayList
import java.util.Collections
import java.util.LinkedHashSet
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.HostnamesKt
import okhttp3.internal.Util
import okhttp3.internal.publicsuffix.PublicSuffixDatabase
import okio.Buffer

public class HttpUrl internal constructor(scheme: String,
   username: String,
   password: String,
   host: String,
   port: Int,
   pathSegments: List<String>,
   queryNamesAndValues: List<String?>?,
   fragment: String?,
   url: String
) {
   public final val encodedFragment: String?
      public final get() {
         if (this.fragment == null) {
            return null;
         } else {
            val var1: Int = StringsKt.indexOf$default(this.url, '#', 0, false, 6, null);
            if (this.url != null) {
               val var3: java.lang.String = this.url.substring(var1 + 1);
               Intrinsics.checkExpressionValueIsNotNull(var3, "(this as java.lang.String).substring(startIndex)");
               return var3;
            } else {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
         }
      }


   public final val encodedPassword: String
      public final get() {
         if (this.password.length() == 0) {
            return "";
         } else {
            val var2: Int = StringsKt.indexOf$default(this.url, ':', this.scheme.length() + 3, false, 4, null);
            val var1: Int = StringsKt.indexOf$default(this.url, '@', 0, false, 6, null);
            if (this.url != null) {
               val var4: java.lang.String = this.url.substring(var2 + 1, var1);
               Intrinsics.checkExpressionValueIsNotNull(var4, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               return var4;
            } else {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
         }
      }


   public final val encodedPath: String
      public final get() {
         val var2: Int = StringsKt.indexOf$default(this.url, '/', this.scheme.length() + 3, false, 4, null);
         val var1: Int = Util.delimiterOffset(this.url, "?#", var2, this.url.length());
         if (this.url != null) {
            val var5: java.lang.String = this.url.substring(var2, var1);
            Intrinsics.checkExpressionValueIsNotNull(var5, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return var5;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }
      }


   public final val encodedPathSegments: List<String>
      public final get() {
         var var1: Int = StringsKt.indexOf$default(this.url, '/', this.scheme.length() + 3, false, 4, null);
         val var2: Int = Util.delimiterOffset(this.url, "?#", var1, this.url.length());
         val var6: java.util.List = new ArrayList();

         while (var1 < var2) {
            val var3: Int = var1 + 1;
            var1 = Util.delimiterOffset(this.url, '/', var1 + 1, var2);
            if (this.url == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            val var7: java.lang.String = this.url.substring(var3, var1);
            Intrinsics.checkExpressionValueIsNotNull(var7, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            var6.add(var7);
         }

         return var6;
      }


   public final val encodedQuery: String?
      public final get() {
         if (this.queryNamesAndValues == null) {
            return null;
         } else {
            val var2: Int = StringsKt.indexOf$default(this.url, '?', 0, false, 6, null) + 1;
            val var1: Int = Util.delimiterOffset(this.url, '#', var2, this.url.length());
            if (this.url != null) {
               val var5: java.lang.String = this.url.substring(var2, var1);
               Intrinsics.checkExpressionValueIsNotNull(var5, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               return var5;
            } else {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
         }
      }


   public final val encodedUsername: String
      public final get() {
         if (this.username.length() == 0) {
            return "";
         } else {
            val var2: Int = this.scheme.length() + 3;
            val var1: Int = Util.delimiterOffset(this.url, ":@", var2, this.url.length());
            if (this.url != null) {
               val var5: java.lang.String = this.url.substring(var2, var1);
               Intrinsics.checkExpressionValueIsNotNull(var5, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               return var5;
            } else {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
         }
      }


   public final val fragment: String?
   public final val host: String
   public final val isHttps: Boolean
   public final val password: String
   public final val pathSegments: List<String>

   public final val pathSize: Int
      public final get() {
         return this.pathSegments.size();
      }


   public final val port: Int

   public final val query: String?
      public final get() {
         if (this.queryNamesAndValues == null) {
            return null;
         } else {
            val var1: StringBuilder = new StringBuilder();
            Companion.toQueryString$okhttp(this.queryNamesAndValues, var1);
            return var1.toString();
         }
      }


   private final val queryNamesAndValues: List<String?>?

   public final val queryParameterNames: Set<String>
      public final get() {
         if (this.queryNamesAndValues == null) {
            return SetsKt.emptySet();
         } else {
            val var4: LinkedHashSet = new LinkedHashSet();
            val var5: IntProgression = RangesKt.step(RangesKt.until(0, this.queryNamesAndValues.size()), 2);
            var var1: Int = var5.getFirst();
            val var3: Int = var5.getLast();
            val var2: Int = var5.getStep();
            if (if (var2 >= 0) var1 <= var3 else var1 >= var3) {
               while (true) {
                  val var7: Any = this.queryNamesAndValues.get(var1);
                  if (var7 == null) {
                     Intrinsics.throwNpe();
                  }

                  var4.add(var7);
                  if (var1 == var3) {
                     break;
                  }

                  var1 += var2;
               }
            }

            val var6: java.util.Set = Collections.unmodifiableSet(var4);
            Intrinsics.checkExpressionValueIsNotNull(var6, "Collections.unmodifiableSet(result)");
            return var6;
         }
      }


   public final val querySize: Int
      public final get() {
         val var1: Int;
         if (this.queryNamesAndValues != null) {
            var1 = this.queryNamesAndValues.size() / 2;
         } else {
            var1 = 0;
         }

         return var1;
      }


   public final val scheme: String
   private final val url: String
   public final val username: String

   init {
      Intrinsics.checkParameterIsNotNull(var1, "scheme");
      Intrinsics.checkParameterIsNotNull(var2, "username");
      Intrinsics.checkParameterIsNotNull(var3, "password");
      Intrinsics.checkParameterIsNotNull(var4, "host");
      Intrinsics.checkParameterIsNotNull(var6, "pathSegments");
      Intrinsics.checkParameterIsNotNull(var9, "url");
      super();
      this.scheme = var1;
      this.username = var2;
      this.password = var3;
      this.host = var4;
      this.port = var5;
      this.pathSegments = var6;
      this.queryNamesAndValues = var7;
      this.fragment = var8;
      this.url = var9;
      this.isHttps = var1 == "https";
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedFragment", imports = []))
   public fun encodedFragment(): String? {
      return this.encodedFragment();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedPassword", imports = []))
   public fun encodedPassword(): String {
      return this.encodedPassword();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedPath", imports = []))
   public fun encodedPath(): String {
      return this.encodedPath();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedPathSegments", imports = []))
   public fun encodedPathSegments(): List<String> {
      return this.encodedPathSegments();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedQuery", imports = []))
   public fun encodedQuery(): String? {
      return this.encodedQuery();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "encodedUsername", imports = []))
   public fun encodedUsername(): String {
      return this.encodedUsername();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "fragment", imports = []))
   public fun fragment(): String? {
      return this.fragment;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "host", imports = []))
   public fun host(): String {
      return this.host;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "password", imports = []))
   public fun password(): String {
      return this.password;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "pathSegments", imports = []))
   public fun pathSegments(): List<String> {
      return this.pathSegments;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "pathSize", imports = []))
   public fun pathSize(): Int {
      return this.pathSize();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "port", imports = []))
   public fun port(): Int {
      return this.port;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "query", imports = []))
   public fun query(): String? {
      return this.query();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "queryParameterNames", imports = []))
   public fun queryParameterNames(): Set<String> {
      return this.queryParameterNames();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "querySize", imports = []))
   public fun querySize(): Int {
      return this.querySize();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "scheme", imports = []))
   public fun scheme(): String {
      return this.scheme;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to toUri()", replaceWith = @ReplaceWith(expression = "toUri()", imports = []))
   public fun uri(): URI {
      return this.uri();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to toUrl()", replaceWith = @ReplaceWith(expression = "toUrl()", imports = []))
   public fun url(): URL {
      return this.url();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "username", imports = []))
   public fun username(): String {
      return this.username;
   }

   public override operator fun equals(other: Any?): Boolean {
      val var2: Boolean;
      if (var1 is HttpUrl && (var1 as HttpUrl).url == this.url) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun hashCode(): Int {
      return this.url.hashCode();
   }

   public fun newBuilder(): okhttp3.HttpUrl.Builder {
      val var2: HttpUrl.Builder = new HttpUrl.Builder();
      var2.setScheme$okhttp(this.scheme);
      var2.setEncodedUsername$okhttp(this.encodedUsername());
      var2.setEncodedPassword$okhttp(this.encodedPassword());
      var2.setHost$okhttp(this.host);
      val var1: Int;
      if (this.port != Companion.defaultPort(this.scheme)) {
         var1 = this.port;
      } else {
         var1 = -1;
      }

      var2.setPort$okhttp(var1);
      var2.getEncodedPathSegments$okhttp().clear();
      var2.getEncodedPathSegments$okhttp().addAll(this.encodedPathSegments());
      var2.encodedQuery(this.encodedQuery());
      var2.setEncodedFragment$okhttp(this.encodedFragment());
      return var2;
   }

   public fun newBuilder(link: String): okhttp3.HttpUrl.Builder? {
      Intrinsics.checkParameterIsNotNull(var1, "link");

      try {
         var4 = new HttpUrl.Builder().parse$okhttp(this, var1);
      } catch (var3: IllegalArgumentException) {
         var4 = null;
      }

      return var4;
   }

   public fun queryParameter(name: String): String? {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      if (this.queryNamesAndValues == null) {
         return null;
      } else {
         val var6: IntProgression = RangesKt.step(RangesKt.until(0, this.queryNamesAndValues.size()), 2);
         var var2: Int = var6.getFirst();
         val var4: Int = var6.getLast();
         val var3: Int = var6.getStep();
         if (if (var3 >= 0) var2 <= var4 else var2 >= var4) {
            while (true) {
               if (var1 == this.queryNamesAndValues.get(var2)) {
                  return this.queryNamesAndValues.get(var2 + 1);
               }

               if (var2 == var4) {
                  break;
               }

               var2 += var3;
            }
         }

         return null;
      }
   }

   public fun queryParameterName(index: Int): String {
      if (this.queryNamesAndValues != null) {
         val var3: Any = this.queryNamesAndValues.get(var1 * 2);
         if (var3 == null) {
            Intrinsics.throwNpe();
         }

         return var3 as java.lang.String;
      } else {
         throw (new IndexOutOfBoundsException()) as java.lang.Throwable;
      }
   }

   public fun queryParameterValue(index: Int): String? {
      if (this.queryNamesAndValues != null) {
         return this.queryNamesAndValues.get(var1 * 2 + 1);
      } else {
         throw (new IndexOutOfBoundsException()) as java.lang.Throwable;
      }
   }

   public fun queryParameterValues(name: String): List<String?> {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      if (this.queryNamesAndValues == null) {
         return CollectionsKt.emptyList();
      } else {
         val var5: java.util.List = new ArrayList();
         val var6: IntProgression = RangesKt.step(RangesKt.until(0, this.queryNamesAndValues.size()), 2);
         var var2: Int = var6.getFirst();
         val var3: Int = var6.getLast();
         val var4: Int = var6.getStep();
         if (if (var4 >= 0) var2 <= var3 else var2 >= var3) {
            while (true) {
               if (var1 == this.queryNamesAndValues.get(var2)) {
                  var5.add(this.queryNamesAndValues.get(var2 + 1));
               }

               if (var2 == var3) {
                  break;
               }

               var2 += var4;
            }
         }

         val var7: java.util.List = Collections.unmodifiableList(var5);
         Intrinsics.checkExpressionValueIsNotNull(var7, "Collections.unmodifiableList(result)");
         return var7;
      }
   }

   public fun redact(): String {
      val var1: HttpUrl.Builder = this.newBuilder("/...");
      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      return var1.username("").password("").build().toString();
   }

   public fun resolve(link: String): HttpUrl? {
      Intrinsics.checkParameterIsNotNull(var1, "link");
      val var2: HttpUrl.Builder = this.newBuilder(var1);
      val var3: HttpUrl;
      if (var2 != null) {
         var3 = var2.build();
      } else {
         var3 = null;
      }

      return var3;
   }

   public override fun toString(): String {
      return this.url;
   }

   public fun topPrivateDomain(): String? {
      val var1: java.lang.String;
      if (Util.canParseAsIpAddress(this.host)) {
         var1 = null;
      } else {
         var1 = PublicSuffixDatabase.Companion.get().getEffectiveTldPlusOne(this.host);
      }

      return var1;
   }

   public fun toUri(): URI {
      val var3: java.lang.String = this.newBuilder().reencodeForUri$okhttp().toString();

      var var6: URI;
      try {
         var6 = new URI(var3);
      } catch (var5: URISyntaxException) {
         try {
            var6 = URI.create(new Regex("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]").replace(var3, ""));
            Intrinsics.checkExpressionValueIsNotNull(var6, "URI.create(stripped)");
         } catch (var4: Exception) {
            throw (new RuntimeException(var5)) as java.lang.Throwable;
         }
      }

      return var6;
   }

   public fun toUrl(): URL {
      try {
         return new URL(this.url);
      } catch (var2: MalformedURLException) {
         throw (new RuntimeException(var2)) as java.lang.Throwable;
      }
   }

   public class Builder {
      internal final var encodedFragment: String?
      internal final var encodedPassword: String
      internal final val encodedPathSegments: MutableList<String>
      internal final var encodedQueryNamesAndValues: MutableList<String?>?
      internal final var encodedUsername: String = ""
      internal final var host: String?
      internal final var port: Int
      internal final var scheme: String?

      private fun addPathSegments(pathSegments: String, alreadyEncoded: Boolean): okhttp3.HttpUrl.Builder {
         val var6: HttpUrl.Builder = this;
         var var3: Int = 0;

         var var4: Int;
         do {
            var4 = Util.delimiterOffset(var1, "/\\", var3, var1.length());
            val var5: Boolean;
            if (var4 < var1.length()) {
               var5 = true;
            } else {
               var5 = false;
            }

            this.push(var1, var3, var4, var5, var2);
            var3 = ++var4;
         } while (var4 <= var1.length());

         return this;
      }

      private fun effectivePort(): Int {
         var var1: Int = this.port;
         if (this.port == -1) {
            if (this.scheme == null) {
               Intrinsics.throwNpe();
            }

            var1 = HttpUrl.Companion.defaultPort(this.scheme);
         }

         return var1;
      }

      private fun isDot(input: String): Boolean {
         val var4: Boolean = var1 == ".";
         var var2: Boolean = true;
         if (!var4) {
            if (StringsKt.equals(var1, "%2e", true)) {
               var2 = true;
            } else {
               var2 = false;
            }
         }

         return var2;
      }

      private fun isDotDot(input: String): Boolean {
         val var4: Boolean = var1 == "..";
         var var2: Boolean = true;
         if (!var4) {
            var2 = true;
            if (!StringsKt.equals(var1, "%2e.", true)) {
               var2 = true;
               if (!StringsKt.equals(var1, ".%2e", true)) {
                  if (StringsKt.equals(var1, "%2e%2e", true)) {
                     var2 = true;
                  } else {
                     var2 = false;
                  }
               }
            }
         }

         return var2;
      }

      private fun pop() {
         if (this.encodedPathSegments.remove(this.encodedPathSegments.size() - 1).length() == 0 && !this.encodedPathSegments.isEmpty()) {
            this.encodedPathSegments.set(this.encodedPathSegments.size() - 1, "");
         } else {
            this.encodedPathSegments.add("");
         }
      }

      private fun push(input: String, pos: Int, limit: Int, addTrailingSlash: Boolean, alreadyEncoded: Boolean) {
         var1 = HttpUrl.Companion.canonicalize$okhttp$default(
            HttpUrl.Companion, var1, var2, var3, " \"<>^`{}|/\\?#", var5, false, false, false, null, 240, null
         );
         if (!this.isDot(var1)) {
            if (this.isDotDot(var1)) {
               this.pop();
            } else {
               if (this.encodedPathSegments.get(this.encodedPathSegments.size() - 1).length() == 0) {
                  this.encodedPathSegments.set(this.encodedPathSegments.size() - 1, var1);
               } else {
                  this.encodedPathSegments.add(var1);
               }

               if (var4) {
                  this.encodedPathSegments.add("");
               }
            }
         }
      }

      private fun removeAllCanonicalQueryParameters(canonicalName: String) {
         if (this.encodedQueryNamesAndValues == null) {
            Intrinsics.throwNpe();
         }

         val var7: IntProgression = RangesKt.step(RangesKt.downTo(this.encodedQueryNamesAndValues.size() - 2, 0), 2);
         var var2: Int = var7.getFirst();
         val var3: Int = var7.getLast();
         val var4: Int = var7.getStep();
         if (if (var4 >= 0) var2 <= var3 else var2 >= var3) {
            while (true) {
               if (this.encodedQueryNamesAndValues == null) {
                  Intrinsics.throwNpe();
               }

               if (var1 == this.encodedQueryNamesAndValues.get(var2)) {
                  if (this.encodedQueryNamesAndValues == null) {
                     Intrinsics.throwNpe();
                  }

                  this.encodedQueryNamesAndValues.remove(var2 + 1);
                  if (this.encodedQueryNamesAndValues == null) {
                     Intrinsics.throwNpe();
                  }

                  this.encodedQueryNamesAndValues.remove(var2);
                  if (this.encodedQueryNamesAndValues == null) {
                     Intrinsics.throwNpe();
                  }

                  if (this.encodedQueryNamesAndValues.isEmpty()) {
                     val var6: java.util.List = null as java.util.List;
                     this.encodedQueryNamesAndValues = null;
                     return;
                  }
               }

               if (var2 == var3) {
                  break;
               }

               var2 += var4;
            }
         }
      }

      private fun resolvePath(input: String, startPos: Int, limit: Int) {
         if (var2 != var3) {
            var var4: Int = var1.charAt(var2);
            if (var4 != 47 && var4 != 92) {
               this.encodedPathSegments.set(this.encodedPathSegments.size() - 1, "");
            } else {
               this.encodedPathSegments.clear();
               this.encodedPathSegments.add("");
               var2++;
            }

            while (var2 < var3) {
               var4 = Util.delimiterOffset(var1, "/\\", var2, var3);
               val var5: Boolean;
               if (var4 < var3) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               this.push(var1, var2, var4, var5, true);
               var2 = var4;
               if (var5) {
                  var2 = var4 + 1;
               }
            }
         }
      }

      public fun addEncodedPathSegment(encodedPathSegment: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "encodedPathSegment");
         val var2: HttpUrl.Builder = this;
         this.push(var1, 0, var1.length(), false, true);
         return this;
      }

      public fun addEncodedPathSegments(encodedPathSegments: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "encodedPathSegments");
         return this.addPathSegments(var1, true);
      }

      public fun addEncodedQueryParameter(encodedName: String, encodedValue: String?): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "encodedName");
         val var3: HttpUrl.Builder = this;
         if (this.encodedQueryNamesAndValues == null) {
            this.encodedQueryNamesAndValues = new ArrayList<>();
         }

         if (this.encodedQueryNamesAndValues == null) {
            Intrinsics.throwNpe();
         }

         this.encodedQueryNamesAndValues
            .add(HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " \"'<>#&=", true, false, true, false, null, 211, null));
         if (this.encodedQueryNamesAndValues == null) {
            Intrinsics.throwNpe();
         }

         if (var2 != null) {
            var1 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var2, 0, 0, " \"'<>#&=", true, false, true, false, null, 211, null);
         } else {
            var1 = null;
         }

         this.encodedQueryNamesAndValues.add(var1);
         return this;
      }

      public fun addPathSegment(pathSegment: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "pathSegment");
         val var2: HttpUrl.Builder = this;
         this.push(var1, 0, var1.length(), false, false);
         return this;
      }

      public fun addPathSegments(pathSegments: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "pathSegments");
         return this.addPathSegments(var1, false);
      }

      public fun addQueryParameter(name: String, value: String?): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         val var3: HttpUrl.Builder = this;
         if (this.encodedQueryNamesAndValues == null) {
            this.encodedQueryNamesAndValues = new ArrayList<>();
         }

         if (this.encodedQueryNamesAndValues == null) {
            Intrinsics.throwNpe();
         }

         this.encodedQueryNamesAndValues
            .add(
               HttpUrl.Companion.canonicalize$okhttp$default(
                  HttpUrl.Companion, var1, 0, 0, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, false, null, 219, null
               )
            );
         if (this.encodedQueryNamesAndValues == null) {
            Intrinsics.throwNpe();
         }

         if (var2 != null) {
            var1 = HttpUrl.Companion.canonicalize$okhttp$default(
               HttpUrl.Companion, var2, 0, 0, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, false, null, 219, null
            );
         } else {
            var1 = null;
         }

         this.encodedQueryNamesAndValues.add(var1);
         return this;
      }

      public fun build(): HttpUrl {
         val var7: java.lang.String = this.scheme;
         if (this.scheme == null) {
            throw (new IllegalStateException("scheme == null")) as java.lang.Throwable;
         } else {
            val var6: java.lang.String = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, this.encodedUsername, 0, 0, false, 7, null);
            val var5: java.lang.String = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, this.encodedPassword, 0, 0, false, 7, null);
            val var4: java.lang.String = this.host;
            if (this.host == null) {
               throw (new IllegalStateException("host == null")) as java.lang.Throwable;
            } else {
               val var1: Int = this.effectivePort();
               val var3: java.lang.Iterable = this.encodedPathSegments;
               val var2: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(this.encodedPathSegments, 10));

               for (java.lang.String var8 : var3) {
                  var2.add(HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, var8, 0, 0, false, 7, null));
               }

               val var18: java.util.List = var2 as java.util.List;
               var var17: java.lang.String = null;
               val var15: java.util.List;
               if (this.encodedQueryNamesAndValues != null) {
                  val var12: java.lang.Iterable = this.encodedQueryNamesAndValues;
                  val var9: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(this.encodedQueryNamesAndValues, 10));

                  for (java.lang.String var13 : var12) {
                     val var14: java.lang.String;
                     if (var13 != null) {
                        var14 = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, var13, 0, 0, true, 3, null);
                     } else {
                        var14 = null;
                     }

                     var9.add(var14);
                  }

                  var15 = var9 as java.util.List;
               } else {
                  var15 = null;
               }

               if (this.encodedFragment != null) {
                  var17 = HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, this.encodedFragment, 0, 0, false, 7, null);
               }

               return new HttpUrl(var7, var6, var5, var4, var1, var18, var15, var17, this.toString());
            }
         }
      }

      public fun encodedFragment(encodedFragment: String?): okhttp3.HttpUrl.Builder {
         val var2: HttpUrl.Builder = this;
         if (var1 != null) {
            var1 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, "", true, false, false, true, null, 179, null);
         } else {
            var1 = null;
         }

         this.encodedFragment = var1;
         return this;
      }

      public fun encodedPassword(encodedPassword: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "encodedPassword");
         val var2: HttpUrl.Builder = this;
         this.encodedPassword = HttpUrl.Companion.canonicalize$okhttp$default(
            HttpUrl.Companion, var1, 0, 0, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 243, null
         );
         return this;
      }

      public fun encodedPath(encodedPath: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "encodedPath");
         val var2: HttpUrl.Builder = this;
         if (StringsKt.startsWith$default(var1, "/", false, 2, null)) {
            this.resolvePath(var1, 0, var1.length());
            return this;
         } else {
            val var3: StringBuilder = new StringBuilder("unexpected encodedPath: ");
            var3.append(var1);
            throw (new IllegalArgumentException(var3.toString().toString())) as java.lang.Throwable;
         }
      }

      public fun encodedQuery(encodedQuery: String?): okhttp3.HttpUrl.Builder {
         label12: {
            val var2: HttpUrl.Builder = this;
            if (var1 != null) {
               var1 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " \"'<>#", true, false, true, false, null, 211, null);
               if (var1 != null) {
                  var4 = HttpUrl.Companion.toQueryNamesAndValues$okhttp(var1);
                  break label12;
               }
            }

            var4 = null;
         }

         this.encodedQueryNamesAndValues = var4;
         return this;
      }

      public fun encodedUsername(encodedUsername: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "encodedUsername");
         val var2: HttpUrl.Builder = this;
         this.encodedUsername = HttpUrl.Companion.canonicalize$okhttp$default(
            HttpUrl.Companion, var1, 0, 0, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 243, null
         );
         return this;
      }

      public fun fragment(fragment: String?): okhttp3.HttpUrl.Builder {
         val var2: HttpUrl.Builder = this;
         if (var1 != null) {
            var1 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, "", false, false, false, true, null, 187, null);
         } else {
            var1 = null;
         }

         this.encodedFragment = var1;
         return this;
      }

      public fun host(host: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "host");
         val var2: HttpUrl.Builder = this;
         val var3: java.lang.String = HostnamesKt.toCanonicalHost(HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, var1, 0, 0, false, 7, null));
         if (var3 != null) {
            this.host = var3;
            return this;
         } else {
            val var4: StringBuilder = new StringBuilder("unexpected host: ");
            var4.append(var1);
            throw (new IllegalArgumentException(var4.toString())) as java.lang.Throwable;
         }
      }

      internal fun parse(base: HttpUrl?, input: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var2, "input");
         var var3: Int = Util.indexOfFirstNonAsciiWhitespace$default(var2, 0, 0, 3, null);
         val var4: Int = Util.indexOfLastNonAsciiWhitespace$default(var2, var3, 0, 2, null);
         val var10: HttpUrl.Builder.Companion = Companion;
         var var5: Int = HttpUrl.Builder.Companion.access$schemeDelimiterOffset(Companion, var2, var3, var4);
         if (var5 != -1) {
            if (StringsKt.startsWith(var2, "https:", var3, true)) {
               this.scheme = "https";
               var3 += 6;
            } else {
               if (!StringsKt.startsWith(var2, "http:", var3, true)) {
                  val var11: StringBuilder = new StringBuilder("Expected URL scheme 'http' or 'https' but was '");
                  var2 = var2.substring(0, var5);
                  Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                  var11.append(var2);
                  var11.append("'");
                  throw (new IllegalArgumentException(var11.toString())) as java.lang.Throwable;
               }

               this.scheme = "http";
               var3 += 5;
            }
         } else {
            if (var1 == null) {
               throw (new IllegalArgumentException("Expected URL scheme 'http' or 'https' but no colon was found")) as java.lang.Throwable;
            }

            this.scheme = var1.scheme();
         }

         var5 = HttpUrl.Builder.Companion.access$slashCount(var10, var2, var3, var4);
         if (var5 < 2 && var1 != null && var1.scheme() == this.scheme) {
            this.encodedUsername = var1.encodedUsername();
            this.encodedPassword = var1.encodedPassword();
            this.host = var1.host();
            this.port = var1.port();
            this.encodedPathSegments.clear();
            this.encodedPathSegments.addAll(var1.encodedPathSegments());
            if (var3 == var4 || var2.charAt(var3) == '#') {
               this.encodedQuery(var1.encodedQuery());
            }
         } else {
            var var6: Int = var3 + var5;
            var var16: Int = 0;
            var var22: Int = 0;

            while (true) {
               val var8: Int = Util.delimiterOffset(var2, "@/\\?#", var6, var4);
               var var7: Int;
               if (var8 != var4) {
                  var7 = var2.charAt(var8);
               } else {
                  var7 = -1;
               }

               if (var7 == -1 || var7 == 35 || var7 == 47 || var7 == 92 || var7 == 63) {
                  val var30: HttpUrl.Builder.Companion = Companion;
                  var22 = HttpUrl.Builder.Companion.access$portColonOffset(Companion, var2, var6, var8);
                  var7 = var22 + 1;
                  if (var22 + 1 < var8) {
                     this.host = HostnamesKt.toCanonicalHost(
                        HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, var2, var6, var22, false, 4, null)
                     );
                     var16 = HttpUrl.Builder.Companion.access$parsePort(var30, var2, var7, var8);
                     this.port = var16;
                     val var18: Boolean;
                     if (var16 != -1) {
                        var18 = true;
                     } else {
                        var18 = false;
                     }

                     if (!var18) {
                        val var31: StringBuilder = new StringBuilder("Invalid URL port: \"");
                        var2 = var2.substring(var7, var8);
                        Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        var31.append(var2);
                        var31.append('"');
                        throw (new IllegalArgumentException(var31.toString().toString())) as java.lang.Throwable;
                     }
                  } else {
                     this.host = HostnamesKt.toCanonicalHost(
                        HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, var2, var6, var22, false, 4, null)
                     );
                     if (this.scheme == null) {
                        Intrinsics.throwNpe();
                     }

                     this.port = HttpUrl.Companion.defaultPort(this.scheme);
                  }

                  val var19: Boolean;
                  if (this.host != null) {
                     var19 = true;
                  } else {
                     var19 = false;
                  }

                  if (!var19) {
                     val var33: StringBuilder = new StringBuilder("Invalid URL host: \"");
                     var2 = var2.substring(var6, var22);
                     Intrinsics.checkExpressionValueIsNotNull(var2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                     var33.append(var2);
                     var33.append('"');
                     throw (new IllegalArgumentException(var33.toString().toString())) as java.lang.Throwable;
                  }

                  var3 = var8;
                  break;
               }

               if (var7 == 64) {
                  if (!var16) {
                     var7 = Util.delimiterOffset(var2, ':', var6, var8);
                     val var34: java.lang.String = HttpUrl.Companion.canonicalize$okhttp$default(
                        HttpUrl.Companion, var2, var6, var7, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 240, null
                     );
                     var var27: java.lang.String = var34;
                     if (var22) {
                        val var28: StringBuilder = new StringBuilder();
                        var28.append(this.encodedUsername);
                        var28.append("%40");
                        var28.append(var34);
                        var27 = var28.toString();
                     }

                     this.encodedUsername = var27;
                     if (var7 != var8) {
                        this.encodedPassword = HttpUrl.Companion.canonicalize$okhttp$default(
                           HttpUrl.Companion, var2, var7 + 1, var8, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 240, null
                        );
                        var16 = 1;
                     }

                     var22 = 1;
                  } else {
                     val var29: StringBuilder = new StringBuilder();
                     var29.append(this.encodedPassword);
                     var29.append("%40");
                     var29.append(
                        HttpUrl.Companion.canonicalize$okhttp$default(
                           HttpUrl.Companion, var2, var6, var8, " \"':;<=>@[]^`{}|/\\?#", true, false, false, false, null, 240, null
                        )
                     );
                     this.encodedPassword = var29.toString();
                  }

                  var6 = var8 + 1;
               }
            }
         }

         var5 = Util.delimiterOffset(var2, "?#", var3, var4);
         this.resolvePath(var2, var3, var5);
         var3 = var5;
         if (var5 < var4) {
            var3 = var5;
            if (var2.charAt(var5) == '?') {
               var3 = Util.delimiterOffset(var2, '#', var5, var4);
               this.encodedQueryNamesAndValues = HttpUrl.Companion
                  .toQueryNamesAndValues$okhttp(
                     HttpUrl.Companion.canonicalize$okhttp$default(
                        HttpUrl.Companion, var2, var5 + 1, var3, " \"'<>#", true, false, true, false, null, 208, null
                     )
                  );
            }
         }

         if (var3 < var4 && var2.charAt(var3) == '#') {
            this.encodedFragment = HttpUrl.Companion.canonicalize$okhttp$default(
               HttpUrl.Companion, var2, var3 + 1, var4, "", true, false, false, true, null, 176, null
            );
         }

         return this;
      }

      public fun password(password: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "password");
         val var2: HttpUrl.Builder = this;
         this.encodedPassword = HttpUrl.Companion.canonicalize$okhttp$default(
            HttpUrl.Companion, var1, 0, 0, " \"':;<=>@[]^`{}|/\\?#", false, false, false, false, null, 251, null
         );
         return this;
      }

      public fun port(port: Int): okhttp3.HttpUrl.Builder {
         val var3: HttpUrl.Builder = this;
         var var2: Boolean = true;
         if (1 > var1 || 65535 < var1) {
            var2 = false;
         }

         if (var2) {
            this.port = var1;
            return this;
         } else {
            val var4: StringBuilder = new StringBuilder("unexpected port: ");
            var4.append(var1);
            throw (new IllegalArgumentException(var4.toString().toString())) as java.lang.Throwable;
         }
      }

      public fun query(query: String?): okhttp3.HttpUrl.Builder {
         label12: {
            val var2: HttpUrl.Builder = this;
            if (var1 != null) {
               var1 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " \"'<>#", false, false, true, false, null, 219, null);
               if (var1 != null) {
                  var4 = HttpUrl.Companion.toQueryNamesAndValues$okhttp(var1);
                  break label12;
               }
            }

            var4 = null;
         }

         this.encodedQueryNamesAndValues = var4;
         return this;
      }

      internal fun reencodeForUri(): okhttp3.HttpUrl.Builder {
         val var4: HttpUrl.Builder = this;
         val var11: java.lang.String;
         if (this.host != null) {
            val var10: java.lang.CharSequence = this.host;
            var11 = new Regex("[\"<>^`{|}]").replace(var10, "");
         } else {
            var11 = null;
         }

         this.host = var11;
         var var3: Int = this.encodedPathSegments.size();

         for (int var1 = 0; var1 < var3; var1++) {
            this.encodedPathSegments
               .set(
                  var1,
                  HttpUrl.Companion.canonicalize$okhttp$default(
                     HttpUrl.Companion, this.encodedPathSegments.get(var1), 0, 0, "[]", true, true, false, false, null, 227, null
                  )
               );
         }

         val var6: java.util.List = this.encodedQueryNamesAndValues;
         if (this.encodedQueryNamesAndValues != null) {
            var3 = this.encodedQueryNamesAndValues.size();

            for (int var7 = 0; var7 < var3; var7++) {
               val var12: java.lang.String = var6.get(var7) as java.lang.String;
               val var13: java.lang.String;
               if (var12 != null) {
                  var13 = HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var12, 0, 0, "\\^`{|}", true, true, true, false, null, 195, null);
               } else {
                  var13 = null;
               }

               var6.set(var7, var13);
            }
         }

         var var14: java.lang.String = null;
         if (this.encodedFragment != null) {
            var14 = HttpUrl.Companion.canonicalize$okhttp$default(
               HttpUrl.Companion, this.encodedFragment, 0, 0, " \"#<>\\^`{|}", true, true, false, true, null, 163, null
            );
         }

         this.encodedFragment = var14;
         return this;
      }

      public fun removeAllEncodedQueryParameters(encodedName: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "encodedName");
         val var2: HttpUrl.Builder = this;
         if (this.encodedQueryNamesAndValues == null) {
            return this;
         } else {
            this.removeAllCanonicalQueryParameters(
               HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, 0, 0, " \"'<>#&=", true, false, true, false, null, 211, null)
            );
            return this;
         }
      }

      public fun removeAllQueryParameters(name: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         val var2: HttpUrl.Builder = this;
         if (this.encodedQueryNamesAndValues == null) {
            return this;
         } else {
            this.removeAllCanonicalQueryParameters(
               HttpUrl.Companion.canonicalize$okhttp$default(
                  HttpUrl.Companion, var1, 0, 0, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, false, null, 219, null
               )
            );
            return this;
         }
      }

      public fun removePathSegment(index: Int): okhttp3.HttpUrl.Builder {
         val var2: HttpUrl.Builder = this;
         this.encodedPathSegments.remove(var1);
         if (this.encodedPathSegments.isEmpty()) {
            this.encodedPathSegments.add("");
         }

         return this;
      }

      public fun scheme(scheme: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "scheme");
         val var2: HttpUrl.Builder = this;
         if (StringsKt.equals(var1, "http", true)) {
            this.scheme = "http";
         } else {
            if (!StringsKt.equals(var1, "https", true)) {
               val var3: StringBuilder = new StringBuilder("unexpected scheme: ");
               var3.append(var1);
               throw (new IllegalArgumentException(var3.toString())) as java.lang.Throwable;
            }

            this.scheme = "https";
         }

         return this;
      }

      public fun setEncodedPathSegment(index: Int, encodedPathSegment: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var2, "encodedPathSegment");
         val var3: HttpUrl.Builder = this;
         val var5: java.lang.String = HttpUrl.Companion.canonicalize$okhttp$default(
            HttpUrl.Companion, var2, 0, 0, " \"<>^`{}|/\\?#", true, false, false, false, null, 243, null
         );
         this.encodedPathSegments.set(var1, var5);
         val var4: Boolean;
         if (!this.isDot(var5) && !this.isDotDot(var5)) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            return this;
         } else {
            val var6: StringBuilder = new StringBuilder("unexpected path segment: ");
            var6.append(var2);
            throw (new IllegalArgumentException(var6.toString().toString())) as java.lang.Throwable;
         }
      }

      public fun setEncodedQueryParameter(encodedName: String, encodedValue: String?): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "encodedName");
         val var3: HttpUrl.Builder = this;
         this.removeAllEncodedQueryParameters(var1);
         this.addEncodedQueryParameter(var1, var2);
         return this;
      }

      public fun setPathSegment(index: Int, pathSegment: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var2, "pathSegment");
         val var4: HttpUrl.Builder = this;
         val var5: java.lang.String = HttpUrl.Companion.canonicalize$okhttp$default(
            HttpUrl.Companion, var2, 0, 0, " \"<>^`{}|/\\?#", false, false, false, false, null, 251, null
         );
         val var3: Boolean;
         if (!this.isDot(var5) && !this.isDotDot(var5)) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            this.encodedPathSegments.set(var1, var5);
            return this;
         } else {
            val var6: StringBuilder = new StringBuilder("unexpected path segment: ");
            var6.append(var2);
            throw (new IllegalArgumentException(var6.toString().toString())) as java.lang.Throwable;
         }
      }

      public fun setQueryParameter(name: String, value: String?): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         val var3: HttpUrl.Builder = this;
         this.removeAllQueryParameters(var1);
         this.addQueryParameter(var1, var2);
         return this;
      }

      public override fun toString(): String {
         val var2: StringBuilder = new StringBuilder();
         if (this.scheme != null) {
            var2.append(this.scheme);
            var2.append("://");
         } else {
            var2.append("//");
         }

         if (this.encodedUsername.length() > 0 || this.encodedPassword.length() > 0) {
            var2.append(this.encodedUsername);
            if (this.encodedPassword.length() > 0) {
               var2.append(':');
               var2.append(this.encodedPassword);
            }

            var2.append('@');
         }

         if (this.host != null) {
            if (this.host == null) {
               Intrinsics.throwNpe();
            }

            if (StringsKt.contains$default(this.host, ':', false, 2, null)) {
               var2.append('[');
               var2.append(this.host);
               var2.append(']');
            } else {
               var2.append(this.host);
            }
         }

         label54:
         if (this.port != -1 || this.scheme != null) {
            val var1: Int = this.effectivePort();
            if (this.scheme != null) {
               if (this.scheme == null) {
                  Intrinsics.throwNpe();
               }

               if (var1 == HttpUrl.Companion.defaultPort(this.scheme)) {
                  break label54;
               }
            }

            var2.append(':');
            var2.append(var1);
         }

         HttpUrl.Companion.toPathString$okhttp(this.encodedPathSegments, var2);
         if (this.encodedQueryNamesAndValues != null) {
            var2.append('?');
            if (this.encodedQueryNamesAndValues == null) {
               Intrinsics.throwNpe();
            }

            HttpUrl.Companion.toQueryString$okhttp(this.encodedQueryNamesAndValues, var2);
         }

         if (this.encodedFragment != null) {
            var2.append('#');
            var2.append(this.encodedFragment);
         }

         val var5: java.lang.String = var2.toString();
         Intrinsics.checkExpressionValueIsNotNull(var5, "StringBuilder().apply(builderAction).toString()");
         return var5;
      }

      public fun username(username: String): okhttp3.HttpUrl.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "username");
         val var2: HttpUrl.Builder = this;
         this.encodedUsername = HttpUrl.Companion.canonicalize$okhttp$default(
            HttpUrl.Companion, var1, 0, 0, " \"':;<=>@[]^`{}|/\\?#", false, false, false, false, null, 251, null
         );
         return this;
      }

      public companion object {
         internal const val INVALID_HOST: String

         private fun parsePort(input: String, pos: Int, limit: Int): Int {
            try {
               var3 = Integer.parseInt(
                  HttpUrl.Companion.canonicalize$okhttp$default(HttpUrl.Companion, var1, var2, var3, "", false, false, false, false, null, 248, null)
               );
            } catch (var5: NumberFormatException) {
               return -1;
            }

            if (1 > var3) {
               var2 = -1;
            } else {
               var2 = -1;
               if (65535 >= var3) {
                  var2 = var3;
               }
            }

            return var2;
         }

         private fun portColonOffset(input: String, pos: Int, limit: Int): Int {
            while (var2 < var3) {
               val var5: Char = var1.charAt(var2);
               if (var5 == ':') {
                  return var2;
               }

               var var4: Int = var2;
               if (var5 != '[') {
                  var4 = var2;
               } else {
                  while (true) {
                     var2 = var4 + 1;
                     var4 = var4 + 1;
                     if (var2 >= var3) {
                        break;
                     }

                     var4 = var2;
                     if (var1.charAt(var2) == ']') {
                        var4 = var2;
                        break;
                     }
                  }
               }

               var2 = var4 + 1;
            }

            return var3;
         }

         private fun schemeDelimiterOffset(input: String, pos: Int, limit: Int): Int {
            val var5: Byte = -1;
            if (var3 - var2 < 2) {
               return -1;
            } else {
               var var4: Int;
               label64: {
                  val var6: Char = var1.charAt(var2);
                  if (var6 >= 'a') {
                     var4 = var2;
                     if (var6 <= 'z') {
                        break label64;
                     }
                  }

                  if (var6 < 'A') {
                     return -1;
                  }

                  var4 = var2;
                  if (var6 > 'Z') {
                     return -1;
                  }
               }

               while (true) {
                  var2 = var4 + 1;
                  var4 = var5;
                  if (var2 >= var3) {
                     break;
                  }

                  val var9: Char = var1.charAt(var2);
                  if (('a' > var9 || 'z' < var9) && ('A' > var9 || 'Z' < var9) && ('0' > var9 || '9' < var9) && var9 != '+' && var9 != '-' && var9 != '.') {
                     var4 = var5;
                     if (var9 == ':') {
                        var4 = var2;
                     }
                     break;
                  }

                  var4 = var2;
               }

               return var4;
            }
         }

         private fun String.slashCount(pos: Int, limit: Int): Int {
            var var4: Int = 0;

            while (var2 < var3) {
               val var5: Char = var1.charAt(var2);
               if (var5 != '\\' && var5 != '/') {
                  break;
               }

               var4++;
               var2++;
            }

            return var4;
         }
      }
   }

   public companion object {
      internal const val FORM_ENCODE_SET: String
      internal const val FRAGMENT_ENCODE_SET: String
      internal const val FRAGMENT_ENCODE_SET_URI: String
      private final val HEX_DIGITS: CharArray
      internal const val PASSWORD_ENCODE_SET: String
      internal const val PATH_SEGMENT_ENCODE_SET: String
      internal const val PATH_SEGMENT_ENCODE_SET_URI: String
      internal const val QUERY_COMPONENT_ENCODE_SET: String
      internal const val QUERY_COMPONENT_ENCODE_SET_URI: String
      internal const val QUERY_COMPONENT_REENCODE_SET: String
      internal const val QUERY_ENCODE_SET: String
      internal const val USERNAME_ENCODE_SET: String

      private fun String.isPercentEncoded(pos: Int, limit: Int): Boolean {
         return var2 + 2 < var3
            && var1.charAt(var2) == '%'
            && Util.parseHexDigit(var1.charAt(var2 + 1)) != -1
            && Util.parseHexDigit(var1.charAt(var2 + 2)) != -1;
      }

      private fun Buffer.writeCanonicalized(
         input: String,
         pos: Int,
         limit: Int,
         encodeSet: String,
         alreadyEncoded: Boolean,
         strict: Boolean,
         plusIsSpace: Boolean,
         unicodeAllowed: Boolean,
         charset: Charset?
      ) {
         var var13: Buffer = null as Buffer;
         var13 = null;

         while (var3 < var4) {
            if (var2 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var var12: Int;
            var var15: Buffer;
            label93: {
               var12 = var2.codePointAt(var3);
               if (var6) {
                  var15 = var13;
                  if (var12 == 9) {
                     break label93;
                  }

                  var15 = var13;
                  if (var12 == 10) {
                     break label93;
                  }

                  var15 = var13;
                  if (var12 == 12) {
                     break label93;
                  }

                  var15 = var13;
                  if (var12 == 13) {
                     break label93;
                  }
               }

               if (var12 == 43 && var8) {
                  val var18: java.lang.String;
                  if (var6) {
                     var18 = "+";
                  } else {
                     var18 = "%2B";
                  }

                  var1.writeUtf8(var18);
                  var15 = var13;
               } else {
                  label100: {
                     label101: {
                        if (var12 >= 32 && var12 != 127 && (var12 < 128 || var9) && !StringsKt.contains$default(var5, (char)var12, false, 2, null)) {
                           if (var12 != 37) {
                              break label101;
                           }

                           if (var6) {
                              if (!var7) {
                                 break label101;
                              }

                              val var14: HttpUrl.Companion = this;
                              if (this.isPercentEncoded(var2, var3, var4)) {
                                 break label101;
                              }
                           }
                        }

                        var var17: Buffer = var13;
                        if (var13 == null) {
                           var17 = new Buffer();
                        }

                        if (var10 != null && !(var10 == StandardCharsets.UTF_8)) {
                           var17.writeString(var2, var3, Character.charCount(var12) + var3, var10);
                        } else {
                           var17.writeUtf8CodePoint(var12);
                        }

                        while (true) {
                           var15 = var17;
                           if (var17.exhausted()) {
                              break label100;
                           }

                           val var11: Byte = var17.readByte();
                           var1.writeByte(37);
                           var1.writeByte(HttpUrl.access$getHEX_DIGITS$cp()[(var11 and 255) shr 4 and 15]);
                           var1.writeByte(HttpUrl.access$getHEX_DIGITS$cp()[var11 and 15]);
                        }
                     }

                     var1.writeUtf8CodePoint(var12);
                     var15 = var13;
                  }
               }
            }

            var3 += Character.charCount(var12);
            var13 = var15;
         }
      }

      private fun Buffer.writePercentDecoded(encoded: String, pos: Int, limit: Int, plusIsSpace: Boolean) {
         while (var3 < var4) {
            if (var2 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var var9: Int;
            label25: {
               var9 = var2.codePointAt(var3);
               if (var9 == 37) {
                  val var8: Int = var3 + 2;
                  if (var3 + 2 < var4) {
                     val var6: Int = Util.parseHexDigit(var2.charAt(var3 + 1));
                     val var7: Int = Util.parseHexDigit(var2.charAt(var8));
                     if (var6 != -1 && var7 != -1) {
                        var1.writeByte((var6 shl 4) + var7);
                        var3 = Character.charCount(var9) + var8;
                        continue;
                     }
                     break label25;
                  }
               }

               if (var9 == 43 && var5) {
                  var1.writeByte(32);
                  var3++;
                  continue;
               }
            }

            var1.writeUtf8CodePoint(var9);
            var3 += Character.charCount(var9);
         }
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "url.toHttpUrl()", imports = ["okhttp3.HttpUrl.Companion.toHttpUrl"]))
      public fun get(url: String): HttpUrl {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         val var2: HttpUrl.Companion = this;
         return this.get(var1);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "uri.toHttpUrlOrNull()", imports = ["okhttp3.HttpUrl.Companion.toHttpUrlOrNull"]))
      public fun get(uri: URI): HttpUrl? {
         Intrinsics.checkParameterIsNotNull(var1, "uri");
         val var2: HttpUrl.Companion = this;
         return this.get(var1);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "url.toHttpUrlOrNull()", imports = ["okhttp3.HttpUrl.Companion.toHttpUrlOrNull"]))
      public fun get(url: URL): HttpUrl? {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         val var2: HttpUrl.Companion = this;
         return this.get(var1);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to extension function", replaceWith = @ReplaceWith(expression = "url.toHttpUrlOrNull()", imports = ["okhttp3.HttpUrl.Companion.toHttpUrlOrNull"]))
      public fun parse(url: String): HttpUrl? {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         val var2: HttpUrl.Companion = this;
         return this.parse(var1);
      }

      internal fun String.canonicalize(
         pos: Int = ...,
         limit: Int = ...,
         encodeSet: String,
         alreadyEncoded: Boolean = ...,
         strict: Boolean = ...,
         plusIsSpace: Boolean = ...,
         unicodeAllowed: Boolean = ...,
         charset: Charset? = ...
      ): String {
         Intrinsics.checkParameterIsNotNull(var1, "$this$canonicalize");
         Intrinsics.checkParameterIsNotNull(var4, "encodeSet");
         var var10: Int = var2;

         while (true) {
            if (var10 >= var3) {
               var1 = var1.substring(var2, var3);
               Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               return var1;
            }

            val var11: Int = var1.codePointAt(var10);
            if (var11 < 32 || var11 == 127 || var11 >= 128 && !var8 || StringsKt.contains$default(var4, (char)var11, false, 2, null)) {
               break;
            }

            if (var11 == 37) {
               if (!var5) {
                  break;
               }

               if (var6) {
                  val var12: HttpUrl.Companion = this;
                  if (!this.isPercentEncoded(var1, var10, var3)) {
                     break;
                  }
               }
            }

            if (var11 == 43 && var7) {
               break;
            }

            var10 += Character.charCount(var11);
         }

         val var15: Buffer = new Buffer();
         var15.writeUtf8(var1, var2, var10);
         val var13: HttpUrl.Companion = this;
         this.writeCanonicalized(var15, var1, var10, var3, var4, var5, var6, var7, var8, var9);
         return var15.readUtf8();
      }

      public fun defaultPort(scheme: String): Int {
         Intrinsics.checkParameterIsNotNull(var1, "scheme");
         val var2: Int = var1.hashCode();
         if (var2 != 3213448) {
            if (var2 == 99617003 && var1.equals("https")) {
               return 443;
            }
         } else if (var1.equals("http")) {
            return 80;
         }

         return -1;
      }

      public fun String.toHttpUrl(): HttpUrl {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toHttpUrl");
         return new HttpUrl.Builder().parse$okhttp(null, var1).build();
      }

      public fun URI.toHttpUrlOrNull(): HttpUrl? {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toHttpUrlOrNull");
         val var2: HttpUrl.Companion = this;
         val var3: java.lang.String = var1.toString();
         Intrinsics.checkExpressionValueIsNotNull(var3, "toString()");
         return this.parse(var3);
      }

      public fun URL.toHttpUrlOrNull(): HttpUrl? {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toHttpUrlOrNull");
         val var2: HttpUrl.Companion = this;
         val var3: java.lang.String = var1.toString();
         Intrinsics.checkExpressionValueIsNotNull(var3, "toString()");
         return this.parse(var3);
      }

      public fun String.toHttpUrlOrNull(): HttpUrl? {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toHttpUrlOrNull");

         try {
            val var2: HttpUrl.Companion = this;
            var4 = this.get(var1);
         } catch (var3: IllegalArgumentException) {
            var4 = null;
         }

         return var4;
      }

      internal fun String.percentDecode(pos: Int = ..., limit: Int = ..., plusIsSpace: Boolean = ...): String {
         Intrinsics.checkParameterIsNotNull(var1, "$this$percentDecode");

         for (int var5 = var2; var5 < var3; var5++) {
            val var6: Char = var1.charAt(var5);
            if (var6 == '%' || var6 == '+' && var4) {
               val var8: Buffer = new Buffer();
               var8.writeUtf8(var1, var2, var5);
               val var7: HttpUrl.Companion = this;
               this.writePercentDecoded(var8, var1, var5, var3, var4);
               return var8.readUtf8();
            }
         }

         var1 = var1.substring(var2, var3);
         Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         return var1;
      }

      internal fun List<String>.toPathString(out: StringBuilder) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toPathString");
         Intrinsics.checkParameterIsNotNull(var2, "out");
         val var4: Int = var1.size();

         for (int var3 = 0; var3 < var4; var3++) {
            var2.append('/');
            var2.append(var1.get(var3) as java.lang.String);
         }
      }

      internal fun String.toQueryNamesAndValues(): MutableList<String?> {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toQueryNamesAndValues");
         val var5: java.util.List = new ArrayList();
         var var2: Int = 0;

         while (var2 <= var1.length()) {
            val var6: java.lang.CharSequence = var1;
            var var4: Int = StringsKt.indexOf$default(var1, '&', var2, false, 4, null);
            var var3: Int = var4;
            if (var4 == -1) {
               var3 = var1.length();
            }

            var4 = StringsKt.indexOf$default(var6, '=', var2, false, 4, null);
            if (var4 != -1 && var4 <= var3) {
               val var9: java.lang.String = var1.substring(var2, var4);
               Intrinsics.checkExpressionValueIsNotNull(var9, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               var5.add(var9);
               val var10: java.lang.String = var1.substring(var4 + 1, var3);
               Intrinsics.checkExpressionValueIsNotNull(var10, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               var5.add(var10);
            } else {
               val var8: java.lang.String = var1.substring(var2, var3);
               Intrinsics.checkExpressionValueIsNotNull(var8, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               var5.add(var8);
               var5.add(null);
            }

            var2 = var3 + 1;
         }

         return var5;
      }

      internal fun List<String?>.toQueryString(out: StringBuilder) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toQueryString");
         Intrinsics.checkParameterIsNotNull(var2, "out");
         val var6: IntProgression = RangesKt.step(RangesKt.until(0, var1.size()), 2);
         var var3: Int = var6.getFirst();
         val var5: Int = var6.getLast();
         val var4: Int = var6.getStep();
         if (if (var4 >= 0) var3 <= var5 else var3 >= var5) {
            while (true) {
               val var7: java.lang.String = var1.get(var3) as java.lang.String;
               val var8: java.lang.String = var1.get(var3 + 1) as java.lang.String;
               if (var3 > 0) {
                  var2.append('&');
               }

               var2.append(var7);
               if (var8 != null) {
                  var2.append('=');
                  var2.append(var8);
               }

               if (var3 == var5) {
                  break;
               }

               var3 += var4;
            }
         }
      }
   }
}
