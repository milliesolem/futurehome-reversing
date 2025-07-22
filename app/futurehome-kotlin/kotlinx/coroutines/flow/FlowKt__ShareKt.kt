package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.Boxing
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Ref
import kotlinx.coroutines.BuildersKt
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CompletableDeferredKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.Job
import kotlinx.coroutines.JobKt
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.internal.ChannelFlow
import kotlinx.coroutines.internal.Symbol

@JvmSynthetic
internal class FlowKt__ShareKt {
   @JvmStatic
   public fun <T> MutableSharedFlow<T>.asSharedFlow(): SharedFlow<T> {
      return new ReadonlySharedFlow(var0, null);
   }

   @JvmStatic
   public fun <T> MutableStateFlow<T>.asStateFlow(): StateFlow<T> {
      return new ReadonlyStateFlow(var0, null);
   }

   @JvmStatic
   private fun <T> Flow<T>.configureSharing(replay: Int): SharingConfig<T> {
      if (DebugKt.getASSERTIONS_ENABLED() && var1 < 0) {
         throw new AssertionError();
      } else {
         val var2: Int = RangesKt.coerceAtLeast(var1, Channel.Factory.getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core()) - var1;
         if (var0 is ChannelFlow) {
            val var5: ChannelFlow = var0 as ChannelFlow;
            val var4: Flow = (var0 as ChannelFlow).dropChannelOperators();
            if (var4 != null) {
               if (var5.capacity != -3 && var5.capacity != -2 && var5.capacity != 0) {
                  var1 = var5.capacity;
               } else {
                  if (var5.onBufferOverflow === BufferOverflow.SUSPEND) {
                     if (var5.capacity != 0) {
                        return new SharingConfig(var4, var2, var5.onBufferOverflow, var5.context);
                     }
                  } else if (var1 == 0) {
                     return new SharingConfig(var4, 1, var5.onBufferOverflow, var5.context);
                  }

                  var1 = 0;
               }

               return new SharingConfig(var4, var1, var5.onBufferOverflow, var5.context);
            }
         }

         return new SharingConfig(var0, var2, BufferOverflow.SUSPEND, EmptyCoroutineContext.INSTANCE);
      }
   }

   @JvmStatic
   private fun <T> CoroutineScope.launchSharing(
      context: CoroutineContext,
      upstream: Flow<T>,
      shared: MutableSharedFlow<T>,
      started: SharingStarted,
      initialValue: T
   ): Job {
      val var6: CoroutineStart;
      if (var4 == SharingStarted.Companion.getEagerly()) {
         var6 = CoroutineStart.DEFAULT;
      } else {
         var6 = CoroutineStart.UNDISPATCHED;
      }

      return BuildersKt.launch(
         var0,
         var1,
         var6,
         (
            new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var4, var2, var3, var5, null) {
               final T $initialValue;
               final MutableSharedFlow<T> $shared;
               final SharingStarted $started;
               final Flow<T> $upstream;
               int label;

               {
                  super(2, var5);
                  this.$started = var1;
                  this.$upstream = var2x;
                  this.$shared = var3;
                  this.$initialValue = (T)var4;
               }

               @Override
               public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                  return new <anonymous constructor>(this.$started, this.$upstream, this.$shared, this.$initialValue, var2);
               }

               public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
                  return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
               }

               @Override
               public final Object invokeSuspend(Object var1) {
                  label53: {
                     val var3: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     if (this.label != 0) {
                        if (this.label == 1) {
                           break label53;
                        }

                        if (this.label != 2) {
                           if (this.label != 3 && this.label != 4) {
                              throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                           }
                           break label53;
                        }

                        ResultKt.throwOnFailure(var1);
                     } else {
                        ResultKt.throwOnFailure(var1);
                        if (this.$started === SharingStarted.Companion.getEagerly()) {
                           var1 = this.$upstream;
                           val var12: FlowCollector = this.$shared;
                           val var15: Continuation = this;
                           this.label = 1;
                           if (var1.collect(var12, var15) === var3) {
                              return var3;
                           }

                           return Unit.INSTANCE;
                        }

                        if (this.$started != SharingStarted.Companion.getLazily()) {
                           var1 = FlowKt.distinctUntilChanged(this.$started.command(this.$shared.getSubscriptionCount()));
                           val var14: Function2 = (
                              new Function2<SharingCommand, Continuation<? super Unit>, Object>(this.$upstream, this.$shared, this.$initialValue, null) {
                                 final T $initialValue;
                                 final MutableSharedFlow<T> $shared;
                                 final Flow<T> $upstream;
                                 Object L$0;
                                 int label;

                                 {
                                    super(2, var4);
                                    this.$upstream = var1;
                                    this.$shared = var2x;
                                    this.$initialValue = (T)var3x;
                                 }

                                 @Override
                                 public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                                    val var3x: Function2 = new <anonymous constructor>(this.$upstream, this.$shared, this.$initialValue, var2);
                                    var3x.L$0 = var1;
                                    return var3x as Continuation<Unit>;
                                 }

                                 public final Object invoke(SharingCommand var1, Continuation<? super Unit> var2x) {
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
                                       val var8: Int = WhenMappings.$EnumSwitchMapping$0[(this.L$0 as SharingCommand).ordinal()];
                                       if (var8 != 1) {
                                          if (var8 == 3) {
                                             if (this.$initialValue === SharedFlowKt.NO_VALUE) {
                                                this.$shared.resetReplayCache();
                                             } else {
                                                this.$shared.tryEmit(this.$initialValue);
                                             }
                                          }
                                       } else {
                                          val var5: Flow = this.$upstream;
                                          var1 = this.$shared;
                                          val var4: Continuation = this;
                                          this.label = 1;
                                          if (var5.collect(var1, var4) === var3x) {
                                             return var3x;
                                          }
                                       }
                                    }

                                    return Unit.INSTANCE;
                                 }
                              }
                           ) as Function2;
                           val var11: Continuation = this;
                           this.label = 4;
                           if (FlowKt.collectLatest(var1, var14, var11) === var3) {
                              return var3;
                           }

                           return Unit.INSTANCE;
                        }

                        var1 = this.$shared.getSubscriptionCount();
                        val var4: Function2 = (new Function2<Integer, Continuation<? super java.lang.Boolean>, Object>(null) {
                           int I$0;
                           int label;

                           {
                              super(2, var1);
                           }

                           @Override
                           public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                              val var3: Function2 = new <anonymous constructor>(var2);
                              var3.I$0 = (var1 as java.lang.Number).intValue();
                              return var3 as Continuation<Unit>;
                           }

                           public final Object invoke(int var1, Continuation<? super java.lang.Boolean> var2x) {
                              return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                           }

                           @Override
                           public final Object invokeSuspend(Object var1) {
                              IntrinsicsKt.getCOROUTINE_SUSPENDED();
                              if (this.label == 0) {
                                 ResultKt.throwOnFailure(var1);
                                 val var2x: Boolean;
                                 if (this.I$0 > 0) {
                                    var2x = true;
                                 } else {
                                    var2x = false;
                                 }

                                 return Boxing.boxBoolean(var2x);
                              } else {
                                 throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                              }
                           }
                        }) as Function2;
                        val var5: Continuation = this;
                        this.label = 2;
                        if (FlowKt.first(var1, var4, var5) === var3) {
                           return var3;
                        }
                     }

                     val var10: Flow = this.$upstream;
                     val var7: FlowCollector = this.$shared;
                     val var13: Continuation = this;
                     this.label = 3;
                     if (var10.collect(var7, var13) === var3) {
                        return var3;
                     }

                     return Unit.INSTANCE;
                  }

                  ResultKt.throwOnFailure(var1);
                  return Unit.INSTANCE;
               }
            }
         ) as (CoroutineScope?, Continuation<? super Unit>?) -> Any
      );
   }

   @JvmStatic
   private fun <T> CoroutineScope.launchSharingDeferred(context: CoroutineContext, upstream: Flow<T>, result: CompletableDeferred<StateFlow<T>>) {
      BuildersKt.launch$default(
         var0,
         var1,
         null,
         (
            new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var2, var3, null) {
               final CompletableDeferred<StateFlow<T>> $result;
               final Flow<T> $upstream;
               private Object L$0;
               int label;

               {
                  super(2, var3x);
                  this.$upstream = var1;
                  this.$result = var2x;
               }

               @Override
               public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                  val var3: Function2 = new <anonymous constructor>(this.$upstream, this.$result, var2);
                  var3.L$0 = var1;
                  return var3 as Continuation<Unit>;
               }

               public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
                  return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
               }

               // $VF: Duplicated exception handlers to handle obfuscated exceptions
               // $VF: Could not inline inconsistent finally blocks
               // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
               @Override
               public final Object invokeSuspend(Object var1) {
                  val var3x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (this.label != 0) {
                     if (this.label != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     try {
                        ResultKt.throwOnFailure(var1);
                     } catch (var8: java.lang.Throwable) {
                        this.$result.completeExceptionally(var8);
                        throw var8;
                     }
                  } else {
                     ResultKt.throwOnFailure(var1);
                     val var4: CoroutineScope = this.L$0 as CoroutineScope;

                     try {
                        val var6: Ref.ObjectRef = new Ref.ObjectRef();
                        var1 = this.$upstream;
                        val var15: FlowCollector = new FlowCollector(var6, var4, this.$result) {
                           final CoroutineScope $$this$launch;
                           final CompletableDeferred<StateFlow<T>> $result;
                           final Ref.ObjectRef<MutableStateFlow<T>> $state;

                           {
                              this.$state = var1;
                              this.$$this$launch = var2x;
                              this.$result = var3x;
                           }

                           @Override
                           public final Object emit(T var1, Continuation<? super Unit> var2x) {
                              val var6x: MutableStateFlow = this.$state.element;
                              val var7: Unit;
                              if (this.$state.element as MutableStateFlow != null) {
                                 var6x.setValue(var1);
                                 var7 = Unit.INSTANCE;
                              } else {
                                 var7 = null;
                              }

                              if (var7 == null) {
                                 val var4x: CoroutineScope = this.$$this$launch;
                                 val var8: Ref.ObjectRef = this.$state;
                                 val var3x: CompletableDeferred = this.$result;
                                 var1 = StateFlowKt.MutableStateFlow(var1);
                                 var3x.complete(new ReadonlyStateFlow<>(var1, JobKt.getJob(var4x.getCoroutineContext())));
                                 var8.element = (T)var1;
                              }

                              return Unit.INSTANCE;
                           }
                        };
                        val var16: Continuation = this;
                        this.label = 1;
                        var1 = var1.collect(var15, var16);
                     } catch (var7: java.lang.Throwable) {
                        this.$result.completeExceptionally(var7);
                        throw var7;
                     }

                     if (var1 === var3x) {
                        return var3x;
                     }
                  }

                  return Unit.INSTANCE;
               }
            }
         ) as Function2,
         2,
         null
      );
   }

   @JvmStatic
   public fun <T> SharedFlow<T>.onSubscription(action: (FlowCollector<T>, Continuation<Unit>) -> Any?): SharedFlow<T> {
      return new SubscribedSharedFlow(var0, var1);
   }

   @JvmStatic
   public fun <T> Flow<T>.shareIn(scope: CoroutineScope, started: SharingStarted, replay: Int = 0): SharedFlow<T> {
      val var4: SharingConfig = configureSharing$FlowKt__ShareKt(var0, var3);
      val var5: MutableSharedFlow = SharedFlowKt.MutableSharedFlow(var3, var4.extraBufferCapacity, var4.onBufferOverflow);
      return new ReadonlySharedFlow(var5, launchSharing$FlowKt__ShareKt(var1, var4.context, var4.upstream, var5, var2, (Symbol)SharedFlowKt.NO_VALUE));
   }

   @JvmStatic
   public suspend fun <T> Flow<T>.stateIn(scope: CoroutineScope): StateFlow<T> {
      val var3: SharingConfig = configureSharing$FlowKt__ShareKt(var0, 1);
      val var4: CompletableDeferred = CompletableDeferredKt.CompletableDeferred$default(null, 1, null);
      launchSharingDeferred$FlowKt__ShareKt(var1, var3.context, var3.upstream, var4);
      return var4.await(var2);
   }

   @JvmStatic
   public fun <T> Flow<T>.stateIn(scope: CoroutineScope, started: SharingStarted, initialValue: T): StateFlow<T> {
      val var4: SharingConfig = configureSharing$FlowKt__ShareKt(var0, 1);
      val var5: MutableStateFlow = StateFlowKt.MutableStateFlow(var3);
      return new ReadonlyStateFlow(var5, launchSharing$FlowKt__ShareKt(var1, var4.context, var4.upstream, var5, var2, var3));
   }
}
