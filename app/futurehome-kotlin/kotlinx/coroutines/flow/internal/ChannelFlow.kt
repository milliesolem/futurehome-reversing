package kotlinx.coroutines.flow.internal

import java.util.ArrayList
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.DebugStringsKt
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.ProduceKt
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

public abstract class ChannelFlow<T> : FusibleFlow<T> {
   public final val capacity: Int

   internal final val collectToFun: (ProducerScope<Any>, Continuation<Unit>) -> Any?
      internal final get() {
         return (new Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object>(this, null) {
            Object L$0;
            int label;
            final ChannelFlow<T> this$0;

            {
               super(2, var2x);
               this.this$0 = var1;
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               val var3: Function2 = new <anonymous constructor>(this.this$0, var2);
               var3.L$0 = var1;
               return var3 as Continuation<Unit>;
            }

            public final Object invoke(ProducerScope<? super T> var1, Continuation<? super Unit> var2x) {
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
                  var1 = this.L$0 as ProducerScope;
                  val var5: ChannelFlow = this.this$0;
                  val var4: Continuation = this;
                  this.label = 1;
                  if (var5.collectTo(var1, var4) === var3x) {
                     return var3x;
                  }
               }

               return Unit.INSTANCE;
            }
         }) as (ProducerScope<? super T>?, Continuation<? super Unit>?) -> Any;
      }


   public final val context: CoroutineContext
   public final val onBufferOverflow: BufferOverflow

   internal final val produceCapacity: Int
      internal final get() {
         var var1: Int = this.capacity;
         if (this.capacity == -3) {
            var1 = -2;
         }

         return var1;
      }


   open fun ChannelFlow(var1: CoroutineContext, var2: Int, var3: BufferOverflow) {
      this.context = var1;
      this.capacity = var2;
      this.onBufferOverflow = var3;
      if (DebugKt.getASSERTIONS_ENABLED() && var2 == -1) {
         throw new AssertionError();
      }
   }

   protected open fun additionalToStringProps(): String? {
      return null;
   }

   public override suspend fun collect(collector: FlowCollector<Any>) {
      return collect$suspendImpl(this, var1, var2);
   }

   protected abstract suspend fun collectTo(scope: ProducerScope<Any>) {
   }

   protected abstract fun create(context: CoroutineContext, capacity: Int, onBufferOverflow: BufferOverflow): ChannelFlow<Any> {
   }

   public open fun dropChannelOperators(): Flow<Any>? {
      return null;
   }

   public override fun fuse(context: CoroutineContext, capacity: Int, onBufferOverflow: BufferOverflow): Flow<Any> {
      if (DebugKt.getASSERTIONS_ENABLED() && var2 == -1) {
         throw new AssertionError();
      } else {
         var1 = var1.plus(this.context);
         if (var3 === BufferOverflow.SUSPEND) {
            if (this.capacity != -3) {
               if (var2 == -3) {
                  var2 = this.capacity;
               } else if (this.capacity != -2) {
                  if (var2 == -2) {
                     var2 = this.capacity;
                  } else {
                     if (DebugKt.getASSERTIONS_ENABLED() && this.capacity < 0) {
                        throw new AssertionError();
                     }

                     if (DebugKt.getASSERTIONS_ENABLED() && var2 < 0) {
                        throw new AssertionError();
                     }

                     var2 += this.capacity;
                     if (var2 < 0) {
                        var2 = Integer.MAX_VALUE;
                     }
                  }
               }
            }

            var3 = this.onBufferOverflow;
         }

         return if (var1 == this.context && var2 == this.capacity && var3 === this.onBufferOverflow) this else this.create(var1, var2, var3);
      }
   }

   public open fun produceImpl(scope: CoroutineScope): ReceiveChannel<Any> {
      return ProduceKt.produce$default(
         var1,
         this.context,
         this.getProduceCapacity$kotlinx_coroutines_core(),
         this.onBufferOverflow,
         CoroutineStart.ATOMIC,
         null,
         this.getCollectToFun$kotlinx_coroutines_core(),
         16,
         null
      );
   }

   public override fun toString(): String {
      val var1: ArrayList = new ArrayList(4);
      val var2: java.lang.String = this.additionalToStringProps();
      if (var2 != null) {
         var1.add(var2);
      }

      if (this.context != EmptyCoroutineContext.INSTANCE) {
         val var3: StringBuilder = new StringBuilder("context=");
         var3.append(this.context);
         var1.add(var3.toString());
      }

      if (this.capacity != -3) {
         val var4: StringBuilder = new StringBuilder("capacity=");
         var4.append(this.capacity);
         var1.add(var4.toString());
      }

      if (this.onBufferOverflow != BufferOverflow.SUSPEND) {
         val var5: StringBuilder = new StringBuilder("onBufferOverflow=");
         var5.append(this.onBufferOverflow);
         var1.add(var5.toString());
      }

      val var6: StringBuilder = new StringBuilder();
      var6.append(DebugStringsKt.getClassSimpleName(this));
      var6.append('[');
      var6.append(CollectionsKt.joinToString$default(var1, ", ", null, null, 0, null, null, 62, null));
      var6.append(']');
      return var6.toString();
   }
}
