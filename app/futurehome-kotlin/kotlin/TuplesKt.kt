package kotlin

public infix fun <A, B> A.to(that: B): Pair<A, B> {
   return (Pair<A, B>)(new Pair<>(var0, var1));
}

public fun <T> Pair<T, T>.toList(): List<T> {
   return (java.util.List<T>)CollectionsKt.listOf(new Object[]{var0.getFirst(), var0.getSecond()});
}

public fun <T> Triple<T, T, T>.toList(): List<T> {
   return (java.util.List<T>)CollectionsKt.listOf(new Object[]{var0.getFirst(), var0.getSecond(), var0.getThird()});
}
