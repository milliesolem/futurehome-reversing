package kotlin.enums

@JvmSynthetic
public inline fun <reified T : Enum<T>> enumEntries(): EnumEntries<T> {
   throw new NotImplementedError(null, 1, null);
}

internal fun <E : Enum<E>> enumEntries(entriesProvider: () -> Array<E>): EnumEntries<E> {
   return new EnumEntriesList(var0.invoke() as Array<java.lang.Enum>);
}

internal fun <E : Enum<E>> enumEntries(entries: Array<E>): EnumEntries<E> {
   return new EnumEntriesList(var0);
}
