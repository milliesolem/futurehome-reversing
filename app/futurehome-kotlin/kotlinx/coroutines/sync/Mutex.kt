package kotlinx.coroutines.sync

import kotlinx.coroutines.selects.SelectClause2

public interface Mutex {
   public val isLocked: Boolean
   public val onLock: SelectClause2<Any?, Mutex>

   public abstract fun holdsLock(owner: Any): Boolean {
   }

   public abstract suspend fun lock(owner: Any? = ...) {
   }

   public abstract fun tryLock(owner: Any? = ...): Boolean {
   }

   public abstract fun unlock(owner: Any? = ...) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls
}
