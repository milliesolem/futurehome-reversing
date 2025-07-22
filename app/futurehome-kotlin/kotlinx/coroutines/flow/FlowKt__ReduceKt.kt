package kotlinx.coroutines.flow

import java.util.NoSuchElementException
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.Ref
import kotlinx.coroutines.flow.internal.AbortFlowException
import kotlinx.coroutines.flow.internal.FlowExceptions_commonKt
import kotlinx.coroutines.flow.internal.NullSurrogateKt

@JvmSynthetic
internal class FlowKt__ReduceKt {
   @JvmStatic
   public suspend fun <T> Flow<T>.first(): T {
      var var3: Any;
      label51: {
         if (var1 is <unrepresentable>) {
            var3 = var1 as <unrepresentable>;
            if (((var1 as <unrepresentable>).label and Integer.MIN_VALUE) != 0) {
               ((<unrepresentable>)var3).label += Integer.MIN_VALUE;
               break label51;
            }
         }

         var3 = new ContinuationImpl(var1) {
            Object L$0;
            Object L$1;
            int label;
            Object result;

            {
               super(var1);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return FlowKt.first(null, this);
            }
         };
      }

      label45: {
         var var11: Ref.ObjectRef = (Ref.ObjectRef)((<unrepresentable>)var3).result;
         val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
         if (((<unrepresentable>)var3).label != 0) {
            if (((<unrepresentable>)var3).label != 1) {
               throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            val var4: <unrepresentable> = ((<unrepresentable>)var3).L$1 as <unrepresentable>;
            var9 = ((<unrepresentable>)var3).L$0 as Ref.ObjectRef;

            try {
               ResultKt.throwOnFailure(var11);
               break label45;
            } catch (var7: AbortFlowException) {
               var11 = var7;
               var3 = var4;
            }
         } else {
            label56: {
               ResultKt.throwOnFailure(var11);
               var11 = new Ref.ObjectRef();
               var11.element = (T)NullSurrogateKt.NULL;
               val var15: FlowCollector = new FlowCollector<T>(var11) {
                  final Ref.ObjectRef $result$inlined;

                  {
                     this.$result$inlined = var1;
                  }

                  @Override
                  public Object emit(T var1, Continuation<? super Unit> var2) {
                     this.$result$inlined.element = (T)var1;
                     throw new AbortFlowException(this as FlowCollector<?>);
                  }
               };

               try {
                  val var6: FlowCollector = var15;
                  ((<unrepresentable>)var3).L$0 = var11;
                  ((<unrepresentable>)var3).L$1 = var15;
                  ((<unrepresentable>)var3).label = 1;
                  var10 = var0.collect(var6, (Continuation<? super Unit>)var3);
               } catch (var8: AbortFlowException) {
                  var9 = var11;
                  var11 = var8;
                  var3 = var15;
                  break label56;
               }

               if (var10 === var5) {
                  return var5;
               }

               var9 = var11;
               break label45;
            }
         }

         FlowExceptions_commonKt.checkOwnership((AbortFlowException)var11, var3 as FlowCollector<?>);
      }

      if (var9.element != NullSurrogateKt.NULL) {
         return var9.element;
      } else {
         throw new NoSuchElementException("Expected at least one element");
      }
   }

   @JvmStatic
   public suspend fun <T> Flow<T>.first(predicate: (T, Continuation<Boolean>) -> Any?): T {
      var var4: Any;
      label51: {
         if (var2 is <unrepresentable>) {
            var4 = var2 as <unrepresentable>;
            if (((var2 as <unrepresentable>).label and Integer.MIN_VALUE) != 0) {
               ((<unrepresentable>)var4).label += Integer.MIN_VALUE;
               break label51;
            }
         }

         var4 = new ContinuationImpl(var2) {
            Object L$0;
            Object L$1;
            Object L$2;
            int label;
            Object result;

            {
               super(var1);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return FlowKt.first(null, null, this);
            }
         };
      }

      label45: {
         var var14: Ref.ObjectRef = (Ref.ObjectRef)((<unrepresentable>)var4).result;
         val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
         if (((<unrepresentable>)var4).label != 0) {
            if (((<unrepresentable>)var4).label != 1) {
               throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            val var5: <unrepresentable> = ((<unrepresentable>)var4).L$2 as <unrepresentable>;
            var12 = ((<unrepresentable>)var4).L$1 as Ref.ObjectRef;
            var10 = ((<unrepresentable>)var4).L$0 as Function2;

            try {
               ResultKt.throwOnFailure(var14);
               break label45;
            } catch (var8: AbortFlowException) {
               var14 = var8;
               var4 = var5;
            }
         } else {
            label56: {
               ResultKt.throwOnFailure(var14);
               var14 = new Ref.ObjectRef();
               var14.element = (T)NullSurrogateKt.NULL;
               val var18: FlowCollector = new FlowCollector<T>(var1, var14) {
                  final Function2 $predicate$inlined;
                  final Ref.ObjectRef $result$inlined;

                  {
                     this.$predicate$inlined = var1;
                     this.$result$inlined = var2;
                  }

                  @Override
                  public Object emit(T var1, Continuation<? super Unit> var2) {
                     label29: {
                        if (var2 is <unrepresentable>) {
                           val var4: <unrepresentable> = var2 as <unrepresentable>;
                           if ((var2.label and Integer.MIN_VALUE) != 0) {
                              var4.label += Integer.MIN_VALUE;
                              var2 = var4;
                              break label29;
                           }
                        }

                        var2 = new ContinuationImpl(this, var2) {
                           Object L$0;
                           Object L$1;
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

                     var var8: Function2 = (Function2)var2.result;
                     val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     val var7: <unrepresentable>;
                     if (var2.label != 0) {
                        if (var2.label != 1) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        var1 = var2.L$1;
                        var7 = var2.L$0 as <unrepresentable>;
                        ResultKt.throwOnFailure(var8);
                     } else {
                        ResultKt.throwOnFailure(var8);
                        var8 = var2;
                        var8 = this.$predicate$inlined;
                        var2.L$0 = this;
                        var2.L$1 = var1;
                        var2.label = 1;
                        var8 = (Function2)var8.invoke(var1, var2);
                        if (var8 === var5) {
                           return var5;
                        }

                        var7 = this;
                     }

                     if (!var8 as java.lang.Boolean) {
                        return Unit.INSTANCE;
                     } else {
                        var7.$result$inlined.element = (T)var1;
                        throw new AbortFlowException(var7);
                     }
                  }
               };

               try {
                  val var7: FlowCollector = var18;
                  ((<unrepresentable>)var4).L$0 = var1;
                  ((<unrepresentable>)var4).L$1 = var14;
                  ((<unrepresentable>)var4).L$2 = var18;
                  ((<unrepresentable>)var4).label = 1;
                  var11 = var0.collect(var7, (Continuation<? super Unit>)var4);
               } catch (var9: AbortFlowException) {
                  var10 = var1;
                  var12 = var14;
                  var14 = var9;
                  var4 = var18;
                  break label56;
               }

               if (var11 === var6) {
                  return var6;
               }

               var10 = var1;
               var12 = var14;
               break label45;
            }
         }

         FlowExceptions_commonKt.checkOwnership((AbortFlowException)var14, var4 as FlowCollector<?>);
      }

      if (var12.element != NullSurrogateKt.NULL) {
         return var12.element;
      } else {
         val var13: StringBuilder = new StringBuilder("Expected at least one element matching the predicate ");
         var13.append(var10);
         throw new NoSuchElementException(var13.toString());
      }
   }

   @JvmStatic
   public suspend fun <T> Flow<T>.firstOrNull(): T? {
      var var3: Any;
      label47: {
         if (var1 is <unrepresentable>) {
            var3 = var1 as <unrepresentable>;
            if (((var1 as <unrepresentable>).label and Integer.MIN_VALUE) != 0) {
               ((<unrepresentable>)var3).label += Integer.MIN_VALUE;
               break label47;
            }
         }

         var3 = new ContinuationImpl(var1) {
            Object L$0;
            Object L$1;
            int label;
            Object result;

            {
               super(var1);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return FlowKt.firstOrNull(null, this);
            }
         };
      }

      var var11: Any = ((<unrepresentable>)var3).result;
      val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      val var9: Ref.ObjectRef;
      if (((<unrepresentable>)var3).label != 0) {
         if (((<unrepresentable>)var3).label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         val var4: <unrepresentable> = ((<unrepresentable>)var3).L$1 as <unrepresentable>;
         var9 = ((<unrepresentable>)var3).L$0 as Ref.ObjectRef;

         try {
            ResultKt.throwOnFailure(var11);
            return var9.element;
         } catch (var7: AbortFlowException) {
            var11 = var7;
            var3 = var4;
         }
      } else {
         label59: {
            ResultKt.throwOnFailure(var11);
            var11 = new Ref.ObjectRef();
            val var15: FlowCollector = new FlowCollector<T>((Ref.ObjectRef)var11) {
               final Ref.ObjectRef $result$inlined;

               {
                  this.$result$inlined = var1;
               }

               @Override
               public Object emit(T var1, Continuation<? super Unit> var2) {
                  this.$result$inlined.element = (T)var1;
                  throw new AbortFlowException(this as FlowCollector<?>);
               }
            };

            try {
               val var6: FlowCollector = var15;
               ((<unrepresentable>)var3).L$0 = var11;
               ((<unrepresentable>)var3).L$1 = var15;
               ((<unrepresentable>)var3).label = 1;
               var10 = var0.collect(var6, (Continuation<? super Unit>)var3);
            } catch (var8: AbortFlowException) {
               var9 = (Ref.ObjectRef)var11;
               var11 = var8;
               var3 = var15;
               break label59;
            }

            if (var10 === var5) {
               return var5;
            }

            return ((Ref.ObjectRef)var11).element;
         }
      }

      FlowExceptions_commonKt.checkOwnership((AbortFlowException)var11, var3 as FlowCollector<?>);
      return var9.element;
   }

   @JvmStatic
   public suspend fun <T> Flow<T>.firstOrNull(predicate: (T, Continuation<Boolean>) -> Any?): T? {
      var var4: Any;
      label47: {
         if (var2 is <unrepresentable>) {
            var4 = var2 as <unrepresentable>;
            if (((var2 as <unrepresentable>).label and Integer.MIN_VALUE) != 0) {
               ((<unrepresentable>)var4).label += Integer.MIN_VALUE;
               break label47;
            }
         }

         var4 = new ContinuationImpl(var2) {
            Object L$0;
            Object L$1;
            int label;
            Object result;

            {
               super(var1);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return FlowKt.firstOrNull(null, null, this);
            }
         };
      }

      var var12: Any = ((<unrepresentable>)var4).result;
      val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      val var9: Ref.ObjectRef;
      val var11: Any;
      if (((<unrepresentable>)var4).label != 0) {
         if (((<unrepresentable>)var4).label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var11 = ((<unrepresentable>)var4).L$1 as <unrepresentable>;
         var9 = ((<unrepresentable>)var4).L$0 as Ref.ObjectRef;

         try {
            ResultKt.throwOnFailure(var12);
            return var9.element;
         } catch (var7: AbortFlowException) {
            var12 = var7;
         }
      } else {
         label59: {
            ResultKt.throwOnFailure(var12);
            var12 = new Ref.ObjectRef();
            var11 = new FlowCollector<T>(var1, (Ref.ObjectRef)var12) {
               final Function2 $predicate$inlined;
               final Ref.ObjectRef $result$inlined;

               {
                  this.$predicate$inlined = var1;
                  this.$result$inlined = var2;
               }

               @Override
               public Object emit(T var1, Continuation<? super Unit> var2) {
                  label29: {
                     if (var2 is <unrepresentable>) {
                        val var4: <unrepresentable> = var2 as <unrepresentable>;
                        if ((var2.label and Integer.MIN_VALUE) != 0) {
                           var4.label += Integer.MIN_VALUE;
                           var2 = var4;
                           break label29;
                        }
                     }

                     var2 = new ContinuationImpl(this, var2) {
                        Object L$0;
                        Object L$1;
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

                  var var5: Function2 = (Function2)var2.result;
                  var var9: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  val var8: Any;
                  if (var2.label != 0) {
                     if (var2.label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     var9 = var2.L$1;
                     var1 = var2.L$0 as <unrepresentable>;
                     ResultKt.throwOnFailure(var5);
                     var8 = var5;
                  } else {
                     ResultKt.throwOnFailure(var5);
                     var5 = var2;
                     var5 = this.$predicate$inlined;
                     var2.L$0 = this;
                     var2.L$1 = var1;
                     var2.label = 1;
                     var8 = var5.invoke(var1, var2);
                     if (var8 === var9) {
                        return var9;
                     }

                     var9 = var1;
                     var1 = this;
                  }

                  if (!var8 as java.lang.Boolean) {
                     return Unit.INSTANCE;
                  } else {
                     var1.$result$inlined.element = (T)var9;
                     throw new AbortFlowException(var1);
                  }
               }
            };

            try {
               val var6: FlowCollector = var11 as FlowCollector;
               ((<unrepresentable>)var4).L$0 = var12;
               ((<unrepresentable>)var4).L$1 = var11;
               ((<unrepresentable>)var4).label = 1;
               var10 = var0.collect(var6, (Continuation<? super Unit>)var4);
            } catch (var8: AbortFlowException) {
               var9 = (Ref.ObjectRef)var12;
               var12 = var8;
               break label59;
            }

            if (var10 === var5) {
               return var5;
            }

            return ((Ref.ObjectRef)var12).element;
         }
      }

      FlowExceptions_commonKt.checkOwnership((AbortFlowException)var12, var11 as FlowCollector<?>);
      return var9.element;
   }

   @JvmStatic
   public suspend inline fun <T, R> Flow<T>.fold(initial: R, crossinline operation: (R, T, Continuation<R>) -> Any?): R {
      label25: {
         if (var3 is <unrepresentable>) {
            val var5: <unrepresentable> = var3 as <unrepresentable>;
            if ((var3.label and Integer.MIN_VALUE) != 0) {
               var5.label += Integer.MIN_VALUE;
               var3 = var5;
               break label25;
            }
         }

         var3 = new ContinuationImpl(var3) {
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
               return FlowKt__ReduceKt.fold(null, null, null, this as Continuation<? super Object>);
            }
         };
      }

      var var10: Ref.ObjectRef = (Ref.ObjectRef)var3.result;
      val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      val var7: Ref.ObjectRef;
      if (var3.label != 0) {
         if (var3.label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var7 = var3.L$0 as Ref.ObjectRef;
         ResultKt.throwOnFailure(var10);
      } else {
         ResultKt.throwOnFailure(var10);
         var10 = new Ref.ObjectRef();
         var10.element = (T)var1;
         var1 = new FlowCollector(var10, var2) {
            final Ref.ObjectRef<R> $accumulator;
            final Function3<R, T, Continuation<? super R>, Object> $operation;

            {
               this.$accumulator = var1;
               this.$operation = var2;
            }

            @Override
            public final Object emit(T var1, Continuation<? super Unit> var2) {
               label25: {
                  if (var2 is <unrepresentable>) {
                     val var4: <unrepresentable> = var2 as <unrepresentable>;
                     if ((var2.label and Integer.MIN_VALUE) != 0) {
                        var4.label += Integer.MIN_VALUE;
                        var2 = var4;
                        break label25;
                     }
                  }

                  var2 = new ContinuationImpl(this, var2) {
                     Object L$0;
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

               var var11: Any = var2.result;
               val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               val var10: Ref.ObjectRef;
               if (var2.label != 0) {
                  if (var2.label != 1) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  var10 = var2.L$0 as Ref.ObjectRef;
                  ResultKt.throwOnFailure(var11);
                  var1 = var11;
               } else {
                  ResultKt.throwOnFailure(var11);
                  var11 = this.$accumulator;
                  val var6: Function3 = this.$operation;
                  val var7: Any = this.$accumulator.element;
                  var2.L$0 = this.$accumulator;
                  var2.label = 1;
                  var1 = var6.invoke(var7, var1, var2);
                  if (var1 === var5) {
                     return var5;
                  }

                  var10 = (Ref.ObjectRef)var11;
               }

               var10.element = (T)var1;
               return Unit.INSTANCE;
            }

            public final Object emit$$forInline(T var1, Continuation<? super Unit> var2) {
               new ContinuationImpl(this, var2) {
                  Object L$0;
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
                     return this.this$0.emit(null, this as Continuation<? super Unit>);
                  }
               };
               this.$accumulator.element = (R)this.$operation.invoke(this.$accumulator.element, (T)var1, var2);
               return Unit.INSTANCE;
            }
         };
         var3.L$0 = var10;
         var3.label = 1;
         if (var0.collect(var1, var3) === var6) {
            return var6;
         }

         var7 = var10;
      }

      return var7.element;
   }

   @JvmStatic
   fun <T, R> `fold$$forInline`(var0: Flow<? extends T>, var1: R, var2: (R?, T?, Continuation<? super R>?) -> Any, var3: Continuation<? super R>): Any {
      val var4: Ref.ObjectRef = new Ref.ObjectRef();
      var4.element = (T)var1;
      var0.collect(new FlowCollector(var4, var2) {
         final Ref.ObjectRef<R> $accumulator;
         final Function3<R, T, Continuation<? super R>, Object> $operation;

         {
            this.$accumulator = var1;
            this.$operation = var2;
         }

         @Override
         public final Object emit(T var1, Continuation<? super Unit> var2) {
            label25: {
               if (var2 is <unrepresentable>) {
                  val var4: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var4.label += Integer.MIN_VALUE;
                     var2 = var4;
                     break label25;
                  }
               }

               var2 = new ContinuationImpl(this, var2) {
                  Object L$0;
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
                     return this.this$0.emit(null, this as Continuation<? super Unit>);
                  }
               };
            }

            var var11: Any = var2.result;
            val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var10: Ref.ObjectRef;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var10 = var2.L$0 as Ref.ObjectRef;
               ResultKt.throwOnFailure(var11);
               var1 = var11;
            } else {
               ResultKt.throwOnFailure(var11);
               var11 = this.$accumulator;
               val var6: Function3 = this.$operation;
               val var7: Any = this.$accumulator.element;
               var2.L$0 = this.$accumulator;
               var2.label = 1;
               var1 = var6.invoke(var7, var1, var2);
               if (var1 === var5) {
                  return var5;
               }

               var10 = (Ref.ObjectRef)var11;
            }

            var10.element = (T)var1;
            return Unit.INSTANCE;
         }

         public final Object emit$$forInline(T var1, Continuation<? super Unit> var2) {
            new ContinuationImpl(this, var2) {
               Object L$0;
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
                  return this.this$0.emit(null, this as Continuation<? super Unit>);
               }
            };
            this.$accumulator.element = (R)this.$operation.invoke(this.$accumulator.element, (T)var1, var2);
            return Unit.INSTANCE;
         }
      }, var3);
      return var4.element;
   }

   @JvmStatic
   public suspend fun <T> Flow<T>.last(): T {
      label29: {
         if (var1 is <unrepresentable>) {
            val var3: <unrepresentable> = var1 as <unrepresentable>;
            if ((var1.label and Integer.MIN_VALUE) != 0) {
               var3.label += Integer.MIN_VALUE;
               var1 = var3;
               break label29;
            }
         }

         var1 = new ContinuationImpl(var1) {
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
               return FlowKt.last(null, this);
            }
         };
      }

      var var8: Ref.ObjectRef = (Ref.ObjectRef)var1.result;
      val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      val var6: Ref.ObjectRef;
      if (var1.label != 0) {
         if (var1.label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var6 = var1.L$0 as Ref.ObjectRef;
         ResultKt.throwOnFailure(var8);
      } else {
         ResultKt.throwOnFailure(var8);
         var8 = new Ref.ObjectRef();
         var8.element = (T)NullSurrogateKt.NULL;
         val var5: FlowCollector = new FlowCollector(var8) {
            final Ref.ObjectRef<Object> $result;

            {
               this.$result = var1;
            }

            @Override
            public final Object emit(T var1, Continuation<? super Unit> var2) {
               this.$result.element = var1;
               return Unit.INSTANCE;
            }
         };
         var1.L$0 = var8;
         var1.label = 1;
         if (var0.collect(var5, var1) === var4) {
            return var4;
         }

         var6 = var8;
      }

      if (var6.element != NullSurrogateKt.NULL) {
         return var6.element;
      } else {
         throw new NoSuchElementException("Expected at least one element");
      }
   }

   @JvmStatic
   public suspend fun <T> Flow<T>.lastOrNull(): T? {
      label25: {
         if (var1 is <unrepresentable>) {
            val var3: <unrepresentable> = var1 as <unrepresentable>;
            if ((var1.label and Integer.MIN_VALUE) != 0) {
               var3.label += Integer.MIN_VALUE;
               var1 = var3;
               break label25;
            }
         }

         var1 = new ContinuationImpl(var1) {
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
               return FlowKt.lastOrNull(null, this);
            }
         };
      }

      var var8: Any = var1.result;
      val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      val var6: Ref.ObjectRef;
      if (var1.label != 0) {
         if (var1.label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var6 = var1.L$0 as Ref.ObjectRef;
         ResultKt.throwOnFailure(var8);
      } else {
         ResultKt.throwOnFailure(var8);
         var8 = new Ref.ObjectRef();
         val var5: FlowCollector = new FlowCollector((Ref.ObjectRef)var8) {
            final Ref.ObjectRef<T> $result;

            {
               this.$result = var1;
            }

            @Override
            public final Object emit(T var1, Continuation<? super Unit> var2) {
               this.$result.element = (T)var1;
               return Unit.INSTANCE;
            }
         };
         var1.L$0 = var8;
         var1.label = 1;
         if (var0.collect(var5, var1) === var4) {
            return var4;
         }

         var6 = (Ref.ObjectRef)var8;
      }

      return var6.element;
   }

   @JvmStatic
   public suspend fun <S, T : S> Flow<T>.reduce(operation: (S, T, Continuation<S>) -> Any?): S {
      label29: {
         if (var2 is <unrepresentable>) {
            val var4: <unrepresentable> = var2 as <unrepresentable>;
            if ((var2.label and Integer.MIN_VALUE) != 0) {
               var4.label += Integer.MIN_VALUE;
               var2 = var4;
               break label29;
            }
         }

         var2 = new ContinuationImpl(var2) {
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
               return FlowKt.reduce(null, null, this as Continuation<? super Object>);
            }
         };
      }

      var var9: Ref.ObjectRef = (Ref.ObjectRef)var2.result;
      val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      val var6: Ref.ObjectRef;
      if (var2.label != 0) {
         if (var2.label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var6 = var2.L$0 as Ref.ObjectRef;
         ResultKt.throwOnFailure(var9);
      } else {
         ResultKt.throwOnFailure(var9);
         var9 = new Ref.ObjectRef();
         var9.element = (T)NullSurrogateKt.NULL;
         val var7: FlowCollector = new FlowCollector(var9, var1) {
            final Ref.ObjectRef<Object> $accumulator;
            final Function3<S, T, Continuation<? super S>, Object> $operation;

            {
               this.$accumulator = var1;
               this.$operation = var2;
            }

            @Override
            public final Object emit(T var1, Continuation<? super Unit> var2) {
               var var4: Any;
               label29: {
                  if (var2 is <unrepresentable>) {
                     var4 = var2 as <unrepresentable>;
                     if (((var2 as <unrepresentable>).label and Integer.MIN_VALUE) != 0) {
                        ((<unrepresentable>)var4).label += Integer.MIN_VALUE;
                        break label29;
                     }
                  }

                  var4 = new ContinuationImpl(this, var2) {
                     Object L$0;
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

               var var6: Function3;
               var var10: Ref.ObjectRef;
               label24: {
                  var var5: Any = ((<unrepresentable>)var4).result;
                  val var7: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  val var9: Ref.ObjectRef;
                  if (((<unrepresentable>)var4).label != 0) {
                     if (((<unrepresentable>)var4).label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     var9 = ((<unrepresentable>)var4).L$0 as Ref.ObjectRef;
                     ResultKt.throwOnFailure(var5);
                     var1 = var5;
                  } else {
                     ResultKt.throwOnFailure(var5);
                     var9 = this.$accumulator;
                     var6 = (Function3)var1;
                     var10 = this.$accumulator;
                     if (this.$accumulator.element === NullSurrogateKt.NULL) {
                        break label24;
                     }

                     var6 = this.$operation;
                     var5 = this.$accumulator.element;
                     ((<unrepresentable>)var4).L$0 = this.$accumulator;
                     ((<unrepresentable>)var4).label = 1;
                     var1 = var6.invoke(var5, var1, var4);
                     if (var1 === var7) {
                        return var7;
                     }
                  }

                  var10 = var9;
                  var6 = (Function3)var1;
               }

               var10.element = (T)var6;
               return Unit.INSTANCE;
            }
         };
         var2.L$0 = var9;
         var2.label = 1;
         if (var0.collect(var7, var2) === var5) {
            return var5;
         }

         var6 = var9;
      }

      if (var6.element != NullSurrogateKt.NULL) {
         return var6.element;
      } else {
         throw new NoSuchElementException("Empty flow can't be reduced");
      }
   }

   @JvmStatic
   public suspend fun <T> Flow<T>.single(): T {
      label29: {
         if (var1 is <unrepresentable>) {
            val var3: <unrepresentable> = var1 as <unrepresentable>;
            if ((var1.label and Integer.MIN_VALUE) != 0) {
               var3.label += Integer.MIN_VALUE;
               var1 = var3;
               break label29;
            }
         }

         var1 = new ContinuationImpl(var1) {
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
               return FlowKt.single(null, this);
            }
         };
      }

      var var8: Ref.ObjectRef = (Ref.ObjectRef)var1.result;
      val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      val var6: Ref.ObjectRef;
      if (var1.label != 0) {
         if (var1.label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var6 = var1.L$0 as Ref.ObjectRef;
         ResultKt.throwOnFailure(var8);
      } else {
         ResultKt.throwOnFailure(var8);
         var8 = new Ref.ObjectRef();
         var8.element = (T)NullSurrogateKt.NULL;
         val var5: FlowCollector = new FlowCollector(var8) {
            final Ref.ObjectRef<Object> $result;

            {
               this.$result = var1;
            }

            @Override
            public final Object emit(T var1, Continuation<? super Unit> var2) {
               if (this.$result.element === NullSurrogateKt.NULL) {
                  this.$result.element = var1;
                  return Unit.INSTANCE;
               } else {
                  throw new IllegalArgumentException("Flow has more than one element".toString());
               }
            }
         };
         var1.L$0 = var8;
         var1.label = 1;
         if (var0.collect(var5, var1) === var4) {
            return var4;
         }

         var6 = var8;
      }

      if (var6.element != NullSurrogateKt.NULL) {
         return var6.element;
      } else {
         throw new NoSuchElementException("Flow is empty");
      }
   }

   @JvmStatic
   public suspend fun <T> Flow<T>.singleOrNull(): T? {
      var var3: Any;
      label52: {
         if (var1 is <unrepresentable>) {
            var3 = var1 as <unrepresentable>;
            if (((var1 as <unrepresentable>).label and Integer.MIN_VALUE) != 0) {
               ((<unrepresentable>)var3).label += Integer.MIN_VALUE;
               break label52;
            }
         }

         var3 = new ContinuationImpl(var1) {
            Object L$0;
            Object L$1;
            int label;
            Object result;

            {
               super(var1);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return FlowKt.singleOrNull(null, this);
            }
         };
      }

      label46: {
         var var12: Ref.ObjectRef = (Ref.ObjectRef)((<unrepresentable>)var3).result;
         val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
         if (((<unrepresentable>)var3).label != 0) {
            if (((<unrepresentable>)var3).label != 1) {
               throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            val var4: <unrepresentable> = ((<unrepresentable>)var3).L$1 as <unrepresentable>;
            var9 = ((<unrepresentable>)var3).L$0 as Ref.ObjectRef;

            try {
               ResultKt.throwOnFailure(var12);
               break label46;
            } catch (var7: AbortFlowException) {
               var12 = var7;
               var3 = var4;
            }
         } else {
            label57: {
               ResultKt.throwOnFailure(var12);
               var12 = new Ref.ObjectRef();
               var12.element = (T)NullSurrogateKt.NULL;
               val var16: FlowCollector = new FlowCollector<T>(var12) {
                  final Ref.ObjectRef $result$inlined;

                  {
                     this.$result$inlined = var1;
                  }

                  @Override
                  public Object emit(T var1, Continuation<? super Unit> var2) {
                     if (this.$result$inlined.element === NullSurrogateKt.NULL) {
                        this.$result$inlined.element = (T)var1;
                        return Unit.INSTANCE;
                     } else {
                        this.$result$inlined.element = (T)NullSurrogateKt.NULL;
                        throw new AbortFlowException(this as FlowCollector<?>);
                     }
                  }
               };

               try {
                  val var6: FlowCollector = var16;
                  ((<unrepresentable>)var3).L$0 = var12;
                  ((<unrepresentable>)var3).L$1 = var16;
                  ((<unrepresentable>)var3).label = 1;
                  var10 = var0.collect(var6, (Continuation<? super Unit>)var3);
               } catch (var8: AbortFlowException) {
                  var9 = var12;
                  var12 = var8;
                  var3 = var16;
                  break label57;
               }

               if (var10 === var5) {
                  return var5;
               }

               var9 = var12;
               break label46;
            }
         }

         FlowExceptions_commonKt.checkOwnership((AbortFlowException)var12, var3 as FlowCollector<?>);
      }

      val var11: Any;
      if (var9.element === NullSurrogateKt.NULL) {
         var11 = null;
      } else {
         var11 = var9.element;
      }

      return var11;
   }
}
