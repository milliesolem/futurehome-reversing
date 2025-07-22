package kotlin.text

import java.util.Collections
import java.util.EnumSet
import java.util.regex.Matcher
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Intrinsics

@JvmSynthetic
fun `access$findNext`(var0: Matcher, var1: Int, var2: java.lang.CharSequence): MatchResult {
   return findNext(var0, var1, var2);
}

@JvmSynthetic
fun `access$matchEntire`(var0: Matcher, var1: java.lang.CharSequence): MatchResult {
   return matchEntire(var0, var1);
}

@JvmSynthetic
fun `access$range`(var0: java.util.regex.MatchResult): IntRange {
   return range(var0);
}

@JvmSynthetic
fun `access$range`(var0: java.util.regex.MatchResult, var1: Int): IntRange {
   return range(var0, var1);
}

@JvmSynthetic
fun `access$toInt`(var0: java.lang.Iterable): Int {
   return toInt(var0);
}

private fun Matcher.findNext(from: Int, input: CharSequence): MatchResult? {
   val var3: MatchResult;
   if (!var0.find(var1)) {
      var3 = null;
   } else {
      var3 = new MatcherMatchResult(var0, var2);
   }

   return var3;
}

@JvmSynthetic
private inline fun <reified T> fromInt(value: Int): Set<T> where T : FlagEnum, T : Enum<T> {
   Intrinsics.reifiedOperationMarker(4, "T");
   val var1: Class = java.lang.Enum::class.java;
   val var3: EnumSet = EnumSet.allOf(java.lang.Enum.class);
   val var2: EnumSet = var3;
   val var5: java.lang.Iterable = var3;
   Intrinsics.needClassReification();
   CollectionsKt.retainAll(var5, new Function1<T, java.lang.Boolean>(var0) {
      final int $value;

      {
         this.$value = var1;
      }

      public final java.lang.Boolean invoke(T var1) {
         val var3: Boolean;
         if ((this.$value and (var1 as FlagEnum).getMask()) == (var1 as FlagEnum).getValue()) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }
   });
   val var4: java.util.Set = Collections.unmodifiableSet(var3);
   val var6: java.util.Set = var4;
   return var4;
}

private fun Matcher.matchEntire(input: CharSequence): MatchResult? {
   val var2: MatchResult;
   if (!var0.matches()) {
      var2 = null;
   } else {
      var2 = new MatcherMatchResult(var0, var1);
   }

   return var2;
}

private fun java.util.regex.MatchResult.range(): IntRange {
   return RangesKt.until(var0.start(), var0.end());
}

private fun java.util.regex.MatchResult.range(groupIndex: Int): IntRange {
   return RangesKt.until(var0.start(var1), var0.end(var1));
}

private fun Iterable<FlagEnum>.toInt(): Int {
   val var2: java.util.Iterator = var0.iterator();
   var var1: Int = 0;

   while (var2.hasNext()) {
      var1 |= (var2.next() as FlagEnum).getValue();
   }

   return var1;
}
