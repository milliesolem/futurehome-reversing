package kotlinx.coroutines.channels

public interface ChannelIterator<E> {
   public abstract suspend operator fun hasNext(): Boolean {
   }

   public abstract operator fun next(): Any {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls
}
