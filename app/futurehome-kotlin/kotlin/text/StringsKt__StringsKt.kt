package kotlin.text

import java.util.ArrayList
import kotlin.contracts.InvocationKind

internal class StringsKt__StringsKt : StringsKt__StringsJVMKt {
   public final val indices: IntRange
      public final get() {
         return new IntRange(0, var0.length() - 1);
      }


   public final val lastIndex: Int
      public final get() {
         return var0.length() - 1;
      }


   open fun StringsKt__StringsKt() {
   }

   @JvmStatic
   public fun CharSequence.commonPrefixWith(other: CharSequence, ignoreCase: Boolean = false): String {
      label28: {
         val var4: Int = Math.min(var0.length(), var1.length());
         var var3: Int = 0;

         while (var3 < var4 && CharsKt.equals(var0.charAt(var3), var1.charAt(var3), var2)) {
            var3++;
         }

         return if (!StringsKt.hasSurrogatePairAt(var0, var3 - 1) && !StringsKt.hasSurrogatePairAt(var1, var3 - 1))
            var0.subSequence(0, var3).toString()
            else
            var0.subSequence(0, var3 - 1).toString();
      }
   }

   @JvmStatic
   public fun CharSequence.commonSuffixWith(other: CharSequence, ignoreCase: Boolean = false): String {
      label28: {
         val var5: Int = var0.length();
         val var6: Int = var1.length();
         val var4: Int = Math.min(var5, var6);
         var var3: Int = 0;

         while (var3 < var4 && CharsKt.equals(var0.charAt(var5 - var3 - 1), var1.charAt(var6 - var3 - 1), var2)) {
            var3++;
         }

         return if (!StringsKt.hasSurrogatePairAt(var0, var5 - var3 - 1) && !StringsKt.hasSurrogatePairAt(var1, var6 - var3 - 1))
            var0.subSequence(var5 - var3, var5).toString()
            else
            var0.subSequence(var5 - (var3 - 1), var5).toString();
      }
   }

   @JvmStatic
   public operator fun CharSequence.contains(char: Char, ignoreCase: Boolean = false): Boolean {
      if (StringsKt.indexOf$default(var0, var1, 0, var2, 2, null) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun CharSequence.contains(other: CharSequence, ignoreCase: Boolean = false): Boolean {
      if (var1 is java.lang.String) {
         if (StringsKt.indexOf$default(var0, var1 as java.lang.String, 0, var2, 2, null) >= 0) {
            return true;
         }
      } else if (indexOf$StringsKt__StringsKt$default(var0, var1, 0, var0.length(), var2, false, 16, null) >= 0) {
         return true;
      }

      return false;
   }

   @JvmStatic
   public inline operator fun CharSequence.contains(regex: Regex): Boolean {
      return var1.containsMatchIn(var0);
   }

   @JvmStatic
   internal fun CharSequence?.contentEqualsIgnoreCaseImpl(other: CharSequence?): Boolean {
      if (var0 is java.lang.String && var1 is java.lang.String) {
         return StringsKt.equals(var0 as java.lang.String, var1 as java.lang.String, true);
      } else if (var0 === var1) {
         return true;
      } else if (var0 != null && var1 != null && var0.length() == var1.length()) {
         val var3: Int = var0.length();

         for (int var2 = 0; var2 < var3; var2++) {
            if (!CharsKt.equals(var0.charAt(var2), var1.charAt(var2), true)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @JvmStatic
   internal fun CharSequence?.contentEqualsImpl(other: CharSequence?): Boolean {
      if (var0 is java.lang.String && var1 is java.lang.String) {
         return var0 == var1;
      } else if (var0 === var1) {
         return true;
      } else if (var0 != null && var1 != null && var0.length() == var1.length()) {
         val var3: Int = var0.length();

         for (int var2 = 0; var2 < var3; var2++) {
            if (var0.charAt(var2) != var1.charAt(var2)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @JvmStatic
   public fun CharSequence.endsWith(char: Char, ignoreCase: Boolean = false): Boolean {
      if (var0.length() > 0 && CharsKt.equals(var0.charAt(StringsKt.getLastIndex(var0)), var1, var2)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public fun CharSequence.endsWith(suffix: CharSequence, ignoreCase: Boolean = false): Boolean {
      return if (!var2 && var0 is java.lang.String && var1 is java.lang.String)
         StringsKt.endsWith$default(var0 as java.lang.String, var1 as java.lang.String, false, 2, null)
         else
         StringsKt.regionMatchesImpl(var0, var0.length() - var1.length(), var1, 0, var1.length(), var2);
   }

   @JvmStatic
   public fun CharSequence.findAnyOf(strings: Collection<String>, startIndex: Int = 0, ignoreCase: Boolean = false): Pair<Int, String>? {
      return findAnyOf$StringsKt__StringsKt(var0, var1, var2, var3, false);
   }

   @JvmStatic
   private fun CharSequence.findAnyOf(strings: Collection<String>, startIndex: Int, ignoreCase: Boolean, last: Boolean): Pair<Int, String>? {
      if (!var3 && var1.size() == 1) {
         val var12: java.lang.String = CollectionsKt.single(var1);
         if (!var4) {
            var2 = StringsKt.indexOf$default(var0, var12, var2, false, 4, null);
         } else {
            var2 = StringsKt.lastIndexOf$default(var0, var12, var2, false, 4, null);
         }

         val var11: Pair;
         if (var2 < 0) {
            var11 = null;
         } else {
            var11 = TuplesKt.to(var2, var12);
         }

         return var11;
      } else {
         val var19: IntProgression;
         if (!var4) {
            var19 = new IntRange(RangesKt.coerceAtLeast(var2, 0), var0.length());
         } else {
            var19 = RangesKt.downTo(RangesKt.coerceAtMost(var2, StringsKt.getLastIndex(var0)), 0);
         }

         if (var0 is java.lang.String) {
            var var6: Int;
            var var7: Int;
            label103: {
               val var5: Int = var19.getFirst();
               var7 = var19.getLast();
               var6 = var19.getStep();
               if (var6 > 0) {
                  var2 = var5;
                  if (var5 <= var7) {
                     break label103;
                  }
               }

               if (var6 >= 0 || var7 > var5) {
                  return null;
               }

               var2 = var5;
            }

            while (true) {
               val var9: java.util.Iterator = var1.iterator();

               do {
                  if (!var9.hasNext()) {
                     var20 = null;
                     break;
                  }

                  var20 = var9.next();
               } while (!StringsKt.regionMatches((java.lang.String)var20, 0, (java.lang.String)var0, var2, ((java.lang.String)var20).length(), var3));

               val var21: java.lang.String = var20 as java.lang.String;
               if (var20 as java.lang.String != null) {
                  return TuplesKt.to(var2, var21);
               }

               if (var2 == var7) {
                  break;
               }

               var2 += var6;
            }
         } else {
            var var17: Int;
            var var18: Int;
            label105: {
               val var16: Int = var19.getFirst();
               var18 = var19.getLast();
               var17 = var19.getStep();
               if (var17 > 0) {
                  var2 = var16;
                  if (var16 <= var18) {
                     break label105;
                  }
               }

               if (var17 >= 0 || var18 > var16) {
                  return null;
               }

               var2 = var16;
            }

            while (true) {
               val var24: java.util.Iterator = var1.iterator();

               do {
                  if (!var24.hasNext()) {
                     var22 = null;
                     break;
                  }

                  var22 = var24.next();
               } while (!StringsKt.regionMatchesImpl((java.lang.String)var22, 0, var0, var2, ((java.lang.String)var22).length(), var3));

               val var23: java.lang.String = var22 as java.lang.String;
               if (var22 as java.lang.String != null) {
                  return TuplesKt.to(var2, var23);
               }

               if (var2 == var18) {
                  break;
               }

               var2 += var17;
            }
         }

         return null;
      }
   }

   @JvmStatic
   public fun CharSequence.findLastAnyOf(strings: Collection<String>, startIndex: Int = StringsKt.getLastIndex(var0), ignoreCase: Boolean = false): Pair<
         Int,
         String
      >? {
      return findAnyOf$StringsKt__StringsKt(var0, var1, var2, var3, true);
   }

   @JvmStatic
   public fun CharSequence.hasSurrogatePairAt(index: Int): Boolean {
      return var1 >= 0 && var1 <= var0.length() - 2 && Character.isHighSurrogate(var0.charAt(var1)) && Character.isLowSurrogate(var0.charAt(var1 + 1));
   }

   @JvmStatic
   public inline fun <C, R> C.ifBlank(defaultValue: () -> R): R where C : CharSequence, C : R {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      var var2: Any = var0;
      if (StringsKt.isBlank(var0)) {
         var2 = var1.invoke();
      }

      return (R)var2;
   }

   @JvmStatic
   public inline fun <C, R> C.ifEmpty(defaultValue: () -> R): R where C : CharSequence, C : R {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      var var2: Any = var0;
      if (var0.length() == 0) {
         var2 = var1.invoke();
      }

      return (R)var2;
   }

   @JvmStatic
   public fun CharSequence.indexOf(char: Char, startIndex: Int = 0, ignoreCase: Boolean = false): Int {
      if (!var3 && var0 is java.lang.String) {
         var2 = (var0 as java.lang.String).indexOf(var1, var2);
      } else {
         var2 = StringsKt.indexOfAny(var0, new char[]{var1}, var2, var3);
      }

      return var2;
   }

   @JvmStatic
   public fun CharSequence.indexOf(string: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int {
      if (!var3 && var0 is java.lang.String) {
         var2 = (var0 as java.lang.String).indexOf(var1, var2);
      } else {
         var2 = indexOf$StringsKt__StringsKt$default(var0, var1, var2, var0.length(), var3, false, 16, null);
      }

      return var2;
   }

   @JvmStatic
   private fun CharSequence.indexOf(other: CharSequence, startIndex: Int, endIndex: Int, ignoreCase: Boolean, last: Boolean = ...): Int {
      val var8: IntProgression;
      if (!var5) {
         var8 = new IntRange(RangesKt.coerceAtLeast(var2, 0), RangesKt.coerceAtMost(var3, var0.length()));
      } else {
         var8 = RangesKt.downTo(RangesKt.coerceAtMost(var2, StringsKt.getLastIndex(var0)), RangesKt.coerceAtLeast(var3, 0));
      }

      if (var0 is java.lang.String && var1 is java.lang.String) {
         var var13: Int;
         var var14: Int;
         label67: {
            var3 = var8.getFirst();
            var13 = var8.getLast();
            var14 = var8.getStep();
            if (var14 > 0) {
               var2 = var3;
               if (var3 <= var13) {
                  break label67;
               }
            }

            if (var14 >= 0 || var13 > var3) {
               return -1;
            }

            var2 = var3;
         }

         while (true) {
            if (StringsKt.regionMatches(var1 as java.lang.String, 0, var0 as java.lang.String, var2, (var1 as java.lang.String).length(), var4)) {
               return var2;
            }

            if (var2 == var13) {
               break;
            }

            var2 += var14;
         }
      } else {
         var var6: Int;
         var var7: Int;
         label69: {
            var3 = var8.getFirst();
            var7 = var8.getLast();
            var6 = var8.getStep();
            if (var6 > 0) {
               var2 = var3;
               if (var3 <= var7) {
                  break label69;
               }
            }

            if (var6 >= 0 || var7 > var3) {
               return -1;
            }

            var2 = var3;
         }

         while (true) {
            if (StringsKt.regionMatchesImpl(var1, 0, var0, var2, var1.length(), var4)) {
               return var2;
            }

            if (var2 == var7) {
               break;
            }

            var2 += var6;
         }
      }

      return -1;
   }

   @JvmStatic
   public fun CharSequence.indexOfAny(strings: Collection<String>, startIndex: Int = 0, ignoreCase: Boolean = false): Int {
      val var4: Pair = findAnyOf$StringsKt__StringsKt(var0, var1, var2, var3, false);
      if (var4 != null) {
         var2 = (var4.getFirst() as java.lang.Number).intValue();
      } else {
         var2 = -1;
      }

      return var2;
   }

   @JvmStatic
   public fun CharSequence.indexOfAny(chars: CharArray, startIndex: Int = 0, ignoreCase: Boolean = false): Int {
      if (!var3 && var1.length == 1 && var0 is java.lang.String) {
         return (var0 as java.lang.String).indexOf(ArraysKt.single(var1), var2);
      } else {
         var2 = RangesKt.coerceAtLeast(var2, 0);
         val var6: Int = StringsKt.getLastIndex(var0);
         if (var2 <= var6) {
            while (true) {
               val var4: Char = var0.charAt(var2);
               val var7: Int = var1.length;

               for (int var5 = 0; var5 < var7; var5++) {
                  if (CharsKt.equals(var1[var5], var4, var3)) {
                     return var2;
                  }
               }

               if (var2 == var6) {
                  break;
               }

               var2++;
            }
         }

         return -1;
      }
   }

   @JvmStatic
   public fun CharSequence.isBlank(): Boolean {
      var var2: Boolean = false;
      var var1: Int = 0;

      while (true) {
         if (var1 >= var0.length()) {
            var2 = true;
            break;
         }

         if (!CharsKt.isWhitespace(var0.charAt(var1))) {
            break;
         }

         var1++;
      }

      return var2;
   }

   @JvmStatic
   public inline fun CharSequence.isEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun CharSequence.isNotBlank(): Boolean {
      return StringsKt.isBlank(var0) xor true;
   }

   @JvmStatic
   public inline fun CharSequence.isNotEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun CharSequence?.isNullOrBlank(): Boolean {
      contract {
         returns(false) implies (this != null)
      }

      val var1: Boolean;
      if (var0 != null && !StringsKt.isBlank(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   @JvmStatic
   public inline fun CharSequence?.isNullOrEmpty(): Boolean {
      contract {
         returns(false) implies (this != null)
      }

      val var1: Boolean;
      if (var0 != null && var0.length() != 0) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   @JvmStatic
   public operator fun CharSequence.iterator(): CharIterator {
      return new CharIterator(var0) {
         final java.lang.CharSequence $this_iterator;
         private int index;

         {
            this.$this_iterator = var1;
         }

         @Override
         public boolean hasNext() {
            val var1: Boolean;
            if (this.index < this.$this_iterator.length()) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         @Override
         public char nextChar() {
            return this.$this_iterator.charAt(this.index++);
         }
      };
   }

   @JvmStatic
   public fun CharSequence.lastIndexOf(char: Char, startIndex: Int = StringsKt.getLastIndex(var0), ignoreCase: Boolean = false): Int {
      if (!var3 && var0 is java.lang.String) {
         var2 = (var0 as java.lang.String).lastIndexOf(var1, var2);
      } else {
         var2 = StringsKt.lastIndexOfAny(var0, new char[]{var1}, var2, var3);
      }

      return var2;
   }

   @JvmStatic
   public fun CharSequence.lastIndexOf(string: String, startIndex: Int = StringsKt.getLastIndex(var0), ignoreCase: Boolean = false): Int {
      if (!var3 && var0 is java.lang.String) {
         var2 = (var0 as java.lang.String).lastIndexOf(var1, var2);
      } else {
         var2 = indexOf$StringsKt__StringsKt(var0, var1, var2, 0, var3, true);
      }

      return var2;
   }

   @JvmStatic
   public fun CharSequence.lastIndexOfAny(strings: Collection<String>, startIndex: Int = StringsKt.getLastIndex(var0), ignoreCase: Boolean = false): Int {
      val var4: Pair = findAnyOf$StringsKt__StringsKt(var0, var1, var2, var3, true);
      if (var4 != null) {
         var2 = (var4.getFirst() as java.lang.Number).intValue();
      } else {
         var2 = -1;
      }

      return var2;
   }

   @JvmStatic
   public fun CharSequence.lastIndexOfAny(chars: CharArray, startIndex: Int = StringsKt.getLastIndex(var0), ignoreCase: Boolean = false): Int {
      if (!var3 && var1.length == 1 && var0 is java.lang.String) {
         return (var0 as java.lang.String).lastIndexOf(ArraysKt.single(var1), var2);
      } else {
         for (int var7 = RangesKt.coerceAtMost(var2, StringsKt.getLastIndex(var0)); -1 < var7; var7--) {
            val var4: Char = var0.charAt(var7);
            val var6: Int = var1.length;

            for (int var5 = 0; var5 < var6; var5++) {
               if (CharsKt.equals(var1[var5], var4, var3)) {
                  return var7;
               }
            }
         }

         return -1;
      }
   }

   @JvmStatic
   public fun CharSequence.lineSequence(): Sequence<String> {
      return new Sequence<java.lang.String>(var0) {
         final java.lang.CharSequence $this_lineSequence$inlined;

         {
            this.$this_lineSequence$inlined = var1;
         }

         @Override
         public java.util.Iterator<java.lang.String> iterator() {
            return new LinesIterator(this.$this_lineSequence$inlined);
         }
      };
   }

   @JvmStatic
   public fun CharSequence.lines(): List<String> {
      return SequencesKt.toList(StringsKt.lineSequence(var0));
   }

   @JvmStatic
   public inline infix fun CharSequence.matches(regex: Regex): Boolean {
      return var1.matches(var0);
   }

   @JvmStatic
   public inline fun String?.orEmpty(): String {
      var var1: java.lang.String = var0;
      if (var0 == null) {
         var1 = "";
      }

      return var1;
   }

   @JvmStatic
   public fun CharSequence.padEnd(length: Int, padChar: Char = 32): CharSequence {
      if (var1 >= 0) {
         if (var1 <= var0.length()) {
            return var0.subSequence(0, var0.length());
         } else {
            val var4: StringBuilder = new StringBuilder(var1);
            var4.append(var0);
            val var3: Int = var1 - var0.length();
            var1 = 1;
            if (1 <= var3) {
               while (true) {
                  var4.append(var2);
                  if (var1 == var3) {
                     break;
                  }

                  var1++;
               }
            }

            return var4;
         }
      } else {
         val var5: StringBuilder = new StringBuilder("Desired length ");
         var5.append(var1);
         var5.append(" is less than zero.");
         throw new IllegalArgumentException(var5.toString());
      }
   }

   @JvmStatic
   public fun String.padEnd(length: Int, padChar: Char = 32): String {
      return StringsKt.padEnd(var0, var1, var2).toString();
   }

   @JvmStatic
   public fun CharSequence.padStart(length: Int, padChar: Char = 32): CharSequence {
      if (var1 >= 0) {
         if (var1 <= var0.length()) {
            return var0.subSequence(0, var0.length());
         } else {
            val var4: StringBuilder = new StringBuilder(var1);
            val var3: Int = var1 - var0.length();
            var1 = 1;
            if (1 <= var3) {
               while (true) {
                  var4.append(var2);
                  if (var1 == var3) {
                     break;
                  }

                  var1++;
               }
            }

            var4.append(var0);
            return var4;
         }
      } else {
         val var5: StringBuilder = new StringBuilder("Desired length ");
         var5.append(var1);
         var5.append(" is less than zero.");
         throw new IllegalArgumentException(var5.toString());
      }
   }

   @JvmStatic
   public fun String.padStart(length: Int, padChar: Char = 32): String {
      return StringsKt.padStart(var0, var1, var2).toString();
   }

   @JvmStatic
   private fun CharSequence.rangesDelimitedBy(delimiters: CharArray, startIndex: Int = ..., ignoreCase: Boolean = ..., limit: Int = ...): Sequence<IntRange> {
      StringsKt.requireNonNegativeLimit(var4);
      return new DelimitedRangesSequence(var0, var2, var4, new StringsKt__StringsKt$$ExternalSyntheticLambda1(var1, var3));
   }

   @JvmStatic
   private fun CharSequence.rangesDelimitedBy(delimiters: Array<out String>, startIndex: Int = ..., ignoreCase: Boolean = ..., limit: Int = ...): Sequence<
         IntRange
      > {
      StringsKt.requireNonNegativeLimit(var4);
      return new DelimitedRangesSequence(var0, var2, var4, new StringsKt__StringsKt$$ExternalSyntheticLambda2(ArraysKt.asList(var1), var3));
   }

   @JvmStatic
   fun `rangesDelimitedBy$lambda$14$StringsKt__StringsKt`(var0: CharArray, var1: Boolean, var2: java.lang.CharSequence, var3: Int): Pair {
      var3 = StringsKt.indexOfAny(var2, var0, var3, var1);
      val var4: Pair;
      if (var3 < 0) {
         var4 = null;
      } else {
         var4 = TuplesKt.to(var3, 1);
      }

      return var4;
   }

   @JvmStatic
   fun `rangesDelimitedBy$lambda$16$StringsKt__StringsKt`(var0: java.util.List, var1: Boolean, var2: java.lang.CharSequence, var3: Int): Pair {
      val var4: Pair = findAnyOf$StringsKt__StringsKt(var2, var0, var3, var1, false);
      val var5: Pair;
      if (var4 != null) {
         var5 = TuplesKt.to(var4.getFirst(), (var4.getSecond() as java.lang.String).length());
      } else {
         var5 = null;
      }

      return var5;
   }

   @JvmStatic
   internal fun CharSequence.regionMatchesImpl(thisOffset: Int, other: CharSequence, otherOffset: Int, length: Int, ignoreCase: Boolean): Boolean {
      if (var3 >= 0 && var1 >= 0 && var1 <= var0.length() - var4 && var3 <= var2.length() - var4) {
         for (int var6 = 0; var6 < var4; var6++) {
            if (!CharsKt.equals(var0.charAt(var1 + var6), var2.charAt(var3 + var6), var5)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @JvmStatic
   public fun CharSequence.removePrefix(prefix: CharSequence): CharSequence {
      return if (StringsKt.startsWith$default(var0, var1, false, 2, null))
         var0.subSequence(var1.length(), var0.length())
         else
         var0.subSequence(0, var0.length());
   }

   @JvmStatic
   public fun String.removePrefix(prefix: CharSequence): String {
      var var2: java.lang.String = var0;
      if (StringsKt.startsWith$default(var0, var1, false, 2, null)) {
         var2 = var0.substring(var1.length());
      }

      return var2;
   }

   @JvmStatic
   public fun CharSequence.removeRange(startIndex: Int, endIndex: Int): CharSequence {
      if (var2 >= var1) {
         if (var2 == var1) {
            return var0.subSequence(0, var0.length());
         } else {
            val var3: StringBuilder = new StringBuilder(var0.length() - (var2 - var1));
            var3.append(var0, 0, var1);
            var3.append(var0, var2, var0.length());
            return var3;
         }
      } else {
         val var4: StringBuilder = new StringBuilder("End index (");
         var4.append(var2);
         var4.append(") is less than start index (");
         var4.append(var1);
         var4.append(").");
         throw new IndexOutOfBoundsException(var4.toString());
      }
   }

   @JvmStatic
   public fun CharSequence.removeRange(range: IntRange): CharSequence {
      return StringsKt.removeRange(var0, var1.getStart(), var1.getEndInclusive() + 1);
   }

   @JvmStatic
   public inline fun String.removeRange(startIndex: Int, endIndex: Int): String {
      return StringsKt.removeRange(var0, var1, var2).toString();
   }

   @JvmStatic
   public inline fun String.removeRange(range: IntRange): String {
      return StringsKt.removeRange(var0, var1).toString();
   }

   @JvmStatic
   public fun CharSequence.removeSuffix(suffix: CharSequence): CharSequence {
      return if (StringsKt.endsWith$default(var0, var1, false, 2, null))
         var0.subSequence(0, var0.length() - var1.length())
         else
         var0.subSequence(0, var0.length());
   }

   @JvmStatic
   public fun String.removeSuffix(suffix: CharSequence): String {
      var var2: java.lang.String = var0;
      if (StringsKt.endsWith$default(var0, var1, false, 2, null)) {
         var2 = var0.substring(0, var0.length() - var1.length());
      }

      return var2;
   }

   @JvmStatic
   public fun CharSequence.removeSurrounding(delimiter: CharSequence): CharSequence {
      return StringsKt.removeSurrounding(var0, var1, var1);
   }

   @JvmStatic
   public fun CharSequence.removeSurrounding(prefix: CharSequence, suffix: CharSequence): CharSequence {
      return if (var0.length() >= var1.length() + var2.length()
            && StringsKt.startsWith$default(var0, var1, false, 2, null)
            && StringsKt.endsWith$default(var0, var2, false, 2, null))
         var0.subSequence(var1.length(), var0.length() - var2.length())
         else
         var0.subSequence(0, var0.length());
   }

   @JvmStatic
   public fun String.removeSurrounding(delimiter: CharSequence): String {
      return StringsKt.removeSurrounding(var0, var1, var1);
   }

   @JvmStatic
   public fun String.removeSurrounding(prefix: CharSequence, suffix: CharSequence): String {
      var var3: java.lang.String = var0;
      if (var0.length() >= var1.length() + var2.length()) {
         val var4: java.lang.CharSequence = var0;
         var3 = var0;
         if (StringsKt.startsWith$default(var4, var1, false, 2, null)) {
            var3 = var0;
            if (StringsKt.endsWith$default(var4, var2, false, 2, null)) {
               var3 = var0.substring(var1.length(), var0.length() - var2.length());
            }
         }
      }

      return var3;
   }

   @JvmStatic
   public inline fun CharSequence.replace(regex: Regex, replacement: String): String {
      return var1.replace(var0, var2);
   }

   @JvmStatic
   public inline fun CharSequence.replace(regex: Regex, noinline transform: (MatchResult) -> CharSequence): String {
      return var1.replace(var0, var2);
   }

   @JvmStatic
   public fun String.replaceAfter(delimiter: Char, replacement: String, missingDelimiterValue: String = var0): String {
      val var5: java.lang.CharSequence = var0;
      val var4: Int = StringsKt.indexOf$default(var0, var1, 0, false, 6, null);
      if (var4 != -1) {
         var3 = StringsKt.replaceRange(var5, var4 + 1, var0.length(), var2).toString();
      }

      return var3;
   }

   @JvmStatic
   public fun String.replaceAfter(delimiter: String, replacement: String, missingDelimiterValue: String = var0): String {
      val var5: java.lang.CharSequence = var0;
      val var4: Int = StringsKt.indexOf$default(var0, var1, 0, false, 6, null);
      if (var4 != -1) {
         var3 = StringsKt.replaceRange(var5, var4 + var1.length(), var0.length(), var2).toString();
      }

      return var3;
   }

   @JvmStatic
   public fun String.replaceAfterLast(delimiter: Char, replacement: String, missingDelimiterValue: String = var0): String {
      val var5: java.lang.CharSequence = var0;
      val var4: Int = StringsKt.lastIndexOf$default(var0, var1, 0, false, 6, null);
      if (var4 != -1) {
         var3 = StringsKt.replaceRange(var5, var4 + 1, var0.length(), var2).toString();
      }

      return var3;
   }

   @JvmStatic
   public fun String.replaceAfterLast(delimiter: String, replacement: String, missingDelimiterValue: String = var0): String {
      val var5: java.lang.CharSequence = var0;
      val var4: Int = StringsKt.lastIndexOf$default(var0, var1, 0, false, 6, null);
      if (var4 != -1) {
         var3 = StringsKt.replaceRange(var5, var4 + var1.length(), var0.length(), var2).toString();
      }

      return var3;
   }

   @JvmStatic
   public fun String.replaceBefore(delimiter: Char, replacement: String, missingDelimiterValue: String = var0): String {
      val var5: java.lang.CharSequence = var0;
      val var4: Int = StringsKt.indexOf$default(var0, var1, 0, false, 6, null);
      if (var4 != -1) {
         var3 = StringsKt.replaceRange(var5, 0, var4, var2).toString();
      }

      return var3;
   }

   @JvmStatic
   public fun String.replaceBefore(delimiter: String, replacement: String, missingDelimiterValue: String = var0): String {
      val var5: java.lang.CharSequence = var0;
      val var4: Int = StringsKt.indexOf$default(var0, var1, 0, false, 6, null);
      if (var4 != -1) {
         var3 = StringsKt.replaceRange(var5, 0, var4, var2).toString();
      }

      return var3;
   }

   @JvmStatic
   public fun String.replaceBeforeLast(delimiter: Char, replacement: String, missingDelimiterValue: String = var0): String {
      val var5: java.lang.CharSequence = var0;
      val var4: Int = StringsKt.lastIndexOf$default(var0, var1, 0, false, 6, null);
      if (var4 != -1) {
         var3 = StringsKt.replaceRange(var5, 0, var4, var2).toString();
      }

      return var3;
   }

   @JvmStatic
   public fun String.replaceBeforeLast(delimiter: String, replacement: String, missingDelimiterValue: String = var0): String {
      val var5: java.lang.CharSequence = var0;
      val var4: Int = StringsKt.lastIndexOf$default(var0, var1, 0, false, 6, null);
      if (var4 != -1) {
         var3 = StringsKt.replaceRange(var5, 0, var4, var2).toString();
      }

      return var3;
   }

   @JvmStatic
   public inline fun CharSequence.replaceFirst(regex: Regex, replacement: String): String {
      return var1.replaceFirst(var0, var2);
   }

   @JvmStatic
   public inline fun String.replaceFirstChar(transform: (Char) -> Char): String {
      var var3: java.lang.String = var0;
      if (var0.length() > 0) {
         val var2: Char = var1.invoke(var0.charAt(0)) as Character;
         val var5: java.lang.String = var0.substring(1);
         val var4: StringBuilder = new StringBuilder();
         var4.append(var2);
         var4.append(var5);
         var3 = var4.toString();
      }

      return var3;
   }

   @JvmStatic
   public inline fun String.replaceFirstChar(transform: (Char) -> CharSequence): String {
      var var2: java.lang.String = var0;
      if (var0.length() > 0) {
         val var4: StringBuilder = new StringBuilder();
         var4.append(var1.invoke(var0.charAt(0)));
         var0 = var0.substring(1);
         var4.append(var0);
         var2 = var4.toString();
      }

      return var2;
   }

   @JvmStatic
   public fun CharSequence.replaceRange(startIndex: Int, endIndex: Int, replacement: CharSequence): CharSequence {
      if (var2 >= var1) {
         val var4: StringBuilder = new StringBuilder();
         var4.append(var0, 0, var1);
         var4.append(var3);
         var4.append(var0, var2, var0.length());
         return var4;
      } else {
         val var5: StringBuilder = new StringBuilder("End index (");
         var5.append(var2);
         var5.append(") is less than start index (");
         var5.append(var1);
         var5.append(").");
         throw new IndexOutOfBoundsException(var5.toString());
      }
   }

   @JvmStatic
   public fun CharSequence.replaceRange(range: IntRange, replacement: CharSequence): CharSequence {
      return StringsKt.replaceRange(var0, var1.getStart(), var1.getEndInclusive() + 1, var2);
   }

   @JvmStatic
   public inline fun String.replaceRange(startIndex: Int, endIndex: Int, replacement: CharSequence): String {
      return StringsKt.replaceRange(var0, var1, var2, var3).toString();
   }

   @JvmStatic
   public inline fun String.replaceRange(range: IntRange, replacement: CharSequence): String {
      return StringsKt.replaceRange(var0, var1, var2).toString();
   }

   @JvmStatic
   internal fun requireNonNegativeLimit(limit: Int) {
      if (var0 < 0) {
         val var1: StringBuilder = new StringBuilder("Limit must be non-negative, but was ");
         var1.append(var0);
         throw new IllegalArgumentException(var1.toString().toString());
      }
   }

   @JvmStatic
   public inline fun CharSequence.split(regex: Regex, limit: Int = 0): List<String> {
      return var1.split(var0, var2);
   }

   @JvmStatic
   public fun CharSequence.split(delimiters: CharArray, ignoreCase: Boolean = false, limit: Int = 0): List<String> {
      if (var1.length == 1) {
         return split$StringsKt__StringsKt(var0, java.lang.String.valueOf(var1[0]), var2, var3);
      } else {
         val var4: java.lang.Iterable = SequencesKt.asIterable(rangesDelimitedBy$StringsKt__StringsKt$default(var0, var1, 0, var2, var3, 2, null));
         val var5: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10));
         val var6: java.util.Iterator = var4.iterator();

         while (var6.hasNext()) {
            var5.add(StringsKt.substring(var0, var6.next() as IntRange));
         }

         return var5 as MutableList<java.lang.String>;
      }
   }

   @JvmStatic
   public fun CharSequence.split(vararg delimiters: String, ignoreCase: Boolean = false, limit: Int = 0): List<String> {
      if (var1.length == 1) {
         val var4: java.lang.String = var1[0];
         if (var1[0].length() != 0) {
            return split$StringsKt__StringsKt(var0, var4, var2, var3);
         }
      }

      val var6: java.lang.Iterable = SequencesKt.asIterable(rangesDelimitedBy$StringsKt__StringsKt$default(var0, var1, 0, var2, var3, 2, null));
      val var5: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var6, 10));
      val var7: java.util.Iterator = var6.iterator();

      while (var7.hasNext()) {
         var5.add(StringsKt.substring(var0, var7.next() as IntRange));
      }

      return var5 as MutableList<java.lang.String>;
   }

   @JvmStatic
   private fun CharSequence.split(delimiter: String, ignoreCase: Boolean, limit: Int): List<String> {
      StringsKt.requireNonNegativeLimit(var3);
      var var6: Int = 0;
      var var7: Int = StringsKt.indexOf(var0, var1, 0, var2);
      if (var7 != -1 && var3 != 1) {
         val var4: Boolean;
         if (var3 > 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         var var5: Int = 10;
         if (var4) {
            var5 = RangesKt.coerceAtMost(var3, 10);
         }

         val var9: ArrayList = new ArrayList(var5);
         var5 = var7;

         val var8: Int;
         do {
            var9.add(var0.subSequence(var6, var5).toString());
            var7 = var1.length() + var5;
            if (var4 && var9.size() == var3 - 1) {
               break;
            }

            var8 = StringsKt.indexOf(var0, var1, var7, var2);
            var6 = var7;
            var5 = var8;
         } while (var8 != -1);

         var9.add(var0.subSequence(var7, var0.length()).toString());
         return var9;
      } else {
         return CollectionsKt.listOf(var0.toString());
      }
   }

   @JvmStatic
   public inline fun CharSequence.splitToSequence(regex: Regex, limit: Int = 0): Sequence<String> {
      return var1.splitToSequence(var0, var2);
   }

   @JvmStatic
   public fun CharSequence.splitToSequence(delimiters: CharArray, ignoreCase: Boolean = false, limit: Int = 0): Sequence<String> {
      return SequencesKt.map(
         rangesDelimitedBy$StringsKt__StringsKt$default(var0, var1, 0, var2, var3, 2, null), new StringsKt__StringsKt$$ExternalSyntheticLambda0(var0)
      );
   }

   @JvmStatic
   public fun CharSequence.splitToSequence(vararg delimiters: String, ignoreCase: Boolean = false, limit: Int = 0): Sequence<String> {
      return SequencesKt.map(
         rangesDelimitedBy$StringsKt__StringsKt$default(var0, var1, 0, var2, var3, 2, null), new StringsKt__StringsKt$$ExternalSyntheticLambda3(var0)
      );
   }

   @JvmStatic
   fun `splitToSequence$lambda$18$StringsKt__StringsKt`(var0: java.lang.CharSequence, var1: IntRange): java.lang.String {
      return StringsKt.substring(var0, var1);
   }

   @JvmStatic
   fun `splitToSequence$lambda$20$StringsKt__StringsKt`(var0: java.lang.CharSequence, var1: IntRange): java.lang.String {
      return StringsKt.substring(var0, var1);
   }

   @JvmStatic
   public fun CharSequence.startsWith(char: Char, ignoreCase: Boolean = false): Boolean {
      val var3: Int = var0.length();
      var var4: Boolean = false;
      if (var3 > 0) {
         var4 = false;
         if (CharsKt.equals(var0.charAt(0), var1, var2)) {
            var4 = true;
         }
      }

      return var4;
   }

   @JvmStatic
   public fun CharSequence.startsWith(prefix: CharSequence, startIndex: Int, ignoreCase: Boolean = false): Boolean {
      return if (!var3 && var0 is java.lang.String && var1 is java.lang.String)
         StringsKt.startsWith$default(var0 as java.lang.String, var1 as java.lang.String, var2, false, 4, null)
         else
         StringsKt.regionMatchesImpl(var0, var2, var1, 0, var1.length(), var3);
   }

   @JvmStatic
   public fun CharSequence.startsWith(prefix: CharSequence, ignoreCase: Boolean = false): Boolean {
      return if (!var2 && var0 is java.lang.String && var1 is java.lang.String)
         StringsKt.startsWith$default(var0 as java.lang.String, var1 as java.lang.String, false, 2, null)
         else
         StringsKt.regionMatchesImpl(var0, 0, var1, 0, var1.length(), var2);
   }

   @JvmStatic
   public fun CharSequence.subSequence(range: IntRange): CharSequence {
      return var0.subSequence(var1.getStart(), var1.getEndInclusive() + 1);
   }

   @Deprecated(message = "Use parameters named startIndex and endIndex.", replaceWith = @ReplaceWith(expression = "subSequence(startIndex = start, endIndex = end)", imports = []))
   @JvmStatic
   public inline fun String.subSequence(start: Int, end: Int): CharSequence {
      return var0.subSequence(var1, var2);
   }

   @JvmStatic
   public inline fun CharSequence.substring(startIndex: Int, endIndex: Int = var0.length()): String {
      return var0.subSequence(var1, var2).toString();
   }

   @JvmStatic
   public fun CharSequence.substring(range: IntRange): String {
      return var0.subSequence(var1.getStart(), var1.getEndInclusive() + 1).toString();
   }

   @JvmStatic
   public fun String.substring(range: IntRange): String {
      var0 = var0.substring(var1.getStart(), var1.getEndInclusive() + 1);
      return var0;
   }

   @JvmStatic
   public fun String.substringAfter(delimiter: Char, missingDelimiterValue: String = var0): String {
      val var3: Int = StringsKt.indexOf$default(var0, var1, 0, false, 6, null);
      if (var3 != -1) {
         var2 = var0.substring(var3 + 1, var0.length());
      }

      return var2;
   }

   @JvmStatic
   public fun String.substringAfter(delimiter: String, missingDelimiterValue: String = var0): String {
      val var3: Int = StringsKt.indexOf$default(var0, var1, 0, false, 6, null);
      if (var3 != -1) {
         var2 = var0.substring(var3 + var1.length(), var0.length());
      }

      return var2;
   }

   @JvmStatic
   public fun String.substringAfterLast(delimiter: Char, missingDelimiterValue: String = var0): String {
      val var3: Int = StringsKt.lastIndexOf$default(var0, var1, 0, false, 6, null);
      if (var3 != -1) {
         var2 = var0.substring(var3 + 1, var0.length());
      }

      return var2;
   }

   @JvmStatic
   public fun String.substringAfterLast(delimiter: String, missingDelimiterValue: String = var0): String {
      val var3: Int = StringsKt.lastIndexOf$default(var0, var1, 0, false, 6, null);
      if (var3 != -1) {
         var2 = var0.substring(var3 + var1.length(), var0.length());
      }

      return var2;
   }

   @JvmStatic
   public fun String.substringBefore(delimiter: Char, missingDelimiterValue: String = var0): String {
      val var3: Int = StringsKt.indexOf$default(var0, var1, 0, false, 6, null);
      if (var3 != -1) {
         var2 = var0.substring(0, var3);
      }

      return var2;
   }

   @JvmStatic
   public fun String.substringBefore(delimiter: String, missingDelimiterValue: String = var0): String {
      val var3: Int = StringsKt.indexOf$default(var0, var1, 0, false, 6, null);
      if (var3 != -1) {
         var2 = var0.substring(0, var3);
      }

      return var2;
   }

   @JvmStatic
   public fun String.substringBeforeLast(delimiter: Char, missingDelimiterValue: String = var0): String {
      val var3: Int = StringsKt.lastIndexOf$default(var0, var1, 0, false, 6, null);
      if (var3 != -1) {
         var2 = var0.substring(0, var3);
      }

      return var2;
   }

   @JvmStatic
   public fun String.substringBeforeLast(delimiter: String, missingDelimiterValue: String = var0): String {
      val var3: Int = StringsKt.lastIndexOf$default(var0, var1, 0, false, 6, null);
      if (var3 != -1) {
         var2 = var0.substring(0, var3);
      }

      return var2;
   }

   @JvmStatic
   public fun String.toBooleanStrict(): Boolean {
      val var1: Boolean;
      if (var0 == "true") {
         var1 = true;
      } else {
         if (!(var0 == "false")) {
            val var2: StringBuilder = new StringBuilder("The string doesn't represent a boolean value: ");
            var2.append(var0);
            throw new IllegalArgumentException(var2.toString());
         }

         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public fun String.toBooleanStrictOrNull(): Boolean? {
      val var1: java.lang.Boolean;
      if (var0 == "true") {
         var1 = true;
      } else if (var0 == "false") {
         var1 = false;
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public fun CharSequence.trim(): CharSequence {
      var var2: Int = var0.length() - 1;
      var var1: Int = 0;
      var var3: Boolean = false;

      while (var1 <= var2) {
         val var4: Int;
         if (!var3) {
            var4 = var1;
         } else {
            var4 = var2;
         }

         val var5: Boolean = CharsKt.isWhitespace(var0.charAt(var4));
         if (!var3) {
            if (!var5) {
               var3 = true;
            } else {
               var1++;
            }
         } else {
            if (!var5) {
               break;
            }

            var2--;
         }
      }

      return var0.subSequence(var1, var2 + 1);
   }

   @JvmStatic
   public inline fun CharSequence.trim(predicate: (Char) -> Boolean): CharSequence {
      var var3: Int = var0.length() - 1;
      var var2: Int = 0;
      var var4: Boolean = false;

      while (var2 <= var3) {
         val var5: Int;
         if (!var4) {
            var5 = var2;
         } else {
            var5 = var3;
         }

         val var6: Boolean = var1.invoke(var0.charAt(var5)) as java.lang.Boolean;
         if (!var4) {
            if (!var6) {
               var4 = true;
            } else {
               var2++;
            }
         } else {
            if (!var6) {
               break;
            }

            var3--;
         }
      }

      return var0.subSequence(var2, var3 + 1);
   }

   @JvmStatic
   public fun CharSequence.trim(chars: CharArray): CharSequence {
      var var3: Int = var0.length() - 1;
      var var2: Int = 0;
      var var4: Boolean = false;

      while (var2 <= var3) {
         val var5: Int;
         if (!var4) {
            var5 = var2;
         } else {
            var5 = var3;
         }

         val var6: Boolean = ArraysKt.contains(var1, var0.charAt(var5));
         if (!var4) {
            if (!var6) {
               var4 = true;
            } else {
               var2++;
            }
         } else {
            if (!var6) {
               break;
            }

            var3--;
         }
      }

      return var0.subSequence(var2, var3 + 1);
   }

   @JvmStatic
   public inline fun String.trim(): String {
      return StringsKt.trim(var0).toString();
   }

   @JvmStatic
   public inline fun String.trim(predicate: (Char) -> Boolean): String {
      val var7: java.lang.CharSequence = var0;
      var var3: Int = var0.length() - 1;
      var var2: Int = 0;
      var var4: Boolean = false;

      while (var2 <= var3) {
         val var5: Int;
         if (!var4) {
            var5 = var2;
         } else {
            var5 = var3;
         }

         val var6: Boolean = var1.invoke(var7.charAt(var5)) as java.lang.Boolean;
         if (!var4) {
            if (!var6) {
               var4 = true;
            } else {
               var2++;
            }
         } else {
            if (!var6) {
               break;
            }

            var3--;
         }
      }

      return var7.subSequence(var2, var3 + 1).toString();
   }

   @JvmStatic
   public fun String.trim(chars: CharArray): String {
      val var7: java.lang.CharSequence = var0;
      var var3: Int = var0.length() - 1;
      var var2: Int = 0;
      var var4: Boolean = false;

      while (var2 <= var3) {
         val var5: Int;
         if (!var4) {
            var5 = var2;
         } else {
            var5 = var3;
         }

         val var6: Boolean = ArraysKt.contains(var1, var7.charAt(var5));
         if (!var4) {
            if (!var6) {
               var4 = true;
            } else {
               var2++;
            }
         } else {
            if (!var6) {
               break;
            }

            var3--;
         }
      }

      return var7.subSequence(var2, var3 + 1).toString();
   }

   @JvmStatic
   public fun CharSequence.trimEnd(): CharSequence {
      var var1: Int = var0.length() - 1;
      if (var1 >= 0) {
         while (true) {
            val var2: Int = var1 - 1;
            if (!CharsKt.isWhitespace(var0.charAt(var1))) {
               return var0.subSequence(0, var1 + 1);
            }

            if (var2 < 0) {
               break;
            }

            var1 = var2;
         }
      }

      return "";
   }

   @JvmStatic
   public inline fun CharSequence.trimEnd(predicate: (Char) -> Boolean): CharSequence {
      var var2: Int = var0.length() - 1;
      if (var2 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (!var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
               return var0.subSequence(0, var2 + 1);
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return "";
   }

   @JvmStatic
   public fun CharSequence.trimEnd(chars: CharArray): CharSequence {
      var var2: Int = var0.length() - 1;
      if (var2 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (!ArraysKt.contains(var1, var0.charAt(var2))) {
               return var0.subSequence(0, var2 + 1);
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return "";
   }

   @JvmStatic
   public inline fun String.trimEnd(): String {
      return StringsKt.trimEnd(var0).toString();
   }

   @JvmStatic
   public inline fun String.trimEnd(predicate: (Char) -> Boolean): String {
      val var4: java.lang.CharSequence = var0;
      var var2: Int = var0.length() - 1;
      if (var2 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (!var1.invoke(var4.charAt(var2)) as java.lang.Boolean) {
               return var4.subSequence(0, var2 + 1).toString();
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return "".toString();
   }

   @JvmStatic
   public fun String.trimEnd(chars: CharArray): String {
      val var4: java.lang.CharSequence = var0;
      var var2: Int = var0.length() - 1;
      if (var2 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (!ArraysKt.contains(var1, var4.charAt(var2))) {
               return var4.subSequence(0, var2 + 1).toString();
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return "".toString();
   }

   @JvmStatic
   public fun CharSequence.trimStart(): CharSequence {
      val var2: Int = var0.length();
      var var1: Int = 0;

      while (true) {
         if (var1 >= var2) {
            var0 = "";
            break;
         }

         if (!CharsKt.isWhitespace(var0.charAt(var1))) {
            var0 = var0.subSequence(var1, var0.length());
            break;
         }

         var1++;
      }

      return var0;
   }

   @JvmStatic
   public inline fun CharSequence.trimStart(predicate: (Char) -> Boolean): CharSequence {
      val var3: Int = var0.length();

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(var0.charAt(var2)) as java.lang.Boolean) {
            return var0.subSequence(var2, var0.length());
         }
      }

      return "";
   }

   @JvmStatic
   public fun CharSequence.trimStart(chars: CharArray): CharSequence {
      val var3: Int = var0.length();
      var var2: Int = 0;

      while (true) {
         if (var2 >= var3) {
            var0 = "";
            break;
         }

         if (!ArraysKt.contains(var1, var0.charAt(var2))) {
            var0 = var0.subSequence(var2, var0.length());
            break;
         }

         var2++;
      }

      return var0;
   }

   @JvmStatic
   public inline fun String.trimStart(): String {
      return StringsKt.trimStart(var0).toString();
   }

   @JvmStatic
   public inline fun String.trimStart(predicate: (Char) -> Boolean): String {
      val var4: java.lang.CharSequence = var0;
      val var3: Int = var0.length();
      var var2: Int = 0;

      while (true) {
         if (var2 >= var3) {
            var5 = "";
            break;
         }

         if (!var1.invoke(var4.charAt(var2)) as java.lang.Boolean) {
            var5 = var4.subSequence(var2, var4.length());
            break;
         }

         var2++;
      }

      return var5.toString();
   }

   @JvmStatic
   public fun String.trimStart(chars: CharArray): String {
      val var4: java.lang.CharSequence = var0;
      val var3: Int = var0.length();
      var var2: Int = 0;

      while (true) {
         if (var2 >= var3) {
            var5 = "";
            break;
         }

         if (!ArraysKt.contains(var1, var4.charAt(var2))) {
            var5 = var4.subSequence(var2, var4.length());
            break;
         }

         var2++;
      }

      return var5.toString();
   }
}
