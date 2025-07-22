package io.flutter.plugins.sharedpreferences

public data class StringListResult(jsonEncodedValue: String? = null, type: StringListLookupResultType) {
   public final val jsonEncodedValue: String?
   public final val type: StringListLookupResultType

   init {
      this.jsonEncodedValue = var1;
      this.type = var2;
   }

   public operator fun component1(): String? {
      return this.jsonEncodedValue;
   }

   public operator fun component2(): StringListLookupResultType {
      return this.type;
   }

   public fun copy(jsonEncodedValue: String? = var0.jsonEncodedValue, type: StringListLookupResultType = var0.type): StringListResult {
      return new StringListResult(var1, var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is StringListResult) {
         return false;
      } else {
         var1 = var1;
         if (!(this.jsonEncodedValue == var1.jsonEncodedValue)) {
            return false;
         } else {
            return this.type === var1.type;
         }
      }
   }

   public override fun hashCode(): Int {
      val var1: Int;
      if (this.jsonEncodedValue == null) {
         var1 = 0;
      } else {
         var1 = this.jsonEncodedValue.hashCode();
      }

      return var1 * 31 + this.type.hashCode();
   }

   public fun toList(): List<Any?> {
      return CollectionsKt.listOf(new Object[]{this.jsonEncodedValue, this.type});
   }

   public override fun toString(): String {
      val var2: java.lang.String = this.jsonEncodedValue;
      val var3: StringListLookupResultType = this.type;
      val var1: StringBuilder = new StringBuilder("StringListResult(jsonEncodedValue=");
      var1.append(var2);
      var1.append(", type=");
      var1.append(var3);
      var1.append(")");
      return var1.toString();
   }

   public companion object {
      public fun fromList(pigeonVar_list: List<Any?>): StringListResult {
         val var2: java.lang.String = var1.get(0) as java.lang.String;
         val var3: Any = var1.get(1);
         return new StringListResult(var2, var3 as StringListLookupResultType);
      }
   }
}
