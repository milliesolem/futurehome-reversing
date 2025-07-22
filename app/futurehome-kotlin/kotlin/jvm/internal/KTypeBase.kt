package kotlin.jvm.internal

import java.lang.reflect.Type
import kotlin.reflect.KType

public interface KTypeBase : KType {
   public val javaType: Type?
}
