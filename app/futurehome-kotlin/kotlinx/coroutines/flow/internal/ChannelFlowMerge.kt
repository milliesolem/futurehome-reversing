package kotlinx.coroutines.flow.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.BuildersKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.JobKt
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.ProduceKt
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.SemaphoreKt

internal class ChannelFlowMerge<T>(flow: Flow<Flow<Any>>,
   concurrency: Int,
   context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
   capacity: Int = -2,
   onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND
) : ChannelFlow(var3, var4, var5) {
   private final val concurrency: Int
   private final val flow: Flow<Flow<Any>>

   init {
      this.flow = var1;
      this.concurrency = var2;
   }

   protected override fun additionalToStringProps(): String {
      val var1: StringBuilder = new StringBuilder("concurrency=");
      var1.append(this.concurrency);
      return var1.toString();
   }

   protected override suspend fun collectTo(scope: ProducerScope<Any>) {
      val var6: Any = this.flow
         .collect(
            new FlowCollector(var2.getContext().get(Job.Key), SemaphoreKt.Semaphore$default(this.concurrency, 0, 2, null), var1, new SendingCollector<>(var1)) {
               final SendingCollector<T> $collector;
               final Job $job;
               final ProducerScope<T> $scope;
               final Semaphore $semaphore;

               {
                  this.$job = var1;
                  this.$semaphore = var2;
                  this.$scope = var3;
                  this.$collector = var4;
               }

               public final Object emit(Flow<? extends T> var1, Continuation<? super Unit> var2) {
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

                  var var5: Any = var2.result;
                  val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  val var7: <unrepresentable>;
                  if (var2.label != 0) {
                     if (var2.label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     var1 = var2.L$1 as Flow;
                     var7 = var2.L$0 as <unrepresentable>;
                     ResultKt.throwOnFailure(var5);
                  } else {
                     ResultKt.throwOnFailure(var5);
                     if (this.$job != null) {
                        JobKt.ensureActive(this.$job);
                     }

                     var5 = this.$semaphore;
                     var2.L$0 = this;
                     var2.L$1 = var1;
                     var2.label = 1;
                     if (((Semaphore)var5).acquire(var2) === var8) {
                        return var8;
                     }

                     var7 = this;
                  }

                  BuildersKt.launch$default(
                     var7.$scope,
                     null,
                     null,
                     (
                        new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var1, var7.$collector, var7.$semaphore, null)// $VF: Couldn't be decompiled
            // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
            // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.modules.decompiler.stats.Statement.getVarDefinitions()" because "stat" is null
            //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarDefinitionHelper.iterateClashingNames(VarDefinitionHelper.java:1468)
            //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarDefinitionHelper.iterateClashingExprent(VarDefinitionHelper.java:1679)
            //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarDefinitionHelper.iterateClashingNames(VarDefinitionHelper.java:1496)
            //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarDefinitionHelper.iterateClashingNames(VarDefinitionHelper.java:1545)
            //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarDefinitionHelper.remapClashingNames(VarDefinitionHelper.java:1458)
            //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarProcessor.rerunClashing(VarProcessor.java:99)
            //   at org.jetbrains.java.decompiler.main.ClassWriter.invokeProcessors(ClassWriter.java:118)
            //   at org.jetbrains.java.decompiler.main.ClassWriter.writeClass(ClassWriter.java:352)
            //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.toJava(NewExprent.java:407)
            //   at org.jetbrains.java.decompiler.modules.decompiler.exps.FunctionExprent.wrapOperandString(FunctionExprent.java:761)
            //   at org.jetbrains.java.decompiler.modules.decompiler.exps.FunctionExprent.wrapOperandString(FunctionExprent.java:727)
            
                     ) as Function2,
                     3,
                     null
                  );
                  return Unit.INSTANCE;
               }
            },
            var2
         );
      return if (var6 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var6 else Unit.INSTANCE;
   }

   protected override fun create(context: CoroutineContext, capacity: Int, onBufferOverflow: BufferOverflow): ChannelFlow<Any> {
      return new ChannelFlowMerge<>(this.flow, this.concurrency, var1, var2, var3);
   }

   public override fun produceImpl(scope: CoroutineScope): ReceiveChannel<Any> {
      return ProduceKt.produce(var1, this.context, this.capacity, this.getCollectToFun$kotlinx_coroutines_core());
   }
}
