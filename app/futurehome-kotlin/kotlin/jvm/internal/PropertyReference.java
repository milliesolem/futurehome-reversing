package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty;

public abstract class PropertyReference extends CallableReference implements KProperty {
   private final boolean syntheticJavaProperty = false;

   public PropertyReference() {
   }

   public PropertyReference(Object var1) {
      super(var1);
   }

   public PropertyReference(Object var1, Class var2, String var3, String var4, int var5) {
      boolean var7 = false;
      boolean var6;
      if ((var5 & 1) == 1) {
         var6 = true;
      } else {
         var6 = false;
      }

      super(var1, var2, var3, var4, var6);
      var6 = var7;
      if ((var5 & 2) == 2) {
         var6 = true;
      }

      this.syntheticJavaProperty = var6;
   }

   @Override
   public KCallable compute() {
      Object var1;
      if (this.syntheticJavaProperty) {
         var1 = this;
      } else {
         var1 = super.compute();
      }

      return (KCallable)var1;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof PropertyReference)) {
         return var1 instanceof KProperty ? var1.equals(this.compute()) : false;
      } else {
         var1 = var1;
         if (!this.getOwner().equals(var1.getOwner())
            || !this.getName().equals(var1.getName())
            || !this.getSignature().equals(var1.getSignature())
            || !Intrinsics.areEqual(this.getBoundReceiver(), var1.getBoundReceiver())) {
            var2 = false;
         }

         return var2;
      }
   }

   protected KProperty getReflected() {
      if (!this.syntheticJavaProperty) {
         return (KProperty)super.getReflected();
      } else {
         throw new UnsupportedOperationException(
            "Kotlin reflection is not yet supported for synthetic Java properties. Please follow/upvote https://youtrack.jetbrains.com/issue/KT-55980"
         );
      }
   }

   @Override
   public int hashCode() {
      return (this.getOwner().hashCode() * 31 + this.getName().hashCode()) * 31 + this.getSignature().hashCode();
   }

   @Override
   public boolean isConst() {
      return this.getReflected().isConst();
   }

   @Override
   public boolean isLateinit() {
      return this.getReflected().isLateinit();
   }

   @Override
   public String toString() {
      KCallable var1 = this.compute();
      if (var1 != this) {
         return var1.toString();
      } else {
         StringBuilder var2 = new StringBuilder("property ");
         var2.append(this.getName());
         var2.append(" (Kotlin reflection is not available)");
         return var2.toString();
      }
   }
}
