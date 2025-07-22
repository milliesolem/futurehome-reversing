package kotlinx.coroutines.internal

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.ArrayList
import kotlin.jvm.functions.Function1
import kotlinx.coroutines.CopyableThrowable

private final val ctorCache: CtorCache
private final val throwableFields: Int

@JvmSynthetic
fun `access$createConstructor`(var0: Class): Function1 {
   return createConstructor(var0);
}

private fun <E : Throwable> createConstructor(clz: Class<E>): (Throwable) -> Throwable? {
   val var5: Function1 = <unrepresentable>.INSTANCE;
   if (throwableFields != fieldsCountOrDefault(var0, 0)) {
      return var5;
   } else {
      var var4: Array<Constructor> = var0.getConstructors();
      val var6: java.util.Collection = new ArrayList(var4.length);
      var var2: Int = var4.length;
      var var1: Int = 0;

      while (true) {
         var var8: Function1 = null;
         if (var1 >= var2) {
            val var19: java.util.Iterator = (var6 as java.util.List).iterator();
            if (var19.hasNext()) {
               var8 = (Function1)var19.next();
               if (var19.hasNext()) {
                  var2 = ((var8 as Pair).getSecond() as java.lang.Number).intValue();
                  var4 = var8;

                  do {
                     val var17: Any = var19.next();
                     val var14: Int = ((var17 as Pair).getSecond() as java.lang.Number).intValue();
                     var1 = var2;
                     var8 = var4;
                     if (var2 < var14) {
                        var8 = (Function1)var17;
                        var1 = var14;
                     }

                     var2 = var1;
                     var4 = var8;
                  } while (var19.hasNext());
               }
            }

            val var16: Pair = var8 as Pair;
            var8 = var5;
            if (var16 != null) {
               var8 = var16.getFirst() as Function1;
               if (var8 == null) {
                  var8 = var5;
               }
            }

            return var8;
         }

         val var9: Constructor = var4[var1];
         val var7: Array<Class> = var4[var1].getParameterTypes();
         val var3: Int = var7.length;
         val var10: Pair;
         if (var7.length != 0) {
            if (var3 != 1) {
               if (var3 != 2) {
                  var10 = TuplesKt.to(null, -1);
               } else if (var7[0] == java.lang.String::class.java && var7[1] == java.lang.Throwable::class.java) {
                  var10 = TuplesKt.to(safeCtor((new Function1<java.lang.Throwable, java.lang.Throwable>(var9) {
                     final Constructor<?> $constructor;

                     {
                        super(1);
                        this.$constructor = var1;
                     }

                     public final java.lang.Throwable invoke(java.lang.Throwable var1) {
                        val var2: Any = this.$constructor.newInstance(var1.getMessage(), var1);
                        return var2 as java.lang.Throwable;
                     }
                  }) as (java.lang.Throwable?) -> java.lang.Throwable), 3);
               } else {
                  var10 = TuplesKt.to(null, -1);
               }
            } else {
               val var18: Class = var7[0];
               if (var7[0] == java.lang.String::class.java) {
                  var10 = TuplesKt.to(safeCtor((new Function1<java.lang.Throwable, java.lang.Throwable>(var9) {
                     final Constructor<?> $constructor;

                     {
                        super(1);
                        this.$constructor = var1;
                     }

                     public final java.lang.Throwable invoke(java.lang.Throwable var1) {
                        val var2: Any = this.$constructor.newInstance(var1.getMessage());
                        val var3: java.lang.Throwable = var2 as java.lang.Throwable;
                        (var2 as java.lang.Throwable).initCause(var1);
                        return var3;
                     }
                  }) as (java.lang.Throwable?) -> java.lang.Throwable), 2);
               } else if (var18 == java.lang.Throwable::class.java) {
                  var10 = TuplesKt.to(safeCtor((new Function1<java.lang.Throwable, java.lang.Throwable>(var9) {
                     final Constructor<?> $constructor;

                     {
                        super(1);
                        this.$constructor = var1;
                     }

                     public final java.lang.Throwable invoke(java.lang.Throwable var1) {
                        val var2: Any = this.$constructor.newInstance(var1);
                        return var2 as java.lang.Throwable;
                     }
                  }) as (java.lang.Throwable?) -> java.lang.Throwable), 1);
               } else {
                  var10 = TuplesKt.to(null, -1);
               }
            }
         } else {
            var10 = TuplesKt.to(safeCtor((new Function1<java.lang.Throwable, java.lang.Throwable>(var9) {
               final Constructor<?> $constructor;

               {
                  super(1);
                  this.$constructor = var1;
               }

               public final java.lang.Throwable invoke(java.lang.Throwable var1) {
                  val var2: Any = this.$constructor.newInstance(null);
                  val var3: java.lang.Throwable = var2 as java.lang.Throwable;
                  (var2 as java.lang.Throwable).initCause(var1);
                  return var3;
               }
            }) as (java.lang.Throwable?) -> java.lang.Throwable), 0);
         }

         var6.add(var10);
         var1++;
      }
   }
}

private tailrec fun Class<*>.fieldsCount(accumulator: Int = 0): Int {
   var var7: Int;
   val var8: Class;
   do {
      val var6: Array<Field> = var0.getDeclaredFields();
      val var5: Int = var6.length;
      var var3: Int = 0;
      var7 = 0;

      while (var3 < var5) {
         var var4: Int = var7;
         if (!Modifier.isStatic(var6[var3].getModifiers())) {
            var4 = var7 + 1;
         }

         var3++;
         var7 = var4;
      }

      var7 = var1 + var7;
      var8 = var0.getSuperclass();
      var0 = var8;
      var1 = var7;
   } while (var8 != null);

   return var7;
}

@JvmSynthetic
fun `fieldsCount$default`(var0: Class, var1: Int, var2: Int, var3: Any): Int {
   if ((var2 and 1) != 0) {
      var1 = 0;
   }

   return fieldsCount(var0, var1);
}

private fun Class<*>.fieldsCountOrDefault(defaultValue: Int): Int {
   JvmClassMappingKt.getKotlinClass(var0);

   label16:
   try {
      val var2: Result.Companion = Result.Companion;
      var6 = Result.constructor-impl(fieldsCount$default(var0, 0, 1, null));
   } catch (var3: java.lang.Throwable) {
      val var5: Result.Companion = Result.Companion;
      var6 = Result.constructor-impl(ResultKt.createFailure(var3));
      break label16;
   }

   var var7: Any = var6;
   if (Result.isFailure-impl(var6)) {
      var7 = var1;
   }

   return (var7 as java.lang.Number).intValue();
}

private fun safeCtor(block: (Throwable) -> Throwable): (Throwable) -> Throwable? {
   return (
      new Function1<java.lang.Throwable, java.lang.Throwable>(var0) {
         final Function1<java.lang.Throwable, java.lang.Throwable> $block;

         {
            super(1);
            this.$block = var1;
         }

         // $VF: Duplicated exception handlers to handle obfuscated exceptions
         // $VF: Could not inline inconsistent finally blocks
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         public final java.lang.Throwable invoke(java.lang.Throwable var1) {
            val var4: Function1 = this.$block;

            label47: {
               try {
                  val var26: Result.Companion = Result.Companion;
                  var31 = var4.invoke(var1) as java.lang.Throwable;
               } catch (var8: java.lang.Throwable) {
                  val var2: Result.Companion = Result.Companion;
                  var25 = Result.constructor-impl(ResultKt.createFailure(var8));
                  break label47;
               }

               var var27: java.lang.Throwable = var31;

               label48: {
                  try {
                     if (var1.getMessage() == var31.getMessage()) {
                        break label48;
                     }
                  } catch (var7: java.lang.Throwable) {
                     val var28: Result.Companion = Result.Companion;
                     var25 = Result.constructor-impl(ResultKt.createFailure(var7));
                     break label47;
                  }

                  var27 = var31;

                  try {
                     if (var31.getMessage() == var1.toString()) {
                        break label48;
                     }
                  } catch (var6: java.lang.Throwable) {
                     val var29: Result.Companion = Result.Companion;
                     var25 = Result.constructor-impl(ResultKt.createFailure(var6));
                     break label47;
                  }

                  var27 = null;
               }

               label28:
               try {
                  var25 = Result.constructor-impl(var27);
               } catch (var5: java.lang.Throwable) {
                  val var30: Result.Companion = Result.Companion;
                  var25 = Result.constructor-impl(ResultKt.createFailure(var5));
                  break label28;
               }
            }

            if (Result.isFailure-impl(var25)) {
               var25 = null;
            }

            return var25 as java.lang.Throwable;
         }
      }
   ) as (java.lang.Throwable?) -> java.lang.Throwable;
}

internal fun <E : Throwable> tryCopyException(exception: E): E? {
   if (var0 is CopyableThrowable) {
      label17:
      try {
         val var5: Result.Companion = Result.Companion;
         var4 = Result.constructor-impl((var0 as CopyableThrowable).createCopy());
      } catch (var2: java.lang.Throwable) {
         val var1: Result.Companion = Result.Companion;
         var4 = Result.constructor-impl(ResultKt.createFailure(var2));
         break label17;
      }

      var var6: Any = var4;
      if (Result.isFailure-impl(var4)) {
         var6 = null;
      }

      return (E)(var6 as java.lang.Throwable);
   } else {
      return (E)(ctorCache.get((Class<? extends java.lang.Throwable>)var0.getClass()).invoke(var0) as java.lang.Throwable);
   }
}
