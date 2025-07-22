package kotlin.collections

import java.util.ArrayList
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function2

internal fun checkWindowSizeStep(size: Int, step: Int) {
   if (var0 <= 0 || var1 <= 0) {
      val var3: java.lang.String;
      if (var0 != var1) {
         val var2: StringBuilder = new StringBuilder("Both size ");
         var2.append(var0);
         var2.append(" and step ");
         var2.append(var1);
         var2.append(" must be greater than zero.");
         var3 = var2.toString();
      } else {
         val var4: StringBuilder = new StringBuilder("size ");
         var4.append(var0);
         var4.append(" must be greater than zero.");
         var3 = var4.toString();
      }

      throw new IllegalArgumentException(var3.toString());
   }
}

internal fun <T> windowedIterator(iterator: Iterator<T>, size: Int, step: Int, partialWindows: Boolean, reuseBuffer: Boolean): Iterator<List<T>> {
   return (java.util.Iterator<java.util.List<T>>)(if (!var0.hasNext())
      EmptyIterator.INSTANCE
      else
      SequencesKt.iterator(
         (new Function2<SequenceScope<? super java.util.List<? extends T>>, Continuation<? super Unit>, Object>(var1, var2, var0, var4, var3, null) {
            final java.util.Iterator<T> $iterator;
            final boolean $partialWindows;
            final boolean $reuseBuffer;
            final int $size;
            final int $step;
            int I$0;
            private Object L$0;
            Object L$1;
            Object L$2;
            int label;

            {
               super(2, var6);
               this.$size = var1;
               this.$step = var2x;
               this.$iterator = var3x;
               this.$reuseBuffer = var4;
               this.$partialWindows = var5;
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               val var3: Function2 = new <anonymous constructor>(this.$size, this.$step, this.$iterator, this.$reuseBuffer, this.$partialWindows, var2);
               var3.L$0 = var1;
               return var3 as Continuation<Unit>;
            }

            public final Object invoke(SequenceScope<? super java.util.List<? extends T>> var1, Continuation<? super Unit> var2x) {
               return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
            }

            // $VF: Irreducible bytecode was duplicated to produce valid code
            @Override
            public final Object invokeSuspend(Object var1) {
               var var8: Any;
               var var21: RingBuffer;
               label138: {
                  var var25: java.util.Iterator;
                  label148: {
                     var8 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     var var3x: Int;
                     var var5: SequenceScope;
                     val var7: java.util.Iterator;
                     var var10: ArrayList;
                     var var15: Int;
                     if (this.label != 0) {
                        if (this.label != 1) {
                           if (this.label == 2) {
                              ResultKt.throwOnFailure(var1);
                              return Unit.INSTANCE;
                           }

                           if (this.label != 3) {
                              if (this.label != 4) {
                                 if (this.label != 5) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                 }

                                 ResultKt.throwOnFailure(var1);
                                 return Unit.INSTANCE;
                              }

                              var21 = this.L$1 as RingBuffer;
                              val var29: SequenceScope = this.L$0 as SequenceScope;
                              ResultKt.throwOnFailure(var1);
                              var21.removeFirst(this.$step);
                              var1 = var29;
                              break label138;
                           }

                           var25 = this.L$2 as java.util.Iterator;
                           var21 = this.L$1 as RingBuffer;
                           val var32: SequenceScope = this.L$0 as SequenceScope;
                           ResultKt.throwOnFailure(var1);
                           var1 = var21;
                           var21.removeFirst(this.$step);
                           var20 = var32;
                           break label148;
                        }

                        var15 = this.I$0;
                        var7 = this.L$2 as java.util.Iterator;
                        val var4: ArrayList = this.L$1 as ArrayList;
                        val var6: SequenceScope = this.L$0 as SequenceScope;
                        ResultKt.throwOnFailure(var1);
                        var3x = var15;
                        if (this.$reuseBuffer) {
                           var4.clear();
                           var10 = var4;
                        } else {
                           var10 = new ArrayList(this.$size);
                        }

                        var15 = var15;
                        var5 = var6;
                     } else {
                        ResultKt.throwOnFailure(var1);
                        val var13: SequenceScope = this.L$0 as SequenceScope;
                        var3x = RangesKt.coerceAtMost(this.$size, 1024);
                        var15 = this.$step - this.$size;
                        if (this.$step - this.$size < 0) {
                           val var30: RingBuffer = new RingBuffer(var3x);
                           var25 = this.$iterator;
                           var20 = var13;
                           var1 = var30;
                           break label148;
                        }

                        val var22: ArrayList = new ArrayList(var3x);
                        var7 = this.$iterator;
                        var3x = 0;
                        var5 = var13;
                        var10 = var22;
                     }

                     while (var7.hasNext()) {
                        val var23: Any = var7.next();
                        if (var3x > 0) {
                           var3x--;
                        } else {
                           var10.add(var23);
                           if (var10.size() == this.$size) {
                              val var9: Continuation = this;
                              this.L$0 = var5;
                              this.L$1 = var10;
                              this.L$2 = var7;
                              this.I$0 = var15;
                              this.label = 1;
                              if (var5.yield(var10, var9) === var8) {
                                 return var8;
                              }

                              var3x = var15;
                              if (this.$reuseBuffer) {
                                 var10.clear();
                                 var10 = var10;
                              } else {
                                 var10 = new ArrayList(this.$size);
                              }

                              var15 = var15;
                              var5 = var5;
                           }
                        }
                     }

                     if (!var10.isEmpty() && (this.$partialWindows || var10.size() == this.$size)) {
                        val var24: Continuation = this;
                        this.L$0 = null;
                        this.L$1 = null;
                        this.L$2 = null;
                        this.label = 2;
                        if (var5.yield(var10, var24) === var8) {
                           return var8;
                        }
                     }

                     return Unit.INSTANCE;
                  }

                  while (var25.hasNext()) {
                     var1.add(var25.next());
                     if (var1.isFull()) {
                        if (var1.size() < this.$size) {
                           var1 = var1.expanded(this.$size);
                        } else {
                           val var31: java.util.List;
                           if (this.$reuseBuffer) {
                              var31 = var1;
                           } else {
                              var31 = new ArrayList(var1);
                           }

                           val var34: Continuation = this;
                           this.L$0 = var20;
                           this.L$1 = var1;
                           this.L$2 = var25;
                           this.label = 3;
                           if (var20.yield(var31, var34) === var8) {
                              return var8;
                           }

                           var1.removeFirst(this.$step);
                           var20 = var20;
                        }
                     }
                  }

                  if (!this.$partialWindows) {
                     return Unit.INSTANCE;
                  }

                  var1 = var20;
                  var21 = var1;
               }

               while (var21.size() > this.$step) {
                  val var27: java.util.List;
                  if (this.$reuseBuffer) {
                     var27 = var21;
                  } else {
                     var27 = new ArrayList(var21);
                  }

                  val var33: Continuation = this;
                  this.L$0 = var1;
                  this.L$1 = var21;
                  this.L$2 = null;
                  this.label = 4;
                  if (var1.yield(var27, var33) === var8) {
                     return var8;
                  }

                  var21.removeFirst(this.$step);
                  var1 = var1;
               }

               if (!var21.isEmpty()) {
                  val var28: Continuation = this;
                  this.L$0 = null;
                  this.L$1 = null;
                  this.L$2 = null;
                  this.label = 5;
                  if (var1.yield(var21, var28) === var8) {
                     return var8;
                  }
               }

               return Unit.INSTANCE;
            }
         }) as Function2
      ));
}

internal fun <T> Sequence<T>.windowedSequence(size: Int, step: Int, partialWindows: Boolean, reuseBuffer: Boolean): Sequence<List<T>> {
   checkWindowSizeStep(var1, var2);
   return (Sequence<java.util.List<T>>)(new Sequence<java.util.List<? extends T>>(var0, var1, var2, var3, var4) {
      final boolean $partialWindows$inlined;
      final boolean $reuseBuffer$inlined;
      final int $size$inlined;
      final int $step$inlined;
      final Sequence $this_windowedSequence$inlined;

      {
         this.$this_windowedSequence$inlined = var1;
         this.$size$inlined = var2;
         this.$step$inlined = var3;
         this.$partialWindows$inlined = var4;
         this.$reuseBuffer$inlined = var5;
      }

      @Override
      public java.util.Iterator<java.util.List<? extends T>> iterator() {
         return SlidingWindowKt.windowedIterator(
            this.$this_windowedSequence$inlined.iterator(), this.$size$inlined, this.$step$inlined, this.$partialWindows$inlined, this.$reuseBuffer$inlined
         );
      }
   });
}
