package kotlinx.coroutines.selects

internal class SelectClause0Impl(clauseObject: Any,
      regFunc: (Any, SelectInstance<*>, Any?) -> Unit,
      onCancellationConstructor: ((SelectInstance<*>, Any?, Any?) -> (Throwable) -> Unit)? = null
   ) :
   SelectClause0 {
   public open val clauseObject: Any
   public open val onCancellationConstructor: ((SelectInstance<*>, Any?, Any?) -> (Throwable) -> Unit)?
   public open val processResFunc: (Any, Any?, Any?) -> Any?
   public open val regFunc: (Any, SelectInstance<*>, Any?) -> Unit

   init {
      this.clauseObject = var1;
      this.regFunc = var2;
      this.onCancellationConstructor = var3;
      this.processResFunc = SelectKt.access$getDUMMY_PROCESS_RESULT_FUNCTION$p();
   }
}
