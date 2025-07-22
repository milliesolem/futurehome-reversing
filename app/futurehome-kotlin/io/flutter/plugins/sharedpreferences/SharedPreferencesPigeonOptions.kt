package io.flutter.plugins.sharedpreferences

import com.signify.hue.flutterreactiveble.utils.Duration$$ExternalSyntheticBackport0

public data class SharedPreferencesPigeonOptions(fileName: String? = null, useDataStore: Boolean) {
   public final val fileName: String?
   public final val useDataStore: Boolean

   init {
      this.fileName = var1;
      this.useDataStore = var2;
   }

   public operator fun component1(): String? {
      return this.fileName;
   }

   public operator fun component2(): Boolean {
      return this.useDataStore;
   }

   public fun copy(fileName: String? = var0.fileName, useDataStore: Boolean = var0.useDataStore): SharedPreferencesPigeonOptions {
      return new SharedPreferencesPigeonOptions(var1, var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is SharedPreferencesPigeonOptions) {
         return false;
      } else {
         var1 = var1;
         if (!(this.fileName == var1.fileName)) {
            return false;
         } else {
            return this.useDataStore == var1.useDataStore;
         }
      }
   }

   public override fun hashCode(): Int {
      val var1: Int;
      if (this.fileName == null) {
         var1 = 0;
      } else {
         var1 = this.fileName.hashCode();
      }

      return var1 * 31 + Duration$$ExternalSyntheticBackport0.m(this.useDataStore);
   }

   public fun toList(): List<Any?> {
      return CollectionsKt.listOf(new Object[]{this.fileName, this.useDataStore});
   }

   public override fun toString(): String {
      val var2: java.lang.String = this.fileName;
      val var1: Boolean = this.useDataStore;
      val var3: StringBuilder = new StringBuilder("SharedPreferencesPigeonOptions(fileName=");
      var3.append(var2);
      var3.append(", useDataStore=");
      var3.append(var1);
      var3.append(")");
      return var3.toString();
   }

   public companion object {
      public fun fromList(pigeonVar_list: List<Any?>): SharedPreferencesPigeonOptions {
         val var2: java.lang.String = var1.get(0) as java.lang.String;
         val var3: Any = var1.get(1);
         return new SharedPreferencesPigeonOptions(var2, var3 as java.lang.Boolean);
      }
   }
}
