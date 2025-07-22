package okhttp3

import java.util.ArrayList
import java.util.Collections
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.HostnamesKt
import okhttp3.internal.Util
import okhttp3.internal.http.DatesKt
import okhttp3.internal.publicsuffix.PublicSuffixDatabase

public class Cookie private constructor(name: String,
   value: String,
   expiresAt: Long,
   domain: String,
   path: String,
   secure: Boolean,
   httpOnly: Boolean,
   persistent: Boolean,
   hostOnly: Boolean
) {
   public final val domain: String
   public final val expiresAt: Long
   public final val hostOnly: Boolean
   public final val httpOnly: Boolean
   public final val name: String
   public final val path: String
   public final val persistent: Boolean
   public final val secure: Boolean
   public final val value: String

   init {
      this.name = var1;
      this.value = var2;
      this.expiresAt = var3;
      this.domain = var5;
      this.path = var6;
      this.secure = var7;
      this.httpOnly = var8;
      this.persistent = var9;
      this.hostOnly = var10;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "domain", imports = []))
   public fun domain(): String {
      return this.domain;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "expiresAt", imports = []))
   public fun expiresAt(): Long {
      return this.expiresAt;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "hostOnly", imports = []))
   public fun hostOnly(): Boolean {
      return this.hostOnly;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "httpOnly", imports = []))
   public fun httpOnly(): Boolean {
      return this.httpOnly;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "name", imports = []))
   public fun name(): String {
      return this.name;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "path", imports = []))
   public fun path(): String {
      return this.path;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "persistent", imports = []))
   public fun persistent(): Boolean {
      return this.persistent;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "secure", imports = []))
   public fun secure(): Boolean {
      return this.secure;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "value", imports = []))
   public fun value(): String {
      return this.value;
   }

   public override operator fun equals(other: Any?): Boolean {
      return var1 is Cookie
         && (var1 as Cookie).name == this.name
         && (var1 as Cookie).value == this.value
         && (var1 as Cookie).expiresAt == this.expiresAt
         && (var1 as Cookie).domain == this.domain
         && (var1 as Cookie).path == this.path
         && (var1 as Cookie).secure == this.secure
         && (var1 as Cookie).httpOnly == this.httpOnly
         && (var1 as Cookie).persistent == this.persistent
         && (var1 as Cookie).hostOnly == this.hostOnly;
   }

   public override fun hashCode(): Int {
      return (
               (
                        (
                                 (
                                          (
                                                   (
                                                            ((527 + this.name.hashCode()) * 31 + this.value.hashCode()) * 31
                                                               + UByte$$ExternalSyntheticBackport0.m(this.expiresAt)
                                                         )
                                                         * 31
                                                      + this.domain.hashCode()
                                                )
                                                * 31
                                             + this.path.hashCode()
                                       )
                                       * 31
                                    + UByte$$ExternalSyntheticBackport0.m(this.secure)
                              )
                              * 31
                           + UByte$$ExternalSyntheticBackport0.m(this.httpOnly)
                     )
                     * 31
                  + UByte$$ExternalSyntheticBackport0.m(this.persistent)
            )
            * 31
         + UByte$$ExternalSyntheticBackport0.m(this.hostOnly);
   }

   public fun matches(url: HttpUrl): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "url");
      val var2: Boolean;
      if (this.hostOnly) {
         var2 = var1.host() == this.domain;
      } else {
         var2 = Cookie.Companion.access$domainMatch(Companion, var1.host(), this.domain);
      }

      if (!var2) {
         return false;
      } else if (!Cookie.Companion.access$pathMatch(Companion, var1, this.path)) {
         return false;
      } else {
         return !this.secure || var1.isHttps();
      }
   }

   public override fun toString(): String {
      return this.toString$okhttp(false);
   }

   internal fun toString(forObsoleteRfc2965: Boolean): String {
      val var2: StringBuilder = new StringBuilder();
      var2.append(this.name);
      var2.append('=');
      var2.append(this.value);
      if (this.persistent) {
         if (this.expiresAt == java.lang.Long.MIN_VALUE) {
            var2.append("; max-age=0");
         } else {
            var2.append("; expires=");
            var2.append(DatesKt.toHttpDateString(new Date(this.expiresAt)));
         }
      }

      if (!this.hostOnly) {
         var2.append("; domain=");
         if (var1) {
            var2.append(".");
         }

         var2.append(this.domain);
      }

      var2.append("; path=");
      var2.append(this.path);
      if (this.secure) {
         var2.append("; secure");
      }

      if (this.httpOnly) {
         var2.append("; httponly");
      }

      val var3: java.lang.String = var2.toString();
      Intrinsics.checkExpressionValueIsNotNull(var3, "toString()");
      return var3;
   }

   public class Builder {
      private final var domain: String?
      private final var expiresAt: Long = 253402300799999L
      private final var hostOnly: Boolean
      private final var httpOnly: Boolean
      private final var name: String?
      private final var path: String = "/"
      private final var persistent: Boolean
      private final var secure: Boolean
      private final var value: String?

      private fun domain(domain: String, hostOnly: Boolean): okhttp3.Cookie.Builder {
         val var3: Cookie.Builder = this;
         val var4: java.lang.String = HostnamesKt.toCanonicalHost(var1);
         if (var4 != null) {
            this.domain = var4;
            this.hostOnly = var2;
            return this;
         } else {
            val var5: StringBuilder = new StringBuilder("unexpected domain: ");
            var5.append(var1);
            throw (new IllegalArgumentException(var5.toString())) as java.lang.Throwable;
         }
      }

      public fun build(): Cookie {
         if (this.name != null) {
            if (this.value != null) {
               if (this.domain != null) {
                  return new Cookie(
                     this.name, this.value, this.expiresAt, this.domain, this.path, this.secure, this.httpOnly, this.persistent, this.hostOnly, null
                  );
               } else {
                  throw (new NullPointerException("builder.domain == null")) as java.lang.Throwable;
               }
            } else {
               throw (new NullPointerException("builder.value == null")) as java.lang.Throwable;
            }
         } else {
            throw (new NullPointerException("builder.name == null")) as java.lang.Throwable;
         }
      }

      public fun domain(domain: String): okhttp3.Cookie.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "domain");
         return this.domain(var1, false);
      }

      public fun expiresAt(expiresAt: Long): okhttp3.Cookie.Builder {
         val var5: Cookie.Builder = this;
         var var3: Long = var1;
         if (var1 <= 0L) {
            var3 = java.lang.Long.MIN_VALUE;
         }

         var1 = var3;
         if (var3 > 253402300799999L) {
            var1 = 253402300799999L;
         }

         this.expiresAt = var1;
         this.persistent = true;
         return this;
      }

      public fun hostOnlyDomain(domain: String): okhttp3.Cookie.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "domain");
         return this.domain(var1, true);
      }

      public fun httpOnly(): okhttp3.Cookie.Builder {
         val var1: Cookie.Builder = this;
         this.httpOnly = true;
         return this;
      }

      public fun name(name: String): okhttp3.Cookie.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         val var2: Cookie.Builder = this;
         if (StringsKt.trim(var1).toString() == var1) {
            this.name = var1;
            return this;
         } else {
            throw (new IllegalArgumentException("name is not trimmed".toString())) as java.lang.Throwable;
         }
      }

      public fun path(path: String): okhttp3.Cookie.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "path");
         val var2: Cookie.Builder = this;
         if (StringsKt.startsWith$default(var1, "/", false, 2, null)) {
            this.path = var1;
            return this;
         } else {
            throw (new IllegalArgumentException("path must start with '/'".toString())) as java.lang.Throwable;
         }
      }

      public fun secure(): okhttp3.Cookie.Builder {
         val var1: Cookie.Builder = this;
         this.secure = true;
         return this;
      }

      public fun value(value: String): okhttp3.Cookie.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "value");
         val var2: Cookie.Builder = this;
         if (StringsKt.trim(var1).toString() == var1) {
            this.value = var1;
            return this;
         } else {
            throw (new IllegalArgumentException("value is not trimmed".toString())) as java.lang.Throwable;
         }
      }
   }

   public companion object {
      private final val DAY_OF_MONTH_PATTERN: Pattern
      private final val MONTH_PATTERN: Pattern
      private final val TIME_PATTERN: Pattern
      private final val YEAR_PATTERN: Pattern

      private fun dateCharacterOffset(input: String, pos: Int, limit: Int, invert: Boolean): Int {
         while (var2 < var3) {
            val var5: Char = var1.charAt(var2);
            val var6: Boolean;
            if ((var5 >= ' ' || var5 == '\t')
               && var5 < 127
               && ('0' > var5 || '9' < var5)
               && ('a' > var5 || 'z' < var5)
               && ('A' > var5 || 'Z' < var5)
               && var5 != ':') {
               var6 = false;
            } else {
               var6 = true;
            }

            if (var6 == (var4 xor true)) {
               return var2;
            }

            var2++;
         }

         return var3;
      }

      private fun domainMatch(urlHost: String, domain: String): Boolean {
         val var4: Boolean = var1 == var2;
         var var3: Boolean = true;
         if (var4) {
            return true;
         } else {
            if (!StringsKt.endsWith$default(var1, var2, false, 2, null)
               || var1.charAt(var1.length() - var2.length() - 1) != '.'
               || Util.canParseAsIpAddress(var1)) {
               var3 = false;
            }

            return var3;
         }
      }

      private fun parseDomain(s: String): String {
         if (!StringsKt.endsWith$default(var1, ".", false, 2, null)) {
            var1 = HostnamesKt.toCanonicalHost(StringsKt.removePrefix(var1, "."));
            if (var1 != null) {
               return var1;
            } else {
               throw (new IllegalArgumentException()) as java.lang.Throwable;
            }
         } else {
            throw (new IllegalArgumentException("Failed requirement.".toString())) as java.lang.Throwable;
         }
      }

      private fun parseExpires(s: String, pos: Int, limit: Int): Long {
         val var17: Cookie.Companion = this;
         var var15: Int = this.dateCharacterOffset(var1, var2, var3, false);
         val var30: Matcher = Cookie.access$getTIME_PATTERN$cp().matcher(var1);
         var2 = -1;
         var var8: Int = -1;
         var var7: Int = -1;
         var var5: Int = -1;
         var var6: Int = -1;
         var var4: Int = -1;

         while (var15 < var3) {
            val var16: Int = this.dateCharacterOffset(var1, var15 + 1, var3, true);
            var30.region(var15, var16);
            var var9: Int;
            var var10: Int;
            var var11: Int;
            var var12: Int;
            var var13: Int;
            var var14: Int;
            if (var8 == -1 && var30.usePattern(Cookie.access$getTIME_PATTERN$cp()).matches()) {
               var var34: java.lang.String = var30.group(1);
               Intrinsics.checkExpressionValueIsNotNull(var34, "matcher.group(1)");
               var9 = Integer.parseInt(var34);
               var34 = var30.group(2);
               Intrinsics.checkExpressionValueIsNotNull(var34, "matcher.group(2)");
               var13 = Integer.parseInt(var34);
               var34 = var30.group(3);
               Intrinsics.checkExpressionValueIsNotNull(var34, "matcher.group(3)");
               var14 = Integer.parseInt(var34);
               var10 = var2;
               var11 = var7;
               var12 = var5;
            } else if (var7 == -1 && var30.usePattern(Cookie.access$getDAY_OF_MONTH_PATTERN$cp()).matches()) {
               val var33: java.lang.String = var30.group(1);
               Intrinsics.checkExpressionValueIsNotNull(var33, "matcher.group(1)");
               var11 = Integer.parseInt(var33);
               var10 = var2;
               var9 = var8;
               var12 = var5;
               var13 = var6;
               var14 = var4;
            } else if (var5 == -1 && var30.usePattern(Cookie.access$getMONTH_PATTERN$cp()).matches()) {
               var var31: java.lang.String = var30.group(1);
               Intrinsics.checkExpressionValueIsNotNull(var31, "matcher.group(1)");
               val var19: Locale = Locale.US;
               Intrinsics.checkExpressionValueIsNotNull(Locale.US, "Locale.US");
               if (var31 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var31 = var31.toLowerCase(var19);
               Intrinsics.checkExpressionValueIsNotNull(var31, "(this as java.lang.String).toLowerCase(locale)");
               val var37: java.lang.String = Cookie.access$getMONTH_PATTERN$cp().pattern();
               Intrinsics.checkExpressionValueIsNotNull(var37, "MONTH_PATTERN.pattern()");
               var12 = StringsKt.indexOf$default(var37, var31, 0, false, 6, null) / 4;
               var10 = var2;
               var9 = var8;
               var11 = var7;
               var13 = var6;
               var14 = var4;
            } else {
               var10 = var2;
               var9 = var8;
               var11 = var7;
               var12 = var5;
               var13 = var6;
               var14 = var4;
               if (var2 == -1) {
                  var10 = var2;
                  var9 = var8;
                  var11 = var7;
                  var12 = var5;
                  var13 = var6;
                  var14 = var4;
                  if (var30.usePattern(Cookie.access$getYEAR_PATTERN$cp()).matches()) {
                     val var18: java.lang.String = var30.group(1);
                     Intrinsics.checkExpressionValueIsNotNull(var18, "matcher.group(1)");
                     var10 = Integer.parseInt(var18);
                     var14 = var4;
                     var13 = var6;
                     var12 = var5;
                     var11 = var7;
                     var9 = var8;
                  }
               }
            }

            var15 = this.dateCharacterOffset(var1, var16 + 1, var3, false);
            var2 = var10;
            var8 = var9;
            var7 = var11;
            var5 = var12;
            var6 = var13;
            var4 = var14;
         }

         if (70 > var2) {
            var3 = var2;
         } else {
            var3 = var2;
            if (99 >= var2) {
               var3 = var2 + 1900;
            }
         }

         if (var3 < 0) {
            var2 = var3;
         } else {
            var2 = var3;
            if (69 >= var3) {
               var2 = var3 + 2000;
            }
         }

         var var24: Boolean;
         if (var2 >= 1601) {
            var24 = true;
         } else {
            var24 = false;
         }

         if (var24) {
            if (var5 != -1) {
               var24 = true;
            } else {
               var24 = false;
            }

            if (!var24) {
               throw (new IllegalArgumentException("Failed requirement.".toString())) as java.lang.Throwable;
            } else {
               if (1 <= var7 && 31 >= var7) {
                  var24 = true;
               } else {
                  var24 = false;
               }

               if (!var24) {
                  throw (new IllegalArgumentException("Failed requirement.".toString())) as java.lang.Throwable;
               } else {
                  if (var8 >= 0 && 23 >= var8) {
                     var24 = true;
                  } else {
                     var24 = false;
                  }

                  if (!var24) {
                     throw (new IllegalArgumentException("Failed requirement.".toString())) as java.lang.Throwable;
                  } else {
                     if (var6 >= 0 && 59 >= var6) {
                        var24 = true;
                     } else {
                        var24 = false;
                     }

                     if (!var24) {
                        throw (new IllegalArgumentException("Failed requirement.".toString())) as java.lang.Throwable;
                     } else {
                        if (var4 >= 0 && 59 >= var4) {
                           var24 = true;
                        } else {
                           var24 = false;
                        }

                        if (var24) {
                           val var20: GregorianCalendar = new GregorianCalendar(Util.UTC);
                           var20.setLenient(false);
                           var20.set(1, var2);
                           var20.set(2, var5 - 1);
                           var20.set(5, var7);
                           var20.set(11, var8);
                           var20.set(12, var6);
                           var20.set(13, var4);
                           var20.set(14, 0);
                           return var20.getTimeInMillis();
                        } else {
                           throw (new IllegalArgumentException("Failed requirement.".toString())) as java.lang.Throwable;
                        }
                     }
                  }
               }
            }
         } else {
            throw (new IllegalArgumentException("Failed requirement.".toString())) as java.lang.Throwable;
         }
      }

      private fun parseMaxAge(s: String): Long {
         var var2: Long = java.lang.Long.MIN_VALUE;

         var var4: Long;
         try {
            var4 = java.lang.Long.parseLong(var1);
         } catch (var8: NumberFormatException) {
            if (new Regex("-?\\d+").matches(var1)) {
               if (!StringsKt.startsWith$default(var1, "-", false, 2, null)) {
                  var2 = java.lang.Long.MAX_VALUE;
               }

               return var2;
            }

            throw var8 as java.lang.Throwable;
         }

         if (var4 > 0L) {
            var2 = var4;
         }

         return var2;
      }

      private fun pathMatch(url: HttpUrl, path: String): Boolean {
         val var3: java.lang.String = var1.encodedPath();
         if (var3 == var2) {
            return true;
         } else {
            if (StringsKt.startsWith$default(var3, var2, false, 2, null)) {
               if (StringsKt.endsWith$default(var2, "/", false, 2, null)) {
                  return true;
               }

               if (var3.charAt(var2.length()) == '/') {
                  return true;
               }
            }

            return false;
         }
      }

      public fun parse(url: HttpUrl, setCookie: String): Cookie? {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         Intrinsics.checkParameterIsNotNull(var2, "setCookie");
         val var3: Cookie.Companion = this;
         return this.parse$okhttp(System.currentTimeMillis(), var1, var2);
      }

      internal fun parse(currentTimeMillis: Long, url: HttpUrl, setCookie: String): Cookie? {
         Intrinsics.checkParameterIsNotNull(var3, "url");
         Intrinsics.checkParameterIsNotNull(var4, "setCookie");
         var var6: Int = Util.delimiterOffset$default(var4, ';', 0, 0, 6, null);
         var var5: Int = Util.delimiterOffset$default(var4, '=', 0, var6, 2, null);
         if (var5 == var6) {
            return null;
         } else {
            val var29: java.lang.String = Util.trimSubstring$default(var4, 0, var5, 1, null);
            if (var29.length() != 0 && Util.indexOfControlOrNonAscii(var29) == -1) {
               val var28: java.lang.String = Util.trimSubstring(var4, var5 + 1, var6);
               if (Util.indexOfControlOrNonAscii(var28) != -1) {
                  return null;
               } else {
                  var var24: java.lang.String = null as java.lang.String;
                  var5 = var6 + 1;
                  var6 = var4.length();
                  var var25: java.lang.String = null;
                  var24 = null;
                  var var11: Long = -1L;
                  var var20: Boolean = false;
                  var var19: Boolean = false;
                  var var18: Boolean = false;
                  var var17: Boolean = true;
                  var var9: Long = 253402300799999L;

                  while (var5 < var6) {
                     val var7: Int = Util.delimiterOffset(var4, ';', var5, var6);
                     val var8: Int = Util.delimiterOffset(var4, '=', var5, var7);
                     val var30: java.lang.String = Util.trimSubstring(var4, var5, var8);
                     var var26: java.lang.String;
                     if (var8 < var7) {
                        var26 = Util.trimSubstring(var4, var8 + 1, var7);
                     } else {
                        var26 = "";
                     }

                     var var13: Long;
                     var var15: Long;
                     var var21: Boolean;
                     var var22: Boolean;
                     var var23: Boolean;
                     var var27: java.lang.String;
                     label115: {
                        if (StringsKt.equals(var30, "expires", true)) {
                           try {
                              val var51: Cookie.Companion = this;
                              var13 = this.parseExpires(var26, 0, var26.length());
                           } catch (var33: IllegalArgumentException) {
                              var26 = var25;
                              var13 = var11;
                              var22 = var20;
                              var21 = var18;
                              var23 = var17;
                              var27 = var24;
                              var15 = var9;
                              break label115;
                           }

                           var9 = var13;
                        } else {
                           if (!StringsKt.equals(var30, "max-age", true)) {
                              if (StringsKt.equals(var30, "domain", true)) {
                                 try {
                                    val var53: Cookie.Companion = this;
                                    var26 = this.parseDomain(var26);
                                 } catch (var31: IllegalArgumentException) {
                                    var26 = var25;
                                    var13 = var11;
                                    var22 = var20;
                                    var21 = var18;
                                    var23 = var17;
                                    var27 = var24;
                                    var15 = var9;
                                    break label115;
                                 }

                                 var23 = false;
                                 var13 = var11;
                                 var22 = var20;
                                 var21 = var18;
                                 var27 = var24;
                                 var15 = var9;
                              } else if (StringsKt.equals(var30, "path", true)) {
                                 var27 = var26;
                                 var26 = var25;
                                 var13 = var11;
                                 var22 = var20;
                                 var21 = var18;
                                 var23 = var17;
                                 var15 = var9;
                              } else if (StringsKt.equals(var30, "secure", true)) {
                                 var22 = true;
                                 var26 = var25;
                                 var13 = var11;
                                 var21 = var18;
                                 var23 = var17;
                                 var27 = var24;
                                 var15 = var9;
                              } else {
                                 var26 = var25;
                                 var13 = var11;
                                 var22 = var20;
                                 var21 = var18;
                                 var23 = var17;
                                 var27 = var24;
                                 var15 = var9;
                                 if (StringsKt.equals(var30, "httponly", true)) {
                                    var19 = true;
                                    var15 = var9;
                                    var27 = var24;
                                    var23 = var17;
                                    var21 = var18;
                                    var22 = var20;
                                    var13 = var11;
                                    var26 = var25;
                                 }
                              }
                              break label115;
                           }

                           try {
                              val var52: Cookie.Companion = this;
                              var13 = this.parseMaxAge(var26);
                           } catch (var32: NumberFormatException) {
                              var26 = var25;
                              var13 = var11;
                              var22 = var20;
                              var21 = var18;
                              var23 = var17;
                              var27 = var24;
                              var15 = var9;
                              break label115;
                           }

                           var11 = var13;
                        }

                        var21 = true;
                        var26 = var25;
                        var13 = var11;
                        var22 = var20;
                        var23 = var17;
                        var27 = var24;
                        var15 = var9;
                     }

                     var5 = var7 + 1;
                     var25 = var26;
                     var11 = var13;
                     var20 = var22;
                     var18 = var21;
                     var17 = var23;
                     var24 = var27;
                     var9 = var15;
                  }

                  if (var11 == java.lang.Long.MIN_VALUE) {
                     var1 = java.lang.Long.MIN_VALUE;
                  } else if (var11 != -1L) {
                     if (var11 <= 9223372036854775L) {
                        var9 = var11 * 1000;
                     } else {
                        var9 = java.lang.Long.MAX_VALUE;
                     }

                     var9 = var1 + var9;
                     if (var1 + var9 >= var1) {
                        var1 = 253402300799999L;
                        if (var9 <= 253402300799999L) {
                           var1 = var9;
                        }
                     } else {
                        var1 = 253402300799999L;
                     }
                  } else {
                     var1 = var9;
                  }

                  val var50: java.lang.String = var3.host();
                  if (var25 == null) {
                     var4 = var50;
                  } else {
                     val var37: Cookie.Companion = this;
                     if (!this.domainMatch(var50, var25)) {
                        return null;
                     }

                     var4 = var25;
                  }

                  if (var50.length() != var4.length() && PublicSuffixDatabase.Companion.get().getEffectiveTldPlusOne(var4) == null) {
                     return null;
                  } else {
                     var var35: java.lang.String;
                     if (var24 != null && StringsKt.startsWith$default(var24, "/", false, 2, null)) {
                        var35 = var24;
                     } else {
                        var24 = var3.encodedPath();
                        var5 = StringsKt.lastIndexOf$default(var24, '/', 0, false, 6, null);
                        var35 = "/";
                        if (var5 != 0) {
                           if (var24 == null) {
                              throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                           }

                           var35 = var24.substring(0, var5);
                           Intrinsics.checkExpressionValueIsNotNull(var35, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                        }
                     }

                     return new Cookie(var29, var28, var1, var4, var35, var20, var19, var18, var17, null);
                  }
               }
            } else {
               return null;
            }
         }
      }

      public fun parseAll(url: HttpUrl, headers: Headers): List<Cookie> {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         Intrinsics.checkParameterIsNotNull(var2, "headers");
         val var6: java.util.List = var2.values("Set-Cookie");
         var var9: java.util.List = null;
         var var5: java.util.List = null as java.util.List;
         val var4: Int = var6.size();
         var var3: Int = 0;

         while (var3 < var4) {
            val var10: Cookie.Companion = this;
            val var7: Cookie = this.parse(var1, var6.get(var3) as java.lang.String);
            var5 = var9;
            if (var7 != null) {
               var5 = var9;
               if (var9 == null) {
                  var5 = new ArrayList();
               }

               var5.add(var7);
            }

            var3++;
            var9 = var5;
         }

         val var8: java.util.List;
         if (var9 != null) {
            var8 = Collections.unmodifiableList(var9);
            Intrinsics.checkExpressionValueIsNotNull(var8, "Collections.unmodifiableList(cookies)");
         } else {
            var8 = CollectionsKt.emptyList();
         }

         return var8;
      }
   }
}
