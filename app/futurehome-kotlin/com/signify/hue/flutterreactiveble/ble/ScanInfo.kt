package com.signify.hue.flutterreactiveble.ble

import java.util.Arrays
import java.util.UUID

public data class ScanInfo(deviceId: String,
   name: String,
   rssi: Int,
   connectable: Connectable,
   serviceData: Map<UUID, ByteArray>,
   serviceUuids: List<UUID>,
   manufacturerData: ByteArray
) {
   public final val deviceId: String
   public final val name: String
   public final val rssi: Int
   public final val connectable: Connectable
   public final val serviceData: Map<UUID, ByteArray>
   public final val serviceUuids: List<UUID>
   public final val manufacturerData: ByteArray

   init {
      this.deviceId = var1;
      this.name = var2;
      this.rssi = var3;
      this.connectable = var4;
      this.serviceData = var5;
      this.serviceUuids = var6;
      this.manufacturerData = var7;
   }

   public operator fun component1(): String {
      return this.deviceId;
   }

   public operator fun component2(): String {
      return this.name;
   }

   public operator fun component3(): Int {
      return this.rssi;
   }

   public operator fun component4(): Connectable {
      return this.connectable;
   }

   public operator fun component5(): Map<UUID, ByteArray> {
      return this.serviceData;
   }

   public operator fun component6(): List<UUID> {
      return this.serviceUuids;
   }

   public operator fun component7(): ByteArray {
      return this.manufacturerData;
   }

   public fun copy(
      deviceId: String = var0.deviceId,
      name: String = var0.name,
      rssi: Int = var0.rssi,
      connectable: Connectable = var0.connectable,
      serviceData: Map<UUID, ByteArray> = var0.serviceData,
      serviceUuids: List<UUID> = var0.serviceUuids,
      manufacturerData: ByteArray = var0.manufacturerData
   ): ScanInfo {
      return new ScanInfo(var1, var2, var3, var4, var5, var6, var7);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else {
         val var3: Class = this.getClass();
         val var2: Class;
         if (var1 != null) {
            var2 = var1.getClass();
         } else {
            var2 = null;
         }

         if (!(var3 == var2)) {
            return false;
         } else {
            var1 = var1;
            if (!(this.deviceId == var1.deviceId)) {
               return false;
            } else if (!(this.name == var1.name)) {
               return false;
            } else if (this.rssi != var1.rssi) {
               return false;
            } else if (this.connectable != var1.connectable) {
               return false;
            } else if (!(this.serviceData == var1.serviceData)) {
               return false;
            } else if (!(this.serviceUuids == var1.serviceUuids)) {
               return false;
            } else {
               return Arrays.equals(this.manufacturerData, var1.manufacturerData);
            }
         }
      }
   }

   public override fun hashCode(): Int {
      return (
               (
                        (((this.deviceId.hashCode() * 31 + this.name.hashCode()) * 31 + this.rssi) * 31 + this.connectable.hashCode()) * 31
                           + this.serviceData.hashCode()
                     )
                     * 31
                  + this.serviceUuids.hashCode()
            )
            * 31
         + Arrays.hashCode(this.manufacturerData);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("ScanInfo(deviceId=");
      var1.append(this.deviceId);
      var1.append(", name=");
      var1.append(this.name);
      var1.append(", rssi=");
      var1.append(this.rssi);
      var1.append(", connectable=");
      var1.append(this.connectable);
      var1.append(", serviceData=");
      var1.append(this.serviceData);
      var1.append(", serviceUuids=");
      var1.append(this.serviceUuids);
      var1.append(", manufacturerData=");
      var1.append(Arrays.toString(this.manufacturerData));
      var1.append(')');
      return var1.toString();
   }
}
