package kotlinx.coroutines

import java.util.concurrent.CancellationException
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlinx.coroutines.selects.SelectClause0

public object NonCancellable : AbstractCoroutineContextElement(Job.Key), Job {
   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited"
   )
   public open val children: Sequence<Job>
      public open get() {
         return SequencesKt.emptySequence();
      }


   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited"
   )
   public open val isActive: Boolean
      public open get() {
         return true;
      }


   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited"
   )
   public open val isCancelled: Boolean
      public open get() {
         return false;
      }


   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited"
   )
   public open val isCompleted: Boolean
      public open get() {
         return false;
      }


   private const val message: String = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited"

   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited"
   )
   public open val onJoin: SelectClause0
      public open get() {
         throw new UnsupportedOperationException("This job is always active");
      }


   @Deprecated(
      level = DeprecationLevel.WARNING,
      message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited"
   )
   public open val parent: Job?
      public open get() {
         return null;
      }


   @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
   public override fun attachChild(child: ChildJob): ChildHandle {
      return NonDisposableHandle.INSTANCE;
   }

   @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
   public override fun cancel(cause: CancellationException?) {
   }

   @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
   public override fun getCancellationException(): CancellationException {
      throw new IllegalStateException("This job is always active");
   }

   @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
   public override fun invokeOnCompletion(handler: (Throwable?) -> Unit): DisposableHandle {
      return NonDisposableHandle.INSTANCE;
   }

   @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
   public override fun invokeOnCompletion(onCancelling: Boolean, invokeImmediately: Boolean, handler: (Throwable?) -> Unit): DisposableHandle {
      return NonDisposableHandle.INSTANCE;
   }

   @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
   public override suspend fun join() {
      throw new UnsupportedOperationException("This job is always active");
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
   override fun plus(var1: Job): Job {
      return Job.DefaultImpls.plus(this, var1);
   }

   @Deprecated(level = DeprecationLevel.WARNING, message = "NonCancellable can be used only as an argument for 'withContext', direct usages of its API are prohibited")
   public override fun start(): Boolean {
      return false;
   }

   public override fun toString(): String {
      return "NonCancellable";
   }
}
