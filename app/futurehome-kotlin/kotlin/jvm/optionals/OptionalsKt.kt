package kotlin.jvm.optionals

import j..util.Optional
import kotlin.contracts.InvocationKind

public fun <T : Any> Optional<out Any>.asSequence(): Sequence<Any> {
   val var1: Sequence;
   if (var0.isPresent()) {
      var1 = SequencesKt.sequenceOf(new Object[]{var0.get()});
   } else {
      var1 = SequencesKt.emptySequence();
   }

   return var1;
}

public fun <T> Optional<out Any>.getOrDefault(defaultValue: Any): Any {
   if (var0.isPresent()) {
      var1 = var0.get();
   }

   return (T)var1;
}

public inline fun <T> Optional<out Any>.getOrElse(defaultValue: () -> Any): Any {
   contract {
      callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
   }

   val var2: Any;
   if (var0.isPresent()) {
      var2 = var0.get();
   } else {
      var2 = var1.invoke();
   }

   return (T)var2;
}

public fun <T : Any> Optional<Any>.getOrNull(): Any? {
   return (T)var0.orElse(null);
}

public fun <T : Any, C : MutableCollection<in Any>> Optional<Any>.toCollection(destination: Any): Any {
   if (var0.isPresent()) {
      val var2: Any = var0.get();
      var1.add(var2);
   }

   return (C)var1;
}

public fun <T : Any> Optional<out Any>.toList(): List<Any> {
   val var1: java.util.List;
   if (var0.isPresent()) {
      var1 = CollectionsKt.listOf(var0.get());
   } else {
      var1 = CollectionsKt.emptyList();
   }

   return var1;
}

public fun <T : Any> Optional<out Any>.toSet(): Set<Any> {
   val var1: java.util.Set;
   if (var0.isPresent()) {
      var1 = SetsKt.setOf(var0.get());
   } else {
      var1 = SetsKt.emptySet();
   }

   return var1;
}
