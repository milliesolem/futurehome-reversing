package kotlin.jvm.internal

import kotlin.reflect.KDeclarationContainer

public interface ClassBasedDeclarationContainer : KDeclarationContainer {
   public val jClass: Class<*>
}
