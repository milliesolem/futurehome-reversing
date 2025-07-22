package kotlin.reflect

import java.lang.reflect.Type

private interface TypeImpl : Type {
   public abstract override fun getTypeName(): String {
   }
}
