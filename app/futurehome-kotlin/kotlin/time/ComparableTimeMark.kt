package kotlin.time

public interface ComparableTimeMark : TimeMark, java.lang.Comparable<ComparableTimeMark> {
   public open operator fun compareTo(other: ComparableTimeMark): Int {
   }

   public abstract override operator fun equals(other: Any?): Boolean {
   }

   public abstract override fun hashCode(): Int {
   }

   public open operator fun minus(duration: Duration): ComparableTimeMark {
   }

   public abstract operator fun minus(other: ComparableTimeMark): Duration {
   }

   public abstract operator fun plus(duration: Duration): ComparableTimeMark {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun compareTo(var0: ComparableTimeMark, var1: ComparableTimeMark): Int {
         return Duration.compareTo-LRDsOJo(var0.minus-UwyO8pc(var1), Duration.Companion.getZERO-UwyO8pc());
      }

      @JvmStatic
      fun hasNotPassedNow(var0: ComparableTimeMark): Boolean {
         return TimeMark.DefaultImpls.hasNotPassedNow(var0);
      }

      @JvmStatic
      fun hasPassedNow(var0: ComparableTimeMark): Boolean {
         return TimeMark.DefaultImpls.hasPassedNow(var0);
      }

      @JvmStatic
      fun `minus-LRDsOJo`(var0: ComparableTimeMark, var1: Long): ComparableTimeMark {
         return var0.plus-LRDsOJo(Duration.unaryMinus-UwyO8pc(var1));
      }
   }
}
