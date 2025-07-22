package com.signify.hue.flutterreactiveble.ble

public data class MtuNegotiateSuccessful(deviceId: String, size: Int) : MtuNegotiateResult() {
   public final val deviceId: String
   public final val size: Int

   init {
      this.deviceId = var1;
      this.size = var2;
   }

   public operator fun component1(): String {
      return this.deviceId;
   }

   public operator fun component2(): Int {
      return this.size;
   }

   public fun copy(deviceId: String = var0.deviceId, size: Int = var0.size): MtuNegotiateSuccessful {
      return new MtuNegotiateSuccessful(var1, var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is MtuNegotiateSuccessful) {
         return false;
      } else {
         var1 = var1;
         if (!(this.deviceId == var1.deviceId)) {
            return false;
         } else {
            return this.size == var1.size;
         }
      }
   }

   public override fun hashCode(): Int {
      return this.deviceId.hashCode() * 31 + this.size;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("MtuNegotiateSuccessful(deviceId=");
      var1.append(this.deviceId);
      var1.append(", size=");
      var1.append(this.size);
      var1.append(')');
      return var1.toString();
   }
}
