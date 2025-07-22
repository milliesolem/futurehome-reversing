package io.sentry.internal.modules;

import io.sentry.ILogger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class CompositeModulesLoader extends ModulesLoader {
   private final List<IModulesLoader> loaders;

   public CompositeModulesLoader(List<IModulesLoader> var1, ILogger var2) {
      super(var2);
      this.loaders = var1;
   }

   @Override
   protected Map<String, String> loadModules() {
      TreeMap var3 = new TreeMap();
      Iterator var1 = this.loaders.iterator();

      while (var1.hasNext()) {
         Map var2 = ((IModulesLoader)var1.next()).getOrLoadModules();
         if (var2 != null) {
            var3.putAll(var2);
         }
      }

      return var3;
   }
}
