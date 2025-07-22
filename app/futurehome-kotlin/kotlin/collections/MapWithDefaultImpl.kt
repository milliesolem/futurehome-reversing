package kotlin.collections

private class MapWithDefaultImpl<K, V>(map: Map<Any, Any>, default: (Any) -> Any) : MapWithDefault<K, V> {
   public open val map: Map<Any, Any>
   private final val default: (Any) -> Any

   public open val size: Int
      public open get() {
         return this.getMap().size();
      }


   public open val keys: Set<Any>
      public open get() {
         return this.getMap().keySet();
      }


   public open val values: Collection<Any>
      public open get() {
         return this.getMap().values();
      }


   public open val entries: Set<kotlin.collections.Map.Entry<Any, Any>>
      public open get() {
         return this.getMap().entrySet();
      }


   init {
      this.map = var1;
      this.default = var2;
   }

   override fun clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
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

   override fun put(var1: K, var2: V): V {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun putAll(var1: MutableMap<K, V>) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun remove(var1: Any): V {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public override fun toString(): String {
      return this.getMap().toString();
   }
}
