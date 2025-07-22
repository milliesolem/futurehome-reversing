package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.Boxing
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlinx.coroutines.channels.ProducerScope

@JvmSynthetic
internal class FlowKt__BuildersKt {
   @JvmStatic
   public fun <T> Iterable<T>.asFlow(): Flow<T> {
      return new Flow<T>(var0) {
         final java.lang.Iterable $this_asFlow$inlined;

         {
            this.$this_asFlow$inlined = var1;
         }

         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            label28: {
               if (var2 is <unrepresentable>) {
                  val var4: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var4.label += Integer.MIN_VALUE;
                     var2 = var4;
                     break label28;
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
            val var7: java.util.Iterator;
            val var9: FlowCollector;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var7 = var2.L$1 as java.util.Iterator;
               var9 = var2.L$0 as FlowCollector;
               ResultKt.throwOnFailure(var5);
            } else {
               ResultKt.throwOnFailure(var5);
               val var10: Continuation = var2;
               var5 = this.$this_asFlow$inlined.iterator();
               var9 = var1;
               var7 = (java.util.Iterator)var5;
            }

            while (var7.hasNext()) {
               var5 = var7.next();
               var2.L$0 = var9;
               var2.L$1 = var7;
               var2.label = 1;
               if (var9.emit(var5, var2) === var6) {
                  return var6;
               }
            }

            return Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T> Iterator<T>.asFlow(): Flow<T> {
      return new Flow<T>(var0) {
         final java.util.Iterator $this_asFlow$inlined;

         {
            this.$this_asFlow$inlined = var1;
         }

         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            label28: {
               if (var2 is <unrepresentable>) {
                  val var4: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var4.label += Integer.MIN_VALUE;
                     var2 = var4;
                     break label28;
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

            var var6: Any = var2.result;
            val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var8: java.util.Iterator;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var8 = var2.L$1 as java.util.Iterator;
               var1 = var2.L$0 as FlowCollector;
               ResultKt.throwOnFailure(var6);
            } else {
               ResultKt.throwOnFailure(var6);
               val var9: Continuation = var2;
               var8 = this.$this_asFlow$inlined;
            }

            while (var8.hasNext()) {
               var6 = var8.next();
               var2.L$0 = var1;
               var2.L$1 = var8;
               var2.label = 1;
               if (var1.emit(var6, var2) === var5) {
                  return var5;
               }
            }

            return Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T> (() -> T).asFlow(): Flow<T> {
      return new Flow<T>(var0) {
         final Function0 $this_asFlow$inlined;

         {
            this.$this_asFlow$inlined = var1;
         }

         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            val var3: Any = var1.emit(this.$this_asFlow$inlined.invoke(), var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T> ((Continuation<T>) -> Any?).asFlow(): Flow<T> {
      return new Flow<T>(var0) {
         final Function1 $this_asFlow$inlined;

         {
            this.$this_asFlow$inlined = var1;
         }

         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            label32: {
               if (var2 is <unrepresentable>) {
                  val var4: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var4.label += Integer.MIN_VALUE;
                     var2 = var4;
                     break label32;
                  }
               }

               var2 = new ContinuationImpl(this, var2) {
                  Object L$0;
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

            var var8: Function1 = (Function1)var2.result;
            val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (var2.label != 0) {
               if (var2.label != 1) {
                  if (var2.label != 2) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  ResultKt.throwOnFailure(var8);
                  return Unit.INSTANCE;
               }

               var1 = var2.L$0 as FlowCollector;
               ResultKt.throwOnFailure(var8);
            } else {
               ResultKt.throwOnFailure(var8);
               var8 = var2;
               var8 = this.$this_asFlow$inlined;
               var2.L$0 = var1;
               var2.label = 1;
               val var5: Any = var8.invoke(var2);
               var8 = (Function1)var5;
               if (var5 === var6) {
                  return var6;
               }
            }

            var2.L$0 = null;
            var2.label = 2;
            return if (var1.emit(var8, var2) === var6) var6 else Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun IntRange.asFlow(): Flow<Int> {
      return new Flow<Integer>(var0) {
         final IntRange $this_asFlow$inlined;

         {
            this.$this_asFlow$inlined = var1;
         }

         @Override
         public Object collect(FlowCollector<? super Integer> var1, Continuation<? super Unit> var2) {
            label28: {
               if (var2 is <unrepresentable>) {
                  val var4: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var4.label += Integer.MIN_VALUE;
                     var2 = var4;
                     break label28;
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
            val var7: java.util.Iterator;
            val var9: FlowCollector;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var7 = var2.L$1 as java.util.Iterator;
               var9 = var2.L$0 as FlowCollector;
               ResultKt.throwOnFailure(var5);
            } else {
               ResultKt.throwOnFailure(var5);
               val var10: Continuation = var2;
               var5 = this.$this_asFlow$inlined.iterator();
               var9 = var1;
               var7 = (java.util.Iterator)var5;
            }

            while (var7.hasNext()) {
               var5 = Boxing.boxInt((var7 as IntIterator).nextInt());
               var2.L$0 = var9;
               var2.L$1 = var7;
               var2.label = 1;
               if (var9.emit(var5, var2) === var6) {
                  return var6;
               }
            }

            return Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun LongRange.asFlow(): Flow<Long> {
      return new Flow<java.lang.Long>(var0) {
         final LongRange $this_asFlow$inlined;

         {
            this.$this_asFlow$inlined = var1;
         }

         @Override
         public Object collect(FlowCollector<? super java.lang.Long> var1, Continuation<? super Unit> var2) {
            label28: {
               if (var2 is <unrepresentable>) {
                  val var4: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var4.label += Integer.MIN_VALUE;
                     var2 = var4;
                     break label28;
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

            var var6: Any = var2.result;
            val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var8: java.util.Iterator;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var8 = var2.L$1 as java.util.Iterator;
               var1 = var2.L$0 as FlowCollector;
               ResultKt.throwOnFailure(var6);
            } else {
               ResultKt.throwOnFailure(var6);
               val var9: Continuation = var2;
               var8 = this.$this_asFlow$inlined.iterator();
            }

            while (var8.hasNext()) {
               var6 = Boxing.boxLong((var8 as LongIterator).nextLong());
               var2.L$0 = var1;
               var2.L$1 = var8;
               var2.label = 1;
               if (var1.emit(var6, var2) === var5) {
                  return var5;
               }
            }

            return Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T> Sequence<T>.asFlow(): Flow<T> {
      return new Flow<T>(var0) {
         final Sequence $this_asFlow$inlined;

         {
            this.$this_asFlow$inlined = var1;
         }

         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            label28: {
               if (var2 is <unrepresentable>) {
                  val var4: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var4.label += Integer.MIN_VALUE;
                     var2 = var4;
                     break label28;
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
            val var7: java.util.Iterator;
            val var9: FlowCollector;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var7 = var2.L$1 as java.util.Iterator;
               var9 = var2.L$0 as FlowCollector;
               ResultKt.throwOnFailure(var5);
            } else {
               ResultKt.throwOnFailure(var5);
               val var10: Continuation = var2;
               var5 = this.$this_asFlow$inlined.iterator();
               var9 = var1;
               var7 = (java.util.Iterator)var5;
            }

            while (var7.hasNext()) {
               var5 = var7.next();
               var2.L$0 = var9;
               var2.L$1 = var7;
               var2.label = 1;
               if (var9.emit(var5, var2) === var6) {
                  return var6;
               }
            }

            return Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun IntArray.asFlow(): Flow<Int> {
      return new Flow<Integer>(var0) {
         final int[] $this_asFlow$inlined;

         {
            this.$this_asFlow$inlined = var1;
         }

         // $VF: Irreducible bytecode was duplicated to produce valid code
         @Override
         public Object collect(FlowCollector<? super Integer> var1, Continuation<? super Unit> var2) {
            label26: {
               if (var2 is <unrepresentable>) {
                  val var5: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var5.label += Integer.MIN_VALUE;
                     var2 = var5;
                     break label26;
                  }
               }

               var2 = new ContinuationImpl(this, var2) {
                  int I$0;
                  int I$1;
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

            var var7: Any = var2.result;
            val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var4: Int;
            var var11: Int;
            val var13: IntArray;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var4 = var2.I$1;
               var11 = var2.I$0;
               var13 = var2.L$1 as IntArray;
               val var6: FlowCollector = var2.L$0 as FlowCollector;
               ResultKt.throwOnFailure(var7);
               var11++;
               var7 = var2;
               var1 = var6;
            } else {
               ResultKt.throwOnFailure(var7);
               val var14: Continuation = var2;
               var13 = this.$this_asFlow$inlined;
               var4 = this.$this_asFlow$inlined.length;
               var11 = 0;
               var7 = var2;
            }

            while (var11 < var4) {
               val var9: Int = Boxing.boxInt(var13[var11]);
               ((<unrepresentable>)var7).L$0 = var1;
               ((<unrepresentable>)var7).L$1 = var13;
               ((<unrepresentable>)var7).I$0 = var11;
               ((<unrepresentable>)var7).I$1 = var4;
               ((<unrepresentable>)var7).label = 1;
               if (var1.emit(var9, (Continuation<? super Unit>)var7) === var8) {
                  return var8;
               }

               var11++;
               var7 = var7;
               var1 = var1;
            }

            return Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun LongArray.asFlow(): Flow<Long> {
      return new Flow<java.lang.Long>(var0) {
         final long[] $this_asFlow$inlined;

         {
            this.$this_asFlow$inlined = var1;
         }

         // $VF: Irreducible bytecode was duplicated to produce valid code
         @Override
         public Object collect(FlowCollector<? super java.lang.Long> var1, Continuation<? super Unit> var2) {
            label26: {
               if (var2 is <unrepresentable>) {
                  val var5: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var5.label += Integer.MIN_VALUE;
                     var2 = var5;
                     break label26;
                  }
               }

               var2 = new ContinuationImpl(this, var2) {
                  int I$0;
                  int I$1;
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

            var var7: Any = var2.result;
            val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var4: Int;
            var var11: Int;
            val var13: LongArray;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var4 = var2.I$1;
               var11 = var2.I$0;
               var13 = var2.L$1 as LongArray;
               val var6: FlowCollector = var2.L$0 as FlowCollector;
               ResultKt.throwOnFailure(var7);
               var11++;
               var7 = var2;
               var1 = var6;
            } else {
               ResultKt.throwOnFailure(var7);
               val var14: Continuation = var2;
               var13 = this.$this_asFlow$inlined;
               var4 = this.$this_asFlow$inlined.length;
               var11 = 0;
               var7 = var2;
            }

            while (var11 < var4) {
               val var9: java.lang.Long = Boxing.boxLong(var13[var11]);
               ((<unrepresentable>)var7).L$0 = var1;
               ((<unrepresentable>)var7).L$1 = var13;
               ((<unrepresentable>)var7).I$0 = var11;
               ((<unrepresentable>)var7).I$1 = var4;
               ((<unrepresentable>)var7).label = 1;
               if (var1.emit(var9, (Continuation<? super Unit>)var7) === var8) {
                  return var8;
               }

               var11++;
               var7 = var7;
               var1 = var1;
            }

            return Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T> Array<T>.asFlow(): Flow<T> {
      return new Flow<T>(var0) {
         final Object[] $this_asFlow$inlined;

         {
            this.$this_asFlow$inlined = var1;
         }

         // $VF: Irreducible bytecode was duplicated to produce valid code
         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            label26: {
               if (var2 is <unrepresentable>) {
                  val var5: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var5.label += Integer.MIN_VALUE;
                     var2 = var5;
                     break label26;
                  }
               }

               var2 = new ContinuationImpl(this, var2) {
                  int I$0;
                  int I$1;
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

            var var6: Any = var2.result;
            val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var4: Int;
            var var11: Int;
            val var13: Array<Any>;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var4 = var2.I$1;
               var11 = var2.I$0;
               var13 = var2.L$1 as Array<Any>;
               val var7: FlowCollector = var2.L$0 as FlowCollector;
               ResultKt.throwOnFailure(var6);
               var11++;
               var6 = var2;
               var1 = var7;
            } else {
               ResultKt.throwOnFailure(var6);
               val var14: Continuation = var2;
               var13 = this.$this_asFlow$inlined;
               var4 = this.$this_asFlow$inlined.length;
               var11 = 0;
               var6 = var2;
            }

            while (var11 < var4) {
               val var9: Any = var13[var11];
               ((<unrepresentable>)var6).L$0 = var1;
               ((<unrepresentable>)var6).L$1 = var13;
               ((<unrepresentable>)var6).I$0 = var11;
               ((<unrepresentable>)var6).I$1 = var4;
               ((<unrepresentable>)var6).label = 1;
               if (var1.emit(var9, (Continuation<? super Unit>)var6) === var8) {
                  return var8;
               }

               var11++;
               var6 = var6;
               var1 = var1;
            }

            return Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T> callbackFlow(block: (ProducerScope<T>, Continuation<Unit>) -> Any?): Flow<T> {
      return new CallbackFlowBuilder(var0, null, 0, null, 14, null);
   }

   @JvmStatic
   public fun <T> channelFlow(block: (ProducerScope<T>, Continuation<Unit>) -> Any?): Flow<T> {
      return new ChannelFlowBuilder(var0, null, 0, null, 14, null);
   }

   @JvmStatic
   public fun <T> emptyFlow(): Flow<T> {
      return EmptyFlow.INSTANCE;
   }

   @JvmStatic
   public fun <T> flow(block: (FlowCollector<T>, Continuation<Unit>) -> Any?): Flow<T> {
      return new SafeFlow(var0);
   }

   @JvmStatic
   public fun <T> flowOf(value: T): Flow<T> {
      return new Flow<T>(var0) {
         final Object $value$inlined;

         {
            this.$value$inlined = var1;
         }

         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            val var3: Any = var1.emit(this.$value$inlined, var2);
            return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
         }
      };
   }

   @JvmStatic
   public fun <T> flowOf(vararg elements: T): Flow<T> {
      return new Flow<T>(var0) {
         final Object[] $elements$inlined;

         {
            this.$elements$inlined = var1;
         }

         // $VF: Irreducible bytecode was duplicated to produce valid code
         @Override
         public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
            label26: {
               if (var2 is <unrepresentable>) {
                  val var5: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var5.label += Integer.MIN_VALUE;
                     var2 = var5;
                     break label26;
                  }
               }

               var2 = new ContinuationImpl(this, var2) {
                  int I$0;
                  int I$1;
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

            var var6: Any = var2.result;
            val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var4: Int;
            var var11: Int;
            val var13: <unrepresentable>;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               var4 = var2.I$1;
               var11 = var2.I$0;
               val var7: FlowCollector = var2.L$1 as FlowCollector;
               var13 = var2.L$0 as <unrepresentable>;
               ResultKt.throwOnFailure(var6);
               var11++;
               var6 = var2;
               var1 = var7;
            } else {
               ResultKt.throwOnFailure(var6);
               val var14: Continuation = var2;
               var4 = this.$elements$inlined.length;
               var11 = 0;
               var13 = this;
               var6 = var2;
            }

            while (var11 < var4) {
               val var9: Any = var13.$elements$inlined[var11];
               ((<unrepresentable>)var6).L$0 = var13;
               ((<unrepresentable>)var6).L$1 = var1;
               ((<unrepresentable>)var6).I$0 = var11;
               ((<unrepresentable>)var6).I$1 = var4;
               ((<unrepresentable>)var6).label = 1;
               if (var1.emit(var9, (Continuation<? super Unit>)var6) === var8) {
                  return var8;
               }

               var11++;
               var6 = var6;
               var1 = var1;
            }

            return Unit.INSTANCE;
         }
      };
   }
}
