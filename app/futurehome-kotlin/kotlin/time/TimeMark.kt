package kotlin.time

public interface TimeMark {
   public abstract fun elapsedNow(): Duration {
   }

   public open fun hasNotPassedNow(): Boolean {
   }

   public open fun hasPassedNow(): Boolean {
   }

   public open operator fun minus(duration: Duration): TimeMark {
   }

   public open operator fun plus(duration: Duration): TimeMark {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun hasNotPassedNow(var0: TimeMark): Boolean {
         return Duration.isNegative-impl(var0.elapsedNow-UwyO8pc());
      }

      @JvmStatic
      fun hasPassedNow(var0: TimeMark): Boolean {
         return Duration.isNegative-impl(var0.elapsedNow-UwyO8pc()) xor true;
      }

      @JvmStatic
      fun `minus-LRDsOJo`(var0: TimeMark, var1: Long): TimeMark {
         return var0.plus-LRDsOJo(Duration.unaryMinus-UwyO8pc(var1));
      }

      @JvmStatic
      fun `plus-LRDsOJo`(var0: TimeMark, var1: Long): TimeMark {
         return new AdjustedTimeMark(var0, var1, null);
      }
   }
}
