package kotlinx.coroutines.flow.internal

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow

public interface FusibleFlow<T> : Flow<T> {
   public abstract fun fuse(context: CoroutineContext = ..., capacity: Int = ..., onBufferOverflow: BufferOverflow = ...): Flow<Any> {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls
}
