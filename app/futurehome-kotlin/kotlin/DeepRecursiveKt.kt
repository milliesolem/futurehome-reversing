package kotlin

import kotlin.coroutines.intrinsics.IntrinsicsKt

private final val UNDEFINED_RESULT: Result<Any>
private Object UNDEFINED_RESULT = Result.constructor-impl(IntrinsicsKt.getCOROUTINE_SUSPENDED());

@JvmSynthetic
fun `access$getUNDEFINED_RESULT$p`(): Any {
   return UNDEFINED_RESULT;
}

public operator fun <T, R> DeepRecursiveFunction<T, R>.invoke(value: T): R {
   return (R)new DeepRecursiveScopeImpl(var0.getBlock$kotlin_stdlib(), var1).runCallLoop();
}
