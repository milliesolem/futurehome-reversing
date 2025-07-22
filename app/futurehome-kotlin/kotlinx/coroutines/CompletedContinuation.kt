package kotlinx.coroutines

private data class CompletedContinuation(result: Any?,
   cancelHandler: CancelHandler? = null,
   onCancellation: ((Throwable) -> Unit)? = null,
   idempotentResume: Any? = null,
   cancelCause: Throwable? = null
) {
   public final val cancelCause: Throwable?
   public final val cancelHandler: CancelHandler?

   public final val cancelled: Boolean
      public final get() {
         val var1: Boolean;
         if (this.cancelCause != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public final val idempotentResume: Any?
   public final val onCancellation: ((Throwable) -> Unit)?
   public final val result: Any?

   init {
      this.result = var1;
      this.cancelHandler = var2;
      this.onCancellation = var3;
      this.idempotentResume = var4;
      this.cancelCause = var5;
   }

   public operator fun component1(): Any? {
      return this.result;
   }

   public operator fun component2(): CancelHandler? {
      return this.cancelHandler;
   }

   public operator fun component3(): ((Throwable) -> Unit)? {
      return this.onCancellation;
   }

   public operator fun component4(): Any? {
      return this.idempotentResume;
   }

   public operator fun component5(): Throwable? {
      return this.cancelCause;
   }

   public fun copy(
      result: Any? = var0.result,
      cancelHandler: CancelHandler? = var0.cancelHandler,
      onCancellation: ((Throwable) -> Unit)? = var0.onCancellation,
      idempotentResume: Any? = var0.idempotentResume,
      cancelCause: Throwable? = var0.cancelCause
   ): CompletedContinuation {
      return new CompletedContinuation(var1, var2, var3, var4, var5);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is CompletedContinuation) {
         return false;
      } else {
         var1 = var1;
         if (!(this.result == var1.result)) {
            return false;
         } else if (!(this.cancelHandler == var1.cancelHandler)) {
            return false;
         } else if (!(this.onCancellation == var1.onCancellation)) {
            return false;
         } else if (!(this.idempotentResume == var1.idempotentResume)) {
            return false;
         } else {
            return this.cancelCause == var1.cancelCause;
         }
      }
   }

   public override fun hashCode(): Int {
      var var5: Int = 0;
      val var1: Int;
      if (this.result == null) {
         var1 = 0;
      } else {
         var1 = this.result.hashCode();
      }

      val var2: Int;
      if (this.cancelHandler == null) {
         var2 = 0;
      } else {
         var2 = this.cancelHandler.hashCode();
      }

      val var3: Int;
      if (this.onCancellation == null) {
         var3 = 0;
      } else {
         var3 = this.onCancellation.hashCode();
      }

      val var4: Int;
      if (this.idempotentResume == null) {
         var4 = 0;
      } else {
         var4 = this.idempotentResume.hashCode();
      }

      if (this.cancelCause != null) {
         var5 = this.cancelCause.hashCode();
      }

      return (((var1 * 31 + var2) * 31 + var3) * 31 + var4) * 31 + var5;
   }

   public fun invokeHandlers(cont: CancellableContinuationImpl<*>, cause: Throwable) {
      if (this.cancelHandler != null) {
         var1.callCancelHandler(this.cancelHandler, var2);
      }

      if (this.onCancellation != null) {
         var1.callOnCancellation(this.onCancellation, var2);
      }
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("CompletedContinuation(result=");
      var1.append(this.result);
      var1.append(", cancelHandler=");
      var1.append(this.cancelHandler);
      var1.append(", onCancellation=");
      var1.append(this.onCancellation);
      var1.append(", idempotentResume=");
      var1.append(this.idempotentResume);
      var1.append(", cancelCause=");
      var1.append(this.cancelCause);
      var1.append(')');
      return var1.toString();
   }
}
