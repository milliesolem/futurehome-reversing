package kotlinx.coroutines

internal open class JobImpl(parent: Job?) : JobSupport(true), CompletableJob {
   internal open val handlesException: Boolean

   internal open val onCancelComplete: Boolean
      internal open get() {
         return true;
      }


   init {
      this.initParentJob(var1);
      this.handlesException = this.handlesException();
   }

   private fun handlesException(): Boolean {
      var var1: ChildHandle = this.getParentHandle$kotlinx_coroutines_core();
      val var3: ChildHandleNode;
      if (var1 is ChildHandleNode) {
         var3 = var1 as ChildHandleNode;
      } else {
         var3 = null;
      }

      if (var3 != null) {
         var var2: JobSupport = var3.getJob();
         var var4: JobSupport = var2;
         if (var2 != null) {
            do {
               if (var4.getHandlesException$kotlinx_coroutines_core()) {
                  return true;
               }

               var1 = var4.getParentHandle$kotlinx_coroutines_core();
               val var6: ChildHandleNode;
               if (var1 is ChildHandleNode) {
                  var6 = var1 as ChildHandleNode;
               } else {
                  var6 = null;
               }

               if (var6 == null) {
                  break;
               }

               var2 = var6.getJob();
               var4 = var2;
            } while (var2 != null);
         }
      }

      return false;
   }

   public override fun complete(): Boolean {
      return this.makeCompleting$kotlinx_coroutines_core(Unit.INSTANCE);
   }

   public override fun completeExceptionally(exception: Throwable): Boolean {
      return this.makeCompleting$kotlinx_coroutines_core(new CompletedExceptionally(var1, false, 2, null));
   }
}
