package kotlin.jvm.internal

import kotlin.reflect.KDeclarationContainer

public open class MutableLocalVariableReference : MutablePropertyReference0 {
   public override fun get(): Any? {
      LocalVariableReferencesKt.access$notSupportedError();
      throw new KotlinNothingValueException();
   }

   public override fun getOwner(): KDeclarationContainer {
      LocalVariableReferencesKt.access$notSupportedError();
      throw new KotlinNothingValueException();
   }

   public override fun set(value: Any?) {
      LocalVariableReferencesKt.access$notSupportedError();
      throw new KotlinNothingValueException();
   }
}
