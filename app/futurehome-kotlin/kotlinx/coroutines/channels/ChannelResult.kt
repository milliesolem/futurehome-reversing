package kotlinx.coroutines.channels

@JvmInline
public inline class ChannelResult<T> {
   internal final val holder: Any?

   public final val isClosed: Boolean
      public final get() {
         return var0 is ChannelResult.Closed;
      }


   public final val isFailure: Boolean
      public final get() {
         return var0 is ChannelResult.Failed;
      }


   public final val isSuccess: Boolean
      public final get() {
         return var0 is ChannelResult.Failed xor true;
      }


   @JvmStatic
   fun <T> `constructor-impl`(var0: Any): Any {
      return var0;
   }

   @JvmStatic
   public open operator fun equals(other: Any?): Boolean {
      if (var1 !is ChannelResult) {
         return false;
      } else {
         return var0 == (var1 as ChannelResult).unbox-impl();
      }
   }

   @JvmStatic
   fun `equals-impl0`(var0: Any, var1: Any): Boolean {
      return var0 == var1;
   }

   @JvmStatic
   public fun exceptionOrNull(): Throwable? {
      val var1: Boolean = var0 is ChannelResult.Closed;
      var var2: java.lang.Throwable = null;
      if (var1) {
         var0 = var0;
      } else {
         var0 = null;
      }

      if (var0 != null) {
         var2 = var0.cause;
      }

      return var2;
   }

   @JvmStatic
   public fun getOrNull(): Any? {
      if (var0 is ChannelResult.Failed) {
         var0 = null;
      }

      return (T)var0;
   }

   @JvmStatic
   public fun getOrThrow(): Any {
      if (var0 !is ChannelResult.Failed) {
         return (T)var0;
      } else {
         if (var0 is ChannelResult.Closed) {
            val var1: ChannelResult.Closed = var0 as ChannelResult.Closed;
            if ((var0 as ChannelResult.Closed).cause != null) {
               throw var1.cause;
            }
         }

         val var2: StringBuilder = new StringBuilder("Trying to call 'getOrThrow' on a failed channel result: ");
         var2.append(var0);
         throw new IllegalStateException(var2.toString().toString());
      }
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
      if (var0 is ChannelResult.Closed) {
         var0 = (var0 as ChannelResult.Closed).toString();
      } else {
         val var1: StringBuilder = new StringBuilder("Value(");
         var1.append((Object)var0);
         var1.append(')');
         var0 = var1.toString();
      }

      return var0;
   }

   override fun equals(var1: Any): Boolean {
      return equals-impl(this.holder, var1);
   }

   override fun hashCode(): Int {
      return hashCode-impl(this.holder);
   }

   override fun toString(): java.lang.String {
      return toString-impl(this.holder);
   }

   internal class Closed(cause: Throwable?) : ChannelResult.Failed {
      public final val cause: Throwable?

      init {
         this.cause = var1;
      }

      public override operator fun equals(other: Any?): Boolean {
         val var2: Boolean;
         if (var1 is ChannelResult.Closed && this.cause == (var1 as ChannelResult.Closed).cause) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public override fun hashCode(): Int {
         val var1: Int;
         if (this.cause != null) {
            var1 = this.cause.hashCode();
         } else {
            var1 = 0;
         }

         return var1;
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder("Closed(");
         var1.append(this.cause);
         var1.append(')');
         return var1.toString();
      }
   }

   public companion object {
      private final val failed: kotlinx.coroutines.channels.ChannelResult.Failed

      public fun <E> closed(cause: Throwable?): ChannelResult<E> {
         return ChannelResult.constructor-impl(new ChannelResult.Closed(var1));
      }

      public fun <E> failure(): ChannelResult<E> {
         return ChannelResult.constructor-impl(ChannelResult.access$getFailed$cp());
      }

      public fun <E> success(value: E): ChannelResult<E> {
         return ChannelResult.constructor-impl(var1);
      }
   }

   internal open class Failed {
      public override fun toString(): String {
         return "Failed";
      }
   }
}
