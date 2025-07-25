package kotlin.jvm.internal;

import java.util.List;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KVariance;

public class ReflectionFactory {
   private static final String KOTLIN_JVM_FUNCTIONS = "kotlin.jvm.functions.";

   public KClass createKotlinClass(Class var1) {
      return new ClassReference(var1);
   }

   public KClass createKotlinClass(Class var1, String var2) {
      return new ClassReference(var1);
   }

   public KFunction function(FunctionReference var1) {
      return var1;
   }

   public KClass getOrCreateKotlinClass(Class var1) {
      return new ClassReference(var1);
   }

   public KClass getOrCreateKotlinClass(Class var1, String var2) {
      return new ClassReference(var1);
   }

   public KDeclarationContainer getOrCreateKotlinPackage(Class var1, String var2) {
      return new PackageReference(var1, var2);
   }

   public KType mutableCollectionType(KType var1) {
      TypeReference var2 = (TypeReference)var1;
      return new TypeReference(var1.getClassifier(), var1.getArguments(), var2.getPlatformTypeUpperBound$kotlin_stdlib(), var2.getFlags$kotlin_stdlib() | 2);
   }

   public KMutableProperty0 mutableProperty0(MutablePropertyReference0 var1) {
      return var1;
   }

   public KMutableProperty1 mutableProperty1(MutablePropertyReference1 var1) {
      return var1;
   }

   public KMutableProperty2 mutableProperty2(MutablePropertyReference2 var1) {
      return var1;
   }

   public KType nothingType(KType var1) {
      TypeReference var2 = (TypeReference)var1;
      return new TypeReference(var1.getClassifier(), var1.getArguments(), var2.getPlatformTypeUpperBound$kotlin_stdlib(), var2.getFlags$kotlin_stdlib() | 4);
   }

   public KType platformType(KType var1, KType var2) {
      return new TypeReference(var1.getClassifier(), var1.getArguments(), var2, ((TypeReference)var1).getFlags$kotlin_stdlib());
   }

   public KProperty0 property0(PropertyReference0 var1) {
      return var1;
   }

   public KProperty1 property1(PropertyReference1 var1) {
      return var1;
   }

   public KProperty2 property2(PropertyReference2 var1) {
      return var1;
   }

   public String renderLambdaToString(FunctionBase var1) {
      String var2 = var1.getClass().getGenericInterfaces()[0].toString();
      String var3 = var2;
      if (var2.startsWith("kotlin.jvm.functions.")) {
         var3 = var2.substring(21);
      }

      return var3;
   }

   public String renderLambdaToString(Lambda var1) {
      return this.renderLambdaToString((FunctionBase)var1);
   }

   public void setUpperBounds(KTypeParameter var1, List<KType> var2) {
      ((TypeParameterReference)var1).setUpperBounds(var2);
   }

   public KType typeOf(KClassifier var1, List<KTypeProjection> var2, boolean var3) {
      return new TypeReference(var1, var2, var3);
   }

   public KTypeParameter typeParameter(Object var1, String var2, KVariance var3, boolean var4) {
      return new TypeParameterReference(var1, var2, var3, var4);
   }
}
