package kotlinx.coroutines.channels

import java.util.concurrent.CancellationException
import kotlin.contracts.InvocationKind
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function1
import kotlinx.coroutines.ExceptionsKt

@JvmSynthetic
internal class ChannelsKt__Channels_commonKt {
   internal const val DEFAULT_CLOSE_MESSAGE: String

   @JvmStatic
   internal fun ReceiveChannel<*>.cancelConsumed(cause: Throwable?) {
      var var2: CancellationException = null;
      var var3: CancellationException = null;
      if (var1 != null) {
         if (var1 is CancellationException) {
            var3 = var1 as CancellationException;
         }

         var2 = var3;
         if (var3 == null) {
            var2 = ExceptionsKt.CancellationException("Channel was consumed, consumer had failed", var1);
         }
      }

      var0.cancel(var2);
   }

   @Deprecated(level = DeprecationLevel.WARNING, message = "BroadcastChannel is deprecated in the favour of SharedFlow and is no longer supported")
   @JvmStatic
   public inline fun <E, R> BroadcastChannel<E>.consume(block: (ReceiveChannel<E>) -> R): R {
      label13: {
         val var4: ReceiveChannel = var0.openSubscription();

         try {
            val var5: Any = var1.invoke(var4);
         } catch (var2: java.lang.Throwable) {
            ReceiveChannel.DefaultImpls.cancel$default(var4, null, 1, null);
         }

         ReceiveChannel.DefaultImpls.cancel$default(var4, null, 1, null);
      }
   }

   @JvmStatic
   public inline fun <E, R> ReceiveChannel<E>.consume(block: (ReceiveChannel<E>) -> R): R {
      contract {
         callsInPlace(block, InvocationKind.EXACTLY_ONCE)
      }

      label15: {
         try {
            var9 = var1.invoke(var0);
         } catch (var4: java.lang.Throwable) {
            val var2: java.lang.Throwable = var4;

            try {
               throw var2;
            } catch (var3: java.lang.Throwable) {
               ChannelsKt.cancelConsumed(var0, var4);
            }
         }

         ChannelsKt.cancelConsumed(var0, null);
         return (R)var9;
      }
   }

   @Deprecated(level = DeprecationLevel.WARNING, message = "BroadcastChannel is deprecated in the favour of SharedFlow and is no longer supported")
   @JvmStatic
   public suspend inline fun <E> BroadcastChannel<E>.consumeEach(action: (E) -> Unit) {
      label82: {
         if (var2 is <unrepresentable>) {
            val var4: <unrepresentable> = var2 as <unrepresentable>;
            if ((var2.label and Integer.MIN_VALUE) != 0) {
               var4.label += Integer.MIN_VALUE;
               var2 = var4;
               break label82;
            }
         }

         var2 = new ContinuationImpl(var2) {
            Object L$0;
            Object L$1;
            Object L$2;
            int label;
            Object result;

            {
               super(var1);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return ChannelsKt__Channels_commonKt.consumeEach(null, null, this);
            }
         };
      }

      label76: {
         label75: {
            var var5: ChannelIterator = (ChannelIterator)var2.result;
            val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            val var45: Any;
            if (var2.label != 0) {
               if (var2.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               val var6: ChannelIterator = var2.L$2 as ChannelIterator;
               val var44: ReceiveChannel = var2.L$1 as ReceiveChannel;
               val var7: Function1 = var2.L$0 as Function1;

               try {
                  ResultKt.throwOnFailure(var5);
               } catch (var11: java.lang.Throwable) {
                  break label75;
               }

               var34 = var44;
               var45 = var7;
               val var40: Any = var5;
               var5 = var6;

               try {
                  if (!var40 as java.lang.Boolean) {
                     break label76;
                  }

                  ((Function1<Object, R>)var45).invoke(var5.next());
               } catch (var12: java.lang.Throwable) {
                  var0 = var12;
                  var1 = var44;
                  break label75;
               }

               var2 = var2;
            } else {
               ResultKt.throwOnFailure(var5);
               var34 = ((BroadcastChannel)var0).openSubscription();

               try {
                  var5 = var34.iterator();
               } catch (var10: java.lang.Throwable) {
                  var1 = var34;
                  var0 = var10;
                  break label75;
               }

               var45 = var1;
            }

            while (true) {
               var var47: Any;
               try {
                  var2.L$0 = var45;
                  var2.L$1 = var34;
                  var2.L$2 = var5;
                  var2.label = 1;
                  var47 = var5.hasNext(var2);
               } catch (var9: java.lang.Throwable) {
                  var1 = var34;
                  var0 = var9;
                  break;
               }

               if (var47 === var8) {
                  return var8;
               }

               val var42: Any = var47;

               try {
                  if (!var42 as java.lang.Boolean) {
                     break label76;
                  }

                  ((Function1<Object, R>)var45).invoke(var5.next());
               } catch (var13: java.lang.Throwable) {
                  var0 = var13;
                  var1 = var34;
                  break;
               }

               var2 = var2;
            }
         }

         ReceiveChannel.DefaultImpls.cancel$default((ReceiveChannel)var1, null, 1, null);
         throw var0;
      }

      ReceiveChannel.DefaultImpls.cancel$default(var34, null, 1, null);
      return Unit.INSTANCE;
   }

   @JvmStatic
   public suspend inline fun <E> ReceiveChannel<E>.consumeEach(action: (E) -> Unit) {
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
      // 001: instanceof kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1
      // 004: ifeq 02b
      // 007: aload 2
      // 008: checkcast kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1
      // 00b: astore 4
      // 00d: aload 4
      // 00f: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1.label I
      // 012: ldc -2147483648
      // 014: iand
      // 015: ifeq 02b
      // 018: aload 4
      // 01a: aload 4
      // 01c: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1.label I
      // 01f: ldc -2147483648
      // 021: iadd
      // 022: putfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1.label I
      // 025: aload 4
      // 027: astore 2
      // 028: goto 034
      // 02b: new kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1
      // 02e: dup
      // 02f: aload 2
      // 030: invokespecial kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1.<init> (Lkotlin/coroutines/Continuation;)V
      // 033: astore 2
      // 034: aload 2
      // 035: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1.result Ljava/lang/Object;
      // 038: astore 4
      // 03a: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 03d: astore 8
      // 03f: aload 2
      // 040: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1.label I
      // 043: istore 3
      // 044: iload 3
      // 045: ifeq 085
      // 048: iload 3
      // 049: bipush 1
      // 04a: if_icmpne 07b
      // 04d: aload 2
      // 04e: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1.L$2 Ljava/lang/Object;
      // 051: checkcast kotlinx/coroutines/channels/ChannelIterator
      // 054: astore 6
      // 056: aload 2
      // 057: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1.L$1 Ljava/lang/Object;
      // 05a: checkcast kotlinx/coroutines/channels/ReceiveChannel
      // 05d: astore 1
      // 05e: aload 2
      // 05f: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1.L$0 Ljava/lang/Object;
      // 062: checkcast kotlin/jvm/functions/Function1
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
      // 078: goto 10d
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
      // 0a0: putfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1.L$0 Ljava/lang/Object;
      // 0a3: aload 1
      // 0a4: astore 0
      // 0a5: aload 5
      // 0a7: aload 1
      // 0a8: putfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1.L$1 Ljava/lang/Object;
      // 0ab: aload 1
      // 0ac: astore 0
      // 0ad: aload 5
      // 0af: aload 6
      // 0b1: putfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1.L$2 Ljava/lang/Object;
      // 0b4: aload 1
      // 0b5: astore 0
      // 0b6: aload 5
      // 0b8: bipush 1
      // 0b9: putfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$consumeEach$1.label I
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
      // 0f1: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 0f6: pop
      // 0f7: aload 2
      // 0f8: astore 4
      // 0fa: goto 09a
      // 0fd: aload 1
      // 0fe: astore 0
      // 0ff: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 102: astore 2
      // 103: aload 1
      // 104: aconst_null
      // 105: invokestatic kotlinx/coroutines/channels/ChannelsKt.cancelConsumed (Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Throwable;)V
      // 108: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 10b: areturn
      // 10c: astore 1
      // 10d: aload 1
      // 10e: athrow
      // 10f: astore 2
      // 110: aload 0
      // 111: aload 1
      // 112: invokestatic kotlinx/coroutines/channels/ChannelsKt.cancelConsumed (Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Throwable;)V
      // 115: aload 2
      // 116: athrow
   }

   @JvmStatic
   public suspend fun <E> ReceiveChannel<E>.toList(): List<E> {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 000: aload 1
      // 001: instanceof kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1
      // 004: ifeq 026
      // 007: aload 1
      // 008: checkcast kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1
      // 00b: astore 3
      // 00c: aload 3
      // 00d: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.label I
      // 010: ldc -2147483648
      // 012: iand
      // 013: ifeq 026
      // 016: aload 3
      // 017: aload 3
      // 018: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.label I
      // 01b: ldc -2147483648
      // 01d: iadd
      // 01e: putfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.label I
      // 021: aload 3
      // 022: astore 1
      // 023: goto 02f
      // 026: new kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1
      // 029: dup
      // 02a: aload 1
      // 02b: invokespecial kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.<init> (Lkotlin/coroutines/Continuation;)V
      // 02e: astore 1
      // 02f: aload 1
      // 030: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.result Ljava/lang/Object;
      // 033: astore 7
      // 035: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 038: astore 8
      // 03a: aload 1
      // 03b: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.label I
      // 03e: istore 2
      // 03f: iload 2
      // 040: ifeq 085
      // 043: iload 2
      // 044: bipush 1
      // 045: if_icmpne 07b
      // 048: aload 1
      // 049: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.L$3 Ljava/lang/Object;
      // 04c: checkcast kotlinx/coroutines/channels/ChannelIterator
      // 04f: astore 5
      // 051: aload 1
      // 052: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.L$2 Ljava/lang/Object;
      // 055: checkcast kotlinx/coroutines/channels/ReceiveChannel
      // 058: astore 0
      // 059: aload 1
      // 05a: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.L$1 Ljava/lang/Object;
      // 05d: checkcast java/util/List
      // 060: astore 6
      // 062: aload 1
      // 063: getfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.L$0 Ljava/lang/Object;
      // 066: checkcast java/util/List
      // 069: astore 4
      // 06b: aload 0
      // 06c: astore 3
      // 06d: aload 7
      // 06f: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 072: goto 0cb
      // 075: astore 1
      // 076: aload 3
      // 077: astore 0
      // 078: goto 105
      // 07b: new java/lang/IllegalStateException
      // 07e: dup
      // 07f: ldc "call to 'resume' before 'invoke' with coroutine"
      // 081: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 084: athrow
      // 085: aload 7
      // 087: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 08a: invokestatic kotlin/collections/CollectionsKt.createListBuilder ()Ljava/util/List;
      // 08d: astore 3
      // 08e: aload 0
      // 08f: invokeinterface kotlinx/coroutines/channels/ReceiveChannel.iterator ()Lkotlinx/coroutines/channels/ChannelIterator; 1
      // 094: astore 5
      // 096: aload 3
      // 097: astore 4
      // 099: aload 1
      // 09a: aload 4
      // 09c: putfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.L$0 Ljava/lang/Object;
      // 09f: aload 1
      // 0a0: aload 3
      // 0a1: putfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.L$1 Ljava/lang/Object;
      // 0a4: aload 1
      // 0a5: aload 0
      // 0a6: putfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.L$2 Ljava/lang/Object;
      // 0a9: aload 1
      // 0aa: aload 5
      // 0ac: putfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.L$3 Ljava/lang/Object;
      // 0af: aload 1
      // 0b0: bipush 1
      // 0b1: putfield kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt$toList$1.label I
      // 0b4: aload 5
      // 0b6: aload 1
      // 0b7: invokeinterface kotlinx/coroutines/channels/ChannelIterator.hasNext (Lkotlin/coroutines/Continuation;)Ljava/lang/Object; 2
      // 0bc: astore 7
      // 0be: aload 7
      // 0c0: aload 8
      // 0c2: if_acmpne 0c8
      // 0c5: aload 8
      // 0c7: areturn
      // 0c8: aload 3
      // 0c9: astore 6
      // 0cb: aload 0
      // 0cc: astore 3
      // 0cd: aload 7
      // 0cf: checkcast java/lang/Boolean
      // 0d2: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 0d5: ifeq 0ef
      // 0d8: aload 0
      // 0d9: astore 3
      // 0da: aload 6
      // 0dc: aload 5
      // 0de: invokeinterface kotlinx/coroutines/channels/ChannelIterator.next ()Ljava/lang/Object; 1
      // 0e3: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 0e8: pop
      // 0e9: aload 6
      // 0eb: astore 3
      // 0ec: goto 099
      // 0ef: aload 0
      // 0f0: astore 3
      // 0f1: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0f4: astore 1
      // 0f5: aload 0
      // 0f6: aconst_null
      // 0f7: invokestatic kotlinx/coroutines/channels/ChannelsKt.cancelConsumed (Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Throwable;)V
      // 0fa: aload 4
      // 0fc: invokestatic kotlin/collections/CollectionsKt.build (Ljava/util/List;)Ljava/util/List;
      // 0ff: areturn
      // 100: astore 1
      // 101: goto 105
      // 104: astore 1
      // 105: aload 1
      // 106: athrow
      // 107: astore 3
      // 108: aload 0
      // 109: aload 1
      // 10a: invokestatic kotlinx/coroutines/channels/ChannelsKt.cancelConsumed (Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Throwable;)V
      // 10d: aload 3
      // 10e: athrow
   }
}
