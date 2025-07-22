package kotlin

import kotlin.coroutines.Continuation

public class DeepRecursiveFunction<T, R>(block: (DeepRecursiveScope<Any, Any>, Any, Continuation<Any>) -> Any?) {
   internal final val block: (DeepRecursiveScope<Any, Any>, Any, Continuation<Any>) -> Any?

   init {
      this.block = var1;
   }
}
