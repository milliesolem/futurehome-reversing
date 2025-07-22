package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlinx.coroutines.flow.internal.ChannelFlowMerge
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge
import kotlinx.coroutines.internal.SystemPropsKt

@JvmSynthetic
internal class FlowKt__MergeKt {
   public final val DEFAULT_CONCURRENCY: Int = SystemPropsKt.systemProp("kotlinx.coroutines.flow.defaultConcurrency", 16, 1, Integer.MAX_VALUE)
   public const val DEFAULT_CONCURRENCY_PROPERTY_NAME: String

   @JvmStatic
   public fun <T, R> Flow<T>.flatMapConcat(transform: (T, Continuation<Flow<R>>) -> Any?): Flow<R> {
      return FlowKt.flattenConcat(new Flow<Flow<? extends R>>(var0, var1) {
         final Flow $this_unsafeTransform$inlined;
         final Function2 $transform$inlined$1;

         {
            this.$this_unsafeTransform$inlined = var1;
            this.$transform$inlined$1 = var2;
         }

         @Override
         public Object collect(FlowCollector var1, Continuation var2) {
            val var3: Any = this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1, this.$transform$inlined$1) {
               final FlowCollector $this_unsafeFlow;
               final Function2 $transform$inlined;

               {
                  this.$this_unsafeFlow = var1;
                  this.$transform$inlined = var2x;
               }

               @Override
               public final Object emit(Object var1, Continuation var2x) {
                  label32: {
                     if (var2x is <unrepresentable>) {
                        val var4: <unrepresentable> = var2x as <unrepresentable>;
                        if ((var2x.label and Integer.MIN_VALUE) != 0) {
                           var4.label += Integer.MIN_VALUE;
                           var2x = var4;
                           break label32;
                        }
                     }

                     var2x = new ContinuationImpl(this, var2x) {
                        Object L$0;
                        int label;
                        Object result;
                        final <unrepresentable> this$0;

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

                  var var5: Function2 = (Function2)var2x.result;
                  val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  val var9: FlowCollector;
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        if (var2x.label != 2) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var5);
                        return Unit.INSTANCE;
                     }

                     var9 = var2x.L$0 as FlowCollector;
                     ResultKt.throwOnFailure(var5);
                     var1 = var5;
                  } else {
                     ResultKt.throwOnFailure(var5);
                     var9 = this.$this_unsafeFlow;
                     var5 = this.$transform$inlined;
                     var2x.L$0 = this.$this_unsafeFlow;
                     var2x.label = 1;
                     var1 = var5.invoke(var1, var2x);
                     if (var1 === var6) {
                        return var6;
                     }
                  }

                  var2x.L$0 = null;
                  var2x.label = 2;
                  return if (var9.emit(var1, var2x) === var6) var6 else Unit.INSTANCE;
               }
            }, var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }
      });
   }

   @JvmStatic
   public inline fun <T, R> Flow<T>.flatMapLatest(crossinline transform: (T, Continuation<Flow<R>>) -> Any?): Flow<R> {
      return FlowKt.transformLatest(var0, (new Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object>(var1, null) {
         final Function2<T, Continuation<? super Flow<? extends R>>, Object> $transform;
         private Object L$0;
         Object L$1;
         int label;

         {
            super(3, var2x);
            this.$transform = var1;
         }

         public final Object invoke(FlowCollector<? super R> var1, T var2, Continuation<? super Unit> var3) {
            val var4: Function3 = new <anonymous constructor>(this.$transform, var3);
            var4.L$0 = var1;
            var4.L$1 = var2;
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
               var var4x: Any = this.L$1;
               var1 = this.$transform;
               this.L$0 = var3x;
               this.label = 1;
               var4x = var1.invoke(var4x, this);
               var1 = (Function2)var4x;
               if (var4x === var5) {
                  return var5;
               }
            }

            val var7: Flow = var1 as Flow;
            val var9: Continuation = this;
            this.L$0 = null;
            this.label = 2;
            return if (FlowKt.emitAll(var3x, var7, var9) === var5) var5 else Unit.INSTANCE;
         }

         public final Object invokeSuspend$$forInline(Object var1) {
            FlowKt.emitAll(this.L$0 as FlowCollector, this.$transform.invoke(this.L$1, this) as Flow, this as Continuation<? super Unit>);
            return Unit.INSTANCE;
         }
      }) as Function3);
   }

   @JvmStatic
   public fun <T, R> Flow<T>.flatMapMerge(concurrency: Int = DEFAULT_CONCURRENCY, transform: (T, Continuation<Flow<R>>) -> Any?): Flow<R> {
      return FlowKt.flattenMerge(new Flow<Flow<? extends R>>(var0, var2) {
         final Flow $this_unsafeTransform$inlined;
         final Function2 $transform$inlined$1;

         {
            this.$this_unsafeTransform$inlined = var1;
            this.$transform$inlined$1 = var2;
         }

         @Override
         public Object collect(FlowCollector var1, Continuation var2) {
            val var3: Any = this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1, this.$transform$inlined$1) {
               final FlowCollector $this_unsafeFlow;
               final Function2 $transform$inlined;

               {
                  this.$this_unsafeFlow = var1;
                  this.$transform$inlined = var2x;
               }

               @Override
               public final Object emit(Object var1, Continuation var2x) {
                  label34: {
                     if (var2x is <unrepresentable>) {
                        val var4: <unrepresentable> = var2x as <unrepresentable>;
                        if ((var2x.label and Integer.MIN_VALUE) != 0) {
                           var4.label += Integer.MIN_VALUE;
                           var2x = var4;
                           break label34;
                        }
                     }

                     var2x = new ContinuationImpl(this, var2x) {
                        Object L$0;
                        int label;
                        Object result;
                        final <unrepresentable> this$0;

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

                  var var5: Function2 = (Function2)var2x.result;
                  val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        if (var2x.label != 2) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var5);
                        return Unit.INSTANCE;
                     }

                     var1 = var2x.L$0 as FlowCollector;
                     ResultKt.throwOnFailure(var5);
                  } else {
                     ResultKt.throwOnFailure(var5);
                     val var10: FlowCollector = this.$this_unsafeFlow;
                     var5 = this.$transform$inlined;
                     var2x.L$0 = this.$this_unsafeFlow;
                     var2x.label = 1;
                     var1 = var5.invoke(var1, var2x);
                     if (var1 === var6) {
                        return var6;
                     }

                     var5 = (Function2)var1;
                     var1 = var10;
                  }

                  var2x.L$0 = null;
                  var2x.label = 2;
                  return if (var1.emit(var5, var2x) === var6) var6 else Unit.INSTANCE;
               }
            }, var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }
      }, var1);
   }

   @JvmStatic
   public fun <T> Flow<Flow<T>>.flattenConcat(): Flow<T> {
      return new Flow<T>(var0) {
         final Flow $this_flattenConcat$inlined;

         {
            this.$this_flattenConcat$inlined = var1;
         }

         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            val var3: Any = this.$this_flattenConcat$inlined.collect(new FlowCollector(var1) {
               final FlowCollector<T> $this_unsafeFlow;

               {
                  this.$this_unsafeFlow = var1;
               }

               public final Object emit(Flow<? extends T> var1, Continuation<? super Unit> var2x) {
                  label23: {
                     if (var2x is <unrepresentable>) {
                        val var4: <unrepresentable> = var2x as <unrepresentable>;
                        if ((var2x.label and Integer.MIN_VALUE) != 0) {
                           var4.label += Integer.MIN_VALUE;
                           var2x = var4;
                           break label23;
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

                  var var5: Any = var2x.result;
                  val var7: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     ResultKt.throwOnFailure(var5);
                  } else {
                     ResultKt.throwOnFailure(var5);
                     var5 = this.$this_unsafeFlow;
                     var2x.label = 1;
                     if (FlowKt.emitAll((FlowCollector<? super T>)var5, var1, var2x) === var7) {
                        return var7;
                     }
                  }

                  return Unit.INSTANCE;
               }
            }, var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T> Flow<Flow<T>>.flattenMerge(concurrency: Int = DEFAULT_CONCURRENCY): Flow<T> {
      if (var1 > 0) {
         if (var1 == 1) {
            var0 = FlowKt.flattenConcat(var0);
         } else {
            var0 = new ChannelFlowMerge(var0, var1, null, 0, null, 28, null);
         }

         return var0;
      } else {
         val var2: StringBuilder = new StringBuilder("Expected positive concurrency level, but had ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun <T, R> Flow<T>.mapLatest(transform: (T, Continuation<R>) -> Any?): Flow<R> {
      return FlowKt.transformLatest(var0, (new Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object>(var1, null) {
         final Function2<T, Continuation<? super R>, Object> $transform;
         private Object L$0;
         Object L$1;
         int label;

         {
            super(3, var2x);
            this.$transform = var1;
         }

         public final Object invoke(FlowCollector<? super R> var1, T var2, Continuation<? super Unit> var3) {
            val var4: Function3 = new <anonymous constructor>(this.$transform, var3);
            var4.L$0 = var1;
            var4.L$1 = var2;
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
               var var4x: Any = this.L$1;
               var1 = this.$transform;
               this.L$0 = var3x;
               this.label = 1;
               var4x = var1.invoke(var4x, this);
               var1 = (Function2)var4x;
               if (var4x === var5) {
                  return var5;
               }
            }

            val var8: Continuation = this;
            this.L$0 = null;
            this.label = 2;
            return if (var3x.emit(var1, var8) === var5) var5 else Unit.INSTANCE;
         }
      }) as Function3);
   }

   @JvmStatic
   public fun <T> Iterable<Flow<T>>.merge(): Flow<T> {
      return new ChannelLimitedFlowMerge(var0, null, 0, null, 14, null);
   }

   @JvmStatic
   public fun <T> merge(vararg flows: Flow<T>): Flow<T> {
      return FlowKt.merge(ArraysKt.asIterable(var0));
   }

   @JvmStatic
   public fun <T, R> Flow<T>.transformLatest(transform: (FlowCollector<R>, T, Continuation<Unit>) -> Any?): Flow<R> {
      return new ChannelFlowTransformLatest(var1, var0, null, 0, null, 28, null);
   }
}
