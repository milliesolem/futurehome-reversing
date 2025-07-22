package com.signify.hue.flutterreactiveble.ble

public data class EstablishConnectionFailure(deviceId: String, errorMessage: String) : EstablishConnectionResult() {
   public final val deviceId: String
   public final val errorMessage: String

   init {
      this.deviceId = var1;
      this.errorMessage = var2;
   }

   public operator fun component1(): String {
      return this.deviceId;
   }

   public operator fun component2(): String {
      return this.errorMessage;
   }

   public fun copy(deviceId: String = var0.deviceId, errorMessage: String = var0.errorMessage): EstablishConnectionFailure {
      return new EstablishConnectionFailure(var1, var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is EstablishConnectionFailure) {
         return false;
      } else {
         var1 = var1;
         if (!(this.deviceId == var1.deviceId)) {
            return false;
         } else {
            return this.errorMessage == var1.errorMessage;
         }
      }
   }

   public override fun hashCode(): Int {
      return this.deviceId.hashCode() * 31 + this.errorMessage.hashCode();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("EstablishConnectionFailure(deviceId=");
      var1.append(this.deviceId);
      var1.append(", errorMessage=");
      var1.append(this.errorMessage);
      var1.append(')');
      return var1.toString();
   }
}
