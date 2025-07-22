package kotlin.sequences

import java.util.ArrayList
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.Boxing
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.random.Random

internal class SequencesKt__SequencesKt : SequencesKt__SequencesJVMKt {
   open fun SequencesKt__SequencesKt() {
   }

   @JvmStatic
   public inline fun <T> Sequence(crossinline iterator: () -> Iterator<T>): Sequence<T> {
      return new Sequence<T>(var0) {
         final Function0<java.util.Iterator<T>> $iterator;

         {
            this.$iterator = var1;
         }

         @Override
         public java.util.Iterator<T> iterator() {
            return this.$iterator.invoke() as MutableIterator<T>;
         }
      };
   }

   @JvmStatic
   public fun <T> Iterator<T>.asSequence(): Sequence<T> {
      return SequencesKt.constrainOnce(new Sequence<T>(var0) {
         final java.util.Iterator $this_asSequence$inlined;

         {
            this.$this_asSequence$inlined = var1;
         }

         @Override
         public java.util.Iterator<T> iterator() {
            return this.$this_asSequence$inlined;
         }
      });
   }

   @JvmStatic
   public fun <T> Sequence<T>.constrainOnce(): Sequence<T> {
      if (var0 !is ConstrainedOnceSequence) {
         var0 = new ConstrainedOnceSequence(var0);
      }

      return var0;
   }

   @JvmStatic
   public fun <T> emptySequence(): Sequence<T> {
      return EmptySequence.INSTANCE;
   }

   @JvmStatic
   internal fun <T, C, R> flatMapIndexed(source: Sequence<T>, transform: (Int, T) -> C, iterator: (C) -> Iterator<R>): Sequence<R> {
      return SequencesKt.sequence((new Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object>(var0, var1, var2, null) {
         final Function1<C, java.util.Iterator<R>> $iterator;
         final Sequence<T> $source;
         final Function2<Integer, T, C> $transform;
         int I$0;
         private Object L$0;
         Object L$1;
         int label;

         {
            super(2, var4);
            this.$source = var1;
            this.$transform = var2x;
            this.$iterator = var3x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$source, this.$transform, this.$iterator, var2);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(SequenceScope<? super R> var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var4: java.util.Iterator;
            var var9: Int;
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var9 = this.I$0;
               var4 = this.L$1 as java.util.Iterator;
               val var5: SequenceScope = this.L$0 as SequenceScope;
               ResultKt.throwOnFailure(var1);
               var1 = var5;
            } else {
               ResultKt.throwOnFailure(var1);
               var1 = this.L$0 as SequenceScope;
               var4 = this.$source.iterator();
               var9 = 0;
            }

            while (var4.hasNext()) {
               var var10: Any = var4.next();
               val var3x: Int = var9 + 1;
               if (var9 < 0) {
                  CollectionsKt.throwIndexOverflow();
               }

               var10 = this.$iterator.invoke(this.$transform.invoke(Boxing.boxInt(var9), var10)) as java.util.Iterator;
               val var13: Continuation = this;
               this.L$0 = var1;
               this.L$1 = var4;
               this.I$0 = var3x;
               this.label = 1;
               if (var1.yieldAll((java.util.Iterator)var10, var13) === var6) {
                  return var6;
               }

               var9 = var3x;
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @JvmStatic
   public fun <T> Sequence<Sequence<T>>.flatten(): Sequence<T> {
      return flatten$SequencesKt__SequencesKt(var0, new SequencesKt__SequencesKt$$ExternalSyntheticLambda2());
   }

   @JvmStatic
   private fun <T, R> Sequence<T>.flatten(iterator: (T) -> Iterator<R>): Sequence<R> {
      return (Sequence<R>)(if (var0 is TransformingSequence)
         (var0 as TransformingSequence).flatten$kotlin_stdlib(var1)
         else
         new FlatteningSequence(var0, new SequencesKt__SequencesKt$$ExternalSyntheticLambda4(), var1));
   }

   @JvmStatic
   fun `flatten$lambda$1$SequencesKt__SequencesKt`(var0: Sequence): java.util.Iterator {
      return var0.iterator();
   }

   @JvmStatic
   fun `flatten$lambda$2$SequencesKt__SequencesKt`(var0: java.lang.Iterable): java.util.Iterator {
      return var0.iterator();
   }

   @JvmStatic
   fun `flatten$lambda$3$SequencesKt__SequencesKt`(var0: Any): Any {
      return var0;
   }

   @JvmStatic
   public fun <T> Sequence<Iterable<T>>.flatten(): Sequence<T> {
      return flatten$SequencesKt__SequencesKt(var0, new SequencesKt__SequencesKt$$ExternalSyntheticLambda3());
   }

   @JvmStatic
   public fun <T : Any> generateSequence(seed: T?, nextFunction: (T) -> T?): Sequence<T> {
      if (var0 == null) {
         var0 = EmptySequence.INSTANCE;
      } else {
         var0 = new GeneratorSequence(new SequencesKt__SequencesKt$$ExternalSyntheticLambda0(var0), var1);
      }

      return var0;
   }

   @JvmStatic
   public fun <T : Any> generateSequence(nextFunction: () -> T?): Sequence<T> {
      return SequencesKt.constrainOnce(new GeneratorSequence(var0, new SequencesKt__SequencesKt$$ExternalSyntheticLambda1(var0)));
   }

   @JvmStatic
   public fun <T : Any> generateSequence(seedFunction: () -> T?, nextFunction: (T) -> T?): Sequence<T> {
      return new GeneratorSequence(var0, var1);
   }

   @JvmStatic
   fun `generateSequence$lambda$4$SequencesKt__SequencesKt`(var0: Function0, var1: Any): Any {
      return var0.invoke();
   }

   @JvmStatic
   fun `generateSequence$lambda$5$SequencesKt__SequencesKt`(var0: Any): Any {
      return var0;
   }

   @JvmStatic
   public fun <T> Sequence<T>.ifEmpty(defaultValue: () -> Sequence<T>): Sequence<T> {
      return SequencesKt.sequence((new Function2<SequenceScope<? super T>, Continuation<? super Unit>, Object>(var0, var1, null) {
         final Function0<Sequence<T>> $defaultValue;
         final Sequence<T> $this_ifEmpty;
         private Object L$0;
         int label;

         {
            super(2, var3x);
            this.$this_ifEmpty = var1;
            this.$defaultValue = var2x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$this_ifEmpty, this.$defaultValue, var2);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(SequenceScope<? super T> var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var3x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
               if (this.label != 1 && this.label != 2) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               ResultKt.throwOnFailure(var1);
            } else {
               ResultKt.throwOnFailure(var1);
               var1 = this.L$0 as SequenceScope;
               val var5: java.util.Iterator = this.$this_ifEmpty.iterator();
               if (var5.hasNext()) {
                  val var4: Continuation = this;
                  this.label = 1;
                  if (var1.yieldAll(var5, var4) === var3x) {
                     return var3x;
                  }
               } else {
                  val var8: Sequence = this.$defaultValue.invoke() as Sequence;
                  val var7: Continuation = this;
                  this.label = 2;
                  if (var1.yieldAll(var8, var7) === var3x) {
                     return var3x;
                  }
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @JvmStatic
   public inline fun <T> Sequence<T>?.orEmpty(): Sequence<T> {
      var var1: Sequence = var0;
      if (var0 == null) {
         var1 = SequencesKt.emptySequence();
      }

      return var1;
   }

   @JvmStatic
   public fun <T> sequenceOf(vararg elements: T): Sequence<T> {
      return (Sequence<T>)ArraysKt.asSequence(var0);
   }

   @JvmStatic
   public fun <T> Sequence<T>.shuffled(): Sequence<T> {
      return SequencesKt.shuffled(var0, Random.Default);
   }

   @JvmStatic
   public fun <T> Sequence<T>.shuffled(random: Random): Sequence<T> {
      return SequencesKt.sequence((new Function2<SequenceScope<? super T>, Continuation<? super Unit>, Object>(var0, var1, null) {
         final Random $random;
         final Sequence<T> $this_shuffled;
         private Object L$0;
         Object L$1;
         int label;

         {
            super(2, var3x);
            this.$this_shuffled = var1;
            this.$random = var2x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$this_shuffled, this.$random, var2);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(SequenceScope<? super T> var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var3x: java.util.List;
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var3x = this.L$1 as java.util.List;
               val var4: SequenceScope = this.L$0 as SequenceScope;
               ResultKt.throwOnFailure(var1);
               var1 = var4;
            } else {
               ResultKt.throwOnFailure(var1);
               var1 = this.L$0 as SequenceScope;
               var3x = SequencesKt.toMutableList(this.$this_shuffled);
            }

            while (!var3x.isEmpty()) {
               val var8: Int = this.$random.nextInt(var3x.size());
               var var5: Any = CollectionsKt.removeLast(var3x);
               var var9: Any = var5;
               if (var8 < var3x.size()) {
                  var9 = var3x.set(var8, var5);
               }

               var5 = this;
               this.L$0 = var1;
               this.L$1 = var3x;
               this.label = 1;
               if (var1.yield(var9, (Continuation<? super Unit>)var5) === var6) {
                  return var6;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @JvmStatic
   public fun <T, R> Sequence<Pair<T, R>>.unzip(): Pair<List<T>, List<R>> {
      val var2: ArrayList = new ArrayList();
      val var1: ArrayList = new ArrayList();

      for (Pair var3 : var0) {
         var2.add(var3.getFirst());
         var1.add(var3.getSecond());
      }

      return TuplesKt.to(var2, var1);
   }
}
