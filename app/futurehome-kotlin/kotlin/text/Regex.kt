package kotlin.text

import java.io.Serializable
import java.util.ArrayList
import java.util.Collections
import java.util.EnumSet
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2

public class Regex  internal constructor(nativePattern: Pattern) : Serializable {
   private final val nativePattern: Pattern

   public final val pattern: String
      public final get() {
         val var1: java.lang.String = this.nativePattern.pattern();
         return var1;
      }


   private final var _options: Set<RegexOption>?

   public final val options: Set<RegexOption>
      public final get() {
         var var2: java.util.Set = this._options;
         if (this._options == null) {
            val var1: Int = this.nativePattern.flags();
            val var4: EnumSet = EnumSet.allOf(RegexOption.class);
            CollectionsKt.retainAll(var4, new Function1<RegexOption, java.lang.Boolean>(var1) {
               final int $value;

               {
                  this.$value = var1;
               }

               public final java.lang.Boolean invoke(RegexOption var1) {
                  val var3: Boolean;
                  if ((this.$value and (var1 as FlagEnum).getMask()) == (var1 as FlagEnum).getValue()) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  return var3;
               }
            });
            var2 = Collections.unmodifiableSet(var4);
            this._options = var2;
         }

         return var2;
      }


   public constructor(pattern: String)  {
      val var2: Pattern = Pattern.compile(var1);
      this(var2);
   }

   public constructor(pattern: String, options: Set<RegexOption>)  {
      val var3: Pattern = Pattern.compile(var1, Regex.Companion.access$ensureUnicodeCase(Companion, RegexKt.access$toInt(var2)));
      this(var3);
   }

   public constructor(pattern: String, option: RegexOption)  {
      val var3: Pattern = Pattern.compile(var1, Regex.Companion.access$ensureUnicodeCase(Companion, var2.getValue()));
      this(var3);
   }

   init {
      this.nativePattern = var1;
   }

   @JvmStatic
   fun `findAll$lambda$1`(var0: Regex, var1: java.lang.CharSequence, var2: Int): MatchResult {
      return var0.find(var1, var2);
   }

   private fun writeReplace(): Any {
      val var1: java.lang.String = this.nativePattern.pattern();
      return new Regex.Serialized(var1, this.nativePattern.flags());
   }

   public fun containsMatchIn(input: CharSequence): Boolean {
      return this.nativePattern.matcher(var1).find();
   }

   public fun find(input: CharSequence, startIndex: Int = 0): MatchResult? {
      val var3: Matcher = this.nativePattern.matcher(var1);
      return RegexKt.access$findNext(var3, var2, var1);
   }

   public fun findAll(input: CharSequence, startIndex: Int = 0): Sequence<MatchResult> {
      if (var2 >= 0 && var2 <= var1.length()) {
         return SequencesKt.generateSequence(new Regex$$ExternalSyntheticLambda0(this, var1, var2), <unrepresentable>.INSTANCE);
      } else {
         val var3: StringBuilder = new StringBuilder("Start index out of bounds: ");
         var3.append(var2);
         var3.append(", input length: ");
         var3.append(var1.length());
         throw new IndexOutOfBoundsException(var3.toString());
      }
   }

   public fun matchAt(input: CharSequence, index: Int): MatchResult? {
      val var3: Matcher = this.nativePattern.matcher(var1).useAnchoringBounds(false).useTransparentBounds(true).region(var2, var1.length());
      val var4: MatcherMatchResult;
      if (var3.lookingAt()) {
         var4 = new MatcherMatchResult(var3, var1);
      } else {
         var4 = null;
      }

      return var4;
   }

   public fun matchEntire(input: CharSequence): MatchResult? {
      val var2: Matcher = this.nativePattern.matcher(var1);
      return RegexKt.access$matchEntire(var2, var1);
   }

   public infix fun matches(input: CharSequence): Boolean {
      return this.nativePattern.matcher(var1).matches();
   }

   public fun matchesAt(input: CharSequence, index: Int): Boolean {
      return this.nativePattern.matcher(var1).useAnchoringBounds(false).useTransparentBounds(true).region(var2, var1.length()).lookingAt();
   }

   public fun replace(input: CharSequence, replacement: String): String {
      val var3: java.lang.String = this.nativePattern.matcher(var1).replaceAll(var2);
      return var3;
   }

   public fun replace(input: CharSequence, transform: (MatchResult) -> CharSequence): String {
      var var3: Int = 0;
      var var6: MatchResult = find$default(this, var1, 0, 2, null);
      if (var6 == null) {
         return var1.toString();
      } else {
         val var5: Int = var1.length();
         val var8: StringBuilder = new StringBuilder(var5);

         val var4: Int;
         val var7: MatchResult;
         do {
            var8.append(var1, var3, var6.getRange().getStart());
            var8.append(var2.invoke(var6) as java.lang.CharSequence);
            var4 = var6.getRange().getEndInclusive() + 1;
            var7 = var6.next();
            if (var4 >= var5) {
               break;
            }

            var6 = var7;
            var3 = var4;
         } while (var7 != null);

         if (var4 < var5) {
            var8.append(var1, var4, var5);
         }

         val var9: java.lang.String = var8.toString();
         return var9;
      }
   }

   public fun replaceFirst(input: CharSequence, replacement: String): String {
      val var3: java.lang.String = this.nativePattern.matcher(var1).replaceFirst(var2);
      return var3;
   }

   public fun split(input: CharSequence, limit: Int = 0): List<String> {
      StringsKt.requireNonNegativeLimit(var2);
      val var5: Matcher = this.nativePattern.matcher(var1);
      if (var2 != 1 && var5.find()) {
         var var3: Int = 10;
         if (var2 > 0) {
            var3 = RangesKt.coerceAtMost(var2, 10);
         }

         val var6: ArrayList = new ArrayList(var3);
         val var4: Int = var2 - 1;
         var2 = 0;

         do {
            var6.add(var1.subSequence(var2, var5.start()).toString());
            var3 = var5.end();
            if (var4 >= 0 && var6.size() == var4) {
               break;
            }

            var2 = var3;
         } while (var5.find());

         var6.add(var1.subSequence(var3, var1.length()).toString());
         return var6;
      } else {
         return CollectionsKt.listOf(var1.toString());
      }
   }

   public fun splitToSequence(input: CharSequence, limit: Int = 0): Sequence<String> {
      StringsKt.requireNonNegativeLimit(var2);
      return SequencesKt.sequence((new Function2<SequenceScope<? super java.lang.String>, Continuation<? super Unit>, Object>(this, var1, var2, null) {
         final java.lang.CharSequence $input;
         final int $limit;
         int I$0;
         private Object L$0;
         Object L$1;
         int label;
         final Regex this$0;

         {
            super(2, var4);
            this.this$0 = var1;
            this.$input = var2x;
            this.$limit = var3x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.this$0, this.$input, this.$limit, var2);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(SequenceScope<? super java.lang.String> var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         // $VF: Irreducible bytecode was duplicated to produce valid code
         @Override
         public final Object invokeSuspend(Object var1) {
            val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            var var7: Matcher;
            val var13: Int;
            if (this.label != 0) {
               if (this.label == 1) {
                  ResultKt.throwOnFailure(var1);
                  return Unit.INSTANCE;
               }

               if (this.label != 2) {
                  if (this.label != 3) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  ResultKt.throwOnFailure(var1);
                  return Unit.INSTANCE;
               }

               var13 = this.I$0;
               var7 = this.L$1 as Matcher;
               val var5: SequenceScope = this.L$0 as SequenceScope;
               ResultKt.throwOnFailure(var1);
               var1 = var5;
            } else {
               ResultKt.throwOnFailure(var1);
               val var6: SequenceScope = this.L$0 as SequenceScope;
               val var14: Matcher = Regex.access$getNativePattern$p(this.this$0).matcher(this.$input);
               if (this.$limit == 1 || !var14.find()) {
                  val var12: java.lang.String = this.$input.toString();
                  val var17: Continuation = this;
                  this.label = 1;
                  if (var6.yield(var12, var17) === var8) {
                     return var8;
                  }

                  return Unit.INSTANCE;
               }

               var13 = 0;
               val var9: java.lang.String = this.$input.subSequence(0, var14.start()).toString();
               val var10: Continuation = this;
               this.L$0 = var6;
               this.L$1 = var14;
               this.I$0 = 0;
               this.label = 2;
               var1 = var6;
               var7 = var14;
               if (var6.yield(var9, var10) === var8) {
                  return var8;
               }
            }

            while (true) {
               val var4: Int = var7.end();
               if (++var13 != this.$limit - 1) {
                  val var18: SequenceScope = var1;
                  if (var7.find()) {
                     val var20: java.lang.String = this.$input.subSequence(var4, var7.start()).toString();
                     val var21: Continuation = this;
                     this.L$0 = var1;
                     this.L$1 = var7;
                     this.I$0 = var13;
                     this.label = 2;
                     var1 = var1;
                     var7 = var7;
                     if (var18.yield(var20, var21) === var8) {
                        break;
                     }
                     continue;
                  }
               }

               val var19: java.lang.String = this.$input.subSequence(var4, this.$input.length()).toString();
               val var16: Continuation = this;
               this.L$0 = null;
               this.L$1 = null;
               this.label = 3;
               if (var1.yield(var19, var16) === var8) {
                  return var8;
               }

               return Unit.INSTANCE;
            }

            return var8;
         }
      }) as (SequenceScope<? super java.lang.String>?, Continuation<? super Unit>?) -> Any);
   }

   public fun toPattern(): Pattern {
      return this.nativePattern;
   }

   public override fun toString(): String {
      val var1: java.lang.String = this.nativePattern.toString();
      return var1;
   }

   public companion object {
      private fun ensureUnicodeCase(flags: Int): Int {
         var var2: Int = var1;
         if ((var1 and 2) != 0) {
            var2 = var1 or 64;
         }

         return var2;
      }

      public fun escape(literal: String): String {
         var1 = Pattern.quote(var1);
         return var1;
      }

      public fun escapeReplacement(literal: String): String {
         var1 = Matcher.quoteReplacement(var1);
         return var1;
      }

      public fun fromLiteral(literal: String): Regex {
         return new Regex(var1, RegexOption.LITERAL);
      }
   }

   private class Serialized(pattern: String, flags: Int) : Serializable {
      public final val pattern: String
      public final val flags: Int

      init {
         this.pattern = var1;
         this.flags = var2;
      }

      private fun readResolve(): Any {
         val var1: Pattern = Pattern.compile(this.pattern, this.flags);
         return new Regex(var1);
      }

      public companion object {
         private const val serialVersionUID: Long
      }
   }
}
