package kotlin.coroutines.intrinsics

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.jvm.internal.BaseContinuationImpl
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.coroutines.jvm.internal.RestrictedContinuationImpl
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.TypeIntrinsics

internal class IntrinsicsKt__IntrinsicsJvmKt {
   open fun IntrinsicsKt__IntrinsicsJvmKt() {
   }

   @JvmStatic
   private inline fun <T> createCoroutineFromSuspendFunction(completion: Continuation<T>, crossinline block: (Continuation<T>) -> Any?): Continuation<Unit> {
      val var2: CoroutineContext = var0.getContext();
      if (var2 === EmptyCoroutineContext.INSTANCE) {
         var0 = new RestrictedContinuationImpl(var0, var1) {
            final Function1<Continuation<? super T>, Object> $block;
            private int label;

            {
               super(var1);
               this.$block = var2;
            }

            @Override
            protected Object invokeSuspend(Object var1) {
               if (this.label != 0) {
                  if (this.label != 1) {
                     throw new IllegalStateException("This coroutine had already completed".toString());
                  }

                  this.label = 2;
                  ResultKt.throwOnFailure(var1);
               } else {
                  this.label = 1;
                  ResultKt.throwOnFailure(var1);
                  var1 = this.$block.invoke(this);
               }

               return var1;
            }
         };
      } else {
         var0 = new ContinuationImpl(var0, var2, var1) {
            final Function1<Continuation<? super T>, Object> $block;
            private int label;

            {
               super(var1, var2);
               this.$block = var3;
            }

            @Override
            protected Object invokeSuspend(Object var1) {
               if (this.label != 0) {
                  if (this.label != 1) {
                     throw new IllegalStateException("This coroutine had already completed".toString());
                  }

                  this.label = 2;
                  ResultKt.throwOnFailure(var1);
               } else {
                  this.label = 1;
                  ResultKt.throwOnFailure(var1);
                  var1 = this.$block.invoke(this);
               }

               return var1;
            }
         };
      }

      return var0;
   }

   @JvmStatic
   public fun <T> ((Continuation<T>) -> Any?).createCoroutineUnintercepted(completion: Continuation<T>): Continuation<Unit> {
      var1 = DebugProbesKt.probeCoroutineCreated(var1);
      val var3: Continuation;
      if (var0 is BaseContinuationImpl) {
         var3 = (var0 as BaseContinuationImpl).create(var1);
      } else {
         val var2: CoroutineContext = var1.getContext();
         if (var2 === EmptyCoroutineContext.INSTANCE) {
            var3 = new RestrictedContinuationImpl(var1, var0) {
               final Function1 $this_createCoroutineUnintercepted$inlined;
               private int label;

               {
                  super(var1);
                  this.$this_createCoroutineUnintercepted$inlined = var2;
               }

               @Override
               protected Object invokeSuspend(Object var1) {
                  if (this.label != 0) {
                     if (this.label != 1) {
                        throw new IllegalStateException("This coroutine had already completed".toString());
                     }

                     this.label = 2;
                     ResultKt.throwOnFailure(var1);
                  } else {
                     this.label = 1;
                     ResultKt.throwOnFailure(var1);
                     var1 = this;
                     var1 = (Continuation)(TypeIntrinsics.beforeCheckcastToFunctionOfArity(this.$this_createCoroutineUnintercepted$inlined, 1) as Function1)
                        .invoke(var1);
                  }

                  return var1;
               }
            };
         } else {
            var3 = new ContinuationImpl(var1, var2, var0) {
               final Function1 $this_createCoroutineUnintercepted$inlined;
               private int label;

               {
                  super(var1, var2);
                  this.$this_createCoroutineUnintercepted$inlined = var3;
               }

               @Override
               protected Object invokeSuspend(Object var1) {
                  if (this.label != 0) {
                     if (this.label != 1) {
                        throw new IllegalStateException("This coroutine had already completed".toString());
                     }

                     this.label = 2;
                     ResultKt.throwOnFailure(var1);
                  } else {
                     this.label = 1;
                     ResultKt.throwOnFailure(var1);
                     var1 = this;
                     var1 = (Continuation)(TypeIntrinsics.beforeCheckcastToFunctionOfArity(this.$this_createCoroutineUnintercepted$inlined, 1) as Function1)
                        .invoke(var1);
                  }

                  return var1;
               }
            };
         }
      }

      return var3;
   }

   @JvmStatic
   public fun <R, T> ((R, Continuation<T>) -> Any?).createCoroutineUnintercepted(receiver: R, completion: Continuation<T>): Continuation<Unit> {
      val var3: Continuation = DebugProbesKt.probeCoroutineCreated(var2);
      val var4: Continuation;
      if (var0 is BaseContinuationImpl) {
         var4 = (var0 as BaseContinuationImpl).create(var1, var3);
      } else {
         val var5: CoroutineContext = var3.getContext();
         if (var5 === EmptyCoroutineContext.INSTANCE) {
            var4 = new RestrictedContinuationImpl(var3, var0, var1) {
               final Object $receiver$inlined;
               final Function2 $this_createCoroutineUnintercepted$inlined;
               private int label;

               {
                  super(var1);
                  this.$this_createCoroutineUnintercepted$inlined = var2;
                  this.$receiver$inlined = var3;
               }

               @Override
               protected Object invokeSuspend(Object var1) {
                  if (this.label != 0) {
                     if (this.label != 1) {
                        throw new IllegalStateException("This coroutine had already completed".toString());
                     }

                     this.label = 2;
                     ResultKt.throwOnFailure(var1);
                  } else {
                     this.label = 1;
                     ResultKt.throwOnFailure(var1);
                     var1 = this;
                     var1 = (Continuation)(TypeIntrinsics.beforeCheckcastToFunctionOfArity(this.$this_createCoroutineUnintercepted$inlined, 2) as Function2)
                        .invoke(this.$receiver$inlined, var1);
                  }

                  return var1;
               }
            };
         } else {
            var4 = new ContinuationImpl(var3, var5, var0, var1) {
               final Object $receiver$inlined;
               final Function2 $this_createCoroutineUnintercepted$inlined;
               private int label;

               {
                  super(var1, var2);
                  this.$this_createCoroutineUnintercepted$inlined = var3;
                  this.$receiver$inlined = var4;
               }

               @Override
               protected Object invokeSuspend(Object var1) {
                  if (this.label != 0) {
                     if (this.label != 1) {
                        throw new IllegalStateException("This coroutine had already completed".toString());
                     }

                     this.label = 2;
                     ResultKt.throwOnFailure(var1);
                  } else {
                     this.label = 1;
                     ResultKt.throwOnFailure(var1);
                     var1 = this;
                     var1 = (Continuation)(TypeIntrinsics.beforeCheckcastToFunctionOfArity(this.$this_createCoroutineUnintercepted$inlined, 2) as Function2)
                        .invoke(this.$receiver$inlined, var1);
                  }

                  return var1;
               }
            };
         }
      }

      return var4;
   }

   @JvmStatic
   private fun <T> createSimpleCoroutineForSuspendFunction(completion: Continuation<T>): Continuation<T> {
      val var1: CoroutineContext = var0.getContext();
      if (var1 === EmptyCoroutineContext.INSTANCE) {
         var0 = new RestrictedContinuationImpl(var0) {
            {
               super(var1);
            }

            @Override
            protected Object invokeSuspend(Object var1) {
               ResultKt.throwOnFailure(var1);
               return var1;
            }
         };
      } else {
         var0 = new ContinuationImpl(var0, var1) {
            {
               super(var1, var2);
            }

            @Override
            protected Object invokeSuspend(Object var1) {
               ResultKt.throwOnFailure(var1);
               return var1;
            }
         };
      }

      return var0;
   }

   @JvmStatic
   public fun <T> Continuation<T>.intercepted(): Continuation<T> {
      val var2: ContinuationImpl;
      if (var0 is ContinuationImpl) {
         var2 = var0 as ContinuationImpl;
      } else {
         var2 = null;
      }

      var var1: Continuation = var0;
      if (var2 != null) {
         var1 = var2.intercepted();
         if (var1 == null) {
            var1 = var0;
         }
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> ((Continuation<T>) -> Any?).startCoroutineUninterceptedOrReturn(completion: Continuation<T>): Any? {
      val var2: Any;
      if (var0 !is BaseContinuationImpl) {
         var2 = IntrinsicsKt.wrapWithContinuationImpl(var0, var1);
      } else {
         var2 = (TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 1) as Function1).invoke(var1);
      }

      return var2;
   }

   @JvmStatic
   public inline fun <R, T> ((R, Continuation<T>) -> Any?).startCoroutineUninterceptedOrReturn(receiver: R, completion: Continuation<T>): Any? {
      val var3: Any;
      if (var0 !is BaseContinuationImpl) {
         var3 = IntrinsicsKt.wrapWithContinuationImpl(var0, var1, var2);
      } else {
         var3 = (TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 2) as Function2).invoke(var1, var2);
      }

      return var3;
   }

   @JvmStatic
   internal inline fun <R, P, T> ((R, P, Continuation<T>) -> Any?).startCoroutineUninterceptedOrReturn(receiver: R, param: P, completion: Continuation<T>): Any? {
      val var4: Any;
      if (var0 !is BaseContinuationImpl) {
         var4 = IntrinsicsKt.wrapWithContinuationImpl(var0, var1, var2, var3);
      } else {
         var4 = (TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 3) as Function3).invoke(var1, var2, var3);
      }

      return var4;
   }

   @JvmStatic
   internal fun <T> ((Continuation<T>) -> Any?).wrapWithContinuationImpl(completion: Continuation<T>): Any? {
      return (TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 1) as Function1)
         .invoke(createSimpleCoroutineForSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(DebugProbesKt.probeCoroutineCreated(var1)));
   }

   @JvmStatic
   internal fun <R, T> ((R, Continuation<T>) -> Any?).wrapWithContinuationImpl(receiver: R, completion: Continuation<T>): Any? {
      return (TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 2) as Function2)
         .invoke(var1, createSimpleCoroutineForSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(DebugProbesKt.probeCoroutineCreated(var2)));
   }

   @JvmStatic
   internal fun <R, P, T> ((R, P, Continuation<T>) -> Any?).wrapWithContinuationImpl(receiver: R, param: P, completion: Continuation<T>): Any? {
      return (TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 3) as Function3)
         .invoke(var1, var2, createSimpleCoroutineForSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(DebugProbesKt.probeCoroutineCreated(var3)));
   }
}
