package kotlinx.coroutines

import java.util.concurrent.CancellationException

internal class JobCancellationException(message: String, cause: Throwable?, job: Job) : CancellationException(var1), CopyableThrowable<JobCancellationException> {
   internal final val job: Job

   init {
      this.job = var3;
      if (var2 != null) {
         this.initCause(var2);
      }
   }

   public open fun createCopy(): JobCancellationException? {
      if (DebugKt.getDEBUG()) {
         val var1: java.lang.String = this.getMessage();
         return new JobCancellationException(var1, this, this.job);
      } else {
         return null;
      }
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 != this) {
         if (var1 !is JobCancellationException) {
            return false;
         }

         if (!((var1 as JobCancellationException).getMessage() == this.getMessage())
            || !((var1 as JobCancellationException).job == this.job)
            || !((var1 as JobCancellationException).getCause() == this.getCause())) {
            return false;
         }
      }

      return true;
   }

   public override fun fillInStackTrace(): Throwable {
      if (DebugKt.getDEBUG()) {
         return super.fillInStackTrace();
      } else {
         this.setStackTrace(new StackTraceElement[0]);
         return this;
      }
   }

   public override fun hashCode(): Int {
      val var4: java.lang.String = this.getMessage();
      val var3: Int = var4.hashCode();
      val var2: Int = this.job.hashCode();
      val var5: java.lang.Throwable = this.getCause();
      val var1: Int;
      if (var5 != null) {
         var1 = var5.hashCode();
      } else {
         var1 = 0;
      }

      return (var3 * 31 + var2) * 31 + var1;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(super.toString());
      var1.append("; job=");
      var1.append(this.job);
      return var1.toString();
   }
}
