package kotlin.enums

import java.io.Serializable

internal class EnumEntriesSerializationProxy<E extends java.lang.Enum<E>>(vararg entries: Any) : Serializable {
   private final val c: Class<Any>

   init {
      val var2: Class = var1.getClass().getComponentType();
      this.c = var2;
   }

   private fun readResolve(): Any {
      val var1: Array<Any> = this.c.getEnumConstants();
      return EnumEntriesKt.enumEntries((E[])var1);
   }

   private companion object {
      private const val serialVersionUID: Long
   }
}
