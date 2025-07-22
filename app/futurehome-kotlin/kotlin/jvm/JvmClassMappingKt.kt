package kotlin.jvm

import kotlin.jvm.internal.ClassBasedDeclarationContainer
import kotlin.jvm.internal.Intrinsics
import kotlin.reflect.KClass

public final val java: Class<T>
   public final get() {
      val var1: Class = (var0 as ClassBasedDeclarationContainer).getJClass();
      return var1;
   }


public final val javaPrimitiveType: Class<T>?
   public final get() {
      val var1: Class = (var0 as ClassBasedDeclarationContainer).getJClass();
      if (var1.isPrimitive()) {
         return var1;
      } else {
         val var2: java.lang.String = var1.getName();
         if (var2 != null) {
            switch (var2.hashCode()) {
               case -2056817302:
                  if (var2.equals("java.lang.Integer")) {
                     return (Class<T>)Int::class.javaPrimitiveType;
                  }
                  break;
               case -527879800:
                  if (var2.equals("java.lang.Float")) {
                     return (Class<T>)java.lang.Float::class.javaPrimitiveType;
                  }
                  break;
               case -515992664:
                  if (var2.equals("java.lang.Short")) {
                     return (Class<T>)java.lang.Short::class.javaPrimitiveType;
                  }
                  break;
               case 155276373:
                  if (var2.equals("java.lang.Character")) {
                     return (Class<T>)Character::class.javaPrimitiveType;
                  }
                  break;
               case 344809556:
                  if (var2.equals("java.lang.Boolean")) {
                     return (Class<T>)java.lang.Boolean::class.javaPrimitiveType;
                  }
                  break;
               case 398507100:
                  if (var2.equals("java.lang.Byte")) {
                     return (Class<T>)java.lang.Byte::class.javaPrimitiveType;
                  }
                  break;
               case 398795216:
                  if (var2.equals("java.lang.Long")) {
                     return (Class<T>)java.lang.Long::class.javaPrimitiveType;
                  }
                  break;
               case 399092968:
                  if (var2.equals("java.lang.Void")) {
                     return (Class<T>)Void::class.javaPrimitiveType;
                  }
                  break;
               case 761287205:
                  if (var2.equals("java.lang.Double")) {
                     return (Class<T>)java.lang.Double::class.javaPrimitiveType;
                  }
               default:
            }
         }

         return null;
      }
   }


public final val javaObjectType: Class<T>
   public final get() {
      val var1: Class = (var0 as ClassBasedDeclarationContainer).getJClass();
      if (!var1.isPrimitive()) {
         return var1;
      } else {
         val var2: java.lang.String = var1.getName();
         var var3: Class = var1;
         if (var2 != null) {
            switch (var2.hashCode()) {
               case -1325958191:
                  if (!var2.equals("double")) {
                     var3 = var1;
                  } else {
                     var3 = java.lang.Double::class.javaObjectType;
                  }
                  break;
               case 104431:
                  if (!var2.equals("int")) {
                     var3 = var1;
                  } else {
                     var3 = Integer::class.javaObjectType;
                  }
                  break;
               case 3039496:
                  if (!var2.equals("byte")) {
                     var3 = var1;
                  } else {
                     var3 = java.lang.Byte::class.javaObjectType;
                  }
                  break;
               case 3052374:
                  if (!var2.equals("char")) {
                     var3 = var1;
                  } else {
                     var3 = Character::class.javaObjectType;
                  }
                  break;
               case 3327612:
                  if (!var2.equals("long")) {
                     var3 = var1;
                  } else {
                     var3 = java.lang.Long::class.javaObjectType;
                  }
                  break;
               case 3625364:
                  if (!var2.equals("void")) {
                     var3 = var1;
                  } else {
                     var3 = Void::class.javaObjectType;
                  }
                  break;
               case 64711720:
                  if (!var2.equals("boolean")) {
                     var3 = var1;
                  } else {
                     var3 = java.lang.Boolean::class.javaObjectType;
                  }
                  break;
               case 97526364:
                  if (!var2.equals("float")) {
                     var3 = var1;
                  } else {
                     var3 = java.lang.Float::class.javaObjectType;
                  }
                  break;
               case 109413500:
                  if (!var2.equals("short")) {
                     var3 = var1;
                  } else {
                     var3 = java.lang.Short::class.javaObjectType;
                  }
                  break;
               default:
                  var3 = var1;
            }
         }

         return var3;
      }
   }


public final val kotlin: KClass<T>
   public final get() {
      return var0.kotlin;
   }


public final val javaClass: Class<T>
   public final inline get() {
      var0 = var0.getClass();
      return var0;
   }


@Deprecated(
   level = DeprecationLevel.ERROR,
   message = "Use 'java' property to get Java class corresponding to this Kotlin class or cast this instance to Any if you really want to get the runtime Java class of this implementation of KClass.",
   replaceWith = @ReplaceWith(
      expression = "(this as Any).javaClass",
      imports = {}
   )
)
public final val javaClass: Class<KClass<T>>
   public final inline get() {
      val var1: Class = var0.getClass();
      return var1;
   }


public final val annotationClass: KClass<out T>
   public final get() {
      val var1: Class = var0.annotationType();
      val var2: KClass = getKotlinClass(var1);
      return var2;
   }


public final val declaringJavaClass: Class<E>
   public final inline get() {
      val var1: Class = var0.getDeclaringClass();
      return var1;
   }


@JvmSynthetic
public fun <reified T : Any> Array<*>.isArrayOf(): Boolean {
   Intrinsics.reifiedOperationMarker(4, "T");
   var var1: Class = Object::class.java;
   var1 = var0.getClass();
   val var2: Class = var1;
   return Object.class.isAssignableFrom(var1.getComponentType());
}
