package kotlin.jvm.internal

import java.lang.reflect.Constructor
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function10
import kotlin.jvm.functions.Function11
import kotlin.jvm.functions.Function12
import kotlin.jvm.functions.Function13
import kotlin.jvm.functions.Function14
import kotlin.jvm.functions.Function15
import kotlin.jvm.functions.Function16
import kotlin.jvm.functions.Function17
import kotlin.jvm.functions.Function18
import kotlin.jvm.functions.Function19
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function20
import kotlin.jvm.functions.Function21
import kotlin.jvm.functions.Function22
import kotlin.jvm.functions.Function3
import kotlin.jvm.functions.Function4
import kotlin.jvm.functions.Function5
import kotlin.jvm.functions.Function6
import kotlin.jvm.functions.Function7
import kotlin.jvm.functions.Function8
import kotlin.jvm.functions.Function9
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.KTypeParameter
import kotlin.reflect.KVisibility

public class ClassReference(jClass: Class<*>) : KClass<Object>, ClassBasedDeclarationContainer {
   public open val jClass: Class<*>

   public open val simpleName: String?
      public open get() {
         return Companion.getClassSimpleName(this.getJClass());
      }


   public open val qualifiedName: String?
      public open get() {
         return Companion.getClassQualifiedName(this.getJClass());
      }


   public open val members: Collection<KCallable<*>>
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val constructors: Collection<KFunction<Any>>
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val nestedClasses: Collection<KClass<*>>
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val annotations: List<Annotation>
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val objectInstance: Any?
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val typeParameters: List<KTypeParameter>
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val supertypes: List<KType>
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val sealedSubclasses: List<KClass<out Any>>
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val visibility: KVisibility?
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val isFinal: Boolean
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val isOpen: Boolean
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val isAbstract: Boolean
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val isSealed: Boolean
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val isData: Boolean
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val isInner: Boolean
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val isCompanion: Boolean
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val isFun: Boolean
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   public open val isValue: Boolean
      public open get() {
         this.error();
         throw new KotlinNothingValueException();
      }


   @JvmStatic
   fun {
      var var0: Int = 0;
      var var2: java.lang.Iterable = CollectionsKt.listOf(
         new Class[]{
            Function0.class,
            Function1.class,
            Function2.class,
            Function3.class,
            Function4.class,
            Function5.class,
            Function6.class,
            Function7.class,
            Function8.class,
            Function9.class,
            Function10.class,
            Function11.class,
            Function12.class,
            Function13.class,
            Function14.class,
            Function15.class,
            Function16.class,
            Function17.class,
            Function18.class,
            Function19.class,
            Function20.class,
            Function21.class,
            Function22.class
         }
      );
      val var1: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));

      for (Object var3 : var2) {
         if (var0 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         var1.add(TuplesKt.to(var3 as Class, var0));
         var0++;
      }

      FUNCTION_CLASSES = MapsKt.toMap(var1);
      val var11: HashMap = new HashMap();
      var11.put("boolean", "kotlin.Boolean");
      var11.put("char", "kotlin.Char");
      var11.put("byte", "kotlin.Byte");
      var11.put("short", "kotlin.Short");
      var11.put("int", "kotlin.Int");
      var11.put("float", "kotlin.Float");
      var11.put("long", "kotlin.Long");
      var11.put("double", "kotlin.Double");
      primitiveFqNames = var11;
      val var18: HashMap = new HashMap();
      var18.put("java.lang.Boolean", "kotlin.Boolean");
      var18.put("java.lang.Character", "kotlin.Char");
      var18.put("java.lang.Byte", "kotlin.Byte");
      var18.put("java.lang.Short", "kotlin.Short");
      var18.put("java.lang.Integer", "kotlin.Int");
      var18.put("java.lang.Float", "kotlin.Float");
      var18.put("java.lang.Long", "kotlin.Long");
      var18.put("java.lang.Double", "kotlin.Double");
      primitiveWrapperFqNames = var18;
      val var8: HashMap = new HashMap();
      var8.put("java.lang.Object", "kotlin.Any");
      var8.put("java.lang.String", "kotlin.String");
      var8.put("java.lang.CharSequence", "kotlin.CharSequence");
      var8.put("java.lang.Throwable", "kotlin.Throwable");
      var8.put("java.lang.Cloneable", "kotlin.Cloneable");
      var8.put("java.lang.Number", "kotlin.Number");
      var8.put("java.lang.Comparable", "kotlin.Comparable");
      var8.put("java.lang.Enum", "kotlin.Enum");
      var8.put("java.lang.annotation.Annotation", "kotlin.Annotation");
      var8.put("java.lang.Iterable", "kotlin.collections.Iterable");
      var8.put("java.util.Iterator", "kotlin.collections.Iterator");
      var8.put("java.util.Collection", "kotlin.collections.Collection");
      var8.put("java.util.List", "kotlin.collections.List");
      var8.put("java.util.Set", "kotlin.collections.Set");
      var8.put("java.util.ListIterator", "kotlin.collections.ListIterator");
      var8.put("java.util.Map", "kotlin.collections.Map");
      var8.put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
      var8.put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
      var8.put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
      var8.putAll(var11);
      var8.putAll(var18);
      val var12: java.util.Collection = var11.values();

      for (Object var4 : var12) {
         val var19: java.util.Map = var8;
         var4 = var4 as java.lang.String;
         val var5: StringBuilder = new StringBuilder("kotlin.jvm.internal.");
         var5.append(StringsKt.substringAfterLast$default((java.lang.String)var4, '.', null, 2, null));
         var5.append("CompanionObject");
         val var29: java.lang.String = var5.toString();
         val var6: StringBuilder = new StringBuilder();
         var6.append((java.lang.String)var4);
         var6.append(".Companion");
         var4 = TuplesKt.to(var29, var6.toString());
         var19.put(((Pair)var4).getFirst(), ((Pair)var4).getSecond());
      }

      val var14: java.util.Map = var8;

      for (Entry var20 : FUNCTION_CLASSES.entrySet()) {
         val var25: Class = var20.getKey() as Class;
         var0 = (var20.getValue() as java.lang.Number).intValue();
         val var21: java.lang.String = var25.getName();
         val var26: StringBuilder = new StringBuilder("kotlin.Function");
         var26.append(var0);
         var8.put(var21, var26.toString());
      }

      classFqNames = var8;
      val var16: java.util.Map = var8;
      val var9: java.util.Map = new LinkedHashMap(MapsKt.mapCapacity(var8.size()));

      for (Entry var27 : var16.entrySet()) {
         var2 = (java.lang.Iterable)var27.getKey();
         val var28: java.lang.String = var27.getValue() as java.lang.String;
         var9.put(var2, StringsKt.substringAfterLast$default(var28, '.', null, 2, null));
      }

      simpleNames = var9;
   }

   init {
      this.jClass = var1;
   }

   private fun error(): Nothing {
      throw new KotlinReflectionNotSupportedError();
   }

   public override operator fun equals(other: Any?): Boolean {
      val var2: Boolean;
      if (var1 is ClassReference && JvmClassMappingKt.<Object>getJavaObjectType(this as KClass<Object>) == JvmClassMappingKt.getJavaObjectType(var1 as KClass)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun hashCode(): Int {
      return JvmClassMappingKt.getJavaObjectType(this as KClass<Object>).hashCode();
   }

   public override fun isInstance(value: Any?): Boolean {
      return Companion.isInstance(var1, this.getJClass());
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this.getJClass());
      var1.append(" (Kotlin reflection is not available)");
      return var1.toString();
   }

   public companion object {
      private final val FUNCTION_CLASSES: Map<Class<out () -> *>, Int>
      private final val primitiveFqNames: HashMap<String, String>
      private final val primitiveWrapperFqNames: HashMap<String, String>
      private final val classFqNames: HashMap<String, String>
      private final val simpleNames: Map<String, String>

      public fun getClassQualifiedName(jClass: Class<*>): String? {
         var var7: java.lang.String;
         if (var1.isAnonymousClass()) {
            var7 = null;
         } else if (var1.isLocalClass()) {
            var7 = null;
         } else if (var1.isArray()) {
            val var8: Class = var1.getComponentType();
            var var5: java.lang.String = null;
            if (var8.isPrimitive()) {
               val var9: java.lang.String = ClassReference.access$getClassFqNames$cp().get(var8.getName()) as java.lang.String;
               var5 = null;
               if (var9 != null) {
                  val var6: StringBuilder = new StringBuilder();
                  var6.append(var9);
                  var6.append("Array");
                  var5 = var6.toString();
               }
            }

            var7 = var5;
            if (var5 == null) {
               var7 = "kotlin.Array";
            }
         } else {
            val var10: java.lang.String = ClassReference.access$getClassFqNames$cp().get(var1.getName()) as java.lang.String;
            var7 = var10;
            if (var10 == null) {
               var7 = var1.getCanonicalName();
            }
         }

         return var7;
      }

      public fun getClassSimpleName(jClass: Class<*>): String? {
         var var10: java.lang.String;
         if (var1.isAnonymousClass()) {
            var10 = null;
         } else if (var1.isLocalClass()) {
            val var5: java.lang.String = var1.getSimpleName();
            val var13: Method = var1.getEnclosingMethod();
            if (var13 != null) {
               val var11: StringBuilder = new StringBuilder();
               var11.append(var13.getName());
               var11.append('$');
               val var14: java.lang.String = StringsKt.substringAfter$default(var5, var11.toString(), null, 2, null);
               if (var14 != null) {
                  return var14;
               }
            }

            val var12: Constructor = var1.getEnclosingConstructor();
            if (var12 != null) {
               val var6: StringBuilder = new StringBuilder();
               var6.append(var12.getName());
               var6.append('$');
               var10 = StringsKt.substringAfter$default(var5, var6.toString(), null, 2, null);
            } else {
               var10 = StringsKt.substringAfter$default(var5, '$', null, 2, null);
            }
         } else if (var1.isArray()) {
            val var17: Class = var1.getComponentType();
            val var9: Boolean = var17.isPrimitive();
            var var7: java.lang.String = null;
            if (var9) {
               val var18: java.lang.String = ClassReference.access$getSimpleNames$cp().get(var17.getName()) as java.lang.String;
               var7 = null;
               if (var18 != null) {
                  val var8: StringBuilder = new StringBuilder();
                  var8.append(var18);
                  var8.append("Array");
                  var7 = var8.toString();
               }
            }

            var10 = var7;
            if (var7 == null) {
               var10 = "Array";
            }
         } else {
            val var16: java.lang.String = ClassReference.access$getSimpleNames$cp().get(var1.getName()) as java.lang.String;
            var10 = var16;
            if (var16 == null) {
               var10 = var1.getSimpleName();
            }
         }

         return var10;
      }

      public fun isInstance(value: Any?, jClass: Class<*>): Boolean {
         val var3: java.util.Map = ClassReference.access$getFUNCTION_CLASSES$cp();
         val var4: Int = var3.get(var2) as Int;
         if (var4 != null) {
            return TypeIntrinsics.isFunctionOfArity(var1, var4.intValue());
         } else {
            var var5: Class = var2;
            if (var2.isPrimitive()) {
               var5 = JvmClassMappingKt.getJavaObjectType(JvmClassMappingKt.getKotlinClass(var2));
            }

            return var5.isInstance(var1);
         }
      }
   }
}
