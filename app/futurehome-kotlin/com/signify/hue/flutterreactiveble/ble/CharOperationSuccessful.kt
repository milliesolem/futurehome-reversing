package com.signify.hue.flutterreactiveble.ble

public data class CharOperationSuccessful(deviceId: String, value: List<Byte>) : CharOperationResult() {
   public final val deviceId: String
   public final val value: List<Byte>

   init {
      this.deviceId = var1;
      this.value = var2;
   }

   public operator fun component1(): String {
      return this.deviceId;
   }

   public operator fun component2(): List<Byte> {
      return this.value;
   }

   public fun copy(deviceId: String = var0.deviceId, value: List<Byte> = var0.value): CharOperationSuccessful {
      return new CharOperationSuccessful(var1, var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is CharOperationSuccessful) {
         return false;
      } else {
         var1 = var1;
         if (!(this.deviceId == var1.deviceId)) {
            return false;
         } else {
            return this.value == var1.value;
         }
      }
   }

   public override fun hashCode(): Int {
      return this.deviceId.hashCode() * 31 + this.value.hashCode();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("CharOperationSuccessful(deviceId=");
      var1.append(this.deviceId);
      var1.append(", value=");
      var1.append(this.value);
      var1.append(')');
      return var1.toString();
   }
}
