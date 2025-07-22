package kotlinx.coroutines

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.ArrayList
import java.util.Collections
import java.util.IdentityHashMap
import java.util.concurrent.CancellationException
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.CoroutineContext.Key
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.CoroutineStackFrame
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.TypeIntrinsics
import kotlinx.atomicfu.AtomicBoolean
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.internal.LockFreeLinkedListHead
import kotlinx.coroutines.internal.LockFreeLinkedListKt
import kotlinx.coroutines.internal.LockFreeLinkedListNode
import kotlinx.coroutines.internal.OpDescriptor
import kotlinx.coroutines.internal.StackTraceRecoveryKt
import kotlinx.coroutines.selects.SelectClause0
import kotlinx.coroutines.selects.SelectClause0Impl
import kotlinx.coroutines.selects.SelectClause1
import kotlinx.coroutines.selects.SelectClause1Impl
import kotlinx.coroutines.selects.SelectInstance

@Deprecated(level = DeprecationLevel.ERROR, message = "This is internal API and may be removed in the future releases")
public open class JobSupport(active: Boolean) : Job, ChildJob, ParentJob {
   private final val _parentHandle: AtomicRef<ChildHandle?>
   private final val _state: AtomicRef<Any?>

   public final val children: Sequence<Job>
      public final get() {
         return SequencesKt.sequence((new Function2<SequenceScope<? super Job>, Continuation<? super Unit>, Object>(this, null) {
            private Object L$0;
            Object L$1;
            Object L$2;
            int label;
            final JobSupport this$0;

            {
               super(2, var2x);
               this.this$0 = var1;
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               val var3: Function2 = new <anonymous constructor>(this.this$0, var2);
               var3.L$0 = var1;
               return var3 as Continuation<Unit>;
            }

            public final Object invoke(SequenceScope<? super Job> var1, Continuation<? super Unit> var2x) {
               return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
            }

            // $VF: Irreducible bytecode was duplicated to produce valid code
            @Override
            public final Object invokeSuspend(Object var1) {
               val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               var var4: SequenceScope;
               var var7: LockFreeLinkedListHead;
               if (this.label != 0) {
                  if (this.label == 1) {
                     ResultKt.throwOnFailure(var1);
                     return Unit.INSTANCE;
                  }

                  if (this.label != 2) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  val var3x: LockFreeLinkedListNode = this.L$2 as LockFreeLinkedListNode;
                  val var5: LockFreeLinkedListHead = this.L$1 as LockFreeLinkedListHead;
                  val var6: SequenceScope = this.L$0 as SequenceScope;
                  ResultKt.throwOnFailure(var1);
                  var1 = var3x.getNextNode();
                  var7 = var5;
                  var4 = var6;
               } else {
                  ResultKt.throwOnFailure(var1);
                  var4 = this.L$0 as SequenceScope;
                  var1 = this.this$0.getState$kotlinx_coroutines_core();
                  if (var1 is ChildHandleNode) {
                     var1 = (var1 as ChildHandleNode).childJob;
                     val var16: Continuation = this;
                     this.label = 1;
                     if (var4.yield(var1, var16) === var8) {
                        return var8;
                     }

                     return Unit.INSTANCE;
                  }

                  if (var1 !is Incomplete) {
                     return Unit.INSTANCE;
                  }

                  var1 = (var1 as Incomplete).getList();
                  if (var1 == null) {
                     return Unit.INSTANCE;
                  }

                  var7 = var1;
                  var1 = var1.getNext();
                  var1 = var1 as LockFreeLinkedListNode;
               }

               while (!(var1 == var7)) {
                  var var15: LockFreeLinkedListNode = var1;
                  var var17: LockFreeLinkedListHead = var7;
                  var var18: SequenceScope = var4;
                  if (var1 is ChildHandleNode) {
                     val var9: ChildJob = (var1 as ChildHandleNode).childJob;
                     this.L$0 = var4;
                     this.L$1 = var7;
                     this.L$2 = var1;
                     this.label = 2;
                     var15 = var1;
                     var17 = var7;
                     var18 = var4;
                     if (var4.yield(var9, this) === var8) {
                        return var8;
                     }
                  }

                  var1 = var15.getNextNode();
                  var7 = var17;
                  var4 = var18;
               }

               return Unit.INSTANCE;
            }
         }) as (SequenceScope<? super Job>?, Continuation<? super Unit>?) -> Any);
      }


   protected final val completionCause: Throwable?
      protected final get() {
         var var1: java.lang.Throwable = (java.lang.Throwable)this.getState$kotlinx_coroutines_core();
         if (var1 is JobSupport.Finishing) {
            var1 = (var1 as JobSupport.Finishing).getRootCause();
            if (var1 == null) {
               val var3: StringBuilder = new StringBuilder("Job is still new or active: ");
               var3.append(this);
               throw new IllegalStateException(var3.toString().toString());
            }
         } else {
            if (var1 is Incomplete) {
               val var4: StringBuilder = new StringBuilder("Job is still new or active: ");
               var4.append(this);
               throw new IllegalStateException(var4.toString().toString());
            }

            if (var1 is CompletedExceptionally) {
               var1 = (var1 as CompletedExceptionally).cause;
            } else {
               var1 = null;
            }
         }

         return var1;
      }


   protected final val completionCauseHandled: Boolean
      protected final get() {
         val var2: Any = this.getState$kotlinx_coroutines_core();
         val var1: Boolean;
         if (var2 is CompletedExceptionally && (var2 as CompletedExceptionally).getHandled()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   internal open val handlesException: Boolean
      internal open get() {
         return true;
      }


   public open val isActive: Boolean
      public open get() {
         val var2: Any = this.getState$kotlinx_coroutines_core();
         val var1: Boolean;
         if (var2 is Incomplete && (var2 as Incomplete).isActive()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public final val isCancelled: Boolean
      public final get() {
         val var2: Any = this.getState$kotlinx_coroutines_core();
         val var1: Boolean;
         if (var2 is CompletedExceptionally || var2 is JobSupport.Finishing && (var2 as JobSupport.Finishing).isCancelling()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public final val isCompleted: Boolean
      public final get() {
         return this.getState$kotlinx_coroutines_core() is Incomplete xor true;
      }


   public final val isCompletedExceptionally: Boolean
      public final get() {
         return this.getState$kotlinx_coroutines_core() is CompletedExceptionally;
      }


   protected open val isScopedCoroutine: Boolean
      protected open get() {
         return false;
      }


   public final val key: Key<*>
      public final get() {
         return Job.Key;
      }


   protected final val onAwaitInternal: SelectClause1<*>
      protected final get() {
         val var1: <unrepresentable> = <unrepresentable>.INSTANCE;
         val var3: Function3 = TypeIntrinsics.beforeCheckcastToFunctionOfArity(var1, 3) as Function3;
         val var2: <unrepresentable> = <unrepresentable>.INSTANCE;
         return new SelectClause1Impl(this, var3, TypeIntrinsics.beforeCheckcastToFunctionOfArity(var2, 3) as Function3, null, 8, null);
      }


   internal open val onCancelComplete: Boolean
      internal open get() {
         return false;
      }


   public final val onJoin: SelectClause0
      public final get() {
         val var1: <unrepresentable> = <unrepresentable>.INSTANCE;
         return new SelectClause0Impl(this, TypeIntrinsics.beforeCheckcastToFunctionOfArity(var1, 3) as Function3, null, 4, null);
      }


   public open val parent: Job?
      public open get() {
         val var1: ChildHandle = this.getParentHandle$kotlinx_coroutines_core();
         val var2: Job;
         if (var1 != null) {
            var2 = var1.getParent();
         } else {
            var2 = null;
         }

         return var2;
      }


   internal final var parentHandle: ChildHandle?
      internal final get() {
         return _parentHandle$FU.get(this) as ChildHandle;
      }

      internal final set(value) {
         _parentHandle$FU.set(this, var1);
      }


   internal final val state: Any?
      internal final get() {
         val var2: AtomicReferenceFieldUpdater = _state$FU;

         while (true) {
            val var1: Any = var2.get(this);
            if (var1 !is OpDescriptor) {
               return var1;
            }

            (var1 as OpDescriptor).perform(this);
         }
      }


   private final val exceptionOrNull: Throwable?
      private final get() {
         val var2: Boolean = var1 is CompletedExceptionally;
         var var3: java.lang.Throwable = null;
         if (var2) {
            var1 = var1;
         } else {
            var1 = null;
         }

         if (var1 != null) {
            var3 = var1.cause;
         }

         return var3;
      }


   private final val isCancelling: Boolean
      private final get() {
         val var2: Boolean;
         if (var1 is JobSupport.Finishing && (var1 as JobSupport.Finishing).isCancelling()) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }


   init {
      val var2: Empty;
      if (var1) {
         var2 = JobSupportKt.access$getEMPTY_ACTIVE$p();
      } else {
         var2 = JobSupportKt.access$getEMPTY_NEW$p();
      }

      this._state = var2;
   }

   private fun addLastAtomic(expect: Any, list: NodeList, node: JobNode): Boolean {
      val var7: LockFreeLinkedListNode = var2;
      val var8: LockFreeLinkedListNode = var3;
      var1 = new LockFreeLinkedListNode.CondAddOp(var3, this, var1) {
         final Object $expect$inlined;
         final JobSupport this$0;

         {
            super(var1);
            this.this$0 = var2;
            this.$expect$inlined = var3;
         }

         public Object prepare(LockFreeLinkedListNode var1) {
            val var2: Any;
            if (this.this$0.getState$kotlinx_coroutines_core() === this.$expect$inlined) {
               var2 = null;
            } else {
               var2 = LockFreeLinkedListKt.getCONDITION_FALSE();
            }

            return var2;
         }
      };

      var var5: Boolean;
      while (true) {
         val var4: Int = var7.getPrevNode().tryCondAddNext(var8, var7, var1);
         var5 = true;
         if (var4 == 1) {
            break;
         }

         if (var4 == 2) {
            var5 = false;
            break;
         }
      }

      return var5;
   }

   private fun addSuppressedExceptions(rootCause: Throwable, exceptions: List<Throwable>) {
      if (var2.size() > 1) {
         val var4: java.util.Set = Collections.newSetFromMap(new IdentityHashMap(var2.size()));
         val var3: java.lang.Throwable;
         if (!DebugKt.getRECOVER_STACK_TRACES()) {
            var3 = var1;
         } else {
            var3 = StackTraceRecoveryKt.unwrapImpl(var1);
         }

         for (java.lang.Throwable var6 : var2) {
            if (DebugKt.getRECOVER_STACK_TRACES()) {
               var6 = StackTraceRecoveryKt.unwrapImpl(var6);
            }

            if (var6 != var1 && var6 != var3 && var6 !is CancellationException && var4.add(var6)) {
               kotlin.ExceptionsKt.addSuppressed(var1, var6);
            }
         }
      }
   }

   private suspend fun awaitSuspend(): Any? {
      val var2: JobSupport.AwaitContinuation = new JobSupport.AwaitContinuation(IntrinsicsKt.intercepted(var1), this);
      var2.initCancellability();
      CancellableContinuationKt.disposeOnCancellation(var2, this.invokeOnCompletion(new ResumeAwaitOnCompletion(var2)));
      val var3: Any = var2.getResult();
      if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var1);
      }

      return var3;
   }

   private fun cancelMakeCompleting(cause: Any?): Any? {
      var var3: Any;
      do {
         var3 = this.getState$kotlinx_coroutines_core();
         if (var3 !is Incomplete || var3 is JobSupport.Finishing && (var3 as JobSupport.Finishing).isCompleting()) {
            return JobSupportKt.access$getCOMPLETING_ALREADY$p();
         }

         var3 = this.tryMakeCompleting(var3, new CompletedExceptionally(this.createCauseException(var1), false, 2, null));
      } while (var3 == JobSupportKt.access$getCOMPLETING_RETRY$p());

      return var3;
   }

   private fun cancelParent(cause: Throwable): Boolean {
      if (this.isScopedCoroutine()) {
         return true;
      } else {
         val var4: Boolean = var1 is CancellationException;
         val var5: ChildHandle = this.getParentHandle$kotlinx_coroutines_core();
         if (var5 != null && var5 != NonDisposableHandle.INSTANCE) {
            var var6: Boolean = true;
            if (!var5.childCancelled(var1)) {
               if (var4) {
                  var6 = true;
               } else {
                  var6 = false;
               }
            }

            return var6;
         } else {
            return var4;
         }
      }
   }

   private fun completeStateFinalization(state: Incomplete, update: Any?) {
      val var4: ChildHandle = this.getParentHandle$kotlinx_coroutines_core();
      if (var4 != null) {
         var4.dispose();
         this.setParentHandle$kotlinx_coroutines_core(NonDisposableHandle.INSTANCE);
      }

      val var11: CompletedExceptionally;
      if (var2 is CompletedExceptionally) {
         var11 = var2 as CompletedExceptionally;
      } else {
         var11 = null;
      }

      var2 = null;
      if (var11 != null) {
         var2 = var11.cause;
      }

      if (var1 is JobNode) {
         try {
            (var1 as JobNode).invoke(var2);
         } catch (var6: java.lang.Throwable) {
            val var10: StringBuilder = new StringBuilder("Exception in completion handler ");
            var10.append(var1);
            var10.append(" for ");
            var10.append(this);
            this.handleOnCompletionException$kotlinx_coroutines_core(new CompletionHandlerException(var10.toString(), var6));
            return;
         }
      } else {
         val var8: NodeList = var1.getList();
         if (var8 != null) {
            this.notifyCompletion(var8, var2);
         }
      }
   }

   private fun continueCompleting(state: kotlinx.coroutines.JobSupport.Finishing, lastChild: ChildHandleNode, proposedUpdate: Any?) {
      if (DebugKt.getASSERTIONS_ENABLED() && this.getState$kotlinx_coroutines_core() != var1) {
         throw new AssertionError();
      } else {
         var2 = this.nextChild(var2);
         if (var2 == null || !this.tryWaitForChild(var1, var2, var3)) {
            this.afterCompletion(this.finalizeFinishingState(var1, var3));
         }
      }
   }

   private fun createCauseException(cause: Any?): Throwable {
      val var2: Boolean;
      if (var1 == null) {
         var2 = true;
      } else {
         var2 = var1 is java.lang.Throwable;
      }

      if (var2) {
         val var3: java.lang.Throwable = var1;
         var1 = var1;
         if (var3 == null) {
            var1 = new JobCancellationException(access$cancellationExceptionMessage(this), null, this);
         }
      } else {
         var1 = (var1 as ParentJob).getChildJobCancellationCause();
      }

      return var1;
   }

   private fun finalizeFinishingState(state: kotlinx.coroutines.JobSupport.Finishing, proposedUpdate: Any?): Any? {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 000: invokestatic kotlinx/coroutines/DebugKt.getASSERTIONS_ENABLED ()Z
      // 003: ifeq 019
      // 006: aload 0
      // 007: invokevirtual kotlinx/coroutines/JobSupport.getState$kotlinx_coroutines_core ()Ljava/lang/Object;
      // 00a: aload 1
      // 00b: if_acmpne 011
      // 00e: goto 019
      // 011: new java/lang/AssertionError
      // 014: dup
      // 015: invokespecial java/lang/AssertionError.<init> ()V
      // 018: athrow
      // 019: invokestatic kotlinx/coroutines/DebugKt.getASSERTIONS_ENABLED ()Z
      // 01c: ifeq 031
      // 01f: aload 1
      // 020: invokevirtual kotlinx/coroutines/JobSupport$Finishing.isSealed ()Z
      // 023: ifne 029
      // 026: goto 031
      // 029: new java/lang/AssertionError
      // 02c: dup
      // 02d: invokespecial java/lang/AssertionError.<init> ()V
      // 030: athrow
      // 031: invokestatic kotlinx/coroutines/DebugKt.getASSERTIONS_ENABLED ()Z
      // 034: ifeq 049
      // 037: aload 1
      // 038: invokevirtual kotlinx/coroutines/JobSupport$Finishing.isCompleting ()Z
      // 03b: ifeq 041
      // 03e: goto 049
      // 041: new java/lang/AssertionError
      // 044: dup
      // 045: invokespecial java/lang/AssertionError.<init> ()V
      // 048: athrow
      // 049: aload 2
      // 04a: instanceof kotlinx/coroutines/CompletedExceptionally
      // 04d: ifeq 059
      // 050: aload 2
      // 051: checkcast kotlinx/coroutines/CompletedExceptionally
      // 054: astore 4
      // 056: goto 05c
      // 059: aconst_null
      // 05a: astore 4
      // 05c: aload 4
      // 05e: ifnull 06b
      // 061: aload 4
      // 063: getfield kotlinx/coroutines/CompletedExceptionally.cause Ljava/lang/Throwable;
      // 066: astore 4
      // 068: goto 06e
      // 06b: aconst_null
      // 06c: astore 4
      // 06e: aload 1
      // 06f: monitorenter
      // 070: aload 1
      // 071: invokevirtual kotlinx/coroutines/JobSupport$Finishing.isCancelling ()Z
      // 074: istore 3
      // 075: aload 1
      // 076: aload 4
      // 078: invokevirtual kotlinx/coroutines/JobSupport$Finishing.sealLocked (Ljava/lang/Throwable;)Ljava/util/List;
      // 07b: astore 6
      // 07d: aload 0
      // 07e: aload 1
      // 07f: aload 6
      // 081: invokespecial kotlinx/coroutines/JobSupport.getFinalRootCause (Lkotlinx/coroutines/JobSupport$Finishing;Ljava/util/List;)Ljava/lang/Throwable;
      // 084: astore 5
      // 086: aload 5
      // 088: ifnull 093
      // 08b: aload 0
      // 08c: aload 5
      // 08e: aload 6
      // 090: invokespecial kotlinx/coroutines/JobSupport.addSuppressedExceptions (Ljava/lang/Throwable;Ljava/util/List;)V
      // 093: aload 1
      // 094: monitorexit
      // 095: aload 5
      // 097: ifnonnull 09d
      // 09a: goto 0b4
      // 09d: aload 5
      // 09f: aload 4
      // 0a1: if_acmpne 0a7
      // 0a4: goto 0b4
      // 0a7: new kotlinx/coroutines/CompletedExceptionally
      // 0aa: dup
      // 0ab: aload 5
      // 0ad: bipush 0
      // 0ae: bipush 2
      // 0af: aconst_null
      // 0b0: invokespecial kotlinx/coroutines/CompletedExceptionally.<init> (Ljava/lang/Throwable;ZILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // 0b3: astore 2
      // 0b4: aload 5
      // 0b6: ifnull 0da
      // 0b9: aload 0
      // 0ba: aload 5
      // 0bc: invokespecial kotlinx/coroutines/JobSupport.cancelParent (Ljava/lang/Throwable;)Z
      // 0bf: ifne 0cb
      // 0c2: aload 0
      // 0c3: aload 5
      // 0c5: invokevirtual kotlinx/coroutines/JobSupport.handleJobException (Ljava/lang/Throwable;)Z
      // 0c8: ifeq 0da
      // 0cb: aload 2
      // 0cc: ldc_w "null cannot be cast to non-null type kotlinx.coroutines.CompletedExceptionally"
      // 0cf: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 0d2: aload 2
      // 0d3: checkcast kotlinx/coroutines/CompletedExceptionally
      // 0d6: invokevirtual kotlinx/coroutines/CompletedExceptionally.makeHandled ()Z
      // 0d9: pop
      // 0da: iload 3
      // 0db: ifne 0e4
      // 0de: aload 0
      // 0df: aload 5
      // 0e1: invokevirtual kotlinx/coroutines/JobSupport.onCancelling (Ljava/lang/Throwable;)V
      // 0e4: aload 0
      // 0e5: aload 2
      // 0e6: invokevirtual kotlinx/coroutines/JobSupport.onCompletionInternal (Ljava/lang/Object;)V
      // 0e9: getstatic kotlinx/coroutines/JobSupport._state$FU Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
      // 0ec: aload 0
      // 0ed: aload 1
      // 0ee: aload 2
      // 0ef: invokestatic kotlinx/coroutines/JobSupportKt.boxIncomplete (Ljava/lang/Object;)Ljava/lang/Object;
      // 0f2: invokestatic androidx/concurrent/futures/AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m (Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0f5: istore 3
      // 0f6: invokestatic kotlinx/coroutines/DebugKt.getASSERTIONS_ENABLED ()Z
      // 0f9: ifeq 10b
      // 0fc: iload 3
      // 0fd: ifeq 103
      // 100: goto 10b
      // 103: new java/lang/AssertionError
      // 106: dup
      // 107: invokespecial java/lang/AssertionError.<init> ()V
      // 10a: athrow
      // 10b: aload 0
      // 10c: aload 1
      // 10d: checkcast kotlinx/coroutines/Incomplete
      // 110: aload 2
      // 111: invokespecial kotlinx/coroutines/JobSupport.completeStateFinalization (Lkotlinx/coroutines/Incomplete;Ljava/lang/Object;)V
      // 114: aload 2
      // 115: areturn
      // 116: astore 2
      // 117: aload 1
      // 118: monitorexit
      // 119: aload 2
      // 11a: athrow
   }

   private fun firstChild(state: Incomplete): ChildHandleNode? {
      val var3: ChildHandleNode;
      if (var1 is ChildHandleNode) {
         var3 = var1 as ChildHandleNode;
      } else {
         var3 = null;
      }

      var var5: ChildHandleNode;
      if (var3 == null) {
         val var6: NodeList = var1.getList();
         var5 = null;
         if (var6 != null) {
            var5 = this.nextChild(var6);
         }
      } else {
         var5 = var3;
      }

      return var5;
   }

   private fun getFinalRootCause(state: kotlinx.coroutines.JobSupport.Finishing, exceptions: List<Throwable>): Throwable? {
      val var3: Boolean = var2.isEmpty();
      val var4: Any = null;
      if (var3) {
         return if (var1.isCancelling()) new JobCancellationException(access$cancellationExceptionMessage(this), null, this) else null;
      } else {
         val var5: java.lang.Iterable = var2;
         val var6: java.util.Iterator = var2.iterator();

         do {
            if (!var6.hasNext()) {
               var7 = null;
               break;
            }

            var7 = var6.next();
         } while ((java.lang.Throwable)var7 instanceof CancellationException);

         val var8: java.lang.Throwable = var7 as java.lang.Throwable;
         if (var7 as java.lang.Throwable != null) {
            return var8;
         } else {
            val var11: java.lang.Throwable = var2.get(0) as java.lang.Throwable;
            if (var11 is TimeoutCancellationException) {
               val var12: java.util.Iterator = var5.iterator();

               do {
                  var9 = var4;
                  if (!var12.hasNext()) {
                     break;
                  }

                  var9 = var12.next();
               } while ((java.lang.Throwable)var9 == var11 || !((java.lang.Throwable)var9 instanceof TimeoutCancellationException));

               val var10: java.lang.Throwable = var9 as java.lang.Throwable;
               if (var9 as java.lang.Throwable != null) {
                  return var10;
               }
            }

            return var11;
         }
      }
   }

   private fun getOrPromoteCancellingList(state: Incomplete): NodeList? {
      val var3: NodeList = var1.getList();
      var var2: NodeList = var3;
      if (var3 == null) {
         if (var1 is Empty) {
            var2 = new NodeList();
         } else {
            if (var1 !is JobNode) {
               val var4: StringBuilder = new StringBuilder("State should have list: ");
               var4.append(var1);
               throw new IllegalStateException(var4.toString().toString());
            }

            this.promoteSingleToNodeList(var1 as JobNode);
            var2 = null;
         }
      }

      return var2;
   }

   private fun joinInternal(): Boolean {
      val var1: Any;
      do {
         var1 = this.getState$kotlinx_coroutines_core();
         if (var1 !is Incomplete) {
            return false;
         }
      } while (this.startInternal(var1) < 0);

      return true;
   }

   private suspend fun joinSuspend() {
      val var2: CancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(var1), 1);
      var2.initCancellability();
      CancellableContinuationKt.disposeOnCancellation(var2, this.invokeOnCompletion(new ResumeOnCompletion(var2)));
      val var4: Any = var2.getResult();
      if (var4 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var1);
      }

      return if (var4 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var4 else Unit.INSTANCE;
   }

   fun `loop$atomicfu`(var1: AtomicReferenceFieldUpdater, var2: (Any?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   private inline fun loopOnState(block: (Any?) -> Unit): Nothing {
      while (true) {
         var1.invoke(this.getState$kotlinx_coroutines_core());
      }
   }

   private fun makeCancelling(cause: Any?): Any? {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:350)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:99)
      //
      // Bytecode:
      // 000: aconst_null
      // 001: astore 5
      // 003: aconst_null
      // 004: astore 3
      // 005: aload 0
      // 006: invokevirtual kotlinx/coroutines/JobSupport.getState$kotlinx_coroutines_core ()Ljava/lang/Object;
      // 009: astore 6
      // 00b: aload 6
      // 00d: instanceof kotlinx/coroutines/JobSupport$Finishing
      // 010: ifeq 083
      // 013: aload 6
      // 015: monitorenter
      // 016: aload 6
      // 018: checkcast kotlinx/coroutines/JobSupport$Finishing
      // 01b: invokevirtual kotlinx/coroutines/JobSupport$Finishing.isSealed ()Z
      // 01e: ifeq 02a
      // 021: invokestatic kotlinx/coroutines/JobSupportKt.access$getTOO_LATE_TO_CANCEL$p ()Lkotlinx/coroutines/internal/Symbol;
      // 024: astore 1
      // 025: aload 6
      // 027: monitorexit
      // 028: aload 1
      // 029: areturn
      // 02a: aload 6
      // 02c: checkcast kotlinx/coroutines/JobSupport$Finishing
      // 02f: invokevirtual kotlinx/coroutines/JobSupport$Finishing.isCancelling ()Z
      // 032: istore 2
      // 033: aload 1
      // 034: ifnonnull 03b
      // 037: iload 2
      // 038: ifne 053
      // 03b: aload 3
      // 03c: astore 4
      // 03e: aload 3
      // 03f: ifnonnull 049
      // 042: aload 0
      // 043: aload 1
      // 044: invokespecial kotlinx/coroutines/JobSupport.createCauseException (Ljava/lang/Object;)Ljava/lang/Throwable;
      // 047: astore 4
      // 049: aload 6
      // 04b: checkcast kotlinx/coroutines/JobSupport$Finishing
      // 04e: aload 4
      // 050: invokevirtual kotlinx/coroutines/JobSupport$Finishing.addExceptionLocked (Ljava/lang/Throwable;)V
      // 053: aload 6
      // 055: checkcast kotlinx/coroutines/JobSupport$Finishing
      // 058: invokevirtual kotlinx/coroutines/JobSupport$Finishing.getRootCause ()Ljava/lang/Throwable;
      // 05b: astore 3
      // 05c: aload 5
      // 05e: astore 1
      // 05f: iload 2
      // 060: ifne 065
      // 063: aload 3
      // 064: astore 1
      // 065: aload 6
      // 067: monitorexit
      // 068: aload 1
      // 069: ifnull 079
      // 06c: aload 0
      // 06d: aload 6
      // 06f: checkcast kotlinx/coroutines/JobSupport$Finishing
      // 072: invokevirtual kotlinx/coroutines/JobSupport$Finishing.getList ()Lkotlinx/coroutines/NodeList;
      // 075: aload 1
      // 076: invokespecial kotlinx/coroutines/JobSupport.notifyCancelling (Lkotlinx/coroutines/NodeList;Ljava/lang/Throwable;)V
      // 079: invokestatic kotlinx/coroutines/JobSupportKt.access$getCOMPLETING_ALREADY$p ()Lkotlinx/coroutines/internal/Symbol;
      // 07c: areturn
      // 07d: astore 1
      // 07e: aload 6
      // 080: monitorexit
      // 081: aload 1
      // 082: athrow
      // 083: aload 6
      // 085: instanceof kotlinx/coroutines/Incomplete
      // 088: ifeq 107
      // 08b: aload 3
      // 08c: astore 4
      // 08e: aload 3
      // 08f: ifnonnull 099
      // 092: aload 0
      // 093: aload 1
      // 094: invokespecial kotlinx/coroutines/JobSupport.createCauseException (Ljava/lang/Object;)Ljava/lang/Throwable;
      // 097: astore 4
      // 099: aload 6
      // 09b: checkcast kotlinx/coroutines/Incomplete
      // 09e: astore 7
      // 0a0: aload 7
      // 0a2: invokeinterface kotlinx/coroutines/Incomplete.isActive ()Z 1
      // 0a7: ifeq 0bc
      // 0aa: aload 4
      // 0ac: astore 3
      // 0ad: aload 0
      // 0ae: aload 7
      // 0b0: aload 4
      // 0b2: invokespecial kotlinx/coroutines/JobSupport.tryMakeCancelling (Lkotlinx/coroutines/Incomplete;Ljava/lang/Throwable;)Z
      // 0b5: ifeq 005
      // 0b8: invokestatic kotlinx/coroutines/JobSupportKt.access$getCOMPLETING_ALREADY$p ()Lkotlinx/coroutines/internal/Symbol;
      // 0bb: areturn
      // 0bc: aload 0
      // 0bd: aload 6
      // 0bf: new kotlinx/coroutines/CompletedExceptionally
      // 0c2: dup
      // 0c3: aload 4
      // 0c5: bipush 0
      // 0c6: bipush 2
      // 0c7: aconst_null
      // 0c8: invokespecial kotlinx/coroutines/CompletedExceptionally.<init> (Ljava/lang/Throwable;ZILkotlin/jvm/internal/DefaultConstructorMarker;)V
      // 0cb: invokespecial kotlinx/coroutines/JobSupport.tryMakeCompleting (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      // 0ce: astore 7
      // 0d0: aload 7
      // 0d2: invokestatic kotlinx/coroutines/JobSupportKt.access$getCOMPLETING_ALREADY$p ()Lkotlinx/coroutines/internal/Symbol;
      // 0d5: if_acmpeq 0e6
      // 0d8: aload 4
      // 0da: astore 3
      // 0db: aload 7
      // 0dd: invokestatic kotlinx/coroutines/JobSupportKt.access$getCOMPLETING_RETRY$p ()Lkotlinx/coroutines/internal/Symbol;
      // 0e0: if_acmpeq 005
      // 0e3: aload 7
      // 0e5: areturn
      // 0e6: new java/lang/StringBuilder
      // 0e9: dup
      // 0ea: ldc_w "Cannot happen in "
      // 0ed: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 0f0: astore 1
      // 0f1: aload 1
      // 0f2: aload 6
      // 0f4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 0f7: pop
      // 0f8: new java/lang/IllegalStateException
      // 0fb: dup
      // 0fc: aload 1
      // 0fd: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 100: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 103: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 106: athrow
      // 107: invokestatic kotlinx/coroutines/JobSupportKt.access$getTOO_LATE_TO_CANCEL$p ()Lkotlinx/coroutines/internal/Symbol;
      // 10a: areturn
   }

   private fun makeNode(handler: (Throwable?) -> Unit, onCancelling: Boolean): JobNode {
      var var3: JobCancellingNode = null;
      var var5: JobNode;
      if (var2) {
         if (var1 is JobCancellingNode) {
            var3 = var1 as JobCancellingNode;
         }

         var var7: JobCancellingNode = var3;
         if (var3 == null) {
            var7 = new InvokeOnCancelling(var1);
         }

         var5 = var7;
      } else {
         var var6: JobNode = null;
         if (var1 is JobNode) {
            var6 = var1 as JobNode;
         }

         if (var6 != null) {
            var5 = var6;
            if (DebugKt.getASSERTIONS_ENABLED()) {
               if (var6 is JobCancellingNode) {
                  throw new AssertionError();
               }

               var5 = var6;
            }
         } else {
            var5 = new InvokeOnCompletion(var1);
         }
      }

      var5.setJob(this);
      return var5;
   }

   private fun LockFreeLinkedListNode.nextChild(): ChildHandleNode? {
      while (true) {
         var var2: LockFreeLinkedListNode = var1;
         if (!var1.isRemoved()) {
            while (true) {
               var1 = var2.getNextNode();
               var2 = var1;
               if (!var1.isRemoved()) {
                  if (var1 is ChildHandleNode) {
                     return var1 as ChildHandleNode;
                  }

                  var2 = var1;
                  if (var1 is NodeList) {
                     return null;
                  }
               }
            }
         }

         var1 = var1.getPrevNode();
      }
   }

   private fun notifyCancelling(list: NodeList, cause: Throwable) {
      this.onCancelling(var2);
      val var5: LockFreeLinkedListHead = var1;
      var var11: LockFreeLinkedListNode = (LockFreeLinkedListNode)var1.getNext();
      var11 = var11;
      var var4: CompletionHandlerException = null;

      while (!(var11 == var5)) {
         var var3: CompletionHandlerException;
         var3 = var4;
         label24:
         if (var11 is JobCancellingNode) {
            val var6: JobNode = var11 as JobNode;

            try {
               var6.invoke(var2);
            } catch (var9: java.lang.Throwable) {
               val var8: java.lang.Throwable = var4;
               if (var4 != null) {
                  kotlin.ExceptionsKt.addSuppressed(var8, var9);
                  var3 = var4;
                  if (var8 != null) {
                     break label24;
                  }
               }

               val var14: StringBuilder = new StringBuilder("Exception in completion handler ");
               var14.append(var11 as JobNode);
               var14.append(" for ");
               var14.append(this);
               var3 = new CompletionHandlerException(var14.toString(), var9);
               break label24;
            }

            var3 = var4;
         }

         var11 = var11.getNextNode();
         var4 = var3;
      }

      val var13: java.lang.Throwable = var4;
      if (var4 != null) {
         this.handleOnCompletionException$kotlinx_coroutines_core(var13);
      }

      this.cancelParent(var2);
   }

   private fun NodeList.notifyCompletion(cause: Throwable?) {
      val var5: LockFreeLinkedListHead = var1;
      var var11: LockFreeLinkedListNode = (LockFreeLinkedListNode)var1.getNext();
      var11 = var11;
      var var3: CompletionHandlerException = null;

      while (!(var11 == var5)) {
         var var4: CompletionHandlerException;
         var4 = var3;
         label24:
         if (var11 is JobNode) {
            val var7: JobNode = var11 as JobNode;

            try {
               var7.invoke(var2);
            } catch (var9: java.lang.Throwable) {
               val var8: java.lang.Throwable = var3;
               if (var3 != null) {
                  kotlin.ExceptionsKt.addSuppressed(var8, var9);
                  var4 = var3;
                  if (var8 != null) {
                     break label24;
                  }
               }

               val var14: StringBuilder = new StringBuilder("Exception in completion handler ");
               var14.append(var11 as JobNode);
               var14.append(" for ");
               var14.append(this);
               var4 = new CompletionHandlerException(var14.toString(), var9);
               break label24;
            }

            var4 = var3;
         }

         var11 = var11.getNextNode();
         var3 = var4;
      }

      val var13: java.lang.Throwable = var3;
      if (var3 != null) {
         this.handleOnCompletionException$kotlinx_coroutines_core(var13);
      }
   }

   private fun onAwaitInternalProcessResFunc(ignoredParam: Any?, result: Any?): Any? {
      if (var2 !is CompletedExceptionally) {
         return var2;
      } else {
         throw (var2 as CompletedExceptionally).cause;
      }
   }

   private fun onAwaitInternalRegFunc(select: SelectInstance<*>, ignoredParam: Any?) {
      do {
         var2 = this.getState$kotlinx_coroutines_core();
         if (var2 !is Incomplete) {
            if (var2 !is CompletedExceptionally) {
               var2 = JobSupportKt.unboxState(var2);
            }

            var1.selectInRegistrationPhase(var2);
            return;
         }
      } while (this.startInternal(var2) < 0);

      var1.disposeOnCompletion(this.invokeOnCompletion(new JobSupport.SelectOnAwaitCompletionHandler(this, var1)));
   }

   private fun promoteEmptyToNodeList(state: Empty) {
      val var2: NodeList = new NodeList();
      val var3: Incomplete;
      if (var1.isActive()) {
         var3 = var2;
      } else {
         var3 = new InactiveNodeList(var2);
      }

      ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, var1, var3);
   }

   private fun promoteSingleToNodeList(state: JobNode) {
      var1.addOneIfEmpty(new NodeList());
      ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, var1, var1.getNextNode());
   }

   private fun registerSelectForOnJoin(select: SelectInstance<*>, ignoredParam: Any?) {
      if (!this.joinInternal()) {
         var1.selectInRegistrationPhase(Unit.INSTANCE);
      } else {
         var1.disposeOnCompletion(this.invokeOnCompletion(new JobSupport.SelectOnJoinCompletionHandler(this, var1)));
      }
   }

   private fun startInternal(state: Any?): Int {
      if (var1 is Empty) {
         if ((var1 as Empty).isActive()) {
            return 0;
         } else if (!ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, var1, JobSupportKt.access$getEMPTY_ACTIVE$p())) {
            return -1;
         } else {
            this.onStart();
            return 1;
         }
      } else if (var1 is InactiveNodeList) {
         if (!ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, var1, (var1 as InactiveNodeList).getList())) {
            return -1;
         } else {
            this.onStart();
            return 1;
         }
      } else {
         return 0;
      }
   }

   private fun stateString(state: Any?): String {
      if (var1 is JobSupport.Finishing) {
         val var4: JobSupport.Finishing = var1 as JobSupport.Finishing;
         if ((var1 as JobSupport.Finishing).isCancelling()) {
            var1 = "Cancelling";
         } else {
            var1 = "Active";
            if (var4.isCompleting()) {
               var1 = "Completing";
            }
         }
      } else if (var1 is Incomplete) {
         if ((var1 as Incomplete).isActive()) {
            var1 = "Active";
         } else {
            var1 = "New";
         }
      } else if (var1 is CompletedExceptionally) {
         var1 = "Cancelled";
      } else {
         var1 = "Completed";
      }

      return var1;
   }

   private fun tryFinalizeSimpleState(state: Incomplete, update: Any?): Boolean {
      if (DebugKt.getASSERTIONS_ENABLED() && var1 !is Empty && var1 !is JobNode) {
         throw new AssertionError();
      } else if (DebugKt.getASSERTIONS_ENABLED() && var2 is CompletedExceptionally) {
         throw new AssertionError();
      } else if (!ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, var1, JobSupportKt.boxIncomplete(var2))) {
         return false;
      } else {
         this.onCancelling(null);
         this.onCompletionInternal(var2);
         this.completeStateFinalization(var1, var2);
         return true;
      }
   }

   private fun tryMakeCancelling(state: Incomplete, rootCause: Throwable): Boolean {
      if (DebugKt.getASSERTIONS_ENABLED() && var1 is JobSupport.Finishing) {
         throw new AssertionError();
      } else if (DebugKt.getASSERTIONS_ENABLED() && !var1.isActive()) {
         throw new AssertionError();
      } else {
         val var3: NodeList = this.getOrPromoteCancellingList(var1);
         if (var3 == null) {
            return false;
         } else if (!ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, var1, new JobSupport.Finishing(var3, false, var2))) {
            return false;
         } else {
            this.notifyCancelling(var3, var2);
            return true;
         }
      }
   }

   private fun tryMakeCompleting(state: Any?, proposedUpdate: Any?): Any? {
      if (var1 !is Incomplete) {
         return JobSupportKt.access$getCOMPLETING_ALREADY$p();
      } else if ((var1 is Empty || var1 is JobNode) && var1 !is ChildHandleNode && var2 !is CompletedExceptionally) {
         return if (this.tryFinalizeSimpleState(var1 as Incomplete, var2)) var2 else JobSupportKt.access$getCOMPLETING_RETRY$p();
      } else {
         return this.tryMakeCompletingSlowPath(var1 as Incomplete, var2);
      }
   }

   private fun tryMakeCompletingSlowPath(state: Incomplete, proposedUpdate: Any?): Any? {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 000: aload 0
      // 001: aload 1
      // 002: invokespecial kotlinx/coroutines/JobSupport.getOrPromoteCancellingList (Lkotlinx/coroutines/Incomplete;)Lkotlinx/coroutines/NodeList;
      // 005: astore 8
      // 007: aload 8
      // 009: ifnonnull 010
      // 00c: invokestatic kotlinx/coroutines/JobSupportKt.access$getCOMPLETING_RETRY$p ()Lkotlinx/coroutines/internal/Symbol;
      // 00f: areturn
      // 010: aload 1
      // 011: instanceof kotlinx/coroutines/JobSupport$Finishing
      // 014: istore 3
      // 015: aconst_null
      // 016: astore 6
      // 018: iload 3
      // 019: ifeq 025
      // 01c: aload 1
      // 01d: checkcast kotlinx/coroutines/JobSupport$Finishing
      // 020: astore 4
      // 022: goto 028
      // 025: aconst_null
      // 026: astore 4
      // 028: aload 4
      // 02a: astore 5
      // 02c: aload 4
      // 02e: ifnonnull 03e
      // 031: new kotlinx/coroutines/JobSupport$Finishing
      // 034: dup
      // 035: aload 8
      // 037: bipush 0
      // 038: aconst_null
      // 039: invokespecial kotlinx/coroutines/JobSupport$Finishing.<init> (Lkotlinx/coroutines/NodeList;ZLjava/lang/Throwable;)V
      // 03c: astore 5
      // 03e: new kotlin/jvm/internal/Ref$ObjectRef
      // 041: dup
      // 042: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 045: astore 9
      // 047: aload 5
      // 049: monitorenter
      // 04a: aload 5
      // 04c: invokevirtual kotlinx/coroutines/JobSupport$Finishing.isCompleting ()Z
      // 04f: ifeq 05b
      // 052: invokestatic kotlinx/coroutines/JobSupportKt.access$getCOMPLETING_ALREADY$p ()Lkotlinx/coroutines/internal/Symbol;
      // 055: astore 1
      // 056: aload 5
      // 058: monitorexit
      // 059: aload 1
      // 05a: areturn
      // 05b: aload 5
      // 05d: bipush 1
      // 05e: invokevirtual kotlinx/coroutines/JobSupport$Finishing.setCompleting (Z)V
      // 061: aload 5
      // 063: aload 1
      // 064: if_acmpeq 07d
      // 067: getstatic kotlinx/coroutines/JobSupport._state$FU Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
      // 06a: aload 0
      // 06b: aload 1
      // 06c: aload 5
      // 06e: invokestatic androidx/concurrent/futures/AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m (Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Z
      // 071: ifne 07d
      // 074: invokestatic kotlinx/coroutines/JobSupportKt.access$getCOMPLETING_RETRY$p ()Lkotlinx/coroutines/internal/Symbol;
      // 077: astore 1
      // 078: aload 5
      // 07a: monitorexit
      // 07b: aload 1
      // 07c: areturn
      // 07d: invokestatic kotlinx/coroutines/DebugKt.getASSERTIONS_ENABLED ()Z
      // 080: ifeq 098
      // 083: aload 5
      // 085: invokevirtual kotlinx/coroutines/JobSupport$Finishing.isSealed ()Z
      // 088: ifne 08e
      // 08b: goto 098
      // 08e: new java/lang/AssertionError
      // 091: astore 1
      // 092: aload 1
      // 093: invokespecial java/lang/AssertionError.<init> ()V
      // 096: aload 1
      // 097: athrow
      // 098: aload 5
      // 09a: invokevirtual kotlinx/coroutines/JobSupport$Finishing.isCancelling ()Z
      // 09d: istore 3
      // 09e: aload 2
      // 09f: instanceof kotlinx/coroutines/CompletedExceptionally
      // 0a2: ifeq 0ae
      // 0a5: aload 2
      // 0a6: checkcast kotlinx/coroutines/CompletedExceptionally
      // 0a9: astore 4
      // 0ab: goto 0b1
      // 0ae: aconst_null
      // 0af: astore 4
      // 0b1: aload 4
      // 0b3: ifnull 0c0
      // 0b6: aload 5
      // 0b8: aload 4
      // 0ba: getfield kotlinx/coroutines/CompletedExceptionally.cause Ljava/lang/Throwable;
      // 0bd: invokevirtual kotlinx/coroutines/JobSupport$Finishing.addExceptionLocked (Ljava/lang/Throwable;)V
      // 0c0: aload 5
      // 0c2: invokevirtual kotlinx/coroutines/JobSupport$Finishing.getRootCause ()Ljava/lang/Throwable;
      // 0c5: astore 7
      // 0c7: bipush 1
      // 0c8: iload 3
      // 0c9: ixor
      // 0ca: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0cd: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 0d0: pop
      // 0d1: aload 6
      // 0d3: astore 4
      // 0d5: iload 3
      // 0d6: ifne 0dd
      // 0d9: aload 7
      // 0db: astore 4
      // 0dd: aload 9
      // 0df: aload 4
      // 0e1: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 0e4: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0e7: astore 4
      // 0e9: aload 5
      // 0eb: monitorexit
      // 0ec: aload 9
      // 0ee: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 0f1: checkcast java/lang/Throwable
      // 0f4: astore 4
      // 0f6: aload 4
      // 0f8: ifnull 103
      // 0fb: aload 0
      // 0fc: aload 8
      // 0fe: aload 4
      // 100: invokespecial kotlinx/coroutines/JobSupport.notifyCancelling (Lkotlinx/coroutines/NodeList;Ljava/lang/Throwable;)V
      // 103: aload 0
      // 104: aload 1
      // 105: invokespecial kotlinx/coroutines/JobSupport.firstChild (Lkotlinx/coroutines/Incomplete;)Lkotlinx/coroutines/ChildHandleNode;
      // 108: astore 1
      // 109: aload 1
      // 10a: ifnull 11c
      // 10d: aload 0
      // 10e: aload 5
      // 110: aload 1
      // 111: aload 2
      // 112: invokespecial kotlinx/coroutines/JobSupport.tryWaitForChild (Lkotlinx/coroutines/JobSupport$Finishing;Lkotlinx/coroutines/ChildHandleNode;Ljava/lang/Object;)Z
      // 115: ifeq 11c
      // 118: getstatic kotlinx/coroutines/JobSupportKt.COMPLETING_WAITING_CHILDREN Lkotlinx/coroutines/internal/Symbol;
      // 11b: areturn
      // 11c: aload 0
      // 11d: aload 5
      // 11f: aload 2
      // 120: invokespecial kotlinx/coroutines/JobSupport.finalizeFinishingState (Lkotlinx/coroutines/JobSupport$Finishing;Ljava/lang/Object;)Ljava/lang/Object;
      // 123: areturn
      // 124: astore 1
      // 125: aload 5
      // 127: monitorexit
      // 128: aload 1
      // 129: athrow
   }

   private tailrec fun tryWaitForChild(state: kotlinx.coroutines.JobSupport.Finishing, child: ChildHandleNode, proposedUpdate: Any?): Boolean {
      while (
         Job.DefaultImpls.invokeOnCompletion$default(var2.childJob, false, false, new JobSupport.ChildCompletion(this, var1, var2, var3), 1, null)
            == NonDisposableHandle.INSTANCE
      ) {
         val var4: ChildHandleNode = this.nextChild(var2);
         var2 = var4;
         if (var4 == null) {
            return false;
         }
      }

      return true;
   }

   protected open fun afterCompletion(state: Any?) {
   }

   public override fun attachChild(child: ChildJob): ChildHandle {
      val var2: DisposableHandle = Job.DefaultImpls.invokeOnCompletion$default(this, true, false, new ChildHandleNode(var1), 2, null);
      return var2 as ChildHandle;
   }

   protected suspend fun awaitInternal(): Any? {
      var var2: Any;
      do {
         var2 = this.getState$kotlinx_coroutines_core();
         if (var2 !is Incomplete) {
            if (var2 is CompletedExceptionally) {
               var2 = (var2 as CompletedExceptionally).cause;
               if (DebugKt.getRECOVER_STACK_TRACES()) {
                  if (var1 !is CoroutineStackFrame) {
                     throw var2;
                  }

                  throw StackTraceRecoveryKt.access$recoverFromStackFrame((java.lang.Throwable)var2, var1 as CoroutineStackFrame);
               }

               throw var2;
            }

            return JobSupportKt.unboxState(var2);
         }
      } while (this.startInternal(var2) < 0);

      return this.awaitSuspend(var1);
   }

   public override fun cancel(cause: CancellationException?) {
      var var2: CancellationException = var1;
      if (var1 == null) {
         var2 = new JobCancellationException(access$cancellationExceptionMessage(this), null, this);
      }

      this.cancelInternal(var2);
   }

   public fun cancelCoroutine(cause: Throwable?): Boolean {
      return this.cancelImpl$kotlinx_coroutines_core(var1);
   }

   internal fun cancelImpl(cause: Any?): Boolean {
      var var4: Any = JobSupportKt.access$getCOMPLETING_ALREADY$p();
      val var3: Boolean = this.getOnCancelComplete$kotlinx_coroutines_core();
      var var2: Boolean = true;
      if (var3) {
         val var5: Any = this.cancelMakeCompleting(var1);
         var4 = var5;
         if (var5 === JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return true;
         }
      }

      var var6: Any = var4;
      if (var4 === JobSupportKt.access$getCOMPLETING_ALREADY$p()) {
         var6 = this.makeCancelling(var1);
      }

      if (var6 != JobSupportKt.access$getCOMPLETING_ALREADY$p() && var6 != JobSupportKt.COMPLETING_WAITING_CHILDREN) {
         if (var6 === JobSupportKt.access$getTOO_LATE_TO_CANCEL$p()) {
            var2 = false;
         } else {
            this.afterCompletion(var6);
         }
      }

      return var2;
   }

   public open fun cancelInternal(cause: Throwable) {
      this.cancelImpl$kotlinx_coroutines_core(var1);
   }

   protected open fun cancellationExceptionMessage(): String {
      return "Job was cancelled";
   }

   public open fun childCancelled(cause: Throwable): Boolean {
      val var3: Boolean = var1 is CancellationException;
      var var2: Boolean = true;
      if (var3) {
         return true;
      } else {
         if (!this.cancelImpl$kotlinx_coroutines_core(var1) || !this.getHandlesException$kotlinx_coroutines_core()) {
            var2 = false;
         }

         return var2;
      }
   }

   internal inline fun defaultCancellationException(message: String? = ..., cause: Throwable? = ...): JobCancellationException {
      var var3: java.lang.String = var1;
      if (var1 == null) {
         var3 = access$cancellationExceptionMessage(this);
      }

      return new JobCancellationException(var3, var2, this);
   }

   override fun <R> fold(var1: R, var2: (R?, CoroutineContext.Element?) -> R): R {
      return Job.DefaultImpls.fold(this, (R)var1, var2);
   }

   override fun <E extends CoroutineContext.Element> get(var1: CoroutineContextKey<E>): E {
      return Job.DefaultImpls.get(this, var1);
   }

   public override fun getCancellationException(): CancellationException {
      label23: {
         var var1: CancellationException = (CancellationException)this.getState$kotlinx_coroutines_core();
         if (var1 is JobSupport.Finishing) {
            val var2: java.lang.Throwable = (var1 as JobSupport.Finishing).getRootCause();
            if (var2 == null) {
               break label23;
            }

            val var3: StringBuilder = new StringBuilder();
            var3.append(DebugStringsKt.getClassSimpleName(this));
            var3.append(" is cancelling");
            var1 = this.toCancellationException(var2, var3.toString());
            if (var1 == null) {
               break label23;
            }
         } else {
            if (var1 is Incomplete) {
               val var7: StringBuilder = new StringBuilder("Job is still new or active: ");
               var7.append(this);
               throw new IllegalStateException(var7.toString().toString());
            }

            if (var1 is CompletedExceptionally) {
               var1 = toCancellationException$default(this, (var1 as CompletedExceptionally).cause, null, 1, null);
            } else {
               val var6: StringBuilder = new StringBuilder();
               var6.append(DebugStringsKt.getClassSimpleName(this));
               var6.append(" has completed normally");
               var1 = new JobCancellationException(var6.toString(), null, this);
            }
         }

         return var1;
      }

      val var5: StringBuilder = new StringBuilder("Job is still new or active: ");
      var5.append(this);
      throw new IllegalStateException(var5.toString().toString());
   }

   public override fun getChildJobCancellationCause(): CancellationException {
      val var5: Any = this.getState$kotlinx_coroutines_core();
      val var1: Boolean = var5 is JobSupport.Finishing;
      var var3: CancellationException = null;
      val var2: java.lang.Throwable;
      if (var1) {
         var2 = (var5 as JobSupport.Finishing).getRootCause();
      } else if (var5 is CompletedExceptionally) {
         var2 = (var5 as CompletedExceptionally).cause;
      } else {
         if (var5 is Incomplete) {
            val var6: StringBuilder = new StringBuilder("Cannot be cancelling child in this state: ");
            var6.append(var5);
            throw new IllegalStateException(var6.toString().toString());
         }

         var2 = null;
      }

      if (var2 is CancellationException) {
         var3 = var2 as CancellationException;
      }

      var var4: CancellationException = var3;
      if (var3 == null) {
         val var7: StringBuilder = new StringBuilder("Parent job is ");
         var7.append(this.stateString(var5));
         var4 = new JobCancellationException(var7.toString(), var2, this);
      }

      return var4;
   }

   internal fun getCompletedInternal(): Any? {
      val var1: Any = this.getState$kotlinx_coroutines_core();
      if (var1 !is Incomplete) {
         if (var1 !is CompletedExceptionally) {
            return JobSupportKt.unboxState(var1);
         } else {
            throw (var1 as CompletedExceptionally).cause;
         }
      } else {
         throw new IllegalStateException("This job has not completed yet".toString());
      }
   }

   public fun getCompletionExceptionOrNull(): Throwable? {
      val var1: Any = this.getState$kotlinx_coroutines_core();
      if (var1 !is Incomplete) {
         return this.getExceptionOrNull(var1);
      } else {
         throw new IllegalStateException("This job has not completed yet".toString());
      }
   }

   protected open fun handleJobException(exception: Throwable): Boolean {
      return false;
   }

   internal open fun handleOnCompletionException(exception: Throwable) {
      throw var1;
   }

   protected fun initParentJob(parent: Job?) {
      if (DebugKt.getASSERTIONS_ENABLED() && this.getParentHandle$kotlinx_coroutines_core() != null) {
         throw new AssertionError();
      } else if (var1 == null) {
         this.setParentHandle$kotlinx_coroutines_core(NonDisposableHandle.INSTANCE);
      } else {
         var1.start();
         val var2: ChildHandle = var1.attachChild(this);
         this.setParentHandle$kotlinx_coroutines_core(var2);
         if (this.isCompleted()) {
            var2.dispose();
            this.setParentHandle$kotlinx_coroutines_core(NonDisposableHandle.INSTANCE);
         }
      }
   }

   public override fun invokeOnCompletion(handler: (Throwable?) -> Unit): DisposableHandle {
      return this.invokeOnCompletion(false, true, var1);
   }

   public override fun invokeOnCompletion(onCancelling: Boolean, invokeImmediately: Boolean, handler: (Throwable?) -> Unit): DisposableHandle {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 000: aload 0
      // 001: aload 3
      // 002: iload 1
      // 003: invokespecial kotlinx/coroutines/JobSupport.makeNode (Lkotlin/jvm/functions/Function1;Z)Lkotlinx/coroutines/JobNode;
      // 006: astore 8
      // 008: aload 0
      // 009: invokevirtual kotlinx/coroutines/JobSupport.getState$kotlinx_coroutines_core ()Ljava/lang/Object;
      // 00c: astore 10
      // 00e: aload 10
      // 010: instanceof kotlinx/coroutines/Empty
      // 013: ifeq 042
      // 016: aload 10
      // 018: checkcast kotlinx/coroutines/Empty
      // 01b: astore 5
      // 01d: aload 5
      // 01f: invokevirtual kotlinx/coroutines/Empty.isActive ()Z
      // 022: ifeq 039
      // 025: getstatic kotlinx/coroutines/JobSupport._state$FU Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
      // 028: aload 0
      // 029: aload 10
      // 02b: aload 8
      // 02d: invokestatic androidx/concurrent/futures/AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m (Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Z
      // 030: ifeq 008
      // 033: aload 8
      // 035: checkcast kotlinx/coroutines/DisposableHandle
      // 038: areturn
      // 039: aload 0
      // 03a: aload 5
      // 03c: invokespecial kotlinx/coroutines/JobSupport.promoteEmptyToNodeList (Lkotlinx/coroutines/Empty;)V
      // 03f: goto 008
      // 042: aload 10
      // 044: instanceof kotlinx/coroutines/Incomplete
      // 047: istore 4
      // 049: aconst_null
      // 04a: astore 6
      // 04c: aconst_null
      // 04d: astore 9
      // 04f: iload 4
      // 051: ifeq 12d
      // 054: aload 10
      // 056: checkcast kotlinx/coroutines/Incomplete
      // 059: invokeinterface kotlinx/coroutines/Incomplete.getList ()Lkotlinx/coroutines/NodeList; 1
      // 05e: astore 11
      // 060: aload 11
      // 062: ifnonnull 079
      // 065: aload 10
      // 067: ldc_w "null cannot be cast to non-null type kotlinx.coroutines.JobNode"
      // 06a: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 06d: aload 0
      // 06e: aload 10
      // 070: checkcast kotlinx/coroutines/JobNode
      // 073: invokespecial kotlinx/coroutines/JobSupport.promoteSingleToNodeList (Lkotlinx/coroutines/JobNode;)V
      // 076: goto 008
      // 079: getstatic kotlinx/coroutines/NonDisposableHandle.INSTANCE Lkotlinx/coroutines/NonDisposableHandle;
      // 07c: astore 6
      // 07e: aload 9
      // 080: astore 7
      // 082: aload 6
      // 084: astore 5
      // 086: iload 1
      // 087: ifeq 102
      // 08a: aload 9
      // 08c: astore 7
      // 08e: aload 6
      // 090: astore 5
      // 092: aload 10
      // 094: instanceof kotlinx/coroutines/JobSupport$Finishing
      // 097: ifeq 102
      // 09a: aload 10
      // 09c: monitorenter
      // 09d: aload 10
      // 09f: checkcast kotlinx/coroutines/JobSupport$Finishing
      // 0a2: invokevirtual kotlinx/coroutines/JobSupport$Finishing.getRootCause ()Ljava/lang/Throwable;
      // 0a5: astore 7
      // 0a7: aload 7
      // 0a9: ifnull 0c6
      // 0ac: aload 6
      // 0ae: astore 5
      // 0b0: aload 3
      // 0b1: instanceof kotlinx/coroutines/ChildHandleNode
      // 0b4: ifeq 0f1
      // 0b7: aload 6
      // 0b9: astore 5
      // 0bb: aload 10
      // 0bd: checkcast kotlinx/coroutines/JobSupport$Finishing
      // 0c0: invokevirtual kotlinx/coroutines/JobSupport$Finishing.isCompleting ()Z
      // 0c3: ifne 0f1
      // 0c6: aload 0
      // 0c7: aload 10
      // 0c9: aload 11
      // 0cb: aload 8
      // 0cd: invokespecial kotlinx/coroutines/JobSupport.addLastAtomic (Ljava/lang/Object;Lkotlinx/coroutines/NodeList;Lkotlinx/coroutines/JobNode;)Z
      // 0d0: istore 4
      // 0d2: iload 4
      // 0d4: ifne 0dd
      // 0d7: aload 10
      // 0d9: monitorexit
      // 0da: goto 008
      // 0dd: aload 7
      // 0df: ifnonnull 0ed
      // 0e2: aload 8
      // 0e4: checkcast kotlinx/coroutines/DisposableHandle
      // 0e7: astore 3
      // 0e8: aload 10
      // 0ea: monitorexit
      // 0eb: aload 3
      // 0ec: areturn
      // 0ed: aload 8
      // 0ef: astore 5
      // 0f1: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0f4: astore 6
      // 0f6: aload 10
      // 0f8: monitorexit
      // 0f9: goto 102
      // 0fc: astore 3
      // 0fd: aload 10
      // 0ff: monitorexit
      // 100: aload 3
      // 101: athrow
      // 102: aload 7
      // 104: ifnull 11a
      // 107: iload 2
      // 108: ifeq 114
      // 10b: aload 3
      // 10c: aload 7
      // 10e: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 113: pop
      // 114: aload 5
      // 116: checkcast kotlinx/coroutines/DisposableHandle
      // 119: areturn
      // 11a: aload 0
      // 11b: aload 10
      // 11d: aload 11
      // 11f: aload 8
      // 121: invokespecial kotlinx/coroutines/JobSupport.addLastAtomic (Ljava/lang/Object;Lkotlinx/coroutines/NodeList;Lkotlinx/coroutines/JobNode;)Z
      // 124: ifeq 008
      // 127: aload 8
      // 129: checkcast kotlinx/coroutines/DisposableHandle
      // 12c: areturn
      // 12d: iload 2
      // 12e: ifeq 15b
      // 131: aload 10
      // 133: instanceof kotlinx/coroutines/CompletedExceptionally
      // 136: ifeq 143
      // 139: aload 10
      // 13b: checkcast kotlinx/coroutines/CompletedExceptionally
      // 13e: astore 5
      // 140: goto 146
      // 143: aconst_null
      // 144: astore 5
      // 146: aload 5
      // 148: ifnull 152
      // 14b: aload 5
      // 14d: getfield kotlinx/coroutines/CompletedExceptionally.cause Ljava/lang/Throwable;
      // 150: astore 6
      // 152: aload 3
      // 153: aload 6
      // 155: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 15a: pop
      // 15b: getstatic kotlinx/coroutines/NonDisposableHandle.INSTANCE Lkotlinx/coroutines/NonDisposableHandle;
      // 15e: checkcast kotlinx/coroutines/DisposableHandle
      // 161: areturn
   }

   public override suspend fun join() {
      if (!this.joinInternal()) {
         JobKt.ensureActive(var1.getContext());
         return Unit.INSTANCE;
      } else {
         val var2: Any = this.joinSuspend(var1);
         return if (var2 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var2 else Unit.INSTANCE;
      }
   }

   internal fun makeCompleting(proposedUpdate: Any?): Boolean {
      val var2: Any;
      do {
         var2 = this.tryMakeCompleting(this.getState$kotlinx_coroutines_core(), var1);
         if (var2 === JobSupportKt.access$getCOMPLETING_ALREADY$p()) {
            return false;
         }

         if (var2 === JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return true;
         }
      } while (var2 == JobSupportKt.access$getCOMPLETING_RETRY$p());

      this.afterCompletion(var2);
      return true;
   }

   internal fun makeCompletingOnce(proposedUpdate: Any?): Any? {
      while (true) {
         var var2: StringBuilder = (StringBuilder)this.tryMakeCompleting(this.getState$kotlinx_coroutines_core(), var1);
         if (var2 != JobSupportKt.access$getCOMPLETING_ALREADY$p()) {
            if (var2 === JobSupportKt.access$getCOMPLETING_RETRY$p()) {
               continue;
            }

            return var2;
         }

         var2 = new StringBuilder("Job ");
         var2.append(this);
         var2.append(" is already complete or completing, but is being completed with ");
         var2.append(var1);
         throw new IllegalStateException(var2.toString(), this.getExceptionOrNull(var1));
      }
   }

   override fun minusKey(var1: CoroutineContextKey<?>): CoroutineContext {
      return Job.DefaultImpls.minusKey(this, var1);
   }

   internal open fun nameString(): String {
      return DebugStringsKt.getClassSimpleName(this);
   }

   protected open fun onCancelling(cause: Throwable?) {
   }

   protected open fun onCompletionInternal(state: Any?) {
   }

   protected open fun onStart() {
   }

   public override fun parentCancelled(parentJob: ParentJob) {
      this.cancelImpl$kotlinx_coroutines_core(var1);
   }

   override fun plus(var1: CoroutineContext): CoroutineContext {
      return Job.DefaultImpls.plus(this, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
   override fun plus(var1: Job): Job {
      return Job.DefaultImpls.plus(this, var1);
   }

   internal fun removeNode(node: JobNode) {
      while (true) {
         val var2: Any = this.getState$kotlinx_coroutines_core();
         if (var2 is JobNode) {
            if (var2 != var1) {
               return;
            }

            if (!ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, var2, JobSupportKt.access$getEMPTY_ACTIVE$p())) {
               continue;
            }

            return;
         }

         if (var2 is Incomplete && (var2 as Incomplete).getList() != null) {
            var1.remove();
         }

         return;
      }
   }

   public override fun start(): Boolean {
      while (true) {
         val var1: Int = this.startInternal(this.getState$kotlinx_coroutines_core());
         if (var1 != 0) {
            if (var1 != 1) {
               continue;
            }

            return true;
         }

         return false;
      }
   }

   protected fun Throwable.toCancellationException(message: String? = null): CancellationException {
      val var3: CancellationException;
      if (var1 is CancellationException) {
         var3 = var1 as CancellationException;
      } else {
         var3 = null;
      }

      var var4: CancellationException = var3;
      if (var3 == null) {
         var var5: java.lang.String = var2;
         if (var2 == null) {
            var5 = access$cancellationExceptionMessage(this);
         }

         var4 = new JobCancellationException(var5, var1, this);
      }

      return var4;
   }

   public fun toDebugString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this.nameString$kotlinx_coroutines_core());
      var1.append('{');
      var1.append(this.stateString(this.getState$kotlinx_coroutines_core()));
      var1.append('}');
      return var1.toString();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this.toDebugString());
      var1.append('@');
      var1.append(DebugStringsKt.getHexAddress(this));
      return var1.toString();
   }

   private class AwaitContinuation<T>(delegate: Continuation<Any>, job: JobSupport) : CancellableContinuationImpl(var1, 1) {
      private final val job: JobSupport

      init {
         this.job = var2;
      }

      public override fun getContinuationCancellationCause(parent: Job): Throwable {
         val var3: Any = this.job.getState$kotlinx_coroutines_core();
         if (var3 is JobSupport.Finishing) {
            val var2: java.lang.Throwable = (var3 as JobSupport.Finishing).getRootCause();
            if (var2 != null) {
               return var2;
            }
         }

         return if (var3 is CompletedExceptionally) (var3 as CompletedExceptionally).cause else var1.getCancellationException();
      }

      protected override fun nameString(): String {
         return "AwaitContinuation";
      }
   }

   private class ChildCompletion(parent: JobSupport, state: kotlinx.coroutines.JobSupport.Finishing, child: ChildHandleNode, proposedUpdate: Any?) : JobNode {
      private final val child: ChildHandleNode
      private final val parent: JobSupport
      private final val proposedUpdate: Any?
      private final val state: kotlinx.coroutines.JobSupport.Finishing

      init {
         this.parent = var1;
         this.state = var2;
         this.child = var3;
         this.proposedUpdate = var4;
      }

      public override operator fun invoke(cause: Throwable?) {
         JobSupport.access$continueCompleting(this.parent, this.state, this.child, this.proposedUpdate);
      }
   }

   private class Finishing(list: NodeList, isCompleting: Boolean, rootCause: Throwable?) : Incomplete {
      private final val _exceptionsHolder: AtomicRef<Any?>
      private final val _isCompleting: AtomicBoolean
      private final val _rootCause: AtomicRef<Throwable?>

      private final var exceptionsHolder: Any?
         private final get() {
            return _exceptionsHolder$FU.get(this);
         }

         private final set(value) {
            _exceptionsHolder$FU.set(this, var1);
         }


      public open val isActive: Boolean
         public open get() {
            val var1: Boolean;
            if (this.getRootCause() == null) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }


      public final val isCancelling: Boolean
         public final get() {
            val var1: Boolean;
            if (this.getRootCause() != null) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }


      public final var isCompleting: Boolean
         public final get() {
            val var1: Boolean;
            if (_isCompleting$FU.get(this) != 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public final set(value) {
            _isCompleting$FU.set(this, var1);
         }


      public final val isSealed: Boolean
         public final get() {
            val var1: Boolean;
            if (this.getExceptionsHolder() === JobSupportKt.access$getSEALED$p()) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }


      public open val list: NodeList

      public final var rootCause: Throwable?
         public final get() {
            return _rootCause$FU.get(this) as java.lang.Throwable;
         }

         public final set(value) {
            _rootCause$FU.set(this, var1);
         }


      init {
         this.list = var1;
         this._isCompleting = var2;
         this._rootCause = var3;
      }

      private fun allocateList(): ArrayList<Throwable> {
         return new ArrayList<>(4);
      }

      public fun addExceptionLocked(exception: Throwable) {
         var var2: java.lang.Throwable = this.getRootCause();
         if (var2 == null) {
            this.setRootCause(var1);
         } else if (var1 != var2) {
            var2 = (java.lang.Throwable)this.getExceptionsHolder();
            if (var2 == null) {
               this.setExceptionsHolder(var1);
            } else if (var2 is java.lang.Throwable) {
               if (var1 === var2) {
                  return;
               }

               val var3: ArrayList = this.allocateList();
               var3.add(var2);
               var3.add(var1);
               this.setExceptionsHolder(var3);
            } else {
               if (var2 !is ArrayList) {
                  val var4: StringBuilder = new StringBuilder("State is ");
                  var4.append(var2);
                  throw new IllegalStateException(var4.toString().toString());
               }

               (var2 as ArrayList).add(var1);
            }
         }
      }

      public fun sealLocked(proposedException: Throwable?): List<Throwable> {
         var var3: Any = this.getExceptionsHolder();
         val var2: ArrayList;
         if (var3 == null) {
            var2 = this.allocateList();
         } else if (var3 is java.lang.Throwable) {
            var2 = this.allocateList();
            var2.add(var3);
         } else {
            if (var3 !is ArrayList) {
               val var4: StringBuilder = new StringBuilder("State is ");
               var4.append(var3);
               throw new IllegalStateException(var4.toString().toString());
            }

            var2 = var3 as ArrayList;
         }

         var3 = this.getRootCause();
         if (var3 != null) {
            var2.add(0, var3);
         }

         if (var1 != null && !(var1 == var3)) {
            var2.add(var1);
         }

         this.setExceptionsHolder(JobSupportKt.access$getSEALED$p());
         return var2;
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder("Finishing[cancelling=");
         var1.append(this.isCancelling());
         var1.append(", completing=");
         var1.append(this.isCompleting());
         var1.append(", rootCause=");
         var1.append(this.getRootCause());
         var1.append(", exceptions=");
         var1.append(this.getExceptionsHolder());
         var1.append(", list=");
         var1.append(this.getList());
         var1.append(']');
         return var1.toString();
      }
   }

   private inner class SelectOnAwaitCompletionHandler(select: SelectInstance<*>) : JobNode {
      private final val select: SelectInstance<*>

      init {
         this.this$0 = var1;
         this.select = var2;
      }

      public override operator fun invoke(cause: Throwable?) {
         var var2: Any = this.this$0.getState$kotlinx_coroutines_core();
         if (var2 !is CompletedExceptionally) {
            var2 = JobSupportKt.unboxState(var2);
         }

         this.select.trySelect(this.this$0, var2);
      }
   }

   private inner class SelectOnJoinCompletionHandler(select: SelectInstance<*>) : JobNode {
      private final val select: SelectInstance<*>

      init {
         this.this$0 = var1;
         this.select = var2;
      }

      public override operator fun invoke(cause: Throwable?) {
         this.select.trySelect(this.this$0, Unit.INSTANCE);
      }
   }
}
