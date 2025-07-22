package kotlinx.coroutines.selects

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.BuildersKt
import kotlinx.coroutines.CancellableContinuationImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineScopeKt
import kotlinx.coroutines.CoroutineStart

internal class UnbiasedSelectBuilderImpl<R>(uCont: Continuation<Any>) : UnbiasedSelectImplementation(var1.getContext()) {
   private final val cont: CancellableContinuationImpl<Any>

   init {
      this.cont = new CancellableContinuationImpl<>(IntrinsicsKt.intercepted(var1), 1);
   }

   internal fun handleBuilderException(e: Throwable) {
      val var2: Continuation = this.cont;
      val var3: Result.Companion = Result.Companion;
      var2.resumeWith(Result.constructor-impl(ResultKt.createFailure(var1)));
   }

   internal fun initSelectResult(): Any? {
      if (this.cont.isCompleted()) {
         return this.cont.getResult();
      } else {
         BuildersKt.launch$default(
            CoroutineScopeKt.CoroutineScope(this.getContext()),
            null,
            CoroutineStart.UNDISPATCHED,
            (
               new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, null) {
                  int label;
                  final UnbiasedSelectBuilderImpl<R> this$0;

                  {
                     super(2, var2x);
                     this.this$0 = var1;
                  }

                  @Override
                  public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                     return new <anonymous constructor>(this.this$0, var2);
                  }

                  public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
                     return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                  }

                  // $VF: Duplicated exception handlers to handle obfuscated exceptions
                  // $VF: Could not inline inconsistent finally blocks
                  // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
                  @Override
                  public final Object invokeSuspend(Object var1) {
                     val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     if (this.label != 0) {
                        if (this.label != 1) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        try {
                           ResultKt.throwOnFailure(var1);
                        } catch (var6: java.lang.Throwable) {
                           SelectOldKt.access$resumeUndispatchedWithException(UnbiasedSelectBuilderImpl.access$getCont$p(this.this$0), var6);
                           return Unit.INSTANCE;
                        }
                     } else {
                        ResultKt.throwOnFailure(var1);

                        var var12: Any;
                        try {
                           var1 = this.this$0;
                           var12 = this;
                           this.label = 1;
                           var12 = var1.doSelect((Continuation<? super R>)var12);
                        } catch (var5: java.lang.Throwable) {
                           SelectOldKt.access$resumeUndispatchedWithException(UnbiasedSelectBuilderImpl.access$getCont$p(this.this$0), var5);
                           return Unit.INSTANCE;
                        }

                        var1 = (UnbiasedSelectBuilderImpl)var12;
                        if (var12 === var4) {
                           return var4;
                        }
                     }

                     SelectOldKt.access$resumeUndispatched(UnbiasedSelectBuilderImpl.access$getCont$p(this.this$0), var1);
                     return Unit.INSTANCE;
                  }
               }
            ) as Function2,
            1,
            null
         );
         return this.cont.getResult();
      }
   }
}
