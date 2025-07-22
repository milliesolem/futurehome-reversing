package kotlinx.coroutines.internal

@JvmSynthetic
fun `CommonThreadLocal$annotations`() {
}

internal fun <T> commonThreadLocal(name: Symbol): ThreadLocal<T> {
   return new ThreadLocal();
}
