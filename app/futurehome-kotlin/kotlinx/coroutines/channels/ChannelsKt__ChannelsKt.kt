package kotlinx.coroutines.channels

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.BuildersKt
import kotlinx.coroutines.CoroutineScope

@JvmSynthetic
internal class ChannelsKt__ChannelsKt {
   @JvmStatic
   public fun <E> SendChannel<E>.trySendBlocking(element: E): ChannelResult<Unit> {
      val var2: Any = var0.trySend-JP2dKIU(var1);
      if (var2 !is ChannelResult.Failed) {
         val var3: Unit = var2 as Unit;
         return ChannelResult.Companion.success-JP2dKIU(Unit.INSTANCE);
      } else {
         return (BuildersKt.runBlocking$default(
               null,
               (
                  new Function2<CoroutineScope, Continuation<? super ChannelResult<? extends Unit>>, Object>(var0, var1, null) {
                     final E $element;
                     final SendChannel<E> $this_trySendBlocking;
                     private Object L$0;
                     int label;

                     {
                        super(2, var3x);
                        this.$this_trySendBlocking = var1;
                        this.$element = (E)var2x;
                     }

                     @Override
                     public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                        val var3: Function2 = new <anonymous constructor>(this.$this_trySendBlocking, this.$element, var2);
                        var3.L$0 = var1;
                        return var3 as Continuation<Unit>;
                     }

                     public final Object invoke(CoroutineScope var1, Continuation<? super ChannelResult<Unit>> var2x) {
                        return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                     }

                     // $VF: Duplicated exception handlers to handle obfuscated exceptions
                     // $VF: Could not inline inconsistent finally blocks
                     // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
                     @Override
                     public final Object invokeSuspend(Object var1) {
                        label39: {
                           val var3x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                           if (this.label != 0) {
                              if (this.label != 1) {
                                 throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                              }

                              try {
                                 ResultKt.throwOnFailure(var1);
                              } catch (var8: java.lang.Throwable) {
                                 var1 = Result.Companion;
                                 var1 = Result.constructor-impl(ResultKt.createFailure(var8));
                                 break label39;
                              }
                           } else {
                              ResultKt.throwOnFailure(var1);
                              var1 = this.L$0 as CoroutineScope;
                              val var4: SendChannel = this.$this_trySendBlocking;
                              var1 = this.$element;

                              try {
                                 val var5: Result.Companion = Result.Companion;
                                 this.label = 1;
                                 if (var4.send(var1, this) === var3x) {
                                    return var3x;
                                 }
                              } catch (var7: java.lang.Throwable) {
                                 var1 = Result.Companion;
                                 var1 = Result.constructor-impl(ResultKt.createFailure(var7));
                                 break label39;
                              }
                           }

                           label29:
                           try {
                              var1 = Result.constructor-impl(Unit.INSTANCE);
                           } catch (var6: java.lang.Throwable) {
                              var1 = Result.Companion;
                              var1 = Result.constructor-impl(ResultKt.createFailure(var6));
                              break label29;
                           }
                        }

                        if (Result.isSuccess-impl(var1)) {
                           var1 = ChannelResult.Companion.success-JP2dKIU(Unit.INSTANCE);
                        } else {
                           var1 = ChannelResult.Companion.closed-JP2dKIU(Result.exceptionOrNull-impl(var1));
                        }

                        return ChannelResult.box-impl(var1);
                     }
                  }
               ) as Function2,
               1,
               null
            ) as ChannelResult)
            .unbox-impl();
      }
   }
}
