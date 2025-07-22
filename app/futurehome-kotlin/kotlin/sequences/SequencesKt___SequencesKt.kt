package kotlin.sequences

import java.util.ArrayList
import java.util.Comparator
import java.util.HashSet
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.NoSuchElementException
import kotlin.contracts.InvocationKind
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.Boxing
import kotlin.internal.PlatformImplementationsKt
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.Ref

internal class SequencesKt___SequencesKt : SequencesKt___SequencesJvmKt {
   open fun SequencesKt___SequencesKt() {
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.all(predicate: (T) -> Boolean): Boolean {
      val var2: java.util.Iterator = var0.iterator();

      while (var2.hasNext()) {
         if (!var1.invoke(var2.next()) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public fun <T> Sequence<T>.any(): Boolean {
      return var0.iterator().hasNext();
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.any(predicate: (T) -> Boolean): Boolean {
      val var2: java.util.Iterator = var0.iterator();

      while (var2.hasNext()) {
         if (var1.invoke(var2.next()) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public fun <T> Sequence<T>.asIterable(): Iterable<T> {
      return new java.lang.Iterable<T>(var0) {
         final Sequence $this_asIterable$inlined;

         {
            this.$this_asIterable$inlined = var1;
         }

         @Override
         public java.util.Iterator<T> iterator() {
            return this.$this_asIterable$inlined.iterator();
         }
      };
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.asSequence(): Sequence<T> {
      return var0;
   }

   @JvmStatic
   public inline fun <T, K, V> Sequence<T>.associate(transform: (T) -> Pair<K, V>): Map<K, V> {
      val var2: java.util.Map = new LinkedHashMap();
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         val var4: Pair = var1.invoke(var3.next()) as Pair;
         var2.put(var4.getFirst(), var4.getSecond());
      }

      return var2;
   }

   @JvmStatic
   public inline fun <T, K> Sequence<T>.associateBy(keySelector: (T) -> K): Map<K, T> {
      val var2: java.util.Map = new LinkedHashMap();

      for (Object var3 : var0) {
         var2.put(var1.invoke(var3), var3);
      }

      return var2;
   }

   @JvmStatic
   public inline fun <T, K, V> Sequence<T>.associateBy(keySelector: (T) -> K, valueTransform: (T) -> V): Map<K, V> {
      val var3: java.util.Map = new LinkedHashMap();

      for (Object var4 : var0) {
         var3.put(var1.invoke(var4), var2.invoke(var4));
      }

      return var3;
   }

   @JvmStatic
   public inline fun <T, K, M : MutableMap<in K, in T>> Sequence<T>.associateByTo(destination: M, keySelector: (T) -> K): M {
      for (Object var4 : var0) {
         var1.put(var2.invoke(var4), var4);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K, V, M : MutableMap<in K, in V>> Sequence<T>.associateByTo(destination: M, keySelector: (T) -> K, valueTransform: (T) -> V): M {
      for (Object var5 : var0) {
         var1.put(var2.invoke(var5), var3.invoke(var5));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K, V, M : MutableMap<in K, in V>> Sequence<T>.associateTo(destination: M, transform: (T) -> Pair<K, V>): M {
      val var4: java.util.Iterator = var0.iterator();

      while (var4.hasNext()) {
         val var3: Pair = var2.invoke(var4.next()) as Pair;
         var1.put(var3.getFirst(), var3.getSecond());
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V> Sequence<K>.associateWith(valueSelector: (K) -> V): Map<K, V> {
      val var2: LinkedHashMap = new LinkedHashMap();

      for (Object var3 : var0) {
         var2.put(var3, var1.invoke(var3));
      }

      return var2;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> Sequence<K>.associateWithTo(destination: M, valueSelector: (K) -> V): M {
      for (Object var4 : var0) {
         var1.put(var4, var2.invoke(var4));
      }

      return (M)var1;
   }

   @JvmStatic
   public fun Sequence<Byte>.average(): Double {
      val var7: java.util.Iterator = var0.iterator();
      var var1: Double = 0.0;
      var var5: Int = 0;

      while (var7.hasNext()) {
         val var3: Double = var1 + (var7.next() as java.lang.Number).byteValue();
         val var6: Int = var5 + 1;
         var1 = var3;
         var5 = var6;
         if (var6 < 0) {
            CollectionsKt.throwCountOverflow();
            var1 = var3;
            var5 = var6;
         }
      }

      if (var5 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var5;
      }

      return var1;
   }

   @JvmStatic
   public fun Sequence<Double>.average(): Double {
      val var7: java.util.Iterator = var0.iterator();
      var var1: Double = 0.0;
      var var5: Int = 0;

      while (var7.hasNext()) {
         val var3: Double = var1 + (var7.next() as java.lang.Number).doubleValue();
         val var6: Int = var5 + 1;
         var1 = var3;
         var5 = var6;
         if (var6 < 0) {
            CollectionsKt.throwCountOverflow();
            var1 = var3;
            var5 = var6;
         }
      }

      if (var5 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var5;
      }

      return var1;
   }

   @JvmStatic
   public fun Sequence<Float>.average(): Double {
      val var7: java.util.Iterator = var0.iterator();
      var var1: Double = 0.0;
      var var5: Int = 0;

      while (var7.hasNext()) {
         val var3: Double = var1 + (var7.next() as java.lang.Number).floatValue();
         val var6: Int = var5 + 1;
         var1 = var3;
         var5 = var6;
         if (var6 < 0) {
            CollectionsKt.throwCountOverflow();
            var1 = var3;
            var5 = var6;
         }
      }

      if (var5 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var5;
      }

      return var1;
   }

   @JvmStatic
   public fun Sequence<Int>.average(): Double {
      val var7: java.util.Iterator = var0.iterator();
      var var1: Double = 0.0;
      var var5: Int = 0;

      while (var7.hasNext()) {
         val var3: Double = var1 + (var7.next() as java.lang.Number).intValue();
         val var6: Int = var5 + 1;
         var1 = var3;
         var5 = var6;
         if (var6 < 0) {
            CollectionsKt.throwCountOverflow();
            var1 = var3;
            var5 = var6;
         }
      }

      if (var5 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var5;
      }

      return var1;
   }

   @JvmStatic
   public fun Sequence<Long>.average(): Double {
      val var7: java.util.Iterator = var0.iterator();
      var var1: Double = 0.0;
      var var5: Int = 0;

      while (var7.hasNext()) {
         val var3: Double = var1 + (var7.next() as java.lang.Number).longValue();
         val var6: Int = var5 + 1;
         var1 = var3;
         var5 = var6;
         if (var6 < 0) {
            CollectionsKt.throwCountOverflow();
            var1 = var3;
            var5 = var6;
         }
      }

      if (var5 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var5;
      }

      return var1;
   }

   @JvmStatic
   public fun Sequence<Short>.average(): Double {
      val var7: java.util.Iterator = var0.iterator();
      var var1: Double = 0.0;
      var var5: Int = 0;

      while (var7.hasNext()) {
         val var3: Double = var1 + (var7.next() as java.lang.Number).shortValue();
         val var6: Int = var5 + 1;
         var1 = var3;
         var5 = var6;
         if (var6 < 0) {
            CollectionsKt.throwCountOverflow();
            var1 = var3;
            var5 = var6;
         }
      }

      if (var5 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var5;
      }

      return var1;
   }

   @JvmStatic
   public fun <T> Sequence<T>.chunked(size: Int): Sequence<List<T>> {
      return SequencesKt.windowed(var0, var1, var1, true);
   }

   @JvmStatic
   public fun <T, R> Sequence<T>.chunked(size: Int, transform: (List<T>) -> R): Sequence<R> {
      return SequencesKt.windowed(var0, var1, var1, true, var2);
   }

   @JvmStatic
   public operator fun <T> Sequence<T>.contains(element: T): Boolean {
      val var2: Boolean;
      if (SequencesKt.indexOf(var0, var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public fun <T> Sequence<T>.count(): Int {
      val var3: java.util.Iterator = var0.iterator();
      var var1: Int = 0;

      while (var3.hasNext()) {
         var3.next();
         val var2: Int = var1 + 1;
         var1 += 1;
         if (var2 < 0) {
            CollectionsKt.throwCountOverflow();
            var1 = var2;
         }
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.count(predicate: (T) -> Boolean): Int {
      val var4: java.util.Iterator = var0.iterator();
      var var2: Int = 0;

      while (var4.hasNext()) {
         if (var1.invoke(var4.next()) as java.lang.Boolean) {
            val var3: Int = var2 + 1;
            var2 += 1;
            if (var3 < 0) {
               if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
                  throw new ArithmeticException("Count overflow has happened.");
               }

               CollectionsKt.throwCountOverflow();
               var2 = var3;
            }
         }
      }

      return var2;
   }

   @JvmStatic
   public fun <T> Sequence<T>.distinct(): Sequence<T> {
      return SequencesKt.distinctBy(var0, new SequencesKt___SequencesKt$$ExternalSyntheticLambda9());
   }

   @JvmStatic
   fun `distinct$lambda$13$SequencesKt___SequencesKt`(var0: Any): Any {
      return var0;
   }

   @JvmStatic
   public fun <T, K> Sequence<T>.distinctBy(selector: (T) -> K): Sequence<T> {
      return new DistinctSequence(var0, var1);
   }

   @JvmStatic
   public fun <T> Sequence<T>.drop(n: Int): Sequence<T> {
      if (var1 >= 0) {
         if (var1 != 0) {
            if (var0 is DropTakeSequence) {
               var0 = (var0 as DropTakeSequence).drop(var1);
            } else {
               var0 = new DropSequence(var0, var1);
            }
         }

         return var0;
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun <T> Sequence<T>.dropWhile(predicate: (T) -> Boolean): Sequence<T> {
      return new DropWhileSequence(var0, var1);
   }

   @JvmStatic
   public fun <T> Sequence<T>.elementAt(index: Int): T {
      return (T)SequencesKt.elementAtOrElse(var0, var1, new SequencesKt___SequencesKt$$ExternalSyntheticLambda5(var1));
   }

   @JvmStatic
   fun `elementAt$lambda$0$SequencesKt___SequencesKt`(var0: Int, var1: Int): Any {
      val var2: StringBuilder = new StringBuilder("Sequence doesn't contain element at index ");
      var2.append(var0);
      var2.append('.');
      throw new IndexOutOfBoundsException(var2.toString());
   }

   @JvmStatic
   public fun <T> Sequence<T>.elementAtOrElse(index: Int, defaultValue: (Int) -> T): T {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      if (var1 < 0) {
         return (T)var2.invoke(var1);
      } else {
         val var5: java.util.Iterator = var0.iterator();

         for (int var3 = 0; var5.hasNext(); var3++) {
            val var4: Any = var5.next();
            if (var1 == var3) {
               return (T)var4;
            }
         }

         return (T)var2.invoke(var1);
      }
   }

   @JvmStatic
   public fun <T> Sequence<T>.elementAtOrNull(index: Int): T? {
      if (var1 < 0) {
         return null;
      } else {
         val var4: java.util.Iterator = var0.iterator();

         for (int var2 = 0; var4.hasNext(); var2++) {
            val var3: Any = var4.next();
            if (var1 == var2) {
               return (T)var3;
            }
         }

         return null;
      }
   }

   @JvmStatic
   public fun <T> Sequence<T>.filter(predicate: (T) -> Boolean): Sequence<T> {
      return new FilteringSequence(var0, true, var1);
   }

   @JvmStatic
   public fun <T> Sequence<T>.filterIndexed(predicate: (Int, T) -> Boolean): Sequence<T> {
      return new TransformingSequence(
         new FilteringSequence<>(new IndexingSequence(var0), true, new SequencesKt___SequencesKt$$ExternalSyntheticLambda2(var1)),
         new SequencesKt___SequencesKt$$ExternalSyntheticLambda3()
      );
   }

   @JvmStatic
   fun `filterIndexed$lambda$2$SequencesKt___SequencesKt`(var0: Function2, var1: IndexedValue): Boolean {
      return var0.invoke(var1.getIndex(), var1.getValue()) as java.lang.Boolean;
   }

   @JvmStatic
   fun `filterIndexed$lambda$3$SequencesKt___SequencesKt`(var0: IndexedValue): Any {
      return var0.getValue();
   }

   @JvmStatic
   public inline fun <T, C : MutableCollection<in T>> Sequence<T>.filterIndexedTo(destination: C, predicate: (Int, T) -> Boolean): C {
      val var4: java.util.Iterator = var0.iterator();

      for (int var3 = 0; var4.hasNext(); var3++) {
         val var5: Any = var4.next();
         if (var3 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         if (var2.invoke(var3, var5) as java.lang.Boolean) {
            var1.add(var5);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public fun <T> Sequence<T>.filterNot(predicate: (T) -> Boolean): Sequence<T> {
      return new FilteringSequence(var0, false, var1);
   }

   @JvmStatic
   public fun <T : Any> Sequence<T?>.filterNotNull(): Sequence<T> {
      var0 = SequencesKt.filterNot(var0, new SequencesKt___SequencesKt$$ExternalSyntheticLambda6());
      return var0;
   }

   @JvmStatic
   fun `filterNotNull$lambda$5$SequencesKt___SequencesKt`(var0: Any): Boolean {
      val var1: Boolean;
      if (var0 == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public fun <C : MutableCollection<in T>, T : Any> Sequence<T?>.filterNotNullTo(destination: C): C {
      for (Object var2 : var0) {
         if (var2 != null) {
            var1.add(var2);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, C : MutableCollection<in T>> Sequence<T>.filterNotTo(destination: C, predicate: (T) -> Boolean): C {
      for (Object var3 : var0) {
         if (!var2.invoke(var3) as java.lang.Boolean) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, C : MutableCollection<in T>> Sequence<T>.filterTo(destination: C, predicate: (T) -> Boolean): C {
      for (Object var3 : var0) {
         if (var2.invoke(var3) as java.lang.Boolean) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.find(predicate: (T) -> Boolean): T? {
      val var2: java.util.Iterator = var0.iterator();

      do {
         if (!var2.hasNext()) {
            var3 = null;
            break;
         }

         var3 = var2.next();
      } while (!var1.invoke(var3));

      return (T)var3;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.findLast(predicate: (T) -> Boolean): T? {
      val var3: java.util.Iterator = var0.iterator();
      var var4: Any = null;

      while (var3.hasNext()) {
         val var2: Any = var3.next();
         if (var1.invoke(var2) as java.lang.Boolean) {
            var4 = var2;
         }
      }

      return (T)var4;
   }

   @JvmStatic
   public fun <T> Sequence<T>.first(): T {
      val var1: java.util.Iterator = var0.iterator();
      if (var1.hasNext()) {
         return (T)var1.next();
      } else {
         throw new NoSuchElementException("Sequence is empty.");
      }
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.first(predicate: (T) -> Boolean): T {
      for (Object var3 : var0) {
         if (var1.invoke(var3) as java.lang.Boolean) {
            return (T)var3;
         }
      }

      throw new NoSuchElementException("Sequence contains no element matching the predicate.");
   }

   @JvmStatic
   public inline fun <T, R : Any> Sequence<T>.firstNotNullOf(transform: (T) -> R?): R {
      val var3: java.util.Iterator = var0.iterator();

      while (true) {
         if (var3.hasNext()) {
            val var2: Any = var1.invoke(var3.next());
            var4 = var2;
            if (var2 == null) {
               continue;
            }
            break;
         }

         var4 = null;
         break;
      }

      if (var4 != null) {
         return (R)var4;
      } else {
         throw new NoSuchElementException("No element of the sequence was transformed to a non-null value.");
      }
   }

   @JvmStatic
   public inline fun <T, R : Any> Sequence<T>.firstNotNullOfOrNull(transform: (T) -> R?): R? {
      val var2: java.util.Iterator = var0.iterator();

      while (var2.hasNext()) {
         val var3: Any = var1.invoke(var2.next());
         if (var3 != null) {
            return (R)var3;
         }
      }

      return null;
   }

   @JvmStatic
   public fun <T> Sequence<T>.firstOrNull(): T? {
      val var1: java.util.Iterator = var0.iterator();
      return (T)(if (!var1.hasNext()) null else var1.next());
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.firstOrNull(predicate: (T) -> Boolean): T? {
      for (Object var3 : var0) {
         if (var1.invoke(var3) as java.lang.Boolean) {
            return (T)var3;
         }
      }

      return null;
   }

   @JvmStatic
   public fun <T, R> Sequence<T>.flatMap(transform: (T) -> Sequence<R>): Sequence<R> {
      return new FlatteningSequence<>(var0, var1, <unrepresentable>.INSTANCE);
   }

   @JvmStatic
   public fun <T, R> Sequence<T>.flatMapIndexed(transform: (Int, T) -> Iterable<R>): Sequence<R> {
      return SequencesKt.flatMapIndexed(var0, var1, <unrepresentable>.INSTANCE);
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Sequence<T>.flatMapIndexedTo(destination: C, transform: (Int, T) -> Iterable<R>): C {
      val var5: java.util.Iterator = var0.iterator();

      for (int var3 = 0; var5.hasNext(); var3++) {
         val var4: Any = var5.next();
         if (var3 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         CollectionsKt.addAll(var1, var2.invoke(var3, var4) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public fun <T, R> Sequence<T>.flatMapIndexed(transform: (Int, T) -> Sequence<R>): Sequence<R> {
      return SequencesKt.flatMapIndexed(var0, var1, <unrepresentable>.INSTANCE);
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Sequence<T>.flatMapIndexedTo(destination: C, transform: (Int, T) -> Sequence<R>): C {
      val var4: java.util.Iterator = var0.iterator();

      for (int var3 = 0; var4.hasNext(); var3++) {
         val var5: Any = var4.next();
         if (var3 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         CollectionsKt.addAll(var1, var2.invoke(var3, var5) as Sequence);
      }

      return (C)var1;
   }

   @JvmStatic
   public fun <T, R> Sequence<T>.flatMap(transform: (T) -> Iterable<R>): Sequence<R> {
      return new FlatteningSequence<>(var0, var1, <unrepresentable>.INSTANCE);
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Sequence<T>.flatMapTo(destination: C, transform: (T) -> Iterable<R>): C {
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         CollectionsKt.addAll(var1, var2.invoke(var3.next()) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Sequence<T>.flatMapTo(destination: C, transform: (T) -> Sequence<R>): C {
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         CollectionsKt.addAll(var1, var2.invoke(var3.next()) as Sequence);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R> Sequence<T>.fold(initial: R, operation: (R, T) -> R): R {
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         var1 = var2.invoke(var1, var3.next());
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <T, R> Sequence<T>.foldIndexed(initial: R, operation: (Int, R, T) -> R): R {
      val var5: java.util.Iterator = var0.iterator();

      for (int var3 = 0; var5.hasNext(); var3++) {
         val var4: Any = var5.next();
         if (var3 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         var1 = var2.invoke(var3, var1, var4);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.forEach(action: (T) -> Unit) {
      val var2: java.util.Iterator = var0.iterator();

      while (var2.hasNext()) {
         var1.invoke(var2.next());
      }
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.forEachIndexed(action: (Int, T) -> Unit) {
      val var4: java.util.Iterator = var0.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         val var3: Any = var4.next();
         if (var2 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         var1.invoke(var2, var3);
      }
   }

   @JvmStatic
   public inline fun <T, K> Sequence<T>.groupBy(keySelector: (T) -> K): Map<K, List<T>> {
      val var3: java.util.Map = new LinkedHashMap();

      for (Object var6 : var0) {
         val var5: Any = var1.invoke(var6);
         val var2: Any = var3.get(var5);
         var var7: Any = var2;
         if (var2 == null) {
            var7 = new ArrayList();
            var3.put(var5, var7);
         }

         (var7 as java.util.List).add(var6);
      }

      return var3;
   }

   @JvmStatic
   public inline fun <T, K, V> Sequence<T>.groupBy(keySelector: (T) -> K, valueTransform: (T) -> V): Map<K, List<V>> {
      val var4: java.util.Map = new LinkedHashMap();

      for (Object var5 : var0) {
         val var7: Any = var1.invoke(var5);
         val var3: Any = var4.get(var7);
         var var8: Any = var3;
         if (var3 == null) {
            var8 = new ArrayList();
            var4.put(var7, var8);
         }

         (var8 as java.util.List).add(var2.invoke(var5));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T, K, M : MutableMap<in K, MutableList<T>>> Sequence<T>.groupByTo(destination: M, keySelector: (T) -> K): M {
      for (Object var5 : var0) {
         val var4: Any = var2.invoke(var5);
         val var3: Any = var1.get(var4);
         var var7: Any = var3;
         if (var3 == null) {
            var7 = new ArrayList();
            var1.put(var4, var7);
         }

         (var7 as java.util.List).add(var5);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K, V, M : MutableMap<in K, MutableList<V>>> Sequence<T>.groupByTo(destination: M, keySelector: (T) -> K, valueTransform: (T) -> V): M {
      for (Object var7 : var0) {
         val var6: Any = var2.invoke(var7);
         val var4: Any = var1.get(var6);
         var var8: Any = var4;
         if (var4 == null) {
            var8 = new ArrayList();
            var1.put(var6, var8);
         }

         (var8 as java.util.List).add(var3.invoke(var7));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K> Sequence<T>.groupingBy(crossinline keySelector: (T) -> K): Grouping<T, K> {
      return new Grouping<T, K>(var0, var1) {
         final Function1<T, K> $keySelector;
         final Sequence<T> $this_groupingBy;

         {
            this.$this_groupingBy = var1;
            this.$keySelector = var2;
         }

         @Override
         public K keyOf(T var1) {
            return (K)this.$keySelector.invoke(var1);
         }

         @Override
         public java.util.Iterator<T> sourceIterator() {
            return this.$this_groupingBy.iterator();
         }
      };
   }

   @JvmStatic
   public fun <T> Sequence<T>.indexOf(element: T): Int {
      val var4: java.util.Iterator = var0.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         val var3: Any = var4.next();
         if (var2 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         if (var1 == var3) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.indexOfFirst(predicate: (T) -> Boolean): Int {
      val var4: java.util.Iterator = var0.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         val var3: Any = var4.next();
         if (var2 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         if (var1.invoke(var3) as java.lang.Boolean) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.indexOfLast(predicate: (T) -> Boolean): Int {
      val var4: java.util.Iterator = var0.iterator();
      var var3: Int = -1;

      for (int var2 = 0; var4.hasNext(); var2++) {
         val var5: Any = var4.next();
         if (var2 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         if (var1.invoke(var5) as java.lang.Boolean) {
            var3 = var2;
         }
      }

      return var3;
   }

   @JvmStatic
   public fun <T, A : Appendable> Sequence<T>.joinTo(
      buffer: A,
      separator: CharSequence = ...,
      prefix: CharSequence = ...,
      postfix: CharSequence = ...,
      limit: Int = ...,
      truncated: CharSequence = ...,
      transform: ((T) -> CharSequence)? = ...
   ): A {
      var1.append(var3);
      val var10: java.util.Iterator = var0.iterator();
      val var8: Int = 0;

      var var9: Int;
      while (true) {
         var9 = var8;
         if (!var10.hasNext()) {
            break;
         }

         val var11: Any = var10.next();
         if (++var8 > 1) {
            var1.append(var2);
         }

         if (var5 >= 0) {
            var9 = var8;
            if (var8 > var5) {
               break;
            }
         }

         StringsKt.appendElement(var1, var11, var7);
      }

      if (var5 >= 0 && var9 > var5) {
         var1.append(var6);
      }

      var1.append(var4);
      return (A)var1;
   }

   @JvmStatic
   public fun <T> Sequence<T>.joinToString(
      separator: CharSequence = ", " as java.lang.CharSequence,
      prefix: CharSequence = "" as java.lang.CharSequence,
      postfix: CharSequence = "" as java.lang.CharSequence,
      limit: Int = -1,
      truncated: CharSequence = "..." as java.lang.CharSequence,
      transform: ((T) -> CharSequence)? = null
   ): String {
      return SequencesKt.joinTo(var0, new StringBuilder(), var1, var2, var3, var4, var5, var6).toString();
   }

   @JvmStatic
   public fun <T> Sequence<T>.last(): T {
      val var1: java.util.Iterator = var0.iterator();
      if (!var1.hasNext()) {
         throw new NoSuchElementException("Sequence is empty.");
      } else {
         var var2: Any = var1.next();

         while (var1.hasNext()) {
            var2 = var1.next();
         }

         return (T)var2;
      }
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.last(predicate: (T) -> Boolean): T {
      val var4: java.util.Iterator = var0.iterator();
      var var5: Any = null;
      var var2: Boolean = false;

      while (var4.hasNext()) {
         val var3: Any = var4.next();
         if (var1.invoke(var3) as java.lang.Boolean) {
            var2 = true;
            var5 = var3;
         }
      }

      if (var2) {
         return (T)var5;
      } else {
         throw new NoSuchElementException("Sequence contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public fun <T> Sequence<T>.lastIndexOf(element: T): Int {
      val var5: java.util.Iterator = var0.iterator();
      var var3: Int = -1;

      for (int var2 = 0; var5.hasNext(); var2++) {
         val var4: Any = var5.next();
         if (var2 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         if (var1 == var4) {
            var3 = var2;
         }
      }

      return var3;
   }

   @JvmStatic
   public fun <T> Sequence<T>.lastOrNull(): T? {
      val var1: java.util.Iterator = var0.iterator();
      if (!var1.hasNext()) {
         return null;
      } else {
         var var2: Any = var1.next();

         while (var1.hasNext()) {
            var2 = var1.next();
         }

         return (T)var2;
      }
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.lastOrNull(predicate: (T) -> Boolean): T? {
      val var3: java.util.Iterator = var0.iterator();
      var var4: Any = null;

      while (var3.hasNext()) {
         val var2: Any = var3.next();
         if (var1.invoke(var2) as java.lang.Boolean) {
            var4 = var2;
         }
      }

      return (T)var4;
   }

   @JvmStatic
   public fun <T, R> Sequence<T>.map(transform: (T) -> R): Sequence<R> {
      return new TransformingSequence(var0, var1);
   }

   @JvmStatic
   public fun <T, R> Sequence<T>.mapIndexed(transform: (Int, T) -> R): Sequence<R> {
      return new TransformingIndexedSequence(var0, var1);
   }

   @JvmStatic
   public fun <T, R : Any> Sequence<T>.mapIndexedNotNull(transform: (Int, T) -> R?): Sequence<R> {
      return SequencesKt.filterNotNull(new TransformingIndexedSequence(var0, var1));
   }

   @JvmStatic
   public inline fun <T, R : Any, C : MutableCollection<in R>> Sequence<T>.mapIndexedNotNullTo(destination: C, transform: (Int, T) -> R?): C {
      val var5: java.util.Iterator = var0.iterator();

      for (int var3 = 0; var5.hasNext(); var3++) {
         var var4: Any = var5.next();
         if (var3 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         var4 = var2.invoke(var3, var4);
         if (var4 != null) {
            var1.add(var4);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Sequence<T>.mapIndexedTo(destination: C, transform: (Int, T) -> R): C {
      val var4: java.util.Iterator = var0.iterator();

      for (int var3 = 0; var4.hasNext(); var3++) {
         val var5: Any = var4.next();
         if (var3 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         var1.add(var2.invoke(var3, var5));
      }

      return (C)var1;
   }

   @JvmStatic
   public fun <T, R : Any> Sequence<T>.mapNotNull(transform: (T) -> R?): Sequence<R> {
      return SequencesKt.filterNotNull(new TransformingSequence(var0, var1));
   }

   @JvmStatic
   public inline fun <T, R : Any, C : MutableCollection<in R>> Sequence<T>.mapNotNullTo(destination: C, transform: (T) -> R?): C {
      val var4: java.util.Iterator = var0.iterator();

      while (var4.hasNext()) {
         val var3: Any = var2.invoke(var4.next());
         if (var3 != null) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Sequence<T>.mapTo(destination: C, transform: (T) -> R): C {
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         var1.add(var2.invoke(var3.next()));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Sequence<T>.maxByOrNull(selector: (T) -> R): T? {
      val var7: java.util.Iterator = var0.iterator();
      if (!var7.hasNext()) {
         return null;
      } else {
         var var4: Any = var7.next();
         if (!var7.hasNext()) {
            return (T)var4;
         } else {
            var var8: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;

            var var3: Any;
            do {
               val var6: Any = var7.next();
               val var5: java.lang.Comparable = var1.invoke(var6) as java.lang.Comparable;
               var3 = var4;
               var var2: java.lang.Comparable = var8;
               if (var8.compareTo(var5) < 0) {
                  var3 = var6;
                  var2 = var5;
               }

               var4 = var3;
               var8 = var2;
            } while (var7.hasNext());

            return (T)var3;
         }
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Sequence<T>.maxBy(selector: (T) -> R): T {
      val var7: java.util.Iterator = var0.iterator();
      if (!var7.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var4: Any = var7.next();
         if (!var7.hasNext()) {
            return (T)var4;
         } else {
            var var2: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;

            var var3: Any;
            do {
               val var6: Any = var7.next();
               val var5: java.lang.Comparable = var1.invoke(var6) as java.lang.Comparable;
               var3 = var4;
               var var8: java.lang.Comparable = var2;
               if (var2.compareTo(var5) < 0) {
                  var3 = var6;
                  var8 = var5;
               }

               var4 = var3;
               var2 = var8;
            } while (var7.hasNext());

            return (T)var3;
         }
      }
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.maxOf(selector: (T) -> Double): Double {
      val var4: java.util.Iterator = var0.iterator();
      if (!var4.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var2: Double = (var1.invoke(var4.next()) as java.lang.Number).doubleValue();

         while (var4.hasNext()) {
            var2 = Math.max(var2, (var1.invoke(var4.next()) as java.lang.Number).doubleValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.maxOf(selector: (T) -> Float): Float {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var2: Float = (var1.invoke(var3.next()) as java.lang.Number).floatValue();

         while (var3.hasNext()) {
            var2 = Math.max(var2, (var1.invoke(var3.next()) as java.lang.Number).floatValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Sequence<T>.maxOf(selector: (T) -> R): R {
      val var3: java.util.Iterator = var0.iterator();
      if (var3.hasNext()) {
         var var4: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;

         while (var3.hasNext()) {
            val var2: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;
            if (var4.compareTo(var2) < 0) {
               var4 = var2;
            }
         }

         return (R)var4;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Sequence<T>.maxOfOrNull(selector: (T) -> R): R? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;

         while (var3.hasNext()) {
            val var2: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;
            if (var4.compareTo(var2) < 0) {
               var4 = var2;
            }
         }

         return (R)var4;
      }
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.maxOfOrNull(selector: (T) -> Double): Double? {
      val var4: java.util.Iterator = var0.iterator();
      if (!var4.hasNext()) {
         return null;
      } else {
         var var2: Double = (var1.invoke(var4.next()) as java.lang.Number).doubleValue();

         while (var4.hasNext()) {
            var2 = Math.max(var2, (var1.invoke(var4.next()) as java.lang.Number).doubleValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.maxOfOrNull(selector: (T) -> Float): Float? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var2: Float = (var1.invoke(var3.next()) as java.lang.Number).floatValue();

         while (var3.hasNext()) {
            var2 = Math.max(var2, (var1.invoke(var3.next()) as java.lang.Number).floatValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T, R> Sequence<T>.maxOfWith(comparator: Comparator<in R>, selector: (T) -> R): R {
      val var4: java.util.Iterator = var0.iterator();
      if (var4.hasNext()) {
         var var5: Any = var2.invoke(var4.next());

         while (var4.hasNext()) {
            val var3: Any = var2.invoke(var4.next());
            if (var1.compare(var5, var3) < 0) {
               var5 = var3;
            }
         }

         return (R)var5;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <T, R> Sequence<T>.maxOfWithOrNull(comparator: Comparator<in R>, selector: (T) -> R): R? {
      val var4: java.util.Iterator = var0.iterator();
      if (!var4.hasNext()) {
         return null;
      } else {
         var var5: Any = var2.invoke(var4.next());

         while (var4.hasNext()) {
            val var3: Any = var2.invoke(var4.next());
            if (var1.compare(var5, var3) < 0) {
               var5 = var3;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> Sequence<T>.maxOrNull(): T? {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         return null;
      } else {
         var var3: java.lang.Comparable = var2.next() as java.lang.Comparable;

         while (var2.hasNext()) {
            val var1: java.lang.Comparable = var2.next() as java.lang.Comparable;
            if (var3.compareTo(var1) < 0) {
               var3 = var1;
            }
         }

         return (T)var3;
      }
   }

   @JvmStatic
   public fun Sequence<Double>.maxOrNull(): Double? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var1: Double = (var3.next() as java.lang.Number).doubleValue();

         while (var3.hasNext()) {
            var1 = Math.max(var1, (var3.next() as java.lang.Number).doubleValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Sequence<Float>.maxOrNull(): Float? {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         return null;
      } else {
         var var1: Float = (var2.next() as java.lang.Number).floatValue();

         while (var2.hasNext()) {
            var1 = Math.max(var1, (var2.next() as java.lang.Number).floatValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Sequence<Double>.max(): Double {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var1: Double = (var3.next() as java.lang.Number).doubleValue();

         while (var3.hasNext()) {
            var1 = Math.max(var1, (var3.next() as java.lang.Number).doubleValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Sequence<Float>.max(): Float {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var1: Float = (var2.next() as java.lang.Number).floatValue();

         while (var2.hasNext()) {
            var1 = Math.max(var1, (var2.next() as java.lang.Number).floatValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> Sequence<T>.max(): T {
      val var2: java.util.Iterator = var0.iterator();
      if (var2.hasNext()) {
         var var3: java.lang.Comparable = var2.next() as java.lang.Comparable;

         while (var2.hasNext()) {
            val var1: java.lang.Comparable = var2.next() as java.lang.Comparable;
            if (var3.compareTo(var1) < 0) {
               var3 = var1;
            }
         }

         return (T)var3;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public fun <T> Sequence<T>.maxWithOrNull(comparator: Comparator<in T>): T? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var4: Any = var3.next();

         while (var3.hasNext()) {
            val var2: Any = var3.next();
            if (var1.compare(var4, var2) < 0) {
               var4 = var2;
            }
         }

         return (T)var4;
      }
   }

   @JvmStatic
   public fun <T> Sequence<T>.maxWith(comparator: Comparator<in T>): T {
      val var3: java.util.Iterator = var0.iterator();
      if (var3.hasNext()) {
         var var4: Any = var3.next();

         while (var3.hasNext()) {
            val var2: Any = var3.next();
            if (var1.compare(var4, var2) < 0) {
               var4 = var2;
            }
         }

         return (T)var4;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Sequence<T>.minByOrNull(selector: (T) -> R): T? {
      val var7: java.util.Iterator = var0.iterator();
      if (!var7.hasNext()) {
         return null;
      } else {
         var var3: Any = var7.next();
         if (!var7.hasNext()) {
            return (T)var3;
         } else {
            var var2: java.lang.Comparable = var1.invoke(var3) as java.lang.Comparable;

            var var4: Any;
            do {
               val var6: Any = var7.next();
               val var5: java.lang.Comparable = var1.invoke(var6) as java.lang.Comparable;
               var4 = var3;
               var var8: java.lang.Comparable = var2;
               if (var2.compareTo(var5) > 0) {
                  var4 = var6;
                  var8 = var5;
               }

               var3 = var4;
               var2 = var8;
            } while (var7.hasNext());

            return (T)var4;
         }
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Sequence<T>.minBy(selector: (T) -> R): T {
      val var7: java.util.Iterator = var0.iterator();
      if (!var7.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var4: Any = var7.next();
         if (!var7.hasNext()) {
            return (T)var4;
         } else {
            var var2: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;

            var var3: Any;
            do {
               val var6: Any = var7.next();
               val var5: java.lang.Comparable = var1.invoke(var6) as java.lang.Comparable;
               var3 = var4;
               var var8: java.lang.Comparable = var2;
               if (var2.compareTo(var5) > 0) {
                  var3 = var6;
                  var8 = var5;
               }

               var4 = var3;
               var2 = var8;
            } while (var7.hasNext());

            return (T)var3;
         }
      }
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.minOf(selector: (T) -> Double): Double {
      val var4: java.util.Iterator = var0.iterator();
      if (!var4.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var2: Double = (var1.invoke(var4.next()) as java.lang.Number).doubleValue();

         while (var4.hasNext()) {
            var2 = Math.min(var2, (var1.invoke(var4.next()) as java.lang.Number).doubleValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.minOf(selector: (T) -> Float): Float {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var2: Float = (var1.invoke(var3.next()) as java.lang.Number).floatValue();

         while (var3.hasNext()) {
            var2 = Math.min(var2, (var1.invoke(var3.next()) as java.lang.Number).floatValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Sequence<T>.minOf(selector: (T) -> R): R {
      val var3: java.util.Iterator = var0.iterator();
      if (var3.hasNext()) {
         var var4: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;

         while (var3.hasNext()) {
            val var2: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;
            if (var4.compareTo(var2) > 0) {
               var4 = var2;
            }
         }

         return (R)var4;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Sequence<T>.minOfOrNull(selector: (T) -> R): R? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;

         while (var3.hasNext()) {
            val var2: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;
            if (var4.compareTo(var2) > 0) {
               var4 = var2;
            }
         }

         return (R)var4;
      }
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.minOfOrNull(selector: (T) -> Double): Double? {
      val var4: java.util.Iterator = var0.iterator();
      if (!var4.hasNext()) {
         return null;
      } else {
         var var2: Double = (var1.invoke(var4.next()) as java.lang.Number).doubleValue();

         while (var4.hasNext()) {
            var2 = Math.min(var2, (var1.invoke(var4.next()) as java.lang.Number).doubleValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.minOfOrNull(selector: (T) -> Float): Float? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var2: Float = (var1.invoke(var3.next()) as java.lang.Number).floatValue();

         while (var3.hasNext()) {
            var2 = Math.min(var2, (var1.invoke(var3.next()) as java.lang.Number).floatValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T, R> Sequence<T>.minOfWith(comparator: Comparator<in R>, selector: (T) -> R): R {
      val var4: java.util.Iterator = var0.iterator();
      if (var4.hasNext()) {
         var var5: Any = var2.invoke(var4.next());

         while (var4.hasNext()) {
            val var3: Any = var2.invoke(var4.next());
            if (var1.compare(var5, var3) > 0) {
               var5 = var3;
            }
         }

         return (R)var5;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <T, R> Sequence<T>.minOfWithOrNull(comparator: Comparator<in R>, selector: (T) -> R): R? {
      val var4: java.util.Iterator = var0.iterator();
      if (!var4.hasNext()) {
         return null;
      } else {
         var var5: Any = var2.invoke(var4.next());

         while (var4.hasNext()) {
            val var3: Any = var2.invoke(var4.next());
            if (var1.compare(var5, var3) > 0) {
               var5 = var3;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> Sequence<T>.minOrNull(): T? {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         return null;
      } else {
         var var3: java.lang.Comparable = var2.next() as java.lang.Comparable;

         while (var2.hasNext()) {
            val var1: java.lang.Comparable = var2.next() as java.lang.Comparable;
            if (var3.compareTo(var1) > 0) {
               var3 = var1;
            }
         }

         return (T)var3;
      }
   }

   @JvmStatic
   public fun Sequence<Double>.minOrNull(): Double? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var1: Double = (var3.next() as java.lang.Number).doubleValue();

         while (var3.hasNext()) {
            var1 = Math.min(var1, (var3.next() as java.lang.Number).doubleValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Sequence<Float>.minOrNull(): Float? {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         return null;
      } else {
         var var1: Float = (var2.next() as java.lang.Number).floatValue();

         while (var2.hasNext()) {
            var1 = Math.min(var1, (var2.next() as java.lang.Number).floatValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Sequence<Double>.min(): Double {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var1: Double = (var3.next() as java.lang.Number).doubleValue();

         while (var3.hasNext()) {
            var1 = Math.min(var1, (var3.next() as java.lang.Number).doubleValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Sequence<Float>.min(): Float {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var1: Float = (var2.next() as java.lang.Number).floatValue();

         while (var2.hasNext()) {
            var1 = Math.min(var1, (var2.next() as java.lang.Number).floatValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> Sequence<T>.min(): T {
      val var2: java.util.Iterator = var0.iterator();
      if (var2.hasNext()) {
         var var3: java.lang.Comparable = var2.next() as java.lang.Comparable;

         while (var2.hasNext()) {
            val var1: java.lang.Comparable = var2.next() as java.lang.Comparable;
            if (var3.compareTo(var1) > 0) {
               var3 = var1;
            }
         }

         return (T)var3;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public fun <T> Sequence<T>.minWithOrNull(comparator: Comparator<in T>): T? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var4: Any = var3.next();

         while (var3.hasNext()) {
            val var2: Any = var3.next();
            if (var1.compare(var4, var2) > 0) {
               var4 = var2;
            }
         }

         return (T)var4;
      }
   }

   @JvmStatic
   public fun <T> Sequence<T>.minWith(comparator: Comparator<in T>): T {
      val var3: java.util.Iterator = var0.iterator();
      if (var3.hasNext()) {
         var var4: Any = var3.next();

         while (var3.hasNext()) {
            val var2: Any = var3.next();
            if (var1.compare(var4, var2) > 0) {
               var4 = var2;
            }
         }

         return (T)var4;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public operator fun <T> Sequence<T>.minus(elements: Iterable<T>): Sequence<T> {
      return new Sequence<T>(var1, var0) {
         final java.lang.Iterable<T> $elements;
         final Sequence<T> $this_minus;

         {
            this.$elements = var1;
            this.$this_minus = var2;
         }

         private static final boolean iterator$lambda$0(java.util.Collection var0, Object var1) {
            return var0.contains(var1);
         }

         @Override
         public java.util.Iterator<T> iterator() {
            val var1: java.util.Collection = CollectionsKt.convertToListIfNotCollection(this.$elements);
            return if (var1.isEmpty())
               this.$this_minus.iterator()
               else
               SequencesKt.filterNot(this.$this_minus, new SequencesKt___SequencesKt$minus$3$$ExternalSyntheticLambda0(var1)).iterator();
         }
      };
   }

   @JvmStatic
   public operator fun <T> Sequence<T>.minus(element: T): Sequence<T> {
      return new Sequence<T>(var0, var1) {
         final T $element;
         final Sequence<T> $this_minus;

         {
            this.$this_minus = var1;
            this.$element = (T)var2;
         }

         private static final boolean iterator$lambda$0(Ref.BooleanRef var0, Object var1, Object var2) {
            var var3: Boolean = true;
            if (!var0.element) {
               var3 = true;
               if (var2 == var1) {
                  var0.element = true;
                  var3 = false;
               }
            }

            return var3;
         }

         @Override
         public java.util.Iterator<T> iterator() {
            return SequencesKt.filter(this.$this_minus, new SequencesKt___SequencesKt$minus$1$$ExternalSyntheticLambda0(new Ref.BooleanRef(), this.$element))
               .iterator();
         }
      };
   }

   @JvmStatic
   public operator fun <T> Sequence<T>.minus(elements: Sequence<T>): Sequence<T> {
      return new Sequence<T>(var1, var0) {
         final Sequence<T> $elements;
         final Sequence<T> $this_minus;

         {
            this.$elements = var1;
            this.$this_minus = var2;
         }

         private static final boolean iterator$lambda$0(java.util.List var0, Object var1) {
            return var0.contains(var1);
         }

         @Override
         public java.util.Iterator<T> iterator() {
            val var1: java.util.List = SequencesKt.toList(this.$elements);
            return if (var1.isEmpty())
               this.$this_minus.iterator()
               else
               SequencesKt.filterNot(this.$this_minus, new SequencesKt___SequencesKt$minus$4$$ExternalSyntheticLambda0(var1)).iterator();
         }
      };
   }

   @JvmStatic
   public operator fun <T> Sequence<T>.minus(elements: Array<out T>): Sequence<T> {
      return if (var1.length == 0) var0 else new Sequence<T>(var0, var1) {
         final T[] $elements;
         final Sequence<T> $this_minus;

         {
            this.$this_minus = var1;
            this.$elements = (T[])var2;
         }

         private static final boolean iterator$lambda$0(Object[] var0, Object var1) {
            return ArraysKt.contains(var0, var1);
         }

         @Override
         public java.util.Iterator<T> iterator() {
            return SequencesKt.filterNot(this.$this_minus, new SequencesKt___SequencesKt$minus$2$$ExternalSyntheticLambda0(this.$elements)).iterator();
         }
      };
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.minusElement(element: T): Sequence<T> {
      return (Sequence<T>)SequencesKt.minus(var0, var1);
   }

   @JvmStatic
   public fun <T> Sequence<T>.none(): Boolean {
      return var0.iterator().hasNext() xor true;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.none(predicate: (T) -> Boolean): Boolean {
      val var2: java.util.Iterator = var0.iterator();

      while (var2.hasNext()) {
         if (var1.invoke(var2.next()) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public fun <T> Sequence<T>.onEach(action: (T) -> Unit): Sequence<T> {
      return SequencesKt.map(var0, new SequencesKt___SequencesKt$$ExternalSyntheticLambda7(var1));
   }

   @JvmStatic
   fun `onEach$lambda$14$SequencesKt___SequencesKt`(var0: Function1, var1: Any): Any {
      var0.invoke(var1);
      return var1;
   }

   @JvmStatic
   public fun <T> Sequence<T>.onEachIndexed(action: (Int, T) -> Unit): Sequence<T> {
      return SequencesKt.mapIndexed(var0, new SequencesKt___SequencesKt$$ExternalSyntheticLambda8(var1));
   }

   @JvmStatic
   fun `onEachIndexed$lambda$15$SequencesKt___SequencesKt`(var0: Function2, var1: Int, var2: Any): Any {
      var0.invoke(var1, var2);
      return var2;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.partition(predicate: (T) -> Boolean): Pair<List<T>, List<T>> {
      val var2: ArrayList = new ArrayList();
      val var3: ArrayList = new ArrayList();

      for (Object var4 : var0) {
         if (var1.invoke(var4) as java.lang.Boolean) {
            var2.add(var4);
         } else {
            var3.add(var4);
         }
      }

      return new Pair<>(var2, var3);
   }

   @JvmStatic
   public operator fun <T> Sequence<T>.plus(elements: Iterable<T>): Sequence<T> {
      return SequencesKt.flatten(SequencesKt.sequenceOf(new Sequence[]{var0, CollectionsKt.asSequence(var1)}));
   }

   @JvmStatic
   public operator fun <T> Sequence<T>.plus(element: T): Sequence<T> {
      return SequencesKt.flatten(SequencesKt.sequenceOf(new Sequence[]{var0, SequencesKt.sequenceOf(new Object[]{var1})}));
   }

   @JvmStatic
   public operator fun <T> Sequence<T>.plus(elements: Sequence<T>): Sequence<T> {
      return SequencesKt.flatten(SequencesKt.sequenceOf(new Sequence[]{var0, var1}));
   }

   @JvmStatic
   public operator fun <T> Sequence<T>.plus(elements: Array<out T>): Sequence<T> {
      return (Sequence<T>)SequencesKt.plus(var0, ArraysKt.asList(var1));
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.plusElement(element: T): Sequence<T> {
      return (Sequence<T>)SequencesKt.plus(var0, var1);
   }

   @JvmStatic
   public inline fun <S, T : S> Sequence<T>.reduce(operation: (S, T) -> S): S {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         throw new UnsupportedOperationException("Empty sequence can't be reduced.");
      } else {
         var var3: Any = var2.next();

         while (var2.hasNext()) {
            var3 = var1.invoke(var3, var2.next());
         }

         return (S)var3;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Sequence<T>.reduceIndexed(operation: (Int, S, T) -> S): S {
      val var3: java.util.Iterator = var0.iterator();
      if (var3.hasNext()) {
         var var4: Any = var3.next();

         for (int var2 = 1; var3.hasNext(); var2++) {
            if (var2 < 0) {
               if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
                  throw new ArithmeticException("Index overflow has happened.");
               }

               CollectionsKt.throwIndexOverflow();
            }

            var4 = var1.invoke(var2, var4, var3.next());
         }

         return (S)var4;
      } else {
         throw new UnsupportedOperationException("Empty sequence can't be reduced.");
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Sequence<T>.reduceIndexedOrNull(operation: (Int, S, T) -> S): S? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var4: Any = var3.next();

         for (int var2 = 1; var3.hasNext(); var2++) {
            if (var2 < 0) {
               if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
                  throw new ArithmeticException("Index overflow has happened.");
               }

               CollectionsKt.throwIndexOverflow();
            }

            var4 = var1.invoke(var2, var4, var3.next());
         }

         return (S)var4;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Sequence<T>.reduceOrNull(operation: (S, T) -> S): S? {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         return null;
      } else {
         var var3: Any = var2.next();

         while (var2.hasNext()) {
            var3 = var1.invoke(var3, var2.next());
         }

         return (S)var3;
      }
   }

   @JvmStatic
   public fun <T : Any> Sequence<T?>.requireNoNulls(): Sequence<T> {
      return SequencesKt.map(var0, new SequencesKt___SequencesKt$$ExternalSyntheticLambda1(var0));
   }

   @JvmStatic
   fun `requireNoNulls$lambda$16$SequencesKt___SequencesKt`(var0: Sequence, var1: Any): Any {
      if (var1 != null) {
         return var1;
      } else {
         var1 = new StringBuilder("null element found in ");
         var1.append(var0);
         var1.append('.');
         throw new IllegalArgumentException(var1.toString());
      }
   }

   @JvmStatic
   public fun <T, R> Sequence<T>.runningFold(initial: R, operation: (R, T) -> R): Sequence<R> {
      return SequencesKt.sequence((new Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object>(var1, var0, var2, null) {
         final R $initial;
         final Function2<R, T, R> $operation;
         final Sequence<T> $this_runningFold;
         private Object L$0;
         Object L$1;
         Object L$2;
         int label;

         {
            super(2, var4);
            this.$initial = (R)var1;
            this.$this_runningFold = var2x;
            this.$operation = var3x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$initial, this.$this_runningFold, this.$operation, var2);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(SequenceScope<? super R> var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         // $VF: Irreducible bytecode was duplicated to produce valid code
         @Override
         public final Object invokeSuspend(Object var1) {
            var var7: Any;
            var var12: SequenceScope;
            var var14: Any;
            label32: {
               var7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               val var9: SequenceScope;
               if (this.label != 0) {
                  if (this.label != 1) {
                     if (this.label != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     val var5: java.util.Iterator = this.L$2 as java.util.Iterator;
                     var12 = (SequenceScope)this.L$1;
                     var14 = this.L$0 as SequenceScope;
                     ResultKt.throwOnFailure(var1);
                     var1 = var5;
                     var12 = (SequenceScope)var14;
                     var14 = var12;
                     break label32;
                  }

                  var12 = this.L$0 as SequenceScope;
                  ResultKt.throwOnFailure(var1);
                  var9 = var12;
               } else {
                  ResultKt.throwOnFailure(var1);
                  var12 = this.L$0 as SequenceScope;
                  val var16: Any = this.$initial;
                  var14 = this;
                  this.L$0 = var12;
                  this.label = 1;
                  var9 = var12;
                  if (var12.yield(var16, (Continuation<? super Unit>)var14) === var7) {
                     return var7;
                  }
               }

               var14 = this.$initial;
               val var17: java.util.Iterator = this.$this_runningFold.iterator();
               var12 = var9;
               var1 = var17;
            }

            while (var1.hasNext()) {
               val var19: Any = this.$operation.invoke(var14, var1.next());
               val var8: Continuation = this;
               this.L$0 = var12;
               this.L$1 = var19;
               this.L$2 = var1;
               this.label = 2;
               if (var12.yield(var19, var8) === var7) {
                  return var7;
               }

               var1 = var1;
               var12 = var12;
               var14 = var19;
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @JvmStatic
   public fun <T, R> Sequence<T>.runningFoldIndexed(initial: R, operation: (Int, R, T) -> R): Sequence<R> {
      return SequencesKt.sequence((new Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object>(var1, var0, var2, null) {
         final R $initial;
         final Function3<Integer, R, T, R> $operation;
         final Sequence<T> $this_runningFoldIndexed;
         int I$0;
         private Object L$0;
         Object L$1;
         Object L$2;
         int label;

         {
            super(2, var4);
            this.$initial = (R)var1;
            this.$this_runningFoldIndexed = var2x;
            this.$operation = var3x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$initial, this.$this_runningFoldIndexed, this.$operation, var2);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(SequenceScope<? super R> var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            var var5: java.util.Iterator;
            var var7: Any;
            var var11: Int;
            var var12: SequenceScope;
            label34: {
               var7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               if (this.label != 0) {
                  if (this.label != 1) {
                     if (this.label != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     var11 = this.I$0;
                     var5 = this.L$2 as java.util.Iterator;
                     val var6: Any = this.L$1;
                     var12 = this.L$0 as SequenceScope;
                     ResultKt.throwOnFailure(var1);
                     var1 = var6;
                     break label34;
                  }

                  var12 = this.L$0 as SequenceScope;
                  ResultKt.throwOnFailure(var1);
                  var1 = var12;
               } else {
                  ResultKt.throwOnFailure(var1);
                  var12 = this.L$0 as SequenceScope;
                  val var14: Any = this.$initial;
                  val var15: Continuation = this;
                  this.L$0 = var12;
                  this.label = 1;
                  var1 = var12;
                  if (var12.yield(var14, var15) === var7) {
                     return var7;
                  }
               }

               val var16: Any = this.$initial;
               var5 = this.$this_runningFoldIndexed.iterator();
               var11 = 0;
               var12 = var1;
               var1 = var16;
            }

            while (var5.hasNext()) {
               val var8: Any = var5.next();
               val var3x: Int = var11 + 1;
               if (var11 < 0) {
                  CollectionsKt.throwIndexOverflow();
               }

               var1 = this.$operation.invoke(Boxing.boxInt(var11), var1, var8);
               val var18: Continuation = this;
               this.L$0 = var12;
               this.L$1 = var1;
               this.L$2 = var5;
               this.I$0 = var3x;
               this.label = 2;
               if (var12.yield(var1, var18) === var7) {
                  return var7;
               }

               var11 = var3x;
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @JvmStatic
   public fun <S, T : S> Sequence<T>.runningReduce(operation: (S, T) -> S): Sequence<S> {
      return SequencesKt.sequence((new Function2<SequenceScope<? super S>, Continuation<? super Unit>, Object>(var0, var1, null) {
         final Function2<S, T, S> $operation;
         final Sequence<T> $this_runningReduce;
         private Object L$0;
         Object L$1;
         Object L$2;
         int label;

         {
            super(2, var3x);
            this.$this_runningReduce = var1;
            this.$operation = var2x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$this_runningReduce, this.$operation, var2);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(SequenceScope<? super S> var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var3x: SequenceScope;
            val var4: java.util.Iterator;
            if (this.label != 0) {
               if (this.label != 1 && this.label != 2) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               val var5: Any = this.L$2;
               var4 = this.L$1 as java.util.Iterator;
               var3x = this.L$0 as SequenceScope;
               ResultKt.throwOnFailure(var1);
               var1 = var5;
            } else {
               ResultKt.throwOnFailure(var1);
               var3x = this.L$0 as SequenceScope;
               var4 = this.$this_runningReduce.iterator();
               if (!var4.hasNext()) {
                  return Unit.INSTANCE;
               }

               var1 = var4.next();
               val var9: Continuation = this;
               this.L$0 = var3x;
               this.L$1 = var4;
               this.L$2 = var1;
               this.label = 1;
               if (var3x.yield(var1, var9) === var6) {
                  return var6;
               }
            }

            while (var4.hasNext()) {
               val var10: Any = this.$operation.invoke(var1, var4.next());
               val var7: Continuation = this;
               this.L$0 = var3x;
               this.L$1 = var4;
               this.L$2 = var10;
               this.label = 2;
               var1 = var10;
               if (var3x.yield(var10, var7) === var6) {
                  return var6;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @JvmStatic
   public fun <S, T : S> Sequence<T>.runningReduceIndexed(operation: (Int, S, T) -> S): Sequence<S> {
      return SequencesKt.sequence((new Function2<SequenceScope<? super S>, Continuation<? super Unit>, Object>(var0, var1, null) {
         final Function3<Integer, S, T, S> $operation;
         final Sequence<T> $this_runningReduceIndexed;
         int I$0;
         private Object L$0;
         Object L$1;
         Object L$2;
         int label;

         {
            super(2, var3x);
            this.$this_runningReduceIndexed = var1;
            this.$operation = var2x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$this_runningReduceIndexed, this.$operation, var2);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(SequenceScope<? super S> var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            var var2x: Int = 1;
            val var4: java.util.Iterator;
            val var5: SequenceScope;
            if (this.label != 0) {
               if (this.label != 1) {
                  if (this.label != 2) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  var2x = this.I$0;
                  val var6: Any = this.L$2;
                  var4 = this.L$1 as java.util.Iterator;
                  var5 = this.L$0 as SequenceScope;
                  ResultKt.throwOnFailure(var1);
                  var1 = var6;
               } else {
                  val var12: Any = this.L$2;
                  var4 = this.L$1 as java.util.Iterator;
                  var5 = this.L$0 as SequenceScope;
                  ResultKt.throwOnFailure(var1);
                  var1 = var12;
               }
            } else {
               ResultKt.throwOnFailure(var1);
               val var13: SequenceScope = this.L$0 as SequenceScope;
               var4 = this.$this_runningReduceIndexed.iterator();
               if (!var4.hasNext()) {
                  return Unit.INSTANCE;
               }

               val var7: Any = var4.next();
               val var9: Continuation = this;
               this.L$0 = var13;
               this.L$1 = var4;
               this.L$2 = var7;
               this.label = 1;
               var1 = var7;
               var5 = var13;
               if (var13.yield(var7, var9) === var8) {
                  return var8;
               }
            }

            while (var4.hasNext()) {
               val var11: Int = var2x + 1;
               if (var2x < 0) {
                  CollectionsKt.throwIndexOverflow();
               }

               var1 = this.$operation.invoke(Boxing.boxInt(var2x), var1, var4.next());
               val var15: Continuation = this;
               this.L$0 = var5;
               this.L$1 = var4;
               this.L$2 = var1;
               this.I$0 = var11;
               this.label = 2;
               if (var5.yield(var1, var15) === var8) {
                  return var8;
               }

               var2x = var11;
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @JvmStatic
   public fun <T, R> Sequence<T>.scan(initial: R, operation: (R, T) -> R): Sequence<R> {
      return (Sequence<R>)SequencesKt.runningFold(var0, var1, var2);
   }

   @JvmStatic
   public fun <T, R> Sequence<T>.scanIndexed(initial: R, operation: (Int, R, T) -> R): Sequence<R> {
      return (Sequence<R>)SequencesKt.runningFoldIndexed(var0, var1, var2);
   }

   @JvmStatic
   public fun <T> Sequence<T>.single(): T {
      val var1: java.util.Iterator = var0.iterator();
      if (var1.hasNext()) {
         val var2: Any = var1.next();
         if (!var1.hasNext()) {
            return (T)var2;
         } else {
            throw new IllegalArgumentException("Sequence has more than one element.");
         }
      } else {
         throw new NoSuchElementException("Sequence is empty.");
      }
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.single(predicate: (T) -> Boolean): T {
      val var4: java.util.Iterator = var0.iterator();
      var var5: Any = null;
      var var2: Boolean = false;

      while (var4.hasNext()) {
         val var3: Any = var4.next();
         if (var1.invoke(var3) as java.lang.Boolean) {
            if (var2) {
               throw new IllegalArgumentException("Sequence contains more than one matching element.");
            }

            var2 = true;
            var5 = var3;
         }
      }

      if (var2) {
         return (T)var5;
      } else {
         throw new NoSuchElementException("Sequence contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public fun <T> Sequence<T>.singleOrNull(): T? {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         return null;
      } else {
         return (T)(if (var2.hasNext()) null else var2.next());
      }
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.singleOrNull(predicate: (T) -> Boolean): T? {
      val var4: java.util.Iterator = var0.iterator();
      var var2: Boolean = false;
      var var5: Any = null;

      while (var4.hasNext()) {
         val var3: Any = var4.next();
         if (var1.invoke(var3) as java.lang.Boolean) {
            if (var2) {
               return null;
            }

            var2 = true;
            var5 = var3;
         }
      }

      return (T)(if (!var2) null else var5);
   }

   @JvmStatic
   public fun <T : Comparable<T>> Sequence<T>.sorted(): Sequence<T> {
      return new Sequence<T>(var0) {
         final Sequence<T> $this_sorted;

         {
            this.$this_sorted = var1;
         }

         @Override
         public java.util.Iterator<T> iterator() {
            val var1: java.util.List = SequencesKt.toMutableList(this.$this_sorted);
            CollectionsKt.sort(var1);
            return var1.iterator();
         }
      };
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Sequence<T>.sortedBy(crossinline selector: (T) -> R?): Sequence<T> {
      return SequencesKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var1) as java.langComparable<?>, var3.invoke(var2) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Sequence<T>.sortedByDescending(crossinline selector: (T) -> R?): Sequence<T> {
      return SequencesKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var2) as java.langComparable<?>, var3.invoke(var1) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public fun <T : Comparable<T>> Sequence<T>.sortedDescending(): Sequence<T> {
      return SequencesKt.sortedWith(var0, ComparisonsKt.reverseOrder());
   }

   @JvmStatic
   public fun <T> Sequence<T>.sortedWith(comparator: Comparator<in T>): Sequence<T> {
      return new Sequence<T>(var0, var1) {
         final Comparator<? super T> $comparator;
         final Sequence<T> $this_sortedWith;

         {
            this.$this_sortedWith = var1;
            this.$comparator = var2;
         }

         @Override
         public java.util.Iterator<T> iterator() {
            val var1: java.util.List = SequencesKt.toMutableList(this.$this_sortedWith);
            CollectionsKt.sortWith(var1, this.$comparator);
            return var1.iterator();
         }
      };
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun <T> Sequence<T>.sumBy(selector: (T) -> Int): Int {
      val var3: java.util.Iterator = var0.iterator();
      var var2: Int = 0;

      while (var3.hasNext()) {
         var2 += (var1.invoke(var3.next()) as java.lang.Number).intValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun <T> Sequence<T>.sumByDouble(selector: (T) -> Double): Double {
      val var4: java.util.Iterator = var0.iterator();
      var var2: Double = 0.0;

      while (var4.hasNext()) {
         var2 += (var1.invoke(var4.next()) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public fun Sequence<Byte>.sum(): Int {
      val var2: java.util.Iterator = var0.iterator();
      var var1: Int = 0;

      while (var2.hasNext()) {
         var1 += (var2.next() as java.lang.Number).byteValue();
      }

      return var1;
   }

   @JvmStatic
   public fun Sequence<Double>.sum(): Double {
      val var3: java.util.Iterator = var0.iterator();
      var var1: Double = 0.0;

      while (var3.hasNext()) {
         var1 += (var3.next() as java.lang.Number).doubleValue();
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.sumOf(selector: (T) -> Double): Double {
      val var4: java.util.Iterator = var0.iterator();
      var var2: Double = 0.0;

      while (var4.hasNext()) {
         var2 += (var1.invoke(var4.next()) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public fun Sequence<Float>.sum(): Float {
      val var2: java.util.Iterator = var0.iterator();
      var var1: Float = 0.0F;

      while (var2.hasNext()) {
         var1 += (var2.next() as java.lang.Number).floatValue();
      }

      return var1;
   }

   @JvmStatic
   public fun Sequence<Int>.sum(): Int {
      val var2: java.util.Iterator = var0.iterator();
      var var1: Int = 0;

      while (var2.hasNext()) {
         var1 += (var2.next() as java.lang.Number).intValue();
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.sumOf(selector: (T) -> Int): Int {
      val var3: java.util.Iterator = var0.iterator();
      var var2: Int = 0;

      while (var3.hasNext()) {
         var2 += (var1.invoke(var3.next()) as java.lang.Number).intValue();
      }

      return var2;
   }

   @JvmStatic
   public fun Sequence<Long>.sum(): Long {
      val var3: java.util.Iterator = var0.iterator();
      var var1: Long = 0L;

      while (var3.hasNext()) {
         var1 += (var3.next() as java.lang.Number).longValue();
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.sumOf(selector: (T) -> Long): Long {
      val var4: java.util.Iterator = var0.iterator();
      var var2: Long = 0L;

      while (var4.hasNext()) {
         var2 += (var1.invoke(var4.next()) as java.lang.Number).longValue();
      }

      return var2;
   }

   @JvmStatic
   public fun Sequence<Short>.sum(): Int {
      val var2: java.util.Iterator = var0.iterator();
      var var1: Int = 0;

      while (var2.hasNext()) {
         var1 += (var2.next() as java.lang.Number).shortValue();
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.sumOf(selector: (T) -> UInt): UInt {
      var var2: Int = UInt.constructor-impl(0);
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         var2 = UInt.constructor-impl(var2 + (var1.invoke(var3.next()) as UInt).unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.sumOf(selector: (T) -> ULong): ULong {
      var var2: Long = ULong.constructor-impl(0L);
      val var4: java.util.Iterator = var0.iterator();

      while (var4.hasNext()) {
         var2 = ULong.constructor-impl(var2 + (var1.invoke(var4.next()) as ULong).unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public fun <T> Sequence<T>.take(n: Int): Sequence<T> {
      if (var1 >= 0) {
         if (var1 == 0) {
            var0 = SequencesKt.emptySequence();
         } else if (var0 is DropTakeSequence) {
            var0 = (var0 as DropTakeSequence).take(var1);
         } else {
            var0 = new TakeSequence(var0, var1);
         }

         return var0;
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun <T> Sequence<T>.takeWhile(predicate: (T) -> Boolean): Sequence<T> {
      return new TakeWhileSequence(var0, var1);
   }

   @JvmStatic
   public fun <T, C : MutableCollection<in T>> Sequence<T>.toCollection(destination: C): C {
      val var2: java.util.Iterator = var0.iterator();

      while (var2.hasNext()) {
         var1.add(var2.next());
      }

      return (C)var1;
   }

   @JvmStatic
   public fun <T> Sequence<T>.toHashSet(): HashSet<T> {
      return SequencesKt.toCollection(var0, new HashSet()) as HashSet<T>;
   }

   @JvmStatic
   public fun <T> Sequence<T>.toList(): List<T> {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         return CollectionsKt.emptyList();
      } else {
         val var3: Any = var2.next();
         if (!var2.hasNext()) {
            return (java.util.List<T>)CollectionsKt.listOf(var3);
         } else {
            val var1: ArrayList = new ArrayList();
            var1.add(var3);

            while (var2.hasNext()) {
               var1.add(var2.next());
            }

            return var1;
         }
      }
   }

   @JvmStatic
   public fun <T> Sequence<T>.toMutableList(): MutableList<T> {
      return SequencesKt.toCollection(var0, new ArrayList()) as MutableList<T>;
   }

   @JvmStatic
   public fun <T> Sequence<T>.toMutableSet(): MutableSet<T> {
      val var1: LinkedHashSet = new LinkedHashSet();
      val var2: java.util.Iterator = var0.iterator();

      while (var2.hasNext()) {
         var1.add(var2.next());
      }

      return var1;
   }

   @JvmStatic
   public fun <T> Sequence<T>.toSet(): Set<T> {
      val var1: java.util.Iterator = var0.iterator();
      if (!var1.hasNext()) {
         return SetsKt.emptySet();
      } else {
         val var2: Any = var1.next();
         if (!var1.hasNext()) {
            return (java.util.Set<T>)SetsKt.setOf(var2);
         } else {
            val var3: LinkedHashSet = new LinkedHashSet();
            var3.add(var2);

            while (var1.hasNext()) {
               var3.add(var1.next());
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public fun <T> Sequence<T>.windowed(size: Int, step: Int = 1, partialWindows: Boolean = false): Sequence<List<T>> {
      return SlidingWindowKt.windowedSequence(var0, var1, var2, var3, false);
   }

   @JvmStatic
   public fun <T, R> Sequence<T>.windowed(size: Int, step: Int = 1, partialWindows: Boolean = false, transform: (List<T>) -> R): Sequence<R> {
      return SequencesKt.map(SlidingWindowKt.windowedSequence(var0, var1, var2, var3, true), var4);
   }

   @JvmStatic
   public fun <T> Sequence<T>.withIndex(): Sequence<IndexedValue<T>> {
      return new IndexingSequence(var0);
   }

   @JvmStatic
   public infix fun <T, R> Sequence<T>.zip(other: Sequence<R>): Sequence<Pair<T, R>> {
      return new MergingSequence<>(var0, var1, new SequencesKt___SequencesKt$$ExternalSyntheticLambda0());
   }

   @JvmStatic
   public fun <T, R, V> Sequence<T>.zip(other: Sequence<R>, transform: (T, R) -> V): Sequence<V> {
      return new MergingSequence(var0, var1, var2);
   }

   @JvmStatic
   fun `zip$lambda$17$SequencesKt___SequencesKt`(var0: Any, var1: Any): Pair {
      return TuplesKt.to(var0, var1);
   }

   @JvmStatic
   public fun <T> Sequence<T>.zipWithNext(): Sequence<Pair<T, T>> {
      return SequencesKt.zipWithNext(var0, new SequencesKt___SequencesKt$$ExternalSyntheticLambda4());
   }

   @JvmStatic
   public fun <T, R> Sequence<T>.zipWithNext(transform: (T, T) -> R): Sequence<R> {
      return SequencesKt.sequence((new Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object>(var0, var1, null) {
         final Sequence<T> $this_zipWithNext;
         final Function2<T, T, R> $transform;
         private Object L$0;
         Object L$1;
         Object L$2;
         int label;

         {
            super(2, var3x);
            this.$this_zipWithNext = var1;
            this.$transform = var2x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$this_zipWithNext, this.$transform, var2);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(SequenceScope<? super R> var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         // $VF: Irreducible bytecode was duplicated to produce valid code
         @Override
         public final Object invokeSuspend(Object var1) {
            val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var5: java.util.Iterator;
            var var10: SequenceScope;
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var10 = (SequenceScope)this.L$2;
               var5 = this.L$1 as java.util.Iterator;
               val var4: SequenceScope = this.L$0 as SequenceScope;
               ResultKt.throwOnFailure(var1);
               var1 = var10;
               var10 = var4;
            } else {
               ResultKt.throwOnFailure(var1);
               var10 = this.L$0 as SequenceScope;
               var5 = this.$this_zipWithNext.iterator();
               if (!var5.hasNext()) {
                  return Unit.INSTANCE;
               }

               var1 = var5.next();
            }

            while (var5.hasNext()) {
               val var11: Any = var5.next();
               val var7: Any = this.$transform.invoke(var1, var11);
               val var8: Continuation = this;
               this.L$0 = var10;
               this.L$1 = var5;
               this.L$2 = var11;
               this.label = 1;
               var1 = var11;
               if (var10.yield(var7, var8) === var6) {
                  return var6;
               }

               var10 = var10;
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @JvmStatic
   fun `zipWithNext$lambda$18$SequencesKt___SequencesKt`(var0: Any, var1: Any): Pair {
      return TuplesKt.to(var0, var1);
   }
}
