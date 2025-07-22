package kotlin.uuid

import java.io.Serializable
import java.util.Comparator
import kotlin.contracts.InvocationKind

public class Uuid internal constructor(mostSignificantBits: Long, leastSignificantBits: Long) : Serializable {
   internal final val mostSignificantBits: Long
   internal final val leastSignificantBits: Long

   init {
      this.mostSignificantBits = var1;
      this.leastSignificantBits = var3;
   }

   @JvmStatic
   fun `LEXICAL_ORDER$lambda$0`(var0: Uuid, var1: Uuid): Int {
      val var2: Int;
      if (var0.mostSignificantBits != var1.mostSignificantBits) {
         var2 = UByte$$ExternalSyntheticBackport0.m(ULong.constructor-impl(var0.mostSignificantBits), ULong.constructor-impl(var1.mostSignificantBits));
      } else {
         var2 = UByte$$ExternalSyntheticBackport0.m(ULong.constructor-impl(var0.leastSignificantBits), ULong.constructor-impl(var1.leastSignificantBits));
      }

      return var2;
   }

   public inline fun <T> toLongs(action: (Long, Long) -> T): T {
      contract {
         callsInPlace(action, InvocationKind.EXACTLY_ONCE)
      }

      return (T)var1.invoke(this.getMostSignificantBits(), this.getLeastSignificantBits());
   }

   public inline fun <T> toULongs(action: (ULong, ULong) -> T): T {
      contract {
         callsInPlace(action, InvocationKind.EXACTLY_ONCE)
      }

      return (T)var1.invoke(
         ULong.box-impl(ULong.constructor-impl(this.getMostSignificantBits())), ULong.box-impl(ULong.constructor-impl(this.getLeastSignificantBits()))
      );
   }

   private fun writeReplace(): Any {
      return UuidKt.serializedUuid(this);
   }

   public override operator fun equals(other: Any?): Boolean {
      var var2: Boolean = true;
      if (this === var1) {
         return true;
      } else if (var1 !is Uuid) {
         return false;
      } else {
         if (this.mostSignificantBits != (var1 as Uuid).mostSignificantBits || this.leastSignificantBits != (var1 as Uuid).leastSignificantBits) {
            var2 = false;
         }

         return var2;
      }
   }

   public override fun hashCode(): Int {
      return (int)((this.mostSignificantBits xor this.leastSignificantBits) shr 32) xor (int)(this.mostSignificantBits xor this.leastSignificantBits);
   }

   public fun toByteArray(): ByteArray {
      val var1: ByteArray = new byte[16];
      UuidKt__UuidKt.access$toByteArray(this.mostSignificantBits, var1, 0);
      UuidKt__UuidKt.access$toByteArray(this.leastSignificantBits, var1, 8);
      return var1;
   }

   public fun toHexString(): String {
      val var1: ByteArray = new byte[32];
      UuidKt__UuidKt.access$formatBytesInto(this.leastSignificantBits, var1, 16, 8);
      UuidKt__UuidKt.access$formatBytesInto(this.mostSignificantBits, var1, 0, 8);
      return StringsKt.decodeToString(var1);
   }

   public override fun toString(): String {
      val var1: ByteArray = new byte[36];
      UuidKt__UuidKt.access$formatBytesInto(this.leastSignificantBits, var1, 24, 6);
      var1[23] = 45;
      UuidKt__UuidKt.access$formatBytesInto(this.leastSignificantBits ushr 48, var1, 19, 2);
      var1[18] = 45;
      UuidKt__UuidKt.access$formatBytesInto(this.mostSignificantBits, var1, 14, 2);
      var1[13] = 45;
      UuidKt__UuidKt.access$formatBytesInto(this.mostSignificantBits ushr 16, var1, 9, 2);
      var1[8] = 45;
      UuidKt__UuidKt.access$formatBytesInto(this.mostSignificantBits ushr 32, var1, 0, 4);
      return StringsKt.decodeToString(var1);
   }

   public companion object {
      public final val NIL: Uuid
      public const val SIZE_BYTES: Int
      public const val SIZE_BITS: Int
      public final val LEXICAL_ORDER: Comparator<Uuid>

      public fun fromByteArray(byteArray: ByteArray): Uuid {
         if (var1.length == 16) {
            return this.fromLongs(UuidKt__UuidKt.access$toLong(var1, 0), UuidKt__UuidKt.access$toLong(var1, 8));
         } else {
            throw new IllegalArgumentException("Expected exactly 16 bytes".toString());
         }
      }

      public fun fromLongs(mostSignificantBits: Long, leastSignificantBits: Long): Uuid {
         val var5: Uuid;
         if (var1 == 0L && var3 == 0L) {
            var5 = this.getNIL();
         } else {
            var5 = new Uuid(var1, var3);
         }

         return var5;
      }

      public fun fromULongs(mostSignificantBits: ULong, leastSignificantBits: ULong): Uuid {
         return this.fromLongs(var1, var3);
      }

      public fun parse(uuidString: String): Uuid {
         if (var1.length() == 36) {
            val var8: Long = HexExtensionsKt.hexToLong$default(var1, 0, 8, null, 4, null);
            UuidKt__UuidKt.access$checkHyphenAt(var1, 8);
            val var4: Long = HexExtensionsKt.hexToLong$default(var1, 9, 13, null, 4, null);
            UuidKt__UuidKt.access$checkHyphenAt(var1, 13);
            val var2: Long = HexExtensionsKt.hexToLong$default(var1, 14, 18, null, 4, null);
            UuidKt__UuidKt.access$checkHyphenAt(var1, 18);
            val var6: Long = HexExtensionsKt.hexToLong$default(var1, 19, 23, null, 4, null);
            UuidKt__UuidKt.access$checkHyphenAt(var1, 23);
            return this.fromLongs(var8 shl 32 or var4 shl 16 or var2, HexExtensionsKt.hexToLong$default(var1, 24, 36, null, 4, null) or var6 shl 48);
         } else {
            throw new IllegalArgumentException("Expected a 36-char string in the standard uuid format.".toString());
         }
      }

      public fun parseHex(hexString: String): Uuid {
         if (var1.length() == 32) {
            return this.fromLongs(HexExtensionsKt.hexToLong$default(var1, 0, 16, null, 4, null), HexExtensionsKt.hexToLong$default(var1, 16, 32, null, 4, null));
         } else {
            throw new IllegalArgumentException("Expected a 32-char hexadecimal string.".toString());
         }
      }

      public fun random(): Uuid {
         return UuidKt.secureRandomUuid();
      }
   }
}
