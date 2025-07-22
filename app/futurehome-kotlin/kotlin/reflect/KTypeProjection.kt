package kotlin.reflect

public data class KTypeProjection(variance: KVariance?, type: KType?) {
   public final val variance: KVariance?
   public final val type: KType?

   init {
      this.variance = var1;
      this.type = var2;
      var var4: Boolean = true;
      val var3: Boolean;
      if (var1 == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var2 != null) {
         var4 = false;
      }

      if (var3 != var4) {
         val var5: java.lang.String;
         if (var1 == null) {
            var5 = "Star projection must have no type specified.";
         } else {
            val var6: StringBuilder = new StringBuilder("The projection variance ");
            var6.append(var1);
            var6.append(" requires type to be specified.");
            var5 = var6.toString();
         }

         throw new IllegalArgumentException(var5.toString());
      }
   }

   public operator fun component1(): KVariance? {
      return this.variance;
   }

   public operator fun component2(): KType? {
      return this.type;
   }

   public fun copy(variance: KVariance? = var0.variance, type: KType? = var0.type): KTypeProjection {
      return new KTypeProjection(var1, var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is KTypeProjection) {
         return false;
      } else {
         var1 = var1;
         if (this.variance != var1.variance) {
            return false;
         } else {
            return this.type == var1.type;
         }
      }
   }

   public override fun hashCode(): Int {
      var var2: Int = 0;
      val var1: Int;
      if (this.variance == null) {
         var1 = 0;
      } else {
         var1 = this.variance.hashCode();
      }

      if (this.type != null) {
         var2 = this.type.hashCode();
      }

      return var1 * 31 + var2;
   }

   public override fun toString(): String {
      val var1: Int;
      if (this.variance == null) {
         var1 = -1;
      } else {
         var1 = KTypeProjection.WhenMappings.$EnumSwitchMapping$0[this.variance.ordinal()];
      }

      val var4: java.lang.String;
      if (var1 != -1) {
         if (var1 != 1) {
            if (var1 != 2) {
               if (var1 != 3) {
                  throw new NoWhenBranchMatchedException();
               }

               val var3: StringBuilder = new StringBuilder("out ");
               var3.append(this.type);
               var4 = var3.toString();
            } else {
               val var5: StringBuilder = new StringBuilder("in ");
               var5.append(this.type);
               var4 = var5.toString();
            }
         } else {
            var4 = java.lang.String.valueOf(this.type);
         }
      } else {
         var4 = "*";
      }

      return var4;
   }

   public companion object {
      internal final val star: KTypeProjection

      public final val STAR: KTypeProjection
         public final get() {
            return KTypeProjection.star;
         }


      public fun contravariant(type: KType): KTypeProjection {
         return new KTypeProjection(KVariance.IN, var1);
      }

      public fun covariant(type: KType): KTypeProjection {
         return new KTypeProjection(KVariance.OUT, var1);
      }

      public fun invariant(type: KType): KTypeProjection {
         return new KTypeProjection(KVariance.INVARIANT, var1);
      }
   }
}
