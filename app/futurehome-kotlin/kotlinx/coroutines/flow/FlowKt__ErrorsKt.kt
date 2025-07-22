package kotlinx.coroutines.flow

import java.util.concurrent.CancellationException
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.Boxing
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlin.jvm.functions.Function4
import kotlin.jvm.internal.Ref
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.Job
import kotlinx.coroutines.internal.StackTraceRecoveryKt

@JvmSynthetic
internal class FlowKt__ErrorsKt {
   @JvmStatic
   public fun <T> Flow<T>.catch(action: (FlowCollector<T>, Throwable, Continuation<Unit>) -> Any?): Flow<T> {
      return new Flow<T>(var0, var1) {
         final Function3 $action$inlined;
         final Flow $this_catch$inlined;

         {
            this.$this_catch$inlined = var1;
            this.$action$inlined = var2;
         }

         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            label36: {
               if (var2 is <unrepresentable>) {
                  val var4: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var4.label += Integer.MIN_VALUE;
                     var2 = var4;
                     break label36;
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
                     return this.this$0.collect(null, this);
                  }
               };
            }

            var var5: Any = var2.result;
            val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var8: <unrepresentable>;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  if (var2.label != 2) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  ResultKt.throwOnFailure(var5);
                  return Unit.INSTANCE;
               }

               var1 = var2.L$1 as FlowCollector;
               var8 = var2.L$0 as <unrepresentable>;
               ResultKt.throwOnFailure(var5);
            } else {
               ResultKt.throwOnFailure(var5);
               val var9: Continuation = var2;
               val var10: Flow = this.$this_catch$inlined;
               var2.L$0 = this;
               var2.L$1 = var1;
               var2.label = 1;
               var5 = FlowKt.catchImpl(var10, var1, var2);
               if (var5 === var6) {
                  return var6;
               }

               var8 = this;
            }

            var5 = var5 as java.lang.Throwable;
            if (var5 as java.lang.Throwable != null) {
               val var11: Function3 = var8.$action$inlined;
               var2.L$0 = null;
               var2.L$1 = null;
               var2.label = 2;
               if (var11.invoke(var1, var5, var2) === var6) {
                  return var6;
               }
            }

            return Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   internal suspend fun <T> Flow<T>.catchImpl(collector: FlowCollector<T>): Throwable? {
      label59: {
         if (var2 is <unrepresentable>) {
            val var4: <unrepresentable> = var2 as <unrepresentable>;
            if ((var2.label and Integer.MIN_VALUE) != 0) {
               var4.label += Integer.MIN_VALUE;
               var2 = var4;
               break label59;
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
               return FlowKt.catchImpl(null, null, this);
            }
         };
      }

      var var15: Any = var2.result;
      val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      if (var2.label != 0) {
         if (var2.label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var1 = var2.L$0 as Ref.ObjectRef;

         label46:
         try {
            ResultKt.throwOnFailure(var15);
            return null;
         } catch (var7: java.lang.Throwable) {
            var0 = var7;
            break label46;
         }
      } else {
         label135: {
            ResultKt.throwOnFailure(var15);
            var15 = new Ref.ObjectRef();

            try {
               var1 = new FlowCollector((FlowCollector)var1, (Ref.ObjectRef<java.lang.Throwable>)var15) {
                  final FlowCollector<T> $collector;
                  final Ref.ObjectRef<java.lang.Throwable> $fromDownstream;

                  {
                     this.$collector = var1;
                     this.$fromDownstream = var2;
                  }

                  // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
                  // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
                  @Override
                  public final Object emit(T var1, Continuation<? super Unit> var2) {
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

                     val var5: Any = var2.result;
                     var var14: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     if (var2.label != 0) {
                        if (var2.label != 1) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        var14 = var2.L$0 as <unrepresentable>;

                        label32:
                        try {
                           ResultKt.throwOnFailure(var5);
                           return Unit.INSTANCE;
                        } catch (var6: java.lang.Throwable) {
                           var2 = var6;
                           break label32;
                        }
                     } else {
                        label113: {
                           ResultKt.throwOnFailure(var5);

                           try {
                              ;
                           } catch (var7: java.lang.Throwable) {
                              var14 = this;
                              break label113;
                           }

                           if (var1 === var14) {
                              return var14;
                           }

                           return Unit.INSTANCE;
                        }
                     }

                     ((<unrepresentable>)var14).$fromDownstream.element = var2;
                     throw var2;
                  }
               };
               var2.L$0 = var15;
               var2.label = 1;
               var0 = ((Flow)var0).collect((FlowCollector)var1, var2);
            } catch (var8: java.lang.Throwable) {
               break label135;
            }

            if (var0 === var5) {
               return var5;
            }

            return null;
         }
      }

      val var13: java.lang.Throwable = ((Ref.ObjectRef)var1).element as java.lang.Throwable;
      if (isSameExceptionAs$FlowKt__ErrorsKt((java.lang.Throwable)var0, ((Ref.ObjectRef)var1).element as java.lang.Throwable)
         || isCancellationCause$FlowKt__ErrorsKt((java.lang.Throwable)var0, var2.getContext())) {
         throw var0;
      } else if (var13 == null) {
         return var0;
      } else if (var0 is CancellationException) {
         ExceptionsKt.addSuppressed(var13, (java.lang.Throwable)var0);
         throw var13;
      } else {
         ExceptionsKt.addSuppressed((java.lang.Throwable)var0, var13);
         throw var0;
      }
   }

   @JvmStatic
   private fun Throwable.isCancellationCause(coroutineContext: CoroutineContext): Boolean {
      val var2: Job = var1.get(Job.Key);
      return var2 != null && var2.isCancelled() && isSameExceptionAs$FlowKt__ErrorsKt(var0, var2.getCancellationException());
   }

   @JvmStatic
   private fun Throwable.isSameExceptionAs(other: Throwable?): Boolean {
      if (var1 != null) {
         if (DebugKt.getRECOVER_STACK_TRACES()) {
            var1 = StackTraceRecoveryKt.unwrapImpl(var1);
         }

         if (DebugKt.getRECOVER_STACK_TRACES()) {
            var0 = StackTraceRecoveryKt.unwrapImpl(var0);
         }

         if (var1 == var0) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public fun <T> Flow<T>.retry(
      retries: Long = java.lang.Long.MAX_VALUE,
      predicate: (Throwable, Continuation<Boolean>) -> Any? = (new Function2<java.lang.Throwable, Continuation<? super java.lang.Boolean>, Object>(null) {
            int label;

            {
               super(2, var1);
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               return new <anonymous constructor>(var2);
            }

            public final Object invoke(java.lang.Throwable var1, Continuation<? super java.lang.Boolean> var2x) {
               return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               IntrinsicsKt.getCOROUTINE_SUSPENDED();
               if (this.label == 0) {
                  ResultKt.throwOnFailure(var1);
                  return Boxing.boxBoolean(true);
               } else {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }
            }
         }) as Function2
   ): Flow<T> {
      if (var1 > 0L) {
         return FlowKt.retryWhen(
            var0,
            (new Function4<FlowCollector<? super T>, java.lang.Throwable, java.lang.Long, Continuation<? super java.lang.Boolean>, Object>(var1, var3, null) {
               final Function2<java.lang.Throwable, Continuation<? super java.lang.Boolean>, Object> $predicate;
               final long $retries;
               long J$0;
               Object L$0;
               int label;

               {
                  super(4, var4);
                  this.$retries = var1;
                  this.$predicate = var3x;
               }

               public final Object invoke(FlowCollector<? super T> var1, java.lang.Throwable var2, long var3, Continuation<? super java.lang.Boolean> var5) {
                  val var6: Function4 = new <anonymous constructor>(this.$retries, this.$predicate, var5);
                  var6.L$0 = var2;
                  var6.J$0 = var3;
                  return var6.invokeSuspend(Unit.INSTANCE);
               }

               @Override
               public final Object invokeSuspend(Object var1) {
                  label24: {
                     val var5x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     if (this.label != 0) {
                        if (this.label != 1) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var1);
                     } else {
                        ResultKt.throwOnFailure(var1);
                        var var4: java.lang.Throwable = this.L$0 as java.lang.Throwable;
                        if (this.J$0 >= this.$retries) {
                           break label24;
                        }

                        var1 = this.$predicate;
                        this.label = 1;
                        var4 = (java.lang.Throwable)var1.invoke(var4, this);
                        var1 = var4;
                        if (var4 === var5x) {
                           return var5x;
                        }
                     }

                     if (var1 as java.lang.Boolean) {
                        return Boxing.boxBoolean(true);
                     }
                  }

                  return Boxing.boxBoolean(false);
               }
            }) as Function4
         );
      } else {
         val var4: StringBuilder = new StringBuilder("Expected positive amount of retries, but had ");
         var4.append(var1);
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   @JvmStatic
   public fun <T> Flow<T>.retryWhen(predicate: (FlowCollector<T>, Throwable, Long, Continuation<Boolean>) -> Any?): Flow<T> {
      return new Flow<T>(var0, var1) {
         final Function4 $predicate$inlined;
         final Flow $this_retryWhen$inlined;

         {
            this.$this_retryWhen$inlined = var1;
            this.$predicate$inlined = var2;
         }

         // $VF: Irreducible bytecode was duplicated to produce valid code
         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            label37: {
               if (var2 is <unrepresentable>) {
                  val var6: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var6.label += Integer.MIN_VALUE;
                     var2 = var6;
                     break label37;
                  }
               }

               var2 = new ContinuationImpl(this, var2) {
                  int I$0;
                  long J$0;
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

            var var7: Function4 = (Function4)var2.result;
            val var9: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            var var4: Long;
            val var18: <unrepresentable>;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  if (var2.label != 2) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  var4 = var2.J$0;
                  val var8: java.lang.Throwable = var2.L$2 as java.lang.Throwable;
                  var1 = var2.L$1 as FlowCollector;
                  var18 = var2.L$0 as <unrepresentable>;
                  ResultKt.throwOnFailure(var7);
                  if (!var7 as java.lang.Boolean) {
                     throw var8;
                  }

                  var4++;
                  if (false) {
                     return Unit.INSTANCE;
                  }
               } else {
                  val var13: Int = var2.I$0;
                  var4 = var2.J$0;
                  var1 = var2.L$1 as FlowCollector;
                  var18 = var2.L$0 as <unrepresentable>;
                  ResultKt.throwOnFailure(var7);
                  val var26: java.lang.Throwable = var7 as java.lang.Throwable;
                  if (var7 as java.lang.Throwable != null) {
                     var7 = var18.$predicate$inlined;
                     val var10: java.lang.Long = Boxing.boxLong(var4);
                     var2.L$0 = var18;
                     var2.L$1 = var1;
                     var2.L$2 = var26;
                     var2.J$0 = var4;
                     var2.label = 2;
                     var7 = (Function4)var7.invoke(var1, var26, var10, var2);
                     if (var7 === var9) {
                        return var9;
                     }

                     if (!var7 as java.lang.Boolean) {
                        throw var26;
                     }

                     var4++;
                     if (false) {
                        return Unit.INSTANCE;
                     }
                  } else if (var13 == 0) {
                     return Unit.INSTANCE;
                  }
               }
            } else {
               ResultKt.throwOnFailure(var7);
               val var19: Continuation = var2;
               var4 = 0L;
               var18 = this;
            }

            while (true) {
               val var22: Flow = var18.$this_retryWhen$inlined;
               var2.L$0 = var18;
               var2.L$1 = var1;
               var2.L$2 = null;
               var2.J$0 = var4;
               var2.I$0 = 0;
               var2.label = 1;
               var7 = (Function4)FlowKt.catchImpl(var22, var1, var2);
               if (var7 === var9) {
                  return var9;
               }

               val var27: java.lang.Throwable = var7 as java.lang.Throwable;
               if (var7 as java.lang.Throwable != null) {
                  var7 = var18.$predicate$inlined;
                  val var28: java.lang.Long = Boxing.boxLong(var4);
                  var2.L$0 = var18;
                  var2.L$1 = var1;
                  var2.L$2 = var27;
                  var2.J$0 = var4;
                  var2.label = 2;
                  var7 = (Function4)var7.invoke(var1, var27, var28, var2);
                  if (var7 === var9) {
                     return var9;
                  }

                  if (!var7 as java.lang.Boolean) {
                     throw var27;
                  }

                  var4++;
                  if (false) {
                     return Unit.INSTANCE;
                  }
               } else if (true) {
                  return Unit.INSTANCE;
               }
            }
         }
      };
   }
}
