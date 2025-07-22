package com.signify.hue.flutterreactiveble.channelhandlers

import android.os.ParcelUuid
import com.signify.hue.flutterreactiveble.model.ScanMode
import com.signify.hue.flutterreactiveble.utils.Duration$$ExternalSyntheticBackport0

private data class ScanParameters(filter: List<ParcelUuid>, mode: ScanMode, locationServiceIsMandatory: Boolean) {
   public final val filter: List<ParcelUuid>
   public final val mode: ScanMode
   public final val locationServiceIsMandatory: Boolean

   init {
      this.filter = var1;
      this.mode = var2;
      this.locationServiceIsMandatory = var3;
   }

   public operator fun component1(): List<ParcelUuid> {
      return this.filter;
   }

   public operator fun component2(): ScanMode {
      return this.mode;
   }

   public operator fun component3(): Boolean {
      return this.locationServiceIsMandatory;
   }

   public fun copy(filter: List<ParcelUuid> = var0.filter, mode: ScanMode = var0.mode, locationServiceIsMandatory: Boolean = var0.locationServiceIsMandatory): ScanParameters {
      return new ScanParameters(var1, var2, var3);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is ScanParameters) {
         return false;
      } else {
         var1 = var1;
         if (!(this.filter == var1.filter)) {
            return false;
         } else if (this.mode != var1.mode) {
            return false;
         } else {
            return this.locationServiceIsMandatory == var1.locationServiceIsMandatory;
         }
      }
   }

   public override fun hashCode(): Int {
      return (this.filter.hashCode() * 31 + this.mode.hashCode()) * 31 + Duration$$ExternalSyntheticBackport0.m(this.locationServiceIsMandatory);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("ScanParameters(filter=");
      var1.append(this.filter);
      var1.append(", mode=");
      var1.append(this.mode);
      var1.append(", locationServiceIsMandatory=");
      var1.append(this.locationServiceIsMandatory);
      var1.append(')');
      return var1.toString();
   }
}
