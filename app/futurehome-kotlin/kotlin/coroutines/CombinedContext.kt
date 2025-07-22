package kotlin.coroutines

import java.io.Serializable
import kotlin.coroutines.CoroutineContext.Element
import kotlin.coroutines.CoroutineContext.Key
import kotlin.jvm.internal.Ref

internal class CombinedContext(left: CoroutineContext, element: Element) : CoroutineContext, Serializable {
   private final val left: CoroutineContext
   private final val element: Element

   init {
      this.left = var1;
      this.element = var2;
   }

   private fun contains(element: Element): Boolean {
      return this.get(var1.getKey()) == var1;
   }

   private fun containsAll(context: CombinedContext): Boolean {
      while (this.contains(var1.element)) {
         val var2: CoroutineContext = var1.left;
         if (var1.left !is CombinedContext) {
            return this.contains(var2 as CoroutineContext.Element);
         }

         var1 = var1.left as CombinedContext;
      }

      return false;
   }

   private fun size(): Int {
      var var1: Int = 2;
      var var2: CombinedContext = this;

      while (true) {
         if (var2.left is CombinedContext) {
            var2 = var2.left as CombinedContext;
         } else {
            var2 = null;
         }

         if (var2 == null) {
            return var1;
         }

         var1++;
      }
   }

   @JvmStatic
   fun `toString$lambda$2`(var0: java.lang.String, var1: CoroutineContext.Element): java.lang.String {
      if (var0.length() == 0) {
         var0 = var1.toString();
      } else {
         val var2: StringBuilder = new StringBuilder();
         var2.append(var0);
         var2.append(", ");
         var2.append(var1);
         var0 = var2.toString();
      }

      return var0;
   }

   private fun writeReplace(): Any {
      val var1: Int = this.size();
      val var2: Array<CoroutineContext> = new CoroutineContext[var1];
      val var3: Ref.IntRef = new Ref.IntRef();
      this.fold(Unit.INSTANCE, new CombinedContext$$ExternalSyntheticLambda0(var2, var3));
      if (var3.element == var1) {
         return new CombinedContext.Serialized(var2);
      } else {
         throw new IllegalStateException("Check failed.");
      }
   }

   @JvmStatic
   fun `writeReplace$lambda$3`(var0: Array<CoroutineContext>, var1: Ref.IntRef, var2: Unit, var3: CoroutineContext.Element): Unit {
      var0[var1.element++] = var3;
      return Unit.INSTANCE;
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this != var1) {
         if (var1 !is CombinedContext) {
            return false;
         }

         if ((var1 as CombinedContext).size() != this.size() || !(var1 as CombinedContext).containsAll(this)) {
            return false;
         }
      }

      return true;
   }

   public override fun <R> fold(initial: R, operation: (R, Element) -> R): R {
      return (R)var2.invoke(this.left.fold(var1, var2), this.element);
   }

   public override operator fun <E : Element> get(key: Key<E>): E? {
      var var2: CombinedContext = this;

      while (true) {
         val var3: CoroutineContext.Element = var2.element.get(var1);
         if (var3 != null) {
            return (E)var3;
         }

         if (var2.left !is CombinedContext) {
            return (E)var2.left.get(var1);
         }

         var2 = var2.left as CombinedContext;
      }
   }

   public override fun hashCode(): Int {
      return this.left.hashCode() + this.element.hashCode();
   }

   public override fun minusKey(key: Key<*>): CoroutineContext {
      if (this.element.get(var1) != null) {
         return this.left;
      } else {
         val var2: CoroutineContext = this.left.minusKey(var1);
         val var3: CoroutineContext;
         if (var2 === this.left) {
            var3 = this;
         } else if (var2 === EmptyCoroutineContext.INSTANCE) {
            var3 = this.element;
         } else {
            var3 = new CombinedContext(var2, this.element);
         }

         return var3;
      }
   }

   override fun plus(var1: CoroutineContext): CoroutineContext {
      return CoroutineContext.DefaultImpls.plus(this, var1);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("[");
      var1.append(this.fold("", new CombinedContext$$ExternalSyntheticLambda1()));
      var1.append(']');
      return var1.toString();
   }

   private class Serialized(vararg elements: Any) : Serializable {
      public final val elements: Array<CoroutineContext>

      init {
         this.elements = var1;
      }

      private fun readResolve(): Any {
         val var4: Array<CoroutineContext> = this.elements;
         var var3: Any = EmptyCoroutineContext.INSTANCE;
         val var2: Int = this.elements.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3 = (var3 as CoroutineContext).plus(var4[var1]);
         }

         return var3;
      }

      public companion object {
         private const val serialVersionUID: Long
      }
   }
}
