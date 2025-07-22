package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.Ref
import kotlinx.coroutines.flow.internal.AbortFlowException
import kotlinx.coroutines.flow.internal.FlowExceptions_commonKt

@JvmSynthetic
internal class FlowKt__LimitKt {
   @JvmStatic
   internal suspend inline fun <T> Flow<T>.collectWhile(crossinline predicate: (T, Continuation<Boolean>) -> Any?) {
      label45: {
         if (var2 is <unrepresentable>) {
            val var4: <unrepresentable> = var2 as <unrepresentable>;
            if ((var2.label and Integer.MIN_VALUE) != 0) {
               var4.label += Integer.MIN_VALUE;
               var2 = var4;
               break label45;
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
               return FlowKt__LimitKt.collectWhile(null, null, this);
            }
         };
      }

      var var5: Any = var2.result;
      val var14: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      var var8: Any;
      val var10: AbortFlowException;
      if (var2.label != 0) {
         if (var2.label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var8 = var2.L$0 as <unrepresentable>;

         try {
            ResultKt.throwOnFailure(var5);
            return Unit.INSTANCE;
         } catch (var6: AbortFlowException) {
            var10 = var6;
         }
      } else {
         label57: {
            ResultKt.throwOnFailure(var5);
            val var11: FlowCollector = new FlowCollector<T>(var1) {
               final Function2<T, Continuation<? super java.lang.Boolean>, Object> $predicate;

               {
                  this.$predicate = var1;
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

                  var var9: Function2 = (Function2)var2.result;
                  val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  val var8: <unrepresentable>;
                  if (var2.label != 0) {
                     if (var2.label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     var8 = var2.L$0 as <unrepresentable>;
                     ResultKt.throwOnFailure(var9);
                     var1 = var9;
                  } else {
                     ResultKt.throwOnFailure(var9);
                     var9 = this.$predicate;
                     var2.L$0 = this;
                     var2.label = 1;
                     var1 = var9.invoke(var1, var2);
                     if (var1 === var5) {
                        return var5;
                     }

                     var8 = this;
                  }

                  if (var1 as java.lang.Boolean) {
                     return Unit.INSTANCE;
                  } else {
                     throw new AbortFlowException(var8);
                  }
               }

               public Object emit$$forInline(T var1, Continuation<? super Unit> var2) {
                  new ContinuationImpl(this, var2) {
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
                        return this.this$0.emit(null, this as Continuation<? super Unit>);
                     }
                  };
                  if (this.$predicate.invoke(var1, var2) as java.lang.Boolean) {
                     return Unit.INSTANCE;
                  } else {
                     throw new AbortFlowException(this as FlowCollector<?>);
                  }
               }
            };

            try {
               var5 = var11;
               var2.L$0 = var11;
               var2.label = 1;
               var8 = var0.collect((FlowCollector)var5, var2);
            } catch (var7: AbortFlowException) {
               var10 = var7;
               var8 = var11;
               break label57;
            }

            if (var8 === var14) {
               return var14;
            }

            return Unit.INSTANCE;
         }
      }

      FlowExceptions_commonKt.checkOwnership(var10, var8 as FlowCollector<?>);
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun <T> `collectWhile$$forInline`(var0: Flow<? extends T>, var1: (T?, Continuation<? super java.lang.Boolean>?) -> Any, var2: Continuation<? super Unit>): Any {
      val var4: FlowCollector = new FlowCollector<T>(var1) {
         final Function2<T, Continuation<? super java.lang.Boolean>, Object> $predicate;

         {
            this.$predicate = var1;
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
                     return this.this$0.emit(null, this as Continuation<? super Unit>);
                  }
               };
            }

            var var9: Function2 = (Function2)var2.result;
            val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var8: <unrepresentable>;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var8 = var2.L$0 as <unrepresentable>;
               ResultKt.throwOnFailure(var9);
               var1 = var9;
            } else {
               ResultKt.throwOnFailure(var9);
               var9 = this.$predicate;
               var2.L$0 = this;
               var2.label = 1;
               var1 = var9.invoke(var1, var2);
               if (var1 === var5) {
                  return var5;
               }

               var8 = this;
            }

            if (var1 as java.lang.Boolean) {
               return Unit.INSTANCE;
            } else {
               throw new AbortFlowException(var8 as FlowCollector<?>);
            }
         }

         public Object emit$$forInline(T var1, Continuation<? super Unit> var2) {
            new ContinuationImpl(this, var2) {
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
                  return this.this$0.emit(null, this as Continuation<? super Unit>);
               }
            };
            if (this.$predicate.invoke(var1, var2) as java.lang.Boolean) {
               return Unit.INSTANCE;
            } else {
               throw new AbortFlowException(this as FlowCollector<?>);
            }
         }
      };

      try {
         var0.collect(var4, var2);
      } catch (var3: AbortFlowException) {
         FlowExceptions_commonKt.checkOwnership(var3, var4);
      }

      return Unit.INSTANCE;
   }

   @JvmStatic
   public fun <T> Flow<T>.drop(count: Int): Flow<T> {
      if (var1 >= 0) {
         return new Flow<T>(var0, var1) {
            final int $count$inlined;
            final Flow $this_drop$inlined;

            {
               this.$this_drop$inlined = var1;
               this.$count$inlined = var2;
            }

            @Override
            public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
               val var4: Any = this.$this_drop$inlined.collect(new FlowCollector(new Ref.IntRef(), this.$count$inlined, var1) {
                  final int $count;
                  final Ref.IntRef $skipped;
                  final FlowCollector<T> $this_unsafeFlow;

                  {
                     this.$skipped = var1;
                     this.$count = var2x;
                     this.$this_unsafeFlow = var3;
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

                     var var5: Any = var2x.result;
                     val var9: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     if (var2x.label != 0) {
                        if (var2x.label != 1) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var5);
                     } else {
                        ResultKt.throwOnFailure(var5);
                        if (this.$skipped.element < this.$count) {
                           this.$skipped.element++;
                           val var8: Int = this.$skipped.element;
                           return Unit.INSTANCE;
                        }

                        var5 = this.$this_unsafeFlow;
                        var2x.label = 1;
                        if (((FlowCollector<Object>)var5).emit(var1, var2x) === var9) {
                           return var9;
                        }
                     }

                     return Unit.INSTANCE;
                  }
               }, var2);
               return if (var4 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var4 else Unit.INSTANCE;
            }
         };
      } else {
         val var2: StringBuilder = new StringBuilder("Drop count should be non-negative, but had ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun <T> Flow<T>.dropWhile(predicate: (T, Continuation<Boolean>) -> Any?): Flow<T> {
      return new Flow<T>(var0, var1) {
         final Function2 $predicate$inlined;
         final Flow $this_dropWhile$inlined;

         {
            this.$this_dropWhile$inlined = var1;
            this.$predicate$inlined = var2;
         }

         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            val var4: Any = this.$this_dropWhile$inlined.collect(new FlowCollector(new Ref.BooleanRef(), var1, this.$predicate$inlined) {
               final Ref.BooleanRef $matched;
               final Function2<T, Continuation<? super java.lang.Boolean>, Object> $predicate;
               final FlowCollector<T> $this_unsafeFlow;

               {
                  this.$matched = var1;
                  this.$this_unsafeFlow = var2x;
                  this.$predicate = var3;
               }

               @Override
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
                  label52: {
                     if (var2x is <unrepresentable>) {
                        val var4x: <unrepresentable> = var2x as <unrepresentable>;
                        if ((var2x.label and Integer.MIN_VALUE) != 0) {
                           var4x.label += Integer.MIN_VALUE;
                           var2x = var4x;
                           break label52;
                        }
                     }

                     var2x = new ContinuationImpl(this, var2x) {
                        Object L$0;
                        Object L$1;
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

                  var var8: Function2 = (Function2)var2x.result;
                  val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  val var5: <unrepresentable>;
                  if (var2x.label != 0) {
                     if (var2x.label == 1) {
                        ResultKt.throwOnFailure(var8);
                        return Unit.INSTANCE;
                     }

                     if (var2x.label != 2) {
                        if (var2x.label != 3) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var8);
                        return Unit.INSTANCE;
                     }

                     var1 = var2x.L$1;
                     var5 = var2x.L$0 as <unrepresentable>;
                     ResultKt.throwOnFailure(var8);
                  } else {
                     ResultKt.throwOnFailure(var8);
                     if (this.$matched.element) {
                        var8 = this.$this_unsafeFlow;
                        var2x.label = 1;
                        if (((FlowCollector<Object>)var8).emit(var1, var2x) === var6) {
                           return var6;
                        }

                        return Unit.INSTANCE;
                     }

                     var8 = this.$predicate;
                     var2x.L$0 = this;
                     var2x.L$1 = var1;
                     var2x.label = 2;
                     var8 = (Function2)var8.invoke(var1, var2x);
                     if (var8 === var6) {
                        return var6;
                     }

                     var5 = this;
                  }

                  if (var8 as java.lang.Boolean) {
                     return Unit.INSTANCE;
                  } else {
                     var5.$matched.element = true;
                     val var10: FlowCollector = var5.$this_unsafeFlow;
                     var2x.L$0 = null;
                     var2x.L$1 = null;
                     var2x.label = 3;
                     return if (var10.emit(var1, var2x) === var6) var6 else Unit.INSTANCE;
                  }
               }
            }, var2);
            return if (var4 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var4 else Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   private suspend fun <T> FlowCollector<T>.emitAbort(value: T) {
      label23: {
         if (var2 is <unrepresentable>) {
            val var4: <unrepresentable> = var2 as <unrepresentable>;
            if ((var2.label and Integer.MIN_VALUE) != 0) {
               var4.label += Integer.MIN_VALUE;
               var2 = var4;
               break label23;
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
               return FlowKt__LimitKt.access$emitAbort$FlowKt__LimitKt(null, null, this);
            }
         };
      }

      val var6: Any = var2.result;
      val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      val var8: FlowCollector;
      if (var2.label != 0) {
         if (var2.label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var8 = var2.L$0 as FlowCollector;
         ResultKt.throwOnFailure(var6);
      } else {
         ResultKt.throwOnFailure(var6);
         var2.L$0 = var0;
         var2.label = 1;
         var8 = var0;
         if (var0.emit(var1, var2) === var5) {
            return var5;
         }
      }

      throw new AbortFlowException(var8);
   }

   @JvmStatic
   public fun <T> Flow<T>.take(count: Int): Flow<T> {
      if (var1 > 0) {
         return new Flow<T>(var0, var1) {
            final int $count$inlined;
            final Flow $this_take$inlined;

            {
               this.$this_take$inlined = var1;
               this.$count$inlined = var2;
            }

            // $VF: Duplicated exception handlers to handle obfuscated exceptions
            @Override
            public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
               var var4: Any;
               label86: {
                  if (var2 is <unrepresentable>) {
                     var4 = var2 as <unrepresentable>;
                     if (((var2 as <unrepresentable>).label and Integer.MIN_VALUE) != 0) {
                        ((<unrepresentable>)var4).label += Integer.MIN_VALUE;
                        break label86;
                     }
                  }

                  var4 = new ContinuationImpl(this, var2) {
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
                        return this.this$0.collect(null, this);
                     }
                  };
               }

               var var6: Flow = (Flow)((<unrepresentable>)var4).result;
               val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               if (((<unrepresentable>)var4).label != 0) {
                  if (((<unrepresentable>)var4).label != 1) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  try {
                     ResultKt.throwOnFailure(var6);
                  } catch (var9: AbortFlowException) {
                     FlowExceptions_commonKt.checkOwnership(var9, ((<unrepresentable>)var4).L$0 as FlowCollector<?>);
                  }
               } else {
                  ResultKt.throwOnFailure(var6);
                  var2 = var4 as Continuation;
                  val var7: Ref.IntRef = new Ref.IntRef();

                  try {
                     var6 = this.$this_take$inlined;
                  } catch (var16: AbortFlowException) {
                     FlowExceptions_commonKt.checkOwnership(var16, var1);
                     return Unit.INSTANCE;
                  }

                  var var8: FlowCollector;
                  try {
                     var8 = new FlowCollector() {
                        final Ref.IntRef $consumed;
                        final int $count;
                        final FlowCollector<T> $this_unsafeFlow;

                        {
                           this.$consumed = var1;
                           this.$count = var2x;
                           this.$this_unsafeFlow = var3;
                        }

                        @Override
                        public final Object emit(T var1, Continuation<? super Unit> var2x) {
                           label37: {
                              if (var2x is <unrepresentable>) {
                                 val var4x: <unrepresentable> = var2x as <unrepresentable>;
                                 if ((var2x.label and Integer.MIN_VALUE) != 0) {
                                    var4x.label += Integer.MIN_VALUE;
                                    var2x = var4x;
                                    break label37;
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

                           var var5x: Any = var2x.result;
                           val var7x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                           if (var2x.label != 0) {
                              if (var2x.label == 1) {
                                 ResultKt.throwOnFailure(var5x);
                                 return Unit.INSTANCE;
                              }

                              if (var2x.label != 2) {
                                 throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                              }

                              ResultKt.throwOnFailure(var5x);
                           } else {
                              ResultKt.throwOnFailure(var5x);
                              this.$consumed.element++;
                              if (this.$consumed.element < this.$count) {
                                 var5x = this.$this_unsafeFlow;
                                 var2x.label = 1;
                                 if (((FlowCollector<Object>)var5x).emit(var1, var2x) === var7x) {
                                    return var7x;
                                 }

                                 return Unit.INSTANCE;
                              }

                              var5x = this.$this_unsafeFlow;
                              var2x.label = 2;
                              if (FlowKt__LimitKt.access$emitAbort$FlowKt__LimitKt((FlowCollector)var5x, var1, var2x) === var7x) {
                                 return var7x;
                              }
                           }

                           return Unit.INSTANCE;
                        }
                     };
                  } catch (var15: AbortFlowException) {
                     FlowExceptions_commonKt.checkOwnership(var15, var1);
                     return Unit.INSTANCE;
                  }

                  try {
                     var8./* $VF: Unable to resugar constructor */<init>(var7, this.$count$inlined, var1);
                  } catch (var14: AbortFlowException) {
                     FlowExceptions_commonKt.checkOwnership(var14, var1);
                     return Unit.INSTANCE;
                  }

                  try {
                     var22 = var8;
                  } catch (var13: AbortFlowException) {
                     FlowExceptions_commonKt.checkOwnership(var13, var1);
                     return Unit.INSTANCE;
                  }

                  try {
                     ((<unrepresentable>)var4).L$0 = var1;
                  } catch (var12: AbortFlowException) {
                     FlowExceptions_commonKt.checkOwnership(var12, var1);
                     return Unit.INSTANCE;
                  }

                  try {
                     ((<unrepresentable>)var4).label = 1;
                  } catch (var11: AbortFlowException) {
                     FlowExceptions_commonKt.checkOwnership(var11, var1);
                     return Unit.INSTANCE;
                  }

                  try {
                     var17 = var6.collect(var22, (Continuation<? super Unit>)var4);
                  } catch (var10: AbortFlowException) {
                     FlowExceptions_commonKt.checkOwnership(var10, var1);
                     return Unit.INSTANCE;
                  }

                  if (var17 === var5) {
                     return var5;
                  }
               }

               return Unit.INSTANCE;
            }
         };
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" should be positive");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun <T> Flow<T>.takeWhile(predicate: (T, Continuation<Boolean>) -> Any?): Flow<T> {
      return new Flow<T>(var0, var1) {
         final Function2 $predicate$inlined;
         final Flow $this_takeWhile$inlined;

         {
            this.$this_takeWhile$inlined = var1;
            this.$predicate$inlined = var2;
         }

         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            label45: {
               if (var2 is <unrepresentable>) {
                  val var4: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var4.label += Integer.MIN_VALUE;
                     var2 = var4;
                     break label45;
                  }
               }

               var2 = new ContinuationImpl(this, var2) {
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
                     return this.this$0.collect(null, this);
                  }
               };
            }

            var var5: Flow = (Flow)var2.result;
            val var13: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var9: Any;
            val var11: AbortFlowException;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var9 = var2.L$0 as <unrepresentable>;

               try {
                  ResultKt.throwOnFailure(var5);
                  return Unit.INSTANCE;
               } catch (var7: AbortFlowException) {
                  var11 = var7;
               }
            } else {
               label57: {
                  ResultKt.throwOnFailure(var5);
                  var5 = var2;
                  var5 = this.$this_takeWhile$inlined;
                  var9 = new FlowCollector<T>(this.$predicate$inlined, var1) {
                     final Function2 $predicate$inlined;
                     final FlowCollector $this_unsafeFlow$inlined;

                     {
                        this.$predicate$inlined = var1;
                        this.$this_unsafeFlow$inlined = var2x;
                     }

                     @Override
                     public Object emit(T var1, Continuation<? super Unit> var2x) {
                        label41: {
                           if (var2x is <unrepresentable>) {
                              val var5x: <unrepresentable> = var2x as <unrepresentable>;
                              if ((var2x.label and Integer.MIN_VALUE) != 0) {
                                 var5x.label += Integer.MIN_VALUE;
                                 var2x = var5x;
                                 break label41;
                              }
                           }

                           var2x = new ContinuationImpl(this, var2x) {
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

                        var var3: Boolean;
                        label36: {
                           var var6: Any = var2x.result;
                           val var7: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                           var3 = true;
                           var var10: Any;
                           if (var2x.label != 0) {
                              if (var2x.label != 1) {
                                 if (var2x.label != 2) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                 }

                                 var1 = var2x.L$0 as <unrepresentable>;
                                 ResultKt.throwOnFailure(var6);
                                 break label36;
                              }

                              var10 = (Function2)var2x.L$1;
                              var1 = var2x.L$0 as <unrepresentable>;
                              ResultKt.throwOnFailure(var6);
                           } else {
                              ResultKt.throwOnFailure(var6);
                              var10 = var2x;
                              var10 = this.$predicate$inlined;
                              var2x.L$0 = this;
                              var2x.L$1 = var1;
                              var2x.label = 1;
                              var6 = var10.invoke(var1, var2x);
                              if (var6 === var7) {
                                 return var7;
                              }

                              var10 = var1;
                              var1 = this;
                           }

                           if (var6 as java.lang.Boolean) {
                              var6 = var1.$this_unsafeFlow$inlined;
                              var2x.L$0 = var1;
                              var2x.L$1 = null;
                              var2x.label = 2;
                              if (((FlowCollector<Function2>)var6).emit(var10, var2x) === var7) {
                                 return var7;
                              }
                           } else {
                              var3 = false;
                           }
                        }

                        if (var3) {
                           return Unit.INSTANCE;
                        } else {
                           throw new AbortFlowException(var1);
                        }
                     }
                  };

                  try {
                     val var6: FlowCollector = var9 as FlowCollector;
                     var2.L$0 = var9;
                     var2.label = 1;
                     var12 = var5.collect(var6, var2);
                  } catch (var8: AbortFlowException) {
                     var11 = var8;
                     break label57;
                  }

                  if (var12 === var13) {
                     return var13;
                  }

                  return Unit.INSTANCE;
               }
            }

            FlowExceptions_commonKt.checkOwnership(var11, var9 as FlowCollector<?>);
            return Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T, R> Flow<T>.transformWhile(transform: (FlowCollector<R>, T, Continuation<Boolean>) -> Any?): Flow<R> {
      return FlowKt.flow((new Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object>(var0, var1, null) {
         final Flow<T> $this_transformWhile;
         final Function3<FlowCollector<? super R>, T, Continuation<? super java.lang.Boolean>, Object> $transform;
         private Object L$0;
         int label;

         {
            super(2, var3x);
            this.$this_transformWhile = var1;
            this.$transform = var2x;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$this_transformWhile, this.$transform, var2);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(FlowCollector<? super R> var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            var var3x: Any;
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var3x = this.L$0 as <unrepresentable>;

               try {
                  ResultKt.throwOnFailure(var1);
                  return Unit.INSTANCE;
               } catch (var7: AbortFlowException) {
                  var1 = var7;
               }
            } else {
               label46: {
                  ResultKt.throwOnFailure(var1);
                  var3x = this.L$0 as FlowCollector;
                  var1 = this.$this_transformWhile;
                  var3x = new FlowCollector<T>(this.$transform, (FlowCollector)var3x) {
                     final FlowCollector $$this$flow$inlined;
                     final Function3 $transform$inlined;

                     {
                        this.$transform$inlined = var1;
                        this.$$this$flow$inlined = var2x;
                     }

                     @Override
                     public Object emit(T var1, Continuation<? super Unit> var2x) {
                        label29: {
                           if (var2x is <unrepresentable>) {
                              val var4x: <unrepresentable> = var2x as <unrepresentable>;
                              if ((var2x.label and Integer.MIN_VALUE) != 0) {
                                 var4x.label += Integer.MIN_VALUE;
                                 var2x = var4x;
                                 break label29;
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

                        var var10: Any = var2x.result;
                        val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        val var9: Any;
                        if (var2x.label != 0) {
                           if (var2x.label != 1) {
                              throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                           }

                           var1 = var2x.L$0 as <unrepresentable>;
                           ResultKt.throwOnFailure(var10);
                           var9 = var10;
                        } else {
                           ResultKt.throwOnFailure(var10);
                           var10 = var2x;
                           val var6: Function3 = this.$transform$inlined;
                           var10 = this.$$this$flow$inlined;
                           var2x.L$0 = this;
                           var2x.label = 1;
                           var9 = var6.invoke(var10, var1, var2x);
                           if (var9 === var5) {
                              return var5;
                           }

                           var1 = this;
                        }

                        if (var9 as java.lang.Boolean) {
                           return Unit.INSTANCE;
                        } else {
                           throw new AbortFlowException(var1);
                        }
                     }
                  };

                  try {
                     val var6: FlowCollector = var3x as FlowCollector;
                     val var5: Continuation = this;
                     this.L$0 = var3x;
                     this.label = 1;
                     var1 = var1.collect(var6, var5);
                  } catch (var8: AbortFlowException) {
                     var1 = var8;
                     break label46;
                  }

                  if (var1 === var4) {
                     return var4;
                  }

                  return Unit.INSTANCE;
               }
            }

            FlowExceptions_commonKt.checkOwnership(var1, var3x as FlowCollector<?>);
            return Unit.INSTANCE;
         }
      }) as Function2);
   }
}
