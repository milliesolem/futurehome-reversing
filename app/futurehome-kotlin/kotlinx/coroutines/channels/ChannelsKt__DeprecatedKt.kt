package kotlinx.coroutines.channels

import java.util.HashSet
import java.util.LinkedHashSet
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.Boxing
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

@JvmSynthetic
internal class ChannelsKt__DeprecatedKt {
   @JvmStatic
   internal fun ReceiveChannel<*>.consumes(): (Throwable?) -> Unit {
      return (new Function1<java.lang.Throwable, Unit>(var0) {
         final ReceiveChannel<?> $this_consumes;

         {
            super(1);
            this.$this_consumes = var1;
         }

         public final void invoke(java.lang.Throwable var1) {
            ChannelsKt.cancelConsumed(this.$this_consumes, var1);
         }
      }) as (java.lang.Throwable?) -> Unit;
   }

   @JvmStatic
   internal fun consumesAll(vararg channels: ReceiveChannel<*>): (Throwable?) -> Unit {
      return (
         new Function1<java.lang.Throwable, Unit>(var0) {
            final ReceiveChannel<?>[] $channels;

            {
               super(1);
               this.$channels = var1;
            }

            // $VF: Could not inline inconsistent finally blocks
            // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
            public final void invoke(java.lang.Throwable var1) {
               val var6: Array<ReceiveChannel> = this.$channels;
               val var3: Int = this.$channels.length;
               var var4: java.lang.Throwable = null;

               for (int var2 = 0; var2 < var3; var2++) {
                  val var5: ReceiveChannel = var6[var2];

                  try {
                     ChannelsKt.cancelConsumed(var5, var1);
                  } catch (var7: java.lang.Throwable) {
                     if (var4 == null) {
                        var4 = var7;
                     } else {
                        ExceptionsKt.addSuppressed(var4, var7);
                     }
                     continue;
                  }
               }

               if (var4 != null) {
                  throw var4;
               }
            }
         }
      ) as (java.lang.Throwable?) -> Unit;
   }

   @JvmStatic
   internal fun <E, K> ReceiveChannel<E>.distinctBy(
      context: CoroutineContext = Dispatchers.getUnconfined() as CoroutineContext,
      selector: (E, Continuation<K>) -> Any?
   ): ReceiveChannel<E> {
      return ProduceKt.produce$default(
         GlobalScope.INSTANCE,
         var1,
         0,
         null,
         ChannelsKt.consumes(var0),
         (new Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object>(var0, var2, null) {
            final Function2<E, Continuation<? super K>, Object> $selector;
            final ReceiveChannel<E> $this_distinctBy;
            private Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            int label;

            {
               super(2, var3x);
               this.$this_distinctBy = var1;
               this.$selector = var2x;
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               val var3: Function2 = new <anonymous constructor>(this.$this_distinctBy, this.$selector, var2);
               var3.L$0 = var1;
               return var3 as Continuation<Unit>;
            }

            public final Object invoke(ProducerScope<? super E> var1, Continuation<? super Unit> var2x) {
               return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
            }

            // $VF: Irreducible bytecode was duplicated to produce valid code
            @Override
            public final Object invokeSuspend(Object var1) {
               val var10: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               var var16: HashSet;
               var var19: ProducerScope;
               var var22: ChannelIterator;
               if (this.label != 0) {
                  if (this.label != 1) {
                     if (this.label != 2) {
                        if (this.label != 3) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        val var5: Any = this.L$3;
                        val var6: ChannelIterator = this.L$2 as ChannelIterator;
                        var16 = this.L$1 as HashSet;
                        var19 = this.L$0 as ProducerScope;
                        ResultKt.throwOnFailure(var1);
                        var16.add(var5);
                        var16 = var16;
                        var19 = var19;
                        var22 = var6;
                     } else {
                        val var9: Any = this.L$3;
                        var22 = this.L$2 as ChannelIterator;
                        var16 = this.L$1 as HashSet;
                        var19 = this.L$0 as ProducerScope;
                        ResultKt.throwOnFailure(var1);
                        if (!var16.contains(var1)) {
                           val var25: Continuation = this;
                           this.L$0 = var19;
                           this.L$1 = var16;
                           this.L$2 = var22;
                           this.L$3 = var1;
                           this.label = 3;
                           if (var19.send(var9, var25) === var10) {
                              return var10;
                           }

                           var16.add(var1);
                           var16 = var16;
                           var19 = var19;
                           var22 = var22;
                        } else {
                           var16 = var16;
                           var19 = var19;
                           var22 = var22;
                        }
                     }
                  } else {
                     var22 = this.L$2 as ChannelIterator;
                     var16 = this.L$1 as HashSet;
                     var19 = this.L$0 as ProducerScope;
                     ResultKt.throwOnFailure(var1);
                     if (!var1 as java.lang.Boolean) {
                        return Unit.INSTANCE;
                     }

                     val var29: Any = var22.next();
                     var1 = this.$selector;
                     this.L$0 = var19;
                     this.L$1 = var16;
                     this.L$2 = var22;
                     this.L$3 = var29;
                     this.label = 2;
                     var1 = var1.invoke(var29, this);
                     if (var1 === var10) {
                        return var10;
                     }

                     if (!var16.contains(var1)) {
                        val var26: Continuation = this;
                        this.L$0 = var19;
                        this.L$1 = var16;
                        this.L$2 = var22;
                        this.L$3 = var1;
                        this.label = 3;
                        if (var19.send(var29, var26) === var10) {
                           return var10;
                        }

                        var16.add(var1);
                        var16 = var16;
                        var19 = var19;
                        var22 = var22;
                     } else {
                        var16 = var16;
                        var19 = var19;
                        var22 = var22;
                     }
                  }
               } else {
                  ResultKt.throwOnFailure(var1);
                  var19 = this.L$0 as ProducerScope;
                  var16 = new HashSet();
                  var22 = this.$this_distinctBy.iterator();
               }

               while (true) {
                  var1 = this;
                  this.L$0 = var19;
                  this.L$1 = var16;
                  this.L$2 = var22;
                  this.L$3 = null;
                  this.label = 1;
                  var var27: Any = var22.hasNext(var1);
                  if (var27 === var10) {
                     return var10;
                  }

                  if (!var27 as java.lang.Boolean) {
                     return Unit.INSTANCE;
                  }

                  val var30: Any = var22.next();
                  var1 = this.$selector;
                  this.L$0 = var19;
                  this.L$1 = var16;
                  this.L$2 = var22;
                  this.L$3 = var30;
                  this.label = 2;
                  var1 = var1.invoke(var30, this);
                  if (var1 === var10) {
                     return var10;
                  }

                  if (!var16.contains(var1)) {
                     var27 = this;
                     this.L$0 = var19;
                     this.L$1 = var16;
                     this.L$2 = var22;
                     this.L$3 = var1;
                     this.label = 3;
                     if (var19.send(var30, (Continuation<? super Unit>)var27) === var10) {
                        return var10;
                     }

                     var16.add(var1);
                     var16 = var16;
                     var19 = var19;
                     var22 = var22;
                  } else {
                     var16 = var16;
                     var19 = var19;
                     var22 = var22;
                  }
               }
            }
         }) as Function2,
         6,
         null
      );
   }

   @JvmStatic
   internal fun <E> ReceiveChannel<E>.filter(
      context: CoroutineContext = Dispatchers.getUnconfined() as CoroutineContext,
      predicate: (E, Continuation<Boolean>) -> Any?
   ): ReceiveChannel<E> {
      return ProduceKt.produce$default(
         GlobalScope.INSTANCE,
         var1,
         0,
         null,
         ChannelsKt.consumes(var0),
         (new Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object>(var0, var2, null) {
            final Function2<E, Continuation<? super java.lang.Boolean>, Object> $predicate;
            final ReceiveChannel<E> $this_filter;
            private Object L$0;
            Object L$1;
            Object L$2;
            int label;

            {
               super(2, var3x);
               this.$this_filter = var1;
               this.$predicate = var2x;
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               val var3: Function2 = new <anonymous constructor>(this.$this_filter, this.$predicate, var2);
               var3.L$0 = var1;
               return var3 as Continuation<Unit>;
            }

            public final Object invoke(ProducerScope<? super E> var1, Continuation<? super Unit> var2x) {
               return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
            }

            // $VF: Irreducible bytecode was duplicated to produce valid code
            @Override
            public final Object invokeSuspend(Object var1) {
               val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               val var3x: ChannelIterator;
               var var16: ProducerScope;
               if (this.label != 0) {
                  if (this.label != 1) {
                     if (this.label != 2) {
                        if (this.label != 3) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        var3x = this.L$1 as ChannelIterator;
                        var16 = this.L$0 as ProducerScope;
                        ResultKt.throwOnFailure(var1);
                        var1 = var16;
                     } else {
                        val var5: Any = this.L$2;
                        var3x = this.L$1 as ChannelIterator;
                        var16 = this.L$0 as ProducerScope;
                        ResultKt.throwOnFailure(var1);
                        if (var1 as java.lang.Boolean) {
                           var1 = this;
                           this.L$0 = var16;
                           this.L$1 = var3x;
                           this.L$2 = null;
                           this.label = 3;
                           if (var16.send(var5, var1) === var6) {
                              return var6;
                           }
                        }

                        var1 = var16;
                     }

                     val var15: Continuation = this;
                     this.L$0 = var1;
                     this.L$1 = var3x;
                     this.L$2 = null;
                     this.label = 1;
                     val var19: Any = var3x.hasNext(var15);
                     var16 = var1;
                     var1 = (Continuation)var19;
                     if (var19 === var6) {
                        return var6;
                     }
                  } else {
                     var3x = this.L$1 as ChannelIterator;
                     var16 = this.L$0 as ProducerScope;
                     ResultKt.throwOnFailure(var1);
                  }
               } else {
                  ResultKt.throwOnFailure(var1);
                  var1 = this.L$0 as ProducerScope;
                  var3x = this.$this_filter.iterator();
                  val var17: Continuation = this;
                  this.L$0 = var1;
                  this.L$1 = var3x;
                  this.L$2 = null;
                  this.label = 1;
                  val var20: Any = var3x.hasNext(var17);
                  var16 = var1;
                  var1 = (Continuation)var20;
                  if (var20 === var6) {
                     return var6;
                  }
               }

               while ((java.lang.Boolean)var1) {
                  var var21: Any = var3x.next();
                  val var10: Function2 = this.$predicate;
                  this.L$0 = var16;
                  this.L$1 = var3x;
                  this.L$2 = var21;
                  this.label = 2;
                  var1 = var10.invoke(var21, this);
                  if (var1 === var6) {
                     return var6;
                  }

                  if (var1 as java.lang.Boolean) {
                     var1 = this;
                     this.L$0 = var16;
                     this.L$1 = var3x;
                     this.L$2 = null;
                     this.label = 3;
                     if (var16.send(var21, var1) === var6) {
                        return var6;
                     }
                  }

                  val var18: Continuation = this;
                  this.L$0 = var16;
                  this.L$1 = var3x;
                  this.L$2 = null;
                  this.label = 1;
                  var21 = var3x.hasNext(var18);
                  var16 = var16;
                  var1 = (Continuation)var21;
                  if (var21 === var6) {
                     return var6;
                  }
               }

               return Unit.INSTANCE;
            }
         }) as Function2,
         6,
         null
      );
   }

   @JvmStatic
   internal fun <E : Any> ReceiveChannel<E?>.filterNotNull(): ReceiveChannel<E> {
      var0 = ChannelsKt.filter$default(var0, null, (new Function2<E, Continuation<? super java.lang.Boolean>, Object>(null) {
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

         public final Object invoke(E var1, Continuation<? super java.lang.Boolean> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
               ResultKt.throwOnFailure(var1);
               val var2x: Boolean;
               if (this.L$0 != null) {
                  var2x = true;
               } else {
                  var2x = false;
               }

               return Boxing.boxBoolean(var2x);
            } else {
               throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
         }
      }) as Function2, 1, null);
      return var0;
   }

   @JvmStatic
   internal fun <E, R> ReceiveChannel<E>.map(
      context: CoroutineContext = Dispatchers.getUnconfined() as CoroutineContext,
      transform: (E, Continuation<R>) -> Any?
   ): ReceiveChannel<R> {
      return ProduceKt.produce$default(
         GlobalScope.INSTANCE,
         var1,
         0,
         null,
         ChannelsKt.consumes(var0),
         (
            new Function2<ProducerScope<? super R>, Continuation<? super Unit>, Object>(var0, var2, null)// $VF: Couldn't be decompiled
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
         6,
         null
      );
   }

   @JvmStatic
   internal fun <E, R> ReceiveChannel<E>.mapIndexed(
      context: CoroutineContext = Dispatchers.getUnconfined() as CoroutineContext,
      transform: (Int, E, Continuation<R>) -> Any?
   ): ReceiveChannel<R> {
      return ProduceKt.produce$default(
         GlobalScope.INSTANCE,
         var1,
         0,
         null,
         ChannelsKt.consumes(var0),
         (new Function2<ProducerScope<? super R>, Continuation<? super Unit>, Object>(var0, var2, null) {
            final ReceiveChannel<E> $this_mapIndexed;
            final Function3<Integer, E, Continuation<? super R>, Object> $transform;
            int I$0;
            private Object L$0;
            Object L$1;
            Object L$2;
            int label;

            {
               super(2, var3x);
               this.$this_mapIndexed = var1;
               this.$transform = var2x;
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               val var3: Function2 = new <anonymous constructor>(this.$this_mapIndexed, this.$transform, var2);
               var3.L$0 = var1;
               return var3 as Continuation<Unit>;
            }

            public final Object invoke(ProducerScope<? super R> var1, Continuation<? super Unit> var2x) {
               return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
            }

            // $VF: Irreducible bytecode was duplicated to produce valid code
            @Override
            public final Object invokeSuspend(Object var1) {
               val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               var var14: Int;
               var var15: ChannelIterator;
               var var20: ProducerScope;
               var var24: Any;
               if (this.label != 0) {
                  if (this.label != 1) {
                     val var4: ProducerScope;
                     if (this.label != 2) {
                        if (this.label != 3) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        var14 = this.I$0;
                        val var5: ChannelIterator = this.L$1 as ChannelIterator;
                        var4 = this.L$0 as ProducerScope;
                        ResultKt.throwOnFailure(var1);
                        var1 = var5;
                     } else {
                        var14 = this.I$0;
                        var24 = this.L$2 as ProducerScope;
                        val var18: ChannelIterator = this.L$1 as ChannelIterator;
                        var4 = this.L$0 as ProducerScope;
                        ResultKt.throwOnFailure(var1);
                        val var7: Continuation = this;
                        this.L$0 = var4;
                        this.L$1 = var18;
                        this.L$2 = null;
                        this.I$0 = var14;
                        this.label = 3;
                        if (((ProducerScope<Function3>)var24).send(var1, var7) === var8) {
                           return var8;
                        }

                        var1 = var18;
                     }

                     val var19: Continuation = this;
                     this.L$0 = var4;
                     this.L$1 = var1;
                     this.I$0 = var14;
                     this.label = 1;
                     var24 = var1.hasNext(var19);
                     if (var24 === var8) {
                        return var8;
                     }

                     var20 = var4;
                     var15 = var1;
                  } else {
                     var14 = this.I$0;
                     var15 = this.L$1 as ChannelIterator;
                     var20 = this.L$0 as ProducerScope;
                     ResultKt.throwOnFailure(var1);
                     var24 = var1;
                  }
               } else {
                  ResultKt.throwOnFailure(var1);
                  val var16: ProducerScope = this.L$0 as ProducerScope;
                  var1 = this.$this_mapIndexed.iterator();
                  var14 = 0;
                  val var21: Continuation = this;
                  this.L$0 = var16;
                  this.L$1 = var1;
                  this.I$0 = 0;
                  this.label = 1;
                  var24 = var1.hasNext(var21);
                  if (var24 === var8) {
                     return var8;
                  }

                  var20 = var16;
                  var15 = var1;
               }

               while ((java.lang.Boolean)var24) {
                  var var27: Any = var15.next();
                  var1 = this.$transform;
                  val var3x: Int = var14 + 1;
                  var24 = Boxing.boxInt(var14);
                  this.L$0 = var20;
                  this.L$1 = var15;
                  this.L$2 = var20;
                  this.I$0 = var3x;
                  this.label = 2;
                  var1 = var1.invoke(var24, var27, this);
                  if (var1 === var8) {
                     return var8;
                  }

                  var14 = var3x;
                  var27 = this;
                  this.L$0 = var20;
                  this.L$1 = var15;
                  this.L$2 = null;
                  this.I$0 = var3x;
                  this.label = 3;
                  if (var20.send(var1, (Continuation<? super Unit>)var27) === var8) {
                     return var8;
                  }

                  val var23: Continuation = this;
                  this.L$0 = var20;
                  this.L$1 = var15;
                  this.I$0 = var3x;
                  this.label = 1;
                  var24 = var15.hasNext(var23);
                  if (var24 === var8) {
                     return var8;
                  }

                  var20 = var20;
                  var15 = var15;
               }

               return Unit.INSTANCE;
            }
         }) as Function2,
         6,
         null
      );
   }

   @JvmStatic
   internal suspend fun <E, C : SendChannel<E>> ReceiveChannel<E>.toChannel(destination: C): C {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 000: aload 2
      // 001: instanceof kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1
      // 004: ifeq 02b
      // 007: aload 2
      // 008: checkcast kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1
      // 00b: astore 4
      // 00d: aload 4
      // 00f: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.label I
      // 012: ldc -2147483648
      // 014: iand
      // 015: ifeq 02b
      // 018: aload 4
      // 01a: aload 4
      // 01c: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.label I
      // 01f: ldc -2147483648
      // 021: iadd
      // 022: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.label I
      // 025: aload 4
      // 027: astore 2
      // 028: goto 034
      // 02b: new kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1
      // 02e: dup
      // 02f: aload 2
      // 030: invokespecial kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.<init> (Lkotlin/coroutines/Continuation;)V
      // 033: astore 2
      // 034: aload 2
      // 035: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.result Ljava/lang/Object;
      // 038: astore 6
      // 03a: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 03d: astore 9
      // 03f: aload 2
      // 040: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.label I
      // 043: istore 3
      // 044: iload 3
      // 045: ifeq 0ba
      // 048: iload 3
      // 049: bipush 1
      // 04a: if_icmpeq 092
      // 04d: iload 3
      // 04e: bipush 2
      // 04f: if_icmpne 088
      // 052: aload 2
      // 053: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.L$2 Ljava/lang/Object;
      // 056: checkcast kotlinx/coroutines/channels/ChannelIterator
      // 059: astore 8
      // 05b: aload 2
      // 05c: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.L$1 Ljava/lang/Object;
      // 05f: checkcast kotlinx/coroutines/channels/ReceiveChannel
      // 062: astore 5
      // 064: aload 2
      // 065: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.L$0 Ljava/lang/Object;
      // 068: checkcast kotlinx/coroutines/channels/SendChannel
      // 06b: astore 7
      // 06d: aload 5
      // 06f: astore 0
      // 070: aload 6
      // 072: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 075: aload 2
      // 076: astore 6
      // 078: aload 5
      // 07a: astore 0
      // 07b: aload 7
      // 07d: astore 1
      // 07e: aload 6
      // 080: astore 2
      // 081: aload 8
      // 083: astore 5
      // 085: goto 0ca
      // 088: new java/lang/IllegalStateException
      // 08b: dup
      // 08c: ldc "call to 'resume' before 'invoke' with coroutine"
      // 08e: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 091: athrow
      // 092: aload 2
      // 093: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.L$2 Ljava/lang/Object;
      // 096: checkcast kotlinx/coroutines/channels/ChannelIterator
      // 099: astore 5
      // 09b: aload 2
      // 09c: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.L$1 Ljava/lang/Object;
      // 09f: checkcast kotlinx/coroutines/channels/ReceiveChannel
      // 0a2: astore 1
      // 0a3: aload 2
      // 0a4: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.L$0 Ljava/lang/Object;
      // 0a7: checkcast kotlinx/coroutines/channels/SendChannel
      // 0aa: astore 4
      // 0ac: aload 1
      // 0ad: astore 0
      // 0ae: aload 6
      // 0b0: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 0b3: goto 107
      // 0b6: astore 1
      // 0b7: goto 171
      // 0ba: aload 6
      // 0bc: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 0bf: aload 0
      // 0c0: astore 4
      // 0c2: aload 0
      // 0c3: invokeinterface kotlinx/coroutines/channels/ReceiveChannel.iterator ()Lkotlinx/coroutines/channels/ChannelIterator; 1
      // 0c8: astore 5
      // 0ca: aload 0
      // 0cb: astore 4
      // 0cd: aload 2
      // 0ce: aload 1
      // 0cf: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.L$0 Ljava/lang/Object;
      // 0d2: aload 0
      // 0d3: astore 4
      // 0d5: aload 2
      // 0d6: aload 0
      // 0d7: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.L$1 Ljava/lang/Object;
      // 0da: aload 0
      // 0db: astore 4
      // 0dd: aload 2
      // 0de: aload 5
      // 0e0: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.L$2 Ljava/lang/Object;
      // 0e3: aload 0
      // 0e4: astore 4
      // 0e6: aload 2
      // 0e7: bipush 1
      // 0e8: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.label I
      // 0eb: aload 0
      // 0ec: astore 4
      // 0ee: aload 5
      // 0f0: aload 2
      // 0f1: invokeinterface kotlinx/coroutines/channels/ChannelIterator.hasNext (Lkotlin/coroutines/Continuation;)Ljava/lang/Object; 2
      // 0f6: astore 6
      // 0f8: aload 6
      // 0fa: aload 9
      // 0fc: if_acmpne 102
      // 0ff: aload 9
      // 101: areturn
      // 102: aload 1
      // 103: astore 4
      // 105: aload 0
      // 106: astore 1
      // 107: aload 1
      // 108: astore 0
      // 109: aload 6
      // 10b: checkcast java/lang/Boolean
      // 10e: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 111: ifeq 15f
      // 114: aload 1
      // 115: astore 0
      // 116: aload 5
      // 118: invokeinterface kotlinx/coroutines/channels/ChannelIterator.next ()Ljava/lang/Object; 1
      // 11d: astore 10
      // 11f: aload 1
      // 120: astore 0
      // 121: aload 2
      // 122: aload 4
      // 124: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.L$0 Ljava/lang/Object;
      // 127: aload 1
      // 128: astore 0
      // 129: aload 2
      // 12a: aload 1
      // 12b: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.L$1 Ljava/lang/Object;
      // 12e: aload 1
      // 12f: astore 0
      // 130: aload 2
      // 131: aload 5
      // 133: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.L$2 Ljava/lang/Object;
      // 136: aload 1
      // 137: astore 0
      // 138: aload 2
      // 139: bipush 2
      // 13a: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toChannel$1.label I
      // 13d: aload 2
      // 13e: astore 6
      // 140: aload 4
      // 142: astore 7
      // 144: aload 5
      // 146: astore 8
      // 148: aload 1
      // 149: astore 5
      // 14b: aload 1
      // 14c: astore 0
      // 14d: aload 4
      // 14f: aload 10
      // 151: aload 2
      // 152: invokeinterface kotlinx/coroutines/channels/SendChannel.send (Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object; 3
      // 157: aload 9
      // 159: if_acmpne 078
      // 15c: aload 9
      // 15e: areturn
      // 15f: aload 1
      // 160: astore 0
      // 161: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 164: astore 2
      // 165: aload 1
      // 166: aconst_null
      // 167: invokestatic kotlinx/coroutines/channels/ChannelsKt.cancelConsumed (Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Throwable;)V
      // 16a: aload 4
      // 16c: areturn
      // 16d: astore 1
      // 16e: aload 4
      // 170: astore 0
      // 171: aload 1
      // 172: athrow
      // 173: astore 2
      // 174: aload 0
      // 175: aload 1
      // 176: invokestatic kotlinx/coroutines/channels/ChannelsKt.cancelConsumed (Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Throwable;)V
      // 179: aload 2
      // 17a: athrow
   }

   @JvmStatic
   internal suspend fun <E, C : MutableCollection<in E>> ReceiveChannel<E>.toCollection(destination: C): C {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 000: aload 2
      // 001: instanceof kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1
      // 004: ifeq 02b
      // 007: aload 2
      // 008: checkcast kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1
      // 00b: astore 4
      // 00d: aload 4
      // 00f: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1.label I
      // 012: ldc -2147483648
      // 014: iand
      // 015: ifeq 02b
      // 018: aload 4
      // 01a: aload 4
      // 01c: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1.label I
      // 01f: ldc -2147483648
      // 021: iadd
      // 022: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1.label I
      // 025: aload 4
      // 027: astore 2
      // 028: goto 034
      // 02b: new kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1
      // 02e: dup
      // 02f: aload 2
      // 030: invokespecial kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1.<init> (Lkotlin/coroutines/Continuation;)V
      // 033: astore 2
      // 034: aload 2
      // 035: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1.result Ljava/lang/Object;
      // 038: astore 4
      // 03a: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 03d: astore 8
      // 03f: aload 2
      // 040: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1.label I
      // 043: istore 3
      // 044: iload 3
      // 045: ifeq 085
      // 048: iload 3
      // 049: bipush 1
      // 04a: if_icmpne 07b
      // 04d: aload 2
      // 04e: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1.L$2 Ljava/lang/Object;
      // 051: checkcast kotlinx/coroutines/channels/ChannelIterator
      // 054: astore 6
      // 056: aload 2
      // 057: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1.L$1 Ljava/lang/Object;
      // 05a: checkcast kotlinx/coroutines/channels/ReceiveChannel
      // 05d: astore 1
      // 05e: aload 2
      // 05f: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1.L$0 Ljava/lang/Object;
      // 062: checkcast java/util/Collection
      // 065: astore 7
      // 067: aload 1
      // 068: astore 0
      // 069: aload 4
      // 06b: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 06e: aload 2
      // 06f: astore 5
      // 071: aload 7
      // 073: astore 2
      // 074: goto 0da
      // 077: astore 1
      // 078: goto 10c
      // 07b: new java/lang/IllegalStateException
      // 07e: dup
      // 07f: ldc "call to 'resume' before 'invoke' with coroutine"
      // 081: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 084: athrow
      // 085: aload 4
      // 087: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 08a: aload 0
      // 08b: invokeinterface kotlinx/coroutines/channels/ReceiveChannel.iterator ()Lkotlinx/coroutines/channels/ChannelIterator; 1
      // 090: astore 6
      // 092: aload 1
      // 093: astore 4
      // 095: aload 0
      // 096: astore 1
      // 097: aload 2
      // 098: astore 5
      // 09a: aload 1
      // 09b: astore 0
      // 09c: aload 5
      // 09e: aload 4
      // 0a0: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1.L$0 Ljava/lang/Object;
      // 0a3: aload 1
      // 0a4: astore 0
      // 0a5: aload 5
      // 0a7: aload 1
      // 0a8: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1.L$1 Ljava/lang/Object;
      // 0ab: aload 1
      // 0ac: astore 0
      // 0ad: aload 5
      // 0af: aload 6
      // 0b1: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1.L$2 Ljava/lang/Object;
      // 0b4: aload 1
      // 0b5: astore 0
      // 0b6: aload 5
      // 0b8: bipush 1
      // 0b9: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toCollection$1.label I
      // 0bc: aload 1
      // 0bd: astore 0
      // 0be: aload 6
      // 0c0: aload 5
      // 0c2: invokeinterface kotlinx/coroutines/channels/ChannelIterator.hasNext (Lkotlin/coroutines/Continuation;)Ljava/lang/Object; 2
      // 0c7: astore 7
      // 0c9: aload 7
      // 0cb: aload 8
      // 0cd: if_acmpne 0d3
      // 0d0: aload 8
      // 0d2: areturn
      // 0d3: aload 4
      // 0d5: astore 2
      // 0d6: aload 7
      // 0d8: astore 4
      // 0da: aload 1
      // 0db: astore 0
      // 0dc: aload 4
      // 0de: checkcast java/lang/Boolean
      // 0e1: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 0e4: ifeq 0fd
      // 0e7: aload 1
      // 0e8: astore 0
      // 0e9: aload 2
      // 0ea: aload 6
      // 0ec: invokeinterface kotlinx/coroutines/channels/ChannelIterator.next ()Ljava/lang/Object; 1
      // 0f1: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
      // 0f6: pop
      // 0f7: aload 2
      // 0f8: astore 4
      // 0fa: goto 09a
      // 0fd: aload 1
      // 0fe: astore 0
      // 0ff: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 102: astore 4
      // 104: aload 1
      // 105: aconst_null
      // 106: invokestatic kotlinx/coroutines/channels/ChannelsKt.cancelConsumed (Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Throwable;)V
      // 109: aload 2
      // 10a: areturn
      // 10b: astore 1
      // 10c: aload 1
      // 10d: athrow
      // 10e: astore 2
      // 10f: aload 0
      // 110: aload 1
      // 111: invokestatic kotlinx/coroutines/channels/ChannelsKt.cancelConsumed (Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Throwable;)V
      // 114: aload 2
      // 115: athrow
   }

   @JvmStatic
   internal suspend fun <K, V, M : MutableMap<in K, in V>> ReceiveChannel<Pair<K, V>>.toMap(destination: M): M {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 000: aload 2
      // 001: instanceof kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2
      // 004: ifeq 02b
      // 007: aload 2
      // 008: checkcast kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2
      // 00b: astore 4
      // 00d: aload 4
      // 00f: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2.label I
      // 012: ldc -2147483648
      // 014: iand
      // 015: ifeq 02b
      // 018: aload 4
      // 01a: aload 4
      // 01c: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2.label I
      // 01f: ldc -2147483648
      // 021: iadd
      // 022: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2.label I
      // 025: aload 4
      // 027: astore 2
      // 028: goto 034
      // 02b: new kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2
      // 02e: dup
      // 02f: aload 2
      // 030: invokespecial kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2.<init> (Lkotlin/coroutines/Continuation;)V
      // 033: astore 2
      // 034: aload 2
      // 035: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2.result Ljava/lang/Object;
      // 038: astore 7
      // 03a: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 03d: astore 8
      // 03f: aload 2
      // 040: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2.label I
      // 043: istore 3
      // 044: iload 3
      // 045: ifeq 085
      // 048: iload 3
      // 049: bipush 1
      // 04a: if_icmpne 07b
      // 04d: aload 2
      // 04e: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2.L$2 Ljava/lang/Object;
      // 051: checkcast kotlinx/coroutines/channels/ChannelIterator
      // 054: astore 4
      // 056: aload 2
      // 057: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2.L$1 Ljava/lang/Object;
      // 05a: checkcast kotlinx/coroutines/channels/ReceiveChannel
      // 05d: astore 1
      // 05e: aload 2
      // 05f: getfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2.L$0 Ljava/lang/Object;
      // 062: checkcast java/util/Map
      // 065: astore 6
      // 067: aload 1
      // 068: astore 0
      // 069: aload 7
      // 06b: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 06e: aload 2
      // 06f: astore 5
      // 071: aload 6
      // 073: astore 2
      // 074: goto 0da
      // 077: astore 1
      // 078: goto 121
      // 07b: new java/lang/IllegalStateException
      // 07e: dup
      // 07f: ldc "call to 'resume' before 'invoke' with coroutine"
      // 081: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 084: athrow
      // 085: aload 7
      // 087: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 08a: aload 0
      // 08b: invokeinterface kotlinx/coroutines/channels/ReceiveChannel.iterator ()Lkotlinx/coroutines/channels/ChannelIterator; 1
      // 090: astore 6
      // 092: aload 1
      // 093: astore 4
      // 095: aload 0
      // 096: astore 1
      // 097: aload 2
      // 098: astore 5
      // 09a: aload 1
      // 09b: astore 0
      // 09c: aload 5
      // 09e: aload 4
      // 0a0: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2.L$0 Ljava/lang/Object;
      // 0a3: aload 1
      // 0a4: astore 0
      // 0a5: aload 5
      // 0a7: aload 1
      // 0a8: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2.L$1 Ljava/lang/Object;
      // 0ab: aload 1
      // 0ac: astore 0
      // 0ad: aload 5
      // 0af: aload 6
      // 0b1: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2.L$2 Ljava/lang/Object;
      // 0b4: aload 1
      // 0b5: astore 0
      // 0b6: aload 5
      // 0b8: bipush 1
      // 0b9: putfield kotlinx/coroutines/channels/ChannelsKt__DeprecatedKt$toMap$2.label I
      // 0bc: aload 1
      // 0bd: astore 0
      // 0be: aload 6
      // 0c0: aload 5
      // 0c2: invokeinterface kotlinx/coroutines/channels/ChannelIterator.hasNext (Lkotlin/coroutines/Continuation;)Ljava/lang/Object; 2
      // 0c7: astore 7
      // 0c9: aload 7
      // 0cb: aload 8
      // 0cd: if_acmpne 0d3
      // 0d0: aload 8
      // 0d2: areturn
      // 0d3: aload 4
      // 0d5: astore 2
      // 0d6: aload 6
      // 0d8: astore 4
      // 0da: aload 1
      // 0db: astore 0
      // 0dc: aload 7
      // 0de: checkcast java/lang/Boolean
      // 0e1: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 0e4: ifeq 112
      // 0e7: aload 1
      // 0e8: astore 0
      // 0e9: aload 4
      // 0eb: invokeinterface kotlinx/coroutines/channels/ChannelIterator.next ()Ljava/lang/Object; 1
      // 0f0: checkcast kotlin/Pair
      // 0f3: astore 6
      // 0f5: aload 1
      // 0f6: astore 0
      // 0f7: aload 2
      // 0f8: aload 6
      // 0fa: invokevirtual kotlin/Pair.getFirst ()Ljava/lang/Object;
      // 0fd: aload 6
      // 0ff: invokevirtual kotlin/Pair.getSecond ()Ljava/lang/Object;
      // 102: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 107: pop
      // 108: aload 4
      // 10a: astore 6
      // 10c: aload 2
      // 10d: astore 4
      // 10f: goto 09a
      // 112: aload 1
      // 113: astore 0
      // 114: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 117: astore 4
      // 119: aload 1
      // 11a: aconst_null
      // 11b: invokestatic kotlinx/coroutines/channels/ChannelsKt.cancelConsumed (Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Throwable;)V
      // 11e: aload 2
      // 11f: areturn
      // 120: astore 1
      // 121: aload 1
      // 122: athrow
      // 123: astore 2
      // 124: aload 0
      // 125: aload 1
      // 126: invokestatic kotlinx/coroutines/channels/ChannelsKt.cancelConsumed (Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Throwable;)V
      // 129: aload 2
      // 12a: athrow
   }

   @JvmStatic
   internal suspend fun <E> ReceiveChannel<E>.toMutableSet(): MutableSet<E> {
      return ChannelsKt.toCollection(var0, new LinkedHashSet(), var1);
   }

   @JvmStatic
   internal fun <E, R, V> ReceiveChannel<E>.zip(
      other: ReceiveChannel<R>,
      context: CoroutineContext = Dispatchers.getUnconfined() as CoroutineContext,
      transform: (E, R) -> V
   ): ReceiveChannel<V> {
      return ProduceKt.produce$default(
         GlobalScope.INSTANCE,
         var2,
         0,
         null,
         ChannelsKt.consumesAll(var0, var1),
         (
            new Function2<ProducerScope<? super V>, Continuation<? super Unit>, Object>(var1, var0, var3, null)// $VF: Couldn't be decompiled
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
         6,
         null
      );
   }
}
