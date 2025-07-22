package kotlinx.coroutines.channels

import java.util.concurrent.CancellationException
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlinx.coroutines.internal.StackTraceRecoveryKt
import kotlinx.coroutines.selects.SelectClause1

public interface ReceiveChannel<E> {
   public val isClosedForReceive: Boolean
   public val isEmpty: Boolean
   public val onReceive: SelectClause1<Any>
   public val onReceiveCatching: SelectClause1<ChannelResult<Any>>

   public open val onReceiveOrNull: SelectClause1<Any?>
      public open get() {
      }


   public abstract fun cancel(cause: CancellationException? = ...) {
   }

   public abstract operator fun iterator(): ChannelIterator<Any> {
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith = @ReplaceWith(expression = "tryReceive().getOrNull()", imports = []))
   public open fun poll(): Any? {
   }

   public abstract suspend fun receive(): Any {
   }

   public abstract suspend fun receiveCatching(): ChannelResult<Any> {
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = []))
   public open suspend fun receiveOrNull(): Any? {
   }

   public abstract fun tryReceive(): ChannelResult<Any> {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <E> getOnReceiveOrNull(var0: ReceiveChannel<? extends E>): SelectClause1<E> {
         return (var0 as BufferedChannel).getOnReceiveOrNull();
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith = @ReplaceWith(expression = "tryReceive().getOrNull()", imports = []))
      @JvmStatic
      fun <E> poll(var0: ReceiveChannel<? extends E>): E {
         var var1: Any = var0.tryReceive-PtdJZtk();
         if (ChannelResult.isSuccess-impl(var1)) {
            return (E)ChannelResult.getOrThrow-impl(var1);
         } else {
            var1 = ChannelResult.exceptionOrNull-impl(var1);
            if (var1 == null) {
               return null;
            } else {
               throw StackTraceRecoveryKt.recoverStackTrace((E)var1);
            }
         }
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = []))
      @JvmStatic
      fun <E> receiveOrNull(var0: ReceiveChannel<? extends E>, var1: Continuation<? super E>): Any {
         label23: {
            if (var1 is <unrepresentable>) {
               val var3: <unrepresentable> = var1 as <unrepresentable>;
               if ((var1.label and Integer.MIN_VALUE) != 0) {
                  var3.label += Integer.MIN_VALUE;
                  var1 = var3;
                  break label23;
               }
            }

            var1 = new ContinuationImpl(var1) {
               int label;
               Object result;

               {
                  super(var1);
               }

               @Override
               public final Object invokeSuspend(Object var1) {
                  this.result = var1;
                  this.label |= Integer.MIN_VALUE;
                  return ReceiveChannel.DefaultImpls.receiveOrNull(null, this);
               }
            };
         }

         val var4: Any = var1.result;
         val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
         val var5: Any;
         if (var1.label != 0) {
            if (var1.label != 1) {
               throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            ResultKt.throwOnFailure(var4);
            var5 = (var4 as ChannelResult).unbox-impl();
         } else {
            ResultKt.throwOnFailure(var4);
            var1.label = 1;
            val var7: Any = var0.receiveCatching-JP2dKIU(var1);
            var5 = var7;
            if (var7 === var8) {
               return var8;
            }
         }

         return ChannelResult.getOrNull-impl(var5);
      }
   }
}
