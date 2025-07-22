package kotlin.coroutines.jvm.internal

import kotlin.coroutines.Continuation
import kotlin.jvm.internal.FunctionBase
import kotlin.jvm.internal.Reflection

internal abstract class RestrictedSuspendLambda : RestrictedContinuationImpl, FunctionBase<Object>, SuspendFunction {
   public open val arity: Int

   open fun RestrictedSuspendLambda(var1: Int) {
      this(var1, null);
   }

   open fun RestrictedSuspendLambda(var1: Int, var2: Continuation<Object>) {
      super(var2);
      this.arity = var1;
   }

   public override fun toString(): String {
      val var1: java.lang.String;
      if (this.getCompletion() == null) {
         var1 = Reflection.renderLambdaToString(this);
      } else {
         var1 = super.toString();
      }

      return var1;
   }
}
