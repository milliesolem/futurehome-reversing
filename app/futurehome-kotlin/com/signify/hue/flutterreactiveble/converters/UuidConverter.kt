package com.signify.hue.flutterreactiveble.converters

import java.nio.ByteBuffer
import java.util.UUID

public class UuidConverter {
   private fun convert128BitNotationToUuid(bytes: ByteArray): UUID {
      val var2: ByteBuffer = ByteBuffer.wrap(var1);
      return new UUID(var2.getLong(), var2.getLong());
   }

   private fun convert16BitToUuid(bytes: ByteArray): UUID {
      return this.convert128BitNotationToUuid(new byte[]{0, 0, var1[0], var1[1], 0, 0, 16, 0, -128, 0, 0, -128, 95, -101, 52, -5});
   }

   private fun convert32BitToUuid(bytes: ByteArray): UUID {
      return this.convert128BitNotationToUuid(new byte[]{var1[0], var1[1], var1[2], var1[3], 0, 0, 16, 0, -128, 0, 0, -128, 95, -101, 52, -5});
   }

   public fun byteArrayFromUuid(uuid: UUID): ByteArray {
      val var2: ByteBuffer = ByteBuffer.wrap(new byte[16]);
      var2.putLong(var1.getMostSignificantBits());
      var2.putLong(var1.getLeastSignificantBits());
      val var3: ByteArray = var2.array();
      return var3;
   }

   public fun uuidFromByteArray(bytes: ByteArray): UUID {
      val var2: Int = var1.length;
      val var3: UUID;
      if (var1.length != 2) {
         if (var2 != 4) {
            var3 = this.convert128BitNotationToUuid(var1);
         } else {
            var3 = this.convert32BitToUuid(var1);
         }
      } else {
         var3 = this.convert16BitToUuid(var1);
      }

      return var3;
   }

   public companion object {
      private const val byteSize16Bit: Int
      private const val byteSize32Bit: Int
      private const val byteBufferSize: Int
   }
}
