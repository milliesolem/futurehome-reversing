package okhttp3

import java.nio.charset.Charset
import java.util.ArrayList
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util
import okio.Buffer
import okio.BufferedSink

public class FormBody internal constructor(encodedNames: List<String>, encodedValues: List<String>) : RequestBody {
   private final val encodedNames: List<String>
   private final val encodedValues: List<String>

   public final val size: Int
      public final get() {
         return this.encodedNames.size();
      }


   init {
      Intrinsics.checkParameterIsNotNull(var1, "encodedNames");
      Intrinsics.checkParameterIsNotNull(var2, "encodedValues");
      super();
      this.encodedNames = Util.toImmutableList(var1);
      this.encodedValues = Util.toImmutableList(var2);
   }

   private fun writeOrCountBytes(sink: BufferedSink?, countBytes: Boolean): Long {
      val var7: Buffer;
      if (var2) {
         var7 = new Buffer();
      } else {
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         var7 = var1.getBuffer();
      }

      val var4: Int = this.encodedNames.size();

      for (int var3 = 0; var3 < var4; var3++) {
         if (var3 > 0) {
            var7.writeByte(38);
         }

         var7.writeUtf8(this.encodedNames.get(var3));
         var7.writeByte(61);
         var7.writeUtf8(this.encodedValues.get(var3));
      }

      val var5: Long;
      if (var2) {
         var5 = var7.size();
         var7.clear();
      } else {
         var5 = 0L;
      }

      return var5;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "size", imports = []))
   public fun size(): Int {
      return this.size();
   }

   public override fun contentLength(): Long {
      return this.writeOrCountBytes(null, true);
   }

   public override fun contentType(): MediaType {
      return CONTENT_TYPE;
   }

   public fun encodedName(index: Int): String {
      return this.encodedNames.get(var1);
   }

   public fun encodedValue(index: Int): String {
      return this.encodedValues.get(var1);
   }

   public fun name(index: Int): String {
      return HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, this.encodedName(var1), 0, 0, true, 3, null);
   }

   public fun value(index: Int): String {
      return HttpUrl.Companion.percentDecode$okhttp$default(HttpUrl.Companion, this.encodedValue(var1), 0, 0, true, 3, null);
   }

   @Throws(java/io/IOException::class)
   public override fun writeTo(sink: BufferedSink) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      this.writeOrCountBytes(var1, false);
   }

   public class Builder  public constructor(charset: Charset? = null) {
      private final val charset: Charset?
      private final val names: MutableList<String>
      private final val values: MutableList<String>

      fun Builder() {
         this(null, 1, null);
      }

      init {
         this.charset = var1;
         this.names = new ArrayList<>();
         this.values = new ArrayList<>();
      }

      public fun add(name: String, value: String): okhttp3.FormBody.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: FormBody.Builder = this;
         this.names
            .add(
               HttpUrl.Companion.canonicalize$okhttp$default(
                  HttpUrl.Companion, var1, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, false, this.charset, 91, null
               )
            );
         this.values
            .add(
               HttpUrl.Companion.canonicalize$okhttp$default(
                  HttpUrl.Companion, var2, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, false, this.charset, 91, null
               )
            );
         return this;
      }

      public fun addEncoded(name: String, value: String): okhttp3.FormBody.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: FormBody.Builder = this;
         this.names
            .add(
               HttpUrl.Companion.canonicalize$okhttp$default(
                  HttpUrl.Companion, var1, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, false, this.charset, 83, null
               )
            );
         this.values
            .add(
               HttpUrl.Companion.canonicalize$okhttp$default(
                  HttpUrl.Companion, var2, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, false, this.charset, 83, null
               )
            );
         return this;
      }

      public fun build(): FormBody {
         return new FormBody(this.names, this.values);
      }
   }

   public companion object {
      private final val CONTENT_TYPE: MediaType
   }
}
