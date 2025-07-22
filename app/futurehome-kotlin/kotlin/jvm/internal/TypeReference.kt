package kotlin.jvm.internal

import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.KVariance

public class TypeReference  public constructor(classifier: KClassifier, arguments: List<KTypeProjection>, platformTypeUpperBound: KType?, flags: Int) : KType {
   public open val classifier: KClassifier
   public open val arguments: List<KTypeProjection>
   internal final val platformTypeUpperBound: KType?
   internal final val flags: Int

   public open val annotations: List<Annotation>
      public open get() {
         return CollectionsKt.emptyList();
      }


   public open val isMarkedNullable: Boolean
      public open get() {
         var var2: Boolean = true;
         if ((this.flags and 1) == 0) {
            var2 = false;
         }

         return var2;
      }


   private final val arrayClassName: String
      private final get() {
         val var2: java.lang.String;
         if (var1 == boolean[]::class.java) {
            var2 = "kotlin.BooleanArray";
         } else if (var1 == char[]::class.java) {
            var2 = "kotlin.CharArray";
         } else if (var1 == byte[]::class.java) {
            var2 = "kotlin.ByteArray";
         } else if (var1 == short[]::class.java) {
            var2 = "kotlin.ShortArray";
         } else if (var1 == int[]::class.java) {
            var2 = "kotlin.IntArray";
         } else if (var1 == float[]::class.java) {
            var2 = "kotlin.FloatArray";
         } else if (var1 == long[]::class.java) {
            var2 = "kotlin.LongArray";
         } else if (var1 == double[]::class.java) {
            var2 = "kotlin.DoubleArray";
         } else {
            var2 = "kotlin.Array";
         }

         return var2;
      }


   init {
      this.classifier = var1;
      this.arguments = var2;
      this.platformTypeUpperBound = var3;
      this.flags = var4;
   }

   public constructor(classifier: KClassifier, arguments: List<KTypeProjection>, isMarkedNullable: Boolean) : this(var1, var2, null, var3)
   private fun KTypeProjection.asString(): String {
      if (var1.getVariance() == null) {
         return "*";
      } else {
         val var3: KType = var1.getType();
         val var9: TypeReference;
         if (var3 is TypeReference) {
            var9 = var3 as TypeReference;
         } else {
            var9 = null;
         }

         label33: {
            if (var9 != null) {
               val var4: java.lang.String = var9.asString(true);
               var10 = var4;
               if (var4 != null) {
                  break label33;
               }
            }

            var10 = java.lang.String.valueOf(var1.getType());
         }

         val var5: KVariance = var1.getVariance();
         val var2: Int;
         if (var5 == null) {
            var2 = -1;
         } else {
            var2 = TypeReference.WhenMappings.$EnumSwitchMapping$0[var5.ordinal()];
         }

         var var6: java.lang.String = var10;
         if (var2 != 1) {
            if (var2 != 2) {
               if (var2 != 3) {
                  throw new NoWhenBranchMatchedException();
               }

               val var7: StringBuilder = new StringBuilder("out ");
               var7.append(var10);
               var6 = var7.toString();
            } else {
               val var8: StringBuilder = new StringBuilder("in ");
               var8.append(var10);
               var6 = var8.toString();
            }
         }

         return var6;
      }
   }

   private fun asString(convertPrimitiveToWrapper: Boolean): String {
      var var3: KClassifier = this.getClassifier();
      val var2: Boolean = var3 is KClass;
      var var4: Class = null;
      val var8: KClass;
      if (var2) {
         var8 = var3 as KClass;
      } else {
         var8 = null;
      }

      if (var8 != null) {
         var4 = JvmClassMappingKt.getJavaClass(var8);
      }

      val var9: java.lang.String;
      if (var4 == null) {
         var9 = this.getClassifier().toString();
      } else if ((this.flags and 4) != 0) {
         var9 = "kotlin.Nothing";
      } else if (var4.isArray()) {
         var9 = this.getArrayClassName(var4);
      } else if (var1 && var4.isPrimitive()) {
         var3 = this.getClassifier();
         var9 = JvmClassMappingKt.getJavaObjectType(var3 as KClass).getName();
      } else {
         var9 = var4.getName();
      }

      var1 = this.getArguments().isEmpty();
      var var5: java.lang.String = "";
      val var14: java.lang.String;
      if (var1) {
         var14 = "";
      } else {
         var14 = CollectionsKt.joinToString$default(this.getArguments(), ", ", "<", ">", 0, null, new TypeReference$$ExternalSyntheticLambda0(this), 24, null);
      }

      if (this.isMarkedNullable()) {
         var5 = "?";
      }

      val var6: StringBuilder = new StringBuilder();
      var6.append(var9);
      var6.append(var14);
      var6.append(var5);
      val var15: java.lang.String = var6.toString();
      var var11: java.lang.String = var15;
      if (this.platformTypeUpperBound is TypeReference) {
         val var12: java.lang.String = (this.platformTypeUpperBound as TypeReference).asString(true);
         if (var12 == var15) {
            var11 = var15;
         } else {
            val var17: StringBuilder = new StringBuilder();
            var17.append(var15);
            var17.append('?');
            if (var12 == var17.toString()) {
               val var13: StringBuilder = new StringBuilder();
               var13.append(var15);
               var13.append('!');
               var11 = var13.toString();
            } else {
               val var18: StringBuilder = new StringBuilder("(");
               var18.append(var15);
               var18.append("..");
               var18.append(var12);
               var18.append(')');
               var11 = var18.toString();
            }
         }
      }

      return var11;
   }

   @JvmStatic
   fun `asString$lambda$0`(var0: TypeReference, var1: KTypeProjection): java.lang.CharSequence {
      return var0.asString(var1);
   }

   public override operator fun equals(other: Any?): Boolean {
      return var1 is TypeReference
         && this.getClassifier() == (var1 as TypeReference).getClassifier()
         && this.getArguments() == (var1 as TypeReference).getArguments()
         && this.platformTypeUpperBound == (var1 as TypeReference).platformTypeUpperBound
         && this.flags == (var1 as TypeReference).flags;
   }

   public override fun hashCode(): Int {
      return (this.getClassifier().hashCode() * 31 + this.getArguments().hashCode()) * 31 + this.flags;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this.asString(false));
      var1.append(" (Kotlin reflection is not available)");
      return var1.toString();
   }

   internal companion object {
      internal const val IS_MARKED_NULLABLE: Int
      internal const val IS_MUTABLE_COLLECTION_TYPE: Int
      internal const val IS_NOTHING_TYPE: Int
   }
}
