package kotlin.streams.jdk8

import j..util.Spliterator
import j..util.Spliterators
import j..util.PrimitiveIterator.OfDouble
import j..util.PrimitiveIterator.OfInt
import j..util.PrimitiveIterator.OfLong
import j..util.stream.Collectors
import j..util.stream.DoubleStream
import j..util.stream.IntStream
import j..util.stream.LongStream
import j..util.stream.Stream
import j..util.stream.StreamSupport

@JvmSynthetic
fun `$r8$lambda$jBRaOqrA5A8aZRswyILQO-7uCm8`(var0: Sequence): Spliterator {
   return asStream$lambda$4(var0);
}

public fun DoubleStream.asSequence(): Sequence<Double> {
   return new Sequence<java.lang.Double>(var0) {
      final DoubleStream $this_asSequence$inlined;

      {
         this.$this_asSequence$inlined = var1;
      }

      @Override
      public java.util.Iterator<java.lang.Double> iterator() {
         val var1: OfDouble = this.$this_asSequence$inlined.iterator();
         return var1 as MutableIterator<java.lang.Double>;
      }
   };
}

public fun IntStream.asSequence(): Sequence<Int> {
   return new Sequence<Integer>(var0) {
      final IntStream $this_asSequence$inlined;

      {
         this.$this_asSequence$inlined = var1;
      }

      @Override
      public java.util.Iterator<Integer> iterator() {
         val var1: OfInt = this.$this_asSequence$inlined.iterator();
         return var1 as MutableIterator<Int>;
      }
   };
}

public fun LongStream.asSequence(): Sequence<Long> {
   return new Sequence<java.lang.Long>(var0) {
      final LongStream $this_asSequence$inlined;

      {
         this.$this_asSequence$inlined = var1;
      }

      @Override
      public java.util.Iterator<java.lang.Long> iterator() {
         val var1: OfLong = this.$this_asSequence$inlined.iterator();
         return var1 as MutableIterator<java.lang.Long>;
      }
   };
}

public fun <T> Stream<Any>.asSequence(): Sequence<Any> {
   return new Sequence<T>(var0) {
      final Stream $this_asSequence$inlined;

      {
         this.$this_asSequence$inlined = var1;
      }

      @Override
      public java.util.Iterator<T> iterator() {
         val var1: java.util.Iterator = this.$this_asSequence$inlined.iterator();
         return var1;
      }
   };
}

public fun <T> Sequence<Any>.asStream(): Stream<Any> {
   val var1: Stream = StreamSupport.stream(new StreamsKt$$ExternalSyntheticLambda0(var0), 16, false);
   return var1;
}

fun `asStream$lambda$4`(var0: Sequence): Spliterator {
   return Spliterators.spliteratorUnknownSize(var0.iterator(), 16);
}

public fun DoubleStream.toList(): List<Double> {
   val var1: DoubleArray = var0.toArray();
   return ArraysKt.asList(var1);
}

public fun IntStream.toList(): List<Int> {
   val var1: IntArray = var0.toArray();
   return ArraysKt.asList(var1);
}

public fun LongStream.toList(): List<Long> {
   val var1: LongArray = var0.toArray();
   return ArraysKt.asList(var1);
}

public fun <T> Stream<Any>.toList(): List<Any> {
   val var1: Any = var0.collect(Collectors.toList());
   return var1 as MutableList<T>;
}
