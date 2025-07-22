package kotlin.reflect

import java.lang.reflect.Modifier
import java.lang.reflect.Type
import java.util.ArrayList
import kotlin.jvm.internal.KTypeBase

public final val javaType: Type
   public final get() {
      if (var0 is KTypeBase) {
         val var1: Type = (var0 as KTypeBase).getJavaType();
         if (var1 != null) {
            return var1;
         }
      }

      return computeJavaType$default(var0, false, 1, null);
   }


private final val javaType: Type
   private final get() {
      val var2: KVariance = var0.getVariance();
      if (var2 == null) {
         return WildcardTypeImpl.Companion.getSTAR();
      } else {
         val var3: KType = var0.getType();
         val var1: Int = TypesJVMKt.WhenMappings.$EnumSwitchMapping$0[var2.ordinal()];
         val var4: Type;
         if (var1 != 1) {
            if (var1 != 2) {
               if (var1 != 3) {
                  throw new NoWhenBranchMatchedException();
               }

               var4 = new WildcardTypeImpl(computeJavaType(var3, true), null);
            } else {
               var4 = computeJavaType(var3, true);
            }
         } else {
            var4 = new WildcardTypeImpl(null, computeJavaType(var3, true));
         }

         return var4;
      }
   }


@JvmSynthetic
fun `access$computeJavaType`(var0: KType, var1: Boolean): Type {
   return computeJavaType(var0, var1);
}

@JvmSynthetic
fun `access$typeToString`(var0: Type): java.lang.String {
   return typeToString(var0);
}

private fun KType.computeJavaType(forceWrapper: Boolean = false): Type {
   var var3: KClassifier = var0.getClassifier();
   if (var3 is KTypeParameter) {
      return new TypeVariableImpl(var3 as KTypeParameter);
   } else if (var3 is KClass) {
      val var9: KClass = var3 as KClass;
      if (var1) {
         var3 = JvmClassMappingKt.getJavaObjectType(var9);
      } else {
         var3 = JvmClassMappingKt.getJavaClass(var9);
      }

      val var4: java.util.List = var0.getArguments();
      if (var4.isEmpty()) {
         return var3 as Type;
      } else if (var3.isArray()) {
         if (var3.getComponentType().isPrimitive()) {
            return var3 as Type;
         } else {
            val var12: KTypeProjection = CollectionsKt.singleOrNull(var4);
            if (var12 != null) {
               val var5: KVariance = var12.component1();
               val var13: KType = var12.component2();
               val var2: Int;
               if (var5 == null) {
                  var2 = -1;
               } else {
                  var2 = TypesJVMKt.WhenMappings.$EnumSwitchMapping$0[var5.ordinal()];
               }

               val var6: Type;
               if (var2 != -1 && var2 != 1) {
                  if (var2 != 2 && var2 != 3) {
                     throw new NoWhenBranchMatchedException();
                  }

                  val var7: Type = computeJavaType$default(var13, false, 1, null);
                  if (var7 !is Class) {
                     var3 = new GenericArrayTypeImpl(var7);
                  }

                  var6 = var3 as Type;
               } else {
                  var6 = var3 as Type;
               }

               return var6;
            } else {
               val var11: StringBuilder = new StringBuilder("kotlin.Array must have exactly one type argument: ");
               var11.append(var0);
               throw new IllegalArgumentException(var11.toString());
            }
         }
      } else {
         return createPossiblyInnerType(var3, var4);
      }
   } else {
      val var8: StringBuilder = new StringBuilder("Unsupported type classifier: ");
      var8.append(var0);
      throw new UnsupportedOperationException(var8.toString());
   }
}

@JvmSynthetic
fun `computeJavaType$default`(var0: KType, var1: Boolean, var2: Int, var3: Any): Type {
   if ((var2 and 1) != 0) {
      var1 = false;
   }

   return computeJavaType(var0, var1);
}

private fun createPossiblyInnerType(jClass: Class<*>, arguments: List<KTypeProjection>): Type {
   val var3: Class = var0.getDeclaringClass();
   if (var3 == null) {
      val var7: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));
      val var11: java.util.Iterator = var1.iterator();

      while (var11.hasNext()) {
         var7.add(getJavaType(var11.next() as KTypeProjection));
      }

      return new ParameterizedTypeImpl(var0, null, var7 as MutableList<Type>);
   } else if (Modifier.isStatic(var0.getModifiers())) {
      val var9: Type = var3;
      val var6: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));
      val var14: java.util.Iterator = var1.iterator();

      while (var14.hasNext()) {
         var6.add(getJavaType(var14.next() as KTypeProjection));
      }

      return new ParameterizedTypeImpl(var0, var9, var6 as MutableList<Type>);
   } else {
      val var2: Int = var0.getTypeParameters().length;
      val var8: Type = createPossiblyInnerType(var3, var1.subList(var2, var1.size()));
      val var4: java.lang.Iterable = var1.subList(0, var2);
      val var5: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10));
      val var12: java.util.Iterator = var4.iterator();

      while (var12.hasNext()) {
         var5.add(getJavaType(var12.next() as KTypeProjection));
      }

      return new ParameterizedTypeImpl(var0, var8, var5 as MutableList<Type>);
   }
}

private fun typeToString(type: Type): String {
   val var3: java.lang.String;
   if (var0 is Class) {
      val var1: Class = var0 as Class;
      if ((var0 as Class).isArray()) {
         val var4: Sequence = SequencesKt.generateSequence(var0, <unrepresentable>.INSTANCE);
         val var2: StringBuilder = new StringBuilder();
         var2.append(SequencesKt.<Class>last(var4).getName());
         var2.append(StringsKt.repeat("[]", SequencesKt.count(var4)));
         var3 = var2.toString();
      } else {
         var3 = var1.getName();
      }
   } else {
      var3 = var0.toString();
   }

   return var3;
}
// $VF: Class flags could not be determined
@JvmSynthetic
internal class WhenMappings {
   @JvmStatic
   public int[] $EnumSwitchMapping$0;

   @JvmStatic
   fun {
      val var0: IntArray = new int[KVariance.values().length];

      try {
         var0[KVariance.IN.ordinal()] = 1;
      } catch (var4: NoSuchFieldError) {
      }

      try {
         var0[KVariance.INVARIANT.ordinal()] = 2;
      } catch (var3: NoSuchFieldError) {
      }

      try {
         var0[KVariance.OUT.ordinal()] = 3;
      } catch (var2: NoSuchFieldError) {
      }

      $EnumSwitchMapping$0 = var0;
   }
}
