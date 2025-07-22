package kotlin.jvm.internal

import kotlin.reflect.KType
import kotlin.reflect.KTypeParameter
import kotlin.reflect.KVariance

public class TypeParameterReference(container: Any?, name: String, variance: KVariance, isReified: Boolean) : KTypeParameter {
   private final val container: Any?
   public open val name: String
   public open val variance: KVariance
   public open val isReified: Boolean
   private final var bounds: List<KType>?

   public open val upperBounds: List<KType>
      public open get() {
         var var1: java.util.List = this.bounds;
         if (this.bounds == null) {
            var1 = CollectionsKt.listOf(Reflection.nullableTypeOf(Object.class));
            this.bounds = var1;
         }

         return var1;
      }


   init {
      this.container = var1;
      this.name = var2;
      this.variance = var3;
      this.isReified = var4;
   }

   public override operator fun equals(other: Any?): Boolean {
      return var1 is TypeParameterReference
         && this.container == (var1 as TypeParameterReference).container
         && this.getName() == (var1 as TypeParameterReference).getName();
   }

   public override fun hashCode(): Int {
      val var1: Int;
      if (this.container != null) {
         var1 = this.container.hashCode();
      } else {
         var1 = 0;
      }

      return var1 * 31 + this.getName().hashCode();
   }

   public fun setUpperBounds(upperBounds: List<KType>) {
      if (this.bounds == null) {
         this.bounds = var1;
      } else {
         val var2: StringBuilder = new StringBuilder("Upper bounds of type parameter '");
         var2.append(this);
         var2.append("' have already been initialized.");
         throw new IllegalStateException(var2.toString().toString());
      }
   }

   public override fun toString(): String {
      return Companion.toString(this);
   }

   public companion object {
      public fun toString(typeParameter: KTypeParameter): String {
         val var3: StringBuilder = new StringBuilder();
         val var2: Int = TypeParameterReference.Companion.WhenMappings.$EnumSwitchMapping$0[var1.getVariance().ordinal()];
         if (var2 != 1) {
            if (var2 != 2) {
               if (var2 != 3) {
                  throw new NoWhenBranchMatchedException();
               }

               var3.append("out ");
            } else {
               var3.append("in ");
            }
         }

         var3.append(var1.getName());
         return var3.toString();
      }
   }
}
