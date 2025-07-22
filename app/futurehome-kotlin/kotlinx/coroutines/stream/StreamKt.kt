package kotlinx.coroutines.stream

import j..util.stream.Stream
import kotlinx.coroutines.flow.Flow

public fun <T> Stream<Any>.consumeAsFlow(): Flow<Any> {
   return new StreamFlow(var0);
}
