package kotlinx.coroutines.channels

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.AbstractTimeSource
import kotlinx.coroutines.AbstractTimeSourceKt
import kotlinx.coroutines.DelayKt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.EventLoop_commonKt
import kotlinx.coroutines.GlobalScope

@JvmSynthetic
fun `access$fixedDelayTicker`(var0: Long, var2: Long, var4: SendChannel, var5: Continuation): Any {
   return fixedDelayTicker(var0, var2, var4, var5);
}

@JvmSynthetic
fun `access$fixedPeriodTicker`(var0: Long, var2: Long, var4: SendChannel, var5: Continuation): Any {
   return fixedPeriodTicker(var0, var2, var4, var5);
}

private suspend fun fixedDelayTicker(delayMillis: Long, initialDelayMillis: Long, channel: SendChannel<Unit>) {
   label31: {
      if (var5 is <unrepresentable>) {
         val var7: <unrepresentable> = var5 as <unrepresentable>;
         if ((var5.label and Integer.MIN_VALUE) != 0) {
            var7.label += Integer.MIN_VALUE;
            var5 = var7;
            break label31;
         }
      }

      var5 = new ContinuationImpl(var5) {
         long J$0;
         Object L$0;
         int label;
         Object result;

         {
            super(var1);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            this.result = var1;
            this.label |= Integer.MIN_VALUE;
            return TickerChannelsKt.access$fixedDelayTicker(0L, 0L, null, this);
         }
      };
   }

   var var13: Any = var5.result;
   val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
   if (var5.label != 0) {
      if (var5.label != 1) {
         if (var5.label != 2) {
            if (var5.label != 3) {
               throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            var2 = var5.J$0;
            var4 = var5.L$0 as SendChannel;
            ResultKt.throwOnFailure(var13);
            var13 = var5;
            var0 = var2;
         } else {
            var0 = var5.J$0;
            var4 = var5.L$0 as SendChannel;
            ResultKt.throwOnFailure(var13);
            var5.L$0 = var4;
            var5.J$0 = var0;
            var5.label = 3;
            if (DelayKt.delay(var0, var5) === var8) {
               return var8;
            }

            var13 = var5;
            var0 = var0;
         }
      } else {
         var0 = var5.J$0;
         var4 = var5.L$0 as SendChannel;
         ResultKt.throwOnFailure(var13);
         var13 = var5;
      }
   } else {
      ResultKt.throwOnFailure(var13);
      var5.L$0 = var4;
      var5.J$0 = var0;
      var5.label = 1;
      var13 = var5;
      if (DelayKt.delay(var2, var5) === var8) {
         return var8;
      }
   }

   while (true) {
      val var12: Unit = Unit.INSTANCE;
      ((<unrepresentable>)var13).L$0 = var4;
      ((<unrepresentable>)var13).J$0 = var0;
      ((<unrepresentable>)var13).label = 2;
      if (var4.send(var12, (Continuation<? super Unit>)var13) === var8) {
         return var8;
      }

      ((<unrepresentable>)var13).L$0 = var4;
      ((<unrepresentable>)var13).J$0 = var0;
      ((<unrepresentable>)var13).label = 3;
      if (DelayKt.delay(var0, (Continuation<? super Unit>)var13) === var8) {
         return var8;
      }

      var13 = var13;
      var0 = var0;
   }
}

private suspend fun fixedPeriodTicker(delayMillis: Long, initialDelayMillis: Long, channel: SendChannel<Unit>) {
   label54: {
      if (var5 is <unrepresentable>) {
         val var11: <unrepresentable> = var5 as <unrepresentable>;
         if ((var5.label and Integer.MIN_VALUE) != 0) {
            var11.label += Integer.MIN_VALUE;
            var5 = var11;
            break label54;
         }
      }

      var5 = new ContinuationImpl(var5) {
         long J$0;
         long J$1;
         Object L$0;
         int label;
         Object result;

         {
            super(var1);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            this.result = var1;
            this.label |= Integer.MIN_VALUE;
            return TickerChannelsKt.access$fixedPeriodTicker(0L, 0L, null, this);
         }
      };
   }

   var var32: AbstractTimeSource = (AbstractTimeSource)var5.result;
   val var12: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
   if (var5.label != 0) {
      if (var5.label != 1) {
         if (var5.label != 2) {
            if (var5.label != 3) {
               if (var5.label != 4) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var2 = var5.J$1;
               var0 = var5.J$0;
               var4 = var5.L$0 as SendChannel;
               ResultKt.throwOnFailure(var32);
            } else {
               var2 = var5.J$1;
               var0 = var5.J$0;
               var4 = var5.L$0 as SendChannel;
               ResultKt.throwOnFailure(var32);
            }
         } else {
            var2 = var5.J$1;
            var0 = var5.J$0;
            var4 = var5.L$0 as SendChannel;
            ResultKt.throwOnFailure(var32);
            var32 = var5;
            val var18: AbstractTimeSource = AbstractTimeSourceKt.getTimeSource();
            var var7: Long;
            if (var18 != null) {
               var7 = var18.nanoTime();
            } else {
               var7 = System.nanoTime();
            }

            var var9: Long = RangesKt.coerceAtLeast(var0 - var7, 0L);
            if (var9 == 0L) {
               if (var2 != 0L) {
                  var9 = var2 - (var7 - var0) % var2;
                  var0 = var7 + (var2 - (var7 - var0) % var2);
                  var7 = EventLoop_commonKt.delayNanosToMillis(var9);
                  var5.L$0 = var4;
                  var5.J$0 = var0;
                  var5.J$1 = var2;
                  var5.label = 3;
                  var5 = var5;
                  if (DelayKt.delay(var7, (Continuation<? super Unit>)var32) === var12) {
                     return var12;
                  }
               } else {
                  var7 = EventLoop_commonKt.delayNanosToMillis(var9);
                  var5.L$0 = var4;
                  var5.J$0 = var0;
                  var5.J$1 = var2;
                  var5.label = 4;
                  var5 = var5;
                  if (DelayKt.delay(var7, (Continuation<? super Unit>)var32) === var12) {
                     return var12;
                  }
               }
            } else {
               var7 = EventLoop_commonKt.delayNanosToMillis(var9);
               var5.L$0 = var4;
               var5.J$0 = var0;
               var5.J$1 = var2;
               var5.label = 4;
               var5 = var5;
               if (DelayKt.delay(var7, (Continuation<? super Unit>)var32) === var12) {
                  return var12;
               }
            }
         }
      } else {
         var0 = var5.J$1;
         var2 = var5.J$0;
         var4 = var5.L$0 as SendChannel;
         ResultKt.throwOnFailure(var32);
         var2 = EventLoop_commonKt.delayToNanos(var2);
      }
   } else {
      ResultKt.throwOnFailure(var32);
      var32 = AbstractTimeSourceKt.getTimeSource();
      var var23: Long;
      if (var32 != null) {
         var23 = var32.nanoTime();
      } else {
         var23 = System.nanoTime();
      }

      var23 = var23 + EventLoop_commonKt.delayToNanos(var2);
      var5.L$0 = var4;
      var5.J$0 = var0;
      var5.J$1 = var23;
      var5.label = 1;
      if (DelayKt.delay(var2, var5) === var12) {
         return var12;
      }

      var0 = var23;
      var2 = EventLoop_commonKt.delayToNanos(var0);
   }

   while (true) {
      var0 += var2;
      val var35: Unit = Unit.INSTANCE;
      var5.L$0 = var4;
      var5.J$0 = var0;
      var5.J$1 = var2;
      var5.label = 2;
      if (var4.send(var35, var5) === var12) {
         return var12;
      }

      var32 = var5;
      val var19: AbstractTimeSource = AbstractTimeSourceKt.getTimeSource();
      var var25: Long;
      if (var19 != null) {
         var25 = var19.nanoTime();
      } else {
         var25 = System.nanoTime();
      }

      var var30: Long = RangesKt.coerceAtLeast(var0 - var25, 0L);
      if (var30 == 0L) {
         if (var2 != 0L) {
            var30 = var2 - (var25 - var0) % var2;
            var0 = var25 + (var2 - (var25 - var0) % var2);
            var25 = EventLoop_commonKt.delayNanosToMillis(var30);
            var5.L$0 = var4;
            var5.J$0 = var0;
            var5.J$1 = var2;
            var5.label = 3;
            var5 = var5;
            if (DelayKt.delay(var25, var32) === var12) {
               return var12;
            }
         } else {
            var25 = EventLoop_commonKt.delayNanosToMillis(var30);
            var5.L$0 = var4;
            var5.J$0 = var0;
            var5.J$1 = var2;
            var5.label = 4;
            var5 = var5;
            if (DelayKt.delay(var25, var32) === var12) {
               return var12;
            }
         }
      } else {
         var25 = EventLoop_commonKt.delayNanosToMillis(var30);
         var5.L$0 = var4;
         var5.J$0 = var0;
         var5.J$1 = var2;
         var5.label = 4;
         var5 = var5;
         if (DelayKt.delay(var25, var32) === var12) {
            return var12;
         }
      }
   }
}

public fun ticker(
   delayMillis: Long,
   initialDelayMillis: Long = var0,
   context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
   mode: TickerMode = TickerMode.FIXED_PERIOD
): ReceiveChannel<Unit> {
   if (var0 >= 0L) {
      if (var2 >= 0L) {
         return ProduceKt.produce(
            GlobalScope.INSTANCE,
            Dispatchers.getUnconfined().plus(var4),
            0,
            (new Function2<ProducerScope<? super Unit>, Continuation<? super Unit>, Object>(var5, var0, var2, null) {
               final long $delayMillis;
               final long $initialDelayMillis;
               final TickerMode $mode;
               private Object L$0;
               int label;

               {
                  super(2, var6);
                  this.$mode = var1;
                  this.$delayMillis = var2x;
                  this.$initialDelayMillis = var4;
               }

               @Override
               public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                  val var3: Function2 = new <anonymous constructor>(this.$mode, this.$delayMillis, this.$initialDelayMillis, var2);
                  var3.L$0 = var1;
                  return var3 as Continuation<Unit>;
               }

               public final Object invoke(ProducerScope<? super Unit> var1, Continuation<? super Unit> var2x) {
                  return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
               }

               @Override
               public final Object invokeSuspend(Object var1) {
                  val var7: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (this.label != 0) {
                     if (this.label != 1 && this.label != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     ResultKt.throwOnFailure(var1);
                  } else {
                     ResultKt.throwOnFailure(var1);
                     var1 = this.L$0 as ProducerScope;
                     val var12: Int = WhenMappings.$EnumSwitchMapping$0[this.$mode.ordinal()];
                     if (var12 != 1) {
                        if (var12 == 2) {
                           val var5: Long = this.$delayMillis;
                           val var3x: Long = this.$initialDelayMillis;
                           val var15: SendChannel = var1.getChannel();
                           val var10: Continuation = this;
                           this.label = 2;
                           if (TickerChannelsKt.access$fixedDelayTicker(var5, var3x, var15, var10) === var7) {
                              return var7;
                           }
                        }
                     } else {
                        val var14: Long = this.$delayMillis;
                        val var13: Long = this.$initialDelayMillis;
                        val var16: SendChannel = var1.getChannel();
                        val var11: Continuation = this;
                        this.label = 1;
                        if (TickerChannelsKt.access$fixedPeriodTicker(var14, var13, var16, var11) === var7) {
                           return var7;
                        }
                     }
                  }

                  return Unit.INSTANCE;
               }
            }) as (ProducerScope<? super Unit>?, Continuation<? super Unit>?) -> Any
         );
      } else {
         val var7: StringBuilder = new StringBuilder("Expected non-negative initial delay, but has ");
         var7.append(var2);
         var7.append(" ms");
         throw new IllegalArgumentException(var7.toString().toString());
      }
   } else {
      val var6: StringBuilder = new StringBuilder("Expected non-negative delay, but has ");
      var6.append(var0);
      var6.append(" ms");
      throw new IllegalArgumentException(var6.toString().toString());
   }
}

@JvmSynthetic
fun `ticker$default`(var0: Long, var2: Long, var4: CoroutineContext, var5: TickerMode, var6: Int, var7: Any): ReceiveChannel {
   if ((var6 and 2) != 0) {
      var2 = var0;
   }

   if ((var6 and 4) != 0) {
      var4 = EmptyCoroutineContext.INSTANCE;
   }

   if ((var6 and 8) != 0) {
      var5 = TickerMode.FIXED_PERIOD;
   }

   return ticker(var0, var2, var4, var5);
}
