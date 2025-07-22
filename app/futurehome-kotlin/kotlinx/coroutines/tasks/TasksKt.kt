package kotlinx.coroutines.tasks

import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import java.util.concurrent.CancellationException
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CancellableContinuationImpl
import kotlinx.coroutines.ChildHandle
import kotlinx.coroutines.ChildJob
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CompletableDeferredKt
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.Job
import kotlinx.coroutines.selects.SelectClause0
import kotlinx.coroutines.selects.SelectClause1

@JvmSynthetic
fun `$r8$lambda$epYkKO3YUebkIFWW0wImisRdi6w`(var0: CompletableDeferred, var1: Task) {
   asDeferredImpl$lambda$0(var0, var1);
}

@JvmSynthetic
fun `access$awaitImpl`(var0: Task, var1: CancellationTokenSource, var2: Continuation): Any {
   return awaitImpl(var0, var1, var2);
}

public fun <T> Task<T>.asDeferred(): Deferred<T> {
   return asDeferredImpl(var0, null);
}

public fun <T> Task<T>.asDeferred(cancellationTokenSource: CancellationTokenSource): Deferred<T> {
   return asDeferredImpl(var0, var1);
}

private fun <T> Task<T>.asDeferredImpl(cancellationTokenSource: CancellationTokenSource?): Deferred<T> {
   val var3: CompletableDeferred = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
   if (var0.isComplete()) {
      val var2: Exception = var0.getException();
      if (var2 == null) {
         if (var0.isCanceled()) {
            Job.DefaultImpls.cancel$default(var3, null, 1, null);
         } else {
            var3.complete(var0.getResult());
         }
      } else {
         var3.completeExceptionally(var2);
      }
   } else {
      var0.addOnCompleteListener(DirectExecutor.INSTANCE, new TasksKt$$ExternalSyntheticLambda0(var3));
   }

   if (var1 != null) {
      var3.invokeOnCompletion((new Function1<java.lang.Throwable, Unit>(var1) {
         final CancellationTokenSource $cancellationTokenSource;

         {
            super(1);
            this.$cancellationTokenSource = var1;
         }

         public final void invoke(java.lang.Throwable var1) {
            this.$cancellationTokenSource.cancel();
         }
      }) as (java.lang.Throwable?) -> Unit);
   }

   return new Deferred<T>(var3) {
      private final CompletableDeferred<T> $$delegate_0;

      {
         this.$$delegate_0 = var1;
      }

      @Override
      public ChildHandle attachChild(ChildJob var1) {
         return this.$$delegate_0.attachChild(var1);
      }

      @Override
      public Object await(Continuation<? super T> var1) {
         return this.$$delegate_0.await(var1);
      }

      @Override
      public void cancel(CancellationException var1) {
         this.$$delegate_0.cancel(var1);
      }

      @Override
      public <R> R fold(R var1, Function2<? super R, ? super CoroutineContext.Element, ? extends R> var2) {
         return this.$$delegate_0.fold((R)var1, var2);
      }

      @Override
      public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> var1) {
         return this.$$delegate_0.get(var1);
      }

      @Override
      public CancellationException getCancellationException() {
         return this.$$delegate_0.getCancellationException();
      }

      @Override
      public Sequence<Job> getChildren() {
         return this.$$delegate_0.getChildren();
      }

      @Override
      public T getCompleted() {
         return (T)this.$$delegate_0.getCompleted();
      }

      @Override
      public java.lang.Throwable getCompletionExceptionOrNull() {
         return this.$$delegate_0.getCompletionExceptionOrNull();
      }

      @Override
      public CoroutineContext.Key<?> getKey() {
         return this.$$delegate_0.getKey();
      }

      @Override
      public SelectClause1<T> getOnAwait() {
         return this.$$delegate_0.getOnAwait();
      }

      @Override
      public SelectClause0 getOnJoin() {
         return this.$$delegate_0.getOnJoin();
      }

      @Override
      public Job getParent() {
         return this.$$delegate_0.getParent();
      }

      @Override
      public DisposableHandle invokeOnCompletion(Function1<? super java.lang.Throwable, Unit> var1) {
         return this.$$delegate_0.invokeOnCompletion(var1);
      }

      @Override
      public DisposableHandle invokeOnCompletion(boolean var1, boolean var2, Function1<? super java.lang.Throwable, Unit> var3) {
         return this.$$delegate_0.invokeOnCompletion(var1, var2, var3);
      }

      @Override
      public boolean isActive() {
         return this.$$delegate_0.isActive();
      }

      @Override
      public boolean isCancelled() {
         return this.$$delegate_0.isCancelled();
      }

      @Override
      public boolean isCompleted() {
         return this.$$delegate_0.isCompleted();
      }

      @Override
      public Object join(Continuation<? super Unit> var1) {
         return this.$$delegate_0.join(var1);
      }

      @Override
      public CoroutineContext minusKey(CoroutineContext.Key<?> var1) {
         return this.$$delegate_0.minusKey(var1);
      }

      @Override
      public CoroutineContext plus(CoroutineContext var1) {
         return this.$$delegate_0.plus(var1);
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`."
      )
      @Override
      public Job plus(Job var1) {
         return this.$$delegate_0.plus(var1);
      }

      @Override
      public boolean start() {
         return this.$$delegate_0.start();
      }
   };
}

fun `asDeferredImpl$lambda$0`(var0: CompletableDeferred, var1: Task) {
   val var2: Exception = var1.getException();
   if (var2 == null) {
      if (var1.isCanceled()) {
         Job.DefaultImpls.cancel$default(var0, null, 1, null);
      } else {
         var0.complete(var1.getResult());
      }
   } else {
      var0.completeExceptionally(var2);
   }
}

public fun <T> Deferred<T>.asTask(): Task<T> {
   val var1: CancellationTokenSource = new CancellationTokenSource();
   val var2: TaskCompletionSource = new TaskCompletionSource(var1.getToken());
   var0.invokeOnCompletion((new Function1<java.lang.Throwable, Unit>(var1, var0, var2) {
      final CancellationTokenSource $cancellation;
      final TaskCompletionSource<T> $source;
      final Deferred<T> $this_asTask;

      {
         super(1);
         this.$cancellation = var1;
         this.$this_asTask = var2;
         this.$source = var3;
      }

      public final void invoke(java.lang.Throwable var1) {
         if (var1 is CancellationException) {
            this.$cancellation.cancel();
         } else {
            val var4: java.lang.Throwable = this.$this_asTask.getCompletionExceptionOrNull();
            if (var4 == null) {
               this.$source.setResult(this.$this_asTask.getCompleted());
            } else {
               val var5: Exception;
               if (var4 is Exception) {
                  var5 = var4 as Exception;
               } else {
                  var5 = null;
               }

               var var2: Exception = var5;
               if (var5 == null) {
                  var2 = (new RuntimeExecutionException(var4)) as Exception;
               }

               this.$source.setException(var2);
            }
         }
      }
   }) as (java.lang.Throwable?) -> Unit);
   return var2.getTask();
}

public suspend fun <T> Task<T>.await(cancellationTokenSource: CancellationTokenSource): T {
   return awaitImpl(var0, var1, var2);
}

public suspend fun <T> Task<T>.await(): T {
   return awaitImpl(var0, null, var1);
}

private suspend fun <T> Task<T>.awaitImpl(cancellationTokenSource: CancellationTokenSource?): T {
   if (var0.isComplete()) {
      val var6: Exception = var0.getException();
      if (var6 == null) {
         if (!var0.isCanceled()) {
            return var0.getResult();
         } else {
            val var7: StringBuilder = new StringBuilder("Task ");
            var7.append(var0);
            var7.append(" was cancelled normally.");
            throw new CancellationException(var7.toString());
         }
      } else {
         throw var6;
      }
   } else {
      val var4: CancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(var2), 1);
      var4.initCancellability();
      val var3: CancellableContinuation = var4;
      var0.addOnCompleteListener(DirectExecutor.INSTANCE, new OnCompleteListener(var4) {
         final CancellableContinuation<T> $cont;

         {
            this.$cont = var1;
         }

         public final void onComplete(Task<T> var1) {
            val var2: Exception = var1.getException();
            if (var2 == null) {
               if (var1.isCanceled()) {
                  CancellableContinuation.DefaultImpls.cancel$default(this.$cont, null, 1, null);
               } else {
                  val var3: Continuation = this.$cont;
                  val var5: Result.Companion = Result.Companion;
                  var3.resumeWith(Result.constructor-impl(var1.getResult()));
               }
            } else {
               val var6: Continuation = this.$cont;
               val var4: Result.Companion = Result.Companion;
               var6.resumeWith(Result.constructor-impl(ResultKt.createFailure(var2)));
            }
         }
      });
      if (var1 != null) {
         var3.invokeOnCancellation((new Function1<java.lang.Throwable, Unit>(var1) {
            final CancellationTokenSource $cancellationTokenSource;

            {
               super(1);
               this.$cancellationTokenSource = var1;
            }

            public final void invoke(java.lang.Throwable var1) {
               this.$cancellationTokenSource.cancel();
            }
         }) as (java.lang.Throwable?) -> Unit);
      }

      val var5: Any = var4.getResult();
      if (var5 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var2);
      }

      return var5;
   }
}
