package kotlin.time

public data class TimedValue<T>(value: Any, duration: Duration) : TimedValue((T)var1, var2) {
   public final val value: Any
   public final val duration: Duration

   fun TimedValue(var1: T, var2: Long) {
      this.value = (T)var1;
      this.duration = var2;
   }

   public operator fun component1(): Any {
      return this.value;
   }

   public operator fun component2(): Duration {
      return this.duration;
   }

   public fun copy(value: Any = ..., duration: Duration = ...): TimedValue<Any> {
      return new TimedValue<>(var1, var2, null);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is TimedValue) {
         return false;
      } else {
         var1 = var1;
         if (!(this.value == var1.value)) {
            return false;
         } else {
            return Duration.equals-impl0(this.duration, var1.duration);
         }
      }
   }

   public override fun hashCode(): Int {
      val var1: Int;
      if (this.value == null) {
         var1 = 0;
      } else {
         var1 = this.value.hashCode();
      }

      return var1 * 31 + Duration.hashCode-impl(this.duration);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("TimedValue(value=");
      var1.append(this.value);
      var1.append(", duration=");
      var1.append(Duration.toString-impl(this.duration));
      var1.append(')');
      return var1.toString();
   }
}
