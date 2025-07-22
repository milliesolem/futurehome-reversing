package kotlin

import kotlin.coroutines.Continuation

public inline fun <R> suspend(noinline block: (Continuation<R>) -> Any?): (Continuation<R>) -> Any? {
   return var0;
}
