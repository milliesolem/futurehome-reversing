package com.signify.hue.flutterreactiveble.ble

import com.polidea.rxandroidble2.RxBleConnection

public data class EstablishedConnection(deviceId: String, rxConnection: RxBleConnection) : EstablishConnectionResult() {
   public final val deviceId: String
   public final val rxConnection: RxBleConnection

   init {
      this.deviceId = var1;
      this.rxConnection = var2;
   }

   public operator fun component1(): String {
      return this.deviceId;
   }

   public operator fun component2(): RxBleConnection {
      return this.rxConnection;
   }

   public fun copy(deviceId: String = var0.deviceId, rxConnection: RxBleConnection = var0.rxConnection): EstablishedConnection {
      return new EstablishedConnection(var1, var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is EstablishedConnection) {
         return false;
      } else {
         var1 = var1;
         if (!(this.deviceId == var1.deviceId)) {
            return false;
         } else {
            return this.rxConnection == var1.rxConnection;
         }
      }
   }

   public override fun hashCode(): Int {
      return this.deviceId.hashCode() * 31 + this.rxConnection.hashCode();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("EstablishedConnection(deviceId=");
      var1.append(this.deviceId);
      var1.append(", rxConnection=");
      var1.append(this.rxConnection);
      var1.append(')');
      return var1.toString();
   }
}
