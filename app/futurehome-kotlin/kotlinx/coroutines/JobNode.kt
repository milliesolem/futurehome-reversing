package kotlinx.coroutines

internal abstract class JobNode : CompletionHandlerBase, DisposableHandle, Incomplete {
   public open val isActive: Boolean
      public open get() {
         return true;
      }


   public final lateinit var job: JobSupport
      internal set

   public open val list: NodeList?
      public open get() {
         return null;
      }


   public override fun dispose() {
      this.getJob().removeNode$kotlinx_coroutines_core(this);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(DebugStringsKt.getClassSimpleName(this));
      var1.append('@');
      var1.append(DebugStringsKt.getHexAddress(this));
      var1.append("[job@");
      var1.append(DebugStringsKt.getHexAddress(this.getJob()));
      var1.append(']');
      return var1.toString();
   }
}
