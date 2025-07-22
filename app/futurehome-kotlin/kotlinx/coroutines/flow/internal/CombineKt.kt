package kotlinx.coroutines.flow.internal

import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlinx.coroutines.BuildersKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelKt
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

internal suspend fun <R, T> FlowCollector<R>.combineInternal(
   flows: Array<out Flow<T>>,
   arrayFactory: () -> Array<T?>?,
   transform: (FlowCollector<R>, Array<T>, Continuation<Unit>) -> Any?
) {
   val var5: Any = FlowCoroutineKt.flowScope(
      (
         new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var1, var2, var3, var0, null) {
            final Function0<T[]> $arrayFactory;
            final Flow<T>[] $flows;
            final FlowCollector<R> $this_combineInternal;
            final Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> $transform;
            int I$0;
            int I$1;
            private Object L$0;
            Object L$1;
            Object L$2;
            int label;

            {
               super(2, var5);
               this.$flows = var1;
               this.$arrayFactory = var2x;
               this.$transform = var3x;
               this.$this_combineInternal = var4;
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               val var3: Function2 = new <anonymous constructor>(this.$flows, this.$arrayFactory, this.$transform, this.$this_combineInternal, var2);
               var3.L$0 = var1;
               return var3 as Continuation<Unit>;
            }

            public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
               return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
            }

            // $VF: Irreducible bytecode was duplicated to produce valid code
            @Override
            public final Object invokeSuspend(Object var1) {
               val var10: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               var var5: Int;
               val var6: Channel;
               val var7: Array<Any>;
               var var15: Int;
               var var63: Int;
               if (this.label != 0) {
                  if (this.label != 1) {
                     if (this.label != 2 && this.label != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                     }

                     val var14: Int = this.I$1;
                     var63 = this.I$0;
                     var var8: ByteArray = this.L$2 as ByteArray;
                     var6 = this.L$1 as Channel;
                     var7 = this.L$0 as Array<Any>;
                     ResultKt.throwOnFailure(var1);
                     var1 = var8;
                     var15 = (byte)(var14 + 1);
                     val var26: Continuation = this;
                     this.L$0 = var7;
                     this.L$1 = var6;
                     this.L$2 = var8;
                     this.I$0 = var63;
                     this.I$1 = var15;
                     this.label = 1;
                     var8 = (byte[])var6.receiveCatching-JP2dKIU(var26);
                     if (var8 === var10) {
                        return var10;
                     }

                     val var9: IndexedValue = ChannelResult.getOrNull-impl(var8) as IndexedValue;
                     if (var9 == null) {
                        return Unit.INSTANCE;
                     }

                     var5 = var9.getIndex();
                     val var46: Any = var7[var5];
                     var7[var5] = var9.getValue();
                     var63 = var63;
                     if (var46 === NullSurrogateKt.UNINITIALIZED) {
                        var63--;
                     }
                  } else {
                     var15 = this.I$1;
                     var63 = this.I$0;
                     var var47: ByteArray = this.L$2 as ByteArray;
                     var6 = this.L$1 as Channel;
                     var7 = this.L$0 as Array<Any>;
                     ResultKt.throwOnFailure(var1);
                     val var29: Any = (var1 as ChannelResult).unbox-impl();
                     var1 = var47;
                     val var48: IndexedValue = ChannelResult.getOrNull-impl(var29) as IndexedValue;
                     if (var48 == null) {
                        return Unit.INSTANCE;
                     }

                     var5 = var48.getIndex();
                     var47 = (byte[])var7[var5];
                     var7[var5] = var48.getValue();
                     var63 = var63;
                     if (var47 === NullSurrogateKt.UNINITIALIZED) {
                        var63--;
                     }
                  }
               } else {
                  ResultKt.throwOnFailure(var1);
                  var var31: CoroutineScope = this.L$0 as CoroutineScope;
                  var63 = this.$flows.length;
                  if (this.$flows.length == 0) {
                     return Unit.INSTANCE;
                  }

                  var7 = new Object[var63];
                  ArraysKt.fill$default(var7, NullSurrogateKt.UNINITIALIZED, 0, 0, 6, null);
                  var6 = ChannelKt.Channel$default(var63, null, null, 6, null);
                  val var13: AtomicInteger = new AtomicInteger(var63);

                  for (int var16 = 0; var16 < var63; var16++) {
                     BuildersKt.launch$default(
                        var31,
                        null,
                        null,
                        (
                           new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this.$flows, var16, var13, var6, null)// $VF: Couldn't be decompiled
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
                  }

                  var1 = new byte[var63];
                  var15 = (byte)(0 + 1);
                  val var32: Continuation = this;
                  this.L$0 = var7;
                  this.L$1 = var6;
                  this.L$2 = var1;
                  this.I$0 = var63;
                  this.I$1 = var15;
                  this.label = 1;
                  var31 = (CoroutineScope)var6.receiveCatching-JP2dKIU(var32);
                  if (var31 === var10) {
                     return var10;
                  }

                  val var50: IndexedValue = ChannelResult.getOrNull-impl(var31) as IndexedValue;
                  if (var50 == null) {
                     return Unit.INSTANCE;
                  }

                  var5 = var50.getIndex();
                  val var51: Any = var7[var5];
                  var7[var5] = var50.getValue();
                  var63 = var63;
                  if (var51 === NullSurrogateKt.UNINITIALIZED) {
                     var63--;
                  }
               }

               while (true) {
                  while (var1[var5] == var15) {
                     if (var63 == 0) {
                        val var35: Array<Any> = this.$arrayFactory.invoke() as Array<Any>;
                        if (var35 == null) {
                           val var52: Function3 = this.$transform;
                           val var36: FlowCollector = this.$this_combineInternal;
                           this.L$0 = var7;
                           this.L$1 = var6;
                           this.L$2 = var1;
                           this.I$0 = var63;
                           this.I$1 = var15;
                           this.label = 2;
                           if (var52.invoke(var36, var7, this) === var10) {
                              return var10;
                           }
                        } else {
                           ArraysKt.copyInto$default(var7, var35, 0, 0, 0, 14, null);
                           val var11: Function3 = this.$transform;
                           val var53: FlowCollector = this.$this_combineInternal;
                           this.L$0 = var7;
                           this.L$1 = var6;
                           this.L$2 = var1;
                           this.I$0 = var63;
                           this.I$1 = var15;
                           this.label = 3;
                           if (var11.invoke(var53, var35, this) === var10) {
                              return var10;
                           }
                        }
                     }

                     var15 = (byte)(var15 + 1);
                     var var37: Continuation = this;
                     this.L$0 = var7;
                     this.L$1 = var6;
                     this.L$2 = var1;
                     this.I$0 = var63;
                     this.I$1 = var15;
                     this.label = 1;
                     var37 = (Continuation)var6.receiveCatching-JP2dKIU(var37);
                     if (var37 === var10) {
                        return var10;
                     }

                     val var54: IndexedValue = ChannelResult.getOrNull-impl(var37) as IndexedValue;
                     val var23: Int = var63;
                     if (var54 == null) {
                        return Unit.INSTANCE;
                     }

                     var5 = var54.getIndex();
                     val var55: Any = var7[var5];
                     var7[var5] = var54.getValue();
                     var63 = var63;
                     if (var55 === NullSurrogateKt.UNINITIALIZED) {
                        var63 = var23 - 1;
                     }
                  }

                  var1[var5] = (byte)var15;
                  var var56: IndexedValue = ChannelResult.getOrNull-impl(var6.tryReceive-PtdJZtk()) as IndexedValue;
                  var var24: Int = var63;
                  if (var56 == null) {
                     if (var63 == 0) {
                        val var41: Array<Any> = this.$arrayFactory.invoke() as Array<Any>;
                        if (var41 == null) {
                           val var57: Function3 = this.$transform;
                           val var42: FlowCollector = this.$this_combineInternal;
                           this.L$0 = var7;
                           this.L$1 = var6;
                           this.L$2 = var1;
                           this.I$0 = var63;
                           this.I$1 = var15;
                           this.label = 2;
                           if (var57.invoke(var42, var7, this) === var10) {
                              return var10;
                           }
                        } else {
                           ArraysKt.copyInto$default(var7, var41, 0, 0, 0, 14, null);
                           val var62: Function3 = this.$transform;
                           val var58: FlowCollector = this.$this_combineInternal;
                           this.L$0 = var7;
                           this.L$1 = var6;
                           this.L$2 = var1;
                           this.I$0 = var63;
                           this.I$1 = var15;
                           this.label = 3;
                           if (var62.invoke(var58, var41, this) === var10) {
                              return var10;
                           }
                        }
                     }

                     var15 = (byte)(var15 + 1);
                     var var43: Continuation = this;
                     this.L$0 = var7;
                     this.L$1 = var6;
                     this.L$2 = var1;
                     this.I$0 = var63;
                     this.I$1 = var15;
                     this.label = 1;
                     var43 = (Continuation)var6.receiveCatching-JP2dKIU(var43);
                     if (var43 === var10) {
                        return var10;
                     }

                     var56 = ChannelResult.getOrNull-impl(var43) as IndexedValue;
                     var24 = var63;
                     if (var56 == null) {
                        return Unit.INSTANCE;
                     }

                     var5 = var56.getIndex();
                     val var60: Any = var7[var5];
                     var7[var5] = var56.getValue();
                     var63 = var63;
                     if (var60 === NullSurrogateKt.UNINITIALIZED) {
                        var63 = var24 - 1;
                     }
                  } else {
                     var5 = var56.getIndex();
                     val var61: Any = var7[var5];
                     var7[var5] = var56.getValue();
                     var63 = var63;
                     if (var61 === NullSurrogateKt.UNINITIALIZED) {
                        var63 = var24 - 1;
                     }
                  }
               }
            }
         }
      ) as Function2,
      var4
   );
   return if (var5 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var5 else Unit.INSTANCE;
}

internal fun <T1, T2, R> zipImpl(flow: Flow<T1>, flow2: Flow<T2>, transform: (T1, T2, Continuation<R>) -> Any?): Flow<R> {
   return new Flow<R>(var1, var0, var2)// $VF: Couldn't be decompiled
// Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
// java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.modules.decompiler.stats.Statement.getVarDefinitions()" because "stat" is null
//   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarDefinitionHelper.iterateClashingNames(VarDefinitionHelper.java:1468)
//   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarDefinitionHelper.iterateClashingExprent(VarDefinitionHelper.java:1679)
//   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarDefinitionHelper.iterateClashingNames(VarDefinitionHelper.java:1496)
//   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarDefinitionHelper.iterateClashingNames(VarDefinitionHelper.java:1545)
//   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarDefinitionHelper.iterateClashingExprent(VarDefinitionHelper.java:1679)
//   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarDefinitionHelper.iterateClashingNames(VarDefinitionHelper.java:1496)
//   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarDefinitionHelper.iterateClashingNames(VarDefinitionHelper.java:1545)
//   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarDefinitionHelper.remapClashingNames(VarDefinitionHelper.java:1458)
//   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarProcessor.rerunClashing(VarProcessor.java:99)
//   at org.jetbrains.java.decompiler.main.ClassWriter.invokeProcessors(ClassWriter.java:118)
//   at org.jetbrains.java.decompiler.main.ClassWriter.writeClass(ClassWriter.java:352)
//   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.toJava(NewExprent.java:407)
;
}
