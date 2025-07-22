package kotlin

private object KotlinVersionCurrentValue {
   @JvmStatic
   public fun get(): KotlinVersion {
      return new KotlinVersion(2, 1, 0);
   }
}
