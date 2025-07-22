package kotlinx.coroutines.selects

public sealed interface SelectClause {
   public val clauseObject: Any
   public val onCancellationConstructor: ((SelectInstance<*>, Any?, Any?) -> (Throwable) -> Unit)?
   public val processResFunc: (Any, Any?, Any?) -> Any?
   public val regFunc: (Any, SelectInstance<*>, Any?) -> Unit
}
