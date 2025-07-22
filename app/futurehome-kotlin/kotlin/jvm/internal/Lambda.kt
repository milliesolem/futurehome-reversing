package kotlin.jvm.internal

import java.io.Serializable

public abstract class Lambda<R> : FunctionBase<R>, Serializable {
   public open val arity: Int

   open fun Lambda(var1: Int) {
      this.arity = var1;
   }

   public override fun toString(): String {
      val var1: java.lang.String = Reflection.renderLambdaToString(this);
      return var1;
   }
}
