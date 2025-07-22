package kotlin.text.jdk8

public operator fun MatchGroupCollection.get(name: String): MatchGroup? {
   val var2: MatchNamedGroupCollection;
   if (var0 is MatchNamedGroupCollection) {
      var2 = var0 as MatchNamedGroupCollection;
   } else {
      var2 = null;
   }

   if (var2 != null) {
      return var2.get(var1);
   } else {
      throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
   }
}
