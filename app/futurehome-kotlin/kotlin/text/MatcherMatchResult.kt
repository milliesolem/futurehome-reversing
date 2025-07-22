package kotlin.text

import java.util.regex.Matcher
import kotlin.internal.PlatformImplementationsKt

private class MatcherMatchResult(matcher: Matcher, input: CharSequence) : MatchResult {
   private final val matcher: Matcher
   private final val input: CharSequence

   private final val matchResult: java.util.regex.MatchResult
      private final get() {
         return this.matcher;
      }


   public open val range: IntRange
      public open get() {
         return RegexKt.access$range(this.getMatchResult());
      }


   public open val value: String
      public open get() {
         val var1: java.lang.String = this.getMatchResult().group();
         return var1;
      }


   public open val groups: MatchGroupCollection
   private final var groupValues_: List<String>?

   public open val groupValues: List<String>
      public open get() {
         if (this.groupValues_ == null) {
            this.groupValues_ = new AbstractList<java.lang.String>(this) {
               final MatcherMatchResult this$0;

               {
                  this.this$0 = var1;
               }

               public java.lang.String get(int var1) {
                  val var3: java.lang.String = MatcherMatchResult.access$getMatchResult(this.this$0).group(var1);
                  var var2: java.lang.String = var3;
                  if (var3 == null) {
                     var2 = "";
                  }

                  return var2;
               }

               @Override
               public int getSize() {
                  return MatcherMatchResult.access$getMatchResult(this.this$0).groupCount() + 1;
               }
            };
         }

         val var1: java.util.List = this.groupValues_;
         return var1;
      }


   init {
      this.matcher = var1;
      this.input = var2;
      this.groups = (
         new MatchNamedGroupCollection(this) {
            final MatcherMatchResult this$0;

            {
               this.this$0 = var1;
            }

            private static final MatchGroup iterator$lambda$0(<unrepresentable> var0, int var1) {
               return var0.get(var1);
            }

            @Override
            public MatchGroup get(int var1) {
               val var2: IntRange = RegexKt.access$range(MatcherMatchResult.access$getMatchResult(this.this$0), var1);
               val var4: MatchGroup;
               if (var2.getStart() >= 0) {
                  val var3: java.lang.String = MatcherMatchResult.access$getMatchResult(this.this$0).group(var1);
                  var4 = new MatchGroup(var3, var2);
               } else {
                  var4 = null;
               }

               return var4;
            }

            @Override
            public MatchGroup get(java.lang.String var1) {
               return PlatformImplementationsKt.IMPLEMENTATIONS.getMatchResultNamedGroup(MatcherMatchResult.access$getMatchResult(this.this$0), var1);
            }

            @Override
            public int getSize() {
               return MatcherMatchResult.access$getMatchResult(this.this$0).groupCount() + 1;
            }

            @Override
            public boolean isEmpty() {
               return false;
            }

            @Override
            public java.util.Iterator<MatchGroup> iterator() {
               return SequencesKt.map(
                     CollectionsKt.asSequence(CollectionsKt.getIndices(this as MutableCollection<*>)),
                     new MatcherMatchResult$groups$1$$ExternalSyntheticLambda0(this)
                  )
                  .iterator();
            }
         }
      ) as MatchGroupCollection;
   }

   override fun getDestructured(): MatchResult.Destructured {
      return MatchResult.DefaultImpls.getDestructured(this);
   }

   public override fun next(): MatchResult? {
      val var2: Int = this.getMatchResult().end();
      var var1: Byte;
      if (this.getMatchResult().end() == this.getMatchResult().start()) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      var1 = var2 + var1;
      val var5: MatchResult;
      if (var2 + var1 <= this.input.length()) {
         val var3: Matcher = this.matcher.pattern().matcher(this.input);
         var5 = RegexKt.access$findNext(var3, var1, this.input);
      } else {
         var5 = null;
      }

      return var5;
   }
}
