package kotlinx.coroutines.channels

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

// $VF: Class flags could not be determined
internal class ChannelsKt {
   @JvmStatic
   public java.lang.String DEFAULT_CLOSE_MESSAGE = "Channel was closed";

   @JvmStatic
   fun cancelConsumed(var0: ReceiveChannel<?>, var1: java.lang.Throwable) {
      ChannelsKt__Channels_commonKt.cancelConsumed(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.WARNING, message = "BroadcastChannel is deprecated in the favour of SharedFlow and is no longer supported")
   @JvmStatic
   fun <E, R> consume(var0: BroadcastChannel<E>, var1: (ReceiveChannel<? extends E>?) -> R): R {
      return ChannelsKt__Channels_commonKt.consume(var0, var1);
   }

   @JvmStatic
   fun <E, R> consume(var0: ReceiveChannel<? extends E>, var1: (ReceiveChannel<? extends E>?) -> R): R {
      return ChannelsKt__Channels_commonKt.consume(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.WARNING, message = "BroadcastChannel is deprecated in the favour of SharedFlow and is no longer supported")
   @JvmStatic
   fun <E> consumeEach(var0: BroadcastChannel<E>, var1: (E?) -> Unit, var2: Continuation<? super Unit>): Any {
      return ChannelsKt__Channels_commonKt.consumeEach(var0, var1, var2);
   }

   @JvmStatic
   fun <E> consumeEach(var0: ReceiveChannel<? extends E>, var1: (E?) -> Unit, var2: Continuation<? super Unit>): Any {
      return ChannelsKt__Channels_commonKt.consumeEach(var0, var1, var2);
   }

   @JvmStatic
   fun consumes(var0: ReceiveChannel<?>): (java.lang.Throwable?) -> Unit {
      return ChannelsKt__DeprecatedKt.consumes(var0);
   }

   @JvmStatic
   fun consumesAll(vararg var0: ReceiveChannel<?>): (java.lang.Throwable?) -> Unit {
      return ChannelsKt__DeprecatedKt.consumesAll(var0);
   }

   @JvmStatic
   fun <E, K> distinctBy(var0: ReceiveChannel<? extends E>, var1: CoroutineContext, var2: (E?, Continuation<? super K>?) -> Any): ReceiveChannel<E> {
      return ChannelsKt__DeprecatedKt.distinctBy(var0, var1, var2);
   }

   @JvmStatic
   fun <E> filter(var0: ReceiveChannel<? extends E>, var1: CoroutineContext, var2: (E?, Continuation<? super java.lang.Boolean>?) -> Any): ReceiveChannel<E> {
      return ChannelsKt__DeprecatedKt.filter(var0, var1, var2);
   }

   @JvmStatic
   fun <E> filterNotNull(var0: ReceiveChannel<? extends E>): ReceiveChannel<E> {
      return ChannelsKt__DeprecatedKt.filterNotNull(var0);
   }

   @JvmStatic
   fun <E, R> map(var0: ReceiveChannel<? extends E>, var1: CoroutineContext, var2: (E?, Continuation<? super R>?) -> Any): ReceiveChannel<R> {
      return ChannelsKt__DeprecatedKt.map(var0, var1, var2);
   }

   @JvmStatic
   fun <E, R> mapIndexed(var0: ReceiveChannel<? extends E>, var1: CoroutineContext, var2: (Int?, E?, Continuation<? super R>?) -> Any): ReceiveChannel<R> {
      return ChannelsKt__DeprecatedKt.mapIndexed(var0, var1, var2);
   }

   @JvmStatic
   fun <E, C extends SendChannel<? super E>> toChannel(var0: ReceiveChannel<? extends E>, var1: C, var2: Continuation<? super C>): Any {
      return ChannelsKt__DeprecatedKt.toChannel(var0, (C)var1, var2);
   }

   @JvmStatic
   fun <E, C extends java.util.Collection<? super E>> toCollection(var0: ReceiveChannel<? extends E>, var1: C, var2: Continuation<? super C>): Any {
      return ChannelsKt__DeprecatedKt.toCollection(var0, (C)var1, var2);
   }

   @JvmStatic
   fun <E> toList(var0: ReceiveChannel<? extends E>, var1: Continuation<? super java.utilList<? extends E>>): Any {
      return ChannelsKt__Channels_commonKt.toList(var0, var1);
   }

   @JvmStatic
   fun <K, V, M extends java.util.Map<? super K, ? super V>> toMap(
      var0: ReceiveChannel<? extends Pair<? extends K, ? extends V>>, var1: M, var2: Continuation<? super M>
   ): Any {
      return ChannelsKt__DeprecatedKt.toMap(var0, (M)var1, var2);
   }

   @JvmStatic
   fun <E> toMutableSet(var0: ReceiveChannel<? extends E>, var1: Continuation<? super java.utilSet<E>>): Any {
      return ChannelsKt__DeprecatedKt.toMutableSet(var0, var1);
   }

   @JvmStatic
   fun <E> trySendBlocking(var0: SendChannel<? super E>, var1: E): Any {
      return ChannelsKt__ChannelsKt.trySendBlocking(var0, var1);
   }

   @JvmStatic
   fun <E, R, V> zip(var0: ReceiveChannel<? extends E>, var1: ReceiveChannel<? extends R>, var2: CoroutineContext, var3: (E?, R?) -> V): ReceiveChannel<V> {
      return ChannelsKt__DeprecatedKt.zip(var0, var1, var2, var3);
   }
}
