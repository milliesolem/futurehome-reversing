package kotlin.collections.builders

import java.io.Externalizable
import java.io.InvalidObjectException
import java.io.ObjectInput
import java.io.ObjectOutput
import java.util.Map.Entry

private class SerializedMap(map: Map<*, *>) : Externalizable {
   private final var map: Map<*, *>

   public constructor() : this(MapsKt.emptyMap())
   init {
      this.map = var1;
   }

   private fun readResolve(): Any {
      return this.map;
   }

   public override fun readExternal(input: ObjectInput) {
      val var2: Byte = var1.readByte();
      if (var2 != 0) {
         val var6: StringBuilder = new StringBuilder("Unsupported flags value: ");
         var6.append((int)var2);
         throw new InvalidObjectException(var6.toString());
      } else {
         val var3: Int = var1.readInt();
         if (var3 < 0) {
            val var5: StringBuilder = new StringBuilder("Illegal size value: ");
            var5.append(var3);
            var5.append('.');
            throw new InvalidObjectException(var5.toString());
         } else {
            val var4: java.util.Map = MapsKt.createMapBuilder(var3);

            for (int var7 = 0; var7 < var3; var7++) {
               var4.put(var1.readObject(), var1.readObject());
            }

            this.map = MapsKt.build(var4);
         }
      }
   }

   public override fun writeExternal(output: ObjectOutput) {
      var1.writeByte(0);
      var1.writeInt(this.map.size());

      for (Entry var3 : this.map.entrySet()) {
         var1.writeObject(var3.getKey());
         var1.writeObject(var3.getValue());
      }
   }

   public companion object {
      private const val serialVersionUID: Long
   }
}
