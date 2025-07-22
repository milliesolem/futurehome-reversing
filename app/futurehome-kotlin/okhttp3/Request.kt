package okhttp3

import java.net.URL
import java.util.LinkedHashMap
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util
import okhttp3.internal.http.HttpMethod

public class Request internal constructor(url: HttpUrl, method: String, headers: Headers, body: RequestBody?, tags: Map<Class<*>, Any>) {
   public final val body: RequestBody?

   public final val cacheControl: CacheControl
      public final get() {
         var var1: CacheControl = this.lazyCacheControl;
         if (this.lazyCacheControl == null) {
            var1 = CacheControl.Companion.parse(this.headers);
            this.lazyCacheControl = var1;
         }

         return var1;
      }


   public final val headers: Headers

   public final val isHttps: Boolean
      public final get() {
         return this.url.isHttps();
      }


   private final var lazyCacheControl: CacheControl?
   public final val method: String
   internal final val tags: Map<Class<*>, Any>
   public final val url: HttpUrl

   init {
      Intrinsics.checkParameterIsNotNull(var1, "url");
      Intrinsics.checkParameterIsNotNull(var2, "method");
      Intrinsics.checkParameterIsNotNull(var3, "headers");
      Intrinsics.checkParameterIsNotNull(var5, "tags");
      super();
      this.url = var1;
      this.method = var2;
      this.headers = var3;
      this.body = var4;
      this.tags = var5;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "body", imports = []))
   public fun body(): RequestBody? {
      return this.body;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cacheControl", imports = []))
   public fun cacheControl(): CacheControl {
      return this.cacheControl();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "headers", imports = []))
   public fun headers(): Headers {
      return this.headers;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "method", imports = []))
   public fun method(): String {
      return this.method;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "url", imports = []))
   public fun url(): HttpUrl {
      return this.url;
   }

   public fun header(name: String): String? {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      return this.headers.get(var1);
   }

   public fun headers(name: String): List<String> {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      return this.headers.values(var1);
   }

   public fun newBuilder(): okhttp3.Request.Builder {
      return new Request.Builder(this);
   }

   public fun tag(): Any? {
      return this.tag(Object.class);
   }

   public fun <T> tag(type: Class<out T>): T? {
      Intrinsics.checkParameterIsNotNull(var1, "type");
      return (T)var1.cast(this.tags.get(var1));
   }

   public override fun toString(): String {
      val var3: StringBuilder = new StringBuilder("Request{method=");
      var3.append(this.method);
      var3.append(", url=");
      var3.append(this.url);
      if (this.headers.size() != 0) {
         var3.append(", headers=[");
         val var2: java.util.Iterator = this.headers.iterator();

         for (int var1 = 0; var2.hasNext(); var1++) {
            val var4: Any = var2.next();
            if (var1 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            val var7: java.lang.String = (var4 as Pair).component1() as java.lang.String;
            val var8: java.lang.String = (var4 as Pair).component2() as java.lang.String;
            if (var1 > 0) {
               var3.append(", ");
            }

            var3.append(var7);
            var3.append(':');
            var3.append(var8);
         }

         var3.append(']');
      }

      if (!this.tags.isEmpty()) {
         var3.append(", tags=");
         var3.append(this.tags);
      }

      var3.append('}');
      val var6: java.lang.String = var3.toString();
      Intrinsics.checkExpressionValueIsNotNull(var6, "StringBuilder().apply(builderAction).toString()");
      return var6;
   }

   public open class Builder {
      internal final var body: RequestBody?
      internal final var headers: Headers.Builder
      internal final var method: String
      internal final var tags: MutableMap<Class<*>, Any> = (new LinkedHashMap()) as java.util.Map
      internal final var url: HttpUrl?

      public constructor()  {
         this.method = "GET";
         this.headers = new Headers.Builder();
      }

      internal constructor(request: Request) : Intrinsics.checkParameterIsNotNull(var1, "request") {
         super();
         this.tags = new LinkedHashMap<>();
         this.url = var1.url();
         this.method = var1.method();
         this.body = var1.body();
         val var2: java.util.Map;
         if (var1.getTags$okhttp().isEmpty()) {
            var2 = new LinkedHashMap();
         } else {
            var2 = MapsKt.toMutableMap(var1.getTags$okhttp());
         }

         this.tags = var2;
         this.headers = var1.headers().newBuilder();
      }

      public open fun addHeader(name: String, value: String): okhttp3.Request.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: Request.Builder = this;
         this.headers.add(var1, var2);
         return this;
      }

      public open fun build(): Request {
         if (this.url != null) {
            return new Request(this.url, this.method, this.headers.build(), this.body, Util.toImmutableMap(this.tags));
         } else {
            throw (new IllegalStateException("url == null".toString())) as java.lang.Throwable;
         }
      }

      public open fun cacheControl(cacheControl: CacheControl): okhttp3.Request.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "cacheControl");
         val var2: java.lang.String = var1.toString();
         val var3: Request.Builder;
         if (var2.length() == 0) {
            var3 = this.removeHeader("Cache-Control");
         } else {
            var3 = this.header("Cache-Control", var2);
         }

         return var3;
      }

      open fun delete(): Request.Builder {
         return delete$default(this, null, 1, null);
      }

      public open fun delete(body: RequestBody? = Util.EMPTY_REQUEST): okhttp3.Request.Builder {
         return this.method("DELETE", var1);
      }

      public open fun get(): okhttp3.Request.Builder {
         return this.method("GET", null);
      }

      public open fun head(): okhttp3.Request.Builder {
         return this.method("HEAD", null);
      }

      public open fun header(name: String, value: String): okhttp3.Request.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: Request.Builder = this;
         this.headers.set(var1, var2);
         return this;
      }

      public open fun headers(headers: Headers): okhttp3.Request.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "headers");
         val var2: Request.Builder = this;
         this.headers = var1.newBuilder();
         return this;
      }

      public open fun method(method: String, body: RequestBody?): okhttp3.Request.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "method");
         val var4: Request.Builder = this;
         val var3: Boolean;
         if (var1.length() > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            if (var2 == null) {
               if (HttpMethod.requiresRequestBody(var1)) {
                  val var5: StringBuilder = new StringBuilder("method ");
                  var5.append(var1);
                  var5.append(" must have a request body.");
                  throw (new IllegalArgumentException(var5.toString().toString())) as java.lang.Throwable;
               }
            } else if (!HttpMethod.permitsRequestBody(var1)) {
               val var6: StringBuilder = new StringBuilder("method ");
               var6.append(var1);
               var6.append(" must not have a request body.");
               throw (new IllegalArgumentException(var6.toString().toString())) as java.lang.Throwable;
            }

            this.method = var1;
            this.body = var2;
            return this;
         } else {
            throw (new IllegalArgumentException("method.isEmpty() == true".toString())) as java.lang.Throwable;
         }
      }

      public open fun patch(body: RequestBody): okhttp3.Request.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "body");
         return this.method("PATCH", var1);
      }

      public open fun post(body: RequestBody): okhttp3.Request.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "body");
         return this.method("POST", var1);
      }

      public open fun put(body: RequestBody): okhttp3.Request.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "body");
         return this.method("PUT", var1);
      }

      public open fun removeHeader(name: String): okhttp3.Request.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         val var2: Request.Builder = this;
         this.headers.removeAll(var1);
         return this;
      }

      public open fun <T> tag(type: Class<in T>, tag: T?): okhttp3.Request.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "type");
         val var3: Request.Builder = this;
         if (var2 == null) {
            this.tags.remove(var1);
         } else {
            if (this.tags.isEmpty()) {
               this.tags = new LinkedHashMap<>();
            }

            val var5: java.util.Map = this.tags;
            var2 = var1.cast(var2);
            if (var2 == null) {
               Intrinsics.throwNpe();
            }

            var5.put(var1, var2);
         }

         return this;
      }

      public open fun tag(tag: Any?): okhttp3.Request.Builder {
         return this.tag(Object.class, var1);
      }

      public open fun url(url: String): okhttp3.Request.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         var var5: java.lang.String;
         if (StringsKt.startsWith(var1, "ws:", true)) {
            val var2: StringBuilder = new StringBuilder("http:");
            var1 = var1.substring(3);
            Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
            var2.append(var1);
            var5 = var2.toString();
         } else {
            var5 = var1;
            if (StringsKt.startsWith(var1, "wss:", true)) {
               val var6: StringBuilder = new StringBuilder("https:");
               var1 = var1.substring(4);
               Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
               var6.append(var1);
               var5 = var6.toString();
            }
         }

         return this.url(HttpUrl.Companion.get(var5));
      }

      public open fun url(url: URL): okhttp3.Request.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         val var2: HttpUrl.Companion = HttpUrl.Companion;
         val var3: java.lang.String = var1.toString();
         Intrinsics.checkExpressionValueIsNotNull(var3, "url.toString()");
         return this.url(var2.get(var3));
      }

      public open fun url(url: HttpUrl): okhttp3.Request.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "url");
         val var2: Request.Builder = this;
         this.url = var1;
         return this;
      }
   }
}
