package kotlin.text

import java.util.ArrayList
import java.util.Comparator
import java.util.HashSet
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.NoSuchElementException
import kotlin.contracts.InvocationKind
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Intrinsics
import kotlin.random.Random

internal class StringsKt___StringsKt : StringsKt___StringsJvmKt {
   open fun StringsKt___StringsKt() {
   }

   @JvmStatic
   public inline fun CharSequence.all(predicate: (Char) -> Boolean): Boolean {
      for (int var2 = 0; var2 < var0.length(); var2++) {
         if (!var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public fun CharSequence.any(): Boolean {
      val var1: Boolean;
      if (var0.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun CharSequence.any(predicate: (Char) -> Boolean): Boolean {
      for (int var2 = 0; var2 < var0.length(); var2++) {
         if (var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public fun CharSequence.asIterable(): Iterable<Char> {
      return (java.lang.Iterable<Character>)(if (var0 is java.lang.String && var0.length() == 0)
         CollectionsKt.emptyList()
         else
         new java.lang.Iterable<Character>(var0) {
            final java.lang.CharSequence $this_asIterable$inlined;

            {
               this.$this_asIterable$inlined = var1;
            }

            @Override
            public java.util.Iterator<Character> iterator() {
               return StringsKt.iterator(this.$this_asIterable$inlined);
            }
         });
   }

   @JvmStatic
   public fun CharSequence.asSequence(): Sequence<Char> {
      return if (var0 is java.lang.String && var0.length() == 0) SequencesKt.emptySequence() else new Sequence<Character>(var0) {
         final java.lang.CharSequence $this_asSequence$inlined;

         {
            this.$this_asSequence$inlined = var1;
         }

         @Override
         public java.util.Iterator<Character> iterator() {
            return StringsKt.iterator(this.$this_asSequence$inlined);
         }
      };
   }

   @JvmStatic
   public inline fun <K, V> CharSequence.associate(transform: (Char) -> Pair<K, V>): Map<K, V> {
      val var3: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length()), 16));

      for (int var2 = 0; var2 < var0.length(); var2++) {
         val var4: Pair = var1.invoke(var0.charAt(var2)) as Pair;
         var3.put(var4.getFirst(), var4.getSecond());
      }

      return var3;
   }

   @JvmStatic
   public inline fun <K> CharSequence.associateBy(keySelector: (Char) -> K): Map<K, Char> {
      val var4: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length()), 16));

      for (int var3 = 0; var3 < var0.length(); var3++) {
         val var2: Char = var0.charAt(var3);
         var4.put(var1.invoke(var2), var2);
      }

      return var4;
   }

   @JvmStatic
   public inline fun <K, V> CharSequence.associateBy(keySelector: (Char) -> K, valueTransform: (Char) -> V): Map<K, V> {
      val var5: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length()), 16));

      for (int var4 = 0; var4 < var0.length(); var4++) {
         val var3: Char = var0.charAt(var4);
         var5.put(var1.invoke(var3), var2.invoke(var3));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, in Char>> CharSequence.associateByTo(destination: M, keySelector: (Char) -> K): M {
      for (int var4 = 0; var4 < var0.length(); var4++) {
         val var3: Char = var0.charAt(var4);
         var1.put(var2.invoke(var3), var3);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> CharSequence.associateByTo(destination: M, keySelector: (Char) -> K, valueTransform: (Char) -> V): M {
      for (int var5 = 0; var5 < var0.length(); var5++) {
         val var4: Char = var0.charAt(var5);
         var1.put(var2.invoke(var4), var3.invoke(var4));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> CharSequence.associateTo(destination: M, transform: (Char) -> Pair<K, V>): M {
      for (int var3 = 0; var3 < var0.length(); var3++) {
         val var4: Pair = var2.invoke(var0.charAt(var3)) as Pair;
         var1.put(var4.getFirst(), var4.getSecond());
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <V> CharSequence.associateWith(valueSelector: (Char) -> V): Map<Char, V> {
      val var4: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(RangesKt.coerceAtMost(var0.length(), 128)), 16));

      for (int var3 = 0; var3 < var0.length(); var3++) {
         val var2: Char = var0.charAt(var3);
         var4.put(var2, var1.invoke(var2));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <V, M : MutableMap<in Char, in V>> CharSequence.associateWithTo(destination: M, valueSelector: (Char) -> V): M {
      for (int var4 = 0; var4 < var0.length(); var4++) {
         val var3: Char = var0.charAt(var4);
         var1.put(var3, var2.invoke(var3));
      }

      return (M)var1;
   }

   @JvmStatic
   public fun CharSequence.chunked(size: Int): List<String> {
      return StringsKt.windowed(var0, var1, var1, true);
   }

   @JvmStatic
   public fun <R> CharSequence.chunked(size: Int, transform: (CharSequence) -> R): List<R> {
      return StringsKt.windowed(var0, var1, var1, true, var2);
   }

   @JvmStatic
   public fun CharSequence.chunkedSequence(size: Int): Sequence<String> {
      return StringsKt.chunkedSequence(var0, var1, new StringsKt___StringsKt$$ExternalSyntheticLambda2());
   }

   @JvmStatic
   public fun <R> CharSequence.chunkedSequence(size: Int, transform: (CharSequence) -> R): Sequence<R> {
      return StringsKt.windowedSequence(var0, var1, var1, true, var2);
   }

   @JvmStatic
   fun `chunkedSequence$lambda$22$StringsKt___StringsKt`(var0: java.lang.CharSequence): java.lang.String {
      return var0.toString();
   }

   @JvmStatic
   public inline fun CharSequence.count(): Int {
      return var0.length();
   }

   @JvmStatic
   public inline fun CharSequence.count(predicate: (Char) -> Boolean): Int {
      var var2: Int = 0;
      var var3: Int = 0;

      while (var2 < var0.length()) {
         var var4: Int = var3;
         if (var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
            var4 = var3 + 1;
         }

         var2++;
         var3 = var4;
      }

      return var3;
   }

   @JvmStatic
   public fun CharSequence.drop(n: Int): CharSequence {
      if (var1 >= 0) {
         return var0.subSequence(RangesKt.coerceAtMost(var1, var0.length()), var0.length());
      } else {
         val var2: StringBuilder = new StringBuilder("Requested character count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun String.drop(n: Int): String {
      if (var1 >= 0) {
         var0 = var0.substring(RangesKt.coerceAtMost(var1, var0.length()));
         return var0;
      } else {
         val var2: StringBuilder = new StringBuilder("Requested character count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun CharSequence.dropLast(n: Int): CharSequence {
      if (var1 >= 0) {
         return StringsKt.take(var0, RangesKt.coerceAtLeast(var0.length() - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested character count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun String.dropLast(n: Int): String {
      if (var1 >= 0) {
         return StringsKt.take(var0, RangesKt.coerceAtLeast(var0.length() - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested character count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public inline fun CharSequence.dropLastWhile(predicate: (Char) -> Boolean): CharSequence {
      for (int var2 = StringsKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
            return var0.subSequence(0, var2 + 1);
         }
      }

      return "";
   }

   @JvmStatic
   public inline fun String.dropLastWhile(predicate: (Char) -> Boolean): String {
      for (int var2 = StringsKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
            var0 = var0.substring(0, var2 + 1);
            return var0;
         }
      }

      return "";
   }

   @JvmStatic
   public inline fun CharSequence.dropWhile(predicate: (Char) -> Boolean): CharSequence {
      val var3: Int = var0.length();

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
            return var0.subSequence(var2, var0.length());
         }
      }

      return "";
   }

   @JvmStatic
   public inline fun String.dropWhile(predicate: (Char) -> Boolean): String {
      val var3: Int = var0.length();

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
            var0 = var0.substring(var2);
            return var0;
         }
      }

      return "";
   }

   @JvmStatic
   public inline fun CharSequence.elementAtOrElse(index: Int, defaultValue: (Int) -> Char): Char {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Char;
      if (var1 >= 0 && var1 < var0.length()) {
         var3 = var0.charAt(var1);
      } else {
         var3 = var2.invoke(var1) as Character;
      }

      return var3;
   }

   @JvmStatic
   public inline fun CharSequence.elementAtOrNull(index: Int): Char? {
      return StringsKt.getOrNull(var0, var1);
   }

   @JvmStatic
   public inline fun CharSequence.filter(predicate: (Char) -> Boolean): CharSequence {
      val var5: Appendable = new StringBuilder();
      val var4: Int = var0.length();

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Char = var0.charAt(var3);
         if (var1.invoke(var2) as java.lang.Boolean) {
            var5.append(var2);
         }
      }

      return var5 as java.lang.CharSequence;
   }

   @JvmStatic
   public inline fun String.filter(predicate: (Char) -> Boolean): String {
      val var5: java.lang.CharSequence = var0;
      val var6: Appendable = new StringBuilder();
      val var4: Int = var5.length();

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Char = var5.charAt(var3);
         if (var1.invoke(var2) as java.lang.Boolean) {
            var6.append(var2);
         }
      }

      return (var6 as StringBuilder).toString();
   }

   @JvmStatic
   public inline fun CharSequence.filterIndexed(predicate: (Int, Char) -> Boolean): CharSequence {
      val var5: Appendable = new StringBuilder();
      var var4: Int = 0;

      for (int var3 = 0; var4 < var0.length(); var3++) {
         val var2: Char = var0.charAt(var4);
         if (var1.invoke(var3, var2) as java.lang.Boolean) {
            var5.append(var2);
         }

         var4++;
      }

      return var5 as java.lang.CharSequence;
   }

   @JvmStatic
   public inline fun String.filterIndexed(predicate: (Int, Char) -> Boolean): String {
      val var6: java.lang.CharSequence = var0;
      val var5: Appendable = new StringBuilder();
      var var4: Int = 0;

      for (int var3 = 0; var4 < var6.length(); var3++) {
         val var2: Char = var6.charAt(var4);
         if (var1.invoke(var3, var2) as java.lang.Boolean) {
            var5.append(var2);
         }

         var4++;
      }

      return (var5 as StringBuilder).toString();
   }

   @JvmStatic
   public inline fun <C : Appendable> CharSequence.filterIndexedTo(destination: C, predicate: (Int, Char) -> Boolean): C {
      var var5: Int = 0;

      for (int var4 = 0; var5 < var0.length(); var4++) {
         val var3: Char = var0.charAt(var5);
         if (var2.invoke(var4, var3) as java.lang.Boolean) {
            var1.append(var3);
         }

         var5++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun CharSequence.filterNot(predicate: (Char) -> Boolean): CharSequence {
      val var4: Appendable = new StringBuilder();

      for (int var3 = 0; var3 < var0.length(); var3++) {
         val var2: Char = var0.charAt(var3);
         if (!var1.invoke(var2) as java.lang.Boolean) {
            var4.append(var2);
         }
      }

      return var4 as java.lang.CharSequence;
   }

   @JvmStatic
   public inline fun String.filterNot(predicate: (Char) -> Boolean): String {
      val var5: java.lang.CharSequence = var0;
      val var4: Appendable = new StringBuilder();

      for (int var3 = 0; var3 < var5.length(); var3++) {
         val var2: Char = var5.charAt(var3);
         if (!var1.invoke(var2) as java.lang.Boolean) {
            var4.append(var2);
         }
      }

      return (var4 as StringBuilder).toString();
   }

   @JvmStatic
   public inline fun <C : Appendable> CharSequence.filterNotTo(destination: C, predicate: (Char) -> Boolean): C {
      for (int var4 = 0; var4 < var0.length(); var4++) {
         val var3: Char = var0.charAt(var4);
         if (!var2.invoke(var3) as java.lang.Boolean) {
            var1.append(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : Appendable> CharSequence.filterTo(destination: C, predicate: (Char) -> Boolean): C {
      val var5: Int = var0.length();

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Char = var0.charAt(var4);
         if (var2.invoke(var3) as java.lang.Boolean) {
            var1.append(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun CharSequence.find(predicate: (Char) -> Boolean): Char? {
      var var3: Int = 0;

      while (true) {
         if (var3 >= var0.length()) {
            var4 = null;
            break;
         }

         val var2: Char = var0.charAt(var3);
         if (var1.invoke(var2) as java.lang.Boolean) {
            var4 = var2;
            break;
         }

         var3++;
      }

      return var4;
   }

   @JvmStatic
   public inline fun CharSequence.findLast(predicate: (Char) -> Boolean): Char? {
      var var3: Int = var0.length() - 1;
      if (var3 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Char = var0.charAt(var3);
            if (var1.invoke(var2) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      return null;
   }

   @JvmStatic
   public fun CharSequence.first(): Char {
      if (var0.length() != 0) {
         return var0.charAt(0);
      } else {
         throw new NoSuchElementException("Char sequence is empty.");
      }
   }

   @JvmStatic
   public inline fun CharSequence.first(predicate: (Char) -> Boolean): Char {
      for (int var3 = 0; var3 < var0.length(); var3++) {
         val var2: Char = var0.charAt(var3);
         if (var1.invoke(var2) as java.lang.Boolean) {
            return var2;
         }
      }

      throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
   }

   @JvmStatic
   public inline fun <R : Any> CharSequence.firstNotNullOf(transform: (Char) -> R?): R {
      var var2: Int = 0;

      var var3: Any;
      while (true) {
         if (var2 >= var0.length()) {
            var3 = null;
            break;
         }

         val var4: Any = var1.invoke(var0.charAt(var2));
         var3 = var4;
         if (var4 != null) {
            break;
         }

         var2++;
      }

      if (var3 != null) {
         return (R)var3;
      } else {
         throw new NoSuchElementException("No element of the char sequence was transformed to a non-null value.");
      }
   }

   @JvmStatic
   public inline fun <R : Any> CharSequence.firstNotNullOfOrNull(transform: (Char) -> R?): R? {
      for (int var2 = 0; var2 < var0.length(); var2++) {
         val var3: Any = var1.invoke(var0.charAt(var2));
         if (var3 != null) {
            return (R)var3;
         }
      }

      return null;
   }

   @JvmStatic
   public fun CharSequence.firstOrNull(): Char? {
      val var1: Character;
      if (var0.length() == 0) {
         var1 = null;
      } else {
         var1 = var0.charAt(0);
      }

      return var1;
   }

   @JvmStatic
   public inline fun CharSequence.firstOrNull(predicate: (Char) -> Boolean): Char? {
      for (int var3 = 0; var3 < var0.length(); var3++) {
         val var2: Char = var0.charAt(var3);
         if (var1.invoke(var2) as java.lang.Boolean) {
            return var2;
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun <R> CharSequence.flatMap(transform: (Char) -> Iterable<R>): List<R> {
      val var3: java.util.Collection = new ArrayList();

      for (int var2 = 0; var2 < var0.length(); var2++) {
         CollectionsKt.addAll(var3, var1.invoke(var0.charAt(var2)) as java.lang.Iterable);
      }

      return var3 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> CharSequence.flatMapIndexed(transform: (Int, Char) -> Iterable<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      var var3: Int = 0;

      for (int var2 = 0; var3 < var0.length(); var2++) {
         CollectionsKt.addAll(var4, var1.invoke(var2, var0.charAt(var3)) as java.lang.Iterable);
         var3++;
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> CharSequence.flatMapIndexedTo(destination: C, transform: (Int, Char) -> Iterable<R>): C {
      var var4: Int = 0;

      for (int var3 = 0; var4 < var0.length(); var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, var0.charAt(var4)) as java.lang.Iterable);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> CharSequence.flatMapTo(destination: C, transform: (Char) -> Iterable<R>): C {
      for (int var3 = 0; var3 < var0.length(); var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var0.charAt(var3)) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R> CharSequence.fold(initial: R, operation: (R, Char) -> R): R {
      for (int var3 = 0; var3 < var0.length(); var3++) {
         var1 = var2.invoke(var1, var0.charAt(var3));
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> CharSequence.foldIndexed(initial: R, operation: (Int, R, Char) -> R): R {
      var var4: Int = 0;

      for (int var3 = 0; var4 < var0.length(); var3++) {
         var1 = var2.invoke(var3, var1, var0.charAt(var4));
         var4++;
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> CharSequence.foldRight(initial: R, operation: (Char, R) -> R): R {
      for (int var3 = StringsKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var0.charAt(var3), var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> CharSequence.foldRightIndexed(initial: R, operation: (Int, Char, R) -> R): R {
      for (int var3 = StringsKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var3, var0.charAt(var3), var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun CharSequence.forEach(action: (Char) -> Unit) {
      for (int var2 = 0; var2 < var0.length(); var2++) {
         var1.invoke(var0.charAt(var2));
      }
   }

   @JvmStatic
   public inline fun CharSequence.forEachIndexed(action: (Int, Char) -> Unit) {
      var var3: Int = 0;

      for (int var2 = 0; var3 < var0.length(); var2++) {
         var1.invoke(var2, var0.charAt(var3));
         var3++;
      }
   }

   @JvmStatic
   public inline fun CharSequence.getOrElse(index: Int, defaultValue: (Int) -> Char): Char {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Char;
      if (var1 >= 0 && var1 < var0.length()) {
         var3 = var0.charAt(var1);
      } else {
         var3 = var2.invoke(var1) as Character;
      }

      return var3;
   }

   @JvmStatic
   public fun CharSequence.getOrNull(index: Int): Char? {
      val var2: Character;
      if (var1 >= 0 && var1 < var0.length()) {
         var2 = var0.charAt(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public inline fun <K> CharSequence.groupBy(keySelector: (Char) -> K): Map<K, List<Char>> {
      val var7: java.util.Map = new LinkedHashMap();

      for (int var3 = 0; var3 < var0.length(); var3++) {
         val var2: Char = var0.charAt(var3);
         val var6: Any = var1.invoke(var2);
         val var5: Any = var7.get(var6);
         var var4: Any = var5;
         if (var5 == null) {
            var4 = new ArrayList();
            var7.put(var6, var4);
         }

         (var4 as java.util.List).add(var2);
      }

      return var7;
   }

   @JvmStatic
   public inline fun <K, V> CharSequence.groupBy(keySelector: (Char) -> K, valueTransform: (Char) -> V): Map<K, List<V>> {
      val var7: java.util.Map = new LinkedHashMap();

      for (int var4 = 0; var4 < var0.length(); var4++) {
         val var3: Char = var0.charAt(var4);
         val var8: Any = var1.invoke(var3);
         val var6: Any = var7.get(var8);
         var var5: Any = var6;
         if (var6 == null) {
            var5 = new ArrayList();
            var7.put(var8, var5);
         }

         (var5 as java.util.List).add(var2.invoke(var3));
      }

      return var7;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, MutableList<Char>>> CharSequence.groupByTo(destination: M, keySelector: (Char) -> K): M {
      for (int var4 = 0; var4 < var0.length(); var4++) {
         val var3: Char = var0.charAt(var4);
         val var7: Any = var2.invoke(var3);
         val var6: Any = var1.get(var7);
         var var5: Any = var6;
         if (var6 == null) {
            var5 = new ArrayList();
            var1.put(var7, var5);
         }

         (var5 as java.util.List).add(var3);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, MutableList<V>>> CharSequence.groupByTo(destination: M, keySelector: (Char) -> K, valueTransform: (Char) -> V): M {
      for (int var5 = 0; var5 < var0.length(); var5++) {
         val var4: Char = var0.charAt(var5);
         val var8: Any = var2.invoke(var4);
         val var7: Any = var1.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var1.put(var8, var6);
         }

         (var6 as java.util.List).add(var3.invoke(var4));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K> CharSequence.groupingBy(crossinline keySelector: (Char) -> K): Grouping<Char, K> {
      return new Grouping<Character, K>(var0, var1) {
         final Function1<Character, K> $keySelector;
         final java.lang.CharSequence $this_groupingBy;

         {
            this.$this_groupingBy = var1;
            this.$keySelector = var2;
         }

         public K keyOf(char var1) {
            return (K)this.$keySelector.invoke(var1);
         }

         @Override
         public java.util.Iterator<Character> sourceIterator() {
            return StringsKt.iterator(this.$this_groupingBy);
         }
      };
   }

   @JvmStatic
   public inline fun CharSequence.indexOfFirst(predicate: (Char) -> Boolean): Int {
      val var3: Int = var0.length();

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun CharSequence.indexOfLast(predicate: (Char) -> Boolean): Int {
      var var2: Int = var0.length() - 1;
      if (var2 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public fun CharSequence.last(): Char {
      if (var0.length() != 0) {
         return var0.charAt(StringsKt.getLastIndex(var0));
      } else {
         throw new NoSuchElementException("Char sequence is empty.");
      }
   }

   @JvmStatic
   public inline fun CharSequence.last(predicate: (Char) -> Boolean): Char {
      var var3: Int = var0.length() - 1;
      if (var3 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Char = var0.charAt(var3);
            if (var1.invoke(var2) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
   }

   @JvmStatic
   public fun CharSequence.lastOrNull(): Char? {
      val var1: Character;
      if (var0.length() == 0) {
         var1 = null;
      } else {
         var1 = var0.charAt(var0.length() - 1);
      }

      return var1;
   }

   @JvmStatic
   public inline fun CharSequence.lastOrNull(predicate: (Char) -> Boolean): Char? {
      var var3: Int = var0.length() - 1;
      if (var3 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Char = var0.charAt(var3);
            if (var1.invoke(var2) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun <R> CharSequence.map(transform: (Char) -> R): List<R> {
      val var3: java.util.Collection = new ArrayList(var0.length());

      for (int var2 = 0; var2 < var0.length(); var2++) {
         var3.add(var1.invoke(var0.charAt(var2)));
      }

      return var3 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> CharSequence.mapIndexed(transform: (Int, Char) -> R): List<R> {
      val var4: java.util.Collection = new ArrayList(var0.length());
      var var3: Int = 0;

      for (int var2 = 0; var3 < var0.length(); var2++) {
         var4.add(var1.invoke(var2, var0.charAt(var3)));
         var3++;
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R : Any> CharSequence.mapIndexedNotNull(transform: (Int, Char) -> R?): List<R> {
      val var5: java.util.Collection = new ArrayList();
      var var3: Int = 0;

      for (int var2 = 0; var3 < var0.length(); var2++) {
         val var4: Any = var1.invoke(var2, var0.charAt(var3));
         if (var4 != null) {
            var5.add(var4);
         }

         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R : Any, C : MutableCollection<in R>> CharSequence.mapIndexedNotNullTo(destination: C, transform: (Int, Char) -> R?): C {
      var var4: Int = 0;

      for (int var3 = 0; var4 < var0.length(); var3++) {
         val var5: Any = var2.invoke(var3, var0.charAt(var4));
         if (var5 != null) {
            var1.add(var5);
         }

         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> CharSequence.mapIndexedTo(destination: C, transform: (Int, Char) -> R): C {
      var var4: Int = 0;

      for (int var3 = 0; var4 < var0.length(); var3++) {
         var1.add(var2.invoke(var3, var0.charAt(var4)));
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R : Any> CharSequence.mapNotNull(transform: (Char) -> R?): List<R> {
      val var3: java.util.Collection = new ArrayList();

      for (int var2 = 0; var2 < var0.length(); var2++) {
         val var4: Any = var1.invoke(var0.charAt(var2));
         if (var4 != null) {
            var3.add(var4);
         }
      }

      return var3 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R : Any, C : MutableCollection<in R>> CharSequence.mapNotNullTo(destination: C, transform: (Char) -> R?): C {
      for (int var3 = 0; var3 < var0.length(); var3++) {
         val var4: Any = var2.invoke(var0.charAt(var3));
         if (var4 != null) {
            var1.add(var4);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> CharSequence.mapTo(destination: C, transform: (Char) -> R): C {
      for (int var3 = 0; var3 < var0.length(); var3++) {
         var1.add(var2.invoke(var0.charAt(var3)));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharSequence.maxByOrNull(selector: (Char) -> R): Char? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var2: Char = var0.charAt(0);
         val var5: Int = StringsKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Char = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0.charAt(var4);
                  val var8: java.lang.Comparable = var1.invoke(var3) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) < 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharSequence.maxBy(selector: (Char) -> R): Char {
      if (var0.length() != 0) {
         var var2: Char = var0.charAt(0);
         val var5: Int = StringsKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Char = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0.charAt(var4);
                  val var8: java.lang.Comparable = var1.invoke(var3) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) < 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun CharSequence.maxOf(selector: (Char) -> Double): Double {
      if (var0.length() == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0.charAt(0)) as java.lang.Number).doubleValue();
         val var7: Int = StringsKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0.charAt(var6)) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharSequence.maxOf(selector: (Char) -> Float): Float {
      if (var0.length() == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0.charAt(0)) as java.lang.Number).floatValue();
         val var5: Int = StringsKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0.charAt(var4)) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharSequence.maxOf(selector: (Char) -> R): R {
      if (var0.length() == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0.charAt(0)) as java.lang.Comparable;
         val var3: Int = StringsKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0.charAt(var2)) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharSequence.maxOfOrNull(selector: (Char) -> R): R? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0.charAt(0)) as java.lang.Comparable;
         val var3: Int = StringsKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0.charAt(var2)) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun CharSequence.maxOfOrNull(selector: (Char) -> Double): Double? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0.charAt(0)) as java.lang.Number).doubleValue();
         val var7: Int = StringsKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0.charAt(var6)) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharSequence.maxOfOrNull(selector: (Char) -> Float): Float? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0.charAt(0)) as java.lang.Number).floatValue();
         val var5: Int = StringsKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0.charAt(var4)) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <R> CharSequence.maxOfWith(comparator: Comparator<in R>, selector: (Char) -> R): R {
      if (var0.length() == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0.charAt(0));
         val var4: Int = StringsKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0.charAt(var3));
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> CharSequence.maxOfWithOrNull(comparator: Comparator<in R>, selector: (Char) -> R): R? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0.charAt(0));
         val var4: Int = StringsKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0.charAt(var3));
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public fun CharSequence.maxOrNull(): Char? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var1: Char = var0.charAt(0);
         val var5: Int = StringsKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Char = var0.charAt(var4);
               var1 = var2;
               if (Intrinsics.compare((int)var2, (int)var3) < 0) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun CharSequence.max(): Char {
      if (var0.length() == 0) {
         throw new NoSuchElementException();
      } else {
         var var1: Char = var0.charAt(0);
         val var5: Int = StringsKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Char = var0.charAt(var4);
               var1 = var2;
               if (Intrinsics.compare((int)var2, (int)var3) < 0) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun CharSequence.maxWithOrNull(comparator: Comparator<in Char>): Char? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var2: Char = var0.charAt(0);
         val var6: Int = StringsKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Char = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Char = var0.charAt(var5);
               var2 = var3;
               if (var1.compare(var3, var4) < 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun CharSequence.maxWith(comparator: Comparator<in Char>): Char {
      if (var0.length() == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Char = var0.charAt(0);
         val var6: Int = StringsKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Char = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Char = var0.charAt(var5);
               var2 = var3;
               if (var1.compare(var3, var4) < 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharSequence.minByOrNull(selector: (Char) -> R): Char? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var2: Char = var0.charAt(0);
         val var5: Int = StringsKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Char = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0.charAt(var4);
                  val var8: java.lang.Comparable = var1.invoke(var3) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) > 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharSequence.minBy(selector: (Char) -> R): Char {
      if (var0.length() != 0) {
         var var2: Char = var0.charAt(0);
         val var5: Int = StringsKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Char = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0.charAt(var4);
                  val var8: java.lang.Comparable = var1.invoke(var3) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) > 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun CharSequence.minOf(selector: (Char) -> Double): Double {
      if (var0.length() == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0.charAt(0)) as java.lang.Number).doubleValue();
         val var7: Int = StringsKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0.charAt(var6)) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharSequence.minOf(selector: (Char) -> Float): Float {
      if (var0.length() == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0.charAt(0)) as java.lang.Number).floatValue();
         val var5: Int = StringsKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0.charAt(var4)) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharSequence.minOf(selector: (Char) -> R): R {
      if (var0.length() == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0.charAt(0)) as java.lang.Comparable;
         val var3: Int = StringsKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0.charAt(var2)) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharSequence.minOfOrNull(selector: (Char) -> R): R? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0.charAt(0)) as java.lang.Comparable;
         val var3: Int = StringsKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0.charAt(var2)) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun CharSequence.minOfOrNull(selector: (Char) -> Double): Double? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0.charAt(0)) as java.lang.Number).doubleValue();
         val var7: Int = StringsKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0.charAt(var6)) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharSequence.minOfOrNull(selector: (Char) -> Float): Float? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0.charAt(0)) as java.lang.Number).floatValue();
         val var5: Int = StringsKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0.charAt(var4)) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <R> CharSequence.minOfWith(comparator: Comparator<in R>, selector: (Char) -> R): R {
      if (var0.length() == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0.charAt(0));
         val var4: Int = StringsKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0.charAt(var3));
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> CharSequence.minOfWithOrNull(comparator: Comparator<in R>, selector: (Char) -> R): R? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0.charAt(0));
         val var4: Int = StringsKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0.charAt(var3));
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public fun CharSequence.minOrNull(): Char? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var1: Char = var0.charAt(0);
         val var5: Int = StringsKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Char = var0.charAt(var4);
               var1 = var2;
               if (Intrinsics.compare((int)var2, (int)var3) > 0) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun CharSequence.min(): Char {
      if (var0.length() == 0) {
         throw new NoSuchElementException();
      } else {
         var var1: Char = var0.charAt(0);
         val var5: Int = StringsKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Char = var0.charAt(var4);
               var1 = var2;
               if (Intrinsics.compare((int)var2, (int)var3) > 0) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun CharSequence.minWithOrNull(comparator: Comparator<in Char>): Char? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var2: Char = var0.charAt(0);
         val var6: Int = StringsKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Char = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Char = var0.charAt(var5);
               var2 = var3;
               if (var1.compare(var3, var4) > 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun CharSequence.minWith(comparator: Comparator<in Char>): Char {
      if (var0.length() == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Char = var0.charAt(0);
         val var6: Int = StringsKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Char = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Char = var0.charAt(var5);
               var2 = var3;
               if (var1.compare(var3, var4) > 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun CharSequence.none(): Boolean {
      val var1: Boolean;
      if (var0.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun CharSequence.none(predicate: (Char) -> Boolean): Boolean {
      for (int var2 = 0; var2 < var0.length(); var2++) {
         if (var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun <S : CharSequence> S.onEach(action: (Char) -> Unit): S {
      for (int var2 = 0; var2 < var0.length(); var2++) {
         var1.invoke(var0.charAt(var2));
      }

      return (S)var0;
   }

   @JvmStatic
   public inline fun <S : CharSequence> S.onEachIndexed(action: (Int, Char) -> Unit): S {
      var var3: Int = 0;

      for (int var2 = 0; var3 < var0.length(); var2++) {
         var1.invoke(var2, var0.charAt(var3));
         var3++;
      }

      return (S)var0;
   }

   @JvmStatic
   public inline fun CharSequence.partition(predicate: (Char) -> Boolean): Pair<CharSequence, CharSequence> {
      val var4: StringBuilder = new StringBuilder();
      val var5: StringBuilder = new StringBuilder();

      for (int var3 = 0; var3 < var0.length(); var3++) {
         val var2: Char = var0.charAt(var3);
         if (var1.invoke(var2) as java.lang.Boolean) {
            var4.append(var2);
         } else {
            var5.append(var2);
         }
      }

      return new Pair<>(var4, var5);
   }

   @JvmStatic
   public inline fun String.partition(predicate: (Char) -> Boolean): Pair<String, String> {
      val var5: StringBuilder = new StringBuilder();
      val var6: StringBuilder = new StringBuilder();
      val var4: Int = var0.length();

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Char = var0.charAt(var3);
         if (var1.invoke(var2) as java.lang.Boolean) {
            var5.append(var2);
         } else {
            var6.append(var2);
         }
      }

      return new Pair<>(var5.toString(), var6.toString());
   }

   @JvmStatic
   public inline fun CharSequence.random(): Char {
      return StringsKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun CharSequence.random(random: Random): Char {
      if (var0.length() != 0) {
         return var0.charAt(var1.nextInt(var0.length()));
      } else {
         throw new NoSuchElementException("Char sequence is empty.");
      }
   }

   @JvmStatic
   public inline fun CharSequence.randomOrNull(): Char? {
      return StringsKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun CharSequence.randomOrNull(random: Random): Char? {
      return if (var0.length() == 0) null else var0.charAt(var1.nextInt(var0.length()));
   }

   @JvmStatic
   public inline fun CharSequence.reduce(operation: (Char, Char) -> Char): Char {
      if (var0.length() == 0) {
         throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
      } else {
         var var3: Char = var0.charAt(0);
         val var5: Int = StringsKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var3;
         if (1 <= var5) {
            while (true) {
               var3 = var1.invoke(var3, var0.charAt(var4)) as Character;
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharSequence.reduceIndexed(operation: (Int, Char, Char) -> Char): Char {
      if (var0.length() == 0) {
         throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
      } else {
         var var3: Char = var0.charAt(0);
         val var5: Int = StringsKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var3;
         if (1 <= var5) {
            while (true) {
               var3 = var1.invoke(var4, var3, var0.charAt(var4)) as Character;
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharSequence.reduceIndexedOrNull(operation: (Int, Char, Char) -> Char): Char? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var3: Char = var0.charAt(0);
         val var5: Int = StringsKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var3;
         if (1 <= var5) {
            while (true) {
               var3 = var1.invoke(var4, var3, var0.charAt(var4)) as Character;
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharSequence.reduceOrNull(operation: (Char, Char) -> Char): Char? {
      if (var0.length() == 0) {
         return null;
      } else {
         var var3: Char = var0.charAt(0);
         val var5: Int = StringsKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var3;
         if (1 <= var5) {
            while (true) {
               var3 = var1.invoke(var3, var0.charAt(var4)) as Character;
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharSequence.reduceRight(operation: (Char, Char) -> Char): Char {
      val var4: Int = StringsKt.getLastIndex(var0);
      if (var4 < 0) {
         throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
      } else {
         var var3: Int = var4 - 1;

         var var2: Char;
         for (var2 = var0.charAt(var4); var3 >= 0; var3--) {
            var2 = var1.invoke(var0.charAt(var3), var2) as Character;
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharSequence.reduceRightIndexed(operation: (Int, Char, Char) -> Char): Char {
      val var4: Int = StringsKt.getLastIndex(var0);
      if (var4 < 0) {
         throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
      } else {
         var var3: Int = var4 - 1;

         var var2: Char;
         for (var2 = var0.charAt(var4); var3 >= 0; var3--) {
            var2 = var1.invoke(var3, var0.charAt(var3), var2) as Character;
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharSequence.reduceRightIndexedOrNull(operation: (Int, Char, Char) -> Char): Char? {
      val var4: Int = StringsKt.getLastIndex(var0);
      if (var4 < 0) {
         return null;
      } else {
         var var3: Int = var4 - 1;

         var var2: Char;
         for (var2 = var0.charAt(var4); var3 >= 0; var3--) {
            var2 = var1.invoke(var3, var0.charAt(var3), var2) as Character;
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharSequence.reduceRightOrNull(operation: (Char, Char) -> Char): Char? {
      val var4: Int = StringsKt.getLastIndex(var0);
      if (var4 < 0) {
         return null;
      } else {
         var var3: Int = var4 - 1;

         var var2: Char;
         for (var2 = var0.charAt(var4); var3 >= 0; var3--) {
            var2 = var1.invoke(var0.charAt(var3), var2) as Character;
         }

         return var2;
      }
   }

   @JvmStatic
   public fun CharSequence.reversed(): CharSequence {
      return new StringBuilder(var0).reverse();
   }

   @JvmStatic
   public inline fun String.reversed(): String {
      return StringsKt.reversed(var0).toString();
   }

   @JvmStatic
   public inline fun <R> CharSequence.runningFold(initial: R, operation: (R, Char) -> R): List<R> {
      if (var0.length() == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var4: ArrayList = new ArrayList(var0.length() + 1);
         var4.add(var1);

         for (int var3 = 0; var3 < var0.length(); var3++) {
            var1 = var2.invoke(var1, var0.charAt(var3));
            var4.add(var1);
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun <R> CharSequence.runningFoldIndexed(initial: R, operation: (Int, R, Char) -> R): List<R> {
      if (var0.length() == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length() + 1);
         var5.add(var1);
         val var4: Int = var0.length();

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0.charAt(var3));
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun CharSequence.runningReduce(operation: (Char, Char) -> Char): List<Char> {
      if (var0.length() == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Char = var0.charAt(0);
         val var6: ArrayList = new ArrayList(var0.length());
         var6.add(var2);
         val var4: Int = var0.length();

         for (int var3 = 1; var3 < var4; var3++) {
            val var5: Character = var1.invoke(var2, var0.charAt(var3)) as Character;
            var2 = var5;
            var6.add(var5);
         }

         return var6;
      }
   }

   @JvmStatic
   public inline fun CharSequence.runningReduceIndexed(operation: (Int, Char, Char) -> Char): List<Char> {
      if (var0.length() == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Char = var0.charAt(0);
         val var6: ArrayList = new ArrayList(var0.length());
         var6.add(var2);
         val var4: Int = var0.length();

         for (int var3 = 1; var3 < var4; var3++) {
            val var5: Character = var1.invoke(var3, var2, var0.charAt(var3)) as Character;
            var2 = var5;
            var6.add(var5);
         }

         return var6;
      }
   }

   @JvmStatic
   public inline fun <R> CharSequence.scan(initial: R, operation: (R, Char) -> R): List<R> {
      val var5: java.util.List;
      if (var0.length() == 0) {
         var5 = CollectionsKt.listOf(var1);
      } else {
         val var4: ArrayList = new ArrayList(var0.length() + 1);
         var4.add(var1);

         for (int var3 = 0; var3 < var0.length(); var3++) {
            var1 = var2.invoke(var1, var0.charAt(var3));
            var4.add(var1);
         }

         var5 = var4;
      }

      return var5;
   }

   @JvmStatic
   public inline fun <R> CharSequence.scanIndexed(initial: R, operation: (Int, R, Char) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length() == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length() + 1);
         var5.add(var1);
         val var4: Int = var0.length();

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0.charAt(var3));
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public fun CharSequence.single(): Char {
      val var1: Int = var0.length();
      if (var1 != 0) {
         if (var1 == 1) {
            return var0.charAt(0);
         } else {
            throw new IllegalArgumentException("Char sequence has more than one element.");
         }
      } else {
         throw new NoSuchElementException("Char sequence is empty.");
      }
   }

   @JvmStatic
   public inline fun CharSequence.single(predicate: (Char) -> Boolean): Char {
      var var6: Character = null;
      var var3: Int = 0;
      var var5: Boolean = false;

      while (var3 < var0.length()) {
         val var2: Char = var0.charAt(var3);
         var var4: Boolean = var5;
         if (var1.invoke(var2) as java.lang.Boolean) {
            if (var5) {
               throw new IllegalArgumentException("Char sequence contains more than one matching element.");
            }

            var6 = var2;
            var4 = true;
         }

         var3++;
         var5 = var4;
      }

      if (var5) {
         return var6;
      } else {
         throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
      }
   }

   @JvmStatic
   public fun CharSequence.singleOrNull(): Char? {
      val var1: Character;
      if (var0.length() == 1) {
         var1 = var0.charAt(0);
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public inline fun CharSequence.singleOrNull(predicate: (Char) -> Boolean): Char? {
      var var3: Int = 0;
      var var6: Character = null;
      var var4: Boolean = false;

      while (var3 < var0.length()) {
         val var2: Char = var0.charAt(var3);
         var var5: Boolean = var4;
         if (var1.invoke(var2) as java.lang.Boolean) {
            if (var4) {
               return null;
            }

            var6 = var2;
            var5 = true;
         }

         var3++;
         var4 = var5;
      }

      return if (!var4) null else var6;
   }

   @JvmStatic
   public fun CharSequence.slice(indices: Iterable<Int>): CharSequence {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return "";
      } else {
         val var3: StringBuilder = new StringBuilder(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.append(var0.charAt((var4.next() as java.lang.Number).intValue()));
         }

         return var3;
      }
   }

   @JvmStatic
   public fun CharSequence.slice(indices: IntRange): CharSequence {
      return if (var1.isEmpty()) "" else StringsKt.subSequence(var0, var1);
   }

   @JvmStatic
   public inline fun String.slice(indices: Iterable<Int>): String {
      return StringsKt.slice(var0, var1).toString();
   }

   @JvmStatic
   public fun String.slice(indices: IntRange): String {
      return if (var1.isEmpty()) "" else StringsKt.substring(var0, var1);
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun CharSequence.sumBy(selector: (Char) -> Int): Int {
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var0.length(); var3++) {
         var2 += (var1.invoke(var0.charAt(var3)) as java.lang.Number).intValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun CharSequence.sumByDouble(selector: (Char) -> Double): Double {
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var0.length(); var4++) {
         var2 += (var1.invoke(var0.charAt(var4)) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun CharSequence.sumOf(selector: (Char) -> Double): Double {
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var0.length(); var4++) {
         var2 += (var1.invoke(var0.charAt(var4)) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun CharSequence.sumOf(selector: (Char) -> Int): Int {
      var var2: Int = 0;

      var var3: Int;
      for (var3 = 0; var2 < var0.length(); var2++) {
         var3 += (var1.invoke(var0.charAt(var2)) as java.lang.Number).intValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun CharSequence.sumOf(selector: (Char) -> Long): Long {
      var var3: Long = 0L;

      for (int var2 = 0; var2 < var0.length(); var2++) {
         var3 += (var1.invoke(var0.charAt(var2)) as java.lang.Number).longValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun CharSequence.sumOf(selector: (Char) -> UInt): UInt {
      var var3: Int = 0;

      var var2: Int;
      for (var2 = UInt.constructor-impl(0); var3 < var0.length(); var3++) {
         var2 = UInt.constructor-impl(var2 + (var1.invoke(var0.charAt(var3)) as UInt).unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public inline fun CharSequence.sumOf(selector: (Char) -> ULong): ULong {
      var var3: Long = ULong.constructor-impl(0L);

      for (int var2 = 0; var2 < var0.length(); var2++) {
         var3 = ULong.constructor-impl(var3 + (var1.invoke(var0.charAt(var2)) as ULong).unbox-impl());
      }

      return var3;
   }

   @JvmStatic
   public fun CharSequence.take(n: Int): CharSequence {
      if (var1 >= 0) {
         return var0.subSequence(0, RangesKt.coerceAtMost(var1, var0.length()));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested character count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun String.take(n: Int): String {
      if (var1 >= 0) {
         var0 = var0.substring(0, RangesKt.coerceAtMost(var1, var0.length()));
         return var0;
      } else {
         val var2: StringBuilder = new StringBuilder("Requested character count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun CharSequence.takeLast(n: Int): CharSequence {
      if (var1 >= 0) {
         val var2: Int = var0.length();
         return var0.subSequence(var2 - RangesKt.coerceAtMost(var1, var2), var2);
      } else {
         val var3: StringBuilder = new StringBuilder("Requested character count ");
         var3.append(var1);
         var3.append(" is less than zero.");
         throw new IllegalArgumentException(var3.toString().toString());
      }
   }

   @JvmStatic
   public fun String.takeLast(n: Int): String {
      if (var1 >= 0) {
         val var2: Int = var0.length();
         var0 = var0.substring(var2 - RangesKt.coerceAtMost(var1, var2));
         return var0;
      } else {
         val var3: StringBuilder = new StringBuilder("Requested character count ");
         var3.append(var1);
         var3.append(" is less than zero.");
         throw new IllegalArgumentException(var3.toString().toString());
      }
   }

   @JvmStatic
   public inline fun CharSequence.takeLastWhile(predicate: (Char) -> Boolean): CharSequence {
      for (int var2 = StringsKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
            return var0.subSequence(var2 + 1, var0.length());
         }
      }

      return var0.subSequence(0, var0.length());
   }

   @JvmStatic
   public inline fun String.takeLastWhile(predicate: (Char) -> Boolean): String {
      for (int var2 = StringsKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
            var0 = var0.substring(var2 + 1);
            return var0;
         }
      }

      return var0;
   }

   @JvmStatic
   public inline fun CharSequence.takeWhile(predicate: (Char) -> Boolean): CharSequence {
      val var3: Int = var0.length();

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
            return var0.subSequence(0, var2);
         }
      }

      return var0.subSequence(0, var0.length());
   }

   @JvmStatic
   public inline fun String.takeWhile(predicate: (Char) -> Boolean): String {
      val var3: Int = var0.length();

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
            var0 = var0.substring(0, var2);
            return var0;
         }
      }

      return var0;
   }

   @JvmStatic
   public fun <C : MutableCollection<in Char>> CharSequence.toCollection(destination: C): C {
      for (int var2 = 0; var2 < var0.length(); var2++) {
         var1.add(var0.charAt(var2));
      }

      return (C)var1;
   }

   @JvmStatic
   public fun CharSequence.toHashSet(): HashSet<Char> {
      return StringsKt.toCollection(var0, new HashSet<>(MapsKt.mapCapacity(RangesKt.coerceAtMost(var0.length(), 128))));
   }

   @JvmStatic
   public fun CharSequence.toList(): List<Char> {
      val var1: Int = var0.length();
      val var2: java.util.List;
      if (var1 != 0) {
         if (var1 != 1) {
            var2 = StringsKt.toMutableList(var0);
         } else {
            var2 = CollectionsKt.listOf(var0.charAt(0));
         }
      } else {
         var2 = CollectionsKt.emptyList();
      }

      return var2;
   }

   @JvmStatic
   public fun CharSequence.toMutableList(): MutableList<Char> {
      return StringsKt.toCollection(var0, new ArrayList<>(var0.length()));
   }

   @JvmStatic
   public fun CharSequence.toSet(): Set<Char> {
      val var1: Int = var0.length();
      val var2: java.util.Set;
      if (var1 != 0) {
         if (var1 != 1) {
            var2 = StringsKt.toCollection(var0, new LinkedHashSet(MapsKt.mapCapacity(RangesKt.coerceAtMost(var0.length(), 128))));
         } else {
            var2 = SetsKt.setOf(var0.charAt(0));
         }
      } else {
         var2 = SetsKt.emptySet();
      }

      return var2;
   }

   @JvmStatic
   public fun CharSequence.windowed(size: Int, step: Int = 1, partialWindows: Boolean = false): List<String> {
      return StringsKt.windowed(var0, var1, var2, var3, new StringsKt___StringsKt$$ExternalSyntheticLambda0());
   }

   @JvmStatic
   public fun <R> CharSequence.windowed(size: Int, step: Int = 1, partialWindows: Boolean = false, transform: (CharSequence) -> R): List<R> {
      SlidingWindowKt.checkWindowSizeStep(var1, var2);
      val var7: Int = var0.length();
      var var8: Int = var7 / var2;
      val var5: Byte;
      if (var7 % var2 == 0) {
         var5 = 0;
      } else {
         var5 = 1;
      }

      val var9: ArrayList = new ArrayList(var8 + var5);

      for (int var10 = 0; var10 >= 0 && var10 < var7; var10 += var2) {
         var var11: Int;
         label35: {
            var8 = var10 + var1;
            if (var10 + var1 >= 0) {
               var11 = var8;
               if (var8 <= var7) {
                  break label35;
               }
            }

            if (!var3) {
               break;
            }

            var11 = var7;
         }

         var9.add(var4.invoke(var0.subSequence(var10, var11)));
      }

      return var9;
   }

   @JvmStatic
   fun `windowed$lambda$23$StringsKt___StringsKt`(var0: java.lang.CharSequence): java.lang.String {
      return var0.toString();
   }

   @JvmStatic
   public fun CharSequence.windowedSequence(size: Int, step: Int = 1, partialWindows: Boolean = false): Sequence<String> {
      return StringsKt.windowedSequence(var0, var1, var2, var3, new StringsKt___StringsKt$$ExternalSyntheticLambda1());
   }

   @JvmStatic
   public fun <R> CharSequence.windowedSequence(size: Int, step: Int = 1, partialWindows: Boolean = false, transform: (CharSequence) -> R): Sequence<R> {
      SlidingWindowKt.checkWindowSizeStep(var1, var2);
      val var5: IntRange;
      if (var3) {
         var5 = StringsKt.getIndices(var0);
      } else {
         var5 = RangesKt.until(0, var0.length() - var1 + 1);
      }

      return SequencesKt.map(CollectionsKt.asSequence(RangesKt.step(var5, var2)), new StringsKt___StringsKt$$ExternalSyntheticLambda4(var1, var0, var4));
   }

   @JvmStatic
   fun `windowedSequence$lambda$24$StringsKt___StringsKt`(var0: java.lang.CharSequence): java.lang.String {
      return var0.toString();
   }

   @JvmStatic
   fun `windowedSequence$lambda$25$StringsKt___StringsKt`(var0: Int, var1: java.lang.CharSequence, var2: Function1, var3: Int): Any {
      return if (var0 + var3 >= 0 && var0 + var3 <= var1.length())
         var2.invoke(var1.subSequence(var3, var0 + var3))
         else
         var2.invoke(var1.subSequence(var3, var1.length()));
   }

   @JvmStatic
   public fun CharSequence.withIndex(): Iterable<IndexedValue<Char>> {
      return new IndexingIterable<>(new StringsKt___StringsKt$$ExternalSyntheticLambda3(var0));
   }

   @JvmStatic
   fun `withIndex$lambda$15$StringsKt___StringsKt`(var0: java.lang.CharSequence): java.util.Iterator {
      return StringsKt.iterator(var0);
   }

   @JvmStatic
   public infix fun CharSequence.zip(other: CharSequence): List<Pair<Char, Char>> {
      val var3: Int = Math.min(var0.length(), var1.length());
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0.charAt(var2), var1.charAt(var2)));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <V> CharSequence.zip(other: CharSequence, transform: (Char, Char) -> V): List<V> {
      val var4: Int = Math.min(var0.length(), var1.length());
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0.charAt(var3), var1.charAt(var3)));
      }

      return var5;
   }

   @JvmStatic
   public fun CharSequence.zipWithNext(): List<Pair<Char, Char>> {
      val var3: Int = var0.length() - 1;
      val var5: java.util.List;
      if (var3 < 1) {
         var5 = CollectionsKt.emptyList();
      } else {
         val var4: ArrayList = new ArrayList(var3);
         var var2: Int = 0;

         while (var2 < var3) {
            var4.add(TuplesKt.to(var0.charAt(var2), var0.charAt(++var2)));
         }

         var5 = var4;
      }

      return var5;
   }

   @JvmStatic
   public inline fun <R> CharSequence.zipWithNext(transform: (Char, Char) -> R): List<R> {
      val var4: Int = var0.length() - 1;
      if (var4 < 1) {
         return CollectionsKt.emptyList();
      } else {
         val var5: ArrayList = new ArrayList(var4);
         var var3: Int = 0;

         while (var3 < var4) {
            var5.add(var1.invoke(var0.charAt(var3), var0.charAt(++var3)));
         }

         return var5;
      }
   }
}
