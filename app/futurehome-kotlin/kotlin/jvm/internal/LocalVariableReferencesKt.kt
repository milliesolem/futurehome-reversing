package kotlin.jvm.internal

@JvmSynthetic
fun `access$notSupportedError`(): Void {
   return notSupportedError();
}

private fun notSupportedError(): Nothing {
   throw new UnsupportedOperationException("Not supported for local property reference.");
}
