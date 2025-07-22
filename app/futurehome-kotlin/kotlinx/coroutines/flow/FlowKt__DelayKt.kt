package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.Boxing
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.Ref
import kotlin.time.Duration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.DelayKt
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.ProduceKt
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.internal.ChildCancelledException
import kotlinx.coroutines.flow.internal.FlowCoroutineKt
import kotlinx.coroutines.flow.internal.NullSurrogateKt
import kotlinx.coroutines.selects.OnTimeoutKt
import kotlinx.coroutines.selects.SelectBuilder
import kotlinx.coroutines.selects.SelectImplementation

@JvmSynthetic
internal class FlowKt__DelayKt {
   @JvmStatic
   public fun <T> Flow<T>.debounce(timeoutMillis: Long): Flow<T> {
      val var4: Long;
      val var3: Byte = (byte)(if ((var4 = var1 - 0L) == 0L) 0 else (if (var4 < 0L) -1 else 1));
      if (var1 >= 0L) {
         return if (var3 == 0) var0 else debounceInternal$FlowKt__DelayKt(var0, (new Function1<T, java.lang.Long>(var1) {
            final long $timeoutMillis;

            {
               super(1);
               this.$timeoutMillis = var1;
            }

            public final java.lang.Long invoke(T var1) {
               return this.$timeoutMillis;
            }
         }) as Function1);
      } else {
         throw new IllegalArgumentException("Debounce timeout should not be negative".toString());
      }
   }

   @JvmStatic
   public fun <T> Flow<T>.debounce(timeoutMillis: (T) -> Long): Flow<T> {
      return debounceInternal$FlowKt__DelayKt(var0, var1);
   }

   @JvmStatic
   public fun <T> Flow<T>.debounce(timeout: Duration): Flow<T> {
      return FlowKt.debounce(var0, DelayKt.toDelayMillis-LRDsOJo(var1));
   }

   @JvmStatic
   public fun <T> Flow<T>.debounce(timeout: (T) -> Duration): Flow<T> {
      return debounceInternal$FlowKt__DelayKt(var0, (new Function1<T, java.lang.Long>(var1) {
         final Function1<T, Duration> $timeout;

         {
            super(1);
            this.$timeout = var1;
         }

         public final java.lang.Long invoke(T var1) {
            return DelayKt.toDelayMillis-LRDsOJo(this.$timeout.invoke(var1).unbox-impl());
         }
      }) as Function1);
   }

   @JvmStatic
   private fun <T> Flow<T>.debounceInternal(timeoutMillisSelector: (T) -> Long): Flow<T> {
      return FlowCoroutineKt.scopedFlow(
         (
            new Function3<CoroutineScope, FlowCollector<? super T>, Continuation<? super Unit>, Object>(var1, var0, null) {
               final Flow<T> $this_debounceInternal;
               final Function1<T, java.lang.Long> $timeoutMillisSelector;
               private Object L$0;
               Object L$1;
               Object L$2;
               Object L$3;
               int label;

               {
                  super(3, var3x);
                  this.$timeoutMillisSelector = var1;
                  this.$this_debounceInternal = var2x;
               }

               public final Object invoke(CoroutineScope var1, FlowCollector<? super T> var2, Continuation<? super Unit> var3) {
                  val var4: Function3 = new <anonymous constructor>(this.$timeoutMillisSelector, this.$this_debounceInternal, var3);
                  var4.L$0 = var1;
                  var4.L$1 = var2;
                  return var4.invokeSuspend(Unit.INSTANCE);
               }

               // $VF: Irreducible bytecode was duplicated to produce valid code
               @Override
               public final Object invokeSuspend(Object var1) {
                  val var10: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  var var3x: ReceiveChannel;
                  var var16: Ref.ObjectRef;
                  if (this.label != 0) {
                     if (this.label != 1) {
                        if (this.label != 2) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        val var5: Ref.ObjectRef = this.L$2 as Ref.ObjectRef;
                        var3x = this.L$1 as ReceiveChannel;
                        val var4x: FlowCollector = this.L$0 as FlowCollector;
                        ResultKt.throwOnFailure(var1);
                        var16 = var5;
                        var1 = var4x;
                     } else {
                        val var21: Ref.LongRef = this.L$3 as Ref.LongRef;
                        var16 = this.L$2 as Ref.ObjectRef;
                        var3x = this.L$1 as ReceiveChannel;
                        val var6: FlowCollector = this.L$0 as FlowCollector;
                        ResultKt.throwOnFailure(var1);
                        var16.element = null;
                        var3x = var3x;
                        if (DebugKt.getASSERTIONS_ENABLED() && var16.element != null && var21.element <= 0L) {
                           throw new AssertionError();
                        }

                        val var22: SelectImplementation = new SelectImplementation(this.getContext());
                        val var27: SelectBuilder = var22;
                        if (var16.element != null) {
                           OnTimeoutKt.onTimeout(var27, var21.element, (new Function1<Continuation<? super Unit>, Object>(var6, var16, null) {
                              final FlowCollector<T> $downstream;
                              final Ref.ObjectRef<Object> $lastValue;
                              int label;

                              {
                                 super(1, var3x);
                                 this.$downstream = var1;
                                 this.$lastValue = var2x;
                              }

                              @Override
                              public final Continuation<Unit> create(Continuation<?> var1) {
                                 return new <anonymous constructor>(this.$downstream, this.$lastValue, var1);
                              }

                              public final Object invoke(Continuation<? super Unit> var1) {
                                 return (this.create(var1) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                              }

                              @Override
                              public final Object invokeSuspend(Object var1) {
                                 val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                 if (this.label != 0) {
                                    if (this.label != 1) {
                                       throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }

                                    ResultKt.throwOnFailure(var1);
                                 } else {
                                    ResultKt.throwOnFailure(var1);
                                    val var5: FlowCollector = this.$downstream;
                                    var1 = this.$lastValue.element;
                                    if (this.$lastValue.element === NullSurrogateKt.NULL) {
                                       var1 = null;
                                    }

                                    val var8: Continuation = this;
                                    this.label = 1;
                                    if (var5.emit(var1, var8) === var4) {
                                       return var4;
                                    }
                                 }

                                 this.$lastValue.element = null;
                                 return Unit.INSTANCE;
                              }
                           }) as Function1);
                        }

                        var27.invoke(
                           var3x.getOnReceiveCatching(),
                           (new Function2<ChannelResult<? extends Object>, Continuation<? super Unit>, Object>(var16, var6, null) {
                              final FlowCollector<T> $downstream;
                              final Ref.ObjectRef<Object> $lastValue;
                              Object L$0;
                              Object L$1;
                              int label;

                              {
                                 super(2, var3x);
                                 this.$lastValue = var1;
                                 this.$downstream = var2x;
                              }

                              @Override
                              public final Continuation<Unit> create(Object var1, Continuation<?> var2x) {
                                 val var3x: Function2 = new <anonymous constructor>(this.$lastValue, this.$downstream, var2x);
                                 var3x.L$0 = var1;
                                 return var3x as Continuation<Unit>;
                              }

                              public final Object invoke_WpGqRn0/* $VF was: invoke-WpGqRn0*/(Object var1, Continuation<? super Unit> var2x) {
                                 return (this.create(ChannelResult.box-impl(var1), var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                              }

                              @Override
                              public final Object invokeSuspend(Object var1) {
                                 var var13: Ref.ObjectRef;
                                 label37: {
                                    val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                    if (this.label != 0) {
                                       if (this.label != 1) {
                                          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                       }

                                       var13 = this.L$1 as Ref.ObjectRef;
                                       ResultKt.throwOnFailure(var1);
                                       var1 = var13;
                                    } else {
                                       ResultKt.throwOnFailure(var1);
                                       val var7: Any = (this.L$0 as ChannelResult).unbox-impl();
                                       val var3x: Boolean = var7 is ChannelResult.Failed;
                                       if (var7 !is ChannelResult.Failed) {
                                          this.$lastValue.element = var7;
                                       }

                                       var1 = this.$lastValue;
                                       val var8: FlowCollector = this.$downstream;
                                       if (!var3x) {
                                          return Unit.INSTANCE;
                                       }

                                       val var12: java.lang.Throwable = ChannelResult.exceptionOrNull-impl(var7);
                                       if (var12 != null) {
                                          throw var12;
                                       }

                                       var13 = var1;
                                       if (var1.element == null) {
                                          break label37;
                                       }

                                       var var14: Any = var1.element;
                                       if (var1.element === NullSurrogateKt.NULL) {
                                          var14 = null;
                                       }

                                       this.L$0 = var7;
                                       this.L$1 = var1;
                                       this.label = 1;
                                       if (var8.emit(var14, this) === var6) {
                                          return var6;
                                       }
                                    }

                                    var13 = var1;
                                 }

                                 var13.element = (T)NullSurrogateKt.DONE;
                                 return Unit.INSTANCE;
                              }
                           }) as Function2
                        );
                        this.L$0 = var6;
                        this.L$1 = var3x;
                        this.L$2 = var16;
                        this.L$3 = null;
                        this.label = 2;
                        if (var22.doSelect(this) === var10) {
                           return var10;
                        }

                        var16 = var16;
                        var1 = var6;
                     }
                  } else {
                     ResultKt.throwOnFailure(var1);
                     val var15: CoroutineScope = this.L$0 as CoroutineScope;
                     var1 = this.L$1 as FlowCollector;
                     var3x = ProduceKt.produce$default(
                        var15, null, 0, (new Function2<ProducerScope<? super Object>, Continuation<? super Unit>, Object>(this.$this_debounceInternal, null) {
                           final Flow<T> $this_debounceInternal;
                           private Object L$0;
                           int label;

                           {
                              super(2, var2x);
                              this.$this_debounceInternal = var1;
                           }

                           @Override
                           public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                              val var3: Function2 = new <anonymous constructor>(this.$this_debounceInternal, var2);
                              var3.L$0 = var1;
                              return var3 as Continuation<Unit>;
                           }

                           public final Object invoke(ProducerScope<Object> var1, Continuation<? super Unit> var2x) {
                              return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                           }

                           @Override
                           public final Object invokeSuspend(Object var1) {
                              val var3x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                              if (this.label != 0) {
                                 if (this.label != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                 }

                                 ResultKt.throwOnFailure(var1);
                              } else {
                                 ResultKt.throwOnFailure(var1);
                                 val var4: ProducerScope = this.L$0 as ProducerScope;
                                 var1 = this.$this_debounceInternal;
                                 val var5: FlowCollector = new FlowCollector(var4) {
                                    final ProducerScope<Object> $$this$produce;

                                    {
                                       this.$$this$produce = var1;
                                    }

                                    @Override
                                    public final Object emit(T var1, Continuation<? super Unit> var2x) {
                                       label27: {
                                          if (var2x is <unrepresentable>) {
                                             val var4x: <unrepresentable> = var2x as <unrepresentable>;
                                             if ((var2x.label and Integer.MIN_VALUE) != 0) {
                                                var4x.label += Integer.MIN_VALUE;
                                                var2x = var4x;
                                                break label27;
                                             }
                                          }

                                          var2x = new ContinuationImpl(this, var2x) {
                                             int label;
                                             Object result;
                                             final <unrepresentable><T> this$0;

                                             {
                                                super(var2x);
                                                this.this$0 = var1;
                                             }

                                             @Override
                                             public final Object invokeSuspend(Object var1) {
                                                this.result = var1;
                                                this.label |= Integer.MIN_VALUE;
                                                return this.this$0.emit(null, this);
                                             }
                                          };
                                       }

                                       var var8: Any = var2x.result;
                                       val var5x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                       if (var2x.label != 0) {
                                          if (var2x.label != 1) {
                                             throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                          }

                                          ResultKt.throwOnFailure(var8);
                                       } else {
                                          ResultKt.throwOnFailure(var8);
                                          val var6: ProducerScope = this.$$this$produce;
                                          var8 = var1;
                                          if (var1 == null) {
                                             var8 = NullSurrogateKt.NULL;
                                          }

                                          var2x.label = 1;
                                          if (var6.send(var8, var2x) === var5x) {
                                             return var5x;
                                          }
                                       }

                                       return Unit.INSTANCE;
                                    }
                                 };
                                 val var7: Continuation = this;
                                 this.label = 1;
                                 if (var1.collect(var5, var7) === var3x) {
                                    return var3x;
                                 }
                              }

                              return Unit.INSTANCE;
                           }
                        }) as Function2, 3, null
                     );
                     var16 = new Ref.ObjectRef();
                  }

                  while (var16.element != NullSurrogateKt.DONE) {
                     val var24: Ref.LongRef = new Ref.LongRef();
                     var var37: Ref.LongRef = var24;
                     var var8: Ref.ObjectRef = var16;
                     var var7: ReceiveChannel = var3x;
                     var var28: FlowCollector = var1;
                     if (var16.element != null) {
                        var28 = var16.element;
                        if (var16.element === NullSurrogateKt.NULL) {
                           var28 = null;
                        }

                        var24.element = this.$timeoutMillisSelector.invoke(var28).longValue();
                        if (var24.element < 0L) {
                           throw new IllegalArgumentException("Debounce timeout should not be negative".toString());
                        }

                        var37 = var24;
                        var8 = var16;
                        var7 = var3x;
                        var28 = var1;
                        if (var24.element == 0L) {
                           var7 = var16.element;
                           if (var16.element === NullSurrogateKt.NULL) {
                              var7 = null;
                           }

                           val var36: Continuation = this;
                           this.L$0 = var1;
                           this.L$1 = var3x;
                           this.L$2 = var16;
                           this.L$3 = var24;
                           this.label = 1;
                           var28 = var1;
                           if (var1.emit(var7, var36) === var10) {
                              return var10;
                           }

                           var16.element = null;
                           var7 = var3x;
                           var8 = var16;
                           var37 = var24;
                        }
                     }

                     var3x = var7;
                     if (DebugKt.getASSERTIONS_ENABLED() && var8.element != null && var37.element <= 0L) {
                        throw new AssertionError();
                     }

                     val var25: SelectImplementation = new SelectImplementation(this.getContext());
                     val var31: SelectBuilder = var25;
                     if (var8.element != null) {
                        OnTimeoutKt.onTimeout(var31, var37.element, (new Function1<Continuation<? super Unit>, Object>(var28, var8, null) {
                           final FlowCollector<T> $downstream;
                           final Ref.ObjectRef<Object> $lastValue;
                           int label;

                           {
                              super(1, var3x);
                              this.$downstream = var1;
                              this.$lastValue = var2x;
                           }

                           @Override
                           public final Continuation<Unit> create(Continuation<?> var1) {
                              return (new <anonymous constructor>(this.$downstream, this.$lastValue, var1)) as Continuation<Unit>;
                           }

                           public final Object invoke(Continuation<? super Unit> var1) {
                              return (this.create(var1) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                           }

                           @Override
                           public final Object invokeSuspend(Object var1) {
                              val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                              if (this.label != 0) {
                                 if (this.label != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                 }

                                 ResultKt.throwOnFailure(var1);
                              } else {
                                 ResultKt.throwOnFailure(var1);
                                 val var5: FlowCollector = this.$downstream;
                                 var1 = this.$lastValue.element;
                                 if (this.$lastValue.element === NullSurrogateKt.NULL) {
                                    var1 = null;
                                 }

                                 val var8: Continuation = this;
                                 this.label = 1;
                                 if (var5.emit(var1, var8) === var4) {
                                    return var4;
                                 }
                              }

                              this.$lastValue.element = null;
                              return Unit.INSTANCE;
                           }
                        }) as Function1);
                     }

                     var31.invoke(
                        var7.getOnReceiveCatching(), (new Function2<ChannelResult<? extends Object>, Continuation<? super Unit>, Object>(var8, var28, null) {
                           final FlowCollector<T> $downstream;
                           final Ref.ObjectRef<Object> $lastValue;
                           Object L$0;
                           Object L$1;
                           int label;

                           {
                              super(2, var3x);
                              this.$lastValue = var1;
                              this.$downstream = var2x;
                           }

                           @Override
                           public final Continuation<Unit> create(Object var1, Continuation<?> var2x) {
                              val var3x: Function2 = new <anonymous constructor>(this.$lastValue, this.$downstream, var2x);
                              var3x.L$0 = var1;
                              return var3x as Continuation<Unit>;
                           }

                           public final Object invoke_WpGqRn0/* $VF was: invoke-WpGqRn0*/(Object var1, Continuation<? super Unit> var2x) {
                              return (this.create(ChannelResult.box-impl(var1), var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                           }

                           @Override
                           public final Object invokeSuspend(Object var1) {
                              var var13: Ref.ObjectRef;
                              label37: {
                                 val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                 if (this.label != 0) {
                                    if (this.label != 1) {
                                       throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }

                                    var13 = this.L$1 as Ref.ObjectRef;
                                    ResultKt.throwOnFailure(var1);
                                    var1 = var13;
                                 } else {
                                    ResultKt.throwOnFailure(var1);
                                    val var7: Any = (this.L$0 as ChannelResult).unbox-impl();
                                    val var3x: Boolean = var7 is ChannelResult.Failed;
                                    if (var7 !is ChannelResult.Failed) {
                                       this.$lastValue.element = var7;
                                    }

                                    var1 = this.$lastValue;
                                    val var8: FlowCollector = this.$downstream;
                                    if (!var3x) {
                                       return Unit.INSTANCE;
                                    }

                                    val var12: java.lang.Throwable = ChannelResult.exceptionOrNull-impl(var7);
                                    if (var12 != null) {
                                       throw var12;
                                    }

                                    var13 = var1;
                                    if (var1.element == null) {
                                       break label37;
                                    }

                                    var var14: Any = var1.element;
                                    if (var1.element === NullSurrogateKt.NULL) {
                                       var14 = null;
                                    }

                                    this.L$0 = var7;
                                    this.L$1 = var1;
                                    this.label = 1;
                                    if (var8.emit(var14, this) === var6) {
                                       return var6;
                                    }
                                 }

                                 var13 = var1;
                              }

                              var13.element = (T)NullSurrogateKt.DONE;
                              return Unit.INSTANCE;
                           }
                        }) as Function2
                     );
                     this.L$0 = var28;
                     this.L$1 = var7;
                     this.L$2 = var8;
                     this.L$3 = null;
                     this.label = 2;
                     if (var25.doSelect(this) === var10) {
                        return var10;
                     }

                     var16 = var8;
                     var1 = var28;
                  }

                  return Unit.INSTANCE;
               }
            }
         ) as Function3
      );
   }

   @JvmStatic
   internal fun CoroutineScope.fixedPeriodTicker(delayMillis: Long, initialDelayMillis: Long = var1): ReceiveChannel<Unit> {
      if (var1 >= 0L) {
         if (var3 >= 0L) {
            return ProduceKt.produce$default(var0, null, 0, (new Function2<ProducerScope<? super Unit>, Continuation<? super Unit>, Object>(var3, var1, null) {
               final long $delayMillis;
               final long $initialDelayMillis;
               private Object L$0;
               int label;

               {
                  super(2, var5);
                  this.$initialDelayMillis = var1;
                  this.$delayMillis = var3x;
               }

               @Override
               public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                  val var3: Function2 = new <anonymous constructor>(this.$initialDelayMillis, this.$delayMillis, var2);
                  var3.L$0 = var1;
                  return var3 as Continuation<Unit>;
               }

               public final Object invoke(ProducerScope<? super Unit> var1, Continuation<? super Unit> var2x) {
                  return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
               }

               // $VF: Irreducible bytecode was duplicated to produce valid code
               @Override
               public final Object invokeSuspend(Object var1) {
                  val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (this.label != 0) {
                     if (this.label != 1) {
                        if (this.label != 2) {
                           if (this.label != 3) {
                              throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                           }

                           val var5: ProducerScope = this.L$0 as ProducerScope;
                           ResultKt.throwOnFailure(var1);
                           var1 = var5;
                        } else {
                           val var12: ProducerScope = this.L$0 as ProducerScope;
                           ResultKt.throwOnFailure(var1);
                           var1 = var12;
                           val var3x: Long = this.$delayMillis;
                           val var13: Continuation = this;
                           this.L$0 = var12;
                           this.label = 3;
                           if (DelayKt.delay(var3x, var13) === var6) {
                              return var6;
                           }
                        }
                     } else {
                        val var14: ProducerScope = this.L$0 as ProducerScope;
                        ResultKt.throwOnFailure(var1);
                        var1 = var14;
                     }
                  } else {
                     ResultKt.throwOnFailure(var1);
                     var1 = this.L$0 as ProducerScope;
                     val var10: Long = this.$initialDelayMillis;
                     val var15: Continuation = this;
                     this.L$0 = var1;
                     this.label = 1;
                     if (DelayKt.delay(var10, var15) === var6) {
                        return var6;
                     }
                  }

                  val var11: Long;
                  var var17: Continuation;
                  do {
                     val var7: SendChannel = var1.getChannel();
                     val var8: Unit = Unit.INSTANCE;
                     var17 = this;
                     this.L$0 = var1;
                     this.label = 2;
                     if (var7.send(var8, var17) === var6) {
                        return var6;
                     }

                     var11 = this.$delayMillis;
                     var17 = this;
                     this.L$0 = var1;
                     this.label = 3;
                  } while (DelayKt.delay(var11, var17) != var6);

                  return var6;
               }
            }) as Function2, 1, null);
         } else {
            val var6: StringBuilder = new StringBuilder("Expected non-negative initial delay, but has ");
            var6.append(var3);
            var6.append(" ms");
            throw new IllegalArgumentException(var6.toString().toString());
         }
      } else {
         val var5: StringBuilder = new StringBuilder("Expected non-negative delay, but has ");
         var5.append(var1);
         var5.append(" ms");
         throw new IllegalArgumentException(var5.toString().toString());
      }
   }

   @JvmStatic
   public fun <T> Flow<T>.sample(periodMillis: Long): Flow<T> {
      if (var1 > 0L) {
         return FlowCoroutineKt.scopedFlow(
            (
               new Function3<CoroutineScope, FlowCollector<? super T>, Continuation<? super Unit>, Object>(var1, var0, null) {
                  final long $periodMillis;
                  final Flow<T> $this_sample;
                  private Object L$0;
                  Object L$1;
                  Object L$2;
                  Object L$3;
                  int label;

                  {
                     super(3, var4x);
                     this.$periodMillis = var1;
                     this.$this_sample = var3x;
                  }

                  public final Object invoke(CoroutineScope var1, FlowCollector<? super T> var2, Continuation<? super Unit> var3) {
                     val var4: Function3 = new <anonymous constructor>(this.$periodMillis, this.$this_sample, var3);
                     var4.L$0 = var1;
                     var4.L$1 = var2;
                     return var4.invokeSuspend(Unit.INSTANCE);
                  }

                  @Override
                  public final Object invokeSuspend(Object var1) {
                     val var7: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     val var3x: ReceiveChannel;
                     val var4x: Ref.ObjectRef;
                     val var5: FlowCollector;
                     if (this.label != 0) {
                        if (this.label != 1) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        val var6: ReceiveChannel = this.L$3 as ReceiveChannel;
                        var4x = this.L$2 as Ref.ObjectRef;
                        var3x = this.L$1 as ReceiveChannel;
                        var5 = this.L$0 as FlowCollector;
                        ResultKt.throwOnFailure(var1);
                        var1 = var6;
                     } else {
                        ResultKt.throwOnFailure(var1);
                        val var10: CoroutineScope = this.L$0 as CoroutineScope;
                        var5 = this.L$1 as FlowCollector;
                        var3x = ProduceKt.produce$default(
                           var10, null, -1, (new Function2<ProducerScope<? super Object>, Continuation<? super Unit>, Object>(this.$this_sample, null) {
                              final Flow<T> $this_sample;
                              private Object L$0;
                              int label;

                              {
                                 super(2, var2x);
                                 this.$this_sample = var1;
                              }

                              @Override
                              public final Continuation<Unit> create(Object var1, Continuation<?> var2x) {
                                 val var3x: Function2 = new <anonymous constructor>(this.$this_sample, var2x);
                                 var3x.L$0 = var1;
                                 return var3x as Continuation<Unit>;
                              }

                              public final Object invoke(ProducerScope<Object> var1, Continuation<? super Unit> var2x) {
                                 return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                              }

                              @Override
                              public final Object invokeSuspend(Object var1) {
                                 val var3x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                 if (this.label != 0) {
                                    if (this.label != 1) {
                                       throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }

                                    ResultKt.throwOnFailure(var1);
                                 } else {
                                    ResultKt.throwOnFailure(var1);
                                    val var4: ProducerScope = this.L$0 as ProducerScope;
                                    var1 = this.$this_sample;
                                    val var7: FlowCollector = new FlowCollector(var4) {
                                       final ProducerScope<Object> $$this$produce;

                                       {
                                          this.$$this$produce = var1;
                                       }

                                       @Override
                                       public final Object emit(T var1, Continuation<? super Unit> var2x) {
                                          label27: {
                                             if (var2x is <unrepresentable>) {
                                                val var4x: <unrepresentable> = var2x as <unrepresentable>;
                                                if ((var2x.label and Integer.MIN_VALUE) != 0) {
                                                   var4x.label += Integer.MIN_VALUE;
                                                   var2x = var4x;
                                                   break label27;
                                                }
                                             }

                                             var2x = new ContinuationImpl(this, var2x) {
                                                int label;
                                                Object result;
                                                final <unrepresentable><T> this$0;

                                                {
                                                   super(var2x);
                                                   this.this$0 = var1;
                                                }

                                                @Override
                                                public final Object invokeSuspend(Object var1) {
                                                   this.result = var1;
                                                   this.label |= Integer.MIN_VALUE;
                                                   return this.this$0.emit(null, this);
                                                }
                                             };
                                          }

                                          var var8: Any = var2x.result;
                                          val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                          if (var2x.label != 0) {
                                             if (var2x.label != 1) {
                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                             }

                                             ResultKt.throwOnFailure(var8);
                                          } else {
                                             ResultKt.throwOnFailure(var8);
                                             val var6: ProducerScope = this.$$this$produce;
                                             var8 = var1;
                                             if (var1 == null) {
                                                var8 = NullSurrogateKt.NULL;
                                             }

                                             var2x.label = 1;
                                             if (var6.send(var8, var2x) === var5) {
                                                return var5;
                                             }
                                          }

                                          return Unit.INSTANCE;
                                       }
                                    };
                                    val var5: Continuation = this;
                                    this.label = 1;
                                    if (var1.collect(var7, var5) === var3x) {
                                       return var3x;
                                    }
                                 }

                                 return Unit.INSTANCE;
                              }
                           }) as Function2, 1, null
                        );
                        var4x = new Ref.ObjectRef();
                        var1 = FlowKt.fixedPeriodTicker$default(var10, this.$periodMillis, 0L, 2, null);
                     }

                     while (var4x.element != NullSurrogateKt.DONE) {
                        val var11: SelectImplementation = new SelectImplementation(this.getContext());
                        val var8: SelectBuilder = var11;
                        var11.invoke(
                           var3x.getOnReceiveCatching(),
                           (new Function2<ChannelResult<? extends Object>, Continuation<? super Unit>, Object>(var4x, var1, null) {
                              final Ref.ObjectRef<Object> $lastValue;
                              final ReceiveChannel<Unit> $ticker;
                              Object L$0;
                              int label;

                              {
                                 super(2, var3x);
                                 this.$lastValue = var1;
                                 this.$ticker = var2x;
                              }

                              @Override
                              public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                                 val var3: Function2 = new <anonymous constructor>(this.$lastValue, this.$ticker, var2);
                                 var3.L$0 = var1;
                                 return var3 as Continuation<Unit>;
                              }

                              public final Object invoke_WpGqRn0/* $VF was: invoke-WpGqRn0*/(Object var1, Continuation<? super Unit> var2x) {
                                 return (this.create(ChannelResult.box-impl(var1), var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                              }

                              @Override
                              public final Object invokeSuspend(Object var1) {
                                 IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                 if (this.label == 0) {
                                    ResultKt.throwOnFailure(var1);
                                    var var4: Any = (this.L$0 as ChannelResult).unbox-impl();
                                    val var2x: Boolean = var4 is ChannelResult.Failed;
                                    if (var4 !is ChannelResult.Failed) {
                                       this.$lastValue.element = var4;
                                    }

                                    val var3x: ReceiveChannel = this.$ticker;
                                    var1 = this.$lastValue;
                                    if (var2x) {
                                       var4 = ChannelResult.exceptionOrNull-impl(var4);
                                       if (var4 != null) {
                                          throw var4;
                                       }

                                       var3x.cancel(new ChildCancelledException());
                                       var1.element = (T)NullSurrogateKt.DONE;
                                    }

                                    return Unit.INSTANCE;
                                 } else {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                 }
                              }
                           }) as Function2
                        );
                        var8.invoke(var1.getOnReceive(), (new Function2<Unit, Continuation<? super Unit>, Object>(var4x, var5, null) {
                           final FlowCollector<T> $downstream;
                           final Ref.ObjectRef<Object> $lastValue;
                           int label;

                           {
                              super(2, var3x);
                              this.$lastValue = var1;
                              this.$downstream = var2x;
                           }

                           @Override
                           public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                              return new <anonymous constructor>(this.$lastValue, this.$downstream, var2);
                           }

                           public final Object invoke(Unit var1, Continuation<? super Unit> var2x) {
                              return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                           }

                           @Override
                           public final Object invokeSuspend(Object var1) {
                              val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                              if (this.label != 0) {
                                 if (this.label != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                 }

                                 ResultKt.throwOnFailure(var1);
                              } else {
                                 ResultKt.throwOnFailure(var1);
                                 var var3: Any = this.$lastValue.element;
                                 if (this.$lastValue.element == null) {
                                    return Unit.INSTANCE;
                                 }

                                 this.$lastValue.element = null;
                                 val var5: FlowCollector = this.$downstream;
                                 var1 = var3;
                                 if (var3 === NullSurrogateKt.NULL) {
                                    var1 = null;
                                 }

                                 var3 = this;
                                 this.label = 1;
                                 if (var5.emit(var1, (Continuation<? super Unit>)var3) === var4) {
                                    return var4;
                                 }
                              }

                              return Unit.INSTANCE;
                           }
                        }) as Function2);
                        this.L$0 = var5;
                        this.L$1 = var3x;
                        this.L$2 = var4x;
                        this.L$3 = var1;
                        this.label = 1;
                        if (var11.doSelect(this) === var7) {
                           return var7;
                        }
                     }

                     return Unit.INSTANCE;
                  }
               }
            ) as Function3
         );
      } else {
         throw new IllegalArgumentException("Sample period should be positive".toString());
      }
   }

   @JvmStatic
   public fun <T> Flow<T>.sample(period: Duration): Flow<T> {
      return FlowKt.sample(var0, DelayKt.toDelayMillis-LRDsOJo(var1));
   }

   @JvmStatic
   public fun <T> Flow<T>.timeout(timeout: Duration): Flow<T> {
      return timeoutInternal-HG0u8IE$FlowKt__DelayKt(var0, var1);
   }

   @JvmStatic
   private fun <T> Flow<T>.timeoutInternal(timeout: Duration): Flow<T> {
      return FlowCoroutineKt.scopedFlow(
         (
            new Function3<CoroutineScope, FlowCollector<? super T>, Continuation<? super Unit>, Object>(var1, var0, null) {
               final Flow<T> $this_timeoutInternal;
               final long $timeout;
               long J$0;
               private Object L$0;
               Object L$1;
               int label;

               {
                  super(3, var4x);
                  this.$timeout = var1;
                  this.$this_timeoutInternal = var3x;
               }

               public final Object invoke(CoroutineScope var1, FlowCollector<? super T> var2, Continuation<? super Unit> var3) {
                  val var4: Function3 = new <anonymous constructor>(this.$timeout, this.$this_timeoutInternal, var3);
                  var4.L$0 = var1;
                  var4.L$1 = var2;
                  return var4.invokeSuspend(Unit.INSTANCE);
               }

               // $VF: Irreducible bytecode was duplicated to produce valid code
               @Override
               public final Object invokeSuspend(Object var1) {
                  val var9: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  val var3x: Long;
                  val var5: ReceiveChannel;
                  if (this.label != 0) {
                     if (this.label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     var3x = this.J$0;
                     var5 = this.L$1 as ReceiveChannel;
                     val var6: FlowCollector = this.L$0 as FlowCollector;
                     ResultKt.throwOnFailure(var1);
                     var1 = var6;
                     if (!var1 as java.lang.Boolean) {
                        return Unit.INSTANCE;
                     }
                  } else {
                     ResultKt.throwOnFailure(var1);
                     val var11: CoroutineScope = this.L$0 as CoroutineScope;
                     var1 = this.L$1 as FlowCollector;
                     if (Duration.compareTo-LRDsOJo(this.$timeout, Duration.Companion.getZERO-UwyO8pc()) <= 0) {
                        throw new TimeoutCancellationException("Timed out immediately");
                     }

                     var5 = FlowKt.produceIn(FlowKt.buffer$default(this.$this_timeoutInternal, 0, null, 2, null), var11);
                     var3x = this.$timeout;
                  }

                  val var8: Any;
                  do {
                     val var12: SelectImplementation = new SelectImplementation(this.getContext());
                     val var13: SelectBuilder = var12;
                     var12.invoke(
                        var5.getOnReceiveCatching(), (new Function2<ChannelResult<? extends T>, Continuation<? super java.lang.Boolean>, Object>(var1, null) {
                           final FlowCollector<T> $downStream;
                           Object L$0;
                           int label;

                           {
                              super(2, var2x);
                              this.$downStream = var1;
                           }

                           @Override
                           public final Continuation<Unit> create(Object var1, Continuation<?> var2x) {
                              val var3x: Function2 = new <anonymous constructor>(this.$downStream, var2x);
                              var3x.L$0 = var1;
                              return var3x as Continuation<Unit>;
                           }

                           public final Object invoke_WpGqRn0/* $VF was: invoke-WpGqRn0*/(Object var1, Continuation<? super java.lang.Boolean> var2x) {
                              return (this.create(ChannelResult.box-impl(var1), var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                           }

                           @Override
                           public final Object invokeSuspend(Object var1) {
                              var var7: Any;
                              label24: {
                                 val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                 if (this.label != 0) {
                                    if (this.label != 1) {
                                       throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }

                                    var7 = this.L$0;
                                    ResultKt.throwOnFailure(var1);
                                    var1 = var7;
                                 } else {
                                    ResultKt.throwOnFailure(var1);
                                    var1 = (this.L$0 as ChannelResult).unbox-impl();
                                    val var5: FlowCollector = this.$downStream;
                                    var7 = var1;
                                    if (var1 is ChannelResult.Failed) {
                                       break label24;
                                    }

                                    this.L$0 = var1;
                                    this.label = 1;
                                    if (var5.emit(var1, this) === var4) {
                                       return var4;
                                    }
                                 }

                                 var7 = var1;
                              }

                              if (var7 is ChannelResult.Closed) {
                                 ChannelResult.exceptionOrNull-impl(var7);
                                 return Boxing.boxBoolean(false);
                              } else {
                                 return Boxing.boxBoolean(true);
                              }
                           }
                        }) as Function2
                     );
                     OnTimeoutKt.onTimeout-8Mi8wO0(var13, var3x, (new Function1<Continuation<?>, Object>(var3x, null) {
                        final long $timeout;
                        int label;

                        {
                           super(1, var3x);
                           this.$timeout = var1;
                        }

                        @Override
                        public final Continuation<Unit> create(Continuation<?> var1) {
                           return new <anonymous constructor>(this.$timeout, var1);
                        }

                        public final Object invoke(Continuation<?> var1) {
                           return (this.create(var1) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override
                        public final Object invokeSuspend(Object var1) {
                           IntrinsicsKt.getCOROUTINE_SUSPENDED();
                           if (this.label != 0) {
                              throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                           } else {
                              ResultKt.throwOnFailure(var1);
                              var1 = new StringBuilder("Timed out waiting for ");
                              var1.append(Duration.toString-impl(this.$timeout));
                              throw new TimeoutCancellationException(var1.toString());
                           }
                        }
                     }) as Function1);
                     this.L$0 = var1;
                     this.L$1 = var5;
                     this.J$0 = var3x;
                     this.label = 1;
                     var8 = var12.doSelect(this);
                     if (var8 === var9) {
                        return var9;
                     }

                     var1 = var1;
                  } while ((java.lang.Boolean)var8);

                  return Unit.INSTANCE;
               }
            }
         ) as Function3
      );
   }
}
