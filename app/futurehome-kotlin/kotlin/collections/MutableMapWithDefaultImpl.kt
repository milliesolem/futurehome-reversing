package kotlin.collections

import kotlin.collections.MutableMap.MutableEntry

private class MutableMapWithDefaultImpl<K, V>(map: MutableMap<Any, Any>, default: (Any) -> Any) : MutableMapWithDefault<K, V> {
   public open val map: MutableMap<Any, Any>
   private final val default: (Any) -> Any

   public open val size: Int
      public open get() {
         return this.getMap().size();
      }


   public open val keys: MutableSet<Any>
      public open get() {
         return this.getMap().keySet();
      }


   public open val values: MutableCollection<Any>
      public open get() {
         return this.getMap().values();
      }


   public open val entries: MutableSet<MutableEntry<Any, Any>>
      public open get() {
         return this.getMap().entrySet();
      }


   init {
      this.map = var1;
      this.default = var2;
   }

   public override fun clear() {
      this.getMap().clear();
   }

   public override fun containsKey(key: Any): Boolean {
      return this.getMap().containsKey(var1);
   }

   public override fun containsValue(value: Any): Boolean {
      return this.getMap().containsValue(var1);
   }

   public override operator fun equals(other: Any?): Boolean {
      return this.getMap().equals(var1);
   }

   public override operator fun get(key: Any): Any? {
      return this.getMap().get(var1);
   }

   public override fun getOrImplicitDefault(key: Any): Any {
      val var4: java.util.Map = this.getMap();
      val var3: Any = var4.get(var1);
      var var2: Any = var3;
      if (var3 == null) {
         var2 = var3;
         if (!var4.containsKey(var1)) {
            var2 = this.default.invoke((K)var1);
         }
      }

      return (V)var2;
   }

   public override fun hashCode(): Int {
      return this.getMap().hashCode();
   }

   public override fun isEmpty(): Boolean {
      return this.getMap().isEmpty();
   }

   public override fun put(key: Any, value: Any): Any? {
      return this.getMap().put((K)var1, (V)var2);
   }

   public override fun putAll(from: Map<out Any, Any>) {
      this.getMap().putAll(var1);
   }

   public override fun remove(key: Any): Any? {
      return this.getMap().remove(var1);
   }

   public override fun toString(): String {
      return this.getMap().toString();
   }
}
