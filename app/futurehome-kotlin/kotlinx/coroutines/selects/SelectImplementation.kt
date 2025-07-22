package kotlinx.coroutines.selects

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.ArrayList
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.CancelHandler
import kotlinx.coroutines.CancelHandlerBase
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CancellableContinuationImpl
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.internal.Segment

internal open class SelectImplementation<R>(context: CoroutineContext) : CancelHandler, SelectBuilder<R>, SelectInstanceInternal<R> {
   private final var clauses: MutableList<kotlinx.coroutines.selects.SelectImplementation.ClauseData>?
   public open val context: CoroutineContext
   private final var disposableHandleOrSegment: Any?

   private final val inRegistrationPhase: Boolean
      private final get() {
         val var2: Any = state$FU.get(this);
         val var1: Boolean;
         if (var2 != SelectKt.access$getSTATE_REG$p() && var2 !is java.util.List) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }


   private final var indexInSegment: Int
   private final var internalResult: Any?

   private final val isCancelled: Boolean
      private final get() {
         val var1: Boolean;
         if (state$FU.get(this) === SelectKt.access$getSTATE_CANCELLED$p()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   private final val isSelected: Boolean
      private final get() {
         return state$FU.get(this) is SelectImplementation.ClauseData;
      }


   private final val state: AtomicRef<Any>

   init {
      this.context = var1;
      this.state = SelectKt.access$getSTATE_REG$p();
      this.clauses = new ArrayList<>(2);
      this.indexInSegment = -1;
      this.internalResult = SelectKt.access$getNO_RESULT$p();
   }

   private fun checkClauseObject(clauseObject: Any) {
      val var2: java.util.List = this.clauses;
      val var3: java.lang.Iterable = var2;
      if (var2 !is java.util.Collection || !(var2 as java.util.Collection).isEmpty()) {
         val var4: java.util.Iterator = var3.iterator();

         while (var4.hasNext()) {
            if ((var4.next() as SelectImplementation.ClauseData).clauseObject === var1) {
               val var5: StringBuilder = new StringBuilder("Cannot use select clauses on the same object: ");
               var5.append(var1);
               throw new IllegalStateException(var5.toString().toString());
            }
         }
      }
   }

   private fun cleanup(selectedClause: kotlinx.coroutines.selects.SelectImplementation.ClauseData) {
      if (DebugKt.getASSERTIONS_ENABLED() && !(state$FU.get(this) == var1)) {
         throw new AssertionError();
      } else if (this.clauses != null) {
         for (SelectImplementation.ClauseData var4 : this.clauses) {
            if (var4 != var1) {
               var4.dispose();
            }
         }

         state$FU.set(this, SelectKt.access$getSTATE_COMPLETED$p());
         this.internalResult = SelectKt.access$getNO_RESULT$p();
         this.clauses = null;
      }
   }

   private suspend fun complete(): Any {
      if (DebugKt.getASSERTIONS_ENABLED() && !this.isSelected()) {
         throw new AssertionError();
      } else {
         var var2: Any = state$FU.get(this);
         var2 = var2 as SelectImplementation.ClauseData;
         val var3: Any = this.internalResult;
         this.cleanup((SelectImplementation<R>.ClauseData)var2);
         return if (!DebugKt.getRECOVER_STACK_TRACES())
            ((SelectImplementation.ClauseData)var2).invokeBlock(((SelectImplementation.ClauseData)var2).processResult(var3), var1)
            else
            this.processResultAndInvokeBlockRecoveringException((SelectImplementation<R>.ClauseData)var2, var3, var1);
      }
   }

   private suspend fun doSelectSuspend(): Any {
      label34: {
         if (var1 is <unrepresentable>) {
            val var3: <unrepresentable> = var1 as <unrepresentable>;
            if ((var1.label and Integer.MIN_VALUE) != 0) {
               var3.label += Integer.MIN_VALUE;
               var1 = var3;
               break label34;
            }
         }

         var1 = new ContinuationImpl(this, var1) {
            Object L$0;
            int label;
            Object result;
            final SelectImplementation<R> this$0;

            {
               super(var2);
               this.this$0 = var1;
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return SelectImplementation.access$doSelectSuspend(this.this$0, this);
            }
         };
      }

      val var4: Any = var1.result;
      val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      val var8: SelectImplementation;
      if (var1.label != 0) {
         if (var1.label != 1) {
            if (var1.label != 2) {
               throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            ResultKt.throwOnFailure(var4);
            return var4;
         }

         var8 = var1.L$0 as SelectImplementation;
         ResultKt.throwOnFailure(var4);
      } else {
         ResultKt.throwOnFailure(var4);
         var1.L$0 = this;
         var1.label = 1;
         if (this.waitUntilSelected(var1) === var5) {
            return var5;
         }

         var8 = this;
      }

      var1.L$0 = null;
      var1.label = 2;
      val var9: Any = var8.complete(var1);
      return if (var9 === var5) var5 else var9;
   }

   private fun findClause(clauseObject: Any): kotlinx.coroutines.selects.SelectImplementation.ClauseData? {
      val var3: Any = null;
      if (this.clauses == null) {
         return null;
      } else {
         val var4: java.util.Iterator = this.clauses.iterator();

         var var5: Any;
         do {
            var5 = (SelectImplementation.ClauseData)var3;
            if (!var4.hasNext()) {
               break;
            }

            var5 = (SelectImplementation.ClauseData)var4.next();
         } while (var5.clauseObject != var1);

         var5 = var5;
         if (var5 != null) {
            return var5;
         } else {
            val var7: StringBuilder = new StringBuilder("Clause with object ");
            var7.append(var1);
            var7.append(" is not found");
            throw new IllegalStateException(var7.toString().toString());
         }
      }
   }

   fun `loop$atomicfu`(var1: AtomicReferenceFieldUpdater, var2: (Any?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   private suspend fun processResultAndInvokeBlockRecoveringException(clause: kotlinx.coroutines.selects.SelectImplementation.ClauseData, internalResult: Any?): Any {
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
      // 00: aload 3
      // 01: instanceof kotlinx/coroutines/selects/SelectImplementation$processResultAndInvokeBlockRecoveringException$1
      // 04: ifeq 2d
      // 07: aload 3
      // 08: checkcast kotlinx/coroutines/selects/SelectImplementation$processResultAndInvokeBlockRecoveringException$1
      // 0b: astore 5
      // 0d: aload 5
      // 0f: getfield kotlinx/coroutines/selects/SelectImplementation$processResultAndInvokeBlockRecoveringException$1.label I
      // 12: ldc_w -2147483648
      // 15: iand
      // 16: ifeq 2d
      // 19: aload 5
      // 1b: aload 5
      // 1d: getfield kotlinx/coroutines/selects/SelectImplementation$processResultAndInvokeBlockRecoveringException$1.label I
      // 20: ldc_w -2147483648
      // 23: iadd
      // 24: putfield kotlinx/coroutines/selects/SelectImplementation$processResultAndInvokeBlockRecoveringException$1.label I
      // 27: aload 5
      // 29: astore 3
      // 2a: goto 37
      // 2d: new kotlinx/coroutines/selects/SelectImplementation$processResultAndInvokeBlockRecoveringException$1
      // 30: dup
      // 31: aload 0
      // 32: aload 3
      // 33: invokespecial kotlinx/coroutines/selects/SelectImplementation$processResultAndInvokeBlockRecoveringException$1.<init> (Lkotlinx/coroutines/selects/SelectImplementation;Lkotlin/coroutines/Continuation;)V
      // 36: astore 3
      // 37: aload 3
      // 38: getfield kotlinx/coroutines/selects/SelectImplementation$processResultAndInvokeBlockRecoveringException$1.result Ljava/lang/Object;
      // 3b: astore 5
      // 3d: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 40: astore 6
      // 42: aload 3
      // 43: getfield kotlinx/coroutines/selects/SelectImplementation$processResultAndInvokeBlockRecoveringException$1.label I
      // 46: istore 4
      // 48: iload 4
      // 4a: ifeq 6d
      // 4d: iload 4
      // 4f: bipush 1
      // 50: if_icmpne 62
      // 53: aload 5
      // 55: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 58: aload 5
      // 5a: astore 1
      // 5b: goto 8f
      // 5e: astore 1
      // 5f: goto 91
      // 62: new java/lang/IllegalStateException
      // 65: dup
      // 66: ldc_w "call to 'resume' before 'invoke' with coroutine"
      // 69: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 6c: athrow
      // 6d: aload 5
      // 6f: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 72: aload 1
      // 73: aload 2
      // 74: invokevirtual kotlinx/coroutines/selects/SelectImplementation$ClauseData.processResult (Ljava/lang/Object;)Ljava/lang/Object;
      // 77: astore 2
      // 78: aload 3
      // 79: bipush 1
      // 7a: putfield kotlinx/coroutines/selects/SelectImplementation$processResultAndInvokeBlockRecoveringException$1.label I
      // 7d: aload 1
      // 7e: aload 2
      // 7f: aload 3
      // 80: invokevirtual kotlinx/coroutines/selects/SelectImplementation$ClauseData.invokeBlock (Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
      // 83: astore 2
      // 84: aload 2
      // 85: astore 1
      // 86: aload 2
      // 87: aload 6
      // 89: if_acmpne 8f
      // 8c: aload 6
      // 8e: areturn
      // 8f: aload 1
      // 90: areturn
      // 91: invokestatic kotlinx/coroutines/DebugKt.getRECOVER_STACK_TRACES ()Z
      // 94: ifeq a9
      // 97: aload 3
      // 98: instanceof kotlin/coroutines/jvm/internal/CoroutineStackFrame
      // 9b: ifne a0
      // 9e: aload 1
      // 9f: athrow
      // a0: aload 1
      // a1: aload 3
      // a2: checkcast kotlin/coroutines/jvm/internal/CoroutineStackFrame
      // a5: invokestatic kotlinx/coroutines/internal/StackTraceRecoveryKt.access$recoverFromStackFrame (Ljava/lang/Throwable;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Ljava/lang/Throwable;
      // a8: athrow
      // a9: aload 1
      // aa: athrow
   }

   private fun reregisterClause(clauseObject: Any) {
      var1 = this.findClause(var1);
      var1.disposableHandleOrSegment = null;
      var1.indexInSegment = -1;
      this.register(var1, true);
   }

   private fun trySelectInternal(clauseObject: Any, internalResult: Any?): Int {
      while (true) {
         val var7: AtomicReferenceFieldUpdater = state$FU;
         val var5: Any = state$FU.get(this);
         if (var5 is CancellableContinuation) {
            val var6: SelectImplementation.ClauseData = this.findClause(var1);
            if (var6 != null) {
               val var4: Function1 = var6.createOnCancellationAction(this, var2);
               if (ExternalSyntheticBackportWithForwarding0.m(var7, this, var5, var6)) {
                  var1 = var5 as CancellableContinuation;
                  this.internalResult = var2;
                  if (SelectKt.access$tryResume(var1, var4)) {
                     return 0;
                  }

                  this.internalResult = null;
                  return 2;
               }
            }
         } else {
            val var3: Boolean;
            if (var5 == SelectKt.access$getSTATE_COMPLETED$p()) {
               var3 = true;
            } else {
               var3 = var5 is SelectImplementation.ClauseData;
            }

            if (var3) {
               return 3;
            }

            if (var5 == SelectKt.access$getSTATE_CANCELLED$p()) {
               return 2;
            }

            if (var5 == SelectKt.access$getSTATE_REG$p()) {
               if (ExternalSyntheticBackportWithForwarding0.m(var7, this, var5, CollectionsKt.listOf(var1))) {
                  return 1;
               }
            } else {
               if (var5 is java.util.List) {
                  if (!ExternalSyntheticBackportWithForwarding0.m(
                     var7, this, var5, CollectionsKt.plus(var5 as MutableCollection<CancellableContinuation>, var1)
                  )) {
                     continue;
                  }

                  return 1;
               }

               val var8: StringBuilder = new StringBuilder("Unexpected state: ");
               var8.append(var5);
               throw new IllegalStateException(var8.toString().toString());
            }
         }
      }
   }

   fun `update$atomicfu`(var1: AtomicReferenceFieldUpdater, var2: (Any?) -> Any, var3: Any) {
      val var4: Any;
      do {
         var4 = var1.get(var3);
      } while (!ExternalSyntheticBackportWithForwarding0.m(var1, var3, var4, var2.invoke(var4)));
   }

   private suspend fun waitUntilSelected() {
      val var4: CancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(var1), 1);
      var4.initCancellability();
      val var3: CancellableContinuation = var4;
      val var2: AtomicReferenceFieldUpdater = access$getState$FU$p();

      label40:
      while (true) {
         var var5: Any = var2.get(this);
         if (var5 === SelectKt.access$getSTATE_REG$p()) {
            if (ExternalSyntheticBackportWithForwarding0.m(access$getState$FU$p(), this, var5, var3)) {
               var3.invokeOnCancellation(this as CancelHandlerBase);
               break;
            }
         } else {
            if (var5 is java.util.List) {
               if (!ExternalSyntheticBackportWithForwarding0.m(access$getState$FU$p(), this, var5, SelectKt.access$getSTATE_REG$p())) {
                  continue;
               }

               val var6: java.util.List = var5 as java.util.List;
               var5 = (var5 as java.lang.Iterable).iterator();

               while (true) {
                  if (!var5.hasNext()) {
                     continue label40;
                  }

                  access$reregisterClause(this, var5.next());
               }
            }

            if (var5 !is SelectImplementation.ClauseData) {
               val var7: StringBuilder = new StringBuilder("unexpected state: ");
               var7.append(var5);
               throw new IllegalStateException(var7.toString().toString());
            }

            var3.resume(Unit.INSTANCE, (var5 as SelectImplementation.ClauseData).createOnCancellationAction(this, access$getInternalResult$p(this)));
            break;
         }
      }

      val var8: Any = var4.getResult();
      if (var8 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var1);
      }

      return if (var8 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var8 else Unit.INSTANCE;
   }

   public override fun disposeOnCompletion(disposableHandle: DisposableHandle) {
      this.disposableHandleOrSegment = var1;
   }

   internal open suspend fun doSelect(): Any {
      return doSelect$suspendImpl(this, var1);
   }

   public override operator fun invoke(cause: Throwable?) {
      val var3: AtomicReferenceFieldUpdater = state$FU;

      val var2: Any;
      do {
         var2 = var3.get(this);
         if (var2 === SelectKt.access$getSTATE_COMPLETED$p()) {
            return;
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(var3, this, var2, SelectKt.access$getSTATE_CANCELLED$p()));

      if (this.clauses != null) {
         val var5: java.util.Iterator = this.clauses.iterator();

         while (var5.hasNext()) {
            (var5.next() as SelectImplementation.ClauseData).dispose();
         }

         this.internalResult = SelectKt.access$getNO_RESULT$p();
         this.clauses = null;
      }
   }

   public override operator fun SelectClause0.invoke(block: (Continuation<Any>) -> Any?) {
      register$default(
         this,
         new SelectImplementation.ClauseData(
            this,
            (Function3<Object, ? super SelectInstance<?>, Object, Unit>)var1.getClauseObject(),
            var1.getRegFunc(),
            var1.getProcessResFunc(),
            SelectKt.getPARAM_CLAUSE_0(),
            var2,
            var1.getOnCancellationConstructor()
         ),
         false,
         1,
         null
      );
   }

   public override operator fun <Q> SelectClause1<Q>.invoke(block: (Q, Continuation<Any>) -> Any?) {
      register$default(
         this,
         new SelectImplementation.ClauseData(
            this,
            (Function3<Object, ? super SelectInstance<?>, Object, Unit>)var1.getClauseObject(),
            var1.getRegFunc(),
            var1.getProcessResFunc(),
            null,
            var2,
            var1.getOnCancellationConstructor()
         ),
         false,
         1,
         null
      );
   }

   public override operator fun <P, Q> SelectClause2<P, Q>.invoke(param: P, block: (Q, Continuation<Any>) -> Any?) {
      register$default(
         this,
         new SelectImplementation.ClauseData(
            this,
            (Function3<Object, ? super SelectInstance<?>, Object, Unit>)var1.getClauseObject(),
            var1.getRegFunc(),
            var1.getProcessResFunc(),
            var2,
            var3,
            var1.getOnCancellationConstructor()
         ),
         false,
         1,
         null
      );
   }

   override fun <P, Q> invoke(var1: SelectClause2<? super P, ? extends Q>, var2: (Q?, Continuation<? super R>?) -> Any) {
      SelectBuilder.DefaultImpls.invoke(this, var1, var2);
   }

   public override fun invokeOnCancellation(segment: Segment<*>, index: Int) {
      this.disposableHandleOrSegment = var1;
      this.indexInSegment = var2;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Replaced with the same extension function", replaceWith = @ReplaceWith(expression = "onTimeout", imports = ["kotlinx.coroutines.selects.onTimeout"]))
   override fun onTimeout(var1: Long, var3: (Continuation<? super R>?) -> Any) {
      SelectBuilder.DefaultImpls.onTimeout(this, var1, var3);
   }

   internal fun kotlinx.coroutines.selects.SelectImplementation.ClauseData.register(reregister: Boolean = false) {
      if (DebugKt.getASSERTIONS_ENABLED() && state$FU.get(this) === SelectKt.access$getSTATE_CANCELLED$p()) {
         throw new AssertionError();
      } else {
         val var3: AtomicReferenceFieldUpdater = state$FU;
         if (state$FU.get(this) !is SelectImplementation.ClauseData) {
            if (!var2) {
               this.checkClauseObject(var1.clauseObject);
            }

            if (var1.tryRegisterAsWaiter(this)) {
               if (!var2) {
                  val var4: java.util.List = this.clauses;
                  var4.add(var1);
               }

               var1.disposableHandleOrSegment = this.disposableHandleOrSegment;
               var1.indexInSegment = this.indexInSegment;
               this.disposableHandleOrSegment = null;
               this.indexInSegment = -1;
            } else {
               var3.set(this, var1);
            }
         }
      }
   }

   public override fun selectInRegistrationPhase(internalResult: Any?) {
      this.internalResult = var1;
   }

   public override fun trySelect(clauseObject: Any, result: Any?): Boolean {
      val var3: Boolean;
      if (this.trySelectInternal(var1, var2) == 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public fun trySelectDetailed(clauseObject: Any, result: Any?): TrySelectDetailedResult {
      return SelectKt.access$TrySelectDetailedResult(this.trySelectInternal(var1, var2));
   }

   internal inner class ClauseData(clauseObject: Any,
      regFunc: (Any, SelectInstance<*>, Any?) -> Unit,
      processResFunc: (Any, Any?, Any?) -> Any?,
      param: Any?,
      block: Any,
      onCancellationConstructor: ((SelectInstance<*>, Any?, Any?) -> (Throwable) -> Unit)?
   ) {
      private final val block: Any
      public final val clauseObject: Any

      public final var disposableHandleOrSegment: Any?
         private set

      public final var indexInSegment: Int
         private set

      public final val onCancellationConstructor: ((SelectInstance<*>, Any?, Any?) -> (Throwable) -> Unit)?
      private final val param: Any?
      private final val processResFunc: (Any, Any?, Any?) -> Any?
      private final val regFunc: (Any, SelectInstance<*>, Any?) -> Unit

      init {
         this.this$0 = var1;
         this.clauseObject = var2;
         this.regFunc = var3;
         this.processResFunc = var4;
         this.param = var5;
         this.block = var6;
         this.onCancellationConstructor = var7;
         this.indexInSegment = -1;
      }

      public fun createOnCancellationAction(select: SelectInstance<*>, internalResult: Any?): ((Throwable) -> Unit)? {
         val var4: Function1;
         if (this.onCancellationConstructor != null) {
            var4 = this.onCancellationConstructor.invoke(var1, this.param, var2);
         } else {
            var4 = null;
         }

         return var4;
      }

      public fun dispose() {
         val var1: Boolean = this.disposableHandleOrSegment is Segment;
         var var2: DisposableHandle = null;
         if (var1) {
            (this.disposableHandleOrSegment as Segment).onCancellation(this.indexInSegment, null, this.this$0.getContext());
         } else {
            if (this.disposableHandleOrSegment is DisposableHandle) {
               var2 = this.disposableHandleOrSegment as DisposableHandle;
            }

            if (var2 != null) {
               var2.dispose();
            }
         }
      }

      public suspend fun invokeBlock(argument: Any?): Any {
         val var3: Any = this.block;
         if (this.param === SelectKt.getPARAM_CLAUSE_0()) {
            return (var3 as Function1).invoke(var2);
         } else {
            return (var3 as Function2).invoke(var1, var2);
         }
      }

      public fun processResult(result: Any?): Any? {
         return this.processResFunc.invoke(this.clauseObject, this.param, var1);
      }

      public fun tryRegisterAsWaiter(select: SelectImplementation<Any>): Boolean {
         if (DebugKt.getASSERTIONS_ENABLED() && !SelectImplementation.access$getInRegistrationPhase(var1) && !SelectImplementation.access$isCancelled(var1)) {
            throw new AssertionError();
         } else if (DebugKt.getASSERTIONS_ENABLED() && SelectImplementation.access$getInternalResult$p(var1) != SelectKt.access$getNO_RESULT$p()) {
            throw new AssertionError();
         } else {
            this.regFunc.invoke(this.clauseObject, var1, this.param);
            val var2: Boolean;
            if (SelectImplementation.access$getInternalResult$p(var1) === SelectKt.access$getNO_RESULT$p()) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }
      }
   }
}
