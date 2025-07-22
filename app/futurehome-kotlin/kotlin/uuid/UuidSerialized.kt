package kotlin.uuid

import java.io.Externalizable
import java.io.ObjectInput
import java.io.ObjectOutput

private class UuidSerialized(mostSignificantBits: Long, leastSignificantBits: Long) : Externalizable {
   public final var mostSignificantBits: Long
      internal set

   public final var leastSignificantBits: Long
      internal set

   public constructor() : this(0L, 0L)
   init {
      this.mostSignificantBits = var1;
      this.leastSignificantBits = var3;
   }

   private fun readResolve(): Any {
      return Uuid.Companion.fromLongs(this.mostSignificantBits, this.leastSignificantBits);
   }

   public override fun readExternal(input: ObjectInput) {
      this.mostSignificantBits = var1.readLong();
      this.leastSignificantBits = var1.readLong();
   }

   public override fun writeExternal(output: ObjectOutput) {
      var1.writeLong(this.mostSignificantBits);
      var1.writeLong(this.leastSignificantBits);
   }

   public companion object {
      private const val serialVersionUID: Long
   }
}
