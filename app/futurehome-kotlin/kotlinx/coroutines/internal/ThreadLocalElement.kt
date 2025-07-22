package kotlinx.coroutines.internal

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.CoroutineContext.Element
import kotlin.coroutines.CoroutineContext.Key
import kotlinx.coroutines.ThreadContextElement

internal class ThreadLocalElement<T>(value: Any, threadLocal: ThreadLocal<Any>) : ThreadContextElement<T> {
   public open val key: Key<*>
   private final val threadLocal: ThreadLocal<Any>
   private final val value: Any

   init {
      this.value = (T)var1;
      this.threadLocal = var2;
      this.key = new ThreadLocalKey(var2);
   }

   override fun <R> fold(var1: R, var2: (R?, CoroutineContext.Element?) -> R): R {
      return ThreadContextElement.DefaultImpls.fold(this, (R)var1, var2);
   }

   public override operator fun <E : Element> get(key: Key<E>): E? {
      val var2: CoroutineContext.Element;
      if (this.getKey() == var1) {
         var2 = this;
      } else {
         var2 = null;
      }

      return (E)var2;
   }

   public override fun minusKey(key: Key<*>): CoroutineContext {
      val var2: CoroutineContext;
      if (this.getKey() == var1) {
         var2 = EmptyCoroutineContext.INSTANCE;
      } else {
         var2 = this;
      }

      return var2;
   }

   override fun plus(var1: CoroutineContext): CoroutineContext {
      return ThreadContextElement.DefaultImpls.plus(this, var1);
   }

   public override fun restoreThreadContext(context: CoroutineContext, oldState: Any) {
      this.threadLocal.set((T)var2);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("ThreadLocal(value=");
      var1.append(this.value);
      var1.append(", threadLocal = ");
      var1.append(this.threadLocal);
      var1.append(')');
      return var1.toString();
   }

   public override fun updateThreadContext(context: CoroutineContext): Any {
      val var2: Any = this.threadLocal.get();
      this.threadLocal.set(this.value);
      return (T)var2;
   }
}
