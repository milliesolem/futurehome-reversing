package kotlin.jvm.internal

import kotlin.reflect.KCallable

public class PackageReference(jClass: Class<*>, moduleName: String) : ClassBasedDeclarationContainer {
   public open val jClass: Class<*>
   private final val moduleName: String

   public open val members: Collection<KCallable<*>>
      public open get() {
         throw new KotlinReflectionNotSupportedError();
      }


   init {
      this.jClass = var1;
      this.moduleName = var2;
   }

   public override operator fun equals(other: Any?): Boolean {
      val var2: Boolean;
      if (var1 is PackageReference && this.getJClass() == (var1 as PackageReference).getJClass()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun hashCode(): Int {
      return this.getJClass().hashCode();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this.getJClass());
      var1.append(" (Kotlin reflection is not available)");
      return var1.toString();
   }
}
