package kotlinx.coroutines.sync

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.TypeIntrinsics
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CancellableContinuationImpl
import kotlinx.coroutines.CancellableContinuationKt
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.DebugStringsKt
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.Waiter
import kotlinx.coroutines.internal.Segment
import kotlinx.coroutines.internal.Symbol
import kotlinx.coroutines.selects.SelectClause2
import kotlinx.coroutines.selects.SelectClause2Impl
import kotlinx.coroutines.selects.SelectInstance
import kotlinx.coroutines.selects.SelectInstanceInternal

internal open class MutexImpl(locked: Boolean) : SemaphoreImpl(1, var1), Mutex {
   public open val isLocked: Boolean
      public open get() {
         val var1: Boolean;
         if (this.getAvailablePermits() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public open val onLock: SelectClause2<Any?, Mutex>
      public open get() {
         val var1: <unrepresentable> = <unrepresentable>.INSTANCE;
         val var3: Function3 = TypeIntrinsics.beforeCheckcastToFunctionOfArity(var1, 3) as Function3;
         val var2: <unrepresentable> = <unrepresentable>.INSTANCE;
         return new SelectClause2Impl<>(
            this, var3, TypeIntrinsics.beforeCheckcastToFunctionOfArity(var2, 3) as (Any?, Any?, Any?) -> Any, this.onSelectCancellationUnlockConstructor
         );
      }


   private final val onSelectCancellationUnlockConstructor: (SelectInstance<*>, Any?, Any?) -> (Throwable) -> Unit
   private final val owner: AtomicRef<Any?>

   init {
      val var2: Symbol;
      if (var1 != 0) {
         var2 = null;
      } else {
         var2 = MutexKt.access$getNO_OWNER$p();
      }

      this.owner = var2;
      this.onSelectCancellationUnlockConstructor = (
         new Function3<SelectInstance<?>, Object, Object, Function1<? super java.lang.Throwable, ? extends Unit>>(this) {
            final MutexImpl this$0;

            {
               super(3);
               this.this$0 = var1;
            }

            public final Function1<java.lang.Throwable, Unit> invoke(SelectInstance<?> var1, Object var2, Object var3) {
               return (new Function1<java.lang.Throwable, Unit>(this.this$0, var2) {
                  final Object $owner;
                  final MutexImpl this$0;

                  {
                     super(1);
                     this.this$0 = var1;
                     this.$owner = var2x;
                  }

                  public final void invoke(java.lang.Throwable var1) {
                     this.this$0.unlock(this.$owner);
                  }
               }) as (java.lang.Throwable?) -> Unit;
            }
         }
      ) as (SelectInstance<?>?, Any?, Any?) -> ((java.lang.Throwable?) -> Unit);
   }

   private fun holdsLockImpl(owner: Any?): Int {
      while (this.isLocked()) {
         val var3: Any = owner$FU.get(this);
         if (var3 != MutexKt.access$getNO_OWNER$p()) {
            val var2: Byte;
            if (var3 === var1) {
               var2 = 1;
            } else {
               var2 = 2;
            }

            return var2;
         }
      }

      return 0;
   }

   private suspend fun lockSuspend(owner: Any?) {
      label21: {
         val var3: CancellableContinuationImpl = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(var2));

         try {
            ;
         } catch (var5: java.lang.Throwable) {
            var3.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
         }

         var1 = var3.getResult();
         if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(var2);
         }

         return if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var1 else Unit.INSTANCE;
      }
   }

   private fun tryLockImpl(owner: Any?): Int {
      while (!this.tryAcquire()) {
         if (var1 == null) {
            return 1;
         }

         val var2: Int = this.holdsLockImpl(var1);
         if (var2 != 1) {
            if (var2 != 2) {
               continue;
            }

            return 1;
         }

         return 2;
      }

      if (DebugKt.getASSERTIONS_ENABLED() && owner$FU.get(this) != MutexKt.access$getNO_OWNER$p()) {
         throw new AssertionError();
      } else {
         owner$FU.set(this, var1);
         return 0;
      }
   }

   public override fun holdsLock(owner: Any): Boolean {
      val var2: Int = this.holdsLockImpl(var1);
      var var3: Boolean = true;
      if (var2 != 1) {
         var3 = false;
      }

      return var3;
   }

   public override suspend fun lock(owner: Any?) {
      return lock$suspendImpl(this, var1, var2);
   }

   protected open fun onLockProcessResult(owner: Any?, result: Any?): Any? {
      if (!(var2 == MutexKt.access$getON_LOCK_ALREADY_LOCKED_BY_OWNER$p())) {
         return this;
      } else {
         var2 = new StringBuilder("This mutex is already locked by the specified owner: ");
         var2.append(var1);
         throw new IllegalStateException(var2.toString().toString());
      }
   }

   protected open fun onLockRegFunction(select: SelectInstance<*>, owner: Any?) {
      if (var2 != null && this.holdsLock(var2)) {
         var1.selectInRegistrationPhase(MutexKt.access$getON_LOCK_ALREADY_LOCKED_BY_OWNER$p());
      } else {
         this.onAcquireRegFunction(new MutexImpl.SelectInstanceWithOwner(this, var1 as SelectInstanceInternal, var2), var2);
      }
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("Mutex@");
      var1.append(DebugStringsKt.getHexAddress(this));
      var1.append("[isLocked=");
      var1.append(this.isLocked());
      var1.append(",owner=");
      var1.append(owner$FU.get(this));
      var1.append(']');
      return var1.toString();
   }

   public override fun tryLock(owner: Any?): Boolean {
      val var2: Int = this.tryLockImpl(var1);
      var var3: Boolean = true;
      if (var2 != 0) {
         if (var2 != 1) {
            if (var2 != 2) {
               throw new IllegalStateException("unexpected".toString());
            }

            val var4: StringBuilder = new StringBuilder("This mutex is already locked by the specified owner: ");
            var4.append(var1);
            throw new IllegalStateException(var4.toString().toString());
         }

         var3 = false;
      }

      return var3;
   }

   public override fun unlock(owner: Any?) {
      while (this.isLocked()) {
         val var3: AtomicReferenceFieldUpdater = owner$FU;
         val var2: Any = owner$FU.get(this);
         if (var2 != MutexKt.access$getNO_OWNER$p()) {
            if (var2 != var1 && var1 != null) {
               val var4: StringBuilder = new StringBuilder("This mutex is locked by ");
               var4.append(var2);
               var4.append(", but ");
               var4.append(var1);
               var4.append(" is expected");
               throw new IllegalStateException(var4.toString().toString());
            }

            if (ExternalSyntheticBackportWithForwarding0.m(var3, this, var2, MutexKt.access$getNO_OWNER$p())) {
               this.release();
               return;
            }
         }
      }

      throw new IllegalStateException("This mutex is not locked".toString());
   }

   private inner class CancellableContinuationWithOwner(cont: CancellableContinuationImpl<Unit>, owner: Any?) : CancellableContinuation<Unit>, Waiter {
      public final val cont: CancellableContinuationImpl<Unit>
      public open val context: CoroutineContext
      public open val isActive: Boolean
      public open val isCancelled: Boolean
      public open val isCompleted: Boolean
      public final val owner: Any?

      init {
         this.this$0 = var1;
         this.cont = var2;
         this.owner = var3;
      }

      public override fun cancel(cause: Throwable? = ...): Boolean {
         return this.cont.cancel(var1);
      }

      public override fun completeResume(token: Any) {
         this.cont.completeResume(var1);
      }

      public override fun initCancellability() {
         this.cont.initCancellability();
      }

      public override fun invokeOnCancellation(handler: (Throwable?) -> Unit) {
         this.cont.invokeOnCancellation(var1);
      }

      public override fun invokeOnCancellation(segment: Segment<*>, index: Int) {
         this.cont.invokeOnCancellation(var1, var2);
      }

      public open fun resume(value: Unit, onCancellation: ((Throwable) -> Unit)?) {
         val var3: MutexImpl = this.this$0;
         if (DebugKt.getASSERTIONS_ENABLED() && MutexImpl.access$getOwner$FU$p().get(var3) != MutexKt.access$getNO_OWNER$p()) {
            throw new AssertionError();
         } else {
            MutexImpl.access$getOwner$FU$p().set(this.this$0, this.owner);
            this.cont.resume(var1, (new Function1<java.lang.Throwable, Unit>(this.this$0, this) {
               final MutexImpl this$0;
               final MutexImpl.CancellableContinuationWithOwner this$1;

               {
                  super(1);
                  this.this$0 = var1;
                  this.this$1 = var2;
               }

               public final void invoke(java.lang.Throwable var1) {
                  this.this$0.unlock(this.this$1.owner);
               }
            }) as (java.lang.Throwable?) -> Unit);
         }
      }

      public open fun CoroutineDispatcher.resumeUndispatched(value: Unit) {
         this.cont.resumeUndispatched(var1, var2);
      }

      public override fun CoroutineDispatcher.resumeUndispatchedWithException(exception: Throwable) {
         this.cont.resumeUndispatchedWithException(var1, var2);
      }

      public override fun resumeWith(result: Result<Unit>) {
         this.cont.resumeWith(var1);
      }

      public open fun tryResume(value: Unit, idempotent: Any? = ...): Any? {
         return this.cont.tryResume(var1, var2);
      }

      public open fun tryResume(value: Unit, idempotent: Any?, onCancellation: ((Throwable) -> Unit)?): Any? {
         val var6: MutexImpl = this.this$0;
         if (DebugKt.getASSERTIONS_ENABLED() && MutexImpl.access$getOwner$FU$p().get(var6) != MutexKt.access$getNO_OWNER$p()) {
            throw new AssertionError();
         } else {
            var2 = this.cont.tryResume(var1, var2, (new Function1<java.lang.Throwable, Unit>(this.this$0, this) {
               final MutexImpl this$0;
               final MutexImpl.CancellableContinuationWithOwner this$1;

               {
                  super(1);
                  this.this$0 = var1;
                  this.this$1 = var2;
               }

               public final void invoke(java.lang.Throwable var1) {
                  var var2: MutexImpl = this.this$0;
                  val var3: MutexImpl.CancellableContinuationWithOwner = this.this$1;
                  if (DebugKt.getASSERTIONS_ENABLED()) {
                     var2 = (MutexImpl)MutexImpl.access$getOwner$FU$p().get(var2);
                     if (var2 != MutexKt.access$getNO_OWNER$p() && var2 != var3.owner) {
                        throw new AssertionError();
                     }
                  }

                  MutexImpl.access$getOwner$FU$p().set(this.this$0, this.this$1.owner);
                  this.this$0.unlock(this.this$1.owner);
               }
            }) as (java.lang.Throwable?) -> Unit);
            if (var2 != null) {
               val var4: MutexImpl = this.this$0;
               if (DebugKt.getASSERTIONS_ENABLED() && MutexImpl.access$getOwner$FU$p().get(var4) != MutexKt.access$getNO_OWNER$p()) {
                  throw new AssertionError();
               }

               MutexImpl.access$getOwner$FU$p().set(this.this$0, this.owner);
            }

            return var2;
         }
      }

      public override fun tryResumeWithException(exception: Throwable): Any? {
         return this.cont.tryResumeWithException(var1);
      }
   }

   private inner class SelectInstanceWithOwner<Q>(select: SelectInstanceInternal<Any>, owner: Any?) : SelectInstanceInternal<Q> {
      public open val context: CoroutineContext
      public final val owner: Any?
      public final val select: SelectInstanceInternal<Any>

      init {
         this.this$0 = var1;
         this.select = var2;
         this.owner = var3;
      }

      public override fun disposeOnCompletion(disposableHandle: DisposableHandle) {
         this.select.disposeOnCompletion(var1);
      }

      public override fun invokeOnCancellation(segment: Segment<*>, index: Int) {
         this.select.invokeOnCancellation(var1, var2);
      }

      public override fun selectInRegistrationPhase(internalResult: Any?) {
         val var2: MutexImpl = this.this$0;
         if (DebugKt.getASSERTIONS_ENABLED() && MutexImpl.access$getOwner$FU$p().get(var2) != MutexKt.access$getNO_OWNER$p()) {
            throw new AssertionError();
         } else {
            MutexImpl.access$getOwner$FU$p().set(this.this$0, this.owner);
            this.select.selectInRegistrationPhase(var1);
         }
      }

      public override fun trySelect(clauseObject: Any, result: Any?): Boolean {
         val var4: MutexImpl = this.this$0;
         if (DebugKt.getASSERTIONS_ENABLED() && MutexImpl.access$getOwner$FU$p().get(var4) != MutexKt.access$getNO_OWNER$p()) {
            throw new AssertionError();
         } else {
            val var3: Boolean = this.select.trySelect(var1, var2);
            var1 = this.this$0;
            if (var3) {
               MutexImpl.access$getOwner$FU$p().set(var1, this.owner);
            }

            return var3;
         }
      }
   }
}
