package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlin.jvm.functions.Function4
import kotlin.jvm.functions.Function5
import kotlin.jvm.functions.Function6
import kotlin.jvm.functions.Function7
import kotlinx.coroutines.flow.internal.CombineKt

@JvmSynthetic
internal class FlowKt__ZipKt {
   @JvmStatic
   public fun <T1, T2, R> combine(flow: Flow<T1>, flow2: Flow<T2>, transform: (T1, T2, Continuation<R>) -> Any?): Flow<R> {
      return FlowKt.flowCombine(var0, var1, var2);
   }

   @JvmStatic
   public fun <T1, T2, T3, R> combine(flow: Flow<T1>, flow2: Flow<T2>, flow3: Flow<T3>, transform: (T1, T2, T3, Continuation<R>) -> Any?): Flow<R> {
      return new Flow<R>(new Flow[]{var0, var1, var2}, var3) {
         final Flow[] $flows$inlined;
         final Function4 $transform$inlined$1;

         {
            this.$flows$inlined = var1;
            this.$transform$inlined$1 = var2;
         }

         @Override
         public Object collect(FlowCollector var1, Continuation var2) {
            val var3: Any = CombineKt.combineInternal(
               var1,
               this.$flows$inlined,
               FlowKt__ZipKt.access$nullArrayFactory(),
               (new Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object>(null, this.$transform$inlined$1) {
                  final Function4 $transform$inlined;
                  private Object L$0;
                  Object L$1;
                  int label;

                  {
                     super(3, var1);
                     this.$transform$inlined = var2x;
                  }

                  public final Object invoke(FlowCollector<? super R> var1, Object[] var2x, Continuation<? super Unit> var3x) {
                     val var4: Function3 = new <anonymous constructor>(var3x, this.$transform$inlined);
                     var4.L$0 = var1;
                     var4.L$1 = var2x;
                     return var4.invokeSuspend(Unit.INSTANCE);
                  }

                  @Override
                  public final Object invokeSuspend(Object var1) {
                     val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     val var3x: FlowCollector;
                     if (this.label != 0) {
                        if (this.label != 1) {
                           if (this.label != 2) {
                              throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                           }

                           ResultKt.throwOnFailure(var1);
                           return Unit.INSTANCE;
                        }

                        var3x = this.L$0 as FlowCollector;
                        ResultKt.throwOnFailure(var1);
                     } else {
                        ResultKt.throwOnFailure(var1);
                        var3x = this.L$0 as FlowCollector;
                        var var4x: Array<Any> = this.L$1 as Array<Any>;
                        var1 = this;
                        val var9: Function4 = this.$transform$inlined;
                        val var7: Any = var4x[0];
                        val var6: Any = var4x[1];
                        var4x = (Object[])var4x[2];
                        this.L$0 = var3x;
                        this.label = 1;
                        var4x = (Object[])var9.invoke(var7, var6, var4x, this);
                        var1 = var4x;
                        if (var4x === var5) {
                           return var5;
                        }
                     }

                     val var12: Continuation = this;
                     this.L$0 = null;
                     this.label = 2;
                     return if (var3x.emit(var1, var12) === var5) var5 else Unit.INSTANCE;
                  }
               }) as Function3,
               var2
            );
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T1, T2, T3, T4, R> combine(
      flow: Flow<T1>,
      flow2: Flow<T2>,
      flow3: Flow<T3>,
      flow4: Flow<T4>,
      transform: (T1, T2, T3, T4, Continuation<R>) -> Any?
   ): Flow<R> {
      return new Flow<R>(new Flow[]{var0, var1, var2, var3}, var4) {
         final Flow[] $flows$inlined;
         final Function5 $transform$inlined$1;

         {
            this.$flows$inlined = var1;
            this.$transform$inlined$1 = var2;
         }

         @Override
         public Object collect(FlowCollector var1, Continuation var2) {
            val var3: Any = CombineKt.combineInternal(
               var1,
               this.$flows$inlined,
               FlowKt__ZipKt.access$nullArrayFactory(),
               (new Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object>(null, this.$transform$inlined$1) {
                  final Function5 $transform$inlined;
                  private Object L$0;
                  Object L$1;
                  int label;

                  {
                     super(3, var1);
                     this.$transform$inlined = var2x;
                  }

                  public final Object invoke(FlowCollector<? super R> var1, Object[] var2x, Continuation<? super Unit> var3x) {
                     val var4: Function3 = new <anonymous constructor>(var3x, this.$transform$inlined);
                     var4.L$0 = var1;
                     var4.L$1 = var2x;
                     return var4.invokeSuspend(Unit.INSTANCE);
                  }

                  @Override
                  public final Object invokeSuspend(Object var1) {
                     val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     val var3x: FlowCollector;
                     if (this.label != 0) {
                        if (this.label != 1) {
                           if (this.label != 2) {
                              throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                           }

                           ResultKt.throwOnFailure(var1);
                           return Unit.INSTANCE;
                        }

                        var3x = this.L$0 as FlowCollector;
                        ResultKt.throwOnFailure(var1);
                     } else {
                        ResultKt.throwOnFailure(var1);
                        var3x = this.L$0 as FlowCollector;
                        var var4x: Array<Any> = this.L$1 as Array<Any>;
                        var1 = this;
                        val var7: Function5 = this.$transform$inlined;
                        val var6: Any = var4x[0];
                        var1 = var4x[1];
                        val var8: Any = var4x[2];
                        var4x = (Object[])var4x[3];
                        this.L$0 = var3x;
                        this.label = 1;
                        var4x = (Object[])var7.invoke(var6, var1, var8, var4x, this);
                        var1 = var4x;
                        if (var4x === var5) {
                           return var5;
                        }
                     }

                     val var13: Continuation = this;
                     this.L$0 = null;
                     this.label = 2;
                     return if (var3x.emit(var1, var13) === var5) var5 else Unit.INSTANCE;
                  }
               }) as Function3,
               var2
            );
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T1, T2, T3, T4, T5, R> combine(
      flow: Flow<T1>,
      flow2: Flow<T2>,
      flow3: Flow<T3>,
      flow4: Flow<T4>,
      flow5: Flow<T5>,
      transform: (T1, T2, T3, T4, T5, Continuation<R>) -> Any?
   ): Flow<R> {
      return new Flow<R>(new Flow[]{var0, var1, var2, var3, var4}, var5) {
         final Flow[] $flows$inlined;
         final Function6 $transform$inlined$1;

         {
            this.$flows$inlined = var1;
            this.$transform$inlined$1 = var2;
         }

         @Override
         public Object collect(FlowCollector var1, Continuation var2) {
            val var3: Any = CombineKt.combineInternal(
               var1,
               this.$flows$inlined,
               FlowKt__ZipKt.access$nullArrayFactory(),
               (new Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object>(null, this.$transform$inlined$1) {
                  final Function6 $transform$inlined;
                  private Object L$0;
                  Object L$1;
                  int label;

                  {
                     super(3, var1);
                     this.$transform$inlined = var2x;
                  }

                  public final Object invoke(FlowCollector<? super R> var1, Object[] var2x, Continuation<? super Unit> var3x) {
                     val var4: Function3 = new <anonymous constructor>(var3x, this.$transform$inlined);
                     var4.L$0 = var1;
                     var4.L$1 = var2x;
                     return var4.invokeSuspend(Unit.INSTANCE);
                  }

                  @Override
                  public final Object invokeSuspend(Object var1) {
                     val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     val var3x: FlowCollector;
                     if (this.label != 0) {
                        if (this.label != 1) {
                           if (this.label != 2) {
                              throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                           }

                           ResultKt.throwOnFailure(var1);
                           return Unit.INSTANCE;
                        }

                        var3x = this.L$0 as FlowCollector;
                        ResultKt.throwOnFailure(var1);
                     } else {
                        ResultKt.throwOnFailure(var1);
                        var3x = this.L$0 as FlowCollector;
                        var var9: Array<Any> = this.L$1 as Array<Any>;
                        var1 = this;
                        val var11: Function6 = this.$transform$inlined;
                        val var6: Any = var9[0];
                        var var4x: Any = var9[1];
                        val var8: Any = var9[2];
                        val var7: Any = var9[3];
                        var9 = (Object[])var9[4];
                        this.L$0 = var3x;
                        this.label = 1;
                        var4x = var11.invoke(var6, var4x, var8, var7, var9, this);
                        var1 = (Continuation)var4x;
                        if (var4x === var5) {
                           return var5;
                        }
                     }

                     val var13: Continuation = this;
                     this.L$0 = null;
                     this.label = 2;
                     return if (var3x.emit(var1, var13) === var5) var5 else Unit.INSTANCE;
                  }
               }) as Function3,
               var2
            );
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T1, T2, R> combineTransform(flow: Flow<T1>, flow2: Flow<T2>, transform: (FlowCollector<R>, T1, T2, Continuation<Unit>) -> Any?): Flow<R> {
      return FlowKt.flow((new Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object>(new Flow[]{var0, var1}, null, var2) {
         final Flow[] $flows;
         final Function4 $transform$inlined;
         private Object L$0;
         int label;

         {
            super(2, var2x);
            this.$flows = var1;
            this.$transform$inlined = var3x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$flows, var2, this.$transform$inlined);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(FlowCollector<? super R> var1, Continuation<? super Unit> var2x) {
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
               val var5: FlowCollector = this.L$0 as FlowCollector;
               val var6: Array<Flow> = this.$flows;
               val var7: Function0 = FlowKt__ZipKt.access$nullArrayFactory();
               val var4: Function3 = (new Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object>(null, this.$transform$inlined) {
                  final Function4 $transform$inlined;
                  private Object L$0;
                  Object L$1;
                  int label;

                  {
                     super(3, var1);
                     this.$transform$inlined = var2x;
                  }

                  public final Object invoke(FlowCollector<? super R> var1, Object[] var2x, Continuation<? super Unit> var3x) {
                     val var4x: Function3 = new <anonymous constructor>(var3x, this.$transform$inlined);
                     var4x.L$0 = var1;
                     var4x.L$1 = var2x;
                     return var4x.invokeSuspend(Unit.INSTANCE);
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
                        var1 = this.L$0 as FlowCollector;
                        var var6: Array<Any> = this.L$1 as Array<Any>;
                        val var4x: Continuation = this;
                        val var8: Function4 = this.$transform$inlined;
                        val var5: Any = var6[0];
                        var6 = (Object[])var6[1];
                        this.label = 1;
                        if (var8.invoke(var1, var5, var6, this) === var3x) {
                           return var3x;
                        }
                     }

                     return Unit.INSTANCE;
                  }
               }) as Function3;
               var1 = this;
               this.label = 1;
               if (CombineKt.combineInternal(var5, var6, var7, var4, var1) === var3x) {
                  return var3x;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @JvmStatic
   public fun <T1, T2, T3, R> combineTransform(
      flow: Flow<T1>,
      flow2: Flow<T2>,
      flow3: Flow<T3>,
      transform: (FlowCollector<R>, T1, T2, T3, Continuation<Unit>) -> Any?
   ): Flow<R> {
      return FlowKt.flow((new Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object>(new Flow[]{var0, var1, var2}, null, var3) {
         final Flow[] $flows;
         final Function5 $transform$inlined;
         private Object L$0;
         int label;

         {
            super(2, var2x);
            this.$flows = var1;
            this.$transform$inlined = var3x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$flows, var2, this.$transform$inlined);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(FlowCollector<? super R> var1, Continuation<? super Unit> var2x) {
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
               val var7: FlowCollector = this.L$0 as FlowCollector;
               var1 = this.$flows;
               val var6: Function0 = FlowKt__ZipKt.access$nullArrayFactory();
               val var4: Function3 = (new Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object>(null, this.$transform$inlined) {
                  final Function5 $transform$inlined;
                  private Object L$0;
                  Object L$1;
                  int label;

                  {
                     super(3, var1);
                     this.$transform$inlined = var2x;
                  }

                  public final Object invoke(FlowCollector<? super R> var1, Object[] var2x, Continuation<? super Unit> var3x) {
                     val var4x: Function3 = new <anonymous constructor>(var3x, this.$transform$inlined);
                     var4x.L$0 = var1;
                     var4x.L$1 = var2x;
                     return var4x.invokeSuspend(Unit.INSTANCE);
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
                        var1 = this.L$0 as FlowCollector;
                        var var6: Array<Any> = this.L$1 as Array<Any>;
                        var var4x: Continuation = this;
                        val var7: Function5 = this.$transform$inlined;
                        val var5: Any = var6[0];
                        var4x = (Continuation)var6[1];
                        var6 = (Object[])var6[2];
                        this.label = 1;
                        if (var7.invoke(var1, var5, var4x, var6, this) === var3x) {
                           return var3x;
                        }
                     }

                     return Unit.INSTANCE;
                  }
               }) as Function3;
               val var5: Continuation = this;
               this.label = 1;
               if (CombineKt.combineInternal(var7, var1, var6, var4, var5) === var3x) {
                  return var3x;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @JvmStatic
   public fun <T1, T2, T3, T4, R> combineTransform(
      flow: Flow<T1>,
      flow2: Flow<T2>,
      flow3: Flow<T3>,
      flow4: Flow<T4>,
      transform: (FlowCollector<R>, T1, T2, T3, T4, Continuation<Unit>) -> Any?
   ): Flow<R> {
      return FlowKt.flow((new Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object>(new Flow[]{var0, var1, var2, var3}, null, var4) {
         final Flow[] $flows;
         final Function6 $transform$inlined;
         private Object L$0;
         int label;

         {
            super(2, var2x);
            this.$flows = var1;
            this.$transform$inlined = var3x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$flows, var2, this.$transform$inlined);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(FlowCollector<? super R> var1, Continuation<? super Unit> var2x) {
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
               val var5: FlowCollector = this.L$0 as FlowCollector;
               var1 = this.$flows;
               val var4: Function0 = FlowKt__ZipKt.access$nullArrayFactory();
               val var6: Function3 = (new Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object>(null, this.$transform$inlined) {
                  final Function6 $transform$inlined;
                  private Object L$0;
                  Object L$1;
                  int label;

                  {
                     super(3, var1);
                     this.$transform$inlined = var2x;
                  }

                  public final Object invoke(FlowCollector<? super R> var1, Object[] var2x, Continuation<? super Unit> var3x) {
                     val var4x: Function3 = new <anonymous constructor>(var3x, this.$transform$inlined);
                     var4x.L$0 = var1;
                     var4x.L$1 = var2x;
                     return var4x.invokeSuspend(Unit.INSTANCE);
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
                        var1 = this.L$0 as FlowCollector;
                        var var8: Array<Any> = this.L$1 as Array<Any>;
                        val var4x: Continuation = this;
                        val var10: Function6 = this.$transform$inlined;
                        val var5: Any = var8[0];
                        val var7: Any = var8[1];
                        val var6: Any = var8[2];
                        var8 = (Object[])var8[3];
                        this.label = 1;
                        if (var10.invoke(var1, var5, var7, var6, var8, this) === var3x) {
                           return var3x;
                        }
                     }

                     return Unit.INSTANCE;
                  }
               }) as Function3;
               val var7: Continuation = this;
               this.label = 1;
               if (CombineKt.combineInternal(var5, var1, var4, var6, var7) === var3x) {
                  return var3x;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @JvmStatic
   public fun <T1, T2, T3, T4, T5, R> combineTransform(
      flow: Flow<T1>,
      flow2: Flow<T2>,
      flow3: Flow<T3>,
      flow4: Flow<T4>,
      flow5: Flow<T5>,
      transform: (FlowCollector<R>, T1, T2, T3, T4, T5, Continuation<Unit>) -> Any?
   ): Flow<R> {
      return FlowKt.flow((new Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object>(new Flow[]{var0, var1, var2, var3, var4}, null, var5) {
         final Flow[] $flows;
         final Function7 $transform$inlined;
         private Object L$0;
         int label;

         {
            super(2, var2x);
            this.$flows = var1;
            this.$transform$inlined = var3x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$flows, var2, this.$transform$inlined);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(FlowCollector<? super R> var1, Continuation<? super Unit> var2x) {
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
               val var4: FlowCollector = this.L$0 as FlowCollector;
               val var5: Array<Flow> = this.$flows;
               val var7: Function0 = FlowKt__ZipKt.access$nullArrayFactory();
               val var6: Function3 = (new Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object>(null, this.$transform$inlined) {
                  final Function7 $transform$inlined;
                  private Object L$0;
                  Object L$1;
                  int label;

                  {
                     super(3, var1);
                     this.$transform$inlined = var2x;
                  }

                  public final Object invoke(FlowCollector<? super R> var1, Object[] var2x, Continuation<? super Unit> var3x) {
                     val var4x: Function3 = new <anonymous constructor>(var3x, this.$transform$inlined);
                     var4x.L$0 = var1;
                     var4x.L$1 = var2x;
                     return var4x.invokeSuspend(Unit.INSTANCE);
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
                        var1 = this.L$0 as FlowCollector;
                        var var6: Array<Any> = this.L$1 as Array<Any>;
                        var var4x: Continuation = this;
                        val var5: Function7 = this.$transform$inlined;
                        val var7: Any = var6[0];
                        var4x = (Continuation)var6[1];
                        val var8: Any = var6[2];
                        val var9: Any = var6[3];
                        var6 = (Object[])var6[4];
                        this.label = 1;
                        if (var5.invoke(var1, var7, var4x, var8, var9, var6, this) === var3x) {
                           return var3x;
                        }
                     }

                     return Unit.INSTANCE;
                  }
               }) as Function3;
               var1 = this;
               this.label = 1;
               if (CombineKt.combineInternal(var4, var5, var7, var6, var1) === var3x) {
                  return var3x;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @JvmStatic
   public fun <T1, T2, R> Flow<T1>.combine(flow: Flow<T2>, transform: (T1, T2, Continuation<R>) -> Any?): Flow<R> {
      return new Flow<R>(var0, var1, var2) {
         final Flow $flow$inlined;
         final Flow $this_combine$inlined;
         final Function3 $transform$inlined;

         {
            this.$this_combine$inlined = var1;
            this.$flow$inlined = var2;
            this.$transform$inlined = var3;
         }

         @Override
         public Object collect(FlowCollector<? super R> var1, Continuation<? super Unit> var2) {
            val var7: Any = CombineKt.combineInternal(
               var1,
               new Flow[]{this.$this_combine$inlined, this.$flow$inlined},
               FlowKt__ZipKt.access$nullArrayFactory(),
               (new Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object>(this.$transform$inlined, null) {
                  final Function3<T1, T2, Continuation<? super R>, Object> $transform;
                  private Object L$0;
                  Object L$1;
                  int label;

                  {
                     super(3, var2x);
                     this.$transform = var1;
                  }

                  public final Object invoke(FlowCollector<? super R> var1, Object[] var2x, Continuation<? super Unit> var3) {
                     val var4: Function3 = new <anonymous constructor>(this.$transform, var3);
                     var4.L$0 = var1;
                     var4.L$1 = var2x;
                     return var4.invokeSuspend(Unit.INSTANCE);
                  }

                  @Override
                  public final Object invokeSuspend(Object var1) {
                     val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     val var3x: FlowCollector;
                     if (this.label != 0) {
                        if (this.label != 1) {
                           if (this.label != 2) {
                              throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                           }

                           ResultKt.throwOnFailure(var1);
                           return Unit.INSTANCE;
                        }

                        var3x = this.L$0 as FlowCollector;
                        ResultKt.throwOnFailure(var1);
                     } else {
                        ResultKt.throwOnFailure(var1);
                        var3x = this.L$0 as FlowCollector;
                        var var6: Array<Any> = this.L$1 as Array<Any>;
                        var1 = this.$transform;
                        var var4x: Any = var6[0];
                        var6 = (Object[])var6[1];
                        this.L$0 = var3x;
                        this.label = 1;
                        var4x = var1.invoke(var4x, var6, this);
                        var1 = (Function3)var4x;
                        if (var4x === var5) {
                           return var5;
                        }
                     }

                     val var9: Continuation = this;
                     this.L$0 = null;
                     this.label = 2;
                     return if (var3x.emit(var1, var9) === var5) var5 else Unit.INSTANCE;
                  }
               }) as Function3,
               var2
            );
            return if (var7 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var7 else Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T1, T2, R> Flow<T1>.combineTransform(flow: Flow<T2>, transform: (FlowCollector<R>, T1, T2, Continuation<Unit>) -> Any?): Flow<R> {
      return FlowKt.flow((new Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object>(new Flow[]{var0, var1}, null, var2) {
         final Flow[] $flows;
         final Function4 $transform$inlined;
         private Object L$0;
         int label;

         {
            super(2, var2x);
            this.$flows = var1;
            this.$transform$inlined = var3x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$flows, var2, this.$transform$inlined);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(FlowCollector<? super R> var1, Continuation<? super Unit> var2x) {
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
               val var4: FlowCollector = this.L$0 as FlowCollector;
               val var6: Array<Flow> = this.$flows;
               val var7: Function0 = FlowKt__ZipKt.access$nullArrayFactory();
               var1 = (new Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object>(null, this.$transform$inlined) {
                  final Function4 $transform$inlined;
                  private Object L$0;
                  Object L$1;
                  int label;

                  {
                     super(3, var1);
                     this.$transform$inlined = var2x;
                  }

                  public final Object invoke(FlowCollector<? super R> var1, Object[] var2x, Continuation<? super Unit> var3x) {
                     val var4x: Function3 = new <anonymous constructor>(var3x, this.$transform$inlined);
                     var4x.L$0 = var1;
                     var4x.L$1 = var2x;
                     return var4x.invokeSuspend(Unit.INSTANCE);
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
                        var1 = this.L$0 as FlowCollector;
                        var var6: Array<Any> = this.L$1 as Array<Any>;
                        val var4x: Continuation = this;
                        val var8: Function4 = this.$transform$inlined;
                        val var5: Any = var6[0];
                        var6 = (Object[])var6[1];
                        this.label = 1;
                        if (var8.invoke(var1, var5, var6, this) === var3x) {
                           return var3x;
                        }
                     }

                     return Unit.INSTANCE;
                  }
               }) as Function3;
               val var5: Continuation = this;
               this.label = 1;
               if (CombineKt.combineInternal(var4, var6, var7, var1, var5) === var3x) {
                  return var3x;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2);
   }

   @JvmStatic
   private fun <T> nullArrayFactory(): () -> Array<T>? {
      return <unrepresentable>.INSTANCE;
   }

   @JvmStatic
   public fun <T1, T2, R> Flow<T1>.zip(other: Flow<T2>, transform: (T1, T2, Continuation<R>) -> Any?): Flow<R> {
      return CombineKt.zipImpl(var0, var1, var2);
   }
}
