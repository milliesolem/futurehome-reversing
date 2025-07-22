package okhttp3.internal.http2

import kotlin.jvm.internal.Intrinsics
import okio.ByteString

public data class Header(name: ByteString, value: ByteString) {
   public final val hpackSize: Int
   public final val name: ByteString
   public final val value: ByteString

   public constructor(name: String, value: String) : Intrinsics.checkParameterIsNotNull(var1, "name") {
      Intrinsics.checkParameterIsNotNull(var2, "value");
      this(ByteString.Companion.encodeUtf8(var1), ByteString.Companion.encodeUtf8(var2));
   }

   public constructor(name: ByteString, value: String) : Intrinsics.checkParameterIsNotNull(var1, "name") {
      Intrinsics.checkParameterIsNotNull(var2, "value");
      this(var1, ByteString.Companion.encodeUtf8(var2));
   }

   init {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var2, "value");
      super();
      this.name = var1;
      this.value = var2;
      this.hpackSize = var1.size() + 32 + var2.size();
   }

   public operator fun component1(): ByteString {
      return this.name;
   }

   public operator fun component2(): ByteString {
      return this.value;
   }

   public fun copy(name: ByteString = var0.name, value: ByteString = var0.value): Header {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var2, "value");
      return new Header(var1, var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this != var1) {
         if (var1 !is Header) {
            return false;
         }

         if (!(this.name == (var1 as Header).name) || !(this.value == (var1 as Header).value)) {
            return false;
         }
      }

      return true;
   }

   public override fun hashCode(): Int {
      var var2: Int = 0;
      val var1: Int;
      if (this.name != null) {
         var1 = this.name.hashCode();
      } else {
         var1 = 0;
      }

      if (this.value != null) {
         var2 = this.value.hashCode();
      }

      return var1 * 31 + var2;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this.name.utf8());
      var1.append(": ");
      var1.append(this.value.utf8());
      return var1.toString();
   }

   public companion object {
      public final val PSEUDO_PREFIX: ByteString
      public final val RESPONSE_STATUS: ByteString
      public const val RESPONSE_STATUS_UTF8: String
      public final val TARGET_AUTHORITY: ByteString
      public const val TARGET_AUTHORITY_UTF8: String
      public final val TARGET_METHOD: ByteString
      public const val TARGET_METHOD_UTF8: String
      public final val TARGET_PATH: ByteString
      public const val TARGET_PATH_UTF8: String
      public final val TARGET_SCHEME: ByteString
      public const val TARGET_SCHEME_UTF8: String
   }
}
