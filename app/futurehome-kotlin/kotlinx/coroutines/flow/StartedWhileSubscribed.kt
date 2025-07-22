package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.Boxing
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlinx.coroutines.DelayKt

private class StartedWhileSubscribed(stopTimeout: Long, replayExpiration: Long) : SharingStarted {
   private final val replayExpiration: Long
   private final val stopTimeout: Long

   init {
      this.stopTimeout = var1;
      this.replayExpiration = var3;
      if (var1 >= 0L) {
         if (var3 < 0L) {
            val var6: StringBuilder = new StringBuilder("replayExpiration(");
            var6.append(var3);
            var6.append(" ms) cannot be negative");
            throw new IllegalArgumentException(var6.toString().toString());
         }
      } else {
         val var5: StringBuilder = new StringBuilder("stopTimeout(");
         var5.append(var1);
         var5.append(" ms) cannot be negative");
         throw new IllegalArgumentException(var5.toString().toString());
      }
   }

   public override fun command(subscriptionCount: StateFlow<Int>): Flow<SharingCommand> {
      return FlowKt.distinctUntilChanged(
         FlowKt.dropWhile(
            FlowKt.transformLatest(var1, (new Function3<FlowCollector<? super SharingCommand>, Integer, Continuation<? super Unit>, Object>(this, null) {
               int I$0;
               private Object L$0;
               int label;
               final StartedWhileSubscribed this$0;

               {
                  super(3, var2x);
                  this.this$0 = var1;
               }

               public final Object invoke(FlowCollector<? super SharingCommand> var1, int var2, Continuation<? super Unit> var3) {
                  val var4: Function3 = new <anonymous constructor>(this.this$0, var3);
                  var4.L$0 = var1;
                  var4.I$0 = var2;
                  return var4.invokeSuspend(Unit.INSTANCE);
               }

               @Override
               public final Object invokeSuspend(Object var1) {
                  label64: {
                     var var6: Any;
                     var var13: FlowCollector;
                     label53: {
                        label57: {
                           var6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                           if (this.label != 0) {
                              if (this.label == 1) {
                                 break label64;
                              }

                              if (this.label != 2) {
                                 if (this.label != 3) {
                                    if (this.label != 4) {
                                       if (this.label != 5) {
                                          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                       }
                                       break label64;
                                    }

                                    var13 = this.L$0 as FlowCollector;
                                    ResultKt.throwOnFailure(var1);
                                    break label53;
                                 }

                                 var13 = this.L$0 as FlowCollector;
                                 ResultKt.throwOnFailure(var1);
                                 break label57;
                              }

                              var13 = this.L$0 as FlowCollector;
                              ResultKt.throwOnFailure(var1);
                              var1 = var13;
                           } else {
                              ResultKt.throwOnFailure(var1);
                              var1 = this.L$0 as FlowCollector;
                              if (this.I$0 > 0) {
                                 val var17: SharingCommand = SharingCommand.START;
                                 val var15: Continuation = this;
                                 this.label = 1;
                                 if (var1.emit(var17, var15) === var6) {
                                    return var6;
                                 }

                                 return Unit.INSTANCE;
                              }

                              val var3x: Long = StartedWhileSubscribed.access$getStopTimeout$p(this.this$0);
                              val var14: Continuation = this;
                              this.L$0 = var1;
                              this.label = 2;
                              if (DelayKt.delay(var3x, var14) === var6) {
                                 return var6;
                              }
                           }

                           var13 = var1;
                           if (StartedWhileSubscribed.access$getReplayExpiration$p(this.this$0) <= 0L) {
                              break label53;
                           }

                           val var7: SharingCommand = SharingCommand.STOP;
                           val var8: Continuation = this;
                           this.L$0 = var1;
                           this.label = 3;
                           var13 = var1;
                           if (var1.emit(var7, var8) === var6) {
                              return var6;
                           }
                        }

                        val var12: Long = StartedWhileSubscribed.access$getReplayExpiration$p(this.this$0);
                        val var10: Continuation = this;
                        this.L$0 = var13;
                        this.label = 4;
                        if (DelayKt.delay(var12, var10) === var6) {
                           return var6;
                        }
                     }

                     val var11: SharingCommand = SharingCommand.STOP_AND_RESET_REPLAY_CACHE;
                     val var16: Continuation = this;
                     this.L$0 = null;
                     this.label = 5;
                     if (var13.emit(var11, var16) === var6) {
                        return var6;
                     }

                     return Unit.INSTANCE;
                  }

                  ResultKt.throwOnFailure(var1);
                  return Unit.INSTANCE;
               }
            }) as Function3), (new Function2<SharingCommand, Continuation<? super java.lang.Boolean>, Object>(null) {
               Object L$0;
               int label;

               {
                  super(2, var1);
               }

               @Override
               public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                  val var3: Function2 = new <anonymous constructor>(var2);
                  var3.L$0 = var1;
                  return var3 as Continuation<Unit>;
               }

               public final Object invoke(SharingCommand var1, Continuation<? super java.lang.Boolean> var2x) {
                  return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
               }

               @Override
               public final Object invokeSuspend(Object var1) {
                  IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  if (this.label == 0) {
                     ResultKt.throwOnFailure(var1);
                     val var2x: Boolean;
                     if (this.L$0 as SharingCommand != SharingCommand.START) {
                        var2x = true;
                     } else {
                        var2x = false;
                     }

                     return Boxing.boxBoolean(var2x);
                  } else {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }
               }
            }) as (SharingCommand?, Continuation<? super java.lang.Boolean>?) -> Any
         )
      );
   }

   public override operator fun equals(other: Any?): Boolean {
      return var1 is StartedWhileSubscribed
         && this.stopTimeout == (var1 as StartedWhileSubscribed).stopTimeout
         && this.replayExpiration == (var1 as StartedWhileSubscribed).replayExpiration;
   }

   public override fun hashCode(): Int {
      return UByte$$ExternalSyntheticBackport0.m(this.stopTimeout) * 31 + UByte$$ExternalSyntheticBackport0.m(this.replayExpiration);
   }

   public override fun toString(): String {
      var var1: java.util.List = CollectionsKt.createListBuilder(2);
      if (this.stopTimeout > 0L) {
         val var2: StringBuilder = new StringBuilder("stopTimeout=");
         var2.append(this.stopTimeout);
         var2.append("ms");
         var1.add(var2.toString());
      }

      if (this.replayExpiration < java.lang.Long.MAX_VALUE) {
         val var4: StringBuilder = new StringBuilder("replayExpiration=");
         var4.append(this.replayExpiration);
         var4.append("ms");
         var1.add(var4.toString());
      }

      var1 = CollectionsKt.build(var1);
      val var5: StringBuilder = new StringBuilder("SharingStarted.WhileSubscribed(");
      var5.append(CollectionsKt.joinToString$default(var1, null, null, null, 0, null, null, 63, null));
      var5.append(')');
      return var5.toString();
   }
}
