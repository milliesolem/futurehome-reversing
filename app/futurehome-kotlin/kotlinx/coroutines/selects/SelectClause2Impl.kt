package kotlinx.coroutines.selects

internal class SelectClause2Impl<P, Q>(clauseObject: Any,
      regFunc: (Any, SelectInstance<*>, Any?) -> Unit,
      processResFunc: (Any, Any?, Any?) -> Any?,
      onCancellationConstructor: ((SelectInstance<*>, Any?, Any?) -> (Throwable) -> Unit)? = null
   ) :
   SelectClause2<P, Q> {
   public open val clauseObject: Any
   public open val onCancellationConstructor: ((SelectInstance<*>, Any?, Any?) -> (Throwable) -> Unit)?
   public open val processResFunc: (Any, Any?, Any?) -> Any?
   public open val regFunc: (Any, SelectInstance<*>, Any?) -> Unit

   init {
      this.clauseObject = var1;
      this.regFunc = var2;
      this.processResFunc = var3;
      this.onCancellationConstructor = var4;
   }
}
