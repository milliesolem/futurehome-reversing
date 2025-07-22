package kotlinx.coroutines.flow.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.Ref
import kotlinx.coroutines.BuildersKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineScopeKt
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

internal class ChannelFlowTransformLatest<T, R>(transform: (FlowCollector<Any>, Any, Continuation<Unit>) -> Any?,
   flow: Flow<Any>,
   context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
   capacity: Int = -2,
   onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND
) : ChannelFlowOperator(var2, var3, var4, var5) {
   private final val transform: (FlowCollector<Any>, Any, Continuation<Unit>) -> Any?

   init {
      this.transform = var1;
   }

   protected override fun create(context: CoroutineContext, capacity: Int, onBufferOverflow: BufferOverflow): ChannelFlow<Any> {
      return (new ChannelFlowTransformLatest<>(this.transform, this.flow, var1, var2, var3)) as ChannelFlow<R>;
   }

   protected override suspend fun flowCollect(collector: FlowCollector<Any>) {
      if (DebugKt.getASSERTIONS_ENABLED() && var1 !is SendingCollector) {
         throw new AssertionError();
      } else {
         val var3: Any = CoroutineScopeKt.coroutineScope(
            (
               new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, var1, null) {
                  final FlowCollector<R> $collector;
                  private Object L$0;
                  int label;
                  final ChannelFlowTransformLatest<T, R> this$0;

                  {
                     super(2, var3x);
                     this.this$0 = var1;
                     this.$collector = var2x;
                  }

                  @Override
                  public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                     val var3: Function2 = new <anonymous constructor>(this.this$0, this.$collector, var2);
                     var3.L$0 = var1;
                     return var3 as Continuation<Unit>;
                  }

                  public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
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
                        val var4: CoroutineScope = this.L$0 as CoroutineScope;
                        val var5: Ref.ObjectRef = new Ref.ObjectRef();
                        var1 = this.this$0.flow;
                        val var8: FlowCollector = new FlowCollector(var5, var4, this.this$0, this.$collector) {
                           final CoroutineScope $$this$coroutineScope;
                           final FlowCollector<R> $collector;
                           final Ref.ObjectRef<Job> $previousFlow;
                           final ChannelFlowTransformLatest<T, R> this$0;

                           {
                              this.$previousFlow = var1;
                              this.$$this$coroutineScope = var2x;
                              this.this$0 = var3x;
                              this.$collector = var4x;
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
                                    Object L$0;
                                    Object L$1;
                                    Object L$2;
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

                              var var9: Job = (Job)var2x.result;
                              val var5x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                              val var8x: <unrepresentable>;
                              if (var2x.label != 0) {
                                 if (var2x.label != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                 }

                                 var1 = var2x.L$2 as Job;
                                 var1 = (Job)var2x.L$1;
                                 var8x = var2x.L$0 as <unrepresentable>;
                                 ResultKt.throwOnFailure(var9);
                              } else {
                                 ResultKt.throwOnFailure(var9);
                                 var9 = this.$previousFlow.element;
                                 if (this.$previousFlow.element != null) {
                                    var9.cancel(new ChildCancelledException());
                                    var2x.L$0 = this;
                                    var2x.L$1 = var1;
                                    var2x.L$2 = var9;
                                    var2x.label = 1;
                                    if (var9.join(var2x) === var5x) {
                                       return var5x;
                                    }
                                 }

                                 var8x = this;
                              }

                              var8x.$previousFlow.element = BuildersKt.launch$default(
                                 var8x.$$this$coroutineScope,
                                 null,
                                 CoroutineStart.UNDISPATCHED,
                                 (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var8x.this$0, var8x.$collector, (T)var1, null) {
                                    final FlowCollector<R> $collector;
                                    final T $value;
                                    int label;
                                    final ChannelFlowTransformLatest<T, R> this$0;

                                    {
                                       super(2, var4x);
                                       this.this$0 = var1;
                                       this.$collector = var2x;
                                       this.$value = (T)var3x;
                                    }

                                    @Override
                                    public final Continuation<Unit> create(Object var1, Continuation<?> var2x) {
                                       return new <anonymous constructor>(this.this$0, this.$collector, this.$value, var2x);
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
                                          val var5: Function3 = ChannelFlowTransformLatest.access$getTransform$p(this.this$0);
                                          val var4: FlowCollector = this.$collector;
                                          var1 = this.$value;
                                          this.label = 1;
                                          if (var5.invoke(var4, var1, this) === var3) {
                                             return var3;
                                          }
                                       }

                                       return Unit.INSTANCE;
                                    }
                                 }) as Function2,
                                 1,
                                 null
                              );
                              return Unit.INSTANCE;
                           }
                        };
                        val var7: Continuation = this;
                        this.label = 1;
                        if (var1.collect(var8, var7) === var3x) {
                           return var3x;
                        }
                     }

                     return Unit.INSTANCE;
                  }
               }
            ) as (CoroutineScope?, Continuation<? super R>?) -> Any,
            var2
         );
         return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
      }
   }
}
