package com.signify.hue.flutterreactiveble.ble

public data class RequestConnectionPrioritySuccess(deviceId: String) : RequestConnectionPriorityResult() {
   public final val deviceId: String

   init {
      this.deviceId = var1;
   }

   public operator fun component1(): String {
      return this.deviceId;
   }

   public fun copy(deviceId: String = var0.deviceId): RequestConnectionPrioritySuccess {
      return new RequestConnectionPrioritySuccess(var1);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is RequestConnectionPrioritySuccess) {
         return false;
      } else {
         return this.deviceId == (var1 as RequestConnectionPrioritySuccess).deviceId;
      }
   }

   public override fun hashCode(): Int {
      return this.deviceId.hashCode();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("RequestConnectionPrioritySuccess(deviceId=");
      var1.append(this.deviceId);
      var1.append(')');
      return var1.toString();
   }
}
