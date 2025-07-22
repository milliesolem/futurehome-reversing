package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.Ref
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.internal.NullSurrogateKt

@JvmSynthetic
internal class FlowKt__TransformKt {
   @JvmStatic
   public inline fun <T> Flow<T>.filter(crossinline predicate: (T, Continuation<Boolean>) -> Any?): Flow<T> {
      return new Flow<T>(var0, var1) {
         final Function2 $predicate$inlined;
         final Flow $this_unsafeTransform$inlined;

         {
            this.$this_unsafeTransform$inlined = var1;
            this.$predicate$inlined = var2;
         }

         @Override
         public Object collect(FlowCollector var1, Continuation var2) {
            val var3: Any = this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1, this.$predicate$inlined) {
               final Function2 $predicate$inlined;
               final FlowCollector $this_unsafeFlow;

               {
                  this.$this_unsafeFlow = var1;
                  this.$predicate$inlined = var2x;
               }

               @Override
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
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

                  var var5: Function2 = (Function2)var2x.result;
                  val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  val var8: FlowCollector;
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        if (var2x.label != 2) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var5);
                        return Unit.INSTANCE;
                     }

                     var8 = var2x.L$1 as FlowCollector;
                     var1 = var2x.L$0;
                     ResultKt.throwOnFailure(var5);
                  } else {
                     ResultKt.throwOnFailure(var5);
                     var8 = this.$this_unsafeFlow;
                     var5 = var2x;
                     var5 = this.$predicate$inlined;
                     var2x.L$0 = var1;
                     var2x.L$1 = var8;
                     var2x.label = 1;
                     var5 = (Function2)var5.invoke(var1, var2x);
                     if (var5 === var6) {
                        return var6;
                     }
                  }

                  if (var5 as java.lang.Boolean) {
                     var2x.L$0 = null;
                     var2x.L$1 = null;
                     var2x.label = 2;
                     if (var8.emit(var1, var2x) === var6) {
                        return var6;
                     }
                  }

                  return Unit.INSTANCE;
               }

               public final Object emit$$forInline(Object var1, Continuation var2x) {
                  new ContinuationImpl(this, var2x) {
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
                        return this.this$0.emit(null, this as Continuation<? super Unit>);
                     }
                  };
                  val var3x: FlowCollector = this.$this_unsafeFlow;
                  if (this.$predicate$inlined.invoke(var1, var2x) as java.lang.Boolean) {
                     var3x.emit(var1, var2x);
                  }

                  return Unit.INSTANCE;
               }
            }, var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }

         public Object collect$$forInline(FlowCollector var1, Continuation var2) {
            new ContinuationImpl(this, var2) {
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
            this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1, this.$predicate$inlined) {
               final Function2 $predicate$inlined;
               final FlowCollector $this_unsafeFlow;

               {
                  this.$this_unsafeFlow = var1;
                  this.$predicate$inlined = var2x;
               }

               @Override
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
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
                           return this.this$0.emit(null, this as Continuation<? super Unit>);
                        }
                     };
                  }

                  var var5: Function2 = (Function2)var2x.result;
                  val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  val var8: FlowCollector;
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        if (var2x.label != 2) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var5);
                        return Unit.INSTANCE;
                     }

                     var8 = var2x.L$1 as FlowCollector;
                     var1 = var2x.L$0;
                     ResultKt.throwOnFailure(var5);
                  } else {
                     ResultKt.throwOnFailure(var5);
                     var8 = this.$this_unsafeFlow;
                     var5 = var2x;
                     var5 = this.$predicate$inlined;
                     var2x.L$0 = var1;
                     var2x.L$1 = var8;
                     var2x.label = 1;
                     var5 = (Function2)var5.invoke(var1, var2x);
                     if (var5 === var6) {
                        return var6;
                     }
                  }

                  if (var5 as java.lang.Boolean) {
                     var2x.L$0 = null;
                     var2x.L$1 = null;
                     var2x.label = 2;
                     if (var8.emit(var1, var2x) === var6) {
                        return var6;
                     }
                  }

                  return Unit.INSTANCE;
               }

               public final Object emit$$forInline(Object var1, Continuation var2x) {
                  new ContinuationImpl(this, var2x) {
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
                        return this.this$0.emit(null, this as Continuation<? super Unit>);
                     }
                  };
                  val var3x: FlowCollector = this.$this_unsafeFlow;
                  if (this.$predicate$inlined.invoke(var1, var2x) as java.lang.Boolean) {
                     var3x.emit(var1, var2x);
                  }

                  return Unit.INSTANCE;
               }
            }, var2);
            return Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <R : Any> Flow<*>.filterIsInstance(klass: KClass<R>): Flow<R> {
      return (Flow<R>)(new Flow<Object>(var0, var1) {
         final KClass $klass$inlined;
         final Flow $this_unsafeTransform$inlined;

         {
            this.$this_unsafeTransform$inlined = var1;
            this.$klass$inlined = var2;
         }

         @Override
         public Object collect(FlowCollector var1, Continuation var2) {
            val var3: Any = this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1, this.$klass$inlined) {
               final KClass $klass$inlined;
               final FlowCollector $this_unsafeFlow;

               {
                  this.$this_unsafeFlow = var1;
                  this.$klass$inlined = var2x;
               }

               @Override
               public final Object emit(Object var1, Continuation var2x) {
                  label25: {
                     if (var2x is <unrepresentable>) {
                        val var4: <unrepresentable> = var2x as <unrepresentable>;
                        if ((var2x.label and Integer.MIN_VALUE) != 0) {
                           var4.label += Integer.MIN_VALUE;
                           var2x = var4;
                           break label25;
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

                  var var5: Any = var2x.result;
                  val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     ResultKt.throwOnFailure(var5);
                  } else {
                     ResultKt.throwOnFailure(var5);
                     var5 = this.$this_unsafeFlow;
                     val var6: Continuation = var2x;
                     if (this.$klass$inlined.isInstance(var1)) {
                        var2x.label = 1;
                        if (((FlowCollector<Object>)var5).emit(var1, var2x) === var8) {
                           return var8;
                        }
                     }
                  }

                  return Unit.INSTANCE;
               }
            }, var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }
      });
   }

   @JvmStatic
   public inline fun <T> Flow<T>.filterNot(crossinline predicate: (T, Continuation<Boolean>) -> Any?): Flow<T> {
      return new Flow<T>(var0, var1) {
         final Function2 $predicate$inlined;
         final Flow $this_unsafeTransform$inlined;

         {
            this.$this_unsafeTransform$inlined = var1;
            this.$predicate$inlined = var2;
         }

         @Override
         public Object collect(FlowCollector var1, Continuation var2) {
            val var3: Any = this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1, this.$predicate$inlined) {
               final Function2 $predicate$inlined;
               final FlowCollector $this_unsafeFlow;

               {
                  this.$this_unsafeFlow = var1;
                  this.$predicate$inlined = var2x;
               }

               @Override
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
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

                  var var5: Function2 = (Function2)var2x.result;
                  val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  val var8: FlowCollector;
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        if (var2x.label != 2) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var5);
                        return Unit.INSTANCE;
                     }

                     var8 = var2x.L$1 as FlowCollector;
                     var1 = var2x.L$0;
                     ResultKt.throwOnFailure(var5);
                  } else {
                     ResultKt.throwOnFailure(var5);
                     var8 = this.$this_unsafeFlow;
                     var5 = var2x;
                     var5 = this.$predicate$inlined;
                     var2x.L$0 = var1;
                     var2x.L$1 = var8;
                     var2x.label = 1;
                     var5 = (Function2)var5.invoke(var1, var2x);
                     if (var5 === var6) {
                        return var6;
                     }
                  }

                  if (!var5 as java.lang.Boolean) {
                     var2x.L$0 = null;
                     var2x.L$1 = null;
                     var2x.label = 2;
                     if (var8.emit(var1, var2x) === var6) {
                        return var6;
                     }
                  }

                  return Unit.INSTANCE;
               }

               public final Object emit$$forInline(Object var1, Continuation var2x) {
                  new ContinuationImpl(this, var2x) {
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
                        return this.this$0.emit(null, this as Continuation<? super Unit>);
                     }
                  };
                  val var3x: FlowCollector = this.$this_unsafeFlow;
                  if (!this.$predicate$inlined.invoke(var1, var2x) as java.lang.Boolean) {
                     var3x.emit(var1, var2x);
                  }

                  return Unit.INSTANCE;
               }
            }, var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }

         public Object collect$$forInline(FlowCollector var1, Continuation var2) {
            new ContinuationImpl(this, var2) {
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
            this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1, this.$predicate$inlined) {
               final Function2 $predicate$inlined;
               final FlowCollector $this_unsafeFlow;

               {
                  this.$this_unsafeFlow = var1;
                  this.$predicate$inlined = var2x;
               }

               @Override
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
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
                           return this.this$0.emit(null, this as Continuation<? super Unit>);
                        }
                     };
                  }

                  var var5: Function2 = (Function2)var2x.result;
                  val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  val var8: FlowCollector;
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        if (var2x.label != 2) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var5);
                        return Unit.INSTANCE;
                     }

                     var8 = var2x.L$1 as FlowCollector;
                     var1 = var2x.L$0;
                     ResultKt.throwOnFailure(var5);
                  } else {
                     ResultKt.throwOnFailure(var5);
                     var8 = this.$this_unsafeFlow;
                     var5 = var2x;
                     var5 = this.$predicate$inlined;
                     var2x.L$0 = var1;
                     var2x.L$1 = var8;
                     var2x.label = 1;
                     var5 = (Function2)var5.invoke(var1, var2x);
                     if (var5 === var6) {
                        return var6;
                     }
                  }

                  if (!var5 as java.lang.Boolean) {
                     var2x.L$0 = null;
                     var2x.L$1 = null;
                     var2x.label = 2;
                     if (var8.emit(var1, var2x) === var6) {
                        return var6;
                     }
                  }

                  return Unit.INSTANCE;
               }

               public final Object emit$$forInline(Object var1, Continuation var2x) {
                  new ContinuationImpl(this, var2x) {
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
                        return this.this$0.emit(null, this as Continuation<? super Unit>);
                     }
                  };
                  val var3x: FlowCollector = this.$this_unsafeFlow;
                  if (!this.$predicate$inlined.invoke(var1, var2x) as java.lang.Boolean) {
                     var3x.emit(var1, var2x);
                  }

                  return Unit.INSTANCE;
               }
            }, var2);
            return Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T : Any> Flow<T?>.filterNotNull(): Flow<T> {
      return new Flow<T>(var0) {
         final Flow $this_unsafeTransform$inlined;

         {
            this.$this_unsafeTransform$inlined = var1;
         }

         @Override
         public Object collect(FlowCollector var1, Continuation var2) {
            val var3: Any = this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1) {
               final FlowCollector $this_unsafeFlow;

               {
                  this.$this_unsafeFlow = var1;
               }

               @Override
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
                  label25: {
                     if (var2x is <unrepresentable>) {
                        val var4: <unrepresentable> = var2x as <unrepresentable>;
                        if ((var2x.label and Integer.MIN_VALUE) != 0) {
                           var4.label += Integer.MIN_VALUE;
                           var2x = var4;
                           break label25;
                        }
                     }

                     var2x = new ContinuationImpl(this, var2x) {
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

                  var var5: Any = var2x.result;
                  val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     ResultKt.throwOnFailure(var5);
                  } else {
                     ResultKt.throwOnFailure(var5);
                     var5 = this.$this_unsafeFlow;
                     val var6: Continuation = var2x;
                     if (var1 != null) {
                        var2x.label = 1;
                        if (((FlowCollector<Object>)var5).emit(var1, var2x) === var8) {
                           return var8;
                        }
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
   public inline fun <T, R> Flow<T>.map(crossinline transform: (T, Continuation<R>) -> Any?): Flow<R> {
      return new Flow<R>(var0, var1) {
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
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
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

                  var var9: Function2 = (Function2)var2x.result;
                  val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        if (var2x.label != 2) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var9);
                        return Unit.INSTANCE;
                     }

                     var1 = var2x.L$0 as FlowCollector;
                     ResultKt.throwOnFailure(var9);
                  } else {
                     ResultKt.throwOnFailure(var9);
                     val var5: FlowCollector = this.$this_unsafeFlow;
                     var9 = var2x;
                     var9 = this.$transform$inlined;
                     var2x.L$0 = this.$this_unsafeFlow;
                     var2x.label = 1;
                     var9 = (Function2)var9.invoke(var1, var2x);
                     if (var9 === var6) {
                        return var6;
                     }

                     var1 = var5;
                  }

                  var2x.L$0 = null;
                  var2x.label = 2;
                  return if (var1.emit(var9, var2x) === var6) var6 else Unit.INSTANCE;
               }

               public final Object emit$$forInline(Object var1, Continuation var2x) {
                  new ContinuationImpl(this, var2x) {
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
                  this.$this_unsafeFlow.emit(this.$transform$inlined.invoke(var1, var2x), var2x);
                  return Unit.INSTANCE;
               }
            }, var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }

         public Object collect$$forInline(FlowCollector var1, Continuation var2) {
            new ContinuationImpl(this, var2) {
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
            this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1, this.$transform$inlined$1) {
               final FlowCollector $this_unsafeFlow;
               final Function2 $transform$inlined;

               {
                  this.$this_unsafeFlow = var1;
                  this.$transform$inlined = var2x;
               }

               @Override
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
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
                           return this.this$0.emit(null, this as Continuation<? super Unit>);
                        }
                     };
                  }

                  var var9: Function2 = (Function2)var2x.result;
                  val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        if (var2x.label != 2) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var9);
                        return Unit.INSTANCE;
                     }

                     var1 = var2x.L$0 as FlowCollector;
                     ResultKt.throwOnFailure(var9);
                  } else {
                     ResultKt.throwOnFailure(var9);
                     val var5: FlowCollector = this.$this_unsafeFlow;
                     var9 = var2x;
                     var9 = this.$transform$inlined;
                     var2x.L$0 = this.$this_unsafeFlow;
                     var2x.label = 1;
                     var9 = (Function2)var9.invoke(var1, var2x);
                     if (var9 === var6) {
                        return var6;
                     }

                     var1 = var5;
                  }

                  var2x.L$0 = null;
                  var2x.label = 2;
                  return if (var1.emit(var9, var2x) === var6) var6 else Unit.INSTANCE;
               }

               public final Object emit$$forInline(Object var1, Continuation var2x) {
                  new ContinuationImpl(this, var2x) {
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
                  this.$this_unsafeFlow.emit(this.$transform$inlined.invoke(var1, var2x), var2x);
                  return Unit.INSTANCE;
               }
            }, var2);
            return Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public inline fun <T, R : Any> Flow<T>.mapNotNull(crossinline transform: (T, Continuation<R?>) -> Any?): Flow<R> {
      return new Flow<R>(var0, var1) {
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
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
                  label35: {
                     if (var2x is <unrepresentable>) {
                        val var4: <unrepresentable> = var2x as <unrepresentable>;
                        if ((var2x.label and Integer.MIN_VALUE) != 0) {
                           var4.label += Integer.MIN_VALUE;
                           var2x = var4;
                           break label35;
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
                     var5 = var2x;
                     var5 = this.$transform$inlined;
                     var2x.L$0 = this.$this_unsafeFlow;
                     var2x.label = 1;
                     var1 = var5.invoke(var1, var2x);
                     if (var1 === var6) {
                        return var6;
                     }
                  }

                  if (var1 != null) {
                     var2x.L$0 = null;
                     var2x.label = 2;
                     if (var9.emit(var1, var2x) === var6) {
                        return var6;
                     }
                  }

                  return Unit.INSTANCE;
               }

               public final Object emit$$forInline(Object var1, Continuation var2x) {
                  new ContinuationImpl(this, var2x) {
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
                  val var3x: FlowCollector = this.$this_unsafeFlow;
                  var1 = this.$transform$inlined.invoke(var1, var2x);
                  if (var1 != null) {
                     var3x.emit(var1, var2x);
                  }

                  return Unit.INSTANCE;
               }
            }, var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }

         public Object collect$$forInline(FlowCollector var1, Continuation var2) {
            new ContinuationImpl(this, var2) {
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
            this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1, this.$transform$inlined$1) {
               final FlowCollector $this_unsafeFlow;
               final Function2 $transform$inlined;

               {
                  this.$this_unsafeFlow = var1;
                  this.$transform$inlined = var2x;
               }

               @Override
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
                  label35: {
                     if (var2x is <unrepresentable>) {
                        val var4: <unrepresentable> = var2x as <unrepresentable>;
                        if ((var2x.label and Integer.MIN_VALUE) != 0) {
                           var4.label += Integer.MIN_VALUE;
                           var2x = var4;
                           break label35;
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
                           return this.this$0.emit(null, this as Continuation<? super Unit>);
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
                     var5 = var2x;
                     var5 = this.$transform$inlined;
                     var2x.L$0 = this.$this_unsafeFlow;
                     var2x.label = 1;
                     var1 = var5.invoke(var1, var2x);
                     if (var1 === var6) {
                        return var6;
                     }
                  }

                  if (var1 != null) {
                     var2x.L$0 = null;
                     var2x.label = 2;
                     if (var9.emit(var1, var2x) === var6) {
                        return var6;
                     }
                  }

                  return Unit.INSTANCE;
               }

               public final Object emit$$forInline(Object var1, Continuation var2x) {
                  new ContinuationImpl(this, var2x) {
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
                  val var3x: FlowCollector = this.$this_unsafeFlow;
                  var1 = this.$transform$inlined.invoke(var1, var2x);
                  if (var1 != null) {
                     var3x.emit(var1, var2x);
                  }

                  return Unit.INSTANCE;
               }
            }, var2);
            return Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T> Flow<T>.onEach(action: (T, Continuation<Unit>) -> Any?): Flow<T> {
      return new Flow<T>(var0, var1) {
         final Function2 $action$inlined;
         final Flow $this_unsafeTransform$inlined;

         {
            this.$this_unsafeTransform$inlined = var1;
            this.$action$inlined = var2;
         }

         @Override
         public Object collect(FlowCollector var1, Continuation var2) {
            val var3: Any = this.$this_unsafeTransform$inlined.collect(new FlowCollector(var1, this.$action$inlined) {
               final Function2 $action$inlined;
               final FlowCollector $this_unsafeFlow;

               {
                  this.$this_unsafeFlow = var1;
                  this.$action$inlined = var2x;
               }

               @Override
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
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

                  var var6: Any = var2x.result;
                  val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  val var8: FlowCollector;
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        if (var2x.label != 2) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var6);
                        return Unit.INSTANCE;
                     }

                     var8 = var2x.L$1 as FlowCollector;
                     var1 = var2x.L$0;
                     ResultKt.throwOnFailure(var6);
                  } else {
                     ResultKt.throwOnFailure(var6);
                     var8 = this.$this_unsafeFlow;
                     var6 = var2x;
                     var6 = this.$action$inlined;
                     var2x.L$0 = var1;
                     var2x.L$1 = var8;
                     var2x.label = 1;
                     if (((Function2<Object, <unrepresentable>, R>)var6).invoke(var1, var2x) === var5) {
                        return var5;
                     }
                  }

                  var2x.L$0 = null;
                  var2x.L$1 = null;
                  var2x.label = 2;
                  return if (var8.emit(var1, var2x) === var5) var5 else Unit.INSTANCE;
               }
            }, var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T, R> Flow<T>.runningFold(initial: R, operation: (R, T, Continuation<R>) -> Any?): Flow<R> {
      return new Flow<R>(var1, var0, var2) {
         final Object $initial$inlined;
         final Function3 $operation$inlined;
         final Flow $this_runningFold$inlined;

         {
            this.$initial$inlined = var1;
            this.$this_runningFold$inlined = var2;
            this.$operation$inlined = var3;
         }

         @Override
         public Object collect(FlowCollector<? super R> var1, Continuation<? super Unit> var2) {
            label34: {
               if (var2 is <unrepresentable>) {
                  val var4: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var4.label += Integer.MIN_VALUE;
                     var2 = var4;
                     break label34;
                  }
               }

               var2 = new ContinuationImpl(this, var2) {
                  Object L$0;
                  Object L$1;
                  Object L$2;
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

            var var7: Any = var2.result;
            val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var5: Ref.ObjectRef;
            val var10: <unrepresentable>;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  if (var2.label != 2) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  ResultKt.throwOnFailure(var7);
                  return Unit.INSTANCE;
               }

               var5 = var2.L$2 as Ref.ObjectRef;
               var1 = var2.L$1 as FlowCollector;
               var10 = var2.L$0 as <unrepresentable>;
               ResultKt.throwOnFailure(var7);
            } else {
               ResultKt.throwOnFailure(var7);
               val var11: Continuation = var2;
               var5 = new Ref.ObjectRef();
               var5.element = (T)this.$initial$inlined;
               val var12: Any = var5.element;
               var2.L$0 = this;
               var2.L$1 = var1;
               var2.L$2 = var5;
               var2.label = 1;
               if (var1.emit(var12, var2) === var6) {
                  return var6;
               }

               var10 = this;
            }

            var7 = var10.$this_runningFold$inlined;
            var1 = new FlowCollector(var5, var10.$operation$inlined, var1) {
               final Ref.ObjectRef<R> $accumulator;
               final Function3<R, T, Continuation<? super R>, Object> $operation;
               final FlowCollector<R> $this_unsafeFlow;

               {
                  this.$accumulator = var1;
                  this.$operation = var2x;
                  this.$this_unsafeFlow = var3;
               }

               @Override
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
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

                  var var5x: Function3 = (Function3)var2x.result;
                  val var6x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  val var11: Ref.ObjectRef;
                  if (var2x.label != 0) {
                     if (var2x.label != 1) {
                        if (var2x.label != 2) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var5x);
                        return Unit.INSTANCE;
                     }

                     var11 = var2x.L$1 as Ref.ObjectRef;
                     var1 = var2x.L$0 as <unrepresentable>;
                     ResultKt.throwOnFailure(var5x);
                  } else {
                     ResultKt.throwOnFailure(var5x);
                     var11 = this.$accumulator;
                     var5x = this.$operation;
                     val var7x: Any = this.$accumulator.element;
                     var2x.L$0 = this;
                     var2x.L$1 = var11;
                     var2x.label = 1;
                     var5x = (Function3)var5x.invoke(var7x, var1, var2x);
                     if (var5x === var6x) {
                        return var6x;
                     }

                     var1 = this;
                  }

                  var11.element = (T)var5x;
                  val var12: FlowCollector = var1.$this_unsafeFlow;
                  var1 = var1.$accumulator.element;
                  var2x.L$0 = null;
                  var2x.L$1 = null;
                  var2x.label = 2;
                  return if (var12.emit(var1, var2x) === var6x) var6x else Unit.INSTANCE;
               }
            };
            var2.L$0 = null;
            var2.L$1 = null;
            var2.L$2 = null;
            var2.label = 2;
            return if (((Flow)var7).collect(var1, var2) === var6) var6 else Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T> Flow<T>.runningReduce(operation: (T, T, Continuation<T>) -> Any?): Flow<T> {
      return new Flow<T>(var0, var1) {
         final Function3 $operation$inlined;
         final Flow $this_runningReduce$inlined;

         {
            this.$this_runningReduce$inlined = var1;
            this.$operation$inlined = var2;
         }

         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            val var3: Ref.ObjectRef = new Ref.ObjectRef();
            var3.element = (T)NullSurrogateKt.NULL;
            val var4: Any = this.$this_runningReduce$inlined.collect(new FlowCollector(var3, this.$operation$inlined, var1) {
               final Ref.ObjectRef<Object> $accumulator;
               final Function3<T, T, Continuation<? super T>, Object> $operation;
               final FlowCollector<T> $this_unsafeFlow;

               {
                  this.$accumulator = var1;
                  this.$operation = var2x;
                  this.$this_unsafeFlow = var3x;
               }

               @Override
               public final Object emit(T var1, Continuation<? super Unit> var2x) {
                  var var5: Any;
                  label41: {
                     if (var2x is <unrepresentable>) {
                        var5 = var2x as <unrepresentable>;
                        if (((var2x as <unrepresentable>).label and Integer.MIN_VALUE) != 0) {
                           ((<unrepresentable>)var5).label += Integer.MIN_VALUE;
                           break label41;
                        }
                     }

                     var5 = new ContinuationImpl(this, var2x) {
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

                  var var7: Any;
                  var var15: <unrepresentable>;
                  var var17: Function3;
                  label35: {
                     var var10: Any = ((<unrepresentable>)var5).result;
                     var7 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     val var4x: Ref.ObjectRef;
                     if (((<unrepresentable>)var5).label != 0) {
                        if (((<unrepresentable>)var5).label != 1) {
                           if (((<unrepresentable>)var5).label != 2) {
                              throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                           }

                           ResultKt.throwOnFailure(var10);
                           return Unit.INSTANCE;
                        }

                        var4x = ((<unrepresentable>)var5).L$1 as Ref.ObjectRef;
                        var1 = ((<unrepresentable>)var5).L$0 as <unrepresentable>;
                        ResultKt.throwOnFailure(var10);
                     } else {
                        ResultKt.throwOnFailure(var10);
                        var11 = this.$accumulator;
                        if (this.$accumulator.element === NullSurrogateKt.NULL) {
                           var15 = this;
                           var17 = var1;
                           break label35;
                        }

                        var17 = this.$operation;
                        var var13: Any = this.$accumulator.element;
                        ((<unrepresentable>)var5).L$0 = this;
                        ((<unrepresentable>)var5).L$1 = var11;
                        ((<unrepresentable>)var5).label = 1;
                        var13 = var17.invoke(var13, var1, var5);
                        if (var13 === var7) {
                           return var7;
                        }

                        var1 = this;
                        var10 = var13;
                        var4x = var11;
                     }

                     var17 = (Function3)var10;
                     var11 = var4x;
                     var15 = var1;
                  }

                  var11.element = (T)var17;
                  val var9: FlowCollector = var15.$this_unsafeFlow;
                  val var12: Any = var15.$accumulator.element;
                  ((<unrepresentable>)var5).L$0 = null;
                  ((<unrepresentable>)var5).L$1 = null;
                  ((<unrepresentable>)var5).label = 2;
                  return if (var9.emit(var12, (Continuation<? super Unit>)var5) === var7) var7 else Unit.INSTANCE;
               }
            }, var2);
            return if (var4 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var4 else Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T, R> Flow<T>.scan(initial: R, operation: (R, T, Continuation<R>) -> Any?): Flow<R> {
      return (Flow<R>)FlowKt.runningFold(var0, var1, var2);
   }

   @JvmStatic
   public fun <T> Flow<T>.withIndex(): Flow<IndexedValue<T>> {
      return (Flow<IndexedValue<T>>)(new Flow<IndexedValue<? extends T>>(var0) {
         final Flow $this_withIndex$inlined;

         {
            this.$this_withIndex$inlined = var1;
         }

         @Override
         public Object collect(FlowCollector<? super IndexedValue<? extends T>> var1, Continuation<? super Unit> var2) {
            val var4: Any = this.$this_withIndex$inlined.collect(new FlowCollector(var1, new Ref.IntRef()) {
               final Ref.IntRef $index;
               final FlowCollector<IndexedValue<? extends T>> $this_unsafeFlow;

               {
                  this.$this_unsafeFlow = var1;
                  this.$index = var2x;
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
                     var5 = this.$this_unsafeFlow;
                     val var8: Int = this.$index.element++;
                     if (var8 < 0) {
                        throw new ArithmeticException("Index overflow has happened");
                     }

                     var1 = new IndexedValue<>(var8, var1);
                     var2x.label = 1;
                     if (((FlowCollector<IndexedValue>)var5).emit(var1, var2x) === var9) {
                        return var9;
                     }
                  }

                  return Unit.INSTANCE;
               }
            }, var2);
            return if (var4 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var4 else Unit.INSTANCE;
         }
      });
   }
}
