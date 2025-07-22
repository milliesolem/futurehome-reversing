package kotlinx.coroutines.channels

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.CoroutineContextKt
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineScopeKt
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

@Deprecated(level = DeprecationLevel.WARNING, message = "BroadcastChannel is deprecated in the favour of SharedFlow and is no longer supported")
public fun <E> CoroutineScope.broadcast(
   context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
   capacity: Int = 1,
   start: CoroutineStart = CoroutineStart.LAZY,
   onCompletion: ((Throwable?) -> Unit)? = null,
   block: (ProducerScope<E>, Continuation<Unit>) -> Any?
): BroadcastChannel<E> {
   val var6: CoroutineContext = CoroutineContextKt.newCoroutineContext(var0, var1);
   val var8: BroadcastChannel = BroadcastChannelKt.BroadcastChannel(var2);
   val var7: BroadcastCoroutine;
   if (var3.isLazy()) {
      var7 = new LazyBroadcastCoroutine(var6, var8, var5);
   } else {
      var7 = new BroadcastCoroutine(var6, var8, true);
   }

   if (var4 != null) {
      var7.invokeOnCompletion(var4);
   }

   var7.start(var3, var7, var5);
   return var7;
}

@Deprecated(level = DeprecationLevel.WARNING, message = "BroadcastChannel is deprecated in the favour of SharedFlow and is no longer supported")
public fun <E> ReceiveChannel<E>.broadcast(capacity: Int = 1, start: CoroutineStart = CoroutineStart.LAZY): BroadcastChannel<E> {
   return broadcast$default(
      CoroutineScopeKt.plus(
         CoroutineScopeKt.plus(GlobalScope.INSTANCE, Dispatchers.getUnconfined()), new CoroutineExceptionHandler(CoroutineExceptionHandler.Key) {
            {
               super(var1);
            }

            @Override
            public void handleException(CoroutineContext var1, java.lang.Throwable var2) {
            }
         }
      ),
      null,
      var1,
      var2,
      (new Function1<java.lang.Throwable, Unit>(var0) {
         final ReceiveChannel<E> $this_broadcast;

         {
            super(1);
            this.$this_broadcast = var1;
         }

         public final void invoke(java.lang.Throwable var1) {
            ChannelsKt.cancelConsumed(this.$this_broadcast, var1);
         }
      }) as Function1,
      (new Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object>(var0, null) {
         final ReceiveChannel<E> $channel;
         private Object L$0;
         Object L$1;
         int label;

         {
            super(2, var2x);
            this.$channel = var1;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$channel, var2);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(ProducerScope<? super E> var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         // $VF: Irreducible bytecode was duplicated to produce valid code
         @Override
         public final Object invokeSuspend(Object var1) {
            val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            var var9: ProducerScope;
            if (this.label != 0) {
               if (this.label != 1) {
                  if (this.label != 2) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  val var4: ChannelIterator = this.L$1 as ChannelIterator;
                  var9 = this.L$0 as ProducerScope;
                  ResultKt.throwOnFailure(var1);
                  var9 = var9;
                  var1 = var4;
               } else {
                  val var11: ChannelIterator = this.L$1 as ChannelIterator;
                  var9 = this.L$0 as ProducerScope;
                  ResultKt.throwOnFailure(var1);
                  if (!var1 as java.lang.Boolean) {
                     return Unit.INSTANCE;
                  }

                  val var7: Any = var11.next();
                  val var5: Continuation = this;
                  this.L$0 = var9;
                  this.L$1 = var11;
                  this.label = 2;
                  if (var9.send(var7, var5) === var6) {
                     return var6;
                  }

                  var9 = var9;
                  var1 = var11;
               }
            } else {
               ResultKt.throwOnFailure(var1);
               var9 = this.L$0 as ProducerScope;
               var1 = this.$channel.iterator();
            }

            while (true) {
               val var12: Continuation = this;
               this.L$0 = var9;
               this.L$1 = var1;
               this.label = 1;
               var var13: Any = var1.hasNext(var12);
               if (var13 === var6) {
                  return var6;
               }

               if (!var13 as java.lang.Boolean) {
                  return Unit.INSTANCE;
               }

               val var15: Any = var1.next();
               var13 = this;
               this.L$0 = var9;
               this.L$1 = var1;
               this.label = 2;
               if (var9.send(var15, (Continuation<? super Unit>)var13) === var6) {
                  return var6;
               }

               var9 = var9;
               var1 = var1;
            }
         }
      }) as Function2,
      1,
      null
   );
}

@JvmSynthetic
fun `broadcast$default`(var0: CoroutineScope, var1: CoroutineContext, var2: Int, var3: CoroutineStart, var4: Function1, var5: Function2, var6: Int, var7: Any): BroadcastChannel {
   if ((var6 and 1) != 0) {
      var1 = EmptyCoroutineContext.INSTANCE;
   }

   if ((var6 and 2) != 0) {
      var2 = 1;
   }

   if ((var6 and 4) != 0) {
      var3 = CoroutineStart.LAZY;
   }

   if ((var6 and 8) != 0) {
      var4 = null;
   }

   return broadcast(var0, var1, var2, var3, var4, var5);
}

@JvmSynthetic
fun `broadcast$default`(var0: ReceiveChannel, var1: Int, var2: CoroutineStart, var3: Int, var4: Any): BroadcastChannel {
   if ((var3 and 1) != 0) {
      var1 = 1;
   }

   if ((var3 and 2) != 0) {
      var2 = CoroutineStart.LAZY;
   }

   return broadcast(var0, var1, var2);
}
