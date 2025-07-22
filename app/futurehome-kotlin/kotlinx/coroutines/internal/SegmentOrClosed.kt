package kotlinx.coroutines.internal

@JvmInline
internal inline class SegmentOrClosed<S extends Segment<S>> {
   public final val isClosed: Boolean
      public final get() {
         val var1: Boolean;
         if (var0 === ConcurrentLinkedListKt.access$getCLOSED$p()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public final val segment: Any
      public final get() {
         if (var0 != ConcurrentLinkedListKt.access$getCLOSED$p()) {
            return (S)var0;
         } else {
            throw new IllegalStateException("Does not contain segment".toString());
         }
      }


   private final val value: Any?

   @JvmStatic
   fun <S extends Segment<S>> `constructor-impl`(var0: Any): Any {
      return var0;
   }

   @JvmStatic
   public open operator fun equals(other: Any?): Boolean {
      if (var1 !is SegmentOrClosed) {
         return false;
      } else {
         return var0 == (var1 as SegmentOrClosed).unbox-impl();
      }
   }

   @JvmStatic
   fun `equals-impl0`(var0: Any, var1: Any): Boolean {
      return var0 == var1;
   }

   @JvmStatic
   public open fun hashCode(): Int {
      val var1: Int;
      if (var0 == null) {
         var1 = 0;
      } else {
         var1 = var0.hashCode();
      }

      return var1;
   }

   @JvmStatic
   public open fun toString(): String {
      val var1: StringBuilder = new StringBuilder("SegmentOrClosed(value=");
      var1.append(var0);
      var1.append(')');
      return var1.toString();
   }

   override fun equals(var1: Any): Boolean {
      return equals-impl(this.value, var1);
   }

   override fun hashCode(): Int {
      return hashCode-impl(this.value);
   }

   override fun toString(): java.lang.String {
      return toString-impl(this.value);
   }
}
