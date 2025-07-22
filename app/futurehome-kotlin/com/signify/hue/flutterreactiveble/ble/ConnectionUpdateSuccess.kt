package com.signify.hue.flutterreactiveble.ble

public data class ConnectionUpdateSuccess(deviceId: String, connectionState: Int) : ConnectionUpdate() {
   public final val deviceId: String
   public final val connectionState: Int

   init {
      this.deviceId = var1;
      this.connectionState = var2;
   }

   public operator fun component1(): String {
      return this.deviceId;
   }

   public operator fun component2(): Int {
      return this.connectionState;
   }

   public fun copy(deviceId: String = var0.deviceId, connectionState: Int = var0.connectionState): ConnectionUpdateSuccess {
      return new ConnectionUpdateSuccess(var1, var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is ConnectionUpdateSuccess) {
         return false;
      } else {
         var1 = var1;
         if (!(this.deviceId == var1.deviceId)) {
            return false;
         } else {
            return this.connectionState == var1.connectionState;
         }
      }
   }

   public override fun hashCode(): Int {
      return this.deviceId.hashCode() * 31 + this.connectionState;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("ConnectionUpdateSuccess(deviceId=");
      var1.append(this.deviceId);
      var1.append(", connectionState=");
      var1.append(this.connectionState);
      var1.append(')');
      return var1.toString();
   }
}
