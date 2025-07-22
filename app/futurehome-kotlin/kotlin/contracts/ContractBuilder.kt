package kotlin.contracts

public interface ContractBuilder {
   public abstract fun <R> callsInPlace(lambda: () -> R, kind: InvocationKind = ...): CallsInPlace {
   }

   public abstract fun returns(): Returns {
   }

   public abstract fun returns(value: Any?): Returns {
   }

   public abstract fun returnsNotNull(): ReturnsNotNull {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls
}
