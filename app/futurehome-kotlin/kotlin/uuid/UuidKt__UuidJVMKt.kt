package kotlin.uuid

import java.nio.BufferOverflowException
import java.nio.BufferUnderflowException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.UUID

internal class UuidKt__UuidJVMKt {
   open fun UuidKt__UuidJVMKt() {
   }

   @JvmStatic
   public fun ByteBuffer.getUuid(): Uuid {
      if (var0.position() + 15 < var0.limit()) {
         val var7: Long = var0.getLong();
         val var5: Long = var0.getLong();
         var var3: Long = var7;
         var var1: Long = var5;
         if (var0.order() == ByteOrder.LITTLE_ENDIAN) {
            var3 = java.lang.Long.reverseBytes(var7);
            var1 = java.lang.Long.reverseBytes(var5);
         }

         return Uuid.Companion.fromLongs(var3, var1);
      } else {
         throw new BufferUnderflowException();
      }
   }

   @JvmStatic
   public fun ByteBuffer.getUuid(index: Int): Uuid {
      if (var1 >= 0) {
         if (var1 + 15 < var0.limit()) {
            val var8: Long = var0.getLong(var1);
            val var6: Long = var0.getLong(var1 + 8);
            var var4: Long = var8;
            var var2: Long = var6;
            if (var0.order() == ByteOrder.LITTLE_ENDIAN) {
               var4 = java.lang.Long.reverseBytes(var8);
               var2 = java.lang.Long.reverseBytes(var6);
            }

            return Uuid.Companion.fromLongs(var4, var2);
         } else {
            val var10: StringBuilder = new StringBuilder("Not enough bytes to read a uuid at index: ");
            var10.append(var1);
            var10.append(", with limit: ");
            var10.append(var0.limit());
            var10.append(' ');
            throw new IndexOutOfBoundsException(var10.toString());
         }
      } else {
         val var11: StringBuilder = new StringBuilder("Negative index: ");
         var11.append(var1);
         throw new IndexOutOfBoundsException(var11.toString());
      }
   }

   @JvmStatic
   public fun ByteBuffer.putUuid(index: Int, uuid: Uuid): ByteBuffer {
      val var5: Long = var2.getMostSignificantBits();
      val var3: Long = var2.getLeastSignificantBits();
      if (var1 >= 0) {
         if (var1 + 15 < var0.limit()) {
            if (var0.order() == ByteOrder.BIG_ENDIAN) {
               var0.putLong(var1, var5);
               var0 = var0.putLong(var1 + 8, var3);
            } else {
               var0.putLong(var1, java.lang.Long.reverseBytes(var5));
               var0 = var0.putLong(var1 + 8, java.lang.Long.reverseBytes(var3));
            }

            return var0;
         } else {
            val var9: StringBuilder = new StringBuilder("Not enough capacity to write a uuid at index: ");
            var9.append(var1);
            var9.append(", with limit: ");
            var9.append(var0.limit());
            var9.append(' ');
            throw new IndexOutOfBoundsException(var9.toString());
         }
      } else {
         val var7: StringBuilder = new StringBuilder("Negative index: ");
         var7.append(var1);
         throw new IndexOutOfBoundsException(var7.toString());
      }
   }

   @JvmStatic
   public fun ByteBuffer.putUuid(uuid: Uuid): ByteBuffer {
      val var4: Long = var1.getMostSignificantBits();
      val var2: Long = var1.getLeastSignificantBits();
      if (var0.position() + 15 < var0.limit()) {
         if (var0.order() == ByteOrder.BIG_ENDIAN) {
            var0.putLong(var4);
            var0 = var0.putLong(var2);
         } else {
            var0.putLong(java.lang.Long.reverseBytes(var4));
            var0 = var0.putLong(java.lang.Long.reverseBytes(var2));
         }

         return var0;
      } else {
         throw new BufferOverflowException();
      }
   }

   @JvmStatic
   internal inline fun Long.reverseBytes(): Long {
      return java.lang.Long.reverseBytes(var0);
   }

   @JvmStatic
   internal fun secureRandomUuid(): Uuid {
      val var0: ByteArray = new byte[16];
      SecureRandomHolder.INSTANCE.getInstance().nextBytes(var0);
      return UuidKt.uuidFromRandomBytes(var0);
   }

   @JvmStatic
   internal fun serializedUuid(uuid: Uuid): Any {
      return new UuidSerialized(var0.getMostSignificantBits(), var0.getLeastSignificantBits());
   }

   @JvmStatic
   public inline fun Uuid.toJavaUuid(): UUID {
      return new UUID(var0.getMostSignificantBits(), var0.getLeastSignificantBits());
   }

   @JvmStatic
   public inline fun UUID.toKotlinUuid(): Uuid {
      return Uuid.Companion.fromLongs(var0.getMostSignificantBits(), var0.getLeastSignificantBits());
   }
}
