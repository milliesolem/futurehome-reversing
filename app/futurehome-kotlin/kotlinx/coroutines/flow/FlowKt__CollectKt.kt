package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.Boxing
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlinx.coroutines.BuildersKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.internal.NopCollector

@JvmSynthetic
internal class FlowKt__CollectKt {
   @JvmStatic
   public suspend fun Flow<*>.collect() {
      val var2: Any = var0.collect(NopCollector.INSTANCE, var1);
      return if (var2 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var2 else Unit.INSTANCE;
   }

   @JvmStatic
   public suspend inline fun <T> Flow<T>.collectIndexed(crossinline action: (Int, T, Continuation<Unit>) -> Any?) {
      val var3: Any = var0.collect(new FlowCollector<T>(var1) {
         final Function3<Integer, T, Continuation<? super Unit>, Object> $action;
         private int index;

         {
            this.$action = var1;
         }

         @Override
         public Object emit(T var1, Continuation<? super Unit> var2) {
            val var4: Function3 = this.$action;
            val var3: Int = this.index++;
            if (var3 >= 0) {
               var1 = var4.invoke(Boxing.boxInt(var3), var1, var2);
               return if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var1 else Unit.INSTANCE;
            } else {
               throw new ArithmeticException("Index overflow has happened");
            }
         }

         public Object emit$$forInline(T var1, Continuation<? super Unit> var2) {
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
                  return this.this$0.emit(null, this);
               }
            };
            val var4: Function3 = this.$action;
            val var3: Int = this.index++;
            if (var3 >= 0) {
               var4.invoke(var3, var1, var2);
               return Unit.INSTANCE;
            } else {
               throw new ArithmeticException("Index overflow has happened");
            }
         }
      }, var2);
      return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
   }

   @JvmStatic
   fun <T> `collectIndexed$$forInline`(var0: Flow<? extends T>, var1: (Int?, T?, Continuation<? super Unit>?) -> Any, var2: Continuation<? super Unit>): Any {
      var0.collect(new FlowCollector<T>(var1) {
         final Function3<Integer, T, Continuation<? super Unit>, Object> $action;
         private int index;

         {
            this.$action = var1;
         }

         @Override
         public Object emit(T var1, Continuation<? super Unit> var2) {
            val var4: Function3 = this.$action;
            val var3: Int = this.index++;
            if (var3 >= 0) {
               var1 = var4.invoke(Boxing.boxInt(var3), var1, var2);
               return if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var1 else Unit.INSTANCE;
            } else {
               throw new ArithmeticException("Index overflow has happened");
            }
         }

         public Object emit$$forInline(T var1, Continuation<? super Unit> var2) {
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
                  return this.this$0.emit(null, this as Continuation<? super Unit>);
               }
            };
            val var4: Function3 = this.$action;
            val var3: Int = this.index++;
            if (var3 >= 0) {
               var4.invoke(var3, var1, var2);
               return Unit.INSTANCE;
            } else {
               throw new ArithmeticException("Index overflow has happened");
            }
         }
      }, var2);
      return Unit.INSTANCE;
   }

   @JvmStatic
   public suspend fun <T> Flow<T>.collectLatest(action: (T, Continuation<Unit>) -> Any?) {
      val var3: Any = FlowKt.collect(FlowKt.buffer$default(FlowKt.mapLatest(var0, var1), 0, null, 2, null), var2);
      return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
   }

   @JvmStatic
   public suspend fun <T> FlowCollector<T>.emitAll(flow: Flow<T>) {
      FlowKt.ensureActive(var0);
      val var3: Any = var1.collect(var0, var2);
      return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
   }

   @JvmStatic
   public fun <T> Flow<T>.launchIn(scope: CoroutineScope): Job {
      return BuildersKt.launch$default(var1, null, null, (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var0, null) {
         final Flow<T> $this_launchIn;
         int label;

         {
            super(2, var2x);
            this.$this_launchIn = var1;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            return new <anonymous constructor>(this.$this_launchIn, var2);
         }

         public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var3: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               ResultKt.throwOnFailure(var1);
            } else {
               ResultKt.throwOnFailure(var1);
               val var4: Flow = this.$this_launchIn;
               var1 = this;
               this.label = 1;
               if (FlowKt.collect(var4, var1) === var3) {
                  return var3;
               }
            }

            return Unit.INSTANCE;
         }
      }) as Function2, 3, null);
   }
}
